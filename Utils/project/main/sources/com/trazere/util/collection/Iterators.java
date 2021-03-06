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
package com.trazere.util.collection;

import com.trazere.util.function.Function1;
import com.trazere.util.function.Predicate1;
import com.trazere.util.lang.Counter;
import com.trazere.util.type.Maybe;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * The {@link Iterators} class provides various factories of iterators.
 * 
 * @see Iterator
 * @deprecated Use {@link com.trazere.core}.
 */
@Deprecated
public class Iterators {
	/**
	 * Builds an empty iterator.
	 * 
	 * @param <V> Type of the the values.
	 * @return The built iterator.
	 * @deprecated Use {@link com.trazere.core.imperative.Iterators#empty()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <V> Iterator<V> empty() {
		return (Iterator<V>) _EMPTY;
	}
	
	private static final Iterator<?> _EMPTY = new Iterator<Object>() {
		@Override
		public boolean hasNext() {
			return false;
		}
		
		@Override
		public Object next()
		throws NoSuchElementException {
			throw new NoSuchElementException();
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	};
	
	/**
	 * Builds an iterator over the given value.
	 * 
	 * @param <V> Type of the the value.
	 * @param value The value. May be <code>null</code>.
	 * @return The built iterator.
	 * @deprecated Use {@link com.trazere.core.imperative.Iterators#fromElement(Object)}.
	 */
	@Deprecated
	public static <V> Iterator<V> fromValue(final V value) {
		return new Iterator<V>() {
			protected boolean _next = true;
			
			@Override
			public boolean hasNext() {
				return _next;
			}
			
			@Override
			public V next()
			throws NoSuchElementException {
				if (_next) {
					_next = false;
					return value;
				} else {
					throw new NoSuchElementException();
				}
			}
			
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	/**
	 * Builds an iterator over the given values.
	 * 
	 * @param <T> Type of the the values.
	 * @param values The values.
	 * @return The built iterator.
	 * @deprecated Use {@link com.trazere.core.imperative.Iterators#fromElements(Object...)}.
	 */
	@Deprecated
	public static <T> Iterator<T> fromValues(final T... values) {
		assert null != values;
		
		return new Iterator<T>() {
			protected int _index = 0;
			
			@Override
			public boolean hasNext() {
				return _index < values.length;
			}
			
			@Override
			public T next()
			throws NoSuchElementException {
				if (_index < values.length) {
					final T value = values[_index];
					_index += 1;
					
					return value;
				} else {
					throw new NoSuchElementException();
				}
			}
			
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	/**
	 * Filters the given iterator using the given predicate.
	 * 
	 * @param <T> Type of the values.
	 * @param predicate The predicate.
	 * @param iterator The iterator.
	 * @return The built iterator over the filtered values.
	 * @deprecated Use {@link com.trazere.core.imperative.IteratorUtils#filter(Iterator, com.trazere.core.functional.Predicate)}.
	 */
	@Deprecated
	public static <T> Iterator<T> filter(final Predicate1<? super T, ? extends RuntimeException> predicate, final Iterator<? extends T> iterator) {
		assert null != predicate;
		assert null != iterator;
		
		return new FilterIterator<T>() {
			@Override
			protected Maybe<T> pull() {
				return CollectionUtils.next(iterator);
			}
			
			@Override
			public boolean filter(final T value) {
				return predicate.evaluate(value);
			}
		};
	}
	
	/**
	 * Transforms the given iterator using the given function.
	 * 
	 * @param <T> Type of the values.
	 * @param <R> Type of the transformed values.
	 * @param function The function.
	 * @param iterator The iterator.
	 * @return The built iterator over the transformed values.
	 * @deprecated Use {@link com.trazere.core.imperative.IteratorUtils#map(Iterator, com.trazere.core.functional.Function)}.
	 */
	@Deprecated
	public static <T, R> Iterator<R> map(final Function1<? super T, ? extends R, ? extends RuntimeException> function, final Iterator<? extends T> iterator) {
		assert null != function;
		assert null != iterator;
		
		return new MapIterator<T, R>() {
			@Override
			protected Maybe<T> pull() {
				return CollectionUtils.next(iterator);
			}
			
			@Override
			protected R map(final T value) {
				return function.evaluate(value);
			}
		};
	}
	
	/**
	 * Filters and transforms the given iterator using the given extractor.
	 * 
	 * @param <T> Type of the values.
	 * @param <R> Type of the transformed values.
	 * @param extractor The extractor.
	 * @param iterator The iterator.
	 * @return The built iterator over the filtered and transformed values.
	 * @deprecated Use {@link #extract(Function1, Iterator)}. (since 1.0)
	 */
	@Deprecated
	public static <T, R> Iterator<R> mapFilter(final Function1<? super T, ? extends Maybe<? extends R>, ? extends RuntimeException> extractor, final Iterator<? extends T> iterator) {
		return extract(extractor, iterator);
	}
	
	/**
	 * Builds an iterator that extracts values from the given iterator using the given extractor.
	 * 
	 * @param <T> Type of the values.
	 * @param <R> Type of the transformed values.
	 * @param extractor The extractor.
	 * @param iterator The iterator.
	 * @return The built iterator over the filtered and transformed values.
	 * @deprecated Use {@link com.trazere.core.imperative.IteratorUtils#extract(Iterator, com.trazere.core.functional.Function)}.
	 */
	@Deprecated
	public static <T, R> Iterator<R> extract(final Function1<? super T, ? extends Maybe<? extends R>, ? extends RuntimeException> extractor, final Iterator<? extends T> iterator) {
		assert null != extractor;
		assert null != iterator;
		
		return new ExtractIterator<T, R>() {
			@Override
			protected Maybe<T> pull() {
				return CollectionUtils.next(iterator);
			}
			
			@Override
			public Maybe<? extends R> extract(final T value) {
				return extractor.evaluate(value);
			}
		};
	}
	
	/**
	 * Builds an iterator which iterates the given list in reverse.
	 * 
	 * @param <T> Type of the values.
	 * @param list List to iterate.
	 * @return The built iterator.
	 * @deprecated Use {@link #reversed(List)}. (since 1.0)
	 */
	@Deprecated
	public static <T> Iterator<T> reverse(final List<? extends T> list) {
		return reversed(list);
	}
	
	/**
	 * Builds an iterator which iterates the given list in reverse.
	 * 
	 * @param <T> Type of the values.
	 * @param list List to iterate.
	 * @return The built iterator.
	 * @deprecated Use {@link com.trazere.core.collection.ListIterators#backward(List)}.
	 */
	@Deprecated
	public static <T> Iterator<T> reversed(final List<? extends T> list) {
		assert null != list;
		
		final ListIterator<? extends T> iterator = list.listIterator(list.size());
		return new Iterator<T>() {
			@Override
			public boolean hasNext() {
				return iterator.hasPrevious();
			}
			
			@Override
			public T next() {
				return iterator.previous();
			}
			
			@Override
			public void remove() {
				iterator.remove();
			}
		};
	}
	
	/**
	 * Builds an iterator over the natural integers.
	 * 
	 * @return The built iterator.
	 * @deprecated Use {@link com.trazere.core.lang.InfiniteIntSequence#iterator()}.
	 */
	@Deprecated
	public static Iterator<Integer> counter() {
		return counter(0, 1);
	}
	
	/**
	 * Builds an iterator over the integers starting at the given value and incrementing by <code>1</code> at each iteration.
	 * 
	 * @param start First value.
	 * @return The built iterator.
	 * @deprecated Use {@link com.trazere.core.lang.InfiniteIntSequence#iterator()}.
	 */
	@Deprecated
	public static Iterator<Integer> counter(final int start) {
		return counter(start, 1);
	}
	
	/**
	 * Builds an iterator over the integers starting at the given value and incrementing by given increment at each iteration.
	 * 
	 * @param start First value.
	 * @param increment Increment.
	 * @return The built iterator.
	 * @deprecated Use {@link com.trazere.core.lang.InfiniteIntSequence#iterator()}.
	 */
	@Deprecated
	public static Iterator<Integer> counter(final int start, final int increment) {
		final Counter counter = new Counter(start, increment);
		return new Iterator<Integer>() {
			@Override
			public boolean hasNext() {
				return true;
			}
			
			@Override
			public Integer next() {
				final int value = counter.get();
				counter.inc();
				return value;
			}
			
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	private Iterators() {
		// Prevent instantiation.
	}
}
