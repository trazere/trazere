/*
 *  Copyright 2006-2011 Julien Dufour
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

import com.trazere.util.text.Describable;
import com.trazere.util.text.Description;
import com.trazere.util.text.TextUtils;
import com.trazere.util.type.Maybe;

/**
 * The {@link ConstantClosure} class represents closures which evaluate to constant values.
 * 
 * @param <T> Type of the value.
 * @param <X> Type of the exceptions.
 */
public class ConstantClosure<T, X extends Exception>
implements Closure<T, X>, Describable {
	/**
	 * Build a closure evaluating to the given value.
	 * 
	 * @param <T> Type of the value.
	 * @param <X> Type of the exceptions.
	 * @param value The value. May be <code>null</code>.
	 * @return The closure.
	 */
	public static <T, X extends Exception> Closure<T, X> build(final T value) {
		return new ConstantClosure<T, X>(value);
	}
	
	/** The value. May be <code>null</code>. */
	private final T _value;
	
	/**
	 * Instantiate a closure using the given value.
	 * 
	 * @param value The value. May be <code>null</code>.
	 */
	public ConstantClosure(final T value) {
		_value = value;
	}
	
	public T evaluate() {
		return _value;
	}
	
	public boolean isEvaluated() {
		return true;
	}
	
	public Maybe<T> asMaybe() {
		return Maybe.some(_value);
	}
	
	@Override
	public String toString() {
		return TextUtils.computeDescription(this);
	}
	
	public void fillDescription(final Description description) {
		description.append("Value", _value);
	}
}
