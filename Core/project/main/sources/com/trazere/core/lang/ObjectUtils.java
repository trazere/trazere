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
package com.trazere.core.lang;

import com.trazere.core.functional.Thunk;

/**
 * The {@link ObjectUtils} class provides various utilities related to {@link Object objects}.
 * 
 * @see Object
 * @since 2.0
 */
public class ObjectUtils {
	/**
	 * Casts the given object to some other type.
	 * <p>
	 * This methods aims to work around various limitations of the Java type system where regular casts cannot be used. It performs no verifications whatsoever
	 * and should be used as seldom as possible because it is inherently unsafe.
	 *
	 * @param <R> Target type.
	 * @param object Object to cast. May be <code>null</code>.
	 * @return The given casted object. May be <code>null</code>.
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <R> R cast(final Object object) {
		return (R) object;
	}
	
	/**
	 * Makes the given object safe by replacing <code>null</code>.
	 *
	 * @param <T> Type of the object.
	 * @param object Unsafe object to make safe. May be <code>null</code>.
	 * @param nullReplacement Object to use instead of <code>null</code>.
	 * @return The given object or the object to use instead of <code>null</code> when the given object is <code>null</code>.
	 * @since 2.0
	 */
	public static <T> T safe(final T object, final T nullReplacement) {
		return null != object ? object : nullReplacement;
	}
	
	/**
	 * Makes the given object safe by replacing <code>null</code>.
	 *
	 * @param <T> Type of the object.
	 * @param object Unsafe object to make safe. May be <code>null</code>.
	 * @param nullReplacement Object to use instead of <code>null</code>.
	 * @return The given object or the object to use instead of <code>null</code> when the given object is <code>null</code>.
	 * @since 2.0
	 */
	public static <T> T safe(final T object, final Thunk<? extends T> nullReplacement) {
		return null != object ? object : nullReplacement.evaluate();
	}
	
	private ObjectUtils() {
		// Prevent instantiation.
	}
}
