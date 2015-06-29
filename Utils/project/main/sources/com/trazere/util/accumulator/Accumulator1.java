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
package com.trazere.util.accumulator;

import com.trazere.util.function.Procedure1;

/**
 * The {@link Accumulator1} interface defines left folding functions over an internal state.
 * 
 * @param <T> Type of the values.
 * @param <S> Type of the states.
 * @param <X> Type of the exceptions.
 * @deprecated Use {@link com.trazere.core.imperative.Accumulator}.
 */
@Deprecated
public interface Accumulator1<T, S, X extends Exception>
extends Procedure1<T, X> {
	/**
	 * Accumulates the given value into the receiver accumulator.
	 * 
	 * @param value Value to accumulate. May be <code>null</code>.
	 * @throws X When the accumulation fails.
	 * @deprecated Use {@link com.trazere.core.imperative.Accumulator#add(Object)}.
	 */
	@Deprecated
	public void add(final T value)
	throws X;
	
	/**
	 * Accumulates the given values into the receiver accumulator.
	 * 
	 * @param values Values to accumulate. May be <code>null</code>.
	 * @throws X When the accumulation fails.
	 * @deprecated Use {@link com.trazere.core.imperative.Accumulator#addAll(Iterable)}.
	 */
	@Deprecated
	public void addAll(final Iterable<? extends T> values)
	throws X;
	
	/**
	 * Gets the current state of the receiver accumulator.
	 * 
	 * @return The state. May be <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.imperative.Accumulator#get()}.
	 */
	@Deprecated
	public S get();
}
