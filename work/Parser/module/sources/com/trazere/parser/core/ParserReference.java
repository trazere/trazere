/*
 *  Copyright 2006-2009 Julien Dufour
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

import com.trazere.parser.Parser;
import com.trazere.parser.ParserClosure;
import com.trazere.parser.ParserException;
import com.trazere.parser.ParserState;
import com.trazere.util.lang.ref.MutableReference;

/**
 * DOCME
 * 
 * @param <Token>
 * @param <Result>
 */
public class ParserReference<Token, Result>
implements Parser<Token, Result> {
	protected MutableReference<Parser<Token, Result>> _parser = new MutableReference<Parser<Token, Result>>();
	
	public void set(final Parser<Token, Result> parser) {
		assert null != parser;
		
		// Set.
		_parser.set(parser);
	}
	
	protected Parser<Token, Result> get() {
		return _parser.get();
	}
	
	public String getDescription() {
		return get().getDescription();
	}
	
	public void run(final ParserClosure<Token, Result> closure, final ParserState<Token> state)
	throws ParserException {
		get().run(closure, state);
	}
}
