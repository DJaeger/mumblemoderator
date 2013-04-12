/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mumblemoderator.sample;

import javax.sound.sampled.Control.Type;
import javax.sound.sampled.*;

/**
 *
 * @author frank
 */
public class AudioTrack implements SourceDataLine {

	@Override
	public void open(AudioFormat format, int bufferSize) throws LineUnavailableException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void open(AudioFormat format) throws LineUnavailableException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int write(byte[] b, int off, int len) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void drain() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void flush() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void start() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void stop() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean isRunning() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean isActive() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public AudioFormat getFormat() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int getBufferSize() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int available() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int getFramePosition() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public long getLongFramePosition() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public long getMicrosecondPosition() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public float getLevel() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Info getLineInfo() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void open() throws LineUnavailableException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void close() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean isOpen() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Control[] getControls() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean isControlSupported(Type control) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Control getControl(Type control) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void addLineListener(LineListener listener) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void removeLineListener(LineListener listener) {
		throw new UnsupportedOperationException("Not supported yet.");
	}


}
