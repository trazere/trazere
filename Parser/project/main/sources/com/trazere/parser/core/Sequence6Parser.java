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

public abstract class Sequence6Parser<Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult5, SubResult6, Result>
extends BaseParser<Token, Result> {
	protected final Parser<Token, ? extends SubResult1> _subParser1;
	protected final Parser<Token, ? extends SubResult2> _subParser2;
	protected final Parser<Token, ? extends SubResult3> _subParser3;
	protected final Parser<Token, ? extends SubResult4> _subParser4;
	protected final Parser<Token, ? extends SubResult5> _subParser5;
	protected final Parser<Token, ? extends SubResult6> _subParser6;
	
	public Sequence6Parser(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final Parser<Token, ? extends SubResult4> subParser4, final Parser<Token, ? extends SubResult5> subParser5, final Parser<Token, ? extends SubResult6> subParser6, final String description) {
		super(description);
		
		// Checks.
		assert null != subParser1;
		assert null != subParser2;
		assert null != subParser3;
		assert null != subParser4;
		assert null != subParser5;
		assert null != subParser6;
		
		// Initialization.
		_subParser1 = subParser1;
		_subParser2 = subParser2;
		_subParser3 = subParser3;
		_subParser4 = subParser4;
		_subParser5 = subParser5;
		_subParser6 = subParser6;
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
			// Part 4.
			state.parse(_subParser4, buildHandler4(closure, subResult1, subResult2, subResult3), closure);
		};
	}
	
	protected ParserHandler<Token, SubResult4> buildHandler4(final ParserClosure<Token, Result> closure, final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3) {
		return (final SubResult4 subResult4, final ParserState<Token> state) -> {
			// Part 5.
			state.parse(_subParser5, buildHandler5(closure, subResult1, subResult2, subResult3, subResult4), closure);
		};
	}
	
	protected ParserHandler<Token, SubResult5> buildHandler5(final ParserClosure<Token, Result> closure, final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4) {
		return (final SubResult5 subResult5, final ParserState<Token> state) -> {
			// Part 6.
			state.parse(_subParser6, buildHandler6(closure, subResult1, subResult2, subResult3, subResult4, subResult5), closure);
		};
	}
	
	protected ParserHandler<Token, SubResult6> buildHandler6(final ParserClosure<Token, Result> closure, final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4, final SubResult5 subResult5) {
		return (final SubResult6 subResult6, final ParserState<Token> state) -> {
			// Success.
			closure.success(combine(subResult1, subResult2, subResult3, subResult4, subResult5, subResult6), state);
		};
	}
	
	protected abstract Result combine(SubResult1 subResult1, SubResult2 subResult2, SubResult3 subResult3, SubResult4 subResult4, SubResult5 subResult5, SubResult6 subResult6);
	
	// Object.
	
	// @Override
	// public int hashCode() {
	// final HashCode result = new HashCode(this);
	// result.append(_description);
	// result.append(_subParser1);
	// result.append(_subParser2);
	// result.append(_subParser3);
	// result.append(_subParser4);
	// result.append(_subParser5);
	// result.append(_subParser6);
	// return result.get();
	// }
	//
	// @Override
	// public boolean equals(final Object object) {
	// if (this == object) {
	// return true;
	// } else if (null != object && getClass().equals(object.getClass())) {
	// final Combine6Parser<?, ?, ?, ?, ?, ?, ?, ?> parser = (Combine6Parser<?, ?, ?, ?, ?, ?, ?, ?>) object;
	// return LangUtils.equals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) &&
	// _subParser3.equals(parser._subParser3) && _subParser4.equals(parser._subParser4) && _subParser5.equals(parser._subParser5) &&
	// _subParser6.equals(parser._subParser6);
	// } else {
	// return false;
	// }
	// }
}
