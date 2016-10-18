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
package com.trazere.core.lang;

import com.trazere.core.collection.CollectionFactory;
import com.trazere.core.functional.Function;
import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Predicate;
import com.trazere.core.imperative.ExIterator;
import com.trazere.core.imperative.Iterators;
import com.trazere.core.imperative.Procedure;
import com.trazere.core.util.Maybe;
import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

/**
 * The {@link Iterables} class provides various factories of {@link Iterable iterables}.
 * 
 * @see Iterable
 * @since 2.0
 */
public class Iterables {
	/**
	 * Builds an empty iterable.
	 * 
	 * @param <E> Type of the elements.
	 * @return The built iterable.
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> ExIterable<E> empty() {
		return (ExIterable<E>) EMPTY;
	}
	
	private static final ExIterable<?> EMPTY = new ExIterable<Object>() {
		// Iterable.
		
		@Override
		public ExIterator<Object> iterator() {
			return Iterators.empty();
		}
		
		@Override
		public void forEach(final Consumer<? super Object> action) {
			// Nothing to do.
		}
		
		@Override
		public Spliterator<Object> spliterator() {
			return Spliterators.emptySpliterator();
		}
		
		// ExIterable.
		
		@Override
		public Object any()
		throws NoSuchElementException {
			throw new NoSuchElementException();
		}
		
		@Override
		public Maybe<Object> optionalAny() {
			return Maybe.none();
		}
		
		@Override
		public <E2> PairIterable<Object, E2> zip(final Iterable<? extends E2> iterable2) {
			return PairIterables.empty();
		}
		
		@Override
		public ExIterable<Object> unmodifiable() {
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
		public ExIterable<Object> take(final int n) {
			return empty();
		}
		
		@Override
		public ExIterable<Object> drop(final int n) {
			return empty();
		}
		
		@Override
		public <B extends Collection<? super Object>> ExIterable<B> group(final int n, final CollectionFactory<? super Object, B> batchFactory) {
			return empty();
		}
		
		@Override
		public ExIterable<Object> filter(final Predicate<? super Object> filter) {
			return empty();
		}
		
		@Override
		public Maybe<Object> filterAny(final Predicate<? super Object> filter) {
			return Maybe.none();
		}
		
		@Override
		public <TE> ExIterable<TE> map(final Function<? super Object, ? extends TE> function) {
			return empty();
		}
		
		@Override
		public <EE> ExIterable<EE> extract(final Function<? super Object, ? extends Maybe<? extends EE>> extractor) {
			return empty();
		}
		
		@Override
		public <EE> Maybe<EE> extractAny(final Function<? super Object, ? extends Maybe<? extends EE>> extractor) {
			return Maybe.none();
		}
		
		@Override
		public <EE> ExIterable<EE> extractAll(final Function<? super Object, ? extends Iterable<? extends EE>> extractor) {
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
	 * Builds an iterable over the given element.
	 * 
	 * @param <E> Type of the the element.
	 * @param element Element provided by the iterable to build.
	 * @return The built iterable.
	 * @since 2.0
	 */
	public static <E> ExIterable<E> fromElement(final E element) {
		return new ExIterable<E>() {
			@Override
			public ExIterator<E> iterator() {
				return Iterators.fromElement(element);
			}
		};
	}
	
	/**
	 * Builds an iterable over the given elements.
	 * 
	 * @param <E> Type of the the elements.
	 * @param elements Elements provided by the iterable to build.
	 * @return The built iterable.
	 * @since 2.0
	 */
	@SafeVarargs
	public static <E> ExIterable<E> fromElements(final E... elements) {
		assert null != elements;
		
		return new ExIterable<E>() {
			@Override
			public ExIterator<E> iterator() {
				return Iterators.fromElements(elements);
			}
		};
	}
	
	private Iterables() {
		// Prevent instantiation.
	}
}
