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
package com.trazere.core.cache;

import com.trazere.core.collection.MapUtils;
import com.trazere.core.functional.Predicate;
import com.trazere.core.util.Maybe;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * The {@link SimpleCache} class provides a simple implementation of caches backed by a map.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @since 1.0
 */
public class SimpleCache<K, V>
extends BaseCache<K, V> {
	public SimpleCache(final CachePolicy<K> policy) {
		super(policy);
	}
	
	// Entries.
	
	/**
	 * Entries of the cache.
	 * 
	 * @since 1.0
	 */
	protected final Map<K, V> _entries = new HashMap<>();
	
	@Override
	public boolean isEmpty() {
		return _entries.isEmpty();
	}
	
	@Override
	public int size() {
		return _entries.size();
	}
	
	@Override
	public boolean contains(final K key) {
		return _entries.containsKey(key);
	}
	
	@Override
	public Set<K> keys() {
		return Collections.unmodifiableSet(_entries.keySet());
	}
	
	@Override
	public void clear(final Predicate<? super K> filter) {
		// Iterate the entries.
		final Iterator<Map.Entry<K, V>> entries = _entries.entrySet().iterator();
		while (entries.hasNext()) {
			final Map.Entry<K, V> entry = entries.next();
			final K key = entry.getKey();
			if (filter.evaluate(key)) {
				// Remove the entry.
				entries.remove();
				
				// Dispose the value.
				dispose(entry.getValue());
				
				// Notify the policy state.
				_policyState.clearedEntry(key);
			}
		}
	}
	
	@Override
	public void clear() {
		// Dispose the values.
		for (final V value : _entries.values()) {
			dispose(value);
		}
		
		// Remove all entries.
		_entries.clear();
		
		// Notify the policy state.
		_policyState.clearedAllEntries();
	}
	
	@Override
	protected Maybe<V> setEntry(final K key, final V value) {
		return MapUtils.put(_entries, key, value);
	}
	
	@Override
	protected Maybe<V> getEntry(final K key) {
		return MapUtils.get(_entries, key);
	}
	
	@Override
	protected Maybe<V> removeEntry(final K key) {
		return MapUtils.remove(_entries, key);
	}
}
