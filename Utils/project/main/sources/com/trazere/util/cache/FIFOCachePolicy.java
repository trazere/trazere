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
package com.trazere.util.cache;

import java.util.Collection;
import java.util.LinkedList;

/**
 * The {@link FIFOCachePolicy} class provides cache policies based on the First In/First Out algorithm.
 * 
 * @param <K> Type of the keys.
 * @deprecated Use {@link com.trazere.core.cache.FIFOCachePolicy}.
 */
@Deprecated
public class FIFOCachePolicy<K>
implements CachePolicy<K> {
	/**
	 * Instanciates a new cache policy.
	 * 
	 * @param capacity The capacity of the cache.
	 * @deprecated Use {@link com.trazere.core.cache.FIFOCachePolicy#FIFOCachePolicy(int)}.
	 */
	@Deprecated
	public FIFOCachePolicy(final int capacity) {
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
	 * @deprecated Use {@link com.trazere.core.cache.FIFOCachePolicy#getCapacity()}.
	 */
	@Deprecated
	public int getCapacity() {
		return _capacity;
	}
	
	// Entries.
	
	protected final LinkedList<K> _uses = new LinkedList<K>();
	
	@Override
	public <C extends Collection<? super K>> C accessedEntry(final K key, final C dirtyEntries) {
		return dirtyEntries;
	}
	
	@Override
	public <C extends Collection<? super K>> C updatedEntry(final K key, final C dirtyEntries) {
		assert null != key;
		
		// Update the uses.
		// Note: the first keys comes first to optimize the cleanups.
		_uses.remove(key);
		_uses.addLast(key);
		
		// Find the dirty entries.
		return findDirtyEntries(dirtyEntries);
	}
	
	@Override
	public void removedEntry(final K key) {
		assert null != key;
		
		_uses.remove(key);
	}
	
	@Override
	public void removedAllEntries() {
		_uses.clear();
	}
	
	protected <C extends Collection<? super K>> C findDirtyEntries(final C dirtyEntries) {
		assert null != dirtyEntries;
		
		final int size = _uses.size();
		if (size > _capacity) {
			dirtyEntries.addAll(_uses.subList(0, size - _capacity));
		}
		return dirtyEntries;
	}
}
