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

import com.trazere.core.collection.CollectionFactory;
import com.trazere.core.collection.IterableDecorator;
import com.trazere.core.functional.Function;
import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Function3;
import com.trazere.core.functional.Predicate;
import com.trazere.core.functional.Predicate2;
import com.trazere.core.imperative.ExIterator;
import com.trazere.core.imperative.IteratorUtils;
import com.trazere.core.imperative.PairIterator;
import com.trazere.core.imperative.Procedure;
import com.trazere.core.imperative.Procedure2;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * The {@link IterableUtils} class provides various utilities regarding {@link Iterable iterables}.
 * 
 * @see Iterable
 * @since 2.0
 */
public class IterableUtils {
	/**
	 * Gets any element provided by the given iterable.
	 *
	 * @param <E> Type of the elements.
	 * @param iterable Iterable providing the element to read.
	 * @return An element provided by the iterable.
	 * @throws NoSuchElementException When the iterable is empty.
	 * @since 2.0
	 */
	public static <E> E any(final Iterable<? extends E> iterable)
	throws NoSuchElementException {
		return iterable.iterator().next();
	}
	
	/**
	 * Gets any element provided by the given iterable.
	 * <p>
	 * This methods support empty iterables.
	 *
	 * @param <E> Type of the elements.
	 * @param iterable Iterable providing the element to read.
	 * @return An element provided by the iterable, or nothing when the iterable is empty.
	 * @since 2.0
	 */
	public static <E> Maybe<E> optionalAny(final Iterable<? extends E> iterable) {
		return IteratorUtils.optionalNext(iterable.iterator());
	}
	
	// TODO: drain equivalent
	
	/**
	 * Left folds over the elements provided by the given iterable using the given binary operator and initial state.
	 * 
	 * @param <E> Type of the elements.
	 * @param <S> Type of the state.
	 * @param iterable Iterable providing the elements to fold over.
	 * @param operator Binary operator to use.
	 * @param initialState Initial state.
	 * @return The folded state.
	 * @since 2.0
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
	 * @since 2.0
	 */
	public static <E1, E2, S> S fold(final Iterable<? extends Tuple2<? extends E1, ? extends E2>> iterable, final Function3<? super S, ? super E1, ? super E2, ? extends S> operator, final S initialState) {
		return IteratorUtils.fold(iterable.iterator(), operator, initialState);
	}
	
	/**
	 * Tests whether any element provided by the given iterable is accepted by the given filter.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterable Iterable providing the elements to test.
	 * @param filter Predicate to use to filter the elements.
	 * @return <code>true</code> when some element is accepted, <code>false</code> when all elements are rejected or when the iterable is empty.
	 * @since 2.0
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
	 * @return <code>true</code> when some pair of elements is accepted, <code>false</code> when all pairs of elements are rejected or when the iterable is
	 *         empty.
	 * @since 2.0
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
	 * @return <code>true</code> when all elements are accepted or when the iterable is empty, <code>false</code> when some element is rejected.
	 * @since 2.0
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
	 * @return <code>true</code> when all pairs of elements are accepted or when the iterable is empty, <code>false</code> when some pair of elements is
	 *         rejected.
	 * @since 2.0
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
	 * @since 2.0
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
	 * @since 2.0
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
	 * @since 2.0
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
	 * @since 2.0
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
	 * @since 2.0
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
	 * @since 2.0
	 */
	public static <E> Maybe<E> greatest(final Iterable<? extends E> iterable, final Comparator<? super E> comparator) {
		return IteratorUtils.greatest(iterable.iterator(), comparator);
	}
	
	/**
	 * Takes n elements provided by the given iterable.
	 * <p>
	 * The elements are taken according their iteration order.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterable Iterable providing the elements to take.
	 * @param n Number of elements to take.
	 * @return An iterable providing the taken elements.
	 * @since 2.0
	 */
	public static <E> ExIterable<E> take(final Iterable<? extends E> iterable, final int n) {
		assert null != iterable;
		
		return () -> IteratorUtils.take(iterable.iterator(), n);
	}
	
	/**
	 * Takes n pairs of elements provided by the given iterable.
	 * <p>
	 * The pairs of elements are taken according their iteration order.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param iterable Iterable providing the pairs of elements to take.
	 * @param n Number of pairs of elements to take.
	 * @return An iterable providing the taken pairs of elements.
	 * @since 2.0
	 */
	public static <E1, E2> PairIterable<E1, E2> take2(final Iterable<? extends Tuple2<E1, E2>> iterable, final int n) {
		assert null != iterable;
		
		// HACK: not a lambda to work around a bug of Eclipse
		//		return () -> IteratorUtils.take2(iterable.iterator(), n);
		return new PairIterable<E1, E2>() {
			@Override
			public PairIterator<E1, E2> iterator() {
				return IteratorUtils.take2(iterable.iterator(), n);
			}
		};
	}
	
	/**
	 * Drops n elements provided by the given iterable.
	 * <p>
	 * The elements are dropped according their iteration order.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterable Iterable providing the elements to drop.
	 * @param n Number of elements to drop.
	 * @return An iterable providing the remaining elements.
	 * @since 2.0
	 */
	public static <E> ExIterable<E> drop(final Iterable<? extends E> iterable, final int n) {
		assert null != iterable;
		
		return () -> IteratorUtils.drop(iterable.iterator(), n);
	}
	
	/**
	 * Drops n pairs of elements provided by the given iterable.
	 * <p>
	 * The pairs of elements are dropped according their iteration order.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param iterable Iterable providing the pairs of elements to drop.
	 * @param n Number of pairs of elements to drop.
	 * @return An iterable providing the remaining pairs of elements.
	 * @since 2.0
	 */
	public static <E1, E2> PairIterable<E1, E2> drop2(final Iterable<? extends Tuple2<E1, E2>> iterable, final int n) {
		assert null != iterable;
		
		// HACK: not a lambda to work around a bug of Eclipse
		//		return () -> IteratorUtils.drop(iterable.iterator(), n);
		return new PairIterable<E1, E2>() {
			@Override
			public PairIterator<E1, E2> iterator() {
				return IteratorUtils.drop2(iterable.iterator(), n);
			}
		};
	}
	
	/**
	 * Groups the elements provided by the given iterable into batches of the given size.
	 *
	 * @param <E> Type of the elements.
	 * @param <B> Type of the batch collections.
	 * @param iterable Iterable providing the elements to group.
	 * @param n Number of elements of each batch.
	 * @param batchFactory Factory of the batch collections.
	 * @return An iterable providing the batches of elements.
	 * @since 2.0
	 */
	public static <E, B extends Collection<? super E>> ExIterable<B> group(final Iterable<? extends E> iterable, final int n, final CollectionFactory<? super E, B> batchFactory) {
		assert null != iterable;
		assert null != batchFactory;
		
		return () -> IteratorUtils.group(iterable.iterator(), n, batchFactory);
	}
	
	/**
	 * Filters the elements provided by the given iterable using the given filter.
	 *
	 * @param <E> Type of the elements.
	 * @param iterable Iterable providing the elements to filter.
	 * @param filter Predicate to use to filter the elements.
	 * @return An iterable providing the filtered elements.
	 * @since 2.0
	 */
	public static <E> ExIterable<E> filter(final Iterable<? extends E> iterable, final Predicate<? super E> filter) {
		assert null != iterable;
		assert null != filter;
		
		return () -> IteratorUtils.filter(iterable.iterator(), filter);
	}
	
	/**
	 * Filters the pairs of elements provided by the given iterable using the given filter.
	 *
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param iterable Iterable providing the pairs of elements to filter.
	 * @param filter Predicate to use to filter the pairs of elements.
	 * @return An iterable providing the filtered pairs of elements.
	 * @since 2.0
	 */
	public static <E1, E2> PairIterable<E1, E2> filter(final Iterable<? extends Tuple2<? extends E1, ? extends E2>> iterable, final Predicate2<? super E1, ? super E2> filter) {
		assert null != iterable;
		assert null != filter;
		
		// HACK: not a lambda to work around a bug of Eclipse
		//		return () -> IteratorUtils.filter(iterable.iterator(), filter);
		return new PairIterable<E1, E2>() {
			@Override
			public PairIterator<E1, E2> iterator() {
				return IteratorUtils.filter(iterable.iterator(), filter);
			}
		};
	}
	
	/**
	 * Gets any element provided by the given iterable accepted by the given filter.
	 * <p>
	 * The elements are filtered according to their iteration order.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterable Iterable providing the elements to filter.
	 * @param filter Predicate to use to filter the elements.
	 * @return The accepted element, or nothing when no elements are accepted by the filter.
	 * @since 2.0
	 */
	public static <E> Maybe<E> filterAny(final Iterable<? extends E> iterable, final Predicate<? super E> filter) {
		return IteratorUtils.filterAny(iterable.iterator(), filter);
	}
	
	/**
	 * Gets any pair of elements provided by the given iterable accepted by the given filter.
	 * <p>
	 * The pairs of elements are filtered according to their iteration order.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param iterable Iterable providing the pairs of elements to filter.
	 * @param filter Predicate to use to filter the pairs of elements.
	 * @return The accepted pair of elements, or nothing when no pairs of elements are accepted by the filter.
	 * @since 2.0
	 */
	public static <E1, E2> Maybe<Tuple2<E1, E2>> filterAny(final Iterable<? extends Tuple2<? extends E1, ? extends E2>> iterable, final Predicate2<? super E1, ? super E2> filter) {
		return IteratorUtils.filterAny(iterable.iterator(), filter);
	}
	
	/**
	 * Transforms the elements provided by the given iterable using the given function.
	 *
	 * @param <E> Type of the elements.
	 * @param <TE> Type of the transformed elements.
	 * @param iterable Iterable providing the elements to transform.
	 * @param function Function to use to transform the elements.
	 * @return An iterable providing the transformed elements.
	 * @since 2.0
	 */
	public static <E, TE> ExIterable<TE> map(final Iterable<? extends E> iterable, final Function<? super E, ? extends TE> function) {
		assert null != iterable;
		assert null != function;
		
		return () -> IteratorUtils.map(iterable.iterator(), function);
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
	 * @since 2.0
	 */
	public static <E1, E2, TE> ExIterable<TE> map(final Iterable<? extends Tuple2<? extends E1, ? extends E2>> iterable, final Function2<? super E1, ? super E2, ? extends TE> function) {
		assert null != iterable;
		assert null != function;
		
		return () -> IteratorUtils.map(iterable.iterator(), function);
	}
	
	/**
	 * Extracts the elements from the elements provided by the given iterable using the given extractor.
	 *
	 * @param <E> Type of the elements.
	 * @param <EE> Type of the extracted elements.
	 * @param iterable Iterable providing the elements to extract from.
	 * @param extractor Function to use to extract from the elements.
	 * @return An iterable providing the extracted elements.
	 * @since 2.0
	 */
	public static <E, EE> ExIterable<EE> extract(final Iterable<? extends E> iterable, final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
		assert null != iterable;
		assert null != extractor;
		
		return () -> IteratorUtils.extract(iterable.iterator(), extractor);
	}
	
	/**
	 * Extracts the elements from the the pairs of elements provided by the given iterable using the given extractor.
	 *
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param <EE> Type of the extracted elements.
	 * @param iterable Iterable providing the pairs of elements to extract from.
	 * @param extractor Function to use to extract from the pairs of elements.
	 * @return An iterable providing the extracted elements.
	 * @since 2.0
	 */
	public static <E1, E2, EE> ExIterable<EE> extract(final Iterable<? extends Tuple2<? extends E1, ? extends E2>> iterable, final Function2<? super E1, ? super E2, ? extends Maybe<? extends EE>> extractor) {
		assert null != iterable;
		assert null != extractor;
		
		return () -> IteratorUtils.extract(iterable.iterator(), extractor);
	}
	
	/**
	 * Gets the element extracted from any element provided by the given iterable using the given extractor.
	 * <p>
	 * The elements are extracted from according to their iteration order.
	 * 
	 * @param <E> Type of the elements.
	 * @param <EE> Type of the extracted elements.
	 * @param iterable Iterable providing the elements to extract from.
	 * @param extractor Function to use to extract from the elements.
	 * @return The extracted element, or nothing when no elements can be extracted from any element.
	 * @since 2.0
	 */
	public static <E, EE> Maybe<EE> extractAny(final Iterable<? extends E> iterable, final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
		return IteratorUtils.extractAny(iterable.iterator(), extractor);
	}
	
	/**
	 * Gets the element extracted from any pair of elements provided by the given iterable by the given extractor.
	 * <p>
	 * The pairs of elements are extracted from according to their iteration order.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param <EE> Type of the extracted elements.
	 * @param iterable Iterable providing the pairs of elements to extract from.
	 * @param extractor Function to use to extract from the pairs of elements.
	 * @return The extracted element, or nothing when no elements can be extracted from any pair of elements.
	 * @since 2.0
	 */
	public static <E1, E2, EE> Maybe<EE> extractAny(final Iterable<? extends Tuple2<? extends E1, ? extends E2>> iterable, final Function2<? super E1, ? super E2, ? extends Maybe<? extends EE>> extractor) {
		return IteratorUtils.extractAny(iterable.iterator(), extractor);
	}
	
	/**
	 * Gets all elements extracted from the elements provided by the given iterable using the given extractor.
	 *
	 * @param <E> Type of the elements.
	 * @param <TE> Type of the extracted elements.
	 * @param iterable Iterable providing the elements to extract from.
	 * @param extractor Function to use to extract from the elements.
	 * @return An iterable providing the extracted elements.
	 * @since 2.0
	 */
	public static <E, TE> ExIterable<TE> extractAll(final Iterable<? extends E> iterable, final Function<? super E, ? extends Iterable<? extends TE>> extractor) {
		assert null != iterable;
		assert null != extractor;
		
		return () -> IteratorUtils.extractAll(iterable.iterator(), extractor);
	}
	
	/**
	 * Gets all elements extracted from the pairs of elements provided by the given iterable using the given extractor.
	 *
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param <EE> Type of the extracted elements.
	 * @param iterable Iterable providing the pairs of elements to extract from.
	 * @param extractor Function to use to extract from the pairs of elements.
	 * @return An iterable providing the extracted elements.
	 * @since 2.0
	 */
	public static <E1, E2, EE> ExIterable<EE> extractAll(final Iterable<? extends Tuple2<? extends E1, ? extends E2>> iterable, final Function2<? super E1, ? super E2, ? extends Iterable<? extends EE>> extractor) {
		assert null != iterable;
		assert null != extractor;
		
		return () -> IteratorUtils.extractAll(iterable.iterator(), extractor);
	}
	
	/**
	 * Appends the given iterables together.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterable1 First iterable providing the elements to append.
	 * @param iterable2 Second iterable providing the elements to append.
	 * @return An iterable providing the appended elements.
	 * @since 2.0
	 */
	public static <E> ExIterable<E> append(final Iterable<? extends E> iterable1, final Iterable<? extends E> iterable2) {
		assert null != iterable1;
		assert null != iterable2;
		
		return () -> IteratorUtils.append(iterable1.iterator(), iterable2.iterator());
	}
	
	/**
	 * Flattens the elements provided by the iterables provided by the given iterable.
	 *
	 * @param <E> Type of the elements.
	 * @param iterable Iterable providing the iterables providing the elements to flatten.
	 * @return An iterable providing the flatten elements.
	 * @since 2.0
	 */
	public static <E> ExIterable<E> flatten(final Iterable<? extends Iterable<? extends E>> iterable) {
		assert null != iterable;
		
		return () -> IteratorUtils.flatten(IteratorUtils.map(iterable.iterator(), IterableFunctions.iterator()));
	}
	
	/**
	 * Transforms and flattens the elements provided by the given iterable using the given function.
	 * 
	 * @param <E> Type of the elements.
	 * @param <TE> Type of the transformed elements.
	 * @param iterable Iterable providing the elements to transform.
	 * @param function Function to use to transform the elements.
	 * @return An iterable providing the flatten, transformed elements.
	 * @since 2.0
	 */
	public static <E, TE> ExIterable<TE> flatMap(final Iterable<? extends E> iterable, final Function<? super E, ? extends Iterable<? extends TE>> function) {
		assert null != iterable;
		assert null != function;
		
		return () -> IteratorUtils.flatMap(iterable.iterator(), element -> function.evaluate(element).iterator());
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
	 * @since 2.0
	 */
	public static <E1, E2, TE> ExIterable<TE> flatMap(final Iterable<? extends Tuple2<? extends E1, ? extends E2>> iterable, final Function2<? super E1, ? super E2, ? extends Iterable<? extends TE>> function) {
		assert null != iterable;
		assert null != function;
		
		return () -> IteratorUtils.flatMap(iterable.iterator(), (element1, element2) -> function.evaluate(element1, element2).iterator());
	}
	
	/**
	 * Composes pairs with the elements provided by the given iterables.
	 * <p>
	 * The pairs are composed of an element provided by each iterable in order. The extra values of the longest iterable are dropped when the given iterables
	 * don't provide the same number of elements.
	 * 
	 * @param <E1> Type of the first elements.
	 * @param <E2> Type of the second elements.
	 * @param iterable1 Iterable providing the first elements of the pairs.
	 * @param iterable2 Iterable providing the second elements of the pairs.
	 * @return An iterable providing the pairs of elements.
	 * @since 2.0
	 */
	public static <E1, E2> PairIterable<E1, E2> zip(final Iterable<? extends E1> iterable1, final Iterable<? extends E2> iterable2) {
		assert null != iterable1;
		assert null != iterable2;
		
		// HACK: not a lambda to work around a bug of Eclipse
		//		return () -> IteratorUtils.zip(iterable1.iterator(), iterable2.iterator());
		return new PairIterable<E1, E2>() {
			@Override
			public PairIterator<E1, E2> iterator() {
				return IteratorUtils.zip(iterable1.iterator(), iterable2.iterator());
			}
		};
	}
	
	/**
	 * Executes the given procedure with each element provided by the given iterable.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterable Iterable providing the elements.
	 * @param procedure Procedure to execute.
	 * @since 2.0
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
	 * @since 2.0
	 */
	public static <E1, E2> void foreach(final Iterable<? extends Tuple2<? extends E1, ? extends E2>> iterable, final Procedure2<? super E1, ? super E2> procedure) {
		IteratorUtils.foreach(iterable.iterator(), procedure);
	}
	
	/**
	 * Builds an unmodifiable view of the given iterable.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterable Iterable to wrap.
	 * @return An unmodifiable view of the given iterable, or the given iterable when is it already unmodifiable.
	 * @since 2.0
	 */
	public static <E> ExIterable<E> unmodifiable(final Iterable<E> iterable) {
		assert null != iterable;
		
		return iterable instanceof UnmodifiableIterable ? (UnmodifiableIterable<E>) iterable : new UnmodifiableIterable<>(iterable);
	}
	
	private static class UnmodifiableIterable<E>
	extends IterableDecorator<E> {
		public UnmodifiableIterable(final Iterable<E> decorated) {
			super(decorated);
		}
		
		// Iterable.
		
		@Override
		public ExIterator<E> iterator() {
			return IteratorUtils.unmodifiable(ExIterator.build(_decorated.iterator()));
		}
		
		// Object.
		
		@Override
		public int hashCode() {
			return _decorated.hashCode();
		}
		
		@Override
		public boolean equals(final Object o) {
			return _decorated.equals(o);
		}
	}
	
	/**
	 * Builds an unmodifiable view of the given iterable of pairs of elements.
	 * 
	 * @param <E1> Type of the first element of the pairs.
	 * @param <E2> Type of the second element of the pairs.
	 * @param iterable Iterable to wrap.
	 * @return An unmodifiable view of the given iterable, or the given iterable when is it already unmodifiable.
	 * @since 2.0
	 */
	public static <E1, E2> PairIterable<E1, E2> unmodifiable(final PairIterable<E1, E2> iterable) {
		assert null != iterable;
		
		return iterable instanceof UnmodifiablePairIterable ? (UnmodifiablePairIterable<E1, E2>) iterable : new UnmodifiablePairIterable<>(iterable);
	}
	
	private static class UnmodifiablePairIterable<E1, E2>
	extends UnmodifiableIterable<Tuple2<E1, E2>>
	implements PairIterable<E1, E2> {
		public UnmodifiablePairIterable(final Iterable<Tuple2<E1, E2>> decorated) {
			super(decorated);
		}
		
		// Iterable.
		
		@Override
		public PairIterator<E1, E2> iterator() {
			return IteratorUtils.unmodifiable(PairIterator.build(_decorated.iterator()));
		}
	}
	
	private IterableUtils() {
		// Prevent instantiation.
	}
}
