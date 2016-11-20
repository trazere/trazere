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
import com.trazere.core.functional.FunctionUtils;
import com.trazere.core.functional.Predicate;
import com.trazere.core.functional.Predicate2;
import com.trazere.core.functional.PredicateUtils;
import com.trazere.core.functional.Thunk;
import com.trazere.core.imperative.Procedure;
import com.trazere.core.imperative.Procedure2;
import com.trazere.core.imperative.ProcedureUtils;
import com.trazere.core.lang.PairIterable;
import com.trazere.core.lang.PairTraversable;
import com.trazere.core.lang.ThrowableFactory;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * The {@link ExMap} interfaces defines {@link Map maps} extended with various utilities.
 * 
 * @param <K> Type of keys.
 * @param <V> Type of values.
 * @since 2.0
 */
public interface ExMap<K, V>
extends Map<K, V>, PairTraversable<K, V> {
	// TODO: move to Maps ?
	// TODO: rename -> fromMap ?
	/**
	 * Builds an extended view of the given map.
	 * 
	 * @param <K> Type of keys.
	 * @param <V> Type of values.
	 * @param map Map to wrap.
	 * @return The extended view of the given map, or the given map when it is already an extended view.
	 * @since 2.0
	 */
	public static <K, V> ExMap<K, V> build(final Map<K, V> map) {
		assert null != map;
		
		if (map instanceof ExMap) {
			return (ExMap<K, V>) map;
		} else {
			return new MapDecorator<>(map);
		}
	}
	
	// Map.
	
	/**
	 * @since 2.0
	 */
	@Override
	ExSet<K> keySet();
	
	/**
	 * @since 2.0
	 */
	@Override
	ExCollection<V> values();
	
	/**
	 * @since 2.0
	 */
	@Override
	ExSet<Map.Entry<K, V>> entrySet();
	
	// ExMap.
	
	/**
	 * Gets a view of the bindings corresponding to this map.
	 * 
	 * @return The bindings.
	 * @see MapUtils#bindings(Map)
	 * @since 2.0
	 */
	default PairIterable<K, V> bindings() {
		return MapUtils.bindings(this);
	}
	
	/**
	 * Gets any binding from this map.
	 *
	 * @return A binding of the map.
	 * @throws NoSuchElementException When the map is empty.
	 * @see MapUtils#any(Map)
	 * @since 2.0
	 */
	default Tuple2<K, V> any()
	throws NoSuchElementException {
		return MapUtils.any(this);
	}
	
	/**
	 * Gets any binding from this map.
	 * <p>
	 * This methods supports empty maps.
	 *
	 * @return A binding of the map, or nothing when the map is empty.
	 * @see MapUtils#optionalAny(Map)
	 * @since 2.0
	 */
	default Maybe<Tuple2<K, V>> optionalAny() {
		return MapUtils.optionalAny(this);
	}
	
	/**
	 * Gets the value associated to the given key in this map.
	 * 
	 * @param key Key of the binding to read.
	 * @return The associated value, or nothing when the map contains no bindings for the key.
	 * @see MapUtils#optionalGet(Map, Object)
	 * @since 2.0
	 */
	default Maybe<V> optionalGet(final K key) {
		return MapUtils.optionalGet(this, key);
	}
	
	/**
	 * Gets the optional value associated to the given key in this map.
	 * <p>
	 * This method returns the associated value, or the default value when the map contains no bindings for the key.
	 *
	 * @param key Key of the binding to read.
	 * @param defaultValue Default value for the missing bindings.
	 * @return The associated value, or the default value when the map contains no bindings for the key.
	 * @see MapUtils#getOptional(Map, Object, Object)
	 * @since 2.0
	 */
	default V getOptional(final K key, final V defaultValue) {
		return MapUtils.getOptional(this, key, defaultValue);
	}
	
	/**
	 * Gets the optional value associated to the given key in this map.
	 * <p>
	 * This method returns the associated value, or the default value when the map contains no bindings for the key.
	 *
	 * @param key Key of the binding to read.
	 * @param defaultValue Default value for the missing bindings.
	 * @return The associated value, or the default value when the map contains no bindings for the key.
	 * @see MapUtils#getOptional(Map, Object, Thunk)
	 * @since 2.0
	 */
	default V getOptional(final K key, final Thunk<? extends V> defaultValue) {
		return MapUtils.getOptional(this, key, defaultValue);
	}
	
	/**
	 * Gets the mandatory value associated to the given key in this map.
	 * <p>
	 * This method returns the associated value, or throws an exception when the map contains no bindings for the key.
	 *
	 * @param key Key of the binding to read.
	 * @param missingBindingFactory Factory of the exceptions for the missing bindings.
	 * @return The associated value.
	 * @throws RuntimeException When the map contains no bindings for the key.
	 * @see MapUtils#getMandatory(Map, Object, ThrowableFactory)
	 * @since 2.0
	 */
	default V getMandatory(final K key, final ThrowableFactory<? extends RuntimeException> missingBindingFactory) {
		return MapUtils.getMandatory(this, key, missingBindingFactory);
	}
	
	/**
	 * Associates the given value to the given key in this map.
	 *
	 * @param key Key of the binding to set.
	 * @param value Value of the binding to set.
	 * @return The presiously associated value, or nothing when the map contained no bindings for the key.
	 * @see MapUtils#optionalPut(Map, Object, Object)
	 * @since 2.0
	 */
	default Maybe<V> optionalPut(final K key, final V value) {
		return MapUtils.optionalPut(this, key, value);
	}
	
	/**
	 * Puts all given bindings into this map.
	 *
	 * @param bindings Bindings to put in the map.
	 * @see MapUtils#putAll(Map, Tuple2...)
	 * @since 2.0
	 */
	default void putAll(@SuppressWarnings("unchecked") final Tuple2<? extends K, ? extends V>... bindings) {
		MapUtils.putAll(this, bindings);
	}
	
	/**
	 * Puts all given bindings into this map.
	 *
	 * @param bindings Bindings to put in the map.
	 * @see MapUtils#putAll(Map, Iterable)
	 * @since 2.0
	 */
	default void putAll(final Iterable<? extends Tuple2<? extends K, ? extends V>> bindings) {
		MapUtils.putAll(this, bindings);
	}
	
	/**
	 * Removes the binding for the given key from this map.
	 *
	 * @param key Key of the binding to remove.
	 * @return The associated removed value, or nothing when the map contains no bindings for the key.
	 * @see MapUtils#optionalRemove(Map, Object)
	 * @since 2.0
	 */
	default Maybe<V> optionalRemove(final K key) {
		return MapUtils.optionalRemove(this, key);
	}
	
	// TODO: generalize and move to ExIterable
	/**
	 * Removes a binding from this map.
	 * <p>
	 * The map must support removal through its iterators.
	 *
	 * @return The removed binding, or nothing when the map is empty.
	 * @see MapUtils#removeAny(Map)
	 * @since 2.0
	 */
	default Maybe<Tuple2<K, V>> removeAny() {
		return MapUtils.removeAny(this);
	}
	
	// TODO: generalize and move to ExIterable
	// TODO: removeAny(Prediate<? super K>)
	
	// TODO: generalize and move to ExIterable
	// TODO: removeAll(K...)
	
	// TODO: generalize and move to ExIterable
	// TODO: removeAll(Iterable<? extends K>)
	
	// TODO: generalize and move to ExIterable
	// TODO: removeAll(Predicate<? super K>)
	
	/**
	 * Retains the bindings accepted by the given filter in this map.
	 * <p>
	 * This method does modify this map.
	 *
	 * @param filter Predicate to use to filter the bindings to retain.
	 * @see MapUtils#retainAll(Map, Predicate2)
	 * @since 2.0
	 */
	default void retainAll(final Predicate2<? super K, ? super V> filter) {
		MapUtils.retainAll(this, filter);
	}
	
	// TODO: zip ?
	
	/**
	 * Builds an unmodifiable view of this map.
	 * 
	 * @return An unmodifiable view of this map, or this map when is it already unmodifiable.
	 * @see MapUtils#unmodifiable(Map)
	 * @since 2.0
	 */
	default ExMap<K, V> unmodifiable() {
		return MapUtils.unmodifiable(this);
	}
	
	// PairTraversable.
	
	/**
	 * Left folds over the bindings of this map using the given operator and initial state.
	 * <p>
	 * The bindings are folded according their iteration order.
	 * 
	 * @see MapUtils#fold(Map, Function3, Object)
	 * @since 2.0
	 */
	@Override
	default <S> S fold2(final Function3<? super S, ? super K, ? super V, ? extends S> operator, final S initialState) {
		return MapUtils.fold(this, operator, initialState);
	}
	
	/**
	 * Tests whether any binding of this map is accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the binding.
	 * @return <code>true</code> when some binding is accepted, <code>false</code> when all bindings are rejected or when the map is empty.
	 * @see MapUtils#isAny(Map, Predicate2)
	 * @since 2.0
	 */
	@Override
	default boolean isAny2(final Predicate2<? super K, ? super V> filter) {
		return MapUtils.isAny(this, filter);
	}
	
	/**
	 * Tests whether all bindings of this map are accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the binding.
	 * @return <code>true</code> when all bindings are accepted or when the map is empty, <code>false</code> when some binding is rejected.
	 * @see MapUtils#areAll(Map, Predicate2)
	 * @since 2.0
	 */
	@Override
	default boolean areAll2(final Predicate2<? super K, ? super V> filter) {
		return MapUtils.areAll(this, filter);
	}
	
	/**
	 * Counts the bindings of this map accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the binding.
	 * @return The number of accepted bindings.
	 * @see MapUtils#count(Map, Predicate2)
	 * @since 2.0
	 */
	@Override
	default int count2(final Predicate2<? super K, ? super V> filter) {
		return MapUtils.count(this, filter);
	}
	
	/**
	 * Filters the bindings of this map using the given filter.
	 *
	 * @param filter Predicate to use to filter the bindings.
	 * @return A map containing the filtered bindings.
	 * @see MapUtils#filter(Map, Predicate2)
	 * @since 2.0
	 */
	@Override
	default ExMap<K, V> filter2(final Predicate2<? super K, ? super V> filter) {
		return MapUtils.filter(this, filter);
	}
	
	/**
	 * Filters the bindings of this map using the given filter.
	 *
	 * @param <M> Type of the result map.
	 * @param filter Predicate to use to filter the bindings.
	 * @param resultFactory Factory of the result map.
	 * @return A new map containing the filtered bindings.
	 * @see MapUtils#filter(Map, Predicate2, MapFactory)
	 * @since 2.0
	 */
	default <M extends Map<? super K, ? super V>> M filter2(final Predicate2<? super K, ? super V> filter, final MapFactory<? super K, ? super V, M> resultFactory) {
		return MapUtils.filter(this, filter, resultFactory);
	}
	
	/**
	 * Gets any binding of this map accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the binding.
	 * @return The accepted binding, or when no bindings are accepted or when the map is empty.
	 * @see MapUtils#filterAny(Map, Predicate2)
	 * @since 2.0
	 */
	@Override
	default Maybe<Tuple2<K, V>> filterAny2(final Predicate2<? super K, ? super V> filter) {
		return MapUtils.filterAny(this, filter);
	}
	
	/**
	 * Transforms the bindings of this map using the given function.
	 *
	 * @param <TE> Type of the transformed elements.
	 * @param function Function to use to transform the bindings.
	 * @return A collection of the transformed elements.
	 * @see MapUtils#map(Map, Function2)
	 * @since 2.0
	 */
	@Override
	default <TE> ExCollection<TE> map2(final Function2<? super K, ? super V, ? extends TE> function) {
		return MapUtils.map(this, function);
	}
	
	/**
	 * Transforms the bindings of this map using the given function.
	 *
	 * @param <TE> Type of the transformed elements.
	 * @param <C> Type of the result collection.
	 * @param function Function to use to transform the bindings.
	 * @param resultFactory Factory of the result collection.
	 * @return A new collection of the transformed elements.
	 * @see MapUtils#map(Map, Function2, CollectionFactory)
	 * @since 2.0
	 */
	default <TE, C extends Collection<? super TE>> C map2(final Function2<? super K, ? super V, ? extends TE> function, final CollectionFactory<? super TE, C> resultFactory) {
		return MapUtils.map(this, function, resultFactory);
	}
	
	// TODO: mapValues
	// TODO: mapValues
	
	/**
	 * Extracts the bindings of this map using the given extractor.
	 * 
	 * @param <EE> Type of the extracted elements.
	 * @param extractor Function to use to extract the bindings.
	 * @return A collection of the extracted elements.
	 * @see MapUtils#extract(Map, Function2)
	 * @since 2.0
	 */
	@Override
	default <EE> ExCollection<EE> extract2(final Function2<? super K, ? super V, ? extends Maybe<? extends EE>> extractor) {
		return MapUtils.extract(this, extractor);
	}
	
	/**
	 * Extracts the bindings of this map using the given extractor.
	 * 
	 * @param <EE> Type of the extracted elements.
	 * @param <C> Type of the result collection.
	 * @param extractor Function to use to extract the bindings.
	 * @param resultFactory Factory of the result collection.
	 * @return A new collection of the extracted elements.
	 * @see MapUtils#extract(Map, Function2, CollectionFactory)
	 * @since 2.0
	 */
	default <EE, C extends Collection<? super EE>> C extract2(final Function2<? super K, ? super V, ? extends Maybe<? extends EE>> extractor, final CollectionFactory<? super EE, C> resultFactory) {
		return MapUtils.extract(this, extractor, resultFactory);
	}
	
	/**
	 * Gets the element extracted from any binding provided by this map using the given extractor.
	 * 
	 * @param <EE> Type of the extracted element.
	 * @param extractor Function to use to extract the bindings.
	 * @return The extracted element, or nothing when no elements can be extracted from any binding.
	 * @see MapUtils#extractAny(Map, Function2)
	 * @since 2.0
	 */
	@Override
	default <EE> Maybe<EE> extractAny2(final Function2<? super K, ? super V, ? extends Maybe<? extends EE>> extractor) {
		return MapUtils.extractAny(this, extractor);
	}
	
	// TODO: extractAll2 to Multimap ?
	
	/**
	 * Appends this map and the given map together.
	 * <p>
	 * The binding of this map have precedence over the bindings of the given map in case of conflict.
	 * 
	 * @param map Map containing the bindings to append.
	 * @return A map containing the appended bindings.
	 * @see MapUtils#append(Map, Map)
	 * @since 2.0
	 */
	default ExMap<K, V> append(final Map<K, V> map) {
		return MapUtils.append(this, map);
	}
	
	/**
	 * Appends this map and the given map together.
	 * <p>
	 * The binding of this map have precedence over the bindings of the given map in case of conflict.
	 * 
	 * @param <M> Type of the result map.
	 * @param map Map containing the bindings to append.
	 * @param resultFactory Factory of the result map.
	 * @return A new map containing the appended bindings.
	 * @see MapUtils#append(Map, Map, MapFactory)
	 * @since 2.0
	 */
	default <M extends Map<? super K, ? super V>> M append(final Map<? extends K, ? extends V> map, final MapFactory<? super K, ? super V, M> resultFactory) {
		return MapUtils.append(this, map, resultFactory);
	}
	
	// TODO: flatMap to Multimap ?
	
	/**
	 * Executes the given procedure with each binding of this map.
	 * 
	 * @see MapUtils#foreach(Map, Procedure2)
	 * @since 2.0
	 */
	@Override
	default void foreach2(final Procedure2<? super K, ? super V> procedure) {
		MapUtils.foreach(this, procedure);
	}
	
	// Traversable.
	
	/**
	 * Left folds over the bindings of this map using the given operator and initial state.
	 * <p>
	 * The bindings are folded according their iteration order.
	 * 
	 * @see MapUtils#fold(Map, Function3, Object)
	 * @since 2.0
	 */
	@Override
	default <S> S fold(final Function2<? super S, ? super Tuple2<K, V>, ? extends S> operator, final S initialState) {
		return MapUtils.fold(this, (s, k, v) -> operator.evaluate(s, new Tuple2<>(k, v)), initialState);
	}
	
	/**
	 * Tests whether any binding of this map is accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the binding.
	 * @return <code>true</code> when some binding is accepted, <code>false</code> when all bindings are rejected or when the map is empty.
	 * @see MapUtils#isAny(Map, Predicate2)
	 * @since 2.0
	 */
	@Override
	default boolean isAny(final Predicate<? super Tuple2<K, V>> filter) {
		return MapUtils.isAny(this, PredicateUtils.curry2(filter));
	}
	
	/**
	 * Tests whether all bindings of this map are accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the binding.
	 * @return <code>true</code> when all bindings are accepted or when the map is empty, <code>false</code> when some binding is rejected.
	 * @see MapUtils#areAll(Map, Predicate2)
	 * @since 2.0
	 */
	@Override
	default boolean areAll(final Predicate<? super Tuple2<K, V>> filter) {
		return MapUtils.areAll(this, PredicateUtils.curry2(filter));
	}
	
	/**
	 * Counts the bindings of this map accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the binding.
	 * @return The number of accepted bindings.
	 * @see MapUtils#count(Map, Predicate2)
	 * @since 2.0
	 */
	@Override
	default int count(final Predicate<? super Tuple2<K, V>> filter) {
		return MapUtils.count(this, PredicateUtils.curry2(filter));
	}
	
	/**
	 * Gets the least binding of this map according to the given comparator.
	 * 
	 * @return The least binding, or nothing when the map is empty.
	 * @see MapUtils#least(Map, Comparator)
	 * @since 2.0
	 */
	@Override
	default Maybe<Tuple2<K, V>> least(final Comparator<? super Tuple2<K, V>> comparator) {
		return MapUtils.least(this, comparator);
	}
	
	/**
	 * Gets the greatest binding of this map according to the given comparator.
	 *
	 * @return The greatest binding, or nothing when the map is empty.
	 * @see MapUtils#greatest(Map, Comparator)
	 * @since 2.0
	 */
	@Override
	default Maybe<Tuple2<K, V>> greatest(final Comparator<? super Tuple2<K, V>> comparator) {
		return MapUtils.greatest(this, comparator);
	}
	
	/**
	 * Gets the binding with the least value of this map according to the given comparator.
	 *
	 * @param comparator Comparator to use.
	 * @return The binding with the least value, or nothing when the map is empty.
	 * @see MapUtils#leastByValue(Map, Comparator)
	 * @since 2.0
	 */
	default Maybe<Tuple2<K, V>> leastByValue(final Comparator<? super V> comparator) {
		return MapUtils.leastByValue(this, comparator);
	}
	
	/**
	 * Gets the binding with the greatest value of this map according to the given comparator.
	 *
	 * @param comparator Comparator to use.
	 * @return The binding with the greatest value, or nothing when the map is empty.
	 * @see MapUtils#greatestByValue(Map)
	 * @since 2.0
	 */
	default Maybe<Tuple2<K, V>> greatestByValue(final Comparator<? super V> comparator) {
		return MapUtils.greatestByValue(this, comparator);
	}
	
	/**
	 * Takes n bindings of this map.
	 * <p>
	 * The bindings are taken according their iteration order.
	 * 
	 * @param n Number of bindings to take.
	 * @return A map containing the taken bindings.
	 * @see MapUtils#take(Map, int)
	 * @since 2.0
	 */
	@Override
	default ExMap<K, V> take(final int n) {
		return MapUtils.take(this, n);
	}
	
	/**
	 * Takes n bindings of this map.
	 * <p>
	 * The bindings are taken according their iteration order.
	 * 
	 * @param <M> Type of the result map.
	 * @param n Number of bindings to take.
	 * @param resultFactory Factory of the result map.
	 * @return A new map containing the taken bindings.
	 * @see MapUtils#take(Map, int, MapFactory)
	 * @since 2.0
	 */
	default <M extends Map<? super K, ? super V>> M take(final int n, final MapFactory<? super K, ? super V, M> resultFactory) {
		return MapUtils.take(this, n, resultFactory);
	}
	
	/**
	 * Drops n bindings of this map.
	 * <p>
	 * The bindings are dropped according their iteration order.
	 * 
	 * @param n Number of bindings to drop.
	 * @return A map containing the remaining bindings.
	 * @see MapUtils#drop(Map, int)
	 * @since 2.0
	 */
	@Override
	default ExMap<K, V> drop(final int n) {
		return MapUtils.drop(this, n);
	}
	
	/**
	 * Drops n bindings of this map.
	 * <p>
	 * The bindings are dropped according their iteration order.
	 * 
	 * @param <M> Type of the result map.
	 * @param n Number of bindings to drop.
	 * @param resultFactory Factory of the result map.
	 * @return A new map containing the remaining bindings.
	 * @see MapUtils#drop(Map, int, MapFactory)
	 * @since 2.0
	 */
	default <M extends Map<? super K, ? super V>> M drop(final int n, final MapFactory<? super K, ? super V, M> resultFactory) {
		return MapUtils.drop(this, n, resultFactory);
	}
	
	/**
	 * Groups the bindings of this map into batches of the given size.
	 *
	 * @param <B> Type of the batch collections.
	 * @param n Number of bindings of each batch.
	 * @param batchFactory Factory of the batch collections.
	 * @return A collection of the batches of bindings.
	 * @see MapUtils#group(Map, int, CollectionFactory)
	 * @since 2.0
	 */
	@Override
	default <B extends Collection<? super Tuple2<K, V>>> ExCollection<B> group(final int n, final CollectionFactory<? super Tuple2<K, V>, B> batchFactory) {
		return MapUtils.group(this, n, batchFactory);
	}
	
	/**
	 * Groups the bindings of this map into batches of the given size.
	 *
	 * @param <B> Type of the batch collections.
	 * @param <C> Type of the result collection.
	 * @param n Number of bindings of each batch.
	 * @param batchFactory Factory of the batch collections.
	 * @param resultFactory Factory of the result collection.
	 * @return A collection of the batches of bindings.
	 * @see MapUtils#group(Map, int, CollectionFactory, CollectionFactory)
	 * @since 2.0
	 */
	default <B extends Collection<? super Tuple2<K, V>>, C extends Collection<? super B>> C group(final int n, final CollectionFactory<? super Tuple2<K, V>, B> batchFactory, final CollectionFactory<? super B, C> resultFactory) {
		return MapUtils.group(this, n, batchFactory, resultFactory);
	}
	
	/**
	 * Filters the bindings of this map using the given filter.
	 *
	 * @param filter Predicate to use to filter the bindings.
	 * @return A map containing the filtered bindings.
	 * @see MapUtils#filter(Map, Predicate2)
	 * @since 2.0
	 */
	@Override
	default ExMap<K, V> filter(final Predicate<? super Tuple2<K, V>> filter) {
		return MapUtils.filter(this, PredicateUtils.curry2(filter));
	}
	
	/**
	 * Filters the bindings of this map using the given filter.
	 *
	 * @param <M> Type of the result map.
	 * @param filter Predicate to use to filter the bindings.
	 * @param resultFactory Factory of the result map.
	 * @return A new map containing the filtered bindings.
	 * @see MapUtils#filter(Map, Predicate2, MapFactory)
	 * @since 2.0
	 */
	default <M extends Map<? super K, ? super V>> M filter(final Predicate<? super Tuple2<K, V>> filter, final MapFactory<? super K, ? super V, M> resultFactory) {
		return MapUtils.filter(this, PredicateUtils.curry2(filter), resultFactory);
	}
	
	/**
	 * Gets any binding of this map accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the binding.
	 * @return The accepted binding, or when no bindings are accepted or when the map is empty.
	 * @see MapUtils#filterAny(Map, Predicate2)
	 * @since 2.0
	 */
	@Override
	default Maybe<Tuple2<K, V>> filterAny(final Predicate<? super Tuple2<K, V>> filter) {
		return MapUtils.filterAny(this, PredicateUtils.curry2(filter));
	}
	
	/**
	 * Transforms the bindings of this map using the given function.
	 *
	 * @param <TE> Type of the transformed elements.
	 * @param function Function to use to transform the bindings.
	 * @return A collection of the transformed elements.
	 * @see MapUtils#map(Map, Function2)
	 * @since 2.0
	 */
	@Override
	default <TE> ExCollection<TE> map(final Function<? super Tuple2<K, V>, ? extends TE> function) {
		return MapUtils.map(this, FunctionUtils.curry2(function));
	}
	
	/**
	 * Transforms the bindings of this map using the given function.
	 *
	 * @param <TE> Type of the transformed elements.
	 * @param <C> Type of the result collection.
	 * @param function Function to use to transform the bindings.
	 * @param resultFactory Factory of the result collection.
	 * @return A new collection of the transformed elements.
	 * @see MapUtils#map(Map, Function2, CollectionFactory)
	 * @since 2.0
	 */
	default <TE, C extends Collection<? super TE>> C map(final Function<? super Tuple2<K, V>, ? extends TE> function, final CollectionFactory<? super TE, C> resultFactory) {
		return MapUtils.map(this, FunctionUtils.curry2(function), resultFactory);
	}
	
	/**
	 * Extracts the bindings of this map using the given extractor.
	 * 
	 * @param <EE> Type of the extracted elements.
	 * @param extractor Function to use to extract the bindings.
	 * @return A collection of the extracted elements.
	 * @see MapUtils#extract(Map, Function2)
	 * @since 2.0
	 */
	@Override
	default <EE> ExCollection<EE> extract(final Function<? super Tuple2<K, V>, ? extends Maybe<? extends EE>> extractor) {
		return MapUtils.extract(this, FunctionUtils.curry2(extractor));
	}
	
	/**
	 * Extracts the bindings of this map using the given extractor.
	 * 
	 * @param <EE> Type of the extracted elements.
	 * @param <C> Type of the result collection.
	 * @param extractor Function to use to extract the bindings.
	 * @param resultFactory Factory of the result collection.
	 * @return A new collection of the extracted elements.
	 * @see MapUtils#extract(Map, Function2, CollectionFactory)
	 * @since 2.0
	 */
	default <EE, C extends Collection<? super EE>> C extract(final Function<? super Tuple2<K, V>, ? extends Maybe<? extends EE>> extractor, final CollectionFactory<? super EE, C> resultFactory) {
		return MapUtils.extract(this, FunctionUtils.curry2(extractor), resultFactory);
	}
	
	/**
	 * Gets the element extracted from any binding provided by this map using the given extractor.
	 * 
	 * @param <EE> Type of the extracted element.
	 * @param extractor Function to use to extract the bindings.
	 * @return The extracted element, or nothing when no elements can be extracted from any binding.
	 * @see MapUtils#extractAny(Map, Function2)
	 * @since 2.0
	 */
	@Override
	default <EE> Maybe<EE> extractAny(final Function<? super Tuple2<K, V>, ? extends Maybe<? extends EE>> extractor) {
		return MapUtils.extractAny(this, FunctionUtils.curry2(extractor));
	}
	
	// TODO: extractAll
	
	/**
	 * Executes the given procedure with each binding of this map.
	 * 
	 * @see MapUtils#foreach(Map, Procedure2)
	 * @since 2.0
	 */
	@Override
	default void foreach(final Procedure<? super Tuple2<K, V>> procedure) {
		MapUtils.foreach(this, ProcedureUtils.curry2(procedure));
	}
}
