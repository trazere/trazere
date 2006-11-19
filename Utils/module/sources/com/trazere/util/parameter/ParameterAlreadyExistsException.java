package com.trazere.util.parameter;

/**
 * <code>MissingParameterException</code> exception are thrown when some parameter already exist.
 */
public class ParameterAlreadyExistsException
extends ParameterException {
	private static final long serialVersionUID = 1L;

	/**
	 * Instanciate a new exception.
	 */
	public ParameterAlreadyExistsException() {
		super();
	}

	/**
	 * Instanciate a new exception using the given message.
	 * 
	 * @param message Details about the exception.
	 */
	public ParameterAlreadyExistsException(final String message) {
		super(message);
	}

	/**
	 * Instanciate a new exception using the given cause.
	 * 
	 * @param cause Cause of the exception.
	 */
	public ParameterAlreadyExistsException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Instanciate a new exception using the given message and cause.
	 * 
	 * @param message Details about the exception.
	 * @param cause Cause of the exception.
	 */
	public ParameterAlreadyExistsException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
