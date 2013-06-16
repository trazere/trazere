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

import com.trazere.util.function.Procedure2;

/**
 * The {@link Accumulator2} interface defines left folding functions of pairs over an internal state.
 * 
 * @param <T1> Type of the first values.
 * @param <T2> Type of the second values.
 * @param <S> Type of the result.
 * @param <X> Type of the exceptions.
 */
public interface Accumulator2<T1, T2, S, X extends Exception>
extends Procedure2<T1, T2, X> {
	/**
	 * Get the current result of the receiver accumulator.
	 * 
	 * @return The result. May be <code>null</code>.
	 */
	public S get();
	
	/**
	 * Accumulate the given pair of values into the receiver accumulator.
	 * 
	 * @param value1 The first value. May be <code>null</code>.
	 * @param value2 The second value. May be <code>null</code>.
	 * @throws X When the accumulation fails.
	 */
	public void add(final T1 value1, T2 value2)
	throws X;
}
