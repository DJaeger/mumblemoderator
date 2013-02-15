package mumblemoderator.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JSplitPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTabbedPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import mumblemoderator.service.Controller;

import java.awt.Color;
import javax.swing.ScrollPaneConstants;

import java.awt.FlowLayout;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JCheckBoxMenuItem;

public class MainJFrame2 extends JFrame {

	private static final long serialVersionUID = -66694897349620605L;
	private JPanel contentPane;
	private final Action action_serververbinden = new SwingAction();
	private final Action action_changecomment = new SwingAction_1();
	private final Action action = new SwingAction_2();
	private final Action action_trennen = new SwingAction_3();
	private JSplitPane splitPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainJFrame2 frame = new MainJFrame2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public MainJFrame2(Controller controller) {
		this();
		this.setName("Root");
		
		UIDataHolder.setController(controller);

	}
	
	
	/**
	 * Create the frame.
	 */
	public MainJFrame2() {
		setTitle("MumbleModerator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnServer = new JMenu("Server");
		menuBar.add(mnServer);
		
		JMenuItem mntmVerbinden = new JMenuItem("Verbinden");
		mntmVerbinden.setAction(action_serververbinden);
		mnServer.add(mntmVerbinden);
		
		JMenuItem mntmTrennen = new JMenuItem("Trennen");
		mntmTrennen.setEnabled(false);
		mntmTrennen.setAction(action_trennen);
		mnServer.add(mntmTrennen);
		
		JSeparator separator = new JSeparator();
		mnServer.add(separator);
		
		JMenuItem mntmSchliessen = new JMenuItem("Schliessen");
		mntmSchliessen.setAction(action);
		mnServer.add(mntmSchliessen);
		
		JMenu mnSelbst = new JMenu("Selbst");
		menuBar.add(mnSelbst);
		
		JCheckBoxMenuItem chckbxmntmNewCheckItem_1 = new JCheckBoxMenuItem("Selbst Stumm stellen");
		chckbxmntmNewCheckItem_1.setSelected(true);
		chckbxmntmNewCheckItem_1.setEnabled(false);
		
		chckbxmntmNewCheckItem_1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Controller controller = UIDataHolder.getController();
				controller.setMute(((JCheckBoxMenuItem)(e.getSource())).isSelected());
			}
		});
				
		mnSelbst.add(chckbxmntmNewCheckItem_1);
		
		JCheckBoxMenuItem chckbxmntmNewCheckItem = new JCheckBoxMenuItem("Selbst Taub stellen");
		chckbxmntmNewCheckItem.setSelected(false);
		chckbxmntmNewCheckItem.setEnabled(false);
		chckbxmntmNewCheckItem.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Controller controller = UIDataHolder.getController();
				controller.setDeaf(((JCheckBoxMenuItem)(e.getSource())).isSelected());
			}
		});

		mnSelbst.add(chckbxmntmNewCheckItem);
		
		JSeparator separator_1 = new JSeparator();
		mnSelbst.add(separator_1);
		
		JMenuItem mntmKommentarndern = new JMenuItem("Kommentar \u00E4ndern");
		mntmKommentarndern.setAction(action_changecomment);
		mnSelbst.add(mntmKommentarndern);
		
		JMenu mnKonfiguration = new JMenu("Konfiguration");
		menuBar.add(mnKonfiguration);
		
		JMenu mnHilfe = new JMenu("Hilfe");
		menuBar.add(mnHilfe);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		splitPane = new JSplitPane();
		splitPane.setDividerSize(5);
		
		JPanel panel = new JPanel();
		
		resetsplitPane();
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPane)
				.addComponent(panel, GroupLayout.PREFERRED_SIZE, 774, GroupLayout.PREFERRED_SIZE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(splitPane)
					.addGap(6)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
		);

		
		contentPane.setLayout(gl_contentPane);
		
		
	}
	
	public void resetsplitPane() {
		DynamicTree dynamicTree = new DynamicTree();
		splitPane.setLeftComponent(dynamicTree);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setRightComponent(tabbedPane);
		
		JPanel panel_timeline = new JPanel();
		panel_timeline.setName("");
		tabbedPane.addTab("TimeLine", null, panel_timeline, null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(Color.WHITE);
		GroupLayout gl_panel_timeline = new GroupLayout(panel_timeline);
		gl_panel_timeline.setHorizontalGroup(
			gl_panel_timeline.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_timeline.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_timeline.setVerticalGroup(
			gl_panel_timeline.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_timeline.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		TimeLineTextField timeLineTextField = new TimeLineTextField();
		scrollPane.setViewportView(timeLineTextField);
		panel_timeline.setLayout(gl_panel_timeline);
		
		JPanel panel_Crontab = new JPCrontab();
		tabbedPane.addTab("Crontab", null, panel_Crontab, null);
		
		JPanel panel_UserTimer = new JPanel();
		tabbedPane.addTab("UserTimer", null, panel_UserTimer, null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		GroupLayout gl_panel_UserTimer = new GroupLayout(panel_UserTimer);
		gl_panel_UserTimer.setHorizontalGroup(
			gl_panel_UserTimer.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_UserTimer.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_UserTimer.setVerticalGroup(
			gl_panel_UserTimer.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_UserTimer.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		ChannelUser channelUser = new ChannelUser();
		scrollPane_2.setViewportView(channelUser);
		panel_UserTimer.setLayout(gl_panel_UserTimer);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		
		JPanel panel_Wortmeldung = new JPWortmeldung();
		tabbedPane.addTab("Wortmeldung", null, panel_Wortmeldung, null);
		

		
	}

	
	private class SwingAction extends AbstractAction {
		private static final long serialVersionUID = -7550113329551976349L;
		public SwingAction() {
			putValue(NAME, "Verbinden");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			new VerbindenJFrame2().setVisible(true);
			
		}
	}

	private class SwingAction_1 extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = -1003312056513642508L;
		public SwingAction_1() {
			putValue(NAME, "Kommentar ‰ndern");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			new ChangeComment().setVisible(true);
		}
	}

	private class SwingAction_2 extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = -5016821190647503499L;
		public SwingAction_2() {
			putValue(NAME, "Schlieﬂen");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			Controller controller = UIDataHolder.getController();
			if (controller != null) {
				controller.disconnect();
				resetsplitPane();
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.exit(0);
			
		}
	}

	private class SwingAction_3 extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = -3825311473158105971L;

		public SwingAction_3() {
			putValue(NAME, "Trennen");
			putValue(SHORT_DESCRIPTION, "Some short description");

		}

		public void actionPerformed(ActionEvent e) {
			Controller controller = UIDataHolder.getController();
			controller.disconnect();
			resetsplitPane();
			
		}

	}

	
}
