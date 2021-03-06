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
package com.trazere.util.feed;

import com.trazere.util.collection.CheckedIterators;
import com.trazere.util.collection.CollectionUtils;
import com.trazere.util.lang.InternalException;
import com.trazere.util.lang.WrapException;
import com.trazere.util.type.Maybe;
import com.trazere.util.type.Tuple1;
import com.trazere.util.type.Tuple2;
import com.trazere.util.type.TypeUtils;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@link FeedUtils} class provides various utilities regarding feeds.
 * 
 * @deprecated Use {@link com.trazere.core}.
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
	 * @deprecated Use {@link com.trazere.core.collection.Feed#head()}.
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
	
	/**
	 * Adapts the given util feed to a core feed.
	 * 
	 * @param <T> Type of the elements.
	 * @param feed Util feed to adapt.
	 * @return The adapted core feed.
	 * @deprecated Use {@link com.trazere.core.collection.Feed}.
	 */
	@Deprecated
	public static <T> com.trazere.core.collection.Feed<T> toFeed(final Feed<? extends T, ?> feed) {
		assert null != feed;
		
		return new com.trazere.core.collection.Feed<T>() {
			@Override
			public boolean isEmpty() {
				try {
					return feed.isEmpty();
				} catch (final Exception exception) {
					throw new WrapException(exception);
				}
			}
			
			@Override
			public T head()
			throws NoSuchElementException {
				try {
					return feed.getHead();
				} catch (final NoSuchElementException exception) {
					throw exception;
				} catch (final Exception exception) {
					throw new WrapException(exception);
				}
			}
			
			@Override
			public com.trazere.core.util.Maybe<T> optionalHead() {
				try {
					return TypeUtils.toMaybe(feed.evaluate().map(Tuple1.<T, InternalException>getFirstFunction()));
				} catch (final Exception exception) {
					throw new WrapException(exception);
				}
			}
			
			@Override
			public com.trazere.core.collection.Feed<? extends T> tail()
			throws NoSuchElementException {
				try {
					return toFeed(feed.getTail());
				} catch (final NoSuchElementException exception) {
					throw exception;
				} catch (final Exception exception) {
					throw new WrapException(exception);
				}
			}
			
			@Override
			public com.trazere.core.util.Maybe<? extends com.trazere.core.collection.Feed<? extends T>> optionalTail() {
				try {
					final Maybe<? extends Tuple2<? extends T, ? extends Feed<? extends T, ?>>> maybeItem = feed.evaluate();
					if (maybeItem.isSome()) {
						return com.trazere.core.util.Maybe.some(toFeed(maybeItem.asSome().getValue().getSecond()));
					} else {
						return com.trazere.core.util.Maybe.none();
					}
				} catch (final Exception exception) {
					throw new WrapException(exception);
				}
			}
			
			@Override
			public com.trazere.core.util.Tuple2<? extends T, ? extends com.trazere.core.collection.Feed<? extends T>> item()
			throws NoSuchElementException {
				try {
					final Maybe<? extends Tuple2<? extends T, ? extends Feed<? extends T, ?>>> maybeItem = feed.evaluate();
					if (maybeItem.isSome()) {
						final Tuple2<? extends T, ? extends Feed<? extends T, ?>> item = maybeItem.asSome().getValue();
						return new com.trazere.core.util.Tuple2<T, com.trazere.core.collection.Feed<T>>(item.getFirst(), toFeed(item.getSecond()));
					} else {
						throw new NoSuchElementException();
					}
				} catch (final NoSuchElementException exception) {
					throw exception;
				} catch (final Exception exception) {
					throw new WrapException(exception);
				}
			}
			
			@Override
			public com.trazere.core.util.Maybe<? extends com.trazere.core.util.Tuple2<? extends T, ? extends com.trazere.core.collection.Feed<? extends T>>> optionalItem() {
				try {
					final Maybe<? extends Tuple2<? extends T, ? extends Feed<? extends T, ?>>> maybeItem = feed.evaluate();
					if (maybeItem.isSome()) {
						final Tuple2<? extends T, ? extends Feed<? extends T, ?>> item = maybeItem.asSome().getValue();
						return com.trazere.core.util.Maybe.some(new com.trazere.core.util.Tuple2<T, com.trazere.core.collection.Feed<T>>(item.getFirst(), toFeed(item.getSecond())));
					} else {
						return com.trazere.core.util.Maybe.none();
					}
				} catch (final Exception exception) {
					throw new WrapException(exception);
				}
			}
			
			// TODO: defaults
			
			// Iterable.
			
			// Note: project is still 1.6
			@Override
			public Iterator<T> iterator() {
				return com.trazere.core.collection.Feed.super.iterator();
			}
		};
	}
	
	/**
	 * Adapts the given core feed to an util feed.
	 * 
	 * @param <E> Type of the elements.
	 * @param feed Core feed to adapt.
	 * @return The adapted util feed.
	 * @deprecated Use {@link com.trazere.core.collection.Feed}.
	 */
	@Deprecated
	public static <E> Feed<E, RuntimeException> fromFeed(final com.trazere.core.collection.Feed<? extends E> feed) {
		assert null != feed;
		
		return new Feed<E, RuntimeException>() {
			@Override
			public boolean isEmpty() {
				return feed.isEmpty();
			}
			
			@Override
			public E getHead()
			throws NoSuchElementException {
				return feed.head();
			}
			
			@Override
			public Feed<? extends E, ? extends RuntimeException> getTail()
			throws NoSuchElementException {
				return fromFeed(feed.tail());
			}
			
			@Override
			public Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E, ? extends RuntimeException>>> evaluate() {
				final com.trazere.core.util.Maybe<? extends com.trazere.core.util.Tuple2<? extends E, ? extends com.trazere.core.collection.Feed<? extends E>>> maybeItem = feed.optionalItem();
				if (maybeItem.isSome()) {
					final com.trazere.core.util.Tuple2<? extends E, ? extends com.trazere.core.collection.Feed<? extends E>> item = maybeItem.asSome().getValue();
					return Maybe.some(new Tuple2<E, Feed<E, RuntimeException>>(item.get1(), fromFeed(item.get2())));
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
