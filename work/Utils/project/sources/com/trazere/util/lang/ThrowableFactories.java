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
 * The {@link ThrowableFactories} class provides various throwable factories.
 */
public class ThrowableFactories {
	/** Factory of {@link Exception} instances. */
	public static final ThrowableFactory<Exception> EXCEPTION = new AbstractThrowableFactory<Exception>() {
		public Exception build() {
			return new Exception();
		}
		
		public Exception build(final String message) {
			return new Exception(message);
		}
		
		public Exception build(final Throwable cause) {
			return new Exception(cause);
		}
		
		public Exception build(final String message, final Throwable cause) {
			return new Exception(message, cause);
		}
	};
	
	/** Factory of {@link RuntimeException} instances. */
	public static final ThrowableFactory<RuntimeException> RUNTIME_EXCEPTION = new AbstractThrowableFactory<RuntimeException>() {
		public RuntimeException build() {
			return new RuntimeException();
		}
		
		public RuntimeException build(final String message) {
			return new RuntimeException(message);
		}
		
		public RuntimeException build(final Throwable cause) {
			return new RuntimeException(cause);
		}
		
		public RuntimeException build(final String message, final Throwable cause) {
			return new RuntimeException(message, cause);
		}
	};
	
	private ThrowableFactories() {
		// Prevent instantiation.
	}
}
