/*
. *  Copyright 2006-2013 Julien Dufour
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
 * The {@link LangAccumulators} class provides various factories of {@link Accumulator accumulators} related to the language.
 */
public class LangAccumulators {
	/**
	 * Builds a logical accumulator corresponding to a conjonction.
	 * 
	 * @param initialState Initial state.
	 * @return The built accumulator.
	 */
	public static Accumulator<Boolean, Boolean> and(final boolean initialState) {
		return new FoldAccumulator<Boolean, Boolean>(initialState) {
			@Override
			protected Boolean fold(final Boolean accumulator, final Boolean value) {
				return LangUtils.safeUnbox(accumulator) && LangUtils.safeUnbox(value);
			}
		};
	}
	
	/**
	 * Builds a logical accumulator corresponding to a disjunction.
	 * 
	 * @param initialState Initial value.
	 * @return The built accumulator.
	 */
	public static Accumulator<Boolean, Boolean> or(final boolean initialState) {
		return new FoldAccumulator<Boolean, Boolean>(initialState) {
			@Override
			protected Boolean fold(final Boolean accumulator, final Boolean value) {
				return LangUtils.safeUnbox(accumulator) || LangUtils.safeUnbox(value);
			}
		};
	}
	
	//	/**
	//	 * Builds an arithmetic accumulator corresponding to a sum.
	//	 *
	//	 * @param <X> Type of the exceptions.
	//	 * @param initialResult The initial result.
	//	 * @return The built accumulator.
	//	 */
	//	public static <X extends Exception> Accumulator1<Integer, Integer, X> sum(final int initialResult) {
	//		return new BaseAccumulator1<Integer, Integer, X>() {
	//			private final MutableInt _value = new MutableInt(initialResult);
	//
	//			@Override
	//			public void add(final Integer value)
	//			throws X {
	//				assert null != value;
	//
	//				_value.add(value.intValue());
	//			}
	//
	//			@Override
	//			public Integer get() {
	//				return _value.get();
	//			}
	//		};
	//	}
	//
	//	/**
	//	 * Builds an arithmetic accumulator corresponding to a sum.
	//	 *
	//	 * @param <X> Type of the exceptions.
	//	 * @param initialResult The initial result.
	//	 * @return The built accumulator.
	//	 */
	//	public static <X extends Exception> Accumulator1<Long, Long, X> sum(final long initialResult) {
	//		return new BaseAccumulator1<Long, Long, X>() {
	//			private final MutableLong _value = new MutableLong(initialResult);
	//
	//			@Override
	//			public void add(final Long value)
	//			throws X {
	//				assert null != value;
	//
	//				_value.add(value.longValue());
	//			}
	//
	//			@Override
	//			public Long get() {
	//				return _value.get();
	//			}
	//		};
	//	}
	
	private LangAccumulators() {
		// Prevents instantiation.
	}
}
