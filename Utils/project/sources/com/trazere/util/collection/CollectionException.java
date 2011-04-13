/*
 *  Copyright 2006-2011 Julien Dufour
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
 */
public class CollectionException
extends Exception {
	private static final long serialVersionUID = 1L;
	
	/** Factory of {@link CollectionException}. */
	public static final ThrowableFactory<CollectionException> FACTORY = new BaseThrowableFactory<CollectionException>() {
		public CollectionException build() {
			return new CollectionException();
		}
		
		public CollectionException build(final String message) {
			return new CollectionException(message);
		}
		
		public CollectionException build(final Throwable cause) {
			return new CollectionException(cause);
		}
		
		public CollectionException build(final String message, final Throwable cause) {
			return new CollectionException(message, cause);
		}
	};
	
	/**
	 * Instantiate a new exception.
	 */
	public CollectionException() {
		super();
	}
	
	/**
	 * Instantiate a new exception using the given message.
	 * 
	 * @param message Details about the exception.
	 */
	public CollectionException(final String message) {
		super(message);
	}
	
	/**
	 * Instantiate a new exception using the given cause.
	 * 
	 * @param cause Cause of the exception.
	 */
	public CollectionException(final Throwable cause) {
		super(cause);
	}
	
	/**
	 * Instantiate a new exception using the given message and cause.
	 * 
	 * @param message Details about the exception.
	 * @param cause Cause of the exception.
	 */
	public CollectionException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
