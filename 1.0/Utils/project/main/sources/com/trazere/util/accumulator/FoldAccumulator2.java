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
 * The {@link FoldAccumulator2} abstract class implements {@link Accumulator2 pair accumulators} based on some binary folding operation.
 * 
 * @param <R> Type of the result.
 * @param <V1> Type of the first values.
 * @param <V2> Type of the second values.
 * @param <X> Type of the exceptions.
 * @deprecated Use {@link com.trazere.core.imperative.FoldAccumulator2}.
 */
@Deprecated
public abstract class FoldAccumulator2<R, V1, V2, X extends Exception>
extends BaseAccumulator2<V1, V2, R, X> {
	/** The current value. */
	private R _result;
	
	/**
	 * Instantiates a new accumulator with the given initial result.
	 * 
	 * @param initialResult The initial result. Maybe be <code>null</code>.
	 */
	public FoldAccumulator2(final R initialResult) {
		// Initialization.
		_result = initialResult;
	}
	
	@Override
	public void add(final V1 value1, final V2 value2)
	throws X {
		_result = fold(_result, value1, value2);
	}
	
	/**
	 * Folds the result by applying the binary operation on the current result and the given pair of values.
	 * 
	 * @param result The current result.
	 * @param value1 The first value.
	 * @param value2 The second value.
	 * @return The next result.
	 * @throws X When the folding fails.
	 */
	protected abstract R fold(final R result, final V1 value1, final V2 value2)
	throws X;
	
	@Override
	public R get() {
		return _result;
	}
}
