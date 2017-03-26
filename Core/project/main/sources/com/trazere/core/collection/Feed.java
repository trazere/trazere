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
import com.trazere.core.imperative.ExIterator;
import com.trazere.core.lang.ExIterable;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.Collection;
import java.util.NoSuchElementException;

// TODO: comparison and hashing ?

/**
 * The {@link Feed} interface defines lazy sequences of elements.
 * 
 * @param <E> Type of the elements.
 * @since 2.0
 */
public interface Feed<E>
extends ExIterable<E> {
	/**
	 * Tests whether this feed is empty.
	 * 
	 * @return <code>true</code> when the feed is empty, <code>false</code> otherwise.
	 * @since 2.0
	 */
	boolean isEmpty();
	
	/**
	 * Gets the head element of this feed.
	 * 
	 * @return The element.
	 * @throws NoSuchElementException When the feed is empty.
	 * @since 2.0
	 */
	E head()
	throws NoSuchElementException;
	
	/**
	 * Gets the head element of this feed.
	 * <p>
	 * This method supports empty feeds.
	 *
	 * @return The head element, or nothing when the feed is empty.
	 * @see #head()
	 * @since 2.0
	 */
	Maybe<E> optionalHead();
	
	/**
	 * Gets the tail of this feed.
	 * 
	 * @return The tail.
	 * @throws NoSuchElementException When the feed is empty.
	 * @since 2.0
	 */
	Feed<? extends E> tail()
	throws NoSuchElementException;
	
	/**
	 * Gets the tail of this feed.
	 * <p>
	 * This method supports empty feeds.
	 *
	 * @return The tail of the feed, or nothing when the feed is empty.
	 * @since 2.0
	 */
	Maybe<? extends Feed<? extends E>> optionalTail();
	
	/**
	 * Gets the head and tail of this feed.
	 * 
	 * @return The head and tail.
	 * @throws NoSuchElementException When the feed is emtpy.
	 * @since 2.0
	 */
	Tuple2<? extends E, ? extends Feed<? extends E>> item()
	throws NoSuchElementException;
	
	/**
	 * Gets the head and tail of this feed.
	 * <p>
	 * This method supports empty feeds.
	 * 
	 * @return The head and tail, or nothing when the feed is empty.
	 * @since 2.0
	 */
	Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> optionalItem();
	
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
				final Tuple2<? extends E, ? extends Feed<? extends E>> item = _tail.item();
				_tail = item.get2();
				return item.get1();
			}
		};
	}
	
	// Traversable.
	
	/**
	 * Takes n elements of this feed.
	 * 
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
					return self.optionalItem().map(item -> new Tuple2<>(item.get1(), item.get2().take(n - 1)));
				} else {
					return Maybe.none();
				}
			}
		};
	}
	
	/**
	 * Takes the elements of this feed while the given predicates holds.
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
				return self.optionalItem().flatMap((final Tuple2<? extends E, ? extends Feed<? extends E>> item) -> {
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
	 * Drops n elements of this feed.
	 * 
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
					final Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> maybeItem = iterFeed.optionalItem();
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
	 * Drops the elements of this feed while the given predicates holds.
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
					final Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> maybeItem = iterFeed.optionalItem();
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
	 * @return A feed of the batches of elements.
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
					final Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> maybeItem = iterFeed.optionalItem();
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
					final Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> maybeItem = iterFeed.optionalItem();
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
	 * Transforms the elements of this feed using the given function.
	 *
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
			public Maybe<? extends Feed<? extends TE>> optionalTail() {
				return self.optionalTail().map(tail -> tail.map(function));
			}
			
			@Override
			public Tuple2<? extends TE, ? extends Feed<? extends TE>> item()
			throws NoSuchElementException {
				final Tuple2<? extends E, ? extends Feed<? extends E>> item = self.item();
				return new Tuple2<>(function.evaluate(item.get1()), item.get2().map(function));
			}
			
			@Override
			public Maybe<? extends Tuple2<? extends TE, ? extends Feed<? extends TE>>> optionalItem() {
				return self.optionalItem().map(item -> new Tuple2<>(function.evaluate(item.get1()), item.get2().map(function)));
			}
			
			// Iterable.
			
			@Override
			public ExIterator<TE> iterator() {
				return self.iterator().map(function);
			}
		};
	}
	
	/**
	 * Extracts the elements from the elements of this feed using the given extractor.
	 *
	 * @return A feed of the extracted elements.
	 * @since 2.0
	 */
	@Override
	default <EE> Feed<EE> extract(final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
		return flatMap(element -> Feeds.fromMaybe(extractor.evaluate(element)));
	}
	
	/**
	 * Gets all elements extracted from the elements of this feed using the given extractor.
	 * 
	 * @param <EE> Type of the extracted elements.
	 * @param extractor Function to use to extract the elements.
	 * @return A feed of the extracted elements.
	 * @since 2.0
	 */
	@Override
	default <EE> Feed<EE> extractAll(final Function<? super E, ? extends Iterable<? extends EE>> extractor) {
		return flatMap(element -> Feeds.fromIterable(extractor.evaluate(element)));
	}
	
	/**
	 * Appends the given feed to this feed.
	 * 
	 * @param appendedFeed Feed to append.
	 * @return A feed of the appended elements.
	 * @see FeedUtils#append(Feed, Feed)
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
	 * @see FeedUtils#flatten(Feed)
	 * @since 2.0
	 */
	default <TE> Feed<TE> flatMap(final Function<? super E, ? extends Feed<? extends TE>> function) {
		return FeedUtils.flatten(map(function));
	}
	
	// TODO: zip
	
	// Misc.
	
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
					return self.optionalItem().map(item -> new Tuple2<>(item.get1(), item.get2().memoized()));
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
			return new BaseResettableFeed<E>() {
				@Override
				protected Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> compute() {
					return self.optionalItem().map(item -> new Tuple2<>(item.get1(), item.get2()));
				}
			};
		}
	}
}
