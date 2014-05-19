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
import com.trazere.parser.ParserException;
import com.trazere.parser.ParserHandler;
import com.trazere.parser.ParserState;

public abstract class Sequence2Parser<Token, SubResult1, SubResult2, Result>
extends BaseParser<Token, Result> {
	protected final Parser<Token, ? extends SubResult1> _subParser1;
	protected final Parser<Token, ? extends SubResult2> _subParser2;
	
	public Sequence2Parser(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final String description) {
		super(description);
		
		// Checks.
		assert null != subParser1;
		assert null != subParser2;
		
		// Initialization.
		_subParser1 = subParser1;
		_subParser2 = subParser2;
	}
	
	// Parser.
	
	@Override
	public void run(final ParserClosure<Token, Result> closure, final ParserState<Token> state)
	throws ParserException {
		// Part 1.
		state.parse(_subParser1, buildHandler1(closure), closure);
	}
	
	protected ParserHandler<Token, SubResult1> buildHandler1(final ParserClosure<Token, Result> closure) {
		return new ParserHandler<Token, SubResult1>() {
			@Override
			public void result(final SubResult1 subResult1, final ParserState<Token> state)
			throws ParserException {
				// Part 2.
				state.parse(_subParser2, buildHandler2(closure, subResult1), closure);
			}
		};
	}
	
	protected ParserHandler<Token, SubResult2> buildHandler2(final ParserClosure<Token, Result> closure, final SubResult1 subResult1) {
		return new ParserHandler<Token, SubResult2>() {
			@Override
			public void result(final SubResult2 subResult2, final ParserState<Token> state)
			throws ParserException {
				// Success.
				closure.success(combine(subResult1, subResult2), state);
			}
		};
	}
	
	protected abstract Result combine(final SubResult1 subResult1, final SubResult2 subResult2)
	throws ParserException;
}
