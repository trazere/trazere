/*
 *  Copyright 2006-2009 Julien Dufour
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
package com.trazere.parser;

import com.trazere.util.lang.ref.MutableReference;
import com.trazere.util.type.Either;
import com.trazere.util.type.Maybe;
import java.util.ArrayList;
import java.util.List;

/**
 * DOCME
 */
public class ParserUtils {
	protected static final SuccessParserEngine SUCCESS_ENGINE = new SuccessParserEngine();
	protected static final FailureParserEngine FAILURE_ENGINE = new FailureParserEngine();
	
	public static <Token, Result> List<Result> parseSuccesses(final Parser<Token, Result> parser, final ParserSource<Token> source)
	throws ParserException {
		return parseSuccesses(parser, source, new IndexParserPosition<Token>());
	}
	
	public static <Token, Result> List<Result> parseSuccesses(final Parser<Token, Result> parser, final ParserSource<Token> source, final ParserPosition<Token> position)
	throws ParserException {
		// Parse.
		final List<Result> successes = new ArrayList<Result>();
		SUCCESS_ENGINE.parse(parser, source, position, new SuccessParserEngine.Handler<Token, Result>() {
			public void success(final Result result, final ParserPosition<Token> resultPosition) {
				successes.add(result);
			}
		});
		
		// Result.
		return successes;
	}
	
	public static <Token, Result> Either<List<Result>, List<ParserFailure<Token>>> parseSuccessesOrFailures(final Parser<Token, Result> parser, final ParserSource<Token> source)
	throws ParserException {
		return parseSuccessesOrFailures(parser, source, new IndexParserPosition<Token>());
	}
	
	public static <Token, Result> Either<List<Result>, List<ParserFailure<Token>>> parseSuccessesOrFailures(final Parser<Token, Result> parser, final ParserSource<Token> source, final ParserPosition<Token> position)
	throws ParserException {
		// Parse.
		final List<Result> successes = new ArrayList<Result>();
		final List<ParserFailure<Token>> failures = new ArrayList<ParserFailure<Token>>();
		FAILURE_ENGINE.parse(parser, source, position, new FailureParserEngine.Handler<Token, Result>() {
			public void success(final Result result, final ParserPosition<Token> resultPosition) {
				successes.add(result);
			}
			
			public void failure(final Parser<Token, ?> failureParser, final ParserPosition<Token> failurePosition)
			throws ParserException {
				failures.add(new ParserFailureImpl<Token>(failureParser, failurePosition));
			}
		});
		
		// Result.
		if (!successes.isEmpty() && failures.isEmpty()) {
			return Either.<List<Result>, List<ParserFailure<Token>>>left(successes);
		} else if (successes.isEmpty() && !failures.isEmpty()) {
			return Either.<List<Result>, List<ParserFailure<Token>>>right(failures);
		} else {
			throw new ParserException("Incompatibles successes or failures !");
		}
	}
	
	public static <Token, Result> Maybe<Result> parseLongestSuccess(final Parser<Token, Result> parser, final ParserSource<Token> source)
	throws ParserException {
		return parseLongestSuccess(parser, source, new IndexParserPosition<Token>());
	}
	
	public static <Token, Result> Maybe<Result> parseLongestSuccess(final Parser<Token, Result> parser, final ParserSource<Token> source, final ParserPosition<Token> position)
	throws ParserException {
		// Parse.
		final MutableReference<Result> successReference = new MutableReference<Result>();
		SUCCESS_ENGINE.parse(parser, source, position, new SuccessParserEngine.Handler<Token, Result>() {
			private final MutableReference<ParserPosition<Token>> _position = new MutableReference<ParserPosition<Token>>();
			
			public void success(final Result result, final ParserPosition<Token> resultPosition) {
				if (!_position.isSet() || resultPosition.compareTo(_position.get()) > 0) {
					successReference.update(result);
					_position.update(resultPosition);
				}
			}
		});
		
		// Result.
		return successReference.asMaybe();
	}
	
	public static <Token, Result> Either<Result, List<ParserFailure<Token>>> parseLongestSuccessOrFailures(final Parser<Token, Result> parser, final ParserSource<Token> source)
	throws ParserException {
		return parseLongestSuccessOrFailures(parser, source, new IndexParserPosition<Token>());
	}
	
	public static <Token, Result> Either<Result, List<ParserFailure<Token>>> parseLongestSuccessOrFailures(final Parser<Token, Result> parser, final ParserSource<Token> source, final ParserPosition<Token> position)
	throws ParserException {
		// Parse.
		final MutableReference<Result> successReference = new MutableReference<Result>();
		final List<ParserFailure<Token>> failures = new ArrayList<ParserFailure<Token>>();
		FAILURE_ENGINE.parse(parser, source, position, new FailureParserEngine.Handler<Token, Result>() {
			private final MutableReference<ParserPosition<Token>> _position = new MutableReference<ParserPosition<Token>>();
			
			public void success(final Result result, final ParserPosition<Token> resultPosition) {
				if (!_position.isSet() || resultPosition.compareTo(_position.get()) > 0) {
					successReference.update(result);
					_position.update(resultPosition);
				}
			}
			
			public void failure(final Parser<Token, ?> failureParser, final ParserPosition<Token> failurePosition)
			throws ParserException {
				failures.add(new ParserFailureImpl<Token>(failureParser, failurePosition));
			}
		});
		
		// Result.
		if (successReference.isSet()) {
			return Either.<Result, List<ParserFailure<Token>>>left(successReference.get());
		} else {
			return Either.<Result, List<ParserFailure<Token>>>right(failures);
		}
	}
	
	public static <Token, Result> Either<Result, ParserFailure<Token>> parseLongestSuccessOrLongestFailure(final Parser<Token, Result> parser, final ParserSource<Token> source)
	throws ParserException {
		return parseLongestSuccessOrLongestFailure(parser, source, new IndexParserPosition<Token>());
	}
	
	public static <Token, Result> Either<Result, ParserFailure<Token>> parseLongestSuccessOrLongestFailure(final Parser<Token, Result> parser, final ParserSource<Token> source, final ParserPosition<Token> position)
	throws ParserException {
		// Parse.
		final MutableReference<Result> successReference = new MutableReference<Result>();
		final MutableReference<ParserFailure<Token>> failureReference = new MutableReference<ParserFailure<Token>>();
		FAILURE_ENGINE.parse(parser, source, position, new FailureParserEngine.Handler<Token, Result>() {
			private final MutableReference<ParserPosition<Token>> _successPosition = new MutableReference<ParserPosition<Token>>();
			private final MutableReference<ParserPosition<Token>> _failurePosition = new MutableReference<ParserPosition<Token>>();
			
			public void success(final Result result, final ParserPosition<Token> resultPosition) {
				if (!_successPosition.isSet() || resultPosition.compareTo(_successPosition.get()) > 0) {
					successReference.update(result);
					_successPosition.update(resultPosition);
				}
			}
			
			public void failure(final Parser<Token, ?> failureParser, final ParserPosition<Token> failurePosition)
			throws ParserException {
				if (!_failurePosition.isSet() || failurePosition.compareTo(_failurePosition.get()) > 0) {
					failureReference.update(new ParserFailureImpl<Token>(failureParser, failurePosition));
					_failurePosition.update(failurePosition);
				}
			}
		});
		
		// Result.
		if (successReference.isSet()) {
			return Either.<Result, ParserFailure<Token>>left(successReference.get());
		} else if (failureReference.isSet()) {
			return Either.<Result, ParserFailure<Token>>right(failureReference.get());
		} else {
			throw new ParserException("Incompatibles successes or failures !");
		}
	}
	
	public static <Token> String renderFailures(final List<ParserFailure<Token>> failures) {
		assert null != failures;
		
		// Render.
		final StringBuilder builder = new StringBuilder();
		final boolean first = true;
		for (final ParserFailure<Token> failure : failures) {
			if (first) {
				builder.append(", or ");
			}
			builder.append(failure.getParser().getDescription()).append(" at ").append(failure.getPosition().getDescription());
		}
		return builder.toString();
	}
	
	private ParserUtils() {
		// Prevent instantiation.
	}
}