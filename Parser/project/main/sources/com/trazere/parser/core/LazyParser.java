/*
 *  Copyright 2006-2013 Julien Dufour
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
import com.trazere.util.closure.Closure;
import com.trazere.util.closure.ResettableClosure;
import com.trazere.util.function.Function0;

public abstract class LazyParser<Token, Result>
implements Parser<Token, Result> {
	public static <Token, Result> LazyParser<Token, Result> build(final Function0<? extends Parser<Token, Result>, ? extends ParserException> parser) {
		assert null != parser;
		
		return new LazyParser<Token, Result>() {
			@Override
			protected Parser<Token, Result> compute()
			throws ParserException {
				return parser.evaluate();
			}
		};
	}
	
	protected final Closure<Parser<Token, Result>, ParserException> _parser = new ResettableClosure<Parser<Token, Result>, ParserException>() {
		@Override
		protected Parser<Token, Result> compute()
		throws ParserException {
			return LazyParser.this.compute();
		}
	};
	
	protected abstract Parser<Token, Result> compute()
	throws ParserException;
	
	@Override
	public String getDescription() {
		try {
			return _parser.evaluate().getDescription();
		} catch (final ParserException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Override
	public void run(final ParserClosure<Token, Result> closure, final ParserState<Token> state)
	throws ParserException {
		_parser.evaluate().run(closure, state);
	}
}
