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

import com.trazere.core.design.Decorator;
import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Function3;
import com.trazere.core.functional.Predicate2;
import com.trazere.core.imperative.Accumulator;
import com.trazere.core.imperative.Procedure2;
import com.trazere.core.lang.IterableUtils;
import com.trazere.core.lang.LangAccumulators;
import com.trazere.core.lang.PairIterable;
import com.trazere.core.util.Comparators;
import com.trazere.core.util.FieldComparators;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * The {@link MultimapUtils} class provides various utilities regarding {@link Multimap multimaps}.
 * 
 * @see Multimap
 * @since 2.0
 */
public class MultimapUtils {
	/**
	 * Gets a view of the bindings corresponding to the given multimap.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param multimap Multimap to read.
	 * @return The bindings.
	 * @since 2.0
	 */
	public static <K, V> PairIterable<K, V> bindings(final Multimap<? extends K, ? extends V, ?> multimap) {
		return PairIterable.build(IterableUtils.flatMap(multimap.collectionEntrySet(), (final Map.Entry<? extends K, ? extends Collection<? extends V>> entry) -> {
			final K key = entry.getKey();
			return IterableUtils.map(entry.getValue(), value -> new Tuple2<>(key, value));
		}));
	}
	
	/**
	 * Gets a binding from the given multimap.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param multimap Multimap to read.
	 * @return A binding of the multimap.
	 * @throws NoSuchElementException When the multimap is empty.
	 * @since 2.0
	 */
	public static <K, V> Tuple2<K, V> any(final Multimap<K, V, ?> multimap)
	throws NoSuchElementException {
		return bindings(multimap).any();
	}
	
	/**
	 * Gets a binding from the given multimap.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param multimap Multimap to read.
	 * @return A binding of the multimap, or nothing when the multimap is empty.
	 * @since 2.0
	 */
	public static <K, V> Maybe<Tuple2<K, V>> optionalAny(final Multimap<K, V, ?> multimap) {
		return bindings(multimap).optionalAny();
	}
	
	/**
	 * Puts the given binding in the given multimap, or does nothing when no value is given.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param multimap Multimap to modify.
	 * @param key Key which the value should be associated to.
	 * @param value Value to associate to the key.
	 * @since 2.0
	 */
	public static <K, V> void put(final Multimap<? super K, ? super V, ?> multimap, final K key, final Maybe<? extends V> value) {
		if (value.isSome()) {
			multimap.put(key, value.asSome().getValue());
		}
	}
	
	/**
	 * Puts all given bindings into the given multimap.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param multimap Multimap to modify.
	 * @param bindings Bindings to put in the multimap.
	 * @since 2.0
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
	 * @since 2.0
	 */
	public static <K, V> void putAll(final Multimap<? super K, ? super V, ?> multimap, final Iterable<? extends Tuple2<? extends K, ? extends V>> bindings) {
		for (final Tuple2<? extends K, ? extends V> binding : bindings) {
			multimap.put(binding.get1(), binding.get2());
		}
	}
	
	// TODO: removeAny(Multimap) ?
	// TODO: removeAny(Multimap, K) ?
	
	/**
	 * Removes all given bindings from the given multimap.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param multimap Multimap to modify.
	 * @param bindings Bindings to remove from the multimap.
	 * @return <code>true</code> when the multimap has changed, <code>false</code> otherwise.
	 * @since 2.0
	 */
	public static <K, V> boolean removeAll(final Multimap<? super K, ? super V, ?> multimap, final Iterable<? extends Tuple2<? extends K, ? extends V>> bindings) {
		final Accumulator<Boolean, Boolean> changed = LangAccumulators.or(false);
		for (final Tuple2<? extends K, ? extends V> binding : bindings) {
			changed.add(multimap.remove(binding.get1(), binding.get2()));
		}
		return changed.get().booleanValue();
	}
	
	// TODO: retain(Multimap, Predicate2), requires Iterator.remove support for entrySet
	
	/**
	 * Executes the given procedure with each binding of the given multimap.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param multimap Multimap containing the bindings.
	 * @param procedure Procedure to execute.
	 * @since 2.0
	 */
	public static <K, V> void foreach(final Multimap<? extends K, ? extends V, ?> multimap, final Procedure2<? super K, ? super V> procedure) {
		bindings(multimap).foreach(procedure);
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
	 * @since 2.0
	 */
	public static <K, V, S> S fold(final Multimap<? extends K, ? extends V, ?> multimap, final Function3<? super S, ? super K, ? super V, ? extends S> operator, final S initialState) {
		return bindings(multimap).fold(operator, initialState);
	}
	
	/**
	 * Gets the first binding of the given multimap accepted by the given filter.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param multimap Multimap containing the bindings to filter.
	 * @param filter Predicate to use to filter the binding.
	 * @return The first accepted binding, or when no binding is accepted.
	 * @since 2.0
	 */
	public static <K, V> Maybe<Tuple2<K, V>> first(final Multimap<? extends K, ? extends V, ?> multimap, final Predicate2<? super K, ? super V> filter) {
		return MultimapUtils.<K, V>bindings(multimap).first(filter);
	}
	
	/**
	 * Tests whether any binding of the given multimap is accepted by the given filter.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param multimap Multimap containing the bindings to test.
	 * @param filter Predicate to use to filter the binding.
	 * @return <code>true</code> when some binding is accepted, <code>false</code> when all bindings are rejected.
	 * @since 2.0
	 */
	public static <K, V> boolean isAny(final Multimap<? extends K, ? extends V, ?> multimap, final Predicate2<? super K, ? super V> filter) {
		return bindings(multimap).isAny(filter);
	}
	
	/**
	 * Tests whether all bindings of the given multimap are accepted by the given filter.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param multimap Multimap containing the bindings to test.
	 * @param filter Predicate to use to filter the binding.
	 * @return <code>true</code> when all bindings are accepted, <code>false</code> when some binding is rejected.
	 * @since 2.0
	 */
	public static <K, V> boolean areAll(final Multimap<? extends K, ? extends V, ?> multimap, final Predicate2<? super K, ? super V> filter) {
		return bindings(multimap).areAll(filter);
	}
	
	/**
	 * Counts the bindings of the given multimap accepted by the given filter.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param multimap Multimap containing the bindings to count.
	 * @param filter Predicate to use to filter the binding.
	 * @return The number of accepted bindings.
	 * @since 2.0
	 */
	public static <K, V> int count(final Multimap<? extends K, ? extends V, ?> multimap, final Predicate2<? super K, ? super V> filter) {
		return bindings(multimap).count(filter);
	}
	
	/**
	 * Gets the binding with the least value of the given multimap.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param multimap Multimap containing the values to compare.
	 * @return The least element.
	 * @since 2.0
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
	 * @since 2.0
	 */
	public static <K, V> Maybe<Tuple2<K, V>> least(final Multimap<? extends K, ? extends V, ?> multimap, final Comparator<? super V> comparator) {
		return MultimapUtils.<K, V>bindings(multimap).least(FieldComparators.field2(comparator));
	}
	
	/**
	 * Gets the binding with the greatest value of the given multimap.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param multimap Multimap containing the values to compare.
	 * @return The greatest element.
	 * @since 2.0
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
	 * @since 2.0
	 */
	public static <K, V> Maybe<Tuple2<K, V>> greatest(final Multimap<? extends K, ? extends V, ?> multimap, final Comparator<? super V> comparator) {
		return MultimapUtils.<K, V>bindings(multimap).greatest(FieldComparators.field2(comparator));
	}
	
	// TODO: append
	// TODO: flatten
	
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
	 * @since 2.0
	 */
	public static <K, V, M extends Multimap<? super K, ? super V, ?>> M take(final Multimap<? extends K, ? extends V, ?> multimap, final int n, final MultimapFactory<? super K, ? super V, ?, M> resultFactory) {
		return resultFactory.build(bindings(multimap).take(n));
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
	 * @since 2.0
	 */
	public static <K, V, M extends Multimap<? super K, ? super V, ?>> M drop(final Multimap<? extends K, ? extends V, ?> multimap, final int n, final MultimapFactory<? super K, ? super V, ?, M> resultFactory) {
		return resultFactory.build(bindings(multimap).drop(n));
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
	 * @since 2.0
	 */
	public static <K, V, M extends Multimap<? super K, ? super V, ?>> M filter(final Multimap<? extends K, ? extends V, ?> multimap, final Predicate2<? super K, ? super V> filter, final MultimapFactory<? super K, ? super V, ?, M> resultFactory) {
		//		return resultFactory.build(IterableUtils.filter(bindings(multimap), filter));
		
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
	 * @since 2.0
	 */
	public static <K, V, TV, M extends Multimap<? super K, ? super TV, ?>> M map(final Multimap<? extends K, ? extends V, ?> multimap, final Function2<? super K, ? super V, ? extends TV> function, final MultimapFactory<? super K, ? super TV, ?, M> resultFactory) {
		//		return resultFactory.build(IterableUtils.map(bindings(multimap), (k, v) -> new Tuple2<>(k, function.evaluate(k, v))));
		
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
	 * @since 2.0
	 */
	public static <K, V, EV, M extends Multimap<? super K, ? super EV, ?>> M extract(final Multimap<? extends K, ? extends V, ?> multimap, final Function2<? super K, ? super V, ? extends Maybe<? extends EV>> extractor, final MultimapFactory<? super K, ? super EV, ?, M> resultFactory) {
		//		return resultFactory.build(IterableUtils.extract(bindings(multimap), (k, v) -> extractor.evaluate(k, v).map(ev -> new Tuple2<>(k, ev))));
		
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
	
	/**
	 * Builds an unmodifiable view of the given multimap.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <C> Type of the collections of values.
	 * @param multimap Multimap to view.
	 * @return The unmodifiable view.
	 * @since 2.0
	 */
	public static <K, V, C extends Collection<V>> Multimap<K, V, C> unmodifiable(final Multimap<K, V, C> multimap) {
		assert null != multimap;
		
		return multimap instanceof UnmodifiableMutimap ? multimap : new UnmodifiableMutimap<>(multimap);
	}
	
	private static class UnmodifiableMutimap<K, V, C extends Collection<V>>
	extends Decorator<Multimap<K, V, C>>
	implements Multimap<K, V, C> {
		public UnmodifiableMutimap(final Multimap<K, V, C> decorated) {
			super(decorated);
		}
		
		// Multimap.
		
		@Override
		public boolean put(final K key, final V value) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean putAll(final K key, final Iterable<? extends V> values) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean putAll(final Multimap<? extends K, ? extends V, ?> multimap_) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean isEmpty() {
			return _decorated.isEmpty();
		}
		
		@Override
		public int size() {
			return _decorated.size();
		}
		
		@Override
		public boolean containsKey(final K key) {
			return _decorated.containsKey(key);
		}
		
		@Override
		public Set<K> keySet() {
			return _decorated.keySet();
		}
		
		@Override
		public boolean contains(final K key, final V value) {
			return _decorated.contains(key, value);
		}
		
		@Override
		public Set<Map.Entry<K, V>> entrySet() {
			return _decorated.entrySet();
		}
		
		@Override
		public Set<Entry<K, C>> collectionEntrySet() {
			return _decorated.collectionEntrySet();
		}
		
		@Override
		public C get(final K key) {
			return _decorated.get(key);
		}
		
		@Override
		public boolean containsValue(final V value) {
			return _decorated.containsValue(value);
		}
		
		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean remove(final K key, final V value) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean removeAll(final K key, final Iterable<? extends V> values) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public C removeKey(final K key) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean removeValue(final V value) {
			throw new UnsupportedOperationException();
		}
		
		// Object.
		
		@Override
		public int hashCode() {
			return _decorated.hashCode();
		}
		
		@Override
		public boolean equals(final Object o) {
			return _decorated.equals(o);
		}
		
		@Override
		public String toString() {
			return _decorated.toString();
		}
	}
	
	private MultimapUtils() {
		// Prevent instantiation.
	}
}
