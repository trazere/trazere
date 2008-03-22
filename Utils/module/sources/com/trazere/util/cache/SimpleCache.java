package com.trazere.util.cache;

/**
 * DOCME
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
public class SimpleCache<K, V>
extends Cache<K, V, CacheEntry<K, V>> {
	@Override
	protected CacheEntry<K, V> buildEntry(final K key, final V value) {
		return new CacheEntry<K, V>(key, value);
	}
}
