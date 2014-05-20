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
package com.trazere.util.reference;

import com.trazere.core.lang.ThrowableFactory;

/**
 * {@link ReferenceNotSetException} exceptions are thrown when trying to reset some reference which has not been set.
 * <p>
 * These exceptions are runtime exceptions in order to reduce clutter when using computations which cannot fail.
 * 
 * @see MutableReference
 */
public class ReferenceNotSetException
extends ReferenceException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new exception.
	 */
	public ReferenceNotSetException() {
		super();
	}
	
	/**
	 * Instantiates a new exception using the given message.
	 * 
	 * @param message Details about the exception.
	 */
	public ReferenceNotSetException(final String message) {
		super(message);
	}
	
	/**
	 * Instantiates a new exception using the given cause.
	 * 
	 * @param cause Cause of the exception.
	 */
	public ReferenceNotSetException(final Throwable cause) {
		super(cause);
	}
	
	/**
	 * Instantiates a new exception using the given message and cause.
	 * 
	 * @param message Details about the exception.
	 * @param cause Cause of the exception.
	 */
	public ReferenceNotSetException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	/** Factory of {@link ReferenceNotSetException}. */
	@SuppressWarnings("hiding")
	public static final ThrowableFactory<ReferenceNotSetException> FACTORY = new ThrowableFactory<ReferenceNotSetException>() {
		@Override
		public ReferenceNotSetException build() {
			return new ReferenceNotSetException();
		}
		
		@Override
		public ReferenceNotSetException build(final String message) {
			return new ReferenceNotSetException(message);
		}
		
		@Override
		public ReferenceNotSetException build(final Throwable cause) {
			return new ReferenceNotSetException(cause);
		}
		
		@Override
		public ReferenceNotSetException build(final String message, final Throwable cause) {
			return new ReferenceNotSetException(message, cause);
		}
	};
}
