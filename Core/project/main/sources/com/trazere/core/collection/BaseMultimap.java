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

import com.trazere.core.lang.HashCode;
import com.trazere.core.util.Tuple2;
import java.util.Collection;
import java.util.Iterator;

/**
 * The {@link BaseMultimap} class provides a skeleton implementation of {@link Multimap multimaps}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <C> Type of the collections of values.
 * @since 2.0
 */
public abstract class BaseMultimap<K, V, C extends Collection<V>>
implements Multimap<K, V, C> {
	// Multimap.
	
	@Override
	public boolean isEmpty() {
		return 0 == size();
	}
	
	@Override
	public boolean containsKey(final K key) {
		return keys().contains(key);
	}
	
	@Override
	public boolean containsValue(final V value) {
		return values().contains(value);
	}
	
	@Override
	public boolean contains(final K key, final V value) {
		return get(key).contains(value);
	}
	
	// Object.
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(0);
		collectionBindings().foreach(result::append);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && object instanceof Multimap<?, ?, ?>) {
			@SuppressWarnings("unchecked")
			final Multimap<K, ?, ?> multimap = (Multimap<K, ?, ?>) object;
			if (keys().size() != multimap.keys().size()) {
				return false;
			}
			for (final Tuple2<K, C> collectionBinding : collectionBindings()) {
				if (!collectionBinding.get2().equals(multimap.get(collectionBinding.get1()))) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		final StringBuilder result = new StringBuilder();
		result.append("{");
		final Iterator<Tuple2<K, C>> collectionBindings = collectionBindings().iterator();
		while (collectionBindings.hasNext()) {
			final Tuple2<K, C> collectionBinding = collectionBindings.next();
			result.append(collectionBinding.get1().toString()).append("=").append(collectionBinding.get2());
			if (collectionBindings.hasNext()) {
				result.append(", ");
			}
		}
		result.append("}");
		return result.toString();
	}
}
