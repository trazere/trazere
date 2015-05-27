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

import com.trazere.core.lang.FiniteIntSequence;
import com.trazere.core.util.Maybe;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@link Splitters} class provides various factories of {@link Splitter splitters}.
 */
public class Splitters {
	/**
	 * Builds a splitter that uses the given character as delimiter.
	 * 
	 * @param delimiter Delimiter to use.
	 * @param includeDelimiters Indicates whether to include the delimiters as tokens or not.
	 * @param trimFilter Filter to use to trim the tokens.
	 * @param ignoreEmpty Indicates whether the empty tokens are ignored.
	 * @return The built splitter.
	 */
	public static Splitter delimiter(final char delimiter, final boolean includeDelimiters, final Maybe<? extends CharPredicate> trimFilter, final boolean ignoreEmpty) {
		return new Splitter(includeDelimiters, trimFilter, ignoreEmpty) {
			@Override
			protected Maybe<FiniteIntSequence> findDelimiter(final CharSequence s, final int offset) {
				final int index = TextUtils.indexOf(s, delimiter);
				if (index >= 0) {
					return Maybe.some(new FiniteIntSequence(index, index + 1));
				} else {
					return Maybe.none();
				}
			}
		};
	}
	
	/**
	 * Builds a splitter that uses the any character accepted by the given filter as delimiter.
	 * 
	 * @param delimiter Predicate to use to filter the delimiter characters.
	 * @param includeDelimiters Indicates whether to include the delimiters as tokens or not.
	 * @param trimFilter Filter to use to trim the tokens.
	 * @param ignoreEmpty Indicates whether the empty tokens are ignored.
	 * @return The built splitter.
	 */
	public static Splitter delimiter(final CharPredicate delimiter, final boolean includeDelimiters, final Maybe<? extends CharPredicate> trimFilter, final boolean ignoreEmpty) {
		assert null != delimiter;
		
		return new Splitter(includeDelimiters, trimFilter, ignoreEmpty) {
			@Override
			protected Maybe<FiniteIntSequence> findDelimiter(final CharSequence s, final int offset) {
				final int index = TextUtils.indexOf(s, delimiter);
				if (index >= 0) {
					return Maybe.some(new FiniteIntSequence(index, index + 1));
				} else {
					return Maybe.none();
				}
			}
		};
	}
	
	/**
	 * Builds a splitter that uses the given sequence of characters as delimiter.
	 * 
	 * @param delimiter Delimiter to use.
	 * @param includeDelimiters Indicates whether to include the delimiters as tokens or not.
	 * @param trimFilter Filter to use to trim the tokens.
	 * @param ignoreEmpty Indicates whether the empty tokens are ignored.
	 * @return The built splitter.
	 */
	public static Splitter delimiter(final CharSequence delimiter, final boolean includeDelimiters, final Maybe<? extends CharPredicate> trimFilter, final boolean ignoreEmpty) {
		assert null != delimiter;
		assert delimiter.length() > 0;
		
		return new Splitter(includeDelimiters, trimFilter, ignoreEmpty) {
			@Override
			protected Maybe<FiniteIntSequence> findDelimiter(final CharSequence s, final int offset) {
				final int index = TextUtils.indexOf(s, delimiter);
				if (index >= 0) {
					return Maybe.some(new FiniteIntSequence(index, index + delimiter.length()));
				} else {
					return Maybe.none();
				}
			}
		};
	}
	
	/**
	 * Builds a splitter that uses the given regexp pattern as delimiter.
	 * 
	 * @param delimiter Delimiter to use.
	 * @param includeDelimiters Indicates whether to include the delimiters as tokens or not.
	 * @param trimFilter Filter to use to trim the tokens.
	 * @param ignoreEmpty Indicates whether the empty tokens are ignored.
	 * @return The built splitter.
	 */
	public static Splitter delimiter(final Pattern delimiter, final boolean includeDelimiters, final Maybe<? extends CharPredicate> trimFilter, final boolean ignoreEmpty) {
		assert null != delimiter;
		
		return new Splitter(includeDelimiters, trimFilter, ignoreEmpty) {
			@Override
			protected Maybe<FiniteIntSequence> findDelimiter(final CharSequence s, final int offset) {
				final Matcher matcher = delimiter.matcher(s);
				if (matcher.find(offset)) {
					return Maybe.some(new FiniteIntSequence(matcher.start(), matcher.end()));
				} else {
					return Maybe.none();
				}
			}
		};
	}
	
	/**
	 * Builds a splitter that cuts tokens of the given length.
	 * <p>
	 * The last token may be shorter.
	 * 
	 * @param n Length of the tokens.
	 * @param trimFilter Filter to use to trim the tokens.
	 * @param ignoreEmpty Indicates whether the empty tokens are ignored.
	 * @return The built splitter.
	 */
	public static Splitter length(final int n, final Maybe<? extends CharPredicate> trimFilter, final boolean ignoreEmpty) {
		assert n > 0;
		
		return new Splitter(false, trimFilter, ignoreEmpty) {
			@Override
			protected Maybe<FiniteIntSequence> findDelimiter(final CharSequence s, final int offset) {
				final int split = offset + n;
				if (split < s.length()) { // Note: no trailing delimiter when the lenght of last token is n
					return Maybe.some(new FiniteIntSequence(split, split));
				} else {
					return Maybe.none();
				}
			}
		};
	}
	
	private Splitters() {
		// Prevent instantiation.
	}
}
