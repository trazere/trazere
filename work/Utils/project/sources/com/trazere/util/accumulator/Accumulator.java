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

import com.trazere.util.function.Procedure1;

// TODO: inverse T and V order
// TODO: rename T to R

/**
 * The {@link Accumulator} interface defines left folding functions over an internal state.
 * 
 * @param <T> Type of the accumulated values.
 * @param <V> Type of the accumulation arguments.
 * @param <X> Type of the exceptions.
 */
public interface Accumulator<T, V, X extends Exception>
extends Procedure1<V, X> {
	/**
	 * Get the current value of the receiver accumulator.
	 * 
	 * @return The value. May be <code>null</code>.
	 */
	public T get();
	
	/**
	 * Accumulate the given value into the receiver accumulator.
	 * 
	 * @param value The argument. May be <code>null</code>.
	 * @throws X When the accumulation fails.
	 */
	public void add(final V value)
	throws X;
}
