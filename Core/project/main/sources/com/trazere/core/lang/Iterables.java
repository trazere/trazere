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
package com.trazere.core.lang;

import com.trazere.core.imperative.Iterators;
import java.util.Iterator;

/**
 * The {@link Iterables} class provides various factories of {@link Iterable iterables}.
 * 
 * @see Iterable
 */
public class Iterables {
	/**
	 * Builds an empty iterable.
	 * 
	 * @param <E> Type of the elements.
	 * @return The built iterable.
	 */
	@SuppressWarnings("unchecked")
	public static <E> Iterable<E> empty() {
		return (Iterable<E>) EMPTY;
	}
	
	private static final Iterable<?> EMPTY = new Iterable<Object>() {
		@Override
		public Iterator<Object> iterator() {
			return Iterators.empty();
		}
	};
	
	/**
	 * Builds an iterable over the given element.
	 * 
	 * @param <E> Type of the the element.
	 * @param element Element provided by the iterable to build.
	 * @return The built iterable.
	 */
	public static <E> Iterable<E> fromElement(final E element) {
		return new Iterable<E>() {
			@Override
			public Iterator<E> iterator() {
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
	 */
	@SafeVarargs
	public static <E> Iterable<E> fromElements(final E... elements) {
		assert null != elements;
		
		return new Iterable<E>() {
			@Override
			public Iterator<E> iterator() {
				return Iterators.fromElements(elements);
			}
		};
	}
	
	private Iterables() {
		// Prevent instantiation.
	}
}
