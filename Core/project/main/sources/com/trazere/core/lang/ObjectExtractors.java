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

import com.trazere.core.functional.Function;
import com.trazere.core.util.Maybe;

/**
 * The {@link ObjectExtractors} class provides various extractors related to {@link Object objects}.
 * <p>
 * An extractor is {@link Function function} that combines a map operation and a filter operation.
 * 
 * @see Function
 * @see Maybe
 * @see Object
 * @since 2.0
 */
public class ObjectExtractors {
	// TODO: move to LangExtractors ?
	/**
	 * Builds an extractor that matches objects against the given type.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <R> Type of the result values.
	 * @param type The type.
	 * @return The built extractor.
	 * @see ObjectUtils#match(Object, Class)
	 * @since 2.0
	 */
	public static <T, R extends T> Function<T, Maybe<R>> match(final Class<R> type) {
		assert null != type;
		
		return object -> ObjectUtils.match(object, type);
	}
	
	private ObjectExtractors() {
		// Prevents instantiation.
	}
}
