/*
 *  Copyright 2006 Julien Dufour
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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
