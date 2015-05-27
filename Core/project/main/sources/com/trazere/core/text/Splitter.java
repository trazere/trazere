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

import com.trazere.core.collection.Feed;
import com.trazere.core.collection.FeedUtils;
import com.trazere.core.collection.Feeds;
import com.trazere.core.lang.FiniteIntSequence;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;

/**
 * The {@link Splitter} class helps to split the sequences of characters according to some delimiter.
 */
public abstract class Splitter {
	/**
	 * Instantiates a new splitter.
	 * 
	 * @param includeDelimiters Indicates whether to include the delimiters as tokens or not.
	 * @param trimFilter Filter to use to trim the tokens.
	 * @param ignoreEmpty Indicates whether the empty tokens are ignored.
	 */
	public Splitter(final boolean includeDelimiters, final Maybe<? extends CharPredicate> trimFilter, final boolean ignoreEmpty) {
		assert null != trimFilter;
		
		// Initialization.
		_includeDelimiters = includeDelimiters;
		_trimFilter = trimFilter;
		_ignoreEmpty = ignoreEmpty;
	}
	
	// Delimiter.
	
	/**
	 * Finds the first delimiter in the given sequence of characters from the given offset.
	 * 
	 * @param s Sequence of characters to look into.
	 * @param offset Index from which the search should start.
	 * @return The range of the delimiter, or nothing when no delimiters are found.
	 */
	protected abstract Maybe<FiniteIntSequence> findDelimiter(final CharSequence s, final int offset);
	
	// Include delimiters.
	
	/** Indicates whether to include the delimiters as tokens or not. */
	protected final boolean _includeDelimiters;
	
	/**
	 * Indicates whether this splitter includes the delimiters as tokens or not.
	 * 
	 * @return <code>true</code> when the delimiters are included as tokens, <code>false</code> otherwise.
	 */
	public boolean isIncludingDelimiters() {
		return _includeDelimiters;
	}
	
	// Trim filter.
	
	/** Filter to use to trim the tokens. */
	protected final Maybe<? extends CharPredicate> _trimFilter;
	
	/**
	 * Gets the filter used by this splitter to trim the tokens.
	 * 
	 * @return The filter, or nothing when the tokens are not trimmed.
	 */
	public Maybe<? extends CharPredicate> getTrimFilter() {
		return _trimFilter;
	}
	
	// Ignore empty.
	
	/** Indicates whether the empty tokens are ignored. */
	protected final boolean _ignoreEmpty;
	
	/**
	 * Indicates whether the empty token representations are ignored by this splitter.
	 * 
	 * @return <code>true</code> when the empty token representations are ignored, <code>false</code> otherwise.
	 */
	public boolean isIgnoringEmpty() {
		return _ignoreEmpty;
	}
	
	// Splitting.
	
	/**
	 * Splits the given sequence of characters.
	 * 
	 * @param s Sequence of characeters to split.
	 * @return A feed of the split tokens.
	 */
	public Feed<CharSequence> split(final CharSequence s) {
		// Split.
		final Feed<CharSequence> tokens = rawSplit(s, 0);
		
		// Trim.
		final Feed<CharSequence> trimmedTokens;
		if (_trimFilter.isSome()) {
			final CharPredicate trimFilter = _trimFilter.asSome().getValue();
			trimmedTokens = FeedUtils.map(tokens, token -> TextUtils.trim(token, trimFilter));
		} else {
			trimmedTokens = tokens;
		}
		
		// Ignore empty.
		return _ignoreEmpty ? FeedUtils.filter(trimmedTokens, TextPredicates.isEmpty()) : trimmedTokens;
	}
	
	/**
	 * Raw splits the given sequence of characters without regarding to trimming and empty tokens.
	 * 
	 * @param s Sequence of characeters to split.
	 * @param offset Index from which the sequence of characters should be split.
	 * @return A feed of the raw split tokens.
	 */
	protected Feed<CharSequence> rawSplit(final CharSequence s, final int offset) {
		assert null != s;
		assert offset >= 0;
		
		return new Feed<CharSequence>() {
			@Override
			public Maybe<? extends Tuple2<? extends CharSequence, ? extends Feed<? extends CharSequence>>> evaluate() {
				final Maybe<FiniteIntSequence> maybeDelimiterRange = findDelimiter(s, offset);
				if (maybeDelimiterRange.isSome()) {
					final FiniteIntSequence delimiterRange = maybeDelimiterRange.asSome().getValue();
					final CharSequence token = s.subSequence(offset, delimiterRange.getStart());
					final Feed<CharSequence> tail = rawSplit(s, delimiterRange.getEnd());
					if (_includeDelimiters) {
						final CharSequence delimiter = s.subSequence(delimiterRange.getStart(), delimiterRange.getEnd());
						return Maybe.some(new Tuple2<>(token, Feeds.feed(delimiter, tail)));
					} else {
						return Maybe.some(new Tuple2<>(token, tail));
					}
				} else {
					final CharSequence token = s.subSequence(offset, s.length());
					return Maybe.some(new Tuple2<>(token, Feeds.empty()));
				}
			}
		};
	}
}
