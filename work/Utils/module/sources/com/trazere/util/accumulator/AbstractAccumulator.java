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
package com.trazere.util.accumulator;

import com.trazere.util.lang.MutableObject;

/**
 * The {@link AbstractAccumulator} abstract class implements skeletons of {@link Accumulator accumulators}.
 * 
 * @param <T> Type of the accumulated values.
 * @param <V> Type of the accumulation arguments.
 * @param <X> Type of the exceptions.
 */
public abstract class AbstractAccumulator<T, V, X extends Exception>
implements Accumulator<T, V, X> {
	/** The accumulated value. */
	private final MutableObject<T> _value;
	
	/**
	 * Instantiate a new accumulator with the given initial value.
	 * 
	 * @param initialValue The initial value. Maybe be <code>null</code>.
	 */
	public AbstractAccumulator(final T initialValue) {
		// Initialization.
		_value = new MutableObject<T>(initialValue);
	}
	
	public T get() {
		return _value.get();
	}
	
	public void accumulate(final V value)
	throws X {
		_value.set(compute(_value.get(), value));
	}
	
	protected abstract T compute(final T accumulator, final V value)
	throws X;
	
	public void execute(final V value)
	throws X {
		accumulate(value);
	}
}
