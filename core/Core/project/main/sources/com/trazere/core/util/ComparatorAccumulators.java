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

import com.trazere.core.imperative.Accumulator;
import com.trazere.core.imperative.FoldAccumulator;
import java.util.Comparator;

/**
 * The {@link ComparatorAccumulators} class provides various factories of accumulators related to {@link Comparator comparators}.
 */
public class ComparatorAccumulators {
	/**
	 * Builds an accumulator of the least value according to the given comparator.
	 *
	 * @param <T> Type of the values.
	 * @param comparator Comparator of the values.
	 * @return The built accumulator.
	 */
	public static <T> Accumulator<T, Maybe<T>> least(final Comparator<? super T> comparator) {
		return least(comparator, Maybe.<T>none());
	}
	
	/**
	 * Builds an accumulator of the least value according to the given comparator.
	 *
	 * @param <T> Type of the values.
	 * @param comparator Comparator of the values.
	 * @param initialState Initial state.
	 * @return The built accumulator.
	 */
	public static <T> Accumulator<T, Maybe<T>> least(final Comparator<? super T> comparator, final T initialState) {
		return least(comparator, Maybe.some(initialState));
	}
	
	/**
	 * Builds an accumulator of the least value according to the given comparator.
	 *
	 * @param <T> Type of the values.
	 * @param comparator Comparator of the values.
	 * @param initialState Initial state.
	 * @return The built accumulator.
	 */
	public static <T> Accumulator<T, Maybe<T>> least(final Comparator<? super T> comparator, final Maybe<T> initialState) {
		assert null != comparator;
		
		return new FoldAccumulator<T, Maybe<T>>(initialState) {
			@Override
			protected Maybe<T> fold(final Maybe<T> currentState, final T value) {
				return Maybe.some(currentState.isSome() ? ComparatorUtils.least(comparator, currentState.asSome().getValue(), value) : value);
			}
		};
	}
	
	/**
	 * Builds an accumulator of the greatest value according to the given comparator.
	 *
	 * @param <T> Type of the values.
	 * @param comparator Comparator of the values.
	 * @return The built accumulator.
	 */
	public static <T> Accumulator<T, Maybe<T>> greatest(final Comparator<? super T> comparator) {
		return greatest(comparator, Maybe.<T>none());
	}
	
	/**
	 * Builds an accumulator of the greatest value according to the given comparator.
	 *
	 * @param <T> Type of the values.
	 * @param comparator Comparator of the values.
	 * @param initialState Initial state.
	 * @return The built accumulator.
	 */
	public static <T> Accumulator<T, Maybe<T>> greatest(final Comparator<? super T> comparator, final T initialState) {
		return greatest(comparator, Maybe.some(initialState));
	}
	
	/**
	 * Builds an accumulator of the greatest value according to the given comparator.
	 *
	 * @param <T> Type of the values.
	 * @param comparator Comparator of the values.
	 * @param initialState Initial state.
	 * @return The built accumulator.
	 */
	public static <T> Accumulator<T, Maybe<T>> greatest(final Comparator<? super T> comparator, final Maybe<T> initialState) {
		assert null != comparator;
		
		return new FoldAccumulator<T, Maybe<T>>(initialState) {
			@Override
			protected Maybe<T> fold(final Maybe<T> currentState, final T value) {
				return Maybe.some(currentState.isSome() ? ComparatorUtils.greatest(comparator, currentState.asSome().getValue(), value) : value);
			}
		};
	}
	
	private ComparatorAccumulators() {
		// Prevent instantiation.
	}
}
