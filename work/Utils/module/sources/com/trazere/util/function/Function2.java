package com.trazere.util.function;

/**
 * The <code>Function2</code> interface defines two arguments functions.
 * 
 * @param <T1> Type of the first argument values.
 * @param <T2> Type of the second argument values.
 * @param <R> Type of the result values.
 */
public interface Function2<T1, T2, R> {
	/**
	 * Apply the receiver function to the given argument values.
	 * 
	 * @param value1 First argument value.
	 * @param value2 Second argument value.
	 * @return The result of the function application.
	 */
	public R apply(final T1 value1, final T2 value2);
}
