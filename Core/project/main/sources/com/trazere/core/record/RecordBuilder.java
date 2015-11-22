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

import com.trazere.core.design.Factory;
import java.util.Set;

/**
 * The {@link RecordBuilder} interface defines builders of {@link Record records}.
 * 
 * @param <K> Type of the field keys.
 * @param <R> Type of the records.
 * @see Record
 * @since 2.0
 */
public interface RecordBuilder<K extends FieldKey<K, ?>, R extends Record<K>>
extends Factory<R> {
	/**
	 * Gets the current size of the record being built by this builder.
	 * <p>
	 * The size of a record corresponds to the number of fields it contains.
	 * 
	 * @return The size of the record.
	 * @since 2.0
	 */
	default int size() {
		return keys().size();
	}
	
	/**
	 * Indicates whether the record being built by this builder is empty or not.
	 * <p>
	 * A record is empty when it contains no fields.
	 * 
	 * @return <code>true</code> when the record is empty, <code>false</code> otherwise.
	 * @since 2.0
	 */
	default boolean isEmpty() {
		return 0 == size();
	}
	
	/**
	 * Indicates whether the record being built by this builder contains a field identified by the given key or not.
	 * 
	 * @param key Key of the field to look for.
	 * @return <code>true</code> when the record contains a field identified by the key, <code>false</code> otherwise.
	 * @since 2.0
	 */
	default boolean contains(final FieldKey<K, ?> key) {
		return keys().contains(key);
	}
	
	/**
	 * Gets a view of the keys identifying the fields of record being built by this builder.
	 * 
	 * @return A set view of the field keys.
	 * @since 2.0
	 */
	Set<? extends FieldKey<K, ?>> keys();
	
	/**
	 * Adds the field corresponding to the given key and value to the record being built by this builder.
	 * <p>
	 * The record must not already contain a field identified by the given key.
	 * 
	 * @param <V> Type of the value of the field.
	 * @param key Key identifying the field to add.
	 * @param value Value of the field to add.
	 * @throws NullFieldException When the value is <code>null</code> and the field is not nullable.
	 * @throws DuplicateFieldException When some field is already identified by the given key.
	 * @since 2.0
	 */
	default <V> void add(final FieldKey<K, V> key, final V value)
	throws NullFieldException, DuplicateFieldException {
		add(Fields.fromKeyAndValue(key, value));
	}
	
	/**
	 * Adds the given field to the record being built by this builder.
	 * <p>
	 * The record must not already contain a field identified by the key of the given field.
	 * 
	 * @param field Field to add.
	 * @throws DuplicateFieldException When some field is already identified by the key of the given field.
	 * @since 2.0
	 */
	default void add(final Field<K, ?> field)
	throws DuplicateFieldException {
		final FieldKey<K, ?> key = field.getKey();
		if (!contains(key)) {
			set(field);
		} else {
			throw new DuplicateFieldException("Conflicting field for \"" + key + "\"");
		}
	}
	
	/**
	 * Adds the given fields to the record being built by this builder.
	 * <p>
	 * The record must not already contain any field identified by the keys of the given fields.
	 * 
	 * @param fields Fields to add.
	 * @throws DuplicateFieldException When some field is already identified by the key of some given field.
	 * @since 2.0
	 */
	default void addAll(final Iterable<? extends Field<K, ?>> fields)
	throws DuplicateFieldException {
		for (final Field<K, ?> field : fields) {
			add(field);
		}
	}
	
	/**
	 * Adds the fields of the given record to the record being built by this builder.
	 * <p>
	 * The record must not already contain any field identified by the keys of the fields of the given record.
	 * 
	 * @param record Record containing the fields to add.
	 * @throws DuplicateFieldException When some field is already identified by some key of the given record.
	 * @since 2.0
	 */
	default void addAll(final Record<K> record)
	throws DuplicateFieldException {
		addAll(record.fields());
	}
	
	// TODO: replace (MissingFieldException)
	
	/**
	 * Completes the record being built by this builder with the field corresponding to the given key and value.
	 * <p>
	 * The field is added only when the record does not already contain a field identified by the given key.
	 * 
	 * @param <V> Type of the value of the field.
	 * @param key The key identifying the field to complete the record with.
	 * @param value The value of the field to complete the record with.
	 * @throws NullFieldException When the value is <code>null</code> and the field is not nullable.
	 * @since 2.0
	 */
	default <V> void complete(final FieldKey<K, V> key, final V value)
	throws NullFieldException {
		complete(Fields.fromKeyAndValue(key, value));
	}
	
	/**
	 * Completes the record being built by this builder with the given field.
	 * <p>
	 * The field is added only when the record does not already contain a field identified by the key of the given field.
	 * 
	 * @param field Field to complete the record with.
	 * @since 2.0
	 */
	default void complete(final Field<K, ?> field) {
		if (!contains(field.getKey())) {
			set(field);
		}
	}
	
	/**
	 * Complete the record being built by this builder with the given fields.
	 * <p>
	 * The fields are added only when the record does not already contain a field identified by their key.
	 * 
	 * @param fields Fields to complete the record with.
	 * @since 2.0
	 */
	default void completeAll(final Iterable<? extends Field<K, ?>> fields) {
		for (final Field<K, ?> field : fields) {
			complete(field);
		}
	}
	
	/**
	 * Complete the record being built by this builder with the fields of the given record.
	 * <p>
	 * The fields are added only when the record does not already contain a field identified by their key.
	 * 
	 * @param record Record containing the fields to complete the record with.
	 * @since 2.0
	 */
	default void completeAll(final Record<K> record) {
		completeAll(record.fields());
	}
	
	/**
	 * Sets the field corresponding to the given key and value in the record being built by this builder.
	 * <p>
	 * The record may already contain a field identified by the given key.
	 * 
	 * @param <V> Type of the value of the field.
	 * @param key The key identifying the field to set.
	 * @param value The value of the field to set.
	 * @throws NullFieldException When the value is <code>null</code> and the field is not nullable.
	 * @since 2.0
	 */
	default <V> void set(final FieldKey<K, V> key, final V value) {
		set(Fields.fromKeyAndValue(key, value));
	}
	
	/**
	 * Sets the given field in the record being built by this builder.
	 * <p>
	 * The record may already contain a field identified by the key of the given field.
	 * 
	 * @param field Field to set.
	 * @since 2.0
	 */
	void set(Field<K, ?> field);
	
	/**
	 * Sets the given fields in the record being built by this builder.
	 * <p>
	 * The record may already contain fields identified keys of the given fields.
	 * 
	 * @param fields Fields to set.
	 * @since 2.0
	 */
	default void setAll(final Iterable<? extends Field<K, ?>> fields) {
		for (final Field<K, ?> field : fields) {
			set(field);
		}
	}
	
	/**
	 * Sets the fields of the given record in the record being built by this builder.
	 * <p>
	 * The record may already contain fields identified keys of the given record.
	 * 
	 * @param record Record containing the fields to set.
	 * @since 2.0
	 */
	default void setAll(final Record<K> record) {
		addAll(record.fields());
	}
	
	/**
	 * Removes the field identified by the given key from the record being built by this builder.
	 * 
	 * @param key Key of the field to remove.
	 * @since 2.0
	 */
	void remove(FieldKey<K, ?> key);
	
	/**
	 * Removes the fields identified by the given keys from the record being built by this builder.
	 * 
	 * @param keys Keys of the fields to remove.
	 * @since 2.0
	 */
	default void removeAll(final Iterable<? extends FieldKey<K, ?>> keys) {
		for (final FieldKey<K, ?> key : keys) {
			remove(key);
		}
	}
	
	/**
	 * Removes all fields from the record being built by this builder.
	 * 
	 * @since 2.0
	 */
	void clear();
	
	/**
	 * Builds the record.
	 * 
	 * @return The built record.
	 * @since 2.0
	 */
	@Override
	R build();
}
