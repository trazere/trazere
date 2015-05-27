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
import com.trazere.core.lang.IterableUtils;
import com.trazere.core.lang.ThrowableFactory;
import com.trazere.core.util.Tuple2;
import java.util.Map;

/**
 * The {@link MapFunctions} class provides various factories of functions related to {@link Map maps}.
 */
public class MapFunctions {
	/**
	 * Builds a function that gets the optional value associated to keys in the given map.
	 * <p>
	 * The built function evaluates to the associated value, or to the default value when no values are associated to the key in the map.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to read.
	 * @param defaultValue Default value for the keys associated to no values.
	 * @return The built function.
	 * @see MapUtils#get(Map, Object, Object)
	 */
	public static <K, V> Function<K, V> get(final Map<? super K, ? extends V> map, final V defaultValue) {
		assert null != map;
		
		return key -> MapUtils.get(map, key, defaultValue);
	}
	
	/**
	 * Builds a function that gets the mandatory value associated to keys in the given map.
	 * <p>
	 * The built function evaluates to the associated value, or throws an exception when no value is associated to the key in the map.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to read.
	 * @param throwableFactory Throwable factory to use.
	 * @return The built function.
	 * @see MapUtils#getMandatory(Map, Object, ThrowableFactory)
	 */
	public static <K, V> Function<K, V> getMandatory(final Map<? super K, ? extends V> map, final ThrowableFactory<? extends RuntimeException> throwableFactory) {
		assert null != map;
		assert null != throwableFactory;
		
		return key -> MapUtils.getMandatory(map, key, throwableFactory);
	}
	
	/**
	 * Builds a function according to the given bindings.
	 * <p>
	 * The built function evaluates to the value of the binding corresponding to the key, or to the default value when no bindings correspond to the key.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param bindings Bindings to use.
	 * @param defaultValue Default value for the keys associated to no values.
	 * @return The built function.
	 */
	public static <K, V> Function<K, V> fromBindings(final Iterable<? extends Tuple2<? extends K, ? extends V>> bindings, final V defaultValue) {
		return get(Maps.fromBindings(bindings), defaultValue);
	}
	
	/**
	 * Builds a function according to the given bindings.
	 * <p>
	 * The built function evaluates to the value of the binding corresponding to the key, or throws an exception when no bindings correspond to the key.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param bindings Bindings to use.
	 * @param throwableFactory Throwable factory to use.
	 * @return The built function.
	 */
	public static <K, V> Function<K, V> fromBindings(final Iterable<? extends Tuple2<? extends K, ? extends V>> bindings, final ThrowableFactory<? extends RuntimeException> throwableFactory) {
		return getMandatory(Maps.fromBindings(bindings), throwableFactory);
	}
	
	/**
	 * Builds a function according to the bindings resulting from the projection of the given keys.
	 * <p>
	 * The built function evaluates to the value of the binding corresponding to the key, or to the default value when no bindings correspond to the key.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param keys Keys of the bindings.
	 * @param projection Projection function that computes the volue associated to each key.
	 * @param defaultValue Default value for the keys associated to no values.
	 * @return The built function.
	 */
	public static <K, V> Function<K, V> fromKeys(final Iterable<? extends K> keys, final Function<? super K, ? extends V> projection, final V defaultValue) {
		return fromBindings(IterableUtils.<K, Tuple2<K, V>>map(keys, key -> new Tuple2<>(key, projection.evaluate(key))), defaultValue);
	}
	
	/**
	 * Builds a function according to the bindings resulting from the projection of the given keys.
	 * <p>
	 * The built function evaluates to the value of the binding corresponding to the key, or throws an exception when no bindings correspond to the key.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param keys Keys of the bindings.
	 * @param projection Projection function that computes the volue associated to each key.
	 * @param throwableFactory Throwable factory to use.
	 * @return The built function.
	 */
	public static <K, V> Function<K, V> fromKeys(final Iterable<? extends K> keys, final Function<? super K, ? extends V> projection, final ThrowableFactory<? extends RuntimeException> throwableFactory) {
		return fromBindings(IterableUtils.<K, Tuple2<K, V>>map(keys, key -> new Tuple2<>(key, projection.evaluate(key))), throwableFactory);
	}
	
	/**
	 * Builds a function according to the bindings resulting from the projection of the given values.
	 * <p>
	 * The built function evaluates to the value of the binding corresponding to the key, or to the default value when no bindings correspond to the key.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param values Values of the bindings.
	 * @param projection Projection function that computes the key associated to each value.
	 * @param defaultValue Default value for the keys associated to no values.
	 * @return The built function.
	 */
	public static <K, V> Function<K, V> fromValues(final Iterable<? extends V> values, final Function<? super V, ? extends K> projection, final V defaultValue) {
		return fromBindings(IterableUtils.<V, Tuple2<K, V>>map(values, value -> new Tuple2<>(projection.evaluate(value), value)), defaultValue);
	}
	
	/**
	 * Builds a function according to the bindings resulting from the projection of the given values.
	 * <p>
	 * The built function evaluates to the value of the binding corresponding to the key, or throws an exception when no bindings correspond to the key.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param values Values of the bindings.
	 * @param projection Projection function that computes the key associated to each value.
	 * @param throwableFactory Throwable factory to use.
	 * @return The built function.
	 */
	public static <K, V> Function<K, V> fromValues(final Iterable<? extends V> values, final Function<? super V, ? extends K> projection, final ThrowableFactory<? extends RuntimeException> throwableFactory) {
		return fromBindings(IterableUtils.<V, Tuple2<K, V>>map(values, value -> new Tuple2<>(projection.evaluate(value), value)), throwableFactory);
	}
	
	private MapFunctions() {
		// Prevent instantiation.
	}
}
