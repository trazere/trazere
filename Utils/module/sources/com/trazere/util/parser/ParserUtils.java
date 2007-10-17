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
import java.util.List;

import com.trazere.util.LazyReference;
import com.trazere.util.ReferenceAlreadySetException;
import com.trazere.util.type.Maybe;

/**
 * DOCME
 */
public class ParserUtils {
	protected static final ParserEngine SUCCESS_ENGINE = new AbstractParserEngine() {
		@Override
		protected <Token> ParserState<Token> buildState(final int position, final List<ParserContinuation<Token>> continuations, final HandlerMap<Token> handlers)
		throws ParserException {
			return new AbstractParserState<Token>(position, continuations, handlers) {
				@Override
				protected <Result> AbstractParserClosure<Token, Result> buildClosure(final Parser<Token, Result> parser) {
					return new SuccessParserClosure<Token, Result>(parser, position);
				}
			};
		}
	};
	
	// protected final Set<ParserNode<Token, ?>> _successNodes;
	// protected final Set<ParserNode<Token, ?>> _failureNodes;
	//
	// public void reportSuccess(final ParserNode<Token, ?> node)
	// throws ParserException {
	// Assert.notNull(node);
	//
	// // Clean the nodes.
	// clearDescendentNodes(_successNodes, node);
	// clearDescendentNodes(_failureNodes, node);
	// _failureNodes.remove(node);
	//
	// // Note the node.
	// _successNodes.add(node);
	// }
	//
	// public void reportFailure(final ParserNode<Token, ?> node)
	// throws ParserException {
	// Assert.notNull(node);
	//
	// // Filter the failure.
	// if (!_successNodes.contains(node) && !CollectionUtils.intersects(node.getParentNodes(), _successNodes)) {
	// if (!_failureNodes.contains(node) && !CollectionUtils.intersects(node.getParentNodes(), _failureNodes)) {
	// // Note the node.
	// clearDescendentNodes(_failureNodes, node);
	// _failureNodes.add(node);
	// }
	// }
	// }
	//
	// protected static <Token> void clearDescendentNodes(final Collection<? extends ParserNode<Token, ?>> nodes, final ParserNode<Token, ?> parentNode) {
	// final Iterator<? extends ParserNode<Token, ?>> nodesIt = nodes.iterator();
	// while (nodesIt.hasNext()) {
	// final ParserNode<Token, ?> node = nodesIt.next();
	//
	// if (node.getParentNodes().contains(parentNode)) {
	// nodesIt.remove();
	// }
	// }
	// }
	
	public static <Token, Result> List<Result> parseSuccesses(final Parser<Token, Result> parser, final ParserSource<Token> source)
	throws ParserException {
		// Build the root handler.
		final List<Result> results = new ArrayList<Result>();
		final ParserHandler<Token, Result> handler = new AbstractParserHandler<Token, Result>() {
			public void result(final Result result, final ParserState<Token> state)
			throws ParserException {
				results.add(result);
			}
		};
		
		// Parse.
		SUCCESS_ENGINE.parse(parser, source, 0, handler);
		return results;
	}
	
	public static <Token, Result> Maybe<Result> parseLongestSuccess(final Parser<Token, Result> parser, final ParserSource<Token> source)
	throws ParserException {
		// Build the root handler.
		final LazyReference<Result> resultReference = new LazyReference<Result>();
		final ParserHandler<Token, Result> handler = new AbstractParserHandler<Token, Result>() {
			protected int _position = -1;
			
			public void result(final Result result, final ParserState<Token> state)
			throws ParserException {
				if (state.getPosition() > _position) {
					try {
						resultReference.set(result, true);
					} catch (final ReferenceAlreadySetException exception) {
						throw new RuntimeException("Internal error");
					}
					_position = state.getPosition();
				}
			}
		};
		
		// Parse.
		SUCCESS_ENGINE.parse(parser, source, 0, handler);
		return resultReference.get();
	}
	
	// Either<Result, Failure> parseLongestSuccessOrLongestFailure
	// Either<Result, List<Failure>> parseLongestSuccessOrFailures
	// Either<List<Result>, List<Failure>> parseSuccessesOrFailures
	
	// public Either<Result, ParserFailure> parse(final ParserSource<Token> source)
	// throws ParserException {
	//
	// }
	
	// public Either<Result, ParserFailure> parse(final Parserfinal ParserSource<Token> source)
	// throws ParserException;
	
	// public static <Token, Result, SubResult> ParserNode<Token, SubResult> buildSubNode(final ParserNode<Token, Result> node, final Parser<Token, SubResult>
	// subParser, final int position) {
	// Assert.notNull(node);
	//
	// // Build the node.
	// final Set<ParserNode<Token, ?>> parentNodes = new HashSet<ParserNode<Token, ?>>(node.getParentNodes());
	// parentNodes.add(node);
	//
	// return new SimpleParserNode<Token, SubResult>(subParser, position, parentNodes);
	// }
	
	private ParserUtils() {
		// Prevent instantiation.
	}
}
