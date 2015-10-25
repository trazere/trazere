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

import com.trazere.core.design.Factory;
import com.trazere.core.imperative.Accumulator;

/**
 * The {@link CachePolicy} interfaces defines retention policies of caches.
 * <p>
 * Cache policies are factories of retention states that implement the retention logic of the caches according to their parent policy.
 * 
 * @param <K> Type of the keys.
 * @see Cache
 * @since 2.0
 */
public interface CachePolicy<K>
extends Factory<CachePolicy.State<K>> {
	/**
	 * The {@link CachePolicy.State} interface defines states of cache retention policies.
	 * <p>
	 * Each retention state is a stateful component that implements the retention logic of a single cache instance.
	 * 
	 * @param <K> Type of the keys.
	 * @since 2.0
	 */
	public interface State<K> {
		/**
		 * Notifies this cache policy state that the entry associated to the given key has been updated.
		 * <p>
		 * This method should populate the given dirty entry accumulator with keys of the entries to clear from the cache.
		 * 
		 * @param <A> Type of the dirty entry accumulator.
		 * @param key Key of the updated entry.
		 * @param dirtyEntries Accumulator to populate with the keys of the dirty entries to clear from the cache.
		 * @return The given dirty entry accumulator.
		 * @since 2.0
		 */
		<A extends Accumulator<? super K, ?>> A updatedEntry(K key, A dirtyEntries);
		
		/**
		 * Notifies this cache policy state that the entry associated to the given key has been accessed in the the cache.
		 * <p>
		 * This method should populate the given dirty entry accumulator with keys of the entries to clear from the cache.
		 * 
		 * @param <A> Type of the dirty entry accumulator.
		 * @param key Key of the accessed entry.
		 * @param dirtyEntries Accumulator to populate with the keys of the dirty entries to clear from the cache.
		 * @return The given dirty entry accumulator.
		 * @since 2.0
		 */
		<A extends Accumulator<? super K, ?>> A accessedEntry(K key, A dirtyEntries);
		
		/**
		 * Notifies this cache policy state that the entry associated to the given key has been cleared from the cache.
		 * 
		 * @param key Key of the removed entry.
		 * @since 2.0
		 */
		void clearedEntry(K key);
		
		/**
		 * Notifies this cache policy state that all values have been cleared from the cache.
		 * 
		 * @since 2.0
		 */
		void clearedAllEntries();
	}
}
