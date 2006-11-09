package com.trazere.util.function;

/**
 * The <code>Function</code> interface defines one argument functions.
 * 
 * @param <T> Type of the argument values.
 * @param <R> Type of the result values.
 */
public interface Function<T, R> {
	/**
	 * Apply the receiver function to the given argument value.
	 * 
	 * @param value Argument value of the function.
	 * @return The result of the function application.
	 */
	public R apply(final T value);
}
