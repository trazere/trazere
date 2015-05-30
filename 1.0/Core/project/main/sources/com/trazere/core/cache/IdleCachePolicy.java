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
package com.trazere.core.cache;

import com.trazere.core.imperative.Accumulator;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The {@link IdleCachePolicy} class implements cache policies based on idle access time.
 * 
 * @param <K> Type of the keys.
 */
public class IdleCachePolicy<K>
implements CachePolicy<K> {
	/**
	 * Instanciates a new cache policy.
	 * 
	 * @param timeout Timeout of the idle entries.
	 */
	public IdleCachePolicy(final Duration timeout) {
		assert null != timeout;
		assert !timeout.isNegative();
		
		// Initialization.
		_timeout = timeout;
	}
	
	// Timeout.
	
	/** Timeout of the idle entries. */
	protected final Duration _timeout;
	
	/**
	 * Get the timeout the idle entries of the receiver policy.
	 * 
	 * @return The timeout.
	 */
	public Duration getTimeout() {
		return _timeout;
	}
	
	// State.
	
	@Override
	public CachePolicy.State<K> build() {
		return new CachePolicy.State<K>() {
			// Note: LinkedHashMap allows to optimize finding dirty entries.
			/** Access dates of the entries. */
			private final LinkedHashMap<K, Instant> _dates = new LinkedHashMap<>();
			
			@Override
			public <A extends Accumulator<? super K, ?>> A updatedEntry(final K key, final A dirtyEntries) {
				return touchEntry(key, dirtyEntries);
			}
			
			@Override
			public <A extends Accumulator<? super K, ?>> A accessedEntry(final K key, final A dirtyEntries) {
				return touchEntry(key, dirtyEntries);
			}
			
			private <A extends Accumulator<? super K, ?>> A touchEntry(final K key, final A dirtyEntries) {
				final Instant now = Instant.now();
				
				// Update the date.
				// Note: date is removed first to maintain the order of the LinkedHashMap
				_dates.remove(key);
				_dates.put(key, now);
				
				// Find the dirty entries.
				for (final Map.Entry<K, Instant> entry : _dates.entrySet()) {
					if (Duration.between(entry.getValue(), now).compareTo(_timeout) >= 0) {
						dirtyEntries.add(entry.getKey());
					} else {
						break;
					}
				}
				return dirtyEntries;
			}
			
			@Override
			public void clearedEntry(final K key) {
				_dates.remove(key);
			}
			
			@Override
			public void clearedAllEntries() {
				_dates.clear();
			}
		};
	}
}
