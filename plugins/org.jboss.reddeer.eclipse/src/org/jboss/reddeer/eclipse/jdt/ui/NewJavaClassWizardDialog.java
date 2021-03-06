package org.jboss.reddeer.eclipse.jdt.ui;

import org.jboss.reddeer.eclipse.jface.wizard.NewWizardDialog;

/**
 * Wizard dialog for creating a java class.
 */
public class NewJavaClassWizardDialog extends NewWizardDialog {
	
	/**
	 * Constructs the wizard with Java > Class.
	 */
	public NewJavaClassWizardDialog() {
		super("Java", "Class");
	}
	
	@Override
	public NewJavaClassWizardPage getFirstPage() {
		return new NewJavaClassWizardPage();
	}
}
