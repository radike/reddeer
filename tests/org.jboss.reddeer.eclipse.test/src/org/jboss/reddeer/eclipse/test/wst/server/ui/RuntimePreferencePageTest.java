package org.jboss.reddeer.eclipse.test.wst.server.ui;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.jboss.reddeer.eclipse.wst.server.ui.Runtime;
import org.jboss.reddeer.eclipse.wst.server.ui.RuntimePreferencePage;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewRuntimeWizardDialog;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewRuntimeWizardPage;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class RuntimePreferencePageTest {

	private static final String SERVER_NAME = TestServerRuntime.NAME;
	
	private static final String SERVER_PATH = "Basic";

	private RuntimePreferencePage preferencePage;

	@Before
	public void setUp(){
		preferencePage = new RuntimePreferencePage();
		preferencePage.open();
		preferencePage.removeAllRuntimes();
		preferencePage.ok();
	}

	@Test
	public void open() throws InterruptedException{
		preferencePage.open();
		assertThat(preferencePage.getName(), is("Server " + RuntimePreferencePage.PAGE_NAME));
	}

	@Test
	public void addRuntime() {
		preferencePage.open();
		
		NewRuntimeWizardDialog wizardDialog = preferencePage.addRuntime(); 
		NewRuntimeWizardPage wizardPage = wizardDialog.getFirstPage();
		wizardPage.selectType(SERVER_PATH, SERVER_NAME);
		wizardDialog.finish();
		
		List<Runtime> runtimes = preferencePage.getServerRuntimes();
		assertThat(runtimes.size(), is(1));
		assertThat(runtimes.get(0), is(new Runtime(SERVER_NAME, SERVER_NAME)));
	}

	@Test
	public void removeRuntime() {
		preferencePage.open();
		NewRuntimeWizardDialog wizardDialog = preferencePage.addRuntime(); 
		NewRuntimeWizardPage wizardPage = wizardDialog.getFirstPage();
		wizardPage.selectType(SERVER_PATH, SERVER_NAME);
		wizardDialog.finish();
		
		wizardPage = preferencePage.addRuntime().getFirstPage();
		wizardPage.selectType(SERVER_PATH, SERVER_NAME);
		wizardDialog.finish();
		
		List<Runtime> runtimes = preferencePage.getServerRuntimes();
		assertThat(runtimes.size(), is(2));
		
		preferencePage.removeRuntime(new Runtime(SERVER_NAME + " (2)", null));
		runtimes = preferencePage.getServerRuntimes();
		assertThat(runtimes.size(), is(1));
		
		preferencePage.removeRuntime(new Runtime(SERVER_NAME, null));
		runtimes = preferencePage.getServerRuntimes();
		assertThat(runtimes.size(), is(0));
	}
	
	@Test
	public void removeAllRuntime() {
		preferencePage.open();
		
		NewRuntimeWizardDialog wizardDialog = preferencePage.addRuntime(); 
		NewRuntimeWizardPage wizardPage = wizardDialog.getFirstPage();
		wizardPage.selectType(SERVER_PATH, SERVER_NAME);
		wizardDialog.finish();
		
		wizardPage = preferencePage.addRuntime().getFirstPage();
		wizardPage.selectType(SERVER_PATH, SERVER_NAME);
		wizardDialog.finish();
		
		List<Runtime> runtimes = preferencePage.getServerRuntimes();
		assertThat(runtimes.size(), is(2));

		preferencePage.removeAllRuntimes();
		runtimes = preferencePage.getServerRuntimes();
		assertThat(runtimes.size(), is(0));
	}
	
	@After
	public void tearDown(){
		preferencePage.open();
		preferencePage.removeAllRuntimes();
		preferencePage.cancel();
	}
}
