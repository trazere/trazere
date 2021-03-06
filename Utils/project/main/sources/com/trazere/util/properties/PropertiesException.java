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
package com.trazere.util.properties;

import com.trazere.util.lang.BaseThrowableFactory;
import com.trazere.util.lang.ThrowableFactory;

/**
 * {@link PropertiesException} exceptions are thrown when properties related errors occur.
 * 
 * @deprecated Use {@link com.trazere.core.record.RecordException}.
 */
@Deprecated
public class PropertiesException
extends Exception {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new exception.
	 * 
	 * @deprecated Use {@link com.trazere.core.record.RecordException#RecordException()}.
	 */
	@Deprecated
	public PropertiesException() {
		super();
	}
	
	/**
	 * Instantiates a new exception using the given message.
	 * 
	 * @param message Details about the exception.
	 * @deprecated Use {@link com.trazere.core.record.RecordException#RecordException(String)}.
	 */
	@Deprecated
	public PropertiesException(final String message) {
		super(message);
	}
	
	/**
	 * Instantiates a new exception using the given cause.
	 * 
	 * @param cause Cause of the exception.
	 * @deprecated Use {@link com.trazere.core.record.RecordException#RecordException(Throwable)}.
	 */
	@Deprecated
	public PropertiesException(final Throwable cause) {
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
	public PropertiesException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Factory of {@link PropertiesException}.
	 * 
	 * @deprecated Use {@link com.trazere.core.record.RecordException#FACTORY}.
	 */
	@Deprecated
	public static final ThrowableFactory<PropertiesException> FACTORY = new BaseThrowableFactory<PropertiesException>() {
		@Override
		public PropertiesException build() {
			return new PropertiesException();
		}
		
		@Override
		public PropertiesException build(final String message) {
			return new PropertiesException(message);
		}
		
		@Override
		public PropertiesException build(final Throwable cause) {
			return new PropertiesException(cause);
		}
		
		@Override
		public PropertiesException build(final String message, final Throwable cause) {
			return new PropertiesException(message, cause);
		}
	};
}
