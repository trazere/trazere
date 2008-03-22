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
package com.trazere.util.parser.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.trazere.util.Assert;
import com.trazere.util.parser.AbstractParser;
import com.trazere.util.parser.AbstractParserHandler;
import com.trazere.util.parser.Parser;
import com.trazere.util.parser.ParserClosure;
import com.trazere.util.parser.ParserException;
import com.trazere.util.parser.ParserHandler;
import com.trazere.util.parser.ParserState;

/**
 * DOCME
 * 
 * @param <Token>
 * @param <Result>
 */
public class ManyParser<Token, Result>
extends AbstractParser<Token, List<Result>> {
	protected final Parser<Token, Result> _subParser;
	
	public ManyParser(final Parser<Token, Result> subParser, final String description) {
		super(description);
		
		// Checks.
		Assert.notNull(subParser);
		
		// Initialization.
		_subParser = subParser;
	}
	
	public void run(final ParserClosure<Token, List<Result>> closure, final ParserState<Token> state)
	throws ParserException {
		// Zero.
		final List<Result> results = new ArrayList<Result>();
		state.reportSuccess(closure, Collections.unmodifiableList(results));
		
		// More.
		state.run(_subParser, buildMoreHandler(closure, results));
	}
	
	protected ParserHandler<Token, Result> buildMoreHandler(final ParserClosure<Token, List<Result>> closure, final List<Result> previousResults) {
		return new AbstractParserHandler<Token, Result>(closure) {
			public void result(final Result result, final ParserState<Token> state)
			throws ParserException {
				// Wrap the result.
				final List<Result> results = new ArrayList<Result>(previousResults);
				results.add(result);
				state.reportSuccess(closure, Collections.unmodifiableList(results));
				
				// More.
				state.run(_subParser, buildMoreHandler(closure, results));
			}
		};
	}
}
