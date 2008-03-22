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

import java.util.Map;

/**
 * The {@link RecordFactory} interface defines factories of records.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <R> Type of the records.
 */
public interface RecordFactory<K, V, R extends Record<K, V>> {
	/**
	 * Build a new empty record.
	 * 
	 * @return The built record.
	 * @throws RecordException
	 */
	public R build()
	throws RecordException;
	
	/**
	 * Build a new record containing fields corresponding to the given map.
	 * 
	 * @param values Values identified by the keys of the fields to fill the record with.
	 * @return The built record builder.
	 * @throws RecordException
	 */
	public R build(final Map<K, V> values)
	throws RecordException;
}
