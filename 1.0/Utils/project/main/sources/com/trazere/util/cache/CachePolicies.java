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

import com.trazere.util.collection.CollectionUtils;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@link CachePolicies} class provides various factories of cache policies.
 * 
 * @deprecated Use core.
 */
@Deprecated
public class CachePolicies {
	/**
	 * Builds a cache policy that keeps all entries.
	 * 
	 * @param <K> Type of the keys.
	 * @return The built policy.
	 * @deprecated Use {@link com.trazere.core.cache.CachePolicies#all()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <K> CachePolicy<K> all() {
		return (CachePolicy<K>) _ALL;
	}
	
	@Deprecated
	private static final CachePolicy<?> _ALL = new AllCachePolicy<Object>();
	
	/**
	 * Builds a cache policy that combines the given cache policies by disjunction.
	 * 
	 * @param <K> Type of the keys.
	 * @param policy1 The first policy.
	 * @param policy2 The second policy.
	 * @return The built policy.
	 * @deprecated Use {@link com.trazere.core.cache.CachePolicies#or(com.trazere.core.cache.CachePolicy, com.trazere.core.cache.CachePolicy)}.
	 */
	@Deprecated
	public static <K> CachePolicy<K> or(final CachePolicy<K> policy1, final CachePolicy<K> policy2) {
		assert null != policy1;
		assert null != policy2;
		
		return new CachePolicy<K>() {
			@Override
			public <C extends Collection<? super K>> C accessedEntry(final K key, final C dirtyEntries) {
				policy1.accessedEntry(key, dirtyEntries);
				policy2.accessedEntry(key, dirtyEntries);
				return dirtyEntries;
			}
			
			@Override
			public <C extends Collection<? super K>> C updatedEntry(final K key, final C dirtyEntries) {
				policy1.updatedEntry(key, dirtyEntries);
				policy2.updatedEntry(key, dirtyEntries);
				return dirtyEntries;
			}
			
			@Override
			public void removedEntry(final K key) {
				policy1.removedEntry(key);
				policy2.removedEntry(key);
			}
			
			@Override
			public void removedAllEntries() {
				policy1.removedAllEntries();
				policy2.removedAllEntries();
			}
		};
	}
	
	/**
	 * Builds a cache policy that combines the given cache policies by conjuction.
	 * 
	 * @param <K> Type of the keys.
	 * @param policy1 The first policy.
	 * @param policy2 The second policy.
	 * @return The built policy.
	 * @deprecated Use {@link com.trazere.core.cache.CachePolicies#and(com.trazere.core.cache.CachePolicy, com.trazere.core.cache.CachePolicy)}.
	 */
	@Deprecated
	public static <K> CachePolicy<K> and(final CachePolicy<K> policy1, final CachePolicy<K> policy2) {
		assert null != policy1;
		assert null != policy2;
		
		return new CachePolicy<K>() {
			@Override
			public <C extends Collection<? super K>> C accessedEntry(final K key, final C dirtyEntries) {
				final Set<K> dirtyEntries1 = policy1.accessedEntry(key, new HashSet<K>());
				final Set<K> dirtyEntries2 = policy2.accessedEntry(key, new HashSet<K>());
				return CollectionUtils.intersection(dirtyEntries1, dirtyEntries2, dirtyEntries);
			}
			
			@Override
			public <C extends Collection<? super K>> C updatedEntry(final K key, final C dirtyEntries) {
				final Set<K> dirtyEntries1 = policy1.updatedEntry(key, new HashSet<K>());
				final Set<K> dirtyEntries2 = policy2.updatedEntry(key, new HashSet<K>());
				return CollectionUtils.intersection(dirtyEntries1, dirtyEntries2, dirtyEntries);
			}
			
			@Override
			public void removedEntry(final K key) {
				policy1.removedEntry(key);
				policy2.removedEntry(key);
			}
			
			@Override
			public void removedAllEntries() {
				policy1.removedAllEntries();
				policy2.removedAllEntries();
			}
		};
	}
	
	private CachePolicies() {
		// Prevents instantiation.
	}
}
