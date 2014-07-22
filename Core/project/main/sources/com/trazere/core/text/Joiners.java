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

import com.trazere.core.functional.Function;
import com.trazere.core.lang.ThrowableFactory;

/**
 * The {@link Joiners} class provides various factories of {@link Joiner joiners}.
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
	 */
	public static <T> Joiner<T> joiner(final Function<? super T, ? extends CharSequence> renderer, final boolean ignoreEmpty, final CharSequence delimiter) {
		return joiner(renderer, ignoreEmpty, delimiter, TextException.FACTORY);
	}
	
	/**
	 * Builds a new joiner.
	 * 
	 * @param <T> Type of the tokens.
	 * @param renderer Function to use to compute the string representation of the tokens.
	 * @param ignoreEmpty Indicates whether the empty token representations are ignored.
	 * @param delimiter Delimiter to insert between the tokens.
	 * @param failureFactory Factory of the failures.
	 * @return The built joiner.
	 */
	public static <T> Joiner<T> joiner(final Function<? super T, ? extends CharSequence> renderer, final boolean ignoreEmpty, final CharSequence delimiter, final ThrowableFactory<? extends RuntimeException> failureFactory) {
		assert null != renderer;
		
		return new Joiner<T>(ignoreEmpty, delimiter, failureFactory) {
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
	 */
	public static Joiner<CharSequence> joiner(final boolean ignoreEmpty, final CharSequence delimiter) {
		return joiner(ignoreEmpty, delimiter, TextException.FACTORY);
	}
	
	/**
	 * Builds a new joiner of sequences of characters.
	 * 
	 * @param ignoreEmpty Indicates whether the empty token representations are ignored.
	 * @param delimiter Delimiter to insert between the tokens.
	 * @param failureFactory Factory of the failures.
	 * @return The built joiner.
	 */
	public static Joiner<CharSequence> joiner(final boolean ignoreEmpty, final CharSequence delimiter, final ThrowableFactory<? extends RuntimeException> failureFactory) {
		return new Joiner<CharSequence>(ignoreEmpty, delimiter, failureFactory) {
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
