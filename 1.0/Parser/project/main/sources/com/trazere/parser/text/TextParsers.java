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

import com.trazere.core.collection.CollectionFactories;
import com.trazere.core.collection.CollectionUtils;
import com.trazere.core.functional.Function;
import com.trazere.core.functional.Function2;
import com.trazere.core.lang.HashCode;
import com.trazere.core.lang.ObjectUtils;
import com.trazere.core.text.CharPredicate;
import com.trazere.core.text.CharPredicates;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import com.trazere.parser.Parser;
import com.trazere.parser.core.CoreParsers;
import com.trazere.parser.core.Sequence3Parser;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.EnumSet;

/**
 * DOCME
 */
public class TextParsers {
	public static Parser<Character, Character> character(final char c, final String description) {
		return character(CharPredicates.value(c), description);
	}
	
	public static Parser<Character, Character> character(final char c) {
		return character(c, "\'" + c + "\'");
	}
	
	public static Parser<Character, Character> character(final CharPredicate filter, final String description) {
		return new FilterCharParser(filter, description);
	}
	
	public static Parser<Character, Character> anyCharacter(final String description) {
		return character(CharPredicates.all(), description);
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
	
	public static Parser<Character, String> string(final CharPredicate filter, final boolean empty, final String description) {
		return new FilterStringParser(filter, empty, description);
	}
	
	public static Parser<Character, String> string(final Parser<Character, Character> characterParser, final boolean empty, final String description) {
		return new CharacterStringParser(characterParser, empty, description);
	}
	
	public static Parser<Character, String> escapedString(final CharPredicate filter, final CharPredicate escapeFilter, final boolean empty, final String description) {
		return new EscapedStringParser(filter, escapeFilter, empty, description);
	}
	
	public static Parser<Character, Character> space(final String description) {
		return character(' ', description);
	}
	
	public static Parser<Character, Character> space() {
		return SPACE;
	}
	
	private static final Parser<Character, Character> SPACE = space("a space");
	
	public static Parser<Character, String> whitespace(final String description) {
		return TextParsers.string(CharPredicates.whitespace(), false, description);
	}
	
	public static Parser<Character, String> whitespace() {
		return WHITESPACE;
	}
	
	private static final Parser<Character, String> WHITESPACE = whitespace("some whitespace");
	
	public static Parser<Character, Character> digit(final String description) {
		return character(CharPredicates.digit(), description);
	}
	
	public static Parser<Character, Character> digit() {
		return DIGIT;
	}
	
	private static final Parser<Character, Character> DIGIT = digit("a digit");
	
	public static Parser<Character, Character> letter(final String description) {
		return character(CharPredicates.letter(), description);
	}
	
	public static Parser<Character, Character> letter() {
		return LETTER;
	}
	
	private static final Parser<Character, Character> LETTER = letter("a letter");
	
	public static Parser<Character, Character> alphanumeric(final String description) {
		return character(CharPredicates.alphanumeric(), description);
	}
	
	public static Parser<Character, Character> alphanumeric() {
		return ALPHANUMERIC;
	}
	
	private static final Parser<Character, Character> ALPHANUMERIC = alphanumeric("an alphanumeric");
	
	public static Parser<Character, BigInteger> integer(final String description) {
		return integer(1, description);
	}
	
	public static Parser<Character, BigInteger> integer(final int length, final String description) {
		return CoreParsers.fold(TextParsers.digit(), FOLD_INTEGER, length, Integer.MAX_VALUE, BigInteger.ZERO, description);
	}
	
	private static final Function2<BigInteger, Character, BigInteger> FOLD_INTEGER = (final BigInteger previousValue, final Character subResult) -> {
		final int digit = Character.digit(subResult.charValue(), 10);
		return previousValue.multiply(BigInteger.TEN).add(BigInteger.valueOf(digit));
	};
	
	public static <N extends Number> Parser<Character, N> integer(final Function<? super BigInteger, ? extends Maybe<? extends N>> extractor, final String description) {
		return CoreParsers.extract(integer(), extractor, description);
	}
	
	public static <N extends Number> Parser<Character, N> integer(final int length, final Function<? super BigInteger, ? extends Maybe<? extends N>> extractor, final String description) {
		return CoreParsers.extract(integer(length, null), extractor, description);
	}
	
	public static Parser<Character, BigInteger> integer() {
		return INTEGER;
	}
	
	private static final Parser<Character, BigInteger> INTEGER = integer("an integer");
	
	public static Parser<Character, BigDecimal> decimal(final String description) {
		final Parser<Character, Tuple2<BigDecimal, BigDecimal>> decimalPartParser = CoreParsers.fold1(TextParsers.digit(), FOLD_DECIMAL_PART, new Tuple2<>(BigDecimal.ONE, BigDecimal.ZERO), null);
		final class DecimalParser
		extends Sequence3Parser<Character, BigInteger, Character, Tuple2<BigDecimal, BigDecimal>, BigDecimal> {
			public DecimalParser() {
				super(integer(), TextParsers.character('.'), decimalPartParser, description);
			}
			
			// Parser.
			
			@Override
			protected BigDecimal combine(final BigInteger integerPart, final Character comma, final Tuple2<BigDecimal, BigDecimal> decimalPart) {
				return new BigDecimal(integerPart).add(decimalPart.get2());
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
					return ObjectUtils.safeEquals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3);
				} else {
					return false;
				}
			}
		}
		return new DecimalParser();
	}
	
	private static final Function2<Tuple2<BigDecimal, BigDecimal>, Character, Tuple2<BigDecimal, BigDecimal>> FOLD_DECIMAL_PART = (final Tuple2<BigDecimal, BigDecimal> previousValue, final Character subResult) -> {
		final int digit = Character.digit(subResult.charValue(), 10);
		final BigDecimal factor = previousValue.get1().divide(BigDecimal.TEN);
		return new Tuple2<>(factor, previousValue.get2().add(factor.multiply(BigDecimal.valueOf(digit))));
	};
	
	public static Parser<Character, BigDecimal> decimal() {
		return DECIMAL;
	}
	
	private static final Parser<Character, BigDecimal> DECIMAL = decimal("a decimal");
	
	public static <N extends Number> Parser<Character, N> decimal(final Function<? super BigDecimal, ? extends Maybe<? extends N>> extractor, final String description) {
		return CoreParsers.extract(decimal(), extractor, description);
	}
	
	public static Parser<Character, String> word(final String description) {
		return string(CharPredicates.letter(), false, description);
	}
	
	public static Parser<Character, String> word() {
		return WORD;
	}
	
	private static final Parser<Character, String> WORD = word("a word");
	
	public static final <E extends Enum<E>> Parser<Character, E> enumeration(final Class<E> type, final Function<E, String> renderer, final String description) {
		assert null != type;
		assert null != renderer;
		
		return CoreParsers.choice(CollectionUtils.map(EnumSet.allOf(type), value -> CoreParsers.lift(string(renderer.evaluate(value)), value, description), CollectionFactories.arrayList()), description);
	}
	
	private TextParsers() {
		// Prevents instantiation.
	}
}
