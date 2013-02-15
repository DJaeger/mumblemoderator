package mumblemoderator;

import java.util.logging.*;
import mumblemoderator.service.Controller;
import mumblemoderator.ui.MainJFrame2;
import mumblemoderator.util.Util;

/**
 *
 * @author frank
 */
public class MainClass {

	Controller controller;

	public MainClass() {
		this(null);

	}

	public MainClass(String[] args) {
		
		Controller controller = new Controller(
				   "mumble.piratenpartei-nrw.de",
				   64738,
				   "z_".concat("Pacman_baut").concat("_z"),
				   ""
				   );
		
		controller.setFollows("PacBot");
		
		Util.getLogger(MainClass.class).log(Level.INFO, "MumbleModerator Started");
//		controller = new Controller();
		
		MainJFrame2 mainjFrame = new MainJFrame2(controller);
		mainjFrame.setVisible(true);
		

	}

	public static void main(String[] args) throws Exception {
		Object tmp = new MainClass();
		tmp.toString();

    }

}
