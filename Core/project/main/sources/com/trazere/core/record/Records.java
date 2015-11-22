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
package com.trazere.core.record;

import com.trazere.core.collection.Lists;
import com.trazere.core.collection.Sets;
import com.trazere.core.util.Maybe;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

/**
 * The {@link Records} class provides various factories of {@link Record records}.
 * 
 * @see Record
 * @since 2.0
 */
public class Records {
	/**
	 * Builds an empty record.
	 * 
	 * @param <K> Type of the field keys.
	 * @return The built record.
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <K extends FieldKey<K, ?>> Record<K> empty() {
		return (Record<K>) EMPTY;
	}
	
	private static final Record<?> EMPTY = new BaseRecord<DummyFieldKey<?>>() {
		// Record.
		
		@Override
		public int size() {
			return 0;
		}
		
		@Override
		public boolean isEmpty() {
			return true;
		}
		
		@Override
		public boolean contains(final FieldKey<DummyFieldKey<?>, ?> key) {
			return false;
		}
		
		@Override
		public Set<DummyFieldKey<?>> keys() {
			return Sets.empty();
		}
		
		@Override
		public <V> Maybe<V> get(final FieldKey<DummyFieldKey<?>, V> key) {
			return Maybe.none();
		}
		
		@Override
		public <V> V getOptional(final FieldKey<DummyFieldKey<?>, V> key, final V defaultValue) {
			return defaultValue;
		}
		
		@Override
		public <V> V getMandatory(final FieldKey<DummyFieldKey<?>, V> key)
		throws MissingFieldException {
			throw new MissingFieldException("Missing field \"" + key + "\" in record " + this);
		}
		
		@Override
		public Collection<Field<DummyFieldKey<?>, ?>> fields() {
			return Lists.empty();
		}
	};
	
	private static abstract class DummyFieldKey<V>
	extends FieldKey<DummyFieldKey<?>, V> {
		public DummyFieldKey(final String label, final Class<V> type, final boolean nullable) {
			super(label, type, nullable);
		}
	}
	
	/**
	 * Builds a record containing one field corresponding to the given key and value.
	 * 
	 * @param <K> Type of the field keys.
	 * @param <V> Type of the value of the field.
	 * @param key Key of the field.
	 * @param value Value of the field.
	 * @return The built record.
	 * @throws NullFieldException When the value is <code>null</code> and the field is not nullable.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>, V> Record<K> fromKeyAndValue(final FieldKey<K, V> key, final V value) {
		return fromField(Fields.fromKeyAndValue(key, value));
	}
	
	/**
	 * Builds a record containing two fields corresponding to the given keys and values.
	 * 
	 * @param <K> Type of the field keys.
	 * @param <V1> Type of the value of the first field.
	 * @param <V2> Type of the value of the second field.
	 * @param key1 Key of the first field.
	 * @param value1 Value of the first field.
	 * @param key2 Key of the second field.
	 * @param value2 Value of the second field.
	 * @return The built record.
	 * @throws NullFieldException When some value is <code>null</code> and the corresponding field is not nullable.
	 * @throws DuplicateFieldException When sereval fields have the same key.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>, V1, V2> Record<K> fromKeysAndValues(final FieldKey<K, V1> key1, final V1 value1, final FieldKey<K, V2> key2, final V2 value2)
	throws DuplicateFieldException {
		return fromFields(Fields.fromKeyAndValue(key1, value1), Fields.fromKeyAndValue(key2, value2));
	}
	
	/**
	 * Builds a record containing three fields corresponding to the given keys and values.
	 * 
	 * @param <K> Type of the field keys.
	 * @param <V1> Type of the value of the first field.
	 * @param <V2> Type of the value of the second field.
	 * @param <V3> Type of the value of the third field.
	 * @param key1 Key of the first field.
	 * @param value1 Value of the first field.
	 * @param key2 Key of the second field.
	 * @param value2 Value of the second field.
	 * @param key3 Key of the third field.
	 * @param value3 Value of the third field.
	 * @return The built record.
	 * @throws NullFieldException When some value is <code>null</code> and the corresponding field is not nullable.
	 * @throws DuplicateFieldException When sereval fields have the same key.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>, V1, V2, V3> Record<K> fromKeysAndValues(final FieldKey<K, V1> key1, final V1 value1, final FieldKey<K, V2> key2, final V2 value2, final FieldKey<K, V3> key3, final V3 value3)
	throws DuplicateFieldException {
		return fromFields(Fields.fromKeyAndValue(key1, value1), Fields.fromKeyAndValue(key2, value2), Fields.fromKeyAndValue(key3, value3));
	}
	
	/**
	 * Builds a record containing the given field.
	 * 
	 * @param <K> Type of the field keys.
	 * @param field Field.
	 * @return The built record.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>> Record<K> fromField(final Field<K, ?> field) {
		return fromFields(Lists.fromElement(field));
	}
	
	/**
	 * Builds a record containing the given fields.
	 * 
	 * @param <K> Type of the field keys.
	 * @param field1 First field.
	 * @param field2 Second field.
	 * @return The built record.
	 * @throws DuplicateFieldException When sereval fields have the same key.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>> Record<K> fromFields(final Field<K, ?> field1, final Field<K, ?> field2)
	throws DuplicateFieldException {
		return fromFields(Lists.fromElements(field1, field2));
	}
	
	/**
	 * Builds a record containing the given fields.
	 * 
	 * @param <K> Type of the field keys.
	 * @param field1 First field.
	 * @param field2 Second field.
	 * @param field3 Third field.
	 * @return The built record.
	 * @throws DuplicateFieldException When sereval fields have the same key.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>> Record<K> fromFields(final Field<K, ?> field1, final Field<K, ?> field2, final Field<K, ?> field3)
	throws DuplicateFieldException {
		return fromFields(Lists.fromElements(field1, field2, field3));
	}
	
	/**
	 * Builds a record containing the given fields.
	 * 
	 * @param <K> Type of the field keys.
	 * @param fields Fields.
	 * @return The built record.
	 * @throws DuplicateFieldException When sereval fields have the same key.
	 * @since 2.0
	 */
	@SafeVarargs
	public static <K extends FieldKey<K, ?>> Record<K> fromFields(final Field<K, ?>... fields)
	throws DuplicateFieldException {
		return fromFields(Arrays.asList(fields));
	}
	
	/**
	 * Builds a record containing the given fields.
	 * <p>
	 * The given fields must all have different keys.
	 * 
	 * @param <K> Type of the field keys.
	 * @param fields Fields.
	 * @return The built record.
	 * @throws DuplicateFieldException When sereval fields have the same key.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>> Record<K> fromFields(final Iterable<? extends Field<K, ?>> fields)
	throws DuplicateFieldException {
		final RecordBuilder<K, ?> builder = new SimpleRecordBuilder<>();
		builder.addAll(fields);
		return builder.build();
	}
	
	// TODO: union, sub...
	
	private Records() {
		// Prevent instantiation.
	}
}
