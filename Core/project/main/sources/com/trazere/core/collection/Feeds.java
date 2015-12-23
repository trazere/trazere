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
import com.trazere.core.imperative.ExIterator;
import com.trazere.core.imperative.Iterators;
import com.trazere.core.imperative.Procedure;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@link Feeds} class provides various factories of {@link Feed feeds}.
 * 
 * @see Feed
 * @since 2.0
 */
public class Feeds {
	/**
	 * Builds a feed using the given head and tail.
	 * 
	 * @param <E> Type of the elements.
	 * @param head Head element of the feed.
	 * @param tail Tail of the feed.
	 * @return The built feed.
	 * @since 2.0
	 */
	public static <E> Feed<E> feed(final E head, final Feed<? extends E> tail) {
		assert null != head;
		assert null != tail;
		
		return new Feed<E>() {
			// Feed.
			
			@Override
			public boolean isEmpty() {
				return false;
			}
			
			@Override
			public Tuple2<? extends E, ? extends Feed<? extends E>> get() {
				return new Tuple2<E, Feed<? extends E>>(head, tail);
			}
			
			@Override
			public E head() {
				return head;
			}
			
			@Override
			public Maybe<E> optionalHead() {
				return Maybe.some(head);
			}
			
			@Override
			public Feed<? extends E> tail() {
				return tail;
			}
			
			@Override
			public Feed<? extends E> optionalTail() {
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
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> Feed<E> empty() {
		return (Feed<E>) EMPTY;
	}
	
	private static final Feed<?> EMPTY = new MemoizedFeed<Object>() {
		// Feed.
		
		@Override
		public boolean isEmpty() {
			return true;
		}
		
		@Override
		public Tuple2<? extends Object, ? extends Feed<? extends Object>> get()
		throws NoSuchElementException {
			throw new NoSuchElementException();
		}
		
		@Override
		public Object head()
		throws NoSuchElementException {
			throw new NoSuchElementException();
		}
		
		@Override
		public Maybe<Object> optionalHead() {
			return Maybe.none();
		}
		
		@Override
		public Feed<Object> tail()
		throws NoSuchElementException {
			throw new NoSuchElementException();
		}
		
		@Override
		public Feed<Object> optionalTail() {
			return this;
		}
		
		@Override
		public <S> S fold(final Function2<? super S, ? super Object, ? extends S> operator, final S initialState) {
			return initialState;
		}
		
		@Override
		public boolean isAny(final Predicate<? super Object> filter) {
			return false;
		}
		
		@Override
		public boolean areAll(final Predicate<? super Object> filter) {
			return true;
		}
		
		@Override
		public int count(final Predicate<? super Object> filter) {
			return 0;
		}
		
		@Override
		public Maybe<Object> least(final Comparator<? super Object> comparator) {
			return Maybe.none();
		}
		
		@Override
		public Maybe<Object> greatest(final Comparator<? super Object> comparator) {
			return Maybe.none();
		}
		
		@Override
		public Feed<Object> take(final int n) {
			return this;
		}
		
		@Override
		public Feed<Object> takeWhile(final Predicate<? super Object> predicate) {
			return this;
		}
		
		@Override
		public Feed<Object> drop(final int n) {
			return this;
		}
		
		@Override
		public Feed<Object> dropWhile(final Predicate<? super Object> predicate) {
			return this;
		}
		
		@Override
		public <B extends Collection<? super Object>> Feed<B> group(final int n, final CollectionFactory<? super Object, B> batchFactory) {
			return Feeds.empty();
		}
		
		@Override
		public Feed<Object> filter(final Predicate<? super Object> filter) {
			return this;
		}
		
		@Override
		public Maybe<Object> filterFirst(final Predicate<? super Object> filter) {
			return Maybe.none();
		}
		
		@Override
		public <TE> Feed<TE> map(final Function<? super Object, ? extends TE> function) {
			return Feeds.empty();
		}
		
		@Override
		public <EE> Feed<EE> extract(final Function<? super Object, ? extends Maybe<? extends EE>> extractor) {
			return Feeds.empty();
		}
		
		@Override
		public <EE> Maybe<EE> extractFirst(final Function<? super Object, ? extends Maybe<? extends EE>> extractor) {
			return Maybe.none();
		}
		
		@Override
		public <EE> Feed<EE> extractAll(final Function<? super Object, ? extends Iterable<? extends EE>> extractor) {
			return Feeds.empty();
		}
		
		@Override
		public <TE> Feed<TE> flatMap(final Function<? super Object, ? extends Feed<? extends TE>> function) {
			return Feeds.empty();
		}
		
		@Override
		public void foreach(final Procedure<? super Object> procedure) {
			// Nothing to do.
		}
		
		// Thunk.
		
		@Override
		public MemoizedFeed<Object> memoized() {
			return this;
		}
		
		@Override
		public boolean isMemoized() {
			return true;
		}
		
		@Override
		public Maybe<Maybe<? extends Tuple2<? extends Object, ? extends Feed<? extends Object>>>> probe() {
			return Maybe.none();
		}
		
		// Function.
		
		@Override
		public Maybe<Tuple2<Object, Feed<Object>>> evaluate() {
			return Maybe.none();
		}
		
		// Iterable.
		
		@Override
		public ExIterator<Object> iterator() {
			return Iterators.empty();
		}
	};
	
	/**
	 * Builds a feed over the given element.
	 * 
	 * @param <E> Type of the elements.
	 * @param element Element of the feed to build.
	 * @return The built feed.
	 * @since 2.0
	 */
	public static <E> Feed<E> fromElement(final E element) {
		// TODO: optimize feed
		return feed(element, Feeds.<E>empty());
	}
	
	/**
	 * Builds a feed over the given elements.
	 * 
	 * @param <E> Type of the elements.
	 * @param elements Elements of the feed to build.
	 * @return The built feed.
	 * @since 2.0
	 */
	@SafeVarargs
	public static <E> Feed<E> fromElements(final E... elements) {
		return fromElements(elements, 0);
	}
	
	// TODO: generalize to any range and make public
	private static <E> Feed<E> fromElements(final E[] elements, final int index) {
		// TODO: optimize feed
		return new Feed<E>() {
			// Feed.
			
			@Override
			public boolean isEmpty() {
				return index >= elements.length;
			}
			
			@Override
			public Tuple2<? extends E, ? extends Feed<? extends E>> get()
			throws NoSuchElementException {
				if (index < elements.length) {
					return new Tuple2<>(elements[index], Feeds.<E>fromElements(elements, index + 1));
				} else {
					throw new NoSuchElementException();
				}
			}
			
			@Override
			public E head()
			throws NoSuchElementException {
				if (index < elements.length) {
					return elements[index];
				} else {
					throw new NoSuchElementException();
				}
			}
			
			@Override
			public Maybe<E> optionalHead() {
				if (index < elements.length) {
					return Maybe.some(elements[index]);
				} else {
					return Maybe.none();
				}
			}
			
			@Override
			public Feed<E> tail()
			throws NoSuchElementException {
				if (index < elements.length) {
					return Feeds.fromElements(elements, index + 1);
				} else {
					throw new NoSuchElementException();
				}
			}
			
			@Override
			public Feed<E> optionalTail() {
				if (index < elements.length) {
					return Feeds.fromElements(elements, index + 1);
				} else {
					return Feeds.empty();
				}
			}
			
			// Function.
			
			@Override
			public Maybe<Tuple2<E, Feed<E>>> evaluate() {
				if (index < elements.length) {
					return Maybe.some(new Tuple2<>(elements[index], Feeds.<E>fromElements(elements, index + 1)));
				} else {
					return Maybe.none();
				}
			}
			
			// Iterable.
			
			// TODO: optimzed iterator
			//			@Override
			//			public ExIterator<E> iterator() {
			//				return Iterators.fromElements(elements, index);
			//			}
		};
	}
	
	/**
	 * Builds a feed over the elements provided by the given iterator.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the elements.
	 * @return The built feed.
	 * @since 2.0
	 */
	public static <E> Feed<E> fromIterator(final Iterator<? extends E> iterator) {
		assert null != iterator;
		
		return new BaseMemoizedFeed<E>() {
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
	 * Builds a feed over the given iterable.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterable Iterable providing the elements.
	 * @return The built feed.
	 * @since 2.0
	 */
	public static <E> Feed<E> fromIterable(final Iterable<? extends E> iterable) {
		return fromIterator(iterable.iterator());
	}
	
	/**
	 * Builds a feed over the value wrapped in the given {@link Maybe} instance.
	 * 
	 * @param <T> Type of the wrapped value.
	 * @param maybe {@link Maybe} instance wrapping the value.
	 * @return The built feed.
	 * @since 2.0
	 */
	public static <T> Feed<T> fromMaybe(final Maybe<? extends T> maybe) {
		return maybe.isSome() ? fromElement(maybe.asSome().getValue()) : empty();
	}
	
	private Feeds() {
		// Prevent instantiation.
	}
}
