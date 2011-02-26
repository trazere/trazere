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
 * @param <SubResult>
 * @param <Result>
 */
public abstract class SequenceParser<Token, SubResult, Result>
extends AbstractParser<Token, Result> {
	protected final List<Parser<Token, ? extends SubResult>> _subParsers;
	
	public SequenceParser(final List<Parser<Token, ? extends SubResult>> subParsers, final String description) {
		super(description);
		
		// Checks.
		assert null != subParsers;
		
		// Initialization.
		_subParsers = Collections.unmodifiableList(subParsers);
	}
	
	// Parser.
	
	public void run(final ParserClosure<Token, Result> closure, final ParserState<Token> state)
	throws ParserException {
		// Start.
		run(closure, new ArrayList<SubResult>(), state);
	}
	
	protected void run(final ParserClosure<Token, Result> closure, final List<SubResult> subResults, final ParserState<Token> state)
	throws ParserException {
		if (subResults.size() < _subParsers.size()) {
			// Continue.
			state.parse(_subParsers.get(subResults.size()), buildHandler(closure, subResults), closure);
		} else {
			// Success.
			closure.success(combine(subResults), state);
		}
	}
	
	protected ParserHandler<Token, SubResult> buildHandler(final ParserClosure<Token, Result> closure, final List<SubResult> previousSubResults) {
		return new ParserHandler<Token, SubResult>() {
			public void result(final SubResult subResult, final ParserState<Token> state)
			throws ParserException {
				// Accumulate the result.
				final List<SubResult> subResults = new ArrayList<SubResult>(previousSubResults);
				subResults.add(subResult);
				run(closure, subResults, state);
			}
		};
	}
	
	protected abstract Result combine(final List<SubResult> subResults)
	throws ParserException;
	
	// Object.
	
	//	@Override
	//	public int hashCode() {
	//		final HashCode result = new HashCode(this);
	//		result.append(_description);
	//		result.append(_subParsers);
	//		return result.get();
	//	}
	//	
	//	@Override
	//	public boolean equals(final Object object) {
	//		if (this == object) {
	//			return true;
	//		} else if (null != object && getClass().equals(object.getClass())) {
	//			final CombineParser<?, ?, ?> parser = (CombineParser<?, ?, ?>) object;
	//			return LangUtils.equals(_description, parser._description) && _subParsers.equals(parser._subParsers);
	//		} else {
	//			return false;
	//		}
	//	}
}
