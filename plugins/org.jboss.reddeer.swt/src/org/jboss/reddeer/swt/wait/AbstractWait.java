package org.jboss.reddeer.swt.wait;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.exception.WaitTimeoutExpiredException;

/**
 * Common ancestor for waiting classes. Contains abstract
 * {@link #stopWaiting(WaitCondition)} method that is called in the constructor.
 * 
 * @author Vlado Pakan
 * @author Lucia Jelinkova
 * 
 */
public abstract class AbstractWait {
	
	/**
	 * Indicates how often the condition should be evaluated. 
	 */
	protected static TimePeriod WAIT_SLEEP = TimePeriod.SHORT;

	/**
	 * Wait logger.
	 */
	private static final Logger log = Logger.getLogger(AbstractWait.class);

	private TimePeriod timeout;

	private boolean throwTimeoutException = true;

	/**
	 * Waits till condition is met for default time period. Throws 
	 * WaitTimeoutExpiredException after waiting for specified time period
	 * and wait condition is not met.
	 * 
	 * @param condition wait condition to met
	 */
	public AbstractWait(WaitCondition condition) {
		this(condition, TimePeriod.NORMAL);
	}

	/**
	 * Waits till condition is met for specified timeout period. Throws 
	 * WaitTimeoutExpiredException after waiting for specified time period
	 * and wait condition is not met.
	 * 
	 * @param condition wait condition to met
	 * @param timePeriod time period to wait
	 */
	public AbstractWait(WaitCondition condition, TimePeriod timePeriod) {
		this(condition, timePeriod, true);
	}

	/**
	 * Waits till condition is met for specified timeout period. There is a
	 * possibility to turn on/off throwing a exception.
	 * 
	 * @param condition wait condition to met
	 * @param timePeriod time period to wait
	 * @param throwRuntimeException whether exception should be thrown after
	 * expiration of the period
	 * @throws WaitTimeoutExpiredException
	 */
	public AbstractWait(WaitCondition condition, TimePeriod timePeriod,
			boolean throwRuntimeException) {
		this.timeout = timePeriod;
		this.throwTimeoutException = throwRuntimeException;

		wait(condition);
	}

	/**
	 * Condition if the waiting should stop. 
	 * 
	 * @param condition wait condition to met
	 * @return true if the while loop should continue, false otherwise
	 */
	protected abstract boolean stopWaiting(WaitCondition condition);

	/**
	 * Gets description of specific wait condition. Description should
	 * provide information about nature of wait condition followed by space
	 * character. Purpose of this method is logging.
	 * 
	 * @return description of specific wait condition
	 */
	protected abstract String description();

	
	private void wait(WaitCondition condition) {
		log.debug(this.description() + condition.description() + "...");
	
		long limit = System.currentTimeMillis() + getTimeout().getSeconds() * 1000;

		while (true) {
			if (stopWaiting(condition)){
				break;
			}
			
			if (timeoutExceeded(condition, limit)){
				return;
			}
			
			sleep(WAIT_SLEEP);
		}
		
		log.debug(this.description() + condition.description()
				+ " finished successfully");
	}

	/**
	 * Gets time period of timeout.
	 * 
	 * @return time period of timeout
	 */
	protected TimePeriod getTimeout() {
		return timeout;
	}

	/**
	 * Finds out whether exception should be thrown is wait condition is not met
	 * after expiration of specified time period.
	 * 
	 * @return true if exception should be thrown, false otherwise
	 */
	protected boolean throwTimeoutException() {
		return throwTimeoutException;
	}

	/**
	 * Sleeps for specified time period.
	 * 
	 * @param timePeriod time period to sleep
	 */
	public static void sleep(TimePeriod timePeriod) {
		try {
			Thread.sleep(timePeriod.getSeconds() * 1000);
		} catch (InterruptedException e) {
			throw new RuntimeException("Sleep interrupted", e);
		}
	}
	
	private boolean timeoutExceeded(WaitCondition condition, long limit) {
		if (System.currentTimeMillis() > limit) {
			if (throwTimeoutException()) {
				log.debug(this.description()  + condition.description() + " failed, an exception will be thrown");
				throw new WaitTimeoutExpiredException("Timeout after: "
						+ timeout + " ms.: " + condition.description());
			} else {
				log.debug(this.description()  + condition.description() + " failed, NO exception will be thrown");
				return true;
			}
		}
		return false;
	}
}
