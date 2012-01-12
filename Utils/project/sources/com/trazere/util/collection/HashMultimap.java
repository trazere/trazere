/*
 *  Copyright 2006-2012 Julien Dufour
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

import com.trazere.util.lang.HashCode;
import com.trazere.util.lang.MutableBoolean;
import com.trazere.util.lang.MutableInt;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The {@link HashMultimap} class represents {@link Multimap multimaps} implemented using a {@link HashMap}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <C> Type of the collections of values.
 */
public class HashMultimap<K, V, C extends Collection<V>>
implements Multimap<K, V, C> {
	/**
	 * Instantiates a new multimap.
	 * 
	 * @param collectionFactory The collection factory.
	 */
	public HashMultimap(final CollectionFactory<V, ? extends C> collectionFactory) {
		assert null != collectionFactory;
		
		// Initialization.
		_collectionFactory = collectionFactory;
	}
	
	/**
	 * Instantiates a new multimap populated with the contents of the given multimap.
	 * 
	 * @param collectionFactory The collection factory.
	 * @param multimap The multimap to copy.
	 */
	public HashMultimap(final CollectionFactory<V, ? extends C> collectionFactory, final Multimap<? extends K, ? extends V, ?> multimap) {
		this(collectionFactory);
		
		// Checks.
		assert null != multimap;
		
		// Initialization.
		copy(multimap);
	}
	
	private <K2 extends K> void copy(final Multimap<K2, ? extends V, ?> multimap) {
		for (final K2 key : multimap.keySet()) {
			_values.put(key, _collectionFactory.build(multimap.get(key)));
		}
	}
	
	// Collection factory.
	
	/** The collection factory. */
	protected final CollectionFactory<V, ? extends C> _collectionFactory;
	
	/**
	 * Gets the collection factory of the receiver multimap.
	 * 
	 * @return The collection factory.
	 */
	public CollectionFactory<V, ? extends C> getCollectionFactory() {
		return _collectionFactory;
	}
	
	// Values.
	
	/** The collections of values associated to their keys. */
	protected final Map<K, C> _values = new HashMap<K, C>();
	
	/**
	 * Gets the collection of values associated to the given key, making it if necessary.
	 * 
	 * @param key The key.
	 * @return The collection.
	 */
	protected C makeCollection(final K key) {
		// Look for the family.
		final C currentFamily = _values.get(key);
		if (null != currentFamily) {
			return currentFamily;
		}
		
		// Create a new family.
		final C family = _collectionFactory.build();
		_values.put(key, family);
		return family;
	}
	
	public boolean put(final K key, final V value) {
		return makeCollection(key).add(value);
	}
	
	public boolean putAll(final K key, final Collection<? extends V> values) {
		assert null != values;
		
		return makeCollection(key).addAll(values);
	}
	
	public boolean putAll(final Multimap<? extends K, ? extends V, ?> multimap) {
		assert null != multimap;
		
		return doPutAll(multimap);
	}
	
	private <K2 extends K> boolean doPutAll(final Multimap<K2, ? extends V, ?> multimap) {
		assert null != multimap;
		
		final MutableBoolean result = new MutableBoolean(false);
		for (final K2 key : multimap.keySet()) {
			if (putAll(key, multimap.get(key))) {
				result.set(true);
			}
		}
		return result.get();
	}
	
	public boolean isEmpty() {
		return _values.isEmpty();
	}
	
	public int size() {
		final MutableInt result = new MutableInt(0);
		for (final C values : _values.values()) {
			result.add(values.size());
		}
		return result.get();
	}
	
	public Set<K> keySet() {
		return Collections.unmodifiableSet(_values.keySet());
	}
	
	public boolean containsKey(final K key) {
		return _values.containsKey(key);
	}
	
	public boolean containsValue(final K key, final V value) {
		return _values.containsKey(key) && _values.get(key).contains(value);
	}
	
	// TODO: should return unmodifiable collections
	public C get(final K key) {
		return _values.containsKey(key) ? _values.get(key) : _collectionFactory.build();
	}
	
	public void clear() {
		_values.clear();
	}
	
	public C remove(final K key) {
		return _values.containsKey(key) ? _values.remove(key) : _collectionFactory.build();
	}
	
	public boolean remove(final K key, final V value) {
		if (_values.containsKey(key)) {
			final C family = _values.get(key);
			final boolean result = family.remove(value);
			if (family.isEmpty()) {
				_values.remove(key);
			}
			return result;
		} else {
			return false;
		}
	}
	
	public boolean removeAll(final K key, final Collection<? extends V> values) {
		if (_values.containsKey(key)) {
			final C family = _values.get(key);
			final boolean result = family.removeAll(values);
			if (family.isEmpty()) {
				_values.remove(key);
			}
			return result;
		} else {
			return false;
		}
	}
	
	// Object.
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_values);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final HashMultimap<?, ?, ?> map = (HashMultimap<?, ?, ?>) object;
			return _values.equals(map._values);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return _values.toString();
	}
}
