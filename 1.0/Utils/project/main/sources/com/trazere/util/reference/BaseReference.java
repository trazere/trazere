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
package com.trazere.util.reference;

import com.trazere.util.function.Function0;
import com.trazere.util.lang.InternalException;

/**
 * The {@link BaseReference} class provides a skeleton implementation of references.
 * 
 * @param <T> Type of the referenced value.
 * @deprecated Use {@link com.trazere.core.reference.Reference}.
 */
@Deprecated
public abstract class BaseReference<T>
implements Reference<T> {
	@Override
	public T get(final T defaultValue) {
		if (isSet()) {
			try {
				return get();
			} catch (final ReferenceNotSetException exception) {
				throw new InternalException(exception);
			}
		} else {
			return defaultValue;
		}
	}
	
	@Override
	public <X extends Exception> T get(final Function0<? extends T, X> defaultValue)
	throws X {
		if (isSet()) {
			try {
				return get();
			} catch (final ReferenceNotSetException exception) {
				throw new InternalException(exception);
			}
		} else {
			return defaultValue.evaluate();
		}
	}
}
