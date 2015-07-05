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
package com.trazere.util.accumulator;

import com.trazere.util.function.Function1;
import com.trazere.util.function.Predicate1;
import com.trazere.util.function.Predicate2;
import com.trazere.util.function.Predicates;
import com.trazere.util.lang.Counter;
import com.trazere.util.reference.MutableReference;
import com.trazere.util.type.Maybe;
import com.trazere.util.type.Tuple2;

/**
 * The {@link Accumulators} class provides various factories of accumulators.
 * 
 * @deprecated Use {@link com.trazere.core}.
 */
@Deprecated
public class Accumulators {
	/**
	 * Builds an accumulator that counts the accumulations (number of times some value is accumulated).
	 * 
	 * @param <T> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @return The built accumulator.
	 * @deprecated Use {@link com.trazere.core.imperative.Accumulators#counter()}.
	 */
	@Deprecated
	public static <T, X extends Exception> Accumulator1<T, Integer, X> counter() {
		return new BaseAccumulator1<T, Integer, X>() {
			private final Counter _result = new Counter();
			
			@Override
			public void add(final T value) {
				_result.inc();
			}
			
			@Override
			public Integer get() {
				return _result.get();
			}
		};
	}
	
	/**
	 * Builds a accumulators that forward the accumulated values and results to the given accumulator.
	 * 
	 * @param <T> Type of the values.
	 * @param <A> Type of the delegate accumulator.
	 * @param <X> Type of the exceptions.
	 * @param accumulator Delegate accumulator.
	 * @return The built accumulator.
	 * @deprecated Use {@link com.trazere.core.imperative.AccumulatorUtils#delegated(com.trazere.core.imperative.Accumulator)}.
	 */
	@Deprecated
	public static <T, A extends Accumulator1<? super T, ?, ? extends X>, X extends Exception> Accumulator1<T, A, X> delegate(final A accumulator) {
		return new BaseAccumulator1<T, A, X>() {
			@Override
			public void add(final T value)
			throws X {
				accumulator.add(value);
			}
			
			@Override
			public void addAll(final Iterable<? extends T> values)
			throws X {
				accumulator.addAll(values);
			}
			
			@Override
			public A get() {
				return accumulator;
			}
		};
	}
	
	/**
	 * Builds a accumulators that forward the accumulated values and results to the given accumulator.
	 * 
	 * @param <T1> Type of the first values.
	 * @param <T2> Type of the second values.
	 * @param <A> Type of the delegate accumulator.
	 * @param <X> Type of the exceptions.
	 * @param accumulator Delegate accumulator.
	 * @return The built accumulator.
	 * @deprecated Use {@link com.trazere.core.imperative.AccumulatorUtils#delegated2(com.trazere.core.imperative.Accumulator2)}.
	 */
	@Deprecated
	public static <T1, T2, A extends Accumulator2<? super T1, ? super T2, ?, ? extends X>, X extends Exception> Accumulator2<T1, T2, A, X> delegate(final A accumulator) {
		return new BaseAccumulator2<T1, T2, A, X>() {
			@Override
			public void add(final T1 value1, final T2 value2)
			throws X {
				accumulator.add(value1, value2);
			}
			
			@Override
			public void addAll(final Iterable<? extends Tuple2<? extends T1, ? extends T2>> values)
			throws X {
				accumulator.addAll(values);
			}
			
			@Override
			public A get() {
				return accumulator;
			}
		};
	}
	
	/**
	 * Builds an accumulator that filters the accumulated values using the given predicate.
	 * 
	 * @param <T> Type of the values.
	 * @param <S> Type of the states.
	 * @param <X> Type of the exceptions.
	 * @param predicate Filter predicate to use.
	 * @param accumulator Accumulator to filter.
	 * @return The built accumulator.
	 * @deprecated Use
	 *             {@link com.trazere.core.imperative.AccumulatorUtils#filter(com.trazere.core.imperative.Accumulator, com.trazere.core.functional.Predicate)}.
	 */
	@Deprecated
	public static <T, S, X extends Exception> Accumulator1<T, S, X> filter(final Predicate1<? super T, ? extends X> predicate, final Accumulator1<? super T, ? extends S, ? extends X> accumulator) {
		assert null != predicate;
		assert null != accumulator;
		
		return new BaseAccumulator1<T, S, X>() {
			@Override
			public void add(final T value)
			throws X {
				if (predicate.evaluate(value)) {
					accumulator.add(value);
				}
			}
			
			@Override
			public S get() {
				return accumulator.get();
			}
		};
	}
	
	/**
	 * Builds an accumulator that filters the accumulated values using the given predicate.
	 * 
	 * @param <T1> Type of the first values.
	 * @param <T2> Type of the second values.
	 * @param <S> Type of the states.
	 * @param <X> Type of the exceptions.
	 * @param predicate Filter predicate to use.
	 * @param accumulator The accumulator to filter.
	 * @return The built accumulator.
	 * @deprecated Use
	 *             {@link com.trazere.core.imperative.AccumulatorUtils#filter2(com.trazere.core.imperative.Accumulator2, com.trazere.core.functional.Predicate2)}
	 *             .
	 */
	@Deprecated
	public static <T1, T2, S, X extends Exception> Accumulator2<T1, T2, S, X> filter(final Predicate2<? super T1, ? super T2, ? extends X> predicate, final Accumulator2<? super T1, ? super T2, ? extends S, ? extends X> accumulator) {
		assert null != predicate;
		assert null != accumulator;
		
		return new BaseAccumulator2<T1, T2, S, X>() {
			@Override
			public void add(final T1 value1, final T2 value2)
			throws X {
				if (predicate.evaluate(value1, value2)) {
					accumulator.add(value1, value2);
				}
			}
			
			@Override
			public S get() {
				return accumulator.get();
			}
		};
	}
	
	/**
	 * Builds an accumulator that maps the accumulated values using the given function.
	 * 
	 * @param <T> Type of the values.
	 * @param <RT> Type of the result values.
	 * @param <S> Type of the states.
	 * @param <X> Type of the exceptions.
	 * @param function Mapping function to use.
	 * @param accumulator Accumulator to map.
	 * @return The built accumulator.
	 * @deprecated Use {@link com.trazere.core.imperative.AccumulatorUtils#map(com.trazere.core.imperative.Accumulator, com.trazere.core.functional.Function)}.
	 */
	@Deprecated
	public static <T, RT, S, X extends Exception> Accumulator1<T, S, X> map(final Function1<? super T, ? extends RT, ? extends X> function, final Accumulator1<? super RT, ? extends S, ? extends X> accumulator) {
		assert null != function;
		assert null != accumulator;
		
		return new BaseAccumulator1<T, S, X>() {
			@Override
			public void add(final T value)
			throws X {
				accumulator.add(function.evaluate(value));
			}
			
			@Override
			public S get() {
				return accumulator.get();
			}
		};
	}
	
	/**
	 * Builds an accumulator that maps the accumulated values using the given function.
	 * 
	 * @param <T> Type of the values.
	 * @param <RT> Type of the result values.
	 * @param <S> Type of the states.
	 * @param <X> Type of the exceptions.
	 * @param function Mapping function to use.
	 * @param accumulator Accumulator to map.
	 * @return The built accumulator.
	 * @deprecated Use
	 *             {@link com.trazere.core.imperative.AccumulatorUtils#extractAll(com.trazere.core.imperative.Accumulator, com.trazere.core.functional.Function)}
	 *             .
	 */
	@Deprecated
	public static <T, RT, S, X extends Exception> Accumulator1<T, S, X> flatMap(final Function1<? super T, ? extends Iterable<? extends RT>, ? extends X> function, final Accumulator1<? super RT, ? extends S, ? extends X> accumulator) {
		assert null != function;
		assert null != accumulator;
		
		return new BaseAccumulator1<T, S, X>() {
			@Override
			public void add(final T value)
			throws X {
				accumulator.addAll(function.evaluate(value));
			}
			
			@Override
			public S get() {
				return accumulator.get();
			}
		};
	}
	
	/**
	 * Builds an accumulator that filters and transforms the accumulated values using the given extractor.
	 * 
	 * @param <T> Type of the values.
	 * @param <RT> Type of the result values.
	 * @param <S> Type of the states.
	 * @param <X> Type of the exceptions.
	 * @param extractor Extractor to use.
	 * @param accumulator Accumulator to transform.
	 * @return The built accumulator.
	 * @deprecated Use {@link #extract(Function1, Accumulator1)}
	 */
	@Deprecated
	public static <T, RT, S, X extends Exception> Accumulator1<T, S, X> mapFilter(final Function1<? super T, ? extends Maybe<? extends RT>, ? extends X> extractor, final Accumulator1<? super RT, ? extends S, ? extends X> accumulator) {
		return extract(extractor, accumulator);
	}
	
	/**
	 * Builds an accumulator that extracts the values accumulated in the given accumulator using the given extractor.
	 * 
	 * @param <T> Type of the values.
	 * @param <RT> Type of the result values.
	 * @param <S> Type of the states.
	 * @param <X> Type of the exceptions.
	 * @param extractor Extractor to use.
	 * @param accumulator Accumulator to transform.
	 * @return The built accumulator.
	 * @deprecated Use
	 *             {@link com.trazere.core.imperative.AccumulatorUtils#extract(com.trazere.core.imperative.Accumulator, com.trazere.core.functional.Function)}.
	 */
	@Deprecated
	public static <T, RT, S, X extends Exception> Accumulator1<T, S, X> extract(final Function1<? super T, ? extends Maybe<? extends RT>, ? extends X> extractor, final Accumulator1<? super RT, ? extends S, ? extends X> accumulator) {
		assert null != extractor;
		assert null != accumulator;
		
		return new BaseAccumulator1<T, S, X>() {
			@Override
			public void add(final T value)
			throws X {
				final Maybe<? extends RT> result = extractor.evaluate(value);
				if (result.isSome()) {
					accumulator.add(result.asSome().getValue());
				}
			}
			
			@Override
			public S get() {
				return accumulator.get();
			}
		};
	}
	
	/**
	 * Curries the given pair accumulator.
	 * 
	 * @param <T1> Type of the first values.
	 * @param <T2> Type of the second values.
	 * @param <S> Type of the states.
	 * @param <X> Type of the exceptions.
	 * @param accumulator Accumulator to curry.
	 * @return The built accumulator.
	 * @deprecated Use {@link com.trazere.core.imperative.AccumulatorUtils#curry(com.trazere.core.imperative.Accumulator)}.
	 */
	@Deprecated
	public static <T1, T2, S, X extends Exception> Accumulator2<T1, T2, S, X> curry(final Accumulator1<? super Tuple2<? super T1, ? super T2>, ? extends S, ? extends X> accumulator) {
		assert null != accumulator;
		
		return new BaseAccumulator2<T1, T2, S, X>() {
			@Override
			public void add(final T1 value1, final T2 value2)
			throws X {
				accumulator.add(Tuple2.build(value1, value2));
			}
			
			@Override
			public S get() {
				return accumulator.get();
			}
		};
	}
	
	/**
	 * Uncurries the given two-arguments accumulator.
	 * 
	 * @param <T1> Type of the first values.
	 * @param <T2> Type of the second values.
	 * @param <S> Type of the states.
	 * @param <X> Type of the exceptions.
	 * @param accumulator Accumulator to uncurry.
	 * @return The built accumulator.
	 * @deprecated Use {@link com.trazere.core.imperative.AccumulatorUtils#uncurry(com.trazere.core.imperative.Accumulator2)}.
	 */
	@Deprecated
	public static <T1, T2, S, X extends Exception> Accumulator1<Tuple2<T1, T2>, S, X> uncurry(final Accumulator2<? super T1, ? super T2, ? extends S, ? extends X> accumulator) {
		assert null != accumulator;
		
		return new BaseAccumulator1<Tuple2<T1, T2>, S, X>() {
			@Override
			public void add(final Tuple2<T1, T2> value)
			throws X {
				accumulator.add(value.getFirst(), value.getSecond());
			}
			
			@Override
			public S get() {
				return accumulator.get();
			}
		};
	}
	
	/**
	 * Builds an accumulator that ignores the accumulated values.
	 * 
	 * @param <T> Type of the values.
	 * @param <S> Type of the states.
	 * @param <X> Type of the exceptions.
	 * @param state State of the accumulator. May be <code>null</code>.
	 * @return The built accumulator.
	 * @deprecated Use {@link com.trazere.core.imperative.Accumulators#constant(Object)}.
	 */
	@Deprecated
	public static <T, S, X extends Exception> Accumulator1<T, S, X> constant(final S state) {
		return new BaseAccumulator1<T, S, X>() {
			@Override
			public void add(final T value) {
				// Nothing to do.
			}
			
			@Override
			public S get() {
				return state;
			}
		};
	}
	
	/**
	 * Builds an accumulator that ignores the accumulated values.
	 * 
	 * @param <T> Type of the values.
	 * @param <S> Type of the states.
	 * @param <X> Type of the exceptions.
	 * @param state State of the accumulator. May be <code>null</code>.
	 * @return The built accumulator.
	 * @deprecated Use {@link #constant(Object)}.
	 */
	@Deprecated
	public static <T, S, X extends Exception> Accumulator1<T, S, X> constant1(final S state) {
		return constant(state);
	}
	
	/**
	 * Builds a two-arguments accumulator that ignores the accumulated values.
	 * 
	 * @param <T1> Type of the first values.
	 * @param <T2> Type of the second values.
	 * @param <S> Type of the states.
	 * @param <X> Type of the exceptions.
	 * @param state State of the accumulator. May be <code>null</code>.
	 * @return The built accumulator.
	 * @deprecated Use {@link com.trazere.core.imperative.Accumulators#constant2(Object)}.
	 */
	@Deprecated
	public static <T1, T2, S, X extends Exception> Accumulator2<T1, T2, S, X> constant2(final S state) {
		return new BaseAccumulator2<T1, T2, S, X>() {
			@Override
			public void add(final T1 value1, final T2 value2) {
				// Nothing to do.
			}
			
			@Override
			public S get() {
				return state;
			}
		};
	}
	
	/**
	 * Builds an accumulator that maps its state using the given function.
	 * 
	 * @param <T> Type of the values.
	 * @param <S> Type of the states.
	 * @param <RS> Type of the result states.
	 * @param <X> Type of the exceptions.
	 * @param function Mapping function to use.
	 * @param accumulator Accumulator to map.
	 * @return The built accumulator.
	 * @deprecated Use
	 *             {@link com.trazere.core.imperative.AccumulatorUtils#mapState(com.trazere.core.imperative.Accumulator, com.trazere.core.functional.Function)}.
	 */
	@Deprecated
	public static <T, S, RS, X extends Exception> Accumulator1<T, RS, X> mapState(final Function1<? super S, ? extends RS, ? extends RuntimeException> function, final Accumulator1<? super T, ? extends S, ? extends X> accumulator) {
		assert null != function;
		assert null != accumulator;
		
		return new BaseAccumulator1<T, RS, X>() {
			@Override
			public void add(final T value)
			throws X {
				accumulator.add(value);
			}
			
			@Override
			public RS get() {
				return function.evaluate(accumulator.get());
			}
		};
	}
	
	/**
	 * Builds an accumulator that maps its state using the given function.
	 * 
	 * @param <T1> Type of the first values.
	 * @param <T2> Type of the second values.
	 * @param <S> Type of the states.
	 * @param <RS> Type of the result states.
	 * @param <X> Type of the exceptions.
	 * @param function Mapping function to use.
	 * @param accumulator Accumulator to map.
	 * @return The built accumulator.
	 * @deprecated Use
	 *             {@link com.trazere.core.imperative.AccumulatorUtils#mapState2(com.trazere.core.imperative.Accumulator2, com.trazere.core.functional.Function)}
	 *             .
	 */
	@Deprecated
	public static <T1, T2, S, RS, X extends Exception> Accumulator2<T1, T2, RS, X> mapState(final Function1<? super S, ? extends RS, ? extends RuntimeException> function, final Accumulator2<? super T1, ? super T2, ? extends S, ? extends X> accumulator) {
		assert null != function;
		assert null != accumulator;
		
		return new BaseAccumulator2<T1, T2, RS, X>() {
			@Override
			public void add(final T1 value1, final T2 value2)
			throws X {
				accumulator.add(value1, value2);
			}
			
			@Override
			public RS get() {
				return function.evaluate(accumulator.get());
			}
		};
	}
	
	/**
	 * Builds an accumulator that retain the first accumulated value.
	 * 
	 * @param <T> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @return The built accumulator.
	 * @deprecated Use {@link com.trazere.core.imperative.Accumulators#first()}.
	 */
	@Deprecated
	public static <T, X extends Exception> Accumulator1<T, Maybe<T>, X> first() {
		return new BaseAccumulator1<T, Maybe<T>, X>() {
			private final MutableReference<T> _value = new MutableReference<T>();
			
			@Override
			public void add(final T value)
			throws X {
				if (!_value.isSet()) {
					_value.set(value);
				}
			}
			
			@Override
			public Maybe<T> get() {
				return _value.asMaybe();
			}
		};
	}
	
	/**
	 * Builds an accumulator that normalizes the accumulated values.
	 * 
	 * @param <T> Type of the values.
	 * @param <S> Type of the state.
	 * @param <X> Type of the exceptions.
	 * @param accumulator Accumulator to populate with the normalized values.
	 * @return The built accumulator.
	 * @see Predicates#normalizer()
	 * @deprecated Use {@link com.trazere.core.imperative.AccumulatorUtils#normalized(com.trazere.core.imperative.Accumulator)}.
	 */
	@Deprecated
	public static <T, S, X extends Exception> Accumulator1<T, S, X> normalizer(final Accumulator1<? super T, ? extends S, ? extends X> accumulator) {
		return filter(Predicates.<T, X>normalizer(), accumulator);
	}
	
	private Accumulators() {
		// Prevents instantiation.
	}
}
