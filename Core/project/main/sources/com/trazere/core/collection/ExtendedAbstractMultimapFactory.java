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
 * The {@link ExtendedAbstractMultimapFactory} interface defines factories of {@link Multimap multimaps} that support providing unmodifiable views of the
 * multimaps.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <C> Type of the collections of values.
 * @param <AM> Type of the abstract multimaps.
 * @param <M> Type of the multimaps.
 * @see Multimap
 * @since 1.0
 */
public interface ExtendedAbstractMultimapFactory<K, V, C extends Collection<V>, AM extends Multimap<K, V, C>, M extends AM>
extends MultimapFactory<K, V, C, M> {
	/**
	 * Builds an unmodifiable view of an empty multimap.
	 * 
	 * @return The built unmodifiable view.
	 * @since 1.0
	 */
	AM empty();
	
	/**
	 * Builds an unmodifiable view of the given multimap.
	 * 
	 * @param multimap Multimap to wrap.
	 * @return The built unmodifiable view.
	 * @since 1.0
	 */
	AM unmodifiable(M multimap);
}
