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

import java.util.Collection;

/**
 * The {@link ExtendedMultimapFactory} interface defines factories of {@link Multimap multimaps}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <C> Type of the collections of values.
 * @param <M> Type of the multimaps.
 * @see Multimap
 * @since 1.0
 */
public interface ExtendedMultimapFactory<K, V, C extends Collection<V>, M extends Multimap<K, V, C>>
extends ExtendedAbstractMultimapFactory<K, V, C, Multimap<K, V, C>, M> {
	@Override
	default Multimap<K, V, C> unmodifiable(final M multimap) {
		return MultimapUtils.unmodifiable(multimap);
	}
}
