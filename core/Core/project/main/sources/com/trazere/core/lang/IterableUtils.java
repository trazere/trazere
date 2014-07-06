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

import com.trazere.core.imperative.IteratorUtils;
import com.trazere.core.util.Maybe;
import java.util.Comparator;
import java.util.Iterator;

/**
 * The {@link IterableUtils} class provides various helpers regarding {@link Iterable iterables}.
 * 
 * @see Iterable
 */
public class IterableUtils {
	/**
	 * Gets an element from the given iterable.
	 *
	 * @param <E> Type of the elements.
	 * @param iterable Iterable to read.
	 * @return Some value if any.
	 */
	public static <E> Maybe<E> any(final Iterable<? extends E> iterable) {
		return IteratorUtils.next(iterable.iterator());
	}
	
	/**
	 * Gets the least element of the given iterable.
	 *
	 * @param <E> Type of the elements.
	 * @param iterable Iterable to read.
	 * @return The least element.
	 */
	public static <E extends Comparable<E>> Maybe<? extends E> least(final Iterable<? extends E> iterable) {
		return IteratorUtils.least(iterable.iterator());
	}
	
	/**
	 * Gets the least element of the given iterable according to the given comparator.
	 *
	 * @param <E> Type of the elements.
	 * @param iterable Iterable to read.
	 * @param comparator Comparator to use.
	 * @return The least element.
	 */
	public static <E> Maybe<? extends E> least(final Iterable<? extends E> iterable, final Comparator<? super E> comparator) {
		return IteratorUtils.least(iterable.iterator(), comparator);
	}
	
	/**
	 * Gets the greatest element of the given iterable.
	 *
	 * @param <E> Type of the elements.
	 * @param iterable Iterable to read.
	 * @return The greatest element.
	 */
	public static <E extends Comparable<E>> Maybe<? extends E> greatest(final Iterable<? extends E> iterable) {
		return IteratorUtils.greatest(iterable.iterator());
	}
	
	/**
	 * Gets the greatest element of the iterable according to the given comparator.
	 *
	 * @param <E> Type of the elements.
	 * @param iterable Iterable to read.
	 * @param comparator Comparator to use.
	 * @return The greatest element.
	 */
	public static <E> Maybe<? extends E> greatest(final Iterable<? extends E> iterable, final Comparator<? super E> comparator) {
		return IteratorUtils.greatest(iterable.iterator(), comparator);
	}
	
	/**
	 * Takes the n first elements of the given iterable.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterable Iterable to read.
	 * @param n Number of elements to take.
	 * @return An iterable of the taken elements.
	 */
	public static <E> Iterable<E> take(final Iterable<? extends E> iterable, final int n) {
		assert null != iterable;
		
		return new Iterable<E>() {
			@Override
			public Iterator<E> iterator() {
				return IteratorUtils.take(iterable.iterator(), n);
			}
		};
	}
	
	/**
	 * Drops the n first elements of the given iterable.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterable Iterable to read.
	 * @param n Number of elements to drop.
	 * @return An iterable of the remaining elements.
	 */
	public static <E> Iterable<E> drop(final Iterable<? extends E> iterable, final int n) {
		assert null != iterable;
		
		return new Iterable<E>() {
			@Override
			public Iterator<E> iterator() {
				return IteratorUtils.drop(iterable.iterator(), n);
			}
		};
	}
	
	// TODO: fold
	
	// TODO: filter
	// TODO: map
	// TODO: flatMap
	// TODO: extract
	
	private IterableUtils() {
		// Prevent instantiation.
	}
}
