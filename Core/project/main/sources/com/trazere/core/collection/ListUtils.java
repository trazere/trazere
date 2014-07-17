/*
 *  Copyright 2006-2013 Julien Dufour
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

import com.trazere.core.util.Maybe;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * The {@link ListUtils} class provides various utilities regarding {@link List lists}.
 * 
 * @see List
 */
public class ListUtils {
	/**
	 * Gets the first element of the given list.
	 *
	 * @param <E> Type of the elements.
	 * @param list List to read.
	 * @return The first element of the list, or nothing when the list is empty.
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
	 */
	public static <E> Maybe<E> remove(final List<? extends E> list, final int index) {
		if (index < list.size()) {
			return Maybe.some(list.remove(index));
		} else {
			return Maybe.none();
		}
	}
	
	/**
	 * Builds an iterator that iterates the given list from the last element to the first one.
	 * 
	 * @param <E> Type of the elements.
	 * @param list List to iterate.
	 * @return The built iterator.
	 */
	public static <E> Iterator<E> backwardIterator(final List<? extends E> list) {
		final ListIterator<? extends E> iterator = list.listIterator(list.size());
		return new Iterator<E>() {
			@Override
			public boolean hasNext() {
				return iterator.hasPrevious();
			}
			
			@Override
			public E next() {
				return iterator.previous();
			}
			
			@Override
			public void remove() {
				iterator.remove();
			}
		};
	}
	
	//	/**
	//	 * Reverses the given list.
	//	 * <p>
	//	 * This method does modify the given list.
	//	 *
	//	 * @param <T> Type of the elements.
	//	 * @param <L> Type of the list.
	//	 * @param list The list.
	//	 * @return The given modified list.
	//	 */
	//	public static <T, L extends List<T>> L reverse(final L list) {
	//		assert null != list;
	//
	//		Collections.reverse(list);
	//		return list;
	//	}
	//
	//	/**
	//	 * Sorts the given list using the given comparator.
	//	 * <p>
	//	 * This method does modify the given list.
	//	 *
	//	 * @param <T> Type of the elements.
	//	 * @param <L> Type of the list.
	//	 * @param list The list.
	//	 * @return The given modified list.
	//	 */
	//	public static <T extends Comparable<? super T>, L extends List<T>> L sort(final L list) {
	//		assert null != list;
	//
	//		Collections.sort(list);
	//		return list;
	//	}
	//
	//	/**
	//	 * Sorts the given list using the given comparator.
	//	 * <p>
	//	 * This method does modify the given list.
	//	 *
	//	 * @param <T> Type of the elements.
	//	 * @param <L> Type of the list.
	//	 * @param list The list.
	//	 * @param comparator The comparator.
	//	 * @return The given modified list.
	//	 */
	//	public static <T, L extends List<T>> L sort(final L list, final Comparator<? super T> comparator) {
	//		assert null != list;
	//		assert null != comparator;
	//
	//		Collections.sort(list, comparator);
	//		return list;
	//	}
	//
	//	/**
	//	 * Sorts the given values topologically and populates the given list with the sorted values.
	//	 * <p>
	//	 * The dependencies between the values are computed using the given function. This function must compute the values whose the argument value depends on. The
	//	 * computed values must belong to the values to sort.
	//	 * <p>
	//	 * This method places the dependencies before the value which depend on them. The sort is stable and fails when the dependencies form a cyclic graph.
	//	 *
	//	 * @param <T> Type of the values.
	//	 * @param <L> Type of the result list.
	//	 * @param <X> Type of the exceptions.
	//	 * @param dependencyFunction The function computing the dependencies.
	//	 * @param close Flag indicating whether a transitive closure should be performed or if the given values are supposed are supposed to closed.
	//	 * @param values The values.
	//	 * @param results The list to populate with the results.
	//	 * @return The given result list.
	//	 * @throws CollectionException When some computed dependency value does not belong to the values to sort.
	//	 * @throws CollectionException When there is a cycle in the dependencies.
	//	 * @throws X When some dependency computation fails.
	//	 */
	//	public static <T, L extends List<? super T>, X extends Exception> L topologicalSort(final Function1<? super T, ? extends Collection<? extends T>, X> dependencyFunction, final boolean close, final Collection<? extends T> values, final L results)
	//	throws CollectionException, X {
	//		assert null != values;
	//		assert null != dependencyFunction;
	//		assert null != results;
	//
	//		// Compute the dependencies.
	//		final List<T> pendingValues = new ArrayList<T>(values.size());
	//		final Collection<Tuple2<T, T>> dependencies = computeTopologicalSortDependencies(dependencyFunction, close, values, pendingValues, new ArrayList<Tuple2<T, T>>());
	//
	//		// Sort the values.
	//		while (!pendingValues.isEmpty()) {
	//			// Find the leaves.
	//			final Set<T> leafValues = findTopologicalSortLeaves(pendingValues, dependencies);
	//			if (leafValues.isEmpty()) {
	//				throw new CollectionException("Cyclic or external dependencies for values " + pendingValues);
	//			}
	//
	//			// Add the leaves to the result.
	//			extractTopologicalSortLeaves(leafValues, pendingValues, results);
	//
	//			// Clean the dependencies.
	//			cleanTopologicalSortDependencies(dependencies, leafValues);
	//		}
	//
	//		return results;
	//	}
	//
	//	/**
	//	 * Sorts the given values topologically and populates the given list with the sorted regions.
	//	 * <p>
	//	 * A region is a set of values which have no dependencies on each other. They however do have dependencies on some values of the previous region.
	//	 * <p>
	//	 * The dependencies between the values are computed using the given function. This function must compute the values whose the argument value depends on. The
	//	 * computed values must belong to the values to sort.
	//	 * <p>
	//	 * This method places the dependencies before the value which depend on them. The sort is stable and fails when the dependencies form a cyclic graph.
	//	 *
	//	 * @param <T> Type of the values.
	//	 * @param <L> Type of the result list.
	//	 * @param <X> Type of the exceptions.
	//	 * @param dependencyFunction The function computing the dependencies.
	//	 * @param close Flag indicating whether a transitive closure should be performed or if the given values are supposed are supposed to closed.
	//	 * @param values The values.
	//	 * @param results The list to populate with the results.
	//	 * @return The given result list.
	//	 * @throws CollectionException When some computed dependency value does not belong to the values to sort.
	//	 * @throws CollectionException When there is a cycle in the dependencies.
	//	 * @throws X When some dependency computation fails.
	//	 */
	//	public static <T, L extends List<? super List<T>>, X extends Exception> L regionTopologicalSort(final Function1<? super T, ? extends Collection<? extends T>, X> dependencyFunction, final boolean close, final Collection<? extends T> values, final L results)
	//	throws CollectionException, X {
	//		assert null != values;
	//		assert null != dependencyFunction;
	//		assert null != results;
	//
	//		// Compute the dependencies.
	//		final List<T> pendingValues = new ArrayList<T>(values.size());
	//		final Collection<Tuple2<T, T>> dependencies = computeTopologicalSortDependencies(dependencyFunction, close, values, pendingValues, new ArrayList<Tuple2<T, T>>());
	//
	//		// Sort the values.
	//		while (!pendingValues.isEmpty()) {
	//			// Find the leaves.
	//			final Set<T> leafValues = findTopologicalSortLeaves(pendingValues, dependencies);
	//			if (leafValues.isEmpty()) {
	//				throw new CollectionException("Cyclic dependencies for values " + pendingValues);
	//			}
	//
	//			// Add the leaves to the result.
	//			results.add(extractTopologicalSortLeaves(leafValues, pendingValues, new ArrayList<T>(leafValues.size())));
	//
	//			// Clean the dependencies.
	//			cleanTopologicalSortDependencies(dependencies, leafValues);
	//		}
	//
	//		return results;
	//	}
	//
	//	private static <T, C extends Collection<? super Tuple2<T, T>>, X extends Exception> C computeTopologicalSortDependencies(final Function1<? super T, ? extends Collection<? extends T>, X> dependencyFunction, final boolean close, final Collection<? extends T> values, final Collection<T> closedValues, final C dependencies)
	//	throws X {
	//		assert null != values;
	//		assert null != closedValues;
	//
	//		if (close) {
	//			return computeClosedTopologicalSortDependencies(dependencyFunction, values, closedValues, dependencies);
	//		} else {
	//			closedValues.addAll(values);
	//			return computeTopologicalSortDependencies(dependencyFunction, values, dependencies);
	//		}
	//	}
	//
	//	private static <T, C extends Collection<? super Tuple2<T, T>>, X extends Exception> C computeTopologicalSortDependencies(final Function1<? super T, ? extends Collection<? extends T>, X> dependencyFunction, final Collection<? extends T> values, final C dependencies)
	//	throws X {
	//		assert null != dependencyFunction;
	//		assert null != values;
	//		assert null != dependencies;
	//
	//		for (final T value : values) {
	//			for (final T dependencyValue : dependencyFunction.evaluate(value)) {
	//				dependencies.add(new Tuple2<T, T>(value, dependencyValue));
	//			}
	//		}
	//		return dependencies;
	//	}
	//
	//	private static <T, C extends Collection<? super Tuple2<T, T>>, X extends Exception> C computeClosedTopologicalSortDependencies(final Function1<? super T, ? extends Collection<? extends T>, X> dependencyFunction, final Collection<? extends T> values, final Collection<T> closedValues, final C dependencies)
	//	throws X {
	//		assert null != dependencyFunction;
	//		assert null != values;
	//		assert null != dependencies;
	//
	//		final Queue<T> pendingValues = new LinkedList<T>(values);
	//		final Set<T> visitedValues = new HashSet<T>();
	//		while (!pendingValues.isEmpty()) {
	//			final T value = pendingValues.poll();
	//			if (visitedValues.add(value)) {
	//				// Add the value.
	//				closedValues.add(value);
	//
	//				// Add the dependencies.
	//				for (final T dependencyValue : dependencyFunction.evaluate(value)) {
	//					dependencies.add(new Tuple2<T, T>(value, dependencyValue));
	//					pendingValues.add(dependencyValue); // Note: must queued in order to keep the closed value stable
	//				}
	//			}
	//		}
	//		return dependencies;
	//	}
	//
	//	private static <T> Set<T> findTopologicalSortLeaves(final Collection<T> values, final Collection<? extends Tuple2<T, T>> dependencies) {
	//		assert null != values;
	//		assert null != dependencies;
	//
	//		final Set<T> leafValues = new HashSet<T>(values);
	//		for (final Tuple2<T, T> dependency : dependencies) {
	//			leafValues.remove(dependency.get1());
	//		}
	//		return leafValues;
	//	}
	//
	//	private static <T, C extends Collection<? super T>> C extractTopologicalSortLeaves(final Set<T> leafValues, final List<T> pendingValues, final C results) {
	//		assert null != leafValues;
	//		assert null != pendingValues;
	//		assert null != results;
	//
	//		// Note: Pending values are iterated instead of leaf values to keep the sort stable and to handle duplicate values.
	//		final Iterator<T> pendingValuesIt = pendingValues.iterator();
	//		while (pendingValuesIt.hasNext()) {
	//			final T value = pendingValuesIt.next();
	//			if (leafValues.contains(value)) {
	//				results.add(value);
	//				pendingValuesIt.remove();
	//			}
	//		}
	//		return results;
	//	}
	//
	//	private static <T> void cleanTopologicalSortDependencies(final Collection<? extends Tuple2<T, T>> dependencies, final Set<T> leafValues) {
	//		assert null != dependencies;
	//		assert null != leafValues;
	//
	//		final Iterator<? extends Tuple2<T, T>> dependenciesIt = dependencies.iterator();
	//		while (dependenciesIt.hasNext()) {
	//			final Tuple2<T, T> dependency = dependenciesIt.next();
	//			if (leafValues.contains(dependency.get2())) {
	//				dependenciesIt.remove();
	//			}
	//		}
	//	}
	
	private ListUtils() {
		// Prevent instantiation.
	}
}
