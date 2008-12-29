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
package com.trazere.test;

import com.trazere.parser.Parser;
import com.trazere.parser.ParserException;
import com.trazere.parser.ParserFailure;
import com.trazere.parser.ParserUtils;
import com.trazere.parser.StringParserSource;
import com.trazere.parser.core.Combine2Parser;
import com.trazere.parser.core.Combine3Parser;
import com.trazere.parser.core.CoreParsers;
import com.trazere.parser.core.ParserReference;
import com.trazere.parser.text.TextParsers;
import com.trazere.util.collection.CollectionUtils;
import com.trazere.util.type.Either;
import com.trazere.util.type.Maybe;
import java.util.Collection;
import java.util.List;

/**
 * DOCME
 */
public class ParserTest2 {
	public static void main(final String[] args)
	throws ParserException {
		runSuccessesOrFailures(EXPR, CollectionUtils.listN(new String[] {
		    "",
		    "1",
		    "1+2",
		    "-2",
		    "-",
		    "+2",
		    "1+2+3",
		    "1+2-3",
		    "-1+2",
		    "1--2",
		    "1---2",
		    "2*3",
		    "2*-3",
		    "1+2*3",
		}));
	}
	
	public static <Result> void runSuccesses(final Parser<Character, Result> parser, final Collection<String> sources)
	throws ParserException {
		System.out.println("Parser = " + parser.getDescription());
		
		for (final String source : sources) {
			System.out.println();
			System.out.println("Source = " + source);
			
			final List<Result> successes = ParserUtils.parseSuccesses(parser, new StringParserSource(source));
			displaySuccesses(successes);
		}
	}
	
	public static <Result> void runSuccessesOrFailures(final Parser<Character, Result> parser, final Collection<String> sources)
	throws ParserException {
		System.out.println("Parser = " + parser.getDescription());
		
		for (final String source : sources) {
			System.out.println();
			System.out.println("Source = " + source);
			
			final Either<List<Result>, List<ParserFailure<Character>>> results = ParserUtils.parseSuccessesOrFailures(parser, new StringParserSource(source));
			if (results.isLeft()) {
				displaySuccesses(results.asLeft().getLeft());
			} else {
				displayFailures(results.asRight().getRight());
			}
		}
	}
	
	public static <Result> void runLongestSuccess(final Parser<Character, Result> parser, final Collection<String> sources)
	throws ParserException {
		System.out.println("Parser = " + parser.getDescription());
		
		for (final String source : sources) {
			System.out.println();
			System.out.println("Source = " + source);
			
			final Maybe<Result> result = ParserUtils.parseLongestSuccess(parser, new StringParserSource(source));
			if (result.isSome()) {
				System.out.println("Success = " + result.asSome().getValue());
			} else {
				System.out.println("Failure");
			}
		}
	}
	
	public static <Result> void runLongestSuccessOrFailures(final Parser<Character, Result> parser, final Collection<String> sources)
	throws ParserException {
		System.out.println("Parser = " + parser.getDescription());
		
		for (final String source : sources) {
			System.out.println();
			System.out.println("Source = " + source);
			
			final Either<Result, List<ParserFailure<Character>>> result = ParserUtils.parseLongestSuccessOrFailures(parser, new StringParserSource(source));
			if (result.isLeft()) {
				System.out.println("Success = " + result.asLeft().getLeft());
			} else {
				displayFailures(result.asRight().getRight());
			}
		}
	}
	
	public static <Result> void runLongestSuccessOrLongestFailure(final Parser<Character, Result> parser, final Collection<String> sources)
	throws ParserException {
		System.out.println("Parser = " + parser.getDescription());
		
		for (final String source : sources) {
			System.out.println();
			System.out.println("Source = " + source);
			
			final Either<Result, ParserFailure<Character>> result = ParserUtils.parseLongestSuccessOrLongestFailure(parser, new StringParserSource(source));
			if (result.isLeft()) {
				System.out.println("Success = " + result.asLeft().getLeft());
			} else {
				final ParserFailure<?> failure = result.asRight().getRight();
				System.out.println("Failure: Excepted " + failure.getParser().getDescription() + " at " + failure.getPosition().getDescription());
			}
		}
	}
	
	private static <Result> void displaySuccesses(final List<Result> successes) {
		System.out.println("Success (" + successes.size() + ")");
		for (final Result success : successes) {
			System.out.println(" -> " + success);
		}
	}
	
	private static <Token> void displayFailures(final List<ParserFailure<Token>> failures) {
		System.out.println("Failure (" + failures.size() + ")");
		for (final ParserFailure<Token> failure : failures) {
			System.out.println(" -> Excepted " + failure.getParser().getDescription() + " at " + failure.getPosition().getDescription());
		}
	}
	
	// Expr
	public static final ParserReference<Character, Integer> EXPR = new ParserReference<Character, Integer>();
	private static final Parser<Character, Integer> NUMBER_EXPR = TextParsers.integer();
	private static final Parser<Character, Integer> PARENS_EXPR = CoreParsers.second(TextParsers.character('('), EXPR, TextParsers.character(')'), null);
	private static final Parser<Character, Integer> NEG_EXPR = new Combine2Parser<Character, Character, Integer, Integer>(TextParsers.character('-'), CoreParsers.choice(NUMBER_EXPR, PARENS_EXPR, null), null) {
		@Override
		protected Integer combine(final Character operator, final Integer value) {
			return -value;
		}
	};
	private static final Parser<Character, Integer> VALUE_EXPR = CoreParsers.choice(NUMBER_EXPR, NEG_EXPR, PARENS_EXPR, null);
	private static final ParserReference<Character, Integer> PRODUCT_EXPR = new ParserReference<Character, Integer>();
	private static final Parser<Character, Integer> MUL_EXPR = new Combine3Parser<Character, Integer, Character, Integer, Integer>(PRODUCT_EXPR, TextParsers.character('*'), VALUE_EXPR, null) {
		@Override
		protected Integer combine(final Integer left, final Character operator, final Integer right) {
			return left * right;
		}
	};
	private static final Parser<Character, Integer> DIV_EXPR = new Combine3Parser<Character, Integer, Character, Integer, Integer>(PRODUCT_EXPR, TextParsers.character('/'), VALUE_EXPR, null) {
		@Override
		protected Integer combine(final Integer left, final Character operator, final Integer right) {
			return left / right;
		}
	};
	static {
		PRODUCT_EXPR.set(CoreParsers.choice(VALUE_EXPR, MUL_EXPR, DIV_EXPR, null));
	}
	private static final ParserReference<Character, Integer> SUM_EXPR = new ParserReference<Character, Integer>();
	private static final Parser<Character, Integer> ADD_EXPR = new Combine3Parser<Character, Integer, Character, Integer, Integer>(SUM_EXPR, TextParsers.character('+'), PRODUCT_EXPR, null) {
		@Override
		protected Integer combine(final Integer left, final Character operator, final Integer right) {
			return left + right;
		}
	};
	private static final Parser<Character, Integer> MIN_EXPR = new Combine3Parser<Character, Integer, Character, Integer, Integer>(SUM_EXPR, TextParsers.character('-'), PRODUCT_EXPR, null) {
		@Override
		protected Integer combine(final Integer left, final Character operator, final Integer right) {
			return left - right;
		}
	};
	static {
		SUM_EXPR.set(CoreParsers.choice(PRODUCT_EXPR, ADD_EXPR, MIN_EXPR, null));
	}
	static {
		EXPR.set(SUM_EXPR);
	}
}
