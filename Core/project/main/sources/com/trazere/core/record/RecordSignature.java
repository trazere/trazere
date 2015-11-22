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

import com.trazere.core.util.Result;
import com.trazere.core.util.Unit;
import java.util.Set;

/**
 * The {@link RecordSignature} interface defines signatures of {@link Record records}.
 * <p>
 * A record signature is the set of keys that identify the fields that the record contains.
 * 
 * @param <K> Type of the field keys.
 * @see FieldKey
 * @since 2.0
 */
public interface RecordSignature<K extends FieldKey<K, ?>> {
	// Keys.
	
	/**
	 * Gets the size of this record signature.
	 * <p>
	 * The size of a record signature corresponds to the number of field keys it contains.
	 * 
	 * @return The size of the record signature.
	 * @since 2.0
	 */
	default int size() {
		return keys().size();
	}
	
	/**
	 * Indicates whether this record signature is empty or not.
	 * <p>
	 * A record signature is empty when it contains no field keys.
	 * 
	 * @return <code>true</code> when the record signature is empty, <code>false</code> otherwise.
	 * @since 2.0
	 */
	default boolean isEmpty() {
		return 0 == size();
	}
	
	/**
	 * Indicates whether this record signature contains the given key field or not.
	 * 
	 * @param key Field key to look for.
	 * @return <code>true</code> when the record signature contains the field key, <code>false</code> otherwise.
	 * @since 2.0
	 */
	default boolean contains(final FieldKey<K, ?> key) {
		return keys().contains(key);
	}
	
	/**
	 * Gets a view of the field keys composing this record signature.
	 * 
	 * @return An unmodiable set of the field keys.
	 * @since 2.0
	 */
	Set<? extends FieldKey<K, ?>> keys();
	
	// Signature.
	
	/**
	 * Tests whether records with the given signauture are compatible with this signature.
	 * <p>
	 * The compliance is defined as follow :
	 * <ul>
	 * <li>each key of this signature must belong to the given record signature,
	 * <li>each key of this signature must be assignable from its couterpart key in the given signature.
	 * </ul>
	 *
	 * @param signature Record signature compatibility is to be tested.
	 * @return <code>true</code> when the record signature is compliant, <code>false</code> otherwise.
	 * @see FieldKey#checkAssignableFrom(FieldKey)
	 */
	boolean isAssignableFrom(RecordSignature<K> signature);
	
	/**
	 * Tests whether records with the given signauture are compatible with this signature.
	 * <p>
	 * The compliance is defined as follow :
	 * <ul>
	 * <li>each key of this signature must belong to the given record signature,
	 * <li>each key of this signature must be assignable from its couterpart key in the given signature.
	 * </ul>
	 *
	 * @param signature Record signature compatibility is to be tested.
	 * @return A success when the record signature is compliant, a failure otherwise.
	 * @see FieldKey#checkAssignableFrom(FieldKey)
	 */
	Result<Unit> checkAssignableFrom(RecordSignature<K> signature);
	
	/**
	 * Tests whether the values of the given record are compatible this signature.
	 * <p>
	 * A value is compatible when its type and nullity are compliant with the constraints of the key.
	 *
	 * @param record Record to test.
	 * @return A success when the values are compatible with the signature, a failure otherwise.
	 * @see FieldKey#checkValue(Object)
	 * @since 2.0
	 */
	Result<Unit> checkValues(Record<K> record);
}
