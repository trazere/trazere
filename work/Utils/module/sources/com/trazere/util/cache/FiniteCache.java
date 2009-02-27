/*
 *  Copyright 2006-2009 Julien Dufour
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

import java.util.LinkedList;

/**
 * The {@link FiniteCache} class implements caches of limited size according to the following parameters:
 * <ul>
 * <li>The capacity of the cache. The cache will not grow bigger than its capacity, unless it would infringe the minimum life time of its entries (see below).
 * <li>The minimum lifetime of the entries. The minimum time each entry stays in the cache before it can be cleaned up.
 * <li>The maximum lifetime of the entries. The maximum time each entry can stays in the cache after which it is cleaned up.
 * </ul>
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
public class FiniteCache<K, V>
extends AbstractCache<K, V, TimedCacheEntry<K, V>> {
	/** Default capacity of the finite cache. */
	public static final int UNLIMITED_CAPACITY = 0;
	
	/** Default minimun lifetime of the elements of the finite cache. */
	public static final int UNLIMITED_MIN_LIFETIME = 0;
	
	/** Default maximum lifetime of the elements of the finite cache. */
	public static final int UNLIMITED_MAX_LIFETIME = 0;
	
	/** List of the entries ordered by creation date. */
	protected final LinkedList<TimedCacheEntry<K, V>> _history = new LinkedList<TimedCacheEntry<K, V>>();
	
	/** Capacity of the cache. <code>0</code> means an unlimited capacity. */
	protected final int _capacity;
	
	/** Minimum life time of the entries in millisedonds. <code>0</code> means no minimum life time. */
	protected final long _minLifeTime;
	
	/** Maximim life time of the entries in millisedonds. <code>0</code> means no maximum life time. */
	protected final long _maxLifeTime;
	
	/**
	 * Instantiate a new cache with the given parameters.
	 * 
	 * @param capacity Capacity of the cache. <code>0</code> means an unlimited capacity.
	 * @param minLifeTime Minimum life time of the entries. <code>0</code> means no minimym life time.
	 * @param maxLifeTime Maximum life time of the entries. <code>0</code> means no maximum life time.
	 */
	public FiniteCache(final int capacity, final long minLifeTime, final long maxLifeTime) {
		// Initialization.
		_capacity = capacity;
		_minLifeTime = minLifeTime;
		_maxLifeTime = UNLIMITED_MAX_LIFETIME == maxLifeTime || minLifeTime <= maxLifeTime ? maxLifeTime : minLifeTime;
	}
	
	/**
	 * Get the capacity of the cache.
	 * 
	 * @return The capacity. <code>0</code> means an unlimited capacity.
	 */
	public int getCapacity() {
		return _capacity;
	}
	
	/**
	 * Get the minimum life time of the entries of the cache.
	 * 
	 * @return The minimum life time in millisecond. <code>0</code> means no minimum life time.
	 */
	public long getMinLifeTime() {
		return _minLifeTime;
	}
	
	/**
	 * Get the maximum life time of the entries of the cache.
	 * 
	 * @return The maximum life time in millisecond. <code>0</code> means no maximum life time.
	 */
	public long getMaxLifeTime() {
		return _maxLifeTime;
	}
	
	@Override
	protected TimedCacheEntry<K, V> buildEntry(final K key, final V value) {
		return new TimedCacheEntry<K, V>(key, value);
	}
	
	@Override
	protected void addedEntry(final TimedCacheEntry<K, V> entry) {
		// Complete the history.
		_history.add(0, entry);
		
		// Clean up.
		cleanup();
	}
	
	@Override
	protected void clearedEntry(final TimedCacheEntry<K, V> entry) {
		// Clean the history.
		_history.remove(entry);
	}
	
	@Override
	protected void clearedCache() {
		// Clear the history.
		_history.clear();
	}
	
	public void cleanup() {
		final long time = System.currentTimeMillis();
		while (!_history.isEmpty()) {
			final TimedCacheEntry<K, V> entry = _history.getLast();
			final long lifeTime = time - entry.getDate();
			if ((_minLifeTime <= 0 || lifeTime >= _minLifeTime) && ((_capacity > 0 && _history.size() > _capacity) || (_maxLifeTime > 0 && lifeTime > _maxLifeTime))) {
				_entries.remove(entry.getKey());
				_history.removeLast();
			} else {
				break;
			}
		}
	}
}
