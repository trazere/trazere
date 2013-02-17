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

import com.trazere.util.function.Function1;
import com.trazere.util.type.Maybe;

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
	
	/**
	 * Unwraps and throws the given wrapped exception using the given extractor.
	 * <p>
	 * This methods simply returns when the extractor returns no results.
	 * 
	 * @param <T> Type of the wrapped exception.
	 * @param exception The exception.
	 * @param extractor The extractor.
	 * @throws T The unwrapped exception.
	 */
	public static <T extends Throwable> void unwrapException(final WrappedException exception, final Function1<? super Throwable, ? extends Maybe<? extends T>, ? extends RuntimeException> extractor)
	throws T {
		assert null != exception;
		assert null != extractor;
		
		final Maybe<? extends T> cause = extractor.evaluate(exception.getCause());
		if (cause.isSome()) {
			throw cause.asSome().getValue();
		}
	}
	
	/**
	 * Unwraps and throws the given wrapped exception using the given extractor, or rethrows the wrapped exception.
	 * <p>
	 * This methods always throws either the wrapped or unwrapped exception, it never returns.
	 * 
	 * @param <T> Type of the wrapped exception.
	 * @param exception The exception.
	 * @param extractor The extractor.
	 * @throws T The unwrapped exception.
	 * @throws WrappedException The wrapped exception when the extractor returns no results.
	 */
	public static <T extends Throwable> void unwrapExceptionOrRethrow(final WrappedException exception, final Function1<? super Throwable, ? extends Maybe<? extends T>, ? extends RuntimeException> extractor)
	throws T, WrappedException {
		assert null != exception;
		
		final Maybe<? extends T> cause = extractor.evaluate(exception.getCause());
		if (cause.isSome()) {
			throw cause.asSome().getValue();
		} else {
			throw exception;
		}
	}
	
	/**
	 * Unwraps and throws the given wrapped exception if it has the given type.
	 * <p>
	 * This methods simply returns when the extractor returns no results.
	 * 
	 * @param <T> Type of the wrapped exception.
	 * @param exception The exception.
	 * @param type The wrapped exception type.
	 * @throws T The unwrapped exception.
	 */
	public static <T extends Throwable> void unwrapException(final WrappedException exception, final Class<T> type)
	throws T {
		unwrapException(exception, LangUtils.<Throwable, T, RuntimeException>matchFunction(type));
	}
	
	/**
	 * Unwraps and throws the given wrapped exception if it has the given type, or rethrows the wrapped exception.
	 * <p>
	 * This methods always throws either the wrapped or unwrapped exception, it never returns.
	 * 
	 * @param <T> Type of the wrapped exception.
	 * @param exception The exception.
	 * @param type The wrapped exception type.
	 * @throws T The unwrapped exception.
	 * @throws WrappedException The wrapped exception when it does not have the given type.
	 */
	public static <T extends Throwable> void unwrapExceptionOrRethrow(final WrappedException exception, final Class<T> type)
	throws T, WrappedException {
		unwrapExceptionOrRethrow(exception, LangUtils.<Throwable, T, RuntimeException>matchFunction(type));
	}
}
