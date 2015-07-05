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

import java.util.Collections;
import java.util.Map;

/**
 * The {@link ExtendedMapFactory} interface defines factories of {@link Map maps}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <M> Type of the maps.
 * @see Map
 * @since 1.0
 */
public interface ExtendedMapFactory<K, V, M extends Map<K, V>>
extends ExtendedAbstractMapFactory<K, V, Map<K, V>, M> {
	@Override
	default Map<K, V> empty() {
		return Collections.emptyMap();
	}
	
	@Override
	default Map<K, V> unmodifiable(final M map) {
		return Collections.unmodifiableMap(map);
	}
	
	@Override
	default Map<K, V> synchronized_(final M map) {
		return Collections.synchronizedMap(map);
	}
}
