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
import com.trazere.core.imperative.ExIterator;
import com.trazere.core.text.TextIterables;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import com.trazere.core.util.Tuples;
import java.util.NoSuchElementException;

/**
 * The {@link LangFeeds} class provides various factories of {@link Feeds feeds} related to the Java language.
 * 
 * @see Feed
 * @since 2.0
 */
public class LangFeeds {
	/**
	 * Builds a feed of the integers starting at the given value.
	 * 
	 * @param start Start value.
	 * @return The built feed.
	 * @since 2.0
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
	 * @since 2.0
	 */
	public static Feed<Integer> integer(final int start, final int increment) {
		return new Feed<Integer>() {
			// Feed.
			
			@Override
			public boolean isEmpty() {
				return false;
			}
			
			@Override
			public Integer head() {
				return start;
			}
			
			@Override
			public Maybe<Integer> optionalHead() {
				return Maybe.some(head());
			}
			
			@Override
			public Feed<Integer> tail() {
				return integer(start + increment, increment);
			}
			
			@Override
			public Maybe<Feed<Integer>> optionalTail() {
				return Maybe.some(tail());
			}
			
			@Override
			public Tuple2<Integer, Feed<Integer>> item() {
				return Tuples.tuple2(start, integer(start + increment, increment));
			}
			
			@Override
			public Maybe<Tuple2<Integer, Feed<Integer>>> optionalItem() {
				return Maybe.some(item());
			}
			
			// Iterable.
			
			@Override
			public ExIterator<Integer> iterator() {
				return new InfiniteIntSequence(start, increment).iterator();
			}
		};
	}
	
	/**
	 * Builds a feed of the natural integers.
	 * 
	 * @return The built feed.
	 * @since 2.0
	 */
	public static Feed<Integer> natural() {
		return integer(0, 1);
	}
	
	/**
	 * Builds a feed of characters corresponding to the given string.
	 * 
	 * @param s String.
	 * @return The built feed.
	 * @since 2.0
	 */
	public static final Feed<Character> fromString(final String s) {
		return fromString(s, 0);
	}
	
	// TODO: make public and add version with a range
	private static final Feed<Character> fromString(final String s, final int index) {
		assert null != s;
		
		return new Feed<Character>() {
			@Override
			public boolean isEmpty() {
				return index >= s.length();
			}
			
			@Override
			public Character head()
			throws NoSuchElementException {
				if (s.length() > index) {
					return s.charAt(index);
				} else {
					throw new NoSuchElementException();
				}
			}
			
			@Override
			public Maybe<Character> optionalHead() {
				if (s.length() > index) {
					return Maybe.some(s.charAt(index));
				} else {
					return Maybe.none();
				}
			}
			
			@Override
			public Feed<Character> tail()
			throws NoSuchElementException {
				if (s.length() > index) {
					return fromString(s, index + 1);
				} else {
					throw new NoSuchElementException();
				}
			}
			
			@Override
			public Maybe<Feed<Character>> optionalTail() {
				if (s.length() > index) {
					return Maybe.some(fromString(s, index + 1));
				} else {
					return Maybe.none();
				}
			}
			
			@Override
			public Tuple2<Character, Feed<Character>> item()
			throws NoSuchElementException {
				if (s.length() > index) {
					return new Tuple2<>(s.charAt(index), fromString(s, index + 1));
				} else {
					throw new NoSuchElementException();
				}
			}
			
			@Override
			public Maybe<Tuple2<Character, Feed<Character>>> optionalItem() {
				if (s.length() > index) {
					return Maybe.some(new Tuple2<>(s.charAt(index), fromString(s, index + 1)));
				} else {
					return Maybe.none();
				}
			}
			
			// Iterable.
			
			@Override
			public ExIterator<Character> iterator() {
				return TextIterables.fromCharSequence(s).iterator();
			}
		};
	}
	
	private LangFeeds() {
		// Prevent instantiation.
	}
}
