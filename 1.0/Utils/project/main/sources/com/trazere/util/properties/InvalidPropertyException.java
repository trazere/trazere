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
package com.trazere.util.properties;

import com.trazere.util.lang.BaseThrowableFactory;
import com.trazere.util.lang.ThrowableFactory;

/**
 * {@link InvalidPropertyException} exceptions are thrown some property is invalid.
 * 
 * @deprecated Use {@link com.trazere.core.record.InvalidFieldException}.
 */
@Deprecated
public class InvalidPropertyException
extends PropertiesException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new exception.
	 */
	public InvalidPropertyException() {
		super();
	}
	
	/**
	 * Instantiates a new exception using the given message.
	 * 
	 * @param message Details about the exception.
	 */
	public InvalidPropertyException(final String message) {
		super(message);
	}
	
	/**
	 * Instantiates a new exception using the given cause.
	 * 
	 * @param cause Cause of the exception.
	 */
	public InvalidPropertyException(final Throwable cause) {
		super(cause);
	}
	
	/**
	 * Instantiates a new exception using the given message and cause.
	 * 
	 * @param message Details about the exception.
	 * @param cause Cause of the exception.
	 */
	public InvalidPropertyException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	/** Factory of {@link InvalidPropertyException}. */
	@SuppressWarnings("hiding")
	public static final ThrowableFactory<InvalidPropertyException> FACTORY = new BaseThrowableFactory<InvalidPropertyException>() {
		@Override
		public InvalidPropertyException build() {
			return new InvalidPropertyException();
		}
		
		@Override
		public InvalidPropertyException build(final String message) {
			return new InvalidPropertyException(message);
		}
		
		@Override
		public InvalidPropertyException build(final Throwable cause) {
			return new InvalidPropertyException(cause);
		}
		
		@Override
		public InvalidPropertyException build(final String message, final Throwable cause) {
			return new InvalidPropertyException(message, cause);
		}
	};
}
