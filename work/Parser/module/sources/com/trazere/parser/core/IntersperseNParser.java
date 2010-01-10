/*
 *  Copyright 2006-2010 Julien Dufour
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DOCME
 * 
 * @param <Token>
 * @param <Result>
 */
public class IntersperseNParser<Token, Result>
extends AbstractParser<Token, List<Result>> {
	protected final Parser<Token, ? extends Result> _valueParser;
	protected final Parser<Token, ?> _delimiterParser;
	
	public IntersperseNParser(final Parser<Token, ? extends Result> valueParser, final Parser<Token, ?> delimiterParser, final String description) {
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
		state.parse(_valueParser, buildTwoHandler(closure, new ArrayList<Result>()), closure);
	}
	
	protected ParserHandler<Token, Result> buildTwoHandler(final ParserClosure<Token, List<Result>> closure, final List<Result> previousResults) {
		return new ParserHandler<Token, Result>() {
			public void result(final Result result, final ParserState<Token> state)
			throws ParserException {
				// Wrap the result.
				final List<Result> results = new ArrayList<Result>(previousResults);
				results.add(result);
				
				// Two.
				state.parse(CoreParsers.second(_delimiterParser, _valueParser, null), buildMoreHandler(closure, results), closure);
			}
		};
	}
	
	protected ParserHandler<Token, Result> buildMoreHandler(final ParserClosure<Token, List<Result>> closure, final List<Result> previousResults) {
		return new ParserHandler<Token, Result>() {
			public void result(final Result result, final ParserState<Token> state)
			throws ParserException {
				// Wrap the result.
				final List<Result> results = new ArrayList<Result>(previousResults);
				results.add(result);
				closure.success(Collections.unmodifiableList(results), state);
				
				// More.
				state.parse(CoreParsers.second(_delimiterParser, _valueParser, null), buildMoreHandler(closure, results), closure);
			}
		};
	}
}
