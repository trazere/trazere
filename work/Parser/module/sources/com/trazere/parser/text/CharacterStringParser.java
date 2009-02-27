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
import com.trazere.parser.Parser;
import com.trazere.parser.ParserClosure;
import com.trazere.parser.ParserException;
import com.trazere.parser.ParserHandler;
import com.trazere.parser.ParserState;

/**
 * DOCME
 * <p>
 * NOTE: the character parser must return at most one result per token !!
 */
public class CharacterStringParser
extends AbstractParser<Character, String> {
	protected final Parser<Character, Character> _characterParser;
	protected final boolean _empty;
	
	public CharacterStringParser(final Parser<Character, Character> characterParser, final boolean empty, final String description) {
		super(description);
		
		// Checks.
		assert null != characterParser;
		
		// Initialization.
		_characterParser = characterParser;
		_empty = empty;
	}
	
	public void run(final ParserClosure<Character, String> closure, final ParserState<Character> state)
	throws ParserException {
		// Zero.
		final StringBuilder result = new StringBuilder();
		if (_empty) {
			closure.success(result.toString(), state);
		}
		
		// More.
		state.parse(_characterParser, buildMoreHandler(closure, result), closure);
	}
	
	protected ParserHandler<Character, Character> buildMoreHandler(final ParserClosure<Character, String> closure, final StringBuilder result) {
		return new ParserHandler<Character, Character>() {
			private boolean _done = false;
			
			public void result(final Character character, final ParserState<Character> state)
			throws ParserException {
				// Check that this is the first result.
				if (!_done) {
					_done = true;
					
					// Accumulate.
					result.append(character.charValue());
					
					// Success.
					closure.success(result.toString(), state);
					
					// More.
					state.parse(_characterParser, buildMoreHandler(closure, result), closure);
				} else {
					throw new ParserException("At most one result is allowed per token for parser " + this);
				}
			}
		};
	}
}
