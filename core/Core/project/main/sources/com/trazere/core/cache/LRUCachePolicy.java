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
package com.trazere.core.cache;

import com.trazere.core.imperative.Accumulator;
import java.util.LinkedList;

/**
 * The {@link LRUCachePolicy} class provides cache policies with a bounded capacity based on the Least Recently Used algorithm.
 * 
 * @param <K> Type of the keys.
 */
public class LRUCachePolicy<K>
implements CachePolicy<K> {
	/**
	 * Instanciates a new cache policy.
	 * 
	 * @param capacity Capacity of the cache.
	 */
	public LRUCachePolicy(final int capacity) {
		assert capacity > 0;
		
		// Initialization.
		_capacity = capacity;
	}
	
	// Capacity.
	
	/** Capacity of the receiver cache. */
	protected final int _capacity;
	
	/**
	 * Gets the capacity of the cache.
	 * 
	 * @return The capacity.
	 */
	public int getCapacity() {
		return _capacity;
	}
	
	// State.
	
	@Override
	public CachePolicy.State<K> build() {
		return new CachePolicy.State<K>() {
			/** Order. */
			private final LinkedList<K> _order = new LinkedList<>();
			
			@Override
			public <A extends Accumulator<? super K, ?>> A updatedEntry(final K key, final A dirtyEntries) {
				// Update the order.
				touchEntry(key);
				
				// Find the dirty entries.
				final int size = _order.size();
				if (size > _capacity) {
					dirtyEntries.addAll(_order.subList(0, size - _capacity));
				}
				return dirtyEntries;
			}
			
			@Override
			public <A extends Accumulator<? super K, ?>> A accessedEntry(final K key, final A dirtyEntries) {
				// Update the order.
				touchEntry(key);
				
				return dirtyEntries;
			}
			
			private void touchEntry(final K key) {
				// Update the order.
				// Note: the least recently used keys comes first to optimize the cleanups.
				_order.remove(key);
				_order.addLast(key);
			}
			
			@Override
			public void clearedEntry(final K key) {
				_order.remove(key);
			}
			
			@Override
			public void clearedAllEntries() {
				_order.clear();
			}
		};
	}
}
