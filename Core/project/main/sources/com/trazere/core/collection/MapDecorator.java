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
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * The {@link MapDecorator} class implements decorators of {@link Map maps}.
 * 
 * @param <K> Type of keys.
 * @param <V> Type of values.
 * @since 2.0
 */
public class MapDecorator<K, V>
extends Decorator<Map<K, V>>
implements ExMap<K, V> {
	/**
	 * Instantiates a new decorator.
	 * 
	 * @param decorated Decorated map.
	 * @since 2.0
	 */
	public MapDecorator(final Map<K, V> decorated) {
		super(decorated);
	}
	
	// Query operations.
	
	@Override
	public int size() {
		return _decorated.size();
	}
	
	@Override
	public boolean isEmpty() {
		return _decorated.isEmpty();
	}
	
	@Override
	public boolean containsKey(final Object key) {
		return _decorated.containsKey(key);
	}
	
	@Override
	public boolean containsValue(final Object value) {
		return _decorated.containsValue(value);
	}
	
	@Override
	public V get(final Object key) {
		return _decorated.get(key);
	}
	
	// Modification operations.
	
	@Override
	public V put(final K key, final V value) {
		return _decorated.put(key, value);
	}
	
	@Override
	public V remove(final Object key) {
		return _decorated.remove(key);
	}
	
	// Bulk operations.
	
	@Override
	public void putAll(final Map<? extends K, ? extends V> m) {
		_decorated.putAll(m);
	}
	
	@Override
	public void clear() {
		_decorated.clear();
	}
	
	// Views.
	
	@Override
	public ExSet<K> keySet() {
		return ExSet.build(_decorated.keySet());
	}
	
	@Override
	public ExCollection<V> values() {
		return ExCollection.build(_decorated.values());
	}
	
	@Override
	public ExSet<Map.Entry<K, V>> entrySet() {
		return ExSet.build(_decorated.entrySet());
	}
	
	// Comparison and hashing
	// FIXME ?
	
	// Defaultable methods.
	
	@Override
	public V getOrDefault(final Object key, final V defaultValue) {
		return _decorated.getOrDefault(key, defaultValue);
	}
	
	@Override
	public void forEach(final BiConsumer<? super K, ? super V> action) {
		_decorated.forEach(action);
	}
	
	@Override
	public void replaceAll(final BiFunction<? super K, ? super V, ? extends V> function) {
		_decorated.replaceAll(function);
	}
	
	@Override
	public V putIfAbsent(final K key, final V value) {
		return _decorated.putIfAbsent(key, value);
	}
	
	@Override
	public boolean remove(final Object key, final Object value) {
		return _decorated.remove(key, value);
	}
	
	@Override
	public boolean replace(final K key, final V oldValue, final V newValue) {
		return _decorated.replace(key, oldValue, newValue);
	}
	
	@Override
	public V replace(final K key, final V value) {
		return _decorated.replace(key, value);
	}
	
	@Override
	public V computeIfAbsent(final K key, final Function<? super K, ? extends V> mappingFunction) {
		return _decorated.computeIfAbsent(key, mappingFunction);
	}
	
	@Override
	public V computeIfPresent(final K key, final BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		return _decorated.computeIfPresent(key, remappingFunction);
	}
	
	@Override
	public V compute(final K key, final BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		return _decorated.compute(key, remappingFunction);
	}
	
	@Override
	public V merge(final K key, final V value, final BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
		return _decorated.merge(key, value, remappingFunction);
	}
}
