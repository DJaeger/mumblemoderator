package mumblemoderator.sample;

import java.util.logging.Level;
import mumblemoderator.util.Util;

/**
 *
 * @author frank
 */
public class Handler {

	
	
	public Handler() {
	}

	public final boolean post(Runnable runnable) {
//		throw new UnsupportedOperationException("Not yet implemented");
		Util.getLogger(Handler.class).log(Level.INFO, "Not yet implemented");
		return true;

	}

}
