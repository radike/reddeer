package org.jboss.reddeer.swt.lookup;

import org.eclipse.swt.custom.StyledText;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.lookup.WidgetLookup;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

/**
 * Text lookup containing lookup routines for StyledText widget type
 * 
 * @author rhopp
 *
 */
public class StyledTextLookup {
	
	private static StyledTextLookup instance = null;
	
	private StyledTextLookup() {
	}
	
	/**
	 * Creates and returns instance of StyledTextLookup
	 * @return TextLookup instance
	 */
	public static StyledTextLookup getInstance() {
		if (instance == null) instance = new StyledTextLookup();
		return instance;
	}

	/**
	 * Return StyledText instance
	 * @param matcher
	 * @return StyledText Widget matching criteria
	 */
	@SuppressWarnings({ "rawtypes" })
	public StyledText getStyledText(ReferencedComposite refComposite, int index, Matcher... matchers) {
		return (StyledText)WidgetLookup.getInstance().activeWidget(refComposite, StyledText.class, index, matchers);
	}
	
}
