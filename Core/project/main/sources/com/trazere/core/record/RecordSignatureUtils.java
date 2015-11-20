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
import com.trazere.core.collection.SetUtils;
import com.trazere.core.functional.Function;
import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Predicate;
import com.trazere.core.imperative.IteratorUtils;
import com.trazere.core.imperative.Procedure;
import com.trazere.core.lang.IterableUtils;
import com.trazere.core.util.Maybe;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;

/**
 * The {@link RecordSignatureUtils} class provides various utilities regarding {@link RecordSignature record signatures}.
 * 
 * @see RecordSignature
 * @since 2.0
 */
public class RecordSignatureUtils {
	// TODO: fields (similar to MapUtils.bindings(...)) for lazy manipulations.
	
	/**
	 * Executes the given procedure with each field key of the given record signature.
	 * 
	 * @param <K> Type of the field keys.
	 * @param signature Record signature containing the field keys over which to iterate.
	 * @param procedure Procedure to execute.
	 * @since 2.0
	 */
	public static <K extends FieldKey<? extends K, ?>> void foreach(final RecordSignature<? extends K> signature, final Procedure<? super FieldKey<? extends K, ?>> procedure) {
		IterableUtils.foreach(signature.keys(), procedure);
	}
	
	/**
	 * Left folds over the field keys of the given record signature using the given binary operator and initial state.
	 * 
	 * @param <K> Type of the field keys.
	 * @param <S> Type of the state.
	 * @param signature Record signature containing the field keys over which to fold.
	 * @param operator Binary operator to use.
	 * @param initialState Initial state.
	 * @return The folded state.
	 * @since 2.0
	 */
	public static <K extends FieldKey<? extends K, ?>, S> S fold(final RecordSignature<? extends K> signature, final Function2<? super S, ? super FieldKey<? extends K, ?>, ? extends S> operator, final S initialState) {
		return IterableUtils.fold(signature.keys(), operator, initialState);
	}
	
	/**
	 * Gets the first field key of the given record signature accepted by the given filter.
	 * 
	 * @param <K> Type of the field keys.
	 * @param signature Record signature containing the field keys to filter.
	 * @param filter Predicate to use to filter the field keys.
	 * @return The first accepted field key.
	 * @since 2.0
	 */
	public static <K extends FieldKey<? extends K, ?>> Maybe<FieldKey<? extends K, ?>> first(final RecordSignature<? extends K> signature, final Predicate<? super FieldKey<? extends K, ?>> filter) {
		return IterableUtils.first(signature.keys(), filter);
	}
	
	/**
	 * Gets the first element extracted from the field keys of the given record signature by the given extractor.
	 * 
	 * @param <K> Type of the field keys.
	 * @param <EE> Type of the extracted elements.
	 * @param signature Record signature containing the field keys from which to extract.
	 * @param extractor Function to use to extract from the field keys.
	 * @return The first extracted element.
	 * @since 2.0
	 */
	public static <K extends FieldKey<? extends K, ?>, EE> Maybe<? extends EE> extractFirst(final RecordSignature<? extends K> signature, final Function<? super FieldKey<? extends K, ?>, ? extends Maybe<? extends EE>> extractor) {
		return IterableUtils.extractFirst(signature.keys(), extractor);
	}
	
	/**
	 * Tests whether any field key of the given record signature is accepted by the given filter.
	 * 
	 * @param <K> Type of the field keys.
	 * @param signature Record signature containing the field keys to test.
	 * @param filter Predicate to use to filter the field keys.
	 * @return <code>true</code> when some field key is accepted by the filter, <code>false</code> when all fields are rejected.
	 * @since 2.0
	 */
	public static <K extends FieldKey<? extends K, ?>> boolean isAny(final RecordSignature<? extends K> signature, final Predicate<? super FieldKey<? extends K, ?>> filter) {
		return IterableUtils.isAny(signature.keys(), filter);
	}
	
	/**
	 * Tests whether all field keys of the given record signature are accepted by the given filter.
	 * 
	 * @param <K> Type of the field keys.
	 * @param signature Record signature containing the field keys to test.
	 * @param filter Predicate to use to filter the field keys.
	 * @return <code>true</code> when all field keys are accepted by the filter, <code>false</code> when some field is rejected.
	 * @since 2.0
	 */
	public static <K extends FieldKey<? extends K, ?>> boolean areAll(final RecordSignature<? extends K> signature, final Predicate<? super FieldKey<? extends K, ?>> filter) {
		return IterableUtils.areAll(signature.keys(), filter);
	}
	
	/**
	 * Counts the field keys of the given record signature accepted by the given filter.
	 * 
	 * @param <K> Type of the field keys.
	 * @param signature Record signature containing the field keys to count.
	 * @param filter Predicate to use to filter the field keys.
	 * @return The number of accepted fields.
	 * @since 2.0
	 */
	public static <K extends FieldKey<? extends K, ?>> int count(final RecordSignature<? extends K> signature, final Predicate<? super FieldKey<? extends K, ?>> filter) {
		return IterableUtils.count(signature.keys(), filter);
	}
	
	// TODO: rename to append ?
	/**
	 * Builds the union of the given record signatures.
	 * <p>
	 * In case of conflict, the field keys of the first record signature have precedence over the field keys of the second record signature.
	 * 
	 * @param <K> Type of the field keys.
	 * @param signature1 First record signature.
	 * @param signature2 Second record signature.
	 * @return A record signature containing the union of the field keys.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>> RecordSignature<K> union(final RecordSignature<K> signature1, final RecordSignature<K> signature2) {
		final Set<? extends FieldKey<? extends K, ?>> keys1 = signature1.keys();
		final Set<FieldKey<? extends K, ?>> duplicateKeys = CollectionUtils.filter(signature2.keys(), key -> keys1.contains(key), CollectionFactories.hashSet());
		return new BaseRecordSignature<K>() {
			@Override
			public int size() {
				return signature1.size() + signature2.size() - duplicateKeys.size();
			}
			
			@Override
			public boolean isEmpty() {
				return signature1.isEmpty() && signature2.isEmpty();
			}
			
			@Override
			public boolean contains(final FieldKey<? extends K, ?> key) {
				return signature1.contains(key) || signature2.contains(key);
			}
			
			@Override
			public Set<? extends FieldKey<? extends K, ?>> keys() {
				return new AbstractSet<FieldKey<? extends K, ?>>() {
					@Override
					public int size() {
						return signature1.size() + signature2.size() - duplicateKeys.size();
					}
					
					@Override
					public Iterator<FieldKey<? extends K, ?>> iterator() {
						return IteratorUtils.append(signature1.keys().iterator(), IteratorUtils.filter(signature2.keys().iterator(), key -> !duplicateKeys.contains(key)));
					}
				};
			}
		};
	}
	
	/**
	 * Filters the field keys of the given record signature using the given filter.
	 * 
	 * @param <K> Type of the field keys.
	 * @param signature Record signature to filter.
	 * @param filter Predicate to use to filter the field keys.
	 * @return A record signature containing the accepted field keys.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>> RecordSignature<K> filter(final RecordSignature<K> signature, final Predicate<? super FieldKey<? extends K, ?>> filter) {
		assert null != signature;
		assert null != filter;
		
		final Set<FieldKey<? extends K, ?>> keys = SetUtils.unmodifiable(new AbstractSet<FieldKey<? extends K, ?>>() {
			@Override
			public int size() {
				return IterableUtils.count(signature.keys(), filter);
			}
			
			@Override
			public Iterator<FieldKey<? extends K, ?>> iterator() {
				return IteratorUtils.filter(signature.keys().iterator(), filter);
			}
		});
		
		return new BaseRecordSignature<K>() {
			@Override
			public int size() {
				return IterableUtils.count(signature.keys(), filter);
			}
			
			@Override
			public boolean isEmpty() {
				return !IterableUtils.isAny(signature.keys(), filter);
			}
			
			@Override
			public boolean contains(final FieldKey<? extends K, ?> key) {
				return signature.contains(key) && filter.evaluate(key);
			}
			
			@Override
			public Set<? extends FieldKey<? extends K, ?>> keys() {
				return keys;
			}
		};
	}
	
	// TODO: map ?
	
	// TODO: extract ?
	
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
	
	private RecordSignatureUtils() {
		// Prevent instantiation.
	}
}
