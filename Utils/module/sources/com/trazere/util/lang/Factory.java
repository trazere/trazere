package com.trazere.util.lang;

/**
 * The {@link Factory} interface defines generic factories.
 * 
 * @param <T> Type of the built values.
 * @param <X> Type of the exceptions.
 */
public interface Factory<T, X extends Exception> {
	/**
	 * Execute the receiver factory to build a new value.
	 * 
	 * @return The built value. May be <code>null</code>.
	 * @throws X When the value cannot be built.
	 */
	public T build()
	throws X;
}
