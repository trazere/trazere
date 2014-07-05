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

import com.trazere.core.design.Factory;
import com.trazere.core.util.Tuple2;
import java.util.Map;

/**
 * The {@link MapFactory} interface defines factories of maps.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <M> Type of the maps.
 */
public interface MapFactory<K, V, M extends Map<? super K, ? super V>>
extends Factory<M> {
	/**
	 * Builds an empty map.
	 * 
	 * @return The built map.
	 */
	@Override
	public M build();
	
	/**
	 * Builds an empty map with the given initial capacity.
	 * 
	 * @param capacity Initial capacity of the map.
	 * @return The built map.
	 */
	public M build(final int capacity);
	
	/**
	 * Builds a map containing the given bindings.
	 * 
	 * @param bindings Bindings.
	 * @return The built map.
	 */
	M build(Iterable<? extends Tuple2<? extends K, ? extends V>> bindings);
	
	/**
	 * Builds a map containing the given bindings.
	 * 
	 * @param bindings Values.
	 * @return The built map.
	 */
	public M build(final Map<? extends K, ? extends V> bindings);
}
