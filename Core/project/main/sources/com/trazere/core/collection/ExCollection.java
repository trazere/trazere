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
import com.trazere.core.lang.ExIterable;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.Collection;

/**
 * The {@link ExCollection} interface defines extended {@link Collection collections}.
 * 
 * @param <E> Type of the elements.
 * @see Collection
 * @since 2.0
 */
public interface ExCollection<E>
extends Collection<E>, ExIterable<E> {
	// TODO: move to Collections ?
	// TODO: rename -> fromCollection ?
	/**
	 * Builds an extended view of the given collection.
	 * 
	 * @param <E> Type of the elements.
	 * @param collection Collection to wrap.
	 * @return The extended view of the given collection, or the given collection when it is already an extended view.
	 * @since 2.0
	 */
	public static <E> ExCollection<E> build(final Collection<E> collection) {
		assert null != collection;
		
		if (collection instanceof ExCollection) {
			return (ExCollection<E>) collection;
		} else {
			return new CollectionDecorator<>(collection);
		}
	}
	
	// Collection.
	
	// TODO: rename
	/**
	 * Tests whether this collection and the given collection contain some common values.
	 * <p>
	 * This method iterates over this collection and tests the presence of the values within the given collection. Therefore, calling this method on a smaller
	 * collection and providing a collection with a faster {@link Collection#contains(Object)} method is more efficient.
	 * 
	 * @param collection Collection.
	 * @return <code>true</code> if the collections intersect, <code>false</code> otherwise.
	 * @see CollectionUtils#intersects(Collection, Collection)
	 * @since 2.0
	 */
	default boolean intersects(final Collection<? extends E> collection) {
		return CollectionUtils.intersects(this, collection);
	}
	
	// TODO: add(Maybe)
	
	/**
	 * Adds all given elements to this collection.
	 * 
	 * @param elements Elements to add.
	 * @return <code>true</code> when the given collection is modified, <code>false</code> otherwise.
	 * @see CollectionUtils#addAll(Collection, Object...)
	 * @since 2.0
	 */
	default boolean addAll(@SuppressWarnings("unchecked") final E... elements) {
		return CollectionUtils.addAll(this, elements);
	}
	
	/**
	 * Adds all elements provided by the given iterable to this collection.
	 *
	 * @param elements Elements to add.
	 * @return <code>true</code> when the given collection is modified, <code>false</code> otherwise.
	 * @see CollectionUtils#addAll(Collection, Iterable)
	 * @since 2.0
	 */
	default boolean addAll(final Iterable<? extends E> elements) {
		return CollectionUtils.addAll(this, elements);
	}
	
	// TODO: generalize and move to ExIterable
	/**
	 * Removes any element from this collection.
	 *
	 * @return The removed element, or nothing when the collection is empty.
	 * @see CollectionUtils#removeAny(Collection)
	 * @since 2.0
	 */
	default Maybe<E> removeAny() {
		return CollectionUtils.removeAny(this);
	}
	
	// TODO: generalize and move to ExIterable
	/**
	 * Removes any element of the given collection accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the elements.
	 * @return The removed element, or nothing when no elements are accepted by the filter or when the collection is empty.
	 * @see CollectionUtils#removeAny(Collection, Predicate)
	 * @since 2.0
	 */
	default Maybe<E> removeAny(final Predicate<? super E> filter) {
		return CollectionUtils.removeAny(this, filter);
	}
	
	/**
	 * Removes all given elements from this collection.
	 *
	 * @param elements Elements to remove.
	 * @return <code>true</code> when the given collection is modified, <code>false</code> otherwise.
	 * @see CollectionUtils#removeAll(Collection, Object...)
	 * @since 2.0
	 */
	default boolean removeAll(@SuppressWarnings("unchecked") final E... elements) {
		return CollectionUtils.removeAll(this, elements);
	}
	
	/**
	 * Removes all given elements from this collection.
	 *
	 * @param elements Iterable providing the elements to remove.
	 * @return <code>true</code> when the given collection is modified, <code>false</code> otherwise.
	 * @see CollectionUtils#removeAll(Collection, Iterable)
	 * @since 2.0
	 */
	default boolean removeAll(final Iterable<? extends E> elements) {
		return CollectionUtils.removeAll(this, elements);
	}
	
	// TODO: generalize and move to ExIterable
	/**
	 * Removes all elements accepted by the given filter from this collection.
	 *
	 * @param filter Predicate to use to filter the elements to remove.
	 * @return <code>true</code> when the given collection is modified, <code>false</code> otherwise.
	 * @see CollectionUtils#removeAll(Collection, Predicate)
	 * @since 2.0
	 */
	default boolean removeAll(final Predicate<? super E> filter) {
		return CollectionUtils.removeAll(this, filter);
	}
	
	// TODO: generalize and move to ExIterable
	/**
	 * Retains the elements accepted by the given filter in this collection.
	 *
	 * @param filter Predicate to use to filter the elements to retain.
	 * @return <code>true</code> when the given collection is modified, <code>false</code> otherwise.
	 * @see CollectionUtils#retainAll(Collection, Predicate)
	 * @since 2.0
	 */
	default boolean retainAll(final Predicate<? super E> filter) {
		return CollectionUtils.retainAll(this, filter);
	}
	
	// Traversable.
	
	/**
	 * Takes n elements of this collection.
	 * <p>
	 * The elements are taken according their iteration order.
	 *
	 * @return A collection of the taken elements.
	 * @see CollectionUtils#take(Collection, int)
	 * @since 2.0
	 */
	@Override
	default ExCollection<E> take(final int n) {
		return CollectionUtils.take(this, n);
	}
	
	/**
	 * Takes n elements of this collection.
	 * <p>
	 * The elements are taken according their iteration order.
	 * 
	 * @param <C> Type of the result collection.
	 * @param n Number of elements to take.
	 * @param resultFactory Factory of the result collection.
	 * @return A new collection of the taken elements.
	 * @see CollectionUtils#take(Collection, int, CollectionFactory)
	 * @since 2.0
	 */
	default <C extends Collection<? super E>> C take(final int n, final CollectionFactory<? super E, C> resultFactory) {
		return CollectionUtils.take(this, n, resultFactory);
	}
	
	/**
	 * Drops n elements of this collection.
	 * <p>
	 * The elements are dropped according their iteration order.
	 *
	 * @return A collection of the remaining elements.
	 * @see CollectionUtils#drop(Collection, int)
	 * @since 2.0
	 */
	@Override
	default ExCollection<E> drop(final int n) {
		return CollectionUtils.drop(this, n);
	}
	
	/**
	 * Drops n elements of this collection.
	 * <p>
	 * The elements are dropped according their iteration order.
	 *
	 * @param <C> Type of the result collection.
	 * @param n Number of elements to drop.
	 * @param resultFactory Factory of the result collection.
	 * @return A new collection of the remaining elements.
	 * @see CollectionUtils#drop(Collection, int, CollectionFactory)
	 * @since 2.0
	 */
	default <C extends Collection<? super E>> C drop(final int n, final CollectionFactory<? super E, C> resultFactory) {
		return CollectionUtils.drop(this, n, resultFactory);
	}
	
	/**
	 * Groups the elements of this collection into batches of the given size.
	 *
	 * @return A collection of the batches of elements.
	 * @see CollectionUtils#group(Collection, int, CollectionFactory)
	 * @since 2.0
	 */
	@Override
	default <B extends Collection<? super E>> ExCollection<B> group(final int n, final CollectionFactory<? super E, B> batchFactory) {
		return CollectionUtils.group(this, n, batchFactory);
	}
	
	/**
	 * Groups the elements of this collection into batches of the given size.
	 *
	 * @param <B> Type of the batch collections.
	 * @param <C> Type of the result collection.
	 * @param n Number of elements of each batch.
	 * @param batchFactory Factory of the batch collections.
	 * @param resultFactory Factory of the result collection.
	 * @return A collection of the batches of elements.
	 * @see CollectionUtils#group(Collection, int, CollectionFactory, CollectionFactory)
	 * @since 2.0
	 */
	default <B extends Collection<? super E>, C extends Collection<? super B>> C group(final int n, final CollectionFactory<? super E, B> batchFactory, final CollectionFactory<? super B, C> resultFactory) {
		return CollectionUtils.group(this, n, batchFactory, resultFactory);
	}
	
	/**
	 * Filters the elements of this collection using the given filter.
	 *
	 * @return A collection of the filtered elements.
	 * @see CollectionUtils#filter(Collection, Predicate)
	 * @since 2.0
	 */
	@Override
	default ExCollection<E> filter(final Predicate<? super E> filter) {
		return CollectionUtils.filter(this, filter);
	}
	
	/**
	 * Filters the elements of this collection using the given filter.
	 *
	 * @param <C> Type of the result collection.
	 * @param filter Predicate to use to filter the elements.
	 * @param resultFactory Factory of the result collection.
	 * @return A new collection of the filtered elements.
	 * @see CollectionUtils#filter(Collection, Predicate, CollectionFactory)
	 * @since 2.0
	 */
	default <C extends Collection<? super E>> C filter(final Predicate<? super E> filter, final CollectionFactory<? super E, C> resultFactory) {
		return CollectionUtils.filter(this, filter, resultFactory);
	}
	
	/**
	 * Transforms the elements of this collection using the given function.
	 * 
	 * @return A collection of the transformed elements.
	 * @see CollectionUtils#map(Collection, Function)
	 * @since 2.0
	 */
	@Override
	default <TE> ExCollection<TE> map(final Function<? super E, ? extends TE> function) {
		return CollectionUtils.map(this, function);
	}
	
	/**
	 * Transforms the elements of this collection using the given function.
	 *
	 * @param <TE> Type of the transformed elements.
	 * @param <C> Type of the result collection.
	 * @param function Function to use to transform the elements.
	 * @param resultFactory Factory of the result collection.
	 * @return A new collection of the transformed elements.
	 * @see CollectionUtils#map(Collection, Function, CollectionFactory)
	 * @since 2.0
	 */
	default <TE, C extends Collection<? super TE>> C map(final Function<? super E, ? extends TE> function, final CollectionFactory<? super TE, C> resultFactory) {
		return CollectionUtils.map(this, function, resultFactory);
	}
	
	/**
	 * Extracts the elements of this collection using the given extractor.
	 *
	 * @return A collection of the extracted elements.
	 * @see CollectionUtils#extract(Collection, Function)
	 * @since 2.0
	 */
	@Override
	default <EE> ExCollection<EE> extract(final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
		return CollectionUtils.extract(this, extractor);
	}
	
	/**
	 * Extracts the elements from the elements of this collection using the given extractor.
	 *
	 * @param <EE> Type of the extracted elements.
	 * @param <C> Type of the result collection.
	 * @param extractor Function to use to extract from the elements.
	 * @param resultFactory Factory of the result collection.
	 * @return A new collection of the extracted elements.
	 * @see CollectionUtils#extract(Collection, Function, CollectionFactory)
	 * @since 2.0
	 */
	default <EE, C extends Collection<? super EE>> C extract(final Function<? super E, ? extends Maybe<? extends EE>> extractor, final CollectionFactory<? super EE, C> resultFactory) {
		return CollectionUtils.extract(this, extractor, resultFactory);
	}
	
	/**
	 * Gets all elements extracted from the elements of this collection using the given extractor.
	 * 
	 * @return A collection of the extracted elements.
	 * @see CollectionUtils#extractAll(Collection, Function)
	 * @since 2.0
	 */
	@Override
	default <EE> ExCollection<EE> extractAll(final Function<? super E, ? extends Iterable<? extends EE>> extractor) {
		return CollectionUtils.extractAll(this, extractor);
	}
	
	/**
	 * Gets all elements extracted from the elements of this collection using the given extractor.
	 * 
	 * @param <EE> Type of the extracted elements.
	 * @param <C> Type of the result collection.
	 * @param extractor Function to use to extract from the elements.
	 * @param resultFactory Factory of the result collection.
	 * @return A new collection of the extracted elements.
	 * @see CollectionUtils#extractAll(Collection, Function, CollectionFactory)
	 * @since 2.0
	 */
	default <EE, C extends Collection<? super EE>> C extractAll(final Function<? super E, ? extends Iterable<? extends EE>> extractor, final CollectionFactory<? super EE, C> resultFactory) {
		return CollectionUtils.extractAll(this, extractor, resultFactory);
	}
	
	/**
	 * Appends the given collection to this collection.
	 * 
	 * @param <C> Type of the result collection.
	 * @param collection2 Second collection of the elements to append.
	 * @param resultFactory Factory of the result collection.
	 * @return A new collection of the appended elements.
	 * @see CollectionUtils#append(Collection, Collection, CollectionFactory)
	 * @since 2.0
	 */
	default <C extends Collection<? super E>> C append(final Collection<? extends E> collection2, final CollectionFactory<? super E, C> resultFactory) {
		return CollectionUtils.append(this, collection2, resultFactory);
	}
	
	// Note: flatMap is not defined here because Java does not support higher order type parameters.
	//	/**
	//	 * Transforms and flattens the elements of this collection using the given function.
	//	 *
	//	 * @param <TE> Type of the transformed elements.
	//	 * @param function Function to use to transform the elements.
	//	 * @return A collection of the flatten, transformed elements.
	//	 * @see CollectionUtils#flatMap(Collection, Function)
	//	 * @since 2.0
	//	 */
	//	default <TE> ExCollection<TE> flatMap(final Function<? super E, ? extends Collection<? extends TE>> function) {
	//		return CollectionUtils.flatMap(this, function);
	//	}
	
	// Note: flatMap is not defined here because Java does not support higher order type parameters.
	//	/**
	//	 * Transforms and flattens the elements of this collection using the given function.
	//	 *
	//	 * @param <TE> Type of the transformed elements.
	//	 * @param <C> Type of the result collection.
	//	 * @param function Function to use to transform the elements.
	//	 * @param resultFactory Factory of the result collection.
	//	 * @return A new collection of the flatten, transformed elements.
	//	 * @see CollectionUtils#flatMap(Collection, Function, CollectionFactory)
	//	 * @since 2.0
	//	 */
	//	default <TE, C extends Collection<? super TE>> C flatMap(final Function<? super E, ? extends Collection<? extends TE>> function, final CollectionFactory<? super TE, C> resultFactory) {
	//		return CollectionUtils.flatMap(this, function, resultFactory);
	//	}
	
	// TODO: intersect(Collection)
	// TODO: intersect(Collection, CollectionFactory)
	// TODO: exclude(Collection)
	// TODO: exclude(Collection, CollectionFactory)
	
	/**
	 * Composes pairs with the elements of this collection and the given collection.
	 * <p>
	 * The pairs are composed of an element of each collection according to their iteration order. The extra values of the longest collection are dropped when
	 * the given collections don't contain the same number of elements.
	 * 
	 * @param <E2> Type of the second elements.
	 * @param collection2 Collection of the second elements of the pairs.
	 * @return A collection of the pairs of elements.
	 * @see CollectionUtils#zip(Collection, Collection)
	 * @since 2.0
	 */
	default <E2> ExCollection<Tuple2<E, E2>> zip(final Collection<? extends E2> collection2) {
		return CollectionUtils.zip(this, collection2);
	}
	
	/**
	 * Composes pairs with the elements of this collection and the given collection.
	 * <p>
	 * The pairs are composed of an element of each collection according to their iteration order. The extra values of the longest collection are dropped when
	 * the given collections don't contain the same number of elements.
	 * 
	 * @param <E2> Type of the second elements.
	 * @param <C> Type of the result collection.
	 * @param collection2 Collection of the second elements of the pairs.
	 * @param resultFactory Factory of the pair collection.
	 * @return A new collection of the pairs of elements.
	 * @see CollectionUtils#zip(Collection, Collection, CollectionFactory)
	 * @since 2.0
	 */
	default <E2, C extends Collection<? super Tuple2<? extends E, ? extends E2>>> C zip(final Collection<? extends E2> collection2, final CollectionFactory<? super Tuple2<? extends E, ? extends E2>, C> resultFactory) {
		return CollectionUtils.zip(this, collection2, resultFactory);
	}
	
	// Misc.
	
	/**
	 * Builds an unmodifiable view of this collection.
	 * 
	 * @return An unmodifiable view of this collection, or this collection when is it already unmodifiable.
	 * @see CollectionUtils#unmodifiable(Collection)
	 * @since 2.0
	 */
	@Override
	default ExCollection<E> unmodifiable() {
		return CollectionUtils.unmodifiable(this);
	}
}
