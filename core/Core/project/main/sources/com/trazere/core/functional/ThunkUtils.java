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
package com.trazere.core.functional;

import com.trazere.core.text.Description;
import com.trazere.core.util.Either;
import com.trazere.core.util.Maybe;

/**
 * The {@link ThunkUtils} class provides various utilities regarding {@link Thunk}s.
 */
public class ThunkUtils {
	/**
	 * Maps the given thunk using the given function.
	 * 
	 * @param <T> Type of the value.
	 * @param <R> Type of the transformed value.
	 * @param thunk Thunk to map.
	 * @param function Mapping function.
	 * @return The built thunk.
	 */
	public static <T, R> Thunk<R> map(final Thunk<? extends T> thunk, final Function<? super T, ? extends R> function) {
		assert null != thunk;
		assert null != function;
		
		return () -> function.evaluate(thunk.evaluate());
	}
	
	/**
	 * Builds a thunk that memoizes the value of the given thunk.
	 * 
	 * @param <T> Type of the value.
	 * @param thunk Thunk to memoize.
	 * @return The built thunk.
	 */
	public static <T> MemoizedThunk<T> memoize(final Thunk<? extends T> thunk) {
		assert null != thunk;
		
		return new MemoizedThunk<T>() {
			/** Unevaluated thunk or memoized value. */
			protected Either<Thunk<? extends T>, T> _value = Either.<Thunk<? extends T>, T>left(thunk);
			
			@Override
			public T evaluate() {
				if (_value.isRight()) {
					return _value.asRight().getRight();
				} else {
					final T value = _value.asLeft().getLeft().evaluate();
					_value = Either.right(value);
					return value;
				}
			}
			
			@Override
			public boolean isMemoized() {
				return _value.isRight();
			}
			
			@Override
			public Maybe<T> get() {
				if (_value.isRight()) {
					return Maybe.some(_value.asRight().getRight());
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
	 * Builds a thunk that memoizes the value of the given thunk and can be reset.
	 * 
	 * @param <T> Type of the value.
	 * @param thunk Thunk to memoize.
	 * @return The build thunk.
	 */
	public static <T> ResettableThunk<T> resettable(final Thunk<? extends T> thunk) {
		return new ResettableThunk<T>() {
			@Override
			protected T compute() {
				return thunk.evaluate();
			}
			
			@Override
			public void appendDescription(final Description description) {
				super.appendDescription(description);
				description.append("Thunk", thunk);
			}
		};
	}
	
	/**
	 * Builds a synchronized thunk that evaluates to the given thunk.
	 * 
	 * @param <T> Type of the value.
	 * @param thunk Thunk to synchronize.
	 * @return The built thunk.
	 */
	public static <T> Thunk<T> synchronize(final Thunk<? extends T> thunk) {
		assert null != thunk;
		
		return () -> synchronizedEvaluate(thunk);
	}
	
	/**
	 * Evaluates the given thunk in a thread safe way.
	 * 
	 * @param <T> Type of the value.
	 * @param thunk Thunk to evaluate.
	 * @return The value of the thunk.
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
