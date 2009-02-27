/*
 *  Copyright 2006-2008 Julien Dufour
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
public abstract class HashMultimap<K, V, C extends Collection<V>>
implements Multimap<K, V, C> {
	/**
	 * Build a new multimap populated using the given collection factory.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <C> Type of the collections of values.
	 * @param collectionFactory The collection factory.
	 * @return The built multimap.
	 */
	public static <K, V, C extends Collection<V>> HashMultimap<K, V, C> build(final CollectionFactory<V, ? extends C> collectionFactory) {
		assert null != collectionFactory;
		
		return new HashMultimap<K, V, C>() {
			@Override
			protected C buildCollection() {
				return collectionFactory.build();
			}
			
			@Override
			protected C buildCollection(final Collection<? extends V> values) {
				return collectionFactory.build(values);
			}
		};
	}
	
	/**
	 * Build a new multimap populated using the given collection factory and populated with the contents of the given multimap.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <C> Type of the collections of values.
	 * @param multimap The multimap to copy.
	 * @param collectionFactory The collection factory.
	 * @return The built multimap.
	 */
	public static <K, V, C extends Collection<V>> HashMultimap<K, V, C> build(final Multimap<? extends K, ? extends V, ?> multimap, final CollectionFactory<V, ? extends C> collectionFactory) {
		assert null != collectionFactory;
		
		return new HashMultimap<K, V, C>(multimap) {
			@Override
			protected C buildCollection() {
				return collectionFactory.build();
			}
			
			@Override
			protected C buildCollection(final Collection<? extends V> values) {
				return collectionFactory.build(values);
			}
		};
	}
	
	/** The collections of values associated to their keys. */
	protected final Map<K, C> _collections = new HashMap<K, C>();
	
	/**
	 * Build a new multi map.
	 */
	public HashMultimap() {
		// Nothing to do.
	}
	
	/**
	 * Instantiate a new multimap populated with the contents of the given multimap.
	 * 
	 * @param multimap The multimap to copy.
	 */
	public HashMultimap(final Multimap<? extends K, ? extends V, ?> multimap) {
		assert null != multimap;
		
		// Initialization.
		copy(multimap);
	}
	
	private <K2 extends K> void copy(final Multimap<K2, ? extends V, ?> multimap) {
		for (final K2 key : multimap.keySet()) {
			_collections.put(key, buildCollection(multimap.get(key)));
		}
	}
	
	/**
	 * Get the collection of values associated to the given key, making it if necessary.
	 * 
	 * @param key The key.
	 * @return The collection.
	 */
	protected C makeCollection(final K key) {
		// Look for the family.
		final C currentFamily = _collections.get(key);
		if (null != currentFamily) {
			return currentFamily;
		}
		
		// Create a new family.
		final C family = buildCollection();
		_collections.put(key, family);
		return family;
	}
	
	/**
	 * Build a fresh collection of values.
	 * 
	 * @return The built collection.
	 */
	protected abstract C buildCollection();
	
	/**
	 * Build a fresh collection of values containing the given values.
	 * 
	 * @param values The values.
	 * @return The built collection.
	 */
	protected abstract C buildCollection(final Collection<? extends V> values);
	
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
		return _collections.isEmpty();
	}
	
	public Set<K> keySet() {
		return Collections.unmodifiableSet(_collections.keySet());
	}
	
	public boolean containsKey(final K key) {
		return _collections.containsKey(key);
	}
	
	public boolean containsValue(final K key, final V value) {
		return _collections.containsKey(key) && _collections.get(key).contains(value);
	}
	
	// TODO: should return unmodifiable collections
	public C get(final K key) {
		return _collections.containsKey(key) ? _collections.get(key) : buildCollection();
	}
	
	public void clear() {
		_collections.clear();
	}
	
	public C remove(final K key) {
		return _collections.containsKey(key) ? _collections.remove(key) : buildCollection();
	}
	
	public boolean remove(final K key, final V value) {
		if (_collections.containsKey(key)) {
			final C family = _collections.get(key);
			final boolean result = family.remove(value);
			if (family.isEmpty()) {
				_collections.remove(key);
			}
			return result;
		} else {
			return false;
		}
	}
	
	public boolean removeAll(final K key, final Collection<? extends V> values) {
		if (_collections.containsKey(key)) {
			final C family = _collections.get(key);
			final boolean result = family.removeAll(values);
			if (family.isEmpty()) {
				_collections.remove(key);
			}
			return result;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		final HashCode hashCode = new HashCode(this);
		hashCode.append(_collections);
		return hashCode.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final HashMultimap<?, ?, ?> map = (HashMultimap<?, ?, ?>) object;
			return _collections.equals(map._collections);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return _collections.toString();
	}
}
