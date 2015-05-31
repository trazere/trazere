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

import com.trazere.core.util.Tuple2;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@link Maps} class provides various factories of {@link Map maps}.
 * 
 * @see Map
 * @since 1.0
 */
public class Maps {
	/**
	 * Builds an unmutable empty map.
	 * 
	 * @param <K> Type of the key.
	 * @param <V> Type of the value.
	 * @return The built map.
	 * @since 1.0
	 */
	public static <K, V> Map<K, V> empty() {
		return Collections.emptyMap();
	}
	
	/**
	 * Builds a map containing the binding corresponding to the given key and value.
	 * 
	 * @param <K> Type of the key.
	 * @param <V> Type of the value.
	 * @param key Key of the binding of the map to build.
	 * @param value Value of the binding of the map to build.
	 * @return The built map.
	 * @since 1.0
	 */
	public static <K, V> Map<K, V> fromBinding(final K key, final V value) {
		final Map<K, V> map = new HashMap<>();
		map.put(key, value);
		return map;
	}
	
	/**
	 * Builds a map containing the bindings corresponding to the given keys and values.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param key1 Key of the first binding of the map to build.
	 * @param value1 Value of the first binding of the map to build.
	 * @param key2 Key of the second binding of the map to build.
	 * @param value2 Value of the second binding of the map to build.
	 * @return The built map.
	 * @since 1.0
	 */
	public static <K, V> Map<K, V> fromBindings(final K key1, final V value1, final K key2, final V value2) {
		final Map<K, V> map = new HashMap<>(2);
		map.put(key1, value1);
		map.put(key2, value2);
		return map;
	}
	
	/**
	 * Builds a map containing the bindings corresponding to the given keys and values.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param key1 Key of the first binding of the map to build.
	 * @param value1 Value of the first binding of the map to build.
	 * @param key2 Key of the second binding of the map to build.
	 * @param value2 Value of the second binding of the map to build.
	 * @param key3 Key of the third binding of the map to build.
	 * @param value3 Value of the third binding of the map to build.
	 * @return The built map.
	 * @since 1.0
	 */
	public static <K, V> Map<K, V> fromBindings(final K key1, final V value1, final K key2, final V value2, final K key3, final V value3) {
		final Map<K, V> map = new HashMap<>(3);
		map.put(key1, value1);
		map.put(key2, value2);
		map.put(key3, value3);
		return map;
	}
	
	/**
	 * Builds a map containing the bindings corresponding to the given keys and values.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param key1 Key of the first binding of the map to build.
	 * @param value1 Value of the first binding of the map to build.
	 * @param key2 Key of the second binding of the map to build.
	 * @param value2 Value of the second binding of the map to build.
	 * @param key3 Key of the third binding of the map to build.
	 * @param value3 Value of the third binding of the map to build.
	 * @param key4 Key of the fourth binding of the map to build.
	 * @param value4 Value of the fourth binding of the map to build.
	 * @return The built map.
	 * @since 1.0
	 */
	public static <K, V> Map<K, V> fromBindings(final K key1, final V value1, final K key2, final V value2, final K key3, final V value3, final K key4, final V value4) {
		final Map<K, V> map = new HashMap<>(4);
		map.put(key1, value1);
		map.put(key2, value2);
		map.put(key3, value3);
		map.put(key4, value4);
		return map;
	}
	
	/**
	 * Builds a map containing the bindings corresponding to the given keys and values.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param key1 Key of the first binding of the map to build.
	 * @param value1 Value of the first binding of the map to build.
	 * @param key2 Key of the second binding of the map to build.
	 * @param value2 Value of the second binding of the map to build.
	 * @param key3 Key of the third binding of the map to build.
	 * @param value3 Value of the third binding of the map to build.
	 * @param key4 Key of the fourth binding of the map to build.
	 * @param value4 Value of the fourth binding of the map to build.
	 * @param key5 Key of the fifth binding of the map to build.
	 * @param value5 Value of the fifth binding of the map to build.
	 * @return The built map.
	 * @since 1.0
	 */
	public static <K, V> Map<K, V> fromBindings(final K key1, final V value1, final K key2, final V value2, final K key3, final V value3, final K key4, final V value4, final K key5, final V value5) {
		final Map<K, V> map = new HashMap<>(5);
		map.put(key1, value1);
		map.put(key2, value2);
		map.put(key3, value3);
		map.put(key4, value4);
		map.put(key5, value5);
		return map;
	}
	
	/**
	 * Builds a map containing the given bindings.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param bindings Bindings of the map to build.
	 * @return The built map.
	 * @since 1.0
	 */
	@SafeVarargs
	public static <K, V> Map<K, V> fromBindings(final Tuple2<? extends K, ? extends V>... bindings) {
		final Map<K, V> map = new HashMap<>(bindings.length);
		for (final Tuple2<? extends K, ? extends V> binding : bindings) {
			map.put(binding.get1(), binding.get2());
		}
		return map;
	}
	
	/**
	 * Builds a map containing the bindings provided by the given iterable.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param iterable Iterable providing the bindings of the map to build.
	 * @return The built map.
	 * @since 1.0
	 */
	public static <K, V> Map<K, V> fromBindings(final Iterable<? extends Tuple2<? extends K, ? extends V>> iterable) {
		final Map<K, V> map = new HashMap<>();
		for (final Tuple2<? extends K, ? extends V> binding : iterable) {
			map.put(binding.get1(), binding.get2());
		}
		return map;
	}
	
	private Maps() {
		// Prevent instantiation.
	}
}
