/*
 *  Copyright 2006-2012 Julien Dufour
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

/**
 * DOCME
 * 
 * @param <Token>
 * @param <SubResult1>
 * @param <SubResult2>
 * @param <SubResult3>
 * @param <SubResult4>
 * @param <SubResult5>
 * @param <SubResult6>
 * @param <SubResult7>
 * @param <SubResult8>
 * @param <Result>
 */
public abstract class Sequence8Parser<Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult5, SubResult6, SubResult7, SubResult8, Result>
extends BaseParser<Token, Result> {
	protected final Parser<Token, ? extends SubResult1> _subParser1;
	protected final Parser<Token, ? extends SubResult2> _subParser2;
	protected final Parser<Token, ? extends SubResult3> _subParser3;
	protected final Parser<Token, ? extends SubResult4> _subParser4;
	protected final Parser<Token, ? extends SubResult5> _subParser5;
	protected final Parser<Token, ? extends SubResult6> _subParser6;
	protected final Parser<Token, ? extends SubResult7> _subParser7;
	protected final Parser<Token, ? extends SubResult8> _subParser8;
	
	public Sequence8Parser(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final Parser<Token, ? extends SubResult4> subParser4, final Parser<Token, ? extends SubResult5> subParser5, final Parser<Token, ? extends SubResult6> subParser6, final Parser<Token, ? extends SubResult7> subParser7, final Parser<Token, ? extends SubResult8> subParser8, final String description) {
		super(description);
		
		// Checks.
		assert null != subParser1;
		assert null != subParser2;
		assert null != subParser3;
		assert null != subParser4;
		assert null != subParser5;
		assert null != subParser6;
		assert null != subParser7;
		assert null != subParser8;
		
		// Initialization.
		_subParser1 = subParser1;
		_subParser2 = subParser2;
		_subParser3 = subParser3;
		_subParser4 = subParser4;
		_subParser5 = subParser5;
		_subParser6 = subParser6;
		_subParser7 = subParser7;
		_subParser8 = subParser8;
	}
	
	// Parser.
	
	public void run(final ParserClosure<Token, Result> closure, final ParserState<Token> state)
	throws ParserException {
		// Part 1.
		state.parse(_subParser1, buildHandler1(closure), closure);
	}
	
	protected ParserHandler<Token, SubResult1> buildHandler1(final ParserClosure<Token, Result> closure) {
		return new ParserHandler<Token, SubResult1>() {
			public void result(final SubResult1 subResult1, final ParserState<Token> state)
			throws ParserException {
				// Part 2.
				state.parse(_subParser2, buildHandler2(closure, subResult1), closure);
			}
		};
	}
	
	protected ParserHandler<Token, SubResult2> buildHandler2(final ParserClosure<Token, Result> closure, final SubResult1 subResult1) {
		return new ParserHandler<Token, SubResult2>() {
			public void result(final SubResult2 subResult2, final ParserState<Token> state)
			throws ParserException {
				// Part 3.
				state.parse(_subParser3, buildHandler3(closure, subResult1, subResult2), closure);
			}
		};
	}
	
	protected ParserHandler<Token, SubResult3> buildHandler3(final ParserClosure<Token, Result> closure, final SubResult1 subResult1, final SubResult2 subResult2) {
		return new ParserHandler<Token, SubResult3>() {
			public void result(final SubResult3 subResult3, final ParserState<Token> state)
			throws ParserException {
				// Part 4.
				state.parse(_subParser4, buildHandler4(closure, subResult1, subResult2, subResult3), closure);
			}
		};
	}
	
	protected ParserHandler<Token, SubResult4> buildHandler4(final ParserClosure<Token, Result> closure, final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3) {
		return new ParserHandler<Token, SubResult4>() {
			public void result(final SubResult4 subResult4, final ParserState<Token> state)
			throws ParserException {
				// Part 5.
				state.parse(_subParser5, buildHandler5(closure, subResult1, subResult2, subResult3, subResult4), closure);
			}
		};
	}
	
	protected ParserHandler<Token, SubResult5> buildHandler5(final ParserClosure<Token, Result> closure, final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4) {
		return new ParserHandler<Token, SubResult5>() {
			public void result(final SubResult5 subResult5, final ParserState<Token> state)
			throws ParserException {
				// Part 6.
				state.parse(_subParser6, buildHandler6(closure, subResult1, subResult2, subResult3, subResult4, subResult5), closure);
			}
		};
	}
	
	protected ParserHandler<Token, SubResult6> buildHandler6(final ParserClosure<Token, Result> closure, final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4, final SubResult5 subResult5) {
		return new ParserHandler<Token, SubResult6>() {
			public void result(final SubResult6 subResult6, final ParserState<Token> state)
			throws ParserException {
				// Part 7.
				state.parse(_subParser7, buildHandler7(closure, subResult1, subResult2, subResult3, subResult4, subResult5, subResult6), closure);
			}
		};
	}
	
	protected ParserHandler<Token, SubResult7> buildHandler7(final ParserClosure<Token, Result> closure, final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4, final SubResult5 subResult5, final SubResult6 subResult6) {
		return new ParserHandler<Token, SubResult7>() {
			public void result(final SubResult7 subResult7, final ParserState<Token> state)
			throws ParserException {
				// Part 8.
				state.parse(_subParser8, buildHandler8(closure, subResult1, subResult2, subResult3, subResult4, subResult5, subResult6, subResult7), closure);
			}
		};
	}
	
	protected ParserHandler<Token, SubResult8> buildHandler8(final ParserClosure<Token, Result> closure, final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4, final SubResult5 subResult5, final SubResult6 subResult6, final SubResult7 subResult7) {
		return new ParserHandler<Token, SubResult8>() {
			public void result(final SubResult8 subResult8, final ParserState<Token> state)
			throws ParserException {
				// Success.
				closure.success(combine(subResult1, subResult2, subResult3, subResult4, subResult5, subResult6, subResult7, subResult8), state);
			}
		};
	}
	
	protected abstract Result combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4, final SubResult5 subResult5, final SubResult6 subResult6, final SubResult7 subResult7, final SubResult8 subResult8)
	throws ParserException;
	
	// Object.
	
	//	@Override
	//	public int hashCode() {
	//		final HashCode result = new HashCode(this);
	//		result.append(_description);
	//		result.append(_subParser1);
	//		result.append(_subParser2);
	//		result.append(_subParser3);
	//		result.append(_subParser4);
	//		result.append(_subParser5);
	//		result.append(_subParser6);
	//		result.append(_subParser7);
	//		result.append(_subParser8);
	//		return result.get();
	//	}
	//	
	//	@Override
	//	public boolean equals(final Object object) {
	//		if (this == object) {
	//			return true;
	//		} else if (null != object && getClass().equals(object.getClass())) {
	//			final Combine8Parser<?, ?, ?, ?, ?, ?, ?, ?, ?, ?> parser = (Combine8Parser<?, ?, ?, ?, ?, ?, ?, ?, ?, ?>) object;
	//			return LangUtils.equals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3) && _subParser4.equals(parser._subParser4) && _subParser5.equals(parser._subParser5) && _subParser6.equals(parser._subParser6) && _subParser7.equals(parser._subParser7) && _subParser8.equals(parser._subParser8);
	//		} else {
	//			return false;
	//		}
	//	}
}
