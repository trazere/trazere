/*
 *  Copyright 2006-2011 Julien Dufour
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

import com.trazere.util.type.Maybe;
import java.util.LinkedList;

/**
 * The {@link BaseFiniteCache} class implements caches of limited size according to the following parameters:
 * <ul>
 * <li>The capacity of the cache. The cache will not grow bigger than its capacity, unless it would infringe the minimum life time of its entries (see below).
 * <li>The minimum lifetime of the entries. The minimum time each entry stays in the cache before it can be cleaned up.
 * <li>The maximum lifetime of the entries. The maximum time each entry can stays in the cache after which it is cleaned up.
 * </ul>
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <E> Type of the entries.
 */
public abstract class BaseFiniteCache<K, V, E extends TimedCacheEntry<K, V>>
extends BaseCache<K, V, E> {
	/**
	 * Instantiates a new cache with the given parameters.
	 * 
	 * @param capacity The capacity of the cache.
	 * @param minLifeTime The minimum life time of the entries.
	 * @param maxLifeTime The maximum life time of the entries.
	 */
	public BaseFiniteCache(final Maybe<Integer> capacity, final Maybe<Long> minLifeTime, final Maybe<Long> maxLifeTime) {
		assert null != capacity;
		assert null != minLifeTime;
		assert null != maxLifeTime;
		assert minLifeTime.isNone() || maxLifeTime.isNone() || minLifeTime.asSome().getValue() <= maxLifeTime.asSome().getValue();
		
		// Initialization.
		_capacity = capacity;
		_minLifeTime = minLifeTime;
		_maxLifeTime = maxLifeTime;
	}
	
	// Capacity.
	
	/** Capacity of the receiver cache. */
	protected final Maybe<Integer> _capacity;
	
	/**
	 * Gets the capacity of the cache.
	 * 
	 * @return The capacity.
	 */
	public Maybe<Integer> getCapacity() {
		return _capacity;
	}
	
	// Life time.
	
	/** Minimum life time of the entries in the cache in millisedonds. */
	protected final Maybe<Long> _minLifeTime;
	
	/** Maximim life time of the entries in the cache in millisedonds. */
	protected final Maybe<Long> _maxLifeTime;
	
	/**
	 * Gets the minimum life time of the entries in the receiver cache.
	 * 
	 * @return The minimum life time in millisecond.
	 */
	public Maybe<Long> getMinLifeTime() {
		return _minLifeTime;
	}
	
	/**
	 * Get the maximum life time of the entries in the receiver cache.
	 * 
	 * @return The maximum life time in millisecond.
	 */
	public Maybe<Long> getMaxLifeTime() {
		return _maxLifeTime;
	}
	
	// Cache.
	
	@Override
	public void clear() {
		super.clear();
		
		// Clear the history.
		_history.clear();
	}
	
	@Override
	public void cleanup() {
		final long time = System.currentTimeMillis();
		while (!_history.isEmpty()) {
			final TimedCacheEntry<K, V> entry = _history.getLast();
			final long lifeTime = time - entry.getDate();
			final boolean exceededinLifeTime = _minLifeTime.isNone() || lifeTime >= _minLifeTime.asSome().getValue();
			final boolean exceededCapacity = _capacity.isSome() && _history.size() > _capacity.asSome().getValue().intValue();
			final boolean exceededMaxLifeTime = _maxLifeTime.isSome() && lifeTime > _maxLifeTime.asSome().getValue();
			if (exceededinLifeTime && (exceededCapacity || exceededMaxLifeTime)) {
				removeEntry(entry.getKey());
			} else {
				break;
			}
		}
	}
	
	/** List of the entries ordered by creation date. */
	protected final LinkedList<TimedCacheEntry<K, V>> _history = new LinkedList<TimedCacheEntry<K, V>>();
	
	@Override
	protected Maybe<E> putEntry(final E entry) {
		final Maybe<E> previousEntry = super.putEntry(entry);
		
		// Update the history.
		if (previousEntry.isSome()) {
			_history.remove(previousEntry.asSome().getValue());
		}
		_history.add(0, entry);
		
		return previousEntry;
	}
	
	@Override
	protected Maybe<E> removeEntry(final K key) {
		final Maybe<E> entry = super.removeEntry(key);
		
		// Update the history.
		if (entry.isSome()) {
			_history.remove(entry.asSome().getValue());
		}
		
		return entry;
	}
}
