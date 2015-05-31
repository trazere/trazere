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

import com.trazere.core.functional.Predicate;
import com.trazere.core.util.Maybe;
import java.util.Set;

/**
 * The {@link Cache} interface defines caches.
 * <p>
 * Caches are collections of values associated to keys similar to maps. However, unlike regular maps, the entries can implicitely be removed according to the
 * retention policy of the cache.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @since 1.0
 */
public interface Cache<K, V> {
	/**
	 * Fills this cache with an entry that associates the given value to the given key.
	 * <p>
	 * The possible current entry associated to the key is replaced.
	 * 
	 * @param key Key of the entry.
	 * @param value Value of the entry.
	 * @return The previous value of the entry, or nothing when no entries was associated to the key.
	 * @since 1.0
	 */
	Maybe<V> fill(K key, V value);
	
	/**
	 * Indicates whether this cache contains entries.
	 * 
	 * @return <code>true</code> when the cache contains no entries, <code>false</code> otherwise.
	 * @since 1.0
	 */
	boolean isEmpty();
	
	/**
	 * Gets the number of entries of this cache.
	 * 
	 * @return The number of entries.
	 * @since 1.0
	 */
	int size();
	
	/**
	 * Tests whether an entry is associated to the given key in this cache.
	 * 
	 * @param key Key to test.
	 * @return <code>true</code> when an entry is associated to the key, <code>false</code> otherwise.
	 * @since 1.0
	 */
	boolean contains(K key);
	
	/**
	 * Gets the keys of all entries of this cache.
	 * 
	 * @return An unmodifiable set of the keys.
	 * @since 1.0
	 */
	Set<K> keys();
	
	/**
	 * Gets the value associated to the given key in this cache.
	 * 
	 * @param key Key whose associated value should be returned.
	 * @return The value of the entry associated to the key, or nothing when no entries is associated to the key.
	 * @since 1.0
	 */
	Maybe<V> get(K key);
	
	/**
	 * Clears the entry of this cache associated to the given key.
	 * 
	 * @param key Key of the entry to clear.
	 * @since 1.0
	 */
	void clear(K key);
	
	/**
	 * Clears the entries of this cache associated to key accepted by the given filter.
	 * 
	 * @param filter Predicated to use to filter the entries to clear.
	 * @since 1.0
	 */
	void clear(Predicate<? super K> filter);
	
	/**
	 * Clears all entries of this cache.
	 * 
	 * @since 1.0
	 */
	void clear();
}
