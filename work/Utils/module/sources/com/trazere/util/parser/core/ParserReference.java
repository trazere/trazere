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
import com.trazere.util.parser.Parser;
import com.trazere.util.parser.ParserClosure;
import com.trazere.util.parser.ParserException;
import com.trazere.util.parser.ParserState;

/**
 * DOCME
 * 
 * @param <Token>
 * @param <Result>
 */
public class ParserReference<Token, Result>
implements Parser<Token, Result> {
	protected Parser<Token, Result> _parser = null;
	
	public void set(final Parser<Token, Result> parser) {
		Assert.notNull(parser);
		
		// Set.
		_parser = parser;
	}
	
	protected Parser<Token, Result> get() {
		if (null == _parser) {
			throw new IllegalStateException("Reference not set");
		}
		return _parser;
	}
	
	public String getDescription() {
		return get().getDescription();
	}
	
	public void run(final ParserClosure<Token, Result> closure, final ParserState<Token> state)
	throws ParserException {
		get().run(closure, state);
	}
}
