package com.trazere.util.collection;

import com.trazere.util.function.Function1;

/**
 * The {@link ObjectTypeMap} interface defines maps from Java class hierarchies to values.
 * 
 * @param <V> Type of the values.
 * @param <X> Type of the exceptions.
 */
public interface ObjectTypeMap<V, X extends Exception>
extends Function1<Class<?>, V, X> {
	/**
	 * Gets the value associated to the given type.
	 * 
	 * @param type The type.
	 * @return The value. May be <code>null</code>.
	 * @throws X When the value cannot be got.
	 */
	public V get(final Class<?> type)
	throws X;
}
