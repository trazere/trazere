/*
 *  Copyright 2006-2010 Julien Dufour
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
package com.trazere.util.collection;

import com.trazere.util.function.Function1;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * The {@link LazyMap} class represents maps which can lazily fill themselves upon access.
 * <p>
 * The backing map supports <code>null</code> keys and values.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <X> Type of the exceptions.
 */
public abstract class LazyMap<K, V, X extends Exception>
implements Function1<K, V, X> {
	/**
	 * Build a lazy map using the given function.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param function Function computing the values of the map.
	 * @return The built lazy map.
	 */
	public static <K, V, X extends Exception> LazyMap<K, V, X> build(final Function1<K, V, X> function) {
		assert null != function;
		
		// Build.
		return new LazyMap<K, V, X>() {
			@Override
			protected V compute(final K key)
			throws X {
				return function.evaluate(key);
			}
		};
	}
	
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
		assert null != entries;
		
		// Initialization.
		_entries = new HashMap<K, V>(entries);
	}
	
	/**
     * Test whether the receiver map is empty.
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
     * Remove the association of the given key.
     * 
     * @param key Key whose association should be removed. May be <code>null</code>.
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

	/**
	 * Get the value associated to the given key in the receiver map.
	 * <p>
	 * When no values are associated to the given key, this method tries to compute to value for the key and implicif
	 * 
	 * @param key Key which the value is associated to. May be <code>null</code>.
	 * @return The value.
	 * @throws X When the value cannot be computed.
	 */
	public V get(final K key)
	throws X {
		// Check the entries.
		if (_entries.containsKey(key)) {
			return _entries.get(key);
		}
		
		// Compute the value.
		final V value = compute(key);
		put(key, value);
		
		return value;
	}
	
	/**
	 * Compute the value to associate to the given key.
	 * 
	 * @param key Key whose value should be computed. May be <code>null</code>.
	 * @return The computed value. May be <code>null</code>.
	 * @throws X When the value cannot be computed.
	 */
	protected abstract V compute(final K key)
	throws X;
	
	/**
     * Test whether a value is associated to the given key in the receiver map.
     * 
     * @param key Key which the value is associated to. May be <code>null</code>.
     * @return <code>true</code> when some value is associated to the given key, <code>false</code> otherwise.
     */
    public boolean containsKey(final K key) {
    	return _entries.containsKey(key);
    }

	/**
     * Get a view of the keys of the receiver map.
     * 
     * @return An unmodifiable set of the keys.
     */
    public Set<K> keySet() {
    	return Collections.unmodifiableSet(_entries.keySet());
    }

	/**
     * Get a view of the values of the receiver map.
     * 
     * @return An unmodifiable collection of the values.
     */
    public Collection<V> values() {
    	return Collections.unmodifiableCollection(_entries.values());
    }

	/**
	 * Get a view of the receiver map.
	 * 
	 * @return An unmodifiable map.
	 */
	public Map<K, V> asMap() {
		return Collections.unmodifiableMap(_entries);
	}
	
	/**
	 * Get a view of the entries of the receiver map.
	 * 
	 * @return An unmodifiable set of the entries.
	 */
	public Set<Entry<K, V>> entrySet() {
		return Collections.unmodifiableSet(_entries.entrySet());
	}
	
	public V evaluate(final K key)
	throws X {
		return get(key);
	}
	
	@Override
	public String toString() {
		return _entries.toString();
	}
}