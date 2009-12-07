/*
 *  Copyright 2006-2009 Julien Dufour
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
import com.trazere.util.type.Maybe;
import com.trazere.util.type.Tuple2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * The {@link CollectionUtils} class provides various helpers regarding collections and maps.
 */
public class CollectionUtils {
	/**
	 * Build a new list containing the given value.
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
	 * Build a new list containing the given values.
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
	 * Build a new list containing the given values.
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
	 * Build a new list containing the given values.
	 * <p>
	 * This method instantiates an {@link ArrayList}.
	 * 
	 * @param <T> Type of the the values.
	 * @param values The values. May be <code>null</code>.
	 * @return The built list.
	 */
	public static <T> List<T> listN(final T... values) {
		assert null != values;
		
		// Build the list.
		final List<T> list = new ArrayList<T>(values.length);
		for (final T value : values) {
			list.add(value);
		}
		return list;
	}
	
	/**
	 * Build a new list containing the given value.
	 * <p>
	 * This method instantiates an {@link ArrayList}.
	 * 
	 * @param <T> Type of the the value.
	 * @param value The value.
	 * @return The built list.
	 */
	public static <T> List<T> list(final Maybe<T> value) {
		assert null != value;
		
		// Build the list.
		final List<T> list = new ArrayList<T>();
		if (value.isSome()) {
			list.add(value.asSome().getValue());
		}
		return list;
	}
	
	/**
	 * Build a set containing the given value.
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
	 * Build a set containing the given values.
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
	 * Build a set containing the given values.
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
	 * Build a set containing the given values.
	 * <p>
	 * This method instantiates a {@link HashSet}.
	 * 
	 * @param <T> Type of the the values.
	 * @param values The values. May be <code>null</code>.
	 * @return The built set.
	 */
	public static <T> Set<T> setN(final T... values) {
		assert null != values;
		
		// Build the set.
		final Set<T> set = new HashSet<T>(values.length);
		for (final T value : values) {
			set.add(value);
		}
		return set;
	}
	
	/**
	 * Build a set containing the given value.
	 * <p>
	 * This method instantiates a {@link HashSet}.
	 * 
	 * @param <T> Type of the the value.
	 * @param value The value.
	 * @return The built set.
	 */
	public static <T> Set<T> set(final Maybe<T> value) {
		assert null != value;
		
		// Build the set.
		final Set<T> set = new HashSet<T>();
		if (value.isSome()) {
			set.add(value.asSome().getValue());
		}
		return set;
	}
	
	/**
	 * Build a map containing the binding of the given key and value.
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
	 * Build a map containing the bindings of the given keys and values.
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
	 * Build an iterator over no values.
	 * 
	 * @param <T> Type of the the values.
	 * @return The built iterator.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Iterator<T> iterator() {
		return (Iterator<T>) _EMPTY_ITERATOR;
	}
	
	/**
	 * Build an iterator over the given values.
	 * 
	 * @param <T> Type of the the values.
	 * @param values The values.
	 * @return The built iterator.
	 */
	public static <T> Iterator<T> iterator(final T... values) {
		assert null != values;
		
		// Build the iterator.
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
	 * Get an element from the given collection.
	 * 
	 * @param <T> Type of the elements.
	 * @param collection The collection.
	 * @return Any element of the collection.
	 */
	public static <T> Maybe<T> any(final Collection<T> collection) {
		assert null != collection;
		
		// Get.
		final Iterator<T> iterator = collection.iterator();
		return iterator.hasNext() ? Maybe.some(iterator.next()) : Maybe.<T>none();
	}
	
	/**
	 * Get a binding from the given map.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map The map.
	 * @return Any binding of the map.
	 */
	public static <K, V> Maybe<Map.Entry<K, V>> any(final Map<K, V> map) {
		assert null != map;
		
		// Get.
		final Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
		return iterator.hasNext() ? Maybe.some(iterator.next()) : Maybe.<Map.Entry<K, V>>none();
	}
	
	/**
	 * Get the element of the given list corresponding to the given index.
	 * 
	 * @param <T> Type of the elements.
	 * @param list The list.
	 * @param index The index.
	 * @return The first element.
	 */
	public static <T> Maybe<T> get(final List<T> list, final int index) {
		assert null != list;
		
		// Get.
		return index < list.size() ? Maybe.some(list.get(index)) : Maybe.<T>none();
	}
	
	/**
	 * Get the first element of the given list.
	 * 
	 * @param <T> Type of the elements.
	 * @param list The list.
	 * @return The first element.
	 */
	public static <T> Maybe<T> first(final List<T> list) {
		return get(list, 0);
	}
	
	/**
	 * Get the last element of the given list.
	 * 
	 * @param <T> Type of the elements.
	 * @param list The list.
	 * @return The last element.
	 */
	public static <T> Maybe<T> last(final List<T> list) {
		return get(list, list.size() - 1);
	}
	
	/**
	 * Add the given value to the given collection.
	 * 
	 * @param <T> Type of the values.
	 * @param collection The collection.
	 * @param value The value.
	 */
	public static <T> void add(final Collection<? super T> collection, final Maybe<? extends T> value) {
		assert null != collection;
		assert null != value;
		
		// Add.
		if (value.isSome()) {
			collection.add(value.asSome().getValue());
		}
	}
	
	/**
	 * Remove an element from the given collection.
	 * <p>
	 * This method does modify the given collection.
	 * 
	 * @param <T> Type of the elements.
	 * @param collection The collection.
	 * @return The removed element.
	 */
	public static <T> Maybe<T> removeAny(final Collection<? extends T> collection) {
		assert null != collection;
		
		// Remove.
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
	 * Get the value identified by the given key in the given map.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map The map.
	 * @param key The key. May be <code>null</code>.
	 * @return The value identified by the key.
	 */
	public static <K, V> Maybe<V> get(final Map<? super K, ? extends V> map, final K key) {
		assert null != map;
		
		// Get.
		return map.containsKey(key) ? Maybe.<V>some(map.get(key)) : Maybe.<V>none();
	}
	
	/**
	 * Get the value identified by the given key in the given map.
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
		
		// Get.
		return map.containsKey(key) ? map.get(key) : defaultValue;
	}
	
	/**
	 * Add the given binding to the given map.
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
		
		// Add.
		if (value.isSome()) {
			map.put(key, value.asSome().getValue());
		}
	}
	
	/**
	 * Reverse the given list.
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
		
		// Build the list.
		Collections.reverse(list);
		return list;
	}
	
	/**
	 * Sort the given list using the given comparator.
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
		
		// Sort.
		Collections.sort(list);
		return list;
	}
	
	/**
	 * Sort the given list using the given comparator.
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
		
		// Sort.
		Collections.sort(list, comparator);
		return list;
	}
	
	/**
	 * Sort the given values topologically and populate the given list with the sorted values.
	 * <p>
	 * The dependencies between the values are computed using the given function. This function must compute the values whose the argument value depends on. The
	 * computed values must belong to the values to sort.
	 * <p>
	 * This method places the dependencies before the value which depend on them. The sort is stable and fails when the dependencies form a cyclic graph.
	 * 
	 * @param <T> Type of the values.
	 * @param <L> Type of the result list.
	 * @param <X> Type of the exceptions.
	 * @param values The values.
	 * @param dependencyFunction The function computing the dependencies.
	 * @param results The list to populate with the results.
	 * @return The given result list.
	 * @throws CollectionException When some computed dependency value does not belong to the values to sort.
	 * @throws CollectionException When there is a cycle in the dependencies.
	 * @throws X When some dependency computation fails.
	 */
	public static <T, L extends List<? super T>, X extends Exception> L topologicalSort(final Collection<? extends T> values, final Function1<? super T, ? extends Collection<? extends T>, X> dependencyFunction, final L results)
	throws CollectionException, X {
		assert null != values;
		assert null != dependencyFunction;
		assert null != results;
		
		// Compute the dependencies.
		final Collection<Tuple2<T, T>> dependencies = new ArrayList<Tuple2<T, T>>();
		for (final T value : values) {
			for (final T dependencyValue : dependencyFunction.evaluate(value)) {
				if (values.contains(dependencyValue)) {
					dependencies.add(new Tuple2<T, T>(value, dependencyValue));
				} else {
					throw new CollectionException("Invalid dependency " + dependencyValue + " for value " + value);
				}
			}
		}
		
		// Sort the values.
		final Collection<T> pendingValues = new ArrayList<T>(values);
		while (!pendingValues.isEmpty()) {
			// Find the leaves.
			final Set<T> leafValues = new HashSet<T>(pendingValues);
			for (final Tuple2<T, T> dependency : dependencies) {
				leafValues.remove(dependency.getFirst());
			}
			
			if (leafValues.isEmpty()) {
				throw new CollectionException("Cyclic dependencies for values " + pendingValues);
			}
			
			// Add the leaves to the result.
			// Pending values are iterated instead of leaf values to keep the sort stable.
			final Iterator<T> pendingValuesIt = pendingValues.iterator();
			while (pendingValuesIt.hasNext()) {
				final T value = pendingValuesIt.next();
				if (leafValues.contains(value)) {
					results.add(value);
					pendingValuesIt.remove();
				}
			}
			
			// Clean the dependencies.
			final Iterator<Tuple2<T, T>> dependenciesIt = dependencies.iterator();
			while (dependenciesIt.hasNext()) {
				final Tuple2<T, T> dependency = dependenciesIt.next();
				if (leafValues.contains(dependency.getSecond())) {
					dependenciesIt.remove();
				}
			}
		}
		
		return results;
	}
	
	/**
	 * Compute the union of the given collections and populate the given result collection with it.
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
		
		// Compute the union.
		results.addAll(collection1);
		results.addAll(collection2);
		return results;
	}
	
	/**
	 * Compute the union of the given maps and populate the given result map with it.
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
		
		// Compute the union.
		results.putAll(map2);
		results.putAll(map1);
		return results;
	}
	
	/**
	 * Test whether the given collections intersect. The collections instersect when they have some common value.
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
		
		// Test.
		for (final T value : collection1) {
			if (collection2.contains(value)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Compute the intersection of the given collections and populate the given result collection with it.
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
		
		// Compute the intersection.
		for (final T value : collection1) {
			if (collection2.contains(value)) {
				results.add(value);
			}
		}
		return results;
	}
	
	/**
	 * Compute the exclusion of given collections (first minus second) and populate the given result collection with it.
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
		
		// Compute the exclusion.
		for (final T value : collection1) {
			if (!collection2.contains(value)) {
				results.add(value);
			}
		}
		return results;
	}
	
	/**
	 * Fill the given map with bindings of the given value identified by the given keys.
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
		
		// Fill.
		for (final K key : keys) {
			map.put(key, value);
		}
		return map;
	}
	
	/**
	 * Get the bindings of the given map identified by the given keys and populate the given result map with them.
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
		
		// Compute the sub map.
		for (final K key : keys) {
			if (map.containsKey(key)) {
				final V value = map.get(key);
				results.put(key, value);
			}
		}
		return results;
	}
	
	/**
	 * Get the bindings of the given map not identified by the given keys and populate the given result map with them.
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
		
		// Compute the exclusion map.
		for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
			final K key = entry.getKey();
			if (!keys.contains(key)) {
				results.put(key, entry.getValue());
			}
		}
		return results;
	}
	
	/**
	 * Group the elements of the given lists and populate the given result list with the pairs.
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
		
		// Zip.
		return zip(list1.iterator(), list2.iterator(), results);
	}
	
	/**
	 * Group the elements provided by the given iterators and populate the given result list with the pairs.
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
		
		// Zip.
		while (iterator1.hasNext() && iterator2.hasNext()) {
			results.add(new Tuple2<T1, T2>(iterator1.next(), iterator2.next()));
		}
		return results;
	}
	
	/**
	 * Decompose the pairs of the given collection and populate the given collections with the first and second values.
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
		
		// Unzip.
		unzip(pairs.iterator(), results1, results2);
	}
	
	/**
	 * Decompose the pairs provided by the given iterator and populate the given collections with the first and second values.
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
		
		// Unzip.
		while (pairs.hasNext()) {
			final Tuple2<T1, T2> value = pairs.next();
			results1.add(value.getFirst());
			results2.add(value.getSecond());
		}
	}
	
	/**
	 * Extract the bindings of the given map and populate the given collection with them.
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
		
		// Get.
		for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
			results.add(new Tuple2<K, V>(entry.getKey(), entry.getValue()));
		}
		return results;
	}
	
	private CollectionUtils() {
		// Prevent instantiation.
	}
}
