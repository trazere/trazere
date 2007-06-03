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
package com.trazere.util.parser.core;

import com.trazere.util.parser.AbstractParser;
import com.trazere.util.parser.ParserClosure;
import com.trazere.util.parser.ParserContinuation;
import com.trazere.util.parser.ParserException;
import com.trazere.util.parser.ParserState;

/**
 * DOCME
 * 
 * @param <Token>
 * @param <Result>
 */
public class EOFParser<Token, Result>
extends AbstractParser<Token, Result> {
	public EOFParser(final String description) {
		super(description);
	}
	
	public void run(final ParserClosure<Token, Result> closure, final ParserState<Token> state)
	throws ParserException {
		state.push(buildContinuation(closure));
	}
	
	protected ParserContinuation<Token> buildContinuation(final ParserClosure<Token, Result> closure) {
		return new ParserContinuation<Token>() {
			public void parseToken(final Token token, final ParserState<Token> state)
			throws ParserException {
				// Failure.
				state.reportFailure(closure);
			}
			
			public void parseEOF(final ParserState<Token> state)
			throws ParserException {
				// Success.
				state.reportSuccess(closure, null);
			}
		};
	}
}
