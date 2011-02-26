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

/**
 * The {@link SimpleCache} class implements caches of unlimited size with no cleaning policies.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
public class SimpleCache<K, V>
extends AbstractCache<K, V, CacheEntry<K, V>> {
	@Override
	protected CacheEntry<K, V> buildEntry(final K key, final V value) {
		return new CacheEntry<K, V>(key, value);
	}
	
	@Override
	protected void addedEntry(final CacheEntry<K, V> entry) {
		// Nothing to do.
	}
	
	@Override
	protected void clearedEntry(final CacheEntry<K, V> entry) {
		// Nothing to do.
	}
	
	@Override
	protected void clearedCache() {
		// Nothing to do.
	}
	
	public void cleanup() {
		// Nothing to do.
	}
}
