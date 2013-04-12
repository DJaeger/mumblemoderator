package mumblemoderator.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;

import mumblemoderator.service.Controller;

public class VerbindenJFrame2 extends JFrame {

	private static final long serialVersionUID = -5054379156202552017L;

	private JPanel contentPane;
	private JTextField txt_Adresse;
	private JTextField txt_Port;
	private JTextField txt_login;
	private JTextField txt_follows;
	private JLabel lbl_Adresse;
	private JLabel lbl_Port;
	private JLabel lbl_login;
	private JLabel lbl_follows;
	private final Action action_ok    = new SwingAction();
	private final Action action_p12   = new SwingAction_1();
	private final Action action_close = new SwingAction_2();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VerbindenJFrame2 frame = new VerbindenJFrame2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VerbindenJFrame2() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		lbl_Adresse = new JLabel("Adresse:");
		lbl_Adresse.setHorizontalAlignment(SwingConstants.RIGHT);
		
		txt_Adresse = new JTextField();
		txt_Adresse.setHorizontalAlignment(SwingConstants.LEFT);
		txt_Adresse.setText("mumble.piratenpartei-nrw.de");
		txt_Adresse.setColumns(15);
		
		lbl_Port = new JLabel("Port:");
		lbl_Port.setHorizontalAlignment(SwingConstants.RIGHT);
		
		txt_Port = new JTextField();
		txt_Port.setText("64738");
		txt_Port.setColumns(10);
		
		lbl_login = new JLabel("Benutzername:");
		lbl_login.setHorizontalAlignment(SwingConstants.RIGHT);
		
		txt_login = new JTextField();
		txt_login.setText("MumbleModerator");
		txt_login.setColumns(10);
		
		lbl_follows = new JLabel("Follows:");
		lbl_follows.setEnabled(true);
		lbl_follows.setHorizontalAlignment(SwingConstants.RIGHT);
		
		txt_follows = new JTextField();
		txt_follows.setEnabled(true);
		txt_follows.setColumns(10);
		
		JButton btn_OK = new JButton("OK");
		btn_OK.setAction(action_ok);

		JButton btn_P12 = new JButton("P12");
		btn_P12.setAction(action_p12);
		btn_P12.setEnabled(false);

		JButton btn_Close = new JButton("Schliessen");
		btn_Close.setAction(action_close);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(6)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btn_OK, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(lbl_follows, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lbl_Port, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lbl_login, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
							.addComponent(lbl_Adresse, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(txt_follows, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
								.addComponent(txt_Port, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
								.addComponent(txt_login, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
								.addComponent(txt_Adresse, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(14)
							.addComponent(btn_P12, GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btn_Close)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_Adresse)
						.addComponent(txt_Adresse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_Port)
						.addComponent(txt_Port, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_login)
						.addComponent(txt_login, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_follows)
						.addComponent(txt_follows, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btn_Close)
						.addComponent(btn_OK)
						.addComponent(btn_P12))
					.addGap(89))
		);
		contentPane.setLayout(gl_contentPane);
	}
	private class SwingAction extends AbstractAction {

		private static final long serialVersionUID = 1294984497736209793L;
		public SwingAction() {
			putValue(NAME, "OK");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			
//			VerbindenJFrame2 obj = (VerbindenJFrame2)e.getSource();
			
			
			Controller controller = new Controller(
					   txt_Adresse.getText(),
					   Integer.parseInt(txt_Port.getText()),
					   "zzz_".concat(txt_login.getText()).concat("_zzz"),
					   ""
					   );
	
			UIDataHolder.setController(controller);
			
			controller.setFollows(txt_follows.getText());
			
			setVisible(false);
			dispose();
			
		}
	}

	private class SwingAction_1 extends AbstractAction {

		private static final long serialVersionUID = 6639302207863974478L;
		public SwingAction_1() {
			putValue(NAME, "P12");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
	
	private class SwingAction_2 extends AbstractAction {

		private static final long serialVersionUID = -8746911862645731772L;
		public SwingAction_2() {
			putValue(NAME, "Schliessen");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
//			VerbindenJFrame2 obj = (VerbindenJFrame2)(e.getSource());
			setVisible(false);
			dispose();

		}
	}
}
