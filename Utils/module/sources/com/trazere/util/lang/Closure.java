/*
 *  Copyright 2006-2008 Julien Dufour
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
package com.trazere.util.lang;

import com.trazere.util.text.Describable;
import com.trazere.util.text.TextUtils;
import com.trazere.util.type.Maybe;
import com.trazere.util.type.Maybe.None;
import com.trazere.util.type.Maybe.Some;

/**
 * The {@link Closure} abstract class represents closures.
 * <p>
 * A closure is a value which is lazily computed and memoized. This class simulates call-by-need evaluations.
 * 
 * @param <T> Type of the value.
 */
public abstract class Closure<T>
implements Describable {
	/**
	 * Build a closure containing the given value.
	 * 
	 * @param <T> Type of the value.
	 * @param value Value. May be <code>null</code>.
	 * @return The closure.
	 */
	public static <T> Closure<T> build(final T value) {
		return new Closure<T>() {
			@Override
			protected T compute() {
				return value;
			}
			
			public void fillDescription(final StringBuilder builder) {
				builder.append(" - Value = ").append(value);
			}
		};
	}
	
	/** The computed value. */
	protected Maybe<T> _value = Maybe.none();
	
	/**
	 * Indicate wether the value of the receiver closure has been computed.
	 * 
	 * @return <code>true</code> if a value has been computed, <code>false</code> otherwise.
	 */
	public boolean isComputed() {
		return _value.isSome();
	}
	
	/**
	 * Get the value of the receiver closure.
	 * <p>
	 * The value is computed if needed and memoized for future calls.
	 * 
	 * @return The computed value. May be <code>null</code>.
	 * @throws CannotComputeValueException When the computation of the value fails.
	 */
	public T get()
	throws CannotComputeValueException {
		if (_value.isSome()) {
			return _value.asSome().getValue();
		} else {
			final T value = compute();
			_value = Maybe.some(value);
			return value;
		}
	}
	
	/**
	 * Compute the value of the receiver closure.
	 * 
	 * @return The computed value. May be <code>null</code>.
	 * @throws CannotComputeValueException When the computation fails.
	 */
	protected abstract T compute()
	throws CannotComputeValueException;
	
	/**
	 * Reset the value memoized in the receiver closure. The value will be computed (again) during the next call to {@link #get()}.
	 */
	public void reset() {
		_value = Maybe.none();
	}
	
	/**
	 * Get a view of the computed value of the receiver closure as an instance of {@link Maybe}.
	 * 
	 * @return The computed value wrapped in {@link Some}, or {@link None} when the value has not been computed yet.
	 */
	public Maybe<T> asMaybe() {
		return _value;
	}
	
	@Override
	public String toString() {
		return _value.isSome() ? String.valueOf(_value.asSome().getValue()) : TextUtils.computeDescription(this);
	}
}
