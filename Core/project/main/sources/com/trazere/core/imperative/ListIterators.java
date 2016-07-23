/*
 *  Copyright 2006-2015 Julien Dufour
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

import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * The {@link ListIterators} class provides various factories of {@link ListIterator list iterators}.
 * 
 * @see ListIterator
 * @since 2.0
 */
public class ListIterators {
	/**
	 * Builds an empty list iterator.
	 * 
	 * @param <E> Type of the elements.
	 * @return The built iterator.
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> ExListIterator<E> empty() {
		return (ExListIterator<E>) EMPTY;
	}
	
	private static final ExListIterator<?> EMPTY = new ExListIterator<Object>() {
		@Override
		public boolean hasNext() {
			return false;
		}
		
		@Override
		public Object next() {
			throw new NoSuchElementException();
		}
		
		@Override
		public int nextIndex() {
			return 0;
		}
		
		@Override
		public boolean hasPrevious() {
			return false;
		}
		
		@Override
		public Object previous() {
			throw new NoSuchElementException();
		}
		
		@Override
		public int previousIndex() {
			return -1;
		}
	};
	
	/**
	 * Builds a list iterator over the given element.
	 * 
	 * @param <E> Type of the the element.
	 * @param element Element provided by the iterator to build.
	 * @return The built iterator.
	 * @since 2.0
	 */
	public static <E> ExListIterator<E> fromElement(final E element) {
		return new ExListIterator<E>() {
			private int _index = 0;
			
			@Override
			public boolean hasNext() {
				return _index < 1;
			}
			
			@Override
			public E next()
			throws NoSuchElementException {
				if (_index < 1) {
					_index += 1;
					return element;
				} else {
					throw new NoSuchElementException();
				}
			}
			
			@Override
			public int nextIndex() {
				return _index;
			}
			
			@Override
			public boolean hasPrevious() {
				return _index > 0;
			}
			
			@Override
			public E previous() {
				if (_index > 0) {
					_index -= 1;
					return element;
				} else {
					throw new NoSuchElementException();
				}
			}
			
			@Override
			public int previousIndex() {
				return _index - 1;
			}
		};
	}
	
	/**
	 * Builds a list iterator over the given elements.
	 * 
	 * @param <E> Type of the the elements.
	 * @param elements Elements provided by the iterator to build.
	 * @return The built iterator.
	 * @since 2.0
	 */
	@SafeVarargs
	public static <E> ExListIterator<E> fromElements(final E... elements) {
		assert null != elements;
		
		return new ExListIterator<E>() {
			private int _index = 0;
			
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
			
			@Override
			public int nextIndex() {
				return _index;
			}
			
			@Override
			public boolean hasPrevious() {
				return _index > 0;
			}
			
			@Override
			public E previous() {
				if (_index > 0) {
					_index -= 1;
					return elements[_index];
				} else {
					throw new NoSuchElementException();
				}
			}
			
			@Override
			public int previousIndex() {
				return _index - 1;
			}
		};
	}
	
	private ListIterators() {
		// Prevent instantiation.
	}
}
