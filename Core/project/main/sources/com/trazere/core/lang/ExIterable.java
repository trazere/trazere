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
import com.trazere.core.functional.Function;
import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Predicate;
import com.trazere.core.imperative.ExIterator;
import com.trazere.core.imperative.Procedure;
import com.trazere.core.util.Maybe;
import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * The {@link ExIterable} interface defines extended {@link Iterable iterables}.
 * 
 * @param <E> Type of the elements.
 * @see Iterable
 * @since 2.0
 */
@FunctionalInterface
public interface ExIterable<E>
extends Iterable<E>, Traversable<E> {
	// TODO: move to Iterables
	// TODO: rename -> fromIterable ?
	/**
	 * Builds an extended view of the given iterable.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterable Iterable to wrap.
	 * @return The extended view of the given iterable, or the given iterable when it is already an extended view.
	 * @since 2.0
	 */
	public static <E> ExIterable<E> build(final Iterable<E> iterable) {
		assert null != iterable;
		
		if (iterable instanceof ExIterable) {
			return (ExIterable<E>) iterable;
		} else {
			return new IterableDecorator<>(iterable);
		}
	}
	
	// Iterable.
	
	/**
	 * @since 2.0
	 */
	@Override
	ExIterator<E> iterator();
	
	/**
	 * Gets any element provided by this iterable.
	 *
	 * @return An element provided by the iterable..
	 * @throws NoSuchElementException When the iterable is empty.
	 * @see IterableUtils#any(Iterable)
	 * @since 2.0
	 */
	default E any()
	throws NoSuchElementException {
		return IterableUtils.any(this);
	}
	
	/**
	 * Gets any element provided by this iterable.
	 *
	 * @return An element provided by the iterable, or nothing when the iterable is empty.
	 * @see IterableUtils#optionalAny(Iterable)
	 * @since 2.0
	 */
	default Maybe<E> optionalAny() {
		return IterableUtils.optionalAny(this);
	}
	
	// Traversable.
	
	/**
	 * Left folds over the elements provided by this iterable using the given binary operator and initial state.
	 * <p>
	 * The elements are folded according their iteration order.
	 * 
	 * @see IterableUtils#fold(Iterable, Function2, Object)
	 * @since 2.0
	 */
	@Override
	default <S> S fold(final Function2<? super S, ? super E, ? extends S> operator, final S initialState) {
		return IterableUtils.fold(this, operator, initialState);
	}
	
	/**
	 * Tests whether any element provided by this iterable is accepted by the given filter.
	 * 
	 * @see IterableUtils#isAny(Iterable, Predicate)
	 * @since 2.0
	 */
	@Override
	default boolean isAny(final Predicate<? super E> filter) {
		return IterableUtils.isAny(this, filter);
	}
	
	/**
	 * Tests whether all elements provided by this iterable are accepted by the given filter.
	 * 
	 * @see IterableUtils#areAll(Iterable, Predicate)
	 * @since 2.0
	 */
	@Override
	default boolean areAll(final Predicate<? super E> filter) {
		return IterableUtils.areAll(this, filter);
	}
	
	/**
	 * Counts the elements provided by this iterable accepted by the given filter.
	 * 
	 * @see IterableUtils#count(Iterable, Predicate)
	 * @since 2.0
	 */
	@Override
	default int count(final Predicate<? super E> filter) {
		return IterableUtils.count(this, filter);
	}
	
	/**
	 * Gets the least element provided by this iterable according to the given comparator.
	 *
	 * @see IterableUtils#least(Iterable, Comparator)
	 * @since 2.0
	 */
	@Override
	default Maybe<E> least(final Comparator<? super E> comparator) {
		return IterableUtils.least(this, comparator);
	}
	
	/**
	 * Gets the greatest element provided by this iterable according to the given comparator.
	 *
	 * @see IterableUtils#greatest(Iterable, Comparator)
	 * @since 2.0
	 */
	@Override
	default Maybe<E> greatest(final Comparator<? super E> comparator) {
		return IterableUtils.greatest(this, comparator);
	}
	
	/**
	 * Takes n elements provided by this iterable.
	 * <p>
	 * The elements are taken according their iteration order.
	 * 
	 * @return An iterable providing the taken elements.
	 * @see IterableUtils#take(Iterable, int)
	 * @since 2.0
	 */
	@Override
	default ExIterable<E> take(final int n) {
		return IterableUtils.take(this, n);
	}
	
	/**
	 * Drops n elements provided by this iterable.
	 * <p>
	 * The elements are dropped according their iteration order.
	 * 
	 * @return An iterable providing the remaining elements.
	 * @see IterableUtils#drop(Iterable, int)
	 * @since 2.0
	 */
	@Override
	default ExIterable<E> drop(final int n) {
		return IterableUtils.drop(this, n);
	}
	
	/**
	 * Groups the elements provided by this iterable into batches of the given size.
	 * 
	 * @return An iterable providing the batches of elements.
	 * @since 2.0
	 */
	@Override
	default <B extends Collection<? super E>> ExIterable<B> group(final int n, final CollectionFactory<? super E, B> batchFactory) {
		return IterableUtils.group(this, n, batchFactory);
	}
	
	/**
	 * Filters the elements provided by this iterable using the given filter.
	 *
	 * @return An iterable providing the filtered elements.
	 * @see IterableUtils#filter(Iterable, Predicate)
	 * @since 2.0
	 */
	@Override
	default ExIterable<E> filter(final Predicate<? super E> filter) {
		return IterableUtils.filter(this, filter);
	}
	
	/**
	 * Gets any element provided by this iterable accepted by the given filter.
	 * <p>
	 * The elements are filtered according to their iteration order.
	 * 
	 * @see IterableUtils#filterAny(Iterable, Predicate)
	 * @since 2.0
	 */
	@Override
	default Maybe<E> filterAny(final Predicate<? super E> filter) {
		return IterableUtils.filterAny(this, filter);
	}
	
	/**
	 * Transforms the elements provided by this iterable using the given function.
	 *
	 * @return An iterable providing the transformed elements.
	 * @see IterableUtils#map(Iterable, Function)
	 * @since 2.0
	 */
	@Override
	default <TE> ExIterable<TE> map(final Function<? super E, ? extends TE> function) {
		return IterableUtils.map(this, function);
	}
	
	/**
	 * Extracts the elements provided by this iterable using the given extractor.
	 *
	 * @return An iterable of the extracted elements.
	 * @see IterableUtils#extract(Iterable, Function)
	 * @since 2.0
	 */
	@Override
	default <EE> ExIterable<EE> extract(final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
		return IterableUtils.extract(this, extractor);
	}
	
	/**
	 * Gets the element extracted from any element provided by this iterable using the given extractor.
	 * <p>
	 * The elements are extracted from according to their iteration order.
	 * 
	 * @see IterableUtils#extractAny(Iterable, Function)
	 * @since 2.0
	 */
	@Override
	default <EE> Maybe<EE> extractAny(final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
		return IterableUtils.extractAny(this, extractor);
	}
	
	/**
	 * Gets all elements extracted from the elements provided by this iterable using the given extractor.
	 * 
	 * @param <EE> Type of the extracted elements.
	 * @param extractor Function to use to extract the elements.
	 * @return An iterable of the extracted elements.
	 * @see IterableUtils#extractAll(Iterable, Function)
	 * @since 2.0
	 */
	default <EE> ExIterable<EE> extractAll(final Function<? super E, ? extends Iterable<? extends EE>> extractor) {
		return IterableUtils.extractAll(this, extractor);
	}
	
	// TODO: append
	
	// Note: flatMap is not defined here because Java does not support higher order type parameters.
	//	/**
	//	 * Transforms and flattens the elements provided by this iterable using the given function.
	//	 *
	//	 * @param <TE> Type of the transformed elements.
	//	 * @param function Function to use to transform the elements.
	//	 * @return An iterable providing the flatten, transformed elements.
	//	 * @see IterableUtils#flatMap(Iterable, Function)
	//	 * @since 2.0
	//	 */
	//	default <TE> ExIterable<TE> flatMap(final Function<? super E, ? extends Iterable<? extends TE>> function) {
	//		return IterableUtils.flatMap(this, function);
	//	}
	
	/**
	 * Composes pairs with the elements provided by this iterable and the given iterable.
	 * <p>
	 * The pairs are composed of an element provided by each iterable in order. The extra values of the longest iterable are dropped when this iterables don't
	 * provide the same number of elements.
	 * 
	 * @param <E2> Type of the second elements.
	 * @param iterable2 Iterable providing the second elements of the pairs.
	 * @return An iterable providing the pairs of elements.
	 * @see IterableUtils#zip(Iterable, Iterable)
	 * @since 2.0
	 */
	default <E2> PairIterable<E, E2> zip(final Iterable<? extends E2> iterable2) {
		return IterableUtils.zip(this, iterable2);
	}
	
	/**
	 * Executes the given procedure with each element provided by this iterable.
	 * 
	 * @see IterableUtils#foreach(Iterable, Procedure)
	 * @since 2.0
	 */
	@Override
	default void foreach(final Procedure<? super E> procedure) {
		IterableUtils.foreach(this, procedure);
	}
	
	// Misc.
	
	/**
	 * Builds an unmodifiable view of this iterable.
	 * 
	 * @return An unmodifiable view of this iterable, or this iterable when is it already unmodifiable.
	 * @see IterableUtils#unmodifiable(Iterable)
	 * @since 2.0
	 */
	default ExIterable<E> unmodifiable() {
		return IterableUtils.unmodifiable(this);
	}
}
