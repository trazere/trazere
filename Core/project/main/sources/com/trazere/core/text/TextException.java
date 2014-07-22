/*
 *  Copyright 2006-2013 Julien Dufour
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
package com.trazere.core.text;

import com.trazere.core.lang.ThrowableFactory;

/**
 * {@link TextException} exceptions are thrown when text related errors occur.
 */
public class TextException
extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new exception.
	 */
	public TextException() {
		super();
	}
	
	/**
	 * Instantiates a new exception using the given message.
	 * 
	 * @param message Details about the exception.
	 */
	public TextException(final String message) {
		super(message);
	}
	
	/**
	 * Instantiates a new exception using the given cause.
	 * 
	 * @param cause Cause of the exception.
	 */
	public TextException(final Throwable cause) {
		super(cause);
	}
	
	/**
	 * Instantiates a new exception using the given message and cause.
	 * 
	 * @param message Details about the exception.
	 * @param cause Cause of the exception.
	 */
	public TextException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	/** Factory of {@link TextException}. */
	public static final ThrowableFactory<TextException> FACTORY = new ThrowableFactory<TextException>() {
		@Override
		public TextException build() {
			return new TextException();
		}
		
		@Override
		public TextException build(final String message) {
			return new TextException(message);
		}
		
		@Override
		public TextException build(final Throwable cause) {
			return new TextException(cause);
		}
		
		@Override
		public TextException build(final String message, final Throwable cause) {
			return new TextException(message, cause);
		}
	};
}
