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
 * {@link RecordException} exceptions are thrown when record related errors occur.
 * 
 * @deprecated Use {@link com.trazere.core.record.RecordException}.
 */
@Deprecated
public class RecordException
extends Exception {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new exception.
	 * 
	 * @deprecated Use {@link com.trazere.core.record.RecordException#RecordException()}.
	 */
	@Deprecated
	public RecordException() {
		super();
	}
	
	/**
	 * Instantiates a new exception using the given message.
	 * 
	 * @param message Details about the exception.
	 * @deprecated Use {@link com.trazere.core.record.RecordException#RecordException(String)}.
	 */
	@Deprecated
	public RecordException(final String message) {
		super(message);
	}
	
	/**
	 * Instantiates a new exception using the given cause.
	 * 
	 * @param cause Cause of the exception.
	 * @deprecated Use {@link com.trazere.core.record.RecordException#RecordException(Throwable)}.
	 */
	@Deprecated
	public RecordException(final Throwable cause) {
		super(cause);
	}
	
	/**
	 * Instantiates a new exception using the given message and cause.
	 * 
	 * @param message Details about the exception.
	 * @param cause Cause of the exception.
	 * @deprecated Use {@link com.trazere.core.record.RecordException#RecordException(String, Throwable)}.
	 */
	@Deprecated
	public RecordException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Factory of {@link RecordException}.
	 * 
	 * @deprecated Use {@link com.trazere.core.record.RecordException#FACTORY}.
	 */
	@Deprecated
	public static final ThrowableFactory<RecordException> FACTORY = new BaseThrowableFactory<RecordException>() {
		@Override
		public RecordException build() {
			return new RecordException();
		}
		
		@Override
		public RecordException build(final String message) {
			return new RecordException(message);
		}
		
		@Override
		public RecordException build(final Throwable cause) {
			return new RecordException(cause);
		}
		
		@Override
		public RecordException build(final String message, final Throwable cause) {
			return new RecordException(message, cause);
		}
	};
}
