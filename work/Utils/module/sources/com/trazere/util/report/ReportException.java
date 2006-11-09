package com.trazere.util.report;

/**
 * <code>ReportException</code> exceptions are thrown when report related errors occur.
 */
public class ReportException
extends Exception {
	/**
	 * Instanciate a new exception.
	 */
	public ReportException() {
		super();
	}

	/**
	 * Instanciate a new exception using the given message.
	 * 
	 * @param message Details about the exception.
	 */
	public ReportException(final String message) {
		super(message);
	}

	/**
	 * Instanciate a new exception using the given cause.
	 * 
	 * @param cause Cause of the exception.
	 */
	public ReportException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Instanciate a new exception using the given message and cause.
	 * 
	 * @param message Details about the exception.
	 * @param cause Cause of the exception.
	 */
	public ReportException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
