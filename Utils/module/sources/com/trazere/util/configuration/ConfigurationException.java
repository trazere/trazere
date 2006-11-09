package com.trazere.util.configuration;

/**
 * <code>ConfigurationException</code> exceptions are thrown when configuration related errors occur.
 */
public class ConfigurationException
extends Exception {
	/**
	 * Instanciate a new exception.
	 */
	public ConfigurationException() {
		super();
	}

	/**
	 * Instanciate a new exception using the given message.
	 * 
	 * @param message Details about the exception.
	 */
	public ConfigurationException(final String message) {
		super(message);
	}

	/**
	 * Instanciate a new exception using the given cause.
	 * 
	 * @param cause Cause of the exception.
	 */
	public ConfigurationException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Instanciate a new exception using the given message and cause.
	 * 
	 * @param message Details about the exception.
	 * @param cause Cause of the exception.
	 */
	public ConfigurationException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
