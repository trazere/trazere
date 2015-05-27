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
package com.trazere.core.collection;

import com.trazere.core.functional.Function;
import java.util.Collection;
import java.util.Map;

/**
 * The {@link MultimapFunctions} class provides various factories of functions related to {@link Map maps}.
 */
public class MultimapFunctions {
	/**
	 * Builds a function that gets the values associated to keys in the given multimap.
	 *
	 * @param <K> Type of the keys.
	 * @param <C> Type of the collections of values.
	 * @param multimap Multimap to read.
	 * @return The built function.
	 * @see MapUtils#get(Map, Object)
	 */
	public static <K, C extends Collection<?>> Function<K, C> get(final Multimap<? super K, ?, ? extends C> multimap) {
		assert null != multimap;
		
		return key -> multimap.get(key);
	}
	
	private MultimapFunctions() {
		// Prevent instantiation.
	}
}
