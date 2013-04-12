package mumblemoderator.service;

import java.util.logging.Logger;
import mumblemoderator.Globals;

public abstract class AbstractHost {

	abstract class ProtocolMessage implements Runnable {

		@Override
		public final void run() {
			if (isDisabled()) {
				Logger.getLogger(AbstractHost.class.getName()).log(java.util.logging.Level.INFO, Globals.LOG_TAG + "Ignoring message, Service is disconnected");

			}

			process();

			for (final IServiceObserver observer : getObservers()) {
				try {
					broadcast(observer);
	
				} catch (final Exception e) {
					Logger.getLogger(AbstractHost.class.getName()).log(java.util.logging.Level.INFO, Globals.LOG_TAG + "Error while broadcasting service state", e);

				}

			}

		}

		protected abstract void broadcast(IServiceObserver observer)
				throws Exception;

		protected abstract Iterable<IServiceObserver> getObservers();

		protected abstract void process();

	}

	boolean disabled = false;

	public void disable() {
		this.disabled = true;

	}

	public boolean isDisabled() {
		return disabled;

	}
}
