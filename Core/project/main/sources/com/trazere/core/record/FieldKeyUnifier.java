package com.trazere.core.record;

/**
 * The {@link FieldKeyUnifier} interfaces defines unifiers of field keys.
 * 
 * @param <K> Type of the keys.
 * @since 2.0
 */
public interface FieldKeyUnifier<K extends FieldKey<K, ?>> {
	/**
	 * Unifies the given field keys.
	 * 
	 * @param <V1> Type of the value of the first field1.
	 * @param <V2> Type of the value of the second field1.
	 * @param key1 First field key.
	 * @param key2 Second field key.
	 * @return The unified field key.
	 * @throws IncompatibleFieldException When the field keys are not compatible.
	 * @since 2.0
	 */
	<V1, V2> FieldKey<K, ?> unify(final FieldKey<K, V1> key1, final FieldKey<K, V2> key2)
	throws IncompatibleFieldException;
}
