/*
 *  Copyright 2008 Julien Dufour
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

import com.trazere.util.function.ApplicationException;
import com.trazere.util.function.Function;
import java.util.Iterator;

/**
 * The {@link FunctionIterator} abstract class represents iterator combinators which transform the iterated elements.
 * 
 * @param <T> Type of the elements of the feeds.
 * @param <R> Type of the produced elements.
 */
public abstract class FunctionIterator<T, R>
implements Iterator<R>, Function<T, R> {
	/**
	 * Build an iterator using the given feed and function.
	 * 
	 * @param <T> Type of the elements of the feeds.
	 * @param <R> Type of the produced elements.
	 * @param feed Element feed.
	 * @param function Function to use to transform the elements.
	 * @return The built iterator.
	 */
	public static <T, R> FunctionIterator<T, R> build(final Iterator<T> feed, final Function<? super T, ? extends R> function) {
		assert null != function;
		
		return new FunctionIterator<T, R>(feed) {
			public R apply(final T value)
			throws ApplicationException {
				return function.apply(value);
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
	public FunctionIterator(final Iterator<T> feed) {
		assert null != feed;
		
		// Initialization.
		_feed = feed;
	}
	
	public boolean hasNext() {
		return _feed.hasNext();
	}
	
	public R next() {
		return apply(_feed.next());
	}
	
	public void remove() {
		_feed.remove();
	}
}
