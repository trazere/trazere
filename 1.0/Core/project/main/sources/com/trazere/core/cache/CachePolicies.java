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

import com.trazere.core.collection.CollectionAccumulators;
import com.trazere.core.functional.Predicates;
import com.trazere.core.imperative.Accumulator;
import com.trazere.core.imperative.AccumulatorUtils;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@link CachePolicies} class provides various factories of {@link CachePolicy cache policies}.
 */
public class CachePolicies {
	/**
	 * Builds a cache policy that preserves all entries.
	 * 
	 * @param <K> Type of the keys.
	 * @return The built policy.
	 */
	@SuppressWarnings("unchecked")
	public static <K> CachePolicy<K> all() {
		return (CachePolicy<K>) ALL;
	}
	
	private static final CachePolicy<?> ALL = new CachePolicy<Object>() {
		@Override
		public CachePolicy.State<Object> build() {
			return ALL_STATE;
		}
	};
	
	private static final CachePolicy.State<Object> ALL_STATE = new CachePolicy.State<Object>() {
		@Override
		public <A extends Accumulator<? super Object, ?>> A updatedEntry(final Object key, final A dirtyEntries) {
			return dirtyEntries;
		}
		
		@Override
		public <A extends Accumulator<? super Object, ?>> A accessedEntry(final Object key, final A dirtyEntries) {
			return dirtyEntries;
		}
		
		@Override
		public void clearedEntry(final Object key) {
			// Nothing to do.
		}
		
		@Override
		public void clearedAllEntries() {
			// Nothing to do.
		}
	};
	
	/**
	 * Builds a cache policy corresponding to the logical disjunction of the given cache policies.
	 * 
	 * @param <K> Type of the keys.
	 * @param policy1 First policy to combine.
	 * @param policy2 Second policy to combine.
	 * @return The built policy.
	 */
	public static <K> CachePolicy<K> or(final CachePolicy<K> policy1, final CachePolicy<K> policy2) {
		assert null != policy1;
		assert null != policy2;
		
		return new CachePolicy<K>() {
			@Override
			public CachePolicy.State<K> build() {
				final CachePolicy.State<K> state1 = policy1.build();
				final CachePolicy.State<K> state2 = policy2.build();
				return new CachePolicy.State<K>() {
					@Override
					public <A extends Accumulator<? super K, ?>> A updatedEntry(final K key, final A dirtyEntries) {
						state1.updatedEntry(key, dirtyEntries);
						state2.updatedEntry(key, dirtyEntries);
						return dirtyEntries;
					}
					
					@Override
					public <A extends Accumulator<? super K, ?>> A accessedEntry(final K key, final A dirtyEntries) {
						state1.accessedEntry(key, dirtyEntries);
						state2.accessedEntry(key, dirtyEntries);
						return dirtyEntries;
					}
					
					@Override
					public void clearedEntry(final K key) {
						state1.clearedEntry(key);
						state2.clearedEntry(key);
					}
					
					@Override
					public void clearedAllEntries() {
						state1.clearedAllEntries();
						state2.clearedAllEntries();
					}
				};
			}
		};
	}
	
	/**
	 * Builds a cache policy corresponding to the logical conjuction of the given cache policies.
	 * 
	 * @param <K> Type of the keys.
	 * @param policy1 First policy to combine.
	 * @param policy2 Second policy to combine.
	 * @return The built policy.
	 */
	public static <K> CachePolicy<K> and(final CachePolicy<K> policy1, final CachePolicy<K> policy2) {
		assert null != policy1;
		assert null != policy2;
		
		return new CachePolicy<K>() {
			@Override
			public CachePolicy.State<K> build() {
				final CachePolicy.State<K> state1 = policy1.build();
				final CachePolicy.State<K> state2 = policy2.build();
				return new CachePolicy.State<K>() {
					@Override
					public <A extends Accumulator<? super K, ?>> A updatedEntry(final K key, final A dirtyEntries) {
						final Set<K> dirtyEntries1 = state1.updatedEntry(key, CollectionAccumulators.add(new HashSet<K>())).get();
						state2.updatedEntry(key, AccumulatorUtils.filter(dirtyEntries, Predicates.values(dirtyEntries1)));
						return dirtyEntries;
					}
					
					@Override
					public <A extends Accumulator<? super K, ?>> A accessedEntry(final K key, final A dirtyEntries) {
						final Set<K> dirtyEntries1 = state1.accessedEntry(key, CollectionAccumulators.add(new HashSet<K>())).get();
						state2.accessedEntry(key, AccumulatorUtils.filter(dirtyEntries, Predicates.values(dirtyEntries1)));
						return dirtyEntries;
					}
					
					@Override
					public void clearedEntry(final K key) {
						state1.clearedEntry(key);
						state2.clearedEntry(key);
					}
					
					@Override
					public void clearedAllEntries() {
						state1.clearedAllEntries();
						state2.clearedAllEntries();
					}
				};
			}
		};
	}
	
	/**
	 * Builds a cache policy based on idle access time.
	 * 
	 * @param <K> Type of the keys.
	 * @param timeout Timeout of the idle entries.
	 * @return The built policy.
	 */
	public <K> CachePolicy<K> idle(final Duration timeout) {
		return new IdleCachePolicy<>(timeout);
	}
	
	/**
	 * Builds a cache policy with a bounded capacity based on the First In/First Out algorithm.
	 * 
	 * @param <K> Type of the keys.
	 * @param capacity Capacity of the cache.
	 * @return The built policy.
	 */
	public <K> CachePolicy<K> fifo(final int capacity) {
		return new FIFOCachePolicy<>(capacity);
	}
	
	/**
	 * Builds a cache policy with a bounded capacity based on the Least Recently Used algorithm.
	 * 
	 * @param <K> Type of the keys.
	 * @param capacity Capacity of the cache.
	 * @return The built policy.
	 */
	public <K> CachePolicy<K> lru(final int capacity) {
		return new LRUCachePolicy<>(capacity);
	}
	
	private CachePolicies() {
		// Prevents instantiation.
	}
}
