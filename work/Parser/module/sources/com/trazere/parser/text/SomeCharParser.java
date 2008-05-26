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
package com.trazere.parser.text;

import com.trazere.parser.AbstractParser;
import com.trazere.parser.ParserClosure;
import com.trazere.parser.ParserContinuation;
import com.trazere.parser.ParserException;
import com.trazere.parser.ParserState;
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
		state.read(buildContinuation(closure));
	}
	
	protected ParserContinuation<Character> buildContinuation(final ParserClosure<Character, Character> closure) {
		return new ParserContinuation<Character>() {
			public void token(final Character token, final ParserState<Character> state)
			throws ParserException {
				if (_filter.filter(token.charValue())) {
					// Success.
					closure.success(token, state);
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
}
