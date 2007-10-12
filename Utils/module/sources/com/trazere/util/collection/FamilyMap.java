/*
 *  Copyright 2006 Julien Dufour
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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.trazere.util.Assert;

// TODO: empty families should not be null (get, remove...)

/**
 * The <code>FamilyMap</code> represents indexed families of collections.
 * <p>
 * Those structures allow to associate multiple values to the same keys. TODO: detail
 * <p>
 * This class allow <code>null</code> key. Support for <code>null</code> value depend on the given type of collections.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <C> Type of the collections to build.
 */
public class FamilyMap<K, V, C extends Collection<V>> {
	/** Factory of the family collections. */
	protected final CollectionFactory<V, ? extends C> _familyFactory;
	
	/** Families of values identified by their keys. */
	protected final Map<K, C> _families = new HashMap<K, C>();
	
	/**
	 * Build a new family map with the given type of collection.
	 * 
	 * @param familyFactory Factory of the family collections.
	 */
	public FamilyMap(final CollectionFactory<V, ? extends C> familyFactory) {
		Assert.notNull(familyFactory);
		
		// Initialization.
		_familyFactory = familyFactory;
	}
	
	/**
	 * Build a new family map with the given map.
	 * 
	 * @param map Family map to copy.
	 */
	public FamilyMap(final FamilyMap<? extends K, V, C> map) {
		this(map, map._familyFactory);
	}
	
	/**
	 * Build a new family map with the given map.
	 * 
	 * @param map Family map to copy.
	 * @param familyFactory Factory of the family collections.
	 */
	public FamilyMap(final FamilyMap<? extends K, ? extends V, ? extends C> map, final CollectionFactory<V, ? extends C> familyFactory) {
		Assert.notNull(map);
		
		// Initialization.
		_familyFactory = familyFactory;
		
		// Copy the families.
		for (final Map.Entry<? extends K, ? extends C> familyEntry : map._families.entrySet()) {
			_families.put(familyEntry.getKey(), familyFactory.build(familyEntry.getValue()));
		}
	}
	
	protected C getFamily(final K key) {
		// Look for the family.
		final C currentFamily = _families.get(key);
		if (null != currentFamily) {
			return currentFamily;
		}
		
		// Create a new family.
		final C family = _familyFactory.build();
		_families.put(key, family);
		return family;
	}
	
	/**
	 * Associate the given value to the given key.
	 * 
	 * @param key Key which the value should be associated to. May be <code>null</code>.
	 * @param value Value to associate. May be <code>null</code>.
	 */
	public void put(final K key, final V value) {
		getFamily(key).add(value);
	}
	
	/**
	 * Associate the given values to the given key.
	 * 
	 * @param key Key which the value should be associated to. May be <code>null</code>.
	 * @param values Values to associate.
	 */
	public void putAll(final K key, final Collection<? extends V> values) {
		Assert.notNull(values);
		
		// Add.
		getFamily(key).addAll(values);
	}
	
	/**
	 * Indicate wether the receiver family map is empty or not.
	 * 
	 * @return <code>true</code> if the map is empty, <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return _families.isEmpty();
	}
	
	/**
	 * Check wether the receiver family contains values for the given key.
	 * 
	 * @param key Key whose associated value should be checked.
	 * @return <code>true</code> when some values are associated to the key, <code>false</code> otherwise.
	 */
	public boolean contains(final K key) {
		return _families.containsKey(key);
	}
	
	/**
	 * Get the values associated to the given key.
	 * 
	 * @param key Key whose associated value should be returned.
	 * @return The associated values, or <code>null</code> when no values are associated to the key.
	 */
	public C get(final K key) {
		return _families.get(key);
	}
	
	/**
	 * Get the families as a map.
	 * <p>
	 * The returned map is backed by the family map so changes to the family map are reflected.
	 * 
	 * @return The families.
	 */
	public Map<K, C> map() {
		return Collections.unmodifiableMap(_families);
	}
	
	/**
	 * Clear the receiver family map.
	 */
	public void clear() {
		_families.clear();
	}
	
	/**
	 * Remove the associations to the given key.
	 * 
	 * @param key Key whose associations should be removed.
	 * @return The removed associated values, or <code>null</code> when no values where associated to the key.
	 */
	public C remove(final K key) {
		return _families.remove(key);
	}
	
	/**
	 * Remove the association of the given value to the given key.
	 * 
	 * @param key Key whose association should be removed.
	 * @param value Value whose association should be removed.
	 * @return <code>true</code> if an association is removed, <code>false</code> otherwise.
	 */
	public boolean remove(final K key, final V value) {
		final C family = _families.get(key);
		if (null != family) {
			final boolean result = family.remove(value);
			if (family.isEmpty()) {
				_families.remove(key);
			}
			return result;
		} else {
			return false;
		}
	}
	
	/**
	 * Remove the associations of the given values to the given key.
	 * 
	 * @param key Key whose association should be removed.
	 * @param values Values whose associations should be removed.
	 * @return <code>true</code> if the associations are removed, <code>false</code> otherwise.
	 */
	public boolean removeAll(final K key, final Collection<? extends V> values) {
		final C family = _families.get(key);
		if (null != family) {
			final boolean result = family.removeAll(values);
			if (family.isEmpty()) {
				_families.remove(key);
			}
			return result;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return _families.hashCode();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final FamilyMap<?, ?, ?> map = (FamilyMap<?, ?, ?>) object;
			return _families.equals(map._families);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return _families.toString();
	}
}
