package com.trazere.util;

/**
 * <code>CannotComputeValueException</code> exceptions are thrown when some values cannot be computed.
 */
public class CannotComputeValueException
extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Instanciate a new exception.
	 */
	public CannotComputeValueException() {
		super();
	}

	/**
	 * Instanciate a new exception using the given message.
	 * 
	 * @param message Details about the exception.
	 */
	public CannotComputeValueException(final String message) {
		super(message);
	}

	/**
	 * Instanciate a new exception using the given cause.
	 * 
	 * @param cause Cause of the exception.
	 */
	public CannotComputeValueException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Instanciate a new exception using the given message and cause.
	 * 
	 * @param message Details about the exception.
	 * @param cause Cause of the exception.
	 */
	public CannotComputeValueException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
