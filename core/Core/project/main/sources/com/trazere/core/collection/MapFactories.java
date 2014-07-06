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

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * The {@link MapFactories} class provides various factories of {@link MapFactory map factories}.
 * 
 * @see MapFactory
 */
public class MapFactories {
	/**
	 * Builds a map factory that builds {@link HashMap}s.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @return The built factory.
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> MapFactory<K, V, HashMap<K, V>> hashMap() {
		return (MapFactory<K, V, HashMap<K, V>>) HASH_MAP;
	}
	
	private static final MapFactory<?, ?, ?> HASH_MAP = new MapFactory<Object, Object, HashMap<Object, Object>>() {
		@Override
		public HashMap<Object, Object> build() {
			return new HashMap<>();
		}
		
		@Override
		public HashMap<Object, Object> build(final int capacity) {
			return new HashMap<>(capacity);
		}
		
		@Override
		public HashMap<Object, Object> build(final Map<? extends Object, ? extends Object> bindings) {
			return new HashMap<>(bindings);
		}
	};
	
	/**
	 * Builds a map factory that builds {@link TreeMap}s.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @return The built factory.
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> MapFactory<K, V, TreeMap<K, V>> treeMap() {
		return (MapFactory<K, V, TreeMap<K, V>>) TREE_MAP;
	}
	
	private static final MapFactory<?, ?, ?> TREE_MAP = new MapFactory<Object, Object, TreeMap<Object, Object>>() {
		@Override
		public TreeMap<Object, Object> build() {
			return new TreeMap<>();
		}
		
		@Override
		public TreeMap<Object, Object> build(final Map<? extends Object, ? extends Object> bindings) {
			return new TreeMap<>(bindings);
		}
	};
	
	private MapFactories() {
		// Prevents instantiation.
	}
}
