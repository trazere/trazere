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
import com.trazere.core.imperative.Accumulator;
import com.trazere.core.imperative.Accumulator2;
import com.trazere.core.imperative.IntCounter;
import com.trazere.core.imperative.IteratorUtils;
import com.trazere.core.imperative.Procedure2;
import com.trazere.core.lang.IterableUtils;
import com.trazere.core.lang.LangAccumulators;
import com.trazere.core.util.Comparators;
import com.trazere.core.util.FieldComparators;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * The {@link MultimapUtils} class provides various utilities regarding {@link Multimap multimaps}.
 * 
 * @see Multimap
 */
public class MultimapUtils {
	/**
	 * Builds an unmodifiable view of the given multimap.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <C> Type of the collections of values.
	 * @param multimap Multimap to view.
	 * @return The unmodifiable view.
	 */
	public static <K, V, C extends Collection<V>> Multimap<K, V, C> unmodifiable(final Multimap<K, V, C> multimap) {
		assert null != multimap;
		
		return new Multimap<K, V, C>() {
			@Override
			public boolean put(final K key, final V value) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean putAll(final K key, final Collection<? extends V> values) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean putAll(final Multimap<? extends K, ? extends V, ?> multimap_) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean isEmpty() {
				return multimap.isEmpty();
			}
			
			@Override
			public int size() {
				return multimap.size();
			}
			
			@Override
			public boolean containsKey(final K key) {
				return multimap.containsKey(key);
			}
			
			@Override
			public Set<K> keySet() {
				return multimap.keySet();
			}
			
			@Override
			public boolean contains(final K key, final V value) {
				return multimap.contains(key, value);
			}
			
			@Override
			public Set<Map.Entry<K, V>> entrySet() {
				return multimap.entrySet();
			}
			
			@Override
			public C get(final K key) {
				return multimap.get(key);
			}
			
			@Override
			public boolean containsValue(final V value) {
				return multimap.containsValue(value);
			}
			
			@Override
			public void clear() {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public C remove(final K key) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean remove(final K key, final V value) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean removeAll(final K key, final Collection<? extends V> values) {
				throw new UnsupportedOperationException();
			}
			
			// Object.
			
			@Override
			public int hashCode() {
				return multimap.hashCode();
			}
			
			@Override
			public boolean equals(final Object o) {
				return multimap.equals(o);
			}
			
			@Override
			public String toString() {
				return multimap.toString();
			}
		};
	}
	
	/**
	 * Gets a view of the bindings corresponding to the given multimap.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param multimap Multimap to read.
	 * @return The bindings.
	 */
	// TODO: optimize to avoid instantiating Map.Entry
	@SuppressWarnings("unchecked")
	public static <K, V> Iterable<Tuple2<K, V>> bindings(final Multimap<? extends K, ? extends V, ?> multimap) {
		return IterableUtils.map(multimap.entrySet(), (Function<Map.Entry<? extends K, ? extends V>, Tuple2<K, V>>) BINDING);
	}
	
	private static final Function<? extends Map.Entry<?, ?>, ? extends Tuple2<?, ?>> BINDING = entry -> new Tuple2<>(entry.getKey(), entry.getValue());
	
	/**
	 * Gets a binding from the given multimap.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param multimap Multimap to read.
	 * @return A binding of the multimap, or nothing when the multimap is empty.
	 */
	public static <K, V> Maybe<Tuple2<K, V>> any(final Multimap<K, V, ?> multimap) {
		final Iterator<Map.Entry<K, V>> entries = multimap.entrySet().iterator();
		if (entries.hasNext()) {
			final Map.Entry<K, V> entry = entries.next();
			return Maybe.some(new Tuple2<>(entry.getKey(), entry.getValue()));
		} else {
			return Maybe.none();
		}
	}
	
	/**
	 * Puts the given binding in the given multimap, or does nothing when no value is given.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param multimap Multimap to modify.
	 * @param key Key which the value should be associated to.
	 * @param value Value to associate to the key.
	 */
	public static <K, V> void put(final Multimap<? super K, ? super V, ?> multimap, final K key, final Maybe<? extends V> value) {
		if (value.isSome()) {
			multimap.put(key, value.asSome().getValue());
		}
	}
	
	// TODO: putAll(MultiMap, K, Iterable<V>)
	
	/**
	 * Puts all given bindings into the given multimap.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param multimap Multimap to modify.
	 * @param bindings Bindings to put in the multimap.
	 */
	@SafeVarargs
	public static <K, V> void putAll(final Multimap<? super K, ? super V, ?> multimap, final Tuple2<? extends K, ? extends V>... bindings) {
		for (final Tuple2<? extends K, ? extends V> binding : bindings) {
			multimap.put(binding.get1(), binding.get2());
		}
	}
	
	/**
	 * Puts all given bindings into the given multimap.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param multimap Multimap to modify.
	 * @param bindings Bindings to put in the multimap.
	 */
	public static <K, V> void putAll(final Multimap<? super K, ? super V, ?> multimap, final Iterable<? extends Tuple2<? extends K, ? extends V>> bindings) {
		for (final Tuple2<? extends K, ? extends V> binding : bindings) {
			multimap.put(binding.get1(), binding.get2());
		}
	}
	
	// TODO: removeAny(Multimap, K)
	
	// TODO: removeAll(Multimap, K, Iterable<V>)
	
	/**
	 * Removes all given bindings from the given multimap.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param multimap Multimap to modify.
	 * @param bindings Bindings to remove from the multimap.
	 * @return <code>true</code> when the multimap has changed, <code>false</code> otherwise.
	 */
	public static <K, V> boolean removeAll(final Multimap<? super K, ? super V, ?> multimap, final Iterable<? extends Tuple2<? extends K, ? extends V>> bindings) {
		final Accumulator<Boolean, Boolean> changed = LangAccumulators.or(false);
		for (final Tuple2<? extends K, ? extends V> binding : bindings) {
			changed.add(multimap.remove(binding.get1(), binding.get2()));
		}
		return changed.get().booleanValue();
	}
	
	/**
	 * Populates the given accumulator with copies of the bindings of the given multimap.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <A> Type of the accumulator to populate.
	 * @param multimap Multimap to read.
	 * @param results Accumulator to populate with the bindings.
	 * @return The given result accumulator.
	 */
	public static <K, V, A extends Accumulator2<? super K, ? super V, ?>> A copy(final Multimap<? extends K, ? extends V, ?> multimap, final A results) {
		for (final Map.Entry<? extends K, ? extends V> entry : multimap.entrySet()) {
			results.add(entry.getKey(), entry.getValue());
		}
		return results;
	}
	
	/**
	 * Adds copies of the bindings of the given multimap to the given collection.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <C> Type of the collection to populate.
	 * @param multimap Multimap to read.
	 * @param results Collection to populate with the bindings.
	 * @return The given result collection.
	 */
	public static <K, V, C extends Collection<? super Tuple2<? extends K, ? extends V>>> C copy(final Multimap<? extends K, ? extends V, ?> multimap, final C results) {
		return copy(multimap, CollectionAccumulators.add2(results)).get();
	}
	
	/**
	 * Executes the given procedure with each binding of the given multimap.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param multimap Multimap containing the bindings.
	 * @param procedure Procedure to execute.
	 */
	public static <K, V> void foreach(final Multimap<? extends K, ? extends V, ?> multimap, final Procedure2<? super K, ? super V> procedure) {
		for (final Map.Entry<? extends K, ? extends V> entry : multimap.entrySet()) {
			procedure.execute(entry.getKey(), entry.getValue());
		}
	}
	
	/**
	 * Left folds over the bindings of the given multimap using the given operator and initial state.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <S> Type of the state.
	 * @param multimap Multimap containing the bindings to fold over.
	 * @param operator Operator to use.
	 * @param initialState Initial state.
	 * @return The folded state.
	 */
	public static <K, V, S> S fold(final Multimap<? extends K, ? extends V, ?> multimap, final Function3<? super S, ? super K, ? super V, ? extends S> operator, final S initialState) {
		return copy(multimap, FunctionAccumulators.fold2(operator, initialState)).get();
	}
	
	/**
	 * Gets the first binding of the given multimap accepted by the given filter.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param multimap Multimap containing the bindings to filter.
	 * @param filter Predicate to use to filter the binding.
	 * @return The first accepted binding, or when no binding is accepted.
	 */
	public static <K, V> Maybe<Tuple2<K, V>> first(final Multimap<? extends K, ? extends V, ?> multimap, final Predicate2<? super K, ? super V> filter) {
		for (final Map.Entry<? extends K, ? extends V> entry : multimap.entrySet()) {
			final K key = entry.getKey();
			final V value = entry.getValue();
			if (filter.evaluate(key, value)) {
				return Maybe.some(new Tuple2<>(key, value));
			}
		}
		return Maybe.none();
	}
	
	/**
	 * Tests whether any binding of the given multimap is accepted by the given filter.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param multimap Multimap containing the bindings to test.
	 * @param filter Predicate to use to filter the binding.
	 * @return <code>true</code> when some binding is accepted, <code>false</code> when all bindings are rejected.
	 */
	public static <K, V> boolean isAny(final Multimap<? extends K, ? extends V, ?> multimap, final Predicate2<? super K, ? super V> filter) {
		for (final Map.Entry<? extends K, ? extends V> entry : multimap.entrySet()) {
			if (filter.evaluate(entry.getKey(), entry.getValue())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Tests whether all bindings of the given multimap are accepted by the given filter.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param multimap Multimap containing the bindings to test.
	 * @param filter Predicate to use to filter the binding.
	 * @return <code>true</code> when all bindings are accepted, <code>false</code> when some binding is rejected.
	 */
	public static <K, V> boolean areAll(final Multimap<? extends K, ? extends V, ?> multimap, final Predicate2<? super K, ? super V> filter) {
		for (final Map.Entry<? extends K, ? extends V> entry : multimap.entrySet()) {
			if (!filter.evaluate(entry.getKey(), entry.getValue())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Counts the bindings of the given multimap accepted by the given filter.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param multimap Multimap containing the bindings to count.
	 * @param filter Predicate to use to filter the binding.
	 * @return The number of accepted bindings.
	 */
	public static <K, V> int count(final Multimap<? extends K, ? extends V, ?> multimap, final Predicate2<? super K, ? super V> filter) {
		final IntCounter counter = new IntCounter();
		for (final Map.Entry<? extends K, ? extends V> entry : multimap.entrySet()) {
			if (filter.evaluate(entry.getKey(), entry.getValue())) {
				counter.inc();
			}
		}
		return counter.get();
	}
	
	/**
	 * Gets the binding with the least value of the given multimap.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param multimap Multimap containing the values to compare.
	 * @return The least element.
	 */
	public static <K, V extends Comparable<V>> Maybe<Tuple2<K, V>> least(final Multimap<? extends K, ? extends V, ?> multimap) {
		return least(multimap, Comparators.<V>natural());
	}
	
	/**
	 * Gets the binding with the least value of the given multimap according to the given comparator.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param multimap Multimap containing the values to compare.
	 * @param comparator Comparator to use.
	 * @return The least element.
	 */
	public static <K, V> Maybe<Tuple2<K, V>> least(final Multimap<? extends K, ? extends V, ?> multimap, final Comparator<? super V> comparator) {
		return IteratorUtils.least(MultimapUtils.<K, V>bindings(multimap).iterator(), FieldComparators.field2(comparator));
	}
	
	/**
	 * Gets the binding with the greatest value of the given multimap.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param multimap Multimap containing the values to compare.
	 * @return The greatest element.
	 */
	public static <K, V extends Comparable<V>> Maybe<Tuple2<K, V>> greatest(final Multimap<? extends K, ? extends V, ?> multimap) {
		return greatest(multimap, Comparators.<V>natural());
	}
	
	/**
	 * Gets the binding with the greatest value of the given multimap according to the given comparator.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param multimap Multimap containing the values to compare.
	 * @param comparator Comparator to use.
	 * @return The greatest element.
	 */
	public static <K, V> Maybe<Tuple2<K, V>> greatest(final Multimap<? extends K, ? extends V, ?> multimap, final Comparator<? super V> comparator) {
		return IteratorUtils.greatest(MultimapUtils.<K, V>bindings(multimap).iterator(), FieldComparators.field2(comparator));
	}
	
	/**
	 * Takes the n first bindings of the given multimap.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result multimap.
	 * @param multimap Multimap containing the bindings to take.
	 * @param n Number of bindings to take.
	 * @param resultFactory Factory of the result multimap.
	 * @return A multimap containing the taken bindings.
	 */
	public static <K, V, M extends Multimap<? super K, ? super V, ?>> M take(final Multimap<? extends K, ? extends V, ?> multimap, final int n, final MultimapFactory<? super K, ? super V, ?, M> resultFactory) {
		return IteratorUtils.drain(IteratorUtils.take(MultimapUtils.<K, V>bindings(multimap).iterator(), n), resultFactory.build());
	}
	
	/**
	 * Drops the n first bindings of the given multimap.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result multimap.
	 * @param multimap Multimap containing the bindings to drop.
	 * @param n Number of bindings to drop.
	 * @param resultFactory Factory of the result multimap.
	 * @return A multimap containing the remaining bindings.
	 */
	public static <K, V, M extends Multimap<? super K, ? super V, ?>> M drop(final Multimap<? extends K, ? extends V, ?> multimap, final int n, final MultimapFactory<? super K, ? super V, ?, M> resultFactory) {
		return IteratorUtils.drain(IteratorUtils.drop(MultimapUtils.<K, V>bindings(multimap).iterator(), n), resultFactory.build());
	}
	
	/**
	 * Filters the bindings of the given multimap using the given filter.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the result multimap.
	 * @param multimap Multimap containing the bindings to filter.
	 * @param filter Predicate to use to filter the bindings.
	 * @param resultFactory Factory of the result multimap.
	 * @return A multimap containing the filtered bindings.
	 */
	public static <K, V, M extends Multimap<? super K, ? super V, ?>> M filter(final Multimap<? extends K, ? extends V, ?> multimap, final Predicate2<? super K, ? super V> filter, final MultimapFactory<? super K, ? super V, ?, M> resultFactory) {
		final M result = resultFactory.build();
		for (final Map.Entry<? extends K, ? extends V> entry : multimap.entrySet()) {
			final K key = entry.getKey();
			final V value = entry.getValue();
			if (filter.evaluate(key, value)) {
				result.put(key, value);
			}
		}
		return result;
	}
	
	/**
	 * Transforms the bindings of the given multimap using the given function.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <TV> Type of the transformed values.
	 * @param <M> Type of the result multimap.
	 * @param multimap Multimap containing the bindings to transform.
	 * @param function Function to use to transform the bindings.
	 * @param resultFactory Factory of the result multimap.
	 * @return A multimap containing the transformed bindings.
	 */
	public static <K, V, TV, M extends Multimap<? super K, ? super TV, ?>> M map(final Multimap<? extends K, ? extends V, ?> multimap, final Function2<? super K, ? super V, ? extends TV> function, final MultimapFactory<? super K, ? super TV, ?, M> resultFactory) {
		final M results = resultFactory.build();
		for (final Map.Entry<? extends K, ? extends V> entry : multimap.entrySet()) {
			final K key = entry.getKey();
			results.put(key, function.evaluate(key, entry.getValue()));
		}
		return results;
	}
	
	/**
	 * Extracts the bindings of the given multimap using the given extractor.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <EV> Type of the extracted values.
	 * @param <M> Type of the result multimap.
	 * @param multimap Multimap containing the bindings to extract.
	 * @param extractor Function to use to extract the bindings.
	 * @param resultFactory Factory of the result multimap.
	 * @return A multimap containing the extracted bindings.
	 */
	public static <K, V, EV, M extends Multimap<? super K, ? super EV, ?>> M extract(final Multimap<? extends K, ? extends V, ?> multimap, final Function2<? super K, ? super V, ? extends Maybe<? extends EV>> extractor, final MultimapFactory<? super K, ? super EV, ?, M> resultFactory) {
		final M results = resultFactory.build();
		for (final Map.Entry<? extends K, ? extends V> entry : multimap.entrySet()) {
			final K key = entry.getKey();
			final Maybe<? extends EV> extractedValue = extractor.evaluate(key, entry.getValue());
			if (extractedValue.isSome()) {
				results.put(key, extractedValue.asSome().getValue());
			}
		}
		return results;
	}
	
	// TODO: extractAll
	
	private MultimapUtils() {
		// Prevent instantiation.
	}
}
