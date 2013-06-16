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

import com.trazere.util.collection.Multimap;
import com.trazere.util.function.Function2;
import com.trazere.util.lang.LangUtils;
import com.trazere.util.lang.MutableInt;
import com.trazere.util.lang.MutableLong;
import com.trazere.util.reference.MutableReference;
import java.util.Collection;
import java.util.Map;

/**
 * The {@link Accumulators} class provides various common accumulators.
 */
public class Accumulators {
	/**
	 * Builds an accumulator that ignores the accumulated values.
	 * 
	 * @param <T> Type of the values.
	 * @param <S> Type of the states.
	 * @param <X> Type of the exceptions.
	 * @param result The result.
	 * @return The built accumulator.
	 */
	public static <T, S, X extends Exception> Accumulator1<T, S, X> constant1(final S result) {
		return new BaseAccumulator1<T, S, X>() {
			@Override
			public S get() {
				return result;
			}
			
			@Override
			public void add(final T value) {
				// Nothing to do.
			}
		};
	}
	
	/**
	 * Builds a pair accumulator that ignores the accumulated values.
	 * 
	 * @param <T1> Type of the first values.
	 * @param <T2> Type of the second values.
	 * @param <S> Type of the states.
	 * @param <X> Type of the exceptions.
	 * @param result The result.
	 * @return The built accumulator.
	 */
	public static <T1, T2, S, X extends Exception> Accumulator2<T1, T2, S, X> constant2(final S result) {
		return new BaseAccumulator2<T1, T2, S, X>() {
			@Override
			public S get() {
				return result;
			}
			
			@Override
			public void add(final T1 value1, final T2 value2) {
				// Nothing to do.
			}
		};
	}
	
	/**
	 * Builds an accumulator that updates the given reference.
	 * 
	 * @param <T> Type of the values.
	 * @param <R> Type of the reference.
	 * @param <X> Type of the exceptions.
	 * @param reference The reference to set.
	 * @return The built accumulator.
	 */
	public static <T, R extends MutableReference<T>, X extends Exception> Accumulator1<T, R, X> reference(final R reference) {
		assert null != reference;
		
		return new BaseAccumulator1<T, R, X>() {
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
	 * @param <T> Type of the values.
	 * @param <C> Type of the collection.
	 * @param <X> Type of the exceptions.
	 * @param collection The collection to populate.
	 * @return The built accumulator.
	 */
	public static <T, C extends Collection<? super T>, X extends Exception> Accumulator1<T, C, X> collection(final C collection) {
		assert null != collection;
		
		return new BaseAccumulator1<T, C, X>() {
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
	 * Builds an accumulator that populates the given map.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map.
	 * @param <X> Type of the exceptions.
	 * @param map The map to populate.
	 * @return The built accumulator.
	 */
	public static <K, V, M extends Map<? super K, ? super V>, X extends Exception> Accumulator2<K, V, M, X> map(final M map) {
		assert null != map;
		
		return new BaseAccumulator2<K, V, M, X>() {
			@Override
			public M get() {
				return map;
			}
			
			@Override
			public void add(final K key, final V value)
			throws X {
				map.put(key, value);
			}
		};
	}
	
	/**
	 * Builds an accumulator that populates the given multimap.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the multimap.
	 * @param <X> Type of the exceptions.
	 * @param multimap The map to populate.
	 * @return The built accumulator.
	 */
	public static <K, V, M extends Multimap<? super K, ? super V, ?>, X extends Exception> Accumulator2<K, V, M, X> multimap(final M multimap) {
		assert null != multimap;
		
		return new BaseAccumulator2<K, V, M, X>() {
			@Override
			public M get() {
				return multimap;
			}
			
			@Override
			public void add(final K key, final V value)
			throws X {
				multimap.put(key, value);
			}
		};
	}
	
	/**
	 * Builds an accumulator that left folds the accumulated values using the given binary operator and initial result.
	 * 
	 * @param <R> Type of the result.
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param operator The operator.
	 * @param initialResult The initial result.
	 * @return The built accumulator.
	 */
	public static <R, V, X extends Exception> Accumulator1<V, R, X> fold(final Function2<? super R, ? super V, ? extends R, ? extends X> operator, final R initialResult) {
		assert null != operator;
		
		return new FoldAccumulator1<R, V, X>(initialResult) {
			@Override
			protected R fold(final R result, final V value)
			throws X {
				return operator.evaluate(result, value);
			}
		};
	}
	
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
	 * @param initialResult The initial result.
	 * @return The built accumulator.
	 */
	public static <X extends Exception> Accumulator1<Long, Long, X> sum(final long initialResult) {
		return new BaseAccumulator1<Long, Long, X>() {
			private final MutableLong _value = new MutableLong(initialResult);
			
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
