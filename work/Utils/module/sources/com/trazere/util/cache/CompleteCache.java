package com.trazere.util.cache;

import java.util.HashSet;
import java.util.Set;

/**
 * The <code>CompleteCache</code> class represents single value caches which are supposed to contain all existing key/value associations.
 * <p>
 * Ergo, the {@link #get(Object)} method never return <code>null</code>, it will genererate an appriopriate entry indicating that no values are associated to
 * the given key.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
public class CompleteCache<K, V>
extends Cache<K, V, CacheEntry<K, V>> {
	@Override
	public CacheEntry<K, V> fillNoValues(final K key) {
		// Remove the entry for the key.
		remove(key);
		
		// Return a placebo entry.
		return buildEntry(key, null);
	}
	
	@Override
	public CacheEntry<K, V> get(final K key) {
		final CacheEntry<K, V> entry = super.get(key);
		return null != entry ? entry : buildEntry(key, null);
	}
	
	@Override
	public Set<K> getAllKeys() {
		return new HashSet<K>(_entriesByKey.keySet());
	}
	
	@Override
	protected CacheEntry<K, V> buildEntry(final K key, final V value) {
		return new CacheEntry<K, V>(key, value);
	}
}
