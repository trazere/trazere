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
package com.trazere.core.collection;

import com.trazere.core.functional.Function;
import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Predicate;
import com.trazere.core.imperative.IteratorUtils;
import com.trazere.core.imperative.Procedure;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import com.trazere.core.util.Tuples;
import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * The {@link FeedUtils} class provides various utilities regarding feeds.
 */
public class FeedUtils {
	/**
	 * Gets the head element of the given feed.
	 * <p>
	 * This method supports empty feeds.
	 * 
	 * @param <E> Type of the elements.
	 * @param feed Feed to read.
	 * @return The head element, or nothing when the feed is empty.
	 */
	public static <E> Maybe<E> head(final Feed<? extends E> feed) {
		return !feed.isEmpty() ? Maybe.<E>some(feed.getHead()) : Maybe.<E>none();
	}
	
	/**
	 * Gets the tail of the given feed.
	 * <p>
	 * This method supports empty feeds.
	 * 
	 * @param <E> Type of the elements.
	 * @param feed Feed to read.
	 * @return The of the feed, or an empty feed when the feed is empty.
	 */
	public static <E> Feed<? extends E> tail(final Feed<? extends E> feed) {
		return !feed.isEmpty() ? feed.getTail() : Feeds.<E>empty();
	}
	
	/**
	 * Executes the given procedure with each element of the given feed.
	 * 
	 * @param <E> Type of the elements.
	 * @param feed Feed of elements.
	 * @param procedure Procedure to execute.
	 */
	public static <E> void foreach(final Feed<? extends E> feed, final Procedure<? super E> procedure) {
		IteratorUtils.foreach(feed.iterator(), procedure);
	}
	
	/**
	 * Left folds over the elements of the given feed using the given binary operator and initial state.
	 * 
	 * @param <E> Type of the elements.
	 * @param <S> Type of the state.
	 * @param feed Feed of the elements to fold over.
	 * @param operator Binary operator to use.
	 * @param initialState Initial state.
	 * @return The folded state.
	 */
	public static <E, S> S fold(final Feed<? extends E> feed, final Function2<? super S, ? super E, ? extends S> operator, final S initialState) {
		return IteratorUtils.fold(feed.iterator(), operator, initialState);
	}
	
	/**
	 * Gets the first element of the given feed accepted by the given filter.
	 * 
	 * @param <E> Type of the elements.
	 * @param feed Feed of the elements to test.
	 * @param filter Predicate to use to filter the elements.
	 * @return The first accepted element.
	 */
	public static <E> Maybe<E> first(final Feed<? extends E> feed, final Predicate<? super E> filter) {
		return IteratorUtils.first(feed.iterator(), filter);
	}
	
	/**
	 * Gets the first element extracted from the given feed by the given extractor.
	 * 
	 * @param <E> Type of the elements.
	 * @param <EE> Type of the extracted elements.
	 * @param collection Collection containing the elements to extract from.
	 * @param extractor Function to use to extract the elements.
	 * @return The first extracted element.
	 */
	public static <E, EE> Maybe<EE> extractFirst(final Iterable<? extends E> collection, final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
		return IteratorUtils.extractFirst(collection.iterator(), extractor);
	}
	
	/**
	 * Tests whether any element of the given feed is accepted by the given filter.
	 * 
	 * @param <E> Type of the elements.
	 * @param feed Feed of the elements to test.
	 * @param filter Predicate to use to filter the elements.
	 * @return <code>true</code> when some element is accepted, <code>false</code> when all elements are rejected.
	 */
	public static <E> boolean isAny(final Feed<? extends E> feed, final Predicate<? super E> filter) {
		return IteratorUtils.isAny(feed.iterator(), filter);
	}
	
	/**
	 * Tests whether all elements of the given feed are accepted by the given filter.
	 * 
	 * @param <E> Type of the elements.
	 * @param feed Feed of the elements to test.
	 * @param filter Predicate to use to filter the elements.
	 * @return <code>true</code> when all elements are accepted, <code>false</code> when some element is rejected.
	 */
	public static <E> boolean areAll(final Feed<? extends E> feed, final Predicate<? super E> filter) {
		return IteratorUtils.areAll(feed.iterator(), filter);
	}
	
	/**
	 * Counts the elements of the given feed accepted by the given filter.
	 * 
	 * @param <E> Type of the elements.
	 * @param feed Feed of the elements to count.
	 * @param filter Predicate to use to filter the elements.
	 * @return The number of accepted elements.
	 */
	public static <E> int count(final Feed<? extends E> feed, final Predicate<? super E> filter) {
		return IteratorUtils.count(feed.iterator(), filter);
	}
	
	/**
	 * Gets the least element of the given feed according to their natural order.
	 *
	 * @param <E> Type of the elements.
	 * @param feed Feed of the elements.
	 * @return The least element.
	 */
	public static <E extends Comparable<E>> Maybe<E> least(final Feed<? extends E> feed) {
		return IteratorUtils.least(feed.iterator());
	}
	
	/**
	 * Gets the least element of the given feed according to the given comparator.
	 *
	 * @param <E> Type of the elements.
	 * @param feed Feed of the elements.
	 * @param comparator Comparator to use.
	 * @return The least element.
	 */
	public static <E> Maybe<E> least(final Feed<? extends E> feed, final Comparator<? super E> comparator) {
		return IteratorUtils.least(feed.iterator(), comparator);
	}
	
	/**
	 * Gets the greatest element of the given feed according to their natural order.
	 *
	 * @param <E> Type of the elements.
	 * @param feed Feed of the elements.
	 * @return The greatest element.
	 */
	public static <E extends Comparable<E>> Maybe<E> greatest(final Feed<? extends E> feed) {
		return IteratorUtils.greatest(feed.iterator());
	}
	
	/**
	 * Gets the greatest element of the given feed according to the given comparator.
	 *
	 * @param <E> Type of the elements.
	 * @param feed Feed of the elements.
	 * @param comparator Comparator to use.
	 * @return The greatest element.
	 */
	public static <E> Maybe<E> greatest(final Feed<? extends E> feed, final Comparator<? super E> comparator) {
		return IteratorUtils.greatest(feed.iterator(), comparator);
	}
	
	/**
	 * Memoizes the given feed.
	 * 
	 * @param <E> Type of the elements.
	 * @param feed Feed to memoize.
	 * @return The built feed.
	 */
	public static <E> Feed<E> memoize(final Feed<? extends E> feed) {
		assert null != feed;
		
		return new MemoizedFeed<E>() {
			@Override
			protected Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> compute() {
				final Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> maybeItem = feed.evaluate();
				if (maybeItem.isSome()) {
					final Tuple2<? extends E, ? extends Feed<? extends E>> item = maybeItem.asSome().getValue();
					return Maybe.some(new Tuple2<E, Feed<E>>(item.get1(), memoize(item.get2())));
				} else {
					return Maybe.none();
				}
			}
		};
	}
	
	/**
	 * Appends the given feeds together.
	 * 
	 * @param <E> Type of the elements.
	 * @param feed First feed.
	 * @param appendedFeed Second feed.
	 * @return The built feed.
	 */
	public static <E> Feed<E> append(final Feed<? extends E> feed, final Feed<? extends E> appendedFeed) {
		assert null != feed;
		assert null != appendedFeed;
		
		return new MemoizedFeed<E>() {
			@Override
			protected Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> compute() {
				final Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> maybeItem1 = feed.evaluate();
				if (maybeItem1.isSome()) {
					final Tuple2<? extends E, ? extends Feed<? extends E>> item1 = maybeItem1.asSome().getValue();
					return Maybe.some(Tuples.tuple2(item1.get1(), append(item1.get2(), appendedFeed)));
				} else {
					return appendedFeed.evaluate();
				}
			}
		};
	}
	
	/**
	 * Flattens the elements of the feeds of the given feed.
	 *
	 * @param <E> Type of the elements.
	 * @param feed Feed of the feeds of the elements to flatten.
	 * @return A feed of the flatten elements.
	 */
	public static <E> Feed<E> flatten(final Feed<? extends Feed<? extends E>> feed) {
		assert null != feed;
		
		return new MemoizedFeed<E>() {
			@Override
			protected Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> compute() {
				Feed<? extends Feed<? extends E>> iterFeed = feed;
				while (true) {
					final Maybe<? extends Tuple2<? extends Feed<? extends E>, ? extends Feed<? extends Feed<? extends E>>>> maybeFeedItem = iterFeed.evaluate();
					if (maybeFeedItem.isSome()) {
						final Tuple2<? extends Feed<? extends E>, ? extends Feed<? extends Feed<? extends E>>> feedItem = maybeFeedItem.asSome().getValue();
						final Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> maybeItem = feedItem.get1().evaluate();
						if (maybeItem.isSome()) {
							final Tuple2<? extends E, ? extends Feed<? extends E>> item = maybeItem.asSome().getValue();
							return Maybe.some(new Tuple2<>(item.get1(), append(item.get2(), flatten(feedItem.get2()))));
						} else {
							iterFeed = feedItem.get2();
						}
					} else {
						return Maybe.none();
					}
				}
			}
		};
	}
	
	/**
	 * Takes the n first elements of the given feed.
	 * 
	 * @param <E> Type of the elements.
	 * @param feed Feed to take from.
	 * @param n Number of elements to take.
	 * @return A feed of the taken elements.
	 */
	public static <E> Feed<E> take(final Feed<? extends E> feed, final int n) {
		assert null != feed;
		
		return new MemoizedFeed<E>() {
			@Override
			protected Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> compute() {
				if (n > 0) {
					final Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> maybeItem = feed.evaluate();
					if (maybeItem.isSome()) {
						final Tuple2<? extends E, ? extends Feed<? extends E>> item = maybeItem.asSome().getValue();
						return Maybe.some(new Tuple2<E, Feed<? extends E>>(item.get1(), take(item.get2(), n - 1)));
					} else {
						return Maybe.none();
					}
				} else {
					return Maybe.none();
				}
			}
		};
	}
	
	/**
	 * Takes the first elements of the given feed while the given predicates holds.
	 * 
	 * @param <E> Type of the elements.
	 * @param feed Feed to take from.
	 * @param predicate Filter predicate.
	 * @return A feed of the taken elements.
	 */
	public static <E> Feed<E> takeWhile(final Feed<? extends E> feed, final Predicate<? super E> predicate) {
		assert null != feed;
		assert null != predicate;
		
		return new MemoizedFeed<E>() {
			@Override
			protected Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> compute() {
				final Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> maybeItem = feed.evaluate();
				if (maybeItem.isSome()) {
					final Tuple2<? extends E, ? extends Feed<? extends E>> item = maybeItem.asSome().getValue();
					final E head = item.get1();
					if (predicate.evaluate(head)) {
						return Maybe.some(new Tuple2<E, Feed<? extends E>>(head, takeWhile(item.get2(), predicate)));
					} else {
						return Maybe.none();
					}
				} else {
					return Maybe.none();
				}
			}
		};
	}
	
	/**
	 * Drops the n first elements of the given feed.
	 * 
	 * @param <E> Type of the elements.
	 * @param feed Feed to drop from.
	 * @param n Number of elements to drop.
	 * @return A feed of the remaining elements.
	 */
	public static <E> Feed<E> drop(final Feed<? extends E> feed, final int n) {
		assert null != feed;
		
		return new MemoizedFeed<E>() {
			@Override
			protected Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> compute() {
				Feed<? extends E> iterFeed = feed;
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
	 * Drops the first elements of the given feed while the given predicates holds.
	 * 
	 * @param <E> Type of the elements.
	 * @param feed Feed to drop from.
	 * @param predicate Filter predicate.
	 * @return A feed of the remaining elements.
	 */
	public static <E> Feed<E> dropWhile(final Feed<? extends E> feed, final Predicate<? super E> predicate) {
		assert null != feed;
		assert null != predicate;
		
		return new MemoizedFeed<E>() {
			@Override
			protected Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> compute() {
				Feed<? extends E> iterFeed = feed;
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
	 * Groups the elements of the given feed into batches of the given size.
	 *
	 * @param <E> Type of the elements.
	 * @param <B> Type of the batch collections.
	 * @param feed Feed containing the elements to group.
	 * @param n Number of elements of each batch.
	 * @param batchFactory Factory of the batch collections.
	 * @return A feed of the groups of elements.
	 */
	public static <E, B extends Collection<? super E>> Feed<B> group(final Feed<? extends E> feed, final int n, final CollectionFactory<? super E, B> batchFactory) {
		assert null != feed;
		assert null != batchFactory;
		
		return new MemoizedFeed<B>() {
			@Override
			protected Maybe<? extends Tuple2<? extends B, ? extends Feed<? extends B>>> compute() {
				Feed<? extends E> iterFeed = feed;
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
					return Maybe.some(new Tuple2<>(batch, group(iterFeed, n, batchFactory)));
				}
			}
		};
	}
	
	/**
	 * Filters the elements of the given feed using the given filter.
	 *
	 * @param <E> Type of the elements.
	 * @param feed Feed of the elements to filter.
	 * @param filter Predicate to use to filter the elements.
	 * @return A feed of the filtered elements.
	 */
	public static <E> Feed<E> filter(final Feed<? extends E> feed, final Predicate<? super E> filter) {
		assert null != feed;
		assert null != filter;
		
		return new MemoizedFeed<E>() {
			@Override
			protected Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> compute() {
				Feed<? extends E> iterFeed = feed;
				while (true) {
					final Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> maybeItem = iterFeed.evaluate();
					if (maybeItem.isSome()) {
						final Tuple2<? extends E, ? extends Feed<? extends E>> item = maybeItem.asSome().getValue();
						final E head = item.get1();
						if (filter.evaluate(head)) {
							return Maybe.some(new Tuple2<E, Feed<E>>(head, filter(item.get2(), filter)));
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
	 * Transforms the elements of the given feed using the given function.
	 *
	 * @param <E> Type of the elements.
	 * @param <TE> Type of the transformed elements.
	 * @param feed Feed of the elements to transform.
	 * @param function Function to use to transform the elements.
	 * @return A feed of the transformed elements.
	 */
	public static <E, TE> Feed<TE> map(final Feed<? extends E> feed, final Function<? super E, ? extends TE> function) {
		assert null != feed;
		assert null != function;
		
		return new Feed<TE>() {
			// Feed.
			
			@Override
			public boolean isEmpty() {
				return feed.isEmpty();
			}
			
			@Override
			public TE getHead()
			throws NoSuchElementException {
				return function.evaluate(feed.getHead());
			}
			
			@Override
			public Feed<TE> getTail()
			throws NoSuchElementException {
				return map(feed.getTail(), function);
			}
			
			// Function.
			
			@Override
			public Maybe<? extends Tuple2<? extends TE, ? extends Feed<? extends TE>>> evaluate() {
				return feed.evaluate().map(item -> new Tuple2<>(function.evaluate(item.get1()), map(item.get2(), function)));
			}
		};
	}
	
	/**
	 * Transforms and flattens the elements of the given feed using the given function.
	 * 
	 * @param <E> Type of the elements.
	 * @param <TE> Type of the transformed elements.
	 * @param feed Feed of the elements to transform.
	 * @param function Function to use to transform the elements.
	 * @return An feed of the flatten, transformed elements.
	 */
	public static <E, TE> Feed<TE> flatMap(final Feed<? extends E> feed, final Function<? super E, ? extends Feed<? extends TE>> function) {
		return flatten(map(feed, function));
	}
	
	/**
	 * Extracts the elements of the given feed using the given extractor.
	 *
	 * @param <E> Type of the elements.
	 * @param <EE> Type of the extracted elements.
	 * @param feed Feed of the elements to extract from.
	 * @param extractor Function to use to extract the elements.
	 * @return A feed of the extracted elements.
	 */
	public static <E, EE> Feed<EE> extract(final Feed<? extends E> feed, final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
		assert null != extractor;
		
		return flatMap(feed, element -> Feeds.fromMaybe(extractor.evaluate(element)));
	}
	
	/**
	 * Composes pairs with the elements of the given feeds.
	 * <p>
	 * The pairs are composed of an element of each feed according in order. The extra values of the longest feed are dropped when the given feeds don't contain
	 * the same number of elements.
	 * 
	 * @param <E1> Type of the first elements.
	 * @param <E2> Type of the second elements.
	 * @param feed1 Feed containing the first elements of the pairs.
	 * @param feed2 Feed containing the second elements of the pairs.
	 * @return A feed of the pairs of elements.
	 */
	public static <E1, E2> Feed<Tuple2<E1, E2>> zip(final Feed<? extends E1> feed1, final Feed<? extends E2> feed2) {
		return new MemoizedFeed<Tuple2<E1, E2>>() {
			@Override
			protected Maybe<? extends Tuple2<? extends Tuple2<E1, E2>, ? extends Feed<? extends Tuple2<E1, E2>>>> compute() {
				final Maybe<? extends Tuple2<? extends E1, ? extends Feed<? extends E1>>> maybeItem1 = feed1.evaluate();
				final Maybe<? extends Tuple2<? extends E2, ? extends Feed<? extends E2>>> maybeItem2 = feed2.evaluate();
				if (maybeItem1.isSome() && maybeItem2.isSome()) {
					final Tuple2<? extends E1, ? extends Feed<? extends E1>> item1 = maybeItem1.asSome().getValue();
					final Tuple2<? extends E2, ? extends Feed<? extends E2>> item2 = maybeItem2.asSome().getValue();
					return Maybe.some(new Tuple2<>(new Tuple2<>(item1.get1(), item2.get1()), zip(item1.get2(), item2.get2())));
				} else {
					return Maybe.none();
				}
			}
		};
	}
	
	private FeedUtils() {
		// Prevent instantiation.
	}
}
