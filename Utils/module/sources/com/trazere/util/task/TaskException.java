/*
 *  Copyright 2006-2010 Julien Dufour
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
package com.trazere.util.task;

import com.trazere.util.lang.ThrowableFactory;

/**
 * {@link TaskException} exceptions are thrown when task related errors occur.
 */
public class TaskException
extends Exception {
	private static final long serialVersionUID = 1L;
	
	/** Factory of {@link TaskException}. */
	public static final ThrowableFactory<TaskException> FACTORY = new ThrowableFactory<TaskException>() {
		public TaskException build() {
			return new TaskException();
		}
		
		public TaskException build(final String message) {
			return new TaskException(message);
		}
		
		public TaskException build(final Throwable cause) {
			return new TaskException(cause);
		}
		
		public TaskException build(final String message, final Throwable cause) {
			return new TaskException(message, cause);
		}
	};
	
	/**
	 * Instantiate a new exception.
	 */
	public TaskException() {
		super();
	}
	
	/**
	 * Instantiate a new exception using the given message.
	 * 
	 * @param message Details about the exception.
	 */
	public TaskException(final String message) {
		super(message);
	}
	
	/**
	 * Instantiate a new exception using the given cause.
	 * 
	 * @param cause Cause of the exception.
	 */
	public TaskException(final Throwable cause) {
		super(cause);
	}
	
	/**
	 * Instantiate a new exception using the given message and cause.
	 * 
	 * @param message Details about the exception.
	 * @param cause Cause of the exception.
	 */
	public TaskException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
