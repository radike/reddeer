package org.jboss.reddeer.requirements.exception;

/**
 * Thrown when some error had appeared on requirements layer. Typically when
 * requirement couldn't be fulfilled (even though canFulfill returned true)
 * 
 * @author rhopp
 */
public class RequirementsLayerException extends RuntimeException {

	private static final long serialVersionUID = 6490745570893239529L;
	
	/**
	 * Constructs a new requirements layer exception with the specified detail
	 * message.
	 * 
	 * @param message the specified detail message
	 * 
	 * @see RuntimeException#RuntimeException(String)
	 */
	public RequirementsLayerException(String message) {
		super(message);
	}
	
	/**
	 * Constructs a new requirements layer exception with the specified detail
	 * message and cause.
	 * 
	 * @param message the specified detail message
	 * @param cause the cause
	 * 
	 * @see RuntimeException#RuntimeException(String, Throwable)
	 */
	public RequirementsLayerException(String message, Throwable cause) {
		super(message, cause);
	}

}
