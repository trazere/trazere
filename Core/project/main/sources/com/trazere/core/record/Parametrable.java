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

/**
 * The {@link Parametrable} interface defines objects that are parametrized by some {@link Record record}.
 * 
 * @param <K> Type of the keys of the parameters.
 */
public interface Parametrable<K extends FieldKey<K, ?>> {
	/**
	 * Gets the requirements of this parametrable over its parameters.
	 * 
	 * @return The record signature of the requirements.
	 */
	default RecordSignature<K> getRequirements() {
		return unifyRequirements(new SimpleRecordSignatureBuilder<K>()).build();
	}
	
	/**
	 * Unifies the requirements of this parametrable over its parameters into the given record signature builder.
	 * 
	 * @param <B> Type of the record signature builder.
	 * @param builder Builder into which the requirements are to be unified.
	 * @return The given signature builder.
	 * @throws IncompatibleFieldException When the unification fails.
	 * @see RecordSignatureBuilder#unify(FieldKey)
	 */
	<B extends RecordSignatureBuilder<K, ?>> B unifyRequirements(B builder)
	throws IncompatibleFieldException;
	
	/**
	 * Checks whether the requirements of this parametrable are satisfied by the given capabilities.
	 * 
	 * @param capabilities Signature of the capabilities.
	 * @return A success when requirements are satified, a failure otherwise.
	 * @see RecordSignature#checkAssignableFrom(RecordSignature)
	 */
	default Result<Unit> checkRequirements(final RecordSignature<K> capabilities) {
		return getRequirements().checkAssignableFrom(capabilities);
	}
	
	/**
	 * Checks whether the requirements of this parametrable are satisfied by the given capabilities.
	 * 
	 * @param capabilities Capabilities.
	 * @return A success when requirements are satified, a failure otherwise.
	 * @see RecordSignature#checkAssignableFrom(RecordSignature)
	 */
	default Result<Unit> checkRequirements(final Record<K> capabilities) {
		return checkRequirements(capabilities.getSignature());
	}
}
