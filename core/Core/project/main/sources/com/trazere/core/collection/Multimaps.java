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

import com.trazere.core.util.Tuple2;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * The {@link Multimaps} class provides various factories of {@link Multimap multimaps}.
 * 
 * @see Multimap
 */
public class Multimaps {
	/**
	 * Builds an unmutable empty multimap.
	 * 
	 * @param <K> Type of the key.
	 * @param <V> Type of the value.
	 * @param <C> Type of the collections of values.
	 * @param valuesFactory Factory of the collections of values.
	 * @return The built multimap.
	 */
	public static <K, V, C extends Collection<V>> Multimap<K, V, C> empty(final ExtendedAbstractCollectionFactory<?, ? extends C, ?> valuesFactory) {
		assert null != valuesFactory;
		
		return new Multimap<K, V, C>() {
			@Override
			public boolean put(final K key, final V value) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean putAll(final K key, final Collection<? extends V> values) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean putAll(final Multimap<? extends K, ? extends V, ?> multimap) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean isEmpty() {
				return true;
			}
			
			@Override
			public int size() {
				return 0;
			}
			
			@Override
			public boolean containsKey(final K key) {
				return false;
			}
			
			@Override
			public Set<K> keySet() {
				return Collections.emptySet();
			}
			
			@Override
			public boolean contains(final K key, final V value) {
				return false;
			}
			
			@Override
			public Set<Map.Entry<K, V>> entrySet() {
				return Collections.emptySet();
			}
			
			@Override
			public C get(final K key) {
				return valuesFactory.empty();
			}
			
			@Override
			public boolean containsValue(final V value) {
				return false;
			}
			
			@Override
			public void clear() {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public C remove(final K key) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean remove(final K key, final V value) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean removeAll(final K key, final Collection<? extends V> values) {
				throw new UnsupportedOperationException();
			}
			
			// Object.
			
			@Override
			public int hashCode() {
				return 1;
			}
			
			@Override
			public boolean equals(final Object object) {
				if (this == object) {
					return true;
				} else if (null != object && object instanceof Multimap) {
					final Multimap<?, ?, ?> multimap = (Multimap<?, ?, ?>) object;
					return multimap.isEmpty();
				} else {
					return false;
				}
			}
			
			@Override
			public String toString() {
				return "{}";
			}
		};
	}
	
	/**
	 * Builds a multimap containing the binding corresponding to the given key and value.
	 * 
	 * @param <K> Type of the key.
	 * @param <V> Type of the value.
	 * @param <C> Type of the collections of values.
	 * @param valuesFactory Factory of the collections of values.
	 * @param key Key of the binding of the multimap to build.
	 * @param value Value of the binding of the multimap to build.
	 * @return The built multimap.
	 */
	public static <K, V, C extends Collection<V>> Multimap<K, V, C> fromBinding(final ExtendedAbstractCollectionFactory<V, ? extends C, ?> valuesFactory, final K key, final V value) {
		final Multimap<K, V, C> multimap = new MapMultimap<>(MapFactories.hashMap(), valuesFactory);
		multimap.put(key, value);
		return multimap;
	}
	
	/**
	 * Builds a multimap containing the bindings corresponding to the given keys and values.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <C> Type of the collections of values.
	 * @param valuesFactory Factory of the collections of values.
	 * @param key1 Key of the first binding of the multimap to build.
	 * @param value1 Value of the first binding of the multimap to build.
	 * @param key2 Key of the second binding of the multimap to build.
	 * @param value2 Value of the second binding of the multimap to build.
	 * @return The built multimap.
	 */
	public static <K, V, C extends Collection<V>> Multimap<K, V, C> fromBindings(final ExtendedAbstractCollectionFactory<V, ? extends C, ?> valuesFactory, final K key1, final V value1, final K key2, final V value2) {
		final Multimap<K, V, C> multimap = new MapMultimap<>(MapFactories.hashMap(), valuesFactory);
		multimap.put(key1, value1);
		multimap.put(key2, value2);
		return multimap;
	}
	
	/**
	 * Builds a multimap containing the bindings corresponding to the given keys and values.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <C> Type of the collections of values.
	 * @param valuesFactory Factory of the collections of values.
	 * @param key1 Key of the first binding of the multimap to build.
	 * @param value1 Value of the first binding of the multimap to build.
	 * @param key2 Key of the second binding of the multimap to build.
	 * @param value2 Value of the second binding of the multimap to build.
	 * @param key3 Key of the third binding of the multimap to build.
	 * @param value3 Value of the third binding of the multimap to build.
	 * @return The built multimap.
	 */
	public static <K, V, C extends Collection<V>> Multimap<K, V, C> fromBindings(final ExtendedAbstractCollectionFactory<V, ? extends C, ?> valuesFactory, final K key1, final V value1, final K key2, final V value2, final K key3, final V value3) {
		final Multimap<K, V, C> multimap = new MapMultimap<>(MapFactories.hashMap(), valuesFactory);
		multimap.put(key1, value1);
		multimap.put(key2, value2);
		multimap.put(key3, value3);
		return multimap;
	}
	
	/**
	 * Builds a multimap containing the bindings corresponding to the given keys and values.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <C> Type of the collections of values.
	 * @param valuesFactory Factory of the collections of values.
	 * @param key1 Key of the first binding of the multimap to build.
	 * @param value1 Value of the first binding of the multimap to build.
	 * @param key2 Key of the second binding of the multimap to build.
	 * @param value2 Value of the second binding of the multimap to build.
	 * @param key3 Key of the third binding of the multimap to build.
	 * @param value3 Value of the third binding of the multimap to build.
	 * @param key4 Key of the fourth binding of the multimap to build.
	 * @param value4 Value of the fourth binding of the multimap to build.
	 * @return The built multimap.
	 */
	public static <K, V, C extends Collection<V>> Multimap<K, V, C> fromBindings(final ExtendedAbstractCollectionFactory<V, ? extends C, ?> valuesFactory, final K key1, final V value1, final K key2, final V value2, final K key3, final V value3, final K key4, final V value4) {
		final Multimap<K, V, C> multimap = new MapMultimap<>(MapFactories.hashMap(), valuesFactory);
		multimap.put(key1, value1);
		multimap.put(key2, value2);
		multimap.put(key3, value3);
		multimap.put(key4, value4);
		return multimap;
	}
	
	/**
	 * Builds a multimap containing the bindings corresponding to the given keys and values.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <C> Type of the collections of values.
	 * @param valuesFactory Factory of the collections of values.
	 * @param key1 Key of the first binding of the multimap to build.
	 * @param value1 Value of the first binding of the multimap to build.
	 * @param key2 Key of the second binding of the multimap to build.
	 * @param value2 Value of the second binding of the multimap to build.
	 * @param key3 Key of the third binding of the multimap to build.
	 * @param value3 Value of the third binding of the multimap to build.
	 * @param key4 Key of the fourth binding of the multimap to build.
	 * @param value4 Value of the fourth binding of the multimap to build.
	 * @param key5 Key of the fifth binding of the multimap to build.
	 * @param value5 Value of the fifth binding of the multimap to build.
	 * @return The built multimap.
	 */
	public static <K, V, C extends Collection<V>> Multimap<K, V, C> fromBindings(final ExtendedAbstractCollectionFactory<V, ? extends C, ?> valuesFactory, final K key1, final V value1, final K key2, final V value2, final K key3, final V value3, final K key4, final V value4, final K key5, final V value5) {
		final Multimap<K, V, C> multimap = new MapMultimap<>(MapFactories.hashMap(), valuesFactory);
		multimap.put(key1, value1);
		multimap.put(key2, value2);
		multimap.put(key3, value3);
		multimap.put(key4, value4);
		multimap.put(key5, value5);
		return multimap;
	}
	
	/**
	 * Builds a multimap containing the given bindings.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <C> Type of the collections of values.
	 * @param valuesFactory Factory of the collections of values.
	 * @param bindings Bindings of the multimap to build.
	 * @return The built multimap.
	 */
	@SafeVarargs
	public static <K, V, C extends Collection<V>> Multimap<K, V, C> fromBindings(final ExtendedAbstractCollectionFactory<V, ? extends C, ?> valuesFactory, final Tuple2<? extends K, ? extends V>... bindings) {
		final Multimap<K, V, C> multimap = new MapMultimap<>(MapFactories.hashMap(), valuesFactory);
		for (final Tuple2<? extends K, ? extends V> binding : bindings) {
			multimap.put(binding.get1(), binding.get2());
		}
		return multimap;
	}
	
	/**
	 * Builds a multimap containing the bindings provided by the given iterable.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <C> Type of the collections of values.
	 * @param valuesFactory Factory of the collections of values.
	 * @param iterable Iterable providing the bindings of the multimap to build.
	 * @return The built multimap.
	 */
	public static <K, V, C extends Collection<V>> Multimap<K, V, C> fromBindings(final ExtendedAbstractCollectionFactory<V, ? extends C, ?> valuesFactory, final Iterable<? extends Tuple2<? extends K, ? extends V>> iterable) {
		final Multimap<K, V, C> multimap = new MapMultimap<>(MapFactories.hashMap(), valuesFactory);
		for (final Tuple2<? extends K, ? extends V> binding : iterable) {
			multimap.put(binding.get1(), binding.get2());
		}
		return multimap;
	}
	
	private Multimaps() {
		// Prevent instantiation.
	}
}
