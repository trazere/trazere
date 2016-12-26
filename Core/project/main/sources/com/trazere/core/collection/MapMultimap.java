/*
 *  Copyright 2006-2015 Julien Dufour
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
import com.trazere.core.imperative.ExIterator;
import com.trazere.core.imperative.IteratorUtils;
import com.trazere.core.imperative.PairIterator;
import com.trazere.core.lang.LangAccumulators;
import com.trazere.core.lang.PairIterable;
import com.trazere.core.util.Tuple2;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * The {@link MapMultimap} class implements {@link Multimap multimaps} backed by a {@link Map map} of {@link Collection collections}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <C> Type of the collections of values.
 * @param <CC> Type of the concrete collections of values.
 * @since 2.0
 */
public class MapMultimap<K, V, C extends Collection<V>, CC extends C>
extends BaseMultimap<K, V, C> {
	/**
	 * Instantiates a new multimap.
	 * 
	 * @param mapFactory Factory of the backing map.
	 * @param collectionFactory Factory of the collections of values.
	 * @since 2.0
	 */
	public MapMultimap(final MapFactory<K, CC, ? extends Map<K, CC>> mapFactory, final ExtendedAbstractCollectionFactory<V, C, CC> collectionFactory) {
		this(mapFactory.build(), collectionFactory);
	}
	
	/**
	 * Instantiates a new multimap containing the bindings of the given multimap.
	 * 
	 * @param mapFactory Factory of the backing map.
	 * @param collectionFactory Factory of the collections of values.
	 * @param multimap Multimap to copy.
	 * @since 2.0
	 */
	public MapMultimap(final MapFactory<K, CC, ? extends Map<K, CC>> mapFactory, final ExtendedAbstractCollectionFactory<V, C, CC> collectionFactory, final Multimap<? extends K, ? extends V, ?> multimap) {
		this(mapFactory.build(multimap.collectionBindings().map(b -> new Tuple2<>(b.get1(), collectionFactory.build(b.get2())))), collectionFactory);
	}
	
	/**
	 * Instantiates a new multimap.
	 * 
	 * @param collectionBindings Backing map of the bindings.
	 * @param collectionFactory Factory of the collections of values.
	 * @since 2.0
	 */
	protected MapMultimap(final Map<K, CC> collectionBindings, final ExtendedAbstractCollectionFactory<V, C, CC> collectionFactory) {
		assert null != collectionBindings;
		assert null != collectionFactory;
		
		// Initialization.
		_collectionBindings = ExMap.build(collectionBindings);
		_collectionBindings.retainAll((k, c) -> !c.isEmpty());
		_collectionFactory = collectionFactory;
	}
	
	// Collection factory.
	
	/**
	 * Factory of the collections of values.
	 * 
	 * @since 2.0
	 */
	protected final ExtendedAbstractCollectionFactory<V, C, CC> _collectionFactory;
	
	/**
	 * Gets the factory of the collections of values of this multimap.
	 * 
	 * @return The collection factory.
	 * @since 2.0
	 */
	public ExtendedAbstractCollectionFactory<V, C, CC> getCollectionFactory() {
		return _collectionFactory;
	}
	
	// Multimap.
	
	/**
	 * Collection bindings.
	 * <p>
	 * Should
	 * 
	 * @since 2.0
	 */
	protected final ExMap<K, CC> _collectionBindings;
	
	/**
	 * Gets the collection of values associated to the given key, creating it if necessary.
	 *
	 * @param key Key of the collection of values to get.
	 * @return The collection of values.
	 * @since 2.0
	 */
	protected C getCollection(final K key) {
		// Look for the current collection.
		final C values = _collectionBindings.get(key);
		if (null != values) {
			return values;
		}
		
		// Create a new collection.
		final CC collection = _collectionFactory.build();
		_collectionBindings.put(key, collection);
		return collection;
	}
	
	@Override
	public ExSet<K> keys() {
		return _collectionBindings.keySet();
	}
	
	@Override
	public ExCollection<V> values() {
		return new AbstractExCollection<V>() {
			@Override
			public int size() {
				return MapMultimap.this.size();
			}
			
			@Override
			public ExIterator<V> iterator() {
				return _collectionBindings.values().iterator().flatMap(Iterable::iterator);
			}
		};
	}
	
	@Override
	public PairIterable<K, V> bindings() {
		// FIXME: should be a lambda interface, bug eclipse ?
		return new PairIterable<K, V>() {
			@Override
			public PairIterator<K, V> iterator() {
				return PairIterator.build(_collectionBindings.bindings().iterator().flatMap2((k, c) -> IteratorUtils.map(c.iterator(), v -> new Tuple2<>(k, v))));
			}
		};
	}
	
	@Override
	public PairIterable<K, C> collectionBindings() {
		return MapUtils.bindings(_collectionBindings);
	}
	
	@Override
	public int size() {
		return _collectionBindings.values().fold((a, c) -> a + c.size(), 0);
	}
	
	@Override
	public boolean isEmpty() {
		return _collectionBindings.isEmpty();
	}
	
	@Override
	public boolean containsKey(final K key) {
		return _collectionBindings.containsKey(key);
	}
	
	@Override
	public boolean containsValue(final V value) {
		return _collectionBindings.values().isAny(c -> c.contains(value));
	}
	
	@Override
	public boolean contains(final K key, final V value) {
		return _collectionBindings.containsKey(key) && _collectionBindings.get(key).contains(value);
	}
	
	@Override
	public C get(final K key) {
		return _collectionFactory.unmodifiable(_collectionBindings.optionalGet(key).get(_collectionFactory::build));
	}
	
	@Override
	public boolean put(final K key, final V value) {
		return getCollection(key).add(value);
	}
	
	@Override
	public boolean putAll(final K key, @SuppressWarnings("unchecked") final V... values) {
		return CollectionUtils.addAll(getCollection(key), values);
	}
	
	@Override
	public boolean putAll(final K key, final Iterable<? extends V> values) {
		return CollectionUtils.addAll(getCollection(key), values);
	}
	
	@Override
	public boolean putAll(final Multimap<? extends K, ? extends V, ?> multimap) {
		final Accumulator<Boolean, Boolean> changed = LangAccumulators.or(false);
		for (final Tuple2<? extends K, ? extends Collection<? extends V>> collectionBinding : multimap.collectionBindings()) {
			changed.add(putAll(collectionBinding.get1(), collectionBinding.get2()));
		}
		return changed.get();
	}
	
	@Override
	public C removeKey(final K key) {
		return _collectionFactory.unmodifiable(_collectionBindings.optionalRemove(key).get(_collectionFactory::build));
	}
	
	@Override
	public boolean removeValue(final V value) {
		final Accumulator<Boolean, Boolean> changed = LangAccumulators.or(false);
		final Iterator<? extends C> collections = _collectionBindings.values().iterator();
		while (collections.hasNext()) {
			final C collection = collections.next();
			if (collection.remove(value)) {
				changed.add(true);
				if (collection.isEmpty()) {
					collections.remove();
				}
			}
		}
		return changed.get();
	}
	
	@Override
	public boolean remove(final K key, final V value) {
		if (_collectionBindings.containsKey(key)) {
			final C collection = _collectionBindings.get(key);
			final boolean result = collection.remove(value);
			if (collection.isEmpty()) {
				_collectionBindings.remove(key);
			}
			return result;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean removeAll(final K key, @SuppressWarnings("unchecked") final V... values) {
		if (_collectionBindings.containsKey(key)) {
			final C collection = _collectionBindings.get(key);
			final boolean result = CollectionUtils.removeAll(collection, values);
			if (collection.isEmpty()) {
				_collectionBindings.remove(key);
			}
			return result;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean removeAll(final K key, final Iterable<? extends V> values) {
		if (_collectionBindings.containsKey(key)) {
			final C collection = _collectionBindings.get(key);
			final boolean result = CollectionUtils.removeAll(collection, values);
			if (collection.isEmpty()) {
				_collectionBindings.remove(key);
			}
			return result;
		} else {
			return false;
		}
	}
	
	@Override
	public void clear() {
		_collectionBindings.clear();
	}
	
	// Object.
	
	@Override
	public String toString() {
		return _collectionBindings.toString();
	}
}
