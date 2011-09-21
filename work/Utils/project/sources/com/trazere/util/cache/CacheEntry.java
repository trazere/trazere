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

import com.trazere.util.function.Function1;
import com.trazere.util.text.Describable;
import com.trazere.util.text.Description;
import com.trazere.util.text.TextUtils;

/**
 * The {@link CacheEntry} class represents entries of caches.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
public class CacheEntry<K, V>
implements Describable {
	/**
	 * Instantiates a new entry.
	 * 
	 * @param key The key.
	 * @param value The value. May be <code>null</code>.
	 */
	public CacheEntry(final K key, final V value) {
		assert null != key;
		
		// Initialization.
		_key = key;
		_value = value;
	}
	
	// Key.
	
	/** Key of the entry. */
	protected final K _key;
	
	/**
	 * Gets the key of the receiver entry.
	 * 
	 * @return The key.
	 */
	public K getKey() {
		return _key;
	}
	
	/**
	 * Builds a function which gets the key of cache entries.
	 * 
	 * @param <K> Type of the key.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <K, X extends Exception> Function1<CacheEntry<? extends K, ?>, K, X> getKeyFunction() {
		return (Function1<CacheEntry<? extends K, ?>, K, X>) _GET_KEY_FUNCTION;
	}
	
	private static final Function1<? extends CacheEntry<?, ?>, ?, ?> _GET_KEY_FUNCTION = new Function1<CacheEntry<?, ?>, Object, RuntimeException>() {
		public Object evaluate(final CacheEntry<?, ?> entry) {
			assert null != entry;
			
			return entry.getKey();
		}
	};
	
	// Value.
	
	/** Value of the entry. May be <code>null</code>. */
	protected final V _value;
	
	/**
	 * Gets the value of the receiver entry.
	 * 
	 * @return The value. May be <code>null</code>.
	 */
	public V getValue() {
		return _value;
	}
	
	/**
	 * Builds a function which gets the value of cache entries.
	 * 
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <V, X extends Exception> Function1<CacheEntry<?, ? extends V>, V, X> getValueFunction() {
		return (Function1<CacheEntry<?, ? extends V>, V, X>) _GET_VALUE_FUNCTION;
	}
	
	private static final Function1<? extends CacheEntry<?, ?>, ?, ?> _GET_VALUE_FUNCTION = new Function1<CacheEntry<?, ?>, Object, RuntimeException>() {
		public Object evaluate(final CacheEntry<?, ?> entry) {
			assert null != entry;
			
			return entry.getValue();
		}
	};
	
	// Object.
	
	@Override
	public final String toString() {
		return TextUtils.computeDescription(this);
	}
	
	public void fillDescription(final Description description) {
		description.append("Key", _key);
		description.append("Value", _value);
	}
}
