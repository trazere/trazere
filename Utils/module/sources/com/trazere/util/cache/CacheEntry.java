/*
 *  Copyright 2006-2008 Julien Dufour
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

import com.trazere.util.text.Describable;
import com.trazere.util.text.TextUtils;

/**
 * The {@link CacheEntry} class represents entries of caches.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
public class CacheEntry<K, V>
implements Describable {
	/** Key of the entry. */
	protected final K _key;
	
	/** Value of the entry. May be <code>null</code>. */
	protected final V _value;
	
	/**
	 * Instantiate a new entry with the given key and value.
	 * 
	 * @param key Key of the entry. May be <code>null</code>.
	 * @param value Value of the entry. May be <code>null</code>.
	 */
	public CacheEntry(final K key, final V value) {
		assert null != key;
		
		// Initialization.
		_key = key;
		_value = value;
	}
	
	/**
	 * Get the key of the receiver entry.
	 * 
	 * @return The key.
	 */
	public K getKey() {
		return _key;
	}
	
	/**
	 * Get the value of the entry.
	 * 
	 * @return The value . May be <code>null</code>.
	 */
	public V getValue() {
		return _value;
	}
	
	@Override
	public final String toString() {
		return TextUtils.computeDescription(this);
	}
	
	public void fillDescription(final StringBuilder builder) {
		builder.append(" - Key = ").append(_key);
		builder.append(" - Value = ").append(_value);
	}
}
