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
package com.trazere.parser.core;

import com.trazere.parser.AbstractParser;
import com.trazere.parser.Parser;
import com.trazere.parser.ParserClosure;
import com.trazere.parser.ParserException;
import com.trazere.parser.ParserHandler;
import com.trazere.parser.ParserState;
import java.util.Collections;
import java.util.List;

/**
 * DOCME
 * 
 * @param <Token>
 * @param <Result>
 */
public class ChoiceParser<Token, Result>
extends AbstractParser<Token, Result> {
	protected final List<? extends Parser<Token, ? extends Result>> _subParsers;
	
	public ChoiceParser(final List<? extends Parser<Token, ? extends Result>> subParsers, final String description) {
		super(description);
		
		// Checks.
		assert null != subParsers;
		
		// Initialization.
		_subParsers = Collections.unmodifiableList(subParsers);
	}
	
	public void run(final ParserClosure<Token, Result> closure, final ParserState<Token> state)
	throws ParserException {
		// Branches.
		final ParserHandler<Token, Result> handler = buildHandler(closure);
		for (final Parser<Token, ? extends Result> subParser : _subParsers) {
			state.parse(subParser, handler, closure);
		}
	}
	
	protected ParserHandler<Token, Result> buildHandler(final ParserClosure<Token, Result> closure) {
		return new ParserHandler<Token, Result>() {
			public void result(final Result result, final ParserState<Token> state)
			throws ParserException {
				closure.success(result, state);
			}
		};
	}
}
