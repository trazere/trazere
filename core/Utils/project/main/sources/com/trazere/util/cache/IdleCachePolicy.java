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
package com.trazere.util.cache;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The {@link IdleCachePolicy} class provides a cache policy based on an idle time.
 * 
 * @param <K> Type of the keys.
 */
public class IdleCachePolicy<K>
implements CachePolicy<K> {
	/**
	 * Instanciates a new cache policy.
	 * 
	 * @param timeout The timeout.
	 */
	public IdleCachePolicy(final long timeout) {
		assert timeout > 0;
		
		// Initialization.
		_timeout = timeout;
	}
	
	// Timeout.
	
	/** Timeout in milliseconds. */
	protected final long _timeout;
	
	/**
	 * Get the timeout of the receiver policy.
	 * 
	 * @return The timeout in milliseconds.
	 */
	public long getTimeout() {
		return _timeout;
	}
	
	// Entries.
	
	/** Access dates of the entries. */
	protected final LinkedHashMap<K, Long> _dates = new LinkedHashMap<K, Long>();
	
	@Override
	public <C extends Collection<? super K>> C accessedEntry(final K key, final C dirtyEntries) {
		assert null != key;
		
		// Update the dates.
		// Note: date is removed first to maintain the order of the map
		_dates.remove(key);
		_dates.put(key, System.currentTimeMillis());
		
		// Find the dirty entries.
		return findDirtyEntries(dirtyEntries);
	}
	
	@Override
	public <C extends Collection<? super K>> C updatedEntry(final K key, final C dirtyEntries) {
		assert null != key;
		
		// Update the dates.
		// Note: date is removed first to maintain the order of the map
		_dates.remove(key);
		_dates.put(key, System.currentTimeMillis());
		
		// Find the dirty entries.
		return findDirtyEntries(dirtyEntries);
	}
	
	@Override
	public void removedEntry(final K key) {
		assert null != key;
		
		_dates.remove(key);
	}
	
	@Override
	public void removedAllEntries() {
		_dates.clear();
	}
	
	protected <C extends Collection<? super K>> C findDirtyEntries(final C dirtyEntries) {
		assert null != dirtyEntries;
		
		final long now = System.currentTimeMillis();
		for (final Map.Entry<K, Long> date : _dates.entrySet()) {
			if ((now - date.getValue().longValue()) >= _timeout) {
				dirtyEntries.add(date.getKey());
			} else {
				break;
			}
		}
		return dirtyEntries;
	}
}
