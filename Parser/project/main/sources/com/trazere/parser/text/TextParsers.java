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
package com.trazere.parser.text;

import com.trazere.core.lang.HashCode;
import com.trazere.core.lang.LangUtils;
import com.trazere.parser.Parser;
import com.trazere.parser.ParserException;
import com.trazere.parser.core.CoreParsers;
import com.trazere.parser.core.Sequence3Parser;
import com.trazere.util.function.Function1;
import com.trazere.util.function.Function2;
import com.trazere.util.function.FunctionUtils;
import com.trazere.util.text.CharPredicate;
import com.trazere.util.text.CharPredicates;
import com.trazere.util.type.Maybe;
import com.trazere.util.type.Tuple2;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.EnumSet;

/**
 * DOCME
 */
public class TextParsers {
	public static Parser<Character, Character> character(final char c, final String description) {
		return character(CharPredicates.<ParserException>value(c), description);
	}
	
	public static Parser<Character, Character> character(final char c) {
		return character(c, "\'" + c + "\'");
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
		return string(string, "\'" + string + "\'");
	}
	
	public static Parser<Character, String> string(final CharPredicate<? extends ParserException> filter, final boolean empty, final String description) {
		return new FilterStringParser(filter, empty, description);
	}
	
	public static Parser<Character, String> string(final Parser<Character, Character> characterParser, final boolean empty, final String description) {
		return new CharacterStringParser(characterParser, empty, description);
	}
	
	public static Parser<Character, String> escapedString(final CharPredicate<? extends ParserException> filter, final CharPredicate<? extends ParserException> escapeFilter, final boolean empty, final String description) {
		return new EscapedStringParser(filter, escapeFilter, empty, description);
	}
	
	public static Parser<Character, Character> space(final String description) {
		return character(' ', description);
	}
	
	public static Parser<Character, Character> space() {
		return _SPACE;
	}
	
	private static final Parser<Character, Character> _SPACE = space("a space");
	
	public static Parser<Character, String> whitespace(final String description) {
		return TextParsers.string(CharPredicates.<ParserException>isWhitespace(), false, description);
	}
	
	public static Parser<Character, String> whitespace() {
		return _WHITESPACE;
	}
	
	private static final Parser<Character, String> _WHITESPACE = whitespace("some whitespace");
	
	public static Parser<Character, Character> digit(final String description) {
		return character(CharPredicates.<ParserException>isDigit(), description);
	}
	
	public static Parser<Character, Character> digit() {
		return _DIGIT;
	}
	
	private static final Parser<Character, Character> _DIGIT = digit("a digit");
	
	public static Parser<Character, Character> letter(final String description) {
		return character(CharPredicates.<ParserException>isLetter(), description);
	}
	
	public static Parser<Character, Character> letter() {
		return _LETTER;
	}
	
	private static final Parser<Character, Character> _LETTER = letter("a letter");
	
	public static Parser<Character, Character> alphanumeric(final String description) {
		return character(CharPredicates.<ParserException>isAlphanumeric(), description);
	}
	
	public static Parser<Character, Character> alphanumeric() {
		return _ALPHANUMERIC;
	}
	
	private static final Parser<Character, Character> _ALPHANUMERIC = alphanumeric("an alphanumeric");
	
	public static Parser<Character, BigInteger> integer(final String description) {
		return integer(1, description);
	}
	
	public static Parser<Character, BigInteger> integer(final int length, final String description) {
		return CoreParsers.fold(TextParsers.digit(), _FOLD_INTEGER, length, Integer.MAX_VALUE, BigInteger.ZERO, description);
	}
	
	private static final Function2<BigInteger, Character, BigInteger, ParserException> _FOLD_INTEGER = new Function2<BigInteger, Character, BigInteger, ParserException>() {
		@Override
		public BigInteger evaluate(final BigInteger previousValue, final Character subResult) {
			final int digit = Character.digit(subResult.charValue(), 10);
			return previousValue.multiply(BigInteger.TEN).add(BigInteger.valueOf(digit));
		}
	};
	
	public static <N extends Number> Parser<Character, N> integer(final Function1<? super BigInteger, ? extends Maybe<? extends N>, ? extends ParserException> extractor, final String description) {
		return CoreParsers.extract(integer(), extractor, description);
	}
	
	public static <N extends Number> Parser<Character, N> integer(final int length, final Function1<? super BigInteger, ? extends Maybe<? extends N>, ? extends ParserException> extractor, final String description) {
		return CoreParsers.extract(integer(length, null), extractor, description);
	}
	
	public static Parser<Character, BigInteger> integer() {
		return _INTEGER;
	}
	
	private static final Parser<Character, BigInteger> _INTEGER = integer("an integer");
	
	public static Parser<Character, BigDecimal> decimal(final String description) {
		final Parser<Character, Tuple2<BigDecimal, BigDecimal>> decimalPartParser = CoreParsers.fold1(TextParsers.digit(), _FOLD_DECIMAL_PART, Tuple2.build(BigDecimal.ONE, BigDecimal.ZERO), null);
		final class DecimalParser
		extends Sequence3Parser<Character, BigInteger, Character, Tuple2<BigDecimal, BigDecimal>, BigDecimal> {
			public DecimalParser() {
				super(integer(), TextParsers.character('.'), decimalPartParser, description);
			}
			
			// Parser.
			
			@Override
			protected BigDecimal combine(final BigInteger integerPart, final Character comma, final Tuple2<BigDecimal, BigDecimal> decimalPart) {
				return new BigDecimal(integerPart).add(decimalPart.getSecond());
			}
			
			// Object.
			
			@Override
			public int hashCode() {
				final HashCode result = new HashCode(this);
				result.append(_description);
				result.append(_subParser1);
				result.append(_subParser2);
				result.append(_subParser3);
				return result.get();
			}
			
			@Override
			public boolean equals(final Object object) {
				if (this == object) {
					return true;
				} else if (null != object && getClass().equals(object.getClass())) {
					final DecimalParser parser = (DecimalParser) object;
					return LangUtils.safeEquals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3);
				} else {
					return false;
				}
			}
		}
		return new DecimalParser();
	}
	
	private static final Function2<Tuple2<BigDecimal, BigDecimal>, Character, Tuple2<BigDecimal, BigDecimal>, ParserException> _FOLD_DECIMAL_PART = new Function2<Tuple2<BigDecimal, BigDecimal>, Character, Tuple2<BigDecimal, BigDecimal>, ParserException>() {
		@Override
		public Tuple2<BigDecimal, BigDecimal> evaluate(final Tuple2<BigDecimal, BigDecimal> previousValue, final Character subResult) {
			final int digit = Character.digit(subResult.charValue(), 10);
			final BigDecimal factor = previousValue.getFirst().divide(BigDecimal.TEN);
			return Tuple2.build(factor, previousValue.getSecond().add(factor.multiply(BigDecimal.valueOf(digit))));
		}
	};
	
	public static Parser<Character, BigDecimal> decimal() {
		return _DECIMAL;
	}
	
	private static final Parser<Character, BigDecimal> _DECIMAL = decimal("a decimal");
	
	public static <N extends Number> Parser<Character, N> decimal(final Function1<? super BigDecimal, ? extends Maybe<? extends N>, ? extends ParserException> extractor, final String description) {
		return CoreParsers.extract(decimal(), extractor, description);
	}
	
	public static Parser<Character, String> word(final String description) {
		return string(CharPredicates.<ParserException>isLetter(), false, description);
	}
	
	public static Parser<Character, String> word() {
		return _WORD;
	}
	
	private static final Parser<Character, String> _WORD = word("a word");
	
	public static final <E extends Enum<E>, X extends Exception> Parser<Character, E> enumeration(final Class<E> type, final Function1<E, String, X> renderer, final String description)
	throws X {
		assert null != type;
		assert null != renderer;
		
		return CoreParsers.choice(FunctionUtils.map(new Function1<E, Parser<Character, E>, X>() {
			@Override
			public Parser<Character, E> evaluate(final E value)
			throws X {
				return CoreParsers.lift(string(renderer.evaluate(value)), value, description);
			}
		}, EnumSet.allOf(type), new ArrayList<Parser<Character, E>>()), description);
	}
	
	private TextParsers() {
		// Prevents instantiation.
	}
}
