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
package com.trazere.core.functional;

import com.trazere.core.lang.Releasable;
import com.trazere.core.text.Describable;
import com.trazere.core.text.Description;
import com.trazere.core.text.TextUtils;
import com.trazere.core.util.Maybe;

/**
 * The {@link ResettableThunk} abstract class represents closures which can be re-evaluated.
 * <p>
 * Once computed, values of the resetable closure are memoized. The computation contexts are however preserved so that the values can be reset and computed
 * again.
 * 
 * @param <T> Type of the value.
 */
public abstract class ResettableThunk<T>
implements MemoizedThunk<T>, Releasable, Describable {
	/** The memoized value. */
	protected Maybe<T> _value = Maybe.none();
	
	@Override
	public T evaluate() {
		if (_value.isSome()) {
			return _value.asSome().getValue();
		} else {
			final T value = compute();
			_value = Maybe.some(value);
			return value;
		}
	}
	
	/**
	 * Computes the value of this thunk.
	 * 
	 * @return The computed value.
	 */
	protected abstract T compute();
	
	@Override
	public boolean isEvaluated() {
		return _value.isSome();
	}
	
	@Override
	public Maybe<T> get() {
		return _value;
	}
	
	/**
	 * Resets this thunk, discarding its possibly memoized value. The value will be computed (again) the next time this thunk is evaluated.
	 */
	public void reset() {
		if (_value.isSome()) {
			dispose(_value.asSome().getValue());
			_value = Maybe.none();
		}
	}
	
	/**
	 * Disposes the given current value of this thunk.
	 * <p>
	 * This methods is called when this evaluated thunk is reset. The defaut implementation does nothing.
	 * 
	 * @param value Value to dispose.
	 */
	protected void dispose(final T value) {
		// Nothing to do.
	}
	
	// Releasable.
	
	@Override
	public void release() {
		reset();
	}
	
	// Object.
	
	@Override
	public String toString() {
		if (_value.isSome()) {
			return String.valueOf(_value.asSome().getValue());
		} else {
			return TextUtils.computeDescription(this);
		}
	}
	
	@Override
	public void appendDescription(final Description description) {
		// Nothing to do.
	}
}
