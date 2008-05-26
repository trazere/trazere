package com.trazere.parser;

import com.trazere.util.type.Maybe;
import java.util.List;

public class SuccessParserEngine {
	public interface Handler<Token, Result> {
		public void success(final Result result, final ParserPosition<Token> position)
		throws ParserException;
	}
	
	public <Token, Result> void parse(final Parser<Token, Result> parser, final ParserSource<Token> source, final ParserPosition<Token> position, final Handler<Token, Result> handler)
	throws ParserException {
		assert null != parser;
		assert null != source;
		assert null != handler;
		
		// Initialization.
		final ParserStateImpl<Token> rootState = buildState(position);
		rootState.parse(parser, new ParserHandler<Token, Result>() {
			public void result(final Result result, final ParserState<Token> state)
			throws ParserException {
				handler.success(result, state.getPosition());
			}
		}, Maybe.<ParserClosure<Token, ?>>none());
		
		// Parse the tokens.
		ParserPosition<Token> currentPosition = position;
		List<ParserContinuation<Token>> continuations = rootState.getContinuations();
		while (source.hasNext() && !continuations.isEmpty()) {
			final Token token = source.next();
			currentPosition = currentPosition.next(token);
			
			final ParserStateImpl<Token> state = buildState(currentPosition);
			for (final ParserContinuation<Token> continuation : continuations) {
				continuation.token(token, state);
			}
			continuations = state.getContinuations();
		}
		
		// Parse the end of file.
		if (!continuations.isEmpty()) {
			final ParserState<Token> state = buildState(currentPosition);
			for (final ParserContinuation<Token> continuation : continuations) {
				continuation.eof(state);
			}
		}
	}
	
	protected <Token> ParserStateImpl<Token> buildState(final ParserPosition<Token> position) {
		return new ParserStateImpl<Token>(position) {
			@Override
			protected <Result> ParserClosureImpl<Token, Result> buildClosure(final Parser<Token, Result> parser, final Maybe<ParserClosure<Token, ?>> parent) {
				return new ParserClosureImpl<Token, Result>(parser, position) {
					public void failure(final ParserState<Token> state) {
						// Nothing to do.
					}
					
					@Override
					public boolean isValidFailure() {
						return false;
					}
				};
			}
		};
	}
}
