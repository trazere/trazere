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
package com.trazere.parser;

import com.trazere.util.type.Maybe;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FailureParserEngine {
	public interface Handler<Token, Result> {
		public void success(final Result result, final ParserPosition<Token> position)
		throws ParserException;
		
		public void failure(final Parser<Token, ?> parser, final ParserPosition<Token> position)
		throws ParserException;
	}
	
	public <Token, Result> void parse(final Parser<Token, Result> parser, final ParserSource<Token> source, final ParserPosition<Token> position, final Handler<Token, Result> handler)
	throws ParserException {
		assert null != parser;
		assert null != source;
		assert null != handler;
		
		// Initialization.
		final List<ParserClosureImpl<Token, ?>> failures = new ArrayList<ParserClosureImpl<Token, ?>>();
		final ParserStateImpl<Token> rootState = buildState(position, failures);
		rootState.parse(parser, new ParserHandler<Token, Result>() {
			public void result(final Result result, final ParserState<Token> state)
			throws ParserException {
				handler.success(result, state.getPosition());
			}
		}, Maybe.<ParserClosure<Token, ?>>none());
		
		// Parse the tokens.
		ParserPosition<Token> currentPosition = position;
		List<ParserContinuation<Token>> continuations = rootState.getContinuations();
		while (source.hasNext() && !continuations.isEmpty()) {
			final Token token = source.next();
			currentPosition = currentPosition.next(token);
			
			final ParserStateImpl<Token> state = buildState(currentPosition, failures);
			for (final ParserContinuation<Token> continuation : continuations) {
				continuation.token(token, state);
			}
			continuations = state.getContinuations();
		}
		
		// Parse the end of file.
		if (!continuations.isEmpty()) {
			final ParserState<Token> state = buildState(currentPosition, failures);
			for (final ParserContinuation<Token> continuation : continuations) {
				continuation.eof(state);
			}
		}
		
		// Report the failures.
		for (final ParserFailure<Token> failure : failures) {
			handler.failure(failure.getParser(), failure.getPosition());
		}
	}
	
	protected <Token> ParserStateImpl<Token> buildState(final ParserPosition<Token> position, final List<ParserClosureImpl<Token, ?>> failures) {
		return new ParserStateImpl<Token>(position) {
			@Override
			protected <Result> ParserClosureImpl<Token, Result> buildClosure(final Parser<Token, Result> parser, final Maybe<ParserClosure<Token, ?>> parent) {
				return new ParserClosureImpl<Token, Result>(parser, position) {
					@Override
					public void success(final Result result, final ParserState<Token> state)
					throws ParserException {
						// Success.
						super.success(result, state);
						
						// Cleanup the failures.
						final Iterator<ParserClosureImpl<Token, ?>> failuresIt = failures.iterator();
						while (failuresIt.hasNext()) {
							final ParserClosureImpl<Token, ?> failure = failuresIt.next();
							if (!failure.isValidFailure()) {
								failuresIt.remove();
							}
						}
					}
					
					public void failure(final ParserState<Token> state) {
						// Add the failure.
						if (isValidFailure()) {
							// TODO: find a parser with a description
							failures.add(this);
						}
					}
					
					@Override
					public boolean isValidFailure() {
						if (!_results.isEmpty()) {
							return false;
						} else if (parent.isSome()) {
							// HACK: work around the type system: higher order types are needed to parametrize the type of the closures (because of the result parameter)
							@SuppressWarnings("unchecked")
							final ParserClosureImpl<Token, Result> closure = (ParserClosureImpl<Token, Result>) parent.asSome().getValue();
							return closure.isValidFailure();
						} else {
							return true;
						}
					}
				};
			}
		};
	}
}
