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
package com.trazere.parser.text;

import com.trazere.parser.Parser;
import com.trazere.parser.ParserException;
import com.trazere.parser.core.Combine3Parser;
import com.trazere.parser.core.Fold1Parser;
import com.trazere.util.text.CharFilter;
import com.trazere.util.text.CharFilters;
import com.trazere.util.type.Tuple2;

/**
 * DOCME
 */
public class TextParsers {
	public static Parser<Character, Character> character(final char c) {
		return character(c, "char \'" + c + "\'");
	}
	
	public static Parser<Character, Character> character(final char c, final String description) {
		return character(CharFilters.build(c), description);
	}
	
	public static Parser<Character, Character> character(final CharFilter filter, final String description) {
		return new FilterCharParser(filter, description);
	}
	
	public static Parser<Character, Character> anyCharacter() {
		return anyCharacter("a char");
	}
	
	public static Parser<Character, Character> anyCharacter(final String description) {
		return character(CharFilters.ALL, description);
	}
	
	private static final Parser<Character, Character> SPACE = space("a space");
	
	public static Parser<Character, Character> space() {
		return SPACE;
	}
	
	public static Parser<Character, Character> space(final String description) {
		return character(' ', description);
	}
	
	private static final Parser<Character, Character> DIGIT = digit("a digit");
	
	public static Parser<Character, Character> digit() {
		return DIGIT;
	}
	
	public static Parser<Character, Character> digit(final String description) {
		return character(CharFilters.DIGIT, description);
	}
	
	private static final Parser<Character, Character> LETTER = letter("a letter");
	
	public static Parser<Character, Character> letter() {
		return LETTER;
	}
	
	public static Parser<Character, Character> letter(final String description) {
		return character(CharFilters.LETTER, description);
	}
	
	private static final Parser<Character, Character> ALPHANUMERIC = alphanumeric("an alphanumeric");
	
	public static Parser<Character, Character> alphanumeric() {
		return ALPHANUMERIC;
	}
	
	public static Parser<Character, Character> alphanumeric(final String description) {
		return character(CharFilters.ALPHANUMERIC, description);
	}
	
	public static Parser<Character, String> string(final String string) {
		return string(string, "string \'" + string + "\'");
	}
	
	public static Parser<Character, String> string(final String string, final String description) {
		return new StringParser(string, description);
	}
	
	public static Parser<Character, String> string(final CharFilter filter, final boolean empty, final String description) {
		return new FilterStringParser(filter, empty, description);
	}
	
	public static Parser<Character, String> string(final Parser<Character, Character> characterParser, final boolean empty, final String description) {
		return new CharacterStringParser(characterParser, empty, description);
	}
	
	private static final Parser<Character, Integer> INTEGER = integer("an integer");
	
	public static Parser<Character, Integer> integer() {
		return INTEGER;
	}
	
	public static Parser<Character, Integer> integer(final String description) {
		return new Fold1Parser<Character, Character, Integer>(DIGIT, Integer.valueOf(0), description) {
			@Override
			protected Integer fold(final Integer previousValue, final Character subResult)
			throws ParserException {
				final int digit = Character.digit(subResult.charValue(), 10);
				return Integer.valueOf(previousValue.intValue() * 10 + digit);
			}
		};
	}
	
	private static final Parser<Character, Double> DECIMAL = decimal("a decimal");
	
	public static Parser<Character, Double> decimal() {
		return DECIMAL;
	}
	
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
	
	private static final Parser<Character, String> WORD = word("a word");
	
	public static Parser<Character, String> word() {
		return WORD;
	}
	
	public static Parser<Character, String> word(final String description) {
		return string(CharFilters.LETTER, false, description);
	}
	
	private TextParsers() {
		// Prevent instantiation.
	}
}
