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

import com.trazere.core.functional.Function;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * The {@link Factory} interface defines factories.
 * <p>
 * TODO: compare to factory design pattern
 * 
 * @param <T> Type of the built values.
 * @since 2.0
 */
@FunctionalInterface
public interface Factory<T> {
	/**
	 * Builds a new value.
	 * 
	 * @return The built value.
	 * @since 2.0
	 */
	T build();
	
	/**
	 * Transforms the values built by this factory using the given function.
	 * 
	 * @param <R> Type of the transformed values.
	 * @param function Function to use to transform the values.
	 * @return A factory of the transformed values.
	 * @since 2.0
	 */
	default <R> Factory<R> map(final Function<? super T, ? extends R> function) {
		assert null != function;
		
		final Factory<T> self = this;
		return () -> function.evaluate(self.build());
	}
	
	/**
	 * Lifts this factory as a callable.
	 * 
	 * @return The built Java 8 callable.
	 * @since 2.0
	 */
	default Callable<T> toCallable() {
		return this::build;
	}
	
	/**
	 * Lifts this factory as a Java 8 supplier.
	 * 
	 * @return The built Java 8 supplier.
	 * @since 2.0
	 */
	default Supplier<T> toSupplier() {
		return this::build;
	}
}
