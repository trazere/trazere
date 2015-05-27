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
package com.trazere.core.text;

import com.trazere.core.imperative.IteratorUtils;
import com.trazere.core.lang.FiniteIntSequence;
import java.util.Iterator;

/**
 * The {@link TextIterables} class provides various factories of {@link Iterable iterable} regarding text.
 * 
 * @see Iterable
 */
public class TextIterables {
	/**
	 * Builds an iterable from the given sequence of characters.
	 * 
	 * @param s Sequence of characters to iterate over.
	 * @return The built iterable.
	 */
	public static Iterable<Character> fromCharSequence(final CharSequence s) {
		assert null != s;
		
		return new Iterable<Character>() {
			@Override
			public Iterator<Character> iterator() {
				return IteratorUtils.map(new FiniteIntSequence(0, s.length()).iterator(), i -> s.charAt(i));
			}
		};
	}
	
	private TextIterables() {
		// Prevent instantiation.
	}
}
