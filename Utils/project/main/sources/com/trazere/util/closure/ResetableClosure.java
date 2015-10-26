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
package com.trazere.util.closure;

import com.trazere.util.function.Function0;
import com.trazere.util.text.Description;

/**
 * The {@link ResetableClosure} abstract class represents closures which can be re-evaluated.
 * <p>
 * Once computed, values of the resetable closure are memoized. The computation contexts are however preserved so that the values can be reset and computed
 * again.
 * 
 * @param <T> Type of the value.
 * @param <X> Type of the exceptions.
 * @deprecated Use {@link ResettableClosure}. (since 1.0)
 */
@Deprecated
public abstract class ResetableClosure<T, X extends Exception>
extends ResettableClosure<T, X> {
	/**
	 * Builds a closure evaluating to the given value.
	 * 
	 * @param <T> Type of the value.
	 * @param <X> Type of the exceptions.
	 * @param value Value. May be <code>null</code>.
	 * @return The closure.
	 * @deprecated Use {@link ResettableClosure#build(Object)}. (since 1.0)
	 */
	@Deprecated
	public static <T, X extends Exception> ResetableClosure<T, X> build(final T value) {
		return new ResetableClosure<T, X>() {
			@Override
			protected T compute() {
				return value;
			}
			
			@Override
			public void fillDescription(final Description description) {
				super.fillDescription(description);
				description.append("Value", value);
			}
		};
	}
	
	/**
	 * Builds a closure evaluating to the result of the given function.
	 * 
	 * @param <T> Type of the value.
	 * @param <X> Type of the exceptions.
	 * @param function The function computing the value.
	 * @return The closure.
	 * @deprecated Use {@link ResettableClosure#build(Function0)}. (since 1.0)
	 */
	@Deprecated
	public static <T, X extends Exception> ResetableClosure<T, X> build(final Function0<? extends T, ? extends X> function) {
		return new ResetableClosure<T, X>() {
			@Override
			protected T compute()
			throws X {
				return function.evaluate();
			}
			
			@Override
			public void fillDescription(final Description description) {
				super.fillDescription(description);
				description.append("Function", function);
			}
		};
	}
}
