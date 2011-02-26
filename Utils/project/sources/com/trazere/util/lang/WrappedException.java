/*
 *  Copyright 2006-2011 Julien Dufour
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
 * The {@link WrappedException} exceptions aim to wrap other exceptions to propagate them out of methods which throw nothing.
 */
public class WrappedException
extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiate a new exception using the given cause.
	 * 
	 * @param cause Cause of the exception.
	 */
	public WrappedException(final Throwable cause) {
		super(cause);
	}
}
