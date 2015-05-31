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
package com.trazere.util.reference;

import com.trazere.util.lang.BaseThrowableFactory;
import com.trazere.util.lang.ThrowableFactory;

/**
 * {@link ReferenceException} exceptions are thrown when reference related errors occur.
 * 
 * @deprecated Use {@link com.trazere.core.reference.ReferenceException}.
 */
@Deprecated
public class ReferenceException
extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new exception.
	 */
	public ReferenceException() {
		super();
	}
	
	/**
	 * Instantiates a new exception using the given message.
	 * 
	 * @param message Details about the exception.
	 */
	public ReferenceException(final String message) {
		super(message);
	}
	
	/**
	 * Instantiates a new exception using the given cause.
	 * 
	 * @param cause Cause of the exception.
	 */
	public ReferenceException(final Throwable cause) {
		super(cause);
	}
	
	/**
	 * Instantiates a new exception using the given message and cause.
	 * 
	 * @param message Details about the exception.
	 * @param cause Cause of the exception.
	 */
	public ReferenceException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	/** Factory of {@link ReferenceException}. */
	public static final ThrowableFactory<ReferenceException> FACTORY = new BaseThrowableFactory<ReferenceException>() {
		@Override
		public ReferenceException build() {
			return new ReferenceException();
		}
		
		@Override
		public ReferenceException build(final String message) {
			return new ReferenceException(message);
		}
		
		@Override
		public ReferenceException build(final Throwable cause) {
			return new ReferenceException(cause);
		}
		
		@Override
		public ReferenceException build(final String message, final Throwable cause) {
			return new ReferenceException(message, cause);
		}
	};
}
