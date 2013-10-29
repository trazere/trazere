/*
 *  Copyright 2006-2013 Julien Dufour
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

import java.util.Collection;

/**
 * The {@link CachePolicy} interfaces defines retention policies of caches.
 * 
 * @param <K> Type of the keys.
 * @see Cache
 */
public interface CachePolicy<K> {
	/**
	 * Notifies the receiver cache policy that the value associated to the given key has been accessed.
	 * <p>
	 * This method should populate the dirty entry collection with the cache entries to clean according the policy.
	 * 
	 * @param <C> Type of the dirty key collection.
	 * @param key The key.
	 * @param dirtyEntries The dirty key collection to populate.
	 * @return The given dirty key collection.
	 */
	public <C extends Collection<? super K>> C accessedEntry(final K key, final C dirtyEntries);
	
	/**
	 * Notifies the receiver cache policy that a value has been associated to the given key.
	 * <p>
	 * This method should populate the dirty entry collection with the cache entries to clean according the policy.
	 * 
	 * @param <C> Type of the dirty key collection.
	 * @param key The key.
	 * @param dirtyEntries The dirty key collection to populate.
	 * @return The given dirty key collection.
	 */
	public <C extends Collection<? super K>> C updatedEntry(final K key, final C dirtyEntries);
	
	/**
	 * Notifies the receiver cache policy that the value associated to the given key has been removed.
	 * 
	 * @param key The key.
	 */
	public void removedEntry(final K key);
	
	/**
	 * Notifies the receiver cache policy that all values have been removed.
	 */
	public void removedAllEntries();
}
