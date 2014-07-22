/*
 *  Copyright 2006-2013 Julien Dufour
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
package com.trazere.core.collection;

import com.trazere.core.functional.Function;
import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Function3;
import com.trazere.core.functional.FunctionAccumulators;
import com.trazere.core.functional.Predicate2;
import com.trazere.core.imperative.Accumulator2;
import com.trazere.core.imperative.IntCounter;
import com.trazere.core.imperative.IteratorUtils;
import com.trazere.core.imperative.Procedure2;
import com.trazere.core.lang.IterableUtils;
import com.trazere.core.lang.ThrowableFactory;
import com.trazere.core.util.Comparators;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import com.trazere.core.util.TupleComparators;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * The {@link MapUtils} class provides various utilities regarding {@link Map maps}.
 * 
 * @see Map
 */
public class MapUtils {
	/**
	 * Gets a view of the bindings corresponding to the given map.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to read.
	 * @return The bindings.
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> Iterable<Tuple2<K, V>> bindings(final Map<? extends K, ? extends V> map) {
		return IterableUtils.map(map.entrySet(), (Function<Map.Entry<? extends K, ? extends V>, Tuple2<K, V>>) BINDING);
	}
	
	private static final Function<? extends Map.Entry<?, ?>, ? extends Tuple2<?, ?>> BINDING = entry -> new Tuple2<>(entry.getKey(), entry.getValue());
	
	/**
	 * Gets a binding from the given map.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to read.
	 * @return A binding of the map, or nothing when the map is empty.
	 */
	public static <K, V> Maybe<Tuple2<K, V>> any(final Map<K, V> map) {
		final Iterator<Entry<K, V>> entries = map.entrySet().iterator();
		if (entries.hasNext()) {
			final Entry<K, V> entry = entries.next();
			return Maybe.some(new Tuple2<>(entry.getKey(), entry.getValue()));
		} else {
			return Maybe.none();
		}
	}
	
	/**
	 * Gets the value associated to the given key in the given map.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to read.
	 * @param key Key whose associated value should be got.
	 * @return The associated value, or nothing when no value is associated to the key in the map.
	 */
	public static <K, V> Maybe<V> get(final Map<? super K, ? extends V> map, final K key) {
		final V value = map.get(key);
		return null != value || map.containsKey(key) ? Maybe.some(value) : Maybe.<V>none();
	}
	
	/**
	 * Gets the optional value associated to the given key in the given map.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to read.
	 * @param key Key whose associated value should be got.
	 * @param defaultValue Default value.
	 * @return The associated value, or the default value when no value is associated to the key in the map.
	 */
	public static <K, V> V get(final Map<? super K, ? extends V> map, final K key, final V defaultValue) {
		final V value = map.get(key);
		return null != value || map.containsKey(key) ? value : defaultValue;
	}
	
	/**
	 * Gets the mandatory value associated to the given key in the given map.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param map Map to read.
	 * @param key Key whose associated value should be got.
	 * @param throwableFactory Throwable factory to use.
	 * @return The associated value.
	 * @throws X When no value is associated to the given key in the map.
	 */
	public static <K, V, X extends Exception> V getMandatory(final Map<? super K, ? extends V> map, final K key, final ThrowableFactory<X> throwableFactory)
	throws X {
		if (map.containsKey(key)) {
			return map.get(key);
		} else {
			throw throwableFactory.build("No value for key \"" + key + "\"");
		}
	}
	
	/**
	 * Associates the given value to the given key in the given map.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to modify.
	 * @param key Key which the value should be associated to.
	 * @param value Value to associate to the key.
	 * @return The presiously associated value, or nothing when no value was associated to the key.
	 */
	public static <K, V> Maybe<V> put(final Map<? super K, V> map, final K key, final V value) {
		final Maybe<V> currentValue = get(map, key);
		map.put(key, value);
		return currentValue;
	}
	
	/**
	 * Associates the given value to the given key in the given map, or do nothing when no value is given.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to modify.
	 * @param key Key which the value should be associated to.
	 * @param value Value to associate to the key.
	 */
	public static <K, V> void put(final Map<? super K, V> map, final K key, final Maybe<? extends V> value) {
		if (value.isSome()) {
			map.put(key, value.asSome().getValue());
		}
	}
	
	/**
	 * Puts all given bindings into the given map.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to modify.
	 * @param bindings Bindings to put in the map.
	 */
	public static <K, V> void putAll(final Map<? super K, ? super V> map, final Iterable<? extends Tuple2<? extends K, ? extends V>> bindings) {
		for (final Tuple2<? extends K, ? extends V> binding : bindings) {
			map.put(binding.get1(), binding.get2());
		}
	}
	
	/**
	 * Removes the value associated to the given key from the given map.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to modify.
	 * @param key Key whose associated value should be removed.
	 * @return The removed value, or nothing when no value is associated to the key in the map.
	 */
	public static <K, V> Maybe<V> remove(final Map<? super K, ? extends V> map, final K key) {
		if (map.containsKey(key)) {
			return Maybe.some(map.remove(key));
		} else {
			return Maybe.none();
		}
	}
	
	/**
	 * Removes a binding from the given map.
	 * <p>
	 * The map must support removal through its iterators.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to modify.
	 * @return The removed binding, or nothing when the map is empty.
	 */
	public static <K, V> Maybe<Tuple2<K, V>> removeAny(final Map<? extends K, ? extends V> map) {
		final Iterator<? extends Map.Entry<? extends K, ? extends V>> iterator = map.entrySet().iterator();
		if (iterator.hasNext()) {
			final Map.Entry<? extends K, ? extends V> entry = iterator.next();
			iterator.remove();
			return Maybe.some(new Tuple2<>(entry.getKey(), entry.getValue()));
		} else {
			return Maybe.none();
		}
	}
	
	/**
	 * Populates the given accumulator with copies of the bindings of the given map.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <A> Type of the accumulator to populate.
	 * @param map Map to read.
	 * @param results Accumulator to populate with the bindings.
	 * @return The given result accumulator.
	 */
	public static <K, V, A extends Accumulator2<? super K, ? super V, ?>> A copy(final Map<? extends K, ? extends V> map, final A results) {
		for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
			results.add(entry.getKey(), entry.getValue());
		}
		return results;
	}
	
	/**
	 * Adds copies of the bindings of the given map to the given collection.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <C> Type of the collection to populate.
	 * @param map Map to read.
	 * @param results Collection to populate with the bindings.
	 * @return The given result collection.
	 */
	public static <K, V, C extends Collection<? super Tuple2<? extends K, ? extends V>>> C copy(final Map<? extends K, ? extends V> map, final C results) {
		return copy(map, CollectionAccumulators.add2(results)).get();
	}
	
	/**
	 * Executes the given procedure with each binding of the given map.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map containing the bindings.
	 * @param procedure Procedure to execute.
	 */
	public static <K, V> void foreach(final Map<? extends K, ? extends V> map, final Procedure2<? super K, ? super V> procedure) {
		for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
			procedure.execute(entry.getKey(), entry.getValue());
		}
	}
	
	/**
	 * Left folds over the bindings of the given map using the given operator and initial state.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <S> Type of the state.
	 * @param map Collection containing the bindings to fold over.
	 * @param operator Operator to use.
	 * @param initialState Initial state.
	 * @return The folded state.
	 */
	public static <K, V, S> S fold(final Map<? extends K, ? extends V> map, final Function3<? super S, ? super K, ? super V, ? extends S> operator, final S initialState) {
		return copy(map, FunctionAccumulators.fold2(operator, initialState)).get();
	}
	
	/**
	 * Gets the first binding of the given map accepted by the given filter.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map containing the bindings to filter.
	 * @param filter Predicate to use to filter the binding.
	 * @return The first accepted binding, or when no binding is accepted.
	 */
	public static <K, V> Maybe<Tuple2<K, V>> first(final Map<? extends K, ? extends V> map, final Predicate2<? super K, ? super V> filter) {
		for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
			final K key = entry.getKey();
			final V value = entry.getValue();
			if (filter.evaluate(key, value)) {
				return Maybe.some(new Tuple2<>(key, value));
			}
		}
		return Maybe.none();
	}
	
	/**
	 * Tests whether any binding of the given map is accepted by the given filter.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map containing the bindings to test.
	 * @param filter Predicate to use to filter the binding.
	 * @return <code>true</code> when some binding is accepted, <code>false</code> when all bindings are rejected.
	 */
	public static <K, V> boolean isAny(final Map<? extends K, ? extends V> map, final Predicate2<? super K, ? super V> filter) {
		for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
			if (filter.evaluate(entry.getKey(), entry.getValue())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Tests whether all bindings of the given map are accepted by the given filter.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map containing the bindings to test.
	 * @param filter Predicate to use to filter the binding.
	 * @return <code>true</code> when all bindings are accepted, <code>false</code> when some binding is rejected.
	 */
	public static <K, V> boolean areAll(final Map<? extends K, ? extends V> map, final Predicate2<? super K, ? super V> filter) {
		for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
			if (!filter.evaluate(entry.getKey(), entry.getValue())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Counts the bindings of the given map accepted by the given filter.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map containing the bindings to count.
	 * @param filter Predicate to use to filter the binding.
	 * @return The number of accepted bindings.
	 */
	public static <K, V> int count(final Map<? extends K, ? extends V> map, final Predicate2<? super K, ? super V> filter) {
		final IntCounter counter = new IntCounter();
		for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
			if (filter.evaluate(entry.getKey(), entry.getValue())) {
				counter.inc();
			}
		}
		return counter.get();
	}
	
	/**
	 * Gets the binding with the least value of the given map.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map containing the values to compare.
	 * @return The least element.
	 */
	public static <K, V extends Comparable<V>> Maybe<Tuple2<K, V>> least(final Map<? extends K, ? extends V> map) {
		return least(map, Comparators.<V>natural());
	}
	
	/**
	 * Gets the binding with the least value of the given map according to the given comparator.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map containing the values to compare.
	 * @param comparator Comparator to use.
	 * @return The least element.
	 */
	public static <K, V> Maybe<Tuple2<K, V>> least(final Map<? extends K, ? extends V> map, final Comparator<? super V> comparator) {
		return IteratorUtils.least(MapUtils.<K, V>bindings(map).iterator(), TupleComparators.element2(comparator));
	}
	
	/**
	 * Gets the binding with the greatest value of the given map.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map containing the values to compare.
	 * @return The greatest element.
	 */
	public static <K, V extends Comparable<V>> Maybe<Tuple2<K, V>> greatest(final Map<? extends K, ? extends V> map) {
		return greatest(map, Comparators.<V>natural());
	}
	
	/**
	 * Gets the binding with the greatest value of the given map according to the given comparator.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map containing the values to compare.
	 * @param comparator Comparator to use.
	 * @return The greatest element.
	 */
	public static <K, V> Maybe<Tuple2<K, V>> greatest(final Map<? extends K, ? extends V> map, final Comparator<? super V> comparator) {
		return IteratorUtils.greatest(MapUtils.<K, V>bindings(map).iterator(), TupleComparators.element2(comparator));
	}
	
	/**
	 * Takes the n first bindings of the given map.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map.
	 * @param map Map containing the bindings to take.
	 * @param n Number of bindings to take.
	 * @param resultFactory Factory of the result map.
	 * @return A map containing the taken bindings.
	 */
	public static <K, V, M extends Map<? super K, ? super V>> M take(final Map<? extends K, ? extends V> map, final int n, final MapFactory<K, V, M> resultFactory) {
		return IteratorUtils.drain(IteratorUtils.take(MapUtils.<K, V>bindings(map).iterator(), n), resultFactory.build(n));
	}
	
	/**
	 * Drops the n first bindings of the given map.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map.
	 * @param map Map containing the bindings to drop.
	 * @param n Number of bindings to drop.
	 * @param resultFactory Factory of the result map.
	 * @return A map containing the remaining bindings.
	 */
	public static <K, V, M extends Map<? super K, ? super V>> M drop(final Map<? extends K, ? extends V> map, final int n, final MapFactory<K, V, M> resultFactory) {
		return IteratorUtils.drain(IteratorUtils.drop(MapUtils.<K, V>bindings(map).iterator(), n), resultFactory.build(Math.max(0, map.size() - n)));
	}
	
	/**
	 * Filters the bindings of the given map using the given filter.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map.
	 * @param map Map containing the bindings to filter.
	 * @param filter Predicate to use to filter the bindings.
	 * @param resultFactory Factory of the result map.
	 * @return A map containing the filtered bindings.
	 */
	public static <K, V, M extends Map<? super K, ? super V>> M filter(final Map<? extends K, ? extends V> map, final Predicate2<? super K, ? super V> filter, final MapFactory<K, V, M> resultFactory) {
		final M result = resultFactory.build();
		for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
			final K key = entry.getKey();
			final V value = entry.getValue();
			if (filter.evaluate(key, value)) {
				result.put(key, value);
			}
		}
		return result;
	}
	
	/**
	 * Transforms the bindings of the given map using the given function.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <TV> Type of the transformed values.
	 * @param <M> Type of the result map.
	 * @param map Map containing the bindings to transform.
	 * @param function Function to use to transform the bindings.
	 * @param resultFactory Factory of the result map.
	 * @return A map containing the transformed bindings.
	 */
	public static <K, V, TV, M extends Map<? super K, ? super TV>> M map(final Map<? extends K, ? extends V> map, final Function2<? super K, ? super V, ? extends TV> function, final MapFactory<K, TV, M> resultFactory) {
		final M results = resultFactory.build(map.size());
		for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
			final K key = entry.getKey();
			results.put(key, function.evaluate(key, entry.getValue()));
		}
		return results;
	}
	
	/**
	 * Extracts the bindings of the given map using the given extractor.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <EV> Type of the extracted values.
	 * @param <M> Type of the result map.
	 * @param map Map containing the bindings to extract.
	 * @param extractor Function to use to extract the bindings.
	 * @param resultFactory Factory of the result map.
	 * @return A map containing the extracted bindings.
	 */
	public static <K, V, EV, M extends Map<? super K, ? super EV>> M extract(final Map<? extends K, ? extends V> map, final Function2<? super K, ? super V, ? extends Maybe<? extends EV>> extractor, final MapFactory<K, EV, M> resultFactory) {
		final M results = resultFactory.build(map.size());
		for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
			final K key = entry.getKey();
			final Maybe<? extends EV> extractedValue = extractor.evaluate(key, entry.getValue());
			if (extractedValue.isSome()) {
				results.put(key, extractedValue.asSome().getValue());
			}
		}
		return results;
	}
	
	// TODO: extractAll to Multimap ?
	
	private MapUtils() {
		// Prevent instantiation.
	}
}
