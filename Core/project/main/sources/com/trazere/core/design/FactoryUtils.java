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

import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * The {@link FactoryUtils} class provides various utilities regarding {@link Factory factories}.
 * 
 * @see Factory
 * @since 2.0
 */
public class FactoryUtils {
	/**
	 * Builds a callable that lifts the given factory.
	 * 
	 * @param <T> Type of the built values.
	 * @param factory Factory to lift.
	 * @return The built callable.
	 * @since 2.0
	 */
	public static <T> Callable<T> toCallable(final Factory<? extends T> factory) {
		assert null != factory;
		
		return () -> factory.build();
	}
	
	/**
	 * Builds a supplier that lifts the given factory.
	 * 
	 * @param <T> Type of the built values.
	 * @param factory Factory to lift.
	 * @return The built supplier.
	 * @since 2.0
	 */
	public static <T> Supplier<T> toSupplier(final Factory<? extends T> factory) {
		assert null != factory;
		
		return () -> factory.build();
	}
	
	private FactoryUtils() {
		// Prevent instantiation.
	}
}
