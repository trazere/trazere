/*
. *  Copyright 2006-2015 Julien Dufour
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

/**
 * The {@link LangAccumulators} class provides various factories of {@link Accumulator accumulators} related to the Java language.
 * 
 * @see Accumulator
 * @since 2.0
 */
public class LangAccumulators {
	// TODO: version with true initialState
	/**
	 * Builds a logical accumulator corresponding to a conjonction.
	 * 
	 * @param initialState Initial state.
	 * @return The built accumulator.
	 * @since 2.0
	 */
	public static Accumulator<Boolean, Boolean> and(final boolean initialState) {
		return new FoldAccumulator<Boolean, Boolean>(initialState) {
			@Override
			protected Boolean fold(final Boolean accumulator, final Boolean value) {
				return LangUtils.safeUnbox(accumulator) && LangUtils.safeUnbox(value);
			}
		};
	}
	
	// TODO: version with false initialState
	/**
	 * Builds a logical accumulator corresponding to a disjunction.
	 * 
	 * @param initialState Initial value.
	 * @return The built accumulator.
	 * @since 2.0
	 */
	public static Accumulator<Boolean, Boolean> or(final boolean initialState) {
		return new FoldAccumulator<Boolean, Boolean>(initialState) {
			@Override
			protected Boolean fold(final Boolean accumulator, final Boolean value) {
				return LangUtils.safeUnbox(accumulator) || LangUtils.safeUnbox(value);
			}
		};
	}
	
	/**
	 * Builds an arithmetic accumulator corresponding to a sum.
	 *
	 * @param initialState Initial state.
	 * @return The built accumulator.
	 * @since 2.0
	 */
	public static Accumulator<Integer, Integer> sum(final int initialState) {
		return new Accumulator<Integer, Integer>() {
			private final MutableInt _value = new MutableInt(initialState);
			
			@Override
			public void add(final Integer value) {
				_value.add(value.intValue());
			}
			
			@Override
			public Integer get() {
				return _value.get();
			}
		};
	}
	
	/**
	 * Builds an arithmetic accumulator corresponding to a sum.
	 *
	 * @param initialState Initial state.
	 * @return The built accumulator.
	 * @since 2.0
	 */
	public static Accumulator<Long, Long> sum(final long initialState) {
		return new Accumulator<Long, Long>() {
			private final MutableLong _value = new MutableLong(initialState);
			
			@Override
			public void add(final Long value) {
				_value.add(value.longValue());
			}
			
			@Override
			public Long get() {
				return _value.get();
			}
		};
	}
	
	private LangAccumulators() {
		// Prevents instantiation.
	}
}
