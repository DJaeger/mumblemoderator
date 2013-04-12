package mumblemoderator.sample;

import mumblemoderator.service.model.Channel;
import mumblemoderator.service.model.Message;
import mumblemoderator.service.model.User;

public interface Observer {

	public void channelupdate(Channel channel);
	public void channeladd(Channel channel);
	public void channelremove(int channelId);
	
	
	public void useradd(User user);
	public void userremove(int userId);
	public void userupdate(User user);

	public void usertalkstate(User user, int state);

	public void messageReceived(Message msg);
	
	public void serverSychronized();
	
	public void channelJoin(int channelId);
	public void commentSet(String comment);

}
