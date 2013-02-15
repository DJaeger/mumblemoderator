package mumblemoderator.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextPane;
import javax.swing.JCheckBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.Timer;

import mumblemoderator.service.Controller;
import mumblemoderator.service.model.Channel;
import javax.swing.AbstractAction;
import javax.swing.Action;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;


/*

57 : Dicker Engel unmoderiert(858)
	858 : Diskussion(864)
		864 : Podium(866)
		864 : Saal Mikrofon(867)
		864 : Zuhörer(865)
	858 : Keller(1863)
	858 : Ruheraum(863)
	

0 : Dicker Engel unmoderiert(858)
864 : 867 : 

0 : Diskussion(864)
858 : 

0 : Podium(866)
865 : 867 : 


0 : Saal Mikrofon(867)
858 : 866 : 

0 : Zuhörer(865)
866 : 


858 -> 864
864 -> 858

858 -> 867

866 -> 865
865 -> 866

866 -> 867
867 -> 866



*/







public class JPCrontab extends JPanel {
	private static final long serialVersionUID = -877098570386280110L;

	private JTextField textField;
	private Timer timer;
	private JCheckBox checkBox;
	private final Action action = new SwingAction();

	private JTextPane textPane;

	private JCheckBox checkBox_1;

	private JCheckBox chckbxMitUnterrumen;

	/**
	 * Create the panel.
	 */
	public JPCrontab() {
		
		textPane = new JTextPane();

		StringBuffer textPanestr = new StringBuffer();
		textPanestr.append("<span ");
		textPanestr.append("style=\"color:#000000; background-color:#cccccc; ");
		textPanestr.append("font-weight:bold; font-family:Georgia, 'Times New Roman', Times, serif; \">\n");
		textPanestr.append("<br />");
		textPanestr.append("Ich bin eine Nachricht, die nach einem eingestellten Intervall immer wieder in den Channel gesendet wird.");
		textPanestr.append("<br />");
		textPanestr.append("</span>");
		textPane.setText(textPanestr.toString());
		
		JPanel panel_1 = new JPanel();
		
		checkBox = new JCheckBox("Aktiv");
		
		checkBox_1 = new JCheckBox("verbundene R\u00E4ume");
		checkBox_1.setSelected(false);
		checkBox_1.setEnabled(true);
		checkBox_1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (checkBox_1.isSelected()) {
					chckbxMitUnterrumen.setSelected(!checkBox_1.isSelected());
				}
			}
		});

		
		
		chckbxMitUnterrumen = new JCheckBox("mit Unterr\u00E4umen");
		chckbxMitUnterrumen.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (chckbxMitUnterrumen.isSelected()) {
					checkBox_1.setSelected(!chckbxMitUnterrumen.isSelected());
					
				};
			}
		});

		chckbxMitUnterrumen.setSelected(false);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(checkBox)
					.addGap(18)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(chckbxMitUnterrumen, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
						.addComponent(checkBox_1, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(checkBox)
						.addComponent(checkBox_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(chckbxMitUnterrumen)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		
		JLabel label = new JLabel("Intervall:");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		
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
		button.setAction(action);

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(textPane, GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
							.addComponent(label)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(label_1)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(button)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(button)
							.addComponent(label_1)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(label))
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textPane, GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
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
			Channel channel = controller.currentChannel();
			if (!checkBox.isSelected()) {
				return;

			}

			if (!controller.isConnected()) {
				return;

			}
			
			controller.sendMessage(textPane.getText(), channel, checkBox_1.isSelected(), chckbxMitUnterrumen.isSelected());

		}

	}
	
	private class SwingAction extends AbstractAction {
		private static final long serialVersionUID = 8132815659796857254L;
		public SwingAction() {
			putValue(NAME, "Fire");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			updateTimer();
			timer.restart();
			
		}
	}
}


