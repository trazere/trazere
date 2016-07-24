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
import java.util.List;
import java.util.ListIterator;

/**
 * The {@link ExListIterator} interfaces defines extended {@link ListIterator list iterators}.
 * 
 * @param <E> Type of the elements.
 * @see ListIterator
 * @since 2.0
 */
public interface ExListIterator<E>
extends ListIterator<E>, Traversable<E> {
	// TODO: move to ListIterators ?
	// TODO: rename -> fromIterator ?
	/**
	 * Builds an extended view of the given list iterator.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterator Iterator to wrap.
	 * @return The extended view of the given iterator, or the given iterator when it is already an extended view.
	 * @since 2.0
	 */
	public static <E> ExListIterator<E> build(final ListIterator<E> iterator) {
		assert null != iterator;
		
		if (iterator instanceof ExListIterator) {
			return (ExListIterator<E>) iterator;
		} else {
			return new ListIteratorDecorator<>(iterator);
		}
	}
	
	// List iterator.
	
	/**
	 * Gets the next element provided by this list iterator.
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
	 * Gets the previous element provided by this list iterator.
	 * <p>
	 * This methods supports exhausted iterators.
	 * 
	 * @return The previous element, or nothing when the iterator is exhausted.
	 * @see ListIteratorUtils#optionalPrevious(ListIterator)
	 * @since 2.0
	 */
	default Maybe<E> optionalPrevious() {
		return IteratorUtils.optionalNext(this);
	}
	
	// TODO: rename to drainAll ?
	/**
	 * Drains all next elements provided by the this list iterator.
	 * 
	 * @see IteratorUtils#drain(Iterator)
	 * @since 2.0
	 */
	default void drain() {
		IteratorUtils.drain(this);
	}
	
	// TODO: rename to drainAll ?
	/**
	 * Drains all next elements provided by the this list iterator and populates the given accumulator with them.
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
	
	// TODO: rename to drainAll ?
	/**
	 * Drains all next elements provided by the this list iterator and adds them to the given collection.
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
	
	// TODO: backwardDrain ?
	
	@Override
	default void remove() {
		throw new UnsupportedOperationException("remove");
	}
	
	@Override
	default void set(final E e) {
		throw new UnsupportedOperationException("set");
	}
	
	@Override
	default void add(final E e) {
		throw new UnsupportedOperationException("add");
	}
	
	// Traversable.
	
	/**
	 * Left folds over the next elements provided by this list iterator using the given binary operator and initial state.
	 * 
	 * @see IteratorUtils#fold(Iterator, Function2, Object)
	 * @since 2.0
	 */
	@Override
	default <S> S fold(final Function2<? super S, ? super E, ? extends S> operator, final S initialState) {
		return IteratorUtils.fold(this, operator, initialState);
	}
	
	// TODO: backwardFold ?
	
	/**
	 * Tests whether any next element provided by this list iterator is accepted by the given filter.
	 * 
	 * @return <code>true</code> when some element is accepted, <code>false</code> when all elements are rejected or when the iterator is exhausted.
	 * @see IteratorUtils#isAny(Iterator, Predicate)
	 * @since 2.0
	 */
	@Override
	default boolean isAny(final Predicate<? super E> filter) {
		return IteratorUtils.isAny(this, filter);
	}
	
	// TODO: backwardIsAny ?
	
	/**
	 * Tests whether all next elements provided by this list iterator are accepted by the given filter.
	 * 
	 * @return <code>true</code> when all elements are accepted or when the iterator is exhausted, <code>false</code> when some element is rejected.
	 * @see IteratorUtils#areAll(Iterator, Predicate)
	 * @since 2.0
	 */
	@Override
	default boolean areAll(final Predicate<? super E> filter) {
		return IteratorUtils.areAll(this, filter);
	}
	
	// TODO: backwardAreAll ?
	
	/**
	 * Counts the next elements provided by this list iterator accepted by the given filter.
	 * 
	 * @see IteratorUtils#count(Iterator, Predicate)
	 * @since 2.0
	 */
	@Override
	default int count(final Predicate<? super E> filter) {
		return IteratorUtils.count(this, filter);
	}
	
	// TODO: backwardCount ?
	
	/**
	 * Gets the least next element provided by this list iterator according to the given comparator.
	 * 
	 * @see IteratorUtils#least(Iterator, Comparator)
	 * @since 2.0
	 */
	@Override
	default Maybe<E> least(final Comparator<? super E> comparator) {
		return IteratorUtils.least(this, comparator);
	}
	
	// TODO: backwardLeast ?
	
	/**
	 * Gets the greatest next element provided by this list iterator according to the given comparator.
	 * 
	 * @see IteratorUtils#greatest(Iterator, Comparator)
	 * @since 2.0
	 */
	@Override
	default Maybe<E> greatest(final Comparator<? super E> comparator) {
		return IteratorUtils.greatest(this, comparator);
	}
	
	// TODO: backwardGreatest ?
	
	/**
	 * Takes n next elements provided by this list iterator.
	 * <p>
	 * The elements are taken according their iteration order.
	 * <p>
	 * The built iterator feeds lazyly from this iterator.
	 * 
	 * @return An iterator providing the taken elements.
	 * @see ListIteratorUtils#take(ListIterator, int)
	 * @since 2.0
	 */
	@Override
	default ExListIterator<E> take(final int n) {
		return ListIteratorUtils.take(this, n);
	}
	
	// TODO: backwardTake ?
	
	/**
	 * Drops all previous and the n first next elements provided by this list iterator.
	 * <p>
	 * The elements are dropped according their iteration order.
	 * <p>
	 * The built iterator feeds lazyly from this iterator.
	 * 
	 * @return An iterator providing the remaining mext elements.
	 * @see ListIteratorUtils#drop(ListIterator, int)
	 * @since 2.0
	 */
	@Override
	default ExListIterator<E> drop(final int n) {
		return ListIteratorUtils.drop(this, n);
	}
	
	/**
	 * Groups the elements provided by this list iterator into batches of the given size.
	 * 
	 * @return An iterator providing the batches of elements.
	 * @see ListIteratorUtils#group(ListIterator, int, CollectionFactory)
	 * @since 2.0
	 */
	@Override
	default <B extends Collection<? super E>> ExListIterator<B> group(final int n, final CollectionFactory<? super E, B> batchFactory) {
		return ListIteratorUtils.group(this, n, batchFactory);
	}
	
	/**
	 * Filters the elements provided by this list iterator using the given filter.
	 * <p>
	 * The built iterator feeds lazyly from this iterator.
	 * 
	 * @return An iterator providing the filtered elements.
	 * @see ListIteratorUtils#filter(ListIterator, Predicate)
	 * @since 2.0
	 */
	@Override
	default ExListIterator<E> filter(final Predicate<? super E> filter) {
		return ListIteratorUtils.filter(this, filter);
	}
	
	/**
	 * Gets any next element provided by this list iterator accepted by the given filter.
	 * 
	 * @see IteratorUtils#filterAny(Iterator, Predicate)
	 * @since 2.0
	 */
	@Override
	default Maybe<E> filterAny(final Predicate<? super E> filter) {
		return IteratorUtils.filterAny(this, filter);
	}
	
	// TODO: backwardFilterAny ?
	
	/**
	 * Transforms the elements provided by this list iterator using the given function.
	 * <p>
	 * The built iterator feeds lazyly from this iterator.
	 * 
	 * @return An iterator providing the transformed elements.
	 * @see ListIteratorUtils#map(ListIterator, Function)
	 * @since 2.0
	 */
	@Override
	default <TE> ExListIterator<TE> map(final Function<? super E, ? extends TE> function) {
		return ListIteratorUtils.map(this, function);
	}
	
	/**
	 * Extracts the elements from the elements provided by this list iterator using the given extractor.
	 * <p>
	 * The built iterator feeds lazyly from this iterator.
	 * 
	 * @return An iterator providing the extracted elements.
	 * @see ListIteratorUtils#extract(ListIterator, Function)
	 * @since 2.0
	 */
	@Override
	default <EE> ExListIterator<EE> extract(final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
		return ListIteratorUtils.extract(this, extractor);
	}
	
	/**
	 * Get the element extracted from any next elements provided by this list iterator using the given extractor.
	 * <p>
	 * The elements are extracted from according to their iteration order.
	 * 
	 * @see IteratorUtils#extractAny(Iterator, Function)
	 * @since 2.0
	 */
	@Override
	default <EE> Maybe<EE> extractAny(final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
		return IteratorUtils.extractAny(this, extractor);
	}
	
	// TODO: backwardExtractAny ?
	
	/**
	 * Gets all elements extracted from the elements provided by this list iterator using the given extractor.
	 * <p>
	 * The built iterator feeds lazyly from this iterator.
	 * 
	 * @param <TE> Type of the extracted elements.
	 * @param extractor Function to use to extract from the elements.
	 * @return An iterator providing the extracted elements.
	 * @see ListIteratorUtils#extractAll(ListIterator, Function)
	 * @since 2.0
	 */
	default <TE> ExListIterator<TE> extractAll(final Function<? super E, ? extends List<? extends TE>> extractor) {
		return ListIteratorUtils.extractAll(this, extractor);
	}
	
	// TODO: append
	
	/**
	 * Transforms and flattens the elements provided by this list iterator using the given function.
	 * <p>
	 * The built iterator feeds lazyly from this iterator.
	 * 
	 * @param <TE> Type of the transformed elements.
	 * @param extractor Function to use to transform the elements.
	 * @return An iterator providing the flatten, transformed elements.
	 * @see ListIteratorUtils#flatMap(ListIterator, Function)
	 * @since 2.0
	 */
	default <TE> ExListIterator<TE> flatMap(final Function<? super E, ? extends ListIterator<? extends TE>> extractor) {
		return ListIteratorUtils.flatMap(this, extractor);
	}
	
	// TODO: zip
	
	/**
	 * Executes the given procedure with each next element provided by this list iterator.
	 * 
	 * @see IteratorUtils#foreach(Iterator, Procedure)
	 * @since 2.0
	 */
	@Override
	default void foreach(final Procedure<? super E> procedure) {
		IteratorUtils.foreach(this, procedure);
	}
	
	// TODO: backwardForeach ?
	
	// Misc.
	
	/**
	 * Builds an unmodifiable view of this list iterator.
	 * 
	 * @return An unmodifiable view of this iterator, or this iterator when is it already unmodifiable.
	 * @see ListIteratorUtils#unmodifiable(ListIterator)
	 * @since 2.0
	 */
	default ExListIterator<E> unmodifiable() {
		return ListIteratorUtils.unmodifiable(this);
	}
}
