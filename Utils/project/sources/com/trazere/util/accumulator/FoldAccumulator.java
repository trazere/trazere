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
package com.trazere.util.accumulator;

import com.trazere.util.lang.MutableObject;

/**
 * The {@link FoldAccumulator} abstract class implements {@link Accumulator accumulators} based on some folding operation.
 * 
 * @param <T> Type of the accumulated values.
 * @param <V> Type of the accumulation arguments.
 * @param <X> Type of the exceptions.
 */
public abstract class FoldAccumulator<T, V, X extends Exception>
extends BaseAccumulator<T, V, X> {
	/** The current value. */
	private final MutableObject<T> _value;
	
	/**
	 * Instantiate a new accumulator with the given initial value.
	 * 
	 * @param initialValue The initial value. Maybe be <code>null</code>.
	 */
	public FoldAccumulator(final T initialValue) {
		// Initialization.
		_value = new MutableObject<T>(initialValue);
	}
	
	@Override
	public T get() {
		return _value.get();
	}
	
	@Override
	public void add(final V value)
	throws X {
		_value.set(fold(_value.get(), value));
	}
	
	protected abstract T fold(final T accumulator, final V value)
	throws X;
}
