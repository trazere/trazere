/*
 *  Copyright 2006-2008 Julien Dufour
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
import com.trazere.util.function.Filter;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@link FilterIterator} abstract class represents iterator combinators which filter the iterated elements.
 * 
 * @param <T> Type of the elements.
 */
public abstract class FilterIterator<T>
implements Iterator<T>, Filter<T> {
	/**
	 * Build an iterator using the given feed and filter.
	 * 
	 * @param <T> Type of the elements.
	 * @param feed Element feed.
	 * @param filter Filter to use.
	 * @return The built iterator.
	 */
	public static <T> FilterIterator<T> build(final Iterator<T> feed, final Filter<? super T> filter) {
		assert null != filter;
		
		return new FilterIterator<T>(feed) {
			public boolean filter(final T value)
			throws ApplicationException {
				return filter.filter(value);
			}
		};
	}
	
	/** Element feed. */
	private final Iterator<T> _feed;
	
	/** Flag indicating wether the next element has been retrieved from the feed. */
	private boolean _lookAhead = false;
	
	/** Flag indicating wether the feed is exhauted. */
	private boolean _eof;
	
	/** Next element from the feed. */
	private T _next = null;
	
	/**
	 * Instanciate a new iterator with the given feed.
	 * 
	 * @param feed Element feed.
	 */
	public FilterIterator(final Iterator<T> feed) {
		assert null != feed;
		
		// Initialization.
		_feed = feed;
	}
	
	public boolean hasNext() {
		lookAhead();
		return !_eof;
	}
	
	public T next() {
		lookAhead();
		if (_eof) {
			throw new NoSuchElementException();
		}
		
		final T next = _next;
		_next = null;
		_lookAhead = false;
		
		return next;
	}
	
	private void lookAhead() {
		while (!_lookAhead) {
			if (_feed.hasNext()) {
				final T next = _feed.next();
				if (filter(next)) {
					_next = next;
					_lookAhead = true;
				}
			} else {
				_eof = true;
				_lookAhead = true;
			}
		}
	}
	
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
