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
package com.trazere.util.cache;

import com.trazere.util.collection.CollectionUtils;
import com.trazere.util.function.Predicate1;
import com.trazere.util.function.Predicates;
import com.trazere.util.text.Describable;
import com.trazere.util.text.Description;
import com.trazere.util.text.TextUtils;
import com.trazere.util.type.Maybe;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * The {@link SimpleCache} class implements caches.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
public class SimpleCache<K, V>
implements Cache<K, V>, Describable {
	public SimpleCache(final CachePolicy<K> policy) {
		assert null != policy;
		
		// Initialization.
		_policy = policy;
	}
	
	// Policy.
	
	/** Policy of the cache. */
	protected final CachePolicy<K> _policy;
	
	/**
	 * Gets the policy of the receiver cache.
	 * 
	 * @return The policy.
	 */
	public CachePolicy<K> getPolicy() {
		return _policy;
	}
	
	// Entries.
	
	/** Cache entries identified by their keys. */
	protected final Map<K, V> _entries = new HashMap<K, V>();
	
	@Override
	public boolean contains(final K key) {
		assert null != key;
		
		// Test.
		return _entries.containsKey(key);
	}
	
	@Override
	public Set<K> getKeys() {
		return Collections.unmodifiableSet(_entries.keySet());
	}
	
	@Override
	public int size() {
		return _entries.size();
	}
	
	@Override
	public Maybe<V> fill(final K key, final V value) {
		assert null != key;
		
		// Fill the cache.
		final Maybe<V> oldValue = CollectionUtils.put(_entries, key, value);
		
		// Dispose the value.
		if (oldValue.isSome()) {
			dispose(oldValue.asSome().getValue());
		}
		
		// Notify the policy.
		final Set<K> dirtyKeys = _policy.updatedEntry(key, new HashSet<K>());
		
		// Clean the cache up.
		clear(Predicates.<K, RuntimeException>any(dirtyKeys));
		
		return oldValue;
	}
	
	@Override
	public Maybe<V> get(final K key) {
		assert null != key;
		
		// Get.
		final Maybe<V> value = CollectionUtils.get(_entries, key);
		
		if (value.isSome()) {
			// Notify the policy.
			final Set<K> dirtyKeys = _policy.accessedEntry(key, new HashSet<K>());
			
			// Clean the cache up.
			clear(Predicates.<K, RuntimeException>any(dirtyKeys));
		}
		
		return value;
	}
	
	@Override
	public Maybe<V> clear(final K key) {
		assert null != key;
		
		// Remove.
		final Maybe<V> value = CollectionUtils.remove(_entries, key);
		if (value.isSome()) {
			// Dispose the value.
			dispose(value.asSome().getValue());
			
			// Notify the policy.
			_policy.removedEntry(key);
		}
		
		return value;
	}
	
	@Override
	public <X extends Exception> void clear(final Predicate1<? super K, X> filter)
	throws X {
		assert null != filter;
		
		final Iterator<Map.Entry<K, V>> entries = _entries.entrySet().iterator();
		while (entries.hasNext()) {
			final Map.Entry<K, V> entry = entries.next();
			final K key = entry.getKey();
			if (filter.evaluate(key)) {
				// Remove.
				entries.remove();
				
				// Dispose the value.
				dispose(entry.getValue());
				
				// Notify the policy.
				_policy.removedEntry(key);
			}
		}
	}
	
	@Override
	public void clear() {
		// Dispose.
		dispose(Collections.unmodifiableCollection(_entries.values()));
		
		// Remove all entries.
		_entries.clear();
		
		// Notify the policy.
		_policy.removedAllEntries();
	}
	
	/**
	 * Dispose the given value which has been removed from the receiver cache.
	 * <p>
	 * This method is called whenever a value is removed from the cache.
	 * 
	 * @param value The value. May be <code>null</code>.
	 */
	protected void dispose(final V value) {
		// Nothing to do.
	}
	
	/**
	 * Dispose all given values which has been removed from the receiver cache.
	 * <p>
	 * The default implementation calls {@link #dispose(Object)} for each value. It may be overrided by a more efficient implementation.
	 * 
	 * @param values The values.
	 */
	protected void dispose(final Collection<V> values) {
		assert null != values;
		
		for (final V value : values) {
			dispose(value);
		}
	}
	
	// Object.
	
	@Override
	public final String toString() {
		return TextUtils.computeDescription(this);
	}
	
	@Override
	public void fillDescription(final Description description) {
		description.append("Policy", _policy);
		description.append("Size", _entries.size());
		description.append("Keys", _entries.keySet());
	}
}
