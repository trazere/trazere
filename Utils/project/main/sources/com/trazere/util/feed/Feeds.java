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

import com.trazere.util.collection.CheckedIterator;
import com.trazere.util.function.Function1;
import com.trazere.util.function.Predicate1;
import com.trazere.util.type.Maybe;
import com.trazere.util.type.Maybe.None;
import com.trazere.util.type.Tuple2;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@link Feeds} class provides various common feeds.
 * 
 * @see Feed
 */
public class Feeds {
	/**
	 * Builds a feed using the given head and tail.
	 * 
	 * @param <T> Type of the elements.
	 * @param <X> Type of the exceptions.
	 * @param head The head.
	 * @param tail The tail.
	 * @return The built feed.
	 */
	public static <T, X extends Exception> Feed<T, X> build(final T head, final Feed<T, X> tail) {
		assert null != head;
		assert null != tail;
		
		return new Feed<T, X>() {
			// Feed.
			
			@Override
			public boolean isEmpty() {
				return false;
			}
			
			@Override
			public T getHead() {
				return head;
			}
			
			@Override
			public Feed<T, X> getTail() {
				return tail;
			}
			
			// Function.
			
			@Override
			public Maybe<Tuple2<T, Feed<T, X>>> evaluate() {
				return Maybe.some(Tuple2.build(head, tail));
			}
		};
	}
	
	/**
	 * Builds an empty feed.
	 * 
	 * @param <T> Type of the elements.
	 * @param <X> Type of the exceptions.
	 * @return The built feed.
	 */
	@SuppressWarnings("unchecked")
	public static <T, X extends Exception> Feed<T, X> empty() {
		return (Feed<T, X>) _EMPTY;
	}
	
	private static final Feed<?, ?> _EMPTY = new Feed<Object, RuntimeException>() {
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
		public Feed<Object, RuntimeException> getTail()
		throws NoSuchElementException {
			throw new NoSuchElementException();
		}
		
		// Function.
		
		@Override
		public None<Tuple2<Object, Feed<Object, RuntimeException>>> evaluate() {
			return Maybe.none();
		}
	};
	
	/**
	 * Builds a feed over the given value.
	 * 
	 * @param <T> Type of the elements.
	 * @param <X> Type of the exceptions.
	 * @param value The value.
	 * @return The built feed.
	 */
	public static <T, X extends Exception> Feed<T, X> fromValue(final T value) {
		assert null != value;
		
		return build(value, Feeds.<T, X>empty());
	}
	
	/**
	 * Builds a feed over the given values.
	 * 
	 * @param <T> Type of the elements.
	 * @param <X> Type of the exceptions.
	 * @param values The values.
	 * @return The built feed.
	 */
	public static <T, X extends Exception> Feed<T, X> fromValues(final T... values) {
		assert null != values;
		
		return fromValues(0, values);
	}
	
	private static <T, X extends Exception> Feed<T, X> fromValues(final int index, final T... values) {
		return new Feed<T, X>() {
			// Feed.
			
			@Override
			public boolean isEmpty() {
				return index >= values.length;
			}
			
			@Override
			public T getHead()
			throws NoSuchElementException {
				if (index < values.length) {
					return values[index];
				} else {
					throw new NoSuchElementException();
				}
			}
			
			@Override
			public Feed<T, X> getTail()
			throws NoSuchElementException {
				if (index < values.length) {
					return Feeds.fromValues(index + 1, values);
				} else {
					throw new NoSuchElementException();
				}
			}
			
			// Function.
			
			@Override
			public Maybe<Tuple2<T, Feed<T, X>>> evaluate() {
				if (index < values.length) {
					return Maybe.some(Tuple2.build(values[index], Feeds.<T, X>fromValues(index + 1, values)));
				} else {
					return Maybe.none();
				}
			}
		};
	}
	
	/**
	 * Builds a feed over the values of the given collection.
	 * 
	 * @param <T> Type of the elements.
	 * @param <X> Type of the exceptions.
	 * @param collection The collection.
	 * @return The built feed.
	 */
	public static <T, X extends Exception> Feed<T, X> fromCollection(final Collection<? extends T> collection) {
		assert null != collection;
		
		return fromIterator(collection.iterator());
	}
	
	/**
	 * Builds a feed over the values provided by the given iterator.
	 * 
	 * @param <T> Type of the elements.
	 * @param <X> Type of the exceptions.
	 * @param iterator The iterator.
	 * @return The built feed.
	 */
	public static <T, X extends Exception> Feed<T, X> fromIterator(final Iterator<? extends T> iterator) {
		assert null != iterator;
		
		return new MemoizedFeed<T, X>() {
			@Override
			protected Maybe<Tuple2<T, Feed<T, X>>> compute() {
				if (iterator.hasNext()) {
					return Maybe.some(Tuple2.<T, Feed<T, X>>build(iterator.next(), Feeds.<T, X>fromIterator(iterator)));
				} else {
					return Maybe.none();
				}
			}
		};
	}
	
	/**
	 * Builds a feed over the values provided by the given iterator.
	 * 
	 * @param <T> Type of the elements.
	 * @param <X> Type of the exceptions.
	 * @param iterator The iterator.
	 * @return The built feed.
	 */
	public static <T, X extends Exception> Feed<T, X> fromCheckedIterator(final CheckedIterator<? extends T, ? extends X> iterator) {
		assert null != iterator;
		
		return new MemoizedFeed<T, X>() {
			@Override
			protected Maybe<Tuple2<T, Feed<T, X>>> compute()
			throws X {
				if (iterator.hasNext()) {
					return Maybe.some(Tuple2.<T, Feed<T, X>>build(iterator.next(), Feeds.<T, X>fromCheckedIterator(iterator)));
				} else {
					return Maybe.none();
				}
			}
		};
	}
	
	/**
	 * Memoizes the given feed.
	 * 
	 * @param <T> Type of the elements.
	 * @param <X> Type of the exceptions.
	 * @param feed The feed.
	 * @return The built feed.
	 */
	public static <T, X extends Exception> Feed<T, X> memoize(final Feed<T, X> feed) {
		assert null != feed;
		
		return new MemoizedFeed<T, X>() {
			@Override
			protected Maybe<? extends Tuple2<? extends T, ? extends Feed<? extends T, ? extends X>>> compute()
			throws X {
				return feed.evaluate();
			}
		};
	}
	
	/**
	 * Filters the given feed using the given predicate.
	 * 
	 * @param <T> Type of the elements.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @param feed The feed.
	 * @return The built feed over the filtered elements.
	 */
	public static <T, X extends Exception> Feed<T, X> filter(final Predicate1<? super T, ? extends X> predicate, final Feed<? extends T, ? extends X> feed) {
		assert null != predicate;
		assert null != feed;
		
		return new MemoizedFeed<T, X>() {
			// Function.
			
			@Override
			protected Maybe<? extends Tuple2<T, ? extends Feed<? extends T, ? extends X>>> compute()
			throws X {
				Feed<? extends T, ? extends X> tail = feed;
				while (!tail.isEmpty()) {
					final T head = tail.getHead();
					if (predicate.evaluate(head)) {
						return Maybe.some(Tuple2.build(head, filter(predicate, tail.getTail())));
					} else {
						tail = tail.getTail();
					}
				}
				return Maybe.none();
			}
		};
	}
	
	/**
	 * Transforms the given feed using the given function.
	 * 
	 * @param <T> Type of the elements.
	 * @param <R> Type of the transformed elements.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @param feed The feed.
	 * @return The built feed over the transformed elements.
	 */
	public static <T, R, X extends Exception> Feed<R, X> map(final Function1<? super T, ? extends R, ? extends X> function, final Feed<? extends T, ? extends X> feed) {
		assert null != function;
		assert null != feed;
		
		return new Feed<R, X>() {
			// Feed.
			
			@Override
			public boolean isEmpty()
			throws X {
				return feed.isEmpty();
			}
			
			@Override
			public R getHead()
			throws NoSuchElementException, X {
				return function.evaluate(feed.getHead());
			}
			
			@Override
			public Feed<R, X> getTail()
			throws NoSuchElementException, X {
				return map(function, feed.getTail());
			}
			
			// Function.
			
			@Override
			public Maybe<Tuple2<R, Feed<R, X>>> evaluate()
			throws X {
				final Maybe<? extends Tuple2<? extends T, ? extends Feed<? extends T, ? extends X>>> maybeValue = feed.evaluate();
				if (maybeValue.isSome()) {
					final Tuple2<? extends T, ? extends Feed<? extends T, ? extends X>> value = maybeValue.asSome().getValue();
					return Maybe.some(Tuple2.<R, Feed<R, X>>build(function.evaluate(value.getFirst()), map(function, value.getSecond())));
				} else {
					return Maybe.none();
				}
			}
		};
	}
	
	// TODO: rename to extract
	/**
	 * Filters and transforms the given feed using the given extractor.
	 * 
	 * @param <T> Type of the elements.
	 * @param <R> Type of the transformed elements.
	 * @param <X> Type of the exceptions.
	 * @param extractor The extractor.
	 * @param feed The feed.
	 * @return The built feed over the filtered and transformed elements.
	 */
	public static <T, R, X extends Exception> Feed<R, X> mapFilter(final Function1<? super T, ? extends Maybe<? extends R>, ? extends X> extractor, final Feed<? extends T, ? extends X> feed) {
		assert null != extractor;
		assert null != feed;
		
		return new MemoizedFeed<R, X>() {
			// Function.
			
			@Override
			protected Maybe<Tuple2<R, Feed<R, X>>> compute()
			throws X {
				Feed<? extends T, ? extends X> tail = feed;
				while (!tail.isEmpty()) {
					final Maybe<? extends R> head = extractor.evaluate(tail.getHead());
					if (head.isSome()) {
						return Maybe.some(Tuple2.<R, Feed<R, X>>build(head.asSome().getValue(), mapFilter(extractor, tail.getTail())));
					} else {
						tail = tail.getTail();
					}
				}
				return Maybe.none();
			}
		};
	}
	
	private Feeds() {
		// Prevent instantiation.
	}
}
