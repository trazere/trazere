/*
 *  Copyright 2008 Julien Dufour
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

import com.trazere.util.function.Filter2;
import java.util.Set;

/**
 * The {@link Cache} interface defines caches.
 * <p>
 * A cache is a collection of values associated to unique keys which, unlike a regular map, can be flushed at any time according to some policy.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
public interface Cache<K, V> {
	/**
	 * Fill the receiver cache with the given value associating it to the given key.
	 * <p>
	 * The possible previous associated value is discarded.
	 * 
	 * @param key Key.
	 * @param value Value. May be <code>null</code>.
	 */
	public void fill(final K key, final V value);
	
	/**
	 * Test wether a value is associated to the given key in the receiver cache.
	 * 
	 * @param key Key to test.
	 * @return <code>true</code> when a value is associated to the key, <code>false</code> otherwise.
	 */
	public boolean contains(final K key);
	
	/**
	 * Get all keys which values are associated to in the receiver cache.
	 * 
	 * @return The keys.
	 */
	public Set<K> getKeys();
	
	/**
	 * Get the value associated to the given key in the receiver cache.
	 * 
	 * @param key Key.
	 * @return The associated value, or <code>null</code> when no values is associated to the key.
	 */
	public V get(final K key);
	
	/**
	 * Clear the receiver cache from the possible value associated to the given key.
	 * 
	 * @param key Key to clear.
	 * @return The clear value, or <code>null</code> when no values was associated to the key.
	 */
	public V clear(final K key);
	
	/**
	 * Clear the receiver cache according to the given key/value pair filter.
	 * 
	 * @param filter Filter of the key/value associations to use.
	 */
	public void clear(final Filter2<? super K, ? super V> filter);
	
	/**
	 * Clear the receiver cache from all values.
	 */
	public void clear();
	
	/**
	 * Clean the receiver cache up.
	 * <p>
	 * This methods removes the unnecessary values from the cache according to its policy. This maintenance is automatically performed when the cache is
	 * modified, but calling this method manually may still useful with various policies (those depending on time for instance).
	 */
	public void cleanup();
}
