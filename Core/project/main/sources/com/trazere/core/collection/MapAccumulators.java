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

import com.trazere.core.imperative.Accumulator;
import com.trazere.core.imperative.Accumulator2;
import java.util.Map;

/**
 * The {@link MapAccumulators} class provides various factories of {@link Accumulator accumulators} related to {@link Map maps}.
 * 
 * @see Accumulator
 * @see Map
 * @since 2.0
 */
public class MapAccumulators {
	/**
	 * Builds an accumulator that puts bindings into the given map.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map.
	 * @param map Map to populate.
	 * @return The built accumulator.
	 * @see Map#put(Object, Object)
	 * @since 2.0
	 */
	public static <K, V, M extends Map<? super K, ? super V>> Accumulator2<K, V, M> put(final M map) {
		assert null != map;
		
		return new Accumulator2<K, V, M>() {
			@Override
			public void add(final K key, final V value) {
				map.put(key, value);
			}
			
			@Override
			public M get() {
				return map;
			}
		};
	}
	
	private MapAccumulators() {
		// Prevents instantiation.
	}
}
