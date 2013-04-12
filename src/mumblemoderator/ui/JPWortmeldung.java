package mumblemoderator.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.Timer;

import mumblemoderator.sample.Observable;
import mumblemoderator.sample.Observer;
import mumblemoderator.service.Controller;
import mumblemoderator.service.model.Channel;
import mumblemoderator.service.model.Message;
import mumblemoderator.service.model.User;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JScrollPane;


class TextMessage{
	private String _bgcolor;
	private String _subject;
	private String _text;
	private String _redeliste = null;
	private String _debug = null;
	private String _textPanestr1;
	private String _textPanestr2;
	private String _textPanestr3;
	private String _textPanestr4;
	
	
	public TextMessage(String bgcolor, String subject, String text) {
		this(bgcolor,subject, text, null);
		
	}

	public TextMessage(String bgcolor, String subject, String text, String redeliste) {
		this(bgcolor,subject, text, redeliste, null);
		
	}
	
	public TextMessage(String bgcolor, String subject, String text, String redeliste, String debug) {
			super();
		this._bgcolor = bgcolor;
		this._subject = subject;
		this._text = text;
		this._redeliste = redeliste;
		this._debug = debug;

		_textPanestr1 = new String(
				  " <table border=\"0\" width=\"100%\"  style=\"padding:10px; background:%bgcolor%; color:#000000; font-family:'Georgia,Times New Roman,Times,serif'\">"
				+ "	<tr><td style=\"padding:10px; text-align:center; font-weight:bold;\" align=\"center\">"
				+ "	%subject%"
				+ "	</td></tr>"
				+ "	<tr><td align=\"center\" style=\"padding:10px; text-align:center; \">"
				+ "	%text%"
				+ "	</td></tr>");

		_textPanestr2 = new String(
				  "	<tr><td style=\"padding:10px; text-align:left; \">"
				+ "	%redeliste%"
				+ "	</td></tr>");

		_textPanestr3 = new String(
				  " <tr><td style=\"padding:10px; text-align:right; font-size:8px;\">"
				+ "	%debug%"
				+ "	</td></tr>");

		_textPanestr4 = new String(
				  " </table>");
	}

	
	public TextMessage() {
		this("#00CC66","Subject","Text", null,"");
	}

	public void setbgcolor(String text){
		this._bgcolor = text;
	}
	
	public void setsubject(String text){
		this._subject = text;
	}
	
	public void setText(String text){
		this._text = text;
	}
	
	public void setRedeliste(String text){
		this._redeliste = text;
	}
	
	public void setDebug(String text){
		this._debug = text;
	}

	
	public String getText(){
		StringBuffer strbuf = new StringBuffer();
		strbuf.append(_textPanestr1);
		
		String searchstr = new String("%bgcolor%");

		int index = strbuf.indexOf(searchstr);
		strbuf.replace(index, index + searchstr.length(), this._bgcolor);
		
		searchstr = new String("%subject%");

		index = strbuf.indexOf(searchstr);
		strbuf.replace(index, index + searchstr.length(), this._subject);
		
		searchstr = new String("%text%");

		index = strbuf.indexOf(searchstr);
		strbuf.replace(index, index + searchstr.length(), this._text);

		if (this._redeliste != null) {
			searchstr = new String("%redeliste%");
			StringBuffer strbuf2 = new StringBuffer();
			strbuf2.append(_textPanestr2);
			index = strbuf2.indexOf(searchstr);
			strbuf2.replace(index, index + searchstr.length(), this._redeliste);
			strbuf.append(strbuf2.toString());
		}
		
		if (this._debug != null) {
			searchstr = new String("%debug%");
			StringBuffer strbuf2 = new StringBuffer();
			strbuf2.append(_textPanestr3);
			index = strbuf2.indexOf(searchstr);
			strbuf2.replace(index, index + searchstr.length(), this._debug);
			strbuf.append(strbuf2.toString());
		}


		strbuf.append(_textPanestr4);
		
		return strbuf.toString();
	}
}




public class JPWortmeldung extends JPanel implements Observer {

	private static final long serialVersionUID = -7678968222555402994L;

	private JTextField textField;
	private Timer timer;
	private JCheckBox checkBox;

	private JCheckBox chckbxCopyToMe;

	private JCheckBox chckbxMitUnterrumen;
	private JScrollPane scrollPane;
	private JTextField textField_1;

	/**
	 * Create the panel.
	 */
	public JPWortmeldung() {

		JPanel panel_1 = new JPanel();
		
		checkBox = new JCheckBox("Aktiv");
		
		chckbxCopyToMe = new JCheckBox("copy to me");
		chckbxCopyToMe.setSelected(false);
		chckbxCopyToMe.setEnabled(true);
		chckbxCopyToMe.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (chckbxCopyToMe.isSelected()) {
					chckbxMitUnterrumen.setSelected(!chckbxCopyToMe.isSelected());
				}
			}
		});

		
		
		chckbxMitUnterrumen = new JCheckBox("mit Unterr\u00E4umen");
		chckbxMitUnterrumen.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (chckbxMitUnterrumen.isSelected()) {
					chckbxCopyToMe.setSelected(!chckbxMitUnterrumen.isSelected());
					
				};
			}
		});

		chckbxMitUnterrumen.setSelected(false);
		
		JCheckBox chckbxPrivat = new JCheckBox("privat");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(checkBox)
						.addComponent(chckbxPrivat))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
						.addComponent(chckbxMitUnterrumen, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
						.addComponent(chckbxCopyToMe, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(checkBox)
						.addComponent(chckbxCopyToMe))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(chckbxMitUnterrumen)
						.addComponent(chckbxPrivat))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		
		JLabel lblIntervall = new JLabel("Intervall:");
		lblIntervall.setHorizontalAlignment(SwingConstants.RIGHT);
		
		textField = new JTextField();
		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				
					int timerdelay = (int)Integer.parseInt(textField.getText());
					if (timerdelay < 60) {
						timerdelay = 60;
					}
					if (timerdelay > 1800)
						timerdelay = 1800;
					
					textField.setText(new Integer(timerdelay).toString());

					updateTimer();
				
			}
		});
		textField.setText("600");
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setColumns(10);
		
		JLabel label_1 = new JLabel("Sekunden");
		
		JButton button = new JButton("Fire");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		scrollPane = new JScrollPane();
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Follows:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JButton btnNewButton = new JButton("Du hast das Wort");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String str = new TextMessage("#00CC66", "Redeliste", "Du hast das Wort").getText();

				sendMessage(str, UIDataHolder.getController().currentChannel());
				
			}
		});
		
		JButton btnNewButton_1 = new JButton("Redeliste leer");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String text = "Die Redeliste ist leer. Jetzt kann wieder jeder etwas sagen.<br />"
						+ "Schreib' 'w' in den Chat um Dich auf die Redeliste zu setzen.";

				
				String str = new TextMessage("#00CC66", "Redeliste", text).getText();

				sendMessage(str, UIDataHolder.getController().currentChannel());

			}
		});
		
		JButton btnNewButton_2 = new JButton("x hat das Wort");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = "%User% hat das Wort.";

				
				String str = new TextMessage("#00CC66", "Redeliste", text).getText();

				sendMessage(str, UIDataHolder.getController().currentChannel());

			}
		});

		
		JButton btnNewButton_3 = new JButton("Nicht das Wort");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = "Du stehst nicht auf der Redeliste<br />"
						+ "Bitte schreib' 'w' in den Chat um Dich auf die Redeliste zu setzen.";

				String str = new TextMessage("#CC6600", "Redeliste", text).getText();

				sendMessage(str, UIDataHolder.getController().currentChannel());

			}
		});
		
		JButton btnNewButton_4 = new JButton("Noch nicht das Wort");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = "Du stehst zwar auf der Redeliste.<br />"
						+ "Du bist aber jetzt noch nicht dran.";

				String str = new TextMessage("#FFFF33", "Redeliste", text).getText();

				sendMessage(str, UIDataHolder.getController().currentChannel());

			}
		});
		
		JButton btnNewButton_5 = new JButton("Redeliste");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = "%User% hat sich auf die Redeliste gesetzt.";
				String text2 = "Redeliste:"
						+ "<br />- %User1%"
						+ "<br />- %User2%"
						+ "<br />- %User3%"
						+ "<br />- %User4%";

				String str = new TextMessage("#6666ff", "Wortmeldung", text, text2).getText();

				sendMessage(str, UIDataHolder.getController().currentChannel());

			}
		});

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblIntervall)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textField, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(label_1)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(button))
								.addComponent(textField_1)))
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(btnNewButton)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNewButton_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNewButton_2))
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(btnNewButton_3)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNewButton_4))
						.addComponent(btnNewButton_5, Alignment.LEADING))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(button)
								.addComponent(label_1)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblIntervall))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel)))
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton)
						.addComponent(btnNewButton_1)
						.addComponent(btnNewButton_2))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton_3)
						.addComponent(btnNewButton_4))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton_5)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
					.addContainerGap())
		);
		setLayout(groupLayout);
		
		timer = new Timer(Integer.MAX_VALUE, new MyActionListener());

	}
	
	
	private void updateTimer() {
		int timerdelay = (int)Integer.parseInt(textField.getText());
		timer.setInitialDelay(1000);
		timer.setDelay(timerdelay*1000);
		timer.setRepeats(true);
		timer.restart();
		
	}
	
	
	class MyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Controller controller = UIDataHolder.getController();
//			Channel channel = controller.currentChannel();
			if (!checkBox.isSelected()) {
				return;

			}

			if (!controller.isConnected()) {
				return;

			}
			
//			controller.sendMessage(textPane.getText(), channel, chckbxCopyToMe.isSelected(), chckbxMitUnterrumen.isSelected());

		}

	}

	private void sendMessage(String msg, Channel channel) {
		Controller controller = UIDataHolder.getController();
		controller.sendMessage(msg, channel, false, false);

	}
	
	private void sendMessage(String msg, User user) {
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


