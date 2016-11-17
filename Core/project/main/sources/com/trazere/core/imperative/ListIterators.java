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
package com.trazere.core.imperative;

import com.trazere.core.collection.CollectionFactory;
import com.trazere.core.functional.Function;
import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Predicate;
import com.trazere.core.util.Maybe;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/**
 * The {@link ListIterators} class provides various factories of {@link ListIterator list iterators}.
 * 
 * @see ListIterator
 * @since 2.0
 */
public class ListIterators {
	/**
	 * Builds an empty list iterator.
	 * 
	 * @param <E> Type of the elements.
	 * @return The built iterator.
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> ExListIterator<E> empty() {
		return (ExListIterator<E>) EMPTY;
	}
	
	private static final ExListIterator<?> EMPTY = new ExListIterator<Object>() {
		// ListIterator.
		
		@Override
		public boolean hasNext() {
			return false;
		}
		
		@Override
		public Object next() {
			throw new NoSuchElementException();
		}
		
		@Override
		public boolean hasPrevious() {
			return false;
		}
		
		@Override
		public Object previous() {
			throw new NoSuchElementException();
		}
		
		@Override
		public int nextIndex() {
			return 0;
		}
		
		@Override
		public int previousIndex() {
			return -1;
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void set(final Object e) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void add(final Object e) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void forEachRemaining(final Consumer<? super Object> action) {
			// Nothing to do.
		}
		
		// ExListIterator.
		
		@Override
		public Maybe<Object> optionalNext() {
			return Maybe.none();
		}
		
		@Override
		public Maybe<Object> optionalPrevious() {
			return Maybe.none();
		}
		
		@Override
		public void drain() {
			// Nothing to do.
		}
		
		@Override
		public <A extends Accumulator<? super Object, ?>> A drain(final A results) {
			return results;
		}
		
		@Override
		public <C extends Collection<? super Object>> C drain(final C results) {
			return results;
		}
		
		// ExIterator.
		
		@Override
		public ExListIterator<Object> unmodifiable() {
			return empty();
		}
		
		// Traversable.
		
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
		public ExListIterator<Object> take(final int n) {
			return empty();
		}
		
		@Override
		public ExListIterator<Object> drop(final int n) {
			return empty();
		}
		
		@Override
		public <B extends Collection<? super Object>> ExListIterator<B> group(final int n, final CollectionFactory<? super Object, B> batchFactory) {
			return empty();
		}
		
		@Override
		public ExListIterator<Object> filter(final Predicate<? super Object> filter) {
			return empty();
		}
		
		@Override
		public Maybe<Object> filterAny(final Predicate<? super Object> filter) {
			return Maybe.none();
		}
		
		@Override
		public <TE> ExListIterator<TE> map(final Function<? super Object, ? extends TE> function) {
			return empty();
		}
		
		@Override
		public <EE> ExListIterator<EE> extract(final Function<? super Object, ? extends Maybe<? extends EE>> extractor) {
			return empty();
		}
		
		@Override
		public <EE> Maybe<EE> extractAny(final Function<? super Object, ? extends Maybe<? extends EE>> extractor) {
			return Maybe.none();
		}
		
		@Override
		public <TE> ExListIterator<TE> extractAll(final Function<? super Object, ? extends List<? extends TE>> extractor) {
			return empty();
		}
		
		@Override
		public <TE> ExListIterator<TE> flatMap(final Function<? super Object, ? extends ListIterator<? extends TE>> extractor) {
			return empty();
		}
		
		@Override
		public void foreach(final Procedure<? super Object> procedure) {
			// Nothing to do.
		}
		
		// Object.
		// FIXME
	};
	
	/**
	 * Builds a list iterator over the given element.
	 * 
	 * @param <E> Type of the the element.
	 * @param element Element provided by the iterator to build.
	 * @return The built iterator.
	 * @since 2.0
	 */
	public static <E> ExListIterator<E> fromElement(final E element) {
		return new ExListIterator<E>() {
			private int _index = 0;
			
			@Override
			public boolean hasNext() {
				return _index < 1;
			}
			
			@Override
			public E next()
			throws NoSuchElementException {
				if (_index < 1) {
					_index += 1;
					return element;
				} else {
					throw new NoSuchElementException();
				}
			}
			
			@Override
			public int nextIndex() {
				return _index;
			}
			
			@Override
			public boolean hasPrevious() {
				return _index > 0;
			}
			
			@Override
			public E previous() {
				if (_index > 0) {
					_index -= 1;
					return element;
				} else {
					throw new NoSuchElementException();
				}
			}
			
			@Override
			public int previousIndex() {
				return _index - 1;
			}
		};
	}
	
	/**
	 * Builds a list iterator over the given elements.
	 * 
	 * @param <E> Type of the the elements.
	 * @param elements Elements provided by the iterator to build.
	 * @return The built iterator.
	 * @since 2.0
	 */
	@SafeVarargs
	public static <E> ExListIterator<E> fromElements(final E... elements) {
		assert null != elements;
		
		return new ExListIterator<E>() {
			private int _index = 0;
			
			@Override
			public boolean hasNext() {
				return _index < elements.length;
			}
			
			@Override
			public E next()
			throws NoSuchElementException {
				if (_index < elements.length) {
					final E value = elements[_index];
					_index += 1;
					
					return value;
				} else {
					throw new NoSuchElementException();
				}
			}
			
			@Override
			public int nextIndex() {
				return _index;
			}
			
			@Override
			public boolean hasPrevious() {
				return _index > 0;
			}
			
			@Override
			public E previous() {
				if (_index > 0) {
					_index -= 1;
					return elements[_index];
				} else {
					throw new NoSuchElementException();
				}
			}
			
			@Override
			public int previousIndex() {
				return _index - 1;
			}
		};
	}
	
	private ListIterators() {
		// Prevent instantiation.
	}
}
