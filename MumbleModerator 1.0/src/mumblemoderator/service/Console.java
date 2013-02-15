package mumblemoderator.service;

import mumblemoderator.sample.Observable;
import mumblemoderator.sample.Observer;
import mumblemoderator.service.model.Channel;
import mumblemoderator.service.model.Message;
import mumblemoderator.service.model.User;
import mumblemoderator.ui.UIDataHolder;

public class Console implements Observer {

	
	public Console(){
		Observable.addObserver(this);
		
	}
	
	public void sendMessage(String msg, Channel channel) {
		Controller controller = UIDataHolder.getController();
		controller.sendMessage(msg, channel, false, false);

	}
	
	public void sendMessage(String msg, User user) {
		Controller controller = UIDataHolder.getController();
		controller.sendMessage(msg, user);

	}
	
	
	
	private void handleMessage(Message msg){
		String commandstring = "!echo ";
		if (msg.message.startsWith(commandstring)) {
//			sendMessage(msg.message.substring(commandstring.length()), UIDataHolder.getController().currentChannel());
			sendMessage(msg.message.substring(commandstring.length()), msg.actor);
			int startlength = commandstring.length();

			Message msg2 = new Message();
			msg2.actor = UIDataHolder.getController().currentUser();
			msg2.message = msg.message.substring(startlength);
			msg2.channel = msg.channel;
			msg2.sender = UIDataHolder.getController().currentUser().name;
			
			Observable.notifyObserversReceivedMessage(msg2);
		}
		

		commandstring = "!help";
		if (msg.message.equalsIgnoreCase(commandstring)) {
//			sendMessage(msg.message.substring(commandstring.length()), UIDataHolder.getController().currentChannel());
			sendMessage(getUsage(), msg.actor);

			Message msg2 = new Message();
			msg2.actor = UIDataHolder.getController().currentUser();
			msg2.message = getUsage();
			msg2.channel = msg.channel;
			msg2.sender = UIDataHolder.getController().currentUser().name;
			
			Observable.notifyObserversReceivedMessage(msg2);
		}

		
		commandstring = "!bs";
		if (msg.message.equalsIgnoreCase(commandstring)) {
//			sendMessage(msg.message.substring(commandstring.length()), UIDataHolder.getController().currentChannel());

			StringBuffer strbuf = new StringBuffer();
			
			
			strbuf.append("<br>\n");
			strbuf.append(UIDataHolder.getchanneluser().getStatistik(true));
			
			
			sendMessage(strbuf.toString(), msg.actor);

			Message msg2 = new Message();
			msg2.actor = UIDataHolder.getController().currentUser();
			msg2.message = strbuf.toString();
			msg2.channel = msg.channel;
			msg2.sender = UIDataHolder.getController().currentUser().name;
			
			Observable.notifyObserversReceivedMessage(msg2);
		}
	
	}
	

	private String getUsage() {
		StringBuffer strbuf = new StringBuffer();
		
		strbuf.append("<br>\n")
		.append("!help: Liefert diesen Text.<br>")
		.append("!bs: Sprecherstatistik<br>")
		.append("!w: [Wortmeldung]<br>")
		.append("!wl: [Wortmeldungsliste]<br>");
		
		return strbuf.toString();
	}
	

	
	@Override
	public void messageReceived(Message msg) {
		handleMessage(msg);
		
	}
	

	
	
	@Override
	public void channelupdate(Channel channel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void channeladd(Channel channel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void channelremove(int channelId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void useradd(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void userremove(int userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void userupdate(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void usertalkstate(User user, int state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void serverSychronized() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void channelJoin(int channelId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commentSet(String comment) {
		// TODO Auto-generated method stub
		
	}

	
	
	

}
