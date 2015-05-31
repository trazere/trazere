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
package com.trazere.util.text;

import com.trazere.util.collection.CollectionUtils;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@link CharPredicates} class provides various common character predicate functions.
 * 
 * @see CharPredicate
 * @deprecated Use core.
 */
@Deprecated
public class CharPredicates {
	/**
	 * Builds a predicate that evaluates to the given result for all characters.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param result Result to return.
	 * @return The built predicate.
	 * @deprecated Use {@link com.trazere.core.text.CharPredicates#constant(boolean)}.
	 */
	@Deprecated
	public static <X extends Exception> CharPredicate<X> constant(final boolean result) {
		return new CharPredicate<X>() {
			@Override
			public boolean evaluate(final char c) {
				return result;
			}
		};
	}
	
	/**
	 * Builds a predicate that evaluates to <code>true</code> for all characters.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built predicate.
	 * @deprecated Use {@link com.trazere.core.text.CharPredicates#all()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <X extends Exception> CharPredicate<X> all() {
		return (CharPredicate<X>) ALL;
	}
	
	private static final CharPredicate<?> ALL = CharPredicates.<RuntimeException>constant(true);
	
	/**
	 * Builds a predicate that evaluates to <code>false</code> for all characters.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built predicate.
	 * @deprecated Use {@link com.trazere.core.text.CharPredicates#none()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <X extends Exception> CharPredicate<X> none() {
		return (CharPredicate<X>) NONE;
	}
	
	private static final CharPredicate<?> NONE = CharPredicates.<RuntimeException>constant(false);
	
	/**
	 * Builds a predicate corresponding to the inverse of the given predicate.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param predicate Predicate to inverse.
	 * @return The built predicate.
	 * @deprecated Use {@link com.trazere.core.text.CharPredicates#not(com.trazere.core.text.CharPredicate)}.
	 */
	@Deprecated
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
	 * @param predicate1 First predicate.
	 * @param predicate2 Second predicate.
	 * @return The built predicate.
	 * @deprecated Use {@link com.trazere.core.text.CharPredicates#and(com.trazere.core.text.CharPredicate, com.trazere.core.text.CharPredicate)}.
	 */
	@Deprecated
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
	 * Builds a predicate corresponding to the conjonction of the given predicates.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param predicates Predicates to combine.
	 * @return The built predicate.
	 * @deprecated Use {@link com.trazere.core.text.CharPredicates#and(Collection)}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <X extends Exception> CharPredicate<X> and(final Collection<? extends CharPredicate<? extends X>> predicates) {
		assert null != predicates;
		
		if (predicates.size() < 2) {
			return (CharPredicate<X>) CollectionUtils.any(predicates).get(CharPredicates.<X>all());
		} else {
			return new CharPredicate<X>() {
				@Override
				public boolean evaluate(final char c)
				throws X {
					for (final CharPredicate<? extends X> predicate : predicates) {
						if (!predicate.evaluate(c)) {
							return false;
						}
					}
					return true;
				}
			};
		}
	}
	
	/**
	 * Builds a predicate corresponding to the disjunction of the given predicates.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param predicate1 First predicate.
	 * @param predicate2 Second predicate.
	 * @return The built predicate.
	 * @deprecated Use {@link com.trazere.core.text.CharPredicates#or(com.trazere.core.text.CharPredicate, com.trazere.core.text.CharPredicate)}.
	 */
	@Deprecated
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
	 * Builds a predicate corresponding to the disjunction of the given predicates.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param predicates Predicates to combine.
	 * @return The built predicate.
	 * @deprecated Use {@link com.trazere.core.text.CharPredicates#or(Collection)}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <X extends Exception> CharPredicate<X> or(final Collection<? extends CharPredicate<? extends X>> predicates) {
		assert null != predicates;
		
		if (predicates.size() < 2) {
			return (CharPredicate<X>) CollectionUtils.any(predicates).get(CharPredicates.<X>none());
		} else {
			return new CharPredicate<X>() {
				@Override
				public boolean evaluate(final char c)
				throws X {
					for (final CharPredicate<? extends X> predicate : predicates) {
						if (predicate.evaluate(c)) {
							return true;
						}
					}
					return false;
				}
			};
		}
	}
	
	/**
	 * Builds a predicate that evaluate to <code>true</code> for the given character.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param value Character to accept.
	 * @return The built predicate.
	 * @deprecated Use {@link com.trazere.core.text.CharPredicates#value(char)}.
	 */
	@Deprecated
	public static <X extends Exception> CharPredicate<X> value(final char value) {
		return new CharPredicate<X>() {
			@Override
			public boolean evaluate(final char c_) {
				return value == c_;
			}
		};
	}
	
	/**
	 * Builds a predicate that evaluates to <code>true</code> for any given characters.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param values Characters to accept.
	 * @return The built predicate.
	 * @deprecated Use {@link com.trazere.core.text.CharPredicates#values(char...)}.
	 */
	@Deprecated
	public static <X extends Exception> CharPredicate<X> values(final char... values) {
		assert null != values;
		
		final Set<Character> values_ = new HashSet<Character>();
		for (final char value : values) {
			values_.add(value);
		}
		return values(values_);
	}
	
	/**
	 * Builds a predicate that evaluates to <code>true</code> for any given characters.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param values Characters to accept.
	 * @return The built predicate.
	 * @deprecated Use {@link com.trazere.core.text.CharPredicates#values(char...)}.
	 */
	@Deprecated
	public static <X extends Exception> CharPredicate<X> values(final Character... values) {
		assert null != values;
		
		return values(new HashSet<Character>(Arrays.asList(values)));
	}
	
	/**
	 * Builds a predicate that evaluates to <code>true</code> for any given values.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param values Characters to accept.
	 * @return The built predicate.
	 * @deprecated Use {@link com.trazere.core.text.CharPredicates#values(Collection)}.
	 */
	@Deprecated
	public static <X extends Exception> CharPredicate<X> values(final Collection<Character> values) {
		assert null != values;
		
		return new CharPredicate<X>() {
			@Override
			public boolean evaluate(final char c) {
				return values.contains(c);
			}
		};
	}
	
	/**
	 * Builds a predicate that evaluate to <code>true</code> for any character of the given string.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param chars Characters to accept.
	 * @return The built predicate.
	 * @deprecated Use {@link com.trazere.core.text.CharPredicates#values(CharSequence)}.
	 */
	@Deprecated
	public static <X extends Exception> CharPredicate<X> values(final String chars) {
		assert null != chars;
		
		return new CharPredicate<X>() {
			@Override
			public boolean evaluate(final char c) {
				return chars.indexOf(c) >= 0;
			}
		};
	}
	
	/**
	 * Builds a predicate that evaluates to <code>true</code> for whitespace characters.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built predicate.
	 * @see Character#isWhitespace(char)
	 * @deprecated Use {@link com.trazere.core.text.CharPredicates#whitespace()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <X extends Exception> CharPredicate<X> isWhitespace() {
		return (CharPredicate<X>) WHITESPACE;
	}
	
	private static final CharPredicate<?> WHITESPACE = new CharPredicate<RuntimeException>() {
		@Override
		public boolean evaluate(final char c) {
			return Character.isWhitespace(c);
		}
	};
	
	/**
	 * Builds a predicate that evaluates to <code>true</code> for digit characters.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built predicate.
	 * @see Character#isDigit(char)
	 * @deprecated Use {@link com.trazere.core.text.CharPredicates#digit()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <X extends Exception> CharPredicate<X> isDigit() {
		return (CharPredicate<X>) DIGIT;
	}
	
	private static final CharPredicate<?> DIGIT = new CharPredicate<RuntimeException>() {
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
	 * @deprecated Use {@link com.trazere.core.text.CharPredicates#letter()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <X extends Exception> CharPredicate<X> isLetter() {
		return (CharPredicate<X>) LETTER;
	}
	
	private static final CharPredicate<?> LETTER = new CharPredicate<RuntimeException>() {
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
	 * @deprecated Use {@link com.trazere.core.text.CharPredicates#alphanumeric()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <X extends Exception> CharPredicate<X> isAlphanumeric() {
		return (CharPredicate<X>) ALPHANUMERIC;
	}
	
	private static final CharPredicate<?> ALPHANUMERIC = new CharPredicate<RuntimeException>() {
		@Override
		public boolean evaluate(final char c) {
			return Character.isLetterOrDigit(c) || '_' == c;
		}
	};
	
	private CharPredicates() {
		// Prevents instantiation.
	}
}
