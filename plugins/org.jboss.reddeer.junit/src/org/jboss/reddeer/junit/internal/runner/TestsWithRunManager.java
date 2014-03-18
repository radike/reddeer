package org.jboss.reddeer.junit.internal.runner;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Brings state for test classes, which divides them into two categories:
 * <ul>
 * <li>Test classes with a run</li>
 * <li>Test classes without a run</li>
 * </ul>
 * 
 * Usage:<br/>
 * Add test class to manager using method {@link #addTest(Class)}.
 * It will be added as test without a run.<br/><br/>
 * 
 * If the test class has a run, change the state of the test class
 * using method {@link #addTestWithRun(Class)}.
 * 
 * @author Radoslav Rabara
 *
 */
public class TestsWithRunManager {
		
	private Set<Class<?>> allTests = new TreeSet<Class<?>>(classNameComparator);
	private Set<Class<?>> testsWithRun = new TreeSet<Class<?>>(classNameComparator);
	
	private static Comparator<Class<?>> classNameComparator = new Comparator<Class<?>>(){
		@Override
		public int compare(Class<?> clazz0, Class<?> clazz1) {
			return clazz0.getName().compareTo(clazz1.getName());
		}
	};
	
	/**
	 * Adds the specified <var>testClass</var> to the manager
	 * as test class WITHOUT A RUN
	 * 
	 * @param testClass test {@link Class} to be added
	 * 				as test class without a run
	 */
	public void addTest(Class<?> testClass) {
		allTests.add(testClass);
	}
	
	/**
	 * Adds the specified <var>testClass</var> to the manager
	 * as test class WITH A RUN
	 * 
	 * @param testClass test {@link Class} to be added
	 * 				as test class with a run
	 */
	public void addTestWithRun(Class<?> testClass) {
		addTest(testClass);
		testsWithRun.add(testClass);
	}
	
	/**
	 * Calculates the number of tests without a run
	 * 
	 * @return the number of tests without a run
	 */
	public int testsWithoutRunCount() {
		return allTests.size() - testsWithRun.size();
	}
	
	/**
	 * Returns <code>true</code> if the specified test {@link Class}
	 * has a run
	 * 
	 * @param testClass test {@link Class} whose status is to be tested 
	 * @return <code>true</code> if the specified test class has a run
	 */
	public boolean hasRun(Class<?> testClass) {
		return testsWithRun.contains(testClass); 
	}
}
