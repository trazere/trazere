/*
 *  Copyright 2006-2010 Julien Dufour
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
import java.util.Iterator;

/**
 * The {@link MapIterator} abstract class represents iterator combinators which transform the iterated elements.
 * 
 * @param <T> Type of the elements of the feeds.
 * @param <R> Type of the produced elements.
 */
public abstract class MapIterator<T, R>
implements Iterator<R> {
	/**
	 * Build an iterator using the given feed and function.
	 * 
	 * @param <T> Type of the elements of the feeds.
	 * @param <R> Type of the produced elements.
	 * @param feed Element feed.
	 * @param function Function to use to transform the elements.
	 * @return The built iterator.
	 */
	public static <T, R> MapIterator<T, R> build(final Iterator<T> feed, final Function1<? super T, ? extends R, ? extends RuntimeException> function) {
		assert null != function;
		
		return new MapIterator<T, R>(feed) {
			@Override
			public R map(final T value) {
				return function.evaluate(value);
			}
		};
	}
	
	/** Element feed. */
	private final Iterator<T> _feed;
	
	/**
	 * Instanciate a new iterator with the given feed.
	 * 
	 * @param feed Element feed.
	 */
	public MapIterator(final Iterator<T> feed) {
		assert null != feed;
		
		// Initialization.
		_feed = feed;
	}
	
	public boolean hasNext() {
		return _feed.hasNext();
	}
	
	public R next() {
		return map(_feed.next());
	}
	
	protected abstract R map(final T value);
	
	public void remove() {
		_feed.remove();
	}
}
