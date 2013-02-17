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
import com.trazere.parser.ParserClosure;
import com.trazere.parser.ParserContinuation;
import com.trazere.parser.ParserException;
import com.trazere.parser.ParserHandler;
import com.trazere.parser.ParserPosition;
import com.trazere.parser.ParserSource;
import com.trazere.parser.ParserState;
import com.trazere.parser.impl.ParserClosureImpl;
import com.trazere.parser.impl.ParserStateImpl;
import com.trazere.util.lang.MutableObject;
import com.trazere.util.type.Maybe;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FailureParserEngine {
	public static final FailureParserEngine INSTANCE = new FailureParserEngine();
	
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
		final List<FailureParserClosure<Token, ?>> failures = new ArrayList<FailureParserClosure<Token, ?>>();
		final ParserStateImpl<Token> rootState = buildState(position, failures);
		rootState.parse(parser, new ParserHandler<Token, Result>() {
			@Override
			public void result(final Result result, final ParserState<Token> state)
			throws ParserException {
				handler.success(result, state.getPosition());
			}
		}, Maybe.<ParserClosure<Token, ?>>none());
		
		// Parse the tokens.
		final MutableObject<ParserPosition<Token>> currentPosition = new MutableObject<ParserPosition<Token>>(position);
		final MutableObject<List<ParserContinuation<Token>>> continuations = new MutableObject<List<ParserContinuation<Token>>>(rootState.getContinuations());
		while (source.hasNext() && !continuations.get().isEmpty()) {
			final Token token = source.next();
			currentPosition.set(currentPosition.get().next(token));
			
			final ParserStateImpl<Token> state = buildState(currentPosition.get(), failures);
			for (final ParserContinuation<Token> continuation : continuations.get()) {
				continuation.token(token, state);
			}
			continuations.set(state.getContinuations());
		}
		
		// Parse the end of file.
		if (!continuations.get().isEmpty()) {
			final ParserState<Token> state = buildState(currentPosition.get(), failures);
			for (final ParserContinuation<Token> continuation : continuations.get()) {
				continuation.eof(state);
			}
		}
		
		// Report the failures.
		for (final ParserClosure<Token, ?> failure : failures) {
			handler.failure(failure.getParser(), failure.getPosition());
		}
	}
	
	protected <Token> ParserStateImpl<Token> buildState(final ParserPosition<Token> position, final List<FailureParserClosure<Token, ?>> failures) {
		return new ParserStateImpl<Token>(position) {
			@Override
			protected <Result> FailureParserClosure<Token, Result> buildClosure(final Parser<Token, Result> parser, final Maybe<ParserClosure<Token, ?>> parent) {
				return new FailureParserClosure<Token, Result>(parser, _position, parent, failures);
			}
		};
	}
	
	protected static class FailureParserState<Token>
	extends ParserStateImpl<Token> {
		public FailureParserState(final ParserPosition<Token> position, final List<FailureParserClosure<Token, ?>> failures) {
			super(position);
			
			// Checks.
			assert null != failures;
			
			// Initialization.
			_failures = failures;
		}
		
		protected final List<FailureParserClosure<Token, ?>> _failures;
		
		@Override
		protected <Result> FailureParserClosure<Token, Result> buildClosure(final Parser<Token, Result> parser, final Maybe<ParserClosure<Token, ?>> parent) {
			return new FailureParserClosure<Token, Result>(parser, _position, parent, _failures);
		}
	}
	
	protected static class FailureParserClosure<Token, Result>
	extends ParserClosureImpl<Token, Result> {
		public FailureParserClosure(final Parser<Token, Result> parser, final ParserPosition<Token> position, final Maybe<ParserClosure<Token, ?>> parent, final List<FailureParserClosure<Token, ?>> failures) {
			super(parser, position);
			
			// Checks.
			assert null != parent;
			assert null != failures;
			
			// Initialization.
			_parent = parent;
			_failures = failures;
		}
		
		protected final List<FailureParserClosure<Token, ?>> _failures;
		
		@Override
		public void success(final Result result, final ParserState<Token> state)
		throws ParserException {
			// Success.
			super.success(result, state);
			
			// Cleanup the failures.
			final Iterator<FailureParserClosure<Token, ?>> failuresIt = _failures.iterator();
			while (failuresIt.hasNext()) {
				final FailureParserClosure<Token, ?> failure = failuresIt.next();
				if (!failure.isValidFailure()) {
					failuresIt.remove();
				}
			}
		}
		
		@Override
		public void failure(final ParserState<Token> state) {
			// Add the failure.
			if (isValidFailure()) {
				// TODO: find a parser with a description
				_failures.add(this);
			}
		}
		
		protected final Maybe<ParserClosure<Token, ?>> _parent;
		
		public boolean isValidFailure() {
			if (!_results.isEmpty()) {
				return false;
			} else if (_parent.isSome()) {
				// HACK: work around the type system: higher order types are needed to parametrize the type of the closures (because of the result parameter)
				final FailureParserClosure<Token, ?> closure = (FailureParserClosure<Token, ?>) _parent.asSome().getValue();
				return closure.isValidFailure();
			} else {
				return true;
			}
		}
	}
}
