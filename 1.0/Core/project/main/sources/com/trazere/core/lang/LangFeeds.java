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

import com.trazere.core.collection.Feed;
import com.trazere.core.collection.Feeds;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import com.trazere.core.util.Tuples;

/**
 * The {@link LangFeeds} class provides various factories of {@link Feeds feeds} related to the Java language.
 * 
 * @see Feed
 * @since 1.0
 */
public class LangFeeds {
	/**
	 * Builds a feed of the integers starting at the given value.
	 * 
	 * @param start Start value.
	 * @return The built feed.
	 * @since 1.0
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
	 * @since 1.0
	 */
	public static Feed<Integer> integer(final int start, final int increment) {
		return () -> Maybe.some(Tuples.tuple2(start, integer(start + increment, increment)));
	}
	
	/**
	 * Builds a feed of the natural integers.
	 * 
	 * @return The built feed.
	 * @since 1.0
	 */
	public static Feed<Integer> natural() {
		return integer(0, 1);
	}
	
	/**
	 * Builds a feed of characters corresponding to the given string.
	 * 
	 * @param s String.
	 * @return The built feed.
	 * @since 1.0
	 */
	public static final Feed<Character> fromString(final String s) {
		return fromString(s, 0);
	}
	
	// TODO: make public and add version with a range
	private static final Feed<Character> fromString(final String s, final int index) {
		assert null != s;
		
		return () -> {
			if (s.length() > index) {
				return Maybe.some(new Tuple2<>(s.charAt(index), fromString(s, index + 1)));
			} else {
				return Maybe.none();
			}
		};
	}
	
	private LangFeeds() {
		// Prevent instantiation.
	}
}
