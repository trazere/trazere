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
package com.trazere.util.collection;

import com.trazere.util.function.Predicate1;
import com.trazere.util.type.Maybe;

/**
 * The {@link CheckedFilterIterator} abstract class represents iterator combinators which filter their values.
 * 
 * @param <T> Type of the values.
 * @param <X> Type of the exceptions.
 */
public abstract class CheckedFilterIterator<T, X extends Exception>
extends CheckedMapFilterIterator<T, T, X> {
	/**
	 * Builds an iterator using the given feed and predicate.
	 * 
	 * @param <T> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param feed The element feed.
	 * @param predicate The predicate to use.
	 * @return The built iterator.
	 */
	public static <T, X extends Exception> CheckedFilterIterator<T, X> build(final CheckedIterator<? extends T, ? extends X> feed, final Predicate1<? super T, ? extends X> predicate) {
		assert null != predicate;
		
		return new CheckedFilterIterator<T, X>() {
			@Override
			protected Maybe<T> pull()
			throws X {
				return CollectionUtils.next(feed);
			}
			
			@Override
			public boolean filter(final T value)
			throws X {
				return predicate.evaluate(value);
			}
		};
	}
	
	// Iterator.
	
	@Override
	protected Maybe<? extends T> extract(final T value)
	throws X {
		return filter(value) ? Maybe.some(value) : Maybe.<T>none();
	}
	
	/**
	 * Filters the given value.
	 * 
	 * @param value The value to filter. May be <code>null</code>.
	 * @return <code>true</code> when the value is accepted, <code>false</code> otherwise.
	 * @throws X When the filtering fails.
	 */
	protected abstract boolean filter(final T value)
	throws X;
}
