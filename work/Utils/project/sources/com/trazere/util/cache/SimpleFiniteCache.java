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

/**
 * The {@link SimpleFiniteCache} class implements caches of limited size according to the following parameters:
 * <ul>
 * <li>The capacity of the cache. The cache will not grow bigger than its capacity, unless it would infringe the minimum life time of its entries (see below).
 * <li>The minimum lifetime of the entries. The minimum time each entry stays in the cache before it can be cleaned up.
 * <li>The maximum lifetime of the entries. The maximum time each entry can stays in the cache after which it is cleaned up.
 * </ul>
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
public class SimpleFiniteCache<K, V>
extends BaseFiniteCache<K, V, TimedCacheEntry<K, V>> {
	/**
	 * Instantiates a new cache with the given parameters.
	 * 
	 * @param capacity The capacity of the cache.
	 * @param minLifeTime The minimum life time of the entries.
	 * @param maxLifeTime The maximum life time of the entries.
	 */
	public SimpleFiniteCache(final Maybe<Integer> capacity, final Maybe<Long> minLifeTime, final Maybe<Long> maxLifeTime) {
		super(capacity, minLifeTime, maxLifeTime);
	}
	
	// Entry.
	
	@Override
	protected TimedCacheEntry<K, V> buildEntry(final K key, final V value) {
		return new TimedCacheEntry<K, V>(key, value);
	}
}
