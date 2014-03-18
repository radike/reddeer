package org.jboss.reddeer.junit.internal.runner;

import org.junit.runner.Runner;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

/**
 * Suite containing tests without a run.<br/>
 * <br/>
 * 
 * It doesn't execute tests. The suite only informs that there are tests, whose
 * requirements were not fulfilled for any test configuration
 * so the tests were not executed at all.<br/>
 * 
 * @author Radoslav Rabara
 *
 */
public class TestsWithoutRunSuite extends Suite {

	/**
	 * Constructs {@link Suite} informing about tests, which were not
	 * executed even once.
	 * 
	 * @param clazz test class or test suite containing test classes
	 * @param testsRunManager {@link TestsWithRunManager} used to determine
	 * 						if the test classes were executed or not
	 * @throws InitializationError is thrown when the initialization fails
	 */
	public TestsWithoutRunSuite(Class<?> clazz, TestsWithRunManager testsRunManager)
			throws InitializationError {
		super(clazz, new TestsWithoutRunRunnerBuilder(testsRunManager));
	}

	@Override
	public String getName() {
		return "NOT EXECUTED TESTS";
	}

	@Override
	public String toString() {
		return "Suite '" + this.getClass().getName() + "' "
				+ "showing tests without a single run";
	}

	private static class TestsWithoutRunRunnerBuilder extends RunnerBuilder {

		private TestsWithRunManager testsRunManager;

		public TestsWithoutRunRunnerBuilder(TestsWithRunManager testsRunManage) {
			this.testsRunManager = testsRunManage;
		}

		@Override
		public Runner runnerForClass(Class<?> clazz) throws Throwable {
			if (testsRunManager.hasRun(clazz)) {
				return null;
			}
			return new TestWithoutRunRunner(clazz);
		}

	}
}
