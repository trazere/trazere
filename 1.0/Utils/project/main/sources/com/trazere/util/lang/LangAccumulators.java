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
package com.trazere.util.lang;

import com.trazere.util.accumulator.Accumulator1;
import com.trazere.util.accumulator.BaseAccumulator1;
import com.trazere.util.accumulator.FoldAccumulator1;
import com.trazere.util.reference.MutableReference;
import com.trazere.util.type.Maybe;
import java.util.Comparator;

/**
 * The {@link LangAccumulators} class provides various factories of accumulators related to the language.
 */
public class LangAccumulators {
	/**
	 * Builds a logical accumulator corresponding to a conjonction.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param initialResult The initial result.
	 * @return The built accumulator.
	 */
	public static <X extends Exception> Accumulator1<Boolean, Boolean, X> and(final boolean initialResult) {
		return new FoldAccumulator1<Boolean, Boolean, X>(initialResult) {
			@Override
			protected Boolean fold(final Boolean accumulator, final Boolean value) {
				return LangUtils.get(accumulator, false) && LangUtils.get(value, false);
			}
		};
	}
	
	/**
	 * Builds a logical accumulator corresponding to a disjunction.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param initialResult The initial value.
	 * @return The built accumulator.
	 */
	public static <X extends Exception> Accumulator1<Boolean, Boolean, X> or(final boolean initialResult) {
		return new FoldAccumulator1<Boolean, Boolean, X>(initialResult) {
			@Override
			protected Boolean fold(final Boolean accumulator, final Boolean value) {
				return LangUtils.get(accumulator, false) || LangUtils.get(value, false);
			}
		};
	}
	
	/**
	 * Builds an arithmetic accumulator corresponding to a sum.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param initialResult The initial result.
	 * @return The built accumulator.
	 */
	public static <X extends Exception> Accumulator1<Integer, Integer, X> sum(final int initialResult) {
		return new BaseAccumulator1<Integer, Integer, X>() {
			private final MutableInt _value = new MutableInt(initialResult);
			
			@Override
			public void add(final Integer value)
			throws X {
				assert null != value;
				
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
	 * @param <X> Type of the exceptions.
	 * @param initialResult The initial result.
	 * @return The built accumulator.
	 */
	public static <X extends Exception> Accumulator1<Long, Long, X> sum(final long initialResult) {
		return new BaseAccumulator1<Long, Long, X>() {
			private final MutableLong _value = new MutableLong(initialResult);
			
			@Override
			public void add(final Long value)
			throws X {
				assert null != value;
				
				_value.add(value.longValue());
			}
			
			@Override
			public Long get() {
				return _value.get();
			}
		};
	}
	
	// TODO: move to CollectionAccumulators or ComparatorAccumulators ?
	/**
	 * Builds an accumulator of the greatest value according to the given comparator.
	 * 
	 * @param <T> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param comparator Comparator of the values.
	 * @return The built accumulator.
	 */
	public static <T, X extends Exception> Accumulator1<T, Maybe<T>, X> greatest(final Comparator<? super T> comparator) {
		assert null != comparator;
		
		return new BaseAccumulator1<T, Maybe<T>, X>() {
			private final MutableReference<T> _result = new MutableReference<T>();
			
			@Override
			public void add(final T value)
			throws X {
				if (_result.isSet()) {
					if (comparator.compare(value, _result.get()) > 1) {
						_result.update(value);
					}
				} else {
					_result.set(value);
				}
			}
			
			@Override
			public Maybe<T> get() {
				return _result.asMaybe();
			}
		};
	}
	
	private LangAccumulators() {
		// Prevents instantiation.
	}
}
