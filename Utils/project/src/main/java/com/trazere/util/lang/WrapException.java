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
 * The {@link WrapException} exceptions aim to wrap checked throwables into unchecked exceptions in order to propagate them out through code that does not
 * provide support them.
 */
public class WrapException
extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new wrap exception with the given throwable.
	 * <p>
	 * The wrapped throwable is stored as cause.
	 * 
	 * @param cause Throwable to wrap.
	 */
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
	 */
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
	 */
	public <T extends Throwable> void unwrap(final Class<T> type)
	throws T {
		unwrap(LangExtractors.<Throwable, T, RuntimeException>match(type));
	}
}
