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
import com.trazere.util.type.Maybe;
import com.trazere.util.type.Maybe.None;
import com.trazere.util.type.Tuple2;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@link Feeds} class provides various common feeds.
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
	 * Builds a feed over the given element.
	 * 
	 * @param <T> Type of the elements.
	 * @param <X> Type of the exceptions.
	 * @param element The element.
	 * @return The built feed.
	 */
	public static <T, X extends Exception> Feed<T, X> value(final T element) {
		assert null != element;
		
		return build(element, Feeds.<T, X>empty());
	}
	
	/**
	 * Builds a feed over the given elements.
	 * 
	 * @param <T> Type of the elements.
	 * @param <X> Type of the exceptions.
	 * @param elements The elements.
	 * @return The built feed.
	 */
	public static <T, X extends Exception> Feed<T, X> values(final T... elements) {
		assert null != elements;
		
		return values(0, elements);
	}
	
	private static <T, X extends Exception> Feed<T, X> values(final int index, final T... elements) {
		return new Feed<T, X>() {
			// Feed.
			
			@Override
			public boolean isEmpty() {
				return index >= elements.length;
			}
			
			@Override
			public T getHead()
			throws NoSuchElementException {
				if (index < elements.length) {
					return elements[index];
				} else {
					throw new NoSuchElementException();
				}
			}
			
			@Override
			public Feed<T, X> getTail()
			throws NoSuchElementException {
				if (index < elements.length) {
					return Feeds.values(index + 1, elements);
				} else {
					throw new NoSuchElementException();
				}
			}
			
			// Function.
			
			@Override
			public Maybe<Tuple2<T, Feed<T, X>>> evaluate() {
				if (index < elements.length) {
					return Maybe.some(Tuple2.build(elements[index], Feeds.<T, X>values(index + 1, elements)));
				} else {
					return Maybe.none();
				}
			}
		};
	}
	
	/**
	 * Builds a feed over the given iterator.
	 * 
	 * @param <T> Type of the elements.
	 * @param <X> Type of the exceptions.
	 * @param elements The iterator providing the elements.
	 * @return The built feed.
	 */
	public static <T, X extends Exception> Feed<T, X> iterator(final Iterator<? extends T> elements) {
		assert null != elements;
		
		return new MemoizedFeed<T, X>() {
			@Override
			protected Maybe<Tuple2<T, Feed<T, X>>> compute() {
				if (elements.hasNext()) {
					return Maybe.some(Tuple2.<T, Feed<T, X>>build(elements.next(), Feeds.<T, X>iterator(elements)));
				} else {
					return Maybe.none();
				}
			}
		};
	}
	
	/**
	 * Builds a feed over the given iterator.
	 * 
	 * @param <T> Type of the elements.
	 * @param <X> Type of the exceptions.
	 * @param elements The iterator providing the elements.
	 * @return The built feed.
	 */
	public static <T, X extends Exception> Feed<T, X> iterator(final CheckedIterator<? extends T, ? extends X> elements) {
		assert null != elements;
		
		return new MemoizedFeed<T, X>() {
			@Override
			protected Maybe<Tuple2<T, Feed<T, X>>> compute()
			throws X {
				if (elements.hasNext()) {
					return Maybe.some(Tuple2.<T, Feed<T, X>>build(elements.next(), Feeds.<T, X>iterator(elements)));
				} else {
					return Maybe.none();
				}
			}
		};
	}
	
	private Feeds() {
		// Prevent instantiation.
	}
}
