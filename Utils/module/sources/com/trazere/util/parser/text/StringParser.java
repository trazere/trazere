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
package com.trazere.util.parser.text;

import com.trazere.util.Assert;
import com.trazere.util.parser.AbstractParser;
import com.trazere.util.parser.AbstractParserContinuation;
import com.trazere.util.parser.ParserClosure;
import com.trazere.util.parser.ParserContinuation;
import com.trazere.util.parser.ParserException;
import com.trazere.util.parser.ParserState;

/**
 * DOCME
 */
public class StringParser
extends AbstractParser<Character, String> {
	protected final String _string;
	
	public StringParser(final String string, final String description) {
		super(description);
		
		// Checks.
		Assert.notNull(string);
		
		// Initialization.
		_string = string;
	}
	
	public void run(final ParserClosure<Character, String> closure, final ParserState<Character> state)
	throws ParserException {
		// Start.
		run(closure, 0, state);
	}
	
	protected void run(final ParserClosure<Character, String> closure, final int index, final ParserState<Character> state)
	throws ParserException {
		if (index < _string.length()) {
			// Continue.
			state.push(buildContinuation(closure, index));
		} else {
			// Success.
			state.reportSuccess(closure, _string);
		}
	}
	
	protected ParserContinuation<Character> buildContinuation(final ParserClosure<Character, String> closure, final int index) {
		return new AbstractParserContinuation<Character>(closure) {
			public void parseToken(final Character token, final ParserState<Character> state)
			throws ParserException {
				if (_string.charAt(index) == token.charValue()) {
					// Continue.
					run(closure, index + 1, state);
				} else {
					// Failure.
					state.reportFailure(closure);
				}
			}
			
			public void parseEOF(final ParserState<Character> state)
			throws ParserException {
				// Failure.
				state.reportFailure(closure);
			}
		};
	}
}
