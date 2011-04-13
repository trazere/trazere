/*
 *  Copyright 2006-2011 Julien Dufour
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
import com.trazere.util.lang.MutableObject;
import com.trazere.util.lang.ThrowableFactory;
import com.trazere.util.type.Maybe;
import com.trazere.util.type.Tuple2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;

/**
 * The {@link CollectionUtils} class provides various utilities regarding the manipulation of collections and maps.
 */
public class CollectionUtils {
	/**
	 * Builds a new list containing the given value.
	 * <p>
	 * This method instantiates an {@link ArrayList}.
	 * 
	 * @param <T> Type of the the value.
	 * @param value The value. May be <code>null</code>.
	 * @return The built list.
	 */
	public static <T> List<T> list(final T value) {
		final List<T> list = new ArrayList<T>(1);
		list.add(value);
		return list;
	}
	
	/**
	 * Builds a new list containing the given values.
	 * <p>
	 * This method instantiates an {@link ArrayList}.
	 * 
	 * @param <T> Type of the the values.
	 * @param value1 The first value. May be <code>null</code>.
	 * @param value2 The second value. May be <code>null</code>.
	 * @return The built list.
	 */
	public static <T> List<T> list(final T value1, final T value2) {
		final List<T> list = new ArrayList<T>(2);
		list.add(value1);
		list.add(value2);
		return list;
	}
	
	/**
	 * Builds a new list containing the given values.
	 * <p>
	 * This method instantiates an {@link ArrayList}.
	 * 
	 * @param <T> Type of the the values.
	 * @param value1 The first value. May be <code>null</code>.
	 * @param value2 The second value. May be <code>null</code>.
	 * @param value3 The third value. May be <code>null</code>.
	 * @return The built list.
	 */
	public static <T> List<T> list(final T value1, final T value2, final T value3) {
		final List<T> list = new ArrayList<T>(3);
		list.add(value1);
		list.add(value2);
		list.add(value3);
		return list;
	}
	
	/**
	 * Builds a new list containing the given values.
	 * <p>
	 * This method instantiates an {@link ArrayList}.
	 * 
	 * @param <T> Type of the the values.
	 * @param values The values. May be <code>null</code>.
	 * @return The built list.
	 */
	public static <T> List<T> listN(final T... values) {
		assert null != values;
		
		final List<T> list = new ArrayList<T>(values.length);
		for (final T value : values) {
			list.add(value);
		}
		return list;
	}
	
	/**
	 * Builds a new list containing the given value.
	 * <p>
	 * This method instantiates an {@link ArrayList}.
	 * 
	 * @param <T> Type of the the value.
	 * @param value The value.
	 * @return The built list.
	 */
	public static <T> List<T> list(final Maybe<T> value) {
		assert null != value;
		
		final List<T> list = new ArrayList<T>();
		if (value.isSome()) {
			list.add(value.asSome().getValue());
		}
		return list;
	}
	
	/**
	 * Builds a set containing the given value.
	 * <p>
	 * This method instantiates a {@link HashSet}.
	 * 
	 * @param <T> Type of the the value.
	 * @param value The value. May be <code>null</code>.
	 * @return The built set.
	 */
	public static <T> Set<T> set(final T value) {
		final Set<T> set = new HashSet<T>(1);
		set.add(value);
		return set;
	}
	
	/**
	 * Builds a set containing the given values.
	 * <p>
	 * This method instantiates a {@link HashSet}.
	 * 
	 * @param <T> Type of the the values.
	 * @param value1 The first value. May be <code>null</code>.
	 * @param value2 The second value. May be <code>null</code>.
	 * @return The built set.
	 */
	public static <T> Set<T> set(final T value1, final T value2) {
		final Set<T> set = new HashSet<T>(2);
		set.add(value1);
		set.add(value2);
		return set;
	}
	
	/**
	 * Builds a set containing the given values.
	 * <p>
	 * This method instantiates a {@link HashSet}.
	 * 
	 * @param <T> Type of the the values.
	 * @param value1 The first value. May be <code>null</code>.
	 * @param value2 The second value. May be <code>null</code>.
	 * @param value3 The third value. May be <code>null</code>.
	 * @return The built set.
	 */
	public static <T> Set<T> set(final T value1, final T value2, final T value3) {
		final Set<T> set = new HashSet<T>(3);
		set.add(value1);
		set.add(value2);
		set.add(value3);
		return set;
	}
	
	/**
	 * Builds a set containing the given values.
	 * <p>
	 * This method instantiates a {@link HashSet}.
	 * 
	 * @param <T> Type of the the values.
	 * @param values The values. May be <code>null</code>.
	 * @return The built set.
	 */
	public static <T> Set<T> setN(final T... values) {
		assert null != values;
		
		final Set<T> set = new HashSet<T>(values.length);
		for (final T value : values) {
			set.add(value);
		}
		return set;
	}
	
	/**
	 * Builds a set containing the given value.
	 * <p>
	 * This method instantiates a {@link HashSet}.
	 * 
	 * @param <T> Type of the the value.
	 * @param value The value.
	 * @return The built set.
	 */
	public static <T> Set<T> set(final Maybe<T> value) {
		assert null != value;
		
		final Set<T> set = new HashSet<T>();
		if (value.isSome()) {
			set.add(value.asSome().getValue());
		}
		return set;
	}
	
	/**
	 * Builds a map containing the binding of the given key and value.
	 * <p>
	 * This method instantiates a {@link HashMap}.
	 * 
	 * @param <K> Type of the key.
	 * @param <V> Type of the value.
	 * @param key The key. May be <code>null</code>.
	 * @param value The value. May be <code>null</code>.
	 * @return The built map.
	 */
	public static <K, V> Map<K, V> map(final K key, final V value) {
		final Map<K, V> result = new HashMap<K, V>();
		result.put(key, value);
		return result;
	}
	
	/**
	 * Builds a map containing the bindings of the given keys and values.
	 * <p>
	 * This method instantiates a {@link HashMap}.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param key1 The first key. May be <code>null</code>.
	 * @param value1 The first value. May be <code>null</code>.
	 * @param key2 The second key. May be <code>null</code>.
	 * @param value2 The second value. May be <code>null</code>.
	 * @return The built map.
	 */
	public static <K, V> Map<K, V> map(final K key1, final V value1, final K key2, final V value2) {
		final Map<K, V> result = new HashMap<K, V>();
		result.put(key2, value2);
		result.put(key1, value1);
		return result;
	}
	
	/**
	 * Builds a map containing the given bindings.
	 * <p>
	 * This method instantiates a {@link HashMap}.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param bindings The bindings.
	 * @return The built map.
	 */
	public static <K, V> Map<K, V> map(final Collection<? extends Tuple2<? extends K, ? extends V>> bindings) {
		assert null != bindings;
		
		final Map<K, V> results = new HashMap<K, V>(bindings.size());
		for (final Tuple2<? extends K, ? extends V> binding : bindings) {
			results.put(binding.getFirst(), binding.getSecond());
		}
		return results;
	}
	
	/**
	 * Builds an iterator over no values.
	 * 
	 * @param <T> Type of the the values.
	 * @return The built iterator.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Iterator<T> iterator() {
		return (Iterator<T>) _EMPTY_ITERATOR;
	}
	
	private static final Iterator<?> _EMPTY_ITERATOR = new Iterator<Object>() {
		public boolean hasNext() {
			return false;
		}
		
		public Object next() {
			throw new NoSuchElementException();
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
	};
	
	/**
	 * Builds an iterator over the given values.
	 * 
	 * @param <T> Type of the the values.
	 * @param values The values.
	 * @return The built iterator.
	 */
	public static <T> Iterator<T> iterator(final T... values) {
		assert null != values;
		
		return new Iterator<T>() {
			protected int _index = 0;
			
			public boolean hasNext() {
				return _index < values.length;
			}
			
			public T next() {
				final T value = values[_index];
				_index += 1;
				
				return value;
			}
			
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	/**
	 * Gets the next value from the given iterator.
	 * 
	 * @param <T> Type of the values.
	 * @param values The values.
	 * @return The next value.
	 */
	public static <T> Maybe<T> next(final Iterator<? extends T> values) {
		assert null != values;
		
		return values.hasNext() ? Maybe.<T>some(values.next()) : Maybe.<T>none();
	}
	
	/**
	 * Drains the elements from the given iterator and populate the given collection with them.
	 * 
	 * @param <T> Type of the elements.
	 * @param <R> Type of the result collection.
	 * @param values The iterator.
	 * @param results The collection to populate with the elements.
	 * @return The result collection.
	 */
	public static <T, R extends Collection<? super T>> R drain(final Iterator<T> values, final R results) {
		assert null != values;
		assert null != results;
		
		while (values.hasNext()) {
			results.add(values.next());
		}
		return results;
	}
	
	/**
	 * Gets an element from the given collection.
	 * 
	 * @param <T> Type of the elements.
	 * @param collection The collection.
	 * @return Any element of the collection.
	 */
	public static <T> Maybe<T> any(final Collection<? extends T> collection) {
		assert null != collection;
		
		return next(collection.iterator());
	}
	
	/**
	 * Gets a binding from the given map.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map The map.
	 * @return Any binding of the map.
	 */
	public static <K, V> Maybe<Map.Entry<K, V>> any(final Map<K, V> map) {
		assert null != map;
		
		return next(map.entrySet().iterator());
	}
	
	/**
	 * Gets the least given value according to the given comparator.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator The comparator.
	 * @param values The values.
	 * @return The least value.
	 */
	public static <T> Maybe<T> least(final Comparator<? super T> comparator, final Collection<? extends T> values) {
		assert null != values;
		
		return least(comparator, values.iterator());
	}
	
	/**
	 * Gets the least given value according to the given comparator.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator The comparator.
	 * @param values The values.
	 * @return The least value.
	 */
	public static <T> Maybe<T> least(final Comparator<? super T> comparator, final Iterator<? extends T> values) {
		assert null != comparator;
		assert null != values;
		
		// Get the first value.
		if (!values.hasNext()) {
			return Maybe.none();
		}
		
		// Get the least value.
		final MutableObject<T> least = new MutableObject<T>(values.next());
		while (values.hasNext()) {
			final T value = values.next();
			if (comparator.compare(value, least.get()) < 1) {
				least.set(value);
			}
		}
		return Maybe.some(least.get());
	}
	
	/**
	 * Gets the element of the given list corresponding to the given index.
	 * 
	 * @param <T> Type of the elements.
	 * @param list The list.
	 * @param index The index.
	 * @return The first element.
	 */
	public static <T> Maybe<T> get(final List<T> list, final int index) {
		assert null != list;
		
		return index >= list.size() ? Maybe.some(list.get(index)) : Maybe.<T>none();
	}
	
	/**
	 * Gets the first element of the given list.
	 * 
	 * @param <T> Type of the elements.
	 * @param list The list.
	 * @return The first element.
	 */
	public static <T> Maybe<T> first(final List<T> list) {
		return get(list, 0);
	}
	
	/**
	 * Gets the last element of the given list.
	 * 
	 * @param <T> Type of the elements.
	 * @param list The list.
	 * @return The last element.
	 */
	public static <T> Maybe<T> last(final List<T> list) {
		return get(list, list.size() - 1);
	}
	
	/**
	 * Adds the given value to the given collection.
	 * 
	 * @param <T> Type of the values.
	 * @param collection The collection.
	 * @param value The value.
	 */
	public static <T> void add(final Collection<? super T> collection, final Maybe<? extends T> value) {
		assert null != collection;
		assert null != value;
		
		if (value.isSome()) {
			collection.add(value.asSome().getValue());
		}
	}
	
	/**
	 * Removes an element from the given collection.
	 * <p>
	 * This method does modify the given collection.
	 * 
	 * @param <T> Type of the elements.
	 * @param collection The collection.
	 * @return The removed element.
	 */
	public static <T> Maybe<T> removeAny(final Collection<? extends T> collection) {
		assert null != collection;
		
		final Iterator<? extends T> iterator = collection.iterator();
		if (iterator.hasNext()) {
			final T object = iterator.next();
			iterator.remove();
			return Maybe.some(object);
		} else {
			return Maybe.none();
		}
	}
	
	/**
	 * Gets the value identified by the given key in the given map.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map The map.
	 * @param key The key. May be <code>null</code>.
	 * @return The value identified by the key.
	 */
	public static <K, V> Maybe<V> get(final Map<? super K, ? extends V> map, final K key) {
		assert null != map;
		
		return map.containsKey(key) ? Maybe.<V>some(map.get(key)) : Maybe.<V>none();
	}
	
	/**
	 * Gets the value identified by the given key in the given map.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map The map.
	 * @param key The key. May be <code>null</code>.
	 * @param defaultValue The default value. May be <code>null</code>.
	 * @return The value identified by the key if any, or the default value.
	 */
	public static <K, V> V get(final Map<? super K, ? extends V> map, final K key, final V defaultValue) {
		assert null != map;
		
		return map.containsKey(key) ? map.get(key) : defaultValue;
	}
	
	/**
	 * Gets the value identified by the given key in the given map.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param map The map.
	 * @param key The key. May be <code>null</code>.
	 * @param throwableFactory The throwable factory.
	 * @return The value identified by the key if any, or the default value.
	 * @throws X When no values is identified by the given key in the map.
	 */
	public static <K, V, X extends Exception> V get(final Map<? super K, ? extends V> map, final K key, final ThrowableFactory<X> throwableFactory)
	throws X {
		assert null != map;
		assert null != key;
		assert null != throwableFactory;
		
		if (map.containsKey(key)) {
			return map.get(key);
		} else {
			throw throwableFactory.build("Missing value for key \"" + key + "\"");
		}
	}
	
	/**
	 * Adds the given binding to the given map.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map The map.
	 * @param key The key. May be <code>null</code>.
	 * @param value The value.
	 */
	public static <K, V> void put(final Map<? super K, ? super V> map, final K key, final Maybe<? extends V> value) {
		assert null != map;
		assert null != value;
		
		if (value.isSome()) {
			map.put(key, value.asSome().getValue());
		}
	}
	
	/**
	 * Reverses the given list.
	 * <p>
	 * This method does modify the given list.
	 * 
	 * @param <T> Type of the elements.
	 * @param <L> Type of the list.
	 * @param list The list.
	 * @return The given modified list.
	 */
	public static <T, L extends List<T>> L reverse(final L list) {
		assert null != list;
		
		Collections.reverse(list);
		return list;
	}
	
	/**
	 * Sorts the given list using the given comparator.
	 * <p>
	 * This method does modify the given list.
	 * 
	 * @param <T> Type of the elements.
	 * @param <L> Type of the list.
	 * @param list The list.
	 * @return The given modified list.
	 */
	public static <T extends Comparable<? super T>, L extends List<T>> L sort(final L list) {
		assert null != list;
		
		Collections.sort(list);
		return list;
	}
	
	/**
	 * Sorts the given list using the given comparator.
	 * <p>
	 * This method does modify the given list.
	 * 
	 * @param <T> Type of the elements.
	 * @param <L> Type of the list.
	 * @param list The list.
	 * @param comparator The comparator.
	 * @return The given modified list.
	 */
	public static <T, L extends List<T>> L sort(final L list, final Comparator<? super T> comparator) {
		assert null != list;
		assert null != comparator;
		
		Collections.sort(list, comparator);
		return list;
	}
	
	/**
	 * Sorts the given values topologically and populates the given list with the sorted values.
	 * <p>
	 * The dependencies between the values are computed using the given function. This function must compute the values whose the argument value depends on. The
	 * computed values must belong to the values to sort.
	 * <p>
	 * This method places the dependencies before the value which depend on them. The sort is stable and fails when the dependencies form a cyclic graph.
	 * 
	 * @param <T> Type of the values.
	 * @param <L> Type of the result list.
	 * @param <X> Type of the exceptions.
	 * @param dependencyFunction The function computing the dependencies.
	 * @param close Flag indicating whether a transitive closure should be performed or if the given values are supposed are supposed to closed.
	 * @param values The values.
	 * @param results The list to populate with the results.
	 * @return The given result list.
	 * @throws CollectionException When some computed dependency value does not belong to the values to sort.
	 * @throws CollectionException When there is a cycle in the dependencies.
	 * @throws X When some dependency computation fails.
	 */
	public static <T, L extends List<? super T>, X extends Exception> L topologicalSort(final Function1<? super T, ? extends Collection<? extends T>, X> dependencyFunction, final boolean close, final Collection<? extends T> values, final L results)
	throws CollectionException, X {
		assert null != values;
		assert null != dependencyFunction;
		assert null != results;
		
		// Compute the dependencies.
		final List<T> pendingValues = new ArrayList<T>(values.size());
		final Collection<Tuple2<T, T>> dependencies = computeTopologicalSortDependencies(dependencyFunction, close, values, pendingValues, new ArrayList<Tuple2<T, T>>());
		
		// Sort the values.
		while (!pendingValues.isEmpty()) {
			// Find the leaves.
			final Set<T> leafValues = findTopologicalSortLeaves(pendingValues, dependencies);
			if (leafValues.isEmpty()) {
				throw new CollectionException("Cyclic or external dependencies for values " + pendingValues);
			}
			
			// Add the leaves to the result.
			extractTopologicalSortLeaves(leafValues, pendingValues, results);
			
			// Clean the dependencies.
			cleanTopologicalSortDependencies(dependencies, leafValues);
		}
		
		return results;
	}
	
	/**
	 * Sorts the given values topologically and populates the given list with the sorted regions.
	 * <p>
	 * A region is a set of values which have no dependencies on each other. They however do have dependencies on some values of the previous region.
	 * <p>
	 * The dependencies between the values are computed using the given function. This function must compute the values whose the argument value depends on. The
	 * computed values must belong to the values to sort.
	 * <p>
	 * This method places the dependencies before the value which depend on them. The sort is stable and fails when the dependencies form a cyclic graph.
	 * 
	 * @param <T> Type of the values.
	 * @param <L> Type of the result list.
	 * @param <X> Type of the exceptions.
	 * @param dependencyFunction The function computing the dependencies.
	 * @param close Flag indicating whether a transitive closure should be performed or if the given values are supposed are supposed to closed.
	 * @param values The values.
	 * @param results The list to populate with the results.
	 * @return The given result list.
	 * @throws CollectionException When some computed dependency value does not belong to the values to sort.
	 * @throws CollectionException When there is a cycle in the dependencies.
	 * @throws X When some dependency computation fails.
	 */
	public static <T, L extends List<? super List<T>>, X extends Exception> L regionTopologicalSort(final Function1<? super T, ? extends Collection<? extends T>, X> dependencyFunction, final boolean close, final Collection<? extends T> values, final L results)
	throws CollectionException, X {
		assert null != values;
		assert null != dependencyFunction;
		assert null != results;
		
		// Compute the dependencies.
		final List<T> pendingValues = new ArrayList<T>(values.size());
		final Collection<Tuple2<T, T>> dependencies = computeTopologicalSortDependencies(dependencyFunction, close, values, pendingValues, new ArrayList<Tuple2<T, T>>());
		
		// Sort the values.
		while (!pendingValues.isEmpty()) {
			// Find the leaves.
			final Set<T> leafValues = findTopologicalSortLeaves(pendingValues, dependencies);
			if (leafValues.isEmpty()) {
				throw new CollectionException("Cyclic dependencies for values " + pendingValues);
			}
			
			// Add the leaves to the result.
			results.add(extractTopologicalSortLeaves(leafValues, pendingValues, new ArrayList<T>(leafValues.size())));
			
			// Clean the dependencies.
			cleanTopologicalSortDependencies(dependencies, leafValues);
		}
		
		return results;
	}
	
	private static <T, C extends Collection<? super Tuple2<T, T>>, X extends Exception> C computeTopologicalSortDependencies(final Function1<? super T, ? extends Collection<? extends T>, X> dependencyFunction, final boolean close, final Collection<? extends T> values, final Collection<T> closedValues, final C dependencies)
	throws X {
		assert null != values;
		assert null != closedValues;
		
		if (close) {
			return computeClosedTopologicalSortDependencies(dependencyFunction, values, closedValues, dependencies);
		} else {
			closedValues.addAll(values);
			return computeTopologicalSortDependencies(dependencyFunction, values, dependencies);
		}
	}
	
	private static <T, C extends Collection<? super Tuple2<T, T>>, X extends Exception> C computeTopologicalSortDependencies(final Function1<? super T, ? extends Collection<? extends T>, X> dependencyFunction, final Collection<? extends T> values, final C dependencies)
	throws X {
		assert null != dependencyFunction;
		assert null != values;
		assert null != dependencies;
		
		for (final T value : values) {
			for (final T dependencyValue : dependencyFunction.evaluate(value)) {
				dependencies.add(new Tuple2<T, T>(value, dependencyValue));
			}
		}
		return dependencies;
	}
	
	private static <T, C extends Collection<? super Tuple2<T, T>>, X extends Exception> C computeClosedTopologicalSortDependencies(final Function1<? super T, ? extends Collection<? extends T>, X> dependencyFunction, final Collection<? extends T> values, final Collection<T> closedValues, final C dependencies)
	throws X {
		assert null != dependencyFunction;
		assert null != values;
		assert null != dependencies;
		
		final Queue<T> pendingValues = new LinkedList<T>(values);
		final Set<T> visitedValues = new HashSet<T>();
		while (!pendingValues.isEmpty()) {
			final T value = pendingValues.poll();
			if (visitedValues.add(value)) {
				// Add the value.
				closedValues.add(value);
				
				// Add the dependencies.
				for (final T dependencyValue : dependencyFunction.evaluate(value)) {
					dependencies.add(new Tuple2<T, T>(value, dependencyValue));
					pendingValues.add(dependencyValue); // Note: must queued in order to keep the closed value stable
				}
			}
		}
		return dependencies;
	}
	
	private static <T> Set<T> findTopologicalSortLeaves(final Collection<T> values, final Collection<? extends Tuple2<T, T>> dependencies) {
		assert null != values;
		assert null != dependencies;
		
		final Set<T> leafValues = new HashSet<T>(values);
		for (final Tuple2<T, T> dependency : dependencies) {
			leafValues.remove(dependency.getFirst());
		}
		return leafValues;
	}
	
	private static <T, C extends Collection<? super T>> C extractTopologicalSortLeaves(final Set<T> leafValues, final List<T> pendingValues, final C results) {
		assert null != leafValues;
		assert null != pendingValues;
		assert null != results;
		
		// Note: Pending values are iterated instead of leaf values to keep the sort stable and to handle duplicate values.
		final Iterator<T> pendingValuesIt = pendingValues.iterator();
		while (pendingValuesIt.hasNext()) {
			final T value = pendingValuesIt.next();
			if (leafValues.contains(value)) {
				results.add(value);
				pendingValuesIt.remove();
			}
		}
		return results;
	}
	
	private static <T> void cleanTopologicalSortDependencies(final Collection<? extends Tuple2<T, T>> dependencies, final Set<T> leafValues) {
		assert null != dependencies;
		assert null != leafValues;
		
		final Iterator<? extends Tuple2<T, T>> dependenciesIt = dependencies.iterator();
		while (dependenciesIt.hasNext()) {
			final Tuple2<T, T> dependency = dependenciesIt.next();
			if (leafValues.contains(dependency.getSecond())) {
				dependenciesIt.remove();
			}
		}
	}
	
	/**
	 * Computes the union of the given collections and populates the given result collection with it.
	 * <p>
	 * When the populated collection is ordered, the items of the first given collection precede the items of the second one.
	 * 
	 * @param <T> Type of the elements.
	 * @param <C> Type of the result collection.
	 * @param collection1 The first collection.
	 * @param collection2 The second collection.
	 * @param results The collection to populate with the results.
	 * @return The given result collection.
	 */
	public static <T, C extends Collection<? super T>> C union(final Collection<? extends T> collection1, final Collection<? extends T> collection2, final C results) {
		assert null != collection1;
		assert null != collection2;
		assert null != results;
		
		results.addAll(collection1);
		results.addAll(collection2);
		return results;
	}
	
	/**
	 * Computes the union of the given maps and populates the given result map with it.
	 * <p>
	 * The values of the first map have precedence over the values of the second map when their domains intersect.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map.
	 * @param map1 The first map.
	 * @param map2 The second map.
	 * @param results The map to populate with the results.
	 * @return The given result map.
	 */
	public static <K, V, M extends Map<? super K, ? super V>> M union(final Map<? extends K, ? extends V> map1, final Map<? extends K, ? extends V> map2, final M results) {
		assert null != map1;
		assert null != map2;
		assert null != results;
		
		results.putAll(map2);
		results.putAll(map1);
		return results;
	}
	
	/**
	 * Tests whether the given collections intersect. The collections instersect when they have some common value.
	 * <p>
	 * This method iterates over the first collection and tests the presence of the values within the second collection. Therefore, providing a smaller first
	 * collection and a second collection with a faster test method is more efficient.
	 * 
	 * @param <T> Type of the elements.
	 * @param collection1 The first collection.
	 * @param collection2 The second collection.
	 * @return <code>true</code> if the collections intersect, <code>false</code> otherwise.
	 */
	public static <T> boolean intersects(final Collection<? extends T> collection1, final Collection<? extends T> collection2) {
		assert null != collection1;
		assert null != collection2;
		
		for (final T value : collection1) {
			if (collection2.contains(value)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Computes the intersection of the given collections and populates the given result collection with it.
	 * <p>
	 * This method iterates over the first collection and tests the presence of the values within the second collection. Therefore, providing a smaller first
	 * collection and a second collection with a faster test method is more efficient.
	 * 
	 * @param <T> Type of the elements.
	 * @param <C> Type of the result collection.
	 * @param collection1 The first collection.
	 * @param collection2 The second collection.
	 * @param results The collection to populate with the results.
	 * @return The given result collection.
	 */
	public static <T, C extends Collection<? super T>> C intersection(final Collection<? extends T> collection1, final Collection<? extends T> collection2, final C results) {
		assert null != collection1;
		assert null != collection2;
		assert null != results;
		
		for (final T value : collection1) {
			if (collection2.contains(value)) {
				results.add(value);
			}
		}
		return results;
	}
	
	/**
	 * Computes the exclusion of given collections (first minus second) and populates the given result collection with it.
	 * <p>
	 * This method iterates over the first collection and tests the presence of the values within the second collection. Therefore, providing a smaller first
	 * collection and a second collection with a faster test method is more efficient.
	 * 
	 * @param <T> Type of the elements.
	 * @param <C> Type of the result collection.
	 * @param collection1 The first collection.
	 * @param collection2 The second collection.
	 * @param results The collection to populate with the results.
	 * @return The given result collection.
	 */
	public static <T, C extends Collection<? super T>> C exclusion(final Collection<? extends T> collection1, final Collection<? extends T> collection2, final C results) {
		assert null != collection1;
		assert null != collection2;
		assert null != results;
		
		for (final T value : collection1) {
			if (!collection2.contains(value)) {
				results.add(value);
			}
		}
		return results;
	}
	
	/**
	 * Fills the given map with bindings of the given value identified by the given keys.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map.
	 * @param map The map.
	 * @param keys The keys.
	 * @param value The value. May be <code>null</code>.
	 * @return The given map.
	 */
	public static <K, V, M extends Map<? super K, ? super V>> M fill(final M map, final Collection<? extends K> keys, final V value) {
		assert null != map;
		assert null != keys;
		
		for (final K key : keys) {
			map.put(key, value);
		}
		return map;
	}
	
	/**
	 * Gets the bindings of the given map identified by the given keys and populates the given result map with them.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map.
	 * @param map The map.
	 * @param keys The keys of the bindings.
	 * @param results The map to populate with the results.
	 * @return The given result map.
	 */
	public static <K, V, M extends Map<? super K, ? super V>> M sub(final Map<? extends K, ? extends V> map, final Set<? extends K> keys, final M results) {
		assert null != map;
		assert null != keys;
		assert null != results;
		
		for (final K key : keys) {
			if (map.containsKey(key)) {
				final V value = map.get(key);
				results.put(key, value);
			}
		}
		return results;
	}
	
	/**
	 * Gets the bindings of the given map not identified by the given keys and populates the given result map with them.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map.
	 * @param map The map.
	 * @param keys The keys of the bindings.
	 * @param results The map to populate with the results.
	 * @return The given result map.
	 */
	public static <K, V, M extends Map<? super K, ? super V>> M retain(final Map<? extends K, ? extends V> map, final Collection<? extends K> keys, final M results) {
		assert null != map;
		assert null != keys;
		assert null != results;
		
		for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
			final K key = entry.getKey();
			if (!keys.contains(key)) {
				results.put(key, entry.getValue());
			}
		}
		return results;
	}
	
	/**
	 * Groups the elements of the given lists and populates the given result list with the pairs.
	 * <p>
	 * The pairs are formed with respect of the positions of the values in the lists. The extra values of the longest list are dropped when the lists don't have
	 * the same length.
	 * 
	 * @param <T1> Type of the first values.
	 * @param <T2> Type of the second values.
	 * @param <L> Type of the result list.
	 * @param list1 The list containing the first values.
	 * @param list2 The list containing the second values.
	 * @param results The list to populate with the results.
	 * @return The given result list.
	 */
	public static <T1, T2, L extends List<? super Tuple2<T1, T2>>> L zip(final List<? extends T1> list1, final List<? extends T2> list2, final L results) {
		assert null != list1;
		assert null != list2;
		
		return zip(list1.iterator(), list2.iterator(), results);
	}
	
	/**
	 * Groups the elements provided by the given iterators and populates the given result list with the pairs.
	 * <p>
	 * The pairs are formed with respect of the order of the iterators. The extra values of the longest iterator are dropped when the given iterators don't
	 * provide the same number of elements.
	 * 
	 * @param <T1> Type of the first values.
	 * @param <T2> Type of the second values.
	 * @param <L> Type of the result list.
	 * @param iterator1 The iterator providing the first values.
	 * @param iterator2 The iterator providing the second values.
	 * @param results The list to populate with the results.
	 * @return The given result list.
	 */
	public static <T1, T2, L extends List<? super Tuple2<T1, T2>>> L zip(final Iterator<? extends T1> iterator1, final Iterator<? extends T2> iterator2, final L results) {
		assert null != iterator1;
		assert null != iterator2;
		assert null != results;
		
		while (iterator1.hasNext() && iterator2.hasNext()) {
			results.add(new Tuple2<T1, T2>(iterator1.next(), iterator2.next()));
		}
		return results;
	}
	
	/**
	 * Decomposes the pairs of the given collection and populates the given collections with the first and second values.
	 * 
	 * @param <T1> Type of the first values.
	 * @param <T2> Type of the second values.
	 * @param pairs The pairs.
	 * @param results1 The collection to populate with the first values.
	 * @param results2 The collection to populate with the second values.
	 */
	public static <T1, T2> void unzip(final Collection<? extends Tuple2<T1, T2>> pairs, final Collection<? super T1> results1, final Collection<? super T2> results2) {
		assert null != pairs;
		assert null != results1;
		assert null != results2;
		
		unzip(pairs.iterator(), results1, results2);
	}
	
	/**
	 * Decomposes the pairs provided by the given iterator and populates the given collections with the first and second values.
	 * 
	 * @param <T1> Type of the first values.
	 * @param <T2> Type of the second values.
	 * @param pairs The iterator.
	 * @param results1 The collection to populate with the first values.
	 * @param results2 The collection to populate with the second values.
	 */
	public static <T1, T2> void unzip(final Iterator<? extends Tuple2<T1, T2>> pairs, final Collection<? super T1> results1, final Collection<? super T2> results2) {
		assert null != pairs;
		assert null != results1;
		assert null != results2;
		
		while (pairs.hasNext()) {
			final Tuple2<T1, T2> value = pairs.next();
			results1.add(value.getFirst());
			results2.add(value.getSecond());
		}
	}
	
	/**
	 * Extracts the bindings of the given map and populates the given collection with them.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <C> Type of the result collection.
	 * @param map The map.
	 * @param results The collection to populate with the results.
	 * @return The given result collection.
	 */
	public static <K, V, C extends Collection<? super Tuple2<K, V>>> C bindings(final Map<? extends K, ? extends V> map, final C results) {
		assert null != map;
		assert null != results;
		
		for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
			results.add(new Tuple2<K, V>(entry.getKey(), entry.getValue()));
		}
		return results;
	}
	
	private CollectionUtils() {
		// Prevent instantiation.
	}
}
