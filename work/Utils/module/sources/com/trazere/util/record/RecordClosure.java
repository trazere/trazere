/*
 *  Copyright 2006-2009 Julien Dufour
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

import com.trazere.util.lang.Closure;
import com.trazere.util.lang.ResetableClosure;
import com.trazere.util.text.Description;
import com.trazere.util.value.RecordReader;
import com.trazere.util.value.ValueException;

/**
 * The {@link Closure} abstract class represents zero argument closures of records.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @see Record
 */
public abstract class RecordClosure<K, V>
extends ResetableClosure<Record<K, V>, RecordException> {
	private static final RecordClosure<?, ?> _EMPTY = build(SimpleRecord.<String, Object>build());
	
	/**
	 * Build a closure containing an empty record.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @return The built closure.
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> RecordClosure<K, V> build() {
		return (RecordClosure<K, V>) _EMPTY;
	}
	
	/**
	 * Build a closure containing the given record.
	 * 
	 * @param value The record.
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @return The built closure.
	 */
	public static <K, V> RecordClosure<K, V> build(final Record<K, V> value) {
		assert null != value;
		
		return new RecordClosure<K, V>() {
			@Override
			protected Record<K, V> compute() {
				return value;
			}
			
			@Override
			public void fillDescription(final Description description) {
				super.fillDescription(description);
				description.append("Value", value);
			}
		};
	}
	
	/**
	 * Build a closure getting its record from the given record reader and parameters.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param reader The record reader.
	 * @param parameters The parameters.
	 * @return The built closure.
	 */
	public static <K, V> RecordClosure<K, V> build(final RecordReader<K, V> reader, final Record<String, Object> parameters) {
		assert null != reader;
		assert null != parameters;
		
		return new RecordClosure<K, V>() {
			@Override
			protected Record<K, V> compute()
			throws RecordException {
				try {
					return reader.read(parameters);
				} catch (final ValueException exception) {
					throw new RecordException(exception);
				}
			}
			
			@Override
			public void fillDescription(final Description description) {
				super.fillDescription(description);
				description.append("Reader", reader);
				description.append("Parameters", parameters);
			}
		};
	}
}
