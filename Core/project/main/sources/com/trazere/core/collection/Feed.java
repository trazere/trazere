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
import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Predicate;
import com.trazere.core.functional.Thunk;
import com.trazere.core.imperative.Procedure;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@link Feed} interface defines lazy sequences of elements.
 * 
 * @param <E> Type of the elements.
 * @since 2.0
 */
@FunctionalInterface
public interface Feed<E>
extends Thunk<Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>>>, Iterable<E> {
	/**
	 * Tests whether this feed is empty.
	 * 
	 * @return <code>true</code> when the feed is empty, <code>false</code> otherwise.
	 * @since 2.0
	 */
	default boolean isEmpty() {
		return evaluate().isNone();
	}
	
	/**
	 * Gets the head element and tail of this feed.
	 * 
	 * @return The head element and tail.
	 * @throws NoSuchElementException When the feed is emtpy.
	 * @since 2.0
	 */
	default Tuple2<? extends E, ? extends Feed<? extends E>> get()
	throws NoSuchElementException {
		final Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> value = evaluate();
		if (value.isSome()) {
			return value.asSome().getValue();
		} else {
			throw new NoSuchElementException();
		}
	}
	
	/**
	 * Gets the head element of this feed.
	 * 
	 * @return The element.
	 * @throws NoSuchElementException When the feed is empty.
	 * @since 2.0
	 */
	default E getHead()
	throws NoSuchElementException {
		return get().get1();
	}
	
	/**
	 * Gets the head element of this feed.
	 * <p>
	 * This method supports empty feeds.
	 * 
	 * @return The head element, or nothing when the feed is empty.
	 * @since 2.0
	 */
	default Maybe<E> head() {
		return FeedUtils.head(this);
	}
	
	/**
	 * Gets the tail of this feed.
	 * 
	 * @return The tail.
	 * @throws NoSuchElementException When the feed is empty.
	 * @since 2.0
	 */
	default Feed<? extends E> getTail()
	throws NoSuchElementException {
		return get().get2();
	}
	
	/**
	 * Gets the tail of this feed.
	 * <p>
	 * This method supports empty feeds.
	 * 
	 * @return The of the feed, or an empty feed when the feed is empty.
	 * @since 2.0
	 */
	default Feed<? extends E> tail() {
		return FeedUtils.tail(this);
	}
	
	// Iterable.
	
	@Override
	default Iterator<E> iterator() {
		return new Iterator<E>() {
			private Feed<? extends E> _tail = Feed.this;
			
			@Override
			public boolean hasNext() {
				return !_tail.isEmpty();
			}
			
			@Override
			public E next()
			throws NoSuchElementException {
				final E head = _tail.getHead();
				_tail = _tail.getTail();
				return head;
			}
		};
	}
	
	// Tools.
	
	/**
	 * Executes the given procedure with each element of this feed.
	 * 
	 * @param procedure Procedure to execute.
	 * @since 2.0
	 */
	default void foreach(final Procedure<? super E> procedure) {
		FeedUtils.foreach(this, procedure);
	}
	
	/**
	 * Left folds over the elements of this feed using the given binary operator and initial state.
	 * 
	 * @param <S> Type of the state.
	 * @param operator Binary operator to use.
	 * @param initialState Initial state.
	 * @return The folded state.
	 * @since 2.0
	 */
	default <S> S fold(final Function2<? super S, ? super E, ? extends S> operator, final S initialState) {
		return FeedUtils.fold(this, operator, initialState);
	}
	
	/**
	 * Gets the first element of this feed accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the elements.
	 * @return The first accepted element.
	 * @since 2.0
	 */
	default Maybe<E> first(final Predicate<? super E> filter) {
		return FeedUtils.first(this, filter);
	}
	
	/**
	 * Gets the first element extracted from this feed by the given extractor.
	 * 
	 * @param <EE> Type of the extracted elements.
	 * @param collection Collection containing the elements to extract from.
	 * @param extractor Function to use to extract the elements.
	 * @return The first extracted element.
	 * @since 2.0
	 */
	default <EE> Maybe<EE> extractFirst(final Iterable<? extends E> collection, final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
		return FeedUtils.extractFirst(this, extractor);
	}
	
	/**
	 * Tests whether any element of this feed is accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the elements.
	 * @return <code>true</code> when some element is accepted, <code>false</code> when all elements are rejected.
	 * @since 2.0
	 */
	default boolean isAny(final Predicate<? super E> filter) {
		return FeedUtils.isAny(this, filter);
	}
	
	/**
	 * Tests whether all elements of this feed are accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the elements.
	 * @return <code>true</code> when all elements are accepted, <code>false</code> when some element is rejected.
	 * @since 2.0
	 */
	default boolean areAll(final Predicate<? super E> filter) {
		return FeedUtils.areAll(this, filter);
	}
	
	/**
	 * Counts the elements of this feed accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the elements.
	 * @return The number of accepted elements.
	 * @since 2.0
	 */
	default int count(final Predicate<? super E> filter) {
		return FeedUtils.count(this, filter);
	}
	
	/**
	 * Gets the least element of this feed according to the given comparator.
	 *
	 * @param comparator Comparator to use.
	 * @return The least element.
	 * @since 2.0
	 */
	default Maybe<E> least(final Comparator<? super E> comparator) {
		return FeedUtils.least(this, comparator);
	}
	
	/**
	 * Gets the greatest element of this feed according to the given comparator.
	 *
	 * @param comparator Comparator to use.
	 * @return The greatest element.
	 * @since 2.0
	 */
	default Maybe<E> greatest(final Comparator<? super E> comparator) {
		return FeedUtils.greatest(this, comparator);
	}
	
	/**
	 * Appends this feed to this feed.
	 * 
	 * @param appendedFeed Feed to append.
	 * @return A feed of the appended elements.
	 * @since 2.0
	 */
	default Feed<E> append(final Feed<? extends E> appendedFeed) {
		return FeedUtils.append(this, appendedFeed);
	}
	
	/**
	 * Takes the n first elements of this feed.
	 * 
	 * @param n Number of elements to take.
	 * @return A feed of the taken elements.
	 * @since 2.0
	 */
	default Feed<E> take(final int n) {
		return FeedUtils.take(this, n);
	}
	
	/**
	 * Takes the first elements of this feed while the given predicates holds.
	 * 
	 * @param predicate Filter predicate.
	 * @return A feed of the taken elements.
	 * @since 2.0
	 */
	default Feed<E> takeWhile(final Predicate<? super E> predicate) {
		return FeedUtils.takeWhile(this, predicate);
	}
	
	/**
	 * Drops the n first elements of this feed.
	 * 
	 * @param n Number of elements to drop.
	 * @return A feed of the remaining elements.
	 * @since 2.0
	 */
	default Feed<E> drop(final int n) {
		return FeedUtils.drop(this, n);
	}
	
	/**
	 * Drops the first elements of this feed while the given predicates holds.
	 * 
	 * @param predicate Filter predicate.
	 * @return A feed of the remaining elements.
	 * @since 2.0
	 */
	default Feed<E> dropWhile(final Predicate<? super E> predicate) {
		return FeedUtils.dropWhile(this, predicate);
	}
	
	/**
	 * Groups the elements of this feed into batches of the given size.
	 *
	 * @param <B> Type of the batch collections.
	 * @param n Number of elements of each batch.
	 * @param batchFactory Factory of the batch collections.
	 * @return A feed of the groups of elements.
	 * @since 2.0
	 */
	default <B extends Collection<? super E>> Feed<B> group(final int n, final CollectionFactory<? super E, B> batchFactory) {
		return FeedUtils.group(this, n, batchFactory);
	}
	
	/**
	 * Filters the elements of this feed using the given filter.
	 *
	 * @param filter Predicate to use to filter the elements.
	 * @return A feed of the filtered elements.
	 * @since 2.0
	 */
	default Feed<E> filter(final Predicate<? super E> filter) {
		return FeedUtils.filter(this, filter);
	}
	
	/**
	 * Transforms the elements of this feed using the given function.
	 *
	 * @param <TE> Type of the transformed elements.
	 * @param function Function to use to transform the elements.
	 * @return A feed of the transformed elements.
	 * @since 2.0
	 */
	default <TE> Feed<TE> map(final Function<? super E, ? extends TE> function) {
		return FeedUtils.map(this, function);
	}
	
	/**
	 * Transforms and flattens the elements of this feed using the given function.
	 * 
	 * @param <TE> Type of the transformed elements.
	 * @param function Function to use to transform the elements.
	 * @return An feed of the flatten, transformed elements.
	 * @since 2.0
	 */
	default <TE> Feed<TE> flatMap(final Function<? super E, ? extends Feed<? extends TE>> function) {
		return FeedUtils.flatMap(this, function);
	}
	
	/**
	 * Extracts the elements of this feed using the given extractor.
	 *
	 * @param <EE> Type of the extracted elements.
	 * @param extractor Function to use to extract the elements.
	 * @return A feed of the extracted elements.
	 * @since 2.0
	 */
	default <EE> Feed<EE> extract(final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
		return FeedUtils.extract(this, extractor);
	}
	
	/**
	 * Builds a memoized view of this feed.
	 * 
	 * @return The built feed.
	 * @see MemoizedFeed
	 * @since 2.0
	 */
	default Feed<E> memoized() {
		return FeedUtils.memoized(this);
	}
	
	/**
	 * Builds a memoized, resettable view of this feed.
	 * 
	 * @return The built feed.
	 * @see ResettableFeed
	 * @since 2.0
	 */
	default ResettableFeed<E> resettable() {
		return FeedUtils.resettable(this);
	}
	
	/**
	 * Builds a feed of pairs composed with the elements of this feeds and the given feed.
	 * <p>
	 * The pairs are composed of an element of each feed according in order. The extra values of the longest feed are dropped when this feeds don't contain the
	 * same number of elements.
	 * 
	 * @param <E2> Type of the second elements.
	 * @param feed2 Feed containing the second elements of the pairs.
	 * @return A feed of the pairs of elements.
	 * @since 2.0
	 */
	default <E2> Feed<Tuple2<E, E2>> zip(final Feed<? extends E2> feed2) {
		return FeedUtils.zip(this, feed2);
	}
}
