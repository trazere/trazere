/*
 *  Copyright 2006-2015 Julien Dufour
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
package com.trazere.core.lang;

/**
 * {@link InternalException} exceptions are thrown when internal errors occur.
 * <p>
 * These exceptions aim to indicate problems in the code (like situations supposed to be impossible) rather than runtime issues.
 * 
 * @since 2.0
 */
public class InternalException
extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new exception.
	 * 
	 * @since 2.0
	 */
	public InternalException() {
		super();
	}
	
	/**
	 * Instantiates a new exception using the given message.
	 * 
	 * @param message Details about the exception.
	 * @since 2.0
	 */
	public InternalException(final String message) {
		super(message);
	}
	
	/**
	 * Instantiates a new exception using the given cause.
	 * 
	 * @param cause Cause of the exception.
	 * @since 2.0
	 */
	public InternalException(final Throwable cause) {
		super(cause);
	}
	
	/**
	 * Instantiates a new exception using the given message and cause.
	 * 
	 * @param message Details about the exception.
	 * @param cause Cause of the exception.
	 * @since 2.0
	 */
	public InternalException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Factory of {@link InternalException}.
	 * 
	 * @since 2.0
	 */
	public static final ThrowableFactory<InternalException> FACTORY = new ThrowableFactory<InternalException>() {
		@Override
		public InternalException build() {
			return new InternalException();
		}
		
		@Override
		public InternalException build(final String message) {
			return new InternalException(message);
		}
		
		@Override
		public InternalException build(final Throwable cause) {
			return new InternalException(cause);
		}
		
		@Override
		public InternalException build(final String message, final Throwable cause) {
			return new InternalException(message, cause);
		}
	};
}
