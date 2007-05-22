package com.trazere.util.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * The <code>FiniteCache</code> class represents single value caches whose size is controled according to the following parameters:
 * <ul>
 * <li>The capacity of the cache. The cache will not grow bigger than its capacity, unless it would infringe the minimum life time of the entries (see below).
 * <li>The minimum life time of the entries. The minimum time each entry stays in the cache before it can be cleaned up.
 * <li>The maximum life time of the entries. The maximim time each entry can stays in the cache after which it is cleaned up.
 * </ul>
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
public class FiniteCache<K, V>
extends Cache<K, V, TimedCacheEntry<K, V>> {
	/** List of the entries ordered by creation date. */
	protected final List<TimedCacheEntry<K, V>> _history = new LinkedList<TimedCacheEntry<K, V>>();

	/** Capacity of the cache. <code>0</code> means an infinite capacity. */
	protected final int _capacity;

	/** Minimum life time of the entries of the cache in millisedonds. <code>0</code> means no minimum life time. */
	protected final long _minLifeTime;

	/** Maximim life time of the entries of the cache in millisedonds. <code>0</code> means no maximum life time. */
	protected final long _maxLifeTime;

	/**
	 * Instantiate a new cache with the given parameters.
	 * 
	 * @param capacity Capacity of the cache. <code>0</code> means an infinite capacity.
	 * @param minLifeTime Minimum life time of the entries of the cache. <code>0</code> means no minimym life time.
	 * @param maxLifeTime Maximim life time of the entries of the cache. <code>0</code> means no maximum life time.
	 */
	public FiniteCache(final int capacity, final long minLifeTime, final long maxLifeTime) {
		_capacity = capacity;
		_minLifeTime = minLifeTime;
		_maxLifeTime = 0 == maxLifeTime || minLifeTime <= maxLifeTime ? maxLifeTime : minLifeTime;
	}

	/**
	 * Get the capacity of the cache.
	 * 
	 * @return The capacity. <code>0</code> means an infinite capacity.
	 */
	public int getCapacity() {
		return _capacity;
	}

	/**
	 * Get the minimum life time of the entries of the cache.
	 * 
	 * @return The minimum life time in millisecond. <code>0</code> means no minimum life time.
	 */
	public long getMinLifeTime() {
		return _minLifeTime;
	}

	/**
	 * Get the maximum life time of the entries of the cache.
	 * 
	 * @return The maximum life time in millisecond. <code>0</code> means no maximum life time.
	 */
	public long getMaxLifeTime() {
		return _maxLifeTime;
	}

	@Override
	public void flush() {
		super.flush();

		// Clear the history.
		_history.clear();
	}

	/**
	 * Clean up the cache from the extra entries in order to control its size. The oldest entries are removed:
	 * <ul>
	 * <li>if their life time has reached the maximum,
	 * <li>if their life time has reached the minimum and if they exceed the capacity of the cache.
	 * </ul>
	 */
	@Override
	public void cleanup() {
		super.cleanup();

		final long time = System.currentTimeMillis();
		final int capacity = _capacity > 0 ? _capacity : _history.size();

		// Find the oldest entries to clean up.
		final Collection<TimedCacheEntry<K, V>> entriesToClean = new ArrayList<TimedCacheEntry<K, V>>();
		final ListIterator<TimedCacheEntry<K, V>> entries = _history.listIterator(_history.size());
		while (entries.hasPrevious()) {
			final TimedCacheEntry<K, V> entry = entries.previous();

			// Remove the entry if needed.
			final long lifeTime = time - entry.getTimestamp();
			if ((_minLifeTime <= 0 || lifeTime >= _minLifeTime) && ((capacity > 0 && _history.size() > capacity) || (_maxLifeTime > 0 && lifeTime > _maxLifeTime))) {
				entriesToClean.add(entry);
			} else {
				break;
			}
		}

		// Clean the entries.
		for (final TimedCacheEntry<K, V> entry : entriesToClean) {
			removeEntry(entry);
		}
	}

	@Override
	protected TimedCacheEntry<K, V> buildEntry(final K key, final V value) {
		return new TimedCacheEntry<K, V>(key, value);
	}

	@Override
	protected void addEntry(final TimedCacheEntry<K, V> entry) {
		super.addEntry(entry);

		// Complete the history.
		_history.add(0, entry);
	}

	@Override
	protected void removeEntry(final TimedCacheEntry<K, V> entry) {
		super.removeEntry(entry);

		// Clean the history.
		_history.remove(entry);
	}
}
