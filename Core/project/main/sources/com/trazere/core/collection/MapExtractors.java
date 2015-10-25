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
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.Map;

/**
 * The {@link MapExtractors} class provides various factories of Function extractors related to {@link Map maps}.
 * <p>
 * An extractor is {@link Function function} that combines a map operation and a filter operation.
 * 
 * @see Function
 * @see Maybe
 * @see Map
 * @since 2.0
 */
public class MapExtractors {
	/**
	 * Builds an extractor that gets the value associated to keys in the given map.
	 * <p>
	 * The built extractor evaluates to the associated value, or to nothing when no values are associated to the key in the map.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param map Map to read.
	 * @return The built extractor.
	 * @see MapUtils#get(Map, Object)
	 * @since 2.0
	 */
	public static <K, V> Function<K, Maybe<V>> get(final Map<? super K, ? extends V> map) {
		assert null != map;
		
		return key -> MapUtils.get(map, key);
	}
	
	/**
	 * Builds an extractor according to the given bindings.
	 * <p>
	 * The built extractor evaluates to the value of the binding corresponding to the key, or to nothing when no bindings correspond to the key.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param bindings Bindings to use.
	 * @return The built extractor.
	 * @since 2.0
	 */
	public static <K, V> Function<K, Maybe<V>> fromBindings(final Iterable<? extends Tuple2<? extends K, ? extends V>> bindings) {
		return get(Maps.fromBindings(bindings));
	}
	
	/**
	 * Builds an extractor according the bindings resulting from the projection of the given keys.
	 * <p>
	 * The built extractor evaluates to the value of the binding corresponding to the key, or to nothing when no bindings correspond to the key.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param keys Keys of the bindings.
	 * @param projection Projection function that computes the volue associated to each key.
	 * @return The built extractor.
	 * @since 2.0
	 */
	public static <K, V> Function<K, Maybe<V>> fromKeys(final Iterable<? extends K> keys, final Function<? super K, ? extends V> projection) {
		return fromBindings(IterableUtils.<K, Tuple2<K, V>>map(keys, key -> new Tuple2<>(key, projection.evaluate(key))));
	}
	
	/**
	 * Builds an extractor according the bindings resulting from the projection of the given values.
	 * <p>
	 * The built extractor evaluates to the value of the binding corresponding to the key, or to nothing when no bindings correspond to the key.
	 *
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param values Values of the bindings.
	 * @param projection Projection function that computes the key associated to each value.
	 * @return The built extractor.
	 * @since 2.0
	 */
	public static <K, V> Function<K, Maybe<V>> fromValues(final Iterable<? extends V> values, final Function<? super V, ? extends K> projection) {
		return fromBindings(IterableUtils.<V, Tuple2<K, V>>map(values, value -> new Tuple2<>(projection.evaluate(value), value)));
	}
	
	private MapExtractors() {
		// Prevent instantiation.
	}
}
