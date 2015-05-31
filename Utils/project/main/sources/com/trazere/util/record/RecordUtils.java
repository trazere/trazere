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

import com.trazere.util.function.Function1;
import com.trazere.util.function.Predicate1;
import com.trazere.util.function.Predicates;
import com.trazere.util.lang.InternalException;
import com.trazere.util.type.Maybe;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The {@link RecordUtils} class provides various utilities regarding record manipulatation.
 * 
 * @deprecated Use core.
 */
@Deprecated
public class RecordUtils {
	/**
	 * Fills the given record builder with fields identified by the given keys and containing the given value.
	 * <p>
	 * No fields may be identified by any given keys in the given builder.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param builder Record builder to fill.
	 * @param keys Keys identifying the fields to fill.
	 * @param value Value of the fields. May be <code>null</code>.
	 * @throws DuplicateFieldException When some field is identified by the given key in the receiver builder.
	 * @deprecated To be removed.
	 */
	@Deprecated
	public static <K, V> void fill(final RecordBuilder<? super K, ? super V, ?> builder, final Iterable<? extends K> keys, final V value)
	throws DuplicateFieldException {
		assert null != builder;
		assert null != keys;
		
		for (final K key : keys) {
			builder.add(key, value);
		}
	}
	
	/**
	 * Fills the given record builder with fields identified by the missing given keys and containing the given value.
	 * <p>
	 * This method only add the missing fields, the existing ones are not modified.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the value.
	 * @param builder Record builder to fill.
	 * @param keys Keys identifying the fields to fill.
	 * @param value Value to set. May be <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.record.RecordBuilder#complete(com.trazere.core.record.FieldKey, Object)}.
	 */
	@Deprecated
	public static <K, V> void complete(final RecordBuilder<? super K, ? super V, ?> builder, final Iterable<? extends K> keys, final V value) {
		assert null != builder;
		assert null != keys;
		
		for (final K key : keys) {
			if (!builder.contains(key)) {
				try {
					builder.add(key, value);
				} catch (final DuplicateFieldException exception) {
					throw new InternalException(exception);
				}
			}
		}
	}
	
	/**
	 * Computes the sub record of the given record containing only the fields identified by the given keys.
	 * <p>
	 * Some field must be identified by every given key in the given record.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <KX> Type of the key filter exceptions.
	 * @param record Record to read.
	 * @param keys Predicates filtering the keys of the fields to keep.
	 * @return The sub record.
	 * @throws KX When some key filter evaluation fails.
	 * @throws InvalidFieldException When some field of the given record cannot be read.
	 * @throws RecordException When the result record cannot be built.
	 * @deprecated Use
	 *             {@link com.trazere.core.record.RecordUtils#filter(com.trazere.core.record.Record, com.trazere.core.functional.Predicate, com.trazere.core.record.RecordFactory)}
	 *             .
	 */
	@Deprecated
	public static <K, V, KX extends Exception> Record<K, V> sub(final Record<K, V> record, final Predicate1<? super K, KX> keys)
	throws KX, InvalidFieldException, RecordException {
		return sub(record, keys, SimpleRecordFactory.<K, V>factory());
	}
	
	/**
	 * Computes the sub record of the given record containing only the fields identified by the given keys.
	 * <p>
	 * Some field must be identified by every given key in the given record.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <R> Type of the sub record.
	 * @param <KX> Type of the key filter exceptions.
	 * @param record Record to read.
	 * @param keys Predicates filtering the keys of the fields to keep.
	 * @param factory Record factory to use.
	 * @return The sub record.
	 * @throws KX When some key filter evaluation fails.
	 * @throws InvalidFieldException When some field of the given record cannot be read.
	 * @throws RecordException When the result record cannot be built.
	 * @deprecated Use
	 *             {@link com.trazere.core.record.RecordUtils#filter(com.trazere.core.record.Record, com.trazere.core.functional.Predicate, com.trazere.core.record.RecordFactory)}
	 *             .
	 */
	@Deprecated
	public static <K, V, R extends Record<K, V>, KX extends Exception> R sub(final Record<K, V> record, final Predicate1<? super K, KX> keys, final RecordFactory<K, V, R> factory)
	throws KX, InvalidFieldException, RecordException {
		assert null != record;
		assert null != keys;
		assert null != factory;
		
		final Map<K, V> fields = new HashMap<K, V>();
		for (final K key : record.getKeys()) {
			if (keys.evaluate(key)) {
				final V value;
				try {
					value = record.get(key);
				} catch (final MissingFieldException exception) {
					throw new InternalException(exception);
				}
				fields.put(key, value);
			}
		}
		return factory.build(fields);
	}
	
	/**
	 * Populates the given record builder with the fields of the given record identified by the given keys.
	 * <p>
	 * Some field must be identified by every given key in the given record and no fields may be identified by any given key in the given builder.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <B> Type of the record builder.
	 * @param <KX> Type of the key filter exceptions.
	 * @param record Record to read.
	 * @param keys Predicates filtering the keys of the fields to copy.
	 * @param builder Record builder to populate.
	 * @return The given record builder.
	 * @throws KX When some key filter evaluation fails.
	 * @throws InvalidFieldException When some field of the given record cannot be read.
	 * @throws DuplicateFieldException When some field is identified by any given key in the given builder.
	 * @deprecated To be removed.
	 */
	@Deprecated
	public static <K, V, B extends RecordBuilder<? super K, ? super V, ?>, KX extends Exception> B sub(final Record<K, V> record, final Predicate1<? super K, KX> keys, final B builder)
	throws KX, InvalidFieldException, DuplicateFieldException {
		assert null != record;
		assert null != keys;
		assert null != builder;
		
		for (final K key : record.getKeys()) {
			if (keys.evaluate(key)) {
				final V value;
				try {
					value = record.get(key);
				} catch (final MissingFieldException exception) {
					throw new InternalException(exception);
				}
				builder.add(key, value);
			}
		}
		return builder;
	}
	
	/**
	 * Computes the sub record of the given record containing the fields which are not identified by the given keys.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <KX> Type of the key filter exceptions.
	 * @param record Record to read.
	 * @param keys Predicates filtering the keys of the fields to drop.
	 * @return The sub record.
	 * @throws KX When some key filter evaluation fails.
	 * @throws InvalidFieldException When some field of the given record cannot be read.
	 * @throws RecordException When the result record cannot be built.
	 * @deprecated Use
	 *             {@link com.trazere.core.record.RecordUtils#filter(com.trazere.core.record.Record, com.trazere.core.functional.Predicate, com.trazere.core.record.RecordFactory)}
	 *             .
	 */
	@Deprecated
	public static <K, V, KX extends Exception> Record<K, V> drop(final Record<K, V> record, final Predicate1<? super K, KX> keys)
	throws KX, InvalidFieldException, RecordException {
		return sub(record, Predicates.not(keys));
	}
	
	/**
	 * Computes the sub record of the given record containing the fields which are not identified by the given keys.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <R> Type of the sub record.
	 * @param <KX> Type of the key filter exceptions.
	 * @param record Record to read.
	 * @param keys Predicates filtering the keys of the fields to drop.
	 * @param factory Record factory to use.
	 * @return The sub record.
	 * @throws KX When some key filter evaluation fails.
	 * @throws InvalidFieldException When some field of the given record cannot be read.
	 * @throws RecordException When the result record cannot be built.
	 * @deprecated Use
	 *             {@link com.trazere.core.record.RecordUtils#filter(com.trazere.core.record.Record, com.trazere.core.functional.Predicate, com.trazere.core.record.RecordFactory)}
	 *             .
	 */
	@Deprecated
	public static <K, V, R extends Record<K, V>, KX extends Exception> R drop(final Record<K, V> record, final Predicate1<? super K, KX> keys, final RecordFactory<K, V, R> factory)
	throws KX, InvalidFieldException, RecordException {
		return sub(record, Predicates.not(keys), factory);
	}
	
	/**
	 * Populates the given record builder with the fields of the given record which are not identified by the given keys.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <B> Type of the record builder.
	 * @param <KX> Type of the key filter exceptions.
	 * @param record Record to read.
	 * @param keys Predicates filtering the keys of the fields to drop.
	 * @param builder Record builder to populate.
	 * @return The given record builder.
	 * @throws KX When some key filter evaluation fails.
	 * @throws InvalidFieldException When some field of the given record cannot be read.
	 * @throws DuplicateFieldException When some field is identified by any given key in the given builder.
	 * @deprecated To be removed.
	 */
	@Deprecated
	public static <K, V, B extends RecordBuilder<K, ? super V, ?>, KX extends Exception> B drop(final Record<K, ? extends V> record, final Predicate1<? super K, KX> keys, final B builder)
	throws KX, InvalidFieldException, DuplicateFieldException {
		return sub(record, Predicates.not(keys), builder);
	}
	
	/**
	 * Builds the union of the given records.
	 * <p>
	 * The keys identifying the fields of the given records must not overlap.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param record1 First record.
	 * @param record2 Second record.
	 * @return The union record.
	 * @throws InvalidFieldException When some field of the given record cannot be read.
	 * @throws DuplicateFieldException When some fields are identified by a same key in both records.
	 * @throws RecordException When the result record cannot be built.
	 * @deprecated Use {@link com.trazere.core.record.RecordUtils#union(com.trazere.core.record.Record, com.trazere.core.record.Record)}.
	 */
	@Deprecated
	public static <K, V> Record<K, V> union(final Record<? extends K, ? extends V> record1, final Record<? extends K, ? extends V> record2)
	throws InvalidFieldException, DuplicateFieldException, RecordException {
		return union(record1, record2, SimpleRecordFactory.<K, V>factory());
	}
	
	/**
	 * Builds the union of the given records.
	 * <p>
	 * The keys identifying the fields of the given records must not overlap.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <R> Type of the union record.
	 * @param record1 First record.
	 * @param record2 Second record.
	 * @param factory Record factory to use.
	 * @return The union record.
	 * @throws InvalidFieldException When some field of the given record cannot be read.
	 * @throws DuplicateFieldException When some fields are identified by a same key in both records.
	 * @throws RecordException When the result record cannot be built.
	 * @deprecated Use
	 *             {@link com.trazere.core.record.RecordUtils#union(com.trazere.core.record.Record, com.trazere.core.record.Record, com.trazere.core.record.RecordFactory)}
	 *             .
	 */
	@Deprecated
	public static <K, V, R extends Record<K, V>> R union(final Record<? extends K, ? extends V> record1, final Record<? extends K, ? extends V> record2, final RecordFactory<K, V, R> factory)
	throws InvalidFieldException, DuplicateFieldException, RecordException {
		assert null != record1;
		assert null != record2;
		assert null != factory;
		
		// Copy the fields of the first record.
		final Map<K, V> fields = new HashMap<K, V>(record1.asMap());
		
		// Copy the fields of the second record.
		for (final Map.Entry<? extends K, ? extends V> entry : record2.asMap().entrySet()) {
			final K key = entry.getKey();
			if (fields.containsKey(key)) {
				throw new DuplicateFieldException("Field \"" + key + "\" exist in both records " + record1 + " and " + record2);
			}
			fields.put(key, entry.getValue());
		}
		
		// Build the record.
		if (fields.isEmpty()) {
			return factory.build();
		} else {
			return factory.build(fields);
		}
	}
	
	/**
	 * Builds the union of the given records.
	 * <p>
	 * The keys identifying the fields of the given records must not overlap.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <B> Type of the record builder.
	 * @param record1 First record.
	 * @param record2 Second record.
	 * @param builder Record builder to populate.
	 * @return The union record.
	 * @throws InvalidFieldException When some field of the given records cannot be read.
	 * @throws DuplicateFieldException When some fields are identified by a same key in both records.
	 * @throws DuplicateFieldException When some fields are identified by the keys of unified fields in the given builder.
	 * @deprecated To be removed.
	 */
	@Deprecated
	public static <K, V, B extends RecordBuilder<? super K, ? super V, ?>> B union(final Record<? extends K, ? extends V> record1, final Record<? extends K, ? extends V> record2, final B builder)
	throws InvalidFieldException, DuplicateFieldException {
		assert null != record1;
		assert null != record2;
		assert null != builder;
		
		// Copy the first record.
		builder.addAll(record1);
		
		// Copy the second record.
		final Set<? extends K> keys1 = record1.getKeys();
		for (final Map.Entry<? extends K, ? extends V> entry : record2.asMap().entrySet()) {
			final K key = entry.getKey();
			if (keys1.contains(key)) {
				throw new DuplicateFieldException("Field \"" + key + "\" exist in both records " + record1 + " and " + record2);
			}
			builder.add(key, entry.getValue());
		}
		
		return builder;
	}
	
	// TODO: remove ??
	/**
	 * Gets the value of the field of the receiver record identified by the given key.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param record The record.
	 * @param key The key of the field.
	 * @return The value of the field.
	 * @throws InvalidFieldException When the value of the field cannot be read.
	 * @deprecated Use {@link com.trazere.core.record.Record#get(com.trazere.core.record.FieldKey)}.
	 */
	@Deprecated
	public static <K, V> Maybe<V> get(final Record<K, ? extends V> record, final K key)
	throws InvalidFieldException {
		assert null != record;
		
		if (record.contains(key)) {
			try {
				return Maybe.<V>some(record.get(key));
			} catch (final MissingFieldException exception) {
				throw new InternalException(exception);
			}
		} else {
			return Maybe.none();
		}
	}
	
	// TODO: remove ??
	/**
	 * Gets the value of the field of the receiver record identified by the given signature.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param record The record.
	 * @param signature The signature of the field.
	 * @return The value of the field.
	 * @throws InvalidFieldException When the value of the field cannot be read.
	 * @throws IncompatibleFieldException When the value of the field is not compatible with the given type.
	 * @throws NullFieldException When the value is null and the field is not nullable.
	 * @deprecated Use {@link com.trazere.core.record.Record#get(com.trazere.core.record.FieldKey)}.
	 */
	@Deprecated
	public static <K, V> Maybe<V> getTyped(final Record<K, ? super V> record, final FieldSignature<? extends K, ? extends V> signature)
	throws InvalidFieldException, IncompatibleFieldException, NullFieldException {
		assert null != record;
		
		if (record.contains(signature.getKey())) {
			try {
				return Maybe.<V>some(record.getTyped(signature));
			} catch (final MissingFieldException exception) {
				throw new InternalException(exception);
			}
		} else {
			return Maybe.none();
		}
	}
	
	/**
	 * Gets the signature of the field identified by the given key of the given record signature.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param signature The record signature.
	 * @param key The key of the field.
	 * @return The signature of the field.
	 * @throws InvalidFieldException When the signature of the field cannot be read.
	 * @deprecated To be removed.
	 */
	@Deprecated
	public static <K, V> Maybe<FieldSignature<K, ? extends V>> get(final RecordSignature<K, V> signature, final K key)
	throws InvalidFieldException {
		assert null != signature;
		
		if (signature.contains(key)) {
			try {
				return Maybe.<FieldSignature<K, ? extends V>>some(signature.get(key));
			} catch (final MissingFieldException exception) {
				throw new InternalException(exception);
			}
		} else {
			return Maybe.none();
		}
	}
	
	/**
	 * Unifies the requirements of the given parametrable accepted by the given filter into the given record signature builder.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <KX> Type of the key filter exceptions.
	 * @param parametrable The parametrable.
	 * @param keys The filter.
	 * @param builder The record signature builder.
	 * @throws KX When some key filter evaluation fails.
	 * @throws InvalidFieldException When some requirement cannot be read.
	 * @throws IncompatibleFieldException When the signature of some requirement is not compatible.
	 * @deprecated To be removed.
	 */
	@Deprecated
	public static <K, V, KX extends Exception> void unifyRequirements(final Parametrable<K, V> parametrable, final Predicate1<? super K, KX> keys, final RecordSignatureBuilder<K, V, ?> builder)
	throws IncompatibleFieldException, InvalidFieldException, KX {
		assert null != parametrable;
		assert null != keys;
		assert null != builder;
		
		final RecordSignature<K, V> requirements = parametrable.getRequirements();
		for (final K key : requirements.getKeys()) {
			if (keys.evaluate(key)) {
				final FieldSignature<K, ? extends V> requirement;
				try {
					requirement = requirements.get(key);
				} catch (final MissingFieldException exception) {
					throw new InternalException(exception);
				}
				builder.unify(requirement);
			}
		}
	}
	
	/**
	 * Extracs the requirements of the given parametrable using the given extractor and unifies them into the given record signature builder.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <EX> Type of the extractor exceptions.
	 * @param parametrable The parametrable.
	 * @param extractor The extractor.
	 * @param builder The record signature builder.
	 * @throws InvalidFieldException When some requirement cannot be read.
	 * @throws EX When some requirement extraction fails.
	 * @throws IncompatibleFieldException When the signature of some requirement is not compatible.
	 * @deprecated To be removed.
	 */
	@Deprecated
	public static <K, V, EX extends Exception> void unifyRequirements(final Parametrable<K, V> parametrable, final Function1<? super FieldSignature<K, ? extends V>, ? extends Maybe<? extends FieldSignature<K, ? extends V>>, EX> extractor, final RecordSignatureBuilder<K, V, ?> builder)
	throws InvalidFieldException, EX, IncompatibleFieldException {
		assert null != parametrable;
		assert null != extractor;
		assert null != builder;
		
		final RecordSignature<K, V> requirements = parametrable.getRequirements();
		for (final K key : requirements.getKeys()) {
			final FieldSignature<K, ? extends V> requirement;
			try {
				requirement = requirements.get(key);
			} catch (final MissingFieldException exception) {
				throw new InternalException(exception);
			}
			final Maybe<? extends FieldSignature<K, ? extends V>> extractedRequirement = extractor.evaluate(requirement);
			builder.unify(extractedRequirement.asSome().getValue());
		}
	}
	
	/**
	 * Typechecks the requirements of the given parametrable against the given signature.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param parametrable The parametrable.
	 * @param signature The signature.
	 * @throws InvalidFieldException When some requirement field cannot be read.
	 * @throws MissingFieldException When some signature field is missing but required.
	 * @throws InvalidFieldException When some signature field cannot be read.
	 * @throws IncompatibleFieldException When some signature field is incompatible with the requirement.
	 * @deprecated To be removed.
	 */
	@Deprecated
	public static <K, V> void typeCheck(final Parametrable<K, V> parametrable, final RecordSignature<? super K, ? extends V> signature)
	throws InvalidFieldException, MissingFieldException, IncompatibleFieldException {
		assert null != parametrable;
		
		typeCheck(parametrable.getRequirements(), signature);
	}
	
	/**
	 * Typechecks the given requirements against the given signature.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param requirements The requierements.
	 * @param signature The signature.
	 * @throws InvalidFieldException When some requirement field cannot be read.
	 * @throws MissingFieldException When some signature field is missing but required.
	 * @throws InvalidFieldException When some signature field cannot be read.
	 * @throws IncompatibleFieldException When some signature field is incompatible with the requirement.
	 * @deprecated To be removed.
	 */
	@Deprecated
	public static <K, V> void typeCheck(final RecordSignature<K, V> requirements, final RecordSignature<? super K, ? extends V> signature)
	throws InvalidFieldException, MissingFieldException, IncompatibleFieldException {
		typeCheck(requirements, signature, Predicates.<K, InternalException>all());
	}
	
	/**
	 * Typechecks the given requirements against the given signature.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <KX> Type of the key filter exceptions.
	 * @param requirements The requierements.
	 * @param signature The signature.
	 * @param keys The filter of the requirements to typecheck.
	 * @throws KX When some key filter evaluation fails.
	 * @throws InvalidFieldException When some requirement field cannot be read.
	 * @throws MissingFieldException When some signature field is missing but required.
	 * @throws InvalidFieldException When some signature field cannot be read.
	 * @throws IncompatibleFieldException When some signature field is incompatible with the requirement.
	 * @deprecated To be removed.
	 */
	@Deprecated
	public static <K, V, KX extends Exception> void typeCheck(final RecordSignature<K, V> requirements, final RecordSignature<? super K, ? extends V> signature, final Predicate1<? super K, KX> keys)
	throws KX, InvalidFieldException, MissingFieldException, IncompatibleFieldException {
		assert null != requirements;
		assert null != signature;
		assert null != keys;
		
		for (final K key : requirements.getKeys()) {
			if (keys.evaluate(key)) {
				final FieldSignature<K, ? extends V> requirement;
				try {
					requirement = requirements.get(key);
				} catch (final MissingFieldException exception) {
					throw new InternalException(exception);
				}
				typeCheck(requirement, signature);
			}
		}
	}
	
	/**
	 * Typechecks the given requirement against the given signature.
	 * 
	 * @param <K> Type of the keys.
	 * @param requirement The requierement.
	 * @param signature The signature.
	 * @throws MissingFieldException When some signature field is missing but required.
	 * @throws InvalidFieldException When some signature field cannot be read.
	 * @throws IncompatibleFieldException When some signature field is incompatible with the requirement.
	 * @deprecated To be removed.
	 */
	// TODO: use TypeCheckException
	@Deprecated
	public static <K> void typeCheck(final FieldSignature<K, ?> requirement, final RecordSignature<? super K, ?> signature)
	throws MissingFieldException, InvalidFieldException, IncompatibleFieldException {
		assert null != requirement;
		assert null != signature;
		
		final K key = requirement.getKey();
		final Maybe<? extends FieldSignature<? super K, ?>> maybeFieldSignature = signature.getMaybe(key);
		if (maybeFieldSignature.isNone()) {
			throw new MissingFieldException("Missing field \"" + key + "\" in signature " + signature);
		}
		final FieldSignature<? super K, ?> fieldSignature = maybeFieldSignature.asSome().getValue();
		// TODO: accepts(FieldSignature)
		if (!requirement.accepts(fieldSignature.getType(), fieldSignature.isNullable())) {
			throw new IncompatibleFieldException("Incompatible field \"" + key + "\" with requirement " + requirement + " in signature " + signature);
		}
	}
	
	private RecordUtils() {
		// Prevents instantiation.
	}
}
