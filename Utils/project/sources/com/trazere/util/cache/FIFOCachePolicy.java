package com.trazere.util.cache;

import java.util.Collection;
import java.util.LinkedList;

/**
 * The {@link FIFOCachePolicy} class provides cache policies based on the First In/First Out algorithm.
 * 
 * @param <K> Type of the keys.
 */
public class FIFOCachePolicy<K>
implements CachePolicy<K> {
	/**
	 * Instanciates a new cache policy.
	 * 
	 * @param capacity The capacity of the cache.
	 */
	public FIFOCachePolicy(final int capacity) {
		assert capacity > 0;
		
		// Initialization.
		_capacity = capacity;
	}
	
	// Capacity.
	
	/** Capacity of the receiver cache. */
	protected final int _capacity;
	
	/**
	 * Gets the capacity of the cache.
	 * 
	 * @return The capacity.
	 */
	public int getCapacity() {
		return _capacity;
	}
	
	// Entries.
	
	protected final LinkedList<K> _uses = new LinkedList<K>();
	
	public <C extends Collection<? super K>> C accessedEntry(final K key, final C dirtyEntries) {
		return dirtyEntries;
	}
	
	public <C extends Collection<? super K>> C updatedEntry(final K key, final C dirtyEntries) {
		assert null != key;
		
		// Update the uses.
		// Note: the first keys comes first to optimize the cleanups.
		_uses.remove(key);
		_uses.addLast(key);
		
		// Find the dirty entries.
		return findDirtyEntries(dirtyEntries);
	}
	
	public void removedEntry(final K key) {
		assert null != key;
		
		_uses.remove(key);
	}
	
	public void removedAllEntries() {
		_uses.clear();
	}
	
	protected <C extends Collection<? super K>> C findDirtyEntries(final C dirtyEntries) {
		assert null != dirtyEntries;
		
		final int size = _uses.size();
		if (size > _capacity) {
			dirtyEntries.addAll(_uses.subList(0, size - _capacity));
		}
		return dirtyEntries;
	}
}
