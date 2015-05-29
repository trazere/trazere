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
package com.trazere.parser.core;

import com.trazere.core.lang.HashCode;
import com.trazere.core.lang.ObjectUtils;
import com.trazere.parser.BaseParser;
import com.trazere.parser.Parser;
import com.trazere.parser.ParserClosure;
import com.trazere.parser.ParserHandler;
import com.trazere.parser.ParserState;
import java.util.Collections;
import java.util.List;

public class ChoiceParser<Token, Result>
extends BaseParser<Token, Result> {
	protected final List<? extends Parser<Token, ? extends Result>> _subParsers;
	
	public ChoiceParser(final List<? extends Parser<Token, ? extends Result>> subParsers, final String description) {
		super(description);
		
		// Checks.
		assert null != subParsers;
		
		// Initialization.
		_subParsers = Collections.unmodifiableList(subParsers);
	}
	
	// Parser.
	
	@Override
	public void run(final ParserClosure<Token, Result> closure, final ParserState<Token> state) {
		// Branches.
		final ParserHandler<Token, Result> handler = buildHandler(closure);
		for (final Parser<Token, ? extends Result> subParser : _subParsers) {
			state.parse(subParser, handler, closure);
		}
	}
	
	protected ParserHandler<Token, Result> buildHandler(final ParserClosure<Token, Result> closure) {
		return (final Result result, final ParserState<Token> state) -> {
			closure.success(result, state);
		};
	}
	
	// Object.
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_description);
		result.append(_subParsers);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final ChoiceParser<?, ?> parser = (ChoiceParser<?, ?>) object;
			return ObjectUtils.safeEquals(_description, parser._description) && _subParsers.equals(parser._subParsers);
		} else {
			return false;
		}
	}
}
