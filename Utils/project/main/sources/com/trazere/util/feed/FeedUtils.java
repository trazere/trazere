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

import com.trazere.core.collection.CollectionFactory;
import com.trazere.core.functional.Function;
import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Predicate;
import com.trazere.core.imperative.Accumulator;
import com.trazere.core.imperative.ExIterator;
import com.trazere.core.imperative.PairIterator;
import com.trazere.core.imperative.Procedure;
import com.trazere.core.lang.ThrowableFactory;
import com.trazere.util.collection.CheckedIterators;
import com.trazere.util.collection.CollectionUtils;
import com.trazere.util.lang.WrapException;
import com.trazere.util.type.Maybe;
import com.trazere.util.type.Tuple2;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@link FeedUtils} class provides various utilities regarding feeds.
 * 
 * @deprecated Use {@link com.trazere.core}.
 */
@Deprecated
public class FeedUtils {
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
			// Note: default must be implemented, project is still 1.6
			@Override
			public boolean isEmpty() {
				try {
					return feed.isEmpty();
				} catch (final Exception exception) {
					throw new WrapException(exception);
				}
			}
			
			// Note: default must be implemented, project is still 1.6
			@Override
			public com.trazere.core.util.Tuple2<? extends T, ? extends com.trazere.core.collection.Feed<? extends T>> get()
			throws NoSuchElementException {
				return com.trazere.core.collection.Feed.super.get();
			}
			
			// Note: default must be implemented, project is still 1.6
			@Override
			public T head()
			throws NoSuchElementException {
				return com.trazere.core.collection.Feed.super.head();
			}
			
			// Note: default must be implemented, project is still 1.6
			@Override
			public com.trazere.core.util.Maybe<T> optionalHead() {
				return com.trazere.core.collection.Feed.super.optionalHead();
			}
			
			// Note: default must be implemented, project is still 1.6
			@Override
			public com.trazere.core.collection.Feed<? extends T> tail()
			throws NoSuchElementException {
				return com.trazere.core.collection.Feed.super.tail();
			}
			
			// Note: default must be implemented, project is still 1.6
			@Override
			public com.trazere.core.collection.Feed<? extends T> optionalTail() {
				return com.trazere.core.collection.Feed.super.optionalTail();
			}
			
			// Note: default must be implemented, project is still 1.6
			@Override
			public <S> S fold(final Function2<? super S, ? super T, ? extends S> operator, final S initialState) {
				return com.trazere.core.collection.Feed.super.fold(operator, initialState);
			}
			
			// Note: default must be implemented, project is still 1.6
			@Override
			public boolean isAny(final Predicate<? super T> filter) {
				return com.trazere.core.collection.Feed.super.isAny(filter);
			}
			
			// Note: default must be implemented, project is still 1.6
			@Override
			public boolean areAll(final Predicate<? super T> filter) {
				return com.trazere.core.collection.Feed.super.areAll(filter);
			}
			
			// Note: default must be implemented, project is still 1.6
			@Override
			public int count(final Predicate<? super T> filter) {
				return com.trazere.core.collection.Feed.super.count(filter);
			}
			
			// Note: default must be implemented, project is still 1.6
			@Override
			public com.trazere.core.util.Maybe<T> least(final Comparator<? super T> comparator) {
				return com.trazere.core.collection.Feed.super.least(comparator);
			}
			
			// Note: default must be implemented, project is still 1.6
			@Override
			public com.trazere.core.util.Maybe<T> greatest(final Comparator<? super T> comparator) {
				return com.trazere.core.collection.Feed.super.greatest(comparator);
			}
			
			// Note: default must be implemented, project is still 1.6
			@Override
			public com.trazere.core.collection.Feed<T> take(final int n) {
				return com.trazere.core.collection.Feed.super.take(n);
			}
			
			// Note: default must be implemented, project is still 1.6
			@Override
			public com.trazere.core.collection.Feed<T> takeWhile(final Predicate<? super T> predicate) {
				return com.trazere.core.collection.Feed.super.takeWhile(predicate);
			}
			
			// Note: default must be implemented, project is still 1.6
			@Override
			public com.trazere.core.collection.Feed<T> drop(final int n) {
				return com.trazere.core.collection.Feed.super.drop(n);
			}
			
			// Note: default must be implemented, project is still 1.6
			@Override
			public com.trazere.core.collection.Feed<T> dropWhile(final Predicate<? super T> predicate) {
				return com.trazere.core.collection.Feed.super.dropWhile(predicate);
			}
			
			// Note: default must be implemented, project is still 1.6
			@Override
			public <B extends Collection<? super T>> com.trazere.core.collection.Feed<B> group(final int n, final CollectionFactory<? super T, B> batchFactory) {
				return com.trazere.core.collection.Feed.super.group(n, batchFactory);
			}
			
			// Note: default must be implemented, project is still 1.6
			@Override
			public com.trazere.core.collection.Feed<T> filter(final Predicate<? super T> filter) {
				return com.trazere.core.collection.Feed.super.filter(filter);
			}
			
			// Note: default must be implemented, project is still 1.6
			@Override
			public com.trazere.core.util.Maybe<T> filterFirst(final Predicate<? super T> filter) {
				return com.trazere.core.collection.Feed.super.filterFirst(filter);
			}
			
			// Note: default must be implemented, project is still 1.6
			@Override
			public <TE> com.trazere.core.collection.Feed<TE> map(final Function<? super T, ? extends TE> function) {
				return com.trazere.core.collection.Feed.super.map(function);
			}
			
			// Note: default must be implemented, project is still 1.6
			@Override
			public <EE> com.trazere.core.collection.Feed<EE> extract(final Function<? super T, ? extends com.trazere.core.util.Maybe<? extends EE>> extractor) {
				return com.trazere.core.collection.Feed.super.extract(extractor);
			}
			
			// Note: default must be implemented, project is still 1.6
			@Override
			public <EE> com.trazere.core.util.Maybe<EE> extractFirst(final Function<? super T, ? extends com.trazere.core.util.Maybe<? extends EE>> extractor) {
				return com.trazere.core.collection.Feed.super.extractFirst(extractor);
			}
			
			// Note: default must be implemented, project is still 1.6
			@Override
			public <EE> com.trazere.core.collection.Feed<EE> extractAll(final Function<? super T, ? extends Iterable<? extends EE>> extractor) {
				return com.trazere.core.collection.Feed.super.extractAll(extractor);
			}
			
			@Override
			public void foreach(final Procedure<? super T> procedure) {
				com.trazere.core.collection.Feed.super.foreach(procedure);
			}
			
			// Function.
			
			@Override
			public com.trazere.core.util.Maybe<? extends com.trazere.core.util.Tuple2<? extends T, ? extends com.trazere.core.collection.Feed<? extends T>>> evaluate() {
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
			
			// Iterable.
			
			// Note: default must be implemented, project is still 1.6
			@Override
			public ExIterator<T> iterator() {
				final com.trazere.core.collection.Feed<? extends T> this_ = this;
				return new ExIterator<T>() {
					private com.trazere.core.collection.Feed<? extends T> _tail = this_;
					
					@Override
					public boolean hasNext() {
						return !_tail.isEmpty();
					}
					
					@Override
					public T next()
					throws NoSuchElementException {
						final com.trazere.core.util.Tuple2<? extends T, ? extends com.trazere.core.collection.Feed<? extends T>> item = _tail.get();
						_tail = item.get2();
						return item.get1();
					}
					
					@Override
					public com.trazere.core.util.Maybe<T> optionalNext() {
						return ExIterator.super.optionalNext();
					}
					
					@Override
					public void drain(final int n) {
						ExIterator.super.drain(n);
					}
					
					@Override
					public <A extends Accumulator<? super T, ?>> A drain(final int n, final A results) {
						return ExIterator.super.drain(n, results);
					}
					
					@Override
					public <C extends Collection<? super T>> C drain(final int n, final C results) {
						return ExIterator.super.drain(n, results);
					}
					
					@Override
					public void drain() {
						ExIterator.super.drain();
					}
					
					@Override
					public <A extends Accumulator<? super T, ?>> A drain(final A results) {
						return ExIterator.super.drain(results);
					}
					
					@Override
					public <C extends Collection<? super T>> C drain(final C results) {
						return ExIterator.super.drain(results);
					}
					
					@Override
					public <S> S fold(final Function2<? super S, ? super T, ? extends S> operator, final S initialState) {
						return ExIterator.super.fold(operator, initialState);
					}
					
					@Override
					public boolean isAny(final Predicate<? super T> filter) {
						return ExIterator.super.isAny(filter);
					}
					
					@Override
					public boolean areAll(final Predicate<? super T> filter) {
						return ExIterator.super.areAll(filter);
					}
					
					@Override
					public int count(final Predicate<? super T> filter) {
						return ExIterator.super.count(filter);
					}
					
					@Override
					public com.trazere.core.util.Maybe<T> least(final Comparator<? super T> comparator) {
						return ExIterator.super.least(comparator);
					}
					
					@Override
					public com.trazere.core.util.Maybe<T> greatest(final Comparator<? super T> comparator) {
						return ExIterator.super.greatest(comparator);
					}
					
					@Override
					public ExIterator<T> take(final int n) {
						return ExIterator.super.take(n);
					}
					
					@Override
					public ExIterator<T> drop(final int n) {
						return ExIterator.super.drop(n);
					}
					
					@Override
					public <B extends Collection<? super T>> ExIterator<B> group(final int n, final CollectionFactory<? super T, B> batchFactory) {
						return ExIterator.super.group(n, batchFactory);
					}
					
					@Override
					public ExIterator<T> filter(final Predicate<? super T> filter) {
						return ExIterator.super.filter(filter);
					}
					
					@Override
					public com.trazere.core.util.Maybe<T> filterFirst(final Predicate<? super T> filter) {
						return ExIterator.super.filterFirst(filter);
					}
					
					@Override
					public <TE> ExIterator<TE> map(final Function<? super T, ? extends TE> function) {
						return ExIterator.super.map(function);
					}
					
					@Override
					public <EE> ExIterator<EE> extract(final Function<? super T, ? extends com.trazere.core.util.Maybe<? extends EE>> extractor) {
						return ExIterator.super.extract(extractor);
					}
					
					@Override
					public <EE> com.trazere.core.util.Maybe<EE> extractFirst(final Function<? super T, ? extends com.trazere.core.util.Maybe<? extends EE>> extractor) {
						return ExIterator.super.extractFirst(extractor);
					}
					
					@Override
					public <TE> ExIterator<TE> extractAll(final Function<? super T, ? extends Iterable<? extends TE>> extractor) {
						return ExIterator.super.extractAll(extractor);
					}
					
					@Override
					public <TE> ExIterator<TE> flatMap(final Function<? super T, ? extends Iterator<? extends TE>> extractor) {
						return ExIterator.super.flatMap(extractor);
					}
					
					@Override
					public <E2> PairIterator<T, E2> zip(final Iterator<? extends E2> iterator2) {
						return ExIterator.super.zip(iterator2);
					}
					
					@Override
					public void foreach(final Procedure<? super T> procedure) {
						ExIterator.super.foreach(procedure);
					}
					
					@Override
					public ExIterator<T> unmodifiable() {
						return ExIterator.super.unmodifiable();
					}
				};
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
				final com.trazere.core.util.Maybe<? extends com.trazere.core.util.Tuple2<? extends E, ? extends com.trazere.core.collection.Feed<? extends E>>> maybeItem = feed.evaluate();
				if (maybeItem.isSome()) {
					final com.trazere.core.util.Tuple2<? extends E, ? extends com.trazere.core.collection.Feed<? extends E>> item = maybeItem.asSome().getValue();
					return Maybe.some(new Tuple2<E, Feed<E, RuntimeException>>(item.get1(), fromFeed(item.get2())));
				} else {
					return Maybe.none();
				}
			}
		};
	}
	
	/**
	 * Adapts the given core feed to an util feed.
	 * 
	 * @param <E> Type of the elements.
	 * @param <X> Type of the exceptions.
	 * @param feed Core feed to adapt.
	 * @param throwableFactory Throwable factory to use.
	 * @return The adapted util feed.
	 * @deprecated Use {@link com.trazere.core.collection.Feed}.
	 */
	@Deprecated
	public static <E, X extends Exception> Feed<E, X> fromFeed(final com.trazere.core.collection.Feed<? extends E> feed, final ThrowableFactory<? extends X> throwableFactory) {
		assert null != feed;
		
		return new Feed<E, X>() {
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
			public Feed<? extends E, ? extends X> getTail()
			throws NoSuchElementException {
				return fromFeed(feed.tail(), throwableFactory);
			}
			
			@Override
			public Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E, ? extends X>>> evaluate()
			throws X {
				try {
					final com.trazere.core.util.Maybe<? extends com.trazere.core.util.Tuple2<? extends E, ? extends com.trazere.core.collection.Feed<? extends E>>> maybeItem = feed.evaluate();
					if (maybeItem.isSome()) {
						final com.trazere.core.util.Tuple2<? extends E, ? extends com.trazere.core.collection.Feed<? extends E>> item = maybeItem.asSome().getValue();
						return Maybe.some(new Tuple2<E, Feed<E, X>>(item.get1(), fromFeed(item.get2(), throwableFactory)));
					} else {
						return Maybe.none();
					}
				} catch (final Exception exception) {
					throw throwableFactory.build(exception);
				}
			}
		};
	}
	
	private FeedUtils() {
		// Prevent instantiation.
	}
}
