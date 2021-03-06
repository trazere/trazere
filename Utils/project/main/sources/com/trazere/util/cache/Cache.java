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
package com.trazere.util.cache;

import com.trazere.util.function.Predicate1;
import com.trazere.util.type.Maybe;
import java.util.Set;

/**
 * The {@link Cache} interface defines caches.
 * <p>
 * Caches are collections of values identified by keys. Unlike regular maps, those bindings can be removed at any time according to the policy of the cache.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @deprecated Use {@link com.trazere.core.cache.Cache}.
 */
@Deprecated
public interface Cache<K, V> {
	/**
	 * Tests whether a value is associated to the given key in the receiver cache.
	 * 
	 * @param key The key.
	 * @return <code>true</code> when a value is associated to the key, <code>false</code> otherwise.
	 * @deprecated Use {@link com.trazere.core.cache.Cache#contains(Object)}.
	 */
	@Deprecated
	public boolean contains(final K key);
	
	/**
	 * Gets all keys which values are associated to in the receiver cache.
	 * 
	 * @return The keys.
	 * @deprecated Use {@link com.trazere.core.cache.Cache#keys()}.
	 */
	@Deprecated
	public Set<K> getKeys();
	
	/**
	 * Gets the size of the receiver cache.
	 * 
	 * @return The number of entries.
	 * @deprecated Use {@link com.trazere.core.cache.Cache#size()}.
	 */
	@Deprecated
	public int size();
	
	/**
	 * Fills the receiver cache with the given value associating it to the given key.
	 * <p>
	 * The possible previous associated value is discarded.
	 * 
	 * @param key The key.
	 * @param value The value. May be <code>null</code>.
	 * @return The previously associated value.
	 * @deprecated Use {@link com.trazere.core.cache.Cache#fill(Object, Object)}.
	 */
	@Deprecated
	public Maybe<V> fill(final K key, final V value);
	
	/**
	 * Gets the value associated to the given key in the receiver cache.
	 * 
	 * @param key The key.
	 * @return The associated value.
	 * @deprecated Use {@link com.trazere.core.cache.Cache#get(Object)}.
	 */
	@Deprecated
	public Maybe<V> get(final K key);
	
	/**
	 * Clears the value associated to the given key in the receiver cache.
	 * 
	 * @param key The key.
	 * @return The cleared value.
	 * @deprecated Use {@link com.trazere.core.cache.Cache#clear(Object)}.
	 */
	@Deprecated
	public Maybe<V> clear(final K key);
	
	/**
	 * Clears the values accepted by the given key filter in the receiver cache.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param filter The filter.
	 * @throws X When some filter evaluation fails.
	 * @deprecated Use {@link com.trazere.core.cache.Cache#clear(com.trazere.core.functional.Predicate)}.
	 */
	@Deprecated
	public <X extends Exception> void clear(final Predicate1<? super K, X> filter)
	throws X;
	
	/**
	 * Clears the receiver cache.
	 * 
	 * @deprecated Use {@link com.trazere.core.cache.Cache#clear()}.
	 */
	@Deprecated
	public void clear();
}
