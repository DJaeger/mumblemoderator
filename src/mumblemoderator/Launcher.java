package mumblemoderator;

import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import mumblemoderator.ui.MainJFrame2;
import mumblemoderator.util.Consts;
import mumblemoderator.util.Util;

public final class Launcher {
	private static MainJFrame2 mainClass;

	public static void main (String... aArgs) {
		logBasicSystemInfo();
		showSplashScreen();

		showMainWindow(aArgs);

		mainClass = new MainJFrame2();

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		mainClass.setVisible(true);

		
		EventQueue.invokeLater( new SplashScreenCloser() );
		fLogger.info("Launch thread now exiting...");

	}

	private static SplashScreen fSplashScreen;
	private static final Logger fLogger = Util.getLogger(Launcher.class);
	private static final String SPLASH_IMAGE = "splashscreen.gif";

	private static void showSplashScreen(){
		fLogger.info("Showing the splash screen.");
		fSplashScreen = new SplashScreen(SPLASH_IMAGE);
		fSplashScreen.splash();

	}

	private static void showMainWindow(String... aArgs){
		fLogger.info("Showing the main window.");

	}

	private static final class SplashScreenCloser implements Runnable {
		@Override
		public void run(){
			fLogger.fine("Closing the splash screen.'");
			fSplashScreen.dispose();

		}

	}
  
	private static void logBasicSystemInfo() {
		fLogger.info("Launching the application...");
		fLogger.log(Level.CONFIG, "Operating System: {0} {1}", new Object[]{System.getProperty("os.name"), System.getProperty("os.version")});
		fLogger.log(Level.CONFIG, "JRE: {0}", System.getProperty("java.version"));
		fLogger.info("Java Launched From: " + System.getProperty("java.home"));
		fLogger.config("Class Path: " + System.getProperty("java.class.path"));
		fLogger.config("Library Path: " + System.getProperty("java.library.path"));
		fLogger.config("Application Name: " + Consts.APP_NAME + "/" + Consts.APP_VERSION);
		//    fLogger.config("User Home Directory: " + System.getProperty("user.home"));
		//    fLogger.config("User Working Directory: " + System.getProperty("user.dir"));
		fLogger.info("Test INFO logging.");
		fLogger.fine("Test FINE logging.");
		fLogger.finest("Test FINEST logging.");

	}

}
