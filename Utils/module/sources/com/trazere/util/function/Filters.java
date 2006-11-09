package com.trazere.util.function;

import java.util.Collection;

/**
 * The <code>Filters</code> class provides various standard filter functions.
 * 
 * @see Filter
 * @see Filter2
 */
public class Filters {
	/**
	 * Build a filter function defined by the given values.
	 * <p>
	 * The built filter function accepts the values included in the given values.
	 * 
	 * @param <T> Type of the argument values.
	 * @param values Values accepted by the filter function to build.
	 * @return The filter function.
	 */
	public static <T> Filter<T> values(final Collection<T> values) {
		return new Filter<T>() {
			public boolean filter(final T value) {
				return values.contains(value);
			}
		};
	}

	/**
	 * Build a two arguments filter function defined by the given values.
	 * <p>
	 * The built filter function accepts the values when the first argument value is included in the given values.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param values Values accepted by the filter function to build.
	 * @return The filter function.
	 */
	public static <T1, T2> Filter2<T1, T2> values1(final Collection<T1> values) {
		return new Filter2<T1, T2>() {
			public boolean filter(final T1 value1, final T2 value2) {
				return values.contains(value1);
			}
		};
	}

	/**
	 * Build a two arguments filter function defined by the given values.
	 * <p>
	 * The built filter function accepts the values when the second argument value is included in the given values.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param values Values accepted by the filter function to build.
	 * @return The filter function.
	 */
	public static <T1, T2> Filter2<T1, T2> values2(final Collection<T2> values) {
		return new Filter2<T1, T2>() {
			public boolean filter(final T1 value1, final T2 value2) {
				return values.contains(value2);
			}
		};
	}

	private Filters() {
		// Prevent instanciation.
	}
}
