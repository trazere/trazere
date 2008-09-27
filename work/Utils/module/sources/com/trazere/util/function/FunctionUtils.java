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

import com.trazere.util.lang.MutableReference;
import com.trazere.util.type.Maybe;
import com.trazere.util.type.Tuple2;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The {@link FunctionUtils} provides various helpers regarding predicates, functions and procedures.
 * 
 * @see Predicate
 * @see Predicate2
 * @see Function
 * @see Function2
 * @see Procedure
 * @see Procedure2
 */
public class FunctionUtils {
	/**
	 * Filter the given values using the given predicate and populate the given result collection with them.
	 * 
	 * @param <T> Type of the values to filter.
	 * @param <C> Type of the collection to populate.
	 * @param <E> Type of the exceptions.
	 * @param predicate Predicate to use.
	 * @param values Values to filter.
	 * @param results The collection to populate with the accepted values.
	 * @return The given result collection.
	 * @throws E When some filter evaluation fails.
	 */
	public static <T, C extends Collection<? super T>, E extends Exception> C filter(final Predicate<? super T, E> predicate, final Collection<? extends T> values, final C results)
	throws E {
		assert null != values;
		assert null != predicate;
		assert null != results;
		
		// Filter.
		for (final T value : values) {
			if (predicate.evaluate(value)) {
				results.add(value);
			}
		}
		return results;
	}
	
	/**
	 * Filter the given bindings using the given predicate and populate the given result map with them.
	 * <p>
	 * This method evaluates the predicate by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map to populate.
	 * @param <E> Type of the exceptions.
	 * @param predicate Predicate to use.
	 * @param bindings Bindings to filter.
	 * @param results The map to populate with the accepted bindings.
	 * @return The given result map.
	 * @throws E When some predicate evaluation fails.
	 */
	public static <K, V, M extends Map<? super K, ? super V>, E extends Exception> M filter(final Predicate2<? super K, ? super V, E> predicate, final Map<? extends K, ? extends V> bindings, final M results)
	throws E {
		assert null != predicate;
		assert null != bindings;
		assert null != results;
		
		// Filter.
		for (final Map.Entry<? extends K, ? extends V> entry : bindings.entrySet()) {
			final K key = entry.getKey();
			final V value = entry.getValue();
			if (predicate.evaluate(key, value)) {
				results.put(key, value);
			}
		}
		return results;
	}
	
	/**
	 * Filter the given bindings using the given predicate and populate the given result set with their keys.
	 * <p>
	 * This method evaluates the predicate by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <S> Type of the set to populate.
	 * @param <E> Type of the exceptions.
	 * @param predicate Predicate to use.
	 * @param bindings Bindings to filter.
	 * @param results The set to populate with the keys of the accepted bindings.
	 * @return The given result set.
	 * @throws E When some predicate evaluation fails.
	 */
	public static <K, V, S extends Set<? super K>, E extends Exception> S filterKeys(final Predicate2<? super K, ? super V, E> predicate, final Map<? extends K, ? extends V> bindings, final S results)
	throws E {
		assert null != predicate;
		assert null != bindings;
		assert null != results;
		
		// Filter.
		for (final Map.Entry<? extends K, ? extends V> entry : bindings.entrySet()) {
			final K key = entry.getKey();
			if (predicate.evaluate(key, entry.getValue())) {
				results.add(key);
			}
		}
		return results;
	}
	
	/**
	 * Filter the given bindings using the given predicate and populate the given result set with their values.
	 * <p>
	 * This method evaluates the predicate by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <C> Type of the collection to populate.
	 * @param <E> Type of the exceptions.
	 * @param predicate Predicate to use.
	 * @param bindings Bindings to filter.
	 * @param results The collection to populate with the results.
	 * @return The given result collection.
	 * @throws E When some predicate evaluation fails.
	 */
	public static <K, V, C extends Collection<? super V>, E extends Exception> C filterValues(final Predicate2<? super K, ? super V, E> predicate, final Map<? extends K, ? extends V> bindings, final C results)
	throws E {
		assert null != predicate;
		assert null != bindings;
		assert null != results;
		
		// Filter.
		for (final Map.Entry<? extends K, ? extends V> entry : bindings.entrySet()) {
			final K key = entry.getKey();
			final V value = entry.getValue();
			if (predicate.evaluate(key, value)) {
				results.add(value);
			}
		}
		return results;
	}
	
	/**
	 * Count the given values accepted by the given predicate.
	 * 
	 * @param <T> Type of the values.
	 * @param <E> Type of the exceptions.
	 * @param predicate Predicate to use.
	 * @param values Values to count.
	 * @return The number of accepted values.
	 * @throws E When some predicate evaluation fails.
	 */
	public static <T, E extends Exception> int count(final Predicate<? super T, E> predicate, final Collection<? extends T> values)
	throws E {
		assert null != predicate;
		assert null != values;
		
		// Count.
		int count = 0;
		for (final T value : values) {
			if (predicate.evaluate(value)) {
				count += 1;
			}
		}
		return count;
	}
	
	/**
	 * Count the values provided by the given iterator accepted by the given predicate.
	 * 
	 * @param <T> Type of the values.
	 * @param <E> Type of the exceptions.
	 * @param predicate Predicate to use.
	 * @param values Iterator providing the values to count.
	 * @return The number of accepted values.
	 * @throws E When some predicate evaluation fails.
	 */
	public static <T, E extends Exception> int count(final Predicate<? super T, E> predicate, final Iterator<? extends T> values)
	throws E {
		assert null != predicate;
		assert null != values;
		
		// Count.
		int count = 0;
		while (values.hasNext()) {
			if (predicate.evaluate(values.next())) {
				count += 1;
			}
		}
		return count;
	}
	
	/**
	 * Count the given bindings accepted by the given predicate.
	 * <p>
	 * This method evaluates the predicate by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <E> Type of the exceptions.
	 * @param predicate Predicate to use.
	 * @param bindings Bindings to count.
	 * @return The number of accepted bindings.
	 * @throws E When some predicate evaluation fails.
	 */
	public static <K, V, E extends Exception> int count(final Predicate2<? super K, ? super V, E> predicate, final Map<? extends K, ? extends V> bindings)
	throws E {
		assert null != predicate;
		assert null != bindings;
		
		// Count.
		int count = 0;
		for (final Map.Entry<? extends K, ? extends V> binding : bindings.entrySet()) {
			if (predicate.evaluate(binding.getKey(), binding.getValue())) {
				count += 1;
			}
		}
		return count;
	}
	
	/**
	 * Test wether any given value is accepted by the given predicate.
	 * 
	 * @param <T> Type of the values.
	 * @param <E> Type of the exceptions.
	 * @param predicate Predicate to use.
	 * @param values Values to test.
	 * @return <code>true</code> if any value is accepted, <code>false</code> if all values are rejected.
	 * @throws E When some predicate evaluation fails.
	 */
	public static <T, E extends Exception> boolean isAny(final Predicate<? super T, E> predicate, final Collection<? extends T> values)
	throws E {
		assert null != predicate;
		assert null != values;
		
		// Test.
		for (final T value : values) {
			if (predicate.evaluate(value)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Test wether any value provided by the given iterator is accepted by the given predicate.
	 * 
	 * @param <T> Type of the values.
	 * @param <E> Type of the exceptions.
	 * @param predicate Predicate to use.
	 * @param values Iterator providing the values to test.
	 * @return <code>true</code> if any value is accepted, <code>false</code> if all values are rejected.
	 * @throws E When some predicate evaluation fails.
	 */
	public static <T, E extends Exception> boolean isAny(final Predicate<? super T, E> predicate, final Iterator<? extends T> values)
	throws E {
		assert null != predicate;
		assert null != values;
		
		// Test.
		while (values.hasNext()) {
			if (predicate.evaluate(values.next())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Test wether any given binding is accepted by the given predicate.
	 * <p>
	 * This method evaluates the predicate by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <E> Type of the exceptions.
	 * @param predicate Predicate to use.
	 * @param bindings Bindings to test.
	 * @return <code>true</code> if any binding is accepted, <code>false</code> if all bindings are rejected.
	 * @throws E When some predicate evaluation fails.
	 */
	public static <K, V, E extends Exception> boolean isAny(final Predicate2<? super K, ? super V, E> predicate, final Map<? extends K, ? extends V> bindings)
	throws E {
		assert null != predicate;
		assert null != bindings;
		
		// Test.
		for (final Map.Entry<? extends K, ? extends V> binding : bindings.entrySet()) {
			if (predicate.evaluate(binding.getKey(), binding.getValue())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Test wether all given values are accepted by the given predicate.
	 * 
	 * @param <T> Type of the values.
	 * @param <E> Type of the exceptions.
	 * @param predicate Predicate to use.
	 * @param values Values to test.
	 * @return <code>true</code> if all values are accepted, <code>false</code> if any value is rejected.
	 * @throws E When some predicate evaluation fails.
	 */
	public static <T, E extends Exception> boolean areAll(final Predicate<? super T, E> predicate, final Collection<? extends T> values)
	throws E {
		assert null != predicate;
		assert null != values;
		
		// Test.
		for (final T value : values) {
			if (!predicate.evaluate(value)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Test wether all values provided by the given iterator are accepted by the given predicate.
	 * 
	 * @param <T> Type of the values.
	 * @param <E> Type of the exceptions.
	 * @param predicate Predicate to use.
	 * @param values Iterator providing the values to test.
	 * @return <code>true</code> if all values are accepted, <code>false</code> if any value is rejected.
	 * @throws E When some predicate evaluation fails.
	 */
	public static <T, E extends Exception> boolean areAll(final Predicate<? super T, E> predicate, final Iterator<? extends T> values)
	throws E {
		assert null != predicate;
		assert null != values;
		
		// Test.
		while (values.hasNext()) {
			if (!predicate.evaluate(values.next())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Test wether all given bindings are accepted by the given predicate.
	 * <p>
	 * This method evaluates the predicate by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <E> Type of the exceptions.
	 * @param predicate Predicate to use.
	 * @param bindings Bindings to test.
	 * @return <code>true</code> if all bindings are accepted, <code>false</code> if any binding is rejected.
	 * @throws E When some predicate evaluation fails.
	 */
	public static <K, V, E extends Exception> boolean areAll(final Predicate2<? super K, ? super V, E> predicate, final Map<? extends K, ? extends V> bindings)
	throws E {
		assert null != predicate;
		assert null != bindings;
		
		// Test.
		for (final Map.Entry<? extends K, ? extends V> binding : bindings.entrySet()) {
			if (!predicate.evaluate(binding.getKey(), binding.getValue())) {
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
	 * @param <C> Type of the collection to populate.
	 * @param <E> Type of the exceptions.
	 * @param function Function to use.
	 * @param values Argument values.
	 * @param results The collection to populate with the results.
	 * @return The given result collection.
	 * @throws E When some function evaluation fails.
	 */
	public static <T1, T2, C extends Collection<? super T2>, E extends Exception> C map(final Function<? super T1, ? extends Maybe<? extends T2>, E> function, final Collection<? extends T1> values, final C results)
	throws E {
		assert null != function;
		assert null != values;
		assert null != results;
		
		// Map.
		for (final T1 value : values) {
			final Maybe<? extends T2> result = function.evaluate(value);
			if (result.isSome()) {
				results.add(result.asSome().getValue());
			}
		}
		return results;
	}
	
	/**
	 * Apply the given function to the values provided by the given iterator and populate the given result collection with the results.
	 * 
	 * @param <T1> Type of the argument values.
	 * @param <T2> Type of the result values.
	 * @param <C> Type of the collection to populate.
	 * @param <E> Type of the exceptions.
	 * @param function Function to use.
	 * @param values Iterator providing the argument values.
	 * @param results The collection to populate with the results.
	 * @return The given result collection.
	 * @throws E When some function evaluation fails.
	 */
	public static <T1, T2, C extends Collection<? super T2>, E extends Exception> C map(final Function<? super T1, ? extends Maybe<? extends T2>, E> function, final Iterator<? extends T1> values, final C results)
	throws E {
		assert null != function;
		assert null != values;
		assert null != results;
		
		// Map.
		while (values.hasNext()) {
			final Maybe<? extends T2> result = function.evaluate(values.next());
			if (result.isSome()) {
				results.add(result.asSome().getValue());
			}
		}
		return results;
	}
	
	/**
	 * Apply the given function to the given bindings and populate the given result map by associating the results to the keys of the corresponding argument
	 * bindings.
	 * <p>
	 * This method evaluates the function by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V1> Type of the argument values.
	 * @param <V2> Type of the result values.
	 * @param <M> Type of the map to populate.
	 * @param <E> Type of the exceptions.
	 * @param function Function to use.
	 * @param bindings Argument bindings.
	 * @param results The map to populate with the results.
	 * @return The given result map.
	 * @throws E When some function evaluation fails.
	 */
	public static <K, V1, V2, M extends Map<? super K, ? super V2>, E extends Exception> M map(final Function2<? super K, ? super V1, ? extends Maybe<? extends V2>, E> function, final Map<? extends K, ? extends V1> bindings, final M results)
	throws E {
		assert null != function;
		assert null != bindings;
		assert null != results;
		
		// Map.
		for (final Map.Entry<? extends K, ? extends V1> binding : bindings.entrySet()) {
			final K key = binding.getKey();
			final Maybe<? extends V2> result = function.evaluate(key, binding.getValue());
			if (result.isSome()) {
				results.put(key, result.asSome().getValue());
			}
		}
		return results;
	}
	
	/**
	 * Apply the given function to the given keys and populate the given result map by associating the results to their argument keys.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map to populate.
	 * @param <E> Type of the exceptions.
	 * @param function Function to use.
	 * @param keys Keys to map.
	 * @param results The map to populate with the results.
	 * @return The given result map.
	 * @throws E When some function evaluation fails.
	 */
	public static <K, V, M extends Map<? super K, ? super V>, E extends Exception> M mapKeys(final Function<? super K, ? extends Maybe<? extends V>, E> function, final Set<? extends K> keys, final M results)
	throws E {
		assert null != function;
		assert null != keys;
		assert null != results;
		
		// Map the keys.
		for (final K key : keys) {
			final Maybe<? extends V> value = function.evaluate(key);
			if (value.isSome()) {
				results.put(key, value.asSome().getValue());
			}
		}
		return results;
	}
	
	/**
	 * Apply the given function to the given values and populate the given result map by associating the argument values to their corresponding result keys.
	 * <p>
	 * When the function evaluates to the same key result for different argument values, the last value is associated to the key in the result map.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map to populate.
	 * @param <E> Type of the exceptions.
	 * @param function Function to use.
	 * @param values Values to map.
	 * @param results The map to populate with the results.
	 * @return The given result map.
	 * @throws E When some function evaluation fails.
	 */
	public static <K, V, M extends Map<? super K, ? super V>, E extends Exception> M mapValues(final Function<? super V, ? extends Maybe<? extends K>, E> function, final Collection<? extends V> values, final M results)
	throws E {
		assert null != function;
		assert null != values;
		assert null != results;
		
		// Map the values.
		for (final V value : values) {
			final Maybe<? extends K> key = function.evaluate(value);
			if (key.isSome()) {
				results.put(key.asSome().getValue(), value);
			}
		}
		return results;
	}
	
	/**
	 * Apply the given function to the keys of the given bindings and populate the given result map by associating the values associated to the argument keys to
	 * the corresponding result keys.
	 * 
	 * @param <K1> Type of the argument keys.
	 * @param <K2> Type of the result keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map to populate.
	 * @param <E> Type of the exceptions.
	 * @param function Function to use.
	 * @param bindings Map to remap.
	 * @param results The map to populate with the results.
	 * @return The given result map.
	 * @throws E When some function evaluation fails.
	 */
	public static <K1, K2, V, M extends Map<? super K2, ? super V>, E extends Exception> M remap(final Function<? super K1, ? extends Maybe<? extends K2>, E> function, final Map<? extends K1, ? extends V> bindings, final M results)
	throws E {
		assert null != function;
		assert null != bindings;
		assert null != results;
		
		// Remap.
		for (final Map.Entry<? extends K1, ? extends V> entry : bindings.entrySet()) {
			final Maybe<? extends K2> newKey = function.evaluate(entry.getKey());
			if (newKey.isSome()) {
				results.put(newKey.asSome().getValue(), entry.getValue());
			}
		}
		return results;
	}
	
	/**
	 * Apply the given function to the keys of the given bindings and populate the given result map by associating the values associated to the argument keys to
	 * the corresponding result keys.
	 * <p>
	 * This method evaluates the function by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K1> Type of the argument keys.
	 * @param <K2> Type of the result keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map to populate.
	 * @param <E> Type of the exceptions.
	 * @param function Function to use.
	 * @param bindings Argument to remap.
	 * @param results The map to populate with the results.
	 * @return The given result map.
	 * @throws E When some function evaluation fails.
	 */
	public static <K1, K2, V, M extends Map<? super K2, ? super V>, E extends Exception> M remap(final Function2<? super K1, ? super V, ? extends Maybe<? extends K2>, E> function, final Map<? extends K1, ? extends V> bindings, final M results)
	throws E {
		assert null != function;
		assert null != bindings;
		assert null != results;
		
		// Remap.
		for (final Map.Entry<? extends K1, ? extends V> entry : bindings.entrySet()) {
			final V value = entry.getValue();
			final Maybe<? extends K2> newKey = function.evaluate(entry.getKey(), value);
			if (newKey.isSome()) {
				results.put(newKey.asSome().getValue(), value);
			}
		}
		return results;
	}
	
	/**
	 * Execute the given procedure with the given values.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <E> Type of the exceptions.
	 * @param procedure Procedure to execute.
	 * @param values Argument values.
	 * @throws E When some procedure execution fails.
	 */
	public static <T, E extends Exception> void execute(final Procedure<? super T, E> procedure, final Collection<? extends T> values)
	throws E {
		assert null != procedure;
		assert null != values;
		
		// Apply.
		for (final T value : values) {
			procedure.execute(value);
		}
	}
	
	/**
	 * Execute the given procedure with the values provided by the given iterator.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <E> Type of the exceptions.
	 * @param procedure Procedure to execute.
	 * @param values Iterator providing the argument values.
	 * @throws E When some procedure execution fails.
	 */
	public static <T, E extends Exception> void execute(final Procedure<? super T, E> procedure, final Iterator<? extends T> values)
	throws E {
		assert null != procedure;
		assert null != values;
		
		// Apply.
		while (values.hasNext()) {
			procedure.execute(values.next());
		}
	}
	
	/**
	 * Execute the given procedure with the given bindings.
	 * <p>
	 * This method executes the procedure by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <E> Type of the exceptions.
	 * @param procedure Procedure to apply.
	 * @param bindings Argument bindings.
	 * @throws E When some procedure execution fails.
	 */
	public static <K, V, E extends Exception> void apply(final Procedure2<? super K, ? super V, E> procedure, final Map<? extends K, ? extends V> bindings)
	throws E {
		assert null != procedure;
		assert null != bindings;
		
		// Apply.
		for (final Map.Entry<? extends K, ? extends V> binding : bindings.entrySet()) {
			procedure.execute(binding.getKey(), binding.getValue());
		}
	}
	
	// DOCME
	public static <T, A, E extends Exception> A fold(final Function2<? super A, ? super T, ? extends A, E> function, final A initialAccumulator, final Collection<? extends T> values)
	throws E {
		assert null != function;
		assert null != values;
		
		// Fold.
		final MutableReference<A> accumulator = new MutableReference<A>(initialAccumulator);
		for (final T value : values) {
			accumulator.set(function.evaluate(accumulator.get(), value), true);
		}
		return accumulator.get();
	}
	
	// DOCME
	public static <T, A, E extends Exception> A fold(final Function2<? super A, ? super T, ? extends A, E> function, final A initialAccumulator, final Iterator<? extends T> iterator)
	throws E {
		assert null != function;
		assert null != iterator;
		
		// Fold.
		final MutableReference<A> accumulator = new MutableReference<A>(initialAccumulator);
		while (iterator.hasNext()) {
			accumulator.set(function.evaluate(accumulator.get(), iterator.next()));
		}
		return accumulator.get();
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
