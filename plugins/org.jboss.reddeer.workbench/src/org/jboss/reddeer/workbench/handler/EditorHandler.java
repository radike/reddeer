package org.jboss.reddeer.workbench.handler;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.jboss.reddeer.swt.wait.WaitWhile;

/**
 * Editor handler handles operations for Editor instances
 * @author rawagner
 *
 */
public class EditorHandler {
	
	protected final Logger log = Logger.getLogger(this.getClass());
	
	private static EditorHandler instance;
	
	private EditorHandler(){
		
	}
	
	public static EditorHandler getInstance(){
		if(instance == null){
			instance = new EditorHandler();
		}
		return instance;
	}
	
	public void save(final IEditorPart editor) {
		activate(editor);
		log.info("Saving editor");
		Display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				editor.doSave(new NullProgressMonitor());
			}
		});
	}

	public boolean isActive(final IEditorPart editor) {
		return Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				return (editor == PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.getActivePart());
			}
		});
	}
	
	public void activate(final IEditorPart editor) {
		if (!isActive(editor)) {
			log.info("Activating editor " + WorkbenchPartHandler.getInstance().getTitle(editor));
			Display.syncExec(new Runnable() {

				@Override
				public void run() {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getActivePage().activate(editor);
					editor.setFocus();
				}
			});
		}
	}
	
	public boolean isDirty(final IEditorPart editor){
		return Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				return editor.isDirty();
			}
		});
	}
	
	public void close(final boolean save, final IEditorPart editor) {

		log.info("Closing editor " + WorkbenchPartHandler.getInstance().getTitle(editor));
		if (isDirty(editor) && save){
			Display.asyncExec(new Runnable() {
				
				@Override
				public void run() {
					IWorkbenchWindow activeWorkbenchWindow = PlatformUI
							.getWorkbench().getActiveWorkbenchWindow();
					activeWorkbenchWindow.getActivePage().closeEditor(
							editor, save);
				}
			});
			new DefaultShell("Save Resource");
			new PushButton("Yes").click();
			new WaitWhile(new ShellWithTextIsActive("Save Resource"));
		}else{
			Display.syncExec(new Runnable() {
	
				@Override
				public void run() {
					IWorkbenchWindow activeWorkbenchWindow = PlatformUI
							.getWorkbench().getActiveWorkbenchWindow();
					activeWorkbenchWindow.getActivePage().closeEditor(
							editor, save);
				}
			});
		}
		log.info("Editor " + WorkbenchPartHandler.getInstance().getTitle(editor) + " is closed");
		
	}

}
