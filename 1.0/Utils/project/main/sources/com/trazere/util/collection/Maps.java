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
package com.trazere.util.collection;

import com.trazere.util.type.Tuple2;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@link Maps} class provides various factories of maps.
 * 
 * @deprecated Use core.
 */
@Deprecated
public class Maps {
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
	 * @deprecated Use {@link com.trazere.core.collection.Maps#fromBinding(Object, Object)}.
	 */
	@Deprecated
	public static <K, V> Map<K, V> fromBinding(final K key, final V value) {
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
	 * @deprecated Use {@link com.trazere.core.collection.Maps#fromBindings(Object, Object, Object, Object)}.
	 */
	@Deprecated
	public static <K, V> Map<K, V> fromBindings(final K key1, final V value1, final K key2, final V value2) {
		final Map<K, V> result = new HashMap<K, V>();
		result.put(key1, value1);
		result.put(key2, value2);
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
	 * @param key3 The third key. May be <code>null</code>.
	 * @param value3 The third value. May be <code>null</code>.
	 * @return The built map.
	 * @deprecated Use {@link com.trazere.core.collection.Maps#fromBindings(Object, Object, Object, Object, Object, Object)}.
	 */
	@Deprecated
	public static <K, V> Map<K, V> fromBindings(final K key1, final V value1, final K key2, final V value2, final K key3, final V value3) {
		final Map<K, V> result = new HashMap<K, V>();
		result.put(key1, value1);
		result.put(key2, value2);
		result.put(key3, value3);
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
	 * @deprecated Use {@link com.trazere.core.collection.Maps#fromBindings(com.trazere.core.util.Tuple2...)}.
	 */
	@Deprecated
	public static <K, V> Map<K, V> fromBindings(final Tuple2<? extends K, ? extends V>... bindings) {
		assert null != bindings;
		
		return fromBindings(Arrays.asList(bindings));
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
	 * @deprecated Use {@link com.trazere.core.collection.Maps#fromBindings(Iterable)}.
	 */
	@Deprecated
	public static <K, V> Map<K, V> fromBindings(final Collection<? extends Tuple2<? extends K, ? extends V>> bindings) {
		assert null != bindings;
		
		final Map<K, V> results = new HashMap<K, V>(bindings.size());
		for (final Tuple2<? extends K, ? extends V> binding : bindings) {
			results.put(binding.getFirst(), binding.getSecond());
		}
		return results;
	}
	
	private Maps() {
		// Prevent instantiation.
	}
}
