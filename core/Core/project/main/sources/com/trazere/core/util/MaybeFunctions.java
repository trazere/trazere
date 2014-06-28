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
package com.trazere.core.util;

import com.trazere.core.functional.Function;
import com.trazere.core.functional.Functions;
import com.trazere.core.functional.Predicate;

/**
 * The {@link MaybeFunctions} class provides various functions related to {@link Maybe}s.
 */
public class MaybeFunctions {
	/**
	 * Builds a function that builds {@link Maybe} instances using the {@link Maybe.Some} constructor.
	 * 
	 * @param <T> Type of the values.
	 * @return The built function.
	 * @see Maybe#some(Object)
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<T, Maybe<T>> some() {
		return (Function<T, Maybe<T>>) SOME;
	}
	
	private static final Function<?, ?> SOME = new Function<Object, Maybe<Object>>() {
		@Override
		public Maybe<Object> evaluate(final Object value) {
			return Maybe.some(value);
		}
	};
	
	/**
	 * Builds a function that builds instances of {@link Maybe} from values.
	 * 
	 * @param <T> Type of the values.
	 * @return The built function.
	 * @see Maybe#fromValue(Object)
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<T, Maybe<T>> fromValue() {
		return (Function<T, Maybe<T>>) FROM_VALUE;
	}
	
	private static final Function<?, ?> FROM_VALUE = new Function<Object, Maybe<Object>>() {
		@Override
		public Maybe<Object> evaluate(final Object value) {
			return Maybe.fromValue(value);
		}
	};
	
	/**
	 * Builds a function that convert {@link Maybe} instances to values.
	 * 
	 * @param <T> Type of the values.
	 * @return The built function.
	 * @see Maybe#toValue(Maybe)
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<Maybe<T>, T> toValue() {
		return (Function<Maybe<T>, T>) TO_VALUE;
	}
	
	private static final Function<?, ?> TO_VALUE = new Function<Maybe<Object>, Object>() {
		@Override
		public Object evaluate(final Maybe<Object> instance) {
			return Maybe.toValue(instance);
		}
	};
	
	/**
	 * Builds a function that maps the value wrapped in {@link Maybe} instances using the given function.
	 * 
	 * @param <T> Type of the values.
	 * @param <R> Type of the mapped values.
	 * @param function Mapping function to use.
	 * @return The built function.
	 * @see Maybe#map(Function)
	 */
	public static <T, R> Function<Maybe<? extends T>, Maybe<R>> map(final Function<? super T, ? extends R> function) {
		assert null != function;
		
		return new Function<Maybe<? extends T>, Maybe<R>>() {
			@Override
			public Maybe<R> evaluate(final Maybe<? extends T> maybe) {
				return maybe.map(function);
			}
		};
	}
	
	/**
	 * Builds a function that filters the value wrapped in {@link Maybe} instances using the given filter.
	 * 
	 * @param <T> Type of the values.
	 * @param filter Filter to use.
	 * @return The built function.
	 * @see Maybe#filter(Predicate)
	 */
	public static <T> Function<Maybe<? extends T>, Maybe<T>> filter(final Predicate<? super T> filter) {
		assert null != filter;
		
		return new Function<Maybe<? extends T>, Maybe<T>>() {
			@Override
			public Maybe<T> evaluate(final Maybe<? extends T> maybe) {
				return maybe.filter(filter).map(Functions.<T>identity());
			}
		};
	}
	
	/**
	 * Builds a function that extracts the value wrapped in {@link Maybe} instances using the given extractor.
	 * 
	 * @param <T> Type of the values.
	 * @param <R> Type of the extracted values.
	 * @param extractor Extractor to use.
	 * @return The built function.
	 * @see Maybe#extract(Function)
	 */
	public static <T, R> Function<Maybe<? extends T>, Maybe<R>> extract(final Function<? super T, ? extends Maybe<? extends R>> extractor) {
		assert null != extractor;
		
		return new Function<Maybe<? extends T>, Maybe<R>>() {
			@Override
			public Maybe<R> evaluate(final Maybe<? extends T> maybe) {
				return maybe.extract(extractor);
			}
		};
	}
	
	private MaybeFunctions() {
		// Prevent instantiation.
	}
}
