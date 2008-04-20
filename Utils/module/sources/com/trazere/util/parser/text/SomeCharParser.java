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
package com.trazere.util.parser.text;

import com.trazere.util.parser.AbstractParser;
import com.trazere.util.parser.AbstractParserContinuation;
import com.trazere.util.parser.ParserClosure;
import com.trazere.util.parser.ParserContinuation;
import com.trazere.util.parser.ParserException;
import com.trazere.util.parser.ParserState;
import com.trazere.util.text.CharFilter;

/**
 * DOCME
 */
public class SomeCharParser
extends AbstractParser<Character, Character> {
	protected final CharFilter _filter;
	
	public SomeCharParser(final CharFilter filter, final String description) {
		super(description);
		
		// Checks.
		assert null != filter;
		
		// Initialization.
		_filter = filter;
	}
	
	public void run(final ParserClosure<Character, Character> closure, final ParserState<Character> state)
	throws ParserException {
		// Char.
		state.push(buildContinuation(closure));
	}
	
	protected ParserContinuation<Character> buildContinuation(final ParserClosure<Character, Character> closure) {
		return new AbstractParserContinuation<Character>(closure) {
			public void parseToken(final Character token, final ParserState<Character> state)
			throws ParserException {
				if (_filter.filter(token.charValue())) {
					// Success.
					state.reportSuccess(closure, token);
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
