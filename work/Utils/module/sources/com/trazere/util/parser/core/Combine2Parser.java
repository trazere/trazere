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

/**
 * DOCME
 * 
 * @param <Token>
 * @param <SubResult1>
 * @param <SubResult2>
 * @param <Result>
 */
public abstract class Combine2Parser<Token, SubResult1, SubResult2, Result>
extends AbstractParser<Token, Result> {
	protected final Parser<Token, ? extends SubResult1> _subParser1;
	protected final Parser<Token, ? extends SubResult2> _subParser2;
	
	public Combine2Parser(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final String description) {
		super(description);
		
		// Checks.
		assert null != subParser1;
		assert null != subParser2;
		
		// Initialization.
		_subParser1 = subParser1;
		_subParser2 = subParser2;
	}
	
	public void run(final ParserClosure<Token, Result> closure, final ParserState<Token> state)
	throws ParserException {
		// Part 1.
		state.run(_subParser1, buildHandler1(closure));
	}
	
	protected ParserHandler<Token, SubResult1> buildHandler1(final ParserClosure<Token, Result> closure) {
		return new AbstractParserHandler<Token, SubResult1>(closure) {
			public void result(final SubResult1 subResult1, final ParserState<Token> state)
			throws ParserException {
				// Part 2.
				state.run(_subParser2, buildHandler2(closure, subResult1));
			}
		};
	}
	
	protected ParserHandler<Token, SubResult2> buildHandler2(final ParserClosure<Token, Result> closure, final SubResult1 subResult1) {
		return new AbstractParserHandler<Token, SubResult2>(closure) {
			public void result(final SubResult2 subResult2, final ParserState<Token> state)
			throws ParserException {
				// Success.
				state.reportSuccess(closure, combine(subResult1, subResult2));
			}
		};
	}
	
	protected abstract Result combine(final SubResult1 subResult1, final SubResult2 subResult2)
	throws ParserException;
}
