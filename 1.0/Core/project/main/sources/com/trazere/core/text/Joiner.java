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

import com.trazere.core.imperative.Accumulator;
import com.trazere.core.lang.LangAccumulators;
import com.trazere.core.lang.ThrowableFactory;
import com.trazere.core.util.Tuple2;

/**
 * The {@link Joiner} class helps to join the text representation of tokens using some delimiter.
 * 
 * @param <T> Type of the tokens to join.
 */
public abstract class Joiner<T> {
	/** Factory of the failures. */
	protected final ThrowableFactory<? extends RuntimeException> _failureFactory;
	
	/**
	 * Instantiates a new joiner.
	 *
	 * @param ignoreEmpty Indicates whether the empty token representations are ignored.
	 * @param delimiter Delimiter to insert between the tokens.
	 */
	public Joiner(final boolean ignoreEmpty, final CharSequence delimiter) {
		this(ignoreEmpty, delimiter, TextException.FACTORY);
	}
	
	/**
	 * Instantiates a new joiner.
	 *
	 * @param ignoreEmpty Indicates whether the empty token representations are ignored.
	 * @param delimiter Delimiter to insert between the tokens.
	 * @param failureFactory Factory of the failures.
	 */
	public Joiner(final boolean ignoreEmpty, final CharSequence delimiter, final ThrowableFactory<? extends RuntimeException> failureFactory) {
		assert null != delimiter;
		assert null != failureFactory;
		
		// Initialization.
		_ignoreEmpty = ignoreEmpty;
		_delimiter = delimiter;
		_failureFactory = failureFactory;
	}
	
	// Ignore empty.
	
	/** Indicates whether the empty token representations are ignored. */
	protected final boolean _ignoreEmpty;
	
	/**
	 * Indicates whether the empty token representations are ignored by this joiner.
	 * 
	 * @return <code>true</code> when the empty token representations are ignored, <code>false</code> otherwise.
	 */
	public boolean isIgnoringEmpty() {
		return _ignoreEmpty;
	}
	
	// Delimiter.
	
	/** Delimiter to insert between the tokens. */
	protected final CharSequence _delimiter;
	
	/**
	 * Gets the delimiter of this joiner to insert between the tokens.
	 * 
	 * @return The delimiter.
	 */
	public CharSequence getDelimiter() {
		return _delimiter;
	}
	
	// Joining.
	
	/**
	 * Joins the given tokens.
	 *
	 * @param tokens Tokens to join.
	 * @return The representation of the joined tokens.
	 */
	public CharSequence join(final Iterable<? extends T> tokens) {
		return join(tokens, new StringBuilder()).get1();
	}
	
	/**
	 * Joins the given tokens into the given appendable.
	 * 
	 * @param <A> Type of the appendable to populate with representation of the joined tokens.
	 * @param tokens Tokens to join.
	 * @param appendable Appendable to populate with the representation of the joined tokens.
	 * @return The given appendable and a boolean indicating whether some token was joined.
	 */
	public <A extends Appendable> Tuple2<A, Boolean> join(final Iterable<? extends T> tokens, final A appendable) {
		return join(tokens, appendable, true);
	}
	
	/**
	 * Joins the given tokens into the given appendable.
	 * 
	 * @param <A> Type of the appendable to populate with representation of the joined tokens.
	 * @param tokens Tokens to join.
	 * @param appendable Appendable to populate with the representation of the joined tokens.
	 * @param first Indicates whether the next appended token will be the first one or not.
	 * @return The given appendable and a boolean indicating whether some token was joined.
	 */
	public <A extends Appendable> Tuple2<A, Boolean> join(final Iterable<? extends T> tokens, final A appendable, final boolean first) {
		try {
			final Accumulator<Boolean, Boolean> joined = LangAccumulators.or(false);
			
			// Iterate the tokens.
			for (final T token : tokens) {
				// Join the token.
				joined.add(join(token, appendable, first && !joined.get()).get2().booleanValue());
			}
			
			return new Tuple2<>(appendable, joined.get());
		} catch (final Exception exception) {
			throw _failureFactory.build("Failed joining tokens " + tokens + " into appendable \"" + appendable + "\"", exception);
		}
	}
	
	/**
	 * Joins the given tokens into the given appendable.
	 * 
	 * @param <A> Type of the appendable to populate with representation of the joined tokens.
	 * @param token Token to join.
	 * @param appendable Appendable to populate with the representation of the joined tokens.
	 * @param first Indicates whether the next appended token will be the first one or not.
	 * @return The given appendable and a boolean indicating whether the token was joined.
	 */
	public <A extends Appendable> Tuple2<A, Boolean> join(final T token, final A appendable, final boolean first) {
		try {
			// Render the token.
			final CharSequence representation = render(token);
			
			// Ignore empty.
			if (!_ignoreEmpty || representation.length() > 0) {
				// Append the delimiter if needed.
				if (!first) {
					appendable.append(_delimiter);
				}
				
				// Append the token.
				appendable.append(representation);
				
				return new Tuple2<>(appendable, true);
			} else {
				return new Tuple2<>(appendable, false);
			}
		} catch (final Exception exception) {
			throw _failureFactory.build("Failed joining token " + token + " into appendable \"" + appendable + "\"", exception);
		}
	}
	
	/**
	 * Renders the string representation of the given token.
	 * 
	 * @param token Token to render.
	 * @return The string representation of the token.
	 */
	protected abstract CharSequence render(T token);
}
