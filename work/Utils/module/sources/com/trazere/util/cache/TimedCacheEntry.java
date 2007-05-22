package com.trazere.util.cache;

/**
 * The <code>TimeCacheEntry</code> class represents timed cache entries, which are cache entries which keep track of their creation date.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
public class TimedCacheEntry<K, V>
extends CacheEntry<K, V> {
	/** Creation date of the entry. */
	protected final long _timestamp;

	/**
	 * Instantiate a new cache entry with the given key and value, and set its creation date to now.
	 * 
	 * @param key Key of the entry. The key can be <code>null</code> if the value is not <code>null</code>.
	 * @param value Value of the entry. The value can be <code>null</code> if the key is not <code>null</code>.
	 */
	protected TimedCacheEntry(final K key, final V value) {
		super(key, value);

		// Set the creation date.
		_timestamp = System.currentTimeMillis();
	}

	/**
	 * Get the creation date of the receiver entry.
	 * 
	 * @return The creation date in milliseconds.
	 * @see System#currentTimeMillis()
	 */
	public long getTimestamp() {
		return _timestamp;
	}

	@Override
	public void fillDescription(final StringBuilder builder) {
		super.fillDescription(builder);
		builder.append(" - Timestamp = ");
		builder.append(_timestamp);
	}
}
