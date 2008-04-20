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

import com.trazere.util.parser.AbstractParser;
import com.trazere.util.parser.AbstractParserHandler;
import com.trazere.util.parser.Parser;
import com.trazere.util.parser.ParserClosure;
import com.trazere.util.parser.ParserException;
import com.trazere.util.parser.ParserHandler;
import com.trazere.util.parser.ParserState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DOCME
 * 
 * @param <Token>
 * @param <Result>
 */
public class Intersperse1Parser<Token, Result>
extends AbstractParser<Token, List<Result>> {
	protected final Parser<Token, ? extends Result> _valueParser;
	protected final Parser<Token, ?> _delimiterParser;
	
	public Intersperse1Parser(final Parser<Token, ? extends Result> valueParser, final Parser<Token, ?> delimiterParser, final String description) {
		super(description);
		
		// Checks.
		assert null != valueParser;
		assert null != delimiterParser;
		
		// Initialization.
		_valueParser = valueParser;
		_delimiterParser = delimiterParser;
	}
	
	public void run(final ParserClosure<Token, List<Result>> closure, final ParserState<Token> state)
	throws ParserException {
		// One.
		state.run(_valueParser, buildMoreHandler(closure, new ArrayList<Result>()));
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
				state.run(CoreParsers.second(_delimiterParser, _valueParser, null), buildMoreHandler(closure, results));
			}
		};
	}
}
