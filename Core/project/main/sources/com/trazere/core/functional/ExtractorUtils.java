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
package com.trazere.core.functional;

import com.trazere.core.util.Maybe;

/**
 * The {@link ExtractorUtils} class provides various utilities regarding extractors.
 * <p>
 * An extractor is {@link Function function} that combines a map operation and a filter operation.
 * 
 * @see Function
 * @see Maybe
 * @since 1.0
 */
public class ExtractorUtils {
	/**
	 * Builds an extractor corresponding to the composition of the given extractors (g . f).
	 * 
	 * @param <A> Type of the arguments.
	 * @param <I> Type of the intermediate values.
	 * @param <R> Type of the results.
	 * @param g Outer extractor.
	 * @param f Inner extractor.
	 * @return The built extractor.
	 * @since 1.0
	 */
	public static <A, I, R> Function<A, Maybe<R>> compose(final Function<? super I, ? extends Maybe<? extends R>> g, final Function<? super A, ? extends Maybe<? extends I>> f) {
		assert null != f;
		assert null != g;
		
		return arg -> f.evaluate(arg).flatMap(g);
	}
	
	/**
	 * Filters the given extractor using the given filter.
	 *
	 * @param <A> Type of the arguments.
	 * @param <R> Type of the results.
	 * @param extractor Extractor to transform.
	 * @param filter Predicate to use to filter the results.
	 * @return The built extractor.
	 * @since 1.0
	 */
	public static <A, R> Function<A, Maybe<? extends R>> filter(final Function<? super A, ? extends Maybe<? extends R>> extractor, final Predicate<? super R> filter) {
		assert null != extractor;
		assert null != filter;
		
		return arg -> extractor.evaluate(arg).filter(filter);
	}
	
	/**
	 * Transforms the given extractor using the given function.
	 * 
	 * @param <A> Type of the arguments.
	 * @param <R> Type of the results.
	 * @param
	 * 		<TR>
	 *        Type of the transformed results.
	 * @param extractor Extractor to transform.
	 * @param function Function to use to transform the results.
	 * @return The built extractor.
	 * @since 1.0
	 */
	public static <A, R, TR> Function<A, Maybe<TR>> map(final Function<? super A, ? extends Maybe<? extends R>> extractor, final Function<? super R, ? extends TR> function) {
		assert null != extractor;
		assert null != function;
		
		return arg -> extractor.evaluate(arg).map(function);
	}
	
	private ExtractorUtils() {
		// Prevents instantiation.
	}
}
