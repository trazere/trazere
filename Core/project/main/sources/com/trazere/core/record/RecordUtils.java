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

import com.trazere.core.collection.CollectionFactories;
import com.trazere.core.collection.CollectionUtils;
import com.trazere.core.functional.Function;
import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Predicate;
import com.trazere.core.imperative.IteratorUtils;
import com.trazere.core.imperative.Procedure;
import com.trazere.core.lang.IterableUtils;
import com.trazere.core.util.Maybe;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * The {@link RecordUtils} class provides various utilities regarding {@link Record records}.
 * 
 * @see Record
 * @since 1.0
 */
public class RecordUtils {
	// TODO: fields (similar to MapUtils.bindings(...)) for lazy manipulations.
	
	/**
	 * Executes the given procedure with each field of the given record.
	 * 
	 * @param <K> Type of the field keys.
	 * @param record Record containing the fields to iterate.
	 * @param procedure Procedure to execute.
	 * @since 1.0
	 */
	public static <K extends FieldKey<? extends K, ?>> void foreach(final Record<? extends K> record, final Procedure<? super Field<? extends K, ?>> procedure) {
		IterableUtils.foreach(record.fields(), procedure);
	}
	
	/**
	 * Left folds over the fields of the given record using the given binary operator and initial state.
	 * 
	 * @param <K> Type of the field keys.
	 * @param <S> Type of the state.
	 * @param record Record containing the fields to fold over.
	 * @param operator Binary operator to use.
	 * @param initialState Initial state.
	 * @return The folded state.
	 * @since 1.0
	 */
	public static <K extends FieldKey<? extends K, ?>, S> S fold(final Record<? extends K> record, final Function2<? super S, ? super Field<? extends K, ?>, ? extends S> operator, final S initialState) {
		return IterableUtils.fold(record.fields(), operator, initialState);
	}
	
	/**
	 * Gets the first field of the given record accepted by the given filter.
	 * 
	 * @param <K> Type of the field keys.
	 * @param record Record containing the fields to filter.
	 * @param filter Predicate to use to filter the fields.
	 * @return The first accepted field.
	 * @since 1.0
	 */
	public static <K extends FieldKey<? extends K, ?>> Maybe<Field<? extends K, ?>> first(final Record<? extends K> record, final Predicate<? super Field<? extends K, ?>> filter) {
		return IterableUtils.first(record.fields(), filter);
	}
	
	/**
	 * Gets the first field extracted from the given collection by the given extractor.
	 * 
	 * @param <K> Type of the field keys.
	 * @param <EE> Type of the extracted elements.
	 * @param record Record containing the fields to extract from.
	 * @param extractor Function to use to extract the fields.
	 * @return The first extracted field.
	 * @since 1.0
	 */
	public static <K extends FieldKey<? extends K, ?>, EE> Maybe<? extends EE> extractFirst(final Record<? extends K> record, final Function<? super Field<? extends K, ?>, ? extends Maybe<? extends EE>> extractor) {
		return IterableUtils.extractFirst(record.fields(), extractor);
	}
	
	/**
	 * Tests whether any field of the given record is accepted by the given filter.
	 * 
	 * @param <K> Type of the field keys.
	 * @param record Record containing the fields to test.
	 * @param filter Predicate to use to filter the fields.
	 * @return <code>true</code> when some field is accepted, <code>false</code> when all fields are rejected.
	 * @since 1.0
	 */
	public static <K extends FieldKey<? extends K, ?>> boolean isAny(final Record<? extends K> record, final Predicate<? super Field<? extends K, ?>> filter) {
		return IterableUtils.isAny(record.fields(), filter);
	}
	
	/**
	 * Tests whether all fields of the given record are accepted by the given filter.
	 * 
	 * @param <K> Type of the field keys.
	 * @param record Record containing the fields to test.
	 * @param filter Predicate to use to filter the fields.
	 * @return <code>true</code> when all fields are accepted, <code>false</code> when some field is rejected.
	 * @since 1.0
	 */
	public static <K extends FieldKey<? extends K, ?>> boolean areAll(final Record<? extends K> record, final Predicate<? super Field<? extends K, ?>> filter) {
		return IterableUtils.areAll(record.fields(), filter);
	}
	
	/**
	 * Counts the fields of the given record accepted by the given filter.
	 * 
	 * @param <K> Type of the field keys.
	 * @param record Record containing the fields to count.
	 * @param filter Predicate to use to filter the fields.
	 * @return The number of accepted fields.
	 * @since 1.0
	 */
	public static <K extends FieldKey<? extends K, ?>> int count(final Record<? extends K> record, final Predicate<? super Field<? extends K, ?>> filter) {
		return IterableUtils.count(record.fields(), filter);
	}
	
	// TODO: rename to append
	/**
	 * Builds the union of the given records.
	 * <p>
	 * In case of conflict, the fields of the first record have precedence over the fields of the second record.
	 * 
	 * @param <K> Type of the field keys.
	 * @param record1 First record.
	 * @param record2 Second record.
	 * @return A record containing the union of the fields.
	 * @since 1.0
	 */
	public static <K extends FieldKey<? extends K, ?>> Record<K> union(final Record<K> record1, final Record<K> record2) {
		final Set<? extends FieldKey<? extends K, ?>> keys1 = record1.keys();
		final Set<FieldKey<? extends K, ?>> duplicateKeys = CollectionUtils.filter(record2.keys(), key -> keys1.contains(key), CollectionFactories.hashSet());
		return new BaseRecord<K>() {
			@Override
			public int size() {
				return record1.size() + record2.size() - duplicateKeys.size();
			}
			
			@Override
			public boolean isEmpty() {
				return record1.isEmpty() && record2.isEmpty();
			}
			
			@Override
			public boolean contains(final FieldKey<? extends K, ?> key) {
				return record1.contains(key) || record2.contains(key);
			}
			
			@Override
			public Set<? extends FieldKey<? extends K, ?>> keys() {
				return new AbstractSet<FieldKey<? extends K, ?>>() {
					@Override
					public int size() {
						return record1.size() + record2.size() - duplicateKeys.size();
					}
					
					@Override
					public Iterator<FieldKey<? extends K, ?>> iterator() {
						return IteratorUtils.append(record1.keys().iterator(), IteratorUtils.filter(record2.keys().iterator(), key -> !duplicateKeys.contains(key)));
					}
				};
			}
			
			@Override
			public <V> Maybe<V> get(final FieldKey<? extends K, V> key)
			throws InvalidFieldException {
				final Maybe<V> value1 = record1.get(key);
				if (value1.isSome()) {
					return value1;
				} else {
					return record2.get(key);
				}
			}
			
			@Override
			public Collection<Field<? extends K, ?>> fields() {
				return new AbstractCollection<Field<? extends K, ?>>() {
					@Override
					public int size() {
						return record1.size() + record2.size() - duplicateKeys.size();
					}
					
					@Override
					public Iterator<Field<? extends K, ?>> iterator() {
						return IteratorUtils.append(record1.fields().iterator(), IteratorUtils.filter(record2.fields().iterator(), field -> !duplicateKeys.contains(field.getKey())));
					}
				};
			}
		};
	}
	
	// TODO: rename to append
	/**
	 * Builds the union of the given records.
	 * <p>
	 * In case of conflict, the fields of the first record have precedence over the fields of the second record.
	 * 
	 * @param <K> Type of the field keys.
	 * @param <R> Type of the result record.
	 * @param record1 First record.
	 * @param record2 Second record.
	 * @param resultFactory Factory of the result record.
	 * @return A record containing the union of the fields.
	 * @since 1.0
	 */
	public static <K extends FieldKey<? extends K, ?>, R extends Record<K>> R union(final Record<? extends K> record1, final Record<? extends K> record2, final RecordFactory<K, R> resultFactory) {
		final RecordBuilder<K, R> builder = resultFactory.newBuilder();
		builder.setAll(record1);
		builder.completeAll(record2);
		return builder.build();
	}
	
	// TODO: flatten
	
	// TODO: add a lazy filter (no factory arg)
	
	/**
	 * Filters the fields of the given record using the given filter.
	 *
	 * @param <K> Type of the field keys.
	 * @param <R> Type of the result record.
	 * @param record Record containing the fields to filter.
	 * @param filter Predicate to use to filter the fields.
	 * @param resultFactory Factory of the result record.
	 * @return A record containing the filtered fields.
	 * @since 1.0
	 */
	public static <K extends FieldKey<? extends K, ?>, R extends Record<K>> R filter(final Record<? extends K> record, final Predicate<? super Field<? extends K, ?>> filter, final RecordFactory<K, R> resultFactory) {
		final RecordBuilder<K, R> builder = resultFactory.newBuilder();
		for (final Field<? extends K, ?> field : record.fields()) {
			if (filter.evaluate(field)) {
				builder.set(field);
			}
		}
		return builder.build();
	}
	
	// TODO: add a lazy map (no factory arg)
	
	/**
	 * Transforms the fields of the given record using the given function.
	 *
	 * @param <K> Type of the field keys.
	 * @param <TK> Type of the transformed field keys.
	 * @param <R> Type of the result record.
	 * @param record Record containing the fields to transform.
	 * @param function Function to use to transform the fields.
	 * @param resultFactory Factory of the result record.
	 * @return A record containing the transformed fields.
	 * @throws DuplicateFieldException When sereral transformed field are conflicting.
	 * @since 1.0
	 */
	public static <K extends FieldKey<? extends K, ?>, TK extends FieldKey<TK, ?>, R extends Record<TK>> R map(final Record<? extends K> record, final Function<? super Field<? extends K, ?>, ? extends Field<? extends TK, ?>> function, final RecordFactory<TK, R> resultFactory)
	throws DuplicateFieldException {
		final RecordBuilder<TK, R> builder = resultFactory.newBuilder();
		for (final Field<? extends K, ?> field : record.fields()) {
			builder.add(function.evaluate(field));
		}
		return builder.build();
	}
	
	// TODO: add a lazy extract (no factory arg)
	
	/**
	 * Extracts the fields of the given record using the given extractor.
	 *
	 * @param <K> Type of the field keys.
	 * @param <EK> Type of the extracted field keys.
	 * @param <R> Type of the result record.
	 * @param record Record containing the fields to extract from.
	 * @param extractor Function to use to extract the fields.
	 * @param resultFactory Factory of the result record.
	 * @return A record containing the extracted fields.
	 * @throws DuplicateFieldException When sereral extracted field are conflicting.
	 * @since 1.0
	 */
	public static <K extends FieldKey<? extends K, ?>, EK extends FieldKey<EK, ?>, R extends Record<EK>> R extract(final Record<? extends K> record, final Function<? super Field<? extends K, ?>, ? extends Maybe<? extends Field<? extends EK, ?>>> extractor, final RecordFactory<EK, R> resultFactory)
	throws DuplicateFieldException {
		final RecordBuilder<EK, R> builder = resultFactory.newBuilder();
		for (final Field<? extends K, ?> field : record.fields()) {
			final Maybe<? extends Field<? extends EK, ?>> extractedField = extractor.evaluate(field);
			if (extractedField.isSome()) {
				builder.add(extractedField.asSome().getValue());
			}
		}
		return builder.build();
	}
	
	// TODO
	//	/**
	//	 * Unifies the requirements of the given parametrable accepted by the given filter into the given record signature builder.
	//	 *
	//	 * @param <K> Type of the keys.
	//	 * @param <V> Type of the values.
	//	 * @param <KX> Type of the key filter exceptions.
	//	 * @param parametrable The parametrable.
	//	 * @param keys The filter.
	//	 * @param builder The record signature builder.
	//	 * @throws KX When some key filter evaluation fails.
	//	 * @throws InvalidFieldException When some requirement cannot be read.
	//	 * @throws IncompatibleFieldException When the signature of some requirement is not compatible.
	//	 */
	//	public static <K, V, KX extends Exception> void unifyRequirements(final Parametrable<K, V> parametrable, final Predicate1<? super K, KX> keys, final RecordSignatureBuilder<K, V, ?> builder)
	//	throws IncompatibleFieldException, InvalidFieldException, KX {
	//		assert null != parametrable;
	//		assert null != keys;
	//		assert null != builder;
	//
	//		final RecordSignature<K, V> requirements = parametrable.getRequirements();
	//		for (final K key : requirements.getKeys()) {
	//			if (keys.evaluate(key)) {
	//				final FieldSignature<K, ? extends V> requirement;
	//				try {
	//					requirement = requirements.get(key);
	//				} catch (final MissingFieldException exception) {
	//					throw new InternalException(exception);
	//				}
	//				builder.unify(requirement);
	//			}
	//		}
	//	}
	//
	//	/**
	//	 * Extracs the requirements of the given parametrable using the given extractor and unifies them into the given record signature builder.
	//	 *
	//	 * @param <K> Type of the keys.
	//	 * @param <V> Type of the values.
	//	 * @param <EX> Type of the extractor exceptions.
	//	 * @param parametrable The parametrable.
	//	 * @param extractor The extractor.
	//	 * @param builder The record signature builder.
	//	 * @throws InvalidFieldException When some requirement cannot be read.
	//	 * @throws EX When some requirement extraction fails.
	//	 * @throws IncompatibleFieldException When the signature of some requirement is not compatible.
	//	 */
	//	public static <K, V, EX extends Exception> void unifyRequirements(final Parametrable<K, V> parametrable, final Function1<? super FieldSignature<K, ? extends V>, ? extends Maybe<? extends FieldSignature<K, ? extends V>>, EX> extractor, final RecordSignatureBuilder<K, V, ?> builder)
	//	throws InvalidFieldException, EX, IncompatibleFieldException {
	//		assert null != parametrable;
	//		assert null != extractor;
	//		assert null != builder;
	//
	//		final RecordSignature<K, V> requirements = parametrable.getRequirements();
	//		for (final K key : requirements.getKeys()) {
	//			final FieldSignature<K, ? extends V> requirement;
	//			try {
	//				requirement = requirements.get(key);
	//			} catch (final MissingFieldException exception) {
	//				throw new InternalException(exception);
	//			}
	//			final Maybe<? extends FieldSignature<K, ? extends V>> extractedRequirement = extractor.evaluate(requirement);
	//			builder.unify(extractedRequirement.asSome().getValue());
	//		}
	//	}
	
	//	/**
	//	 * Typechecks the requirements of the given parametrable against the given signature.
	//	 *
	//	 * @param <K> Type of the keys.
	//	 * @param <V> Type of the values.
	//	 * @param parametrable The parametrable.
	//	 * @param signature The signature.
	//	 * @throws InvalidFieldException When some requirement field cannot be read.
	//	 * @throws MissingFieldException When some signature field is missing but required.
	//	 * @throws InvalidFieldException When some signature field cannot be read.
	//	 * @throws IncompatibleFieldException When some signature field is incompatible with the requirement.
	//	 */
	//	public static <K, V> void typeCheck(final Parametrable<K, V> parametrable, final RecordSignature<? super K, ? extends V> signature)
	//	throws InvalidFieldException, MissingFieldException, IncompatibleFieldException {
	//		assert null != parametrable;
	//
	//		typeCheck(parametrable.getRequirements(), signature);
	//	}
	//
	//	/**
	//	 * Typechecks the given requirements against the given signature.
	//	 *
	//	 * @param <K> Type of the keys.
	//	 * @param <V> Type of the values.
	//	 * @param requirements The requierements.
	//	 * @param signature The signature.
	//	 * @throws InvalidFieldException When some requirement field cannot be read.
	//	 * @throws MissingFieldException When some signature field is missing but required.
	//	 * @throws InvalidFieldException When some signature field cannot be read.
	//	 * @throws IncompatibleFieldException When some signature field is incompatible with the requirement.
	//	 */
	//	public static <K, V> void typeCheck(final RecordSignature<K, V> requirements, final RecordSignature<? super K, ? extends V> signature)
	//	throws InvalidFieldException, MissingFieldException, IncompatibleFieldException {
	//		typeCheck(requirements, signature, Predicates.<K, InternalException>all());
	//	}
	//
	//	/**
	//	 * Typechecks the given requirements against the given signature.
	//	 *
	//	 * @param <K> Type of the keys.
	//	 * @param <V> Type of the values.
	//	 * @param <KX> Type of the key filter exceptions.
	//	 * @param requirements The requierements.
	//	 * @param signature The signature.
	//	 * @param keys The filter of the requirements to typecheck.
	//	 * @throws KX When some key filter evaluation fails.
	//	 * @throws InvalidFieldException When some requirement field cannot be read.
	//	 * @throws MissingFieldException When some signature field is missing but required.
	//	 * @throws InvalidFieldException When some signature field cannot be read.
	//	 * @throws IncompatibleFieldException When some signature field is incompatible with the requirement.
	//	 */
	//	public static <K, V, KX extends Exception> void typeCheck(final RecordSignature<K, V> requirements, final RecordSignature<? super K, ? extends V> signature, final Predicate1<? super K, KX> keys)
	//	throws KX, InvalidFieldException, MissingFieldException, IncompatibleFieldException {
	//		assert null != requirements;
	//		assert null != signature;
	//		assert null != keys;
	//
	//		for (final K key : requirements.getKeys()) {
	//			if (keys.evaluate(key)) {
	//				final FieldSignature<K, ? extends V> requirement;
	//				try {
	//					requirement = requirements.get(key);
	//				} catch (final MissingFieldException exception) {
	//					throw new InternalException(exception);
	//				}
	//				typeCheck(requirement, signature);
	//			}
	//		}
	//	}
	//
	//	/**
	//	 * Typechecks the given requirement against the given signature.
	//	 *
	//	 * @param <K> Type of the keys.
	//	 * @param requirement The requierement.
	//	 * @param signature The signature.
	//	 * @throws MissingFieldException When some signature field is missing but required.
	//	 * @throws InvalidFieldException When some signature field cannot be read.
	//	 * @throws IncompatibleFieldException When some signature field is incompatible with the requirement.
	//	 */
	//	// TODO: use TypeCheckException
	//	public static <K> void typeCheck(final FieldSignature<K, ?> requirement, final RecordSignature<? super K, ?> signature)
	//	throws MissingFieldException, InvalidFieldException, IncompatibleFieldException {
	//		assert null != requirement;
	//		assert null != signature;
	//
	//		final K key = requirement.getKey();
	//		final Maybe<? extends FieldSignature<? super K, ?>> maybeFieldSignature = signature.getMaybe(key);
	//		if (maybeFieldSignature.isNone()) {
	//			throw new MissingFieldException("Missing field \"" + key + "\" in signature " + signature);
	//		}
	//		final FieldSignature<? super K, ?> fieldSignature = maybeFieldSignature.asSome().getValue();
	//		if (requirement.isSubSignature(fieldSignature).isFailure()) {
	//			throw new IncompatibleFieldException("Incompatible field \"" + key + "\" with requirement " + requirement + " in signature " + signature);
	//		}
	//	}
	
	private RecordUtils() {
		// Prevent instantiation.
	}
}
