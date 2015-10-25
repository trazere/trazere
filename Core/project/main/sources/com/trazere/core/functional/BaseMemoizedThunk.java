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
package com.trazere.core.functional;

import com.trazere.core.util.Maybe;

/**
 * The {@link BaseMemoizedThunk} class provides a skeleton implementation of {@link MemoizedThunk memoized thunks}.
 * 
 * @param <T> Type of the value.
 * @since 2.0
 */
public abstract class BaseMemoizedThunk<T>
implements MemoizedThunk<T> {
	/**
	 * Indicates whether the value has been computed or not.
	 * 
	 * @since 2.0
	 */
	protected boolean _evaluated = false;
	
	/**
	 * Value of the thunk.
	 * 
	 * @since 2.0
	 */
	protected T _value = null;
	
	@Override
	public T evaluate() {
		if (!_evaluated) {
			_value = compute();
			_evaluated = true;
		}
		return _value;
	}
	
	/**
	 * Computes the value of this thunk.
	 * 
	 * @return The computed value.
	 * @since 2.0
	 */
	protected abstract T compute();
	
	@Override
	public boolean isMemoized() {
		return _evaluated;
	}
	
	@Override
	public Maybe<T> probe() {
		return _evaluated ? Maybe.some(_value) : Maybe.none();
	}
}
