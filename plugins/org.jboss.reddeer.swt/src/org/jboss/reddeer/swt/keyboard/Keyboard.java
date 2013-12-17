package org.jboss.reddeer.swt.keyboard;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.lookup.WidgetLookup;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.OS;
import org.jboss.reddeer.swt.util.Utils;

/**
 * Class for operating with keyboard
 * 
 * @author rhopp
 * 
 */

public class Keyboard {
	
	protected static final Logger log = Logger.getLogger(Keyboard.class);
	
	private static final int DELAY = 150;
	
	/**
	 * Invokes given key combination. Accepts chars or {@link org.eclipse.swt.SWT} constants. For example: invokeKeyCombination(SWT.CONTROL, SWT.SHIFT, 't');
	 * 
	 * @param keys either chars or values from SWT.KeyCombination
	 * @see org.eclipse.swt.SWT
	 */
	
	public static void invokeKeyCombination(int... keys){
		log.debug("Invoking keys: ");
		for (int i=0; i<keys.length; i++){
			delay(DELAY);
			log.debug("    As char:"+(char)keys[i]+", as int:"+keys[i]);
			Display.getDisplay().post(keyEvent(keys[i], SWT.KeyDown));
		}
		for (int i=keys.length-1; i>=0; i--){
			delay(DELAY);
			Display.getDisplay().post(keyEvent(keys[i], SWT.KeyUp));
		}
	}
	
	/**
	 * Types given text
	 * 
	 * @param text
	 */
	
	public static void type(String text){
		log.debug("Typing text \""+text+"\"");
		for (char c : text.toCharArray()) {
			invokeKeyCombination(DefaultKeyboardLayout.getInstance().getKeyCombination(c));
		}
	}
	
	/**
	 *  Types given character
	 * @param c
	 */
	
	public static void type(int c){
		press(c);
		release(c);
	}
	
	/**
	 * Selects `shift` characters to the side of cursor specified by `toLeft`
	 * 
	 * @param shift
	 * @param toLeft
	 */
	
	public static void select(int shift, boolean toLeft){
		if (toLeft){
			log.debug("Selecting "+shift+" characters to the left");
		}else{
			log.debug("Selecting "+shift+" characters to the right");
		}
		press(SWT.SHIFT);
		moveCursor(shift, toLeft);
		release(SWT.SHIFT);
	}
	
	/**
	 * Moves cursor by `shift` characters to the side of cursor specified by `toLeft`
	 * @param shift
	 * @param toLeft
	 */
	
	public static void moveCursor(int shift, boolean toLeft){
		for (int i=0; i<shift; i++){
			if (toLeft){
				type(SWT.ARROW_LEFT);
			}else{
				type(SWT.ARROW_RIGHT);
			}
			delay(DELAY);
		}
	}
	
	/**
	 * Either cuts or copies selected text to clipboard
	 * 
	 * @param cut cuts the text if true, copies otherwise
	 */
	
	public static void writeToClipboard(boolean cut){
		log.debug("Writing to clipboard");
		if (Utils.isRunningOS(OS.MACOSX)){
			press(SWT.COMMAND);
		}else{
			press(SWT.CONTROL);
		}
		if (cut){
			type('x');
		}else{
			type('c');
		}
		if (Utils.isRunningOS(OS.MACOSX)){
			release(SWT.COMMAND);
		}else{
			release(SWT.CONTROL);
		}
	}
	
	/**
	 * Pastes text stored in clipboard
	 */
	
	public static void pasteFromClipboard(){
		log.debug("Pasting from clipboard");
		if (Utils.isRunningOS(OS.MACOSX)){
			press(SWT.COMMAND);
			type('v');
			release(SWT.COMMAND);
		}else{
			press(SWT.CONTROL);
			type('v');
			release(SWT.CONTROL);
		}
	}
	
	private static void press(final int key){
		Display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				Display.getDisplay().post(keyEvent(key, SWT.KeyDown));
				delay(DELAY);;
			}
		});
	}
	
	private static void release(final int key){
		Display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				Display.getDisplay().post(keyEvent(key, SWT.KeyUp));
				delay(DELAY);;
			}
		});
	}
	
	private static Event keyEvent(int key, int eventType){
		Event e = new Event();
		e.keyCode = key;
		e.character = (char) key;
		e.type = eventType;
		e.widget = WidgetLookup.getInstance().getFocusControl();
		return e;
	}
	
	private static void delay(int delay){
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
