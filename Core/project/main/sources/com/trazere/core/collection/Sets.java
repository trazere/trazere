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
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;

/**
 * The {@link Sets} class provides various factories of {@link ExSet sets}.
 * 
 * @see ExSet
 * @since 2.0
 */
public class Sets {
	/**
	 * Builds an unmutable empty set.
	 * 
	 * @param <E> Type of the elements.
	 * @return The built set.
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> ExSet<E> empty() {
		return (ExSet<E>) EMPTY;
	}
	
	private static final ExSet<?> EMPTY = new ExSet<Object>() {
		// Collection.
		
		@Override
		public int size() {
			return 0;
		}
		
		@Override
		public boolean isEmpty() {
			return true;
		}
		
		@Override
		public boolean contains(final Object o) {
			return false;
		}
		
		@Override
		public Object[] toArray() {
			return new Object[0];
		}
		
		@Override
		public <T> T[] toArray(final T[] a) {
			return a;
		}
		
		@Override
		public boolean add(final Object e) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean remove(final Object o) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean containsAll(final Collection<?> c) {
			return false;
		}
		
		@Override
		public boolean addAll(final Collection<? extends Object> c) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean removeAll(final Collection<?> c) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean retainAll(final Collection<?> c) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}
		
		// Iterable.
		
		@Override
		public ExIterator<Object> iterator() {
			return Iterators.empty();
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
		public ExSet<Object> take(final int n) {
			return Sets.empty();
		}
		
		@Override
		public ExSet<Object> drop(final int n) {
			return Sets.empty();
		}
		
		@Override
		public <B extends Collection<? super Object>> ExSet<B> group(final int n, final CollectionFactory<? super Object, B> batchFactory) {
			return Sets.empty();
		}
		
		@Override
		public ExSet<Object> filter(final Predicate<? super Object> filter) {
			return Sets.empty();
		}
		
		@Override
		public Maybe<Object> filterAny(final Predicate<? super Object> filter) {
			return Maybe.none();
		}
		
		@Override
		public <TE> ExSet<TE> map(final Function<? super Object, ? extends TE> function) {
			return Sets.empty();
		}
		
		@Override
		public <EE> ExSet<EE> extract(final Function<? super Object, ? extends Maybe<? extends EE>> extractor) {
			return Sets.empty();
		}
		
		@Override
		public <EE> Maybe<EE> extractAny(final Function<? super Object, ? extends Maybe<? extends EE>> extractor) {
			return Maybe.none();
		}
		
		@Override
		public void foreach(final Procedure<? super Object> procedure) {
			// Nothing to do.
		}
	};
	
	/**
	 * Builds a new set containing the given element.
	 * 
	 * @param <E> Type of the the element.
	 * @param element Element of the set to build.
	 * @return The built set.
	 * @since 2.0
	 */
	public static <E> ExSet<E> fromElement(final E element) {
		final ExSet<E> set = new SimpleSet<>(1);
		set.add(element);
		return set;
	}
	
	/**
	 * Builds a new set containing the given elements.
	 * 
	 * @param <E> Type of the the elements.
	 * @param element1 First element of the set to build.
	 * @param element2 Second element of the set to build.
	 * @return The built set.
	 * @since 2.0
	 */
	public static <E> ExSet<E> fromElements(final E element1, final E element2) {
		final ExSet<E> set = new SimpleSet<>(2);
		set.add(element1);
		set.add(element2);
		return set;
	}
	
	/**
	 * Builds a new set containing the given elements.
	 * 
	 * @param <E> Type of the the elements.
	 * @param element1 First element of the set to build.
	 * @param element2 Second element of the set to build.
	 * @param element3 Third element of the set to build.
	 * @return The built set.
	 * @since 2.0
	 */
	public static <E> ExSet<E> fromElements(final E element1, final E element2, final E element3) {
		final ExSet<E> set = new SimpleSet<>(3);
		set.add(element1);
		set.add(element2);
		set.add(element3);
		return set;
	}
	
	/**
	 * Builds a new set containing the given elements.
	 * 
	 * @param <E> Type of the the elements.
	 * @param element1 First element of the set to build.
	 * @param element2 Second element of the set to build.
	 * @param element3 Third element of the set to build.
	 * @param element4 Fourth element of the set to build.
	 * @return The built set.
	 * @since 2.0
	 */
	public static <E> ExSet<E> fromElements(final E element1, final E element2, final E element3, final E element4) {
		final ExSet<E> set = new SimpleSet<>(4);
		set.add(element1);
		set.add(element2);
		set.add(element3);
		set.add(element4);
		return set;
	}
	
	/**
	 * Builds a new set containing the given elements.
	 * 
	 * @param <E> Type of the the elements.
	 * @param element1 First element of the set to build.
	 * @param element2 Second element of the set to build.
	 * @param element3 Third element of the set to build.
	 * @param element4 Fourth element of the set to build.
	 * @param element5 Fifth element of the set to build.
	 * @return The built set.
	 * @since 2.0
	 */
	public static <E> ExSet<E> fromElements(final E element1, final E element2, final E element3, final E element4, final E element5) {
		final ExSet<E> set = new SimpleSet<>(5);
		set.add(element1);
		set.add(element2);
		set.add(element3);
		set.add(element4);
		set.add(element5);
		return set;
	}
	
	/**
	 * Builds a new set containing the given elements.
	 * 
	 * @param <E> Type of the the elements.
	 * @param elements Elements of the set to build
	 * @return The built set.
	 * @since 2.0
	 */
	@SafeVarargs
	public static <E> ExSet<E> fromElements(final E... elements) {
		final ExSet<E> set = new SimpleSet<>(elements.length);
		for (final E element : elements) {
			set.add(element);
		}
		return set;
	}
	
	/**
	 * Builds a new set containing the elements provided by the given iterable.
	 * 
	 * @param <E> Type of the the elements.
	 * @param iterable Iterable providing the elements of the set to build.
	 * @return The built set.
	 * @since 2.0
	 */
	public static <E> ExSet<E> fromIterable(final Iterable<? extends E> iterable) {
		final ExSet<E> set = new SimpleSet<>();
		for (final E element : iterable) {
			set.add(element);
		}
		return set;
	}
	
	private static class SimpleSet<E>
	extends HashSet<E>
	implements ExSet<E> {
		private static final long serialVersionUID = 1L;
		
		public SimpleSet() {
			super();
		}
		
		public SimpleSet(final int initialCapacity) {
			super(initialCapacity);
		}
		
		@Override
		public ExIterator<E> iterator() {
			return ExIterator.build(super.iterator());
		}
	}
	
	private Sets() {
		// Prevent instantiation.
	}
}
