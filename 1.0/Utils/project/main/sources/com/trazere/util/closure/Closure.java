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

import com.trazere.util.function.Function0;
import com.trazere.util.type.Maybe;
import com.trazere.util.type.Maybe.None;
import com.trazere.util.type.Maybe.Some;

// TODO: move to function package ?

/**
 * The {@link Closure} interface represents closures.
 * <p>
 * Closures provide an abstraction for lazy evaluation. The computation of the values relies solely on the contexts captured by the closures.
 * 
 * @param <T> Type of the value.
 * @param <X> Type of the exceptions.
 * @deprecated Use {@link com.trazere.core.functional.MemoizedThunk}.
 */
@Deprecated
public interface Closure<T, X extends Exception>
extends Function0<T, X> {
	/**
	 * Evaluate the receiver closure.
	 * 
	 * @return The value of the closure. May be <code>null</code>.
	 * @throws X When the evaluation of the closure fails.
	 */
	@Override
	public T evaluate()
	throws X;
	
	/**
	 * Indicate whether the receiver closure has been evaluated.
	 * 
	 * @return <code>true</code> if the closure has been evaluated, <code>false</code> otherwise.
	 * @deprecated Use {@link com.trazere.core.functional.MemoizedThunk#isMemoized()}.
	 */
	@Deprecated
	public boolean isEvaluated();
	
	/**
	 * Get a view of the value of the receiver closure as an instance of {@link Maybe}.
	 * 
	 * @return The evaluated value wrapped in {@link Some}, or {@link None} when the closure has not been evaluated yet.
	 * @deprecated Use {@link com.trazere.core.functional.MemoizedThunk#get()}.
	 */
	@Deprecated
	public Maybe<T> asMaybe();
}
