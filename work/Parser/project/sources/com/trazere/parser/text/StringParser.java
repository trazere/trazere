/*
 *  Copyright 2006-2012 Julien Dufour
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

import com.trazere.parser.BaseParser;
import com.trazere.parser.ParserClosure;
import com.trazere.parser.ParserContinuation;
import com.trazere.parser.ParserException;
import com.trazere.parser.ParserState;
import com.trazere.util.lang.HashCode;
import com.trazere.util.lang.LangUtils;

/**
 * DOCME
 */
public class StringParser
extends BaseParser<Character, String> {
	protected final String _string;
	protected final boolean _caseSentitive;
	
	public StringParser(final String string, final String description) {
		this(string, true, description);
	}
	
	public StringParser(final String string, final boolean caseSensitive, final String description) {
		super(description);
		
		// Checks.
		assert null != string;
		
		// Initialization.
		_string = string;
		_caseSentitive = caseSensitive;
	}
	
	// Parser.
	
	public void run(final ParserClosure<Character, String> closure, final ParserState<Character> state)
	throws ParserException {
		// Start.
		run(closure, 0, state);
	}
	
	protected void run(final ParserClosure<Character, String> closure, final int index, final ParserState<Character> state)
	throws ParserException {
		if (index < _string.length()) {
			// Continue.
			state.read(buildContinuation(closure, index));
		} else {
			// Success.
			closure.success(_string, state);
		}
	}
	
	protected ParserContinuation<Character> buildContinuation(final ParserClosure<Character, String> closure, final int index) {
		return new ParserContinuation<Character>() {
			public void token(final Character token, final ParserState<Character> state)
			throws ParserException {
				// Get the next char.
				final char stringChar;
				final char tokenChar;
				if (_caseSentitive) {
					stringChar = _string.charAt(index);
					tokenChar = token.charValue();
				} else {
					stringChar = Character.toLowerCase(_string.charAt(index));
					tokenChar = Character.toLowerCase(token.charValue());
				}
				
				// Compare the next char.
				if (stringChar == tokenChar) {
					// Continue.
					run(closure, index + 1, state);
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
	
	// Object.
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_description);
		result.append(_string);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final StringParser parser = (StringParser) object;
			return LangUtils.equals(_description, parser._description) && _string.equals(parser._string);
		} else {
			return false;
		}
	}
}
