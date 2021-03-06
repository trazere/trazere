/*
 *  Copyright 2006-2015 Julien Dufour
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

public abstract class Sequence3Parser<Token, SubResult1, SubResult2, SubResult3, Result>
extends BaseParser<Token, Result> {
	protected final Parser<Token, ? extends SubResult1> _subParser1;
	protected final Parser<Token, ? extends SubResult2> _subParser2;
	protected final Parser<Token, ? extends SubResult3> _subParser3;
	
	public Sequence3Parser(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final String description) {
		super(description);
		
		// Checks.
		assert null != subParser1;
		assert null != subParser2;
		assert null != subParser3;
		
		// Initialization.
		_subParser1 = subParser1;
		_subParser2 = subParser2;
		_subParser3 = subParser3;
	}
	
	// Parser.
	
	@Override
	public void run(final ParserClosure<Token, Result> closure, final ParserState<Token> state) {
		// Part 1.
		state.parse(_subParser1, buildHandler1(closure), closure);
	}
	
	protected ParserHandler<Token, SubResult1> buildHandler1(final ParserClosure<Token, Result> closure) {
		return (final SubResult1 subResult1, final ParserState<Token> state) -> {
			// Part 2.
			state.parse(_subParser2, buildHandler2(closure, subResult1), closure);
		};
	}
	
	protected ParserHandler<Token, SubResult2> buildHandler2(final ParserClosure<Token, Result> closure, final SubResult1 subResult1) {
		return (final SubResult2 subResult2, final ParserState<Token> state) -> {
			// Part 3.
			state.parse(_subParser3, buildHandler3(closure, subResult1, subResult2), closure);
		};
	}
	
	protected ParserHandler<Token, SubResult3> buildHandler3(final ParserClosure<Token, Result> closure, final SubResult1 subResult1, final SubResult2 subResult2) {
		return (final SubResult3 subResult3, final ParserState<Token> state) -> {
			// Success.
			closure.success(combine(subResult1, subResult2, subResult3), state);
		};
	}
	
	protected abstract Result combine(SubResult1 subResult1, SubResult2 subResult2, SubResult3 subResult3);
}
