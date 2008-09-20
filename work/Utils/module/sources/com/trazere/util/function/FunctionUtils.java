/*
 *  Copyright 2006-2008 Julien Dufour
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.trazere.util.function;

import com.trazere.util.type.Maybe;
import com.trazere.util.type.Tuple2;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The {@link FunctionUtils} provides various helpers regarding the manipulation of filters and functions.
 * 
 * @see Filter
 * @see Filter2
 * @see Function
 * @see Function2
 * @see Procedure
 * @see Procedure2
 */
public class FunctionUtils {
	/**
	 * Filter the given values with the given filter function and populate the given result collection with them.
	 * <p>
	 * This method applies the filter function to every value of the given collection and returns a collection of the accepted values.
	 * 
	 * @param <T> Type of the values to filter.
	 * @param <C> Type of the result collection to build.
	 * @param values Values to filter.
	 * @param filter Filter function to use.
	 * @param results The collection to populate with the results.
	 * @return The given result collection.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <T, C extends Collection<? super T>> C filter(final Collection<? extends T> values, final Filter<? super T> filter, final C results)
	throws ApplicationException {
		assert null != values;
		assert null != filter;
		assert null != results;
		
		// Filter.
		for (final T value : values) {
			if (filter.filter(value)) {
				results.add(value);
			}
		}
		return results;
	}
	
	/**
	 * Filter the given bindings with the given filter function and populate the given result map with them.
	 * <p>
	 * This method applies the filter function to every binding of the given map and returns a map containing the accepted bindings. The keys and the values of
	 * the map are respectively passed as first and second arguments to the filter function.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map to build.
	 * @param bindings Bindings to filter.
	 * @param filter Filter function to use.
	 * @param results The map to populate with the results.
	 * @return The given result map.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <K, V, M extends Map<K, V>> M filter(final Map<? extends K, ? extends V> bindings, final Filter2<? super K, ? super V> filter, final M results)
	throws ApplicationException {
		assert null != bindings;
		assert null != filter;
		assert null != results;
		
		// Filter.
		for (final Map.Entry<? extends K, ? extends V> entry : bindings.entrySet()) {
			final K key = entry.getKey();
			final V value = entry.getValue();
			if (filter.filter(key, value)) {
				results.put(key, value);
			}
		}
		return results;
	}
	
	/**
	 * Filter the given bindings with the given filter function and populate the given result set with their keys.
	 * <p>
	 * This method applies the filter function to every binding of the given map and returns a set of the keys of the accepted bindings. The keys and the values
	 * of the map are respectively passed as first and second arguments to filter function.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <S> Type of the result set to build.
	 * @param bindings Bindings to filter.
	 * @param filter Filter function to use.
	 * @param results The set to populate with the results.
	 * @return The given result set.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <K, V, S extends Set<K>> S filterKeys(final Map<? extends K, ? extends V> bindings, final Filter2<? super K, ? super V> filter, final S results)
	throws ApplicationException {
		assert null != bindings;
		assert null != filter;
		assert null != results;
		
		// Filter.
		for (final Map.Entry<? extends K, ? extends V> entry : bindings.entrySet()) {
			final K key = entry.getKey();
			if (filter.filter(key, entry.getValue())) {
				results.add(key);
			}
		}
		return results;
	}
	
	/**
	 * Filter the given bindings with the given filter function and populate the given result collection with their values.
	 * <p>
	 * This method applies the filter function to every binding of the given map and returns a collection of the values of the accepted bindings. The keys and
	 * the values of the map are respectively passed as first and second arguments to the filter function.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <C> Type of the result collection to build.
	 * @param bindings Bindings to filter.
	 * @param filter Filter function to use.
	 * @param results The collection to populate with the results.
	 * @return The given result collection.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <K, V, C extends Collection<V>> C filterValues(final Map<? extends K, ? extends V> bindings, final Filter2<? super K, ? super V> filter, final C results)
	throws ApplicationException {
		assert null != bindings;
		assert null != filter;
		assert null != results;
		
		// Filter.
		for (final Map.Entry<? extends K, ? extends V> entry : bindings.entrySet()) {
			final K key = entry.getKey();
			final V value = entry.getValue();
			if (filter.filter(key, value)) {
				results.add(value);
			}
		}
		return results;
	}
	
	/**
	 * Count the number of given values accepted by the given filter.
	 * 
	 * @param <T> Type of the values.
	 * @param values Values to count.
	 * @param filter Filter function to use.
	 * @return The number of accepted values.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <T> int count(final Collection<? extends T> values, final Filter<? super T> filter)
	throws ApplicationException {
		assert null != values;
		assert null != filter;
		
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
	 * Count the number of values provided by the given iterator accepted by the given filter.
	 * 
	 * @param <T> Type of the values.
	 * @param values Iterator providing the values to count.
	 * @param filter Filter function to use.
	 * @return The number of accepted values.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <T> int count(final Iterator<? extends T> values, final Filter<? super T> filter)
	throws ApplicationException {
		assert null != values;
		assert null != filter;
		
		// Count.
		int count = 0;
		while (values.hasNext()) {
			if (filter.filter(values.next())) {
				count += 1;
			}
		}
		return count;
	}
	
	/**
	 * Count the number of given bindings accepted by the given filter.
	 * <p>
	 * This method applies the filter function to every binding of the given map. The keys and the values of the map are respectively passed as first and second
	 * arguments to the filter function.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param bindings Bindings to count.
	 * @param filter Filter function to use.
	 * @return The number of accepted bindings.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <K, V> int count(final Map<? extends K, ? extends V> bindings, final Filter2<? super K, ? super V> filter)
	throws ApplicationException {
		assert null != bindings;
		assert null != filter;
		
		// Count.
		int count = 0;
		for (final Map.Entry<? extends K, ? extends V> binding : bindings.entrySet()) {
			if (filter.filter(binding.getKey(), binding.getValue())) {
				count += 1;
			}
		}
		return count;
	}
	
	/**
	 * Test wether any given value is accepted by the given filter function.
	 * 
	 * @param <T> Type of the values.
	 * @param values Values to test.
	 * @param filter Filter function to use.
	 * @return <code>true</code> if any value is accepted, <code>false</code> if all values are rejected.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <T> boolean isAny(final Collection<? extends T> values, final Filter<? super T> filter)
	throws ApplicationException {
		assert null != values;
		assert null != filter;
		
		// Test.
		for (final T value : values) {
			if (filter.filter(value)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Test wether any value provided by the goiven iterator is accepted by the given filter function.
	 * 
	 * @param <T> Type of the values.
	 * @param values Iterator providing the values to test.
	 * @param filter Filter function to use.
	 * @return <code>true</code> if any value is accepted, <code>false</code> if all values are rejected.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <T> boolean isAny(final Iterator<? extends T> values, final Filter<? super T> filter)
	throws ApplicationException {
		assert null != values;
		assert null != filter;
		
		// Test.
		while (values.hasNext()) {
			if (filter.filter(values.next())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Test wether any given binding is accepted by the given filter function.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param bindings Bindings to test.
	 * @param filter Filter function to use.
	 * @return <code>true</code> if any binding is accepted, <code>false</code> if all bindings are rejected.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <K, V> boolean isAny(final Map<? extends K, ? extends V> bindings, final Filter2<? super K, ? super V> filter)
	throws ApplicationException {
		assert null != bindings;
		assert null != filter;
		
		// Test.
		for (final Map.Entry<? extends K, ? extends V> binding : bindings.entrySet()) {
			if (filter.filter(binding.getKey(), binding.getValue())) {
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
	 * @throws ApplicationException When some function application fails.
	 */
	public static <T> boolean areAll(final Collection<? extends T> values, final Filter<? super T> filter)
	throws ApplicationException {
		assert null != values;
		assert null != filter;
		
		// Test.
		for (final T value : values) {
			if (!filter.filter(value)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Test wether all values provided by the given iterator are accepted by the given filter function.
	 * 
	 * @param <T> Type of the values.
	 * @param values Iterator providing the values to test.
	 * @param filter Filter function to use.
	 * @return <code>true</code> if all values are accepted, <code>false</code> if any value is rejected.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <T> boolean areAll(final Iterator<? extends T> values, final Filter<? super T> filter)
	throws ApplicationException {
		assert null != values;
		assert null != filter;
		
		// Test.
		while (values.hasNext()) {
			if (!filter.filter(values.next())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Test wether all given bindings are accepted by the given filter function.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param bindings Bindings to test.
	 * @param filter Filter function to use.
	 * @return <code>true</code> if all bindings are accepted, <code>false</code> if any binding is rejected.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <K, V> boolean areAll(final Map<? extends K, ? extends V> bindings, final Filter2<? super K, ? super V> filter)
	throws ApplicationException {
		assert null != bindings;
		assert null != filter;
		
		// Test.
		for (final Map.Entry<? extends K, ? extends V> binding : bindings.entrySet()) {
			if (!filter.filter(binding.getKey(), binding.getValue())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Apply the given function to the given values and populate the given result collection with the results.
	 * 
	 * @param <T1> Type of the argument values.
	 * @param <T2> Type of the result values.
	 * @param <C> Type of the result collection to build.
	 * @param values Argument values.
	 * @param function Function to apply.
	 * @param results The collection to populate with the results.
	 * @return The given result collection.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <T1, T2, C extends Collection<T2>> C map(final Collection<? extends T1> values, final Function<? super T1, ? extends Maybe<? extends T2>> function, final C results)
	throws ApplicationException {
		assert null != values;
		assert null != function;
		assert null != results;
		
		// Map.
		for (final T1 value : values) {
			final Maybe<? extends T2> result = function.apply(value);
			if (result.isSome()) {
				results.add(result.asSome().getValue());
			}
		}
		return results;
	}
	
	/**
	 * Apply the given function to the values provided by the given iterator and populate the given result collection with the results accepted by the given
	 * filter.
	 * 
	 * @param <T1> Type of the argument values.
	 * @param <T2> Type of the result values.
	 * @param <C> Type of the result collection to build.
	 * @param values Iterator providing the argument values.
	 * @param function Function to apply.
	 * @param results The collection to populate with the results.
	 * @return The given result collection.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <T1, T2, C extends Collection<T2>> C map(final Iterator<? extends T1> values, final Function<? super T1, ? extends Maybe<? extends T2>> function, final C results)
	throws ApplicationException {
		assert null != values;
		assert null != function;
		assert null != results;
		
		// Map.
		while (values.hasNext()) {
			final Maybe<? extends T2> result = function.apply(values.next());
			if (result.isSome()) {
				results.add(result.asSome().getValue());
			}
		}
		return results;
	}
	
	/**
	 * Apply the given function to the given bindings and populate the given result map with the results accepted by the given filter associated to their
	 * argument keys.
	 * <p>
	 * This method applies the function to every binding of the given map. The keys and the values of the map are respectively passed as first and second
	 * arguments to the function.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V1> Type of the argument values.
	 * @param <V2> Type of the result values.
	 * @param <M> Type of the result map to build.
	 * @param bindings Argument bindings.
	 * @param function Function to apply.
	 * @param results The map to populate with the results.
	 * @return The given result map.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <K, V1, V2, M extends Map<K, V2>> M map(final Map<? extends K, ? extends V1> bindings, final Function2<? super K, ? super V1, ? extends Maybe<? extends V2>> function, final M results)
	throws ApplicationException {
		assert null != bindings;
		assert null != function;
		assert null != results;
		
		// Map.
		for (final Map.Entry<? extends K, ? extends V1> binding : bindings.entrySet()) {
			final K key = binding.getKey();
			final Maybe<? extends V2> result = function.apply(key, binding.getValue());
			if (result.isSome()) {
				results.put(key, result.asSome().getValue());
			}
		}
		return results;
	}
	
	/**
	 * Apply the given function to the given keys and populate the given result map of the results associated to their argument keys.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map to build.
	 * @param keys Keys to map.
	 * @param function Function computing the values.
	 * @param results The map to populate with the results.
	 * @return The given result map.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <K, V, M extends Map<K, V>> Map<K, V> mapKeys(final Set<? extends K> keys, final Function<? super K, ? extends Maybe<? extends V>> function, final M results)
	throws ApplicationException {
		assert null != keys;
		assert null != function;
		assert null != results;
		
		// Map the keys.
		for (final K key : keys) {
			final Maybe<? extends V> value = function.apply(key);
			if (value.isSome()) {
				results.put(key, value.asSome().getValue());
			}
		}
		return results;
	}
	
	/**
	 * Apply the given function to the given values and populate the given result map with the argument values associated to their corresponding results.
	 * <p>
	 * When the function return a same key for different values, the last value is associated to the key in the result map.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map to build.
	 * @param values Values to map.
	 * @param function Function computing the keys.
	 * @param results The map to populate with the results.
	 * @return The given result map.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <K, V, M extends Map<K, V>> M mapValues(final Collection<? extends V> values, final Function<? super V, ? extends Maybe<? extends K>> function, final M results)
	throws ApplicationException {
		assert null != values;
		assert null != function;
		assert null != results;
		
		// Map the values.
		for (final V value : values) {
			final Maybe<? extends K> key = function.apply(value);
			if (key.isSome()) {
				results.put(key.asSome().getValue(), value);
			}
		}
		return results;
	}
	
	/**
	 * Apply the given function to the keys of the given map and populate the given result map with the values corresponding to the argument keys associated to
	 * the result keys.
	 * 
	 * @param <K1> Type of the argument keys.
	 * @param <K2> Type of the result keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map to build.
	 * @param map Map to read.
	 * @param function Function to apply.
	 * @param results The map to populate with the results.
	 * @return The given result map.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <K1, K2, V, M extends Map<K2, V>> M remap(final Map<? extends K1, ? extends V> map, final Function<? super K1, ? extends Maybe<? extends K2>> function, final M results)
	throws ApplicationException {
		assert null != map;
		assert null != function;
		assert null != results;
		
		// Remap.
		for (final Map.Entry<? extends K1, ? extends V> entry : map.entrySet()) {
			final Maybe<? extends K2> newKey = function.apply(entry.getKey());
			if (newKey.isSome()) {
				results.put(newKey.asSome().getValue(), entry.getValue());
			}
		}
		return results;
	}
	
	/**
	 * Apply the given function to the given bindings and populate the given result map with the values associated to the result keys.
	 * <p>
	 * This method applies the function to every binding of the given map. The keys and the values of the map are respectively passed as first and second
	 * arguments to the function.
	 * 
	 * @param <K1> Type of the argument keys.
	 * @param <K2> Type of the result keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map to build.
	 * @param bindings Argument bindings.
	 * @param function Function to apply.
	 * @param results The map to populate with the results.
	 * @return The given result map.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <K1, K2, V, M extends Map<K2, V>> M remap(final Map<? extends K1, ? extends V> bindings, final Function2<? super K1, ? super V, ? extends Maybe<? extends K2>> function, final M results)
	throws ApplicationException {
		assert null != bindings;
		assert null != function;
		assert null != results;
		
		// Remap.
		for (final Map.Entry<? extends K1, ? extends V> entry : bindings.entrySet()) {
			final V value = entry.getValue();
			final Maybe<? extends K2> newKey = function.apply(entry.getKey(), value);
			if (newKey.isSome()) {
				results.put(newKey.asSome().getValue(), value);
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
	 * @throws ApplicationException When some function application fails.
	 */
	public static <T> void apply(final Collection<? extends T> values, final Procedure<? super T> procedure)
	throws ApplicationException {
		assert null != values;
		assert null != procedure;
		
		// Apply.
		for (final T value : values) {
			procedure.apply(value);
		}
	}
	
	/**
	 * Apply the given procedure to the values provided by the given iterator.
	 * 
	 * @param <T> Type of the argument values.
	 * @param values Iterator providing the argument values.
	 * @param procedure Procedure to apply.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <T> void apply(final Iterator<? extends T> values, final Procedure<? super T> procedure)
	throws ApplicationException {
		assert null != values;
		assert null != procedure;
		
		// Apply.
		while (values.hasNext()) {
			procedure.apply(values.next());
		}
	}
	
	/**
	 * Apply the given procedure to the given bindings.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param bindings Argument bindings.
	 * @param procedure Procedure to apply.
	 * @throws ApplicationException When some function application fails.
	 */
	public static <K, V> void apply(final Map<? extends K, ? extends V> bindings, final Procedure2<? super K, ? super V> procedure)
	throws ApplicationException {
		assert null != bindings;
		assert null != procedure;
		
		// Apply.
		for (final Map.Entry<? extends K, ? extends V> binding : bindings.entrySet()) {
			procedure.apply(binding.getKey(), binding.getValue());
		}
	}
	
	// DOCME
	public static <T, A> A fold(final Collection<? extends T> values, final Function2<? super A, ? super T, ? extends A> function, final A initialAccumulator)
	throws ApplicationException {
		assert null != values;
		assert null != function;
		
		// Fold.
		A accumulator = initialAccumulator;
		for (final T value : values) {
			accumulator = function.apply(accumulator, value);
		}
		return accumulator;
	}
	
	// DOCME
	public static <T, A> A fold(final Iterator<? extends T> iterator, final Function2<? super A, ? super T, ? extends A> function, final A initialAccumulator)
	throws ApplicationException {
		assert null != iterator;
		assert null != function;
		
		// Fold.
		A accumulator = initialAccumulator;
		while (iterator.hasNext()) {
			final T value = iterator.next();
			accumulator = function.apply(accumulator, value);
		}
		return accumulator;
	}
	
	// DOCME
	public static <T1, T2, L extends List<Tuple2<T1, T2>>> L zip(final Collection<T1> values1, final Collection<T2> values2, final L results) {
		assert null != values1;
		assert null != values2;
		
		// Zip.
		return zip(values1.iterator(), values2.iterator(), results);
	}
	
	// DOCME
	public static <T1, T2, L extends List<Tuple2<T1, T2>>> L zip(final Iterator<T1> list1, final Iterator<T2> list2, final L results) {
		assert null != list1;
		assert null != list2;
		assert null != results;
		
		// Zip.
		while (list1.hasNext() && list2.hasNext()) {
			results.add(Tuple2.build(list1.next(), list2.next()));
		}
		return results;
	}
	
	// DOCME
	public static <T1, T2, L1 extends List<T1>, L2 extends List<T2>> void unzip(final Collection<? extends Tuple2<T1, T2>> values, final L1 results1, final L2 results2) {
		assert null != values;
		assert null != results1;
		assert null != results2;
		
		// Unzip.
		for (final Tuple2<T1, T2> value : values) {
			results1.add(value.getFirst());
			results2.add(value.getSecond());
		}
	}
	
	// DOCME
	public static <T1, T2, L1 extends List<T1>, L2 extends List<T2>> void unzip(final Iterator<? extends Tuple2<T1, T2>> values, final L1 results1, final L2 results2) {
		assert null != values;
		assert null != results1;
		assert null != results2;
		
		// Unzip.
		while (values.hasNext()) {
			final Tuple2<T1, T2> value = values.next();
			results1.add(value.getFirst());
			results2.add(value.getSecond());
		}
	}
	
	private FunctionUtils() {
		// Prevent instantiation.
	}
}
