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

import com.trazere.core.functional.Thunk;
import com.trazere.core.util.Maybe;
import java.util.Collection;
import java.util.Set;

/**
 * The {@link Record} interface defines records.
 * <p>
 * A record is a collection of {@link Field fields} uniquely identified by their {@link FieldKey keys}.
 * 
 * @param <K> Type of the field keys.
 * @see Field
 * @since 2.0
 */
public interface Record<K extends FieldKey<K, ?>> {
	/**
	 * Gets the size of this record.
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
	 * Indicates whether this record is empty or not.
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
	 * Indicates whether this record contains a field identified by the given key or not.
	 * 
	 * @param key Key of the field to look for.
	 * @return <code>true</code> when the record contains a field identified by the given key, <code>false</code> otherwise.
	 * @since 2.0
	 */
	default boolean contains(final FieldKey<? extends K, ?> key) {
		return keys().contains(key);
	}
	
	/**
	 * Gets a view of the keys identifying the fields of this record.
	 * 
	 * @return An unmodiable set of the field keys.
	 * @since 2.0
	 */
	Set<? extends FieldKey<? extends K, ?>> keys();
	
	/**
	 * Gets the value of the field of this record identified by the given key.
	 * 
	 * @param <V> Type of the value of the field.
	 * @param key Key of the field to get.
	 * @return The value of the field, or nothing when the record contains no fields are identified by the key.
	 * @throws InvalidFieldException When the value of the field cannot be read.
	 * @throws NullFieldException When the value of the field is <code>null</code> and whereas the field is not nullable.
	 * @throws IncompatibleFieldException When the value of the field is not compatible with the type of the field.
	 * @since 2.0
	 */
	<V> Maybe<V> get(FieldKey<? extends K, ? extends V> key)
	throws InvalidFieldException, NullFieldException, IncompatibleFieldException;
	
	/**
	 * Gets the value of the optional field of this record identified by the given key.
	 * 
	 * @param <V> Type of the value of the field.
	 * @param key Key of the field to get.
	 * @param defaultValue Default value of the field. May be <code>null</code>.
	 * @return The value of the field, or the given default value when no fields are identified by the given key. May be <code>null</code>.
	 * @throws InvalidFieldException When the value of the field cannot be read.
	 * @throws NullFieldException When the value of the field is <code>null</code> and whereas the field is not nullable.
	 * @throws IncompatibleFieldException When the value of the field is not compatible with the type of the field.
	 * @since 2.0
	 */
	default <V> V getOptional(final FieldKey<? extends K, ? extends V> key, final V defaultValue)
	throws InvalidFieldException, NullFieldException, IncompatibleFieldException {
		return this.<V>get(key).get(defaultValue);
	}
	
	/**
	 * Gets the value of the optional field of this record identified by the given key.
	 * 
	 * @param <V> Type of the value of the field.
	 * @param key Key of the field to get.
	 * @param defaultValue Default value of the field. May be <code>null</code>.
	 * @return The value of the field, or the given default value when no fields are identified by the given key. May be <code>null</code>.
	 * @throws InvalidFieldException When the value of the field cannot be read.
	 * @throws NullFieldException When the value of the field is <code>null</code> and whereas the field is not nullable.
	 * @throws IncompatibleFieldException When the value of the field is not compatible with the type of the field.
	 * @since 2.0
	 */
	default <V> V getOptional(final FieldKey<? extends K, ? extends V> key, final Thunk<? extends V> defaultValue)
	throws InvalidFieldException, NullFieldException, IncompatibleFieldException {
		return this.<V>get(key).get(defaultValue);
	}
	
	/**
	 * Gets the value of the mandatory field of this record identified by the given key.
	 * 
	 * @param <V> Type of the value of the field.
	 * @param key Key of the field.
	 * @return The value of the field. May be <code>null</code>.
	 * @throws MissingFieldException When no fields are identified by the given key in the record.
	 * @throws InvalidFieldException When the value of the field cannot be read.
	 * @throws NullFieldException When the value of the field is <code>null</code> and whereas the field is not nullable.
	 * @throws IncompatibleFieldException When the value of the field is not compatible with the type of the field.
	 * @since 2.0
	 */
	default <V> V getMandatory(final FieldKey<? extends K, ? extends V> key)
	throws MissingFieldException, InvalidFieldException, NullFieldException, IncompatibleFieldException {
		final Maybe<V> value = get(key);
		if (value.isSome()) {
			return value.asSome().getValue();
		} else {
			throw new MissingFieldException("Missing field \"" + key + "\" in record " + this);
		}
	}
	
	/**
	 * Gets a view of the fields of this record.
	 * 
	 * @return An unmodiable collection of the fields.
	 * @since 2.0
	 */
	Collection<? extends Field<? extends K, ?>> fields();
	
	// TODO
	//	/**
	//	 * Populates the given record builder with the fields of this record.
	//	 *
	//	 * @param <B>
	//	 * @param builder
	//	 * @return
	//	 * @throws DuplicateFieldException
	//	 */
	//	<B extends RecordBuilder<? super K, ?>> B populate(final B builder)
	//	throws DuplicateFieldException;
	//
	//	<B extends RecordBuilder<? super K, ?>> B populate(final B builder, final Predicate<? super K> keys)
	//	throws DuplicateFieldException;
}
