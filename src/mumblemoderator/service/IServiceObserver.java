package mumblemoderator.service;

import mumblemoderator.service.model.User;
import mumblemoderator.service.model.Message;
import mumblemoderator.service.model.Channel;

interface IServiceObserver {
	void onChannelAdded(Channel channel);
	void onChannelRemoved(Channel channel);
	void onChannelUpdated(Channel channel);

	void onCurrentChannelChanged();
	
	void onCurrentUserUpdated();
	
	void onUserAdded(User user);
	void onUserRemoved(User user);
	void onUserUpdated(User user);
	
	void onMessageReceived(Message msg);
	void onMessageSent(Message msg);
	
	/**
	 * Called when the connection state changes.
	 */
	void onConnectionStateChanged(int state);

}
