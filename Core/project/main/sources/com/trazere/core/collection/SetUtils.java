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
package com.trazere.core.collection;

import java.util.Collections;
import java.util.Set;

/**
 * The {@link SetUtils} class provides various utilities regarding {@link Set sets}.
 * 
 * @see Set
 * @since 1.0
 */
public class SetUtils {
	/**
	 * Builds an unmodifiable view of the given set.
	 * 
	 * @param <E> Type of the elements.
	 * @param set Set to wrap.
	 * @return An unmodifiable view of the given set, or the given set when is it already unmodifiable.
	 * @since 1.0
	 */
	public static <E> Set<E> unmodifiable(final Set<E> set) {
		return UNMODIFIABLE_SET_CLASS.isInstance(set) ? set : Collections.unmodifiableSet(set);
	}
	
	private static Class<?> UNMODIFIABLE_SET_CLASS = Collections.unmodifiableSet(Collections.emptySet()).getClass();
	
	private SetUtils() {
		// Prevent instantiation.
	}
}
