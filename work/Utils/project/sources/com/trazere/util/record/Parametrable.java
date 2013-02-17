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

/**
 * The {@link Parametrable} interface defines elements which put requirements over some record of parameters.
 * 
 * @param <K> Type of the keys of the parameters.
 * @param <V> Type of the values of the parameters.
 */
public interface Parametrable<K, V> {
	/**
	 * Gets the requirements of the receiver parametrable over its parameters.
	 * 
	 * @return The signature of the requirements.
	 * @throws RecordException When the requirements cannot be computed.
	 */
	public RecordSignature<K, V> getRequirements()
	throws RecordException;
	
	/**
	 * Unifies the requirements of the receiver parametrable over its parameters within the given builder.
	 * 
	 * @param <B> Type of the signature builder.
	 * @param builder The builder within which the requirements should be unified.
	 * @return The given signature builder.
	 * @throws IncompatibleFieldException When the unification fails.
	 * @throws RecordException When the requirements cannot be computed.
	 */
	public <B extends RecordSignatureBuilder<K, V, ?>> B unifyRequirements(final B builder)
	throws RecordException;
}
