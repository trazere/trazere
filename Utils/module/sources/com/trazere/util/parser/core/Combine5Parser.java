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
 * @param <SubResult1>
 * @param <SubResult2>
 * @param <SubResult3>
 * @param <SubResult4>
 * @param <SubResult5>
 * @param <Result>
 */
public abstract class Combine5Parser<Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult5, Result>
extends AbstractParser<Token, Result> {
	protected final Parser<Token, ? extends SubResult1> _subParser1;
	protected final Parser<Token, ? extends SubResult2> _subParser2;
	protected final Parser<Token, ? extends SubResult3> _subParser3;
	protected final Parser<Token, ? extends SubResult4> _subParser4;
	protected final Parser<Token, ? extends SubResult5> _subParser5;
	
	public Combine5Parser(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final Parser<Token, ? extends SubResult4> subParser4, final Parser<Token, ? extends SubResult5> subParser5, final String description) {
		super(description);
		
		// Checks.
		Assert.notNull(subParser1);
		Assert.notNull(subParser2);
		Assert.notNull(subParser3);
		Assert.notNull(subParser4);
		Assert.notNull(subParser5);
		
		// Initialization.
		_subParser1 = subParser1;
		_subParser2 = subParser2;
		_subParser3 = subParser3;
		_subParser4 = subParser4;
		_subParser5 = subParser5;
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
				// Part 3.
				state.run(_subParser3, buildHandler3(closure, subResult1, subResult2));
			}
		};
	}
	
	protected ParserHandler<Token, SubResult3> buildHandler3(final ParserClosure<Token, Result> closure, final SubResult1 subResult1, final SubResult2 subResult2) {
		return new AbstractParserHandler<Token, SubResult3>(closure) {
			public void result(final SubResult3 subResult3, final ParserState<Token> state)
			throws ParserException {
				// Part 4.
				state.run(_subParser4, buildHandler4(closure, subResult1, subResult2, subResult3));
			}
		};
	}
	
	protected ParserHandler<Token, SubResult4> buildHandler4(final ParserClosure<Token, Result> closure, final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3) {
		return new AbstractParserHandler<Token, SubResult4>(closure) {
			public void result(final SubResult4 subResult4, final ParserState<Token> state)
			throws ParserException {
				// Part 5.
				state.run(_subParser5, buildHandler5(closure, subResult1, subResult2, subResult3, subResult4));
			}
		};
	}
	
	protected ParserHandler<Token, SubResult5> buildHandler5(final ParserClosure<Token, Result> closure, final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4) {
		return new AbstractParserHandler<Token, SubResult5>(closure) {
			public void result(final SubResult5 subResult5, final ParserState<Token> state)
			throws ParserException {
				// Success.
				state.reportSuccess(closure, combine(subResult1, subResult2, subResult3, subResult4, subResult5));
			}
		};
	}
	
	protected abstract Result combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4, final SubResult5 subResult5)
	throws ParserException;
}
