/*
 *  Copyright 2008 Julien Dufour
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

import com.trazere.util.Assert;
import com.trazere.util.lang.CannotComputeValueException;
import com.trazere.util.lang.ObjectUtils;
import java.util.Comparator;

/**
 * The {@link RecordComparator} class represents comparators of records based on the content of a single field.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <R> Type of the records.
 */
public class RecordComparator<K, V, R extends Record<? super K, ? extends V>>
implements Comparator<R> {
	/** Key identified the fields used for comparison. */
	protected final K _key;
	
	/** Comparator of the values of the fields. */
	protected final Comparator<V> _comparator;
	
	/**
	 * Instantiate a new record comparator.
	 * 
	 * @param key Key identifying the field used for comparison.
	 * @param comparator Comparator of the svalues of the fields.
	 */
	public RecordComparator(final K key, final Comparator<V> comparator) {
		Assert.notNull(key);
		Assert.notNull(comparator);
		
		// Initialization.
		_key = key;
		_comparator = comparator;
	}
	
	/**
	 * Get the key identifying the fields used for comparison.
	 * 
	 * @return The key.
	 */
	public K getKey() {
		return _key;
	}
	
	public int compare(final R record1, final R record2) {
		try {
			return ObjectUtils.compare(record1.get(_key), record2.get(_key), _comparator);
		} catch (final RecordException exception) {
			throw new CannotComputeValueException("Failed reading records " + record1 + " and " + record2, exception);
		}
	}
}
