/*
 *  Copyright 2008 Julien Dufour
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
package com.trazere.util.value;

import com.trazere.util.record.IncompatibleFieldException;
import com.trazere.util.record.Record;
import com.trazere.util.record.RecordBuilder;
import com.trazere.util.record.RecordSignature;
import com.trazere.util.record.RecordSignatureBuilder;

/**
 * The {@link RecordReader} interface defines record reading functions.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
public interface RecordReader<K, V> {
	/**
	 * Get the signature of the records produced by the receiver reader.
	 * 
	 * @return The signature of the record.
	 * @throws ValueException When the signature cannot be computed.
	 */
	public RecordSignature<K, V> getSignature()
	throws ValueException;
	
	/**
	 * Read the record defined by the receiver reader using the given parameters.
	 * 
	 * @param parameters Parameters to use.
	 * @return The produced record.
	 * @throws ValueException When the record cannot be read.
	 */
	public Record<K, V> read(final Record<String, Object> parameters)
	throws ValueException;
	
	/**
	 * Populate the given record builder with the record read by the receiver reader using the given parameters.
	 * 
	 * @param <B> Type of the record builder.
	 * @param parameters Parameters to use.
	 * @param builder Record builder to populate.
	 * @return The produced record.
	 * @throws ValueException When the record cannot be read.
	 */
	public <B extends RecordBuilder<K, V, ?>> B read(final Record<String, Object> parameters, final B builder)
	throws ValueException;
	
	/**
	 * Get the requirements of the receiver reader over its parameters.
	 * 
	 * @return The signature of the requirements.
	 * @throws ValueException When the requirements cannot be computed.
	 */
	public RecordSignature<String, Object> getRequirements()
	throws ValueException;
	
	/**
	 * Unify the requirements of the receiver reader within the given builder.
	 * 
	 * @param <B> Type of the signature builder.
	 * @param builder The builder within which the requirements should be unified.
	 * @return The given signature builder.
	 * @throws ValueException When the requirements cannot be computed.
	 * @throws IncompatibleFieldException When the unification fails.
	 */
	public <B extends RecordSignatureBuilder<String, Object, ?>> B unifyRequirements(final B builder)
	throws ValueException, IncompatibleFieldException;
}
