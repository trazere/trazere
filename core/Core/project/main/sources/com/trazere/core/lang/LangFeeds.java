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
package com.trazere.core.lang;

import com.trazere.core.collection.Feed;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import com.trazere.core.util.Tuples;

/**
 * The {@link LangFeeds} class provides various factories of feeds related to the language.
 */
public class LangFeeds {
	/**
	 * Builds a feed of the integers starting at the given value.
	 * 
	 * @param start Start value.
	 * @return The built feed.
	 */
	public static Feed<Integer> integer(final int start) {
		return integer(start, 1);
	}
	
	/**
	 * Builds a feed of the integers starting at the given value and with the given increment.
	 * 
	 * @param start Start value.
	 * @param increment Increment.
	 * @return The built feed.
	 */
	public static Feed<Integer> integer(final int start, final int increment) {
		return new Feed<Integer>() {
			@Override
			public Maybe<? extends Tuple2<? extends Integer, ? extends Feed<? extends Integer>>> evaluate() {
				return Maybe.some(Tuples.tuple2(start, integer(start + increment, increment)));
			}
		};
	}
	
	/**
	 * Builds a feed of the natural integers.
	 * 
	 * @return The built feed.
	 */
	public static Feed<Integer> natural() {
		return integer(0, 1);
	}
	
	private LangFeeds() {
		// Prevent instantiation.
	}
}
