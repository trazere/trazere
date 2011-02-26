/*
 *  Copyright 2006-2011 Julien Dufour
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
import com.trazere.util.function.FunctionUtils;
import com.trazere.util.function.Predicate2;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The {@link LazyMap} class represents maps which can lazily fill themselves upon access.
 * <p>
 * <code>null</code> keys and values are accepted.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <X> Type of the exceptions.
 */
public abstract class LazyMap<K, V, X extends Exception>
implements Function1<K, V, X> {
	/**
	 * Builds a lazy map using the given function to compute the values.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
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
	protected final Map<K, V> _mappings;
	
	/**
	 * Instantiates a new empty lazy map.
	 */
	public LazyMap() {
		// Initialization.
		_mappings = new HashMap<K, V>();
	}
	
	/**
	 * Instantiates a new lazy map containing the given mappings.
	 * 
	 * @param mappings Entries of the map.
	 */
	public LazyMap(final Map<? extends K, ? extends V> mappings) {
		assert null != mappings;
		
		// Initialization.
		_mappings = new HashMap<K, V>(mappings);
	}
	
	/**
	 * Tests whether the receiver map is empty.
	 * 
	 * @return <code>true</code> if the map is empty, <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return _mappings.isEmpty();
	}
	
	/**
	 * Tests whether the receiver map contains a mapping for the given key.
	 * 
	 * @param key Key which the value is associated to. May be <code>null</code>.
	 * @return <code>true</code> when some value is associated to the given key, <code>false</code> otherwise.
	 */
	public boolean containsKey(final K key) {
		return _mappings.containsKey(key);
	}
	
	/**
	 * Gets a view of the keys of the receiver map.
	 * 
	 * @return An unmodifiable set of the keys.
	 */
	public Set<K> keySet() {
		return Collections.unmodifiableSet(_mappings.keySet());
	}
	
	/**
	 * Gets the number of mappings in the receiver map.
	 * 
	 * @return The number of mappings.
	 */
	public int size() {
		return _mappings.size();
	}
	
	/**
	 * Maps the given value to the given key in the receiver map.
	 * 
	 * @param key The key. May be <code>null</code>.
	 * @param value The value. May be <code>null</code>.
	 * @return The value previously mapped to the key, or <code>null</code> when no values were mapped to the key.
	 */
	public V put(final K key, final V value) {
		return _mappings.put(key, value);
	}
	
	/**
	 * Copies the given mappings into the receiver map.
	 * 
	 * @param mappings The mappings.
	 */
	public void putAll(final Map<? extends K, ? extends V> mappings) {
		assert null != mappings;
		
		_mappings.putAll(mappings);
	}
	
	/**
	 * Gets the value mapped to the given key in the receiver map.
	 * <p>
	 * The value is implicitely computed using the {@link #compute(Object)} method and added into the map when no values are mapped to the given key.
	 * 
	 * @param key The key. May be <code>null</code>.
	 * @return The value. May ne <code>null</code>.
	 * @throws X When the value cannot be computed.
	 */
	public V get(final K key)
	throws X {
		// Check the entries.
		if (_mappings.containsKey(key)) {
			return _mappings.get(key);
		}
		
		// Compute the value.
		final V value = compute(key);
		put(key, value);
		
		return value;
	}
	
	/**
	 * Computes the value to corresponding to the given key.
	 * 
	 * @param key The key. May be <code>null</code>.
	 * @return The computed value. May be <code>null</code>.
	 * @throws X When the value cannot be computed.
	 */
	protected abstract V compute(final K key)
	throws X;
	
	/**
	 * Gets a view of the values of the receiver map.
	 * 
	 * @return An unmodifiable collection of the values.
	 */
	public Collection<V> values() {
		return Collections.unmodifiableCollection(_mappings.values());
	}
	
	/**
	 * Gets a view of the receiver map.
	 * 
	 * @return An unmodifiable map.
	 */
	public Map<K, V> asMap() {
		return Collections.unmodifiableMap(_mappings);
	}
	
	/**
	 * Removes the mapping for the given key.
	 * 
	 * @param key The key. May be <code>null</code>.
	 * @return The value mapped to the key, or <code>null</code> when no values were mapped to the key.
	 */
	public V remove(final K key) {
		return _mappings.remove(key);
	}
	
	/**
	 * Retains the mappings of the receiver map according to the given predicate.
	 * 
	 * @param <PX> Type of the exceptions.
	 * @param predicate The predicate.
	 * @throws PX
	 */
	public <PX extends Exception> void retain(final Predicate2<? super K, ? super V, PX> predicate)
	throws PX {
		FunctionUtils.retain(predicate, _mappings);
	}
	
	/**
	 * Clears the receiver map.
	 */
	public void clear() {
		_mappings.clear();
	}
	
	public V evaluate(final K key)
	throws X {
		return get(key);
	}
	
	@Override
	public String toString() {
		return _mappings.toString();
	}
}
