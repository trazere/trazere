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
package com.trazere.parser.impl;

import com.trazere.core.util.Maybe;
import com.trazere.parser.Parser;
import com.trazere.parser.ParserClosure;
import com.trazere.parser.ParserContinuation;
import com.trazere.parser.ParserHandler;
import com.trazere.parser.ParserPosition;
import com.trazere.parser.ParserState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ParserStateImpl<Token>
implements ParserState<Token> {
	// FIXME: implement parser graph comparison function
	private static class ClosureMap<Token> {
		protected final Map<Parser<Token, ?>, ParserClosure<Token, ?>> _closures = new HashMap<>();
		
		public boolean containsKey(final Parser<Token, ?> parser) {
			return _closures.containsKey(parser);
		}
		
		@SuppressWarnings("unchecked")
		public <Result> ParserClosureImpl<Token, Result> get(final Parser<Token, Result> parser) {
			return (ParserClosureImpl<Token, Result>) _closures.get(parser);
		}
		
		public <Result> void put(final Parser<Token, Result> parser, final ParserClosureImpl<Token, Result> closure) {
			_closures.put(parser, closure);
		}
	}
	
	protected final ParserPosition<Token> _position;
	protected final ClosureMap<Token> _closures = new ClosureMap<>();
	protected final List<ParserContinuation<Token>> _continuations = new ArrayList<>();
	
	public ParserStateImpl(final ParserPosition<Token> position) {
		assert null != position;
		
		// Initialization.
		_position = position;
	}
	
	@Override
	public ParserPosition<Token> getPosition() {
		return _position;
	}
	
	@Override
	public <Result> void parse(final Parser<Token, Result> parser, final ParserHandler<Token, ? super Result> handler, final ParserClosure<Token, ?> parent) {
		assert null != parent;
		
		parse(parser, handler, Maybe.<ParserClosure<Token, ?>>some(parent));
	}
	
	public <Result> void parse(final Parser<Token, Result> parser, final ParserHandler<Token, ? super Result> handler, final Maybe<ParserClosure<Token, ?>> parent) {
		assert null != parser;
		assert null != handler;
		assert null != parent;
		
		if (_closures.containsKey(parser)) {
			// Get the closure.
			final ParserClosureImpl<Token, Result> closure = _closures.get(parser);
			
			// Add the handler.
			closure.handle(handler, this);
		} else {
			// Build the closure.
			final ParserClosureImpl<Token, Result> closure = buildClosure(parser, parent);
			_closures.put(parser, closure);
			
			// Add the handler.
			closure.handle(handler, this);
			
			// Run the parser.
			parser.run(closure, this);
		}
	}
	
	protected abstract <Result> ParserClosureImpl<Token, Result> buildClosure(Parser<Token, Result> parser, Maybe<ParserClosure<Token, ?>> parent);
	
	@Override
	public void read(final ParserContinuation<Token> continuation) {
		assert null != continuation;
		
		// Add the continuation.
		_continuations.add(continuation);
	}
	
	public List<ParserContinuation<Token>> getContinuations() {
		return Collections.unmodifiableList(_continuations);
	}
}
