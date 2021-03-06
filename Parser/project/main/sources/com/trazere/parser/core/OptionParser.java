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
package com.trazere.parser.core;

import com.trazere.core.lang.HashCode;
import com.trazere.core.lang.ObjectUtils;
import com.trazere.core.util.Maybe;
import com.trazere.parser.BaseParser;
import com.trazere.parser.Parser;
import com.trazere.parser.ParserClosure;
import com.trazere.parser.ParserHandler;
import com.trazere.parser.ParserState;

public class OptionParser<Token, Result>
extends BaseParser<Token, Maybe<Result>> {
	protected final Parser<Token, Result> _subParser;
	
	public OptionParser(final Parser<Token, Result> subParser, final String description) {
		super(description);
		
		// Checks.
		assert null != subParser;
		
		// Initialization.
		_subParser = subParser;
	}
	
	// Parser.
	
	@Override
	public void run(final ParserClosure<Token, Maybe<Result>> closure, final ParserState<Token> state) {
		// Zero.
		closure.success(Maybe.<Result>none(), state);
		
		// One.
		state.parse(_subParser, buildOneHandler(closure), closure);
	}
	
	protected ParserHandler<Token, Result> buildOneHandler(final ParserClosure<Token, Maybe<Result>> closure) {
		return (final Result result, final ParserState<Token> state) -> {
			// Wrap the result.
			closure.success(Maybe.some(result), state);
		};
	}
	
	// Object.
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_description);
		result.append(_subParser);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final OptionParser<?, ?> parser = (OptionParser<?, ?>) object;
			return ObjectUtils.safeEquals(_description, parser._description) && _subParser.equals(parser._subParser);
		} else {
			return false;
		}
	}
}
