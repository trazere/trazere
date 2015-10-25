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
package com.trazere.core.lang;

import com.trazere.core.imperative.Accumulator;
import com.trazere.core.imperative.FoldAccumulator;
import com.trazere.core.util.Maybe;

/**
 * The {@link ComparableAccumulators} class provides various factories of {@link Accumulator accumulators} related to {@link Comparable comparables}.
 * 
 * @see Accumulator
 * @see Comparable
 * @since 2.0
 */
public class ComparableAccumulators {
	/**
	 * Builds an accumulator of the least value according to their natural order.
	 *
	 * @param <T> Type of the values.
	 * @return The built accumulator.
	 * @since 2.0
	 */
	public static <T extends Comparable<T>> Accumulator<T, Maybe<T>> least() {
		return least(Maybe.<T>none());
	}
	
	/**
	 * Builds an accumulator of the least value according to their natural order.
	 *
	 * @param <T> Type of the values.
	 * @param initialState Initial state.
	 * @return The built accumulator.
	 * @since 2.0
	 */
	public static <T extends Comparable<T>> Accumulator<T, Maybe<T>> least(final T initialState) {
		return least(Maybe.some(initialState));
	}
	
	/**
	 * Builds an accumulator of the least value according to their natural order.
	 *
	 * @param <T> Type of the values.
	 * @param initialState Initial state.
	 * @return The built accumulator.
	 * @since 2.0
	 */
	public static <T extends Comparable<T>> Accumulator<T, Maybe<T>> least(final Maybe<T> initialState) {
		return new FoldAccumulator<T, Maybe<T>>(initialState) {
			@Override
			protected Maybe<T> fold(final Maybe<T> currentState, final T value) {
				return Maybe.some(currentState.isSome() ? ComparableUtils.least(currentState.asSome().getValue(), value) : value);
			}
		};
	}
	
	/**
	 * Builds an accumulator of the greatest value according to their natural order.
	 *
	 * @param <T> Type of the values.
	 * @return The built accumulator.
	 * @since 2.0
	 */
	public static <T extends Comparable<T>> Accumulator<T, Maybe<T>> greatest() {
		return greatest(Maybe.<T>none());
	}
	
	/**
	 * Builds an accumulator of the greatest value according to their natural order.
	 *
	 * @param <T> Type of the values.
	 * @param initialState Initial state.
	 * @return The built accumulator.
	 * @since 2.0
	 */
	public static <T extends Comparable<T>> Accumulator<T, Maybe<T>> greatest(final T initialState) {
		return greatest(Maybe.some(initialState));
	}
	
	/**
	 * Builds an accumulator of the greatest value according to their natural order.
	 *
	 * @param <T> Type of the values.
	 * @param initialState Initial state.
	 * @return The built accumulator.
	 * @since 2.0
	 */
	public static <T extends Comparable<T>> Accumulator<T, Maybe<T>> greatest(final Maybe<T> initialState) {
		return new FoldAccumulator<T, Maybe<T>>(initialState) {
			@Override
			protected Maybe<T> fold(final Maybe<T> currentState, final T value) {
				return Maybe.some(currentState.isSome() ? ComparableUtils.greatest(currentState.asSome().getValue(), value) : value);
			}
		};
	}
	
	private ComparableAccumulators() {
		// Prevent instantiation.
	}
}
