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
 * {@link ReferenceAlreadySetException} exceptions are thrown when trying to set some reference which has already been set.
 * <p>
 * These exceptions are runtime exceptions in order to reduce clutter when using computations which cannot fail.
 * 
 * @see MutableReference
 * @deprecated Use {@link com.trazere.core.reference.ReferenceAlreadySetException}.
 */
@Deprecated
public class ReferenceAlreadySetException
extends ReferenceException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new exception.
	 * 
	 * @deprecated Use {@link com.trazere.core.reference.ReferenceAlreadySetException#ReferenceAlreadySetException()}.
	 */
	@Deprecated
	public ReferenceAlreadySetException() {
		super();
	}
	
	/**
	 * Instantiates a new exception using the given message.
	 * 
	 * @param message Details about the exception.
	 * @deprecated Use {@link com.trazere.core.reference.ReferenceAlreadySetException#ReferenceAlreadySetException(String)}.
	 */
	@Deprecated
	public ReferenceAlreadySetException(final String message) {
		super(message);
	}
	
	/**
	 * Instantiates a new exception using the given cause.
	 * 
	 * @param cause Cause of the exception.
	 * @deprecated Use {@link com.trazere.core.reference.ReferenceAlreadySetException#ReferenceAlreadySetException(Throwable)}.
	 */
	@Deprecated
	public ReferenceAlreadySetException(final Throwable cause) {
		super(cause);
	}
	
	/**
	 * Instantiates a new exception using the given message and cause.
	 * 
	 * @param message Details about the exception.
	 * @param cause Cause of the exception.
	 * @deprecated Use {@link com.trazere.core.reference.ReferenceAlreadySetException#ReferenceAlreadySetException(String, Throwable)}.
	 */
	@Deprecated
	public ReferenceAlreadySetException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Factory of {@link ReferenceAlreadySetException}.
	 * 
	 * @deprecated Use {@link com.trazere.core.reference.ReferenceAlreadySetException#FACTORY}.
	 */
	@Deprecated
	@SuppressWarnings("hiding")
	public static final ThrowableFactory<ReferenceAlreadySetException> FACTORY = new BaseThrowableFactory<ReferenceAlreadySetException>() {
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
