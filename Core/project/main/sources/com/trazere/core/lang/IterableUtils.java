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
import com.trazere.core.functional.Function3;
import com.trazere.core.functional.Predicate;
import com.trazere.core.functional.Predicate2;
import com.trazere.core.imperative.IteratorUtils;
import com.trazere.core.imperative.Procedure;
import com.trazere.core.imperative.Procedure2;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.Comparator;
import java.util.Iterator;

/**
 * The {@link IterableUtils} class provides various utilities regarding {@link Iterable iterables}.
 * 
 * @see Iterable
 */
public class IterableUtils {
	/**
	 * Gets an element provided by the given iterable.
	 *
	 * @param <E> Type of the elements.
	 * @param iterable Iterable providing the element to read.
	 * @return An element provided by the iterable, or nothing when the iterable is empty.
	 */
	public static <E> Maybe<E> any(final Iterable<? extends E> iterable) {
		return IteratorUtils.next(iterable.iterator());
	}
	
	/**
	 * Executes the given procedure with each element provided by the given iterable.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterable Iterable providing the elements.
	 * @param procedure Procedure to execute.
	 */
	public static <E> void foreach(final Iterable<? extends E> iterable, final Procedure<? super E> procedure) {
		IteratorUtils.foreach(iterable.iterator(), procedure);
	}
	
	/**
	 * Executes the given procedure with each pair of elements provided by the given iterable.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param iterable Iterable providing the pairs of elements.
	 * @param procedure Procedure to execute.
	 */
	public static <E1, E2> void foreach(final Iterable<? extends Tuple2<? extends E1, ? extends E2>> iterable, final Procedure2<? super E1, ? super E2> procedure) {
		IteratorUtils.foreach(iterable.iterator(), procedure);
	}
	
	/**
	 * Left folds over the elements provided by the given iterable using the given binary operator and initial state.
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
	 * Left folds over the pairs of elements provided by the given iterable using the given operator and initial state.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param <S> Type of the state.
	 * @param iterable Iterable providing the pairs of elements to fold over.
	 * @param operator Operator to use.
	 * @param initialState Initial state.
	 * @return The folded state.
	 */
	public static <E1, E2, S> S fold(final Iterable<? extends Tuple2<? extends E1, ? extends E2>> iterable, final Function3<? super S, ? super E1, ? super E2, ? extends S> operator, final S initialState) {
		return IteratorUtils.fold(iterable.iterator(), operator, initialState);
	}
	
	/**
	 * Gets the first element provided by the given iterable accepted by the given filter.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterable Iterable providing the elements to filter.
	 * @param filter Predicate to use to filter the elements.
	 * @return The first accepted element.
	 */
	public static <E> Maybe<E> first(final Iterable<? extends E> iterable, final Predicate<? super E> filter) {
		return IteratorUtils.first(iterable.iterator(), filter);
	}
	
	/**
	 * Gets the first pair of elements provided by the given iterable accepted by the given filter.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param iterable Iterable providing the pairs of elements to filter.
	 * @param filter Predicate to use to filter the pairs of elements.
	 * @return The first accepted pair of elements.
	 */
	public static <E1, E2> Maybe<Tuple2<E1, E2>> first(final Iterable<? extends Tuple2<? extends E1, ? extends E2>> iterable, final Predicate2<? super E1, ? super E2> filter) {
		return IteratorUtils.first(iterable.iterator(), filter);
	}
	
	/**
	 * Gets the first element extracted from the elements provided by the given iterable by the given extractor.
	 * 
	 * @param <E> Type of the elements.
	 * @param <EE> Type of the extracted elements.
	 * @param iterable Iterable providing the elements to extract from.
	 * @param extractor Function to use to extract the elements.
	 * @return The first extracted element.
	 */
	public static <E, EE> Maybe<? extends EE> extractFirst(final Iterable<? extends E> iterable, final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
		return IteratorUtils.extractFirst(iterable.iterator(), extractor);
	}
	
	/**
	 * Gets the first element extracted from the pairs of elements provided by the given iterable by the given extractor.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param <EE> Type of the extracted elements.
	 * @param iterable Iterable providing the pairs of elements to extract from.
	 * @param extractor Function to use to extract the elements.
	 * @return The first extracted element.
	 */
	public static <E1, E2, EE> Maybe<? extends EE> extractFirst(final Iterable<? extends Tuple2<? extends E1, ? extends E2>> iterable, final Function2<? super E1, ? super E2, ? extends Maybe<? extends EE>> extractor) {
		return IteratorUtils.extractFirst(iterable.iterator(), extractor);
	}
	
	/**
	 * Tests whether any element provided by the given iterable is accepted by the given filter.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterable Iterable providing the elements to test.
	 * @param filter Predicate to use to filter the elements.
	 * @return <code>true</code> when some element is accepted, <code>false</code> when all elements are rejected.
	 */
	public static <E> boolean isAny(final Iterable<? extends E> iterable, final Predicate<? super E> filter) {
		return IteratorUtils.isAny(iterable.iterator(), filter);
	}
	
	/**
	 * Tests whether any pair of elements provided by the given iterable is accepted by the given filter.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param iterable Iterable providing the pairs of elements to test.
	 * @param filter Predicate to use to filter the pairs of elements.
	 * @return <code>true</code> when some pair of elements is accepted, <code>false</code> when all pairs of elements are rejected.
	 */
	public static <E1, E2> boolean isAny(final Iterable<? extends Tuple2<? extends E1, ? extends E2>> iterable, final Predicate2<? super E1, ? super E2> filter) {
		return IteratorUtils.isAny(iterable.iterator(), filter);
	}
	
	/**
	 * Tests whether all elements provided by the given iterable are accepted by the given filter.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterable Iterable providing the elements to test.
	 * @param filter Predicate to use to filter the elements.
	 * @return <code>true</code> when all elements are accepted, <code>false</code> when some element is rejected.
	 */
	public static <E> boolean areAll(final Iterable<? extends E> iterable, final Predicate<? super E> filter) {
		return IteratorUtils.areAll(iterable.iterator(), filter);
	}
	
	/**
	 * Tests whether all pairs of elements provided by the given iterable are accepted by the given filter.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param iterable Iterable providing the pairs of elements to test.
	 * @param filter Predicate to use to filter the pairs of elements.
	 * @return <code>true</code> when all pairs of elements are accepted, <code>false</code> when some pair of elements is rejected.
	 */
	public static <E1, E2> boolean areAll(final Iterable<? extends Tuple2<? extends E1, ? extends E2>> iterable, final Predicate2<? super E1, ? super E2> filter) {
		return IteratorUtils.areAll(iterable.iterator(), filter);
	}
	
	/**
	 * Counts the elements provided by the given iterable accepted by the given filter.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterable Iterable providing the elements to count.
	 * @param filter Predicate to use to filter the elements.
	 * @return The number of accepted elements.
	 */
	public static <E> int count(final Iterable<? extends E> iterable, final Predicate<? super E> filter) {
		return IteratorUtils.count(iterable.iterator(), filter);
	}
	
	/**
	 * Counts the pairs of elements provided by the given iterable accepted by the given filter.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param iterable Iterable providing the pairs of elements to count.
	 * @param filter Predicate to use to filter the pairs of elements.
	 * @return The number of accepted pairs of elements.
	 */
	public static <E1, E2> int count(final Iterable<? extends Tuple2<? extends E1, ? extends E2>> iterable, final Predicate2<? super E1, ? super E2> filter) {
		return IteratorUtils.count(iterable.iterator(), filter);
	}
	
	/**
	 * Gets the least element provided by the given iterable.
	 *
	 * @param <E> Type of the elements.
	 * @param iterable Iterable providing the elements to compare.
	 * @return The least element.
	 */
	public static <E extends Comparable<E>> Maybe<E> least(final Iterable<? extends E> iterable) {
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
	public static <E> Maybe<E> least(final Iterable<? extends E> iterable, final Comparator<? super E> comparator) {
		return IteratorUtils.least(iterable.iterator(), comparator);
	}
	
	/**
	 * Gets the greatest element provided by the given iterable.
	 *
	 * @param <E> Type of the elements.
	 * @param iterable Iterable providing the elements to compare.
	 * @return The greatest element.
	 */
	public static <E extends Comparable<E>> Maybe<E> greatest(final Iterable<? extends E> iterable) {
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
	public static <E> Maybe<E> greatest(final Iterable<? extends E> iterable, final Comparator<? super E> comparator) {
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
	 * Filters the pairs of elements provided by the given iterable using the given filter.
	 *
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param iterable Iterable providing the pairs of elements to filter.
	 * @param filter Predicate to use to filter the pairs of elements.
	 * @return An iterable providing the filtered pairs of elements.
	 */
	public static <E1, E2> Iterable<Tuple2<? extends E1, ? extends E2>> filter(final Iterable<? extends Tuple2<? extends E1, ? extends E2>> iterable, final Predicate2<? super E1, ? super E2> filter) {
		assert null != iterable;
		assert null != filter;
		
		return new Iterable<Tuple2<? extends E1, ? extends E2>>() {
			@Override
			public Iterator<Tuple2<? extends E1, ? extends E2>> iterator() {
				return IteratorUtils.filter(iterable.iterator(), filter);
			}
		};
	}
	
	/**
	 * Transforms the elements provided by the given iterable using the given function.
	 *
	 * @param <E> Type of the elements.
	 * @param <TE> Type of the transformed elements.
	 * @param iterable Iterable providing the elements to transform.
	 * @param function Function to use to transform the elements.
	 * @return An iterable providing the transformed elements.
	 */
	public static <E, TE> Iterable<TE> map(final Iterable<? extends E> iterable, final Function<? super E, ? extends TE> function) {
		assert null != iterable;
		assert null != function;
		
		return new Iterable<TE>() {
			@Override
			public Iterator<TE> iterator() {
				return IteratorUtils.map(iterable.iterator(), function);
			}
		};
	}
	
	/**
	 * Transforms the pairs of elements provided by the given iterable using the given function.
	 *
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param <TE> Type of the transformed elements.
	 * @param iterable Iterable providing the pairs of elements to transform.
	 * @param function Function to use to transform the pairs of elements.
	 * @return An iterable providing the transformed elements.
	 */
	public static <E1, E2, TE> Iterable<TE> map(final Iterable<? extends Tuple2<? extends E1, ? extends E2>> iterable, final Function2<? super E1, ? super E2, ? extends TE> function) {
		assert null != iterable;
		assert null != function;
		
		return new Iterable<TE>() {
			@Override
			public Iterator<TE> iterator() {
				return IteratorUtils.map(iterable.iterator(), function);
			}
		};
	}
	
	/**
	 * Transforms and flattens the elements provided by the given iterable using the given function.
	 * 
	 * @param <E> Type of the elements.
	 * @param <TE> Type of the transformed elements.
	 * @param iterable Iterable providing the elements to transform.
	 * @param function Function to use to transform the elements.
	 * @return An iterable providing the flatten, transformed elements.
	 */
	public static <E, TE> Iterable<TE> flatMap(final Iterable<? extends E> iterable, final Function<? super E, ? extends Iterable<? extends TE>> function) {
		return flatten(map(iterable, function));
	}
	
	/**
	 * Transforms and flattens the pairs of elements provided by the given iterable using the given function.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param <TE> Type of the transformed elements.
	 * @param iterable Iterable providing the pairs of elements to transform.
	 * @param function Function to use to transform the pairs of elements.
	 * @return An iterable providing the flatten, transformed elements.
	 */
	public static <E1, E2, TE> Iterable<TE> flatMap(final Iterable<? extends Tuple2<? extends E1, ? extends E2>> iterable, final Function2<? super E1, ? super E2, ? extends Iterable<? extends TE>> function) {
		return flatten(map(iterable, function));
	}
	
	private IterableUtils() {
		// Prevent instantiation.
	}
}
