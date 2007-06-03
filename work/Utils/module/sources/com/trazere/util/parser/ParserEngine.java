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
package com.trazere.util.parser;

import java.util.ArrayList;
import java.util.List;

import com.trazere.util.Assert;

/**
 * DOCME
 */
public abstract class ParserEngine {
	protected <Token, Result> void parse(final Parser<Token, Result> parser, final ParserSource<Token> source, final int rootPosition, final ParserHandler<Token, ? super Result> rootHandler)
	throws ParserException {
		Assert.notNull(parser);
		Assert.notNull(source);
		Assert.notNull(rootHandler);

		// Parsing initialization.
		List<ParserContinuation<Token>> continuations = new ArrayList<ParserContinuation<Token>>();
		int position = rootPosition;
		final ParserState<Token> rootState = buildState(continuations, position);
		rootState.run(parser, rootHandler);

		// Parse the token.
		while (source.hasNext() && !continuations.isEmpty()) {
			final Token token = source.next();
			position += 1;

			final List<ParserContinuation<Token>> nextContinuations = new ArrayList<ParserContinuation<Token>>();
			final ParserState<Token> state = buildState(nextContinuations, position);
			for (final ParserContinuation<Token> continuation : continuations) {
				continuation.parseToken(token, state);
			}
			continuations = nextContinuations;
		}

		// Parse the EOF.
		if (!continuations.isEmpty()) {
			final List<ParserContinuation<Token>> extraContinuations = new ArrayList<ParserContinuation<Token>>();
			final ParserState<Token> state = buildState(extraContinuations, position);
			for (final ParserContinuation<Token> continuation : continuations) {
				continuation.parseEOF(state);
			}
		}
	}

	protected abstract <Token> ParserState<Token> buildState(final List<ParserContinuation<Token>> continuations, final int position)
	throws ParserException;
}
