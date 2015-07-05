/*
 *  Copyright 2006-2015 Julien Dufour
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
import com.trazere.core.functional.Predicate2;
import com.trazere.core.imperative.IteratorUtils;
import com.trazere.core.imperative.Procedure2;
import com.trazere.core.lang.IterableUtils;
import com.trazere.core.lang.ThrowableFactory;
import com.trazere.core.util.Comparators;
import com.trazere.core.util.FieldComparators;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;

/**
 * The {@link MapUtils} class provides various utilities regarding {@link Map maps}.
 * 
 * @see Map
 * @since 1.0
 */
public class MapUtils {
	/**
	 * Gets a view of the bindings corresponding to the given map.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to read.
	 * @return The bindings.
	 * @since 1.0
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
	 * @since 1.0
	 */
	public static <K, V> Maybe<Tuple2<K, V>> any(final Map<K, V> map) {
		return IteratorUtils.next(bindings(map).iterator());
	}
	
	/**
	 * Gets the value associated to the given key in the given map.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to read.
	 * @param key Key of the binding to read.
	 * @return The associated value, or nothing when the map contains no bindings for the key.
	 * @since 1.0
	 */
	public static <K, V> Maybe<V> get(final Map<? super K, ? extends V> map, final K key) {
		final V value = map.get(key);
		return null != value || map.containsKey(key) ? Maybe.some(value) : Maybe.<V>none();
	}
	
	// TODO: rename to getOptional ?
	// TODO: add a version that uses a thunk as default value
	/**
	 * Gets the optional value associated to the given key in the given map.
	 * <p>
	 * This method returns the associated value, or the default value when the map contains no bindings for the key.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to read.
	 * @param key Key of the binding to read.
	 * @param defaultValue Default value for the missing bindings.
	 * @return The associated value, or the default value when the map contains no bindings for the key.
	 * @since 1.0
	 */
	public static <K, V> V get(final Map<? super K, ? extends V> map, final K key, final V defaultValue) {
		final V value = map.get(key);
		return null != value || map.containsKey(key) ? value : defaultValue;
	}
	
	/**
	 * Gets the mandatory value associated to the given key in the given map.
	 * <p>
	 * This method returns the associated value, or throws an exception when the map contains no bindings for the key.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to read.
	 * @param key Key of the binding to read.
	 * @param missingBindingFactory Factory of the exceptions for the missing bindings.
	 * @return The associated value.
	 * @throws RuntimeException When the map contains no bindings for the key.
	 * @since 1.0
	 */
	public static <K, V> V getMandatory(final Map<? super K, ? extends V> map, final K key, final ThrowableFactory<? extends RuntimeException> missingBindingFactory) {
		if (map.containsKey(key)) {
			return map.get(key);
		} else {
			throw missingBindingFactory.build("No binding for key \"" + key + "\"");
		}
	}
	
	/**
	 * Associates the given value to the given key in the given map.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to modify.
	 * @param key Key of the binding to set.
	 * @param value Value of the binding to set.
	 * @return The presiously associated value, or nothing when the map contained no bindings for the key.
	 * @since 1.0
	 */
	public static <K, V> Maybe<V> put(final Map<? super K, V> map, final K key, final V value) {
		final Maybe<V> currentValue = get(map, key);
		map.put(key, value);
		return currentValue;
	}
	
	/**
	 * Associates the given value to the given key in the given map, or does nothing when no value is given.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to modify.
	 * @param key Key of the binding to set.
	 * @param value Value of the binding to set.
	 * @since 1.0
	 */
	public static <K, V> void put(final Map<? super K, ? super V> map, final K key, final Maybe<? extends V> value) {
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
	 * @since 1.0
	 */
	@SafeVarargs
	public static <K, V> void putAll(final Map<? super K, ? super V> map, final Tuple2<? extends K, ? extends V>... bindings) {
		for (final Tuple2<? extends K, ? extends V> binding : bindings) {
			map.put(binding.get1(), binding.get2());
		}
	}
	
	/**
	 * Puts all given bindings into the given map.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to modify.
	 * @param bindings Bindings to put in the map.
	 * @since 1.0
	 */
	public static <K, V> void putAll(final Map<? super K, ? super V> map, final Iterable<? extends Tuple2<? extends K, ? extends V>> bindings) {
		for (final Tuple2<? extends K, ? extends V> binding : bindings) {
			map.put(binding.get1(), binding.get2());
		}
	}
	
	/**
	 * Removes the binding for the given key from the given map.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to modify.
	 * @param key Key of the binding to remove.
	 * @return The associated removed value, or nothing when the map contains no bindings for the key.
	 * @since 1.0
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
	 * @since 1.0
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
	
	// TODO: removeAll
	
	/**
	 * Retains the bindings in the given map according to the given filter.
	 * <p>
	 * This method does modify the given map.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map.
	 * @param map Map to filter.
	 * @param filter Predicate to use to filter the bindings to retain.
	 * @return The given filtered map.
	 * @since 1.0
	 */
	public static <K, V, M extends Map<? extends K, ? extends V>> M retain(final M map, final Predicate2<? super K, ? super V> filter) {
		final Iterator<? extends Map.Entry<? extends K, ? extends V>> entries = map.entrySet().iterator();
		while (entries.hasNext()) {
			final Map.Entry<? extends K, ? extends V> entry = entries.next();
			if (!filter.evaluate(entry.getKey(), entry.getValue())) {
				entries.remove();
			}
		}
		return map;
	}
	
	/**
	 * Executes the given procedure with each binding of the given map.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map containing the bindings.
	 * @param procedure Procedure to execute.
	 * @since 1.0
	 */
	public static <K, V> void foreach(final Map<? extends K, ? extends V> map, final Procedure2<? super K, ? super V> procedure) {
		IteratorUtils.foreach(bindings(map).iterator(), procedure);
	}
	
	/**
	 * Left folds over the bindings of the given map using the given operator and initial state.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <S> Type of the state.
	 * @param map Map containing the bindings to fold over.
	 * @param operator Operator to use.
	 * @param initialState Initial state.
	 * @return The folded state.
	 * @since 1.0
	 */
	public static <K, V, S> S fold(final Map<? extends K, ? extends V> map, final Function3<? super S, ? super K, ? super V, ? extends S> operator, final S initialState) {
		return IteratorUtils.fold(bindings(map).iterator(), operator, initialState);
	}
	
	/**
	 * Gets the first binding of the given map accepted by the given filter.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map containing the bindings to filter.
	 * @param filter Predicate to use to filter the binding.
	 * @return The first accepted binding, or when no binding is accepted.
	 * @since 1.0
	 */
	public static <K, V> Maybe<Tuple2<K, V>> first(final Map<? extends K, ? extends V> map, final Predicate2<? super K, ? super V> filter) {
		return IteratorUtils.first(bindings(map).iterator(), filter);
	}
	
	// TODO: extractFirst
	
	/**
	 * Tests whether any binding of the given map is accepted by the given filter.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map containing the bindings to test.
	 * @param filter Predicate to use to filter the binding.
	 * @return <code>true</code> when some binding is accepted, <code>false</code> when all bindings are rejected.
	 * @since 1.0
	 */
	public static <K, V> boolean isAny(final Map<? extends K, ? extends V> map, final Predicate2<? super K, ? super V> filter) {
		return IteratorUtils.isAny(bindings(map).iterator(), filter);
	}
	
	/**
	 * Tests whether all bindings of the given map are accepted by the given filter.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map containing the bindings to test.
	 * @param filter Predicate to use to filter the binding.
	 * @return <code>true</code> when all bindings are accepted, <code>false</code> when some binding is rejected.
	 * @since 1.0
	 */
	public static <K, V> boolean areAll(final Map<? extends K, ? extends V> map, final Predicate2<? super K, ? super V> filter) {
		return IteratorUtils.areAll(bindings(map).iterator(), filter);
	}
	
	/**
	 * Counts the bindings of the given map accepted by the given filter.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map containing the bindings to count.
	 * @param filter Predicate to use to filter the binding.
	 * @return The number of accepted bindings.
	 * @since 1.0
	 */
	public static <K, V> int count(final Map<? extends K, ? extends V> map, final Predicate2<? super K, ? super V> filter) {
		return IteratorUtils.count(bindings(map).iterator(), filter);
	}
	
	/**
	 * Gets the binding with the least value of the given map.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map containing the values to compare.
	 * @return The least element.
	 * @since 1.0
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
	 * @since 1.0
	 */
	public static <K, V> Maybe<Tuple2<K, V>> least(final Map<? extends K, ? extends V> map, final Comparator<? super V> comparator) {
		return IteratorUtils.least(MapUtils.<K, V>bindings(map).iterator(), FieldComparators.field2(comparator));
	}
	
	/**
	 * Gets the binding with the greatest value of the given map.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map containing the values to compare.
	 * @return The greatest element.
	 * @since 1.0
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
	 * @since 1.0
	 */
	public static <K, V> Maybe<Tuple2<K, V>> greatest(final Map<? extends K, ? extends V> map, final Comparator<? super V> comparator) {
		return IteratorUtils.greatest(MapUtils.<K, V>bindings(map).iterator(), FieldComparators.field2(comparator));
	}
	
	/**
	 * Appends the given maps together.
	 * <p>
	 * The binding of the first map have precedence over the bindings of the second map in case of conflict.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map.
	 * @param map1 First map containing the bindings to append.
	 * @param map2 Second map containing the bindings to append.
	 * @param resultFactory Factory of the result map.
	 * @return A map containing the appended bindings.
	 * @since 1.0
	 */
	public static <K, V, M extends Map<? super K, ? super V>> M append(final Map<? extends K, ? extends V> map1, final Map<? extends K, ? extends V> map2, final MapFactory<? super K, ? super V, M> resultFactory) {
		final M results = resultFactory.build(map1.size() + map2.size());
		results.putAll(map2);
		results.putAll(map1);
		return results;
	}
	
	/**
	 * Flattens the bindings of the maps contained in the given map.
	 * <p>
	 * The keys of the outer and inner maps are combined.
	 *
	 * @param <K1> Type of the keys of the outer map.
	 * @param <K2> Type of the keys or the inner maps.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map.
	 * @param map Map containing the maps containing the bindings to flatten.
	 * @param resultFactory Factory of the result map.
	 * @return A map containing the flatten bindings.
	 * @since 1.0
	 */
	public static <K1, K2, V, M extends Map<? super Tuple2<K1, K2>, ? super V>> M flatten(final Map<? extends K1, ? extends Map<? extends K2, ? extends V>> map, final MapFactory<? super Tuple2<K1, K2>, ? super V, M> resultFactory) {
		final M results = resultFactory.build();
		for (final Map.Entry<? extends K1, ? extends Map<? extends K2, ? extends V>> outerEntry : map.entrySet()) {
			final K1 outerKey = outerEntry.getKey();
			for (final Map.Entry<? extends K2, ? extends V> innerEntry : outerEntry.getValue().entrySet()) {
				results.put(new Tuple2<>(outerKey, innerEntry.getKey()), innerEntry.getValue());
			}
		}
		return results;
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
	 * @since 1.0
	 */
	public static <K, V, M extends Map<? super K, ? super V>> M take(final Map<? extends K, ? extends V> map, final int n, final MapFactory<? super K, ? super V, M> resultFactory) {
		return IteratorUtils.drain(IteratorUtils.take(bindings(map).iterator(), n), resultFactory.build(n));
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
	 * @since 1.0
	 */
	public static <K, V, M extends Map<? super K, ? super V>> M drop(final Map<? extends K, ? extends V> map, final int n, final MapFactory<? super K, ? super V, M> resultFactory) {
		return IteratorUtils.drain(IteratorUtils.drop(bindings(map).iterator(), n), resultFactory.build(Math.max(0, map.size() - n)));
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
	 * @since 1.0
	 */
	public static <K, V, M extends Map<? super K, ? super V>> M filter(final Map<? extends K, ? extends V> map, final Predicate2<? super K, ? super V> filter, final MapFactory<? super K, ? super V, M> resultFactory) {
		//		return IteratorUtils.drain(IteratorUtils.filter(bindings(map).iterator(), filter), resultFactory.build());
		
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
	 * @since 1.0
	 */
	public static <K, V, TV, M extends Map<? super K, ? super TV>> M map(final Map<? extends K, ? extends V> map, final Function2<? super K, ? super V, ? extends TV> function, final MapFactory<? super K, ? super TV, M> resultFactory) {
		//		return IteratorUtils.drain(IteratorUtils.map(bindings(map).iterator(), (k, v) -> new Tuple2<K, TV>(k, function.evaluate(k, v))), resultFactory.build(map.size()));
		
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
	 * @since 1.0
	 */
	public static <K, V, EV, M extends Map<? super K, ? super EV>> M extract(final Map<? extends K, ? extends V> map, final Function2<? super K, ? super V, ? extends Maybe<? extends EV>> extractor, final MapFactory<? super K, ? super EV, M> resultFactory) {
		//		return IteratorUtils.drain(IteratorUtils.extract(bindings(map).iterator(), (k, v) -> extractor.evaluate(k, v).map(ev -> new Tuple2<K, EV>(k, ev))), resultFactory.build());
		
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
	
	// TODO: add unmodifiable
	
	private MapUtils() {
		// Prevent instantiation.
	}
}
