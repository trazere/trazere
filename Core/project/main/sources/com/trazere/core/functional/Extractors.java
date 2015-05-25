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
import com.trazere.core.util.MaybeFunctions;

/**
 * The {@link Extractors} class provides various factories of extractors.
 * <p>
 * An extractor is function that combines a map and a filter operation.
 * 
 * @see Function
 * @see Maybe
 */
public class Extractors {
	/**
	 * Builds an identity extractor.
	 * 
	 * @param <T> Type of the values.
	 * @return The built extractor.
	 */
	public static <T> Function<T, Maybe<T>> identity() {
		return MaybeFunctions.some();
	}
	
	/**
	 * Builds an extractor corresponding to the composition of the given extractors (g . f).
	 * 
	 * @param <A> Type of the arguments.
	 * @param <I> Type of the intermediate values.
	 * @param <R> Type of the results.
	 * @param g Outer extractor.
	 * @param f Inner extractor.
	 * @return The built extractor.
	 */
	public static <A, I, R> Function<A, Maybe<R>> compose(final Function<? super I, ? extends Maybe<? extends R>> g, final Function<? super A, ? extends Maybe<? extends I>> f) {
		assert null != f;
		assert null != g;
		
		return arg -> f.evaluate(arg).flatMap(g);
	}
	
	/**
	 * Builds an extractor that lifts the given predicate.
	 * 
	 * @param <T> Type of the values.
	 * @param predicate Predicate to lift.
	 * @return The built extractor.
	 */
	public static <T> Function<T, Maybe<T>> fromPredicate(final Predicate<? super T> predicate) {
		assert null != predicate;
		
		return (arg) -> predicate.evaluate(arg) ? Maybe.some(arg) : Maybe.<T>none();
	}
	
	/**
	 * Builds an extractor that lifts the given function.
	 * 
	 * @param <A> Type of the arguments.
	 * @param <R> Type of the results.
	 * @param function Function to lift.
	 * @return The built extractor.
	 */
	public static <A, R> Function<A, Maybe<R>> fromFunction(final Function<? super A, ? extends R> function) {
		assert null != function;
		
		return (arg) -> Maybe.<R>some(function.evaluate(arg));
	}
	
	private Extractors() {
		// Prevents instantiation.
	}
}
