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
 * The {@link ResettableThunk} interface defines memoized thunks that can be re-evaluated.
 * 
 * @param <T> Type of the value.
 * @since 2.0
 */
public interface ResettableThunk<T>
extends MemoizedThunk<T>, Releasable {
	/**
	 * Resets this thunk, discarding its possibly memoized value. The value will be computed (again) the next time this thunk is evaluated.
	 * 
	 * @since 2.0
	 */
	void reset();
	
	// Releasable.
	
	@Override
	default void release() {
		reset();
	}
}
