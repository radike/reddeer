package org.jboss.reddeer.swt.impl.spinner;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Spinner;
import org.jboss.reddeer.swt.handler.SpinnerHandler;
import org.jboss.reddeer.swt.handler.WidgetHandler;

/**
 * Abstract class for all Spinner implementations
 * 
 * @author Andrej Podhradsky
 * 
 */
public abstract class AbstractSpinner implements Spinner {

	private static final Logger log = Logger.getLogger(AbstractSpinner.class);
	
	protected org.eclipse.swt.widgets.Spinner swtSpinner;

	@Override
	public int getValue() {
		return SpinnerHandler.getInstance().getValue(swtSpinner);
	}

	@Override
	public void setValue(int value) {
		log.info("Set spinner value to " + value);
		SpinnerHandler.getInstance().setValue(swtSpinner, value);
	}
	
	public org.eclipse.swt.widgets.Spinner getSWTWidget(){
		return swtSpinner;
	}
	
	@Override
	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(swtSpinner);
	}

}
