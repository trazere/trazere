/*
 *  Copyright 2006-2012 Julien Dufour
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
import com.trazere.parser.core.CoreParsers;
import com.trazere.parser.core.Sequence3Parser;
import com.trazere.util.function.Function1;
import com.trazere.util.function.Function2;
import com.trazere.util.function.FunctionUtils;
import com.trazere.util.lang.HashCode;
import com.trazere.util.lang.LangUtils;
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
		return character(CharPredicates.<ParserException>equal(c), description);
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
	
	public static Parser<Character, BigInteger> integer(final String description) {
		return CoreParsers.fold1(_DIGIT, _FOLD_INTEGER, BigInteger.ZERO, description);
	}
	
	private static final Function2<BigInteger, Character, BigInteger, ParserException> _FOLD_INTEGER = new Function2<BigInteger, Character, BigInteger, ParserException>() {
		@Override
		public BigInteger evaluate(final BigInteger previousValue, final Character subResult) {
			final int digit = Character.digit(subResult.charValue(), 10);
			return previousValue.multiply(BigInteger.TEN).add(BigInteger.valueOf(digit));
		}
	};
	
	public static Parser<Character, BigInteger> integer() {
		return _INTEGER;
	}
	
	private static final Parser<Character, BigInteger> _INTEGER = integer("an integer");
	
	public static <N extends Number> Parser<Character, N> integer(final IntegerExtractor<N> extractor, final String description) {
		return CoreParsers.map(integer(), new Function1<BigInteger, N, ParserException>() {
			@Override
			public N evaluate(final BigInteger value)
			throws ParserException {
				final Maybe<N> result = extractor.extract(value);
				if (result.isSome()) {
					return result.asSome().getValue();
				} else {
					throw new ParserException("Failed extracting integer \"" + value + "\"");
				}
			}
		}, description);
	}
	
	// TODO: generalize and move to lang
	public static abstract class IntegerExtractor<N extends Number>
	implements Function1<BigInteger, Maybe<N>, ParserException> {
		public abstract Maybe<N> extract(final BigInteger value);
		
		@Override
		public Maybe<N> evaluate(final BigInteger value) {
			return extract(value);
		}
	}
	
	public static final IntegerExtractor<Byte> BYTE_INTEGER_EXTRACTOR = new IntegerExtractor<Byte>() {
		@Override
		public Maybe<Byte> extract(final BigInteger value) {
			assert null != value;
			
			if (value.bitLength() < Byte.SIZE) {
				return Maybe.some(value.byteValue());
			} else {
				return Maybe.none();
			}
		}
	};
	
	public static final IntegerExtractor<Short> SHORT_INTEGER_EXTRACTOR = new IntegerExtractor<Short>() {
		@Override
		public Maybe<Short> extract(final BigInteger value) {
			assert null != value;
			
			if (value.bitLength() < Short.SIZE) {
				return Maybe.some(value.shortValue());
			} else {
				return Maybe.none();
			}
		}
	};
	
	public static final IntegerExtractor<Integer> INTEGER_INTEGER_EXTRACTOR = new IntegerExtractor<Integer>() {
		@Override
		public Maybe<Integer> extract(final BigInteger value) {
			assert null != value;
			
			if (value.bitLength() < Integer.SIZE) {
				return Maybe.some(value.intValue());
			} else {
				return Maybe.none();
			}
		}
	};
	
	public static final IntegerExtractor<Long> LONG_INTEGER_EXTRACTOR = new IntegerExtractor<Long>() {
		@Override
		public Maybe<Long> extract(final BigInteger value) {
			assert null != value;
			
			if (value.bitLength() < Long.SIZE) {
				return Maybe.some(value.longValue());
			} else {
				return Maybe.none();
			}
		}
	};
	
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
					return LangUtils.equals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3);
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
	
	public static <N extends Number> Parser<Character, N> decimal(final DecimalExtractor<N> extractor, final String description) {
		return CoreParsers.map(decimal(), extractor, description);
	}
	
	private static abstract class DecimalExtractor<N extends Number>
	implements Function1<BigDecimal, N, ParserException> {
		public abstract N extract(final BigDecimal value)
		throws ParserException;
		
		@Override
		public N evaluate(final BigDecimal value)
		throws ParserException {
			return extract(value);
		}
	}
	
	public static final DecimalExtractor<Byte> BYTE_DECIMAL_EXTRACTOR = new DecimalExtractor<Byte>() {
		@Override
		public Byte extract(final BigDecimal value)
		throws ParserException {
			assert null != value;
			
			try {
				return value.byteValueExact();
			} catch (final ArithmeticException exception) {
				throw new ParserException(exception);
			}
		}
	};
	
	public static final DecimalExtractor<Short> SHORT_DECIMAL_EXTRACTOR = new DecimalExtractor<Short>() {
		@Override
		public Short extract(final BigDecimal value)
		throws ParserException {
			assert null != value;
			
			try {
				return value.shortValueExact();
			} catch (final ArithmeticException exception) {
				throw new ParserException(exception);
			}
		}
	};
	
	public static final DecimalExtractor<Integer> INTEGER_DECIMAL_EXTRACTOR = new DecimalExtractor<Integer>() {
		@Override
		public Integer extract(final BigDecimal value)
		throws ParserException {
			assert null != value;
			
			try {
				return value.intValueExact();
			} catch (final ArithmeticException exception) {
				throw new ParserException(exception);
			}
		}
	};
	
	public static final DecimalExtractor<Long> LONG_DECIMAL_EXTRACTOR = new DecimalExtractor<Long>() {
		@Override
		public Long extract(final BigDecimal value)
		throws ParserException {
			assert null != value;
			
			try {
				return value.longValueExact();
			} catch (final ArithmeticException exception) {
				throw new ParserException(exception);
			}
		}
	};
	
	public static final DecimalExtractor<Float> FLOAT_DECIMAL_EXTRACTOR = new DecimalExtractor<Float>() {
		@Override
		public Float extract(final BigDecimal value)
		throws ParserException {
			assert null != value;
			
			try {
				return value.floatValue();
			} catch (final NumberFormatException exception) {
				throw new ParserException(exception);
			}
		}
	};
	
	public static final DecimalExtractor<Double> DOUBLE_DECIMAL_EXTRACTOR = new DecimalExtractor<Double>() {
		@Override
		public Double extract(final BigDecimal value)
		throws ParserException {
			assert null != value;
			
			try {
				return value.doubleValue();
			} catch (final NumberFormatException exception) {
				throw new ParserException(exception);
			}
		}
	};
	
	public static Parser<Character, String> word(final String description) {
		return string(CharPredicates.<ParserException>letter(), false, description);
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
