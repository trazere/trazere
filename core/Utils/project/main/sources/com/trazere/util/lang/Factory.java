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
package com.trazere.util.lang;

import com.trazere.util.function.Function0;

/**
 * The {@link Factory} interface defines generic factories.
 * 
 * @param <T> Type of the built values.
 * @param <X> Type of the exceptions.
 * @deprecated Use {@link com.trazere.core.design.Factory}.
 */
@Deprecated
public interface Factory<T, X extends Exception>
extends Function0<T, X> {
	/**
	 * Builds a new value.
	 * 
	 * @return The built value. May be <code>null</code>.
	 * @throws X When the value cannot be built.
	 */
	public T build()
	throws X;
}
