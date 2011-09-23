package com.trazere.util.cache;

import com.trazere.util.collection.CollectionUtils;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * DOCME
 */
public class CacheUtils {
	/**
	 * Builds a cache policy which combine the given cache policies by disjunction.
	 * 
	 * @param <K> Type of the keys.
	 * @param policy1 The first policy.
	 * @param policy2 The second policy.
	 * @return The built policy.
	 */
	public static <K> CachePolicy<K> or(final CachePolicy<K> policy1, final CachePolicy<K> policy2) {
		assert null != policy1;
		assert null != policy2;
		
		return new CachePolicy<K>() {
			public <C extends Collection<? super K>> C accessedEntry(final K key, final C dirtyEntries) {
				policy1.accessedEntry(key, dirtyEntries);
				policy2.accessedEntry(key, dirtyEntries);
				return dirtyEntries;
			}
			
			public <C extends Collection<? super K>> C updatedEntry(final K key, final C dirtyEntries) {
				policy1.updatedEntry(key, dirtyEntries);
				policy2.updatedEntry(key, dirtyEntries);
				return dirtyEntries;
			}
			
			public void removedEntry(final K key) {
				policy1.removedEntry(key);
				policy2.removedEntry(key);
			}
			
			public void removedAllEntries() {
				policy1.removedAllEntries();
				policy2.removedAllEntries();
			}
		};
	}
	
	/**
	 * Builds a cache policy which combine the given cache policies by conjuction.
	 * 
	 * @param <K> Type of the keys.
	 * @param policy1 The first policy.
	 * @param policy2 The second policy.
	 * @return The built policy.
	 */
	public static <K> CachePolicy<K> and(final CachePolicy<K> policy1, final CachePolicy<K> policy2) {
		assert null != policy1;
		assert null != policy2;
		
		return new CachePolicy<K>() {
			public <C extends Collection<? super K>> C accessedEntry(final K key, final C dirtyEntries) {
				final Set<K> dirtyEntries1 = policy1.accessedEntry(key, new HashSet<K>());
				final Set<K> dirtyEntries2 = policy2.accessedEntry(key, new HashSet<K>());
				return CollectionUtils.intersection(dirtyEntries1, dirtyEntries2, dirtyEntries);
			}
			
			public <C extends Collection<? super K>> C updatedEntry(final K key, final C dirtyEntries) {
				final Set<K> dirtyEntries1 = policy1.updatedEntry(key, new HashSet<K>());
				final Set<K> dirtyEntries2 = policy2.updatedEntry(key, new HashSet<K>());
				return CollectionUtils.intersection(dirtyEntries1, dirtyEntries2, dirtyEntries);
			}
			
			public void removedEntry(final K key) {
				policy1.removedEntry(key);
				policy2.removedEntry(key);
			}
			
			public void removedAllEntries() {
				policy1.removedAllEntries();
				policy2.removedAllEntries();
			}
		};
	}
	
	private CacheUtils() {
		// Prevents instantiation.
	}
}
