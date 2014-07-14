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

import com.trazere.core.design.Factory;
import com.trazere.core.imperative.Effect;
import com.trazere.core.lang.ThrowableFactory;
import com.trazere.core.util.Maybe;

/**
 * The {@link Thunks} class provides various factories of {@link Thunk}s.
 */
public class Thunks {
	/**
	 * Builds a thunk evaluating to the given value.
	 * 
	 * @param <T> Type of the value.
	 * @param value Value of the thunk.
	 * @return The built thunk.
	 */
	// TODO: rename to fromValue ?
	public static <T> MemoizedThunk<T> constant(final T value) {
		return new MemoizedThunk<T>() {
			@Override
			public T evaluate() {
				return value;
			}
			
			@Override
			public boolean isEvaluated() {
				return true;
			}
			
			@Override
			public Maybe<T> get() {
				return Maybe.some(value);
			}
		};
	}
	
	/**
	 * Builds a thunk that throws the given exception.
	 *
	 * @param <T> Type of the value.
	 * @param exception Exception to throw.
	 * @return The built thunk.
	 */
	public static <T> Thunk<T> failure(final RuntimeException exception) {
		assert null != exception;
		
		return () -> {
			throw exception;
		};
	}
	
	/**
	 * Builds a thunk that throws an exception.
	 *
	 * @param <T> Type of the value.
	 * @param throwableFactory Throwable factory to use.
	 * @return The built thunk.
	 */
	public static <T> Thunk<T> failure(final ThrowableFactory<? extends RuntimeException> throwableFactory) {
		assert null != throwableFactory;
		
		return () -> {
			throw throwableFactory.build();
		};
	}
	
	/**
	 * Builds a thunk that lifts the given factory.
	 * 
	 * @param <T> Type of the value.
	 * @param factory Factory to lift.
	 * @return The built thunk.
	 */
	public static <T> Thunk<T> fromFactory(final Factory<T> factory) {
		assert null != factory;
		
		return () -> factory.build();
	}
	
	/**
	 * Builds a thunk that lifts the given effect.
	 *
	 * @param effect Effect to lift.
	 * @return The built thunk.
	 */
	public static Thunk<Void> fromEffect(final Effect effect) {
		return fromEffect(effect, (Void) null);
	}
	
	/**
	 * Builds a thunk that lifts the given effect and evaluates to the given value.
	 * 
	 * @param <T> Type of the value.
	 * @param effect Effect to lift.
	 * @param value Value of the thunk.
	 * @return The built thunk.
	 */
	public static <T> Thunk<T> fromEffect(final Effect effect, final T value) {
		assert null != effect;
		
		return () -> {
			effect.execute();
			return value;
		};
	}
	
	private Thunks() {
		// Prevent instantiation.
	}
}
