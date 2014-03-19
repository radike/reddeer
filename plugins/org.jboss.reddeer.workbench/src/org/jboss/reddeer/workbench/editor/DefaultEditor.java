package org.jboss.reddeer.workbench.editor;

import org.hamcrest.Matcher;
import org.jboss.reddeer.workbench.editor.Editor;
import org.jboss.reddeer.workbench.impl.editor.AbstractEditor;

/**
 * Represents general editor with basic operations implemented.
 * 
 * @author rhopp
 * @author rawagner
 * @deprecated use org.jboss.reddeer.workbench.impl.editor.DefaultEditor
 */
public class DefaultEditor extends AbstractEditor implements Editor {

	/**
	 * Initialize currently active editor
	 */
	public DefaultEditor() {
		super();
	}

	/**
	 * Initialize editor with given title and activate it.
	 * 
	 * @param title
	 *            Title of editor to initialize and activate
	 */
	public DefaultEditor(final String title) {
		super(title);
	}

	/**
	 * Initialize editor with given title matcher.
	 * 
	 * @param titleMatcher
	 *            Title matcher of editor to initialize and activate
	 */
	public DefaultEditor(final Matcher<String> titleMatcher) {
		super(titleMatcher);
	}

}
