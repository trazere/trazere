/*
 *  Copyright 2006-2010 Julien Dufour
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

import com.trazere.util.Releasable;
import com.trazere.util.function.Function0;
import com.trazere.util.text.Describable;
import com.trazere.util.text.Description;
import com.trazere.util.text.TextUtils;
import com.trazere.util.type.Maybe;
import com.trazere.util.type.Maybe.None;
import com.trazere.util.type.Maybe.Some;

/**
 * The {@link ResetableClosure} abstract class represents closures which can be re-evaluated.
 * <p>
 * Once computed, values of the resetable closure are memoized. The computation contexts are however preserved so that the values can be reset and computed
 * again.
 * 
 * @param <T> Type of the value.
 * @param <X> Type of the exceptions.
 */
public abstract class ResetableClosure<T, X extends Exception>
implements Closure<T, X>, Releasable<RuntimeException>, Describable {
	/**
	 * Build a closure evaluating to the given value.
	 * 
	 * @param <T> Type of the value.
	 * @param <X> Type of the exceptions.
	 * @param value Value. May be <code>null</code>.
	 * @return The closure.
	 */
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
	 * Build a closure evaluating to the result of the given function.
	 * 
	 * @param <T> Type of the value.
	 * @param <X> Type of the exceptions.
	 * @param function The function computing the value.
	 * @return The closure.
	 */
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
	
	/** The value. */
	protected Maybe<T> _value = Maybe.none();
	
	public T evaluate()
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
	
	public boolean isEvaluated() {
		return _value.isSome();
	}
	
	/**
	 * Reset the value memoized in the receiver closure. The value will be computed (again) during the next call to {@link #evaluate()}.
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
	
	public void release() {
		reset();
	}
	
	@Override
	public String toString() {
		return _value.isSome() ? String.valueOf(_value.asSome().getValue()) : TextUtils.computeDescription(this);
	}
	
	public void fillDescription(final Description description) {
		// Nothing to do.
	}
}
