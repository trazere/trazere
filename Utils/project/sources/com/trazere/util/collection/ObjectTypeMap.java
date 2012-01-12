/*
 *  Copyright 2006-2012 Julien Dufour
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
package com.trazere.util.collection;

import com.trazere.util.function.Function1;

/**
 * The {@link ObjectTypeMap} interface defines maps from Java class hierarchies to values.
 * 
 * @param <T> Upper bound type.
 * @param <V> Type of the values.
 * @param <X> Type of the exceptions.
 */
public interface ObjectTypeMap<T, V, X extends Exception>
extends Function1<Class<? extends T>, V, X> {
	/**
	 * Gets the value associated to the given type.
	 * 
	 * @param type The type.
	 * @return The value. May be <code>null</code>.
	 * @throws X When the value cannot be got.
	 */
	public V get(final Class<? extends T> type)
	throws X;
}
