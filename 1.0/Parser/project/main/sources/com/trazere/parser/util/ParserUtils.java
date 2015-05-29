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

import com.trazere.core.lang.InternalException;
import com.trazere.core.reference.MutableReference;
import com.trazere.core.reference.ReferenceNotSetException;
import com.trazere.core.text.Joiner;
import com.trazere.core.text.Joiners;
import com.trazere.core.util.Either;
import com.trazere.core.util.Maybe;
import com.trazere.parser.Parser;
import com.trazere.parser.ParserException;
import com.trazere.parser.ParserFailure;
import com.trazere.parser.ParserPosition;
import com.trazere.parser.ParserSource;
import com.trazere.parser.core.CoreParsers;
import com.trazere.parser.impl.ParserFailureImpl;
import java.util.ArrayList;
import java.util.List;

/**
 * DOCME
 */
public class ParserUtils {
	public static <Token, Result> List<Result> parseSuccesses(final Parser<Token, Result> parser, final ParserSource<Token> source) {
		return parseSuccesses(parser, source, new IndexParserPosition<Token>());
	}
	
	public static <Token, Result> List<Result> parseSuccesses(final Parser<Token, Result> parser, final ParserSource<Token> source, final ParserPosition<Token> position) {
		return parseSuccesses(parser, source, position, SuccessParserEngine.INSTANCE);
	}
	
	public static <Token, Result> List<Result> parseSuccesses(final Parser<Token, Result> parser, final ParserSource<Token> source, final ParserPosition<Token> position, final SuccessParserEngine engine) {
		assert null != engine;
		
		// Parse.
		final List<Result> successes = new ArrayList<>();
		engine.parse(parser, source, position, new SuccessParserEngine.Handler<Token, Result>() {
			@Override
			public void success(final Result result, final ParserPosition<Token> resultPosition) {
				successes.add(result);
			}
		});
		
		// Result.
		return successes;
	}
	
	public static <Token, Result> Either<List<Result>, List<ParserFailure<Token>>> parseSuccessesOrFailures(final Parser<Token, Result> parser, final ParserSource<Token> source) {
		return parseSuccessesOrFailures(parser, source, new IndexParserPosition<Token>());
	}
	
	public static <Token, Result> Either<List<Result>, List<ParserFailure<Token>>> parseSuccessesOrFailures(final Parser<Token, Result> parser, final ParserSource<Token> source, final ParserPosition<Token> position) {
		return parseSuccessesOrFailures(parser, source, position, FailureParserEngine.INSTANCE);
	}
	
	public static <Token, Result> Either<List<Result>, List<ParserFailure<Token>>> parseSuccessesOrFailures(final Parser<Token, Result> parser, final ParserSource<Token> source, final ParserPosition<Token> position, final FailureParserEngine engine) {
		assert null != engine;
		
		// Parse.
		final List<Result> successes = new ArrayList<>();
		final List<ParserFailure<Token>> failures = new ArrayList<>();
		engine.parse(parser, source, position, new FailureParserEngine.Handler<Token, Result>() {
			@Override
			public void success(final Result result, final ParserPosition<Token> resultPosition) {
				successes.add(result);
			}
			
			@Override
			public void failure(final Parser<Token, ?> failureParser, final ParserPosition<Token> failurePosition) {
				failures.add(new ParserFailureImpl<>(failureParser, failurePosition));
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
	
	public static <Token, Result> Maybe<Result> parseLongestSuccess(final Parser<Token, Result> parser, final ParserSource<Token> source) {
		return parseLongestSuccess(parser, source, new IndexParserPosition<Token>());
	}
	
	public static <Token, Result> Maybe<Result> parseLongestSuccess(final Parser<Token, Result> parser, final ParserSource<Token> source, final ParserPosition<Token> position) {
		return parseLongestSuccess(parser, source, position, SuccessParserEngine.INSTANCE);
	}
	
	public static <Token, Result> Maybe<Result> parseLongestSuccess(final Parser<Token, Result> parser, final ParserSource<Token> source, final ParserPosition<Token> position, final SuccessParserEngine engine) {
		assert null != engine;
		
		// Parse.
		final MutableReference<Result> successReference = new MutableReference<>();
		engine.parse(parser, source, position, new SuccessParserEngine.Handler<Token, Result>() {
			private final MutableReference<ParserPosition<Token>> _position = new MutableReference<>();
			
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
	
	public static <Token, Result> Either<Result, List<ParserFailure<Token>>> parseLongestSuccessOrFailures(final Parser<Token, Result> parser, final ParserSource<Token> source) {
		return parseLongestSuccessOrFailures(parser, source, new IndexParserPosition<Token>());
	}
	
	public static <Token, Result> Either<Result, List<ParserFailure<Token>>> parseLongestSuccessOrFailures(final Parser<Token, Result> parser, final ParserSource<Token> source, final ParserPosition<Token> position) {
		return parseLongestSuccessOrFailures(parser, source, position, FailureParserEngine.INSTANCE);
	}
	
	public static <Token, Result> Either<Result, List<ParserFailure<Token>>> parseLongestSuccessOrFailures(final Parser<Token, Result> parser, final ParserSource<Token> source, final ParserPosition<Token> position, final FailureParserEngine engine) {
		assert null != engine;
		
		// Parse.
		final MutableReference<Result> successReference = new MutableReference<>();
		final List<ParserFailure<Token>> failures = new ArrayList<>();
		engine.parse(parser, source, position, new FailureParserEngine.Handler<Token, Result>() {
			private final MutableReference<ParserPosition<Token>> _position = new MutableReference<>();
			
			@Override
			public void success(final Result result, final ParserPosition<Token> resultPosition) {
				try {
					if (!_position.isSet() || resultPosition.compareTo(_position.get()) > 0) {
						successReference.update(result);
						_position.update(resultPosition);
					}
				} catch (final ReferenceNotSetException exception) {
					throw new InternalException(exception);
				}
			}
			
			@Override
			public void failure(final Parser<Token, ?> failureParser, final ParserPosition<Token> failurePosition) {
				failures.add(new ParserFailureImpl<>(failureParser, failurePosition));
			}
		});
		
		// Result.
		if (successReference.isSet()) {
			try {
				return Either.<Result, List<ParserFailure<Token>>>left(successReference.get());
			} catch (final ReferenceNotSetException exception) {
				throw new InternalException(exception);
			}
		} else {
			return Either.<Result, List<ParserFailure<Token>>>right(failures);
		}
	}
	
	public static <Token, Result> Either<Result, ParserFailure<Token>> parseLongestSuccessOrLongestFailure(final Parser<Token, Result> parser, final ParserSource<Token> source) {
		return parseLongestSuccessOrLongestFailure(parser, source, new IndexParserPosition<Token>());
	}
	
	public static <Token, Result> Either<Result, ParserFailure<Token>> parseLongestSuccessOrLongestFailure(final Parser<Token, Result> parser, final ParserSource<Token> source, final ParserPosition<Token> position) {
		return parseLongestSuccessOrLongestFailure(parser, source, position, FailureParserEngine.INSTANCE);
	}
	
	public static <Token, Result> Either<Result, ParserFailure<Token>> parseLongestSuccessOrLongestFailure(final Parser<Token, Result> parser, final ParserSource<Token> source, final ParserPosition<Token> position, final FailureParserEngine engine) {
		assert null != engine;
		
		// Parse.
		final MutableReference<Result> successReference = new MutableReference<>();
		final MutableReference<ParserFailure<Token>> failureReference = new MutableReference<>();
		engine.parse(parser, source, position, new FailureParserEngine.Handler<Token, Result>() {
			private final MutableReference<ParserPosition<Token>> _successPosition = new MutableReference<>();
			private final MutableReference<ParserPosition<Token>> _failurePosition = new MutableReference<>();
			
			@Override
			public void success(final Result result, final ParserPosition<Token> resultPosition) {
				try {
					if (!_successPosition.isSet() || resultPosition.compareTo(_successPosition.get()) > 0) {
						successReference.update(result);
						_successPosition.update(resultPosition);
					}
				} catch (final ReferenceNotSetException exception) {
					throw new InternalException(exception);
				}
			}
			
			@Override
			public void failure(final Parser<Token, ?> failureParser, final ParserPosition<Token> failurePosition) {
				try {
					if (!_failurePosition.isSet() || failurePosition.compareTo(_failurePosition.get()) > 0) {
						failureReference.update(new ParserFailureImpl<>(failureParser, failurePosition));
						_failurePosition.update(failurePosition);
					}
				} catch (final ReferenceNotSetException exception) {
					throw new InternalException(exception);
				}
			}
		});
		
		// Result.
		if (successReference.isSet()) {
			try {
				return Either.<Result, ParserFailure<Token>>left(successReference.get());
			} catch (final ReferenceNotSetException exception) {
				throw new InternalException(exception);
			}
		} else if (failureReference.isSet()) {
			try {
				return Either.<Result, ParserFailure<Token>>right(failureReference.get());
			} catch (final ReferenceNotSetException exception) {
				throw new InternalException(exception);
			}
		} else {
			throw new ParserException("Incompatible successes or failures !");
		}
	}
	
	public static <T> Either<T, String> parse(final Parser<Character, ? extends T> parser, final String representation) {
		assert null != parser;
		assert null != representation;
		
		final Parser<Character, T> parser_ = CoreParsers.first(parser, CoreParsers.<Character, Object>eof(), null);
		final Either<List<T>, List<ParserFailure<Character>>> results = ParserUtils.parseSuccessesOrFailures(parser_, new StringParserSource(representation));
		if (results.isLeft()) {
			final List<T> successes = results.asLeft().getValue();
			if (successes.isEmpty()) {
				return Either.right("Invalid representation \"" + representation + "\"");
			} else if (1 == successes.size()) {
				return Either.left(successes.get(0));
			} else {
				return Either.right("Ambiguous representation \"" + representation + "\"");
			}
		} else {
			return Either.right("Invalid representation \"" + representation + "\" : expected " + ParserUtils.renderFailures(results.asRight().getValue()));
		}
	}
	
	public static <Token> String renderFailures(final List<ParserFailure<Token>> failures) {
		return renderFailures(failures, new StringBuilder()).toString();
	}
	
	public static <Token> StringBuilder renderFailures(final List<ParserFailure<Token>> failures, final StringBuilder builder) {
		return RENDER_FAILURES.join(failures, builder).get1();
	}
	
	private static final Joiner<ParserFailure<?>> RENDER_FAILURES = Joiners.joiner(failure -> failure.getParser().getDescription() + " at " + failure.getPosition().getDescription(), false, ", or ");
	
	private ParserUtils() {
		// Prevents instantiation.
	}
}
