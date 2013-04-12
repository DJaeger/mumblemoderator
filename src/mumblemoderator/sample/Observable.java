package mumblemoderator.sample;

import java.util.ArrayList;
import java.util.List;
import mumblemoderator.service.model.Channel;
import mumblemoderator.service.model.Message;
import mumblemoderator.service.model.User;

public class Observable {
	
	private static List<Observer> observers = new ArrayList<Observer>();
	
	public synchronized static void addObserver(Observer o) {
		observers.add(o);

	}
	
	public synchronized static void removeObserver(Observer o) {
		observers.remove(o);

	}
	
	public synchronized static void notifyObserversAddChannel(Channel channel) {
		for (Observer o : observers) {
			o.channeladd(channel);

		}

	}

	public synchronized static void notifyObserversRemoveChannel(int channelId) {
		for (Observer o : observers) {
			o.channelremove(channelId);

		}

	}

	public synchronized static void notifyObserversUpdateChannel(Channel channel) {
		for (Observer o : observers) {
			o.channelupdate(channel);

		}

	}

	public synchronized static void notifyObserversAddUser(User user) {
		for (Observer o : observers) {
			o.useradd(user);

		}

	}

	public synchronized static void notifyObserversRemoveUser(int userId) {
		for (Observer o : observers) {
			o.userremove(userId);

		}

	}

	public synchronized static void notifyObserversUpdateUser(User user) {
		for (Observer o : observers) {
			o.userupdate(user);

		}

	}

	public synchronized static void notifyObserversUpdateUserTalkState(User user, int state) {
		for (Observer o : observers) {
			o.usertalkstate(user, state);

		}

	}


	public synchronized static void notifyObserversReceivedMessage(Message msg) {
		for (Observer o : observers) {
			o.messageReceived(msg);

		}

	}

	
	
	
	public synchronized static void notifyObserversJoinChannel(int channelId) {
		for (Observer o : observers) {
			o.channelJoin(channelId);

		}
	}

	public synchronized static void notifyObserversSetComment(String comment) {
		for (Observer o : observers) {
			o.commentSet(comment);

		}
	}

	public synchronized static void notifyObserversServerSychronized() {
		for (Observer o : observers) {
			o.serverSychronized();

		}

	}

}
