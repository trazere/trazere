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

import com.trazere.core.functional.Function;
import com.trazere.core.functional.Predicate;

/**
 * The {@link MaybeFunctions} class provides various factories of {@link Function functions} related to {@link Maybe maybes}.
 * 
 * @see Function
 * @see Maybe
 * @since 1.0
 */
public class MaybeFunctions {
	/**
	 * Builds a function that builds {@link Maybe} instances using the {@link Maybe.Some} constructor.
	 * 
	 * @param <T> Type of the values.
	 * @return The built function.
	 * @see Maybe#some(Object)
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<T, Maybe<T>> some() {
		return (Function<T, Maybe<T>>) SOME;
	}
	
	private static final Function<?, ? extends Maybe<?>> SOME = Maybe::some;
	
	/**
	 * Builds a function that builds instances of {@link Maybe} from nullable values.
	 * 
	 * @param <T> Type of the values.
	 * @return The built function.
	 * @see MaybeUtils#fromNullable(Object)
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<T, Maybe<T>> fromNullable() {
		return (Function<T, Maybe<T>>) FROM_NULLABLE;
	}
	
	private static final Function<?, ? extends Maybe<?>> FROM_NULLABLE = MaybeUtils::fromNullable;
	
	/**
	 * Builds a function that convert {@link Maybe} instances to nullable values.
	 * 
	 * @param <T> Type of the values.
	 * @return The built function.
	 * @see MaybeUtils#toNullable(Maybe)
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<Maybe<T>, T> toNullable() {
		return (Function<Maybe<T>, T>) TO_NULLABLE;
	}
	
	private static final Function<? extends Maybe<?>, ?> TO_NULLABLE = instance -> MaybeUtils.toNullable(instance);
	
	/**
	 * Builds a function that filters the value wrapped in {@link Maybe} instances using the given filter.
	 * 
	 * @param <T> Type of the values.
	 * @param filter Predicate to use to filter the value.
	 * @return The built function.
	 * @see Maybe#filter(Predicate)
	 * @since 1.0
	 */
	public static <T> Function<Maybe<? extends T>, Maybe<? extends T>> filter(final Predicate<? super T> filter) {
		assert null != filter;
		
		return maybe -> maybe.filter(filter);
	}
	
	/**
	 * Builds a function that maps the value wrapped in {@link Maybe} instances using the given function.
	 * 
	 * @param <T> Type of the values.
	 * @param <R> Type of the mapped values.
	 * @param function Function to use to transform the value.
	 * @return The built function.
	 * @see Maybe#map(Function)
	 * @since 1.0
	 */
	public static <T, R> Function<Maybe<? extends T>, Maybe<R>> map(final Function<? super T, ? extends R> function) {
		assert null != function;
		
		return maybe -> maybe.map(function);
	}
	
	/**
	 * Builds a function that extracts the value wrapped in {@link Maybe} instances using the given extractor.
	 * 
	 * @param <T> Type of the values.
	 * @param <R> Type of the extracted values.
	 * @param function Function to use to transform the value.
	 * @return The built function.
	 * @see Maybe#flatMap(Function)
	 * @since 1.0
	 */
	public static <T, R> Function<Maybe<? extends T>, Maybe<R>> flatMap(final Function<? super T, ? extends Maybe<? extends R>> function) {
		assert null != function;
		
		return maybe -> maybe.flatMap(function);
	}
	
	private MaybeFunctions() {
		// Prevent instantiation.
	}
}
