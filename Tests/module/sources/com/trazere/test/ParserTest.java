/*
 *  Copyright 2006 Julien Dufour
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
package com.trazere.test;

import java.util.ArrayList;
import java.util.List;

import com.trazere.util.collection.CollectionUtils;
import com.trazere.util.parser.Parser;
import com.trazere.util.parser.ParserException;
import com.trazere.util.parser.ParserUtils;
import com.trazere.util.parser.StringParserSource;
import com.trazere.util.parser.core.ChoiceParser;
import com.trazere.util.parser.core.Combine3Parser;
import com.trazere.util.parser.core.CoreParsers;
import com.trazere.util.parser.core.ParserReference;
import com.trazere.util.parser.text.TextParsers;
import com.trazere.util.type.Maybe;

/**
 * DOCME
 */
public class ParserTest {
	public static void main(final String[] args)
	throws ParserException {
		final Parser<Character, Character> aParser = TextParsers.character('A');
		final Parser<Character, Character> bParser = TextParsers.character('B');
		final Parser<Character, List<Character>> manyAParser = CoreParsers.many(aParser, "test");
		final Parser<Character, Maybe<Character>> optionBParser = CoreParsers.option(bParser, "toto");
		final Parser<Character, Character> aOrBParser = new ChoiceParser<Character, Character>(CollectionUtils.<Parser<Character, ? extends Character>>list(aParser, bParser), "dsglkj");
		final Parser<Character, List<Character>> many1AOrBParser = CoreParsers.many1(aOrBParser, null);
		final Parser<Character, String> combineParser = new Combine3Parser<Character, List<Character>, Maybe<Character>, Object, String>(manyAParser, optionBParser, CoreParsers.<Character, Object>eof(), "test") {
			@Override
			protected String combine(final List<Character> subResult1, final Maybe<Character> subResult2, final Object subResult3)
			throws ParserException {
				final StringBuilder builder = new StringBuilder();
				for (final Character c : subResult1) {
					builder.append(c);
				}
				if (subResult2.isSome()) {
					builder.append(((Maybe.Some<?>) subResult2).getValue());
				}
				return builder.toString();
			}
		};
		//		final Parser<Character, String> sequenceParser = new Sequence2Parser<Character, Character, Maybe<Character>, String>(aParser, optionBParser, "titi") {
		//			@Override
		//			protected Parser<Character, String> sequence(final Character subResult1, final Maybe<Character> subResult2)
		//			throws ParserException {
		//				return new Sequence1Parser<Character, List<Character>, String>(manyAParser, "titi") {
		//					@Override
		//					protected Parser<Character, String> sequence(List<Character> subResult3)
		//					throws ParserException {
		//						final StringBuilder builder = new StringBuilder();
		//						builder.append(subResult1);
		//						if (subResult2.isSome()) {
		//							builder.append(((Maybe.Some<?>) subResult2).getValue());
		//						}
		//						for (final Character c : subResult3) {
		//							builder.append(c);
		//						}
		//						return new SuccessParser<Character, String>(builder.toString(), "gf");
		//					}
		//				};
		//			}
		//		};
		
		final ParserReference<Character, Integer> exprParser = new ParserReference<Character, Integer>();
		final Parser<Character, Integer> parenParser = new Combine3Parser<Character, Character, Integer, Character, Integer>(TextParsers.character('('), exprParser, TextParsers.character(')'), null) {
			@Override
			protected Integer combine(final Character subResult1, final Integer subResult2, final Character subResult3) {
				return subResult2;
			}
		};
		final Parser<Character, Integer> plusParser = new Combine3Parser<Character, Integer, Character, Integer, Integer>(exprParser, TextParsers.character('+'), exprParser, null) {
			@Override
			protected Integer combine(final Integer subResult1, final Character subResult2, final Integer subResult3) {
				return Integer.valueOf(subResult1.intValue() + subResult3.intValue());
			}
		};
		final Parser<Character, Integer> mulParser = new Combine3Parser<Character, Integer, Character, Integer, Integer>(exprParser, TextParsers.character('*'), exprParser, null) {
			@Override
			protected Integer combine(final Integer subResult1, final Character subResult2, final Integer subResult3) {
				return Integer.valueOf(subResult1.intValue() * subResult3.intValue());
			}
		};
		final List<Parser<Character, ? extends Integer>> exprSubParsers = new ArrayList<Parser<Character, ? extends Integer>>();
		exprSubParsers.add(TextParsers.decimal());
		exprSubParsers.add(parenParser);
		exprSubParsers.add(plusParser);
		exprSubParsers.add(mulParser);
		exprParser.set(CoreParsers.<Character, Integer>choice(exprSubParsers, null));
		
		final String source = "1+2*2*2";
		System.out.println(source);
		
		// final Object result = ParserUtils.parseLongestSuccess(TextParsers.decimal(), new StringParserSource(source));
		final Object result = ParserUtils.parseSuccesses(exprParser, new StringParserSource(source));
		System.out.println(result);
	}
}
