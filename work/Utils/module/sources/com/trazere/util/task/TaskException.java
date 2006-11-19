package com.trazere.util.task;

/**
 * <code>TaskException</code> exceptions are thrown when task related errors occur.
 */
public class TaskException
extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Instanciate a new exception.
	 */
	public TaskException() {
		super();
	}

	/**
	 * Instanciate a new exception using the given message.
	 * 
	 * @param message Details about the exception.
	 */
	public TaskException(final String message) {
		super(message);
	}

	/**
	 * Instanciate a new exception using the given cause.
	 * 
	 * @param cause Cause of the exception.
	 */
	public TaskException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Instanciate a new exception using the given message and cause.
	 * 
	 * @param message Details about the exception.
	 * @param cause Cause of the exception.
	 */
	public TaskException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
