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
package com.trazere.util.record;

import java.util.Map;

/**
 * The {@link RecordFactory} interface defines factories of {@link Record records}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <R> Type of the records.
 * @see Record
 */
public interface RecordFactory<K, V, R extends Record<K, V>> {
	/**
	 * Build a new empty record.
	 * 
	 * @return The built record.
	 * @throws RecordException When the record cannot be built.
	 */
	public R build()
	throws RecordException;
	
	/**
	 * Build a new record populated with the given fields.
	 * 
	 * @param fields Values of the fields identified by their keys.
	 * @return The built record builder.
	 * @throws RecordException When the record cannot be built.
	 */
	public R build(final Map<? extends K, ? extends V> fields)
	throws RecordException;
	
	/**
	 * Build a new record populated with the fields of given record.
	 * 
	 * @param record Record to copy.
	 * @return The built record builder.
	 * @throws RecordException When the record cannot be built.
	 */
	public R build(final Record<? extends K, ? extends V> record)
	throws RecordException;
}
