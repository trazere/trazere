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
import java.util.Collection;

/**
 * The {@link MultimapFactory} interface defines factories of {@link Multimap multimaps}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <C> Type of the collections of values.
 * @param <M> Type of the multimaps.
 * @see Multimap
 * @since 1.0
 */
public interface MultimapFactory<K, V, C extends Collection<V>, M extends Multimap<K, V, C>>
extends Factory<M> {
	/**
	 * Builds an empty multimap.
	 * 
	 * @return The built multimap.
	 * @since 1.0
	 */
	@Override
	M build();
	
	/**
	 * Builds a multimap containing the given bindings.
	 * 
	 * @param bindings Bindings.
	 * @return The built multimap.
	 * @since 1.0
	 */
	default M build(@SuppressWarnings("unchecked") final Tuple2<? extends K, ? extends V>... bindings) {
		final M multimap = build();
		MultimapUtils.putAll(multimap, bindings);
		return multimap;
	}
	
	/**
	 * Builds a multimap containing the given bindings.
	 * 
	 * @param bindings Bindings.
	 * @return The built multimap.
	 * @since 1.0
	 */
	default M build(final Iterable<? extends Tuple2<? extends K, ? extends V>> bindings) {
		final M multimap = build();
		MultimapUtils.putAll(multimap, bindings);
		return multimap;
	}
	
	/**
	 * Builds a multimap containing the given bindings.
	 * 
	 * @param bindings Values.
	 * @return The built multimap.
	 * @since 1.0
	 */
	M build(Multimap<? extends K, ? extends V, ?> bindings);
}
