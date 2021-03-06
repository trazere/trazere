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
package com.trazere.core.text;

import com.trazere.core.functional.Function;

/**
 * The {@link Joiners} class provides various factories of {@link Joiner joiners}.
 * 
 * @see Joiner
 * @since 2.0
 */
public class Joiners {
	/**
	 * Builds a new joiner.
	 *
	 * @param <T> Type of the tokens.
	 * @param renderer Function to use to compute the string representation of the tokens.
	 * @param ignoreEmpty Indicates whether the empty token representations are ignored.
	 * @param delimiter Delimiter to insert between the tokens.
	 * @return The built joiner.
	 * @since 2.0
	 */
	public static <T> Joiner<T> joiner(final Function<? super T, ? extends CharSequence> renderer, final boolean ignoreEmpty, final CharSequence delimiter) {
		assert null != renderer;
		
		return new Joiner<T>(ignoreEmpty, delimiter) {
			@Override
			protected CharSequence render(final T token) {
				return renderer.evaluate(token);
			}
		};
	}
	
	/**
	 * Builds a new joiner of sequences of characters.
	 *
	 * @param ignoreEmpty Indicates whether the empty token representations are ignored.
	 * @param delimiter Delimiter to insert between the tokens.
	 * @return The built joiner.
	 * @since 2.0
	 */
	public static Joiner<CharSequence> joiner(final boolean ignoreEmpty, final CharSequence delimiter) {
		return new Joiner<CharSequence>(ignoreEmpty, delimiter) {
			@Override
			protected CharSequence render(final CharSequence token) {
				return token;
			}
		};
	}
	
	private Joiners() {
		// Prevent instantiation.
	}
}
