/*
 *  Copyright 2006-2012 Julien Dufour
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

import com.trazere.util.lang.LangUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * The {@link MapFactories} class provides various standard map factories.
 * 
 * @see MapFactory
 */
public class MapFactories {
	private static final MapFactory<?, ?, ?> _HASHMAP = new BaseMapFactory<Object, Object, HashMap<Object, Object>>() {
		public HashMap<Object, Object> build() {
			return new HashMap<Object, Object>();
		}
		
		public HashMap<Object, Object> build(final int capacity) {
			return new HashMap<Object, Object>(capacity);
		}
		
		public HashMap<Object, Object> build(final Map<? extends Object, ? extends Object> bindings) {
			return new HashMap<Object, Object>(bindings);
		}
	};
	
	/**
	 * Build a map factory which produces {@link HashMap}s.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @return The factory.
	 */
	public static <K, V> MapFactory<K, V, HashMap<K, V>> hashMap() {
		return LangUtils.cast(_HASHMAP);
	}
	
	private static final MapFactory<?, ?, ?> _TREEMAP = new BaseMapFactory<Object, Object, TreeMap<Object, Object>>() {
		public TreeMap<Object, Object> build() {
			return new TreeMap<Object, Object>();
		}
		
		public TreeMap<Object, Object> build(final int capacity) {
			return new TreeMap<Object, Object>();
		}
		
		public TreeMap<Object, Object> build(final Map<? extends Object, ? extends Object> bindings) {
			return new TreeMap<Object, Object>(bindings);
		}
	};
	
	/**
	 * Build a map factory which produces {@link TreeMap}s.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @return The factory.
	 */
	public static <K, V> MapFactory<K, V, TreeMap<K, V>> treeMap() {
		return LangUtils.cast(_TREEMAP);
	}
	
	private MapFactories() {
		// Prevents instantiation.
	}
}
