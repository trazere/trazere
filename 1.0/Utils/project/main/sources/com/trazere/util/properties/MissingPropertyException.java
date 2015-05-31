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
 * {@link MissingPropertyException} exceptions are thrown some property are missing.
 * 
 * @deprecated {@link com.trazere.core.record.MissingFieldException}.
 */
@Deprecated
public class MissingPropertyException
extends PropertiesException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new exception.
	 */
	public MissingPropertyException() {
		super();
	}
	
	/**
	 * Instantiates a new exception using the given message.
	 * 
	 * @param message Details about the exception.
	 */
	public MissingPropertyException(final String message) {
		super(message);
	}
	
	/**
	 * Instantiates a new exception using the given cause.
	 * 
	 * @param cause Cause of the exception.
	 */
	public MissingPropertyException(final Throwable cause) {
		super(cause);
	}
	
	/**
	 * Instantiates a new exception using the given message and cause.
	 * 
	 * @param message Details about the exception.
	 * @param cause Cause of the exception.
	 */
	public MissingPropertyException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	/** Factory of {@link MissingPropertyException}. */
	@SuppressWarnings("hiding")
	public static final ThrowableFactory<MissingPropertyException> FACTORY = new BaseThrowableFactory<MissingPropertyException>() {
		@Override
		public MissingPropertyException build() {
			return new MissingPropertyException();
		}
		
		@Override
		public MissingPropertyException build(final String message) {
			return new MissingPropertyException(message);
		}
		
		@Override
		public MissingPropertyException build(final Throwable cause) {
			return new MissingPropertyException(cause);
		}
		
		@Override
		public MissingPropertyException build(final String message, final Throwable cause) {
			return new MissingPropertyException(message, cause);
		}
	};
}
