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
package com.trazere.core.reference;

import com.trazere.core.functional.Thunk;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Maybe.None;
import com.trazere.core.util.Maybe.Some;

/**
 * The {@link Reference} interface defines references to a value.
 * <p>
 * References are placeholders for an optional value.
 * 
 * @param <T> Type of the referenced value.
 * @since 2.0
 */
public interface Reference<T> {
	/**
	 * Tests whether this reference is set.
	 * 
	 * @return <code>true</code> when the reference is set, <code>false</code> otherwise.
	 * @since 2.0
	 */
	boolean isSet();
	
	/**
	 * Gets the value set in this reference.
	 * <p>
	 * The reference must be set.
	 * 
	 * @return The referenced value.
	 * @throws ReferenceNotSetException When the reference is not set.
	 * @since 2.0
	 */
	T get()
	throws ReferenceNotSetException;
	
	/**
	 * Gets the value set in this reference.
	 * 
	 * @param defaultValue Default value.
	 * @return The referenced value or the given default value when the reference is not set.
	 * @since 2.0
	 */
	default T get(final T defaultValue) {
		if (isSet()) {
			return get();
		} else {
			return defaultValue;
		}
	}
	
	/**
	 * Gets the value set in this reference.
	 * 
	 * @param defaultValue Default value.
	 * @return The referenced value or the given default value when the reference is not set.
	 * @since 2.0
	 */
	default T get(final Thunk<? extends T> defaultValue) {
		if (isSet()) {
			return get();
		} else {
			return defaultValue.evaluate();
		}
	}
	
	/**
	 * Gets a view of the value set in this reference.
	 * <p>
	 * The referenced value is wrapped in an instance of {@link Maybe} using the {@link Some} constructor when the reference is set and the {@link None}
	 * constructor when the reference is not set.
	 * 
	 * @return The view of the referenced value.
	 * @since 2.0
	 */
	default Maybe<T> asMaybe() {
		if (isSet()) {
			return Maybe.some(get());
		} else {
			return Maybe.none();
		}
	}
}
