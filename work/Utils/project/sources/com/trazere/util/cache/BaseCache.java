/*
 *  Copyright 2006-2011 Julien Dufour
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

import com.trazere.util.collection.CollectionUtils;
import com.trazere.util.function.FunctionUtils;
import com.trazere.util.function.Predicate1;
import com.trazere.util.function.Predicate2;
import com.trazere.util.text.Describable;
import com.trazere.util.text.Description;
import com.trazere.util.text.TextUtils;
import com.trazere.util.type.Maybe;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The {@link BaseCache} abstract class provides a skeleton implementation of {@link Cache caches}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <E> Type of the entries.
 */
public abstract class BaseCache<K, V, E extends CacheEntry<K, V>>
implements Cache<K, V>, Describable {
	/** Cache entries identified by their keys. */
	protected final Map<K, E> _entries = new HashMap<K, E>();
	
	public boolean contains(final K key) {
		assert null != key;
		
		// Test.
		return _entries.containsKey(key);
	}
	
	public int size() {
		return _entries.size();
	}
	
	public Set<K> getKeys() {
		return Collections.unmodifiableSet(_entries.keySet());
	}
	
	public Maybe<V> fill(final K key, final V value) {
		assert null != key;
		
		// Fill the cache.
		final E entry = buildEntry(key, value);
		final Maybe<E> previousEntry = putEntry(entry);
		
		// Clean the cache up.
		cleanup();
		
		return previousEntry.map(CacheEntry.<V, RuntimeException>getValueFunction());
	}
	
	/**
	 * Builds a new cache entry with the given key and value.
	 * 
	 * @param key The key.
	 * @param value The value. May be <code>null</code>.
	 * @return The built cache entry.
	 */
	protected abstract E buildEntry(final K key, final V value);
	
	public Maybe<V> get(final K key) {
		final Maybe<E> entry = getEntry(key);
		return entry.map(CacheEntry.<V, RuntimeException>getValueFunction());
	}
	
	public Maybe<V> clear(final K key) {
		final Maybe<E> entry = removeEntry(key);
		return entry.map(CacheEntry.<V, RuntimeException>getValueFunction());
	}
	
	public <X extends Exception> void clear(final Predicate2<? super K, ? super V, X> filter)
	throws X {
		assert null != filter;
		
		final Predicate1<E, X> filter_ = new Predicate1<E, X>() {
			public boolean evaluate(final E entry)
			throws X {
				return filter.evaluate(entry.getKey(), entry.getValue());
			}
		};
		for (final E entry : FunctionUtils.filter(filter_, _entries.values(), new HashSet<E>())) {
			removeEntry(entry.getKey());
		}
	}
	
	public void clear() {
		_entries.clear();
	}
	
	public void cleanup() {
		// Nothing to do.
	}
	
	/**
	 * Gets the entry associated to the given key from the receiver cache.
	 * <p>
	 * This method can overridden to handle entry accesses.
	 * 
	 * @param key The key. May be <code>null</code>.
	 * @return The entry.
	 */
	protected Maybe<E> getEntry(final K key) {
		return CollectionUtils.get(_entries, key);
	}
	
	/**
	 * Fills the receiver cache with the given entry.
	 * <p>
	 * This method is called whenever an entry is added to the cache.
	 * 
	 * @param entry The entry.
	 * @return The previously associated entry.
	 */
	protected Maybe<E> putEntry(final E entry) {
		assert null != entry;
		
		return CollectionUtils.put(_entries, entry.getKey(), entry);
	}
	
	/**
	 * Clears the entry associated to the given key from the receiver cache.
	 * <p>
	 * This method is called whenever an entry is removed from the cache, unless the entry is replaced by a call to {@link #putEntry(CacheEntry)} or
	 * {@link #clear()} is called.
	 * 
	 * @param key The key. May be <code>null</code>.
	 * @return The entry.
	 */
	protected Maybe<E> removeEntry(final K key) {
		return CollectionUtils.remove(_entries, key);
	}
	
	// Object.
	
	@Override
	public final String toString() {
		return TextUtils.computeDescription(this);
	}
	
	public void fillDescription(final Description description) {
		description.append("Entries", _entries.values());
	}
}
