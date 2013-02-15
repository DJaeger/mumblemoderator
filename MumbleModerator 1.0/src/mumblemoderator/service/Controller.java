/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mumblemoderator.service;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import javax.management.Notification;
import javax.swing.Timer;
import mumblemoderator.Globals;
import mumblemoderator.sample.Handler;
import mumblemoderator.sample.Observable;
//import mumblemoderator.service.AbstractHost.*;
import mumblemoderator.service.audio.AudioOutputHost;
import mumblemoderator.service.model.Channel;
import mumblemoderator.service.model.Message;
import mumblemoderator.service.model.User;
import mumblemoderator.ui.UIDataHolder;
import mumblemoderator.util.Util;

/**
 *
 * @author frank
 */
public class Controller extends Observable{

	public static final int CONNECTION_STATE_DISCONNECTED = 0;
	public static final int CONNECTION_STATE_CONNECTING = 1;
	public static final int CONNECTION_STATE_SYNCHRONIZING = 2;
	public static final int CONNECTION_STATE_CONNECTED = 3;
	private static final String[] CONNECTION_STATE_NAMES = {
		"Disconnected", "Connecting", "Synchronizing", "Connected"
	};
	int state;
	public static ServiceConnectionHost mConnectionHost;
	private static ServiceProtocolHost mProtocolHost;
	private static ServiceAudioOutputHost mAudioHost;
	private static MumbleProtocol mProtocol;
	private Thread mClientThread;
	private Notification mNotification;
	private Method mStartForeground;
//	private Method mStopForeground;
	private final Object[] mStartForegroundArgs = new Object[2];
//	private final Object[] mStopForegroundArgs = new Object[1];
//	private String errorString;
	private int serviceState;
	private boolean synced;
	
	private String _host;
	private String _login;
	private String _password;
	private int _port;
	private Console console;

	public final String getHost() {
		return _host;
	}

	private void setHost(String _host) {
		this._host = _host;
	}

	public final String getLogin() {
		return _login;
	}

	private void setLogin(String _login) {
		this._login = _login;
	}

	public final String getPassword() {
		return _password;
	}

	private void setPassword(String _password) {
		this._password = _password;
	}

	public final int getPort() {
		return _port;
	}

	private void setPort(int _port) {
		this._port = _port;
	}

	public Controller() {

		mConnectionHost = new ServiceConnectionHost();
		mProtocolHost = new ServiceProtocolHost();
		mAudioHost = new ServiceAudioOutputHost();

		MumbleConnection connection = new MumbleConnection(
				mConnectionHost,
				"mumble.piratenpartei-nrw.de",
				64738,
				"zzzMumbleModerator-devzzz",
				"");

		mProtocol = new MumbleProtocol(
				mProtocolHost,
				mAudioHost,
				connection);

		connection.start(mProtocol);
		
	}

	
	
	public Controller(String host, int port, String login, String password) {

		mConnectionHost = new ServiceConnectionHost();
		mProtocolHost = new ServiceProtocolHost();
		mAudioHost = new ServiceAudioOutputHost();
		
		setHost(host);
		setPort(port);
		setLogin(login);
		setPassword(password);

		connection = new MumbleConnection(
				mConnectionHost,
				getHost(),
				getPort(),
				getLogin(),
				getPassword());

		mProtocol = new MumbleProtocol(
				mProtocolHost,
				mAudioHost,
				connection);

		connection.start(mProtocol);
		
		console = new Console();

	}

	public void disconnect(){
		connection.disconnect();
		while (connection.isConnectionAlive()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

			}
			
		}
		
	}
	
	
	Handler handler = new Handler();
	private final Map<Object, IServiceObserver> observers = new HashMap<Object, IServiceObserver>();
	final List<Channel> channels = new ArrayList<Channel>();
//	final List<User> users = new ArrayList<User>();
	final Map<Integer,User> users = new HashMap<Integer, User>();
	
	final Map<User, Timer>usertimer = new HashMap<User, Timer>();
	private MumbleConnection connection;
	private String followsuser;

	public int getUserId(String username) {
		Iterator<User> tmp = users.values().iterator();
		while (tmp.hasNext()) {
			User user = tmp.next();
			if (user.name.equalsIgnoreCase(username)) {
				return user.hashCode();

			}
			
		}
		return -1;

	}

	public Channel currentChannel() {
		return currentUser().getChannel();
	}

	public User currentUser() {
		return mProtocol.currentUser;
	}

	public boolean isConnected() {
		return !mProtocolHost.isDisabled();
	}

	class ServiceConnectionHost extends AbstractHost implements MumbleConnectionHost{
		abstract class ServiceConnectionMessage extends ProtocolMessage {

			@Override
			protected Iterable<IServiceObserver> getObservers() {
				return observers.values();

			}

		}

		public void setConnectionState(final int state) {
			handler.post(new ServiceConnectionMessage() {

				@Override
				public void process() {
					if (Controller.this.state == state) {
						return;
					}

					Controller.this.state = state;

					// Handle foreground stuff
					if (state == MumbleConnectionHost.STATE_CONNECTED) {
						showNotification();
					} else if (state == MumbleConnectionHost.STATE_DISCONNECTED) {
						doConnectionDisconnect();
					}
				}

				@Override
				protected void broadcast(final IServiceObserver observer) {
				}
			});
		}

		@Override
		public void setError(final String error) {
			handler.post(new Runnable() {
				@Override
				public void run() {
//					errorString = error;
				}
			});
		}


	}

	class ServiceAudioOutputHost extends AbstractHost implements
		AudioOutputHost {
		abstract class ServiceAudioMessage extends ProtocolMessage {
			@Override
			protected Iterable<IServiceObserver> getObservers() {
				return observers.values();
			}
		}

		Map<User, Integer> userlist = new HashMap<User, Integer>();
		Map<User, Long> timesStamp = new HashMap<User,Long>();
		Map<User, Timer> usertimer = new HashMap<User,Timer>();

		
		ActionListener taskPerformer = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				//...Perform a task...
			}
		};
				
		@Override
		public void setTalkState(final User user, final int talkState) {
			if (talkState == AudioOutputHost.STATE_PASSIVE) {
//				Util.getLogger(Handler.class).log(Level.INFO, "" + talkState);
				if (usertimer.containsKey(user)) {
//					usertimer.get(user).restart();

				} else {
					Timer t = new Timer(600, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							Util.getLogger(Handler.class).log(Level.FINE, "TalkState "+ user.name +" changed: " + talkState);
							usertimer.remove(user);
							user.talkstop();
							notifyObserversUpdateUserTalkState(user, talkState);

						}
						
					});
					t.start();
					t.setRepeats(false);
					usertimer.put(user, t);

				}
				
			} if (talkState == AudioOutputHost.STATE_TALKING) {
//				Util.getLogger(Handler.class).log(Level.INFO, "" + talkState);
				if (usertimer.containsKey(user)) {
					usertimer.get(user).stop();
					usertimer.remove(user);

				} else {
					Util.getLogger(Handler.class).log(Level.FINE, "TalkState "+ user.name +" changed: " + talkState);
					user.talkstart();
					notifyObserversUpdateUserTalkState(user, talkState);
					
				}
			
			}

		}

		@Override
		public long getTimestamp(User user) {
			return timesStamp.get(user);
		}


		@Override
		public int getTalkState(User user) {
			return userlist.get(user);
		}

	}

	
	class ServiceProtocolHost extends AbstractHost implements MumbleProtocolHost{

		abstract class ServiceProtocolMessage extends ProtocolMessage {

			@Override
			protected Iterable<IServiceObserver> getObservers() {
				return observers.values();
			}
		}

		@Override
		public void channelAdded(final Channel channel) {
			Util.getLogger(Controller.class).log(Level.FINE, "channelAdded: " +
					channel.id + "|" + channel.pid + "|" + channel.name);

			notifyObserversAddChannel(channel);
			channels.add(channel);

		}

		@Override
		public void channelRemoved(int channelId) {
			Util.getLogger(Controller.class).log(Level.FINE, "channelRemoved: " + channelId);

			notifyObserversRemoveChannel(channelId);
			
			Channel channel = null;

			for (int i = 0; i < channels.size(); i++) {
				if (channels.get(i).id == channelId) {
					channel = channels.remove(i);
					break;
				}
			}
			
			if (channel != null) {
				
			}

		}

		@Override
		public void channelUpdated(Channel channel) {
			Util.getLogger(Controller.class).log(Level.FINE, "channelUpdated: " + channel.id);

			notifyObserversUpdateChannel(channel);

			for (int i = 0; i < channels.size(); i++) {
				if (channels.get(i).id == channel.id) {
					channels.set(i, channel);
					break;

				}

			}

		}


		@Override
		public void userAdded(User user) {
			Util.getLogger(Controller.class).log(Level.FINE, "userAdded: " + user.getChannel() + "*" + user.session + "*" + user.name);
			users.put(user.hashCode(), user);
			notifyObserversAddUser(user);

		}

		@Override
		public void userRemoved(int userId) {
			Util.getLogger(Controller.class).log(Level.FINE, "userRemoved: " + userId);
			users.remove(userId);
			notifyObserversRemoveUser(userId);

		}

		@Override
		public void userUpdated(User user) {
			Util.getLogger(Controller.class).log(Level.FINE, "userUpdated: " + user.session);
			notifyObserversUpdateUser(user);

		}
		
		@Override
		public void currentChannelChanged() {
			Util.getLogger(Controller.class).log(Level.INFO, "currentChannelChanged: ");
		}

		@Override
		public void currentUserUpdated() {
			Util.getLogger(Controller.class).log(Level.INFO, "currentUserUpdated: ");
		}
		
		
		
		@Override
		public void messageReceived(Message msg) {
			Util.getLogger(Controller.class).log(Level.INFO, "messageReceived: " + msg.message);
			notifyObserversReceivedMessage(msg);
		}

		@Override
		public void messageSent(Message msg) {
			Util.getLogger(Controller.class).log(Level.INFO, "messageSent: " + msg.message);
		}

		@Override
		public void setError(String error) {
			Util.getLogger(Controller.class).log(Level.INFO, "setError: " + error);
		}

		@Override
		public void setSynchronized(boolean synced) {
			Util.getLogger(Controller.class).log(Level.INFO, "setSynchronized: " + synced);
			if (synced) {
				notifyObserversServerSychronized();
				setComment(UIDataHolder.getComment());
				setMute(true);

			}
			
		}

	};

	
	public void joinChannel(final int channelId) {
		mProtocol.joinChannel(channelId);
		notifyObserversJoinChannel(channelId);

	}
	
	public void setComment(final String comment) {
		mProtocol.setComment(comment);
		notifyObserversSetComment(comment);

	}
	
	public void setMute(boolean value) {
		mProtocol.setMute(value);

	}
	
	public void setDeaf(boolean value) {
		mProtocol.setDeaf(value);

	}
	
	public void sendMessage(
			final String message,
			final Channel channel,
			final boolean connectedchannels, 
			final boolean lowerChannels) {

		mProtocol.sendChannelTextMessage(message, channel, connectedchannels, lowerChannels);

	}
	
	public void sendMessage(
			final String message,
			final User user) {

		mProtocol.sendUserTextMessage(message, user);

	}
	

	void showNotification() {
//		mNotification = new Notification(
//				R.drawable.icon,
//				"Mumble connected",
//				System.currentTimeMillis());

//		final Intent channelListIntent = new Intent(
//				Controller.this,
//				ChannelList.class);
//		channelListIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(
//				Intent.FLAG_ACTIVITY_NEW_TASK);
//		mNotification.setLatestEventInfo(
//				Controller.this,
//				"Mumble",
//				"Mumble is connected to a server",
//				PendingIntent.getActivity(
//				Controller.this,
//				0,
//				channelListIntent,
//				0));
		startForegroundCompat(1, mNotification);
	}

	void hideNotification() {
		if (mNotification != null) {
//			stopForegroundCompat(1);
			mNotification = null;
		}
	}

	void doConnectionDisconnect() {
		// First disable all hosts to prevent old callbacks from being processed.
		if (mProtocolHost != null) {
			mProtocolHost.disable();
			mProtocolHost = null;
		}

		if (mConnectionHost != null) {
			mConnectionHost.disable();
			mConnectionHost = null;
		}

		if (mAudioHost != null) {
			mAudioHost.disable();
			mAudioHost = null;
		}

		// Stop threads.
		if (mProtocol != null) {
			mProtocol.stop();
		}
		if (mClientThread != null) {
			mClientThread.interrupt();
		}

		// Broadcast state, this is synchronous with observers.
		state = MumbleConnectionHost.STATE_DISCONNECTED;
		updateConnectionState();

		hideNotification();

		// Now observers shouldn't need these anymore.
		users.clear();
		channels.clear();

	}

	/**
	 * This is a wrapper around the new startForeground method, using the older
	 * APIs if it is not available.
	 */
	void startForegroundCompat(final int id, final Notification notification) {
		// If we have the new startForeground API, then use it.
		if (mStartForeground != null) {
			mStartForegroundArgs[0] = Integer.valueOf(id);
			mStartForegroundArgs[1] = notification;
			try {
				mStartForeground.invoke(this, mStartForegroundArgs);
			} catch (final InvocationTargetException e) {
				// Should not happen.
//				Log.w(Globals.LOG_TAG, "Unable to invoke startForeground", e);
				Util.getLogger(Controller.class).log(Level.INFO, Globals.LOG_TAG + "Unable to invoke startForeground", e);
			} catch (final IllegalAccessException e) {
				// Should not happen.
//				Log.w(Globals.LOG_TAG, "Unable to invoke startForeground", e);
				Util.getLogger(Controller.class).log(Level.INFO, Globals.LOG_TAG + "Unable to invoke startForeground", e);
			}
			return;
		}

		// Fall back on the old API.
//		setForeground(true);
//		((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(
//			id,
//			notification);
	}

	void updateConnectionState() {
		final int oldState = serviceState;

		switch (state) {
			case MumbleConnectionHost.STATE_CONNECTING:
				serviceState = CONNECTION_STATE_CONNECTING;
				break;
			case MumbleConnectionHost.STATE_CONNECTED:
				serviceState = synced ? CONNECTION_STATE_CONNECTED
						: CONNECTION_STATE_SYNCHRONIZING;
				break;
			case MumbleConnectionHost.STATE_DISCONNECTED:
				serviceState = CONNECTION_STATE_DISCONNECTED;
				break;
			default:
				//Assert.fail();
				Util.getLogger(Controller.class).log(Level.INFO, "Unable to determinate ConnectioState");

		}

		if (oldState != serviceState) {
			broadcastState();
		}
	}


	public boolean isConnectionAlive() {
		return mProtocol.getConnection().isConnectionAlive();
		
	}

	
	private void broadcastState() {
//		for (final IServiceObserver observer : observers.values()) {
//			try {
//				observer.onConnectionStateChanged(serviceState);
//			} catch (final RemoteException e) {
////				Log.e(Globals.LOG_TAG, "Failed to update connection state", e);
//			Util.getLogger(Controller.class).log(Level.INFO, Globals.LOG_TAG + "Failed to update connection state");

//			}
//		}

//		Log.i(Globals.LOG_TAG, "MumbleService: Connection state changed to " +
//							   CONNECTION_STATE_NAMES[serviceState]);
		Util.getLogger(Controller.class).log(Level.INFO, Globals.LOG_TAG + "MumbleService: Connection state changed to "
				+ CONNECTION_STATE_NAMES[serviceState]);

	}

	
	public void setFollows(String user) {
		// TODO Auto-generated method stub
		followsuser = user;
	}
	
}