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

import com.trazere.core.imperative.IteratorUtils;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import com.trazere.core.util.Tuples;

/**
 * The {@link FeedUtils} class provides various utilities regarding {@link Feed feeds}.
 * 
 * @see Feed
 * @since 2.0
 */
public class FeedUtils {
	/**
	 * Gets the least element of the given feed according to their natural order.
	 *
	 * @param <E> Type of the elements.
	 * @param feed Feed of the elements.
	 * @return The least element.
	 * @since 2.0
	 */
	public static <E extends Comparable<E>> Maybe<E> least(final Feed<? extends E> feed) {
		return IteratorUtils.least(feed.iterator());
	}
	
	/**
	 * Gets the greatest element of the given feed according to their natural order.
	 *
	 * @param <E> Type of the elements.
	 * @param feed Feed of the elements.
	 * @return The greatest element.
	 * @since 2.0
	 */
	public static <E extends Comparable<E>> Maybe<E> greatest(final Feed<? extends E> feed) {
		return IteratorUtils.greatest(feed.iterator());
	}
	
	/**
	 * Appends the given feeds together.
	 * 
	 * @param <E> Type of the elements.
	 * @param feed1 First feed.
	 * @param feed2 Second feed.
	 * @return A feed of the appended elements.
	 * @since 2.0
	 */
	public static <E> Feed<E> append(final Feed<? extends E> feed1, final Feed<? extends E> feed2) {
		assert null != feed1;
		assert null != feed2;
		
		return new BaseMemoizedFeed<E>() {
			@Override
			protected Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> compute() {
				final Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> maybeItem1 = feed1.optionalItem();
				if (maybeItem1.isSome()) {
					final Tuple2<? extends E, ? extends Feed<? extends E>> item1 = maybeItem1.asSome().getValue();
					return Maybe.some(Tuples.tuple2(item1.get1(), FeedUtils.append(item1.get2(), feed2)));
				} else {
					return feed2.optionalItem();
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
	 * @since 2.0
	 */
	public static <E> Feed<E> flatten(final Feed<? extends Feed<? extends E>> feed) {
		assert null != feed;
		
		return new BaseMemoizedFeed<E>() {
			@Override
			protected Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> compute() {
				Feed<? extends Feed<? extends E>> iterFeed = feed;
				while (true) {
					final Maybe<? extends Tuple2<? extends Feed<? extends E>, ? extends Feed<? extends Feed<? extends E>>>> maybeFeedItem = iterFeed.optionalItem();
					if (maybeFeedItem.isSome()) {
						final Tuple2<? extends Feed<? extends E>, ? extends Feed<? extends Feed<? extends E>>> feedItem = maybeFeedItem.asSome().getValue();
						final Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> maybeItem = feedItem.get1().optionalItem();
						if (maybeItem.isSome()) {
							final Tuple2<? extends E, ? extends Feed<? extends E>> item = maybeItem.asSome().getValue();
							return Maybe.some(new Tuple2<>(item.get1(), FeedUtils.append(item.get2(), flatten(feedItem.get2()))));
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
	 * Builds a feed of pairs composed with the elements of the given feeds.
	 * <p>
	 * The pairs are composed of an element of each feed according in order. The extra values of the longest feed are dropped when the given feeds don't contain
	 * the same number of elements.
	 * 
	 * @param <E1> Type of the first elements.
	 * @param <E2> Type of the second elements.
	 * @param feed1 Feed containing the first elements of the pairs.
	 * @param feed2 Feed containing the second elements of the pairs.
	 * @return A feed of the pairs of elements.
	 * @since 2.0
	 */
	public static <E1, E2> Feed<Tuple2<E1, E2>> zip(final Feed<? extends E1> feed1, final Feed<? extends E2> feed2) {
		assert null != feed1;
		assert null != feed2;
		
		return new BaseMemoizedFeed<Tuple2<E1, E2>>() {
			@Override
			protected Maybe<? extends Tuple2<? extends Tuple2<E1, E2>, ? extends Feed<? extends Tuple2<E1, E2>>>> compute() {
				final Maybe<? extends Tuple2<? extends E1, ? extends Feed<? extends E1>>> maybeItem1 = feed1.optionalItem();
				final Maybe<? extends Tuple2<? extends E2, ? extends Feed<? extends E2>>> maybeItem2 = feed2.optionalItem();
				if (maybeItem1.isSome() && maybeItem2.isSome()) {
					final Tuple2<? extends E1, ? extends Feed<? extends E1>> item1 = maybeItem1.asSome().getValue();
					final Tuple2<? extends E2, ? extends Feed<? extends E2>> item2 = maybeItem2.asSome().getValue();
					return Maybe.some(new Tuple2<>(new Tuple2<>(item1.get1(), item2.get1()), FeedUtils.zip(item1.get2(), item2.get2())));
				} else {
					return Maybe.none();
				}
			}
		};
	}
	
	// TODO: unzip
	
	private FeedUtils() {
		// Prevent instantiation.
	}
}
