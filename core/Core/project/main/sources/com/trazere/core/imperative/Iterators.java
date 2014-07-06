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
package com.trazere.core.imperative;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * The {@link Iterators} class provides various factories of iterators.
 * 
 * @see Iterator
 */
public class Iterators {
	/**
	 * Builds an empty iterator.
	 * 
	 * @param <E> Type of the the elements.
	 * @return The built iterator.
	 */
	@SuppressWarnings("unchecked")
	public static <E> Iterator<E> empty() {
		return (Iterator<E>) EMPTY;
	}
	
	private static final Iterator<?> EMPTY = new Iterator<Object>() {
		@Override
		public boolean hasNext() {
			return false;
		}
		
		@Override
		public Object next()
		throws NoSuchElementException {
			throw new NoSuchElementException();
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	};
	
	/**
	 * Builds an iterator over the given value.
	 * 
	 * @param <E> Type of the the element.
	 * @param value Value to iterate.
	 * @return The built iterator.
	 */
	public static <E> Iterator<E> fromValue(final E value) {
		return new Iterator<E>() {
			protected boolean _next = true;
			
			@Override
			public boolean hasNext() {
				return _next;
			}
			
			@Override
			public E next()
			throws NoSuchElementException {
				if (_next) {
					_next = false;
					return value;
				} else {
					throw new NoSuchElementException();
				}
			}
			
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	/**
	 * Builds an iterator over the given values.
	 * 
	 * @param <E> Type of the the elements.
	 * @param values Values to iterate.
	 * @return The built iterator.
	 */
	@SafeVarargs
	public static <E> Iterator<E> fromValues(final E... values) {
		assert null != values;
		
		return new Iterator<E>() {
			protected int _index = 0;
			
			@Override
			public boolean hasNext() {
				return _index < values.length;
			}
			
			@Override
			public E next()
			throws NoSuchElementException {
				if (_index < values.length) {
					final E value = values[_index];
					_index += 1;
					
					return value;
				} else {
					throw new NoSuchElementException();
				}
			}
			
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	/**
	 * Builds an iterator that iterates the given list in reverse.
	 * 
	 * @param <E> Type of the elements.
	 * @param list List to iterate.
	 * @return The built iterator.
	 */
	public static <E> Iterator<E> reverse(final List<? extends E> list) {
		final ListIterator<? extends E> iterator = list.listIterator(list.size());
		return new Iterator<E>() {
			@Override
			public boolean hasNext() {
				return iterator.hasPrevious();
			}
			
			@Override
			public E next() {
				return iterator.previous();
			}
			
			@Override
			public void remove() {
				iterator.remove();
			}
		};
	}
	
	private Iterators() {
		// Prevent instantiation.
	}
}
