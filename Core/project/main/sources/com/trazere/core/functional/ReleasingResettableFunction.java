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

import com.trazere.core.lang.Releasable;

/**
 * The {@link ReleasingResettableFunction} class implements resettable functions that release their memoized results on reset.
 * 
 * @param <A> Type of the arguments.
 * @param <R> Type of the results.
 * @since 2.0
 */
public abstract class ReleasingResettableFunction<A, R extends Releasable>
extends ResettableFunction<A, R> {
	@Override
	protected void dispose(final A arg, final R result) {
		// Release.
		result.release();
		
		// Dispose.
		super.dispose(arg, result);
	}
}
