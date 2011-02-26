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
import com.trazere.util.lang.MutableBoolean;
import com.trazere.util.reference.MutableReference;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@link FilterIterator} abstract class represents iterator combinators which filter the iterated elements.
 * 
 * @param <T> Type of the elements.
 */
public abstract class FilterIterator<T>
implements Iterator<T> {
	/**
	 * Build an iterator using the given feed and predicate.
	 * 
	 * @param <T> Type of the elements.
	 * @param feed Element feed.
	 * @param predicate Predicate to use.
	 * @return The built iterator.
	 */
	public static <T> FilterIterator<T> build(final Iterator<T> feed, final Predicate1<? super T, ? extends RuntimeException> predicate) {
		assert null != predicate;
		
		return new FilterIterator<T>(feed) {
			@Override
			public boolean filter(final T value) {
				return predicate.evaluate(value);
			}
		};
	}
	
	/** Element feed. */
	private final Iterator<T> _feed;
	
	/** Flag indicating whether the next element has been retrieved from the feed. */
	private final MutableBoolean _lookAhead = new MutableBoolean(false);
	
	/** Next element from the feed. */
	private final MutableReference<T> _next = new MutableReference<T>();
	
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
		return lookAhead();
	}
	
	public T next() {
		if (lookAhead()) {
			final T next = _next.get();
			_next.reset();
			_lookAhead.set(false);
			return next;
		} else {
			throw new NoSuchElementException();
		}
	}
	
	private boolean lookAhead() {
		if (_lookAhead.get()) {
			return _next.isSet();
		} else {
			while (true) {
				if (_feed.hasNext()) {
					final T next = _feed.next();
					if (filter(next)) {
						_next.update(next);
						_lookAhead.set(true);
						return true;
					}
				} else {
					_next.reset();
					_lookAhead.set(true);
					return false;
				}
			}
		}
	}
	
	/**
	 * Filter the given value.
	 * 
	 * @param value Value to filter. May be <code>null</code>.
	 * @return <code>true</code> when the value is accepted, <code>false</code> otherwise.
	 */
	protected abstract boolean filter(final T value);
	
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
