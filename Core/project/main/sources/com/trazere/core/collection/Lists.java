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
import com.trazere.core.imperative.ExListIterator;
import com.trazere.core.imperative.Iterators;
import com.trazere.core.imperative.ListIterators;
import com.trazere.core.imperative.Procedure;
import com.trazere.core.lang.PairIterable;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/**
 * The {@link Lists} class provides various factories of {@link List lists}.
 * 
 * @see List
 * @since 2.0
 */
public class Lists {
	/**
	 * Builds an unmutable empty list.
	 * 
	 * @param <E> Type of the elements.
	 * @return The built list.
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> ExList<E> empty() {
		return (ExList<E>) EMPTY;
	}
	
	private static final ExList<?> EMPTY = new ExList<Object>() {
		// List.
		
		@Override
		public boolean addAll(final int index, final Collection<? extends Object> c) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void replaceAll(final UnaryOperator<Object> operator) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void sort(final Comparator<? super Object> c) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public Object get(final int index) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: 0");
		}
		
		@Override
		public Object set(final int index, final Object element) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void add(final int index, final Object element) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public Object remove(final int index) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public int indexOf(final Object o) {
			return -1;
		}
		
		@Override
		public int lastIndexOf(final Object o) {
			return -1;
		}
		
		@Override
		public ExListIterator<Object> listIterator() {
			return ListIterators.empty();
		}
		
		@Override
		public ExListIterator<Object> listIterator(final int index) {
			if (index > 0) {
				throw new IndexOutOfBoundsException("Index: " + index + ", Size: 0");
			} else {
				return ListIterators.empty();
			}
		}
		
		@Override
		public ExList<Object> subList(final int fromIndex, final int toIndex) {
			if (fromIndex > 0) {
				throw new IndexOutOfBoundsException("From index: " + fromIndex + ", Size: 0");
			} else if (toIndex > 0) {
				throw new IndexOutOfBoundsException("To index: " + toIndex + ", Size: 0");
			} else {
				return empty();
			}
		}
		
		// ExList.
		
		@Override
		public Object first()
		throws IndexOutOfBoundsException {
			throw new IndexOutOfBoundsException("Index: 0, Size: 0");
		}
		
		@Override
		public Maybe<Object> optionalFirst() {
			return Maybe.none();
		}
		
		@Override
		public Object last()
		throws IndexOutOfBoundsException {
			throw new IndexOutOfBoundsException("Index: 0, Size: 0");
		}
		
		@Override
		public Maybe<Object> optionalLast() {
			return Maybe.none();
		}
		
		@Override
		public Maybe<Object> optionalGet(final int index) {
			return Maybe.none();
		}
		
		@Override
		public boolean addAll(final int index, final Object... elements) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean addAll(final int index, final Iterable<? extends Object> elements) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public Maybe<Object> optionalRemove(final int index) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void reverse() {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void shuffle() {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void shuffle(final Random random) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public <E2> ExList<Tuple2<Object, E2>> zip(final List<? extends E2> list2) {
			return empty();
		}
		
		@Override
		public ExList<Object> reversed() {
			return empty();
		}
		
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
		public boolean removeIf(final java.util.function.Predicate<? super Object> filter) {
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
		
		// ExCollection.
		
		@Override
		public boolean intersects(final Collection<? extends Object> collection) {
			return false;
		}
		
		@Override
		public boolean addAll(final Object... elements) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean addAll(final Iterable<? extends Object> elements) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public Maybe<Object> removeAny() {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public Maybe<Object> removeAny(final Predicate<? super Object> filter) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean removeAll(final Object... elements) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean removeAll(final Iterable<? extends Object> elements) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean removeAll(final Predicate<? super Object> filter) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean retainAll(final Predicate<? super Object> filter) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public <C extends Collection<? super Object>> C take(final int n, final CollectionFactory<? super Object, C> resultFactory) {
			return resultFactory.build();
		}
		
		@Override
		public <C extends Collection<? super Object>> C drop(final int n, final CollectionFactory<? super Object, C> resultFactory) {
			return resultFactory.build();
		}
		
		@Override
		public <B extends Collection<? super Object>, C extends Collection<? super B>> C group(final int n, final CollectionFactory<? super Object, B> batchFactory, final CollectionFactory<? super B, C> resultFactory) {
			return resultFactory.build();
		}
		
		@Override
		public <C extends Collection<? super Object>> C filter(final Predicate<? super Object> filter, final CollectionFactory<? super Object, C> resultFactory) {
			return resultFactory.build();
		}
		
		@Override
		public <TE, C extends Collection<? super TE>> C map(final Function<? super Object, ? extends TE> function, final CollectionFactory<? super TE, C> resultFactory) {
			return resultFactory.build();
		}
		
		@Override
		public <EE, C extends Collection<? super EE>> C extract(final Function<? super Object, ? extends Maybe<? extends EE>> extractor, final CollectionFactory<? super EE, C> resultFactory) {
			return resultFactory.build();
		}
		
		@Override
		public <EE, C extends Collection<? super EE>> C extractAll(final Function<? super Object, ? extends Iterable<? extends EE>> extractor, final CollectionFactory<? super EE, C> resultFactory) {
			return resultFactory.build();
		}
		
		@Override
		public <C extends Collection<? super Object>> C append(final Collection<? extends Object> collection2, final CollectionFactory<? super Object, C> resultFactory) {
			return resultFactory.build();
		}
		
		// TODO: flatMap
		// TODO: intersect
		// TODO: exclude
		
		@Override
		public <E2> ExCollection<Tuple2<Object, E2>> zip(final Collection<? extends E2> collection2) {
			return empty();
		}
		
		@Override
		public <E2, C extends Collection<? super Tuple2<? extends Object, ? extends E2>>> C zip(final Collection<? extends E2> collection2, final CollectionFactory<? super Tuple2<? extends Object, ? extends E2>, C> resultFactory) {
			return resultFactory.build();
		}
		
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
		public ExList<Object> unmodifiable() {
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
		public ExList<Object> take(final int n) {
			return empty();
		}
		
		@Override
		public ExList<Object> drop(final int n) {
			return empty();
		}
		
		@Override
		public <B extends Collection<? super Object>> ExList<B> group(final int n, final CollectionFactory<? super Object, B> batchFactory) {
			return empty();
		}
		
		@Override
		public ExList<Object> filter(final Predicate<? super Object> filter) {
			return empty();
		}
		
		@Override
		public Maybe<Object> filterAny(final Predicate<? super Object> filter) {
			return Maybe.none();
		}
		
		@Override
		public <TE> ExList<TE> map(final Function<? super Object, ? extends TE> function) {
			return empty();
		}
		
		@Override
		public <EE> ExList<EE> extract(final Function<? super Object, ? extends Maybe<? extends EE>> extractor) {
			return empty();
		}
		
		@Override
		public <EE> Maybe<EE> extractAny(final Function<? super Object, ? extends Maybe<? extends EE>> extractor) {
			return Maybe.none();
		}
		
		@Override
		public <EE> ExList<EE> extractAll(final Function<? super Object, ? extends Iterable<? extends EE>> extractor) {
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
	 * Builds a new list containing the given element.
	 * 
	 * @param <E> Type of the element.
	 * @param element Element of the list to build.
	 * @return The built list.
	 * @since 2.0
	 */
	public static <E> ExList<E> fromElement(final E element) {
		final ExList<E> list = new SimpleList<>(1);
		list.add(element);
		return list;
	}
	
	/**
	 * Builds a new list containing the given elements.
	 * 
	 * @param <E> Type of the elements.
	 * @param element1 First element of the list to build.
	 * @param element2 Second element of the list to build.
	 * @return The built list.
	 * @since 2.0
	 */
	public static <E> ExList<E> fromElements(final E element1, final E element2) {
		final ExList<E> list = new SimpleList<>(2);
		list.add(element1);
		list.add(element2);
		return list;
	}
	
	/**
	 * Builds a new list containing the given elements.
	 * 
	 * @param <E> Type of the elements.
	 * @param element1 First element of the list to build.
	 * @param element2 Second element of the list to build.
	 * @param element3 Third element of the list to build.
	 * @return The built list.
	 * @since 2.0
	 */
	public static <E> ExList<E> fromElements(final E element1, final E element2, final E element3) {
		final ExList<E> list = new SimpleList<>(3);
		list.add(element1);
		list.add(element2);
		list.add(element3);
		return list;
	}
	
	/**
	 * Builds a new list containing the given elements.
	 * 
	 * @param <E> Type of the elements.
	 * @param element1 First element of the list to build.
	 * @param element2 Second element of the list to build.
	 * @param element3 Third element of the list to build.
	 * @param element4 Fourth element of the list to build.
	 * @return The built list.
	 * @since 2.0
	 */
	public static <E> ExList<E> fromElements(final E element1, final E element2, final E element3, final E element4) {
		final ExList<E> list = new SimpleList<>(4);
		list.add(element1);
		list.add(element2);
		list.add(element3);
		list.add(element4);
		return list;
	}
	
	/**
	 * Builds a new list containing the given elements.
	 * 
	 * @param <E> Type of the elements.
	 * @param element1 First element of the list to build.
	 * @param element2 Second element of the list to build.
	 * @param element3 Third element of the list to build.
	 * @param element4 Fourth element of the list to build.
	 * @param element5 Fifth element of the list to build.
	 * @return The built list.
	 * @since 2.0
	 */
	public static <E> ExList<E> fromElements(final E element1, final E element2, final E element3, final E element4, final E element5) {
		final ExList<E> list = new SimpleList<>(5);
		list.add(element1);
		list.add(element2);
		list.add(element3);
		list.add(element4);
		list.add(element5);
		return list;
	}
	
	/**
	 * Builds a new list containing the given elements.
	 * 
	 * @param <E> Type of the elements.
	 * @param elements Elements of the list to build
	 * @return The built list.
	 * @since 2.0
	 */
	@SafeVarargs
	public static <E> ExList<E> fromElements(final E... elements) {
		final ExList<E> list = new SimpleList<>(elements.length);
		list.addAll(elements);
		return list;
	}
	
	/**
	 * Builds a new list containing the elements provided by the given iterable
	 *
	 * @param <E> Type of the elements.
	 * @param iterable Iterable providing the elements of the list to build.
	 * @return The built list.
	 * @since 2.0
	 */
	public static <E> ExList<E> fromIterable(final Iterable<? extends E> iterable) {
		final ExList<E> list = new SimpleList<>();
		list.addAll(iterable);
		return list;
	}
	
	private static class SimpleList<E>
	extends ArrayList<E>
	implements ExList<E> {
		private static final long serialVersionUID = 1L;
		
		public SimpleList() {
			super();
		}
		
		public SimpleList(final int initialCapacity) {
			super(initialCapacity);
		}
		
		@Override
		public ExIterator<E> iterator() {
			return ExIterator.build(super.iterator());
		}
		
		@Override
		public ExListIterator<E> listIterator() {
			return ExListIterator.build(super.listIterator());
		}
		
		@Override
		public ExListIterator<E> listIterator(final int index) {
			return ExListIterator.build(super.listIterator(index));
		}
		
		@Override
		public ExList<E> subList(final int fromIndex, final int toIndex) {
			return ExList.build(super.subList(fromIndex, toIndex));
		}
	}
	
	private Lists() {
		// Prevent instantiation.
	}
}
