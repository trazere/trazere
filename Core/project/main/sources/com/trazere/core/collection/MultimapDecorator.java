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

import com.trazere.core.design.Decorator;
import com.trazere.core.lang.PairIterable;
import java.util.Collection;

/**
 * The {@link MultimapDecorator} class implements decorators of {@link Multimap multimaps}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <C> Type of the collections of values.
 * @since 2.0
 */
public class MultimapDecorator<K, V, C extends Collection<V>>
extends Decorator<Multimap<K, V, C>>
implements Multimap<K, V, C> {
	/**
	 * Instantiates a new decorator.
	 * 
	 * @param decorated Decorated map.
	 * @since 2.0
	 */
	public MultimapDecorator(final Multimap<K, V, C> decorated) {
		super(decorated);
	}
	
	// Multimap.
	
	@Override
	public ExSet<K> keys() {
		return _decorated.keys();
	}
	
	@Override
	public ExCollection<V> values() {
		return _decorated.values();
	}
	
	@Override
	public PairIterable<K, V> bindings() {
		return _decorated.bindings();
	}
	
	@Override
	public PairIterable<K, C> collectionBindings() {
		return _decorated.collectionBindings();
	}
	
	@Override
	public int size() {
		return _decorated.size();
	}
	
	@Override
	public boolean isEmpty() {
		return _decorated.isEmpty();
	}
	
	@Override
	public boolean containsKey(final K key) {
		return _decorated.containsKey(key);
	}
	
	@Override
	public boolean containsValue(final V value) {
		return _decorated.containsValue(value);
	}
	
	@Override
	public boolean contains(final K key, final V value) {
		return _decorated.contains(key, value);
	}
	
	@Override
	public C get(final K key) {
		return _decorated.get(key);
	}
	
	@Override
	public boolean put(final K key, final V value) {
		return _decorated.put(key, value);
	}
	
	@Override
	public C removeKey(final K key) {
		return _decorated.removeKey(key);
	}
	
	@Override
	public boolean removeValue(final V value) {
		return _decorated.removeValue(value);
	}
	
	@Override
	public boolean remove(final K key, final V value) {
		return _decorated.remove(key, value);
	}
	
	@Override
	public void clear() {
		_decorated.clear();
	}
	
	// Object.
	// TODO
}
