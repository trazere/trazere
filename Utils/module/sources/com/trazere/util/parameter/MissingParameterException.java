package com.trazere.util.parameter;

/**
 * <code>MissingParameterException</code> exceptions are thrown when some required parameters are missing.
 */
public class MissingParameterException
extends ParameterException {
	/**
	 * Instanciate a new exception.
	 */
	public MissingParameterException() {
		super();
	}

	/**
	 * Instanciate a new exception using the given message.
	 * 
	 * @param message Details about the exception.
	 */
	public MissingParameterException(final String message) {
		super(message);
	}

	/**
	 * Instanciate a new exception using the given cause.
	 * 
	 * @param cause Cause of the exception.
	 */
	public MissingParameterException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Instanciate a new exception using the given message and cause.
	 * 
	 * @param message Details about the exception.
	 * @param cause Cause of the exception.
	 */
	public MissingParameterException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
