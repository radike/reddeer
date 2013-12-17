package org.jboss.reddeer.swt.test.keyboard;

import static org.junit.Assert.assertEquals;

import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.jboss.reddeer.swt.condition.ShellIsActive;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.keyboard.Keyboard;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class KeyboardTest extends RedDeerTest {

	private Text text;

	@After
	public void cleanup() {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				for (Shell shell : org.jboss.reddeer.swt.
						util.Display.getDisplay().getShells()) {
					if (shell.getText().equals("Testing shell")) {
						shell.dispose();
						break;
					}
				}
			}
		});
	}
	
	@Test
	public void typingWithShiftTest(){
		openTestingShell();
		Keyboard.type("{@Test}");
		assertEquals("{@Test}", getText());
	}
	
	@Test
	public void typingTest() {
		openTestingShell();
		Keyboard.type("test123");
		assertEquals("test123", getText());
	}
	
	@Test
	public void keyCombinationTest(){
		new DefaultShell();
		Keyboard.invokeKeyCombination(SWT.CONTROL, 'h');
		new DefaultShell().close();
	}
	
	@Test
	public void selectionTest(){
		openTestingShell();
		Keyboard.type("test");
		Keyboard.select(2, true);
		Keyboard.type(SWT.DEL);
		assertEquals("te", getText());
	}
	
	@Test
	public void copyPasteTest(){
		openTestingShell();
		Keyboard.type("test");
		Keyboard.select(2, true);
		Keyboard.writeToClipboard(false);
		Keyboard.moveCursor(5, true);
		Keyboard.pasteFromClipboard();
		assertEquals("sttest", getText());
	}
	
	@Test
	public void cutPasteTest(){
		openTestingShell();
		Keyboard.type("test");
		Keyboard.select(2, true);
		Keyboard.writeToClipboard(true);
		Keyboard.moveCursor(5, true);
		Keyboard.pasteFromClipboard();
		assertEquals("stte", getText());
	}

	private void openTestingShell(){
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				org.eclipse.swt.widgets.Display display = org.eclipse.swt.widgets.Display
						.getDefault();
				Shell shell = new Shell(display);
				shell.setLayout(new GridLayout());
				shell.setText("Testing shell");
				createControls(shell);
				shell.open();
				shell.setFocus();
			}
		});
	}
	
	private void createControls(Shell shell) {
		text = new Text(shell, SWT.NONE);
		text.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				System.out.println("KeyReleased:");
				System.out.println("    Character: "+arg0.character);
				System.out.println("    KeyCode: "+arg0.keyCode);
				System.out.println("    StateMask: "+arg0.stateMask);
				
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				System.out.println("KeyPressed:");
				System.out.println("    Character: "+arg0.character);
				System.out.println("    KeyCode: "+arg0.keyCode);
				System.out.println("    StateMask: "+arg0.stateMask);
			}
		});
		
	}

	private String getText() {
		
		return Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				return text.getText();
			}
		});
	}

}
