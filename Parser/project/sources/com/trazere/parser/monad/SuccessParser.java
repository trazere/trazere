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
package com.trazere.parser.monad;

import com.trazere.parser.AbstractParser;
import com.trazere.parser.ParserClosure;
import com.trazere.parser.ParserException;
import com.trazere.parser.ParserState;
import com.trazere.util.lang.HashCode;
import com.trazere.util.lang.LangUtils;

/**
 * DOCME
 * 
 * @param <Token>
 * @param <Result>
 */
public class SuccessParser<Token, Result>
extends AbstractParser<Token, Result> {
	protected final Result _result;
	
	public SuccessParser(final Result result, final String description) {
		super(description);
		
		// Initialization.
		_result = result;
	}
	
	// Parser.
	
	public void run(final ParserClosure<Token, Result> closure, final ParserState<Token> state)
	throws ParserException {
		// Success.
		closure.success(_result, state);
	}
	
	// Object.
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_description);
		result.append(_result);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final SuccessParser<?, ?> parser = (SuccessParser<?, ?>) object;
			return LangUtils.equals(_description, parser._description) && _result.equals(parser._result);
		} else {
			return false;
		}
	}
}