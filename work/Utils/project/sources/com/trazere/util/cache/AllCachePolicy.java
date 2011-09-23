package com.trazere.util.cache;

import java.util.Collection;

/**
 * The {@link FIFOCachePolicy} class provides cache policies which keeps all entries.
 * 
 * @param <K> Type of the keys.
 */
public class AllCachePolicy<K>
implements CachePolicy<K> {
	public <C extends Collection<? super K>> C accessedEntry(final K key, final C dirtyEntries) {
		return dirtyEntries;
	}
	
	public <C extends Collection<? super K>> C updatedEntry(final K key, final C dirtyEntries) {
		return dirtyEntries;
	}
	
	public void removedEntry(final K key) {
		// Nothing to do.
	}
	
	public void removedAllEntries() {
		// Nothing to do.
	}
}
