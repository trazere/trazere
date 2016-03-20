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
package com.trazere.core.design;

import com.trazere.core.functional.Thunk;
import com.trazere.core.lang.ThrowableFactory;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * The {@link Factories} class provides various factories of {@link Factory factories}.
 * 
 * @see Factory
 * @since 2.0
 */
public class Factories {
	/**
	 * Builds a factory that builds the given value.
	 * 
	 * @param <T> Type of the built values.
	 * @param value Value to build.
	 * @return The built factory.
	 * @since 2.0
	 */
	public static <T> Factory<T> fromValue(final T value) {
		return () -> value;
	}
	
	// TODO: replace by Thunk.toFactory ?
	/**
	 * Builds a factory that lifts the given thunks.
	 * 
	 * @param <T> Type of the built values.
	 * @param thunk Thunk to lift.
	 * @return The built factory.
	 * @since 2.0
	 */
	public static <T> Factory<T> fromThunk(final Thunk<? extends T> thunk) {
		assert null != thunk;
		
		return thunk::evaluate;
	}
	
	/**
	 * Builds a factory that lifts the given Java 8 callable.
	 * 
	 * @param <T> Type of the built values.
	 * @param callable Java 8 callable to lift.
	 * @param failureFactory Factory of the exceptions for the failures.
	 * @return The built factory.
	 * @since 2.0
	 */
	public static <T> Factory<T> fromCallable(final Callable<? extends T> callable, final ThrowableFactory<? extends RuntimeException> failureFactory) {
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
	 * Builds a factory that lifts the given Java 8 supplier.
	 * 
	 * @param <T> Type of the built values.
	 * @param supplier Java 8 supplier to lift.
	 * @return The build factory.
	 * @since 2.0
	 */
	public static <T> Factory<T> fromSupplier(final Supplier<? extends T> supplier) {
		assert null != supplier;
		
		return supplier::get;
	}
	
	private Factories() {
		// Prevents instantiation.
	}
}
