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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.trazere.util.Assert;
import com.trazere.util.parser.AbstractParserEngine.HandlerMap;

/**
 * DOCME
 * 
 * @param <Token>
 */
public abstract class AbstractParserState<Token>
implements ParserState<Token> {
	private static class ClosureMap<Token> {
		protected final Map<Parser<Token, ?>, ParserClosure<Token, ?>> _closures = new HashMap<Parser<Token, ?>, ParserClosure<Token, ?>>();
		
		public boolean containsKey(final Parser<Token, ?> parser) {
			return _closures.containsKey(parser);
		}
		
		public <Result> ParserClosure<Token, Result> get(final Parser<Token, Result> parser) {
			// HACK: work around the type system.
			@SuppressWarnings("unchecked")
			final ParserClosure<Token, Result> result = (ParserClosure<Token, Result>) _closures.get(parser);
			return result;
		}
		
		public <Result> void put(final Parser<Token, Result> parser, final ParserClosure<Token, Result> closure) {
			_closures.put(parser, closure);
		}
	}
	
	private static class CallMap<Token> {
		protected final Map<ParserClosure<Token, ?>, Collection<?>> _calls = new HashMap<ParserClosure<Token, ?>, Collection<?>>();
		
		protected <Result> Collection<Result> getCalls(final ParserClosure<Token, Result> closure) {
			// Look for the family.
			// HACK: work around the type system.
			@SuppressWarnings("unchecked")
			final Collection<Result> currentCalls = (Collection<Result>) _calls.get(closure);
			if (null != currentCalls) {
				return currentCalls;
			}
			
			// Create a new family.
			final Collection<Result> calls = new ArrayList<Result>();
			_calls.put(closure, calls);
			return calls;
		}
		
		public <Result> Collection<Result> get(final ParserClosure<Token, Result> closure) {
			// HACK: work around the type system.
			@SuppressWarnings("unchecked")
			final Collection<Result> calls = (Collection<Result>) _calls.get(closure);
			return null != calls ? Collections.unmodifiableCollection(calls) : Collections.<Result>emptyList();
		}
		
		public <Result> void add(final ParserClosure<Token, Result> closure, final Result result) {
			getCalls(closure).add(result);
		}
	}
	
	protected final int _position;
	protected final List<ParserContinuation<Token>> _continuations;
	protected final HandlerMap<Token> _handlers;
	protected final ClosureMap<Token> _closures = new ClosureMap<Token>();
	protected final CallMap<Token> _calls = new CallMap<Token>();
	
	public AbstractParserState(final int position, final List<ParserContinuation<Token>> continuations, final HandlerMap<Token> handlers) {
		// Checks.
		Assert.notNull(continuations);
		
		// Initialization.
		_position = position;
		_continuations = continuations;
		_handlers = handlers;
	}
	
	public int getPosition() {
		return _position;
	}
	
	public <Result> void run(final Parser<Token, Result> parser, final ParserHandler<Token, ? super Result> handler)
	throws ParserException {
		if (_closures.containsKey(parser)) {
			// Get the closure.
			final ParserClosure<Token, Result> closure = _closures.get(parser);
			
			// Add the handler.
			_handlers.add(closure, handler);
			
			// Call the handler.
			for (final Result result : _calls.get(closure)) {
				handler.result(result, this);
			}
		} else {
			// Build the closure.
			final ParserClosure<Token, Result> closure;
			closure = buildClosure(parser);
			_closures.put(parser, closure);
			
			// Add the handler.
			_handlers.add(closure, handler);
			
			// Run the parser.
			parser.run(closure, this);
		}
	}
	
	protected abstract <Result> ParserClosure<Token, Result> buildClosure(final Parser<Token, Result> parser)
	throws ParserException;
	
	public void push(final ParserContinuation<Token> continuation)
	throws ParserException {
		_continuations.add(continuation);
	}
	
	public <Result> void reportSuccess(final ParserClosure<Token, Result> closure, final Result result)
	throws ParserException {
		// Add the call.
		_calls.add(closure, result);
		
		// Call the handlers.
		// FIXME: proove that it is not possible to fill the same closure handling the result or iterate a copy of the handler list
		for (final ParserHandler<Token, ? super Result> handler : _handlers.get(closure)) {
			handler.result(result, this);
		}
	}
	
	public void reportFailure(final ParserClosure<Token, ?> closure) {
		// Nothing to do.
	}
}
