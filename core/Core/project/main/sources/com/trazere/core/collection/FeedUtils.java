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

import com.trazere.core.functional.Predicate;
import com.trazere.core.lang.IterableUtils;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.Comparator;

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
	 * Gets the least element of the given feed according to their natural order.
	 *
	 * @param <E> Type of the elements.
	 * @param feed Feed of the elements.
	 * @return The least element.
	 */
	public static <E extends Comparable<E>> Maybe<? extends E> least(final Feed<? extends E> feed) {
		return IterableUtils.least(feed);
	}
	
	/**
	 * Gets the least element of the given feed according to the given comparator.
	 *
	 * @param <E> Type of the elements.
	 * @param feed Feed of the elements.
	 * @param comparator Comparator to use.
	 * @return The least element.
	 */
	public static <E> Maybe<? extends E> least(final Feed<? extends E> feed, final Comparator<? super E> comparator) {
		return IterableUtils.least(feed, comparator);
	}
	
	/**
	 * Gets the greatest element of the given feed according to their natural order.
	 *
	 * @param <E> Type of the elements.
	 * @param feed Feed of the elements.
	 * @return The greatest element.
	 */
	public static <E extends Comparable<E>> Maybe<? extends E> greatest(final Feed<? extends E> feed) {
		return IterableUtils.greatest(feed);
	}
	
	/**
	 * Gets the greatest element of the given feed according to the given comparator.
	 *
	 * @param <E> Type of the elements.
	 * @param feed Feed of the elements.
	 * @param comparator Comparator to use.
	 * @return The greatest element.
	 */
	public static <E> Maybe<? extends E> greatest(final Feed<? extends E> feed, final Comparator<? super E> comparator) {
		return IterableUtils.greatest(feed, comparator);
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
	
	// TODO: fold
	
	//	/**
	//	 * Filters the elements of the given feed using the given predicate.
	//	 *
	//	 * @param <T> Type of the elements.
	//	 * @param feed Feed to filter.
	//	 * @param filter Filter to use.
	//	 * @return The built feed over the filtered elements.
	//	 */
	//	public static <T> Feed<T> filter(final Feed<? extends T> feed, final Predicate<? super T> filter) {
	//		assert null != feed;
	//		assert null != filter;
	//
	//		return new MemoizedFeed<T>() {
	//			// Function.
	//
	//			@Override
	//			protected Maybe<? extends Tuple2<T, ? extends Feed<? extends T>>> compute() {
	//				Feed<? extends T> tail = feed;
	//				while (!tail.isEmpty()) {
	//					final T head = tail.getHead();
	//					if (filter.evaluate(head)) {
	//						return Maybe.some(Tuples.tuple2(head, filter(tail.getTail(), filter)));
	//					} else {
	//						tail = tail.getTail();
	//					}
	//				}
	//				return Maybe.none();
	//			}
	//		};
	//	}
	//
	//	/**
	//	 * Transforms the elements of the given feed using the given function.
	//	 *
	//	 * @param <T> Type of the elements.
	//	 * @param <R> Type of the transformed elements.
	//	 * @param feed Feed to transform.
	//	 * @param function Transform function to use.
	//	 * @return The built feed over the transformed elements.
	//	 */
	//	public static <T, R> Feed<R> map(final Feed<? extends T> feed, final Function<? super T, ? extends R> function) {
	//		assert null != feed;
	//		assert null != function;
	//
	//		return new BaseFeed<R>() {
	//			// Feed.
	//
	//			@Override
	//			public boolean isEmpty() {
	//				return feed.isEmpty();
	//			}
	//
	//			@Override
	//			public R getHead()
	//			throws NoSuchElementException {
	//				return function.evaluate(feed.getHead());
	//			}
	//
	//			@Override
	//			public Feed<R> getTail()
	//			throws NoSuchElementException {
	//				return map(feed.getTail(), function);
	//			}
	//
	//			// Function.
	//
	//			@Override
	//			public Maybe<Tuple2<R, Feed<R>>> evaluate() {
	//				final Maybe<? extends Tuple2<? extends T, ? extends Feed<? extends T>>> maybeItem = feed.evaluate();
	//				if (maybeItem.isSome()) {
	//					final Tuple2<? extends T, ? extends Feed<? extends T>> item = maybeItem.asSome().getValue();
	//					return Maybe.some(new Tuple2<R, Feed<R>>(function.evaluate(item.get1()), map(item.get2(), function)));
	//				} else {
	//					return Maybe.none();
	//				}
	//			}
	//		};
	//	}
	//
	//	/**
	//	 * Builds a feed that extracts values from the given feed using the given extractor.
	//	 *
	//	 * @param <T> Type of the elements.
	//	 * @param <R> Type of the transformed elements.
	//	 * @param <X> Type of the exceptions.
	//	 * @param extractor The extractor.
	//	 * @param feed The feed.
	//	 * @return The built feed over the filtered and transformed elements.
	//	 */
	//	public static <T, R, X extends Exception> Feed<R, X> extract(final Function1<? super T, ? extends Maybe<? extends R>, ? extends X> extractor, final Feed<? extends T, ? extends X> feed) {
	//		assert null != extractor;
	//		assert null != feed;
	//
	//		return new MemoizedFeed<R, X>() {
	//			// Function.
	//
	//			@Override
	//			protected Maybe<Tuple2<R, Feed<R, X>>> compute()
	//			throws X {
	//				Feed<? extends T, ? extends X> tail = feed;
	//				while (!tail.isEmpty()) {
	//					final Maybe<? extends R> head = extractor.evaluate(tail.getHead());
	//					if (head.isSome()) {
	//						return Maybe.some(new Tuple2<R, Feed<R, X>>(head.asSome().getValue(), extract(extractor, tail.getTail())));
	//					} else {
	//						tail = tail.getTail();
	//					}
	//				}
	//				return Maybe.none();
	//			}
	//		};
	//	}
	
	private FeedUtils() {
		// Prevent instantiation.
	}
}
