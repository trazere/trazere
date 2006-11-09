package com.trazere.util.function;

import java.util.Map;

import com.trazere.util.Assert;

/**
 * The <code>Functions</code> class provides various standard functions.
 * 
 * @see Function
 * @see Function2
 */
public class Functions {
	/**
	 * Build a function defined by the given map.
	 * <p>
	 * The domain of the built function is the set of keys of the map. The built function returns the values associated to the keys in the map, or
	 * <code>null</code>.
	 * 
	 * @param <T> Type of the keys of the map (the argument values).
	 * @param <R> Type of the values of the map (the result values).
	 * @param map Map defining the function to build.
	 * @return The function.
	 */
	public static <T, R> Function<T, R> map(final Map<T, R> map) {
		// Checks.
		Assert.notNull(map);

		// Build the function.
		return new Function<T, R>() {
			public R apply(final T key) {
				return map.get(key);
			}
		};
	}

	private Functions() {
		// Prevent instanciation.
	}
}
