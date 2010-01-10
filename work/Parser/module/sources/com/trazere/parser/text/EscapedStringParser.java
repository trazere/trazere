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
package com.trazere.parser.text;

import com.trazere.parser.AbstractParser;
import com.trazere.parser.ParserClosure;
import com.trazere.parser.ParserContinuation;
import com.trazere.parser.ParserException;
import com.trazere.parser.ParserState;
import com.trazere.util.text.CharPredicate;

/**
 * DOCME
 */
public class EscapedStringParser
extends AbstractParser<Character, String> {
	protected final CharPredicate<? extends ParserException> _filter;
	protected final CharPredicate<? extends ParserException> _escapeFilter;
	
	public EscapedStringParser(final CharPredicate<? extends ParserException> filter, final CharPredicate<? extends ParserException> escapeFilter, final String description) {
		super(description);
		
		// Checks.
		assert null != filter;
		assert null != escapeFilter;
		
		// Initialization.
		_filter = filter;
		_escapeFilter = escapeFilter;
	}
	
	public void run(final ParserClosure<Character, String> closure, final ParserState<Character> state)
	throws ParserException {
		// Zero.
		final StringBuilder result = new StringBuilder();
		closure.success(result.toString(), state);
		
		// More.
		state.read(buildContinuation(closure, result));
	}
	
	protected ParserContinuation<Character> buildContinuation(final ParserClosure<Character, String> closure, final StringBuilder builder) {
		return new ParserContinuation<Character>() {
			public void token(final Character token, final ParserState<Character> state)
			throws ParserException {
				final char c = token.charValue();
				if (_escapeFilter.evaluate(c)) {
					// Escape.
					state.read(buildEscapeContinuation(closure, builder));
				} else if (_filter.evaluate(c)) {
					// Accumulate the char.
					builder.append(c);
					
					// Success.
					closure.success(builder.toString(), state);
					
					// More.
					state.read(buildContinuation(closure, builder));
				} else {
					// Failure.
					closure.failure(state);
				}
			}
			
			public void eof(final ParserState<Character> state)
			throws ParserException {
				// Failure.
				closure.failure(state);
			}
		};
	}
	
	protected ParserContinuation<Character> buildEscapeContinuation(final ParserClosure<Character, String> closure, final StringBuilder builder) {
		return new ParserContinuation<Character>() {
			public void token(final Character token, final ParserState<Character> state)
			throws ParserException {
				// Accumulate the char.
				builder.append(token.charValue());
				
				// Success.
				closure.success(builder.toString(), state);
				
				// More.
				state.read(buildContinuation(closure, builder));
			}
			
			public void eof(final ParserState<Character> state)
			throws ParserException {
				// Failure.
				closure.failure(state);
			}
		};
	}
}
