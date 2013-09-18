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

import com.trazere.util.function.Function1;
import com.trazere.util.function.Predicate1;
import com.trazere.util.function.Predicates;
import com.trazere.util.type.Maybe;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * The {@link RecordUtils} class provides various utilities regarding record manipulatation.
 */
public class RecordUtils {
	/**
	 * Fill the given record builder with fields identified by the given keys and containing the given value.
	 * <p>
	 * No fields may be identified by any given keys in the given builder.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param builder Record builder to fill.
	 * @param keys Keys identifying the fields to fill.
	 * @param value Value of the fields. May be <code>null</code>.
	 * @throws DuplicateFieldException When some field is identified by the given key in the receiver builder.
	 * @throws RecordException When the fields cannot be added.
	 */
	public static <K, V> void fill(final RecordBuilder<? super K, ? super V, ?> builder, final Iterable<? extends K> keys, final V value)
	throws RecordException {
		assert null != builder;
		assert null != keys;
		
		for (final K key : keys) {
			builder.add(key, value);
		}
	}
	
	/**
	 * Fill the given record builder with fields identified by the missing given keys and containing the given value.
	 * <p>
	 * This method only add the missing fields, the existing ones are not modified.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the value.
	 * @param builder Record builder to fill.
	 * @param keys Keys identifying the fields to fill.
	 * @param value Value to set. May be <code>null</code>.
	 * @throws RecordException When the fields cannot be added.
	 */
	public static <K, V> void complete(final RecordBuilder<? super K, ? super V, ?> builder, final Iterable<? extends K> keys, final V value)
	throws RecordException {
		assert null != builder;
		assert null != keys;
		
		for (final K key : keys) {
			if (!builder.contains(key)) {
				builder.add(key, value);
			}
		}
	}
	
	/**
	 * Compute the sub record of the given record containing only the fields identified by the given keys.
	 * <p>
	 * Some field must be identified by every given key in the given record.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param record Record to read.
	 * @param keys Predicates filtering the keys of the fields to keep.
	 * @return The sub record.
	 * @throws MissingFieldException When no fields are identified by any given key in the the given record.
	 * @throws RecordException When the fields cannot be read.
	 */
	public static <K, V> Record<K, V> sub(final Record<K, V> record, final Predicate1<? super K, ? extends RecordException> keys)
	throws RecordException {
		return sub(record, keys, SimpleRecordFactory.<K, V>factory());
	}
	
	/**
	 * Compute the sub record of the given record containing only the fields identified by the given keys.
	 * <p>
	 * Some field must be identified by every given key in the given record.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <R> Type of the sub record.
	 * @param record Record to read.
	 * @param keys Predicates filtering the keys of the fields to keep.
	 * @param factory Record factory to use.
	 * @return The sub record.
	 * @throws MissingFieldException When no fields are identified by any given key in the the given record.
	 * @throws RecordException When the fields cannot be read.
	 */
	public static <K, V, R extends Record<K, V>> R sub(final Record<K, V> record, final Predicate1<? super K, ? extends RecordException> keys, final RecordFactory<K, V, R> factory)
	throws RecordException {
		assert null != record;
		assert null != keys;
		assert null != factory;
		
		final Map<K, V> fields = new HashMap<K, V>();
		for (final Entry<K, ? extends V> entry : record.asMap().entrySet()) {
			final K key = entry.getKey();
			if (keys.evaluate(key)) {
				fields.put(key, entry.getValue());
			}
		}
		return factory.build(fields);
	}
	
	/**
	 * Populate the given record builder with the fields of the given record identified by the given keys.
	 * <p>
	 * Some field must be identified by every given key in the given record and no fields may be identified by any given key in the given builder.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <B> Type of the record builder.
	 * @param record Record to read.
	 * @param keys Predicates filtering the keys of the fields to copy.
	 * @param builder Record builder to populate.
	 * @return The given record builder.
	 * @throws MissingFieldException When no fields are identified by any given key in the the given record.
	 * @throws DuplicateFieldException When some field is identified by any given key in the given builder.
	 * @throws RecordException When the fields cannot be read.
	 * @throws RecordException When the fields cannot be added.
	 */
	public static <K, V, B extends RecordBuilder<? super K, ? super V, ?>> B sub(final Record<K, V> record, final Predicate1<? super K, ? extends RecordException> keys, final B builder)
	throws RecordException {
		assert null != record;
		assert null != keys;
		assert null != builder;
		
		for (final Map.Entry<K, ? extends V> entry : record.asMap().entrySet()) {
			final K key = entry.getKey();
			if (keys.evaluate(key)) {
				builder.add(key, entry.getValue());
			}
		}
		return builder;
	}
	
	/**
	 * Compute the sub record of the given record containing the fields which are not identified by the given keys.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param record Record to read.
	 * @param keys Predicates filtering the keys of the fields to drop.
	 * @return The sub record.
	 * @throws RecordException When the fields cannot be read.
	 */
	public static <K, V> Record<K, V> drop(final Record<K, V> record, final Predicate1<? super K, ? extends RecordException> keys)
	throws RecordException {
		return sub(record, Predicates.not(keys));
	}
	
	/**
	 * Compute the sub record of the given record containing the fields which are not identified by the given keys.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <R> Type of the sub record.
	 * @param record Record to read.
	 * @param keys Predicates filtering the keys of the fields to drop.
	 * @param factory Record factory to use.
	 * @return The sub record.
	 * @throws RecordException When the fields cannot be read.
	 */
	public static <K, V, R extends Record<K, V>> R drop(final Record<K, V> record, final Predicate1<? super K, ? extends RecordException> keys, final RecordFactory<K, V, R> factory)
	throws RecordException {
		return sub(record, Predicates.not(keys), factory);
	}
	
	/**
	 * Populate the given record builder with the fields of the given record which are not identified by the given keys.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <B> Type of the record builder.
	 * @param record Record to read.
	 * @param keys Predicates filtering the keys of the fields to drop.
	 * @param builder Record builder to populate.
	 * @return The given record builder.
	 * @throws DuplicateFieldException When some fields are identified by the keys of kept fields in the given builder.
	 * @throws RecordException When the fields cannot be read.
	 * @throws RecordException When the fields cannot be added.
	 */
	public static <K, V, B extends RecordBuilder<K, ? super V, ?>> B drop(final Record<K, ? extends V> record, final Predicate1<? super K, ? extends RecordException> keys, final B builder)
	throws RecordException {
		return sub(record, Predicates.not(keys), builder);
	}
	
	/**
	 * Build the union of the given records.
	 * <p>
	 * The keys identifying the fields of the given records must not overlap.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param record1 First record.
	 * @param record2 Second record.
	 * @return The union record.
	 * @throws DuplicateFieldException When some fields are identified by a same key in both records.
	 * @throws RecordException When the fields cannot be read.
	 */
	public static <K, V> Record<K, V> union(final Record<? extends K, ? extends V> record1, final Record<? extends K, ? extends V> record2)
	throws RecordException {
		return union(record1, record2, SimpleRecordFactory.<K, V>factory());
	}
	
	/**
	 * Build the union of the given records.
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
	 * @throws DuplicateFieldException When some fields are identified by a same key in both records.
	 * @throws RecordException When the fields cannot be read.
	 */
	public static <K, V, R extends Record<K, V>> R union(final Record<? extends K, ? extends V> record1, final Record<? extends K, ? extends V> record2, final RecordFactory<K, V, R> factory)
	throws RecordException {
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
	 * Build the union of the given records.
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
	 * @throws DuplicateFieldException When some fields are identified by a same key in both records.
	 * @throws DuplicateFieldException When some fields are identified by the keys of unified fields in the given builder.
	 * @throws RecordException When the fields cannot be read.
	 */
	public static <K, V, B extends RecordBuilder<? super K, ? super V, ?>> B union(final Record<? extends K, ? extends V> record1, final Record<? extends K, ? extends V> record2, final B builder)
	throws RecordException {
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
	
	/**
	 * Get the value of the field of the receiver record identified by the given key.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param record The record.
	 * @param key The key of the field.
	 * @return The value of the field.
	 * @throws RecordException When the field cannot be got.  
	 */
	public static <K, V> Maybe<V> get(final Record<K, ? extends V> record, final K key)
	throws RecordException {
		assert null != record;
		
		if (record.contains(key)) {
			return Maybe.<V>some(record.get(key));
		} else {
			return Maybe.none();
		}
	}
	
	/**
	 * Get the value of the field of the receiver record identified by the given signature.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param record The record.
	 * @param signature The signature of the field.
	 * @return The value of the field.
	 * @throws RecordException When the field cannot be got.  
	 */
	public static <K, V> Maybe<V> getTyped(final Record<K, ? super V> record, final FieldSignature<? extends K, ? extends V> signature)
	throws RecordException {
		assert null != record;
		
		if (record.contains(signature.getKey())) {
			return Maybe.<V>some(record.getTyped(signature));
		} else {
			return Maybe.none();
		}
	}
	
	/**
	 * Get the signature of the field identified by the given key of the given record signature.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param signature The record signature.
	 * @param key The key of the field.
	 * @return The signature of the field.
	 * @throws RecordException When the field signature cannot be got.
	 */
	public static <K, V> Maybe<FieldSignature<K, ? extends V>> get(final RecordSignature<K, V> signature, final K key)
	throws RecordException {
		assert null != signature;
		
		if (signature.contains(key)) {
			return Maybe.<FieldSignature<K, ? extends V>>some(signature.get(key));
		} else {
			return Maybe.none();
		}
	}
	
	/**
	 * Unifies the requirements of the given parametrable accepted by the given filter into the given record signature builder.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <X> Type of the filter exceptions.
	 * @param parametrable The parametrable.
	 * @param filter The filter.
	 * @param builder The record signature builder.
	 * @throws RecordException When the unification of some requirement fails.
	 * @throws X When some filter evaluation fails.
	 */
	public static <K, V, X extends Exception> void unifyRequirements(final Parametrable<K, V> parametrable, final Predicate1<? super K, X> filter, final RecordSignatureBuilder<K, V, ?> builder)
	throws RecordException, X {
		assert null != parametrable;
		assert null != filter;
		assert null != builder;
		
		final RecordSignature<K, V> requirements = parametrable.getRequirements();
		for (final FieldSignature<K, ? extends V> requirement : requirements.asMap().values()) {
			if (filter.evaluate(requirement.getKey())) {
				builder.unify(requirement);
			}
		}
	}
	
	/**
	 * Extracs the requirements of the given parametrable using the given extractor and unifies them into the given record signature builder.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <X> Type of the filter exceptions.
	 * @param parametrable The parametrable.
	 * @param extractor The extractor.
	 * @param builder The record signature builder.
	 * @throws RecordException When the unification of some requirement fails.
	 * @throws X When some extractor evaluation fails.
	 */
	public static <K, V, X extends Exception> void unifyRequirements(final Parametrable<K, V> parametrable, final Function1<? super FieldSignature<K, ? extends V>, ? extends Maybe<? extends FieldSignature<K, ? extends V>>, X> extractor, final RecordSignatureBuilder<K, V, ?> builder)
	throws RecordException, X {
		assert null != parametrable;
		assert null != extractor;
		assert null != builder;
		
		final RecordSignature<K, V> requirements = parametrable.getRequirements();
		for (final FieldSignature<K, ? extends V> requirement : requirements.asMap().values()) {
			final Maybe<? extends FieldSignature<K, ? extends V>> extractedRequirement = extractor.evaluate(requirement);
			if (extractedRequirement.isSome()) {
				builder.unify(extractedRequirement.asSome().getValue());
			}
		}
	}
	
	/**
	 * Typechecks the requirements of the given parametrable against the given signature.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param parametrable The parametrable.
	 * @param signature The signature.
	 * @throws RecordException When the typechecking fails.
	 */
	public static <K, V> void typeCheck(final Parametrable<K, V> parametrable, final RecordSignature<? super K, ? extends V> signature)
	throws RecordException {
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
	 * @throws RecordException When the typechecking fails.
	 */
	public static <K, V> void typeCheck(final RecordSignature<K, V> requirements, final RecordSignature<? super K, ? extends V> signature)
	throws RecordException {
		typeCheck(requirements, signature, Predicates.<K, RecordException>all());
	}
	
	/**
	 * Typechecks the given requirements against the given signature.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param requirements The requierements.
	 * @param signature The signature.
	 * @param filter The filter of the requirements to typecheck.
	 * @throws RecordException When the typechecking fails.
	 */
	public static <K, V> void typeCheck(final RecordSignature<K, V> requirements, final RecordSignature<? super K, ? extends V> signature, final Predicate1<? super K, ? extends RecordException> filter)
	throws RecordException {
		assert null != requirements;
		assert null != signature;
		assert null != filter;
		
		for (final FieldSignature<K, ? extends V> requirement : requirements.asMap().values()) {
			if (filter.evaluate(requirement.getKey())) {
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
	 * @throws RecordException When the typechecking fails.
	 */
	public static <K> void typeCheck(final FieldSignature<K, ?> requirement, final RecordSignature<? super K, ?> signature)
	throws RecordException {
		assert null != requirement;
		assert null != signature;
		
		final K key = requirement.getKey();
		if (!signature.contains(key)) {
			throw new RecordException("Missing parameter \"" + key + "\" in parameters " + signature);
		} else if (!requirement.accepts(signature.get(key).getType(), signature.get(key).isNullable())) {
			throw new RecordException("Incompatible parameter \"" + key + "\" with requirement " + requirement + " in parameters " + signature);
		}
	}
	
	private RecordUtils() {
		// Prevents instantiation.
	}
}
