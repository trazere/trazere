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
package com.trazere.util.lang;

/**
 * {@link TypeCheckException} exceptions are thrown when some type checking fails.
 */
public class TypeCheckException
extends Exception {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new exception.
	 */
	public TypeCheckException() {
		super();
	}
	
	/**
	 * Instantiates a new exception using the given message.
	 * 
	 * @param message Details about the exception.
	 */
	public TypeCheckException(final String message) {
		super(message);
	}
	
	/**
	 * Instantiates a new exception using the given cause.
	 * 
	 * @param cause Cause of the exception.
	 */
	public TypeCheckException(final Throwable cause) {
		super(cause);
	}
	
	/**
	 * Instantiates a new exception using the given message and cause.
	 * 
	 * @param message Details about the exception.
	 * @param cause Cause of the exception.
	 */
	public TypeCheckException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	/** Factory of {@link TypeCheckException}. */
	public static final ThrowableFactory<TypeCheckException> FACTORY = new BaseThrowableFactory<TypeCheckException>() {
		@Override
		public TypeCheckException build() {
			return new TypeCheckException();
		}
		
		@Override
		public TypeCheckException build(final String message) {
			return new TypeCheckException(message);
		}
		
		@Override
		public TypeCheckException build(final Throwable cause) {
			return new TypeCheckException(cause);
		}
		
		@Override
		public TypeCheckException build(final String message, final Throwable cause) {
			return new TypeCheckException(message, cause);
		}
	};
}
