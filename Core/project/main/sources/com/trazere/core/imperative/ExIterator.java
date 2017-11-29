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

import com.trazere.core.collection.CollectionFactory;
import com.trazere.core.functional.Function;
import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Predicate;
import com.trazere.core.lang.Traversable;
import com.trazere.core.util.Maybe;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

/**
 * The {@link ExIterator} interfaces defines {@link Iterator iterators} extended with various utilities.
 * 
 * @param <E> Type of the elements.
 * @see Iterator
 * @since 2.0
 */
public interface ExIterator<E>
extends Iterator<E>, Traversable<E> {
	// TODO: move to Iterators ?
	// TODO: rename -> fromIterator ?
	/**
	 * Builds an extended view of the given iterator.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterator Iterator to wrap.
	 * @return The extended view of the given iterator, or the given iterator when it is already an extended view.
	 * @since 2.0
	 */
	public static <E> ExIterator<E> build(final Iterator<E> iterator) {
		assert null != iterator;
		
		if (iterator instanceof ExIterator) {
			return (ExIterator<E>) iterator;
		} else {
			return new IteratorDecorator<>(iterator);
		}
	}
	
	// ExIterator.
	
	/**
	 * Gets the next element provided by this iterator.
	 * <p>
	 * This methods supports exhausted iterators.
	 * 
	 * @return The next element, or nothing when the iterator is exhausted.
	 * @see IteratorUtils#optionalNext(Iterator)
	 * @since 2.0
	 */
	default Maybe<E> optionalNext() {
		return IteratorUtils.optionalNext(this);
	}
	
	/**
	 * Drains all elements provided by the this iterator.
	 * 
	 * @see IteratorUtils#drain(Iterator)
	 * @since 2.0
	 */
	default void drain() {
		IteratorUtils.drain(this);
	}
	
	/**
	 * Drains all elements provided by the this iterator and populates the given accumulator with them.
	 * 
	 * @param <A> Type of the accumulator to populate.
	 * @param results Accumulator to populate with the drained elements.
	 * @return The given result accumulator.
	 * @see IteratorUtils#drain(Iterator, Accumulator)
	 * @since 2.0
	 */
	default <A extends Accumulator<? super E, ?>> A drain(final A results) {
		return IteratorUtils.drain(this, results);
	}
	
	/**
	 * Drains all elements provided by the this iterator and adds them to the given collection.
	 * 
	 * @param <C> Type of the collection to populate.
	 * @param results Collection to populate with the drained elements.
	 * @return The given result collection.
	 * @see IteratorUtils#drain(Iterator, Collection)
	 * @since 2.0
	 */
	default <C extends Collection<? super E>> C drain(final C results) {
		return IteratorUtils.drain(this, results);
	}
	
	/**
	 * Composes pairs with the elements provided by this iterator and the given iterator.
	 * <p>
	 * The pairs are composed of an element provided by each iterator in order. The extra values of the longest iterator are dropped when this iterators don't
	 * provide the same number of elements.
	 * <p>
	 * The built iterator feeds lazyly from this iterator and the given iterator.
	 * 
	 * @param <E2> Type of the second elements.
	 * @param iterator2 Iterator providing the second elements of the pairs.
	 * @return An iterator providing the pairs of elements.
	 * @see IteratorUtils#zip(Iterator, Iterator)
	 * @since 2.0
	 */
	default <E2> PairIterator<E, E2> zip(final Iterator<? extends E2> iterator2) {
		return IteratorUtils.zip(this, iterator2);
	}
	
	/**
	 * Builds an unmodifiable view of this iterator.
	 * 
	 * @return An unmodifiable view of this iterator, or this iterator when is it already unmodifiable.
	 * @see IteratorUtils#unmodifiable(Iterator)
	 * @since 2.0
	 */
	default ExIterator<E> unmodifiable() {
		return IteratorUtils.unmodifiable(this);
	}
	
	// Traversable.
	
	/**
	 * Left folds over the elements provided by this iterator using the given binary operator and initial state.
	 * 
	 * @see IteratorUtils#fold(Iterator, Function2, Object)
	 * @since 2.0
	 */
	@Override
	default <S> S fold(final Function2<? super S, ? super E, ? extends S> operator, final S initialState) {
		return IteratorUtils.fold(this, operator, initialState);
	}
	
	/**
	 * Tests whether any element provided by this iterator is accepted by the given filter.
	 * 
	 * @return <code>true</code> when some element is accepted, <code>false</code> when all elements are rejected or when the iterator is exhausted.
	 * @see IteratorUtils#isAny(Iterator, Predicate)
	 * @since 2.0
	 */
	@Override
	default boolean isAny(final Predicate<? super E> filter) {
		return IteratorUtils.isAny(this, filter);
	}
	
	/**
	 * Tests whether all elements provided by this iterator are accepted by the given filter.
	 * 
	 * @return <code>true</code> when all elements are accepted or when the iterator is exhausted, <code>false</code> when some element is rejected.
	 * @see IteratorUtils#areAll(Iterator, Predicate)
	 * @since 2.0
	 */
	@Override
	default boolean areAll(final Predicate<? super E> filter) {
		return IteratorUtils.areAll(this, filter);
	}
	
	/**
	 * Counts the elements provided by this iterator accepted by the given filter.
	 * 
	 * @see IteratorUtils#count(Iterator, Predicate)
	 * @since 2.0
	 */
	@Override
	default int count(final Predicate<? super E> filter) {
		return IteratorUtils.count(this, filter);
	}
	
	/**
	 * Gets the least element provided by this iterator according to the given comparator.
	 * 
	 * @see IteratorUtils#least(Iterator, Comparator)
	 * @since 2.0
	 */
	@Override
	default Maybe<E> least(final Comparator<? super E> comparator) {
		return IteratorUtils.least(this, comparator);
	}
	
	/**
	 * Gets the greatest element provided by this iterator according to the given comparator.
	 * 
	 * @see IteratorUtils#greatest(Iterator, Comparator)
	 * @since 2.0
	 */
	@Override
	default Maybe<E> greatest(final Comparator<? super E> comparator) {
		return IteratorUtils.greatest(this, comparator);
	}
	
	/**
	 * Takes n elements provided by this iterator.
	 * <p>
	 * The elements are taken according their iteration order.
	 * <p>
	 * The built iterator feeds lazyly from this iterator.
	 * 
	 * @return An iterator providing the taken elements.
	 * @see IteratorUtils#take(Iterator, int)
	 * @since 2.0
	 */
	@Override
	default ExIterator<E> take(final int n) {
		return IteratorUtils.take(this, n);
	}
	
	/**
	 * Drops the n first elements provided by this iterator.
	 * <p>
	 * The elements are dropped according their iteration order.
	 * <p>
	 * The built iterator feeds lazyly from this iterator.
	 * 
	 * @return An iterator providing the remaining elements.
	 * @see IteratorUtils#drop(Iterator, int)
	 * @since 2.0
	 */
	@Override
	default ExIterator<E> drop(final int n) {
		return IteratorUtils.drop(this, n);
	}
	
	/**
	 * Groups the elements provided by this iterator into batches of the given size.
	 * 
	 * @return An iterator providing the batches of elements.
	 * @see IteratorUtils#group(Iterator, int, CollectionFactory)
	 * @since 2.0
	 */
	@Override
	default <B extends Collection<? super E>> ExIterator<B> group(final int n, final CollectionFactory<? super E, B> batchFactory) {
		return IteratorUtils.group(this, n, batchFactory);
	}
	
	/**
	 * Filters the elements provided by this iterator using the given filter.
	 * <p>
	 * The built iterator feeds lazyly from this iterator.
	 * 
	 * @return An iterator providing the filtered elements.
	 * @see IteratorUtils#filter(Iterator, Predicate)
	 * @since 2.0
	 */
	@Override
	default ExIterator<E> filter(final Predicate<? super E> filter) {
		return IteratorUtils.filter(this, filter);
	}
	
	/**
	 * Gets any element provided by this iterator accepted by the given filter.
	 * 
	 * @see IteratorUtils#filterAny(Iterator, Predicate)
	 * @since 2.0
	 */
	@Override
	default Maybe<E> filterAny(final Predicate<? super E> filter) {
		return IteratorUtils.filterAny(this, filter);
	}
	
	/**
	 * Transforms the elements provided by this iterator using the given function.
	 * <p>
	 * The built iterator feeds lazyly from this iterator.
	 * 
	 * @return An iterator providing the transformed elements.
	 * @see IteratorUtils#map(Iterator, Function)
	 * @since 2.0
	 */
	@Override
	default <TE> ExIterator<TE> map(final Function<? super E, ? extends TE> function) {
		return IteratorUtils.map(this, function);
	}
	
	/**
	 * Extracts the elements from the elements provided by this iterator using the given extractor.
	 * <p>
	 * The built iterator feeds lazyly from this iterator.
	 * 
	 * @return An iterator providing the extracted elements.
	 * @see IteratorUtils#extract(Iterator, Function)
	 * @since 2.0
	 */
	@Override
	default <EE> ExIterator<EE> extract(final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
		return IteratorUtils.extract(this, extractor);
	}
	
	/**
	 * Get the element extracted from any elements provided by this iterator using the given extractor.
	 * 
	 * @see IteratorUtils#extractAny(Iterator, Function)
	 * @since 2.0
	 */
	@Override
	default <EE> Maybe<EE> extractAny(final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
		return IteratorUtils.extractAny(this, extractor);
	}
	
	/**
	 * Gets all elements extracted from the elements provided by this iterator using the given extractor.
	 * <p>
	 * The built iterator feeds lazyly from this iterator.
	 * 
	 * @param <EE> Type of the extracted elements.
	 * @param extractor Function to use to extract from the elements.
	 * @return An iterator providing the extracted elements.
	 * @see IteratorUtils#extractAll(Iterator, Function)
	 * @since 2.0
	 */
	// TODO: rename ? extractMany ? extractN ? extractMultiple ?
	default <EE> ExIterator<EE> extractAll(final Function<? super E, ? extends Iterable<? extends EE>> extractor) {
		return IteratorUtils.extractAll(this, extractor);
	}
	
	// TODO: append
	
	/**
	 * Transforms and flattens the elements provided by this iterator using the given function.
	 * <p>
	 * The built iterator feeds lazyly from this iterator.
	 * 
	 * @param <TE> Type of the transformed elements.
	 * @param extractor Function to use to transform the elements.
	 * @return An iterator providing the flatten, transformed elements.
	 * @see IteratorUtils#flatMap(Iterator, Function)
	 * @since 2.0
	 */
	default <TE> ExIterator<TE> flatMap(final Function<? super E, ? extends Iterator<? extends TE>> extractor) {
		return IteratorUtils.flatMap(this, extractor);
	}
	
	/**
	 * Executes the given procedure with each element provided by this iterator.
	 * 
	 * @see IteratorUtils#foreach(Iterator, Procedure)
	 * @since 2.0
	 */
	@Override
	default void foreach(final Procedure<? super E> procedure) {
		IteratorUtils.foreach(this, procedure);
	}
}