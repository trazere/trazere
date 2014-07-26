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
package com.trazere.core.cache;

import com.trazere.core.collection.CollectionAccumulators;
import com.trazere.core.collection.CollectionFactories;
import com.trazere.core.collection.CollectionUtils;
import com.trazere.core.functional.Predicate;
import com.trazere.core.functional.Predicates;
import com.trazere.core.imperative.Accumulator;
import com.trazere.core.text.Describable;
import com.trazere.core.text.Description;
import com.trazere.core.text.TextUtils;
import com.trazere.core.util.Maybe;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@link BaseCache} class provides a skeleton implementation of caches.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
public abstract class BaseCache<K, V>
implements Cache<K, V>, Describable {
	public BaseCache(final CachePolicy<K> policy) {
		assert null != policy;
		
		// Initialization.
		_policy = policy;
		_policyState = policy.build();
	}
	
	// Retention.
	
	/** Retention policy of the cache. */
	protected final CachePolicy<K> _policy;
	
	/** Retention state of the cache. */
	protected final CachePolicy.State<K> _policyState;
	
	/**
	 * Gets the retention policy of the receiver cache.
	 * 
	 * @return The retention policy.
	 */
	public CachePolicy<K> getPolicy() {
		return _policy;
	}
	
	// Entries.
	
	@Override
	public Maybe<V> fill(final K key, final V value) {
		// Fill the cache.
		final Maybe<V> oldValue = setEntry(key, value);
		
		// Dispose the previous value.
		if (oldValue.isSome()) {
			dispose(oldValue.asSome().getValue());
		}
		
		// Notify the policy state and clear the dirty entries.
		final Accumulator<K, Set<K>> dirtyKeys = _policyState.updatedEntry(key, CollectionAccumulators.add(new HashSet<K>()));
		clear(Predicates.<K>values(dirtyKeys.get()));
		
		return oldValue;
	}
	
	@Override
	public Maybe<V> get(final K key) {
		assert null != key;
		
		// Read the cache.
		final Maybe<V> value = getEntry(key);
		
		if (value.isSome()) {
			// Notify the policy state and clear the dirty entries.
			final Accumulator<K, Set<K>> dirtyKeys = _policyState.accessedEntry(key, CollectionAccumulators.add(new HashSet<K>()));
			clear(Predicates.<K>values(dirtyKeys.get()));
		}
		
		return value;
	}
	
	@Override
	public void clear(final K key) {
		// Clean the cache.
		final Maybe<V> value = removeEntry(key);
		if (value.isSome()) {
			// Dispose the value.
			dispose(value.asSome().getValue());
			
			// Notify the policy.
			_policyState.clearedEntry(key);
		}
	}
	
	@Override
	public void clear(final Predicate<? super K> filter) {
		for (final K key : CollectionUtils.filter(keys(), filter, CollectionFactories.hashSet())) {
			clear(key);
		}
	}
	
	@Override
	public void clear() {
		for (final K key : new HashSet<>(keys())) {
			// Clean the cache.
			final Maybe<V> value = removeEntry(key);
			if (value.isSome()) {
				// Dispose the value.
				dispose(value.asSome().getValue());
			}
		}
		
		// Notify the policy.
		_policyState.clearedAllEntries();
	}
	
	/**
	 * Sets the entry associated to the given key.
	 * 
	 * @param key Key of the entry.
	 * @param value Value of the entry.
	 * @return The previous value of the entry, or nothing when no entries was associated to the key.
	 */
	protected abstract Maybe<V> setEntry(final K key, final V value);

	/**
	 * Gets the value of the entry associated to the given key.
	 * 
	 * @param key Key of the entry to read.
	 * @return The value of the entry associated to the key, or nothing when no entries is associated to the key.
	 */
	protected abstract Maybe<V> getEntry(final K key);

	/**
	 * Removes the entry associated to the given key.
	 * 
	 * @param key Key of the entry to remove.
	 * @return The value of the removed entry, or nothing when no entries is associated to the key.
	 */
	protected abstract Maybe<V> removeEntry(final K key);

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
	
	// Object.
	
	@Override
	public final String toString() {
		return TextUtils.description(this);
	}
	
	@Override
	public void appendDescription(final Description description) {
		description.append("Policy", _policy);
		description.append("Size", size());
		description.append("Keys", keys());
	}
}
