/*
 *  Copyright 2006-2010 Julien Dufour
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
package com.trazere.parser.text;

import com.trazere.parser.Parser;
import com.trazere.parser.ParserException;
import com.trazere.parser.core.Combine3Parser;
import com.trazere.parser.core.Fold1Parser;
import com.trazere.util.text.CharPredicate;
import com.trazere.util.text.CharPredicates;
import com.trazere.util.type.Tuple2;

/**
 * DOCME
 */
public class TextParsers {
	public static Parser<Character, Character> character(final char c, final String description) {
		return character(CharPredicates.<ParserException>equal(c), description);
	}
	
	public static Parser<Character, Character> character(final char c) {
		return character(c, "char \'" + c + "\'");
	}
	
	public static Parser<Character, Character> character(final CharPredicate<? extends ParserException> filter, final String description) {
		return new FilterCharParser(filter, description);
	}
	
	public static Parser<Character, Character> anyCharacter(final String description) {
		return character(CharPredicates.<ParserException>all(), description);
	}
	
	public static Parser<Character, Character> anyCharacter() {
		return anyCharacter("a char");
	}
	
	public static Parser<Character, String> string(final String string, final String description) {
		return new StringParser(string, description);
	}
	
	public static Parser<Character, String> string(final String string) {
		return string(string, "string \'" + string + "\'");
	}
	
	public static Parser<Character, String> string(final CharPredicate<? extends ParserException> filter, final boolean empty, final String description) {
		return new FilterStringParser(filter, empty, description);
	}
	
	public static Parser<Character, String> string(final Parser<Character, Character> characterParser, final boolean empty, final String description) {
		return new CharacterStringParser(characterParser, empty, description);
	}
	
	public static Parser<Character, Character> space(final String description) {
		return character(' ', description);
	}
	
	public static Parser<Character, Character> space() {
		return _SPACE;
	}
	
	private static final Parser<Character, Character> _SPACE = space("a space");
	
	public static Parser<Character, String> whitespace(final String description) {
		return TextParsers.string(CharPredicates.<ParserException>whitespace(), false, description);
	}
	
	public static Parser<Character, String> whitespace() {
		return _WHITESPACE;
	}
	
	private static final Parser<Character, String> _WHITESPACE = whitespace("some whitespace");
	
	public static Parser<Character, Character> digit(final String description) {
		return character(CharPredicates.<ParserException>digit(), description);
	}
	
	public static Parser<Character, Character> digit() {
		return _DIGIT;
	}
	
	private static final Parser<Character, Character> _DIGIT = digit("a digit");
	
	public static Parser<Character, Character> letter(final String description) {
		return character(CharPredicates.<ParserException>letter(), description);
	}
	
	public static Parser<Character, Character> letter() {
		return _LETTER;
	}
	
	private static final Parser<Character, Character> _LETTER = letter("a letter");
	
	public static Parser<Character, Character> alphanumeric(final String description) {
		return character(CharPredicates.<ParserException>alphanumeric(), description);
	}
	
	public static Parser<Character, Character> alphanumeric() {
		return _ALPHANUMERIC;
	}
	
	private static final Parser<Character, Character> _ALPHANUMERIC = alphanumeric("an alphanumeric");
	
	public static Parser<Character, Integer> integer(final String description) {
		return new Fold1Parser<Character, Character, Integer>(_DIGIT, Integer.valueOf(0), description) {
			@Override
			protected Integer fold(final Integer previousValue, final Character subResult)
			throws ParserException {
				final int digit = Character.digit(subResult.charValue(), 10);
				return Integer.valueOf(previousValue.intValue() * 10 + digit);
			}
		};
	}
	
	public static Parser<Character, Integer> integer() {
		return _INTEGER;
	}
	
	private static final Parser<Character, Integer> _INTEGER = integer("an integer");
	
	public static Parser<Character, Double> decimal(final String description) {
		final Parser<Character, Double> integerPartParser = new Fold1Parser<Character, Character, Double>(TextParsers.digit(), Double.valueOf(0), null) {
			@Override
			protected Double fold(final Double previousValue, final Character subResult) {
				final int digit = Character.digit(subResult.charValue(), 10);
				return Double.valueOf(previousValue.intValue() * 10 + digit);
			}
		};
		final Parser<Character, Tuple2<Double, Double>> decimalPartParser = new Fold1Parser<Character, Character, Tuple2<Double, Double>>(TextParsers.digit(), Tuple2.build(0.1, 0.0), null) {
			@Override
			protected Tuple2<Double, Double> fold(final Tuple2<Double, Double> previousValue, final Character subResult) {
				final int digit = Character.digit(subResult.charValue(), 10);
				final double factor = previousValue.getFirst().doubleValue();
				return Tuple2.build(factor / 10, previousValue.getSecond().doubleValue() + (factor * digit));
			}
		};
		return new Combine3Parser<Character, Double, Character, Tuple2<Double, Double>, Double>(integerPartParser, TextParsers.character('.'), decimalPartParser, description) {
			@Override
			protected Double combine(final Double subResult1, final Character subResult2, final Tuple2<Double, Double> subResult3) {
				return subResult1.doubleValue() + subResult3.getSecond().doubleValue();
			}
		};
	}
	
	public static Parser<Character, Double> decimal() {
		return _DECIMAL;
	}
	
	private static final Parser<Character, Double> _DECIMAL = decimal("a decimal");
	
	public static Parser<Character, String> word(final String description) {
		return string(CharPredicates.<ParserException>letter(), false, description);
	}
	
	public static Parser<Character, String> word() {
		return _WORD;
	}
	
	private static final Parser<Character, String> _WORD = word("a word");
	
	private TextParsers() {
		// Prevent instantiation.
	}
}
