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
import com.trazere.core.imperative.Procedure;
import com.trazere.core.imperative.Procedure2;
import com.trazere.core.lang.PairIterable;
import com.trazere.core.lang.PairIterables;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * The {@link Multimaps} class provides various factories of {@link Multimap multimaps}.
 * 
 * @see Multimap
 * @since 2.0
 */
public class Multimaps {
	/**
	 * Builds an unmutable empty multimap.
	 * 
	 * @param <K> Type of the key.
	 * @param <V> Type of the value.
	 * @param <C> Type of the collections of values.
	 * @param collectionFactory Factory of the collections of values.
	 * @return The built multimap.
	 * @since 2.0
	 */
	public static <K, V, C extends Collection<V>> Multimap<K, V, C> empty(final ExtendedAbstractCollectionFactory<?, ? extends C, ?> collectionFactory) {
		assert null != collectionFactory;
		
		return new Multimap<K, V, C>() {
			@Override
			public ExSet<K> keys() {
				return Sets.empty();
			}
			
			@Override
			public ExCollection<V> values() {
				return Lists.empty();
			}
			
			@Override
			public PairIterable<K, V> bindings() {
				return PairIterables.empty();
			}
			
			@Override
			public PairIterable<K, C> collectionBindings() {
				return PairIterables.empty();
			}
			
			@Override
			public int size() {
				return 0;
			}
			
			@Override
			public boolean isEmpty() {
				return true;
			}
			
			@Override
			public boolean containsKey(final K key) {
				return false;
			}
			
			@Override
			public boolean containsValue(final V value) {
				return false;
			}
			
			@Override
			public boolean contains(final K key, final V value) {
				return false;
			}
			
			@Override
			public Tuple2<K, V> any()
			throws NoSuchElementException {
				throw new NoSuchElementException();
			}
			
			@Override
			public Maybe<Tuple2<K, V>> optionalAny() {
				return Maybe.none();
			}
			
			@Override
			public C get(final Object key) {
				return collectionFactory.empty();
			}
			
			@Override
			public boolean put(final K key, final V value) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean put(final Tuple2<? extends K, ? extends V> binding) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean putAll(final K key, @SuppressWarnings("unchecked") final V... values) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean putAll(final K key, final Iterable<? extends V> values) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean putAll(@SuppressWarnings("unchecked") final Tuple2<? extends K, ? extends V>... bindings) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean putAll(final Iterable<? extends Tuple2<? extends K, ? extends V>> bindings) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean putAll(final Map<? extends K, ? extends V> map) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean putAll(final Multimap<? extends K, ? extends V, ?> multimap_) {
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
			
			@Override
			public boolean remove(final K key, final V value) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean remove(final Tuple2<? extends K, ? extends V> binding) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean removeAll(final K key, @SuppressWarnings("unchecked") final V... values) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean removeAll(final K key, final Iterable<? extends V> values) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean removeAll(@SuppressWarnings("unchecked") final Tuple2<? extends K, ? extends V>... bindings) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public boolean removeAll(final Iterable<? extends Tuple2<? extends K, ? extends V>> bindings) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public void clear() {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public Multimap<K, V, C> unmodifiable() {
				return this;
			}
			
			// PairTraversable.
			
			@Override
			public <S> S fold2(final Function3<? super S, ? super K, ? super V, ? extends S> operator, final S initialState) {
				return initialState;
			}
			
			@Override
			public boolean isAny2(final Predicate2<? super K, ? super V> filter) {
				return false;
			}
			
			@Override
			public boolean areAll2(final Predicate2<? super K, ? super V> filter) {
				return true;
			}
			
			@Override
			public int count2(final Predicate2<? super K, ? super V> filter) {
				return 0;
			}
			
			@Override
			public Multimap<K, V, C> filter2(final Predicate2<? super K, ? super V> filter) {
				return this;
			}
			
			@Override
			public Maybe<Tuple2<K, V>> filterAny2(final Predicate2<? super K, ? super V> filter) {
				return Maybe.none();
			}
			
			@Override
			public <TE> ExCollection<TE> map2(final Function2<? super K, ? super V, ? extends TE> function) {
				return Lists.empty();
			}
			
			@Override
			public <TE, RC extends Collection<? super TE>> RC map2(final Function2<? super K, ? super V, ? extends TE> function, final CollectionFactory<? super TE, RC> resultFactory) {
				return resultFactory.build();
			}
			
			@Override
			public <EE> ExCollection<EE> extract2(final Function2<? super K, ? super V, ? extends Maybe<? extends EE>> extractor) {
				return Lists.empty();
			}
			
			@Override
			public <EE, RC extends Collection<? super EE>> RC extract2(final Function2<? super K, ? super V, ? extends Maybe<? extends EE>> extractor, final CollectionFactory<? super EE, RC> resultFactory) {
				return resultFactory.build();
			}
			
			@Override
			public <EE> Maybe<EE> extractAny2(final Function2<? super K, ? super V, ? extends Maybe<? extends EE>> extractor) {
				return Maybe.none();
			}
			
			@Override
			public void foreach2(final Procedure2<? super K, ? super V> procedure) {
				// Nothing to do.
			}
			
			// Traversable.
			
			@Override
			public <S> S fold(final Function2<? super S, ? super Tuple2<K, V>, ? extends S> operator, final S initialState) {
				return initialState;
			}
			
			@Override
			public boolean isAny(final Predicate<? super Tuple2<K, V>> filter) {
				return false;
			}
			
			@Override
			public boolean areAll(final Predicate<? super Tuple2<K, V>> filter) {
				return true;
			}
			
			@Override
			public int count(final Predicate<? super Tuple2<K, V>> filter) {
				return 0;
			}
			
			@Override
			public Maybe<Tuple2<K, V>> least(final Comparator<? super Tuple2<K, V>> comparator) {
				return Maybe.none();
			}
			
			@Override
			public Maybe<Tuple2<K, V>> greatest(final Comparator<? super Tuple2<K, V>> comparator) {
				return Maybe.none();
			}
			
			@Override
			public Maybe<Tuple2<K, V>> leastByValue(final Comparator<? super V> comparator) {
				return Maybe.none();
			}
			
			@Override
			public Maybe<Tuple2<K, V>> greatestByValue(final Comparator<? super V> comparator) {
				return Maybe.none();
			}
			
			@Override
			public Multimap<K, V, C> take(final int n) {
				return this;
			}
			
			@Override
			public Multimap<K, V, C> drop(final int n) {
				return this;
			}
			
			@Override
			public <B extends Collection<? super Tuple2<K, V>>> ExCollection<B> group(final int n, final CollectionFactory<? super Tuple2<K, V>, B> batchFactory) {
				return Lists.empty();
			}
			
			@Override
			public Multimap<K, V, C> filter(final Predicate<? super Tuple2<K, V>> filter) {
				return this;
			}
			
			@Override
			public Maybe<Tuple2<K, V>> filterAny(final Predicate<? super Tuple2<K, V>> filter) {
				return Maybe.none();
			}
			
			@Override
			public <TE> ExCollection<TE> map(final Function<? super Tuple2<K, V>, ? extends TE> function) {
				return Lists.empty();
			}
			
			@Override
			public <EE> ExCollection<EE> extract(final Function<? super Tuple2<K, V>, ? extends Maybe<? extends EE>> extractor) {
				return Lists.empty();
			}
			
			@Override
			public <EE> Maybe<EE> extractAny(final Function<? super Tuple2<K, V>, ? extends Maybe<? extends EE>> extractor) {
				return Maybe.none();
			}
			
			@Override
			public void foreach(final Procedure<? super Tuple2<K, V>> procedure) {
				// Nothing to do.
			}
			
			// Object.
			// FIXME
		};
	}
	
	/**
	 * Builds a multimap containing the binding corresponding to the given key and value.
	 * 
	 * @param <K> Type of the key.
	 * @param <V> Type of the value.
	 * @param <C> Type of the collections of values.
	 * @param collectionFactory Factory of the collections of values.
	 * @param key Key of the binding of the multimap to build.
	 * @param value Value of the binding of the multimap to build.
	 * @return The built multimap.
	 * @since 2.0
	 */
	public static <K, V, C extends Collection<V>> Multimap<K, V, C> fromBinding(final ExtendedAbstractCollectionFactory<V, C, ?> collectionFactory, final K key, final V value) {
		final Multimap<K, V, C> multimap = new MapMultimap<>(MapFactories.hashMap(), collectionFactory);
		multimap.put(key, value);
		return multimap;
	}
	
	/**
	 * Builds a multimap containing the bindings corresponding to the given keys and values.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <C> Type of the collections of values.
	 * @param collectionFactory Factory of the collections of values.
	 * @param key1 Key of the first binding of the multimap to build.
	 * @param value1 Value of the first binding of the multimap to build.
	 * @param key2 Key of the second binding of the multimap to build.
	 * @param value2 Value of the second binding of the multimap to build.
	 * @return The built multimap.
	 * @since 2.0
	 */
	public static <K, V, C extends Collection<V>> Multimap<K, V, C> fromBindings(final ExtendedAbstractCollectionFactory<V, C, ?> collectionFactory, final K key1, final V value1, final K key2, final V value2) {
		final Multimap<K, V, C> multimap = new MapMultimap<>(MapFactories.hashMap(), collectionFactory);
		multimap.put(key1, value1);
		multimap.put(key2, value2);
		return multimap;
	}
	
	/**
	 * Builds a multimap containing the bindings corresponding to the given keys and values.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <C> Type of the collections of values.
	 * @param collectionFactory Factory of the collections of values.
	 * @param key1 Key of the first binding of the multimap to build.
	 * @param value1 Value of the first binding of the multimap to build.
	 * @param key2 Key of the second binding of the multimap to build.
	 * @param value2 Value of the second binding of the multimap to build.
	 * @param key3 Key of the third binding of the multimap to build.
	 * @param value3 Value of the third binding of the multimap to build.
	 * @return The built multimap.
	 * @since 2.0
	 */
	public static <K, V, C extends Collection<V>> Multimap<K, V, C> fromBindings(final ExtendedAbstractCollectionFactory<V, C, ?> collectionFactory, final K key1, final V value1, final K key2, final V value2, final K key3, final V value3) {
		final Multimap<K, V, C> multimap = new MapMultimap<>(MapFactories.hashMap(), collectionFactory);
		multimap.put(key1, value1);
		multimap.put(key2, value2);
		multimap.put(key3, value3);
		return multimap;
	}
	
	/**
	 * Builds a multimap containing the bindings corresponding to the given keys and values.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <C> Type of the collections of values.
	 * @param collectionFactory Factory of the collections of values.
	 * @param key1 Key of the first binding of the multimap to build.
	 * @param value1 Value of the first binding of the multimap to build.
	 * @param key2 Key of the second binding of the multimap to build.
	 * @param value2 Value of the second binding of the multimap to build.
	 * @param key3 Key of the third binding of the multimap to build.
	 * @param value3 Value of the third binding of the multimap to build.
	 * @param key4 Key of the fourth binding of the multimap to build.
	 * @param value4 Value of the fourth binding of the multimap to build.
	 * @return The built multimap.
	 * @since 2.0
	 */
	public static <K, V, C extends Collection<V>> Multimap<K, V, C> fromBindings(final ExtendedAbstractCollectionFactory<V, C, ?> collectionFactory, final K key1, final V value1, final K key2, final V value2, final K key3, final V value3, final K key4, final V value4) {
		final Multimap<K, V, C> multimap = new MapMultimap<>(MapFactories.hashMap(), collectionFactory);
		multimap.put(key1, value1);
		multimap.put(key2, value2);
		multimap.put(key3, value3);
		multimap.put(key4, value4);
		return multimap;
	}
	
	/**
	 * Builds a multimap containing the bindings corresponding to the given keys and values.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <C> Type of the collections of values.
	 * @param collectionFactory Factory of the collections of values.
	 * @param key1 Key of the first binding of the multimap to build.
	 * @param value1 Value of the first binding of the multimap to build.
	 * @param key2 Key of the second binding of the multimap to build.
	 * @param value2 Value of the second binding of the multimap to build.
	 * @param key3 Key of the third binding of the multimap to build.
	 * @param value3 Value of the third binding of the multimap to build.
	 * @param key4 Key of the fourth binding of the multimap to build.
	 * @param value4 Value of the fourth binding of the multimap to build.
	 * @param key5 Key of the fifth binding of the multimap to build.
	 * @param value5 Value of the fifth binding of the multimap to build.
	 * @return The built multimap.
	 * @since 2.0
	 */
	public static <K, V, C extends Collection<V>> Multimap<K, V, C> fromBindings(final ExtendedAbstractCollectionFactory<V, C, ?> collectionFactory, final K key1, final V value1, final K key2, final V value2, final K key3, final V value3, final K key4, final V value4, final K key5, final V value5) {
		final Multimap<K, V, C> multimap = new MapMultimap<>(MapFactories.hashMap(), collectionFactory);
		multimap.put(key1, value1);
		multimap.put(key2, value2);
		multimap.put(key3, value3);
		multimap.put(key4, value4);
		multimap.put(key5, value5);
		return multimap;
	}
	
	/**
	 * Builds a multimap containing the given bindings.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <C> Type of the collections of values.
	 * @param collectionFactory Factory of the collections of values.
	 * @param bindings Bindings of the multimap to build.
	 * @return The built multimap.
	 * @since 2.0
	 */
	@SafeVarargs
	public static <K, V, C extends Collection<V>> Multimap<K, V, C> fromBindings(final ExtendedAbstractCollectionFactory<V, C, ?> collectionFactory, final Tuple2<? extends K, ? extends V>... bindings) {
		final Multimap<K, V, C> multimap = new MapMultimap<>(MapFactories.hashMap(), collectionFactory);
		for (final Tuple2<? extends K, ? extends V> binding : bindings) {
			multimap.put(binding.get1(), binding.get2());
		}
		return multimap;
	}
	
	/**
	 * Builds a multimap containing the bindings provided by the given iterable.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <C> Type of the collections of values.
	 * @param collectionFactory Factory of the collections of values.
	 * @param iterable Iterable providing the bindings of the multimap to build.
	 * @return The built multimap.
	 * @since 2.0
	 */
	public static <K, V, C extends Collection<V>> Multimap<K, V, C> fromBindings(final ExtendedAbstractCollectionFactory<V, C, ?> collectionFactory, final Iterable<? extends Tuple2<? extends K, ? extends V>> iterable) {
		final Multimap<K, V, C> multimap = new MapMultimap<>(MapFactories.hashMap(), collectionFactory);
		for (final Tuple2<? extends K, ? extends V> binding : iterable) {
			multimap.put(binding.get1(), binding.get2());
		}
		return multimap;
	}
	
	private Multimaps() {
		// Prevent instantiation.
	}
}
