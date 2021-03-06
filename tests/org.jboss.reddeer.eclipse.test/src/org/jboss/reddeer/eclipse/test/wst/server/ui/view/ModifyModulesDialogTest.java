package org.jboss.reddeer.eclipse.test.wst.server.ui.view;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.Project;
import org.jboss.reddeer.eclipse.wst.server.ui.view.Server;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServerModule;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.ModifyModulesDialog;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.ModifyModulesPage;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
/**
 * 
 * @author Lucia Jelinkova
 *
 */
public class ModifyModulesDialogTest extends ServersViewTestCase{

	private static final String SERVER = "Server ABC";
	
	private Server server;

	@BeforeClass
	public static void createProjects(){
		importProjects();
	}
	
	@AfterClass
	public static void removeProjects(){
		PackageExplorer explorer = new PackageExplorer();
		for (Project project : explorer.getProjects()){
			project.delete(true);
		}
	}
	
	@Before
	public void setUp(){
		createServer(SERVER);

		server = serversView.getServer(SERVER);
	}

	@Test
	public void addAll_removeTwo(){
		ModifyModulesDialog dialog = server.addAndRemoveModules();
		ModifyModulesPage page = dialog.getFirstPage();
		page.addAll();
		dialog.finish();

		List<ServerModule> modules = server.getModules();
		assertThat(modules.size(), is(3));
		assertThat(modules.get(0).getLabel().getName(), is(PROJECT_1));
		assertThat(modules.get(1).getLabel().getName(), is(PROJECT_2));
		assertThat(modules.get(2).getLabel().getName(), is(PROJECT_3));
		
		server.addAndRemoveModules();
		page = dialog.getFirstPage();
		page.remove(PROJECT_1, PROJECT_3);
		dialog.finish();
		
		modules = server.getModules();
		assertThat(modules.size(), is(1));
		assertThat(modules.get(0).getLabel().getName(), is(PROJECT_2));
	}
	
	@Test
	public void addTwo_removeAll(){
		ModifyModulesDialog dialog = server.addAndRemoveModules();
		ModifyModulesPage page = dialog.getFirstPage();
		page.add(PROJECT_1, PROJECT_3);
		dialog.finish();

		List<ServerModule> modules = server.getModules();
		assertThat(modules.size(), is(2));
		assertThat(modules.get(0).getLabel().getName(), is(PROJECT_1));
		assertThat(modules.get(1).getLabel().getName(), is(PROJECT_3));
		
		server.addAndRemoveModules();
		page = dialog.getFirstPage();
		page.removeAll();
		dialog.finish();
		
		modules = server.getModules();
		assertThat(modules.size(), is(0));
	}
}
