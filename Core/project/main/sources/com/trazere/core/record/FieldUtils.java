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

import com.trazere.core.collection.CollectionAccumulators;
import com.trazere.core.collection.CollectionFactory;
import com.trazere.core.functional.Function;
import com.trazere.core.functional.Functions;
import com.trazere.core.functional.Thunk;
import com.trazere.core.imperative.Accumulator;
import com.trazere.core.lang.Iterables;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.MaybeUtils;
import com.trazere.core.util.Serializer;
import com.trazere.core.util.SerializerFunctions;
import java.util.Collection;

/**
 * The {@link FieldUtils} class provides utilities related to {@link Field fields}.
 * 
 * @see Field
 * @since 2.0
 */
public class FieldUtils {
	/**
	 * Reads the value of the given field.
	 * <p>
	 * This methods does really do anything, it is provided as a convenience to allow homogenous value reading.
	 * 
	 * @param <V> Type of the value.
	 * @param description Description of the field to read (for error messages).
	 * @param representation Representation of the field to read.
	 * @return The read value, or nothing when there is no representation.
	 * @since 2.0
	 */
	public static <V> Maybe<V> read(final String description, final Maybe<? extends V> representation) {
		return read(description, representation, Functions.<V>identity());
	}
	
	/**
	 * Reads the value of the given field.
	 * 
	 * @param <R> Type of the representation.
	 * @param <V> Type of the value.
	 * @param description Description of the field to read (for error messages).
	 * @param representation Representation of the field to read.
	 * @param deserializer Function to use to deserialize the representation.
	 * @return The read value, or nothing when there is no representation.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static <R, V> Maybe<V> read(final String description, final Maybe<? extends R> representation, final Function<? super R, ? extends V> deserializer)
	throws InvalidFieldException {
		try {
			return representation.map(deserializer);
		} catch (final Exception exception) {
			throw new InvalidFieldException("Invalid " + description + " field \"" + representation + "\"", exception);
		}
	}
	
	/**
	 * Reads the value of the given field.
	 * 
	 * @param <R> Type of the representation.
	 * @param <V> Type of the value.
	 * @param description Description of the field to read (for error messages).
	 * @param representation Representation of the field to read.
	 * @param deserializer Serializer to use to deserialize the representation.
	 * @return The read value, or nothing when there is no representation.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static <R, V> Maybe<V> read(final String description, final Maybe<? extends R> representation, final Serializer<? extends V, ? super R> deserializer)
	throws InvalidFieldException {
		return read(description, representation, SerializerFunctions.deserialize(deserializer));
	}
	
	/**
	 * Reads the value of the given optional field.
	 * 
	 * @param <V> Type of the value.
	 * @param description Description of the field to read (for error messages).
	 * @param representation Representation of the field to read.
	 * @param defaultValue Default value when the field is missing.
	 * @return The read value, or the default value when there is no representation.
	 * @since 2.0
	 */
	public static <V> V readOptional(final String description, final Maybe<? extends V> representation, final V defaultValue) {
		return readOptional(description, representation, Functions.<V>identity(), defaultValue);
	}
	
	/**
	 * Reads the value of the given optional field.
	 * 
	 * @param <R> Type of the representation.
	 * @param <V> Type of the value.
	 * @param description Description of the field to read (for error messages).
	 * @param representation Representation of the field to read.
	 * @param deserializer Function to use to deserialize the representation.
	 * @param defaultValue Default value when the field is missing.
	 * @return The read value, or the default value when there is no representation.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static <R, V> V readOptional(final String description, final Maybe<? extends R> representation, final Function<? super R, ? extends V> deserializer, final V defaultValue)
	throws InvalidFieldException {
		if (representation.isSome()) {
			try {
				return deserializer.evaluate(representation.asSome().getValue());
			} catch (final Exception exception) {
				throw new InvalidFieldException("Invalid " + description + " field \"" + representation + "\"", exception);
			}
		} else {
			return defaultValue;
		}
	}
	
	/**
	 * Reads the value of the given optional field.
	 * 
	 * @param <R> Type of the representation.
	 * @param <V> Type of the value.
	 * @param description Description of the field to read (for error messages).
	 * @param representation Representation of the field to read.
	 * @param deserializer Serializer to use to deserialize the representation.
	 * @param defaultValue Default value when the field is missing.
	 * @return The read value, or the default value when there is no representation.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static <R, V> V readOptional(final String description, final Maybe<? extends R> representation, final Serializer<? extends V, ? super R> deserializer, final V defaultValue)
	throws InvalidFieldException {
		return readOptional(description, representation, SerializerFunctions.deserialize(deserializer), defaultValue);
	}
	
	/**
	 * Reads the value of the given optional field.
	 * 
	 * @param <V> Type of the value.
	 * @param description Description of the field to read (for error messages).
	 * @param representation Representation of the field to read.
	 * @param defaultValue Default value when the field is missing.
	 * @return The read value, or the default value when there is no representation.
	 * @since 2.0
	 */
	public static <V> V readOptional(final String description, final Maybe<? extends V> representation, final Thunk<? extends V> defaultValue) {
		return readOptional(description, representation, Functions.<V>identity(), defaultValue);
	}
	
	/**
	 * Reads the value of the given optional field.
	 * 
	 * @param <R> Type of the representation.
	 * @param <V> Type of the value.
	 * @param description Description of the field to read (for error messages).
	 * @param representation Representation of the field to read.
	 * @param deserializer Function to use to deserialize the representation.
	 * @param defaultValue Default value when the field is missing.
	 * @return The read value, or the default value when there is no representation.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static <R, V> V readOptional(final String description, final Maybe<? extends R> representation, final Function<? super R, ? extends V> deserializer, final Thunk<? extends V> defaultValue)
	throws InvalidFieldException {
		if (representation.isSome()) {
			try {
				return deserializer.evaluate(representation.asSome().getValue());
			} catch (final Exception exception) {
				throw new InvalidFieldException("Invalid " + description + " field \"" + representation + "\"", exception);
			}
		} else {
			return defaultValue.evaluate();
		}
	}
	
	/**
	 * Reads the value of the given optional field.
	 * 
	 * @param <R> Type of the representation.
	 * @param <V> Type of the value.
	 * @param description Description of the field to read (for error messages).
	 * @param representation Representation of the field to read.
	 * @param deserializer Serializer to use to deserialize the representation.
	 * @param defaultValue Default value when the field is missing.
	 * @return The read value, or the default value when there is no representation.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static <R, V> V readOptional(final String description, final Maybe<? extends R> representation, final Serializer<? extends V, ? super R> deserializer, final Thunk<? extends V> defaultValue)
	throws InvalidFieldException {
		return readOptional(description, representation, SerializerFunctions.deserialize(deserializer), defaultValue);
	}
	
	/**
	 * Reads the value of the given mandatory field.
	 * 
	 * @param <V> Type of the value.
	 * @param description Description of the field to read (for error messages).
	 * @param representation Representation of the field to read.
	 * @return The read value.
	 * @throws MissingFieldException When the representation is missing.
	 * @since 2.0
	 */
	public static <V> V readMandatory(final String description, final Maybe<? extends V> representation)
	throws MissingFieldException {
		return readMandatory(description, representation, Functions.<V>identity());
	}
	
	/**
	 * Reads the value of the given mandatory field.
	 * 
	 * @param <R> Type of the representation.
	 * @param <V> Type of the value.
	 * @param description Description of the field to read (for error messages).
	 * @param representation Representation of the field to read.
	 * @param deserializer Function to use to deserialize the representation.
	 * @return The read value.
	 * @throws MissingFieldException When the representation is missing.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static <R, V> V readMandatory(final String description, final Maybe<? extends R> representation, final Function<? super R, ? extends V> deserializer)
	throws MissingFieldException, InvalidFieldException {
		if (representation.isSome()) {
			try {
				return deserializer.evaluate(representation.asSome().getValue());
			} catch (final Exception exception) {
				throw new InvalidFieldException("Invalid " + description + " field \"" + representation + "\"", exception);
			}
		} else {
			throw new MissingFieldException("Missing " + description + " field");
		}
	}
	
	/**
	 * Reads the value of the given mandatory field.
	 * 
	 * @param <R> Type of the representation.
	 * @param <V> Type of the value.
	 * @param description Description of the field to read (for error messages).
	 * @param representation Representation of the field to read.
	 * @param deserializer Serializer to use to deserialize the representation.
	 * @return The read value.
	 * @throws MissingFieldException When the representation is missing.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static <R, V> V readMandatory(final String description, final Maybe<? extends R> representation, final Serializer<? extends V, ? super R> deserializer)
	throws MissingFieldException, InvalidFieldException {
		return readMandatory(description, representation, SerializerFunctions.deserialize(deserializer));
	}
	
	/**
	 * Reads the values of the given field.
	 * 
	 * @param <V> Type of the values.
	 * @param <C> Type of the collection of values to build.
	 * @param description Description of the field to read (for error messages).
	 * @param representations Representations of the field to read.
	 * @param factory Factory of the collection of values.
	 * @return The read values.
	 * @since 2.0
	 */
	public static <V, C extends Collection<? super V>> C readMultiple(final String description, final Iterable<? extends V> representations, final CollectionFactory<? super V, ? extends C> factory) {
		return readMultiple(description, representations, Functions.<V>identity(), factory);
	}
	
	/**
	 * Reads the values of the given field.
	 * 
	 * @param <R> Type of the representations.
	 * @param <V> Type of the values.
	 * @param <C> Type of the collection of values to build.
	 * @param description Description of the field to read (for error messages).
	 * @param representations Representations of the field to read.
	 * @param deserializer Function to use to deserialize the representations.
	 * @param factory Factory of the collection of values.
	 * @return The read values.
	 * @throws InvalidFieldException When some representation is invalid.
	 * @since 2.0
	 */
	public static <R, V, C extends Collection<? super V>> C readMultiple(final String description, final Iterable<? extends R> representations, final Function<? super R, ? extends V> deserializer, final CollectionFactory<? super V, ? extends C> factory)
	throws InvalidFieldException {
		return readMultiple(description, representations, deserializer, CollectionAccumulators.add(factory.build())).get();
	}
	
	/**
	 * Reads the values of the given field.
	 * 
	 * @param <R> Type of the representations.
	 * @param <V> Type of the values.
	 * @param <C> Type of the collection of values to build.
	 * @param description Description of the field to read (for error messages).
	 * @param representations Representations of the field to read.
	 * @param deserializer Serializer to use to deserialize the representations.
	 * @param factory Factory of the collection of values.
	 * @return The read values.
	 * @throws InvalidFieldException When some representation is invalid.
	 * @since 2.0
	 */
	public static <R, V, C extends Collection<? super V>> C readMultiple(final String description, final Iterable<? extends R> representations, final Serializer<? extends V, ? super R> deserializer, final CollectionFactory<? super V, ? extends C> factory)
	throws InvalidFieldException {
		return readMultiple(description, representations, SerializerFunctions.deserialize(deserializer), factory);
	}
	
	/**
	 * Reads the values of the given field.
	 * 
	 * @param <V> Type of the values.
	 * @param <A> Type of the accumulator to populate with the read values.
	 * @param description Description of the field to read (for error messages).
	 * @param representations Representations of the field to read.
	 * @param results Accumulator to populate with the read values.
	 * @return The given accumulator of the read values.
	 * @since 2.0
	 */
	public static <V, A extends Accumulator<? super V, ?>> A readMultiple(final String description, final Iterable<? extends V> representations, final A results) {
		return readMultiple(description, representations, Functions.<V>identity(), results);
	}
	
	/**
	 * Reads the values of the given field.
	 * 
	 * @param <R> Type of the representations.
	 * @param <V> Type of the values.
	 * @param <A> Type of the accumulator to populate with the read values.
	 * @param description Description of the field to read (for error messages).
	 * @param representations Representations of the field to read.
	 * @param deserializer Function to use to deserialize the representations.
	 * @param results Accumulator to populate with the read values.
	 * @return The given accumulator of the read values.
	 * @throws InvalidFieldException When some representation is invalid.
	 * @since 2.0
	 */
	public static <R, V, A extends Accumulator<? super V, ?>> A readMultiple(final String description, final Iterable<? extends R> representations, final Function<? super R, ? extends V> deserializer, final A results)
	throws InvalidFieldException {
		for (final R representation : representations) {
			final V value;
			try {
				value = deserializer.evaluate(representation);
			} catch (final Exception exception) {
				throw new InvalidFieldException("Invalid " + description + " field \"" + representation + "\"", exception);
			}
			
			results.add(value);
		}
		return results;
	}
	
	/**
	 * Reads the values of the given field.
	 * 
	 * @param <R> Type of the representations.
	 * @param <V> Type of the values.
	 * @param <A> Type of the accumulator to populate with the read values.
	 * @param description Description of the field to read (for error messages).
	 * @param representations Representations of the field to read.
	 * @param deserializer Serializer to use to deserialize the representations.
	 * @param results Accumulator to populate with the read values.
	 * @return The given accumulator of the read values.
	 * @throws InvalidFieldException When some representation is invalid.
	 * @since 2.0
	 */
	public static <R, V, A extends Accumulator<? super V, ?>> A readMultiple(final String description, final Iterable<? extends R> representations, final Serializer<? extends V, ? super R> deserializer, final A results)
	throws InvalidFieldException {
		return readMultiple(description, representations, SerializerFunctions.deserialize(deserializer), results);
	}
	
	/**
	 * Reads the value of the given field.
	 * <p>
	 * This methods does really do anything, it is provided as a convenience to allow homogenous value reading.
	 * 
	 * @param <V> Type of the value.
	 * @param description Description of the field to read (for error messages).
	 * @param representation Representation of the field to read. May be <code>null</code> when no representations.
	 * @return The read value, or nothing when there is no representation.
	 * @since 2.0
	 */
	public static <V> Maybe<V> readNullable(final String description, final V representation) {
		return read(description, MaybeUtils.fromNullable(representation));
	}
	
	/**
	 * Reads the value of the given field.
	 * 
	 * @param <R> Type of the representation.
	 * @param <V> Type of the value.
	 * @param description Description of the field to read (for error messages).
	 * @param representation Representation of the field to read. May be <code>null</code> when no representations.
	 * @param deserializer Function to use to deserialize the representation.
	 * @return The read value, or nothing when there is no representation.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static <R, V> Maybe<V> readNullable(final String description, final R representation, final Function<? super R, ? extends V> deserializer)
	throws InvalidFieldException {
		return read(description, MaybeUtils.fromNullable(representation), deserializer);
	}
	
	/**
	 * Reads the value of the given field.
	 * 
	 * @param <R> Type of the representation.
	 * @param <V> Type of the value.
	 * @param description Description of the field to read (for error messages).
	 * @param representation Representation of the field to read. May be <code>null</code> when no representations.
	 * @param deserializer Serializer to use to deserialize the representation.
	 * @return The read value, or nothing when there is no representation.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static <R, V> Maybe<V> readNullable(final String description, final R representation, final Serializer<? extends V, ? super R> deserializer)
	throws InvalidFieldException {
		return read(description, MaybeUtils.fromNullable(representation), deserializer);
	}
	
	/**
	 * Reads the value of the given optional field.
	 * 
	 * @param <V> Type of the value.
	 * @param description Description of the field to read (for error messages).
	 * @param representation Representation of the field to read. May be <code>null</code> when no representations.
	 * @param defaultValue Default value when the field is missing.
	 * @return The read value, or the default value when there is no representation.
	 * @since 2.0
	 */
	public static <V> V readOptionalNullable(final String description, final V representation, final V defaultValue) {
		return readOptional(description, MaybeUtils.fromNullable(representation), defaultValue);
	}
	
	/**
	 * Reads the value of the given optional field.
	 * 
	 * @param <R> Type of the representation.
	 * @param <V> Type of the value.
	 * @param description Description of the field to read (for error messages).
	 * @param representation Representation of the field to read. May be <code>null</code> when no representations.
	 * @param deserializer Function to use to deserialize the representation.
	 * @param defaultValue Default value when the field is missing.
	 * @return The read value, or the default value when there is no representation.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static <R, V> V readOptionalNullable(final String description, final R representation, final Function<? super R, ? extends V> deserializer, final V defaultValue)
	throws InvalidFieldException {
		return readOptional(description, MaybeUtils.fromNullable(representation), deserializer, defaultValue);
	}
	
	/**
	 * Reads the value of the given optional field.
	 * 
	 * @param <R> Type of the representation.
	 * @param <V> Type of the value.
	 * @param description Description of the field to read (for error messages).
	 * @param representation Representation of the field to read. May be <code>null</code> when no representations.
	 * @param deserializer Serializer to use to deserialize the representation.
	 * @param defaultValue Default value when the field is missing.
	 * @return The read value, or the default value when there is no representation.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static <R, V> V readOptionalNullable(final String description, final R representation, final Serializer<? extends V, ? super R> deserializer, final V defaultValue)
	throws InvalidFieldException {
		return readOptional(description, MaybeUtils.fromNullable(representation), deserializer, defaultValue);
	}
	
	/**
	 * Reads the value of the given optional field.
	 * 
	 * @param <V> Type of the value.
	 * @param description Description of the field to read (for error messages).
	 * @param representation Representation of the field to read. May be <code>null</code> when no representations.
	 * @param defaultValue Default value when the field is missing.
	 * @return The read value, or the default value when there is no representation.
	 * @since 2.0
	 */
	public static <V> V readOptionalNullable(final String description, final V representation, final Thunk<? extends V> defaultValue) {
		return readOptional(description, MaybeUtils.fromNullable(representation), defaultValue);
	}
	
	/**
	 * Reads the value of the given optional field.
	 * 
	 * @param <R> Type of the representation.
	 * @param <V> Type of the value.
	 * @param description Description of the field to read (for error messages).
	 * @param representation Representation of the field to read. May be <code>null</code> when no representations.
	 * @param deserializer Function to use to deserialize the representation.
	 * @param defaultValue Default value when the field is missing.
	 * @return The read value, or the default value when there is no representation.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static <R, V> V readOptionalNullable(final String description, final R representation, final Function<? super R, ? extends V> deserializer, final Thunk<? extends V> defaultValue)
	throws InvalidFieldException {
		return readOptional(description, MaybeUtils.fromNullable(representation), deserializer, defaultValue);
	}
	
	/**
	 * Reads the value of the given optional field.
	 * 
	 * @param <R> Type of the representation.
	 * @param <V> Type of the value.
	 * @param description Description of the field to read (for error messages).
	 * @param representation Representation of the field to read. May be <code>null</code> when no representations.
	 * @param deserializer Serializer to use to deserialize the representation.
	 * @param defaultValue Default value when the field is missing.
	 * @return The read value, or the default value when there is no representation.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static <R, V> V readOptionalNullable(final String description, final R representation, final Serializer<? extends V, ? super R> deserializer, final Thunk<? extends V> defaultValue)
	throws InvalidFieldException {
		return readOptional(description, MaybeUtils.fromNullable(representation), deserializer, defaultValue);
	}
	
	/**
	 * Reads the value of the given mandatory field.
	 * 
	 * @param <V> Type of the value.
	 * @param description Description of the field to read (for error messages).
	 * @param representation Representation of the field to read. May be <code>null</code> when no representations.
	 * @return The read value.
	 * @throws MissingFieldException When the representation is missing.
	 * @since 2.0
	 */
	public static <V> V readMandatoryNullable(final String description, final V representation)
	throws MissingFieldException {
		return readMandatory(description, MaybeUtils.fromNullable(representation));
	}
	
	/**
	 * Reads the value of the given mandatory field.
	 * 
	 * @param <R> Type of the representation.
	 * @param <V> Type of the value.
	 * @param description Description of the field to read (for error messages).
	 * @param representation Representation of the field to read. May be <code>null</code> when no representations.
	 * @param deserializer Function to use to deserialize the representation.
	 * @return The read value.
	 * @throws MissingFieldException When the representation is missing.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static <R, V> V readMandatoryNullable(final String description, final R representation, final Function<? super R, ? extends V> deserializer)
	throws MissingFieldException, InvalidFieldException {
		return readMandatory(description, MaybeUtils.fromNullable(representation), deserializer);
	}
	
	/**
	 * Reads the value of the given mandatory field.
	 * 
	 * @param <R> Type of the representation.
	 * @param <V> Type of the value.
	 * @param description Description of the field to read (for error messages).
	 * @param representation Representation of the field to read. May be <code>null</code> when no representations.
	 * @param deserializer Serializer to use to deserialize the representation.
	 * @return The read value.
	 * @throws MissingFieldException When the representation is missing.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static <R, V> V readMandatoryNullable(final String description, final R representation, final Serializer<? extends V, ? super R> deserializer)
	throws MissingFieldException, InvalidFieldException {
		return readMandatory(description, MaybeUtils.fromNullable(representation), deserializer);
	}
	
	/**
	 * Reads the values of the given field.
	 * 
	 * @param <V> Type of the values.
	 * @param <C> Type of the collection of values to build.
	 * @param description Description of the field to read (for error messages).
	 * @param representations Representations of the field to read. May be <code>null</code> when no representations.
	 * @param factory Factory of the collection of values.
	 * @return The read values.
	 * @since 2.0
	 */
	public static <V, C extends Collection<? super V>> C readMultipleNullable(final String description, final Iterable<? extends V> representations, final CollectionFactory<? super V, ? extends C> factory) {
		return readMultiple(description, MaybeUtils.fromNullable(representations).get(Iterables.empty()), factory);
	}
	
	/**
	 * Reads the values of the given field.
	 * 
	 * @param <R> Type of the representations.
	 * @param <V> Type of the values.
	 * @param <C> Type of the collection of values to build.
	 * @param description Description of the field to read (for error messages).
	 * @param representations Representations of the field to read. May be <code>null</code> when no representations.
	 * @param deserializer Function to use to deserialize the representations.
	 * @param factory Factory of the collection of values.
	 * @return The read values.
	 * @throws InvalidFieldException When some representation is invalid.
	 * @since 2.0
	 */
	public static <R, V, C extends Collection<? super V>> C readMultipleNullable(final String description, final Iterable<? extends R> representations, final Function<? super R, ? extends V> deserializer, final CollectionFactory<? super V, ? extends C> factory)
	throws InvalidFieldException {
		return readMultiple(description, MaybeUtils.fromNullable(representations).get(Iterables.empty()), deserializer, factory);
	}
	
	/**
	 * Reads the values of the given field.
	 * 
	 * @param <R> Type of the representations.
	 * @param <V> Type of the values.
	 * @param <C> Type of the collection of values to build.
	 * @param description Description of the field to read (for error messages).
	 * @param representations Representations of the field to read. May be <code>null</code> when no representations.
	 * @param deserializer Serializer to use to deserialize the representations.
	 * @param factory Factory of the collection of values.
	 * @return The read values.
	 * @throws InvalidFieldException When some representation is invalid.
	 * @since 2.0
	 */
	public static <R, V, C extends Collection<? super V>> C readMultipleNullable(final String description, final Iterable<? extends R> representations, final Serializer<? extends V, ? super R> deserializer, final CollectionFactory<? super V, ? extends C> factory)
	throws InvalidFieldException {
		return readMultiple(description, MaybeUtils.fromNullable(representations).get(Iterables.empty()), deserializer, factory);
	}
	
	/**
	 * Reads the values of the given field.
	 * 
	 * @param <V> Type of the values.
	 * @param <A> Type of the accumulator to populate with the read values.
	 * @param description Description of the field to read (for error messages).
	 * @param representations Representations of the field to read. May be <code>null</code> when no representations.
	 * @param results Accumulator to populate with the read values.
	 * @return The given accumulator of the read values.
	 * @since 2.0
	 */
	public static <V, A extends Accumulator<? super V, ?>> A readMultipleNullable(final String description, final Iterable<? extends V> representations, final A results) {
		return readMultiple(description, MaybeUtils.fromNullable(representations).get(Iterables.empty()), results);
	}
	
	/**
	 * Reads the values of the given field.
	 * 
	 * @param <R> Type of the representations.
	 * @param <V> Type of the values.
	 * @param <A> Type of the accumulator to populate with the read values.
	 * @param description Description of the field to read (for error messages).
	 * @param representations Representations of the field to read. May be <code>null</code> when no representations.
	 * @param deserializer Function to use to deserialize the representations.
	 * @param results Accumulator to populate with the read values.
	 * @return The given accumulator of the read values.
	 * @throws InvalidFieldException When some representation is invalid.
	 * @since 2.0
	 */
	public static <R, V, A extends Accumulator<? super V, ?>> A readMultipleNullable(final String description, final Iterable<? extends R> representations, final Function<? super R, ? extends V> deserializer, final A results)
	throws InvalidFieldException {
		return readMultipleNullable(description, MaybeUtils.fromNullable(representations).get(Iterables.empty()), deserializer, results);
	}
	
	/**
	 * Reads the values of the given field.
	 * 
	 * @param <R> Type of the representations.
	 * @param <V> Type of the values.
	 * @param <A> Type of the accumulator to populate with the read values.
	 * @param description Description of the field to read (for error messages).
	 * @param representations Representations of the field to read. May be <code>null</code> when no representations.
	 * @param deserializer Serializer to use to deserialize the representations.
	 * @param results Accumulator to populate with the read values.
	 * @return The given accumulator of the read values.
	 * @throws InvalidFieldException When some representation is invalid.
	 * @since 2.0
	 */
	public static <R, V, A extends Accumulator<? super V, ?>> A readMultipleNullable(final String description, final Iterable<? extends R> representations, final Serializer<? extends V, ? super R> deserializer, final A results)
	throws InvalidFieldException {
		return readMultiple(description, MaybeUtils.fromNullable(representations).get(Iterables.empty()), deserializer, results);
	}
	
	private FieldUtils() {
		// Prevents instantiation.
	}
}
