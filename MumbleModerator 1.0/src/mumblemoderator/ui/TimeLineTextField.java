/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mumblemoderator.ui;

import java.util.Calendar;
import java.util.Formatter;
import java.util.Locale;
import java.util.logging.Level;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;
import mumblemoderator.sample.Observable;
import mumblemoderator.sample.Observer;
import mumblemoderator.service.audio.AudioOutputHost;
import mumblemoderator.service.model.Channel;
import mumblemoderator.service.model.Message;
import mumblemoderator.service.model.User;
import mumblemoderator.util.Util;

/**
 *
 * @author ffitzke
 */
public class TimeLineTextField extends JTextArea implements Observer{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2248796701300183591L;
	
	int intent;
	
	public TimeLineTextField(){
		Observable.addObserver(this);
		DefaultCaret caret = (DefaultCaret)this.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		intent = 0;
		
	}
	

	@Override
	public void channelupdate(Channel channel) {
//		Util.getLogger(Handler.class).log(Level.INFO, "Not supported yet.");
	}

	@Override
	public void channeladd(Channel channel) {
//		Util.getLogger(Handler.class).log(Level.INFO, "Not supported yet.");
	}

	@Override
	public void channelremove(int channelId) {
//		Util.getLogger(Handler.class).log(Level.INFO, "Not supported yet.");
	}

	@Override
	public void useradd(User user) {
//		Util.getLogger(Handler.class).log(Level.INFO, "Not supported yet.");
	}

	@Override
	public void userremove(int userId) {
//		Util.getLogger(Handler.class).log(Level.INFO, "Not supported yet.");
	}

	@Override
	public void userupdate(User user) {
//		Util.getLogger(Handler.class).log(Level.INFO, "Not supported yet.");
	}
	@Override
	public void usertalkstate(User user, int talkstate) {
		String tmp = "";
		
		int i = 1;
		
		if (talkstate == AudioOutputHost.STATE_TALKING) {
			intent++;
			while (i<intent) {			
				tmp = tmp.concat("  ");
				i++;
			}
			this.append(tmp + user.name + " sendet");

		} else if (talkstate == AudioOutputHost.STATE_PASSIVE) {
			intent--;
			while (i<intent) {			
				tmp = tmp.concat("  ");
				i++;

			}
			this.append(tmp + user.name + " OVER! (" + user.gettalktime() + ")");
			

		} else {
			this.append(tmp + user.name + " Talkstatus geändert");
			
		}
		
	}

	@Override
	public void serverSychronized() {
//		Util.getLogger(TimeLineTextField.class).log(Level.INFO, "Not supported yet.");

	}
	
	@Override
	public void append(String str) {
		StringBuilder sb = new StringBuilder();
		StringBuffer strbuf = new StringBuffer();
		
		Formatter formatter = new Formatter(sb, Locale.GERMANY);

		strbuf.append(formatter.format("[%tT] ", Calendar.getInstance()).toString());

		strbuf.append(str).append("\n");
		
		super.append(strbuf.toString());
		formatter.close();
		
	}

	@Override
	public void channelJoin(int channelId) {
		Util.getLogger(TimeLineTextField.class).log(Level.INFO, "Not supported yet.");
	}

	@Override
	public void commentSet(String comment) {
		Util.getLogger(TimeLineTextField.class).log(Level.INFO, "Not supported yet.");
	}


	@Override
	public void messageReceived(Message msg) {
		append("" + msg.actor.name + " schreibt: " + msg.message);
	}
	
}
