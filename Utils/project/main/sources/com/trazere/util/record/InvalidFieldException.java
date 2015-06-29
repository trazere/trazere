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
package com.trazere.util.record;

import com.trazere.util.lang.BaseThrowableFactory;
import com.trazere.util.lang.ThrowableFactory;

/**
 * {@link InvalidFieldException} exceptions are thrown when some record field is invalid or cannot be computed.
 * 
 * @deprecated Use {@link com.trazere.core.record.InvalidFieldException}.
 */
@Deprecated
public class InvalidFieldException
extends RecordException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new exception.
	 * 
	 * @deprecated Use {@link com.trazere.core.record.InvalidFieldException#InvalidFieldException()}.
	 */
	@Deprecated
	public InvalidFieldException() {
		super();
	}
	
	/**
	 * Instantiates a new exception using the given message.
	 * 
	 * @param message Details about the exception.
	 * @deprecated Use {@link com.trazere.core.record.InvalidFieldException#InvalidFieldException(String)}.
	 */
	@Deprecated
	public InvalidFieldException(final String message) {
		super(message);
	}
	
	/**
	 * Instantiates a new exception using the given cause.
	 * 
	 * @param cause Cause of the exception.
	 * @deprecated Use {@link com.trazere.core.record.InvalidFieldException#InvalidFieldException(Throwable)}.
	 */
	@Deprecated
	public InvalidFieldException(final Throwable cause) {
		super(cause);
	}
	
	/**
	 * Instantiates a new exception using the given message and cause.
	 * 
	 * @param message Details about the exception.
	 * @param cause Cause of the exception.
	 * @deprecated Use {@link com.trazere.core.record.InvalidFieldException#InvalidFieldException(String, Throwable)}.
	 */
	@Deprecated
	public InvalidFieldException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Factory of {@link InvalidFieldException}.
	 * 
	 * @deprecated Use {@link com.trazere.core.record.InvalidFieldException#FACTORY}.
	 */
	@Deprecated
	@SuppressWarnings("hiding")
	public static final ThrowableFactory<InvalidFieldException> FACTORY = new BaseThrowableFactory<InvalidFieldException>() {
		@Override
		public InvalidFieldException build() {
			return new InvalidFieldException();
		}
		
		@Override
		public InvalidFieldException build(final String message) {
			return new InvalidFieldException(message);
		}
		
		@Override
		public InvalidFieldException build(final Throwable cause) {
			return new InvalidFieldException(cause);
		}
		
		@Override
		public InvalidFieldException build(final String message, final Throwable cause) {
			return new InvalidFieldException(message, cause);
		}
	};
}
