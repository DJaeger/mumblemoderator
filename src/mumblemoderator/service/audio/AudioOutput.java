package mumblemoderator.service.audio;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.Control.Type;
import javax.sound.sampled.Line.Info;
import javax.sound.sampled.*;
import mumblemoderator.Settings;
import mumblemoderator.sample.Context;
import mumblemoderator.service.MumbleProtocol;
import mumblemoderator.service.PacketDataStream;
import mumblemoderator.service.audio.AudioUser.PacketReadyHandler;
import mumblemoderator.service.model.User;
import mumblemoderator.util.Util;


/**
 * Audio output thread.
 * Handles the playback of UDP packets added with addFrameToBuffer.
 *
 * @author pcgod, Rantanen
 */
public class AudioOutput implements Runnable {
	private final PacketReadyHandler packetReadyHandler = new PacketReadyHandler() {
		@Override
		public void packetReady(final AudioUser user) {
			synchronized (userPackets) {
				if (!userPackets.containsKey(user.getUser())) {
					host.setTalkState(
						user.getUser(),
						AudioOutputHost.STATE_TALKING);
					userPackets.put(user.getUser(), user);
					userPackets.notify();
				}
			}
		}
	};

	private final static int standbyTreshold = 5000;
	private final Settings settings;

	private boolean shouldRun;
//	private final AudioTrack at;
	private final int bufferSize;
	private final int minBufferSize;

	final Map<User, AudioUser> userPackets = new HashMap<User, AudioUser>();
	private final Map<User, AudioUser> users = new HashMap<User, AudioUser>();

	/**
	 * Buffer used to hold temporary float values while mixing multiple
	 * inputs. Only for use in the audio thread.
	 */
	final float[] tempMix = new float[MumbleProtocol.FRAME_SIZE];

	private final AudioOutputHost host;

	private final AudioFormat format;
	private final int desiredBufferSize;
	private SourceDataLine line;

	
	public AudioOutput(final Context ctx, final AudioOutputHost host) {
		this.settings = new Settings(ctx);
		this.host = host;

//		minBufferSize = AudioTrack.getMinBufferSize(
//			MumbleProtocol.SAMPLE_RATE,
//			AudioFormat.CHANNEL_CONFIGURATION_MONO,
//			AudioFormat.ENCODING_PCM_16BIT);

		format = new AudioFormat(MumbleProtocol.SAMPLE_RATE, 16, 1, true, true);
		try {
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
			line = (SourceDataLine) AudioSystem.getLine(info);
			line.open(format);
		} catch (LineUnavailableException ex) {
			ex.printStackTrace();
		}

		// Double the buffer size to reduce stuttering.
		desiredBufferSize = line.getBufferSize() / 5;
		minBufferSize = line.getBufferSize() / 10;
		
		bufferSize = line.getBufferSize();
		
				// Resolve the minimum frame count that fills the minBuffer requirement.
		final int frameCount = (int) Math.ceil((double) desiredBufferSize /
											   MumbleProtocol.FRAME_SIZE);

		// Set this here so this.start(); this.shouldRun = false; doesn't
		// result in run() setting shouldRun to true afterwards and continuing
		// running.
		shouldRun = true;
	}

	public void addFrameToBuffer(
		final User u,
		final PacketDataStream pds,
		final int flags) {
		AudioUser user = users.get(u);
		if (user == null) {
			user = new AudioUser(u, settings.isJitterBuffer());
			users.put(u, user);
			// Don't add the user to userPackets yet. The collection should
			// have only users with ready frames. Since this method is
			// called only from the TCP connection thread it will never
			// create a new AudioUser while a previous one is still decoding.
		}

		user.addFrameToBuffer(pds, packetReadyHandler);
	}

	public void run() {
//		android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
		try {
			audioLoop();
		} catch (final InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void stop() {
		shouldRun = false;
		synchronized (userPackets) {
			userPackets.notify();
		}
	}

	private void audioLoop() throws InterruptedException {
		final short[] out = new short[MumbleProtocol.FRAME_SIZE];
		final byte[] lineout = new byte[MumbleProtocol.FRAME_SIZE];
		final List<AudioUser> mix = new LinkedList<AudioUser>();

		int buffered = 0;
		boolean playing = false;

		while (shouldRun) {
			mix.clear();

			// Get mix frames from the AudioUsers
			fillMixFrames(mix);

			// If there is output, play it now.
			if (mix.size() > 0) {
				// Mix all the frames into one array.
//				mix(out, mix);
				mixline(lineout,mix);
				
//				at.write(out, 0, MumbleProtocol.FRAME_SIZE);
				line.write(lineout, 0, MumbleProtocol.FRAME_SIZE);
				line.flush();
				line.drain();
				// Make sure we are playing when there are enough samples
				// buffered.
				if (!playing) {
					buffered += out.length;

					if (buffered >= minBufferSize) {
//						at.play();
//						line.start();
						playing = true;
						buffered = 0;

						Util.getLogger(AudioOutput.class).log(Level.INFO, "AudioOutput: Enough data buffered. Starting audio.");

					}

				} else {
					line.flush();
					
				}

				// Continue with playback since we know that there is at least
				// one AudioUser in userPackets that wasn't removed as it had
				// frames for mixing.
				continue;
			}

			// Wait for more input.
			playing &= !pauseForInput();
			if (!playing && buffered > 0) {
//				Util.getLogger(AudioOutput.class).log(Level.FINEST, "AudioOutput: Stopped playing while buffered data present.");

			}
		}

		line.drain();
		line.stop();
//		at.flush();
//		at.stop();
	}

	private void fillMixFrames(final List<AudioUser> mix) {
		synchronized (userPackets) {
			final Iterator<AudioUser> i = userPackets.values().iterator();
			while (i.hasNext()) {
				final AudioUser user = i.next();
				if (user.hasFrame()) {
					mix.add(user);
				} else {
					i.remove();
					host.setTalkState(user.getUser(), AudioOutputHost.STATE_PASSIVE);

				}
			}
		}
	}
	
	private void mix(final short[] clipOut, final List<AudioUser> mix) {
		// Reset mix buffer.
		Arrays.fill(tempMix, 0);

		// Sum the buffers.
		for (final AudioUser user : mix) {
			for (int i = 0; i < tempMix.length; i++) {
				tempMix[i] += user.lastFrame[i];
			}
		}

		// Clip buffer for real output.
		for (int i = 0; i < MumbleProtocol.FRAME_SIZE; i++) {
			clipOut[i] = (short) (Short.MAX_VALUE * (tempMix[i] < -1.0f ? -1.0f
				: (tempMix[i] > 1.0f ? 1.0f : tempMix[i])));
		}
	}

	private boolean pauseForInput() throws InterruptedException {
		long silentTime;
		boolean paused = false;
		synchronized (userPackets) {
			silentTime = System.currentTimeMillis();

			// Wait with the audio on
			while (shouldRun && userPackets.isEmpty() &&
				   (silentTime + standbyTreshold) > System.currentTimeMillis()) {

				userPackets.wait((silentTime + standbyTreshold) -
								 System.currentTimeMillis() + 1);
			}

			// If conditions are still not filled, pause audio and wait more.
			if (shouldRun && userPackets.isEmpty()) {
//				at.pause();
				paused = true;

				Util.getLogger(AudioOutput.class).log(Level.INFO, "AudioOutput: Standby timeout reached. Audio paused.");
				 
				while (shouldRun && userPackets.isEmpty()) {
					userPackets.wait();
				}
			}
		}
		return paused;
	}

	private void mixline(byte[] lineout, List<AudioUser> mix) {

//		byte[] tempMix2 = new byte[MumbleProtocol.FRAME_SIZE];
		// Reset mix buffer.
		Arrays.fill(tempMix, 0);

		// Sum the buffers.
		for (final AudioUser user : mix) {
			for (int i = 0; i < tempMix.length; i++) {
				tempMix[i] += user.lastFrame[i];
			}
		}

		// Clip buffer for real output.
		for (int i = 0; i < MumbleProtocol.FRAME_SIZE; i++) {
			lineout[i] = (byte) (Byte.MAX_VALUE * (tempMix[i] < -1.0f ? -1.0f
				: (tempMix[i] > 1.0f ? 1.0f : tempMix[i])));
			
		}
		
	}
} 
