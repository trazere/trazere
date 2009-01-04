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
import com.trazere.util.text.Description;
import com.trazere.util.text.TextUtils;
import com.trazere.util.type.Maybe;
import com.trazere.util.type.Maybe.None;
import com.trazere.util.type.Maybe.Some;

/**
 * The {@link Closure} abstract class represents zero argument closures.
 * <p>
 * This class provides an abstraction for computing values and for lazy evaluation. The computation methods are provided by the subclasses and rely solely on
 * the contexts captured by the closures (no arguments). The values are computed when the closures are read and are memoized so that they are computed at most
 * once.
 * <p>
 * This class simulated call-by-need evaluations.
 * 
 * @param <T> Type of the value.
 * @param <X> Type of the exceptions.
 */
public abstract class Closure<T, X extends Exception>
implements Describable {
	/**
	 * Build a closure containing the given value.
	 * 
	 * @param <T> Type of the value.
	 * @param <X> Type of the exceptions.
	 * @param value Value. May be <code>null</code>.
	 * @return The closure.
	 */
	public static <T, X extends Exception> Closure<T, X> build(final T value) {
		return new Closure<T, X>() {
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
	 * Build a closure which gets its from the given factory.
	 * 
	 * @param <T> Type of the value.
	 * @param <X> Type of the exceptions.
	 * @param factory The factory which builds the value.
	 * @return The closure.
	 */
	public static <T, X extends Exception> Closure<T, X> build(final Factory<T, X> factory) {
		return new Closure<T, X>() {
			@Override
			protected T compute()
			throws X {
				return factory.build();
			}
			
			@Override
			public void fillDescription(final Description description) {
				super.fillDescription(description);
				description.append("Factory", factory);
			}
		};
	}
	
	/** The computed value. */
	protected Maybe<T> _value = Maybe.none();
	
	/**
	 * Get the value of the receiver closure.
	 * <p>
	 * The value is computed if needed and memoized for future calls.
	 * 
	 * @return The computed value. May be <code>null</code>.
	 * @throws X When the computation of the value fails.
	 */
	public T get()
	throws X {
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
	 * @throws X When the computation fails.
	 */
	protected abstract T compute()
	throws X;
	
	/**
	 * Indicate wether the value of the receiver closure has been computed.
	 * 
	 * @return <code>true</code> if a value has been computed, <code>false</code> otherwise.
	 */
	public boolean isComputed() {
		return _value.isSome();
	}
	
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
	
	public void fillDescription(final Description description) {
		// Nothing to do.
	}
}
