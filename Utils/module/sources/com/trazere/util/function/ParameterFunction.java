package com.trazere.util.function;

import com.trazere.util.record.Record;

/**
 * The {@link Parametrable} interface defines functions which take a record of parameters as argument.
 * 
 * @param <K> Type of the keys of the parameters.
 * @param <V> Type of the values of the parameters.
 * @param <T> Type of the result values.
 * @param <X> Type of the exceptions.
 */
public interface ParameterFunction<K, V, T, X extends Exception>
extends Function1<Record<K, V>, T, X>, Parametrable<K, V> {
	// Just typing.
}
