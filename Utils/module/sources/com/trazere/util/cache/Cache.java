package com.trazere.util.cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.trazere.util.Assert;
import com.trazere.util.function.Filter;
import com.trazere.util.text.Descriptable;
import com.trazere.util.text.TextUtils;

/**
 * The <code>Cache</code> abstract class provides the core implementation single value caches.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <E> Type of the entries.
 */
public abstract class Cache<K, V, E extends CacheEntry<K, V>>
implements Descriptable {
	/** Cache entries identified by key. */
	protected final Map<K, E> _entriesByKey = new HashMap<K, E>();

	/**
	 * Associate the given value to the given key.
	 * <p>
	 * The possible previous associated value is discarded.
	 * 
	 * @param key Key of the association.
	 * @param value Value of the association.
	 * @return The corresponding cache entry.
	 */
	public E fill(final K key, final V value) {
		// Checks.
		Assert.notNull(key);
		Assert.notNull(value);

		// Fill the cache.
		final E entry = buildEntry(key, value);
		fill(entry);

		return entry;
	}

	/**
	 * Associate no values to the given key.
	 * <p>
	 * The possible previous associated value is discarded.
	 * 
	 * @param key Key of the association.
	 * @return The built cache entry.
	 */
	public E fillNoValues(final K key) {
		// Checks.
		Assert.notNull(key);

		// Fill the cache.
		final E entry = buildEntry(key, null);
		fill(entry);

		return entry;
	}

	/**
	 * Fill the cache with the given entry.
	 * <p>
	 * The possible previous entry associated to key of the given entry is discarded.
	 * 
	 * @param entry Cache entry with which fill the cache.
	 */
	protected void fill(final E entry) {
		addEntry(entry);
		cleanup();
	}

	/**
	 * Get the cache entry for the given key. The value of the retrieved entry may be <code>null</code> if no values are attached to the given key.
	 * 
	 * @param key Association key.
	 * @return The corresponding cache entry, or <code>null</code> if no information is available about the given key.
	 */
	public E get(final K key) {
		// Checks.
		Assert.notNull(key);

		// Get.
		return _entriesByKey.get(key);
	}

	/**
	 * Get all keys which are associated to a value.
	 * 
	 * @return The keys.
	 */
	public Set<K> getAllKeys() {
		final Set<K> keys = new HashSet<K>();
		for (final E entry : _entriesByKey.values()) {
			if (null != entry.getValue()) {
				keys.add(entry.getKey());
			}
		}
		return keys;
	}

	/**
	 * Remove all information about the given key from the cache.
	 * 
	 * @param key Key to clear.
	 * @return The removed cache entry, or <code>null</code> if no information is available about the given key.
	 */
	public E remove(final K key) {
		// Checks.
		Assert.notNull(key);

		// Remove the entry.
		final E entry = _entriesByKey.get(key);
		if (null != entry) {
			// Remove the entry.
			removeEntry(entry);
			return entry;
		} else {
			return null;
		}
	}

	/**
	 * Flush the receiver cache from some information according to the given filter.
	 * 
	 * @param filter Filter of the entries.
	 */
	public void flush(final Filter<? super E> filter) {
		final Iterator<Map.Entry<K, E>> entries = _entriesByKey.entrySet().iterator();
		while (entries.hasNext()) {
			final Map.Entry<K, E> entry = entries.next();
			if (filter.filter(entry.getValue())) {
				entries.remove();
			}
		}
	}

	/**
	 * Flush the receiver cache from all information. All entries are removed.
	 */
	public void flush() {
		_entriesByKey.clear();
	}

	/**
	 * Clean up the cache from the extra entries in order to control its size. This method is called when filling the cache with some entry, and may be called
	 * externally at any time. The default implementation does nothing. Subclasses may override it with the required behavior.
	 */
	public void cleanup() {
		// Nothing to do
	}

	/**
	 * Build a new cache entry with the given key and value. Subclasses should override this factory in order to provide their own cache entry implementation.
	 * 
	 * @param key Key of the entry. The key can be <code>null</code> if the value is not <code>null</code>.
	 * @param value Value of the entry. The value can be <code>null</code> if the key is not <code>null</code>.
	 * @return The built cache entry.
	 */
	protected abstract E buildEntry(final K key, final V value);

	/**
	 * Primitive method which adds the given entry into the receiver cache.
	 * 
	 * @param entry Cache entry to add.
	 */
	protected void addEntry(final E entry) {
		// Checks.
		Assert.notNull(entry);

		// Fill the cache.
		final K key = entry.getKey();
		if (null != key) {
			_entriesByKey.put(key, entry);
		}
	}

	/**
	 * Primitive method which removes the given entry from the receiver cache.
	 * 
	 * @param entry Cache entry to remove.
	 */
	protected void removeEntry(final E entry) {
		// Checks.
		Assert.notNull(entry);

		// Clean the entries.
		_entriesByKey.remove(entry.getKey());
	}

	@Override
	public final String toString() {
		return TextUtils.computeDescription(this);
	}

	public void fillDescription(final StringBuilder builder) {
		builder.append(" - Entries = ").append(_entriesByKey.values());
	}
}
