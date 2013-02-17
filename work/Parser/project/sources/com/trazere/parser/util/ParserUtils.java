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
package com.trazere.parser.util;

import com.trazere.parser.Parser;
import com.trazere.parser.ParserException;
import com.trazere.parser.ParserFailure;
import com.trazere.parser.ParserPosition;
import com.trazere.parser.ParserSource;
import com.trazere.parser.core.CoreParsers;
import com.trazere.parser.impl.ParserFailureImpl;
import com.trazere.util.function.Procedure2;
import com.trazere.util.reference.MutableReference;
import com.trazere.util.text.TextUtils;
import com.trazere.util.type.Either;
import com.trazere.util.type.Maybe;
import java.util.ArrayList;
import java.util.List;

/**
 * DOCME
 */
public class ParserUtils {
	public static <Token, Result> List<Result> parseSuccesses(final Parser<Token, Result> parser, final ParserSource<Token> source)
	throws ParserException {
		return parseSuccesses(parser, source, new IndexParserPosition<Token>());
	}
	
	public static <Token, Result> List<Result> parseSuccesses(final Parser<Token, Result> parser, final ParserSource<Token> source, final ParserPosition<Token> position)
	throws ParserException {
		return parseSuccesses(parser, source, position, SuccessParserEngine.INSTANCE);
	}
	
	public static <Token, Result> List<Result> parseSuccesses(final Parser<Token, Result> parser, final ParserSource<Token> source, final ParserPosition<Token> position, final SuccessParserEngine engine)
	throws ParserException {
		assert null != engine;
		
		// Parse.
		final List<Result> successes = new ArrayList<Result>();
		engine.parse(parser, source, position, new SuccessParserEngine.Handler<Token, Result>() {
			@Override
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
		return parseSuccessesOrFailures(parser, source, position, FailureParserEngine.INSTANCE);
	}
	
	public static <Token, Result> Either<List<Result>, List<ParserFailure<Token>>> parseSuccessesOrFailures(final Parser<Token, Result> parser, final ParserSource<Token> source, final ParserPosition<Token> position, final FailureParserEngine engine)
	throws ParserException {
		assert null != engine;
		
		// Parse.
		final List<Result> successes = new ArrayList<Result>();
		final List<ParserFailure<Token>> failures = new ArrayList<ParserFailure<Token>>();
		engine.parse(parser, source, position, new FailureParserEngine.Handler<Token, Result>() {
			@Override
			public void success(final Result result, final ParserPosition<Token> resultPosition) {
				successes.add(result);
			}
			
			@Override
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
			throw new ParserException("Incompatible successes or failures !");
		}
	}
	
	public static <Token, Result> Maybe<Result> parseLongestSuccess(final Parser<Token, Result> parser, final ParserSource<Token> source)
	throws ParserException {
		return parseLongestSuccess(parser, source, new IndexParserPosition<Token>());
	}
	
	public static <Token, Result> Maybe<Result> parseLongestSuccess(final Parser<Token, Result> parser, final ParserSource<Token> source, final ParserPosition<Token> position)
	throws ParserException {
		return parseLongestSuccess(parser, source, position, SuccessParserEngine.INSTANCE);
	}
	
	public static <Token, Result> Maybe<Result> parseLongestSuccess(final Parser<Token, Result> parser, final ParserSource<Token> source, final ParserPosition<Token> position, final SuccessParserEngine engine)
	throws ParserException {
		assert null != engine;
		
		// Parse.
		final MutableReference<Result> successReference = new MutableReference<Result>();
		engine.parse(parser, source, position, new SuccessParserEngine.Handler<Token, Result>() {
			private final MutableReference<ParserPosition<Token>> _position = new MutableReference<ParserPosition<Token>>();
			
			@Override
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
		return parseLongestSuccessOrFailures(parser, source, position, FailureParserEngine.INSTANCE);
	}
	
	public static <Token, Result> Either<Result, List<ParserFailure<Token>>> parseLongestSuccessOrFailures(final Parser<Token, Result> parser, final ParserSource<Token> source, final ParserPosition<Token> position, final FailureParserEngine engine)
	throws ParserException {
		assert null != engine;
		
		// Parse.
		final MutableReference<Result> successReference = new MutableReference<Result>();
		final List<ParserFailure<Token>> failures = new ArrayList<ParserFailure<Token>>();
		engine.parse(parser, source, position, new FailureParserEngine.Handler<Token, Result>() {
			private final MutableReference<ParserPosition<Token>> _position = new MutableReference<ParserPosition<Token>>();
			
			@Override
			public void success(final Result result, final ParserPosition<Token> resultPosition) {
				if (!_position.isSet() || resultPosition.compareTo(_position.get()) > 0) {
					successReference.update(result);
					_position.update(resultPosition);
				}
			}
			
			@Override
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
		return parseLongestSuccessOrLongestFailure(parser, source, position, FailureParserEngine.INSTANCE);
	}
	
	public static <Token, Result> Either<Result, ParserFailure<Token>> parseLongestSuccessOrLongestFailure(final Parser<Token, Result> parser, final ParserSource<Token> source, final ParserPosition<Token> position, final FailureParserEngine engine)
	throws ParserException {
		assert null != engine;
		
		// Parse.
		final MutableReference<Result> successReference = new MutableReference<Result>();
		final MutableReference<ParserFailure<Token>> failureReference = new MutableReference<ParserFailure<Token>>();
		engine.parse(parser, source, position, new FailureParserEngine.Handler<Token, Result>() {
			private final MutableReference<ParserPosition<Token>> _successPosition = new MutableReference<ParserPosition<Token>>();
			private final MutableReference<ParserPosition<Token>> _failurePosition = new MutableReference<ParserPosition<Token>>();
			
			@Override
			public void success(final Result result, final ParserPosition<Token> resultPosition) {
				if (!_successPosition.isSet() || resultPosition.compareTo(_successPosition.get()) > 0) {
					successReference.update(result);
					_successPosition.update(resultPosition);
				}
			}
			
			@Override
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
			throw new ParserException("Incompatible successes or failures !");
		}
	}
	
	public static <T> T parse(final Parser<Character, ? extends T> parser, final String representation)
	throws ParserException {
		assert null != parser;
		assert null != representation;
		
		final Parser<Character, T> parser_ = CoreParsers.first(parser, CoreParsers.<Character, Object>eof(), null);
		final Either<List<T>, List<ParserFailure<Character>>> results = ParserUtils.parseSuccessesOrFailures(parser_, new StringParserSource(representation));
		if (results.isLeft()) {
			final List<T> successes = results.asLeft().getLeft();
			if (successes.isEmpty()) {
				throw new ParserException("Invalid representation \"" + representation + "\"");
			} else if (1 == successes.size()) {
				return successes.get(0);
			} else {
				throw new ParserException("Ambiguous representation \"" + representation + "\"");
			}
		} else {
			throw new ParserException("Invalid representation \"" + representation + "\" : expected " + renderFailures(results.asRight().getRight()));
		}
	}
	
	public static <Token> String renderFailures(final List<ParserFailure<Token>> failures) {
		return renderFailures(failures, new StringBuilder()).toString();
	}
	
	public static <Token> StringBuilder renderFailures(final List<ParserFailure<Token>> failures, final StringBuilder builder) {
		return TextUtils.join(failures.iterator(), _RENDER_FAILURE, ", or ", builder);
	}
	
	private static final Procedure2<StringBuilder, ParserFailure<?>, RuntimeException> _RENDER_FAILURE = new Procedure2<StringBuilder, ParserFailure<?>, RuntimeException>() {
		@Override
		public void execute(final StringBuilder builder, final ParserFailure<?> failure) {
			builder.append(failure.getParser().getDescription()).append(" at ").append(failure.getPosition().getDescription());
		}
	};
	
	private ParserUtils() {
		// Prevents instantiation.
	}
}
