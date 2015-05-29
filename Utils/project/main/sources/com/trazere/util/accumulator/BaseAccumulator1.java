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

/**
 * The {@link BaseAccumulator1} abstract class provides a skeleton implementation of {@link Accumulator1 accumulators}.
 * 
 * @param <T> Type of the values.
 * @param <S> Type of the states.
 * @param <X> Type of the exceptions.
 * @deprecated Use {@link com.trazere.core.imperative.Accumulator}.
 */
@Deprecated
public abstract class BaseAccumulator1<T, S, X extends Exception>
implements Accumulator1<T, S, X> {
	// Accumulator.
	
	@Override
	public void addAll(final Iterable<? extends T> values)
	throws X {
		assert null != values;
		
		for (final T value : values) {
			add(value);
		}
	}
	
	// Procedure.
	
	@Override
	public void execute(final T value)
	throws X {
		add(value);
	}
}
