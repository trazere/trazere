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

/**
 * The {@link MultimapAccumulators} class provides various factories of accumulators related to collections.
 */
public class MultimapAccumulators {
	/**
	 * Builds an accumulator that puts bindings into the given multimap.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the multimap.
	 * @param multimap Multimap to populate.
	 * @return The built accumulator.
	 */
	public static <K, V, M extends Multimap<? super K, ? super V, ?>> Accumulator2<K, V, M> put(final M multimap) {
		assert null != multimap;
		
		return new Accumulator2<K, V, M>() {
			@Override
			public void add(final K key, final V value) {
				multimap.put(key, value);
			}
			
			@Override
			public M get() {
				return multimap;
			}
		};
	}
	
	private MultimapAccumulators() {
		// Prevents instantiation.
	}
}
