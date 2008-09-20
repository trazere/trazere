/*
 *  Copyright 2006-2008 Julien Dufour
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

import com.trazere.util.function.ApplicationException;
import com.trazere.util.function.Filter;
import com.trazere.util.function.Filter2;
import com.trazere.util.function.Function;
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
 * The <code>CollectionUtils</code> class provides various helpers regarding the manipulation of collections and maps.
 */
public class CollectionUtils {
	/**
	 * Build a new list with the given value.
	 * <p>
	 * This method Instantiates an <code>ArrayList</code>.
	 * 
	 * @param <T> Type of the the values.
	 * @param value Value. May be <code>null</code>.
	 * @return The list.
	 * @see ArrayList
	 */
	public static <T> List<T> list(final T value) {
		// Build the list.
		final List<T> list = new ArrayList<T>(1);
		list.add(value);
		
		return list;
	}
	
	/**
	 * Build a list with the given values.
	 * <p>
	 * This method Instantiates an <code>ArrayList</code>.
	 * 
	 * @param <T> Type of the the values.
	 * @param value1 First value. May be <code>null</code>.
	 * @param value2 Second value. May be <code>null</code>.
	 * @return The list.
	 * @see ArrayList
	 */
	public static <T> List<T> list(final T value1, final T value2) {
		// Build the list.
		final List<T> list = new ArrayList<T>(2);
		list.add(value1);
		list.add(value2);
		
		return list;
	}
	
	/**
	 * Build a list with the given values.
	 * <p>
	 * This method Instantiates an <code>ArrayList</code>.
	 * 
	 * @param <T> Type of the the values.
	 * @param value1 First value. May be <code>null</code>.
	 * @param value2 Second value. May be <code>null</code>.
	 * @param value3 Second value. May be <code>null</code>.
	 * @return The list.
	 * @see ArrayList
	 */
	public static <T> List<T> list(final T value1, final T value2, final T value3) {
		// Build the list.
		final List<T> list = new ArrayList<T>(3);
		list.add(value1);
		list.add(value2);
		list.add(value3);
		
		return list;
	}
	
	/**
	 * Build a list with the given values.
	 * <p>
	 * This method Instantiates an <code>ArrayList</code>.
	 * 
	 * @param <T> Type of the the values.
	 * @param values Array containing the values.
	 * @return The set.
	 */
	public static <T> List<T> listN(final T[] values) {
		assert null != values;
		
		// Build the list.
		final List<T> list = new ArrayList<T>(values.length);
		for (final T value : values) {
			list.add(value);
		}
		return list;
	}
	
	/**
	 * Build a set with the given value.
	 * <p>
	 * This method Instantiates a <code>HashSet</code>.
	 * 
	 * @param <T> Type of the the values.
	 * @param value Value. May be <code>null</code>.
	 * @return The set.
	 * @see HashSet
	 */
	public static <T> Set<T> set(final T value) {
		// Build the set.
		final Set<T> set = new HashSet<T>(1);
		set.add(value);
		
		return set;
	}
	
	/**
	 * Build a set with the given values.
	 * <p>
	 * This method Instantiates a <code>HashSet</code>.
	 * 
	 * @param <T> Type of the the values.
	 * @param value1 First value. May be <code>null</code>.
	 * @param value2 Second value. May be <code>null</code>.
	 * @return The set.
	 * @see HashSet
	 */
	public static <T> Set<T> set(final T value1, final T value2) {
		// Build the set.
		final Set<T> set = new HashSet<T>(2);
		set.add(value1);
		set.add(value2);
		
		return set;
	}
	
	/**
	 * Build a set with the given values.
	 * <p>
	 * This method Instantiates a <code>HashSet</code>.
	 * 
	 * @param <T> Type of the the values.
	 * @param value1 First value. May be <code>null</code>.
	 * @param value2 Second value. May be <code>null</code>.
	 * @param value3 Third value. May be <code>null</code>.
	 * @return The set.
	 * @see HashSet
	 */
	public static <T> Set<T> set(final T value1, final T value2, final T value3) {
		// Build the set.
		final Set<T> set = new HashSet<T>(3);
		set.add(value1);
		set.add(value2);
		set.add(value3);
		
		return set;
	}
	
	/**
	 * Build a set with the given values.
	 * <p>
	 * This method Instantiates a <code>HashSet</code>.
	 * 
	 * @param <T> Type of the the values.
	 * @param values Array containing the values.
	 * @return The set.
	 * @see HashSet
	 */
	public static <T> Set<T> setN(final T[] values) {
		assert null != values;
		
		// Build the set.
		final Set<T> set = new HashSet<T>(values.length);
		for (final T value : values) {
			set.add(value);
		}
		return set;
	}
	
	/**
	 * Build a map with the binding of the given key and value.
	 * <p>
	 * This method Instantiates a <code>HashMap</code>.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param key Key of the binding. May be <code>null</code>.
	 * @param value Value of the binding. May be <code>null</code>.
	 * @return The map.
	 * @see HashMap
	 */
	public static <K, V> Map<K, V> map(final K key, final V value) {
		// Build the map.
		final Map<K, V> result = new HashMap<K, V>();
		result.put(key, value);
		
		return result;
	}
	
	/**
	 * Build a map with the bindings of the given keys and values.
	 * <p>
	 * This method Instantiates a <code>HashMap</code>.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param key1 Key of the first binding. May be <code>null</code>.
	 * @param value1 Value of the first binding. May be <code>null</code>.
	 * @param key2 Key of the second binding. May be <code>null</code>.
	 * @param value2 Value of the second binding. May be <code>null</code>.
	 * @return The map.
	 * @see HashMap
	 */
	public static <K, V> Map<K, V> map(final K key1, final V value1, final K key2, final V value2) {
		// Build the map.
		final Map<K, V> result = new HashMap<K, V>();
		result.put(key2, value2);
		result.put(key1, value1);
		
		return result;
	}
	
	private static final Iterator<?> EMPTY_ITERATOR = new Iterator<Object>() {
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
	 * @return The iterator.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Iterator<T> iterator() {
		return (Iterator<T>) EMPTY_ITERATOR;
	}
	
	/**
	 * Build an iterator over the given value.
	 * 
	 * @param <T> Type of the the values.
	 * @param value Value to iterate. May be <code>null</code>.
	 * @return The iterator.
	 */
	public static <T> Iterator<T> iterator(final T value) {
		// Build the iterator.
		return new Iterator<T>() {
			protected boolean _iterated = false;
			
			public boolean hasNext() {
				return !_iterated;
			}
			
			public T next() {
				if (!_iterated) {
					_iterated = true;
					return value;
				} else {
					throw new NoSuchElementException();
				}
			}
			
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	/**
	 * Build an iterator over the given values.
	 * 
	 * @param <T> Type of the the values.
	 * @param values Values to iterate.
	 * @return The iterator.
	 */
	public static <T> Iterator<T> iteratorN(final T[] values) {
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
	 * Get an objet from the given collection.
	 * 
	 * @param <T> Type of the elements.
	 * @param collection Collection to read.
	 * @return Any object from the collection, or <code>null</code> when the collection is empty.
	 */
	public static <T> Maybe<T> any(final Collection<T> collection) {
		assert null != collection;
		
		// Read.
		final Iterator<T> iterator = collection.iterator();
		return iterator.hasNext() ? Maybe.some(iterator.next()) : Maybe.<T>none();
	}
	
	/**
	 * Get an entry from the given map.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to read.
	 * @return Any entry from the map, or <code>null</code> if the map is empty.
	 */
	public static <K, V> Maybe<Map.Entry<K, V>> any(final Map<K, V> map) {
		assert null != map;
		
		// Read.
		final Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
		return iterator.hasNext() ? Maybe.some(iterator.next()) : Maybe.<Map.Entry<K, V>>none();
	}
	
	/**
	 * Get the last value from the given list.
	 * 
	 * @param <T> Type of the elements.
	 * @param list List to read.
	 * @return The last value, or <code>null</code> when the list is empty.
	 */
	public static <T> T last(final List<T> list) {
		assert null != list;
		
		// Read.
		final int size = list.size();
		return size > 0 ? list.get(size - 1) : null;
	}
	
	/**
	 * Get the value identified by the given key in the given map.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to read.
	 * @param key Key identifying the value to get. May be <code>null</code>.
	 * @return The value identified by the key if any, or the default value.
	 */
	public static <K, V> Maybe<V> get(final Map<K, V> map, final K key) {
		assert null != map;
		
		// Get.
		return map.containsKey(key) ? Maybe.some(map.get(key)) : Maybe.<V>none();
	}
	
	/**
	 * Get the value identified by the given key in the given map using the given default value.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to read.
	 * @param key Key identifying the value to get. May be <code>null</code>.
	 * @param defaultValue Default value. May be <code>null</code>.
	 * @return The value identified by the key if any, or the default value.
	 */
	public static <K, V> V get(final Map<K, V> map, final K key, final V defaultValue) {
		assert null != map;
		
		// Get.
		return map.containsKey(key) ? map.get(key) : defaultValue;
	}
	
	/**
	 * Remove an objet from the given collection.
	 * 
	 * @param <T> Type of the elements.
	 * @param collection Collection to modify.
	 * @return The remove object, or <code>null</code> when the collection is empty.
	 */
	public static <T> T removeAny(final Collection<T> collection) {
		assert null != collection;
		
		// Remove.
		final Iterator<T> iterator = collection.iterator();
		if (iterator.hasNext()) {
			final T object = iterator.next();
			iterator.remove();
			return object;
		} else {
			return null;
		}
	}
	
	/**
	 * Reverse the given list.
	 * <p>
	 * This method does modify the given list.
	 * 
	 * @param <T> Type of the elements.
	 * @param <L> Type of the list.
	 * @param list List to reverse.
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
	 * @param list List to sort.
	 * @param comparator Comparator to use.
	 * @return The given modified list.
	 */
	public static <T, L extends List<T>> L sort(final L list, final Comparator<? super T> comparator) {
		assert null != list;
		
		// Sort.
		Collections.sort(list, comparator);
		return list;
	}
	
	/**
	 * Sort the given values topologically and populate the given list with the results.
	 * <p>
	 * The dependencies between the values are computed using the given function. This function must compute the values whose the argument value depends on. The
	 * computed value must belong to the values to sort.
	 * <p>
	 * This method places the dependencies before the value which depend on them. The sort is stable and fails when the dependencies form a cyclic graph.
	 * 
	 * @param <T> Type of the values.
	 * @param <L> Type of the populated list.
	 * @param values Values to sort. The given collection is not modified by the method.
	 * @param dependencyFunction Function which computes the dependencies.
	 * @param results List to populate with the results.
	 * @return The populated list.
	 * @throws CollectionException When some dependencies are invalid or cyclic.
	 */
	public static <T, L extends List<T>> L topologicalSort(final Collection<? extends T> values, final Function<? super T, ? extends Collection<? extends T>> dependencyFunction, final L results)
	throws CollectionException {
		assert null != values;
		assert null != dependencyFunction;
		assert null != results;
		
		// Compute the dependencies.
		final Collection<Tuple2<T, T>> dependencies = new ArrayList<Tuple2<T, T>>();
		for (final T value : values) {
			try {
				for (final T dependencyValue : dependencyFunction.apply(value)) {
					if (values.contains(dependencyValue)) {
						dependencies.add(new Tuple2<T, T>(value, dependencyValue));
					} else {
						throw new CollectionException("Invalid dependency " + dependencyValue + " for value " + value);
					}
				}
			} catch (final ApplicationException exception) {
				throw new CollectionException("Failed computing dependencies for value " + value, exception);
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
	 * Filter the content of the given collection using the given filter.
	 * <p>
	 * This method does modify the given collection.
	 * 
	 * @param <T> Type of the elements.
	 * @param <C> Type of the collection.
	 * @param collection Collection to filter.
	 * @param filter Filter to use.
	 * @return The given modified collection.
	 */
	public static <T, C extends Collection<T>> C filter(final C collection, final Filter<? super T> filter) {
		assert null != collection;
		assert null != filter;
		
		// Filter.
		final Iterator<T> values_ = collection.iterator();
		while (values_.hasNext()) {
			final T value = values_.next();
			if (!filter.filter(value)) {
				values_.remove();
			}
		}
		return collection;
	}
	
	/**
	 * Filter the content of the given map using the given filter.
	 * <p>
	 * This method does modify the given map.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map.
	 * @param map Map to filter.
	 * @param filter Filter to use.
	 * @return The given modified map.
	 */
	public static <K, V, M extends Map<K, V>> M filter(final M map, final Filter2<? super K, ? super V> filter) {
		assert null != map;
		assert null != filter;
		
		// Filter.
		final Iterator<Map.Entry<K, V>> entries = map.entrySet().iterator();
		while (entries.hasNext()) {
			final Map.Entry<K, V> entry = entries.next();
			if (!filter.filter(entry.getKey(), entry.getValue())) {
				entries.remove();
			}
		}
		return map;
	}
	
	/**
	 * Build the union of the given collections and populate the given result collection with it.
	 * <p>
	 * When the populated collection is ordered, the items of the first given collection precede the items of the second one.
	 * 
	 * @param <T> Type of the elements.
	 * @param <C> Type of the populated collection.
	 * @param collection1 First collection.
	 * @param collection2 Second collection.
	 * @param results The populated collection.
	 * @return The populated collection.
	 */
	public static <T, C extends Collection<T>> C union(final Collection<? extends T> collection1, final Collection<? extends T> collection2, final C results) {
		assert null != collection1;
		assert null != collection2;
		assert null != results;
		
		// Build the union.
		results.addAll(collection1);
		results.addAll(collection2);
		return results;
	}
	
	/**
	 * Build the union of the given maps and populate the given result map with it.
	 * <p>
	 * The values of the first map have precedence over the values of the second map when their domains intersect.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the populated map.
	 * @param map1 First map.
	 * @param map2 Second map.
	 * @param results Map to populate with the results.
	 * @return The populated map.
	 */
	public static <K, V, M extends Map<K, V>> M union(final Map<? extends K, ? extends V> map1, final Map<? extends K, ? extends V> map2, final M results) {
		assert null != map1;
		assert null != map2;
		assert null != results;
		
		// Build the union.
		results.putAll(map2);
		results.putAll(map1);
		return results;
	}
	
	/**
	 * Test wether the given collections intersect. Collections instersect when they have some common value.
	 * <p>
	 * This method iterates over the first collection and tests the presence of the values within the second collection. Therefore, providing a smaller first
	 * collection and a second collection with a faster test method is more efficient.
	 * 
	 * @param <T> Type of the elements.
	 * @param collection1 First collection.
	 * @param collection2 Second collection.
	 * @return <code>true</code> if the given collections intersect, <code>false</code> otherwise.
	 */
	public static <T> boolean intersects(final Collection<? extends T> collection1, final Collection<? extends T> collection2) {
		assert null != collection1;
		assert null != collection2;
		
		// Test.
		for (final Object value : collection1) {
			if (collection2.contains(value)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Build the intersection of the given collections and populate the given result collection with it.
	 * <p>
	 * This method iterates over the first collection and tests the presence of the values within the second collection. Therefore, providing a smaller first
	 * collection and a second collection with a faster test method is more efficient.
	 * 
	 * @param <T> Type of the elements.
	 * @param <C> Type of the populated collection.
	 * @param collection1 First collection.
	 * @param collection2 Second collection.
	 * @param results Collection to populate with the results.
	 * @return The populated collection.
	 */
	public static <T, C extends Collection<T>> C intersection(final Collection<? extends T> collection1, final Collection<? extends T> collection2, final C results) {
		assert null != collection1;
		assert null != collection2;
		assert null != results;
		
		// Build the intersection.
		for (final T value : collection1) {
			if (collection2.contains(value)) {
				results.add(value);
			}
		}
		return results;
	}
	
	/**
	 * Build the exclusion of given collections (first minus second) and populate the given results collection with it.
	 * <p>
	 * This method iterates over the first collection and tests the presence of the values within the second collection. Therefore, providing a smaller first
	 * collection and a second collection with a faster test method is more efficient.
	 * 
	 * @param <T> Type of the elements.
	 * @param <C> Type of the populated collection.
	 * @param collection1 First collection.
	 * @param collection2 Second collection.
	 * @param results The collection to populate with the results.
	 * @return The populated collection.
	 */
	public static <T, C extends Collection<T>> C exclusion(final Collection<? extends T> collection1, final Collection<? extends T> collection2, final C results) {
		// Checks
		assert null != collection1;
		assert null != collection2;
		assert null != results;
		
		// Build the exclusion.
		for (final T value : collection1) {
			if (!collection2.contains(value)) {
				results.add(value);
			}
		}
		return results;
	}
	
	/**
	 * Get the bindings of the given map with the given keys and populate the given result map with them.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the populated map.
	 * @param map Map to copy.
	 * @param keys Keys of the bindings to retain.
	 * @param results Map to populate with the bindings.
	 * @return The populated map.
	 */
	public static <K, V, M extends Map<K, V>> M subMap(final Map<? extends K, ? extends V> map, final Set<? extends K> keys, final M results) {
		assert null != map;
		assert null != keys;
		assert null != results;
		
		// Build the map.
		for (final K key : keys) {
			if (map.containsKey(key)) {
				final V value = map.get(key);
				results.put(key, value);
			}
		}
		return results;
	}
	
	/**
	 * Get the bindings of the given map whose keys do not belong to the given keys and populate the given result map with them.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the populated map.
	 * @param map Map to copy.
	 * @param keys Keys of the binding to exclude.
	 * @param results Map to populate with the results.
	 * @return The populated map.
	 */
	public static <K, V, M extends Map<K, V>> M retainMap(final Map<? extends K, ? extends V> map, final Collection<? extends K> keys, final M results) {
		assert null != map;
		assert null != keys;
		assert null != results;
		
		// Build the exclusion.
		for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
			final K key = entry.getKey();
			if (!keys.contains(key)) {
				results.put(key, entry.getValue());
			}
		}
		return results;
	}
	
	private CollectionUtils() {
		// Prevent instantiation.
	}
}
