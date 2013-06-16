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
package com.trazere.util.accumulator;

import com.trazere.util.function.Function2;
import com.trazere.util.lang.LangUtils;
import com.trazere.util.lang.MutableInt;
import com.trazere.util.lang.MutableLong;
import com.trazere.util.reference.MutableReference;
import java.util.Collection;

/**
 * The {@link Accumulators} class provides various common accumulators.
 */
public class Accumulators {
	/**
	 * Builds an accumulator that ignores the accumulated values.
	 * 
	 * @param <T> Type of the accumulated values.
	 * @param <V> Type of the accumulation arguments.
	 * @param <X> Type of the exceptions.
	 * @param result
	 * @return The built accumulator.
	 */
	public static <T, V, X extends Exception> Accumulator<T, V, X> constant(final T result) {
		return new BaseAccumulator<T, V, X>() {
			@Override
			public T get() {
				return result;
			}
			
			@Override
			public void add(final V value) {
				// Nothing to do.
			}
		};
	}
	
	/**
	 * Builds an accumulator that updates the given reference.
	 * 
	 * @param <T> Type of the accumulated values.
	 * @param <R> Type of the reference.
	 * @param <X> Type of the exceptions.
	 * @param reference The reference to set.
	 * @return The built accumulator.
	 */
	public static <T, R extends MutableReference<T>, X extends Exception> Accumulator<R, T, X> reference(final R reference) {
		assert null != reference;
		
		return new BaseAccumulator<R, T, X>() {
			@Override
			public R get() {
				return reference;
			}
			
			@Override
			public void add(final T value)
			throws X {
				reference.update(value);
			}
		};
	}

	/**
	 * Builds an accumulator that populates the given collection.
	 * 
	 * @param <T> Type of the accumulated values.
	 * @param <C> Type of the collection.
	 * @param <X> Type of the exceptions.
	 * @param collection The collection to populate.
	 * @return The built accumulator.
	 */
	public static <T, C extends Collection<? super T>, X extends Exception> Accumulator<C, T, X> collection(final C collection) {
		assert null != collection;
		
		return new BaseAccumulator<C, T, X>() {
			@Override
			public C get() {
				return collection;
			}
			
			@Override
			public void add(final T value)
			throws X {
				collection.add(value);
			}
		};
	}
	
	/**
	 * Builds an accumulator that left folds the accumulated values using the given function and initial value.
	 * 
	 * @param <T> Type of the accumulated values.
	 * @param <V> Type of the accumulation arguments.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @param initialValue The initial value.
	 * @return The built accumulator.
	 */
	public static <T, V, X extends Exception> Accumulator<T, V, X> fold(final Function2<? super T, ? super V, ? extends T, ? extends X> function, final T initialValue) {
		assert null != function;
		
		return new FoldAccumulator<T, V, X>(initialValue) {
			@Override
			protected T fold(final T accumulator, final V value)
			throws X {
				return function.evaluate(accumulator, value);
			}
		};
	}
	
	/**
	 * Builds a logical accumulator corresponding to a conjonction.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param initialValue The initial value.
	 * @return The built accumulator.
	 */
	public static <X extends Exception> Accumulator<Boolean, Boolean, X> and(final boolean initialValue) {
		return new FoldAccumulator<Boolean, Boolean, X>(initialValue) {
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
	 * @param initialValue The initial value.
	 * @return The built accumulator.
	 */
	public static <X extends Exception> Accumulator<Boolean, Boolean, X> or(final boolean initialValue) {
		return new FoldAccumulator<Boolean, Boolean, X>(initialValue) {
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
	 * @param initial The initial value.
	 * @return The built accumulator.
	 */
	public static <X extends Exception> Accumulator<Integer, Integer, X> sum(final int initial) {
		return new BaseAccumulator<Integer, Integer, X>() {
			private final MutableInt _value = new MutableInt(initial);
			
			@Override
			public Integer get() {
				return _value.get();
			}
			
			@Override
			public void add(final Integer value)
			throws X {
				assert null != value;
				
				_value.add(value.intValue());
			}
		};
	}
	
	/**
	 * Builds an arithmetic accumulator corresponding to a sum.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param initial The initial value.
	 * @return The built accumulator.
	 */
	public static <X extends Exception> Accumulator<Long, Long, X> sum(final long initial) {
		return new BaseAccumulator<Long, Long, X>() {
			private final MutableLong _value = new MutableLong(initial);
			
			@Override
			public Long get() {
				return _value.get();
			}
			
			@Override
			public void add(final Long value)
			throws X {
				assert null != value;
				
				_value.add(value.longValue());
			}
		};
	}
	
	private Accumulators() {
		// Prevents instantiation.
	}
}
