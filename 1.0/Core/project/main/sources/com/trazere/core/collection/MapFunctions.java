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
import com.trazere.core.lang.IterableUtils;
import com.trazere.core.lang.ThrowableFactory;
import com.trazere.core.util.Tuple2;
import java.util.Map;

/**
 * The {@link MapFunctions} class provides various factories of {@link Function functions} related to {@link Map maps}.
 * 
 * @see Function
 * @see Map
 * @since 1.0
 */
public class MapFunctions {
	/**
	 * Builds a function that gets the optional value associated to keys in the given map.
	 * <p>
	 * The built function evaluates to the value associated to the argument key, or to the default value when the map contains no bindings for the argument key.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to read.
	 * @param defaultValue Default value for the missing bindings.
	 * @return The built function.
	 * @see MapUtils#get(Map, Object, Object)
	 * @since 1.0
	 */
	public static <K, V> Function<K, V> get(final Map<? super K, ? extends V> map, final V defaultValue) {
		assert null != map;
		
		return key -> MapUtils.get(map, key, defaultValue);
	}
	
	/**
	 * Builds a function that gets the mandatory value associated to keys in the given map.
	 * <p>
	 * The built function evaluates to the value associated to the argument key, or throws an exception when the map contains no bindings for the argument key.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to read.
	 * @param missingBindingFactory Factory of the exceptions for the missing bindings.
	 * @return The built function.
	 * @see MapUtils#getMandatory(Map, Object, ThrowableFactory)
	 * @since 1.0
	 */
	public static <K, V> Function<K, V> getMandatory(final Map<? super K, ? extends V> map, final ThrowableFactory<? extends RuntimeException> missingBindingFactory) {
		assert null != map;
		assert null != missingBindingFactory;
		
		return key -> MapUtils.getMandatory(map, key, missingBindingFactory);
	}
	
	/**
	 * Builds a function according to the given bindings.
	 * <p>
	 * The built function evaluates to the value of the binding corresponding to the argument key, or to the default value when no bindings are provided for the
	 * argument key.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param bindings Bindings to use.
	 * @param defaultValue Default value for the missing bindings.
	 * @return The built function.
	 * @since 1.0
	 */
	public static <K, V> Function<K, V> fromBindings(final Iterable<? extends Tuple2<? extends K, ? extends V>> bindings, final V defaultValue) {
		return get(Maps.fromBindings(bindings), defaultValue);
	}
	
	/**
	 * Builds a function according to the given bindings.
	 * <p>
	 * The built function evaluates to the value of the binding corresponding to the argument key, or throws an exception when no bindings are provided for the
	 * argument key.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param bindings Bindings to use.
	 * @param missingBindingFactory Factory of the exceptions for the missing bindings.
	 * @return The built function.
	 * @since 1.0
	 */
	public static <K, V> Function<K, V> fromBindings(final Iterable<? extends Tuple2<? extends K, ? extends V>> bindings, final ThrowableFactory<? extends RuntimeException> missingBindingFactory) {
		return getMandatory(Maps.fromBindings(bindings), missingBindingFactory);
	}
	
	/**
	 * Builds a function according to the bindings resulting from the projection of the given keys.
	 * <p>
	 * The built function evaluates to the value corresponding to the projection of the argument key, or to the default value when the argument key was not
	 * included.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param keys Keys of the bindings.
	 * @param projection Projection function that computes the volue associated to each key.
	 * @param defaultValue Default value for the keys associated to no values.
	 * @return The built function.
	 * @since 1.0
	 */
	public static <K, V> Function<K, V> fromKeys(final Iterable<? extends K> keys, final Function<? super K, ? extends V> projection, final V defaultValue) {
		return fromBindings(IterableUtils.<K, Tuple2<K, V>>map(keys, key -> new Tuple2<>(key, projection.evaluate(key))), defaultValue);
	}
	
	/**
	 * Builds a function according to the bindings resulting from the projection of the given keys.
	 * <p>
	 * The built function evaluates to the value corresponding to the projection of the argument key, or throws an exception when the argument key was not
	 * included.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param keys Keys of the bindings.
	 * @param projection Projection function that computes the volue associated to each key.
	 * @param missingBindingFactory Factory of the exceptions for the missing bindings.
	 * @return The built function.
	 * @since 1.0
	 */
	public static <K, V> Function<K, V> fromKeys(final Iterable<? extends K> keys, final Function<? super K, ? extends V> projection, final ThrowableFactory<? extends RuntimeException> missingBindingFactory) {
		return fromBindings(IterableUtils.<K, Tuple2<K, V>>map(keys, key -> new Tuple2<>(key, projection.evaluate(key))), missingBindingFactory);
	}
	
	/**
	 * Builds a function according to the bindings resulting from the projection of the given values.
	 * <p>
	 * The built function evaluates to the value whose projection is the argument key, or to the default value when no included values is projected to the
	 * argument key.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param values Values of the bindings.
	 * @param projection Projection function that computes the key associated to each value.
	 * @param defaultValue Default value for the keys associated to no values.
	 * @return The built function.
	 * @since 1.0
	 */
	public static <K, V> Function<K, V> fromValues(final Iterable<? extends V> values, final Function<? super V, ? extends K> projection, final V defaultValue) {
		return fromBindings(IterableUtils.<V, Tuple2<K, V>>map(values, value -> new Tuple2<>(projection.evaluate(value), value)), defaultValue);
	}
	
	/**
	 * Builds a function according to the bindings resulting from the projection of the given values.
	 * <p>
	 * The built function evaluates to the value whose projection is the argument key, or throws an exception when no included values is projected to the
	 * argument key.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param values Values of the bindings.
	 * @param projection Projection function that computes the key associated to each value.
	 * @param missingBindingFactory Factory of the exceptions for the missing bindings.
	 * @return The built function.
	 * @since 1.0
	 */
	public static <K, V> Function<K, V> fromValues(final Iterable<? extends V> values, final Function<? super V, ? extends K> projection, final ThrowableFactory<? extends RuntimeException> missingBindingFactory) {
		return fromBindings(IterableUtils.<V, Tuple2<K, V>>map(values, value -> new Tuple2<>(projection.evaluate(value), value)), missingBindingFactory);
	}
	
	private MapFunctions() {
		// Prevent instantiation.
	}
}
