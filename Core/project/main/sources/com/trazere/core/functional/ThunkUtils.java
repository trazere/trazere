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
package com.trazere.core.functional;

import com.trazere.core.text.DescriptionBuilder;
import com.trazere.core.util.Either;
import com.trazere.core.util.Maybe;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * The {@link ThunkUtils} class provides various utilities regarding {@link Thunk thunks}.
 * 
 * @see Thunk
 * @since 2.0
 */
public class ThunkUtils {
	/**
	 * Maps the given thunk using the given function.
	 * 
	 * @param <T> Type of the values.
	 * @param <R> Type of the transformed values.
	 * @param thunk Thunk to map.
	 * @param function Mapping function.
	 * @return The built thunk.
	 * @since 2.0
	 */
	public static <T, R> Thunk<R> map(final Thunk<? extends T> thunk, final Function<? super T, ? extends R> function) {
		assert null != thunk;
		assert null != function;
		
		return () -> function.evaluate(thunk.evaluate());
	}
	
	/**
	 * Builds a memoized view of the the given thunk.
	 * 
	 * @param <T> Type of the values.
	 * @param thunk Thunk to memoize.
	 * @return The built thunk.
	 * @see MemoizedThunk
	 * @since 2.0
	 */
	public static <T> MemoizedThunk<T> memoized(final Thunk<? extends T> thunk) {
		assert null != thunk;
		
		return new MemoizedThunk<T>() {
			/** Unevaluated thunk or memoized value. */
			protected Either<Thunk<? extends T>, T> _value = Either.<Thunk<? extends T>, T>left(thunk);
			
			@Override
			public T evaluate() {
				if (_value.isRight()) {
					return _value.asRight().getValue();
				} else {
					final T value = _value.asLeft().getValue().evaluate();
					_value = Either.right(value);
					return value;
				}
			}
			
			@Override
			public boolean isMemoized() {
				return _value.isRight();
			}
			
			@Override
			public Maybe<T> probe() {
				if (_value.isRight()) {
					return Maybe.some(_value.asRight().getValue());
				} else {
					return Maybe.<T>none();
				}
			}
			
			// Object.
			
			@Override
			public String toString() {
				if (_value.isLeft()) {
					return "=> " + _value.asLeft().getLeft();
				} else {
					return String.valueOf(_value.asRight().getRight());
				}
			}
		};
	}
	
	/**
	 * Builds a memoized, resettable view of the the given thunk.
	 * 
	 * @param <T> Type of the values.
	 * @param thunk Thunk to memoize.
	 * @return The build thunk.
	 * @see ResettableThunk
	 * @since 2.0
	 */
	public static <T> ResettableThunk<T> resettable(final Thunk<? extends T> thunk) {
		assert null != thunk;
		
		return new BaseResettableThunk<T>() {
			@Override
			protected T compute() {
				return thunk.evaluate();
			}
			
			@Override
			public void appendDescription(final DescriptionBuilder description) {
				super.appendDescription(description);
				description.append("Thunk", thunk);
			}
		};
	}
	
	/**
	 * Builds a thunk that evaluates to the given thunk in a thread safe way.
	 * 
	 * @param <T> Type of the values.
	 * @param thunk Thunk to synchronize.
	 * @return The built thunk.
	 * @since 2.0
	 */
	public static <T> Thunk<T> synchronized_(final Thunk<? extends T> thunk) {
		assert null != thunk;
		
		return () -> synchronizedEvaluate(thunk);
	}
	
	/**
	 * Builds a callable that lifts the given thunk.
	 * 
	 * @param <T> Type of the values.
	 * @param thunk Thunk to lift.
	 * @return The built callable.
	 * @since 2.0
	 */
	public static <T> Callable<T> toCallable(final Thunk<? extends T> thunk) {
		assert null != thunk;
		
		return () -> thunk.evaluate();
	}
	
	/**
	 * Builds a supplier that lifts the given thunk.
	 * 
	 * @param <T> Type of the values.
	 * @param thunk Thunk to lift.
	 * @return The built supplier.
	 * @since 2.0
	 */
	public static <T> Supplier<T> toSupplier(final Thunk<? extends T> thunk) {
		assert null != thunk;
		
		return () -> thunk.evaluate();
	}
	
	/**
	 * Evaluates the given thunk in a thread safe way.
	 * 
	 * @param <T> Type of the values.
	 * @param thunk Thunk to evaluate.
	 * @return The value of the thunk.
	 * @since 2.0
	 */
	public static <T> T synchronizedEvaluate(final Thunk<? extends T> thunk) {
		synchronized (thunk) {
			return thunk.evaluate();
		}
	}
	
	/**
	 * Resets the given thunk in a thread safe way.
	 * 
	 * @param thunk Thunk to reset.
	 * @since 2.0
	 */
	public static void synchronizedReset(final ResettableThunk<?> thunk) {
		synchronized (thunk) {
			thunk.reset();
		}
	}
	
	private ThunkUtils() {
		// Prevent instantiation.
	}
}
