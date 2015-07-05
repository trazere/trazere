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
package com.trazere.util.closure;

import com.trazere.core.functional.MemoizedThunk;
import com.trazere.util.lang.WrapException;
import com.trazere.util.type.Maybe;
import com.trazere.util.type.TypeUtils;

/**
 * The {@link ClosureUtils} class provides helpers regarding the closures.
 * 
 * @deprecated Use {@link com.trazere.core}.
 */
@Deprecated
public class ClosureUtils {
	/**
	 * Evaluates the given closure in a thread safe way.
	 * 
	 * @param <T> Type of the value.
	 * @param <X> Type of the exceptions.
	 * @param closure The closure.
	 * @return The value of the closure.
	 * @throws X When the evaluation of the closure fails.
	 * @deprecated Use {@link com.trazere.core.functional.ThunkUtils#synchronizedEvaluate(com.trazere.core.functional.Thunk)}.
	 */
	@Deprecated
	public static <T, X extends Exception> T synchronizedEvaluate(final Closure<T, ? extends X> closure)
	throws X {
		assert null != closure;
		
		synchronized (closure) {
			return closure.evaluate();
		}
	}
	
	/**
	 * Resets the given closure in a thread safe way.
	 * 
	 * @param closure The closure.
	 * @deprecated Use {@link com.trazere.core.functional.ThunkUtils#synchronizedReset(com.trazere.core.functional.ResettableThunk)}.
	 */
	@Deprecated
	public static void synchronizedReset(final ResettableClosure<?, ?> closure) {
		assert null != closure;
		
		synchronized (closure) {
			closure.reset();
		}
	}
	
	/**
	 * Adapts the given closure to a memoized thunk.
	 * 
	 * @param <T> Type of the value.
	 * @param closure Closure to adapt.
	 * @return The adapted memoized thunk.
	 * @deprecated Use {@link MemoizedThunk}.
	 */
	@Deprecated
	public static <T> MemoizedThunk<T> toMemoizedThunk(final Closure<? extends T, ?> closure) {
		assert null != closure;
		
		return new MemoizedThunk<T>() {
			@Override
			public T evaluate() {
				try {
					return closure.evaluate();
				} catch (final Exception exception) {
					throw new WrapException(exception);
				}
			}
			
			@Override
			public boolean isMemoized() {
				return closure.isEvaluated();
			}
			
			@Override
			public com.trazere.core.util.Maybe<T> probe() {
				return TypeUtils.toMaybe(closure.asMaybe());
			}
		};
	}
	
	/**
	 * Adapts the given memoized thunk to a closure.
	 * 
	 * @param <T> Type of the value.
	 * @param thunk Memoized thunk to adapt.
	 * @return The adapted closure.
	 * @deprecated Use {@link MemoizedThunk}.
	 */
	@Deprecated
	public static <T> Closure<T, RuntimeException> fromMemoizedThunk(final MemoizedThunk<? extends T> thunk) {
		assert null != thunk;
		
		return new Closure<T, RuntimeException>() {
			@Override
			public T evaluate() {
				return thunk.evaluate();
			}
			
			@Override
			public boolean isEvaluated() {
				return thunk.isMemoized();
			}
			
			@Override
			public Maybe<T> asMaybe() {
				return TypeUtils.fromMaybe(thunk.probe());
			}
		};
	}
	
	private ClosureUtils() {
		// Prevents instantiation.
	}
}
