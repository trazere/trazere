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
package com.trazere.util.feed;

import com.trazere.util.collection.CheckedIterators;
import com.trazere.util.collection.CollectionUtils;
import com.trazere.util.type.Maybe;
import java.util.Collection;

/**
 * The {@link FeedUtils} class provides various utilities regarding feeds.
 * 
 * @deprecated Use core.
 */
@Deprecated
public class FeedUtils {
	// TODO: rename to head
	/**
	 * Gets the next value from the given feed.
	 * 
	 * @param <T> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param feed The feed.
	 * @return The next value.
	 * @throws X When the retrieval of the next value fails.
	 * @deprecated Use {@link com.trazere.core.collection.FeedUtils#head(com.trazere.core.collection.Feed)}.
	 */
	@Deprecated
	public static <T, X extends Exception> Maybe<T> next(final Feed<? extends T, ? extends X> feed)
	throws X {
		assert null != feed;
		
		return !feed.isEmpty() ? Maybe.<T>some(feed.getHead()) : Maybe.<T>none();
	}
	
	// TODO: rename to take
	/**
	 * Drains all elements from the given checked iterator and populates the given collection with them.
	 * 
	 * @param <T> Type of the elements.
	 * @param <C> Type of the result collection.
	 * @param <X> Type of the exceptions.
	 * @param feed The feed.
	 * @param results The collection to populate with the elements.
	 * @return The result collection.
	 * @throws X When the retrieval of some value fails.
	 * @deprecated Use {@link com.trazere.core.imperative.IteratorUtils#drain(java.util.Iterator, Collection)}.
	 */
	@Deprecated
	public static <T, C extends Collection<? super T>, X extends Exception> C drain(final Feed<? extends T, ? extends X> feed, final C results)
	throws X {
		assert null != feed;
		assert null != results;
		
		return CollectionUtils.drain(CheckedIterators.fromFeed(feed), results);
	}
	
	// TODO: rename to take
	/**
	 * Drains n elements from the given iterator and populates the given collection with them.
	 * 
	 * @param <T> Type of the elements.
	 * @param <C> Type of the result collection.
	 * @param <X> Type of the exceptions.
	 * @param n The number of elements to drain.
	 * @param feed The feed.
	 * @param results The collection to populate with the elements.
	 * @return The result collection.
	 * @throws X When the retrieval of some value fails.
	 * @deprecated Use {@link com.trazere.core.imperative.IteratorUtils#drain(java.util.Iterator, int, Collection)}.
	 */
	@Deprecated
	public static <T, C extends Collection<? super T>, X extends Exception> C drain(final int n, final Feed<? extends T, ? extends X> feed, final C results)
	throws X {
		assert null != feed;
		assert null != results;
		
		return CollectionUtils.drain(n, CheckedIterators.fromFeed(feed), results);
	}
	
	// TODO: add takeWhile
	// TODO: add drop
	// TODO: add dropWhile
	
	private FeedUtils() {
		// Prevent instantiation.
	}
}
