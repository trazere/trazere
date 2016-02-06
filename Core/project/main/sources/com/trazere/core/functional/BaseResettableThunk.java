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

/**
 * The {@link BaseResettableThunk} class provides a skeleton implementation of {@link ResettableThunk resettable thunks}.
 * 
 * @param <T> Type of the value.
 * @since 2.0
 */
public abstract class BaseResettableThunk<T>
extends BaseMemoizedThunk<T>
implements ResettableThunk<T> {
	@Override
	public void reset() {
		if (_evaluated) {
			// Dispose.
			dispose(_value);
			
			// Reset.
			_evaluated = false;
			_value = null;
		}
	}
	
	/**
	 * Disposes the given current value of this thunk.
	 * <p>
	 * This methods is called when this evaluated thunk is reset. The defaut implementation does nothing.
	 * 
	 * @param value Value to dispose.
	 * @since 2.0
	 */
	protected void dispose(final T value) {
		// Nothing to do.
	}
}
