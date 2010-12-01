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

/**
 * DOCME
 * 
 * @param <Token>
 * @param <SubResult>
 * @param <Result>
 */
public abstract class FoldParser<Token, SubResult, Result>
extends AbstractParser<Token, Result> {
	protected final Parser<Token, ? extends SubResult> _subParser;
	protected final Result _initialValue;
	
	public FoldParser(final Parser<Token, ? extends SubResult> subParser, final Result initialValue, final String description) {
		super(description);
		
		// Checks.
		assert null != subParser;
		
		// Initialization.
		_subParser = subParser;
		_initialValue = initialValue;
	}
	
	// Parser.
	
	public void run(final ParserClosure<Token, Result> closure, final ParserState<Token> state)
	throws ParserException {
		// Zero.
		closure.success(_initialValue, state);
		
		// More.
		state.parse(_subParser, buildMoreHandler(closure, _initialValue), closure);
	}
	
	protected ParserHandler<Token, SubResult> buildMoreHandler(final ParserClosure<Token, Result> closure, final Result previousValue) {
		return new ParserHandler<Token, SubResult>() {
			public void result(final SubResult subResult, final ParserState<Token> state)
			throws ParserException {
				// Fold the result.
				final Result value = fold(previousValue, subResult);
				closure.success(value, state);
				
				// More.
				state.parse(_subParser, buildMoreHandler(closure, value), closure);
			}
		};
	}
	
	protected abstract Result fold(final Result previousValue, final SubResult subResult)
	throws ParserException;
	
	// Object.
	
	//	@Override
	//	public int hashCode() {
	//		final HashCode result = new HashCode(this);
	//		result.append(_description);
	//		result.append(_subParser);
	//		result.append(_initialValue);
	//		return result.get();
	//	}
	//	
	//	@Override
	//	public boolean equals(final Object object) {
	//		if (this == object) {
	//			return true;
	//		} else if (null != object && getClass().equals(object.getClass())) {
	//			final FoldParser<?, ?, ?> parser = (FoldParser<?, ?, ?>) object;
	//			return LangUtils.equals(_description, parser._description) && _subParser.equals(parser._subParser) && LangUtils.equals(_initialValue, parser._initialValue);
	//		} else {
	//			return false;
	//		}
	//	}
}
