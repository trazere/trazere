package com.trazere.util.collection;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.trazere.util.Assert;
import com.trazere.util.CannotComputeValueException;

/**
 * The <code>LazyMap</code> class represents maps which can lazily fill themselves upon access.
 * <p>
 * The backing map supports <code>null</code> keys and values.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
public abstract class LazyMap<K, V> {
	/** Entries. */
	protected final Map<K, V> _entries;

	/**
	 * Build a new empty lazy map.
	 */
	public LazyMap() {
		// Initialization.
		_entries = new HashMap<K, V>();
	}

	/**
	 * Build a new lazy map containing the given entries.
	 * 
	 * @param entries Entries of the map.
	 */
	public LazyMap(final Map<? extends K, ? extends V> entries) {
		Assert.notNull(entries);

		// Initialization.
		_entries = new HashMap<K, V>(entries);
	}

	/**
	 * Associate the given value to the given key in the receiver map.
	 * 
	 * @param key Key which the value should be associated to. May be <code>null</code>.
	 * @param value Value to associate to the key. May be <code>null</code>.
	 * @return The value previously associated to the key, or <code>null</code>.
	 */
	public V put(final K key, final V value) {
		return _entries.put(key, value);
	}

	/**
	 * Test wether the receiver map is empty.
	 * 
	 * @return <code>true</code> if the map is empty, <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return _entries.isEmpty();
	}

	/**
	 * Get the number of entries in the receiver map.
	 * 
	 * @return The number of entries.
	 */
	public int size() {
		return _entries.size();
	}

	/**
	 * Get the value associated to the given key in the receiver map.
	 * <p>
	 * When no values are associated to the given key, this method tries to compute to value for the key and implicif
	 * 
	 * @param key Key which the value is associated to.
	 * @return The value.
	 * @throws CannotComputeValueException
	 */
	public V get(final K key)
	throws CannotComputeValueException {
		// Check the entries.
		if (_entries.containsKey(key)) {
			return _entries.get(key);
		}

		// Compute the value.
		final V value = computeValue(key);
		put(key, value);

		return value;
	}

	/**
	 * Compute the value to associate to the given key.
	 * 
	 * @param key Key whose value should be computed.
	 * @return The computed value. May be <code>null</code>.
	 * @throws CannotComputeValueException
	 */
	protected abstract V computeValue(final K key)
	throws CannotComputeValueException;

	/**
	 * Get the keys of the receiver map.
	 * 
	 * @return An unmodifiable set of the keys.
	 */
	public Set<K> keySet() {
		return Collections.unmodifiableSet(_entries.keySet());
	}

	/**
	 * Get the values of the receiver map.
	 * 
	 * @return An unmodifiable collection of the values.
	 */
	public Collection<V> values() {
		return Collections.unmodifiableCollection(_entries.values());
	}

	/**
	 * Get the entries of the receiver map.
	 * 
	 * @return An unmodifiable set of the entries.
	 */
	public Set<Entry<K, V>> entrySet() {
		return Collections.unmodifiableSet(_entries.entrySet());
	}

	/**
	 * Remove the association of the given key.
	 * 
	 * @param key Key whose association should be removed.
	 * @return The value associated to the key or <code>null</code>.
	 */
	public V remove(final K key) {
		return _entries.remove(key);
	}

	/**
	 * Clear the receiver map.
	 */
	public void clear() {
		_entries.clear();
	}

	@Override
	public String toString() {
		return _entries.toString();
	}
}
