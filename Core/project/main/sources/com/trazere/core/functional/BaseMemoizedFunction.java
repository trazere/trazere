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

import com.trazere.core.collection.MapUtils;
import com.trazere.core.util.Maybe;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@link BaseMemoizedFunction} abstract class provides a skeleton implementation of {@link MemoizedFunction memoized functions}.
 * 
 * @param <A> Type of the arguments.
 * @param <R> Type of the results.
 * @since 2.0
 */
public abstract class BaseMemoizedFunction<A, R>
implements MemoizedFunction<A, R> {
	/**
	 * Memoized results.
	 * 
	 * @since 2.0
	 */
	protected final Map<A, R> _results = new HashMap<>();
	
	@Override
	public R evaluate(final A arg) {
		if (_results.containsKey(arg)) {
			return _results.get(arg);
		} else {
			final R result = compute(arg);
			_results.put(arg, result);
			return result;
		}
	}
	
	/**
	 * Computes the result of the evaluation of this function with the given argument.
	 * 
	 * @param arg Argument to evaluate the function with.
	 * @return The result of the function evaluation.
	 * @since 2.0
	 */
	protected abstract R compute(A arg);
	
	@Override
	public boolean isMemoized(final A arg) {
		return _results.containsKey(arg);
	}
	
	@Override
	public Maybe<R> probe(final A arg) {
		return MapUtils.get(_results, arg);
	}
}
