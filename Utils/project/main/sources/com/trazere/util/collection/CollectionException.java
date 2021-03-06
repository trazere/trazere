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
package com.trazere.util.collection;

import com.trazere.util.lang.BaseThrowableFactory;
import com.trazere.util.lang.ThrowableFactory;

/**
 * {@link CollectionException} exceptions are thrown when collection related errors occur.
 * 
 * @deprecated Use {@link RuntimeException}.
 */
@Deprecated
public class CollectionException
extends Exception {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new exception.
	 * 
	 * @deprecated Use {@link RuntimeException#RuntimeException()}.
	 */
	@Deprecated
	public CollectionException() {
		super();
	}
	
	/**
	 * Instantiates a new exception using the given message.
	 * 
	 * @param message Details about the exception.
	 * @deprecated Use {@link RuntimeException#RuntimeException(String)}.
	 */
	@Deprecated
	public CollectionException(final String message) {
		super(message);
	}
	
	/**
	 * Instantiates a new exception using the given cause.
	 * 
	 * @param cause Cause of the exception.
	 * @deprecated Use {@link RuntimeException#RuntimeException(Throwable)}.
	 */
	@Deprecated
	public CollectionException(final Throwable cause) {
		super(cause);
	}
	
	/**
	 * Instantiates a new exception using the given message and cause.
	 * 
	 * @param message Details about the exception.
	 * @param cause Cause of the exception.
	 * @deprecated Use {@link RuntimeException#RuntimeException(String, Throwable)}.
	 */
	@Deprecated
	public CollectionException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Factory of {@link CollectionException}.
	 * 
	 * @deprecated Use {@link com.trazere.core.lang.ThrowableFactories#RUNTIME_EXCEPTION}.
	 */
	@Deprecated
	public static final ThrowableFactory<CollectionException> FACTORY = new BaseThrowableFactory<CollectionException>() {
		@Override
		public CollectionException build() {
			return new CollectionException();
		}
		
		@Override
		public CollectionException build(final String message) {
			return new CollectionException(message);
		}
		
		@Override
		public CollectionException build(final Throwable cause) {
			return new CollectionException(cause);
		}
		
		@Override
		public CollectionException build(final String message, final Throwable cause) {
			return new CollectionException(message, cause);
		}
	};
}
