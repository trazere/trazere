/*
 *  Copyright 2006-2013 Julien Dufour
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

import com.trazere.core.lang.ThrowableFactory;

/**
 * The {@link ThrowableFactories} class provides various throwable factories.
 */
public class ThrowableFactories {
	/** Factory of {@link Exception} instances. */
	public static final ThrowableFactory<Exception> EXCEPTION = new ThrowableFactory<Exception>() {
		@Override
		public Exception build() {
			return new Exception();
		}
		
		@Override
		public Exception build(final String message) {
			return new Exception(message);
		}
		
		@Override
		public Exception build(final Throwable cause) {
			return new Exception(cause);
		}
		
		@Override
		public Exception build(final String message, final Throwable cause) {
			return new Exception(message, cause);
		}
	};
	
	/** Factory of {@link RuntimeException} instances. */
	public static final ThrowableFactory<RuntimeException> RUNTIME_EXCEPTION = new ThrowableFactory<RuntimeException>() {
		@Override
		public RuntimeException build() {
			return new RuntimeException();
		}
		
		@Override
		public RuntimeException build(final String message) {
			return new RuntimeException(message);
		}
		
		@Override
		public RuntimeException build(final Throwable cause) {
			return new RuntimeException(cause);
		}
		
		@Override
		public RuntimeException build(final String message, final Throwable cause) {
			return new RuntimeException(message, cause);
		}
	};
	
	private ThrowableFactories() {
		// Prevents instantiation.
	}
}
