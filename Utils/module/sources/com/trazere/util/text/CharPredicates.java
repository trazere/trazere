/*
 *  Copyright 2006-2008 Julien Dufour
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
	private static final CharPredicate<?> _ALL = new CharPredicate<RuntimeException>() {
		public boolean evaluate(final char c) {
			return true;
		}
	};
	
	/**
	 * Build a predicate which evaluates to <code>true</code> for all characters.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built predicate.
	 */
	@SuppressWarnings("unchecked")
	public static <X extends Exception> CharPredicate<X> all() {
		return (CharPredicate<X>) _ALL;
	}
	
	private static final CharPredicate<?> _NONE = new CharPredicate<RuntimeException>() {
		public boolean evaluate(final char c) {
			return false;
		}
	};
	
	/**
	 * Build a predicate which evaluates to <code>false</code> for all characters.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built predicate.
	 */
	@SuppressWarnings("unchecked")
	public static <X extends Exception> CharPredicate<X> none() {
		return (CharPredicate<X>) _NONE;
	}
	
	private static final CharPredicate<?> _DIGIT = new CharPredicate<RuntimeException>() {
		public boolean evaluate(final char c) {
			return Character.isDigit(c);
		}
	};
	
	/**
	 * Build a predicate corresponding to the inverse of the given predicate.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @return The built predicate.
	 */
	public static <X extends Exception> CharPredicate<X> not(final CharPredicate<X> predicate) {
		assert null != predicate;
		
		// Build.
		return new CharPredicate<X>() {
			public boolean evaluate(final char c)
			throws X {
				return !predicate.evaluate(c);
			}
		};
	}
	
	/**
	 * Build a predicate corresponding to the conjonction of the given predicates.
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
			public boolean evaluate(final char c)
			throws X {
				return predicate1.evaluate(c) && predicate2.evaluate(c);
			}
		};
	}
	
	/**
	 * Build a predicate corresponding to the disjunction of the given predicates.
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
			public boolean evaluate(final char c)
			throws X {
				return predicate1.evaluate(c) || predicate2.evaluate(c);
			}
		};
	}
	
	/**
	 * Build a predicate which evaluates to <code>true</code> for digit characters.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built predicate.
	 * @see Character#isDigit(char)
	 */
	@SuppressWarnings("unchecked")
	public static <X extends Exception> CharPredicate<X> digit() {
		return (CharPredicate<X>) _DIGIT;
	}
	
	private static final CharPredicate<?> _LETTER = new CharPredicate<RuntimeException>() {
		public boolean evaluate(final char c) {
			return Character.isLetter(c);
		}
	};
	
	/**
	 * Build a predicate which evaluates to <code>true</code> for letter characters.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built predicate.
	 * @see Character#isLetter(char)
	 */
	@SuppressWarnings("unchecked")
	public static <X extends Exception> CharPredicate<X> letter() {
		return (CharPredicate<X>) _LETTER;
	}
	
	private static final CharPredicate<?> _ALPHANUMERIC = new CharPredicate<RuntimeException>() {
		public boolean evaluate(final char c) {
			return Character.isLetterOrDigit(c) || '_' == c;
		}
	};
	
	/**
	 * Build a predicate which evaluates to <code>true</code> for alphanumeric and <code>'_'</code> characters.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built predicate.
	 * @see Character#isLetterOrDigit(char)
	 */
	@SuppressWarnings("unchecked")
	public static <X extends Exception> CharPredicate<X> alphanumeric() {
		return (CharPredicate<X>) _ALPHANUMERIC;
	}
	
	/**
	 * Build a predicate which evaluate to <code>true</code> for the given character.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param c The character.
	 * @return The built predicate.
	 */
	public static <X extends Exception> CharPredicate<X> equal(final char c) {
		return new CharPredicate<X>() {
			public boolean evaluate(final char c_) {
				return c == c_;
			}
		};
	}
	
	/**
	 * Build a predicate which evaluate to <code>true</code> for any character of the given string.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param chars The characters.
	 * @return The built predicate.
	 */
	public static <X extends Exception> CharPredicate<X> any(final String chars) {
		assert null != chars;
		
		// Build.
		return new CharPredicate<X>() {
			public boolean evaluate(final char c) {
				return chars.indexOf(c) >= 0;
			}
		};
	}
	
	private CharPredicates() {
		// Prevent instantiation.
	}
}
