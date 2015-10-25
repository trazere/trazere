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
import com.trazere.core.imperative.ImperativePredicates;
import com.trazere.core.util.Maybe;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

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
	 * @return The first element of the list, or nothing when the list is empty.
	 * @since 2.0
	 */
	public static <E> Maybe<E> first(final List<? extends E> list) {
		return !list.isEmpty() ? Maybe.some(list.get(0)) : Maybe.<E>none();
	}
	
	/**
	 * Gets the last element of the given list.
	 *
	 * @param <E> Type of the elements.
	 * @param list List to read.
	 * @return The last element, or nothing when the list is empty.
	 * @since 2.0
	 */
	public static <E> Maybe<E> last(final List<? extends E> list) {
		final int size = list.size();
		return size > 0 ? Maybe.<E>some(list.get(size - 1)) : Maybe.<E>none();
	}
	
	/**
	 * Gets the element of the given list at the given position.
	 *
	 * @param <E> Type of the elements.
	 * @param list List to read.
	 * @param index Index of the element to get.
	 * @return The specified element, or nothing when the index is out of bound.
	 * @since 2.0
	 */
	public static <E> Maybe<E> get(final List<? extends E> list, final int index) {
		return index < list.size() ? Maybe.<E>some(list.get(index)) : Maybe.<E>none();
	}
	
	/**
	 * Gets the element of the given list.
	 *
	 * @param <E> Type of the elements.
	 * @param list List to read.
	 * @param index Index of the element to get.
	 * @param defaultElement Default element.
	 * @return The specified element, or the default element when the index is out of bound.
	 * @since 2.0
	 */
	public static <E> E get(final List<? extends E> list, final int index, final E defaultElement) {
		return index < list.size() ? list.get(index) : defaultElement;
	}
	
	/**
	 * Inserts all given elements in the given list at the given position.
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
	
	/**
	 * Removes the element of the given list at the given position.
	 * 
	 * @param <E> Type of the elements.
	 * @param list List to modify.
	 * @param index Index of the element to remove.
	 * @return The removed element, or nothing when the index is out of bound.
	 * @since 2.0
	 */
	public static <E> Maybe<E> remove(final List<? extends E> list, final int index) {
		if (index < list.size()) {
			return Maybe.some(list.remove(index));
		} else {
			return Maybe.none();
		}
	}
	
	/**
	 * Sorts the elements in the given list according to their natural order.
	 * <p>
	 * This method does modify the given list.
	 * 
	 * @param <E> Type of the elements.
	 * @param <L> Type of the list.
	 * @param list List to sort.
	 * @return The given sorted list.
	 * @since 2.0
	 */
	public static <E extends Comparable<? super E>, L extends List<E>> L sort(final L list) {
		assert null != list;
		
		Collections.sort(list);
		return list;
	}
	
	/**
	 * Sorts the elements in the given list using the given comparator.
	 * <p>
	 * This method does modify the given list.
	 * 
	 * @param <E> Type of the elements.
	 * @param <L> Type of the list.
	 * @param list List to sort.
	 * @param comparator Comparator to use.
	 * @return The given sorted list.
	 * @since 2.0
	 */
	public static <E, L extends List<E>> L sort(final L list, final Comparator<? super E> comparator) {
		assert null != list;
		assert null != comparator;
		
		Collections.sort(list, comparator);
		return list;
	}
	
	/**
	 * Reverses the order of the elements in the given list.
	 * <p>
	 * This method does modify the given list.
	 * 
	 * @param <E> Type of the elements.
	 * @param <L> Type of the list.
	 * @param list List to reverse.
	 * @return The given reversed list.
	 * @since 2.0
	 */
	public static <E, L extends List<E>> L reverse(final L list) {
		assert null != list;
		
		Collections.reverse(list);
		return list;
	}
	
	/**
	 * Shuffles the elements in the given list.
	 * <p>
	 * This method does modify the given list.
	 * 
	 * @param <E> Type of the elements.
	 * @param <L> Type of the list.
	 * @param list List to shuffle.
	 * @return The given shuffled list.
	 * @since 2.0
	 */
	public static <E extends Comparable<? super E>, L extends List<E>> L shuffle(final L list) {
		assert null != list;
		
		Collections.shuffle(list);
		return list;
	}
	
	/**
	 * Shuffles the elements in the given list using the given source of randomness.
	 * <p>
	 * This method does modify the given list.
	 * 
	 * @param <E> Type of the elements.
	 * @param <L> Type of the list.
	 * @param list List to shuffle.
	 * @param random Source of randomness to use.
	 * @return The given shuffled list.
	 * @since 2.0
	 */
	public static <E extends Comparable<? super E>, L extends List<E>> L shuffle(final L list, final Random random) {
		assert null != list;
		
		Collections.shuffle(list, random);
		return list;
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
		final Accumulator<E, L> results = CollectionAccumulators.add(resultFactory.build());
		
		// Compute the dependencies.
		final List<E> pendingElements = new LinkedList<>();
		final Multimap<E, E, Set<E>> pendingDependencies = computeTopologicalDependencies(elements, dependencies, includeDependencies, CollectionAccumulators.add(pendingElements), MultimapAccumulators.put(new MapMultimap<>(MapFactories.hashMap(), CollectionFactories.hashSet()))).get();
		
		// Sort.
		while (!pendingElements.isEmpty()) {
			// Iterate the pending elements.
			final Maybe<E> maybeLeaf = CollectionUtils.removeFirst(pendingElements, element -> !pendingDependencies.containsKey(element));
			if (maybeLeaf.isNone()) {
				throw new IllegalArgumentException("Cyclic or external dependencies for elements " + pendingElements);
			}
			final E leaf = maybeLeaf.asSome().getValue();
			
			// Add the element to the result.
			results.add(leaf);
			
			// Clean the dependencies.
			pendingDependencies.removeValue(leaf);
		}
		return results.get();
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
	public static <E, R extends List<? super E>, L extends List<? super R>> L topologicalRegionSort(final Collection<? extends E> elements, final Function<? super E, ? extends Collection<? extends E>> dependencies, final boolean includeDependencies, final CollectionFactory<? super E, R> regionFactory, final CollectionFactory<? super R, L> resultFactory) {
		final Accumulator<R, L> results = CollectionAccumulators.add(resultFactory.build());
		
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
		
		return results.get();
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
	
	/**
	 * Builds an unmodifiable view of the given list.
	 * 
	 * @param <E> Type of the elements.
	 * @param list List to wrap.
	 * @return An unmodifiable view of the given list, or the given list when is it already unmodifiable.
	 * @since 2.0
	 */
	public static <E> List<E> unmodifiable(final List<E> list) {
		return UNMODIFIABLE_LIST_CLASS.isInstance(list) ? list : Collections.unmodifiableList(list);
	}
	
	private static Class<?> UNMODIFIABLE_LIST_CLASS = Collections.unmodifiableList(Collections.emptyList()).getClass();
	
	/**
	 * Builds a view of the given list in the reversed order.
	 * <p>
	 * The built list is backed by the given list, any modification to one list is reported on the other.
	 * 
	 * @param <E> Type of the elements.
	 * @param list List to view.
	 * @return The built list.
	 * @since 2.0
	 */
	public static <E> List<E> reversed(final List<E> list) {
		assert null != list;
		
		return new AbstractList<E>() {
			@Override
			public int size() {
				return list.size();
			}
			
			@Override
			public Iterator<E> iterator() {
				return ListIterators.backward(list);
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
			public void clear() {
				list.clear();
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
				list.add(computeAddIndex(index) + 1, element);
			}
			
			@Override
			public E remove(final int index) {
				return list.remove(computeIndex(index));
			}
			
			@Override
			public ListIterator<E> listIterator(final int index) {
				final ListIterator<E> iterator = list.listIterator(computeAddIndex(index));
				return new ListIterator<E>() {
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
			public List<E> subList(final int fromIndex, final int toIndex) {
				return reversed(list.subList(fromIndex, toIndex));
			}
			
			private int computeIndex(final int index) {
				return list.size() - index - 1;
			}
			
			private int computeAddIndex(final int index) {
				return list.size() - index;
			}
		};
	}
	
	private ListUtils() {
		// Prevent instantiation.
	}
}
