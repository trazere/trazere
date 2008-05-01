/*
 *  Copyright 2008 Julien Dufour
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
import com.trazere.parser.core.Fold1Parser;
import com.trazere.util.text.CharFilter;
import com.trazere.util.text.CharFilters;

/**
 * DOCME
 */
public class TextParsers {
	public static Parser<Character, Character> character(final char c) {
		return someCharacter(CharFilters.build(c), "char \'" + c + "\'");
	}
	
	public static Parser<Character, Character> character(final char c, final String description) {
		return new SomeCharParser(CharFilters.build(c), description);
	}
	
	public static Parser<Character, Character> anyCharacter() {
		return anyCharacter("a char");
	}
	
	public static Parser<Character, Character> anyCharacter(final String description) {
		return someCharacter(CharFilters.ALL, description);
	}
	
	public static Parser<Character, Character> someCharacter(final CharFilter filter, final String description) {
		return new SomeCharParser(filter, description);
	}
	
	private static final Parser<Character, Character> SPACE = space("a space");
	
	public static Parser<Character, Character> space() {
		return SPACE;
	}
	
	public static Parser<Character, Character> space(final String description) {
		return new SomeCharParser(CharFilters.build(' '), description);
	}
	
	private static final Parser<Character, Character> DIGIT = digit("a digit");
	
	public static Parser<Character, Character> digit() {
		return DIGIT;
	}
	
	public static Parser<Character, Character> digit(final String description) {
		return new SomeCharParser(CharFilters.DIGIT, description);
	}
	
	private static final Parser<Character, Character> LETTER = letter("a letter");
	
	public static Parser<Character, Character> letter() {
		return LETTER;
	}
	
	public static Parser<Character, Character> letter(final String description) {
		return new SomeCharParser(CharFilters.LETTER, description);
	}
	
	private static final Parser<Character, Character> ALPHANUMERIC = alphanumeric("an alphanumeric");
	
	public static Parser<Character, Character> alphanumeric() {
		return ALPHANUMERIC;
	}
	
	public static Parser<Character, Character> alphanumeric(final String description) {
		return new SomeCharParser(CharFilters.ALPHANUMERIC, description);
	}
	
	public static Parser<Character, String> string(final String string) {
		return string(string, "string \'" + string + "\'");
	}
	
	public static Parser<Character, String> string(final String string, final String description) {
		return new StringParser(string, description);
	}
	
	public static Parser<Character, String> someString(final CharFilter filter, final String description) {
		return new SomeStringParser(filter, description);
	}
	
	private static final Parser<Character, Integer> DECIMAL = decimal("a decimal");
	
	public static Parser<Character, Integer> decimal() {
		return DECIMAL;
	}
	
	public static Parser<Character, Integer> decimal(final String description) {
		return new Fold1Parser<Character, Character, Integer>(DIGIT, Integer.valueOf(0), description) {
			@Override
			protected Integer fold(final Integer previousValue, final Character subResult)
			throws ParserException {
				final int digit = Character.digit(subResult.charValue(), 10);
				return Integer.valueOf(previousValue.intValue() * 10 + digit);
			}
		};
	}
	
	private static final Parser<Character, String> WORD = word("a word");
	
	public static Parser<Character, String> word() {
		return WORD;
	}
	
	public static Parser<Character, String> word(final String description) {
		return new SomeStringParser(CharFilters.LETTER, description);
	}
	
	private TextParsers() {
		// Prevent instantiation.
	}
}
