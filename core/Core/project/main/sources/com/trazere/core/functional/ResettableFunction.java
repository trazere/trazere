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
package com.trazere.core.functional;

import com.trazere.core.collection.MapUtils;
import com.trazere.core.lang.Releasable;
import com.trazere.core.util.Maybe;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * The {@link ResettableFunction} class implements memoized functions that can be re-evaluated.
 * 
 * @param <A> Type of the arguments.
 * @param <R> Type of the results.
 */
public abstract class ResettableFunction<A, R>
implements MemoizedFunction<A, R>, Releasable {
	/** Memoized results. */
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
	 * Computes the evaluation this function with the given argument.
	 * 
	 * @param arg Argument to evaluate the function with.
	 * @return The result of the function evaluation.
	 */
	protected abstract R compute(A arg);
	
	@Override
	public boolean isMemoized(final A arg) {
		return _results.containsKey(arg);
	}
	
	/**
	 * Gets the arguments whose evaluation is memoized.
	 * 
	 * @return An unmutable set of the arguments.
	 */
	public Set<A> memoizedArgs() {
		return Collections.unmodifiableSet(_results.keySet());
	}
	
	@Override
	public Maybe<R> get(final A arg) {
		return MapUtils.get(_results, arg);
	}
	
	/**
	 * Resets the evaluation of this function with the given argument, discarding the possibly memoized result. The result will be computed (again) the next
	 * time this function is evaluated with the given argument.
	 * 
	 * @param arg Argument whose evaluation should be reset.
	 */
	public void reset(final A arg) {
		if (_results.containsKey(arg)) {
			dispose(arg, _results.remove(arg));
		}
	}
	
	/**
	 * Resets the evaluations of this function with the arguments accepted by the given filter, discarding the possibly memoized results. The results will be
	 * computed (again) the next time this function is evaluated with the accepted arguments.
	 * 
	 * @param filter Predicate to use to filter the arguments whose evaluation should be reset.
	 */
	public void reset(final Predicate<? super A> filter) {
		final Iterator<Map.Entry<A, R>> entries = _results.entrySet().iterator();
		while (entries.hasNext()) {
			final Map.Entry<A, R> entry = entries.next();
			final A arg = entry.getKey();
			if (filter.evaluate(arg)) {
				dispose(arg, entry.getValue());
				entries.remove();
			}
		}
	}
	
	/**
	 * Resets all evaluations of this function, discarding the possibly memoized results. The results will be computed (again) the next time this function is
	 * evaluated.
	 */
	public void resetAll() {
		final Iterator<Map.Entry<A, R>> entries = _results.entrySet().iterator();
		while (entries.hasNext()) {
			final Map.Entry<A, R> entry = entries.next();
			dispose(entry.getKey(), entry.getValue());
			entries.remove();
		}
	}
	
	/**
	 * Disposes the given result corresponding to the evaluation of this function with the given argument.
	 * <p>
	 * This methods is called when some memoized result is reset. The defaut implementation does nothing.
	 * 
	 * @param arg Argument whose evaluation produced the result to dispose.
	 * @param result Result to dispose.
	 */
	protected void dispose(final A arg, final R result) {
		// Nothing to do.
	}
	
	// Releasable.
	
	@Override
	public void release() {
		resetAll();
	}
}
