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
import com.trazere.core.imperative.ExIterator;
import com.trazere.core.imperative.Procedure;
import com.trazere.core.lang.Traversable;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.MaybeUtils;
import com.trazere.core.util.Tuple2;
import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * The {@link Feed} interface defines lazy sequences of elements.
 * 
 * @param <E> Type of the elements.
 * @since 2.0
 */
@FunctionalInterface
public interface Feed<E>
extends Thunk<Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>>>, Traversable<E>, Iterable<E> {
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
		return evaluate().get(() -> {
			throw new NoSuchElementException();
		});
	}
	
	/**
	 * Gets the head element of this feed.
	 * 
	 * @return The element.
	 * @throws NoSuchElementException When the feed is empty.
	 * @since 2.0
	 */
	default E head()
	throws NoSuchElementException {
		return get().get1();
	}
	
	/**
	 * Gets the head element of this feed.
	 * <p>
	 * This method supports empty feeds.
	 *
	 * @return The head element, or nothing when the feed is empty.
	 * @see #head()
	 * @since 2.0
	 */
	default Maybe<E> optionalHead() {
		return evaluate().map(Tuple2::get1);
	}
	
	/**
	 * Gets the tail of this feed.
	 * 
	 * @return The tail.
	 * @throws NoSuchElementException When the feed is empty.
	 * @since 2.0
	 */
	default Feed<? extends E> tail()
	throws NoSuchElementException {
		return get().get2();
	}
	
	/**
	 * Gets the tail of this feed.
	 * <p>
	 * This method supports empty feeds.
	 *
	 * @return The tail of the feed, or an empty feed when the feed is empty.
	 * @since 2.0
	 */
	default Feed<? extends E> optionalTail() {
		return MaybeUtils.<Feed<? extends E>>get(evaluate().map(Tuple2::get2), Feeds.empty()); // HACK: explicit type argument to work around a bug of javac
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
	@Override
	default <S> S fold(final Function2<? super S, ? super E, ? extends S> operator, final S initialState) {
		return iterator().fold(operator, initialState);
	}
	
	/**
	 * Tests whether any element of this feed is accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the elements.
	 * @return <code>true</code> when some element is accepted, <code>false</code> when all elements are rejected.
	 * @since 2.0
	 */
	@Override
	default boolean isAny(final Predicate<? super E> filter) {
		return iterator().isAny(filter);
	}
	
	/**
	 * Tests whether all elements of this feed are accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the elements.
	 * @return <code>true</code> when all elements are accepted, <code>false</code> when some element is rejected.
	 * @since 2.0
	 */
	@Override
	default boolean areAll(final Predicate<? super E> filter) {
		return iterator().areAll(filter);
	}
	
	/**
	 * Counts the elements of this feed accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the elements.
	 * @return The number of accepted elements.
	 * @since 2.0
	 */
	@Override
	default int count(final Predicate<? super E> filter) {
		return iterator().count(filter);
	}
	
	/**
	 * Gets the least element of this feed according to the given comparator.
	 *
	 * @param comparator Comparator to use.
	 * @return The least element.
	 * @since 2.0
	 */
	@Override
	default Maybe<E> least(final Comparator<? super E> comparator) {
		return iterator().least(comparator);
	}
	
	/**
	 * Gets the greatest element of this feed according to the given comparator.
	 *
	 * @param comparator Comparator to use.
	 * @return The greatest element.
	 * @since 2.0
	 */
	@Override
	default Maybe<E> greatest(final Comparator<? super E> comparator) {
		return iterator().greatest(comparator);
	}
	
	/**
	 * Takes the n first elements of this feed.
	 * 
	 * @param n Number of elements to take.
	 * @return A feed of the taken elements.
	 * @since 2.0
	 */
	@Override
	default Feed<E> take(final int n) {
		final Feed<E> self = this;
		return new BaseMemoizedFeed<E>() {
			@Override
			protected Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> compute() {
				if (n > 0) {
					return self.evaluate().map(item -> new Tuple2<>(item.get1(), item.get2().take(n - 1)));
				} else {
					return Maybe.none();
				}
			}
		};
	}
	
	/**
	 * Takes the first elements of this feed while the given predicates holds.
	 * 
	 * @param predicate Filter predicate.
	 * @return A feed of the taken elements.
	 * @since 2.0
	 */
	default Feed<E> takeWhile(final Predicate<? super E> predicate) {
		assert null != predicate;
		
		final Feed<E> self = this;
		return new BaseMemoizedFeed<E>() {
			@Override
			protected Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> compute() {
				return self.evaluate().flatMap((final Tuple2<? extends E, ? extends Feed<? extends E>> item) -> {
					final E head = item.get1();
					if (predicate.evaluate(head)) {
						return Maybe.some(new Tuple2<>(head, item.get2().takeWhile(predicate)));
					} else {
						return Maybe.none();
					}
				});
			}
		};
	}
	
	/**
	 * Drops the n first elements of this feed.
	 * 
	 * @param n Number of elements to drop.
	 * @return A feed of the remaining elements.
	 * @since 2.0
	 */
	@Override
	default Feed<E> drop(final int n) {
		final Feed<E> self = this;
		return new BaseMemoizedFeed<E>() {
			@Override
			protected Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> compute() {
				Feed<? extends E> iterFeed = self;
				int iterN = n;
				while (true) {
					final Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> maybeItem = iterFeed.evaluate();
					if (maybeItem.isSome()) {
						if (iterN > 0) {
							iterFeed = maybeItem.asSome().getValue().get2();
							iterN -= 1;
						} else {
							return maybeItem;
						}
					} else {
						return Maybe.none();
					}
				}
			}
		};
	}
	
	/**
	 * Drops the first elements of this feed while the given predicates holds.
	 * 
	 * @param predicate Filter predicate.
	 * @return A feed of the remaining elements.
	 * @since 2.0
	 */
	default Feed<E> dropWhile(final Predicate<? super E> predicate) {
		assert null != predicate;
		
		final Feed<E> self = this;
		return new BaseMemoizedFeed<E>() {
			@Override
			protected Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> compute() {
				Feed<? extends E> iterFeed = self;
				while (true) {
					final Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> maybeItem = iterFeed.evaluate();
					if (maybeItem.isSome()) {
						final Tuple2<? extends E, ? extends Feed<? extends E>> item = maybeItem.asSome().getValue();
						if (predicate.evaluate(item.get1())) {
							iterFeed = item.get2();
						} else {
							return maybeItem;
						}
					} else {
						return Maybe.none();
					}
				}
			}
		};
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
	@Override
	default <B extends Collection<? super E>> Feed<B> group(final int n, final CollectionFactory<? super E, B> batchFactory) {
		assert null != batchFactory;
		
		final Feed<E> self = this;
		return new BaseMemoizedFeed<B>() {
			@Override
			protected Maybe<? extends Tuple2<? extends B, ? extends Feed<? extends B>>> compute() {
				Feed<? extends E> iterFeed = self;
				int iterN = n;
				final B batch = batchFactory.build(n);
				while (iterN > 0) {
					final Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> maybeItem = iterFeed.evaluate();
					if (maybeItem.isSome()) {
						final Tuple2<? extends E, ? extends Feed<? extends E>> item = maybeItem.asSome().getValue();
						batch.add(item.get1());
						iterFeed = item.get2();
						iterN -= 1;
					} else {
						break;
					}
				}
				if (batch.isEmpty()) {
					return Maybe.none();
				} else {
					return Maybe.some(new Tuple2<>(batch, iterFeed.group(n, batchFactory)));
				}
			}
		};
	}
	
	/**
	 * Filters the elements of this feed using the given filter.
	 *
	 * @param filter Predicate to use to filter the elements.
	 * @return A feed of the filtered elements.
	 * @since 2.0
	 */
	@Override
	default Feed<E> filter(final Predicate<? super E> filter) {
		assert null != filter;
		
		final Feed<E> self = this;
		return new BaseMemoizedFeed<E>() {
			@Override
			protected Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> compute() {
				Feed<? extends E> iterFeed = self;
				while (true) {
					final Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> maybeItem = iterFeed.evaluate();
					if (maybeItem.isSome()) {
						final Tuple2<? extends E, ? extends Feed<? extends E>> item = maybeItem.asSome().getValue();
						final E head = item.get1();
						if (filter.evaluate(head)) {
							return Maybe.some(new Tuple2<>(head, item.get2().filter(filter)));
						} else {
							iterFeed = item.get2();
						}
					} else {
						return Maybe.none();
					}
				}
			}
		};
	}
	
	/**
	 * Gets the first element of this feed accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the elements.
	 * @return The first accepted element.
	 * @since 2.0
	 */
	@Override
	default Maybe<E> filterFirst(final Predicate<? super E> filter) {
		return iterator().filterFirst(filter);
	}
	
	/**
	 * Transforms the elements of this feed using the given function.
	 *
	 * @param <TE> Type of the transformed elements.
	 * @param function Function to use to transform the elements.
	 * @return A feed of the transformed elements.
	 * @since 2.0
	 */
	@Override
	default <TE> Feed<TE> map(final Function<? super E, ? extends TE> function) {
		assert null != function;
		
		final Feed<E> self = this;
		return new Feed<TE>() {
			// Feed.
			
			@Override
			public boolean isEmpty() {
				return self.isEmpty();
			}
			
			@Override
			public TE head()
			throws NoSuchElementException {
				return function.evaluate(self.head());
			}
			
			@Override
			public Maybe<TE> optionalHead() {
				return self.optionalHead().map(function);
			}
			
			@Override
			public Feed<TE> tail()
			throws NoSuchElementException {
				return self.tail().map(function);
			}
			
			@Override
			public Feed<? extends TE> optionalTail() {
				return self.optionalTail().map(function);
			}
			
			// TODO: optimize defaults
			
			// Function.
			
			@Override
			public Maybe<? extends Tuple2<? extends TE, ? extends Feed<? extends TE>>> evaluate() {
				return self.evaluate().map(item -> new Tuple2<>(function.evaluate(item.get1()), item.get2().map(function)));
			}
			
			// Iterable.
			
			@Override
			public ExIterator<TE> iterator() {
				return self.iterator().map(function);
			}
		};
	}
	
	/**
	 * Extracts the elements of this feed using the given extractor.
	 *
	 * @param <EE> Type of the extracted elements.
	 * @param extractor Function to use to extract the elements.
	 * @return A feed of the extracted elements.
	 * @since 2.0
	 */
	@Override
	default <EE> Feed<EE> extract(final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
		return flatMap(element -> Feeds.fromMaybe(extractor.evaluate(element)));
	}
	
	/**
	 * Gets the first element extracted from this feed by the given extractor.
	 * 
	 * @param <EE> Type of the extracted elements.
	 * @param extractor Function to use to extract the elements.
	 * @return The first extracted element.
	 * @since 2.0
	 */
	@Override
	default <EE> Maybe<EE> extractFirst(final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
		return iterator().extractFirst(extractor);
	}
	
	/**
	 * Gets all elements extracted from the elements of this feed by the given extractor.
	 * 
	 * @param <EE> Type of the extracted elements.
	 * @param extractor Function to use to extract the elements.
	 * @return A feed of the extracted elements.
	 * @since 2.0
	 */
	default <EE> Feed<EE> extractAll(final Function<? super E, ? extends Iterable<? extends EE>> extractor) {
		return flatMap(element -> Feeds.fromIterable(extractor.evaluate(element)));
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
	 * Transforms and flattens the elements of this feed using the given function.
	 * 
	 * @param <TE> Type of the transformed elements.
	 * @param function Function to use to transform the elements.
	 * @return An feed of the flatten, transformed elements.
	 * @since 2.0
	 */
	default <TE> Feed<TE> flatMap(final Function<? super E, ? extends Feed<? extends TE>> function) {
		return FeedUtils.flatten(map(function));
	}
	
	/**
	 * Executes the given procedure with each element of this feed.
	 * 
	 * @param procedure Procedure to execute.
	 * @since 2.0
	 */
	@Override
	default void foreach(final Procedure<? super E> procedure) {
		iterator().foreach(procedure);
	}
	
	/**
	 * Builds a memoized view of this feed.
	 * 
	 * @return The built feed.
	 * @see MemoizedFeed
	 * @since 2.0
	 */
	default MemoizedFeed<E> memoized() {
		if (this instanceof MemoizedFeed) {
			return (MemoizedFeed<E>) this;
		} else {
			final Feed<E> self = this;
			return new BaseMemoizedFeed<E>() {
				@Override
				protected Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> compute() {
					return self.evaluate().map(item -> new Tuple2<>(item.get1(), item.get2().memoized()));
				}
			};
		}
	}
	
	/**
	 * Builds a memoized, resettable view of this feed.
	 * 
	 * @return The built feed.
	 * @see ResettableFeed
	 * @since 2.0
	 */
	default ResettableFeed<E> resettable() {
		if (this instanceof ResettableFeed) {
			return (ResettableFeed<E>) this;
		} else {
			final Feed<E> self = this;
			return new ResettableFeed<E>() {
				@Override
				protected Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> compute() {
					return self.evaluate().map(item -> new Tuple2<>(item.get1(), item.get2()));
				}
			};
		}
	}
	
	// Iterable.
	
	@Override
	default ExIterator<E> iterator() {
		return new ExIterator<E>() {
			private Feed<? extends E> _tail = Feed.this;
			
			@Override
			public boolean hasNext() {
				return !_tail.isEmpty();
			}
			
			@Override
			public E next()
			throws NoSuchElementException {
				final Tuple2<? extends E, ? extends Feed<? extends E>> item = _tail.get();
				_tail = item.get2();
				return item.get1();
			}
		};
	}
}
