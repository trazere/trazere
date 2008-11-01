/*
 *  Copyright 2006-2008 Julien Dufour
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
package com.trazere.parser;

import java.util.ArrayList;
import java.util.Collection;

// TODO: make a sub class for isValidFailure()

/**
 * DOCME
 * 
 * @param <Token>
 * @param <Result>
 */
public abstract class ParserClosureImpl<Token, Result>
implements ParserClosure<Token, Result> {
	protected final Parser<Token, Result> _parser;
	protected final ParserPosition<Token> _position;
	protected final Collection<ParserHandler<Token, ? super Result>> _handlers = new ArrayList<ParserHandler<Token, ? super Result>>();
	protected final Collection<Result> _results = new ArrayList<Result>();
	
	public ParserClosureImpl(final Parser<Token, Result> parser, final ParserPosition<Token> position) {
		assert null != parser;
		assert null != position;
		
		// Initialization.
		_parser = parser;
		_position = position;
	}
	
	public Parser<Token, Result> getParser() {
		return _parser;
	}
	
	public ParserPosition<Token> getPosition() {
		return _position;
	}
	
	public void handle(final ParserHandler<Token, ? super Result> handler, final ParserState<Token> state)
	throws ParserException {
		assert null != handler;
		assert null != state;
		
		// Add the handler.
		_handlers.add(handler);
		
		// Call the handlers.
		// FIXME: proove that it is not possible to add a result to the receiver closure while calling the handlers or iterate over a copy of the results.
		for (final Result result : _results) {
			handler.result(result, state);
		}
	}
	
	public void success(final Result result, final ParserState<Token> state)
	throws ParserException {
		assert null != state;
		
		// Add the result.
		_results.add(result);
		
		// Call the handlers.
		// FIXME: proove that it is not possible to add a handler to the receiver closure while calling the handlers or iterate over a copy of the handler.
		for (final ParserHandler<Token, ? super Result> handler : _handlers) {
			handler.result(result, state);
		}
	}
	
	public abstract boolean isValidFailure();
}
