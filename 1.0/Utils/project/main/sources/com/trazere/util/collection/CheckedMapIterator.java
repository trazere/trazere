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
package com.trazere.util.collection;

import com.trazere.util.type.Maybe;

/**
 * The {@link CheckedMapIterator} abstract class provides iterator combinators which transform their values.
 * 
 * @param <T> Type of the values of the feed.
 * @param <R> Type of the extracted values.
 * @param <X> Type of the exceptions.
 * @deprecated Use {@link com.trazere.core.imperative.IteratorUtils#map(java.util.Iterator, com.trazere.core.functional.Function)}.
 */
@Deprecated
public abstract class CheckedMapIterator<T, R, X extends Exception>
extends CheckedExtractIterator<T, R, X> {
	// Iterator.
	
	@Override
	protected Maybe<? extends R> extract(final T value)
	throws X {
		return Maybe.some(map(value));
	}
	
	/**
	 * Transform the given value.
	 * 
	 * @param value The value to transform. May be <code>null</code>.
	 * @return The transformed value.
	 * @throws X When the transformation fails.
	 */
	protected abstract R map(final T value)
	throws X;
}
