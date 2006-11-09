package com.trazere.util.function;

/**
 * The <code>Filter</code> interface defines one argument filter functions.
 * 
 * @param <T> Type of the argument values.
 */
public interface Filter<T> {
	/**
	 * Filter the given argument value.
	 * 
	 * @param value Argument value to filter.
	 * @return <code>true</code> to accept the value, <code>false</code> to reject it.
	 */
	public boolean filter(final T value);
}
