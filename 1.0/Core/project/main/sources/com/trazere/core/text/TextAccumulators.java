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

import com.trazere.core.functional.Functions;
import com.trazere.core.imperative.Accumulator;
import com.trazere.core.imperative.AccumulatorUtils;
import com.trazere.core.lang.LangAccumulators;
import com.trazere.core.lang.ThrowableFactory;
import java.io.IOException;

/**
 * The {@link TextAccumulators} class provides various factories of {@link Accumulator accumulators} related to text.
 */
public class TextAccumulators {
	/**
	 * Builds an accumulator that appends to the given appendable.
	 * 
	 * @param <A> Type of the appendable.
	 * @param appendable Appendable that should be appended to.
	 * @return The built accumulator.
	 * @see Appendable#append(CharSequence)
	 */
	public static <A extends Appendable> Accumulator<CharSequence, A> append(final A appendable) {
		return append(appendable, TextException.FACTORY);
	}
	
	/**
	 * Builds an accumulator that appends to the given appendable.
	 * 
	 * @param <A> Type of the appendable.
	 * @param appendable Appendable that should be appended to.
	 * @param failureFactory Factory of the append failures.
	 * @return The built accumulator.
	 * @see Appendable#append(CharSequence)
	 */
	public static <A extends Appendable> Accumulator<CharSequence, A> append(final A appendable, final ThrowableFactory<? extends RuntimeException> failureFactory) {
		return new Accumulator<CharSequence, A>() {
			@Override
			public void add(final CharSequence s) {
				try {
					appendable.append(s);
				} catch (final IOException exception) {
					throw failureFactory.build("Failed appending \"" + s + "\"", exception);
				}
			}
			
			@Override
			public A get() {
				return appendable;
			}
		};
	}
	
	/**
	 * Builds an accumulator that concatenate character sequences.
	 * 
	 * @param initialState The initial character sequence.
	 * @return The built accumulator.
	 */
	public static Accumulator<CharSequence, CharSequence> concat(final CharSequence initialState) {
		return AccumulatorUtils.mapState(append(new StringBuilder(initialState)), Functions.identity());
	}
	
	/**
	 * Builds an accumulator that joins the text representation of the accumulated tokens into the given appendable.
	 * 
	 * @param <T> Type of the tokens.
	 * @param <A> Type of the appendable to populate.
	 * @param joiner Joiner to use.
	 * @param appendable Appendable to populate with the joined representations of the tokens.
	 * @param first Indicates whether the next appended token will be the first one or not.
	 * @return The built accumulator.
	 * @see Joiner#join(Object, Appendable, boolean)
	 */
	public static <T, A extends Appendable> Accumulator<T, A> join(final Joiner<? super T> joiner, final A appendable, final boolean first) {
		assert null != joiner;
		assert null != appendable;
		
		return new Accumulator<T, A>() {
			private final Accumulator<Boolean, Boolean> _first = LangAccumulators.and(first);
			
			@Override
			public void add(final T token) {
				_first.add(!joiner.join(token, appendable, _first.get()).get2().booleanValue());
			}
			
			@Override
			public A get() {
				return appendable;
			}
		};
	}
	
	private TextAccumulators() {
		// Prevent instantiation.
	}
}
