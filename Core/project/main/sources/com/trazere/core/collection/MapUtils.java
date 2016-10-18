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

import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Function3;
import com.trazere.core.functional.Predicate2;
import com.trazere.core.functional.Thunk;
import com.trazere.core.imperative.Accumulator;
import com.trazere.core.imperative.ExIterator;
import com.trazere.core.imperative.IteratorUtils;
import com.trazere.core.imperative.PairIterator;
import com.trazere.core.imperative.Procedure2;
import com.trazere.core.lang.IterableUtils;
import com.trazere.core.lang.LangAccumulators;
import com.trazere.core.lang.PairIterable;
import com.trazere.core.lang.ThrowableFactory;
import com.trazere.core.util.Comparators;
import com.trazere.core.util.FieldComparators;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * The {@link MapUtils} class provides various utilities regarding {@link Map maps}.
 * 
 * @see Map
 * @since 2.0
 */
public class MapUtils {
	/**
	 * Gets a view of the bindings corresponding to the given map.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to read.
	 * @return The bindings.
	 * @since 2.0
	 */
	public static <K, V> PairIterable<K, V> bindings(final Map<? extends K, ? extends V> map) {
		// HACK: not a lambda to work around a bug of Eclipse
		return new PairIterable<K, V>() {
			@Override
			public PairIterator<K, V> iterator() {
				final Iterator<? extends Map.Entry<? extends K, ? extends V>> iterator = map.entrySet().iterator();
				return new PairIterator<K, V>() {
					@Override
					public boolean hasNext() {
						return iterator.hasNext();
					}
					
					@Override
					public Tuple2<K, V> next() {
						final Map.Entry<? extends K, ? extends V> entry = iterator.next();
						return new Tuple2<>(entry.getKey(), entry.getValue());
					}
				};
			}
		};
	}
	
	/**
	 * Gets any binding from the given map.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to read.
	 * @return A binding of the map.
	 * @throws NoSuchElementException When the map is empty.
	 * @since 2.0
	 */
	public static <K, V> Tuple2<K, V> any(final Map<K, V> map)
	throws NoSuchElementException {
		return bindings(map).any();
	}
	
	/**
	 * Gets any binding from the given map.
	 * <p>
	 * This methods supports empty maps.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to read.
	 * @return A binding of the map, or nothing when the map is empty.
	 * @since 2.0
	 */
	public static <K, V> Maybe<Tuple2<K, V>> optionalAny(final Map<K, V> map) {
		return bindings(map).optionalAny();
	}
	
	/**
	 * Gets the value associated to the given key in the given map.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to read.
	 * @param key Key of the binding to read.
	 * @return The associated value, or nothing when the map contains no bindings for the key.
	 * @since 2.0
	 */
	public static <K, V> Maybe<V> optionalGet(final Map<? super K, ? extends V> map, final K key) {
		final V value = map.get(key);
		return null != value || map.containsKey(key) ? Maybe.some(value) : Maybe.none();
	}
	
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
	 * @since 2.0
	 */
	public static <K, V> V getOptional(final Map<? super K, ? extends V> map, final K key, final V defaultValue) {
		final V value = map.get(key);
		return null != value || map.containsKey(key) ? value : defaultValue;
	}
	
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
	 * @since 2.0
	 */
	public static <K, V> V getOptional(final Map<? super K, ? extends V> map, final K key, final Thunk<? extends V> defaultValue) {
		final V value = map.get(key);
		return null != value || map.containsKey(key) ? value : defaultValue.evaluate();
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
	 * @since 2.0
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
	 * @since 2.0
	 */
	public static <K, V> Maybe<V> optionalPut(final Map<? super K, V> map, final K key, final V value) {
		final Maybe<V> currentValue = optionalGet(map, key);
		map.put(key, value);
		return currentValue;
	}
	
	/**
	 * Puts all given bindings into the given map.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to modify.
	 * @param bindings Bindings to put in the map.
	 * @since 2.0
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
	 * @since 2.0
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
	 * @since 2.0
	 */
	public static <K, V> Maybe<V> optionalRemove(final Map<? super K, ? extends V> map, final K key) {
		if (map.containsKey(key)) {
			return Maybe.some(map.remove(key));
		} else {
			return Maybe.none();
		}
	}
	
	// TODO: generalize and move to IterableUtils
	/**
	 * Removes any binding from the given map.
	 * <p>
	 * The map must support removal through its iterators.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to modify.
	 * @return The removed binding, or nothing when the map is empty.
	 * @since 2.0
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
	
	// TODO: generalize and move to IterableUtils
	// TODO: removeAny(Map, Prediate<? super K>)
	
	// TODO: generalize and move to IterableUtils
	// TODO: removeAll(Map, K...)
	
	// TODO: generalize and move to IterableUtils
	// TODO: removeAll(Map, Iterable<? extends K>)
	
	// TODO: generalize and move to IterableUtils
	// TODO: removeAll(Map, Predicate<? super K>)
	
	/**
	 * Retains the bindings accepted by the given filter in the given map.
	 * <p>
	 * This method does modify the given map.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to filter.
	 * @param filter Predicate to use to filter the bindings to retain.
	 * @return <code>true</code> when the given map is modified, <code>false</code> otherwise.
	 * @since 2.0
	 */
	public static <K, V> boolean retainAll(final Map<? extends K, ? extends V> map, final Predicate2<? super K, ? super V> filter) {
		final Accumulator<Boolean, Boolean> changed = LangAccumulators.or(false);
		final Iterator<? extends Map.Entry<? extends K, ? extends V>> entries = map.entrySet().iterator();
		while (entries.hasNext()) {
			final Map.Entry<? extends K, ? extends V> entry = entries.next();
			if (!filter.evaluate(entry.getKey(), entry.getValue())) {
				entries.remove();
				changed.add(true);
			}
		}
		return changed.get().booleanValue();
	}
	
	/**
	 * Left folds over the bindings of the given map using the given operator and initial state.
	 * <p>
	 * The bindings are folded according their iteration order.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <S> Type of the state.
	 * @param map Map containing the bindings to fold over.
	 * @param operator Operator to use.
	 * @param initialState Initial state.
	 * @return The folded state.
	 * @since 2.0
	 */
	public static <K, V, S> S fold(final Map<? extends K, ? extends V> map, final Function3<? super S, ? super K, ? super V, ? extends S> operator, final S initialState) {
		return bindings(map).fold(operator, initialState);
	}
	
	// TODO: extractAny
	
	/**
	 * Tests whether any binding of the given map is accepted by the given filter.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map containing the bindings to test.
	 * @param filter Predicate to use to filter the binding.
	 * @return <code>true</code> when some binding is accepted, <code>false</code> when all bindings are rejected or when the map is empty.
	 * @since 2.0
	 */
	public static <K, V> boolean isAny(final Map<? extends K, ? extends V> map, final Predicate2<? super K, ? super V> filter) {
		return bindings(map).isAny(filter);
	}
	
	/**
	 * Tests whether all bindings of the given map are accepted by the given filter.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map containing the bindings to test.
	 * @param filter Predicate to use to filter the binding.
	 * @return <code>true</code> when all bindings are accepted or when the map is empty, <code>false</code> when some binding is rejected.
	 * @since 2.0
	 */
	public static <K, V> boolean areAll(final Map<? extends K, ? extends V> map, final Predicate2<? super K, ? super V> filter) {
		return bindings(map).areAll(filter);
	}
	
	/**
	 * Counts the bindings of the given map accepted by the given filter.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map containing the bindings to count.
	 * @param filter Predicate to use to filter the binding.
	 * @return The number of accepted bindings.
	 * @since 2.0
	 */
	public static <K, V> int count(final Map<? extends K, ? extends V> map, final Predicate2<? super K, ? super V> filter) {
		return bindings(map).count(filter);
	}
	
	/**
	 * Gets the least binding of the given map according to the given comparator.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map containing the values to compare.
	 * @param comparator Comparator to use.
	 * @return The least binding, or nothing when the map is empty.
	 * @since 2.0
	 */
	public static <K, V> Maybe<Tuple2<K, V>> least(final Map<? extends K, ? extends V> map, final Comparator<? super Tuple2<K, V>> comparator) {
		return MapUtils.<K, V>bindings(map).least(comparator);
	}
	
	/**
	 * Gets the greatest binding of the given map according to the given comparator.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map containing the values to compare.
	 * @param comparator Comparator to use.
	 * @return The greatest binding, or nothing when the map is empty.
	 * @since 2.0
	 */
	public static <K, V> Maybe<Tuple2<K, V>> greatest(final Map<? extends K, ? extends V> map, final Comparator<? super Tuple2<K, V>> comparator) {
		return MapUtils.<K, V>bindings(map).greatest(comparator);
	}
	
	/**
	 * Gets the binding with the least value of the given map.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map containing the values to compare.
	 * @return The binding with the least value, or nothing when the map is empty.
	 * @since 2.0
	 */
	public static <K, V extends Comparable<V>> Maybe<Tuple2<K, V>> leastByValue(final Map<? extends K, ? extends V> map) {
		return leastByValue(map, Comparators.<V>natural());
	}
	
	/**
	 * Gets the binding with the least value of the given map according to the given comparator.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map containing the values to compare.
	 * @param comparator Comparator to use.
	 * @return The binding with the least value, or nothing when the map is empty.
	 * @since 2.0
	 */
	public static <K, V> Maybe<Tuple2<K, V>> leastByValue(final Map<? extends K, ? extends V> map, final Comparator<? super V> comparator) {
		return least(map, FieldComparators.field2(comparator));
	}
	
	/**
	 * Gets the binding with the greatest value of the given map.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map containing the values to compare.
	 * @return The binding with the greatest value, or nothing when the map is empty.
	 * @since 2.0
	 */
	public static <K, V extends Comparable<V>> Maybe<Tuple2<K, V>> greatestByValue(final Map<? extends K, ? extends V> map) {
		return greatestByValue(map, Comparators.<V>natural());
	}
	
	/**
	 * Gets the binding with the greatest value of the given map according to the given comparator.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map containing the values to compare.
	 * @param comparator Comparator to use.
	 * @return The binding with the greatest value, or nothing when the map is empty.
	 * @since 2.0
	 */
	public static <K, V> Maybe<Tuple2<K, V>> greatestByValue(final Map<? extends K, ? extends V> map, final Comparator<? super V> comparator) {
		return greatest(map, FieldComparators.field2(comparator));
	}
	
	/**
	 * Takes n bindings of the given map.
	 * <p>
	 * The bindings are taken according their iteration order.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map containing the bindings to take.
	 * @param n Number of bindings to take.
	 * @return A map containing the taken bindings.
	 * @since 2.0
	 */
	public static <K, V> ExMap<K, V> take(final Map<K, V> map, final int n) {
		assert null != map;
		
		return new AbstractExMap<K, V>() {
			private final ExSet<Map.Entry<K, V>> _entrySet = SetUtils.take(map.entrySet(), n);
			
			@Override
			public ExSet<Map.Entry<K, V>> entrySet() {
				return _entrySet;
			}
		};
	}
	
	/**
	 * Takes n bindings of the given map.
	 * <p>
	 * The bindings are taken according their iteration order.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map.
	 * @param map Map containing the bindings to take.
	 * @param n Number of bindings to take.
	 * @param resultFactory Factory of the result map.
	 * @return A new map containing the taken bindings.
	 * @since 2.0
	 */
	public static <K, V, M extends Map<? super K, ? super V>> M take(final Map<? extends K, ? extends V> map, final int n, final MapFactory<? super K, ? super V, M> resultFactory) {
		// final ExMap<? extends K, ? extends V> bindings = take(map, n);
		// return MapUtils.<K, V>bindings(bindings).iterator().drain(resultFactory.build(bindings.size()));
		// Note: avoids building the bindings
		final M result = resultFactory.build(Math.min(map.size(), n));
		for (final Map.Entry<? extends K, ? extends V> entry : IterableUtils.take(map.entrySet(), n)) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
	
	/**
	 * Drops n bindings of the given map.
	 * <p>
	 * The bindings are dropped according their iteration order.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map containing the bindings to drop.
	 * @param n Number of bindings to drop.
	 * @return A map containing the remaining bindings.
	 * @since 2.0
	 */
	public static <K, V> ExMap<K, V> drop(final Map<K, V> map, final int n) {
		assert null != map;
		
		return new AbstractExMap<K, V>() {
			private final ExSet<Map.Entry<K, V>> _entrySet = SetUtils.drop(map.entrySet(), n);
			
			@Override
			public ExSet<Map.Entry<K, V>> entrySet() {
				return _entrySet;
			}
		};
	}
	
	/**
	 * Drops n bindings of the given map.
	 * <p>
	 * The bindings are dropped according their iteration order.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map.
	 * @param map Map containing the bindings to drop.
	 * @param n Number of bindings to drop.
	 * @param resultFactory Factory of the result map.
	 * @return A new map containing the remaining bindings.
	 * @since 2.0
	 */
	public static <K, V, M extends Map<? super K, ? super V>> M drop(final Map<? extends K, ? extends V> map, final int n, final MapFactory<? super K, ? super V, M> resultFactory) {
		// final ExMap<? extends K, ? extends V> bindings = drop(map, n);
		// return MapUtils.<K, V>bindings(bindings).iterator().drain(resultFactory.build(bindings.size()));
		// Note: avoids building the bindings
		final M result = resultFactory.build(Math.min(map.size(), n));
		for (final Map.Entry<? extends K, ? extends V> entry : IterableUtils.drop(map.entrySet(), n)) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
	
	/**
	 * Groups the bindings of the given map into batches of the given size.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <B> Type of the batch collections.
	 * @param map Map containing the bindings to group.
	 * @param n Number of bindings of each batch.
	 * @param batchFactory Factory of the batch collections.
	 * @return A collection of the batches of bindings.
	 * @since 2.0
	 */
	public static <K, V, B extends Collection<? super Tuple2<K, V>>> ExCollection<B> group(final Map<? extends K, ? extends V> map, final int n, final CollectionFactory<? super Tuple2<K, V>, B> batchFactory) {
		assert null != map;
		assert null != batchFactory;
		
		return new AbstractExCollection<B>() {
			@Override
			public int size() {
				return (map.size() + n - 1) / n;
			}
			
			@Override
			public ExIterator<B> iterator() {
				return IteratorUtils.group(MapUtils.<K, V>bindings(map).iterator(), n, batchFactory);
			}
		};
	}
	
	/**
	 * Groups the bindings of the given map into batches of the given size.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <B> Type of the batch collections.
	 * @param <C> Type of the result collection.
	 * @param map Map containing the bindings to group.
	 * @param n Number of bindings of each batch.
	 * @param batchFactory Factory of the batch collections.
	 * @param resultFactory Factory of the result collection.
	 * @return A new collection of the batches of bindings.
	 * @since 2.0
	 */
	public static <K, V, B extends Collection<? super Tuple2<K, V>>, C extends Collection<? super B>> C group(final Map<? extends K, ? extends V> map, final int n, final CollectionFactory<? super Tuple2<K, V>, B> batchFactory, final CollectionFactory<? super B, C> resultFactory) {
		final ExCollection<B> groups = group(map, n, batchFactory);
		return groups.iterator().drain(resultFactory.build(groups.size()));
	}
	
	/**
	 * Filters the bindings of the given map using the given filter.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map containing the bindings to filter.
	 * @param filter Predicate to use to filter the bindings.
	 * @return A map containing the filtered bindings.
	 * @since 2.0
	 */
	public static <K, V> ExMap<K, V> filter(final Map<K, V> map, final Predicate2<? super K, ? super V> filter) {
		assert null != map;
		assert null != filter;
		
		return new AbstractExMap<K, V>() {
			private final ExSet<Map.Entry<K, V>> _entrySet = ExSet.build(map.entrySet()).filter(e -> filter.evaluate(e.getKey(), e.getValue()));
			
			@Override
			public ExSet<Map.Entry<K, V>> entrySet() {
				return _entrySet;
			}
		};
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
	 * @return A new map containing the filtered bindings.
	 * @since 2.0
	 */
	public static <K, V, M extends Map<? super K, ? super V>> M filter(final Map<? extends K, ? extends V> map, final Predicate2<? super K, ? super V> filter, final MapFactory<? super K, ? super V, M> resultFactory) {
		// return resultFactory.build(IterableUtils.filter(bindings(map), filter));
		// Note: avoids building the bindings
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
	 * Gets any binding of the given map accepted by the given filter.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map containing the bindings to filter.
	 * @param filter Predicate to use to filter the binding.
	 * @return The accepted binding, or when no bindings are accepted or when the map is empty.
	 * @since 2.0
	 */
	public static <K, V> Maybe<Tuple2<K, V>> filterAny(final Map<? extends K, ? extends V> map, final Predicate2<? super K, ? super V> filter) {
		return MapUtils.<K, V>bindings(map).filterAny(filter);
	}
	
	/**
	 * Transforms the bindings of the given map using the given function.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <TE> Type of the transformed elements.
	 * @param map Map containing the bindings to transform.
	 * @param function Function to use to transform the bindings.
	 * @return A collection of the transformed elements.
	 * @since 2.0
	 */
	public static <K, V, TE> ExCollection<TE> map(final Map<? extends K, ? extends V> map, final Function2<? super K, ? super V, ? extends TE> function) {
		assert null != map;
		assert null != function;
		
		return new AbstractExCollection<TE>() {
			@Override
			public int size() {
				return map.size();
			}
			
			@Override
			public ExIterator<TE> iterator() {
				return IteratorUtils.map(map.entrySet().iterator(), e -> function.evaluate(e.getKey(), e.getValue()));
			}
		};
	}
	
	/**
	 * Transforms the bindings of the given map using the given function.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <TE> Type of the transformed elements.
	 * @param <C> Type of the result collection.
	 * @param map Map containing the bindings to transform.
	 * @param function Function to use to transform the bindings.
	 * @param resultFactory Factory of the result collection.
	 * @return A new collection of the transformed elements.
	 * @since 2.0
	 */
	public static <K, V, TE, C extends Collection<? super TE>> C map(final Map<? extends K, ? extends V> map, final Function2<? super K, ? super V, ? extends TE> function, final CollectionFactory<? super TE, C> resultFactory) {
		// return IteratorUtils.map(bindings(map).iterator(), function).drain(resultFactory.build(map.size()));
		// Note: avoids building the bindings
		final C results = resultFactory.build(map.size());
		for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
			results.add(function.evaluate(entry.getKey(), entry.getValue()));
		}
		return results;
	}
	
	/**
	 * Extracts the bindings of the given map using the given extractor.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <EE> Type of the extracted elements.
	 * @param map Map containing the bindings to extract.
	 * @param extractor Function to use to extract the bindings.
	 * @return A collection of the extracted elements.
	 * @since 2.0
	 */
	public static <K, V, EE> ExCollection<EE> extract(final Map<? extends K, ? extends V> map, final Function2<? super K, ? super V, ? extends Maybe<? extends EE>> extractor) {
		assert null != map;
		assert null != extractor;
		
		return new AbstractExCollection<EE>() {
			@Override
			public int size() {
				return IteratorUtils.count(map.entrySet().iterator(), e -> extractor.evaluate(e.getKey(), e.getValue()).isSome());
			}
			
			@Override
			public ExIterator<EE> iterator() {
				return IteratorUtils.extract(map.entrySet().iterator(), e -> extractor.evaluate(e.getKey(), e.getValue()));
			}
		};
	}
	
	/**
	 * Extracts the bindings of the given map using the given extractor.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <EE> Type of the extracted elements.
	 * @param <C> Type of the result collection.
	 * @param map Map containing the bindings to extract.
	 * @param extractor Function to use to extract the bindings.
	 * @param resultFactory Factory of the result collection.
	 * @return A new collection of the extracted elements.
	 * @since 2.0
	 */
	public static <K, V, EE, C extends Collection<? super EE>> C extract(final Map<? extends K, ? extends V> map, final Function2<? super K, ? super V, ? extends Maybe<? extends EE>> extractor, final CollectionFactory<? super EE, C> resultFactory) {
		// return IteratorUtils.extract(bindings(map).iterator(), extractor).drain(resultFactory.build(map.size()));
		// Note: avoids building the bindings
		final C results = resultFactory.build(map.size());
		for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
			final Maybe<? extends EE> extractedElement = extractor.evaluate(entry.getKey(), entry.getValue());
			if (extractedElement.isSome()) {
				results.add(extractedElement.asSome().getValue());
			}
		}
		return results;
	}
	
	// TODO: extractAny
	
	// TODO: extractAll to Multimap ?
	
	/**
	 * Appends the given maps together.
	 * <p>
	 * The binding of the first map have precedence over the bindings of the second map in case of conflict.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map1 First map containing the bindings to append.
	 * @param map2 Second map containing the bindings to append.
	 * @return A map containing the appended bindings.
	 * @since 2.0
	 */
	public static <K, V> ExMap<K, V> append(final Map<K, V> map1, final Map<K, V> map2) {
		assert null != map1;
		assert null != map2;
		
		return new AbstractExMap<K, V>() {
			private final ExSet<Map.Entry<K, V>> _entrySet = new AbstractExSet<Map.Entry<K, V>>() {
				@Override
				public int size() {
					final Set<K> keySet1 = map2.keySet();
					return map1.size() + IteratorUtils.count(map2.keySet().iterator(), k -> !keySet1.contains(k));
				}
				
				@Override
				public ExIterator<Map.Entry<K, V>> iterator() {
					final Set<K> keySet1 = map2.keySet();
					return IteratorUtils.append(map1.entrySet().iterator(), IteratorUtils.filter(map2.entrySet().iterator(), e -> !keySet1.contains(e.getKey())));
				}
			};
			
			@Override
			public ExSet<Map.Entry<K, V>> entrySet() {
				return _entrySet;
			}
		};
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
	 * @since 2.0
	 */
	public static <K, V, M extends Map<? super K, ? super V>> M append(final Map<? extends K, ? extends V> map1, final Map<? extends K, ? extends V> map2, final MapFactory<? super K, ? super V, M> resultFactory) {
		final M results = resultFactory.build(map1.size() + map2.size());
		results.putAll(map2);
		results.putAll(map1);
		return results;
	}
	
	/**
	 * Flattens the bindings contained in the maps contained in the given map.
	 * <p>
	 * The keys of the outer and inner maps are combined.
	 *
	 * @param <K1> Type of the keys of the outer map.
	 * @param <K2> Type of the keys or the inner maps.
	 * @param <V> Type of the values.
	 * @param map Map containing the maps containing the bindings to flatten.
	 * @return A map containing the flatten bindings.
	 * @since 2.0
	 */
	public static <K1, K2, V> ExMap<Tuple2<K1, K2>, V> flatten(final Map<? extends K1, ? extends Map<? extends K2, ? extends V>> map) {
		assert null != map;
		
		return new AbstractExMap<Tuple2<K1, K2>, V>() {
			private final ExSet<Map.Entry<Tuple2<K1, K2>, V>> _entrySet = new AbstractExSet<Map.Entry<Tuple2<K1, K2>, V>>() {
				@Override
				public int size() {
					return IterableUtils.fold(map.values(), (s, m) -> s + m.size(), 0);
				}
				
				@Override
				public ExIterator<Map.Entry<Tuple2<K1, K2>, V>> iterator() {
					return IteratorUtils.flatMap(map.entrySet().iterator(), (final Map.Entry<? extends K1, ? extends Map<? extends K2, ? extends V>> outerEntry) -> {
						final K1 outerKey = outerEntry.getKey();
						return IteratorUtils.map(outerEntry.getValue().entrySet().iterator(), innerEntry -> new SimpleEntry<>(new Tuple2<>(outerKey, innerEntry.getKey()), innerEntry.getValue()));
					});
				}
			};
			
			@Override
			public ExSet<Map.Entry<Tuple2<K1, K2>, V>> entrySet() {
				return _entrySet;
			}
		};
	}
	
	/**
	 * Flattens the bindings contained in the maps contained in the given map.
	 * <p>
	 * The keys of the outer and inner maps are combined.
	 *
	 * @param <K1> Type of the keys of the outer map.
	 * @param <K2> Type of the keys or the inner maps.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result map.
	 * @param map Map containing the maps containing the bindings to flatten.
	 * @param resultFactory Factory of the result map.
	 * @return A new map containing the flatten bindings.
	 * @since 2.0
	 */
	public static <K1, K2, V, M extends Map<? super Tuple2<K1, K2>, ? super V>> M flatten(final Map<? extends K1, ? extends Map<? extends K2, ? extends V>> map, final MapFactory<? super Tuple2<K1, K2>, ? super V, M> resultFactory) {
		// return resultFactory.build(IterableUtils.flatMap(MapUtils.bindings(map), (final Tuple2<? extends K1, ? extends Map<? extends K2, ? extends V>> outerBinding) -> {
		// final K1 outerKey = outerBinding.get1();
		// return MapUtils.bindings(outerBinding.get2()).map(innerBinding -> new Tuple2<>(new Tuple2<K1, K2>(outerKey, innerBinding.get1()), innerBinding.get2()));
		// }));
		// Note: avoids building the bindings
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
	 * Executes the given procedure with each binding of the given map.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map containing the bindings.
	 * @param procedure Procedure to execute.
	 * @since 2.0
	 */
	public static <K, V> void foreach(final Map<? extends K, ? extends V> map, final Procedure2<? super K, ? super V> procedure) {
		bindings(map).foreach(procedure);
	}
	
	/**
	 * Builds an unmodifiable view of the given map.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to wrap.
	 * @return An unmodifiable view of the given map, or the given map when is it already unmodifiable.
	 * @since 2.0
	 */
	public static <K, V> Map<K, V> unmodifiable(final Map<K, V> map) {
		assert null != map;
		
		return map instanceof UnmodifiableMap<?, ?> ? (UnmodifiableMap<K, V>) map : new UnmodifiableMap<>(map);
	}
	
	private static class UnmodifiableMap<K, V>
	extends MapDecorator<K, V> {
		public UnmodifiableMap(final Map<K, V> decorated) {
			super(decorated);
		}
		
		// Map.
		
		@Override
		public V put(final K key, final V value) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public Maybe<V> optionalPut(final K key, final V value) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void putAll(@SuppressWarnings("unchecked") final Tuple2<? extends K, ? extends V>... bindings) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void putAll(final Iterable<? extends Tuple2<? extends K, ? extends V>> bindings) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void putAll(final Map<? extends K, ? extends V> m) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public V remove(final Object key) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public Maybe<V> optionalRemove(final K key) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean remove(final Object key, final Object value) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public Maybe<Tuple2<K, V>> removeAny() {
			throw new UnsupportedOperationException();
		}
		
		// TODO: removeAny(Prediate<? super K>)
		// TODO: removeAll(K...)
		// TODO: removeAll(Iterable<? extends K>)
		// TODO: removeAll(Predicate<? super K>)
		
		@Override
		public void retainAll(final Predicate2<? super K, ? super V> filter) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public ExSet<K> keySet() {
			return super.keySet().unmodifiable();
		}
		
		@Override
		public ExCollection<V> values() {
			return super.values().unmodifiable();
		}
		
		@Override
		public ExSet<Map.Entry<K, V>> entrySet() {
			// unmodifiable entries
			//			return super.entrySet().unmodifiable();
		}
		
		@Override
		public PairIterable<K, V> bindings() {
			return super.bindings().unmodifiable();
		}
		
		@Override
		public V putIfAbsent(final K key, final V value) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public V replace(final K key, final V value) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean replace(final K key, final V oldValue, final V newValue) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void replaceAll(final BiFunction<? super K, ? super V, ? extends V> function) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public V compute(final K key, final BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public V computeIfAbsent(final K key, final Function<? super K, ? extends V> mappingFunction) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public V computeIfPresent(final K key, final BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public V merge(final K key, final V value, final BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
			throw new UnsupportedOperationException();
		}
	}
	
	private MapUtils() {
		// Prevent instantiation.
	}
}
