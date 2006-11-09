package com.trazere.util.function;

/**
 * The <code>Filter</code> interface defines two arguments filter functions.
 * 
 * @param <T1> Type of the first argument values.
 * @param <T2> Type of the second argument values.
 */
public interface Filter2<T1, T2> {
	/**
	 * Filter the given argument values.
	 * 
	 * @param value1 First argument value to filter.
	 * @param value2 Second argument value to filter.
	 * @return <code>true</code> to accept the values, <code>false</code> to reject them.
	 */
	public boolean filter(final T1 value1, final T2 value2);
}
