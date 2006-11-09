package com.trazere.util;

/**
 * <code>AssertException</code> exceptions are thrown when assertion tests fails.
 * 
 * @see Assert
 */
public class AssertException
extends RuntimeException {
	/**
	 * Instanciate a new exception.
	 */
	public AssertException() {
		super();
	}

	/**
	 * Instanciate a new exception using the given message.
	 * 
	 * @param message Details about the exception.
	 */
	public AssertException(final String message) {
		super(message);
	}

	/**
	 * Instanciate a new exception using the given cause.
	 * 
	 * @param cause Cause of the exception.
	 */
	public AssertException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Instanciate a new exception using the given message and cause.
	 * 
	 * @param message Details about the exception.
	 * @param cause Cause of the exception.
	 */
	public AssertException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
