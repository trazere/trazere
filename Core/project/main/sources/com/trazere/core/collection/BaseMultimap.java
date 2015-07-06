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
import com.trazere.core.imperative.IteratorUtils;
import com.trazere.core.lang.LangAccumulators;
import com.trazere.core.lang.MutableInt;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * The {@link Multimap} interface defines collections of bindings where each keys can be associated to a collection of values.
 * <p>
 * This data structure is similar to {@link Map}s but allows to associate multiple values to a same key.
 * <p>
 * The values associated to a each key are hold inside collections. The semantics of those collections therefore impact the semantics of the operations of the
 * multimap.
 * <p>
 * The implementations may have restrictions on the keys they may contain. For example, <code>null</code> keys may be prohibited or restricted to certains
 * types. Similarily, the values may be restricted as well according the type of the collection holding them.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <C> Type of the collections of values.
 * @since 1.0
 */
public abstract class BaseMultimap<K, V, C extends Collection<V>>
implements Multimap<K, V, C> {
	@Override
	public boolean putAll(final K key, final Iterable<? extends V> values) {
		final Accumulator<Boolean, Boolean> changed = LangAccumulators.or(false);
		for (final V value : values) {
			changed.add(put(key, value));
		}
		return changed.get().booleanValue();
	}
	
	@Override
	public boolean putAll(final Multimap<? extends K, ? extends V, ?> multimap) {
		return innerPutAll(multimap);
	}
	
	private <MK extends K> boolean innerPutAll(final Multimap<MK, ? extends V, ?> multimap) {
		final Accumulator<Boolean, Boolean> changed = LangAccumulators.or(false);
		for (final MK key : multimap.keySet()) {
			changed.add(putAll(key, multimap.get(key)));
		}
		return changed.get().booleanValue();
	}
	
	@Override
	public boolean isEmpty() {
		return 0 == size();
	}
	
	@Override
	public boolean containsKey(final K key) {
		return !get(key).isEmpty();
	}
	
	@Override
	public boolean contains(final K key, final V value) {
		return get(key).contains(value);
	}
	
	// TODO: add support for Iterator.remove
	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		return new AbstractSet<Map.Entry<K, V>>() {
			@Override
			public boolean add(final Map.Entry<K, V> e) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean addAll(final Collection<? extends Map.Entry<K, V>> c) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean isEmpty() {
				return BaseMultimap.this.isEmpty();
			}
			
			@Override
			public int size() {
				return BaseMultimap.this.size();
			}
			
			@Override
			public boolean contains(final Object o) {
				if (o instanceof Map.Entry<?, ?>) {
					@SuppressWarnings("unchecked")
					final Map.Entry<K, V> entry = (Map.Entry<K, V>) o;
					return BaseMultimap.this.contains(entry.getKey(), entry.getValue());
				} else {
					return false;
				}
			}
			
			@Override
			public void clear() {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean remove(final Object o) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean removeAll(final Collection<?> c) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean retainAll(final Collection<?> c) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public Iterator<Map.Entry<K, V>> iterator() {
				return IteratorUtils.flatMap(BaseMultimap.this.keySet().iterator(), (final K key) -> {
					return IteratorUtils.map(BaseMultimap.this.get(key).iterator(), (final V value) -> {
						return new AbstractMap.SimpleEntry<>(key, value);
					});
				});
			}
		};
	}
	
	// TODO: add support for Iterator.remove
	@Override
	public Set<Entry<K, C>> collectionEntrySet() {
		return new AbstractSet<Map.Entry<K, C>>() {
			@Override
			public boolean add(final Map.Entry<K, C> e) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean addAll(final Collection<? extends Map.Entry<K, C>> c) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean isEmpty() {
				return BaseMultimap.this.isEmpty();
			}
			
			@Override
			public int size() {
				return BaseMultimap.this.size();
			}
			
			@Override
			public boolean contains(final Object o) {
				if (o instanceof Map.Entry<?, ?>) {
					@SuppressWarnings("unchecked")
					final Map.Entry<K, C> entry = (Map.Entry<K, C>) o;
					return BaseMultimap.this.get(entry.getKey()).equals(entry.getValue());
				} else {
					return false;
				}
			}
			
			@Override
			public void clear() {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean remove(final Object o) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean removeAll(final Collection<?> c) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean retainAll(final Collection<?> c) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public Iterator<Map.Entry<K, C>> iterator() {
				return IteratorUtils.map(BaseMultimap.this.keySet().iterator(), (final K key) -> {
					return new AbstractMap.SimpleEntry<>(key, BaseMultimap.this.get(key));
				});
			}
		};
	}
	
	// Object.
	
	@Override
	public int hashCode() {
		final MutableInt result = new MutableInt(1);
		for (final K key : keySet()) {
			result.add(null == key ? 0 : key.hashCode() ^ get(key).hashCode());
		}
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && object instanceof Multimap<?, ?, ?>) {
			@SuppressWarnings("unchecked")
			final Multimap<K, ?, ?> map = (Multimap<K, ?, ?>) object;
			if (keySet().size() != map.keySet().size()) {
				return false;
			}
			for (final K key : keySet()) {
				if (!get(key).equals(map.get(key))) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}
}
