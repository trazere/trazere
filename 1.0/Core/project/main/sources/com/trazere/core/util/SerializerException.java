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
package com.trazere.core.util;

import com.trazere.core.lang.ThrowableFactory;

/**
 * {@link SerializerException} exceptions are thrown when {@link Serializer serializer} related errors occur.
 * 
 * @see Serializer
 * @since 1.0
 */
public class SerializerException
extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new exception.
	 * 
	 * @since 1.0
	 */
	public SerializerException() {
		super();
	}
	
	/**
	 * Instantiates a new exception using the given message.
	 * 
	 * @param message Details about the exception.
	 * @since 1.0
	 */
	public SerializerException(final String message) {
		super(message);
	}
	
	/**
	 * Instantiates a new exception using the given cause.
	 * 
	 * @param cause Cause of the exception.
	 * @since 1.0
	 */
	public SerializerException(final Throwable cause) {
		super(cause);
	}
	
	/**
	 * Instantiates a new exception using the given message and cause.
	 * 
	 * @param message Details about the exception.
	 * @param cause Cause of the exception.
	 * @since 1.0
	 */
	public SerializerException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Factory of {@link SerializerException}.
	 * 
	 * @since 1.0
	 */
	public static final ThrowableFactory<SerializerException> FACTORY = new ThrowableFactory<SerializerException>() {
		@Override
		public SerializerException build() {
			return new SerializerException();
		}
		
		@Override
		public SerializerException build(final String message) {
			return new SerializerException(message);
		}
		
		@Override
		public SerializerException build(final Throwable cause) {
			return new SerializerException(cause);
		}
		
		@Override
		public SerializerException build(final String message, final Throwable cause) {
			return new SerializerException(message, cause);
		}
	};
}
