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

import com.trazere.core.design.Factory;
import com.trazere.core.lang.ThrowableFactory;
import com.trazere.core.util.Maybe;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * The {@link Thunks} class provides various factories of {@link Thunk thunks}.
 * 
 * @see Thunk
 * @since 2.0
 */
public class Thunks {
	/**
	 * Builds a thunk evaluating to the given value.
	 * 
	 * @param <T> Type of the value.
	 * @param value Value of the thunk.
	 * @return The built thunk.
	 * @since 2.0
	 */
	public static <T> MemoizedThunk<T> constant(final T value) {
		return new MemoizedThunk<T>() {
			@Override
			public T evaluate() {
				return value;
			}
			
			@Override
			public boolean isMemoized() {
				return true;
			}
			
			@Override
			public Maybe<T> probe() {
				return Maybe.some(value);
			}
		};
	}
	
	/**
	 * Builds a thunk that throws the given exception.
	 *
	 * @param <T> Type of the values.
	 * @param exception Exception to throw.
	 * @return The built thunk.
	 * @since 2.0
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
	 * @param <T> Type of the values.
	 * @param failureFactory Factory of the exceptions for the failures.
	 * @return The built thunk.
	 * @since 2.0
	 */
	public static <T> Thunk<T> failure(final ThrowableFactory<? extends RuntimeException> failureFactory) {
		assert null != failureFactory;
		
		return () -> {
			throw failureFactory.build();
		};
	}
	
	/**
	 * Builds a thunk that throws an exception.
	 *
	 * @param <T> Type of the values.
	 * @param failureFactory Factory of the exceptions for the failures.
	 * @param message Message of the throwable.
	 * @return The built thunk.
	 * @since 2.0
	 */
	public static <T> Thunk<T> failure(final ThrowableFactory<? extends RuntimeException> failureFactory, final String message) {
		assert null != failureFactory;
		
		return () -> {
			throw failureFactory.build(message);
		};
	}
	
	// TODO: replace by Factory.toThunk ?
	/**
	 * Builds a thunk that lifts the given factory.
	 * 
	 * @param <T> Type of the values.
	 * @param factory Factory to lift.
	 * @return The built thunk.
	 * @since 2.0
	 */
	public static <T> Thunk<T> fromFactory(final Factory<T> factory) {
		assert null != factory;
		
		return factory::build;
	}
	
	/**
	 * Builds a thunk that lifts the given Java 8 callable.
	 * 
	 * @param <T> Type of the built values.
	 * @param callable Java 8 callable to lift.
	 * @param failureFactory Factory of the exceptions for the failures.
	 * @return The built thunk.
	 * @since 2.0
	 */
	public static <T> Thunk<T> fromCallable(final Callable<? extends T> callable, final ThrowableFactory<? extends RuntimeException> failureFactory) {
		assert null != callable;
		assert null != failureFactory;
		
		return () -> {
			try {
				return callable.call();
			} catch (final Exception exception) {
				throw failureFactory.build(exception);
			}
		};
	}
	
	/**
	 * Builds a thunk that lifts the given Java 8 supplier.
	 * 
	 * @param <T> Type of the values.
	 * @param supplier Java 8 supplier to lift.
	 * @return The built thunk.
	 * @since 2.0
	 */
	public static <T> Thunk<T> fromSupplier(final Supplier<? extends T> supplier) {
		assert null != supplier;
		
		return supplier::get;
	}
	
	private Thunks() {
		// Prevent instantiation.
	}
}
