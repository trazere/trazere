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

import com.trazere.core.imperative.Accumulator2;
import java.util.Map;

/**
 * The {@link MapAccumulators} class provides various factories of accumulators related to collections.
 */
public class MapAccumulators {
	//	/**
	//	 * Builds an accumulator that puts bindings into the given map.
	//	 * 
	//	 * @param <K> Type of the keys.
	//	 * @param <V> Type of the values.
	//	 * @param <M> Type of the map.
	//	 * @param map Map to populate.
	//	 * @return The built accumulator.
	//	 */
	//	public static <K, V, M extends Map<? super K, ? super V>> Accumulator<Tuple2<? extends K, ? extends V>, M> put(final M map) {
	//		assert null != map;
	//		
	//		return new Accumulator<Tuple2<? extends K, ? extends V>, M>() {
	//			@Override
	//			public void add(final Tuple2<? extends K, ? extends V> binding) {
	//				map.put(binding.get1(), binding.get2());
	//			}
	//			
	//			@Override
	//			public M get() {
	//				return map;
	//			}
	//		};
	//	}
	
	/**
	 * Builds an accumulator that puts into the given map.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map.
	 * @param map Map to populate.
	 * @return The built accumulator.
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
	
	//	/**
	//	 * Builds an accumulator that populates the given multimap.
	//	 *
	//	 * @param <K> Type of the keys.
	//	 * @param <V> Type of the values.
	//	 * @param <M> Type of the multimap.
	//	 * @param <X> Type of the exceptions.
	//	 * @param multimap The map to populate.
	//	 * @return The built accumulator.
	//	 */
	//	public static <K, V, M extends Multimap<? super K, ? super V, ?>, X extends Exception> Accumulator2<K, V, M, X> put(final M multimap) {
	//		assert null != multimap;
	//
	//		return new BaseAccumulator2<K, V, M, X>() {
	//			@Override
	//			public void add(final K key, final V value)
	//			throws X {
	//				multimap.put(key, value);
	//			}
	//
	//			@Override
	//			public M get() {
	//				return multimap;
	//			}
	//		};
	//	}
	
	private MapAccumulators() {
		// Prevents instantiation.
	}
}
