/*
 *  Copyright 2006-2015 Julien Dufour
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
import com.trazere.core.lang.ObjectUtils;
import com.trazere.core.text.CharPredicate;
import com.trazere.parser.BaseParser;
import com.trazere.parser.ParserClosure;
import com.trazere.parser.ParserContinuation;
import com.trazere.parser.ParserState;

/**
 * DOCME
 */
public class EscapedStringParser
extends BaseParser<Character, String> {
	protected final CharPredicate _filter;
	protected final CharPredicate _escapeFilter;
	protected final boolean _empty;
	
	public EscapedStringParser(final CharPredicate filter, final CharPredicate escapeFilter, final boolean empty, final String description) {
		super(description);
		
		// Checks.
		assert null != filter;
		assert null != escapeFilter;
		
		// Initialization.
		_filter = filter;
		_escapeFilter = escapeFilter;
		_empty = empty;
	}
	
	// Parser.
	
	@Override
	public void run(final ParserClosure<Character, String> closure, final ParserState<Character> state) {
		// Zero.
		final StringBuilder result = new StringBuilder();
		if (_empty) {
			closure.success(result.toString(), state);
		}
		
		// More.
		state.read(buildContinuation(closure, result));
	}
	
	protected ParserContinuation<Character> buildContinuation(final ParserClosure<Character, String> closure, final StringBuilder builder) {
		return new ParserContinuation<Character>() {
			@Override
			public void token(final Character token, final ParserState<Character> state) {
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
			
			@Override
			public void eof(final ParserState<Character> state) {
				// Failure.
				closure.failure(state);
			}
		};
	}
	
	protected ParserContinuation<Character> buildEscapeContinuation(final ParserClosure<Character, String> closure, final StringBuilder builder) {
		return new ParserContinuation<Character>() {
			@Override
			public void token(final Character token, final ParserState<Character> state) {
				// Accumulate the char.
				builder.append(token.charValue());
				
				// Success.
				closure.success(builder.toString(), state);
				
				// More.
				state.read(buildContinuation(closure, builder));
			}
			
			@Override
			public void eof(final ParserState<Character> state) {
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
		result.append(_escapeFilter);
		result.append(_empty);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final EscapedStringParser parser = (EscapedStringParser) object;
			return ObjectUtils.safeEquals(_description, parser._description) && _filter.equals(parser._filter) && _escapeFilter.equals(parser._escapeFilter) && _empty == parser._empty;
		} else {
			return false;
		}
	}
}
