/*
 *  Copyright 2006-2013 Julien Dufour
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
package com.trazere.core.collection;

import com.trazere.core.imperative.Accumulator;
import com.trazere.core.lang.LangAccumulators;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * The {@link MapMultimap} class provides a skeleton implementation of {@link Multimap} backed by a {@link Map map} of {@link Collection collections}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <C> Type of the collections of values.
 * @param <CC> Type of the concrete collections of values.
 */
public class MapMultimap<K, V, C extends Collection<V>, CC extends C>
extends BaseMultimap<K, V, C> {
	/**
	 * Instantiates a new multimap.
	 * 
	 * @param mapFactory Factory of the backing map.
	 * @param valuesFactory Factory of the collections of values.
	 */
	public MapMultimap(final MapFactory<K, CC, ? extends Map<K, CC>> mapFactory, final ExtendedAbstractCollectionFactory<V, C, CC> valuesFactory) {
		this(mapFactory.build(), valuesFactory);
	}
	
	/**
	 * Instantiates a new multimap containing the bindings of the given multimap.
	 * 
	 * @param mapFactory Factory of the backing map.
	 * @param valuesFactory Factory of the collections of values.
	 * @param multimap Multimap to copy.
	 */
	public MapMultimap(final MapFactory<K, CC, ? extends Map<K, CC>> mapFactory, final ExtendedAbstractCollectionFactory<V, C, CC> valuesFactory, final Multimap<? extends K, ? extends V, ?> multimap) {
		this(copy(mapFactory.build(), valuesFactory, multimap), valuesFactory);
	}
	
	private static <K, V, C extends Collection<V>, CC extends C, M extends Map<? super K, CC>> M copy(final M bindings, final ExtendedAbstractCollectionFactory<V, C, CC> valuesFactory, final Multimap<K, ? extends V, ?> multimap) {
		for (final K key : multimap.keySet()) {
			bindings.put(key, valuesFactory.build(multimap.get(key)));
		}
		return bindings;
	}
	
	/**
	 * Instantiates a new multimap.
	 * 
	 * @param bindings Backing map of the bindings.
	 * @param collectionFactory Factory of the collections of values.
	 */
	protected MapMultimap(final Map<K, CC> bindings, final ExtendedAbstractCollectionFactory<V, C, CC> collectionFactory) {
		assert null != bindings;
		assert null != collectionFactory;
		
		// Initialization.
		_bindings = bindings;
		_collectionFactory = collectionFactory;
	}
	
	// Values factory.
	
	/** Factory of the collections of values. */
	protected final ExtendedAbstractCollectionFactory<V, C, CC> _collectionFactory;
	
	/**
	 * Gets the factory of the collections of values of this multimap.
	 * 
	 * @return The collection factory.
	 */
	public ExtendedAbstractCollectionFactory<V, C, CC> getCollectionFactory() {
		return _collectionFactory;
	}
	
	// Bindings.
	
	/** Backing map of the bindings. */
	protected final Map<K, CC> _bindings;
	
	/**
	 * Gets the collection of values associated to the given key, creating it if necessary.
	 * 
	 * @param key Key of the bindings.
	 * @return The collection of values.
	 */
	protected C getCollection(final K key) {
		// Look for the current collection.
		final C values = _bindings.get(key);
		if (null != values) {
			return values;
		}
		
		// Create a new collection.
		final CC collection = _collectionFactory.build();
		_bindings.put(key, collection);
		return collection;
	}
	
	@Override
	public boolean put(final K key, final V value) {
		return getCollection(key).add(value);
	}
	
	@Override
	public boolean putAll(final K key, final Collection<? extends V> values) {
		return getCollection(key).addAll(values);
	}
	
	@Override
	public boolean isEmpty() {
		return _bindings.isEmpty();
	}
	
	@Override
	public int size() {
		final Accumulator<Integer, Integer> size = LangAccumulators.sum(0);
		for (final C values : _bindings.values()) {
			size.add(values.size());
		}
		return size.get().intValue();
	}
	
	@Override
	public boolean containsKey(final K key) {
		return _bindings.containsKey(key);
	}
	
	@Override
	public Set<K> keySet() {
		return Collections.unmodifiableSet(_bindings.keySet());
	}
	
	@Override
	public boolean contains(final K key, final V value) {
		return _bindings.containsKey(key) && _bindings.get(key).contains(value);
	}
	
	@Override
	public C get(final K key) {
		return _collectionFactory.unmodifiable(_bindings.containsKey(key) ? _bindings.get(key) : _collectionFactory.build());
	}
	
	@Override
	public boolean containsValue(final V value) {
		for (final C values : _bindings.values()) {
			if (values.contains(value)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void clear() {
		_bindings.clear();
	}
	
	@Override
	public boolean remove(final K key, final V value) {
		if (_bindings.containsKey(key)) {
			final C collection = _bindings.get(key);
			final boolean result = collection.remove(value);
			if (collection.isEmpty()) {
				_bindings.remove(key);
			}
			return result;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean removeAll(final K key, final Collection<? extends V> values) {
		if (_bindings.containsKey(key)) {
			final C collection = _bindings.get(key);
			final boolean result = collection.removeAll(values);
			if (collection.isEmpty()) {
				_bindings.remove(key);
			}
			return result;
		} else {
			return false;
		}
	}
	
	@Override
	public C removeKey(final K key) {
		return _collectionFactory.unmodifiable(_bindings.containsKey(key) ? _bindings.remove(key) : _collectionFactory.build());
	}
	
	@Override
	public boolean removeValue(final V value) {
		final Accumulator<Boolean, Boolean> result = LangAccumulators.or(false);
		final Iterator<CC> bindings = _bindings.values().iterator();
		while (bindings.hasNext()) {
			final CC values = bindings.next();
			if (values.remove(value)) {
				result.add(true);
				if (values.isEmpty()) {
					bindings.remove();
				}
			}
		}
		return result.get();
	}
	
	// Object.
	
	@Override
	public String toString() {
		return _bindings.toString();
	}
}
