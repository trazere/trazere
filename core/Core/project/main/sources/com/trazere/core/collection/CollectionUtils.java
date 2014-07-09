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

import com.trazere.core.functional.Function;
import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Predicate;
import com.trazere.core.imperative.Accumulator;
import com.trazere.core.imperative.IteratorUtils;
import com.trazere.core.imperative.Procedure;
import com.trazere.core.lang.IterableUtils;
import com.trazere.core.lang.LangAccumulators;
import com.trazere.core.util.Maybe;
import java.util.Collection;
import java.util.Comparator;

/**
 * The {@link CollectionUtils} class provides various utilities regarding the manipulation of collections and maps.
 */
public class CollectionUtils {
	/**
	 * Gets an element from the given collection.
	 *
	 * @param <E> Type of the elements.
	 * @param collection Collection to read.
	 * @return Some value if any.
	 */
	public static <E> Maybe<E> any(final Collection<? extends E> collection) {
		return IteratorUtils.next(collection.iterator());
	}
	
	/**
	 * Executes the given procedure with each element of the given collection.
	 * 
	 * @param <E> Type of the elements.
	 * @param collection Collection of elements.
	 * @param procedure Procedure to execute.
	 */
	public static <E> void foreach(final Collection<? extends E> collection, final Procedure<? super E> procedure) {
		IteratorUtils.foreach(collection.iterator(), procedure);
	}
	
	/**
	 * Evaluates the given function with each element of the given collection.
	 * 
	 * @param <E> Type of the elements.
	 * @param collection Collection of elements.
	 * @param function Function to evaluate.
	 */
	public static <E> void foreach(final Collection<? extends E> collection, final Function<? super E, ?> function) {
		IteratorUtils.foreach(collection.iterator(), function);
	}
	
	/**
	 * Left folds over the elements of the given collection using the given binary operator and initial value.
	 * 
	 * @param <E> Type of the elements.
	 * @param <S> Type of the state.
	 * @param collection Collection containing the elements to fold over.
	 * @param operator Binary operator to use.
	 * @param initialState Initial state.
	 * @return The folded state.
	 */
	public static <E, S> S fold(final Collection<? extends E> collection, final Function2<? super S, ? super E, ? extends S> operator, final S initialState) {
		return IteratorUtils.fold(collection.iterator(), operator, initialState);
	}
	
	/**
	 * Gets the first element of the given collection accepted by the given filter.
	 * 
	 * @param <E> Type of the elements.
	 * @param collection Collection containing the elements to filter.
	 * @param filter Filter predicate.
	 * @return The first accepted element.
	 */
	public static <E> Maybe<E> first(final Collection<? extends E> collection, final Predicate<? super E> filter) {
		return IteratorUtils.first(collection.iterator(), filter);
	}
	
	/**
	 * Gets the first element extracted from the given collection by the given extractor.
	 * 
	 * @param <E> Type of the elements.
	 * @param <RE> Type of the extracted elements.
	 * @param collection Collection containing the elements to extract from.
	 * @param extractor Extractor function.
	 * @return The first extracted element.
	 */
	public static <E, RE> Maybe<? extends RE> first(final Collection<? extends E> collection, final Function<? super E, ? extends Maybe<? extends RE>> extractor) {
		return IteratorUtils.first(collection.iterator(), extractor);
	}
	
	/**
	 * Tests whether any element of the given collection is accepted by the given filter.
	 * 
	 * @param <E> Type of the elements.
	 * @param collection Collection to test.
	 * @param filter Filter predicate.
	 * @return <code>true</code> when some element is accepted, <code>false</code> when all elements are rejected.
	 */
	public static <E> boolean isAny(final Collection<? extends E> collection, final Predicate<? super E> filter) {
		return IteratorUtils.isAny(collection.iterator(), filter);
	}
	
	/**
	 * Tests whether all elements of the given collection are accepted by the given filter.
	 * 
	 * @param <E> Type of the elements.
	 * @param collection Collection to test.
	 * @param filter Filter predicate.
	 * @return <code>true</code> when all values are accepted, <code>false</code> when some value is rejected.
	 */
	public static <E> boolean areAll(final Collection<? extends E> collection, final Predicate<? super E> filter) {
		return IteratorUtils.areAll(collection.iterator(), filter);
	}
	
	/**
	 * Counts the elements of the given collection accepted by the given filter.
	 * 
	 * @param <E> Type of the elements.
	 * @param collection Collection containing the elements to count.
	 * @param filter Filter predicate.
	 * @return The number of accepted elements.
	 */
	public static <E> int count(final Collection<? extends E> collection, final Predicate<? super E> filter) {
		return IteratorUtils.count(collection.iterator(), filter);
	}
	
	/**
	 * Gets the least element of the given collection.
	 *
	 * @param <E> Type of the elements.
	 * @param collection Collection containing the elements to compare.
	 * @return The least element.
	 */
	public static <E extends Comparable<E>> Maybe<? extends E> least(final Collection<? extends E> collection) {
		return IteratorUtils.least(collection.iterator());
	}
	
	/**
	 * Gets the least element of the given collection according to the given comparator.
	 *
	 * @param <E> Type of the elements.
	 * @param collection Collection providing the elements to compare.
	 * @param comparator Comparator to use.
	 * @return The least element.
	 */
	public static <E> Maybe<? extends E> least(final Collection<? extends E> collection, final Comparator<? super E> comparator) {
		return IteratorUtils.least(collection.iterator(), comparator);
	}
	
	/**
	 * Gets the greatest element of the given collection.
	 *
	 * @param <E> Type of the elements.
	 * @param collection Collection containing the elements to compare.
	 * @return The greatest element.
	 */
	public static <E extends Comparable<E>> Maybe<? extends E> greatest(final Collection<? extends E> collection) {
		return IteratorUtils.greatest(collection.iterator());
	}
	
	/**
	 * Gets the greatest element of the given collection according to the given comparator.
	 *
	 * @param <E> Type of the elements.
	 * @param collection Collection containing the elements to compare.
	 * @param comparator Comparator to use.
	 * @return The greatest element.
	 */
	public static <E> Maybe<? extends E> greatest(final Collection<? extends E> collection, final Comparator<? super E> comparator) {
		return IteratorUtils.greatest(collection.iterator(), comparator);
	}
	
	/**
	 * Appends the given collections together.
	 * 
	 * @param <E> Type of the elements.
	 * @param <R> Type of the result collection.
	 * @param collection1 First collection providing the elements to append.
	 * @param collection2 Second collection providing the elements to append.
	 * @param resultFactory Factory of the result collection.
	 * @return A collection containing the appended elements.
	 */
	public static <E, R extends Collection<? super E>> R append(final Collection<? extends E> collection1, final Collection<? extends E> collection2, final CollectionFactory<E, R> resultFactory) {
		final R results = resultFactory.build(collection1.size() + collection2.size());
		results.addAll(collection1);
		results.addAll(collection2);
		return results;
	}
	
	/**
	 * Flattens the elements of the collections contained in the given collection.
	 *
	 * @param <E> Type of the elements.
	 * @param <R> Type of the result collection.
	 * @param collection Collection containing the collections containing the elements to flatten.
	 * @param resultFactory Factory of the result collection.
	 * @return A collection containing the flatten elements.
	 */
	public static <E, R extends Collection<? super E>> R flatten(final Collection<? extends Collection<? extends E>> collection, final CollectionFactory<E, R> resultFactory) {
		final R results = resultFactory.build();
		for (final Collection<? extends E> elements : collection) {
			results.addAll(elements);
		}
		return results;
	}
	
	/**
	 * Takes the n first elements of the given collection.
	 *
	 * @param <E> Type of the elements.
	 * @param <R> Type of the result collection.
	 * @param collection Collection containing the elements to take.
	 * @param n Number of elements to take.
	 * @param resultFactory Factory of the result collection.
	 * @return A collection containing the taken elements.
	 */
	public static <E, R extends Collection<? super E>> R take(final Collection<? extends E> collection, final int n, final CollectionFactory<E, R> resultFactory) {
		return IteratorUtils.drainAll(IteratorUtils.take(collection.iterator(), n), resultFactory.build(n));
	}
	
	/**
	 * Drops the n first elements of the given collection.
	 *
	 * @param <E> Type of the elements.
	 * @param <R> Type of the result collection.
	 * @param collection Collection containing the elements to drop.
	 * @param n Number of elements to drop.
	 * @param resultFactory Factory of the result collection.
	 * @return A collection containing the remaining elements.
	 */
	public static <E, R extends Collection<? super E>> R drop(final Collection<? extends E> collection, final int n, final CollectionFactory<E, R> resultFactory) {
		return IteratorUtils.drainAll(IteratorUtils.drop(collection.iterator(), n), resultFactory.build(n));
	}
	
	/**
	 * Filters the elements of the given collection using the given filter.
	 *
	 * @param <E> Type of the elements.
	 * @param <R> Type of the result collection.
	 * @param collection Collection containing the elements to filter.
	 * @param filter Predicate to use to filter the elements.
	 * @param resultFactory Factory of the result collection.
	 * @return A collection containing the filtered elements.
	 */
	public static <E, R extends Collection<? super E>> R filter(final Collection<? extends E> collection, final Predicate<? super E> filter, final CollectionFactory<E, R> resultFactory) {
		return IteratorUtils.drainAll(IteratorUtils.filter(collection.iterator(), filter), resultFactory.build());
	}
	
	/**
	 * Transforms the elements of the given collection using the given function.
	 *
	 * @param <E> Type of the elements.
	 * @param <RE> Type of the transformed elements.
	 * @param <R> Type of the result collection.
	 * @param collection Collection containing the elements to transform.
	 * @param function Function to use to transform the elements.
	 * @param resultFactory Factory of the result collection.
	 * @return A collection containing the transformed elements.
	 */
	public static <E, RE, R extends Collection<? super RE>> R map(final Collection<? extends E> collection, final Function<? super E, ? extends RE> function, final CollectionFactory<RE, R> resultFactory) {
		return IteratorUtils.drainAll(IteratorUtils.map(collection.iterator(), function), resultFactory.build(collection.size()));
	}
	
	/**
	 * Transforms and flattens the elements of the given collection using the given function.
	 * 
	 * @param <E> Type of the elements.
	 * @param <RE> Type of the transformed elements.
	 * @param <R> Type of the result collection.
	 * @param collection Collection containing the elements to transform.
	 * @param function Function to use to transform the elements.
	 * @param resultFactory Factory of the result collection.
	 * @return A collection containing the flatten, transformed elements.
	 */
	public static <E, RE, R extends Collection<? super RE>> R flatMap(final Collection<? extends E> collection, final Function<? super E, ? extends Collection<? extends RE>> function, final CollectionFactory<RE, R> resultFactory) {
		return resultFactory.build(IterableUtils.flatMap(collection, function));
	}
	
	/**
	 * Extracts and flattens the elements of the given collection using the given extractor.
	 *
	 * @param <E> Type of the elements.
	 * @param <RE> Type of the extracted elements.
	 * @param <R> Type of the result collection.
	 * @param collection Collection containing the elements to extract from.
	 * @param extractor Function to use to extract the elements.
	 * @param resultFactory Factory of the result collection.
	 * @return A collection containing the extracted elements.
	 */
	public static <E, RE, R extends Collection<? super RE>> R extract(final Collection<? extends E> collection, final Function<? super E, ? extends Iterable<? extends RE>> extractor, final CollectionFactory<RE, R> resultFactory) {
		return IteratorUtils.drainAll(IteratorUtils.extract(collection.iterator(), extractor), resultFactory.build());
	}
	
	//	/**
	//	 * Gets a binding from the given map.
	//	 *
	//	 * @param <K> Type of the keys.
	//	 * @param <V> Type of the values.
	//	 * @param map Map to read.
	//	 * @return Some binding if any.
	//	 */
	//	public static <K, V> Maybe<Map.Entry<K, V>> any(final Map<K, V> map) {
	//		assert null != map;
	//
	//		return IteratorUtils.next(map.entrySet().iterator());
	//	}
	//
	//	/**
	//	 * Gets the element of the given list corresponding to the given index.
	//	 *
	//	 * @param <T> Type of the elements.
	//	 * @param list List to read.
	//	 * @param index Index of the element to get.
	//	 * @return The element or nothing if the index is out of bound.
	//	 */
	//	public static <T> Maybe<T> get(final List<? extends T> list, final int index) {
	//		assert null != list;
	//
	//		return index < list.size() ? Maybe.<T>some(list.get(index)) : Maybe.<T>none();
	//	}
	//
	//	/**
	//	 * Gets the first element of the given list.
	//	 *
	//	 * @param <T> Type of the elements.
	//	 * @param list List to read.
	//	 * @return The first element.
	//	 */
	//	public static <T> Maybe<T> first(final List<? extends T> list) {
	//		return get(list, 0);
	//	}
	//
	//	/**
	//	 * Gets the last element of the given list.
	//	 *
	//	 * @param <T> Type of the elements.
	//	 * @param list List to read.
	//	 * @return The last element.
	//	 */
	//	public static <T> Maybe<T> last(final List<? extends T> list) {
	//		final int size = list.size();
	//		return size > 0 ? Maybe.<T>some(list.get(size - 1)) : Maybe.<T>none();
	//	}
	
	/**
	 * Adds all given values to the given collection.
	 *
	 * @param <T> Type of the values.
	 * @param collection Collection to populate.
	 * @param values Values to add.
	 * @return <code>true</code> when the given collection has been modified, <code>false</code> otherwise.
	 */
	public static <T> boolean addAll(final Collection<? super T> collection, final Iterable<? extends T> values) {
		final Accumulator<Boolean, Boolean> changed = LangAccumulators.or(false);
		for (final T value : values) {
			changed.add(collection.add(value));
		}
		return changed.get().booleanValue();
	}
	
	//	/**
	//	 * Removes an element from the given collection.
	//	 * <p>
	//	 * This method does modify the given collection.
	//	 *
	//	 * @param <T> Type of the elements.
	//	 * @param collection The collection.
	//	 * @return The removed element.
	//	 */
	//	public static <T> Maybe<T> removeAny(final Collection<? extends T> collection) {
	//		assert null != collection;
	//
	//		final Iterator<? extends T> iterator = collection.iterator();
	//		if (iterator.hasNext()) {
	//			final T object = iterator.next();
	//			iterator.remove();
	//			return Maybe.some(object);
	//		} else {
	//			return Maybe.none();
	//		}
	//	}
	//
	//	/**
	//	 * Gets the value associated to the given key in the given map.
	//	 *
	//	 * @param <K> Type of the keys.
	//	 * @param <V> Type of the values.
	//	 * @param map The map.
	//	 * @param key The key. May be <code>null</code>.
	//	 * @return The associated value.
	//	 */
	//	public static <K, V> Maybe<V> get(final Map<? super K, ? extends V> map, final K key) {
	//		assert null != map;
	//
	//		return map.containsKey(key) ? Maybe.<V>some(map.get(key)) : Maybe.<V>none();
	//	}
	//
	//	/**
	//	 * Gets the value associated to the given key in the given map.
	//	 *
	//	 * @param <K> Type of the keys.
	//	 * @param <V> Type of the values.
	//	 * @param map The map.
	//	 * @param key The key. May be <code>null</code>.
	//	 * @param defaultValue The default value. May be <code>null</code>.
	//	 * @return The associated value if any, or the default value. May be <code>null</code>.
	//	 */
	//	public static <K, V> V get(final Map<? super K, ? extends V> map, final K key, final V defaultValue) {
	//		assert null != map;
	//
	//		return map.containsKey(key) ? map.get(key) : defaultValue;
	//	}
	//
	//	/**
	//	 * Gets the value associated to the given key in the given map.
	//	 *
	//	 * @param <K> Type of the keys.
	//	 * @param <V> Type of the values.
	//	 * @param <X> Type of the exceptions.
	//	 * @param map The map.
	//	 * @param key The key. May be <code>null</code>.
	//	 * @param throwableFactory The throwable factory.
	//	 * @return The associated value. May be <code>null</code>.
	//	 * @throws X When no values is identified by the given key in the map.
	//	 */
	//	public static <K, V, X extends Exception> V get(final Map<? super K, ? extends V> map, final K key, final ThrowableFactory<X> throwableFactory)
	//	throws X {
	//		assert null != map;
	//		assert null != throwableFactory;
	//
	//		if (map.containsKey(key)) {
	//			return map.get(key);
	//		} else {
	//			throw throwableFactory.build("Missing value for key \"" + key + "\"");
	//		}
	//	}
	//
	//	/**
	//	 * Associates the given value to the given key in the given map.
	//	 *
	//	 * @param <K> Type of the keys.
	//	 * @param <V> Type of the values.
	//	 * @param map The map.
	//	 * @param key The key. May be <code>null</code>.
	//	 * @param value The value. May be <code>null</code>.
	//	 * @return The presiously associated value.
	//	 */
	//	public static <K, V> Maybe<V> put(final Map<? super K, V> map, final K key, final V value) {
	//		assert null != map;
	//
	//		final Maybe<V> currentValue = get(map, key);
	//		map.put(key, value);
	//		return currentValue;
	//	}
	//
	//	/**
	//	 * Associates the given value to the given key in the given map.
	//	 *
	//	 * @param <K> Type of the keys.
	//	 * @param <V> Type of the values.
	//	 * @param map The map.
	//	 * @param key The key. May be <code>null</code>.
	//	 * @param value The value.
	//	 */
	//	public static <K, V> void put(final Map<? super K, V> map, final K key, final Maybe<? extends V> value) {
	//		assert null != map;
	//		assert null != value;
	//
	//		if (value.isSome()) {
	//			map.put(key, value.asSome().getValue());
	//		}
	//	}
	//
	//	/**
	//	 * Removes the value associated to the given key from the given map.
	//	 *
	//	 * @param <K> Type of the keys.
	//	 * @param <V> Type of the values.
	//	 * @param map The map.
	//	 * @param key The key. May be <code>null</code>.
	//	 * @return The removed value.
	//	 */
	//	public static <K, V> Maybe<V> remove(final Map<? super K, ? extends V> map, final K key) {
	//		assert null != map;
	//		assert null != key;
	//
	//		if (map.containsKey(key)) {
	//			return Maybe.<V>some(map.remove(key));
	//		} else {
	//			return Maybe.none();
	//		}
	//	}
	//
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
	//
	//	/**
	//	 * Computes the union of the given collections and populates the given result collection with it.
	//	 * <p>
	//	 * When the populated collection is ordered, the items of the first given collection precede the items of the second one.
	//	 *
	//	 * @param <T> Type of the elements.
	//	 * @param <C> Type of the result collection.
	//	 * @param collection1 The first collection.
	//	 * @param collection2 The second collection.
	//	 * @param results The collection to populate with the results.
	//	 * @return The given result collection.
	//	 */
	//	public static <T, C extends Collection<? super T>> C union(final Collection<? extends T> collection1, final Collection<? extends T> collection2, final C results) {
	//		assert null != collection1;
	//		assert null != collection2;
	//		assert null != results;
	//
	//		results.addAll(collection1);
	//		results.addAll(collection2);
	//		return results;
	//	}
	//
	//	/**
	//	 * Computes the union of the given maps and populates the given result map with it.
	//	 * <p>
	//	 * The values of the first map have precedence over the values of the second map when their domains intersect.
	//	 *
	//	 * @param <K> Type of the keys.
	//	 * @param <V> Type of the values.
	//	 * @param <M> Type of the result map.
	//	 * @param map1 The first map.
	//	 * @param map2 The second map.
	//	 * @param results The map to populate with the results.
	//	 * @return The given result map.
	//	 */
	//	public static <K, V, M extends Map<? super K, ? super V>> M union(final Map<? extends K, ? extends V> map1, final Map<? extends K, ? extends V> map2, final M results) {
	//		assert null != map1;
	//		assert null != map2;
	//		assert null != results;
	//
	//		results.putAll(map2);
	//		results.putAll(map1);
	//		return results;
	//	}
	//
	//	/**
	//	 * Tests whether the given collections intersect. The collections instersect when they have some common value.
	//	 * <p>
	//	 * This method iterates over the first collection and tests the presence of the values within the second collection. Therefore, providing a smaller first
	//	 * collection and a second collection with a faster test method is more efficient.
	//	 *
	//	 * @param <T> Type of the elements.
	//	 * @param collection1 The first collection.
	//	 * @param collection2 The second collection.
	//	 * @return <code>true</code> if the collections intersect, <code>false</code> otherwise.
	//	 */
	//	public static <T> boolean intersects(final Collection<? extends T> collection1, final Collection<? extends T> collection2) {
	//		assert null != collection1;
	//		assert null != collection2;
	//
	//		for (final T value : collection1) {
	//			if (collection2.contains(value)) {
	//				return true;
	//			}
	//		}
	//		return false;
	//	}
	//
	//	/**
	//	 * Computes the intersection of the given collections and populates the given result collection with it.
	//	 * <p>
	//	 * This method iterates over the first collection and tests the presence of the values within the second collection. Therefore, providing a smaller first
	//	 * collection and a second collection with a faster test method is more efficient.
	//	 *
	//	 * @param <T> Type of the elements.
	//	 * @param <C> Type of the result collection.
	//	 * @param collection1 The first collection.
	//	 * @param collection2 The second collection.
	//	 * @param results The collection to populate with the results.
	//	 * @return The given result collection.
	//	 */
	//	public static <T, C extends Collection<? super T>> C intersection(final Collection<? extends T> collection1, final Collection<? extends T> collection2, final C results) {
	//		assert null != collection1;
	//		assert null != collection2;
	//		assert null != results;
	//
	//		for (final T value : collection1) {
	//			if (collection2.contains(value)) {
	//				results.add(value);
	//			}
	//		}
	//		return results;
	//	}
	//
	//	/**
	//	 * Computes the exclusion of given collections (first minus second) and populates the given result collection with it.
	//	 * <p>
	//	 * This method iterates over the first collection and tests the presence of the values within the second collection. Therefore, providing a smaller first
	//	 * collection and a second collection with a faster test method is more efficient.
	//	 *
	//	 * @param <T> Type of the elements.
	//	 * @param <C> Type of the result collection.
	//	 * @param collection1 The first collection.
	//	 * @param collection2 The second collection.
	//	 * @param results The collection to populate with the results.
	//	 * @return The given result collection.
	//	 */
	//	public static <T, C extends Collection<? super T>> C exclusion(final Collection<? extends T> collection1, final Collection<? extends T> collection2, final C results) {
	//		assert null != collection1;
	//		assert null != collection2;
	//		assert null != results;
	//
	//		for (final T value : collection1) {
	//			if (!collection2.contains(value)) {
	//				results.add(value);
	//			}
	//		}
	//		return results;
	//	}
	//
	//	/**
	//	 * Fills the given map with the bindings corresponding to the given value associated to the given keys.
	//	 *
	//	 * @param <K> Type of the keys.
	//	 * @param <V> Type of the values.
	//	 * @param <M> Type of the map.
	//	 * @param map The map.
	//	 * @param keys The keys.
	//	 * @param value The value. May be <code>null</code>.
	//	 * @return The given map.
	//	 */
	//	public static <K, V, M extends Map<? super K, ? super V>> M fill(final M map, final Iterable<? extends K> keys, final V value) {
	//		assert null != map;
	//		assert null != keys;
	//
	//		for (final K key : keys) {
	//			map.put(key, value);
	//		}
	//		return map;
	//	}
	//
	//	// TODO: rename to retain or filter
	//	// TODO: genalize with a funciton instead of a map
	//	/**
	//	 * Gets the bindings of the given map associated to the given keys and populates the given result map with them.
	//	 *
	//	 * @param <K> Type of the keys.
	//	 * @param <V> Type of the values.
	//	 * @param <M> Type of the result map.
	//	 * @param map The map.
	//	 * @param keys The keys of the bindings.
	//	 * @param results The map to populate with the results.
	//	 * @return The given result map.
	//	 */
	//	public static <K, V, M extends Map<? super K, ? super V>> M sub(final Map<? extends K, ? extends V> map, final Iterable<? extends K> keys, final M results) {
	//		assert null != map;
	//		assert null != keys;
	//		assert null != results;
	//
	//		for (final K key : keys) {
	//			if (map.containsKey(key)) {
	//				final V value = map.get(key);
	//				results.put(key, value);
	//			}
	//		}
	//		return results;
	//	}
	//
	//	// TODO: rename to ???
	//	// TODO: genalize with a funciton instead of a map
	//	/**
	//	 * Gets the bindings of the given map not associated to the given keys and populates the given result map with them.
	//	 *
	//	 * @param <K> Type of the keys.
	//	 * @param <V> Type of the values.
	//	 * @param <M> Type of the result map.
	//	 * @param map The map.
	//	 * @param keys The keys of the bindings.
	//	 * @param results The map to populate with the results.
	//	 * @return The given result map.
	//	 */
	//	public static <K, V, M extends Map<? super K, ? super V>> M retain(final Map<? extends K, ? extends V> map, final Collection<? extends K> keys, final M results) {
	//		assert null != map;
	//		assert null != keys;
	//		assert null != results;
	//
	//		for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
	//			final K key = entry.getKey();
	//			if (!keys.contains(key)) {
	//				results.put(key, entry.getValue());
	//			}
	//		}
	//		return results;
	//	}
	//
	//	// TODO: accumulator version
	//	/**
	//	 * Groups the given values by batches of the given size and populates the given collections with the batches.
	//	 *
	//	 * @param <T> Type of the values.
	//	 * @param <C> Type of the batch collections.
	//	 * @param <R> Type of the result collection.
	//	 * @param factory The collection factory of the batches.
	//	 * @param size The maximum size of each batch.
	//	 * @param values The values.
	//	 * @param results The collection to populates with the results.
	//	 * @return The given result collection.
	//	 */
	//	public static <T, C extends Collection<? super T>, R extends Collection<? super C>> R group(final CollectionFactory<T, C> factory, final int size, final Iterable<? extends T> values, final R results) {
	//		assert null != factory;
	//		assert null != values;
	//		assert null != results;
	//
	//		final Iterator<? extends T> valuesIt = values.iterator();
	//		while (valuesIt.hasNext()) {
	//			results.add(IteratorUtils.drain(valuesIt, size, factory.build(size)));
	//		}
	//		return results;
	//	}
	//
	//	// TODO: accumulator version
	//	/**
	//	 * Groups the elements of the given lists and populates the given result list with the pairs.
	//	 * <p>
	//	 * The pairs are formed with respect of the positions of the values in the lists. The extra values of the longest list are dropped when the lists don't have
	//	 * the same length.
	//	 *
	//	 * @param <T1> Type of the first values.
	//	 * @param <T2> Type of the second values.
	//	 * @param <L> Type of the result list.
	//	 * @param list1 The list containing the first values.
	//	 * @param list2 The list containing the second values.
	//	 * @param results The list to populate with the results.
	//	 * @return The given result list.
	//	 */
	//	public static <T1, T2, L extends List<? super Tuple2<T1, T2>>> L zip(final Iterable<? extends T1> list1, final Iterable<? extends T2> list2, final L results) {
	//		assert null != list1;
	//		assert null != list2;
	//
	//		return zip(list1.iterator(), list2.iterator(), results);
	//	}
	//
	//	/**
	//	 * Groups the elements provided by the given iterators and populates the given result list with the pairs.
	//	 * <p>
	//	 * The pairs are formed with respect of the order of the iterators. The extra values of the longest iterator are dropped when the given iterators don't
	//	 * provide the same number of elements.
	//	 *
	//	 * @param <T1> Type of the first values.
	//	 * @param <T2> Type of the second values.
	//	 * @param <L> Type of the result list.
	//	 * @param iterator1 The iterator providing the first values.
	//	 * @param iterator2 The iterator providing the second values.
	//	 * @param results The list to populate with the results.
	//	 * @return The given result list.
	//	 */
	//	public static <T1, T2, L extends List<? super Tuple2<T1, T2>>> L zip(final Iterator<? extends T1> iterator1, final Iterator<? extends T2> iterator2, final L results) {
	//		assert null != iterator1;
	//		assert null != iterator2;
	//		assert null != results;
	//
	//		while (iterator1.hasNext() && iterator2.hasNext()) {
	//			results.add(new Tuple2<T1, T2>(iterator1.next(), iterator2.next()));
	//		}
	//		return results;
	//	}
	//
	//	// TODO: accumulator version
	//	/**
	//	 * Decomposes the pairs of the given collection and populates the given collections with the first and second values.
	//	 *
	//	 * @param <T1> Type of the first values.
	//	 * @param <T2> Type of the second values.
	//	 * @param pairs The pairs.
	//	 * @param results1 The collection to populate with the first values.
	//	 * @param results2 The collection to populate with the second values.
	//	 */
	//	public static <T1, T2> void unzip(final Iterable<? extends Tuple2<T1, T2>> pairs, final Collection<? super T1> results1, final Collection<? super T2> results2) {
	//		assert null != pairs;
	//		assert null != results1;
	//		assert null != results2;
	//
	//		unzip(pairs.iterator(), results1, results2);
	//	}
	//
	//	/**
	//	 * Decomposes the pairs provided by the given iterator and populates the given collections with the first and second values.
	//	 *
	//	 * @param <T1> Type of the first values.
	//	 * @param <T2> Type of the second values.
	//	 * @param pairs The iterator.
	//	 * @param results1 The collection to populate with the first values.
	//	 * @param results2 The collection to populate with the second values.
	//	 */
	//	public static <T1, T2> void unzip(final Iterator<? extends Tuple2<T1, T2>> pairs, final Collection<? super T1> results1, final Collection<? super T2> results2) {
	//		assert null != pairs;
	//		assert null != results1;
	//		assert null != results2;
	//
	//		while (pairs.hasNext()) {
	//			final Tuple2<T1, T2> value = pairs.next();
	//			results1.add(value.get1());
	//			results2.add(value.get2());
	//		}
	//	}
	//
	//	/**
	//	 * Extracts the bindings of the given map and populates the given collection with them.
	//	 *
	//	 * @param <K> Type of the keys.
	//	 * @param <V> Type of the values.
	//	 * @param <C> Type of the result collection.
	//	 * @param map The map.
	//	 * @param results The collection to populate with the results.
	//	 * @return The given result collection.
	//	 */
	//	public static <K, V, C extends Collection<? super Tuple2<K, V>>> C bindings(final Map<? extends K, ? extends V> map, final C results) {
	//		assert null != map;
	//		assert null != results;
	//
	//		for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
	//			results.add(new Tuple2<K, V>(entry.getKey(), entry.getValue()));
	//		}
	//		return results;
	//	}
	
	private CollectionUtils() {
		// Prevents instantiation.
	}
}
