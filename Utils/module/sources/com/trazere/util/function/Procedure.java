package com.trazere.util.function;

/**
 * The <code>Procedure</code> interface defines one argument procedures (one argument functions which return no results).
 * 
 * @param <T> Type of the argument values.
 */
public interface Procedure<T> {
	/**
	 * Process the given argument value.
	 * 
	 * @param value Argument value to process.
	 */
	public void apply(final T value);
}
