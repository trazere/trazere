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

import com.trazere.core.design.Factory;
import com.trazere.core.util.Tuple2;
import java.util.Map;

/**
 * The {@link MapFactory} interface defines factories of {@link Map maps}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <M> Type of the maps.
 * @see Map
 * @since 2.0
 */
public interface MapFactory<K, V, M extends Map<K, V>>
extends Factory<M> {
	/**
	 * Builds an empty map.
	 * 
	 * @return The built map.
	 * @since 2.0
	 */
	@Override
	M build();
	
	/**
	 * Builds an empty map with the given initial capacity.
	 * 
	 * @param capacity Initial capacity of the map.
	 * @return The built map.
	 * @since 2.0
	 */
	default M build(final int capacity) {
		return build();
	}
	
	// TODO: build(K, V)
	// TODO: build(Tuple2)
	
	/**
	 * Builds a map containing the given bindings.
	 * 
	 * @param bindings Bindings.
	 * @return The built map.
	 * @since 2.0
	 */
	default M build(@SuppressWarnings("unchecked") final Tuple2<? extends K, ? extends V>... bindings) {
		final M map = build();
		MapUtils.putAll(map, bindings);
		return map;
	}
	
	/**
	 * Builds a map containing the given bindings.
	 * 
	 * @param bindings Bindings.
	 * @return The built map.
	 * @since 2.0
	 */
	default M build(final Iterable<? extends Tuple2<? extends K, ? extends V>> bindings) {
		final M map = build();
		MapUtils.putAll(map, bindings);
		return map;
	}
	
	/**
	 * Builds a map containing the given bindings.
	 * 
	 * @param bindings Values.
	 * @return The built map.
	 * @since 2.0
	 */
	M build(Map<? extends K, ? extends V> bindings);
}
