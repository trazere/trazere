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
package com.trazere.core.functional;

import com.trazere.core.util.Maybe;

/**
 * The {@link ExtractorUtils} class provides various helpers regarding extractors.
 * <p>
 * An extractor is function that combines a map and a filter operation.
 * 
 * @see Function
 * @see Maybe
 */
public class ExtractorUtils {
	/**
	 * Filters the given extractor using the given filter.
	 *
	 * @param <A> Type of the arguments.
	 * @param <R> Type of the results.
	 * @param extractor Extractor to transform.
	 * @param filter Predicate to use to filter the results.
	 * @return The built extractor.
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
	 * @param <TR> Type of the transformed results.
	 * @param extractor Extractor to transform.
	 * @param function Function to use to transform the results.
	 * @return The built extractor.
	 */
	public static <A, R, TR> Function<A, Maybe<TR>> map(final Function<? super A, ? extends Maybe<? extends R>> extractor, final Function<? super R, ? extends TR> function) {
		assert null != extractor;
		assert null != function;
		
		return arg -> extractor.evaluate(arg).map(function);
	}
	
	/**
	 * Transforms and flattens the given extractor using the given extractor.
	 * <p>
	 * This method is equivalent to extractor composition.
	 * 
	 * @param <A> Type of the arguments.
	 * @param <R> Type of the results.
	 * @param <ER> Type of the extracted results.
	 * @param extractor Extractor to transform.
	 * @param flatMapExtractor Extractor to use to extract the results.
	 * @return The built extractor.
	 */
	public static <A, R, ER> Function<A, Maybe<ER>> flatMap(final Function<? super A, ? extends Maybe<? extends R>> extractor, final Function<? super R, ? extends Maybe<? extends ER>> flatMapExtractor) {
		assert null != extractor;
		assert null != flatMapExtractor;
		
		return arg -> extractor.evaluate(arg).flatMap(flatMapExtractor);
	}
	
	private ExtractorUtils() {
		// Prevents instantiation.
	}
}
