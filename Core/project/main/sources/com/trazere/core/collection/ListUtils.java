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
import com.trazere.core.functional.Predicate;
import com.trazere.core.imperative.Accumulator;
import com.trazere.core.imperative.Accumulator2;
import com.trazere.core.imperative.Accumulators;
import com.trazere.core.imperative.ExIterator;
import com.trazere.core.imperative.ExListIterator;
import com.trazere.core.imperative.ImperativePredicates;
import com.trazere.core.imperative.IteratorUtils;
import com.trazere.core.lang.IterableUtils;
import com.trazere.core.lang.LangAccumulators;
import com.trazere.core.lang.PairIterable;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.function.UnaryOperator;

/**
 * The {@link ListUtils} class provides various utilities regarding {@link List lists}.
 * 
 * @see List
 * @since 2.0
 */
public class ListUtils {
	/**
	 * Gets the first element of the given list.
	 *
	 * @param <E> Type of the elements.
	 * @param list List to read.
	 * @return The first element of the list.
	 * @throws IndexOutOfBoundsException When the list is empty.
	 * @since 2.0
	 */
	public static <E> E first(final List<? extends E> list)
	throws IndexOutOfBoundsException {
		return list.get(0);
	}
	
	/**
	 * Gets the first element of the given list.
	 * <p>
	 * This methods support empty lists.
	 *
	 * @param <E> Type of the elements.
	 * @param list List to read.
	 * @return The first element of the list, or nothing when the list is empty.
	 * @since 2.0
	 */
	public static <E> Maybe<E> optionalFirst(final List<? extends E> list) {
		return !list.isEmpty() ? Maybe.some(list.get(0)) : Maybe.none();
	}
	
	/**
	 * Gets the last element of the given list.
	 *
	 * @param <E> Type of the elements.
	 * @param list List to read.
	 * @return The last element.
	 * @throws IndexOutOfBoundsException When the list is empty.
	 * @since 2.0
	 */
	public static <E> E last(final List<? extends E> list)
	throws IndexOutOfBoundsException {
		return list.get(list.size() - 1);
	}
	
	/**
	 * Gets the last element of the given list.
	 * <p>
	 * This methods support empty lists.
	 *
	 * @param <E> Type of the elements.
	 * @param list List to read.
	 * @return The last element, or nothing when the list is empty.
	 * @since 2.0
	 */
	public static <E> Maybe<E> optionalLast(final List<? extends E> list) {
		final int size = list.size();
		return size > 0 ? Maybe.some(list.get(size - 1)) : Maybe.none();
	}
	
	/**
	 * Gets the element of the given list at the given position.
	 * <p>
	 * This methods support indexes out of bounds.
	 *
	 * @param <E> Type of the elements.
	 * @param list List to read.
	 * @param index Index of the element to get.
	 * @return The specified element, or nothing when the index is out of bounds.
	 * @since 2.0
	 */
	public static <E> Maybe<E> optionalGet(final List<? extends E> list, final int index) {
		return index < list.size() ? Maybe.<E>some(list.get(index)) : Maybe.<E>none();
	}
	
	/**
	 * Inserts all given elements in the given list at the given position.
	 * <p>
	 * This method does modify the given list.
	 *
	 * @param <E> Type of the elements.
	 * @param list List to modify.
	 * @param index Index at which the elements should be inserted.
	 * @param elements Elements to insert.
	 * @return <code>true</code> when the given list is modified, <code>false</code> otherwise.
	 * @since 2.0
	 */
	@SafeVarargs
	public static <E> boolean addAll(final List<? super E> list, final int index, final E... elements) {
		int iterIndex = index;
		for (final E element : elements) {
			list.add(iterIndex, element);
			iterIndex += 1;
		}
		return iterIndex > index;
	}
	
	/**
	 * Inserts all given elements in the given list at the given position.
	 * <p>
	 * This method does modify the given list.
	 *
	 * @param <E> Type of the elements.
	 * @param list List to modify.
	 * @param index Index at which the elements should be inserted.
	 * @param elements Elements to insert.
	 * @return <code>true</code> when the given list is modified, <code>false</code> otherwise.
	 * @since 2.0
	 */
	public static <E> boolean addAll(final List<? super E> list, final int index, final Iterable<? extends E> elements) {
		int iterIndex = index;
		for (final E element : elements) {
			list.add(iterIndex, element);
			iterIndex += 1;
		}
		return iterIndex > index;
	}
	
	// TODO: safeSet
	// TODO: optionalSet
	
	/**
	 * Removes the element of the given list at the given position.
	 * <p>
	 * This method does modify the given list.
	 * 
	 * @param <E> Type of the elements.
	 * @param list List to modify.
	 * @param index Index of the element to remove.
	 * @return The removed element, or nothing when the index is out of bound.
	 * @since 2.0
	 */
	public static <E> Maybe<E> optionalRemove(final List<? extends E> list, final int index) {
		if (index < list.size()) {
			return Maybe.some(list.remove(index));
		} else {
			return Maybe.none();
		}
	}
	
	/**
	 * Takes n elements of the given list.
	 * <p>
	 * The first elements of the list are taken.
	 *
	 * @param <E> Type of the elements.
	 * @param list List of the elements to take.
	 * @param n Number of elements to take.
	 * @return A list of the taken elements.
	 * @since 2.0
	 */
	public static <E> ExList<E> take(final List<? extends E> list, final int n) {
		assert null != list;
		
		return new AbstractExList<E>() {
			@Override
			public E get(final int index) {
				if (index < n) {
					return list.get(index);
				} else {
					throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + n);
				}
			}
			
			@Override
			public int size() {
				return Math.min(list.size(), n);
			}
			
			@Override
			public ExIterator<E> iterator() {
				return IteratorUtils.take(list.iterator(), n);
			}
		};
	}
	
	/**
	 * Drops n elements of the given list.
	 * <p>
	 * The fist elements of the list are dropped.
	 *
	 * @param <E> Type of the elements.
	 * @param list List of the elements to drop.
	 * @param n Number of elements to drop.
	 * @return A list of the remaining elements.
	 * @since 2.0
	 */
	public static <E> ExList<E> drop(final List<? extends E> list, final int n) {
		assert null != list;
		
		return new AbstractExList<E>() {
			@Override
			public E get(final int index) {
				if (index < (list.size() - n)) {
					return list.get(index + n);
				} else {
					throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + (list.size() - n));
				}
			}
			
			@Override
			public int size() {
				return Math.max(list.size() - n, 0);
			}
			
			@Override
			public ExIterator<E> iterator() {
				return IteratorUtils.drop(list.iterator(), n);
			}
		};
	}
	
	/**
	 * Groups the elements of the given list into batches of the given size.
	 *
	 * @param <E> Type of the elements.
	 * @param <B> Type of the batch collections.
	 * @param list List of the elements to group.
	 * @param n Number of elements of each batch.
	 * @param batchFactory Factory of the batch collections.
	 * @return A list of the batches of elements.
	 * @since 2.0
	 */
	public static <E, B extends Collection<? super E>> ExList<B> group(final List<? extends E> list, final int n, final CollectionFactory<? super E, B> batchFactory) {
		assert null != list;
		assert null != batchFactory;
		
		return new AbstractExList<B>() {
			@Override
			public B get(final int index) {
				final int start = index * n;
				final int size = list.size();
				if (start < size) {
					return batchFactory.build(list.subList(start, Math.min(start + n, size)));
				} else {
					throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + (size + n - 1) / n);
				}
			}
			
			@Override
			public int size() {
				return (list.size() + n - 1) / n;
			}
			
			@Override
			public ExIterator<B> iterator() {
				return IteratorUtils.group(list.iterator(), n, batchFactory);
			}
		};
	}
	
	/**
	 * Filters the elements of the given list using the given filter.
	 *
	 * @param <E> Type of the elements.
	 * @param list List of the elements to filter.
	 * @param filter Predicate to use to filter the elements.
	 * @return A list of the filtered elements.
	 * @since 2.0
	 */
	public static <E> ExList<E> filter(final List<? extends E> list, final Predicate<? super E> filter) {
		assert null != list;
		assert null != filter;
		
		return new AbstractExList<E>() {
			@Override
			public E get(final int index) {
				return iterator().drop(index).optionalNext().get(() -> {
					throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
				});
			}
			
			@Override
			public int size() {
				return IterableUtils.count(list, filter);
			}
			
			@Override
			public ExIterator<E> iterator() {
				return IteratorUtils.filter(list.iterator(), filter);
			}
		};
	}
	
	/**
	 * Transforms the elements of the given list using the given function.
	 *
	 * @param <E> Type of the elements.
	 * @param <TE> Type of the transformed elements.
	 * @param list List of the elements to transform.
	 * @param function Function to use to transform the elements.
	 * @return A list of the transformed elements.
	 * @since 2.0
	 */
	public static <E, TE> ExList<TE> map(final List<? extends E> list, final Function<? super E, ? extends TE> function) {
		assert null != list;
		assert null != function;
		
		return new AbstractExList<TE>() {
			@Override
			public TE get(final int index) {
				return function.evaluate(list.get(index));
			}
			
			@Override
			public int size() {
				return list.size();
			}
			
			@Override
			public ExIterator<TE> iterator() {
				return IteratorUtils.map(list.iterator(), function);
			}
		};
	}
	
	/**
	 * Extracts the elements from the elements of the given list using the given extractor.
	 *
	 * @param <E> Type of the elements.
	 * @param <EE> Type of the extracted elements.
	 * @param list List of the elements to extract from.
	 * @param extractor Function to use to extract from the elements.
	 * @return A list of the extracted elements.
	 * @since 2.0
	 */
	public static <E, EE> ExList<EE> extract(final List<? extends E> list, final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
		assert null != list;
		assert null != extractor;
		
		return new AbstractExList<EE>() {
			@Override
			public EE get(final int index) {
				return iterator().drop(index).optionalNext().get(() -> {
					throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
				});
			}
			
			@Override
			public int size() {
				return IteratorUtils.count(list.iterator(), e -> extractor.evaluate(e).isSome());
			}
			
			@Override
			public ExIterator<EE> iterator() {
				return IteratorUtils.extract(list.iterator(), extractor);
			}
		};
	}
	
	/**
	 * Gets all elements extracted from the elements of the given list using the given extractor.
	 * 
	 * @param <E> Type of the elements.
	 * @param <EE> Type of the extracted elements.
	 * @param list List of the elements to extract from.
	 * @param extractor Function to use to extract from the elements.
	 * @return A list of the extracted elements.
	 * @since 2.0
	 */
	public static <E, EE> ExList<EE> extractAll(final List<? extends E> list, final Function<? super E, ? extends Iterable<? extends EE>> extractor) {
		assert null != list;
		assert null != extractor;
		
		return new AbstractExList<EE>() {
			@Override
			public EE get(final int index) {
				return iterator().drop(index).optionalNext().get(() -> {
					throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
				});
			}
			
			@Override
			public int size() {
				final Accumulator<EE, Integer> size = Accumulators.counter();
				for (final E element : list) {
					size.addAll(extractor.evaluate(element));
				}
				return size.get().intValue();
			}
			
			@Override
			public ExIterator<EE> iterator() {
				return IteratorUtils.extractAll(list.iterator(), extractor);
			}
		};
	}
	
	/**
	 * Appends the given lists together.
	 * 
	 * @param <E> Type of the elements.
	 * @param list1 First list of the elements to append.
	 * @param list2 Second list of the elements to append.
	 * @return A list of the appended elements.
	 * @since 2.0
	 */
	public static <E> ExList<E> append(final List<? extends E> list1, final List<? extends E> list2) {
		assert null != list1;
		assert null != list2;
		
		return new AbstractExList<E>() {
			@Override
			public E get(final int index) {
				final int size1 = list1.size();
				if (index < size1) {
					return list1.get(index);
				} else {
					final int index2 = index - size1;
					final int size2 = list2.size();
					if (index2 < size2) {
						return list2.get(index2);
					} else {
						throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + (size1 + size2));
					}
				}
			}
			
			@Override
			public int size() {
				return list1.size() + list2.size();
			}
			
			@Override
			public ExIterator<E> iterator() {
				return IteratorUtils.append(list1.iterator(), list2.iterator());
			}
		};
	}
	
	/**
	 * Flattens the elements of the lists contained in the given list.
	 *
	 * @param <E> Type of the elements.
	 * @param list List of the lists of the elements to flatten.
	 * @return A list of the flatten elements.
	 * @since 2.0
	 */
	public static <E> ExList<E> flatten(final List<? extends List<? extends E>> list) {
		assert null != list;
		
		return new AbstractExList<E>() {
			@Override
			public E get(final int index) {
				int iterIndex = index;
				for (final List<? extends E> l : list) {
					final int size = l.size();
					if (iterIndex < size) {
						return l.get(iterIndex);
					} else {
						iterIndex -= size;
					}
				}
				throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
			}
			
			@Override
			public int size() {
				return IterableUtils.fold(list, (s, l) -> s + l.size(), 0);
			}
			
			@Override
			public ExIterator<E> iterator() {
				return IteratorUtils.flatten(IterableUtils.map(list, Iterable::iterator).iterator());
			}
		};
	}
	
	/**
	 * Transforms and flattens the elements of the given list using the given function.
	 * 
	 * @param <E> Type of the elements.
	 * @param <TE> Type of the transformed elements.
	 * @param list List of the elements to transform.
	 * @param function Function to use to transform the elements.
	 * @return A list of the flatten, transformed elements.
	 * @since 2.0
	 */
	public static <E, TE> ExList<TE> flatMap(final List<? extends E> list, final Function<? super E, ? extends List<? extends TE>> function) {
		assert null != list;
		assert null != function;
		
		return new AbstractExList<TE>() {
			@Override
			public TE get(final int index) {
				return iterator().drop(index).optionalNext().get(() -> {
					throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
				});
			}
			
			@Override
			public int size() {
				final Accumulator<Integer, Integer> size = LangAccumulators.sum(0);
				for (final E element : list) {
					size.add(function.evaluate(element).size());
				}
				return size.get().intValue();
			}
			
			@Override
			public ExIterator<TE> iterator() {
				return IteratorUtils.flatMap(list.iterator(), element -> function.evaluate(element).iterator());
			}
		};
	}
	
	/**
	 * Composes pairs with the elements of the given lists.
	 * <p>
	 * The pairs are composed of an element of each list according to their iteration order. The extra values of the longest list are dropped when the given
	 * lists don't contain the same number of elements.
	 * 
	 * @param <E1> Type of the first elements.
	 * @param <E2> Type of the second elements.
	 * @param list1 List of the first elements of the pairs.
	 * @param list2 List of the second elements of the pairs.
	 * @return A list of the pairs of elements.
	 * @since 2.0
	 */
	public static <E1, E2> ExList<Tuple2<E1, E2>> zip(final List<? extends E1> list1, final List<? extends E2> list2) {
		assert null != list1;
		assert null != list2;
		
		return new AbstractExList<Tuple2<E1, E2>>() {
			@Override
			public Tuple2<E1, E2> get(final int index) {
				final int size = size();
				if (index < size) {
					return new Tuple2<>(list1.get(index), list2.get(index));
				} else {
					throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
				}
			}
			
			@Override
			public int size() {
				return Math.min(list1.size(), list2.size());
			}
			
			@Override
			public ExIterator<Tuple2<E1, E2>> iterator() {
				return IteratorUtils.zip(list1.iterator(), list2.iterator());
			}
		};
	}
	
	/**
	 * Builds an unmodifiable view of the given list.
	 * 
	 * @param <E> Type of the elements.
	 * @param list List to wrap.
	 * @return An unmodifiable view of the given list, or the given list when is it already unmodifiable.
	 * @since 2.0
	 */
	public static <E> ExList<E> unmodifiable(final List<E> list) {
		assert null != list;
		
		return list instanceof UnmodifiableList ? (UnmodifiableList<E>) list : new UnmodifiableList<>(list);
	}
	
	private static class UnmodifiableList<E>
	extends ListDecorator<E> {
		public UnmodifiableList(final List<E> decorated) {
			super(decorated);
		}
		
		// List.
		
		@Override
		public boolean addAll(final int index, final Collection<? extends E> c) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void replaceAll(final UnaryOperator<E> operator) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void sort(final Comparator<? super E> c) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public E set(final int index, final E element) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void add(final int index, final E element) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public E remove(final int index) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public ExListIterator<E> listIterator() {
			return super.listIterator().unmodifiable();
		}
		
		@Override
		public ExListIterator<E> listIterator(final int index) {
			return super.listIterator(index).unmodifiable();
		}
		
		@Override
		public ExList<E> subList(final int fromIndex, final int toIndex) {
			return super.subList(fromIndex, toIndex).unmodifiable();
		}
		
		// ExList.
		
		@Override
		public boolean addAll(final int index, @SuppressWarnings("unchecked") final E... elements) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean addAll(final int index, final Iterable<? extends E> elements) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public Maybe<E> optionalRemove(final int index) {
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
		public <E2> ExList<Tuple2<E, E2>> zip(final List<? extends E2> list2) {
			return super.<E2>zip(list2).unmodifiable();
		}
		
		@Override
		public ExList<E> reversed() {
			return super.reversed().unmodifiable();
		}
		
		// Collection.
		
		@Override
		public boolean add(final E e) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean remove(final Object o) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean addAll(final Collection<? extends E> c) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean removeAll(final Collection<?> c) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean removeIf(final java.util.function.Predicate<? super E> filter) {
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
		public boolean addAll(@SuppressWarnings("unchecked") final E... elements) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean addAll(final Iterable<? extends E> elements) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public Maybe<E> removeAny() {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public Maybe<E> removeAny(final Predicate<? super E> filter) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean removeAll(@SuppressWarnings("unchecked") final E... elements) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean removeAll(final Iterable<? extends E> elements) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean removeAll(final Predicate<? super E> filter) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean retainAll(final Predicate<? super E> filter) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public <E2> ExCollection<Tuple2<E, E2>> zip(final Collection<? extends E2> collection2) {
			return super.<E2>zip(collection2).unmodifiable();
		}
		
		// Iterable.
		
		@Override
		public ExIterator<E> iterator() {
			return IteratorUtils.unmodifiable(_decorated.iterator());
		}
		
		// ExIterable.
		
		@Override
		public <E2> PairIterable<E, E2> zip(final Iterable<? extends E2> iterable2) {
			return super.<E2>zip(iterable2).unmodifiable();
		}
		
		@Override
		public ExList<E> unmodifiable() {
			return this;
		}
		
		// Traversable.
		
		@Override
		public ExList<E> take(final int n) {
			return super.take(n).unmodifiable();
		}
		
		@Override
		public ExList<E> drop(final int n) {
			return super.drop(n).unmodifiable();
		}
		
		@Override
		public <B extends Collection<? super E>> ExList<B> group(final int n, final CollectionFactory<? super E, B> batchFactory) {
			return super.group(n, batchFactory).unmodifiable();
		}
		
		@Override
		public ExList<E> filter(final Predicate<? super E> filter) {
			return super.filter(filter).unmodifiable();
		}
		
		@Override
		public <TE> ExList<TE> map(final Function<? super E, ? extends TE> function) {
			return super.<TE>map(function).unmodifiable();
		}
		
		@Override
		public <EE> ExList<EE> extract(final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
			return super.<EE>extract(extractor).unmodifiable();
		}
		
		@Override
		public <EE> ExList<EE> extractAll(final Function<? super E, ? extends Iterable<? extends EE>> extractor) {
			return super.<EE>extractAll(extractor).unmodifiable();
		}
		
		// Object.
		
		@Override
		public int hashCode() {
			return _decorated.hashCode();
		}
		
		@Override
		public boolean equals(final Object o) {
			return _decorated.equals(o);
		}
	}
	
	/**
	 * Builds a view of the given list in the reversed order.
	 * <p>
	 * The built list is backed by the given list, any modification to one list is reported on the other.
	 * 
	 * @param <E> Type of the elements.
	 * @param list List to wrap.
	 * @return A reversed view of the given list.
	 * @since 2.0
	 */
	public static <E> ExList<E> reversed(final List<E> list) {
		assert null != list;
		
		return new AbstractExList<E>() {
			// List.
			
			@Override
			public int indexOf(final Object o) {
				final int index = list.lastIndexOf(o);
				return index >= 0 ? computeIndex(index) : index;
			}
			
			@Override
			public int lastIndexOf(final Object o) {
				final int index = list.indexOf(o);
				return index >= 0 ? computeIndex(index) : index;
			}
			
			@Override
			public E get(final int index) {
				return list.get(computeIndex(index));
			}
			
			@Override
			public E set(final int index, final E element) {
				return list.set(computeIndex(index), element);
			}
			
			@Override
			public void add(final int index, final E element) {
				list.add(computeAddIndex(index), element);
			}
			
			@Override
			public E remove(final int index) {
				return list.remove(computeIndex(index));
			}
			
			@Override
			public boolean addAll(final int index, final Collection<? extends E> c) {
				if (c.isEmpty()) {
					return false;
				} else {
					final int addIndex = computeAddIndex(index);
					for (final E e : c) {
						add(addIndex, e);
					}
					return true;
				}
			}
			
			@Override
			public ExListIterator<E> listIterator(final int index) {
				final ListIterator<E> iterator = list.listIterator(computeAddIndex(index));
				return new ExListIterator<E>() {
					@Override
					public boolean hasNext() {
						return iterator.hasPrevious();
					}
					
					@Override
					public E next() {
						return iterator.previous();
					}
					
					@Override
					public boolean hasPrevious() {
						return iterator.hasNext();
					}
					
					@Override
					public E previous() {
						return iterator.next();
					}
					
					@Override
					public int nextIndex() {
						return computeIndex(iterator.previousIndex());
					}
					
					@Override
					public int previousIndex() {
						return computeIndex(iterator.nextIndex());
					}
					
					@Override
					public void remove() {
						iterator.remove();
					}
					
					@Override
					public void set(final E e) {
						iterator.set(e);
					}
					
					@Override
					public void add(final E e) {
						iterator.add(e);
						iterator.previous();
					}
				};
			}
			
			@Override
			public ExListIterator<E> listIterator() {
				return listIterator(0);
			}
			
			@Override
			public ExList<E> subList(final int fromIndex, final int toIndex) {
				return ListUtils.reversed(list.subList(computeIndex(fromIndex), computeIndex(toIndex)));
			}
			
			private int computeIndex(final int index) {
				return list.size() - index - 1;
			}
			
			private int computeAddIndex(final int index) {
				return list.size() - index;
			}
			
			// Collection.
			
			@Override
			public boolean isEmpty() {
				return list.isEmpty();
			}
			
			@Override
			public int size() {
				return list.size();
			}
			
			@Override
			public boolean contains(final Object o) {
				return list.contains(o);
			}
			
			@Override
			public boolean add(final E e) {
				list.add(0, e);
				return true;
			}
			
			@Override
			public boolean remove(final Object o) {
				return list.remove(o);
			}
			
			@Override
			public boolean containsAll(final Collection<?> c) {
				return list.containsAll(c);
			}
			
			@Override
			public boolean addAll(final Collection<? extends E> c) {
				return addAll(0, c);
			}
			
			@Override
			public boolean removeAll(final Collection<?> c) {
				return list.removeAll(c);
			}
			
			@Override
			public boolean retainAll(final Collection<?> c) {
				return list.retainAll(c);
			}
			
			@Override
			public void clear() {
				list.clear();
			}
			
			// Iterable.
			
			@Override
			public ExIterator<E> iterator() {
				return ListIterators.backward(list);
			}
		};
	}
	
	/**
	 * Sorts the given elements topologically.
	 * <p>
	 * The dependencies between the elements are computed using the given function. It must result to the elements whose the argument element depends on. The
	 * sort fails when the dependency graph is cyclic.
	 * <p>
	 * The depencency elements that does not belong to the elements to sort may be transitively included into the results. The sort fails when the dependency
	 * graph include such elements and that they are not included in the results.
	 * <p>
	 * The sort is stable and places the dependencies before the elements that depend on them.
	 * 
	 * @param <E> Type of the elements.
	 * @param <L> Type of the result list.
	 * @param elements Elements to sort.
	 * @param dependencies Function computing the dependencies.
	 * @param includeDependencies Indicates whether the dependency elements that do not belong to the elements to sort should be included in the results or not.
	 * @param resultFactory Factory of the result list.
	 * @return A list containing the sorted elements.
	 * @throws IllegalArgumentException When there is a cycle in the dependency graph.
	 * @throws IllegalArgumentException When some dependency element does not belong to the elements to sort and is not included into the results.
	 * @since 2.0
	 */
	public static <E, L extends List<? super E>> L topologicalSort(final Iterable<? extends E> elements, final Function<? super E, ? extends Iterable<? extends E>> dependencies, final boolean includeDependencies, final CollectionFactory<? super E, L> resultFactory) {
		return topologicalSort(elements, dependencies, includeDependencies, CollectionAccumulators.add(resultFactory.build())).get();
	}
	
	/**
	 * Sorts the given elements topologically.
	 * <p>
	 * The dependencies between the elements are computed using the given function. It must result to the elements whose the argument element depends on. The
	 * sort fails when the dependency graph is cyclic.
	 * <p>
	 * The depencency elements that does not belong to the elements to sort may be transitively included into the results. The sort fails when the dependency
	 * graph include such elements and that they are not included in the results.
	 * <p>
	 * The sort is stable and places the dependencies before the elements that depend on them.
	 * 
	 * @param <E> Type of the elements.
	 * @param <A> Type of the accumulator of the sorted elements.
	 * @param elements Elements to sort.
	 * @param dependencies Function computing the dependencies.
	 * @param includeDependencies Indicates whether the dependency elements that do not belong to the elements to sort should be included in the results or not.
	 * @param results Accumulator to populated with the sorted elements.
	 * @return The given result accumulator.
	 * @throws IllegalArgumentException When there is a cycle in the dependency graph.
	 * @throws IllegalArgumentException When some dependency element does not belong to the elements to sort and is not included into the results.
	 * @since 2.0
	 */
	public static <E, A extends Accumulator<? super E, ?>> A topologicalSort(final Iterable<? extends E> elements, final Function<? super E, ? extends Iterable<? extends E>> dependencies, final boolean includeDependencies, final A results) {
		// Compute the dependencies.
		final List<E> pendingElements = new LinkedList<>();
		final Multimap<E, E, Set<E>> pendingDependencies = computeTopologicalDependencies(elements, dependencies, includeDependencies, CollectionAccumulators.add(pendingElements), MultimapAccumulators.put(new MapMultimap<>(MapFactories.hashMap(), CollectionFactories.hashSet()))).get();
		
		// Sort.
		while (!pendingElements.isEmpty()) {
			// Iterate the pending elements.
			final Maybe<E> maybeLeaf = CollectionUtils.removeAny(pendingElements, element -> !pendingDependencies.containsKey(element));
			if (maybeLeaf.isNone()) {
				throw new IllegalArgumentException("Cyclic or external dependencies for elements " + pendingElements);
			}
			final E leaf = maybeLeaf.asSome().getValue();
			
			// Add the element to the result.
			results.add(leaf);
			
			// Clean the dependencies.
			pendingDependencies.removeValue(leaf);
		}
		
		return results;
	}
	
	/**
	 * Sorts the given elements topologically by region.
	 * <p>
	 * A region is a set of elements that only have dependencies on elements of previous region.
	 * <p>
	 * The dependencies between the elements are computed using the given function. It must result to the elements whose the argument element depends on. The
	 * sort fails when the dependency graph is cyclic.
	 * <p>
	 * The depencency elements that does not belong to the elements to sort may be transitively included into the results. The sort fails when the dependency
	 * graph include such elements and that they are not included in the results.
	 * <p>
	 * This sort is stable and places the dependencies before the regions that depend on them.
	 *
	 * @param <E> Type of the elements.
	 * @param <R> Type of the region lists.
	 * @param <L> Type of the result list.
	 * @param elements Elements to sort.
	 * @param dependencies Function computing the dependencies.
	 * @param includeDependencies Indicates whether the dependency elements that do not belong to the elements to sort should be included in the results or not.
	 * @param regionFactory Factory of the region lists.
	 * @param resultFactory Factory of the result list.
	 * @return A list containing the sorted regions of elements.
	 * @throws IllegalArgumentException When there is a cycle in the dependency graph.
	 * @throws IllegalArgumentException When some dependency element does not belong to the elements to sort and is not included into the results.
	 * @since 2.0
	 */
	public static <E, R extends Collection<? super E>, L extends List<? super R>> L topologicalRegionSort(final Collection<? extends E> elements, final Function<? super E, ? extends Collection<? extends E>> dependencies, final boolean includeDependencies, final CollectionFactory<? super E, R> regionFactory, final CollectionFactory<? super R, L> resultFactory) {
		return topologicalRegionSort(elements, dependencies, includeDependencies, regionFactory, CollectionAccumulators.add(resultFactory.build())).get();
	}
	
	/**
	 * Sorts the given elements topologically by region.
	 * <p>
	 * A region is a set of elements that only have dependencies on elements of previous region.
	 * <p>
	 * The dependencies between the elements are computed using the given function. It must result to the elements whose the argument element depends on. The
	 * sort fails when the dependency graph is cyclic.
	 * <p>
	 * The depencency elements that does not belong to the elements to sort may be transitively included into the results. The sort fails when the dependency
	 * graph include such elements and that they are not included in the results.
	 * <p>
	 * This sort is stable and places the dependencies before the regions that depend on them.
	 *
	 * @param <E> Type of the elements.
	 * @param <R> Type of the regions.
	 * @param <A> Type of the accumulator of the sorted regions.
	 * @param elements Elements to sort.
	 * @param dependencies Function computing the dependencies.
	 * @param includeDependencies Indicates whether the dependency elements that do not belong to the elements to sort should be included in the results or not.
	 * @param regionFactory Factory of the region lists.
	 * @param results Accumulator to populate with the sorted regions.
	 * @return The given result accumulator.
	 * @throws IllegalArgumentException When there is a cycle in the dependency graph.
	 * @throws IllegalArgumentException When some dependency element does not belong to the elements to sort and is not included into the results.
	 * @since 2.0
	 */
	public static <E, R extends Collection<? super E>, A extends Accumulator<? super R, ?>> A topologicalRegionSort(final Collection<? extends E> elements, final Function<? super E, ? extends Collection<? extends E>> dependencies, final boolean includeDependencies, final CollectionFactory<? super E, R> regionFactory, final A results) {
		// Compute the dependencies.
		final List<E> pendingElements = new LinkedList<>();
		final Multimap<E, E, Set<E>> pendingDependencies = computeTopologicalDependencies(elements, dependencies, includeDependencies, CollectionAccumulators.add(pendingElements), MultimapAccumulators.put(new MapMultimap<>(MapFactories.hashMap(), CollectionFactories.hashSet()))).get();
		
		// Sort.
		while (!pendingElements.isEmpty()) {
			// Iterate the pending elements.
			final R region = regionFactory.build();
			final Set<E> leaves = new HashSet<>();
			final Iterator<E> elementsIt = pendingElements.iterator();
			while (elementsIt.hasNext()) {
				final E element = elementsIt.next();
				if (!pendingDependencies.containsKey(element)) {
					// Add the element.
					region.add(element);
					leaves.add(element);
					
					// Not pending anymore.
					elementsIt.remove();
				}
			}
			
			// Check that some leaf were found.
			if (leaves.isEmpty()) {
				throw new IllegalArgumentException("Cyclic or external dependencies for elements " + pendingElements);
			} else {
				results.add(region);
			}
			
			// Clean the dependencies.
			for (final E leaf : leaves) {
				pendingDependencies.removeValue(leaf);
			}
		}
		
		return results;
	}
	
	private static <E, A extends Accumulator2<? super E, ? super E, ?>> A computeTopologicalDependencies(final Iterable<? extends E> elements, final Function<? super E, ? extends Iterable<? extends E>> dependencies, final boolean includeDependencies, final Accumulator<? super E, ?> traversedElements, final A resultDependencies) {
		if (!includeDependencies) {
			// Do not include the dependencies.
			for (final E element : elements) {
				// Add the element.
				traversedElements.add(element);
				
				// Add the dependencies.
				for (final E dependency : dependencies.evaluate(element)) {
					resultDependencies.add(element, dependency);
				}
			}
			return resultDependencies;
		} else {
			// Include the dependencies.
			final Predicate<E> visitedElements = ImperativePredicates.normalizer();
			final Queue<E> pendingElements = new LinkedList<>();
			CollectionUtils.addAll(pendingElements, elements);
			while (!pendingElements.isEmpty()) {
				final E element = pendingElements.poll();
				if (visitedElements.evaluate(element)) {
					// Add the element.
					traversedElements.add(element);
					
					// Add the dependencies.
					for (final E dependency : dependencies.evaluate(element)) {
						resultDependencies.add(element, dependency);
						pendingElements.add(dependency); // Note: must queued rather than stacked in order to keep the sort stable.
					}
				}
			}
			return resultDependencies;
		}
	}
	
	private ListUtils() {
		// Prevent instantiation.
	}
}
