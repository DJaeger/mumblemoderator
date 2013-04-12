package mumblemoderator.service;

import mumblemoderator.service.model.Channel;
import mumblemoderator.service.model.Message;
import mumblemoderator.service.model.User;

/**
 * Callback interface for Protocol to communicate back to the Service
 *
 * @author Rantanen
 *
 */
public interface MumbleProtocolHost {
	public void channelAdded(Channel channel);

	public void channelRemoved(int channelId);

	public void channelUpdated(Channel channel);

	public void currentChannelChanged();

	public void currentUserUpdated();

	public void messageReceived(Message msg);

	public void messageSent(Message msg);

	public void setError(String error);

	public void setSynchronized(boolean synced);

	public void userAdded(User user);

	public void userRemoved(int userId);

	public void userUpdated(User user);
}
