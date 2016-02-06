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

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * The {@link BaseResettableFunction} class provides a skeleton implementation of {@link ResettableFunction resettable functions}.
 * 
 * @param <A> Type of the arguments.
 * @param <R> Type of the results.
 * @since 2.0
 */
public abstract class BaseResettableFunction<A, R>
extends BaseMemoizedFunction<A, R>
implements ResettableFunction<A, R> {
	@Override
	public Set<A> memoizedArgs() {
		return Collections.unmodifiableSet(_results.keySet());
	}
	
	@Override
	public void reset(final A arg) {
		if (_results.containsKey(arg)) {
			dispose(arg, _results.remove(arg));
		}
	}
	
	@Override
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
	
	@Override
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
	 * @since 2.0
	 */
	protected void dispose(final A arg, final R result) {
		// Nothing to do.
	}
}
