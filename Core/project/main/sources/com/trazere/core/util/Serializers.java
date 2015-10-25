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
package com.trazere.core.util;

/**
 * The {@link Serializers} class provides various factories of {@link Serializer serializers}.
 * 
 * @see Serializer
 * @since 2.0
 */
public class Serializers {
	/**
	 * Builds an identity serializer.
	 * 
	 * @param <T> Type of the values.
	 * @return The built serializer.
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> Serializer<T, T> identity() {
		return (Serializer<T, T>) IDENTITY;
	}
	
	private static final Serializer<?, ?> IDENTITY = new Serializer<Object, Object>() {
		@Override
		public Object serialize(final Object value) {
			return value;
		}
		
		@Override
		public Object deserialize(final Object representation) {
			return representation;
		}
	};
	
	/**
	 * Builds a serializer corresponding to the composition of the given serializers (g . f).
	 * 
	 * @param <V> Type of the values.
	 * @param <I> Type of the intermediate values/representations.
	 * @param <R> Type of the representations.
	 * @param g Outer serializer.
	 * @param f Inner serializer.
	 * @return The built value serializer.
	 * @since 2.0
	 */
	public static <V, I, R> Serializer<V, R> compose(final Serializer<I, R> g, final Serializer<V, I> f) {
		assert null != f;
		assert null != g;
		
		return new Serializer<V, R>() {
			@Override
			public R serialize(final V value) {
				return g.serialize(f.serialize(value));
			}
			
			@Override
			public V deserialize(final R representation) {
				return f.deserialize(g.deserialize(representation));
			}
		};
	}
	
	private Serializers() {
		// Prevents instantiation.
	}
}
