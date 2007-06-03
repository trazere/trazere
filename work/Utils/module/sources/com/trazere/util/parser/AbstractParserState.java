/*
 *  Copyright 2006 Julien Dufour
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
package com.trazere.util.parser;

import java.util.HashMap;
import java.util.Map;

/**
 * DOCME
 * 
 * @param <Token>
 */
public abstract class AbstractParserState<Token>
implements ParserState<Token> {
	protected final Map<Parser<Token, ?>, AbstractParserClosure<Token, ?>> _closures = new HashMap<Parser<Token, ?>, AbstractParserClosure<Token, ?>>();
	
	public <Result> void run(final Parser<Token, Result> parser, final ParserHandler<Token, ? super Result> handler)
	throws ParserException {
		if (_closures.containsKey(parser)) {
			// Get the closure.
			// HACK: work around the type system.
			@SuppressWarnings("unchecked")
			final AbstractParserClosure<Token, Result> closure = (AbstractParserClosure<Token, Result>) _closures.get(parser);
			
			// Add the handler.
			closure.addHandler(handler);
		} else {
			// Build the closure.
			final AbstractParserClosure<Token, Result> closure;
			closure = buildClosure(parser);
			_closures.put(parser, closure);
			
			// Add the handler.
			closure.addHandler(handler);
			
			// Run the parser.
			parser.run(closure, this);
		}
	}
	
	protected abstract <Result> AbstractParserClosure<Token, Result> buildClosure(final Parser<Token, Result> parser)
	throws ParserException;
}
