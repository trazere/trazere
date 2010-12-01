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
import com.trazere.util.lang.HashCode;
import com.trazere.util.lang.LangUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DOCME
 * 
 * @param <Token>
 * @param <Result>
 */
public class Many1Parser<Token, Result>
extends AbstractParser<Token, List<Result>> {
	protected final Parser<Token, ? extends Result> _subParser;
	
	public Many1Parser(final Parser<Token, ? extends Result> subParser, final String description) {
		super(description);
		
		// Checks.
		assert null != subParser;
		
		// Initialization.
		_subParser = subParser;
	}
	
	// Parser.
	
	public void run(final ParserClosure<Token, List<Result>> closure, final ParserState<Token> state)
	throws ParserException {
		// One.
		state.parse(_subParser, buildMoreHandler(closure, new ArrayList<Result>()), closure);
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
				state.parse(_subParser, buildMoreHandler(closure, results), closure);
			}
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
			final Many1Parser<?, ?> parser = (Many1Parser<?, ?>) object;
			return LangUtils.equals(_description, parser._description) && _subParser.equals(parser._subParser);
		} else {
			return false;
		}
	}
}
