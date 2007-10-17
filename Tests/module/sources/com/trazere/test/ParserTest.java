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
import java.util.Collection;
import java.util.List;

import com.trazere.util.collection.CollectionUtils;
import com.trazere.util.parser.Parser;
import com.trazere.util.parser.ParserException;
import com.trazere.util.parser.ParserUtils;
import com.trazere.util.parser.StringParserSource;
import com.trazere.util.parser.core.ChoiceParser;
import com.trazere.util.parser.core.Combine1Parser;
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
		runSuccesses(EXPR2, CollectionUtils.listN(new String[] {
		    "",
		    "a",
		    "a+b",
		    "+b",
		    "a+b+c",
		    "(a+b)+c",
		}));
	}
	
	public static <Result> void runSuccesses(final Parser<Character, Result> parser, final Collection<String> sources)
	throws ParserException {
		for (final String source : sources) {
			System.out.println("Source = " + source);
			
			final List<Result> results = ParserUtils.parseSuccesses(parser, new StringParserSource(source));
			System.out.println(results.size() + " results");
			for (final Result result : results) {
				System.out.println(" -> " + result);
			}
			
			System.out.println();
		}
	}
	
	public static <Result> void runLongestSuccess(final Parser<Character, Result> parser, final Collection<String> sources)
	throws ParserException {
		for (final String source : sources) {
			System.out.println("Source = " + source);
			
			final Maybe<Result> result = ParserUtils.parseLongestSuccess(parser, new StringParserSource(source));
			if (result.isSome()) {
				System.out.println("Sussess = " + result);
			} else {
				System.out.println("Failure");
			}
			
			System.out.println();
		}
	}
	
	// Basic
	public static final Parser<Character, Character> A = TextParsers.character('A');
	public static final Parser<Character, Character> B = TextParsers.character('B');
	public static final Parser<Character, List<Character>> MANY_A = CoreParsers.many(A, "test");
	public static final Parser<Character, Maybe<Character>> OPTION_B = CoreParsers.option(B, "toto");
	public static final Parser<Character, Character> A_OR_B = new ChoiceParser<Character, Character>(CollectionUtils.<Parser<Character, ? extends Character>>list(A, B), "dsglkj");
	public static final Parser<Character, List<Character>> MANY1_A_OR_B = CoreParsers.many1(A_OR_B, null);
	public static final Parser<Character, String> COMBINE = new Combine3Parser<Character, List<Character>, Maybe<Character>, Object, String>(MANY_A, OPTION_B, CoreParsers.<Character, Object>eof(), "test") {
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
	
	// Expr
	public static final ParserReference<Character, Integer> EXPR = new ParserReference<Character, Integer>();
	public static final Parser<Character, Integer> EXPR_PAREN = new Combine3Parser<Character, Character, Integer, Character, Integer>(TextParsers.character('('), EXPR, TextParsers.character(')'), null) {
		@Override
		protected Integer combine(final Character subResult1, final Integer subResult2, final Character subResult3) {
			return subResult2;
		}
	};
	public static final Parser<Character, Integer> EXPR_PLUS = new Combine3Parser<Character, Integer, Character, Integer, Integer>(EXPR, TextParsers.character('+'), EXPR, null) {
		@Override
		protected Integer combine(final Integer subResult1, final Character subResult2, final Integer subResult3) {
			return Integer.valueOf(subResult1.intValue() + subResult3.intValue());
		}
	};
	public static final Parser<Character, Integer> EXPR_MUL = new Combine3Parser<Character, Integer, Character, Integer, Integer>(EXPR, TextParsers.character('*'), EXPR, null) {
		@Override
		protected Integer combine(final Integer subResult1, final Character subResult2, final Integer subResult3) {
			return Integer.valueOf(subResult1.intValue() * subResult3.intValue());
		}
	};
	static {
		final List<Parser<Character, ? extends Integer>> exprs = new ArrayList<Parser<Character, ? extends Integer>>();
		exprs.add(TextParsers.decimal());
		exprs.add(EXPR_PAREN);
		exprs.add(EXPR_PLUS);
		exprs.add(EXPR_MUL);
		EXPR.set(CoreParsers.<Character, Integer>choice(exprs, null));
	}
	
	// Expr 2
	public static final ParserReference<Character, String> EXPR2 = new ParserReference<Character, String>();
	public static final Parser<Character, String> EXPR2_ATOM = new Combine1Parser<Character, List<Character>, String>(CoreParsers.many(TextParsers.letter(), null), null) {
		@Override
		protected String combine(final List<Character> characters) {
			final StringBuilder builder = new StringBuilder();
			builder.append('\'');
			for (final Character character : characters) {
				builder.append(character.charValue());
			}
			builder.append('\'');
			return builder.toString();
		}
	};
	public static final Parser<Character, String> EXPR2_PAREN = CoreParsers.second(TextParsers.character('('), EXPR2, TextParsers.character(')'), null);
	public static final Parser<Character, String> EXPR2_PLUS = new Combine3Parser<Character, String, Character, String, String>(EXPR2, TextParsers.character('+'), EXPR2, null) {
		@Override
		protected String combine(final String subResult1, final Character subResult2, final String subResult3) {
			final StringBuilder builder = new StringBuilder();
			builder.append("(");
			builder.append(subResult1);
			builder.append(" + ");
			builder.append(subResult3);
			builder.append(")");
			return builder.toString();
		}
	};
	static {
		final List<Parser<Character, ? extends String>> exprs = new ArrayList<Parser<Character, ? extends String>>();
		exprs.add(EXPR2_ATOM);
		exprs.add(EXPR2_PAREN);
		exprs.add(EXPR2_PLUS);
		EXPR2.set(CoreParsers.<Character, String>choice(exprs, null));
	}
}
