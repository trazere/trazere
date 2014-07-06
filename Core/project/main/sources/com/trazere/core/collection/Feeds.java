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

import com.trazere.core.imperative.Iterators;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Maybe.None;
import com.trazere.core.util.Tuple2;
import com.trazere.core.util.Tuples;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@link Feeds} class provides various factories of feeds.
 * 
 * @see Feed
 */
public class Feeds {
	/**
	 * Builds a feed using the given head and tail.
	 * 
	 * @param <E> Type of the elements.
	 * @param head Head element of the feed.
	 * @param tail Tail of the feed.
	 * @return The built feed.
	 */
	public static <E> Feed<E> feed(final E head, final Feed<? extends E> tail) {
		assert null != head;
		assert null != tail;
		
		return new BaseFeed<E>() {
			// Feed.
			
			@Override
			public boolean isEmpty() {
				return false;
			}
			
			@Override
			public E getHead() {
				return head;
			}
			
			@Override
			public Feed<? extends E> getTail() {
				return tail;
			}
			
			// Function.
			
			@Override
			public Maybe<Tuple2<E, Feed<? extends E>>> evaluate() {
				return Maybe.some(new Tuple2<E, Feed<? extends E>>(head, tail));
			}
		};
	}
	
	/**
	 * Builds an empty feed.
	 * 
	 * @param <E> Type of the elements.
	 * @return The built feed.
	 */
	@SuppressWarnings("unchecked")
	public static <E> Feed<E> empty() {
		return (Feed<E>) EMPTY;
	}
	
	private static final Feed<?> EMPTY = new Feed<Object>() {
		// Feed.
		
		@Override
		public boolean isEmpty() {
			return true;
		}
		
		@Override
		public Object getHead()
		throws NoSuchElementException {
			throw new NoSuchElementException();
		}
		
		@Override
		public Feed<Object> getTail()
		throws NoSuchElementException {
			throw new NoSuchElementException();
		}
		
		// Function.
		
		@Override
		public None<Tuple2<Object, Feed<Object>>> evaluate() {
			return Maybe.none();
		}
		
		// Iterable.
		
		@Override
		public Iterator<Object> iterator() {
			return Iterators.empty();
		}
	};
	
	/**
	 * Builds a feed over the given value.
	 * 
	 * @param <E> Type of the elements.
	 * @param value Value.
	 * @return The built feed.
	 */
	public static <E> Feed<E> fromValue(final E value) {
		return feed(value, Feeds.<E>empty());
	}
	
	/**
	 * Builds a feed over the given values.
	 * 
	 * @param <E> Type of the elements.
	 * @param values Values.
	 * @return The built feed.
	 */
	@SafeVarargs
	public static <E> Feed<E> fromValues(final E... values) {
		return fromValues(values, 0);
	}
	
	private static <E> Feed<E> fromValues(final E[] values, final int index) {
		return new BaseFeed<E>() {
			// Feed.
			
			@Override
			public boolean isEmpty() {
				return index >= values.length;
			}
			
			@Override
			public E getHead()
			throws NoSuchElementException {
				if (index < values.length) {
					return values[index];
				} else {
					throw new NoSuchElementException();
				}
			}
			
			@Override
			public Feed<E> getTail()
			throws NoSuchElementException {
				if (index < values.length) {
					return Feeds.fromValues(values, index + 1);
				} else {
					throw new NoSuchElementException();
				}
			}
			
			// Function.
			
			@Override
			public Maybe<Tuple2<E, Feed<E>>> evaluate() {
				if (index < values.length) {
					return Maybe.some(new Tuple2<>(values[index], Feeds.<E>fromValues(values, index + 1)));
				} else {
					return Maybe.none();
				}
			}
		};
	}
	
	/**
	 * Builds a feed over the given iterable.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterable Iterable of the elements.
	 * @return The built feed.
	 */
	public static <E> Feed<E> fromIterable(final Iterable<? extends E> iterable) {
		return fromIterator(iterable.iterator());
	}
	
	/**
	 * Builds a feed over the elements provided by the given iterator.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the elements.
	 * @return The built feed.
	 */
	public static <E> Feed<E> fromIterator(final Iterator<? extends E> iterator) {
		assert null != iterator;
		
		return new MemoizedFeed<E>() {
			@Override
			protected Maybe<Tuple2<E, Feed<E>>> compute() {
				if (iterator.hasNext()) {
					return Maybe.some(new Tuple2<E, Feed<E>>(iterator.next(), Feeds.fromIterator(iterator)));
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
	 * @param feed1 First feed.
	 * @param feed2 Second feed.
	 * @return The built feed.
	 */
	public static <E> Feed<E> append(final Feed<? extends E> feed1, final Feed<? extends E> feed2) {
		assert null != feed1;
		assert null != feed2;
		
		return new BaseFeed<E>() {
			@Override
			public Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> evaluate() {
				final Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> maybeItem1 = feed1.evaluate();
				if (maybeItem1.isSome()) {
					final Tuple2<? extends E, ? extends Feed<? extends E>> item1 = maybeItem1.asSome().getValue();
					return Maybe.some(Tuples.tuple2(item1.get1(), append(item1.get2(), feed2)));
				} else {
					return feed2.evaluate();
				}
			}
		};
	}
	
	private Feeds() {
		// Prevent instantiation.
	}
}
