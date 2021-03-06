/*
 *  Copyright 2006-2015 Julien Dufour
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

import com.trazere.util.lang.Factory;
import java.util.Map;

/**
 * The {@link RecordFactory} interface defines factories of {@link Record records}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <R> Type of the records.
 * @see Record
 * @deprecated Use {@link com.trazere.core.record.RecordFactory}.
 */
@Deprecated
public interface RecordFactory<K, V, R extends Record<K, V>>
extends Factory<R, RecordException> {
	/**
	 * Builds a new empty record.
	 * 
	 * @return The built record.
	 * @throws RecordException When the record cannot be built.
	 */
	@Override
	public R build()
	throws RecordException;
	
	/**
	 * Builds a new record populated with the given fields.
	 * 
	 * @param fields Values of the fields identified by their keys.
	 * @return The built record builder.
	 * @throws RecordException When the record cannot be built.
	 * @deprecated To be removed.
	 */
	@Deprecated
	public R build(final Map<? extends K, ? extends V> fields)
	throws RecordException;
	
	/**
	 * Builds a new record populated with the fields of given record.
	 * 
	 * @param record Record to copy.
	 * @return The built record builder.
	 * @throws RecordException When the record cannot be built.
	 * @deprecated Use {@link com.trazere.core.record.RecordFactory#build(com.trazere.core.record.Record)}.
	 */
	@Deprecated
	public R build(final Record<? extends K, ? extends V> record)
	throws RecordException;
}
