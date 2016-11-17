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
package com.trazere.core.collection;

import com.trazere.core.functional.Function;
import com.trazere.core.functional.Predicate;
import com.trazere.core.imperative.ExListIterator;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * The {@link ExList} interface defines {@link List lists} extended with various utilities.
 * 
 * @param <E> Type of the elements.
 * @see List
 * @since 2.0
 */
public interface ExList<E>
extends List<E>, ExCollection<E> {
	// TODO: move to Lists ?
	// TODO: rename -> fromList ?
	/**
	 * Builds an extended view of the given list.
	 * 
	 * @param <E> Type of the elements.
	 * @param list List to wrap.
	 * @return The extended view of the given list, or the given list when it is already an extended view.
	 * @since 2.0
	 */
	public static <E> ExList<E> build(final List<E> list) {
		assert null != list;
		
		if (list instanceof ExList) {
			return (ExList<E>) list;
		} else {
			return new ListDecorator<>(list);
		}
	}
	
	// List.
	
	/**
	 * @since 2.0
	 */
	@Override
	public ExListIterator<E> listIterator();
	
	/**
	 * @since 2.0
	 */
	@Override
	public ExListIterator<E> listIterator(int index);
	
	/**
	 * @since 2.0
	 */
	@Override
	ExList<E> subList(int fromIndex, int toIndex);
	
	// ExList.
	
	/**
	 * Gets the first element of this list.
	 *
	 * @return The first element of the list.
	 * @throws IndexOutOfBoundsException When the list is empty.
	 * @see ListUtils#first(List)
	 * @since 2.0
	 */
	default E first()
	throws IndexOutOfBoundsException {
		return ListUtils.first(this);
	}
	
	/**
	 * Gets the first element of this list.
	 * <p>
	 * This methods support empty lists.
	 *
	 * @return The first element of the list, or nothing when the list is empty.
	 * @see ListUtils#optionalFirst(List)
	 * @since 2.0
	 */
	default Maybe<E> optionalFirst() {
		return ListUtils.optionalFirst(this);
	}
	
	/**
	 * Gets the last element of this list.
	 *
	 * @return The last element.
	 * @throws IndexOutOfBoundsException When the list is empty.
	 * @see ListUtils#last(List)
	 * @since 2.0
	 */
	default E last()
	throws IndexOutOfBoundsException {
		return ListUtils.last(this);
	}
	
	/**
	 * Gets the last element of this list.
	 * <p>
	 * This methods support empty lists.
	 *
	 * @return The last element, or nothing when the list is empty.
	 * @see ListUtils#optionalLast(List)
	 * @since 2.0
	 */
	default Maybe<E> optionalLast() {
		return ListUtils.optionalLast(this);
	}
	
	/**
	 * Gets the element of this list at the given position.
	 * <p>
	 * This methods support indexes out of bounds.
	 *
	 * @param index Index of the element to get.
	 * @return The specified element, or nothing when the index is out of bounds.
	 * @see ListUtils#optionalGet(List, int)
	 * @since 2.0
	 */
	default Maybe<E> optionalGet(final int index) {
		return ListUtils.optionalGet(this, index);
	}
	
	/**
	 * Inserts all given elements in this list at the given position.
	 * <p>
	 * This method does modify this list.
	 *
	 * @param index Index at which the elements should be inserted.
	 * @param elements Elements to insert.
	 * @return <code>true</code> when the list is modified, <code>false</code> otherwise.
	 * @see ListUtils#addAll(List, int, Object...)
	 * @since 2.0
	 */
	default boolean addAll(final int index, @SuppressWarnings("unchecked") final E... elements) {
		return ListUtils.addAll(this, index, elements);
	}
	
	/**
	 * Inserts all given elements in this list at the given position.
	 * <p>
	 * This method does modify this list.
	 *
	 * @param index Index at which the elements should be inserted.
	 * @param elements Elements to insert.
	 * @return <code>true</code> when the list is modified, <code>false</code> otherwise.
	 * @see ListUtils#addAll(List, int, Iterable)
	 * @since 2.0
	 */
	default boolean addAll(final int index, final Iterable<? extends E> elements) {
		return ListUtils.addAll(this, index, elements);
	}
	
	/**
	 * Removes the element of this list at the given position.
	 * <p>
	 * This method does modify this list.
	 * 
	 * @param index Index of the element to remove.
	 * @return The removed element, or nothing when the index is out of bound.
	 * @see ListUtils#optionalRemove(List, int)
	 * @since 2.0
	 */
	default Maybe<E> optionalRemove(final int index) {
		return ListUtils.optionalRemove(this, index);
	}
	
	/**
	 * Reverses the order of the elements of this list.
	 * <p>
	 * This method does modify this list.
	 * 
	 * @see Collections#reverse(List)
	 * @since 2.0
	 */
	default void reverse() {
		Collections.reverse(this);
	}
	
	/**
	 * Shuffles the elements of this list.
	 * <p>
	 * This method does modify this list.
	 * 
	 * @see Collections#shuffle(List)
	 * @since 2.0
	 */
	default void shuffle() {
		Collections.shuffle(this);
	}
	
	/**
	 * Shuffles the elements of this list using the given source of randomness.
	 * <p>
	 * This method does modify this list.
	 * 
	 * @param random Source of randomness to use.
	 * @see Collections#shuffle(List, Random)
	 * @since 2.0
	 */
	default void shuffle(final Random random) {
		Collections.shuffle(this, random);
	}
	
	// TODO: append(List)
	// TODO: intersect(List)
	// TODO: intersect(List, CollectionFactory)
	// TODO: exclude(List)
	// TODO: exclude(List, CollectionFactory)
	
	/**
	 * Builds a view of this list in the reversed order.
	 * <p>
	 * The built list is backed by this list, any modification to one list is reported on the other.
	 * 
	 * @return A reversed view of this list.
	 * @see ListUtils#reversed(List)
	 * @since 2.0
	 */
	default ExList<E> reversed() {
		return ListUtils.reversed(this);
	}
	
	/**
	 * Composes pairs with the elements of this list and the given list.
	 * <p>
	 * The pairs are composed of an element of each list according to their iteration order. The extra values of the longest list are dropped when the given
	 * lists don't contain the same number of elements.
	 * 
	 * @param <E2> Type of the second elements.
	 * @param list2 List of the second elements of the pairs.
	 * @return A list of the pairs of elements.
	 * @see ListUtils#zip(List, List)
	 * @since 2.0
	 */
	default <E2> ExList<Tuple2<E, E2>> zip(final List<? extends E2> list2) {
		return ListUtils.zip(this, list2);
	}
	
	// ExIterable.
	
	/**
	 * Builds an unmodifiable view of this list.
	 * 
	 * @return An unmodifiable view of this list, or this list when is it already unmodifiable.
	 * @see ListUtils#unmodifiable(List)
	 * @since 2.0
	 */
	@Override
	default ExList<E> unmodifiable() {
		return ListUtils.unmodifiable(this);
	}
	
	// Traversable.
	
	/**
	 * Takes n elements of this list.
	 * <p>
	 * The first elements of the list are taken.
	 *
	 * @return A list of the taken elements.
	 * @see ListUtils#take(List, int)
	 * @since 2.0
	 */
	@Override
	default ExList<E> take(final int n) {
		return ListUtils.take(this, n);
	}
	
	/**
	 * Drops n elements of this list.
	 * <p>
	 * The fist elements of the list are dropped.
	 *
	 * @return A list of the remaining elements.
	 * @see ListUtils#drop(List, int)
	 * @since 2.0
	 */
	@Override
	default ExList<E> drop(final int n) {
		return ListUtils.drop(this, n);
	}
	
	/**
	 * Groups the elements of this list into batches of the given size.
	 *
	 * @return A list of the batches of elements.
	 * @see ListUtils#group(List, int, CollectionFactory)
	 * @since 2.0
	 */
	@Override
	default <B extends Collection<? super E>> ExList<B> group(final int n, final CollectionFactory<? super E, B> batchFactory) {
		return ListUtils.group(this, n, batchFactory);
	}
	
	/**
	 * Filters the elements of this list using the given filter.
	 *
	 * @return A list of the filtered elements.
	 * @see ListUtils#filter(List, Predicate)
	 * @since 2.0
	 */
	@Override
	default ExList<E> filter(final Predicate<? super E> filter) {
		return ListUtils.filter(this, filter);
	}
	
	/**
	 * Transforms the elements of this list using the given function.
	 * 
	 * @return A list of the transformed elements.
	 * @see ListUtils#map(List, Function)
	 * @since 2.0
	 */
	@Override
	default <TE> ExList<TE> map(final Function<? super E, ? extends TE> function) {
		return ListUtils.map(this, function);
	}
	
	/**
	 * Extracts the elements of this list using the given extractor.
	 *
	 * @return A list of the extracted elements.
	 * @see ListUtils#extract(List, Function)
	 * @since 2.0
	 */
	@Override
	default <EE> ExList<EE> extract(final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
		return ListUtils.extract(this, extractor);
	}
	
	/**
	 * Gets all elements extracted from the elements of this list using the given extractor.
	 * 
	 * @param <EE> Type of the extracted elements.
	 * @param extractor Function to use to extract from the elements.
	 * @return A list of the extracted elements.
	 * @see ListUtils#extractAll(List, Function)
	 * @since 2.0
	 */
	@Override
	default <EE> ExList<EE> extractAll(final Function<? super E, ? extends Iterable<? extends EE>> extractor) {
		return ListUtils.extractAll(this, extractor);
	}
	
	// Note: flatMap is not defined here because Java does not support higher order type parameters.
	//	/**
	//	 * Transforms and flattens the elements of this list using the given function.
	//	 *
	//	 * @param <TE> Type of the transformed elements.
	//	 * @param function Function to use to transform the elements.
	//	 * @return A list of the flatten, transformed elements.
	//	 * @see ListUtils#flatMap(List, Function)
	//	 * @since 2.0
	//	 */
	//	default <TE> ExList<TE> flatMap(final Function<? super E, ? extends List<? extends TE>> function) {
	//		return ListUtils.flatMap(this, function);
	//	}
}
