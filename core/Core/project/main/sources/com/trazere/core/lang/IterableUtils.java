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

import com.trazere.core.functional.Function;
import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Functions;
import com.trazere.core.functional.Predicate;
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
	 * @param iterable Iterable providing the element to read.
	 * @return Some value if any.
	 */
	public static <E> Maybe<E> any(final Iterable<? extends E> iterable) {
		return IteratorUtils.next(iterable.iterator());
	}
	
	/**
	 * Left folds over the elements provided by the given iterable using the given binary operator and initial value.
	 * 
	 * @param <E> Type of the elements.
	 * @param <S> Type of the state.
	 * @param iterable Iterable providing the elements to fold over.
	 * @param operator Binary operator to use.
	 * @param initialState Initial state.
	 * @return The folded state.
	 */
	public static <E, S> S fold(final Iterable<? extends E> iterable, final Function2<? super S, ? super E, ? extends S> operator, final S initialState) {
		return IteratorUtils.fold(iterable.iterator(), operator, initialState);
	}
	
	/**
	 * Gets the first element provided by the given iterable accepted by the given filter.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterable Iterable providing the elements to filter.
	 * @param filter Filter predicate.
	 * @return The first accepted element.
	 */
	public static <E> Maybe<E> first(final Iterable<? extends E> iterable, final Predicate<? super E> filter) {
		return IteratorUtils.first(iterable.iterator(), filter);
	}
	
	/**
	 * Gets the first element extracted from the elements provided by the given iterable by the given extractor.
	 * 
	 * @param <E> Type of the elements.
	 * @param <RE> Type of the extracted elements.
	 * @param iterable Iterable providing the elements to extract from.
	 * @param extractor Extractor function.
	 * @return The first extracted element.
	 */
	public static <E, RE> Maybe<? extends RE> first(final Iterable<? extends E> iterable, final Function<? super E, ? extends Maybe<? extends RE>> extractor) {
		return IteratorUtils.first(iterable.iterator(), extractor);
	}
	
	/**
	 * Tests whether any element provided by the given iterable is accepted by the given filter.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterable Iterable providing the elements to test.
	 * @param filter Filter predicate.
	 * @return <code>true</code> when some element is accepted, <code>false</code> when all elements are rejected.
	 */
	public static <E> boolean isAny(final Iterable<? extends E> iterable, final Predicate<? super E> filter) {
		return IteratorUtils.isAny(iterable.iterator(), filter);
	}
	
	/**
	 * Tests whether all elements provided by the given iterable are accepted by the given filter.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterable Iterable providing the elements to test.
	 * @param filter Filter predicate.
	 * @return <code>true</code> when all values are accepted, <code>false</code> when some value is rejected.
	 */
	public static <E> boolean areAll(final Iterable<? extends E> iterable, final Predicate<? super E> filter) {
		return IteratorUtils.areAll(iterable.iterator(), filter);
	}
	
	/**
	 * Counts the elements provided by the given iterable accepted by the given filter.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterable Iterable providing the elements to count.
	 * @param filter Filter predicate.
	 * @return The number of accepted elements.
	 */
	public static <E> int count(final Iterable<? extends E> iterable, final Predicate<? super E> filter) {
		return IteratorUtils.count(iterable.iterator(), filter);
	}
	
	/**
	 * Gets the least element provided by the given iterable.
	 *
	 * @param <E> Type of the elements.
	 * @param iterable Iterable providing the elements to compare.
	 * @return The least element.
	 */
	public static <E extends Comparable<E>> Maybe<? extends E> least(final Iterable<? extends E> iterable) {
		return IteratorUtils.least(iterable.iterator());
	}
	
	/**
	 * Gets the least element provided by the given iterable according to the given comparator.
	 *
	 * @param <E> Type of the elements.
	 * @param iterable Iterable providing the elements to compare.
	 * @param comparator Comparator to use.
	 * @return The least element.
	 */
	public static <E> Maybe<? extends E> least(final Iterable<? extends E> iterable, final Comparator<? super E> comparator) {
		return IteratorUtils.least(iterable.iterator(), comparator);
	}
	
	/**
	 * Gets the greatest element provided by the given iterable.
	 *
	 * @param <E> Type of the elements.
	 * @param iterable Iterable providing the elements to compare.
	 * @return The greatest element.
	 */
	public static <E extends Comparable<E>> Maybe<? extends E> greatest(final Iterable<? extends E> iterable) {
		return IteratorUtils.greatest(iterable.iterator());
	}
	
	/**
	 * Gets the greatest element provided by the given iterable according to the given comparator.
	 *
	 * @param <E> Type of the elements.
	 * @param iterable Iterable providing the elements to compare.
	 * @param comparator Comparator to use.
	 * @return The greatest element.
	 */
	public static <E> Maybe<? extends E> greatest(final Iterable<? extends E> iterable, final Comparator<? super E> comparator) {
		return IteratorUtils.greatest(iterable.iterator(), comparator);
	}
	
	/**
	 * Appends the given iterables together.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterable1 First iterable providing the elements to append.
	 * @param iterable2 Second iterable providing the elements to append.
	 * @return An iterable providing the appended elements.
	 */
	public static <E> Iterable<E> append(final Iterable<? extends E> iterable1, final Iterable<? extends E> iterable2) {
		assert null != iterable1;
		assert null != iterable2;
		
		return new Iterable<E>() {
			@Override
			public Iterator<E> iterator() {
				return IteratorUtils.append(iterable1.iterator(), iterable2.iterator());
			}
		};
	}
	
	/**
	 * Flattens the elements provided by the iterables provided by the given iterable.
	 *
	 * @param <E> Type of the elements.
	 * @param iterable Iterable providing the iterables providing the elements to flatten.
	 * @return An iterable providing the flatten elements.
	 */
	public static <E> Iterable<E> flatten(final Iterable<? extends Iterable<? extends E>> iterable) {
		assert null != iterable;
		
		return new Iterable<E>() {
			@Override
			public Iterator<E> iterator() {
				return IteratorUtils.flatten(IteratorUtils.map(iterable.iterator(), IterableFunctions.iterator()));
			}
		};
	}
	
	/**
	 * Takes the n first elements provided by the given iterable.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterable Iterable providing the elements to take.
	 * @param n Number of elements to take.
	 * @return An iterable providing the taken elements.
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
	 * Drops the n first elements provided by the given iterable.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterable Iterable providing the elements to drop.
	 * @param n Number of elements to drop.
	 * @return An iterable providing the remaining elements.
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
	
	/**
	 * Filters the elements provided by the given iterable using the given filter.
	 *
	 * @param <E> Type of the elements.
	 * @param iterable Iterable providing the elements to filter.
	 * @param filter Predicate to use to filter the elements.
	 * @return An iterable providing the filtered elements.
	 */
	public static <E> Iterable<E> filter(final Iterable<? extends E> iterable, final Predicate<? super E> filter) {
		assert null != iterable;
		assert null != filter;
		
		return new Iterable<E>() {
			@Override
			public Iterator<E> iterator() {
				return IteratorUtils.filter(iterable.iterator(), filter);
			}
		};
	}
	
	/**
	 * Transforms the elements provided by the given iterable using the given function.
	 *
	 * @param <E> Type of the elements.
	 * @param <RE> Type of the transformed elements.
	 * @param iterable Iterable providing the elements to transform.
	 * @param function Function to use to transform the elements.
	 * @return An iterable providing the transformed elements.
	 */
	public static <E, RE> Iterable<RE> map(final Iterable<? extends E> iterable, final Function<? super E, ? extends RE> function) {
		assert null != iterable;
		assert null != function;
		
		return new Iterable<RE>() {
			@Override
			public Iterator<RE> iterator() {
				return IteratorUtils.map(iterable.iterator(), function);
			}
		};
	}
	
	/**
	 * Transforms and flatten the elements provided by the given iterable using the given function.
	 * 
	 * @param <E> Type of the elements.
	 * @param <RE> Type of the transformed elements.
	 * @param iterable Iterable providing the elements to transform.
	 * @param function Function to use to transform the elements.
	 * @return An iterable providing the flatten, transformed elements.
	 */
	public static <E, RE> Iterable<RE> flatMap(final Iterable<? extends E> iterable, final Function<? super E, ? extends Iterable<? extends RE>> function) {
		assert null != iterable;
		assert null != function;
		
		return new Iterable<RE>() {
			@Override
			public Iterator<RE> iterator() {
				return IteratorUtils.flatMap(iterable.iterator(), Functions.compose(IterableFunctions.iterator(), function));
			}
		};
	}
	
	/**
	 * Extracts from the elements provided by the given iterable using the given extractor.
	 *
	 * @param <E> Type of the elements.
	 * @param <RE> Type of the extracted elements.
	 * @param iterable Iterable providing the elements to extract from.
	 * @param extractor Function to use to extract the elements.
	 * @return An iterable providing the extracted elements.
	 */
	public static <E, RE> Iterable<RE> extract(final Iterable<? extends E> iterable, final Function<? super E, ? extends Maybe<? extends RE>> extractor) {
		assert null != iterable;
		assert null != extractor;
		
		return new Iterable<RE>() {
			@Override
			public Iterator<RE> iterator() {
				return IteratorUtils.extract(iterable.iterator(), extractor);
			}
		};
	}
	
	private IterableUtils() {
		// Prevent instantiation.
	}
}
