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

/**
 * The {@link FoldAccumulator1} abstract class implements {@link Accumulator1 accumulators} based on some folding binary operation.
 * 
 * @param <R> Type of the result.
 * @param <V> Type of the values.
 * @param <X> Type of the exceptions.
 * @deprecated Use {@link com.trazere.core.imperative.FoldAccumulator}.
 */
@Deprecated
public abstract class FoldAccumulator1<R, V, X extends Exception>
extends BaseAccumulator1<V, R, X> {
	/** The current value. */
	private R _result;
	
	/**
	 * Instantiates a new accumulator with the given initial result.
	 * 
	 * @param initialResult The initial result. Maybe be <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.imperative.FoldAccumulator#FoldAccumulator(Object)}.
	 */
	@Deprecated
	public FoldAccumulator1(final R initialResult) {
		// Initialization.
		_result = initialResult;
	}
	
	@Override
	public void add(final V value)
	throws X {
		_result = fold(_result, value);
	}
	
	/**
	 * Folds the result by applying the binary operation on the current result and the given value.
	 * 
	 * @param result The current result.
	 * @param value The value.
	 * @return The next result.
	 * @throws X When the folding fails.
	 * @deprecated Use {@link com.trazere.core.imperative.FoldAccumulator#fold(Object, Object)}.
	 */
	@Deprecated
	@SuppressWarnings("javadoc")
	protected abstract R fold(final R result, final V value)
	throws X;
	
	@Override
	public R get() {
		return _result;
	}
}
