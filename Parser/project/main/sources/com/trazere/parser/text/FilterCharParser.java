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
package com.trazere.parser.text;

import com.trazere.core.lang.HashCode;
import com.trazere.core.lang.LangUtils;
import com.trazere.parser.BaseParser;
import com.trazere.parser.ParserClosure;
import com.trazere.parser.ParserContinuation;
import com.trazere.parser.ParserException;
import com.trazere.parser.ParserState;
import com.trazere.util.text.CharPredicate;

/**
 * DOCME
 */
public class FilterCharParser
extends BaseParser<Character, Character> {
	protected final CharPredicate<? extends ParserException> _filter;
	
	public FilterCharParser(final CharPredicate<? extends ParserException> filter, final String description) {
		super(description);
		
		// Checks.
		assert null != filter;
		
		// Initialization.
		_filter = filter;
	}
	
	// Parser.
	
	@Override
	public void run(final ParserClosure<Character, Character> closure, final ParserState<Character> state)
	throws ParserException {
		// Char.
		state.read(buildContinuation(closure));
	}
	
	protected ParserContinuation<Character> buildContinuation(final ParserClosure<Character, Character> closure) {
		return new ParserContinuation<Character>() {
			@Override
			public void token(final Character token, final ParserState<Character> state)
			throws ParserException {
				if (_filter.evaluate(token.charValue())) {
					// Success.
					closure.success(token, state);
				} else {
					// Failure.
					closure.failure(state);
				}
			}
			
			@Override
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
		result.append(_filter);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final FilterCharParser parser = (FilterCharParser) object;
			return LangUtils.safeEquals(_description, parser._description) && _filter.equals(parser._filter);
		} else {
			return false;
		}
	}
}
