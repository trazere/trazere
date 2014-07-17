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
import java.util.NoSuchElementException;

/**
 * The {@link Iterators} class provides various factories of {@link Iterator iterators}.
 * 
 * @see Iterator
 */
public class Iterators {
	/**
	 * Builds an empty iterator.
	 * 
	 * @param <E> Type of the elements.
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
	};
	
	/**
	 * Builds an iterator over the given element.
	 * 
	 * @param <E> Type of the the element.
	 * @param element Element provided by the iterator to build.
	 * @return The built iterator.
	 */
	public static <E> Iterator<E> fromElement(final E element) {
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
					return element;
				} else {
					throw new NoSuchElementException();
				}
			}
		};
	}
	
	/**
	 * Builds an iterator over the given elements.
	 * 
	 * @param <E> Type of the the elements.
	 * @param elements Elements provided by the iterator to build.
	 * @return The built iterator.
	 */
	@SafeVarargs
	public static <E> Iterator<E> fromElements(final E... elements) {
		assert null != elements;
		
		return new Iterator<E>() {
			protected int _index = 0;
			
			@Override
			public boolean hasNext() {
				return _index < elements.length;
			}
			
			@Override
			public E next()
			throws NoSuchElementException {
				if (_index < elements.length) {
					final E value = elements[_index];
					_index += 1;
					
					return value;
				} else {
					throw new NoSuchElementException();
				}
			}
		};
	}
	
	private Iterators() {
		// Prevent instantiation.
	}
}
