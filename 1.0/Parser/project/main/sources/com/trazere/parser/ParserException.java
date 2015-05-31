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
package com.trazere.parser;

import com.trazere.core.lang.ThrowableFactory;

/**
 * {@link ParserException} exceptions are thrown when parser related errors occur.
 */
public class ParserException
extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new exception.
	 */
	public ParserException() {
		super();
	}
	
	/**
	 * Instantiates a new exception using the given message.
	 * 
	 * @param message Details about the exception.
	 */
	public ParserException(final String message) {
		super(message);
	}
	
	/**
	 * Instantiates a new exception using the given cause.
	 * 
	 * @param cause Cause of the exception.
	 */
	public ParserException(final Throwable cause) {
		super(cause);
	}
	
	/**
	 * Instantiates a new exception using the given message and cause.
	 * 
	 * @param message Details about the exception.
	 * @param cause Cause of the exception.
	 */
	public ParserException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	/** Factory of {@link ParserException}. */
	public static final ThrowableFactory<ParserException> FACTORY = new ThrowableFactory<ParserException>() {
		@Override
		public ParserException build() {
			return new ParserException();
		}
		
		@Override
		public ParserException build(final String message) {
			return new ParserException(message);
		}
		
		@Override
		public ParserException build(final Throwable cause) {
			return new ParserException(cause);
		}
		
		@Override
		public ParserException build(final String message, final Throwable cause) {
			return new ParserException(message, cause);
		}
	};
}
