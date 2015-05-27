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
package com.trazere.core.reference;

import com.trazere.core.lang.ThrowableFactory;

/**
 * {@link ReferenceAlreadySetException} exceptions are thrown when trying to set some reference which has already been set.
 * 
 * @see MutableReference
 */
public class ReferenceAlreadySetException
extends ReferenceException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new exception.
	 */
	public ReferenceAlreadySetException() {
		super();
	}
	
	/**
	 * Instantiates a new exception using the given message.
	 * 
	 * @param message Details about the exception.
	 */
	public ReferenceAlreadySetException(final String message) {
		super(message);
	}
	
	/**
	 * Instantiates a new exception using the given cause.
	 * 
	 * @param cause Cause of the exception.
	 */
	public ReferenceAlreadySetException(final Throwable cause) {
		super(cause);
	}
	
	/**
	 * Instantiates a new exception using the given message and cause.
	 * 
	 * @param message Details about the exception.
	 * @param cause Cause of the exception.
	 */
	public ReferenceAlreadySetException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	/** Factory of {@link ReferenceAlreadySetException}. */
	@SuppressWarnings("hiding")
	public static final ThrowableFactory<ReferenceAlreadySetException> FACTORY = new ThrowableFactory<ReferenceAlreadySetException>() {
		@Override
		public ReferenceAlreadySetException build() {
			return new ReferenceAlreadySetException();
		}
		
		@Override
		public ReferenceAlreadySetException build(final String message) {
			return new ReferenceAlreadySetException(message);
		}
		
		@Override
		public ReferenceAlreadySetException build(final Throwable cause) {
			return new ReferenceAlreadySetException(cause);
		}
		
		@Override
		public ReferenceAlreadySetException build(final String message, final Throwable cause) {
			return new ReferenceAlreadySetException(message, cause);
		}
	};
}
