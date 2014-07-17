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
import com.trazere.core.lang.ThrowableFactory;
import com.trazere.core.util.Maybe;
import java.util.Map;

/**
 * The {@link MapFunctions} class provides various factories of functions related to {@link Map maps}.
 */
public class MapFunctions {
	/**
	 * Builds a function that gets the value associated to keys in the given map.
	 * <p>
	 * The built function evaluates to the associated value, or nothing when no value is associated to the key in the map.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to read.
	 * @return The built function.
	 * @see MapUtils#get(Map, Object)
	 */
	public static <K, V> Function<K, Maybe<V>> get(final Map<? super K, ? extends V> map) {
		assert null != map;
		
		return key -> MapUtils.get(map, key);
	}
	
	/**
	 * Builds a function that gets the optional value associated to keys in the given map.
	 * <p>
	 * The built function evaluates to the associated value, or the default value when no value is associated to the key in the map.
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
	
	// TODO: MapFunctions
	//	/**
	//	 * Builds a function corresponding to the given multimap.
	//	 *
	//	 * @param <K> Type of the arguments (the keys of the multimap).
	//	 * @param <C> Type of the results (the collections of the multimap).
	//	 * @param <X> Type of the exceptions.
	//	 * @param map The multimap.
	//	 * @return The built function.
	//	 */
	//	public static <K, C extends Collection<?>, X extends Exception> Function1<K, C, X> fromMultimap(final Multimap<? super K, ?, ? extends C> map) {
	//		assert null != map;
	//
	//		return new Function1<K, C, X>() {
	//			@Override
	//			public C evaluate(final K key) {
	//				return map.get(key);
	//			}
	//		};
	//	}
	
	private MapFunctions() {
		// Prevent instantiation.
	}
}
