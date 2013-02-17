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

import com.trazere.util.function.Predicate1;
import com.trazere.util.type.Maybe;
import java.util.Iterator;

/**
 * The {@link FilterIterator} abstract class represents iterator combinators which filter their values.
 * 
 * @param <T> Type of the values.
 */
public abstract class FilterIterator<T>
extends CheckedFilterIterator<T, RuntimeException>
implements Iterator<T> {
	/**
	 * Builds an iterator using the given feed and predicate.
	 * 
	 * @param <T> Type of the values.
	 * @param feed The element feed.
	 * @param predicate The predicate to use.
	 * @return The built iterator.
	 */
	public static <T> FilterIterator<T> build(final Iterator<? extends T> feed, final Predicate1<? super T, ? extends RuntimeException> predicate) {
		assert null != predicate;
		
		return new FilterIterator<T>() {
			@Override
			protected Maybe<T> pull() {
				return CollectionUtils.next(feed);
			}
			
			@Override
			public boolean filter(final T value) {
				return predicate.evaluate(value);
			}
		};
	}
	
	// Iterator.
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
