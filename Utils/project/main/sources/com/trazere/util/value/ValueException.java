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
package com.trazere.util.value;

import com.trazere.util.lang.BaseThrowableFactory;
import com.trazere.util.lang.ThrowableFactory;

/**
 * {@link ValueException} exceptions are thrown when value related errors occur.
 * 
 * @deprecated To be removed.
 */
@Deprecated
public class ValueException
extends Exception {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new exception.
	 * 
	 * @deprecated To be removed.
	 */
	@Deprecated
	public ValueException() {
		super();
	}
	
	/**
	 * Instantiates a new exception using the given message.
	 * 
	 * @param message Details about the exception.
	 * @deprecated To be removed.
	 */
	@Deprecated
	public ValueException(final String message) {
		super(message);
	}
	
	/**
	 * Instantiates a new exception using the given cause.
	 * 
	 * @param cause Cause of the exception.
	 * @deprecated To be removed.
	 */
	@Deprecated
	public ValueException(final Throwable cause) {
		super(cause);
	}
	
	/**
	 * Instantiates a new exception using the given message and cause.
	 * 
	 * @param message Details about the exception.
	 * @param cause Cause of the exception.
	 * @deprecated To be removed.
	 */
	@Deprecated
	public ValueException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Factory of {@link ValueException}.
	 * 
	 * @deprecated To be removed.
	 */
	@Deprecated
	public static final ThrowableFactory<ValueException> FACTORY = new BaseThrowableFactory<ValueException>() {
		@Override
		public ValueException build() {
			return new ValueException();
		}
		
		@Override
		public ValueException build(final String message) {
			return new ValueException(message);
		}
		
		@Override
		public ValueException build(final Throwable cause) {
			return new ValueException(cause);
		}
		
		@Override
		public ValueException build(final String message, final Throwable cause) {
			return new ValueException(message, cause);
		}
	};
}
