/*
 *  Copyright 2006-2012 Julien Dufour
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

import com.trazere.util.function.Function1;
import com.trazere.util.type.Maybe;

/**
 * The {@link CheckedMapIterator} abstract class provides iterator combinators which transform their values.
 * 
 * @param <T> Type of the values of the feed.
 * @param <R> Type of the extracted values.
 * @param <X> Type of the exceptions.
 */
public abstract class CheckedMapIterator<T, R, X extends Exception>
extends CheckedMapFilterIterator<T, R, X> {
	/**
	 * Builds an iterator using the given feed and function.
	 * 
	 * @param <T> Type of the values of the feeds.
	 * @param <R> Type of the produced values.
	 * @param <X> Type of the exceptions.
	 * @param feed The feed.
	 * @param function The function.
	 * @return The built iterator.
	 */
	public static <T, R, X extends Exception> CheckedMapIterator<T, R, X> build(final CheckedIterator<? extends T, ? extends X> feed, final Function1<? super T, ? extends R, ? extends X> function) {
		assert null != feed;
		assert null != function;
		
		return new CheckedMapIterator<T, R, X>() {
			@Override
			protected Maybe<T> pull()
			throws X {
				return CollectionUtils.next(feed);
			}
			
			@Override
			protected R map(final T value)
			throws X {
				return function.evaluate(value);
			}
		};
	}
	
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
