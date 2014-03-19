package org.jboss.reddeer.swt.impl.button;

import org.eclipse.swt.SWT;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.handler.ButtonHandler;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
/**
 * Class represents Button with type Toggle (Checkbox)
 * 
 * @author rhopp
 * 
 */

public class CheckBox extends AbstractButton {

	protected final Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * Default constructor
	 */
	public CheckBox() {
		this(0);
	}
	
	/**
	 * Checkbox inside given referencedComposite
	 * @param referencedComposite
	 */
	public CheckBox(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}
	
	/**
	 * Checkbox with given index
	 * 
	 * @param index
	 */
	public CheckBox(int index){
		this(null, index,"");
	}
	
	/**
	 * Checkbox with given index inside given referencedComposite
	 * @param referencedComposite
	 * @param index
	 */
	public CheckBox(ReferencedComposite referencedComposite, int index){
		this(referencedComposite, index,"");
	}
	
	/**
	 * Checkbox with given text
	 * 
	 * @param text
	 */
	public CheckBox(String text) {
		this(null, 0,text);
	}
	
	/**
	 * Checkbox with given text inside given referencedComposite
	 * @param referencedComposite
	 * @param text
	 */
	public CheckBox(ReferencedComposite referencedComposite,String text) {
		this(referencedComposite, 0,text);
	}
	
	/**
	 * Check Box with given index and text
	 * @param index
	 * @param text
	 */
	public CheckBox (int index , String text){
		super(null, index,text,SWT.CHECK);
	}
	
	/**
	 * Check Box with given index and text inside given referencedComposite
	 * @param index
	 * @param text
	 * @param referencedComposite
	 */
	public CheckBox (ReferencedComposite referencedComposite, int index , String text){
		super(referencedComposite, index,text,SWT.CHECK);
	}
	
	/**
	 * Returns true when Check Box is checked
	 * @return
	 */
	public boolean isChecked() {
		return ButtonHandler.getInstance().isSelected(swtButton);
	}
	
	/**
	 * Sets checkbox to state 'checked'
	 * 
	 * @param checked
	 */
	public void toggle(boolean checked){
		if (checked){
			if (isChecked()) {
				log.debug("Checkbox already checked");
				return;
			}else{
				log.info("Checking checkbox " + getText());
				click();
			}
		}else{
			if (isChecked()) {
				log.info("Unchecking checkbox " + getText());
				click();
			}else{
				log.debug("Checkbox already unchecked");
				return;
			}
		}
	}
}
