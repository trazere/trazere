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

import com.trazere.parser.BaseParser;
import com.trazere.parser.Parser;
import com.trazere.parser.ParserClosure;
import com.trazere.parser.ParserHandler;
import com.trazere.parser.ParserState;

public abstract class FoldParser<Token, Value, Result>
extends BaseParser<Token, Result> {
	protected final Parser<Token, ? extends Value> _valueParser;
	protected final int _min;
	protected final int _max;
	protected final Result _initialResult;
	
	public FoldParser(final Parser<Token, ? extends Value> valueParser, final Result initialValue, final String description) {
		this(valueParser, 0, Integer.MAX_VALUE, initialValue, description);
	}
	
	public FoldParser(final Parser<Token, ? extends Value> valueParser, final int min, final int max, final Result initialResult, final String description) {
		super(description);
		
		// Checks.
		assert null != valueParser;
		
		// Initialization.
		_valueParser = valueParser;
		_min = min;
		_max = max;
		_initialResult = initialResult;
	}
	
	// Parser.
	
	@Override
	public void run(final ParserClosure<Token, Result> closure, final ParserState<Token> state) {
		run(closure, 0, _initialResult, state);
	}
	
	private void run(final ParserClosure<Token, Result> closure, final int count, final Result result, final ParserState<Token> state) {
		// Success.
		if (count >= _min) {
			closure.success(result, state);
		}
		
		// More.
		if (count < _max) {
			state.parse(_valueParser, buildMoreHandler(closure, count, result), closure);
		}
	}
	
	protected ParserHandler<Token, Value> buildMoreHandler(final ParserClosure<Token, Result> closure, final int previousCount, final Result previousResult) {
		return (final Value value, final ParserState<Token> state) -> {
			// Fold the result.
			final Result result = fold(previousResult, value);
			
			// Continue.
			run(closure, previousCount + 1, result, state);
		};
	}
	
	protected abstract Result fold(Result previousResult, Value value);
}
