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
package com.trazere.util.parameter;

/**
 * <code>IncompatibleParameterException</code> exceptions are thrown when some parameters doesn't have the excepted type.
 */
public class IncompatibleParameterException
extends ParameterException {
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiate a new exception.
	 */
	public IncompatibleParameterException() {
		super();
	}

	/**
	 * Instantiate a new exception using the given message.
	 * 
	 * @param message Details about the exception.
	 */
	public IncompatibleParameterException(final String message) {
		super(message);
	}

	/**
	 * Instantiate a new exception using the given cause.
	 * 
	 * @param cause Cause of the exception.
	 */
	public IncompatibleParameterException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiate a new exception using the given message and cause.
	 * 
	 * @param message Details about the exception.
	 * @param cause Cause of the exception.
	 */
	public IncompatibleParameterException(final String message, final Throwable cause) {
		super(message, cause);
	}
}