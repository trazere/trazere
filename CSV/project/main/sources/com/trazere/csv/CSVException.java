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
package com.trazere.csv;

import com.trazere.core.lang.ThrowableFactory;

/**
 * {@link CSVException} exceptions are thrown when CSV related errors occur.
 * 
 * @since 1.0
 */
public class CSVException
extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new exception.
	 * 
	 * @since 1.0
	 */
	public CSVException() {
		super();
	}
	
	/**
	 * Instantiates a new exception using the given message.
	 * 
	 * @param message Details about the exception.
	 * @since 1.0
	 */
	public CSVException(final String message) {
		super(message);
	}
	
	/**
	 * Instantiates a new exception using the given cause.
	 * 
	 * @param cause Cause of the exception.
	 * @since 1.0
	 */
	public CSVException(final Throwable cause) {
		super(cause);
	}
	
	/**
	 * Instantiates a new exception using the given message and cause.
	 * 
	 * @param message Details about the exception.
	 * @param cause Cause of the exception.
	 * @since 1.0
	 */
	public CSVException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Factory of {@link CSVException}.
	 * 
	 * @since 1.0
	 */
	public static final ThrowableFactory<CSVException> FACTORY = new ThrowableFactory<CSVException>() {
		@Override
		public CSVException build() {
			return new CSVException();
		}
		
		@Override
		public CSVException build(final String message) {
			return new CSVException(message);
		}
		
		@Override
		public CSVException build(final Throwable cause) {
			return new CSVException(cause);
		}
		
		@Override
		public CSVException build(final String message, final Throwable cause) {
			return new CSVException(message, cause);
		}
	};
}
