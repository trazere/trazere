/*
 *  Copyright 2006-2008 Julien Dufour
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

import com.trazere.util.function.Predicate2;
import com.trazere.util.text.Describable;
import com.trazere.util.text.TextUtils;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * The {@link AbstractCache} abstract class implements skeletons of {@link Cache caches}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <E> Type of the entries.
 */
public abstract class AbstractCache<K, V, E extends CacheEntry<K, V>>
implements Cache<K, V>, Describable {
	/** Cache entries identified by key. */
	protected final Map<K, E> _entries = new HashMap<K, E>();
	
	public void fill(final K key, final V value) {
		assert null != key;
		
		// Add the entry.
		final E entry = buildEntry(key, value);
		final E currentEntry = _entries.put(entry.getKey(), entry);
		
		clearedEntry(currentEntry);
		addedEntry(entry);
	}
	
	public boolean contains(final K key) {
		assert null != key;
		
		// Test.
		return _entries.containsKey(key);
	}
	
	public Set<K> getKeys() {
		return Collections.unmodifiableSet(_entries.keySet());
	}
	
	public V get(final K key) {
		assert null != key;
		
		// Get.
		final E entry = _entries.get(key);
		return null != entry ? entry.getValue() : null;
	}
	
	public V clear(final K key) {
		assert null != key;
		
		// Clear.
		final E entry = _entries.remove(key);
		if (null != entry) {
			clearedEntry(entry);
			return entry.getValue();
		} else {
			return null;
		}
	}
	
	public <X extends Exception> void clear(final Predicate2<? super K, ? super V, X> filter)
	throws X {
		assert null != filter;
		
		// Filter.
		final Iterator<Map.Entry<K, E>> entries = _entries.entrySet().iterator();
		while (entries.hasNext()) {
			final Map.Entry<K, E> entryEntry = entries.next();
			final E entry = entryEntry.getValue();
			if (filter.evaluate(entryEntry.getKey(), entry.getValue())) {
				entries.remove();
				clearedEntry(entry);
			}
		}
	}
	
	public void clear() {
		_entries.clear();
		clearedCache();
	}
	
	/**
	 * Build a new cache entry with the given key and value.
	 * 
	 * @param key Key.
	 * @param value Value. May be <code>null</code>.
	 * @return The built cache entry.
	 */
	protected abstract E buildEntry(final K key, final V value);
	
	/**
	 * Notify that the given entry was added to the receiver cache.
	 * 
	 * @param entry The entry which was added.
	 */
	protected abstract void addedEntry(final E entry);
	
	/**
	 * Notify that the given entry was removed from the receiver cache.
	 * 
	 * @param entry The entry which was removed.
	 */
	protected abstract void clearedEntry(final E entry);
	
	/**
	 * Notify that the all entries were removed from the receiver cache.
	 */
	protected abstract void clearedCache();
	
	@Override
	public final String toString() {
		return TextUtils.computeDescription(this);
	}
	
	public void fillDescription(final StringBuilder builder) {
		builder.append(" - Entries = ").append(_entries.values());
	}
}
