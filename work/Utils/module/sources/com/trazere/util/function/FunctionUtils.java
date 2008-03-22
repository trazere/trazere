/*
 *  Copyright 2008 Julien Dufour
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
package com.trazere.util.function;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.trazere.util.Assert;
import com.trazere.util.collection.CollectionFactory;
import com.trazere.util.collection.MapFactory;
import com.trazere.util.type.Tuple2;

/**
 * The <code>FunctionUtils</code> provides various helpers regarding the manipulation of filters and functions.
 * 
 * @see Filter
 * @see Filter2
 * @see Function
 * @see Function2
 * @see Procedure
 */
public class FunctionUtils {
	/**
	 * Filter the given values with the given filter function.
	 * 
	 * @param <T> Type of the values to filter.
	 * @param <C> Type of the result collection to build.
	 * @param values Values to filter.
	 * @param filter Filter function to use.
	 * @param factory Factory to use to build the result collection.
	 * @return The accepted values.
	 * @throws ApplicationException When some function application fails.
	 * @see #filter(Collection, Filter, Collection)
	 */
	public static <T, C extends Collection<? super T>> C filter(final Collection<? extends T> values, final Filter<? super T> filter, final CollectionFactory<? super T, ? extends C> factory)
	throws ApplicationException {
		Assert.notNull(factory);
		
		// Filter.
		return filter(values, filter, factory.build());
	}
	
	/**
	 * Filter the given values with the given filter function and populate the given result collection with them.
	 * <p>
	 * This method applies the filter function to every value of the given collection and returns a collection of the accepted values.
	 * 
	 * @param <T> Type of the values to filter.
	 * @param <C> Type of the result collection to build.
	 * @param values Values to filter.
	 * @param filter Filter function to use.
	 * @param results The collection to populate with the results.
	 * @return The populated result collection.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <T, C extends Collection<? super T>> C filter(final Collection<? extends T> values, final Filter<? super T> filter, final C results)
	throws ApplicationException {
		Assert.notNull(values);
		Assert.notNull(filter);
		Assert.notNull(results);
		
		// Filter.
		for (final T value : values) {
			if (filter.filter(value)) {
				results.add(value);
			}
		}
		return results;
	}
	
	/**
	 * Filter the given bindings with the given filter function.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map to build.
	 * @param bindings Bindings to filter.
	 * @param filter Filter function to use.
	 * @param factory Factory to use to build the result map.
	 * @return The accepted bindings.
	 * @throws ApplicationException When some function application fails.
	 * @see #filter(Map, Filter2, Map)
	 */
	public static <K, V, M extends Map<K, V>> M filter(final Map<? extends K, ? extends V> bindings, final Filter2<? super K, ? super V> filter, final MapFactory<? super K, ? super V, ? extends M> factory)
	throws ApplicationException {
		Assert.notNull(factory);
		
		// Filter.
		return filter(bindings, filter, factory.build());
	}
	
	/**
	 * Filter the given bindings with the given filter function and populate the given result map with them.
	 * <p>
	 * This method applies the filter function to every binding of the given map and returns a map containing the accepted bindings. The keys and the values of
	 * the map are respectively passed as first and second arguments to the filter function.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map to build.
	 * @param bindings Bindings to filter.
	 * @param filter Filter function to use.
	 * @param results The map to populate with the results.
	 * @return The populated result map.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <K, V, M extends Map<K, V>> M filter(final Map<? extends K, ? extends V> bindings, final Filter2<? super K, ? super V> filter, final M results)
	throws ApplicationException {
		Assert.notNull(bindings);
		Assert.notNull(filter);
		Assert.notNull(results);
		
		// Filter.
		for (final Map.Entry<? extends K, ? extends V> entry : bindings.entrySet()) {
			final K key = entry.getKey();
			final V value = entry.getValue();
			if (filter.filter(key, value)) {
				results.put(key, value);
			}
		}
		return results;
	}
	
	/**
	 * Filter the given bindings with the given filter function.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <S> Type of the result set to build.
	 * @param bindings Bindings to filter.
	 * @param filter Filter function to use.
	 * @param factory Factory to use to build the result set.
	 * @return The accepted keys.
	 * @throws ApplicationException When some function application fails.
	 * @see #filterKeys(Map, Filter2, Set)
	 */
	public static <K, V, S extends Set<K>> S filterKeys(final Map<? extends K, ? extends V> bindings, final Filter2<? super K, ? super V> filter, final CollectionFactory<? super K, ? extends S> factory)
	throws ApplicationException {
		Assert.notNull(factory);
		
		// Filter.
		return filterKeys(bindings, filter, factory.build());
	}
	
	/**
	 * Filter the given bindings with the given filter function and populate the given result set with their keys.
	 * <p>
	 * This method applies the filter function to every binding of the given map and returns a set of the keys of the accepted bindings. The keys and the values
	 * of the map are respectively passed as first and second arguments to filter function.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <S> Type of the result set to build.
	 * @param bindings Bindings to filter.
	 * @param filter Filter function to use.
	 * @param results The set to populate with the results.
	 * @return The populated result set.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <K, V, S extends Set<K>> S filterKeys(final Map<? extends K, ? extends V> bindings, final Filter2<? super K, ? super V> filter, final S results)
	throws ApplicationException {
		Assert.notNull(bindings);
		Assert.notNull(filter);
		Assert.notNull(results);
		
		// Filter.
		for (final Map.Entry<? extends K, ? extends V> entry : bindings.entrySet()) {
			final K key = entry.getKey();
			final V value = entry.getValue();
			if (filter.filter(key, value)) {
				results.add(key);
			}
		}
		return results;
	}
	
	/**
	 * Filter the given bindings with the given filter function.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <C> Type of the result collection to build.
	 * @param bindings Bindings to filter.
	 * @param filter Filter function to use.
	 * @param factory Factory to use to build the result collection.
	 * @return The accepted values.
	 * @throws ApplicationException When some function application fails.
	 * @see #filterValues(Map, Filter2, Collection)
	 */
	public static <K, V, C extends Collection<V>> C filterValues(final Map<? extends K, ? extends V> bindings, final Filter2<? super K, ? super V> filter, final CollectionFactory<? super V, ? extends C> factory)
	throws ApplicationException {
		Assert.notNull(factory);
		
		// Filter.
		return filterValues(bindings, filter, factory.build());
	}
	
	/**
	 * Filter the given bindings with the given filter function and populate the given result collection with their values.
	 * <p>
	 * This method applies the filter function to every binding of the given map and returns a collection of the values of the accepted bindings. The keys and
	 * the values of the map are respectively passed as first and second arguments to the filter function.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <C> Type of the result collection to build.
	 * @param bindings Bindings to filter.
	 * @param filter Filter function to use.
	 * @param results The collection to populate with the results.
	 * @return The populated result collection.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <K, V, C extends Collection<V>> C filterValues(final Map<? extends K, ? extends V> bindings, final Filter2<? super K, ? super V> filter, final C results)
	throws ApplicationException {
		Assert.notNull(bindings);
		Assert.notNull(filter);
		Assert.notNull(results);
		
		// Filter.
		for (final Map.Entry<? extends K, ? extends V> entry : bindings.entrySet()) {
			final K key = entry.getKey();
			final V value = entry.getValue();
			if (filter.filter(key, value)) {
				results.add(value);
			}
		}
		return results;
	}
	
	/**
	 * Count the number of values of the given collection accepted by the given filter.
	 * 
	 * @param <T> Type of the values.
	 * @param values Values to count.
	 * @param filter Filter function to use.
	 * @return The number of accepted values.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <T> int count(final Collection<? extends T> values, final Filter<? super T> filter)
	throws ApplicationException {
		Assert.notNull(values);
		Assert.notNull(filter);
		
		// Count.
		int count = 0;
		for (final T value : values) {
			if (filter.filter(value)) {
				count += 1;
			}
		}
		return count;
	}
	
	/**
	 * Count the number of bindings of the given map accepted by the given filter.
	 * <p>
	 * This method applies the filter function to every binding of the given map. The keys and the values of the map are respectively passed as first and second
	 * arguments to the filter function.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to count.
	 * @param filter Filter function to use.
	 * @return The number of accepted bindings.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <K, V> int count(final Map<? extends K, ? extends V> map, final Filter2<? super K, ? super V> filter)
	throws ApplicationException {
		Assert.notNull(map);
		Assert.notNull(filter);
		
		// Count.
		int count = 0;
		for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
			final K key = entry.getKey();
			final V value = entry.getValue();
			if (filter.filter(key, value)) {
				count += 1;
			}
		}
		return count;
	}
	
	/**
	 * Test wether any of the given values is accepted by the given filter function.
	 * 
	 * @param <T> Type of the values.
	 * @param values Values to test.
	 * @param filter Filter function to use.
	 * @return <code>true</code> if any value is accepted, <code>false</code> if all values are rejected.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <T> boolean isAny(final Collection<? extends T> values, final Filter<? super T> filter)
	throws ApplicationException {
		Assert.notNull(values);
		Assert.notNull(filter);
		
		// Test.
		for (final T value : values) {
			if (filter.filter(value)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Test wether all given values are accepted by the given filter function.
	 * 
	 * @param <T> Type of the values.
	 * @param values Values to test.
	 * @param filter Filter function to use.
	 * @return <code>true</code> if all values are accepted, <code>false</code> if any value is rejected.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <T> boolean areAll(final Collection<? extends T> values, final Filter<? super T> filter)
	throws ApplicationException {
		Assert.notNull(values);
		Assert.notNull(filter);
		
		// Test.
		for (final T value : values) {
			if (!filter.filter(value)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Apply the given function to the given values and return the results.
	 * 
	 * @param <T1> Type of the argument values.
	 * @param <T2> Type of the result values.
	 * @param <C> Type of the result collection to build.
	 * @param values Argument values.
	 * @param function Function to apply.
	 * @param ignoreNull Flag indincating wether the <code>null</code> results should be ignored or not.
	 * @param factory Factory to use to build the result collection.
	 * @return The results of the function applications.
	 * @throws ApplicationException When some function application fails.
	 * @see #map(Collection, Function, boolean, Collection)
	 */
	public static <T1, T2, C extends Collection<T2>> C map(final Collection<? extends T1> values, final Function<? super T1, ? extends T2> function, final boolean ignoreNull, final CollectionFactory<? super T2, ? extends C> factory)
	throws ApplicationException {
		Assert.notNull(factory);
		
		// Map.
		return map(values, function, ignoreNull, factory.build(values.size()));
	}
	
	/**
	 * Apply the given function to the given values and populate the given result collection with the results.
	 * 
	 * @param <T1> Type of the argument values.
	 * @param <T2> Type of the result values.
	 * @param <C> Type of the result collection to build.
	 * @param values Argument values.
	 * @param function Function to apply.
	 * @param ignoreNull Flag indincating wether the <code>null</code> results should be ignored or not.
	 * @param results The collection to populate with the results.
	 * @return The populated result collection.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <T1, T2, C extends Collection<T2>> C map(final Collection<? extends T1> values, final Function<? super T1, ? extends T2> function, final boolean ignoreNull, final C results)
	throws ApplicationException {
		Assert.notNull(values);
		Assert.notNull(function);
		Assert.notNull(results);
		
		// Map.
		for (final T1 value : values) {
			final T2 result = function.apply(value);
			if (null != result || !ignoreNull) {
				results.add(result);
			}
		}
		return results;
	}
	
	/**
	 * Apply the given function to the given bindings and return a map of the results associated to their argument keys.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V1> Type of the argument values.
	 * @param <V2> Type of the result values.
	 * @param <M> Type of the result map to build.
	 * @param bindings Argument bindings.
	 * @param function Function to apply.
	 * @param ignoreNull Flag indincating wether the <code>null</code> results should be ignored or not.
	 * @param factory Factory to use to build the result map.
	 * @return A map of the function applications results associated to the corresponding argument keys.
	 * @throws ApplicationException When some function application fails.
	 * @see #map(Map, Function2, boolean, Map)
	 */
	public static <K, V1, V2, M extends Map<K, V2>> M map(final Map<? extends K, ? extends V1> bindings, final Function2<? super K, ? super V1, ? extends V2> function, final boolean ignoreNull, final MapFactory<? super K, ? super V2, ? extends M> factory)
	throws ApplicationException {
		Assert.notNull(factory);
		
		// Map.
		return map(bindings, function, ignoreNull, factory.build(bindings.size()));
	}
	
	/**
	 * Apply the given function to the given bindings and populate the given result map with the results associated to their argument keys.
	 * <p>
	 * This method applies the function to every binding of the given map. The keys and the values of the map are respectively passed as first and second
	 * arguments to the function.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V1> Type of the argument values.
	 * @param <V2> Type of the result values.
	 * @param <M> Type of the result map to build.
	 * @param bindings Argument bindings.
	 * @param function Function to apply.
	 * @param ignoreNull Flag indincating wether the <code>null</code> results should be ignored or not.
	 * @param results The map to populate with the results.
	 * @return The populated result map.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <K, V1, V2, M extends Map<K, V2>> M map(final Map<? extends K, ? extends V1> bindings, final Function2<? super K, ? super V1, ? extends V2> function, final boolean ignoreNull, final M results)
	throws ApplicationException {
		Assert.notNull(bindings);
		Assert.notNull(function);
		Assert.notNull(results);
		
		// Map.
		for (final Map.Entry<? extends K, ? extends V1> entry : bindings.entrySet()) {
			final K key = entry.getKey();
			final V1 value = entry.getValue();
			final V2 result = function.apply(key, value);
			if (null != result || !ignoreNull) {
				results.put(key, result);
			}
		}
		return results;
	}
	
	/**
	 * Apply the given function to the given keys and build a map of the results associated to their argument keys.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map to build.
	 * @param keys Keys to map.
	 * @param function Function computing the values.
	 * @param ignoreNull Flag indincating wether the <code>null</code> results should be ignored or not.
	 * @param factory Factory to use to build the result map.
	 * @return A map of the function application results associated to the corresponding argument keys.
	 * @throws ApplicationException When some function application fails.
	 * @see #mapKeys(Set, Function, boolean, Map)
	 */
	public static <K, V, M extends Map<K, V>> Map<K, V> mapKeys(final Set<? extends K> keys, final Function<? super K, ? extends V> function, final boolean ignoreNull, final MapFactory<? super K, ? super V, ? extends M> factory)
	throws ApplicationException {
		Assert.notNull(factory);
		
		// Map the keys.
		return mapKeys(keys, function, ignoreNull, factory.build(keys.size()));
	}
	
	/**
	 * Apply the given function to the given keys and populate the given result map of the results associated to their argument keys.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map to build.
	 * @param keys Keys to map.
	 * @param function Function computing the values.
	 * @param ignoreNull Flag indincating wether the <code>null</code> results should be ignored or not.
	 * @param results The map to populate with the results.
	 * @return The populated result map.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <K, V, M extends Map<K, V>> Map<K, V> mapKeys(final Set<? extends K> keys, final Function<? super K, ? extends V> function, final boolean ignoreNull, final M results)
	throws ApplicationException {
		Assert.notNull(keys);
		Assert.notNull(function);
		Assert.notNull(results);
		
		// Map the keys.
		for (final K key : keys) {
			final V value = function.apply(key);
			if (null != value || !ignoreNull) {
				results.put(key, value);
			}
		}
		return results;
	}
	
	/**
	 * Apply the given function to the given values and build a map of the argument values associated to their corresponding results.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map to build.
	 * @param values Values to map.
	 * @param function Function computing the keys.
	 * @param ignoreNull Flag indincating wether the <code>null</code> results should be ignored or not.
	 * @param factory Factory to use to build the result map.
	 * @return A map of the argument values associated to the corresponding function application results.
	 * @throws ApplicationException When some function application fails.
	 * @see #mapValues(Collection, Function, boolean, Map)
	 */
	public static <K, V, M extends Map<K, V>> M mapValues(final Collection<? extends V> values, final Function<? super V, ? extends K> function, final boolean ignoreNull, final MapFactory<? super K, ? super V, ? extends M> factory)
	throws ApplicationException {
		Assert.notNull(factory);
		
		// Map the values.
		return mapValues(values, function, ignoreNull, factory.build(values.size()));
	}
	
	/**
	 * Apply the given function to the given values and populate the given result map with the argument values associated to their corresponding results.
	 * <p>
	 * When the function return a same key for different values, the last value is associated to the key in the result map.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map to build.
	 * @param values Values to map.
	 * @param function Function computing the keys.
	 * @param ignoreNull Flag indincating wether the <code>null</code> results should be ignored or not.
	 * @param results The map to populate with the results.
	 * @return The populated result map.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <K, V, M extends Map<K, V>> M mapValues(final Collection<? extends V> values, final Function<? super V, ? extends K> function, final boolean ignoreNull, final M results)
	throws ApplicationException {
		Assert.notNull(values);
		Assert.notNull(function);
		Assert.notNull(results);
		
		// Map the values.
		for (final V value : values) {
			final K key = function.apply(value);
			if (null != key || !ignoreNull) {
				results.put(key, value);
			}
		}
		return results;
	}
	
	/**
	 * Apply the given function to the keys of the given map and return a map of the values corresponding to the argument keys associated to the result keys.
	 * 
	 * @param <K1> Type of the argument keys.
	 * @param <K2> Type of the result keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map to build.
	 * @param map Map to read.
	 * @param function Function to apply.
	 * @param ignoreNull Flag indincating wether the <code>null</code> results should be ignored or not.
	 * @param factory Factory to use to build the result map.
	 * @return A map of the values corresponding to the argument keys associated to the result keys.
	 * @throws ApplicationException When some function application fails.
	 * @see #remap(Map, Function, boolean, Map)
	 */
	public static <K1, K2, V, M extends Map<K2, V>> M remap(final Map<? extends K1, ? extends V> map, final Function<? super K1, ? extends K2> function, final boolean ignoreNull, final MapFactory<? super K2, ? super V, ? extends M> factory)
	throws ApplicationException {
		Assert.notNull(factory);
		
		// Remap.
		return remap(map, function, ignoreNull, factory.build(map.size()));
	}
	
	/**
	 * Apply the given function to the keys of the given map and populate the given result map with the values corresponding to the argument keys associated to
	 * the result keys.
	 * 
	 * @param <K1> Type of the argument keys.
	 * @param <K2> Type of the result keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map to build.
	 * @param map Map to read.
	 * @param function Function to apply.
	 * @param ignoreNull Flag indincating wether the <code>null</code> results should be ignored or not.
	 * @param results The map to populate with the results.
	 * @return The populated result map.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <K1, K2, V, M extends Map<K2, V>> M remap(final Map<? extends K1, ? extends V> map, final Function<? super K1, ? extends K2> function, final boolean ignoreNull, final M results)
	throws ApplicationException {
		Assert.notNull(map);
		Assert.notNull(function);
		Assert.notNull(results);
		
		// Remap.
		for (final Map.Entry<? extends K1, ? extends V> entry : map.entrySet()) {
			final K1 key = entry.getKey();
			final K2 newKey = function.apply(key);
			if (null != newKey || !ignoreNull) {
				final V value = entry.getValue();
				results.put(newKey, value);
			}
		}
		return results;
	}
	
	/**
	 * Apply the given function to the given bindings and return a map of the values associated to the result keys.
	 * 
	 * @param <K1> Type of the argument keys.
	 * @param <K2> Type of the result keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map to build.
	 * @param bindings Argument bindings.
	 * @param function Function to apply.
	 * @param ignoreNull Flag indincating wether the <code>null</code> results should be ignored or not.
	 * @param factory Factory to use to build the result map.
	 * @return A map of the values associated to the corresponding result keys.
	 * @throws ApplicationException When some function application fails.
	 * @see #remap(Map, Function2, boolean, Map)
	 */
	public static <K1, K2, V, M extends Map<K2, V>> M remap(final Map<? extends K1, ? extends V> bindings, final Function2<? super K1, ? super V, ? extends K2> function, final boolean ignoreNull, final MapFactory<? super K2, ? super V, ? extends M> factory)
	throws ApplicationException {
		Assert.notNull(factory);
		
		// Remap.
		return remap(bindings, function, ignoreNull, factory.build(bindings.size()));
	}
	
	/**
	 * Apply the given function to the given bindings and populate the given result map with the values associated to the result keys.
	 * <p>
	 * This method applies the function to every binding of the given map. The keys and the values of the map are respectively passed as first and second
	 * arguments to the function.
	 * 
	 * @param <K1> Type of the argument keys.
	 * @param <K2> Type of the result keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map to build.
	 * @param bindings Argument bindings.
	 * @param function Function to apply.
	 * @param ignoreNull Flag indincating wether the <code>null</code> results should be ignored or not.
	 * @param results The map to populate with the results.
	 * @return The populated result map.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <K1, K2, V, M extends Map<K2, V>> M remap(final Map<? extends K1, ? extends V> bindings, final Function2<? super K1, ? super V, ? extends K2> function, final boolean ignoreNull, final M results)
	throws ApplicationException {
		Assert.notNull(bindings);
		Assert.notNull(function);
		Assert.notNull(results);
		
		// Remap.
		for (final Map.Entry<? extends K1, ? extends V> entry : bindings.entrySet()) {
			final K1 key = entry.getKey();
			final V value = entry.getValue();
			final K2 newKey = function.apply(key, value);
			if (null != newKey || !ignoreNull) {
				results.put(newKey, value);
			}
		}
		return results;
	}
	
	/**
	 * Apply the given procedure to the given values.
	 * 
	 * @param <T> Type of the argument values.
	 * @param values Argument values
	 * @param procedure Procedure to apply.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <T> void apply(final Collection<? extends T> values, final Procedure<? super T> procedure)
	throws ApplicationException {
		Assert.notNull(values);
		Assert.notNull(procedure);
		
		// Apply.
		for (final T value : values) {
			procedure.apply(value);
		}
	}
	
	/**
	 * Apply the given procedure to the values provided by the given iterator.
	 * 
	 * @param <T> Type of the argument values.
	 * @param iterator Iterator providing the argument values.
	 * @param procedure Procedure to apply.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <T> void apply(final Iterator<? extends T> iterator, final Procedure<? super T> procedure)
	throws ApplicationException {
		Assert.notNull(iterator);
		Assert.notNull(procedure);
		
		// Apply.
		while (iterator.hasNext()) {
			final T value = iterator.next();
			procedure.apply(value);
		}
	}
	
	// DOCME
	public static <T, A> A fold(final Collection<? extends T> values, final Function2<? super A, ? super T, ? extends A> function, final A initialAccumulator)
	throws ApplicationException {
		Assert.notNull(values);
		Assert.notNull(function);
		
		// Fold.
		A accumulator = initialAccumulator;
		for (final T value : values) {
			accumulator = function.apply(accumulator, value);
		}
		return accumulator;
	}
	
	// DOCME
	public static <T, A> A fold(final Iterator<? extends T> iterator, final Function2<? super A, ? super T, ? extends A> function, final A initialAccumulator)
	throws ApplicationException {
		Assert.notNull(iterator);
		Assert.notNull(function);
		
		// Fold.
		A accumulator = initialAccumulator;
		while (iterator.hasNext()) {
			final T value = iterator.next();
			accumulator = function.apply(accumulator, value);
		}
		return accumulator;
	}
	
	// DOCME
	public static <T1, T2, L extends List<Tuple2<T1, T2>>> L zip(final Iterator<T1> list1, final Iterator<T2> list2, final L results) {
		Assert.notNull(list1);
		Assert.notNull(list2);
		Assert.notNull(results);
		
		// Zip.
		while (list1.hasNext() && list2.hasNext()) {
			results.add(Tuple2.build(list1.next(), list2.next()));
		}
		return results;
	}
	
	// DOCME
	public static <T1, T2, L1 extends List<T1>, L2 extends List<T2>> void unzip(final Iterator<? extends Tuple2<T1, T2>> list, final L1 results1, final L2 results2) {
		Assert.notNull(list);
		Assert.notNull(results1);
		Assert.notNull(results2);
		
		// Unzip.
		while (list.hasNext()) {
			final Tuple2<T1, T2> value = list.next();
			results1.add(value.getFirst());
			results2.add(value.getSecond());
		}
	}
	
	private FunctionUtils() {
		// Prevent instantiation.
	}
}
