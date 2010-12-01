/*
 *  Copyright 2006-2010 Julien Dufour
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
package com.trazere.parser.core;

import com.trazere.parser.AbstractParser;
import com.trazere.parser.ParserClosure;
import com.trazere.parser.ParserContinuation;
import com.trazere.parser.ParserException;
import com.trazere.parser.ParserState;
import com.trazere.util.lang.HashCode;
import com.trazere.util.lang.LangUtils;

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
	
	// Parser.
	
	public void run(final ParserClosure<Token, Result> closure, final ParserState<Token> state)
	throws ParserException {
		state.read(buildContinuation(closure));
	}
	
	protected ParserContinuation<Token> buildContinuation(final ParserClosure<Token, Result> closure) {
		return new ParserContinuation<Token>() {
			public void token(final Token token, final ParserState<Token> state)
			throws ParserException {
				// Failure.
				closure.failure(state);
			}
			
			public void eof(final ParserState<Token> state)
			throws ParserException {
				// Success.
				closure.success(null, state);
			}
		};
	}
	
	// Object.
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_description);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final EOFParser<?, ?> parser = (EOFParser<?, ?>) object;
			return LangUtils.equals(_description, parser._description);
		} else {
			return false;
		}
	}
}
