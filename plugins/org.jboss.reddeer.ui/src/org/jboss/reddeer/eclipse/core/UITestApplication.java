package org.jboss.reddeer.eclipse.core;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Platform;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.testing.ITestHarness;
import org.eclipse.ui.testing.TestableObject;

/**
 * Just a copy of
 * {@link org.eclipse.pde.internal.junit.runtime.UITestApplication} with
 * {@link #runTests()} overridden so that the tests run in a non-UI thread.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class UITestApplication implements IApplication, ITestHarness {

	private static final String DEFAULT_APP_3_0 = "org.eclipse.ui.ide.workbench"; //$NON-NLS-1$

	private TestableObject fTestableObject;
	private IApplication fApplication;

	/**
	 * 
	 * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.
	 * IApplicationContext)
	 */
	public Object start(IApplicationContext context) throws Exception {
		String[] args = (String[]) context.getArguments().get(
				IApplicationContext.APPLICATION_ARGS);
		Object app = getApplication(args);

		Assert.isNotNull(app, "The application " + getApplicationToRun(args)
				+ " could not be found.");

		fTestableObject = PlatformUI.getTestableObject();
		fTestableObject.setTestHarness(this);
		if (app instanceof IApplication) {
			fApplication = (IApplication) app;
			return fApplication.start(context);
		}
		throw new IllegalArgumentException("Could not execute application "
				+ getApplicationToRun(args));
	}

	/**
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	public void stop() {
		if (fApplication != null)
			fApplication.stop();
	}

	/*
	 * return the application to run, or null if not even the default
	 * application is found.
	 */
	private Object getApplication(String[] args) throws CoreException {
		// Find the name of the application as specified by the PDE JUnit
		// launcher.
		// If no application is specified, the 3.0 default workbench application
		// is returned.
		String applicationToRun = getApplicationToRun(args);

		IExtension extension = Platform.getExtensionRegistry()
				.getExtension(Platform.PI_RUNTIME, Platform.PT_APPLICATIONS,
						applicationToRun);

		Assert.isNotNull(extension,
				"Could not find IExtension for application: "
						+ applicationToRun);

		// If the extension does not have the correct grammar, return null.
		// Otherwise, return the application object.
		IConfigurationElement[] elements = extension.getConfigurationElements();
		if (elements.length > 0) {
			IConfigurationElement[] runs = elements[0].getChildren("run"); //$NON-NLS-1$
			if (runs.length > 0) {
				Object runnable = runs[0].createExecutableExtension("class"); //$NON-NLS-1$
				if (runnable instanceof IApplication)
					return runnable;
			}
		}
		return null;
	}

	/*
	 * The -testApplication argument specifies the application to be run. If the
	 * PDE JUnit launcher did not set this argument, then return the name of the
	 * default application. In 3.0, the default is the
	 * "org.eclipse.ui.ide.worbench" application.
	 */
	private String getApplicationToRun(String[] args) {
		IProduct product = Platform.getProduct();
		if (product != null)
			return product.getApplication();
		for (int i = 0; i < args.length; i++)
			if (args[i].equals("-testApplication") && (i < args.length - 1)) //$NON-NLS-1$
				return args[i + 1];
		return DEFAULT_APP_3_0;
	}

	/**
	 * @see org.eclipse.ui.testing.ITestHarness#runTests()
	 */
	public void runTests() {
		fTestableObject.testingStarting();
		RemotePluginTestRunner.main(Platform.getCommandLineArgs());
		fTestableObject.testingFinished();
	}
}
