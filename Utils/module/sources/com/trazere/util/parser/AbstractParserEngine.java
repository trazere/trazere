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
package com.trazere.util.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.trazere.util.Assert;
import com.trazere.util.collection.CollectionUtils;

/**
 * DOCME
 */
public abstract class AbstractParserEngine
implements ParserEngine {
	public static class HandlerMap<Token> {
		protected final Map<ParserClosure<Token, ?>, List<? extends ParserHandler<Token, ?>>> _handlers;
		
		public HandlerMap() {
			this(new HashMap<ParserClosure<Token, ?>, List<? extends ParserHandler<Token, ?>>>());
		}
		
		private HandlerMap(final Map<ParserClosure<Token, ?>, List<? extends ParserHandler<Token, ?>>> handlers) {
			_handlers = handlers;
		}
		
		protected <Result> Collection<ParserHandler<Token, ? super Result>> getHandlers(final ParserClosure<Token, Result> closure) {
			// Look for the family.
			// HACK: work around the type system.
			@SuppressWarnings("unchecked")
			final List<ParserHandler<Token, ? super Result>> currentHandlers = (List<ParserHandler<Token, ? super Result>>) _handlers.get(closure);
			if (null != currentHandlers) {
				return currentHandlers;
			}
			
			// Create a new family.
			final List<ParserHandler<Token, ? super Result>> handlers = new ArrayList<ParserHandler<Token, ? super Result>>();
			_handlers.put(closure, handlers);
			return handlers;
		}
		
		public <Result> List<ParserHandler<Token, ? super Result>> get(final ParserClosure<Token, Result> closure) {
			// HACK: work around the type system.
			@SuppressWarnings("unchecked")
			final List<ParserHandler<Token, ? super Result>> handlers = (List<ParserHandler<Token, ? super Result>>) _handlers.get(closure);
			return null != handlers ? Collections.unmodifiableList(handlers) : Collections.<ParserHandler<Token, ? super Result>>emptyList();
		}
		
		public int size() {
			return _handlers.size();
		}
		
		public <Result> void add(final ParserClosure<Token, Result> closure, final ParserHandler<Token, ? super Result> handler) {
			getHandlers(closure).add(handler);
		}
		
		public HandlerMap<Token> subSet(final Set<ParserClosure<Token, ?>> closures) {
			return new HandlerMap<Token>(CollectionUtils.subMap(_handlers, closures));
		}
	}
	
	public <Token, Result> void parse(final Parser<Token, Result> parser, final ParserSource<Token> source, final int rootPosition, final ParserHandler<Token, ? super Result> rootHandler)
	throws ParserException {
		Assert.notNull(parser);
		Assert.notNull(source);
		Assert.notNull(rootHandler);
		
		// Parsing initialization.
		HandlerMap<Token> handlers = new HandlerMap<Token>();
		List<ParserContinuation<Token>> continuations = new ArrayList<ParserContinuation<Token>>();
		int position = rootPosition;
		final ParserState<Token> rootState = buildState(position, continuations, handlers);
		rootState.run(parser, rootHandler);
		//		System.out.println(handlers.size() + " live closures at " + position);
		
		// Parse the token.
		while (source.hasNext() && !continuations.isEmpty()) {
			final Token token = source.next();
			position += 1;
			
			final List<ParserContinuation<Token>> nextContinuations = new ArrayList<ParserContinuation<Token>>();
			final ParserState<Token> state = buildState(position, nextContinuations, handlers);
			for (final ParserContinuation<Token> continuation : continuations) {
				continuation.parseToken(token, state);
			}
			continuations = nextContinuations;
			handlers = handlers.subSet(computeLiveClosures(continuations, handlers));
			//			System.out.println(handlers.size() + " live closures at " + position);
		}
		
		// Parse the EOF.
		if (!continuations.isEmpty()) {
			final List<ParserContinuation<Token>> extraContinuations = new ArrayList<ParserContinuation<Token>>();
			final ParserState<Token> state = buildState(position, extraContinuations, handlers);
			for (final ParserContinuation<Token> continuation : continuations) {
				continuation.parseEOF(state);
			}
		}
	}
	
	protected abstract <Token> ParserState<Token> buildState(final int position, final List<ParserContinuation<Token>> continuations, final HandlerMap<Token> handlers)
	throws ParserException;
	
	protected <Token> Set<ParserClosure<Token, ?>> computeLiveClosures(final List<ParserContinuation<Token>> continuations, final HandlerMap<Token> handlers) {
		final Set<ParserClosure<Token, ?>> closures = new HashSet<ParserClosure<Token, ?>>();
		for (final ParserContinuation<Token> continuation : continuations) {
			for (final ParserClosure<Token, ?> continuationClosure : continuation.getClosures()) {
				computeLiveClosures(continuationClosure, handlers, closures);
			}
		}
		return closures;
	}
	
	protected <Token> void computeLiveClosures(final ParserClosure<Token, ?> closure, final HandlerMap<Token> handlers, final Set<ParserClosure<Token, ?>> closures) {
		if (closures.add(closure)) {
			for (final ParserHandler<Token, ?> handler : handlers.get(closure)) {
				for (final ParserClosure<Token, ?> handlerClosure : handler.getClosures()) {
					computeLiveClosures(handlerClosure, handlers, closures);
				}
			}
		}
	}
}
