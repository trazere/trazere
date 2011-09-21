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

import com.trazere.util.text.Description;

/**
 * The {@link TimedCacheEntry} class represents cache entries which keep track of their creation date.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
public class TimedCacheEntry<K, V>
extends CacheEntry<K, V> {
	/**
	 * Instantiates a new entry with the current creation date.
	 * 
	 * @param key The key.
	 * @param value The value. May be <code>null</code>.
	 */
	public TimedCacheEntry(final K key, final V value) {
		this(key, value, System.currentTimeMillis());
	}
	
	/**
	 * Instantiates a new entry.
	 * 
	 * @param key The key.
	 * @param value The value. May be <code>null</code>.
	 * @param date The creation date.
	 */
	protected TimedCacheEntry(final K key, final V value, final long date) {
		super(key, value);
		
		// Initialization.
		_date = date;
	}
	
	// Date.
	
	/** Creation date of the entry. */
	protected final long _date;
	
	/**
	 * Gets the creation date of the receiver entry.
	 * 
	 * @return The creation date in milliseconds.
	 * @see System#currentTimeMillis()
	 */
	public long getDate() {
		return _date;
	}
	
	// Object.
	
	@Override
	public void fillDescription(final Description description) {
		super.fillDescription(description);
		description.append("Date", _date);
	}
}
