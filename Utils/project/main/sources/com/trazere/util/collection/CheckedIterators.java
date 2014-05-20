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

import com.trazere.util.feed.Feed;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@link CheckedIterators} class provides various factories of checked iterators.
 */
@Deprecated
public class CheckedIterators {
	/**
	 * Builds a checked iterator over no values.
	 * 
	 * @param <V> Type of the the values.
	 * @param <X> Type of the exceptions.
	 * @return The built iterator.
	 */
	@SuppressWarnings("unchecked")
	public static <V, X extends Exception> CheckedIterator<V, X> empty() {
		return (CheckedIterator<V, X>) _EMPTY;
	}
	
	private static final CheckedIterator<?, ?> _EMPTY = new CheckedIterator<Object, RuntimeException>() {
		@Override
		public boolean hasNext() {
			return false;
		}
		
		@Override
		public Object next()
		throws NoSuchElementException {
			throw new NoSuchElementException();
		}
	};
	
	/**
	 * Builds a checked iterator over the given value.
	 * 
	 * @param <V> Type of the the value.
	 * @param <X> Type of the exceptions.
	 * @param value The value. May be <code>null</code>.
	 * @return The built iterator.
	 */
	public static <V, X extends Exception> CheckedIterator<V, X> fromValue(final V value) {
		return new CheckedIterator<V, X>() {
			protected boolean _next = true;
			
			@Override
			public boolean hasNext() {
				return _next;
			}
			
			@Override
			public V next()
			throws NoSuchElementException {
				if (_next) {
					_next = false;
					return value;
				} else {
					throw new NoSuchElementException();
				}
			}
		};
	}
	
	/**
	 * Builds a checked iterator over the given values.
	 * 
	 * @param <V> Type of the the values.
	 * @param <X> Type of the exceptions.
	 * @param values The values.
	 * @return The built iterator.
	 */
	public static <V, X extends Exception> CheckedIterator<V, X> fromValues(final V... values) {
		assert null != values;
		
		return new CheckedIterator<V, X>() {
			protected int _index = 0;
			
			@Override
			public boolean hasNext() {
				return _index < values.length;
			}
			
			@Override
			public V next()
			throws NoSuchElementException {
				if (_index < values.length) {
					final V value = values[_index];
					_index += 1;
					
					return value;
				} else {
					throw new NoSuchElementException();
				}
			}
		};
	}
	
	/**
	 * Builds a checked iterator over the values of the given collection.
	 * 
	 * @param <V> Type of the the values.
	 * @param <X> Type of the exceptions.
	 * @param collection The collection.
	 * @return The built iterator.
	 */
	public static <V, X extends Exception> CheckedIterator<V, X> fromIterable(final Iterable<? extends V> collection) {
		assert null != collection;
		
		return fromIterator(collection.iterator());
	}
	
	/**
	 * Builds a checked iterator over the values provided by the given iterator.
	 * 
	 * @param <V> Type of the the values.
	 * @param <X> Type of the exceptions.
	 * @param iterator The iterator providing the values.
	 * @return The built iterator.
	 */
	public static <V, X extends Exception> CheckedIterator<V, X> fromIterator(final Iterator<? extends V> iterator) {
		assert null != iterator;
		
		return new CheckedIterator<V, X>() {
			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}
			
			@Override
			public V next()
			throws NoSuchElementException {
				return iterator.next();
			}
		};
	}
	
	/**
	 * Builds a checked iterator over the elements provided by the given feed.
	 * 
	 * @param <T> Type of the elements.
	 * @param <X> Type of the exceptions.
	 * @param feed The feed.
	 * @return The built iterator.
	 */
	public static <T, X extends Exception> CheckedIterator<T, X> fromFeed(final Feed<T, X> feed) {
		assert null != feed;
		
		return new CheckedIterator<T, X>() {
			private Feed<? extends T, ? extends X> _tail = feed;
			
			@Override
			public boolean hasNext()
			throws X {
				return !_tail.isEmpty();
			}
			
			@Override
			public T next()
			throws NoSuchElementException, X {
				final T value = _tail.getHead();
				_tail = _tail.getTail();
				return value;
			}
		};
	}
	
	private CheckedIterators() {
		// Prevent instantiation.
	}
}
