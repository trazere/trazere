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
package com.trazere.util.lang;

import com.trazere.util.function.Function1;
import com.trazere.util.type.Maybe;

/**
 * The {@link WrapException} exceptions aim to wrap checked throwables into unchecked exceptions in order to propagate them out through code that does not
 * provide support them.
 * 
 * @deprecated Use {@link RuntimeException}.
 */
@Deprecated
public class WrapException
extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Prevents instantiating wrap exceptions with runtime exception causes.
	 * 
	 * @param cause Throwable to wrap.
	 */
	@SuppressWarnings("unused")
	private WrapException(final RuntimeException cause) {
		this((Throwable) cause);
	}
	
	/**
	 * Prevents instantiating wrap exceptions with error causes.
	 * 
	 * @param cause Throwable to wrap.
	 */
	@SuppressWarnings("unused")
	private WrapException(final Error cause) {
		this((Throwable) cause);
	}
	
	/**
	 * Instantiates a new wrap exception with the given throwable.
	 * <p>
	 * The wrapped throwable is stored as cause.
	 * 
	 * @param cause Throwable to wrap.
	 * @deprecated Use {@link RuntimeException#RuntimeException(Throwable)}.
	 */
	@Deprecated
	public WrapException(final Throwable cause) {
		super(cause);
		
		// Checks.
		assert null != cause;
	}
	
	/**
	 * Unwraps and throws the throwable wrapped within the receiver exception using the given extractor.
	 * <p>
	 * This methods does nothing when the extractor returns no results.
	 * 
	 * @param <T> Type of the extracted throwable.
	 * @param extractor Extractor of the throwable to unwrap.
	 * @throws T The unwrapped throwable.
	 * @deprecated Use {@link com.trazere.core.lang.ThrowableUtils#throwCause(Throwable, com.trazere.core.functional.Function)}.
	 */
	@Deprecated
	public <T extends Throwable> void unwrap(final Function1<? super Throwable, ? extends Maybe<? extends T>, ? extends RuntimeException> extractor)
	throws T {
		assert null != extractor;
		
		final Maybe<? extends T> cause = extractor.evaluate(getCause());
		if (cause.isSome()) {
			throw cause.asSome().getValue();
		}
	}
	
	/**
	 * Unwraps and throws the throwable of the given type wrapped within the receiver exception.
	 * <p>
	 * This methods does nothing when the wrapped throwable does not have the given type.
	 * 
	 * @param <T> Type of the extracted throwable.
	 * @param type Type of the throwable to unwrap.
	 * @throws T The unwrapped throwable.
	 * @deprecated Use {@link com.trazere.core.lang.ThrowableUtils#throwCause(Throwable, Class)}.
	 */
	@Deprecated
	public <T extends Throwable> void unwrap(final Class<T> type)
	throws T {
		unwrap(LangExtractors.<Throwable, T, InternalException>match(type));
	}
	
	/**
	 * Builds a factory of wrap exceptions.
	 * 
	 * @param throwableFactory Factory of the throwables to wrap.
	 * @return The built factory.
	 * @deprecated To be removed.
	 */
	@Deprecated
	public static ThrowableFactory<WrapException> factory(final ThrowableFactory<?> throwableFactory) {
		assert null != throwableFactory;
		
		return new BaseThrowableFactory<WrapException>() {
			@Override
			public WrapException build() {
				return new WrapException(throwableFactory.build());
			}
			
			@Override
			public WrapException build(final String message) {
				return new WrapException(throwableFactory.build(message));
			}
			
			@Override
			public WrapException build(final Throwable cause) {
				return new WrapException(throwableFactory.build(cause));
			}
			
			@Override
			public WrapException build(final String message, final Throwable cause) {
				return new WrapException(throwableFactory.build(message, cause));
			}
		};
	}
}
