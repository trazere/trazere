/*
 *  Copyright 2006-2009 Julien Dufour
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

import com.trazere.util.accumulator.Accumulator;
import com.trazere.util.accumulator.Accumulators;
import com.trazere.util.collection.Multimap;
import com.trazere.util.type.Maybe;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * The {@link FunctionUtils} provides various helpers regarding predicates, functions and procedures.
 * 
 * @see Predicate1
 * @see Predicate2
 * @see Function1
 * @see Function2
 * @see Procedure1
 * @see Procedure2
 */
public class FunctionUtils {
	/**
	 * Filter the given values using the given predicate and populate the given result collection with them.
	 * 
	 * @param <T> Type of the values to filter.
	 * @param <C> Type of the collection to populate.
	 * @param <X> Type of the exceptions.
	 * @param predicate Predicate to use.
	 * @param values Values to filter.
	 * @param results The collection to populate with the accepted values.
	 * @return The given result collection.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <T, C extends Collection<? super T>, X extends Exception> C filter(final Predicate1<? super T, X> predicate, final Collection<T> values, final C results)
	throws X {
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
	 * Filter the content of the given collection using the given predicate.
	 * <p>
	 * This method does modify the given collection.
	 * 
	 * @param <T> Type of the elements.
	 * @param <C> Type of the collection.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @param collection The collection.
	 * @return The given modified collection.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <T, C extends Collection<T>, X extends Exception> C filter(final Predicate1<? super T, X> predicate, final C collection)
	throws X {
		assert null != predicate;
		assert null != collection;
		
		// Filter.
		final Iterator<T> values_ = collection.iterator();
		while (values_.hasNext()) {
			final T value = values_.next();
			if (!predicate.evaluate(value)) {
				values_.remove();
			}
		}
		return collection;
	}
	
	/**
	 * Filter the given bindings using the given predicate and populate the given result map with them.
	 * <p>
	 * This method evaluates the predicate by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map to populate.
	 * @param <X> Type of the exceptions.
	 * @param predicate Predicate to use.
	 * @param bindings Bindings to filter.
	 * @param results The map to populate with the accepted bindings.
	 * @return The given result map.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <K, V, M extends Map<? super K, ? super V>, X extends Exception> M filter(final Predicate2<? super K, ? super V, X> predicate, final Map<K, V> bindings, final M results)
	throws X {
		assert null != predicate;
		assert null != bindings;
		assert null != results;
		
		// Filter.
		for (final Map.Entry<K, V> entry : bindings.entrySet()) {
			final K key = entry.getKey();
			final V value = entry.getValue();
			if (predicate.evaluate(key, value)) {
				results.put(key, value);
			}
		}
		return results;
	}
	
	/**
	 * Filter the content of the given map using the given predicate.
	 * <p>
	 * This method does modify the given map.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @param map The map.
	 * @return The given modified map.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <K, V, M extends Map<K, V>, X extends Exception> M filter(final Predicate2<? super K, ? super V, X> predicate, final M map)
	throws X {
		assert null != predicate;
		assert null != map;
		
		// Filter.
		final Iterator<Map.Entry<K, V>> entries = map.entrySet().iterator();
		while (entries.hasNext()) {
			final Map.Entry<K, V> entry = entries.next();
			if (!predicate.evaluate(entry.getKey(), entry.getValue())) {
				entries.remove();
			}
		}
		return map;
	}
	
	/**
	 * Filter the given bindings using the given predicate and populate the given result set with their keys.
	 * <p>
	 * This method evaluates the predicate by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <S> Type of the set to populate.
	 * @param <X> Type of the exceptions.
	 * @param predicate Predicate to use.
	 * @param bindings Bindings to filter.
	 * @param results The set to populate with the keys of the accepted bindings.
	 * @return The given result set.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <K, V, S extends Set<? super K>, X extends Exception> S filterKeys(final Predicate2<? super K, ? super V, X> predicate, final Map<K, V> bindings, final S results)
	throws X {
		assert null != predicate;
		assert null != bindings;
		assert null != results;
		
		// Filter.
		for (final Map.Entry<K, V> entry : bindings.entrySet()) {
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
	 * @param <X> Type of the exceptions.
	 * @param predicate Predicate to use.
	 * @param bindings Bindings to filter.
	 * @param results The collection to populate with the results.
	 * @return The given result collection.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <K, V, C extends Collection<? super V>, X extends Exception> C filterValues(final Predicate2<? super K, ? super V, X> predicate, final Map<K, V> bindings, final C results)
	throws X {
		assert null != predicate;
		assert null != bindings;
		assert null != results;
		
		// Filter.
		for (final Map.Entry<K, V> entry : bindings.entrySet()) {
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
	 * @param <X> Type of the exceptions.
	 * @param predicate Predicate to use.
	 * @param values Values to count.
	 * @return The number of accepted values.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <T, X extends Exception> int count(final Predicate1<? super T, X> predicate, final Collection<T> values)
	throws X {
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
	 * @param <X> Type of the exceptions.
	 * @param predicate Predicate to use.
	 * @param values Iterator providing the values to count.
	 * @return The number of accepted values.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <T, X extends Exception> int count(final Predicate1<? super T, X> predicate, final Iterator<T> values)
	throws X {
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
	 * @param <X> Type of the exceptions.
	 * @param predicate Predicate to use.
	 * @param bindings Bindings to count.
	 * @return The number of accepted bindings.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <K, V, X extends Exception> int count(final Predicate2<? super K, ? super V, X> predicate, final Map<K, V> bindings)
	throws X {
		assert null != predicate;
		assert null != bindings;
		
		// Count.
		int count = 0;
		for (final Map.Entry<K, V> binding : bindings.entrySet()) {
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
	 * @param <X> Type of the exceptions.
	 * @param predicate Predicate to use.
	 * @param values Values to test.
	 * @return <code>true</code> if any value is accepted, <code>false</code> if all values are rejected.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <T, X extends Exception> boolean isAny(final Predicate1<? super T, X> predicate, final Collection<T> values)
	throws X {
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
	 * @param <X> Type of the exceptions.
	 * @param predicate Predicate to use.
	 * @param values Iterator providing the values to test.
	 * @return <code>true</code> if any value is accepted, <code>false</code> if all values are rejected.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <T, X extends Exception> boolean isAny(final Predicate1<? super T, X> predicate, final Iterator<T> values)
	throws X {
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
	 * @param <X> Type of the exceptions.
	 * @param predicate Predicate to use.
	 * @param bindings Bindings to test.
	 * @return <code>true</code> if any binding is accepted, <code>false</code> if all bindings are rejected.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <K, V, X extends Exception> boolean isAny(final Predicate2<? super K, ? super V, X> predicate, final Map<K, V> bindings)
	throws X {
		assert null != predicate;
		assert null != bindings;
		
		// Test.
		for (final Map.Entry<K, V> binding : bindings.entrySet()) {
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
	 * @param <X> Type of the exceptions.
	 * @param predicate Predicate to use.
	 * @param values Values to test.
	 * @return <code>true</code> if all values are accepted, <code>false</code> if any value is rejected.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <T, X extends Exception> boolean areAll(final Predicate1<? super T, X> predicate, final Collection<T> values)
	throws X {
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
	 * @param <X> Type of the exceptions.
	 * @param predicate Predicate to use.
	 * @param values Iterator providing the values to test.
	 * @return <code>true</code> if all values are accepted, <code>false</code> if any value is rejected.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <T, X extends Exception> boolean areAll(final Predicate1<? super T, X> predicate, final Iterator<T> values)
	throws X {
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
	 * @param <X> Type of the exceptions.
	 * @param predicate Predicate to use.
	 * @param bindings Bindings to test.
	 * @return <code>true</code> if all bindings are accepted, <code>false</code> if any binding is rejected.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <K, V, X extends Exception> boolean areAll(final Predicate2<? super K, ? super V, X> predicate, final Map<K, V> bindings)
	throws X {
		assert null != predicate;
		assert null != bindings;
		
		// Test.
		for (final Map.Entry<K, V> binding : bindings.entrySet()) {
			if (!predicate.evaluate(binding.getKey(), binding.getValue())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Transform the given values using the given function and populate the given collection with the result values.
	 * 
	 * @param <T1> Type of the argument values.
	 * @param <T2> Type of the result values.
	 * @param <C> Type of the collection to populate with the result values.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @param values The argument values.
	 * @param results The collection to populate with the result values.
	 * @return The given result collection.
	 * @throws X When some function evaluation fails.
	 */
	public static <T1, T2, C extends Collection<? super T2>, X extends Exception> C map(final Function1<? super T1, T2, X> function, final Collection<T1> values, final C results)
	throws X {
		assert null != function;
		assert null != values;
		assert null != results;
		
		// Map.
		for (final T1 value : values) {
			results.add(function.evaluate(value));
		}
		return results;
	}
	
	/**
	 * Transform the values provided by the given iterator using the given function and populate the given collection with the result values.
	 * 
	 * @param <T1> Type of the argument values.
	 * @param <T2> Type of the result values.
	 * @param <C> Type of the collection to populate with the result values.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @param values The iterator providing the argument values.
	 * @param results The collection to populate with the result values.
	 * @return The given result collection.
	 * @throws X When some function evaluation fails.
	 */
	public static <T1, T2, C extends Collection<? super T2>, X extends Exception> C map(final Function1<? super T1, T2, X> function, final Iterator<T1> values, final C results)
	throws X {
		assert null != function;
		assert null != values;
		assert null != results;
		
		// Map.
		while (values.hasNext()) {
			results.add(function.evaluate(values.next()));
		}
		return results;
	}
	
	/**
	 * Transform the given bindings using the given function and populate the given map with the bindings of the argument keys and the corresponding result
	 * values.
	 * <p>
	 * This method evaluates the function by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V1> Type of the argument values.
	 * @param <V2> Type of the result values.
	 * @param <M> Type of the map to populate with the result bindings.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @param bindings The argument bindings.
	 * @param results The map to populate with the result bindings.
	 * @return The given result map.
	 * @throws X When some function evaluation fails.
	 */
	public static <K, V1, V2, M extends Map<? super K, ? super V2>, X extends Exception> M map(final Function2<? super K, ? super V1, V2, X> function, final Map<K, V1> bindings, final M results)
	throws X {
		assert null != function;
		assert null != bindings;
		assert null != results;
		
		// Map.
		for (final Map.Entry<K, V1> binding : bindings.entrySet()) {
			final K key = binding.getKey();
			results.put(key, function.evaluate(key, binding.getValue()));
		}
		return results;
	}
	
	/**
	 * Transform the given bindings using the given function and populate the given multi-map with the bindings of the argument keys and the corresponding
	 * result values.
	 * <p>
	 * This method evaluates the function by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V1> Type of the argument values.
	 * @param <V2> Type of the result values.
	 * @param <M> Type of the map to populate with the result bindings.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @param bindings The argument bindings.
	 * @param results The map to populate with the result bindings.
	 * @return The given result map.
	 * @throws X When some function evaluation fails.
	 */
	public static <K, V1, V2, M extends Multimap<? super K, ? super V2, ?>, X extends Exception> M map(final Function2<? super K, ? super V1, V2, X> function, final Multimap<K, V1, ?> bindings, final M results)
	throws X {
		assert null != function;
		assert null != bindings;
		assert null != results;
		
		// Map.
		for (final K key : bindings.keySet()) {
			for (final V1 value : bindings.get(key)) {
				results.put(key, function.evaluate(key, value));
			}
		}
		return results;
	}
	
	/**
	 * Transform the given keys using the given function and populate the given map with the bindings of the keys and the result values.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map to populate with the result bindings.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @param keys The argument keys.
	 * @param results The map to populate with the result bindings.
	 * @return The given result map.
	 * @throws X When some function evaluation fails.
	 */
	public static <K, V, M extends Map<? super K, ? super V>, X extends Exception> M mapKeys(final Function1<? super K, V, X> function, final Set<K> keys, final M results)
	throws X {
		assert null != function;
		assert null != keys;
		assert null != results;
		
		// Map the keys.
		for (final K key : keys) {
			results.put(key, function.evaluate(key));
		}
		return results;
	}
	
	/**
	 * Transform the given values using the given function and populate the given map with the bindings of the result keys and the argument values.
	 * <p>
	 * When the function evaluates to the same result key for different argument values, the last value is associated to the key in the result map.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map to populate with the result bindings.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @param values The argument values.
	 * @param results The map to populate with the result bindings.
	 * @return The given result map.
	 * @throws X When some function evaluation fails.
	 */
	public static <K, V, M extends Map<? super K, ? super V>, X extends Exception> M mapValues(final Function1<? super V, K, X> function, final Collection<V> values, final M results)
	throws X {
		assert null != function;
		assert null != values;
		assert null != results;
		
		// Map the values.
		for (final V value : values) {
			results.put(function.evaluate(value), value);
		}
		return results;
	}
	
	/**
	 * Filter and transform the given values using the given function and populate the given collection with the result values.
	 * 
	 * @param <T1> Type of the argument values.
	 * @param <T2> Type of the result values.
	 * @param <C> Type of the collection to populate with the result values.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @param values The argument values.
	 * @param results The collection to populate with the result values.
	 * @return The given result collection.
	 * @throws X When some function evaluation fails.
	 */
	public static <T1, T2, C extends Collection<? super T2>, X extends Exception> C mapFilter(final Function1<? super T1, ? extends Maybe<T2>, X> function, final Collection<T1> values, final C results)
	throws X {
		assert null != function;
		assert null != values;
		assert null != results;
		
		// Map.
		for (final T1 value : values) {
			final Maybe<T2> result = function.evaluate(value);
			if (result.isSome()) {
				results.add(result.asSome().getValue());
			}
		}
		return results;
	}
	
	/**
	 * Filter and transform the values provided by the given iterator using the given function and populate the given collection with the result values.
	 * 
	 * @param <T1> Type of the argument values.
	 * @param <T2> Type of the result values.
	 * @param <C> Type of the collection to populate with the result values.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @param values The iterator providing the argument values.
	 * @param results The collection to populate with the result values.
	 * @return The given result collection.
	 * @throws X When some function evaluation fails.
	 */
	public static <T1, T2, C extends Collection<? super T2>, X extends Exception> C mapFilter(final Function1<? super T1, ? extends Maybe<T2>, X> function, final Iterator<T1> values, final C results)
	throws X {
		assert null != function;
		assert null != values;
		assert null != results;
		
		// Map.
		while (values.hasNext()) {
			final Maybe<T2> result = function.evaluate(values.next());
			if (result.isSome()) {
				results.add(result.asSome().getValue());
			}
		}
		return results;
	}
	
	/**
	 * Filter and transform the given bindings using the given function and populate the given map with the bindings of the argument keys and the corresponding
	 * result values.
	 * <p>
	 * This method evaluates the function by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V1> Type of the argument values.
	 * @param <V2> Type of the result values.
	 * @param <M> Type of the map to populate with the result bindings.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @param bindings The argument bindings.
	 * @param results The map to populate with the result bindings.
	 * @return The given result map.
	 * @throws X When some function evaluation fails.
	 */
	public static <K, V1, V2, M extends Map<? super K, ? super V2>, X extends Exception> M mapFilter(final Function2<? super K, ? super V1, ? extends Maybe<V2>, X> function, final Map<K, V1> bindings, final M results)
	throws X {
		assert null != function;
		assert null != bindings;
		assert null != results;
		
		// Map.
		for (final Map.Entry<K, V1> binding : bindings.entrySet()) {
			final K key = binding.getKey();
			final Maybe<V2> result = function.evaluate(key, binding.getValue());
			if (result.isSome()) {
				results.put(key, result.asSome().getValue());
			}
		}
		return results;
	}
	
	/**
	 * Filter and transform the given keys using the given function and populate the given map with the bindings of the keys and the result values.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map to populate with the result bindings.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @param keys The argument keys.
	 * @param results The map to populate with the result bindings.
	 * @return The given result map.
	 * @throws X When some function evaluation fails.
	 */
	public static <K, V, M extends Map<? super K, ? super V>, X extends Exception> M mapFilterKeys(final Function1<? super K, ? extends Maybe<V>, X> function, final Set<K> keys, final M results)
	throws X {
		assert null != function;
		assert null != keys;
		assert null != results;
		
		// Map the keys.
		for (final K key : keys) {
			final Maybe<V> value = function.evaluate(key);
			if (value.isSome()) {
				results.put(key, value.asSome().getValue());
			}
		}
		return results;
	}
	
	/**
	 * Filter and transform the given values using the given function and populate the given map with the bindings of the result keys and the argument values.
	 * <p>
	 * When the function evaluates to the same result key for different argument values, the last value is associated to the key in the result map.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map to populate with the result bindings.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @param values The argument values.
	 * @param results The map to populate with the result bindings.
	 * @return The given result map.
	 * @throws X When some function evaluation fails.
	 */
	public static <K, V, M extends Map<? super K, ? super V>, X extends Exception> M mapFilterValues(final Function1<? super V, ? extends Maybe<K>, X> function, final Collection<V> values, final M results)
	throws X {
		assert null != function;
		assert null != values;
		assert null != results;
		
		// Map the values.
		for (final V value : values) {
			final Maybe<K> key = function.evaluate(value);
			if (key.isSome()) {
				results.put(key.asSome().getValue(), value);
			}
		}
		return results;
	}
	
	/**
	 * Transform the keys of the given bindings using the given function and populate the given map with the bindings of the result keys and the values
	 * associated to the arguments keys.
	 * 
	 * @param <K1> Type of the argument keys.
	 * @param <K2> Type of the result keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map to populate with the result bindings.
	 * @param <X> Type of the exceptions.
	 * @param function The function .
	 * @param bindings The argument bindings.
	 * @param results The map to populate with the result bindings.
	 * @return The given result map.
	 * @throws X When some function evaluation fails.
	 */
	public static <K1, K2, V, M extends Map<? super K2, ? super V>, X extends Exception> M remap(final Function1<? super K1, K2, X> function, final Map<K1, V> bindings, final M results)
	throws X {
		assert null != function;
		assert null != bindings;
		assert null != results;
		
		// Remap.
		for (final Map.Entry<K1, V> entry : bindings.entrySet()) {
			results.put(function.evaluate(entry.getKey()), entry.getValue());
		}
		return results;
	}
	
	/**
	 * Transform the given bindings using the given function and populate the given map with the bindings of the result keys and the argument values.
	 * <p>
	 * This method evaluates the function by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K1> Type of the argument keys.
	 * @param <K2> Type of the result keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map to populate.
	 * @param <X> Type of the exceptions.
	 * @param function Function to use.
	 * @param bindings Argument to remap.
	 * @param results The map to populate with the results.
	 * @return The given result map.
	 * @throws X When some function evaluation fails.
	 */
	public static <K1, K2, V, M extends Map<? super K2, ? super V>, X extends Exception> M remap(final Function2<? super K1, ? super V, K2, X> function, final Map<K1, V> bindings, final M results)
	throws X {
		assert null != function;
		assert null != bindings;
		assert null != results;
		
		// Remap.
		for (final Map.Entry<K1, V> entry : bindings.entrySet()) {
			final V value = entry.getValue();
			results.put(function.evaluate(entry.getKey(), value), value);
		}
		return results;
	}
	
	/**
	 * Filter and transform the keys of the given bindings using the given function and populate the given map with the bindings of the result keys and the
	 * values associated to the arguments keys.
	 * 
	 * @param <K1> Type of the argument keys.
	 * @param <K2> Type of the result keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map to populate with the result bindings.
	 * @param <X> Type of the exceptions.
	 * @param function The function .
	 * @param bindings The argument bindings.
	 * @param results The map to populate with the result bindings.
	 * @return The given result map.
	 * @throws X When some function evaluation fails.
	 */
	public static <K1, K2, V, M extends Map<? super K2, ? super V>, X extends Exception> M remapFilter(final Function1<? super K1, ? extends Maybe<K2>, X> function, final Map<K1, V> bindings, final M results)
	throws X {
		assert null != function;
		assert null != bindings;
		assert null != results;
		
		// Remap.
		for (final Map.Entry<K1, V> entry : bindings.entrySet()) {
			final Maybe<K2> newKey = function.evaluate(entry.getKey());
			if (newKey.isSome()) {
				results.put(newKey.asSome().getValue(), entry.getValue());
			}
		}
		return results;
	}
	
	/**
	 * Filter and transform the given bindings using the given function and populate the given map with the bindings of the result keys and the argument values.
	 * <p>
	 * This method evaluates the function by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K1> Type of the argument keys.
	 * @param <K2> Type of the result keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map to populate.
	 * @param <X> Type of the exceptions.
	 * @param function Function to use.
	 * @param bindings Argument to remap.
	 * @param results The map to populate with the results.
	 * @return The given result map.
	 * @throws X When some function evaluation fails.
	 */
	public static <K1, K2, V, M extends Map<? super K2, ? super V>, X extends Exception> M remapFilter(final Function2<? super K1, ? super V, ? extends Maybe<K2>, X> function, final Map<K1, V> bindings, final M results)
	throws X {
		assert null != function;
		assert null != bindings;
		assert null != results;
		
		// Remap.
		for (final Map.Entry<K1, V> entry : bindings.entrySet()) {
			final V value = entry.getValue();
			final Maybe<K2> newKey = function.evaluate(entry.getKey(), value);
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
	 * @param <X> Type of the exceptions.
	 * @param procedure Procedure to execute.
	 * @param values Argument values.
	 * @throws X When some procedure execution fails.
	 */
	public static <T, X extends Exception> void execute(final Procedure1<? super T, X> procedure, final Collection<T> values)
	throws X {
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
	 * @param <X> Type of the exceptions.
	 * @param procedure Procedure to execute.
	 * @param values Iterator providing the argument values.
	 * @throws X When some procedure execution fails.
	 */
	public static <T, X extends Exception> void execute(final Procedure1<? super T, X> procedure, final Iterator<T> values)
	throws X {
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
	 * @param <X> Type of the exceptions.
	 * @param procedure Procedure to apply.
	 * @param bindings Argument bindings.
	 * @throws X When some procedure execution fails.
	 */
	public static <K, V, X extends Exception> void apply(final Procedure2<? super K, ? super V, X> procedure, final Map<K, V> bindings)
	throws X {
		assert null != procedure;
		assert null != bindings;
		
		// Apply.
		for (final Map.Entry<K, V> binding : bindings.entrySet()) {
			procedure.execute(binding.getKey(), binding.getValue());
		}
	}
	
	/**
	 * Left fold the given collection of values using the given operator and initial argument.
	 * 
	 * @param <R> Type of the result.
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param operator The operator.
	 * @param initialAccumulator The initial argument. May be <code>null</code>.
	 * @param values The values.
	 * @return The result of the fold. May be <code>null</code>.
	 * @throws X When a operator evaluation fails.
	 */
	public static <R, V, X extends Exception> R fold(final Function2<? super R, ? super V, ? extends R, X> operator, final R initialAccumulator, final Collection<V> values)
	throws X {
		assert null != operator;
		assert null != values;
		
		// Fold.
		final Accumulator<R, V, X> accumulator = Accumulators.function(operator, initialAccumulator);
		for (final V value : values) {
			accumulator.accumulate(value);
		}
		return accumulator.get();
	}
	
	/**
	 * Left fold the given values provided by the given iterator using the given operator and initial argument.
	 * 
	 * @param <R> Type of the result.
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param operator The operator.
	 * @param initialAccumulator The initial argument. May be <code>null</code>.
	 * @param values The iterator providing the values.
	 * @return The result of the fold. May be <code>null</code>.
	 * @throws X When a operator evaluation fails.
	 */
	public static <R, V, X extends Exception> R fold(final Function2<? super R, ? super V, ? extends R, X> operator, final R initialAccumulator, final Iterator<V> values)
	throws X {
		assert null != operator;
		assert null != values;
		
		// Fold.
		final Accumulator<R, V, X> accumulator = Accumulators.function(operator, initialAccumulator);
		while (values.hasNext()) {
			accumulator.accumulate(values.next());
		}
		return accumulator.get();
	}
	
	private FunctionUtils() {
		// Prevent instantiation.
	}
}
