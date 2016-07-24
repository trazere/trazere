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
package com.trazere.core.util;

import com.trazere.core.util.Maybe.None;
import com.trazere.core.util.Maybe.Some;
import java.util.Optional;

/**
 * The {@link Maybes} class provides various factories of {@link Maybe maybes}.
 * 
 * @see Maybe
 * @since 2.0
 */
public class Maybes {
	/**
	 * Builds an instance of {@link Maybe} from the given nullable value according to the following rules:
	 * <ul>
	 * <li><code>null</code> are translated to an absent value ({@link None}),
	 * <li>non-<code>null</code> values are wrapped in available values ({@link Some}).
	 * <p>
	 * This method aims to simplify the interoperability with legacy Java code.
	 * 
	 * @param <T> Type of the value.
	 * @param value Value to wrap.
	 * @return The built instance.
	 * @since 2.0
	 */
	public static <T> Maybe<T> fromNullable(final T value) {
		if (null != value) {
			return Maybe.some(value);
		} else {
			return Maybe.none();
		}
	}
	
	/**
	 * Builds an instance of {@link Maybe} from the given instance of {@link Optional}.
	 * 
	 * @param <T> Type of the value.
	 * @param optional Instance of {@link Optional} to convert.
	 * @return The built instance.
	 * @since 2.0
	 */
	public static <T> Maybe<T> fromOptional(final Optional<? extends T> optional) {
		assert null != optional;
		
		if (optional.isPresent()) {
			return Maybe.some(optional.get());
		} else {
			return Maybe.none();
		}
	}
	
	private Maybes() {
		// Prevent instantiation.
	}
}
