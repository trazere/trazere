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
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@link CharPredicates} class provides various factories of character predicates.
 * 
 * @see CharPredicate
 */
public class CharPredicates {
	/**
	 * Builds a character predicate that evaluates to <code>true</code> for all characters.
	 * 
	 * @return The built predicate.
	 */
	public static CharPredicate all() {
		return ALL;
	}
	
	private static final CharPredicate ALL = c -> true;
	
	/**
	 * Builds a character predicate that evaluates to <code>false</code> for all characters.
	 * 
	 * @return The built predicate.
	 */
	public static CharPredicate none() {
		return NONE;
	}
	
	private static final CharPredicate NONE = c -> false;
	
	/**
	 * Builds a character predicate that evaluates to the given result for all characters.
	 * 
	 * @param result Result of the predicate.
	 * @return The built predicate.
	 */
	public static CharPredicate constant(final boolean result) {
		return result ? all() : none();
	}
	
	/**
	 * Builds a character predicate corresponding to the logical negation of the given character predicate.
	 * 
	 * @param predicate Predicate to inverse.
	 * @return The built predicate.
	 */
	public static CharPredicate not(final CharPredicate predicate) {
		assert null != predicate;
		
		return c -> !predicate.evaluate(c);
	}
	
	/**
	 * Builds a character predicate corresponding to the logical conjunction of the given character predicates.
	 * 
	 * @param predicate1 First predicate to combine.
	 * @param predicate2 Second predicate to combine.
	 * @return The built predicate.
	 */
	public static CharPredicate and(final CharPredicate predicate1, final CharPredicate predicate2) {
		assert null != predicate1;
		assert null != predicate2;
		
		return c -> predicate1.evaluate(c) && predicate2.evaluate(c);
	}
	
	/**
	 * Builds a character predicate corresponding to the logical conjunction of the given character predicates.
	 * 
	 * @param predicates Predicates to combine.
	 * @return The built predicate.
	 */
	public static CharPredicate and(final Collection<? extends CharPredicate> predicates) {
		if (predicates.isEmpty()) {
			return all();
		} else if (1 == predicates.size()) {
			return predicates.iterator().next();
		} else {
			return (final char c) -> {
				for (final CharPredicate predicate : predicates) {
					if (!predicate.evaluate(c)) {
						return false;
					}
				}
				return true;
			};
		}
	}
	
	/**
	 * Builds a character predicate corresponding to the logical disjunction of the given character predicates.
	 * 
	 * @param predicate1 First predicate to combine.
	 * @param predicate2 Second predicate to combine.
	 * @return The built predicate.
	 */
	public static CharPredicate or(final CharPredicate predicate1, final CharPredicate predicate2) {
		assert null != predicate1;
		assert null != predicate2;
		
		return c -> predicate1.evaluate(c) || predicate2.evaluate(c);
	}
	
	/**
	 * Builds a character predicate corresponding to the logical disjunction of the given character predicates.
	 * 
	 * @param predicates Predicates to combine.
	 * @return The built predicate.
	 */
	public static CharPredicate or(final Collection<? extends CharPredicate> predicates) {
		if (predicates.isEmpty()) {
			return none();
		} else if (1 == predicates.size()) {
			return predicates.iterator().next();
		} else {
			return (final char c) -> {
				for (final CharPredicate predicate : predicates) {
					if (predicate.evaluate(c)) {
						return true;
					}
				}
				return false;
			};
		}
	}
	
	// TODO: fromChar ?
	/**
	 * Builds a character predicate that evaluates to <code>true</code> for the given character.
	 * 
	 * @param c Character to accept.
	 * @return The built predicate.
	 */
	public static CharPredicate value(final char c) {
		return cArg -> cArg == c;
	}
	
	// TODO: fromChars ?
	/**
	 * Builds a character predicate that evaluates to <code>true</code> for the given characters.
	 * 
	 * @param cs Characters to accept.
	 * @return The built predicate.
	 */
	public static CharPredicate values(final char... cs) {
		final Set<Character> cs_ = new HashSet<>();
		for (final char c : cs) {
			cs_.add(c);
		}
		return values(cs_);
	}
	
	// TODO: fromChars ?
	/**
	 * Builds a character predicate that evaluates to <code>true</code> for the given characters.
	 * 
	 * @param cs Characters to accept.
	 * @return The built predicate.
	 */
	public static CharPredicate values(final Collection<Character> cs) {
		assert null != cs;
		
		return c -> cs.contains(c);
	}
	
	// TODO: fromSeq ?
	/**
	 * Builds a character predicate that evaluates to <code>true</code> for the characters of the given character sequence.
	 * 
	 * @param s Sequence containing the characters to accept.
	 * @return The built predicate.
	 */
	public static CharPredicate values(final CharSequence s) {
		final Set<Character> cs = new HashSet<>();
		for (final int i : new FiniteIntSequence(0, s.length())) {
			cs.add(s.charAt(i));
		}
		return values(cs);
	}
	
	/**
	 * Builds a character predicate that evaluates to <code>true</code> for the characters in the given range.
	 * 
	 * @param start Starting character of the range (included).
	 * @param end Ending character of the range (included).
	 * @return The built predicate.
	 */
	public static CharPredicate range(final char start, final char end) {
		return c -> c >= start && c <= end;
	}
	
	/**
	 * Builds a character predicate that evaluates to <code>true</code> for whitespace characters.
	 * 
	 * @return The built predicate.
	 * @see Character#isWhitespace(char)
	 */
	public static CharPredicate whitespace() {
		return WHITESPACE;
	}
	
	private static final CharPredicate WHITESPACE = Character::isWhitespace;
	
	/**
	 * Builds a character predicate that evaluates to <code>true</code> for digit characters.
	 * 
	 * @return The built predicate.
	 * @see Character#isDigit(char)
	 */
	public static CharPredicate digit() {
		return DIGIT;
	}
	
	private static final CharPredicate DIGIT = Character::isDigit;
	
	/**
	 * Builds a character predicate that evaluates to <code>true</code> for letter characters.
	 * 
	 * @return The built predicate.
	 * @see Character#isLetter(char)
	 */
	public static CharPredicate letter() {
		return LETTER;
	}
	
	private static final CharPredicate LETTER = Character::isLetter;
	
	/**
	 * Builds a character predicate that evaluates to <code>true</code> for letter, digit and <code>'_'</code> characters.
	 * 
	 * @return The built predicate.
	 * @see Character#isLetterOrDigit(char)
	 */
	public static CharPredicate alphanumeric() {
		return ALPHANUMERIC;
	}
	
	private static final CharPredicate ALPHANUMERIC = c -> Character.isLetterOrDigit(c) || '_' == c;
	
	private CharPredicates() {
		// Prevents instantiation.
	}
}
