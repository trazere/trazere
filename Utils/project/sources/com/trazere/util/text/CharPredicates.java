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
package com.trazere.util.text;

/**
 * The {@link CharPredicates} class provides various common character predicate functions.
 * 
 * @see CharPredicate
 */
public class CharPredicates {
	/**
	 * Builds a predicate that evaluates to <code>true</code> for all characters.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built predicate.
	 */
	@SuppressWarnings("unchecked")
	public static <X extends Exception> CharPredicate<X> all() {
		return (CharPredicate<X>) _ALL;
	}
	
	private static final CharPredicate<?> _ALL = CharPredicates.<RuntimeException>constant(true);
	
	/**
	 * Builds a predicate that evaluates to <code>false</code> for all characters.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built predicate.
	 */
	@SuppressWarnings("unchecked")
	public static <X extends Exception> CharPredicate<X> none() {
		return (CharPredicate<X>) _NONE;
	}
	
	private static final CharPredicate<?> _NONE = CharPredicates.<RuntimeException>constant(false);
	
	/**
	 * Builds a predicate that evaluates to the given result for all characters.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param result The result.
	 * @return The built predicate.
	 */
	public static <X extends Exception> CharPredicate<X> constant(final boolean result) {
		return new CharPredicate<X>() {
			@Override
			public boolean evaluate(final char c) {
				return result;
			}
		};
	}
	
	/**
	 * Builds a predicate that evaluates to <code>true</code> for whitespace characters.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built predicate.
	 * @see Character#isWhitespace(char)
	 */
	@SuppressWarnings("unchecked")
	public static <X extends Exception> CharPredicate<X> whitespace() {
		return (CharPredicate<X>) _WHITESPACE;
	}
	
	private static final CharPredicate<?> _WHITESPACE = new CharPredicate<RuntimeException>() {
		@Override
		public boolean evaluate(final char c) {
			return Character.isWhitespace(c);
		}
	};
	
	/**
	 * Builds a predicate corresponding to the inverse of the given predicate.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @return The built predicate.
	 */
	public static <X extends Exception> CharPredicate<X> not(final CharPredicate<X> predicate) {
		assert null != predicate;
		
		// Build.
		return new CharPredicate<X>() {
			@Override
			public boolean evaluate(final char c)
			throws X {
				return !predicate.evaluate(c);
			}
		};
	}
	
	/**
	 * Builds a predicate corresponding to the conjonction of the given predicates.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param predicate1 The first predicate.
	 * @param predicate2 The second predicate.
	 * @return The built predicate.
	 */
	public static <X extends Exception> CharPredicate<X> and(final CharPredicate<? extends X> predicate1, final CharPredicate<? extends X> predicate2) {
		assert null != predicate1;
		assert null != predicate2;
		
		// Build.
		return new CharPredicate<X>() {
			@Override
			public boolean evaluate(final char c)
			throws X {
				return predicate1.evaluate(c) && predicate2.evaluate(c);
			}
		};
	}
	
	/**
	 * Builds a predicate corresponding to the disjunction of the given predicates.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param predicate1 The first predicate.
	 * @param predicate2 The second predicate.
	 * @return The built predicate.
	 */
	public static <X extends Exception> CharPredicate<X> or(final CharPredicate<? extends X> predicate1, final CharPredicate<? extends X> predicate2) {
		assert null != predicate1;
		assert null != predicate2;
		
		// Build.
		return new CharPredicate<X>() {
			@Override
			public boolean evaluate(final char c)
			throws X {
				return predicate1.evaluate(c) || predicate2.evaluate(c);
			}
		};
	}
	
	/**
	 * Builds a predicate that evaluates to <code>true</code> for digit characters.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built predicate.
	 * @see Character#isDigit(char)
	 */
	@SuppressWarnings("unchecked")
	public static <X extends Exception> CharPredicate<X> digit() {
		return (CharPredicate<X>) _DIGIT;
	}
	
	private static final CharPredicate<?> _DIGIT = new CharPredicate<RuntimeException>() {
		@Override
		public boolean evaluate(final char c) {
			return Character.isDigit(c);
		}
	};
	
	/**
	 * Builds a predicate that evaluates to <code>true</code> for letter characters.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built predicate.
	 * @see Character#isLetter(char)
	 */
	@SuppressWarnings("unchecked")
	public static <X extends Exception> CharPredicate<X> letter() {
		return (CharPredicate<X>) _LETTER;
	}
	
	private static final CharPredicate<?> _LETTER = new CharPredicate<RuntimeException>() {
		@Override
		public boolean evaluate(final char c) {
			return Character.isLetter(c);
		}
	};
	
	/**
	 * Builds a predicate that evaluates to <code>true</code> for alphanumeric and <code>'_'</code> characters.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built predicate.
	 * @see Character#isLetterOrDigit(char)
	 */
	@SuppressWarnings("unchecked")
	public static <X extends Exception> CharPredicate<X> alphanumeric() {
		return (CharPredicate<X>) _ALPHANUMERIC;
	}
	
	private static final CharPredicate<?> _ALPHANUMERIC = new CharPredicate<RuntimeException>() {
		@Override
		public boolean evaluate(final char c) {
			return Character.isLetterOrDigit(c) || '_' == c;
		}
	};
	
	/**
	 * Builds a predicate that evaluate to <code>true</code> for the given character.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param c The character.
	 * @return The built predicate.
	 */
	public static <X extends Exception> CharPredicate<X> equals(final char c) {
		return new CharPredicate<X>() {
			@Override
			public boolean evaluate(final char c_) {
				return c == c_;
			}
		};
	}
	
	/**
	 * Builds a predicate that evaluate to <code>true</code> for any character of the given string.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param chars The characters.
	 * @return The built predicate.
	 */
	public static <X extends Exception> CharPredicate<X> any(final String chars) {
		assert null != chars;
		
		// Build.
		return new CharPredicate<X>() {
			@Override
			public boolean evaluate(final char c) {
				return chars.indexOf(c) >= 0;
			}
		};
	}
	
	private CharPredicates() {
		// Prevents instantiation.
	}
}
