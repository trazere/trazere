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
package com.trazere.core.lang;

import com.trazere.core.imperative.ExIterator;
import com.trazere.core.imperative.Iterators;

/**
 * The {@link Iterables} class provides various factories of {@link Iterable iterables}.
 * 
 * @see Iterable
 * @since 2.0
 */
public class Iterables {
	/**
	 * Builds an empty iterable.
	 * 
	 * @param <E> Type of the elements.
	 * @return The built iterable.
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> ExIterable<E> empty() {
		return (ExIterable<E>) EMPTY;
	}
	
	private static final ExIterable<?> EMPTY = new ExIterable<Object>() {
		@Override
		public ExIterator<Object> iterator() {
			return Iterators.empty();
		}
	};
	
	/**
	 * Builds an iterable over the given element.
	 * 
	 * @param <E> Type of the the element.
	 * @param element Element provided by the iterable to build.
	 * @return The built iterable.
	 * @since 2.0
	 */
	public static <E> ExIterable<E> fromElement(final E element) {
		return new ExIterable<E>() {
			@Override
			public ExIterator<E> iterator() {
				return Iterators.fromElement(element);
			}
		};
	}
	
	/**
	 * Builds an iterable over the given elements.
	 * 
	 * @param <E> Type of the the elements.
	 * @param elements Elements provided by the iterable to build.
	 * @return The built iterable.
	 * @since 2.0
	 */
	@SafeVarargs
	public static <E> ExIterable<E> fromElements(final E... elements) {
		assert null != elements;
		
		return new ExIterable<E>() {
			@Override
			public ExIterator<E> iterator() {
				return Iterators.fromElements(elements);
			}
		};
	}
	
	private Iterables() {
		// Prevent instantiation.
	}
}
