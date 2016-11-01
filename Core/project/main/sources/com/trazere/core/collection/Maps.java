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
import com.trazere.core.functional.Predicate;
import com.trazere.core.functional.Predicate2;
import com.trazere.core.functional.Thunk;
import com.trazere.core.imperative.Procedure;
import com.trazere.core.imperative.Procedure2;
import com.trazere.core.lang.PairIterable;
import com.trazere.core.lang.PairIterables;
import com.trazere.core.lang.ThrowableFactory;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * The {@link Maps} class provides various factories of {@link Map maps}.
 * 
 * @see Map
 * @since 2.0
 */
public class Maps {
	/**
	 * Builds an unmutable empty map.
	 * 
	 * @param <K> Type of the key.
	 * @param <V> Type of the value.
	 * @return The built map.
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> ExMap<K, V> empty() {
		return (ExMap<K, V>) EMPTY;
	}
	
	private static final ExMap<?, ?> EMPTY = new ExMap<Object, Object>() {
		// Map.
		
		@Override
		public int size() {
			return 0;
		}
		
		@Override
		public boolean isEmpty() {
			return true;
		}
		
		@Override
		public boolean containsKey(final Object key) {
			return false;
		}
		
		@Override
		public boolean containsValue(final Object value) {
			return false;
		}
		
		@Override
		public Object get(final Object key) {
			return null;
		}
		
		@Override
		public Object put(final Object key, final Object value) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public Object remove(final Object key) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void putAll(final Map<? extends Object, ? extends Object> m) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public ExSet<Object> keySet() {
			return Sets.empty();
		}
		
		@Override
		public ExCollection<Object> values() {
			return Lists.empty();
		}
		
		@Override
		public ExSet<Map.Entry<Object, Object>> entrySet() {
			return Sets.empty();
		}
		
		@Override
		public Object getOrDefault(final Object key, final Object defaultValue) {
			return defaultValue;
		}
		
		@Override
		public void forEach(final BiConsumer<? super Object, ? super Object> action) {
			// Nothing to do.
		}
		
		@Override
		public void replaceAll(final BiFunction<? super Object, ? super Object, ? extends Object> function) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public Object putIfAbsent(final Object key, final Object value) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean remove(final Object key, final Object value) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean replace(final Object key, final Object oldValue, final Object newValue) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public Object replace(final Object key, final Object value) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public Object computeIfAbsent(final Object key, final java.util.function.Function<? super Object, ? extends Object> mappingFunction) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public Object computeIfPresent(final Object key, final BiFunction<? super Object, ? super Object, ? extends Object> remappingFunction) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public Object compute(final Object key, final BiFunction<? super Object, ? super Object, ? extends Object> remappingFunction) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public Object merge(final Object key, final Object value, final BiFunction<? super Object, ? super Object, ? extends Object> remappingFunction) {
			throw new UnsupportedOperationException();
		}
		
		// ExMap.
		
		@Override
		public PairIterable<Object, Object> bindings() {
			return PairIterables.empty();
		}
		
		@Override
		public Tuple2<Object, Object> any()
		throws NoSuchElementException {
			throw new NoSuchElementException();
		}
		
		@Override
		public Maybe<Tuple2<Object, Object>> optionalAny() {
			return Maybe.none();
		}
		
		@Override
		public Maybe<Object> optionalGet(final Object key) {
			return Maybe.none();
		}
		
		@Override
		public Object getOptional(final Object key, final Object defaultValue) {
			return defaultValue;
		}
		
		@Override
		public Object getOptional(final Object key, final Thunk<? extends Object> defaultValue) {
			return defaultValue.evaluate();
		}
		
		@Override
		public Object getMandatory(final Object key, final ThrowableFactory<? extends RuntimeException> missingBindingFactory) {
			throw missingBindingFactory.build("No binding for key \"" + key + "\"");
		}
		
		@Override
		public Maybe<Object> optionalPut(final Object key, final Object value) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void putAll(@SuppressWarnings("unchecked") final Tuple2<? extends Object, ? extends Object>... bindings) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void putAll(final Iterable<? extends Tuple2<? extends Object, ? extends Object>> bindings) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public Maybe<Object> optionalRemove(final Object key) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public Maybe<Tuple2<Object, Object>> removeAny() {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void retainAll(final Predicate2<? super Object, ? super Object> filter) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public Maybe<Tuple2<Object, Object>> leastByValue(final Comparator<? super Object> comparator) {
			return Maybe.none();
		}
		
		@Override
		public Maybe<Tuple2<Object, Object>> greatestByValue(final Comparator<? super Object> comparator) {
			return Maybe.none();
		}
		
		@Override
		public <B extends Collection<? super Tuple2<Object, Object>>, C extends Collection<? super B>> C group(final int n, final CollectionFactory<? super Tuple2<Object, Object>, B> batchFactory, final CollectionFactory<? super B, C> resultFactory) {
			return resultFactory.build();
		}
		
		@Override
		public ExMap<Object, Object> append(final Map<Object, Object> map) {
			return ExMap.build(map);
		}
		
		@Override
		public <M extends Map<? super Object, ? super Object>> M append(final Map<? extends Object, ? extends Object> map, final MapFactory<? super Object, ? super Object, M> resultFactory) {
			return resultFactory.build(map);
		}
		
		@Override
		public ExMap<Object, Object> unmodifiable() {
			return this;
		}
		
		// PairTraversable.
		
		@Override
		public <S> S fold(final Function3<? super S, ? super Object, ? super Object, ? extends S> operator, final S initialState) {
			return initialState;
		}
		
		@Override
		public boolean isAny(final Predicate2<? super Object, ? super Object> filter) {
			return false;
		}
		
		@Override
		public boolean areAll(final Predicate2<? super Object, ? super Object> filter) {
			return true;
		}
		
		@Override
		public int count(final Predicate2<? super Object, ? super Object> filter) {
			return 0;
		}
		
		@Override
		public ExMap<Object, Object> filter(final Predicate2<? super Object, ? super Object> filter) {
			return empty();
		}
		
		@Override
		public <M extends Map<? super Object, ? super Object>> M filter(final Predicate2<? super Object, ? super Object> filter, final MapFactory<? super Object, ? super Object, M> resultFactory) {
			return resultFactory.build();
		}
		
		@Override
		public Maybe<Tuple2<Object, Object>> filterAny(final Predicate2<? super Object, ? super Object> filter) {
			return Maybe.none();
		}
		
		@Override
		public <TE> ExCollection<TE> map(final Function2<? super Object, ? super Object, ? extends TE> function) {
			return Lists.empty();
		}
		
		@Override
		public <TE, C extends Collection<? super TE>> C map(final Function2<? super Object, ? super Object, ? extends TE> function, final CollectionFactory<? super TE, C> resultFactory) {
			return resultFactory.build();
		}
		
		// TODO: mapValues
		
		@Override
		public <EE> ExCollection<EE> extract(final Function2<? super Object, ? super Object, ? extends Maybe<? extends EE>> extractor) {
			return Lists.empty();
		}
		
		@Override
		public <EE, C extends Collection<? super EE>> C extract(final Function2<? super Object, ? super Object, ? extends Maybe<? extends EE>> extractor, final CollectionFactory<? super EE, C> resultFactory) {
			return resultFactory.build();
		}
		
		@Override
		public <EE> Maybe<EE> extractAny(final Function2<? super Object, ? super Object, ? extends Maybe<? extends EE>> extractor) {
			return Maybe.none();
		}
		
		// TODO: extractAll to Multimap ?
		
		@Override
		public void foreach(final Procedure2<? super Object, ? super Object> procedure) {
			// Nothing to do.
		}
		
		// Traversable.
		
		@Override
		public <S> S fold(final Function2<? super S, ? super Tuple2<Object, Object>, ? extends S> operator, final S initialState) {
			return initialState;
		}
		
		@Override
		public boolean isAny(final Predicate<? super com.trazere.core.util.Tuple2<Object, Object>> filter) {
			return false;
		}
		
		@Override
		public boolean areAll(final Predicate<? super Tuple2<Object, Object>> filter) {
			return true;
		}
		
		@Override
		public int count(final Predicate<? super Tuple2<Object, Object>> filter) {
			return 0;
		}
		
		@Override
		public Maybe<Tuple2<Object, Object>> least(final Comparator<? super Tuple2<Object, Object>> comparator) {
			return Maybe.none();
		}
		
		@Override
		public Maybe<Tuple2<Object, Object>> greatest(final Comparator<? super Tuple2<Object, Object>> comparator) {
			return Maybe.none();
		}
		
		@Override
		public ExMap<Object, Object> take(final int n) {
			return empty();
		}
		
		@Override
		public <M extends Map<? super Object, ? super Object>> M take(final int n, final MapFactory<? super Object, ? super Object, M> resultFactory) {
			return resultFactory.build();
		}
		
		@Override
		public ExMap<Object, Object> drop(final int n) {
			return empty();
		}
		
		@Override
		public <M extends Map<? super Object, ? super Object>> M drop(final int n, final MapFactory<? super Object, ? super Object, M> resultFactory) {
			return resultFactory.build();
		}
		
		@Override
		public <B extends Collection<? super Tuple2<Object, Object>>> ExCollection<B> group(final int n, final CollectionFactory<? super Tuple2<Object, Object>, B> batchFactory) {
			return Lists.empty();
		}
		
		@Override
		public ExMap<Object, Object> filter(final Predicate<? super Tuple2<Object, Object>> filter) {
			return empty();
		}
		
		@Override
		public <M extends Map<? super Object, ? super Object>> M filter(final Predicate<? super Tuple2<Object, Object>> filter, final MapFactory<? super Object, ? super Object, M> resultFactory) {
			return resultFactory.build();
		}
		
		@Override
		public Maybe<Tuple2<Object, Object>> filterAny(final Predicate<? super Tuple2<Object, Object>> filter) {
			return Maybe.none();
		}
		
		@Override
		public <TE> ExCollection<TE> map(final Function<? super Tuple2<Object, Object>, ? extends TE> function) {
			return Lists.empty();
		}
		
		@Override
		public <TE, C extends Collection<? super TE>> C map(final Function<? super Tuple2<Object, Object>, ? extends TE> function, final CollectionFactory<? super TE, C> resultFactory) {
			return resultFactory.build();
		}
		
		// TODO: mapValues
		
		@Override
		public <EE> ExCollection<EE> extract(final Function<? super Tuple2<Object, Object>, ? extends Maybe<? extends EE>> extractor) {
			return Lists.empty();
		}
		
		@Override
		public <EE, C extends Collection<? super EE>> C extract(final Function<? super Tuple2<Object, Object>, ? extends Maybe<? extends EE>> extractor, final CollectionFactory<? super EE, C> resultFactory) {
			return resultFactory.build();
		}
		
		@Override
		public <EE> Maybe<EE> extractAny(final Function<? super Tuple2<Object, Object>, ? extends Maybe<? extends EE>> extractor) {
			return Maybe.none();
		}
		
		// TODO: extractAll
		
		// TODO: flatMap
		
		@Override
		public void foreach(final Procedure<? super Tuple2<Object, Object>> procedure) {
			// Nothing to do.
		}
		
		// Object.
		// FIXME
	};
	
	/**
	 * Builds a map containing the binding corresponding to the given key and value.
	 * 
	 * @param <K> Type of the key.
	 * @param <V> Type of the value.
	 * @param key Key of the binding of the map to build.
	 * @param value Value of the binding of the map to build.
	 * @return The built map.
	 * @since 2.0
	 */
	public static <K, V> ExMap<K, V> fromBinding(final K key, final V value) {
		final ExMap<K, V> map = new SimpleMap<>();
		map.put(key, value);
		return map;
	}
	
	/**
	 * Builds a map containing the bindings corresponding to the given keys and values.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param key1 Key of the first binding of the map to build.
	 * @param value1 Value of the first binding of the map to build.
	 * @param key2 Key of the second binding of the map to build.
	 * @param value2 Value of the second binding of the map to build.
	 * @return The built map.
	 * @since 2.0
	 */
	public static <K, V> ExMap<K, V> fromBindings(final K key1, final V value1, final K key2, final V value2) {
		final ExMap<K, V> map = new SimpleMap<>(2);
		map.put(key1, value1);
		map.put(key2, value2);
		return map;
	}
	
	/**
	 * Builds a map containing the bindings corresponding to the given keys and values.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param key1 Key of the first binding of the map to build.
	 * @param value1 Value of the first binding of the map to build.
	 * @param key2 Key of the second binding of the map to build.
	 * @param value2 Value of the second binding of the map to build.
	 * @param key3 Key of the third binding of the map to build.
	 * @param value3 Value of the third binding of the map to build.
	 * @return The built map.
	 * @since 2.0
	 */
	public static <K, V> ExMap<K, V> fromBindings(final K key1, final V value1, final K key2, final V value2, final K key3, final V value3) {
		final ExMap<K, V> map = new SimpleMap<>(3);
		map.put(key1, value1);
		map.put(key2, value2);
		map.put(key3, value3);
		return map;
	}
	
	/**
	 * Builds a map containing the bindings corresponding to the given keys and values.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param key1 Key of the first binding of the map to build.
	 * @param value1 Value of the first binding of the map to build.
	 * @param key2 Key of the second binding of the map to build.
	 * @param value2 Value of the second binding of the map to build.
	 * @param key3 Key of the third binding of the map to build.
	 * @param value3 Value of the third binding of the map to build.
	 * @param key4 Key of the fourth binding of the map to build.
	 * @param value4 Value of the fourth binding of the map to build.
	 * @return The built map.
	 * @since 2.0
	 */
	public static <K, V> ExMap<K, V> fromBindings(final K key1, final V value1, final K key2, final V value2, final K key3, final V value3, final K key4, final V value4) {
		final ExMap<K, V> map = new SimpleMap<>(4);
		map.put(key1, value1);
		map.put(key2, value2);
		map.put(key3, value3);
		map.put(key4, value4);
		return map;
	}
	
	/**
	 * Builds a map containing the bindings corresponding to the given keys and values.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param key1 Key of the first binding of the map to build.
	 * @param value1 Value of the first binding of the map to build.
	 * @param key2 Key of the second binding of the map to build.
	 * @param value2 Value of the second binding of the map to build.
	 * @param key3 Key of the third binding of the map to build.
	 * @param value3 Value of the third binding of the map to build.
	 * @param key4 Key of the fourth binding of the map to build.
	 * @param value4 Value of the fourth binding of the map to build.
	 * @param key5 Key of the fifth binding of the map to build.
	 * @param value5 Value of the fifth binding of the map to build.
	 * @return The built map.
	 * @since 2.0
	 */
	public static <K, V> ExMap<K, V> fromBindings(final K key1, final V value1, final K key2, final V value2, final K key3, final V value3, final K key4, final V value4, final K key5, final V value5) {
		final ExMap<K, V> map = new SimpleMap<>(5);
		map.put(key1, value1);
		map.put(key2, value2);
		map.put(key3, value3);
		map.put(key4, value4);
		map.put(key5, value5);
		return map;
	}
	
	/**
	 * Builds a map containing the given bindings.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param bindings Bindings of the map to build.
	 * @return The built map.
	 * @since 2.0
	 */
	@SafeVarargs
	public static <K, V> ExMap<K, V> fromBindings(final Tuple2<? extends K, ? extends V>... bindings) {
		final ExMap<K, V> map = new SimpleMap<>(bindings.length);
		map.putAll(bindings);
		return map;
	}
	
	/**
	 * Builds a map containing the bindings provided by the given iterable.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param iterable Iterable providing the bindings of the map to build.
	 * @return The built map.
	 * @since 2.0
	 */
	public static <K, V> ExMap<K, V> fromBindings(final Iterable<? extends Tuple2<? extends K, ? extends V>> iterable) {
		final ExMap<K, V> map = new SimpleMap<>();
		map.putAll(iterable);
		return map;
	}
	
	private static class SimpleMap<K, V>
	extends HashMap<K, V>
	implements ExMap<K, V> {
		private static final long serialVersionUID = 1L;
		
		public SimpleMap() {
			super();
		}
		
		public SimpleMap(final int initialCapacity) {
			super(initialCapacity);
		}
		
		// Map.
		
		@Override
		public ExSet<K> keySet() {
			return ExSet.build(super.keySet());
		}
		
		@Override
		public ExCollection<V> values() {
			return ExCollection.build(super.values());
		}
		
		@Override
		public ExSet<Map.Entry<K, V>> entrySet() {
			return ExSet.build(super.entrySet());
		}
	}
	
	private Maps() {
		// Prevent instantiation.
	}
}
