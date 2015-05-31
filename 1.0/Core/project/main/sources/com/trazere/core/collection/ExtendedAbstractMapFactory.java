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

import java.util.Map;

/**
 * The {@link ExtendedAbstractMapFactory} interface defines factories of {@link Map maps} that support providing unmodifiable and synchronized views of the
 * maps.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <AM> Type of the abstract maps.
 * @param <M> Type of the maps.
 * @see Map
 * @since 1.0
 */
public interface ExtendedAbstractMapFactory<K, V, AM extends Map<K, V>, M extends AM>
extends MapFactory<K, V, M> {
	/**
	 * Builds an unmodifiable view of an empty map.
	 * 
	 * @return The built unmodifiable view.
	 * @since 1.0
	 */
	AM empty();
	
	/**
	 * Builds an unmodifiable view of the given map.
	 * 
	 * @param map Map to wrap.
	 * @return The built unmodifiable view.
	 * @since 1.0
	 */
	AM unmodifiable(M map);
	
	/**
	 * Builds a synchronized view of the given map.
	 * 
	 * @param map Map to wrap.
	 * @return The built synchronized view.
	 * @since 1.0
	 */
	AM synchronize(M map);
}
