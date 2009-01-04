package com.trazere.util.function;

import com.trazere.util.record.IncompatibleFieldException;
import com.trazere.util.record.Record;
import com.trazere.util.record.RecordSignature;
import com.trazere.util.record.RecordSignatureBuilder;

/**
 * DOCME
 * 
 * @param <K> Type of the keys of the parameters.
 * @param <V> Type of the values of the parameters.
 * @param <T> Type of the result values.
 * @param <X> Type of the exceptions.
 */
public interface ParameterFunction<K, V, T, X extends Exception>
extends Function1<Record<K, V>, T, X> {
	/**
	 * Get the requirements of the receiver function over its parameters.
	 * 
	 * @return The signature of the requirements.
	 * @throws X When the requirements cannot be computed.
	 */
	public RecordSignature<K, V> getRequirements()
	throws X;
	
	/**
	 * Unify the requirements of the receiver function over its parameters within the given builder.
	 * 
	 * @param <B> Type of the signature builder.
	 * @param builder The builder within which the requirements should be unified.
	 * @return The given signature builder.
	 * @throws X When the requirements cannot be computed.
	 * @throws IncompatibleFieldException When the unification fails.
	 */
	public <B extends RecordSignatureBuilder<K, V, ?>> B unifyRequirements(final B builder)
	throws X, IncompatibleFieldException;
}
