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
package com.trazere.core.record;

import com.trazere.core.lang.ThrowableFactory;

/**
 * {@link IncompatibleFieldException} exceptions are thrown when the value of some field does not have the appropriate type.
 * 
 * @since 2.0
 */
public class IncompatibleFieldException
extends RecordException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new exception.
	 * 
	 * @since 2.0
	 */
	public IncompatibleFieldException() {
		super();
	}
	
	/**
	 * Instantiates a new exception using the given message.
	 * 
	 * @param message Details about the exception.
	 * @since 2.0
	 */
	public IncompatibleFieldException(final String message) {
		super(message);
	}
	
	/**
	 * Instantiates a new exception using the given cause.
	 * 
	 * @param cause Cause of the exception.
	 * @since 2.0
	 */
	public IncompatibleFieldException(final Throwable cause) {
		super(cause);
	}
	
	/**
	 * Instantiates a new exception using the given message and cause.
	 * 
	 * @param message Details about the exception.
	 * @param cause Cause of the exception.
	 * @since 2.0
	 */
	public IncompatibleFieldException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Factory of {@link IncompatibleFieldException}.
	 * 
	 * @since 2.0
	 */
	@SuppressWarnings("hiding")
	public static final ThrowableFactory<IncompatibleFieldException> FACTORY = new ThrowableFactory<IncompatibleFieldException>() {
		@Override
		public IncompatibleFieldException build() {
			return new IncompatibleFieldException();
		}
		
		@Override
		public IncompatibleFieldException build(final String message) {
			return new IncompatibleFieldException(message);
		}
		
		@Override
		public IncompatibleFieldException build(final Throwable cause) {
			return new IncompatibleFieldException(cause);
		}
		
		@Override
		public IncompatibleFieldException build(final String message, final Throwable cause) {
			return new IncompatibleFieldException(message, cause);
		}
	};
}
