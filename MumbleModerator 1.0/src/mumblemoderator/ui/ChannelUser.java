package mumblemoderator.ui;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;

import mumblemoderator.sample.Observable;
import mumblemoderator.sample.Observer;
import mumblemoderator.service.model.Channel;
import mumblemoderator.service.model.Message;
import mumblemoderator.service.model.User;
import mumblemoderator.util.Util;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.Scrollable;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.BevelBorder;

/**
 * 
 * @author frank
 */

class ValueComparator implements Comparator<User> {

	Map<User, Object> base;

	public ValueComparator(Map<User, Object> base) {
		this.base = base;
	}

	// Note: this comparator imposes orderings that are inconsistent with
	// equals.
	public int compare(User a, User b) {
		if (a.gettalktime() >= b.gettalktime()) {
			return -1;

		} else {
			return 1;

		} // returning 0 would merge keys

	}
}

public class ChannelUser extends JPanel implements Scrollable, Observer {

	private static final long serialVersionUID = -2534086634793943706L;

	/**
	 * Creates new form ChannelUser
	 */
	HashMap<User, Object> monitor;

	public ChannelUser() {
		setBackground(Color.LIGHT_GRAY);
		Observable.addObserver(this);
		this.setSize(10, 10);


		monitor = new HashMap<User, Object>();

		initComponents();
		
		UIDataHolder.setchanneluser(this);

	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */

	private void initComponents() {
		

	}
	
	@Override
	public void channelupdate(Channel channel) {
//		Util.getLogger(ChannelUser.class).log(Level.INFO, "Not supported yet.");

	}

	@Override
	public void channeladd(Channel channel) {
//		Util.getLogger(ChannelUser.class).log(Level.INFO, "Not supported yet.");

	}

	@Override
	public void channelremove(int channelId) {
//		Util.getLogger(ChannelUser.class).log(Level.INFO, "Not supported yet.");

	}

	@Override
	public void useradd(User user) {
//		Util.getLogger(ChannelUser.class).log(Level.INFO, "Not supported yet.");

	}

	@Override
	public void userremove(int userId) {
		Util.getLogger(ChannelUser.class).log(Level.FINE, "Not supported yet.");

	}

	@Override
	public void userupdate(User user) {
		Util.getLogger(ChannelUser.class).log(Level.FINE, "Not supported yet.");

	}

	@Override
	public void usertalkstate(User user, int state) {

		if (!monitor.containsKey(user) && (state != User.TALKINGSTATE_PASSIVE)) {
			UserTimer userTimer = new UserTimer(user);
			monitor.put(user, userTimer);

			setPreferredSize(new Dimension((int) userTimer
					.getPreferredSize().getWidth() + 10, (((int) userTimer
					.getPreferredSize().getHeight() + 5) * monitor.size()) + 5));
			
			setSize(getPreferredSize());

			add(userTimer);

			
		}

		if (user != null) {
			UserTimer usertComponent = ((UserTimer) monitor.get(user));
			if (usertComponent != null) {
				if (state != User.TALKINGSTATE_PASSIVE) {
					usertComponent.setBackground(new Color(169, 220, 169));
					usertComponent.startTimer();

				} else {
					usertComponent.setBackground(new Color(169, 169, 169));
					usertComponent.stopTimer();

					removeAll();

					ValueComparator bvc = new ValueComparator(monitor);
					TreeMap<User, Object> sorted_map = new TreeMap<User, Object>(bvc);
					sorted_map.putAll(monitor);

					Iterator<User> it = sorted_map.keySet().iterator();
					while (it.hasNext()) {
						User pairs = (User) it.next();

						UserTimer obj = ((UserTimer) monitor.get(pairs));

						add(obj);
						
					}
					repaint();

				}
				
			}

		}

	}

	public String getStatistik(boolean html) {
		StringBuffer strbuf = new StringBuffer();

		ValueComparator bvc = new ValueComparator(monitor);
		TreeMap<User, Object> sorted_map = new TreeMap<User, Object>(bvc);
		sorted_map.putAll(monitor);

		Iterator<User> it = sorted_map.keySet().iterator();
		UserTimer obj = null;
		while (it.hasNext()) {
			User pairs = (User) it.next();
	
			strbuf
			.append(Util.millisectoString(pairs.gettalktime()))
			.append(" : ")
			.append(Util.millisectoString(pairs.getavgtalktime()))
			.append(" : ")
			.append(pairs.name);
			
			if (html) 
				strbuf.append("<br>");
			strbuf.append("\n");
			
			if (!it.hasNext())
				obj = ((UserTimer) monitor.get(pairs));
			
		}

		if (obj != null) {

			strbuf.append("================================");
			if (html) 
				strbuf.append("<br>");
			strbuf.append("\n");
		
			strbuf.append(Util.sectoString(UserTimer.getAvgTalkTime()));
		}
		
		return strbuf.toString();
	}


	
	@Override
	public void serverSychronized() {
//		Util.getLogger(ChannelUser.class).log(Level.INFO, "Not supported yet.");

	}

	@Override
	public void channelJoin(int channelId) {
		Iterator<User> it = monitor.keySet().iterator();
		while (it.hasNext()) {
			User pairs = (User) it.next();
			remove(((UserTimer) monitor.get(pairs)));

		}
		revalidate();
		repaint();

		monitor.clear();

	}

	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return this.getPreferredSize();

	}

	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void commentSet(String comment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void messageReceived(Message msg) {
		// TODO Auto-generated method stub
		
	}

}
