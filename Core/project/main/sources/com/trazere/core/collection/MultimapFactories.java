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

import java.util.Collection;
import java.util.Map;

/**
 * The {@link MultimapFactories} class provides various factories of {@link MultimapFactory multimap factories}.
 * 
 * @see MultimapFactory
 */
public class MultimapFactories {
	/**
	 * Builds a map factory that builds {@link MapMultimap}s.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <C> Type of the collections of values.
	 * @param <CC> Type of the concrete collections of values.
	 * @param mapFactory Factory of the backing maps.
	 * @param valuesFactory Factory of the collections of values.
	 * @return The built factory.
	 */
	public static <K, V, C extends Collection<V>, CC extends C> ExtendedMultimapFactory<K, V, C, MapMultimap<K, V, C, CC>> mapMultimap(final MapFactory<K, CC, ? extends Map<K, CC>> mapFactory, final ExtendedAbstractCollectionFactory<V, C, CC> valuesFactory) {
		assert null != mapFactory;
		assert null != valuesFactory;
		
		return new ExtendedMultimapFactory<K, V, C, MapMultimap<K, V, C, CC>>() {
			@Override
			public MapMultimap<K, V, C, CC> build() {
				return new MapMultimap<>(mapFactory, valuesFactory);
			}
			
			@Override
			public MapMultimap<K, V, C, CC> build(final Multimap<? extends K, ? extends V, ?> bindings) {
				return new MapMultimap<>(mapFactory, valuesFactory, bindings);
			}
			
			@Override
			public Multimap<K, V, C> empty() {
				return Multimaps.empty(valuesFactory);
			}
		};
	}
	
	private MultimapFactories() {
		// Prevents instantiation.
	}
}
