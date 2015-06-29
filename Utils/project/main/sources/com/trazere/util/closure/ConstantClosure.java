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
package com.trazere.util.closure;

import com.trazere.util.type.Maybe;

/**
 * The {@link ConstantClosure} class represents closures which evaluate to constant values.
 * 
 * @param <T> Type of the value.
 * @param <X> Type of the exceptions.
 * @deprecated Use {@link com.trazere.core.functional.Thunks#constant(Object)}.
 */
@Deprecated
public class ConstantClosure<T, X extends Exception>
implements Closure<T, X> {
	/**
	 * Build a closure evaluating to the given value.
	 * 
	 * @param <T> Type of the value.
	 * @param <X> Type of the exceptions.
	 * @param value The value. May be <code>null</code>.
	 * @return The closure.
	 * @deprecated Use {@link com.trazere.core.functional.Thunks#constant(Object)}.
	 */
	@Deprecated
	public static <T, X extends Exception> ConstantClosure<T, X> build(final T value) {
		return new ConstantClosure<T, X>(value);
	}
	
	/** The value. May be <code>null</code>. */
	private final T _value;
	
	/**
	 * Instantiate a closure using the given value.
	 * 
	 * @param value The value. May be <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.functional.Thunks#constant(Object)}.
	 */
	@Deprecated
	public ConstantClosure(final T value) {
		_value = value;
	}
	
	@Override
	public T evaluate() {
		return _value;
	}
	
	@Override
	public boolean isEvaluated() {
		return true;
	}
	
	@Override
	public Maybe<T> asMaybe() {
		return Maybe.some(_value);
	}
	
	// Object.
	
	@Override
	public String toString() {
		return String.valueOf(_value);
	}
}
