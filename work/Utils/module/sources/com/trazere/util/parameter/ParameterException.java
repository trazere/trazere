package com.trazere.util.parameter;

/**
 * <code>ParameterException</code> exceptions are thrown when parameter related errors occur.
 */
public class ParameterException
extends Exception {
	/**
	 * Instanciate a new exception.
	 */
	public ParameterException() {
		super();
	}

	/**
	 * Instanciate a new exception using the given message.
	 * 
	 * @param message Details about the exception.
	 */
	public ParameterException(final String message) {
		super(message);
	}

	/**
	 * Instanciate a new exception using the given cause.
	 * 
	 * @param cause Cause of the exception.
	 */
	public ParameterException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Instanciate a new exception using the given message and cause.
	 * 
	 * @param message Details about the exception.
	 * @param cause Cause of the exception.
	 */
	public ParameterException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
