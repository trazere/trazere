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
import java.util.Iterator;

/**
 * The {@link MapFilterIterator} abstract class provides iterator combinators which transform and filter their values.
 * 
 * @param <T> Type of the values of the feed.
 * @param <R> Type of the extracted values.
 */
public abstract class MapFilterIterator<T, R>
extends CheckedMapFilterIterator<T, R, RuntimeException>
implements Iterator<R> {
	/**
	 * Builds an iterator using the given feed and extractor.
	 * 
	 * @param <T> Type of the values of the feeds.
	 * @param <R> Type of the produced values.
	 * @param feed The feed.
	 * @param extractor The extractor.
	 * @return The built iterator.
	 */
	public static <T, R> MapFilterIterator<T, R> build(final Iterator<? extends T> feed, final Function1<? super T, ? extends Maybe<? extends R>, ? extends RuntimeException> extractor) {
		assert null != feed;
		assert null != extractor;
		
		return new MapFilterIterator<T, R>() {
			@Override
			protected Maybe<T> pull() {
				return CollectionUtils.next(feed);
			}
			
			@Override
			public Maybe<? extends R> extract(final T value) {
				return extractor.evaluate(value);
			}
		};
	}
	
	// Iterator.
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
