package com.trazere.util.function;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.trazere.util.Assert;
import com.trazere.util.collection.CollectionUtils;

/**
 * The <code>FunctionUtils</code> provides various helpers regarding the manipulation of filters and functions.
 * 
 * @see Filter
 * @see Filter2
 * @see Function
 * @see Function2
 * @see Procedure
 */
public class FunctionUtils {
	/**
	 * Filter the given collection with the given filter function.
	 * <p>
	 * This method applies the filter function to every value of the given collection and returns a collection of the accepted values.
	 * 
	 * @param <T> Type of the values to filter.
	 * @param <C> Type of the result collection to build.
	 * @param values Values to filter.
	 * @param filter Filter function to use.
	 * @param type Class of the result collection to build. Must be instanciable with no arguments.
	 * @return A <code>Collection</code> containing the accepted values.
	 */
	public static <T, C extends Collection<T>> C filter(final Collection<? extends T> values, final Filter<T> filter, final Class<C> type) {
		Assert.notNull(values);
		Assert.notNull(filter);

		// Filter.
		final C results = CollectionUtils.buildCollection(type);
		for (final T value : values) {
			if (filter.filter(value)) {
				results.add(value);
			}
		}
		return results;
	}

	/**
	 * Filter the given map with the given filter function.
	 * <p>
	 * This method applies the filter function to every binding of the given map and returns a map containing the accepted bindings. The keys and the values of
	 * the map are respectively passed as first and second arguments to the filter function.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map to build.
	 * @param map Map to filter.
	 * @param filter Filter function to use.
	 * @param type Class of the result map to build. Must be instanciable with no arguments.
	 * @return A <code>Map</code> containing the accepted bindings.
	 */
	public static <K, V, M extends Map<K, V>> M filter(final Map<? extends K, ? extends V> map, final Filter2<K, V> filter, final Class<M> type) {
		Assert.notNull(map);
		Assert.notNull(filter);

		// Filter.
		final M results = CollectionUtils.buildMap(type);
		for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
			final K key = entry.getKey();
			final V value = entry.getValue();
			if (filter.filter(key, value)) {
				results.put(key, value);
			}
		}
		return results;
	}

	/**
	 * Filter the given map with the given filter function.
	 * <p>
	 * This method applies the filter function to every binding of the given map and returns a set of the keys of the accepted bindings. The keys and the values
	 * of the map are respectively passed as first and second arguments to filter function.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <S> Type of the result set to build.
	 * @param map Map to filter.
	 * @param filter Filter function to use.
	 * @param type Class of the result set to build. Must be instanciable with no arguments.
	 * @return A <code>Set</code> containing the accepted keys.
	 */
	public static <K, V, S extends Set<K>> S filterKeys(final Map<? extends K, ? extends V> map, final Filter2<K, V> filter, final Class<S> type) {
		Assert.notNull(map);
		Assert.notNull(filter);

		// Filter.
		final S results = CollectionUtils.buildCollection(type);
		for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
			final K key = entry.getKey();
			final V value = entry.getValue();
			if (filter.filter(key, value)) {
				results.add(key);
			}
		}
		return results;
	}

	/**
	 * Filter the given map with the given filter function.
	 * <p>
	 * This method applies the filter function to every binding of the given map and returns a collection of the values of the accepted bindings. The keys and
	 * the values of the map are respectively passed as first and second arguments to the filter function.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <C> Type of the result collection to build.
	 * @param map Map to filter.
	 * @param filter Filter function to use.
	 * @param type Class of the result collection to build. Must be instanciable with no arguments.
	 * @return A <code>Collection</code> containing the accepted values.
	 */
	public static <K, V, C extends Collection<V>> C filterValues(final Map<? extends K, ? extends V> map, final Filter2<K, V> filter, final Class<C> type) {
		Assert.notNull(map);
		Assert.notNull(filter);

		// Filter.
		final C results = CollectionUtils.buildCollection(type);
		for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
			final K key = entry.getKey();
			final V value = entry.getValue();
			if (filter.filter(key, value)) {
				results.add(value);
			}
		}
		return results;
	}

	/**
	 * Count the number of values of the given collection accepted by the given filter.
	 * 
	 * @param <T> Type of the values.
	 * @param values Values to count.
	 * @param filter Filter function to use.
	 * @return The number of accepted values.
	 */
	public static <T> int count(final Collection<? extends T> values, final Filter<T> filter) {
		Assert.notNull(values);
		Assert.notNull(filter);

		// Count.
		int count = 0;
		for (final T value : values) {
			if (filter.filter(value)) {
				count += 1;
			}
		}
		return count;
	}

	/**
	 * Count the number of bindings of the given map accepted by the given filter.
	 * <p>
	 * This method applies the filter function to every binding of the given map. The keys and the values of the map are respectively passed as first and second
	 * arguments to the filter function.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to count.
	 * @param filter Filter function to use.
	 * @return The number of accepted bindings.
	 */
	public static <K, V> int count(final Map<? extends K, ? extends V> map, final Filter2<K, V> filter) {
		Assert.notNull(map);
		Assert.notNull(filter);

		// Count.
		int count = 0;
		for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
			final K key = entry.getKey();
			final V value = entry.getValue();
			if (filter.filter(key, value)) {
				count += 1;
			}
		}
		return count;
	}

	/**
	 * Test wether any of the given values is accepted by the given filter function.
	 * 
	 * @param <T> Type of the values.
	 * @param values Values to test.
	 * @param filter Filter function to use.
	 * @return <code>true</code> if any value is accepted, <code>false</code> if all values are rejected.
	 */
	public static <T> boolean isAny(final Collection<? extends T> values, final Filter<T> filter) {
		Assert.notNull(values);
		Assert.notNull(filter);

		// Test.
		for (final T value : values) {
			if (filter.filter(value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Test wether all given values are accepted by the given filter function.
	 * 
	 * @param <T> Type of the values.
	 * @param values Values to test.
	 * @param filter Filter function to use.
	 * @return <code>true</code> if all values are accepted, <code>false</code> if any value is rejected.
	 */
	public static <T> boolean areAll(final Collection<? extends T> values, final Filter<T> filter) {
		Assert.notNull(values);
		Assert.notNull(filter);

		// Test.
		for (final T value : values) {
			if (!filter.filter(value)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Apply the given function to the given values and return the results.
	 * 
	 * @param <T1> Type of the argument values.
	 * @param <T2> Type of the result values.
	 * @param <C> Type of the result collection to build.
	 * @param values Argument values.
	 * @param function Function to apply.
	 * @param ignoreNull Flag indincating wether the <code>null</code> results should be ignored or not.
	 * @param type Class of the result collection to build. Must be instanciable with no arguments.
	 * @return A <code>Collection</code> containing the results of the function applications.
	 */
	public static <T1, T2, C extends Collection<T2>> C map(final Collection<? extends T1> values, final Function<T1, T2> function, final boolean ignoreNull, final Class<C> type) {
		Assert.notNull(values);
		Assert.notNull(function);

		// Map.
		final C results = CollectionUtils.buildCollection(type);
		for (final T1 value : values) {
			final T2 result = function.apply(value);
			if (null != result || !ignoreNull) {
				results.add(result);
			}
		}
		return results;
	}

	/**
	 * Apply the given function to the bindings of the given map and return a map of the results associated to their argument keys.
	 * <p>
	 * This method applies the function to every binding of the given map. The keys and the values of the map are respectively passed as first and second
	 * arguments to the function.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V1> Type of the argument values.
	 * @param <V2> Type of the result values.
	 * @param <M> Type of the result map to build.
	 * @param map Argument map.
	 * @param function Function to apply.
	 * @param ignoreNull Flag indincating wether the <code>null</code> results should be ignored or not.
	 * @param type Class of the result map to build. Must be instanciable with no arguments.
	 * @return A <code>Map</code> containing the results of the function applications identified by the corresponding argument keys.
	 */
	public static <K, V1, V2, M extends Map<K, V2>> M map(final Map<? extends K, ? extends V1> map, final Function2<K, V1, V2> function, final boolean ignoreNull, final Class<M> type) {
		Assert.notNull(map);
		Assert.notNull(function);

		// Map.
		final M results = CollectionUtils.buildMap(type);
		for (final Map.Entry<? extends K, ? extends V1> entry : map.entrySet()) {
			final K key = entry.getKey();
			final V1 value = entry.getValue();
			final V2 result = function.apply(key, value);
			if (null != result || !ignoreNull) {
				results.put(key, result);
			}
		}
		return results;
	}

	/**
	 * Apply the given function to the given keys and build a map of the results associated to their argument keys.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map to build.
	 * @param keys Keys to map.
	 * @param function Function computing the values.
	 * @param ignoreNull Flag indincating wether the <code>null</code> results should be ignored or not.
	 * @param type Class of the result map to build. Must be instanciable with no arguments.
	 * @return A <code>Map</code> containing the results of the function applications identified by the corresponding argument keys.
	 */
	public static <K, V, M extends Map<K, V>> Map<K, V> mapKeys(final Set<? extends K> keys, final Function<K, V> function, final boolean ignoreNull, final Class<M> type) {
		Assert.notNull(keys);
		Assert.notNull(function);

		// Map the keys.
		final M results = CollectionUtils.buildMap(type);
		for (final K key : keys) {
			final V value = function.apply(key);
			if (null != value || !ignoreNull) {
				results.put(key, value);
			}
		}
		return results;
	}

	/**
	 * Apply the given function to the given values and build a map of the argument values associated to their corresponding results.
	 * <p>
	 * When the function return a same key for different values, the last value is associated to the key in the result map.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map to build.
	 * @param values Values to map.
	 * @param function Function computing the keys.
	 * @param ignoreNull Flag indincating wether the <code>null</code> results should be ignored or not.
	 * @param type Class of the result map to build. Must be instanciable with no arguments.
	 * @return A <code>Map</code> containing the argument values identified by the corresponding result of the function application.
	 */
	public static <K, V, M extends Map<K, V>> M mapValues(final Collection<? extends V> values, final Function<V, K> function, final boolean ignoreNull, final Class<M> type) {
		Assert.notNull(values);
		Assert.notNull(function);

		// Map the values.
		final M results = CollectionUtils.buildMap(type);
		for (final V value : values) {
			final K key = function.apply(value);
			if (null != key || !ignoreNull) {
				results.put(key, value);
			}
		}
		return results;
	}

	/**
	 * Apply the given function to the keys of the given map and return a map of the values corresponding to the argument keys associated to the result keys.
	 * 
	 * @param <K1> Type of the argument keys.
	 * @param <K2> Type of the result keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map to build.
	 * @param map Map to read.
	 * @param function Function to apply.
	 * @param ignoreNull Flag indincating wether the <code>null</code> results should be ignored or not.
	 * @param type Class of the result map to build. Must be instanciable with no arguments.
	 * @return A <code>Map</code> containing the values corresponding the the argument keys associated to the result keys.
	 */
	public static <K1, K2, V, M extends Map<K2, V>> M remap(final Map<? extends K1, ? extends V> map, final Function<K1, K2> function, final boolean ignoreNull, final Class<M> type) {
		Assert.notNull(map);
		Assert.notNull(function);

		// Remap.
		final M results = CollectionUtils.buildMap(type);
		for (final Map.Entry<? extends K1, ? extends V> entry : map.entrySet()) {
			final K1 key = entry.getKey();
			final K2 newKey = function.apply(key);
			if (null != newKey || !ignoreNull) {
				final V value = entry.getValue();
				results.put(newKey, value);
			}
		}
		return results;
	}

	/**
	 * Apply the given function to the bindings of the given map and return a map of the values associated to the result keys.
	 * <p>
	 * This method applies the function to every binding of the given map. The keys and the values of the map are respectively passed as first and second
	 * arguments to the function.
	 * 
	 * @param <K1> Type of the argument keys.
	 * @param <K2> Type of the result keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map to build.
	 * @param map Map to read.
	 * @param function Function to apply.
	 * @param ignoreNull Flag indincating wether the <code>null</code> results should be ignored or not.
	 * @param type Class of the result map to build. Must be instanciable with no arguments.
	 * @return A <code>Map</code> containing the values associated to the result keys.
	 */
	public static <K1, K2, V, M extends Map<K2, V>> M remap(final Map<? extends K1, ? extends V> map, final Function2<K1, V, K2> function, final boolean ignoreNull, final Class<M> type) {
		Assert.notNull(map);
		Assert.notNull(function);

		// Remap.
		final M results = CollectionUtils.buildMap(type);
		for (final Map.Entry<? extends K1, ? extends V> entry : map.entrySet()) {
			final K1 key = entry.getKey();
			final V value = entry.getValue();
			final K2 newKey = function.apply(key, value);
			if (null != newKey || !ignoreNull) {
				results.put(newKey, value);
			}
		}
		return results;
	}

	/**
	 * Apply the given procedure to the given values.
	 * 
	 * @param <T> Type of the argument values.
	 * @param values Argument values
	 * @param procedure Procedure to apply.
	 */
	public static <T> void apply(final Collection<? extends T> values, final Procedure<T> procedure) {
		Assert.notNull(values);
		Assert.notNull(procedure);

		// Apply.
		for (final T value : values) {
			procedure.apply(value);
		}
	}

	/**
	 * Apply the given procedure to the values provided by the given iterator.
	 * 
	 * @param <T> Type of the argument values.
	 * @param iterator Iterator providing the argument values.
	 * @param procedure Procedure to apply.
	 */
	public static <T> void apply(final Iterator<? extends T> iterator, final Procedure<T> procedure) {
		Assert.notNull(iterator);
		Assert.notNull(procedure);

		// Apply.
		while (iterator.hasNext()) {
			final T value = iterator.next();
			procedure.apply(value);
		}
	}

	// DOCME
	public static <T, A> A fold(final Collection<? extends T> values, final Function2<A, T, A> function, final A initialAccumulator) {
		Assert.notNull(values);
		Assert.notNull(function);

		// Fold.
		A accumulator = initialAccumulator;
		for (final T value : values) {
			accumulator = function.apply(accumulator, value);
		}
		return accumulator;
	}

	// DOCME
	public static <T, A> A fold(final Iterator<? extends T> iterator, final Function2<A, T, A> function, final A initialAccumulator) {
		Assert.notNull(iterator);
		Assert.notNull(function);

		// Fold.
		A accumulator = initialAccumulator;
		while (iterator.hasNext()) {
			final T value = iterator.next();
			accumulator = function.apply(accumulator, value);
		}
		return accumulator;
	}

	private FunctionUtils() {
		// Prevent instanciation.
	}
}
