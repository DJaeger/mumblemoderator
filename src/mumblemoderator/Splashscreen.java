package mumblemoderator;

import java.awt.*;
import java.net.URL;
import java.awt.image.ImageObserver;

final class SplashScreen extends Frame {

	private static final long serialVersionUID = -8625802115654071272L;

	SplashScreen(String aImageId) {

		if (aImageId == null || aImageId.trim().length() == 0) {
			throw new IllegalArgumentException("Image Id does not have content.");
		}
		fImageId = aImageId;
	}

	void splash() {
		initImageAndTracker();
		setSize(fImage.getWidth(NO_OBSERVER), fImage.getHeight(NO_OBSERVER));
		center();

		fMediaTracker.addImage(fImage, IMAGE_ID);
		try {
			fMediaTracker.waitForID(IMAGE_ID);
		} catch (InterruptedException ex) {
			System.out.println("Cannot track image load.");
		}

		SplashWindow splashWindow = new SplashWindow(this, fImage);
		splashWindow.repaint();
	}
	// PRIVATE//
	private final String fImageId;
	private MediaTracker fMediaTracker;
	private Image fImage;
	private static final ImageObserver NO_OBSERVER = null;
	private static final int IMAGE_ID = 0;

	private void initImageAndTracker() {
		fMediaTracker = new MediaTracker(this);
		URL imageURL = SplashScreen.class.getResource(fImageId);
		fImage = Toolkit.getDefaultToolkit().getImage(imageURL);
	}

	private void center() {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle frame = getBounds();
		setLocation((screen.width - frame.width) / 2, (screen.height - frame.height) / 2);
	}

	private class SplashWindow extends Window {

		/**
		 * 
		 */
		private static final long serialVersionUID = -4112141574112565482L;
		
		SplashWindow(Frame aParent, Image aImage) {
			super(aParent);
			fImage = aImage;
			setSize(fImage.getWidth(NO_OBSERVER), fImage.getHeight(NO_OBSERVER));
			Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
			Rectangle window = getBounds();
			setLocation((screen.width - window.width) / 2, (screen.height - window.height) / 2);
			setVisible(true);
		}

		@Override
		public void paint(Graphics graphics) {
			if (fImage != null) {
				graphics.drawImage(fImage, 0, 0, this);
			}
		}
		private Image fImage;
	}

	public static void main(String... aArgs) {
		SplashScreen splashScreen = new SplashScreen("splashscreen.gif");
		splashScreen.splash();
		try {
			Thread.sleep(6000);
		} catch (InterruptedException ex) {
			System.out.println(ex);
		}
		System.exit(0);
	}
}