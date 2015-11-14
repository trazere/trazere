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
 * {@link DuplicateFieldException} exceptions are thrown when some fields are in conflict.
 * 
 * @since 2.0
 */
public class DuplicateFieldException
extends RecordException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new exception.
	 * 
	 * @since 2.0
	 */
	public DuplicateFieldException() {
		super();
	}
	
	/**
	 * Instantiates a new exception using the given message.
	 * 
	 * @param message Details about the exception.
	 * @since 2.0
	 */
	public DuplicateFieldException(final String message) {
		super(message);
	}
	
	/**
	 * Instantiates a new exception using the given cause.
	 * 
	 * @param cause Cause of the exception.
	 * @since 2.0
	 */
	public DuplicateFieldException(final Throwable cause) {
		super(cause);
	}
	
	/**
	 * Instantiates a new exception using the given message and cause.
	 * 
	 * @param message Details about the exception.
	 * @param cause Cause of the exception.
	 * @since 2.0
	 */
	public DuplicateFieldException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Factory of {@link DuplicateFieldException}.
	 * 
	 * @since 2.0
	 */
	@SuppressWarnings("hiding")
	public static final ThrowableFactory<DuplicateFieldException> FACTORY = new ThrowableFactory<DuplicateFieldException>() {
		@Override
		public DuplicateFieldException build() {
			return new DuplicateFieldException();
		}
		
		@Override
		public DuplicateFieldException build(final String message) {
			return new DuplicateFieldException(message);
		}
		
		@Override
		public DuplicateFieldException build(final Throwable cause) {
			return new DuplicateFieldException(cause);
		}
		
		@Override
		public DuplicateFieldException build(final String message, final Throwable cause) {
			return new DuplicateFieldException(message, cause);
		}
	};
}
