/*
 *  Copyright 2006-2011 Julien Dufour
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
import com.trazere.util.reference.MutableReference;
import java.util.Collection;

/**
 * The {@link Accumulators} class provides various common accumulators.
 */
public class Accumulators {
	/**
	 * Builds an accumulator which discards the accumulated values.
	 * 
	 * @param <V> Type of the accumulated values.
	 * @param <X> Type of the exceptions.
	 * @return The built accumulator.
	 */
	@SuppressWarnings("unchecked")
	public static <V, X extends Exception> Accumulator<Void, V, X> discard() {
		return (Accumulator<Void, V, X>) _DISCARD;
	}
	
	private static final Accumulator<Void, ?, ?> _DISCARD = new AbstractAccumulator<Void, Object, RuntimeException>() {
		public Void get() {
			return null;
		}
		
		public void add(final Object value) {
			// Nothing to do.
		}
	};
	
	/**
	 * Builds an accumulator which populates the given collection.
	 * 
	 * @param <T> Type of the accumulated values.
	 * @param <C> Type of the collection.
	 * @param <X> Type of the exceptions.
	 * @param collection The collection to populate.
	 * @return The built accumulator.
	 */
	public static <T, C extends Collection<? super T>, X extends Exception> Accumulator<C, T, X> collection(final C collection) {
		assert null != collection;
		
		return new AbstractAccumulator<C, T, X>() {
			public C get() {
				return collection;
			}
			
			public void add(final T value)
			throws X {
				collection.add(value);
			}
		};
	}
	
	/**
	 * Builds an accumulator which updates the given reference.
	 * 
	 * @param <T> Type of the accumulated values.
	 * @param <R> Type of the reference.
	 * @param <X> Type of the exceptions.
	 * @param reference The reference to set.
	 * @return The built accumulator.
	 */
	public static <T, R extends MutableReference<T>, X extends Exception> Accumulator<R, T, X> reference(final R reference) {
		assert null != reference;
		
		return new AbstractAccumulator<R, T, X>() {
			public R get() {
				return reference;
			}
			
			public void add(final T value)
			throws X {
				reference.update(value);
			}
		};
	}
	
	/**
	 * Builds an accumulator which left folds the accumulated values using the given function and initial value.
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
		
		return new AbstractFoldAccumulator<T, V, X>(initialValue) {
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
		return new AbstractFoldAccumulator<Boolean, Boolean, X>(initialValue) {
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
		return new AbstractFoldAccumulator<Boolean, Boolean, X>(initialValue) {
			@Override
			protected Boolean fold(final Boolean accumulator, final Boolean value) {
				return LangUtils.get(accumulator, false) || LangUtils.get(value, false);
			}
		};
	}
	
	private Accumulators() {
		// Prevent instantiation.
	}
}
