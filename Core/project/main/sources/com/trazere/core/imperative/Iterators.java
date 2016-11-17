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
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/**
 * The {@link Iterators} class provides various factories of {@link Iterator iterators}.
 * 
 * @see Iterator
 * @since 2.0
 */
public class Iterators {
	/**
	 * Builds an empty iterator.
	 * 
	 * @param <E> Type of the elements.
	 * @return The built iterator.
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> ExIterator<E> empty() {
		return (ExIterator<E>) EMPTY;
	}
	
	private static final ExIterator<?> EMPTY = new ExIterator<Object>() {
		// Iterator.
		
		@Override
		public boolean hasNext() {
			return false;
		}
		
		@Override
		public Object next() {
			throw new NoSuchElementException();
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void forEachRemaining(final Consumer<? super Object> action) {
			// Nothing to do.
		}
		
		// ExIterator.
		
		@Override
		public Maybe<Object> optionalNext() {
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
		
		@Override
		public <E2> PairIterator<Object, E2> zip(final Iterator<? extends E2> iterator2) {
			return PairIterators.empty();
		}
		
		@Override
		public ExIterator<Object> unmodifiable() {
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
		public ExIterator<Object> take(final int n) {
			return empty();
		}
		
		@Override
		public ExIterator<Object> drop(final int n) {
			return empty();
		}
		
		@Override
		public <B extends Collection<? super Object>> ExIterator<B> group(final int n, final CollectionFactory<? super Object, B> batchFactory) {
			return empty();
		}
		
		@Override
		public ExIterator<Object> filter(final Predicate<? super Object> filter) {
			return empty();
		}
		
		@Override
		public Maybe<Object> filterAny(final Predicate<? super Object> filter) {
			return Maybe.none();
		}
		
		@Override
		public <TE> ExIterator<TE> map(final Function<? super Object, ? extends TE> function) {
			return empty();
		}
		
		@Override
		public <EE> ExIterator<EE> extract(final Function<? super Object, ? extends Maybe<? extends EE>> extractor) {
			return empty();
		}
		
		@Override
		public <EE> Maybe<EE> extractAny(final Function<? super Object, ? extends Maybe<? extends EE>> extractor) {
			return Maybe.none();
		}
		
		@Override
		public <EE> ExIterator<EE> extractAll(final Function<? super Object, ? extends Iterable<? extends EE>> extractor) {
			return empty();
		}
		
		@Override
		public <TE> ExIterator<TE> flatMap(final Function<? super Object, ? extends Iterator<? extends TE>> extractor) {
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
	 * Builds an iterator over the given element.
	 * 
	 * @param <E> Type of the the element.
	 * @param element Element provided by the iterator to build.
	 * @return The built iterator.
	 * @since 2.0
	 */
	public static <E> ExIterator<E> fromElement(final E element) {
		return new ExIterator<E>() {
			protected boolean _next = true;
			
			@Override
			public boolean hasNext() {
				return _next;
			}
			
			@Override
			public E next()
			throws NoSuchElementException {
				if (_next) {
					_next = false;
					return element;
				} else {
					throw new NoSuchElementException();
				}
			}
		};
	}
	
	/**
	 * Builds an iterator over the given elements.
	 * 
	 * @param <E> Type of the the elements.
	 * @param elements Elements provided by the iterator to build.
	 * @return The built iterator.
	 * @since 2.0
	 */
	@SafeVarargs
	public static <E> ExIterator<E> fromElements(final E... elements) {
		assert null != elements;
		
		return new ExIterator<E>() {
			protected int _index = 0;
			
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
		};
	}
	
	private Iterators() {
		// Prevent instantiation.
	}
}
