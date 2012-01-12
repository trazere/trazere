/*
 *  Copyright 2006-2012 Julien Dufour
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
package com.trazere.util.record;

import com.trazere.util.lang.ViewComparator;
import com.trazere.util.text.Describable;
import com.trazere.util.text.Description;
import com.trazere.util.text.TextUtils;
import java.util.Comparator;

/**
 * The {@link RecordComparator} class represents comparators of records based on the value of a single field.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <R> Type of the records.
 */
public class RecordComparator<K, V, R extends Record<? super K, ? extends V>>
extends ViewComparator<R, V>
implements Describable {
	/** Key identifying the field used for comparison. */
	protected final K _key;
	
	/**
	 * Instantiate a new record comparator.
	 * 
	 * @param key Key identifying the field used for comparison.
	 * @param comparator Comparator of the values of the field.
	 */
	public RecordComparator(final K key, final Comparator<V> comparator) {
		super(comparator);
		
		// Checks.
		assert null != key;
		
		// Initialization.
		_key = key;
	}
	
	/**
	 * Get the key identifying the field used for comparison.
	 * 
	 * @return The key.
	 */
	public K getKey() {
		return _key;
	}
	
	@Override
	protected V computeValue(final R record) {
		assert null != record;
		
		try {
			return record.get(_key);
		} catch (final RecordException exception) {
			throw new RuntimeException("Failed reading record " + record, exception);
		}
	}
	
	@Override
	public String toString() {
		return TextUtils.computeDescription(this);
	}
	
	public void fillDescription(final Description description) {
		description.append("Key", _key);
	}
}
