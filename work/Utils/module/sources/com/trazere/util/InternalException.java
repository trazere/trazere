package com.trazere.util;

/**
 * <code>InternalException</code> exceptions are thrown when some internal error occurs (when there is something wrong in the code).
 */
public class InternalException
extends RuntimeException {
	/**
	 * Instanciate a new exception.
	 */
	public InternalException() {
		super();
	}

	/**
	 * Instanciate a new exception using the given message.
	 * 
	 * @param message Details about the exception.
	 */
	public InternalException(final String message) {
		super(message);
	}

	/**
	 * Instanciate a new exception using the given cause.
	 * 
	 * @param cause Cause of the exception.
	 */
	public InternalException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Instanciate a new exception using the given message and cause.
	 * 
	 * @param message Details about the exception.
	 * @param cause Cause of the exception.
	 */
	public InternalException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
