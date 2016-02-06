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
package com.trazere.parser.core;

import com.trazere.core.functional.BaseResettableThunk;
import com.trazere.core.functional.Thunk;
import com.trazere.parser.Parser;
import com.trazere.parser.ParserClosure;
import com.trazere.parser.ParserState;

public abstract class LazyParser<Token, Result>
implements Parser<Token, Result> {
	public static <Token, Result> LazyParser<Token, Result> build(final Thunk<? extends Parser<Token, Result>> parser) {
		assert null != parser;
		
		return new LazyParser<Token, Result>() {
			@Override
			protected Parser<Token, Result> compute() {
				return parser.evaluate();
			}
		};
	}
	
	protected final Thunk<Parser<Token, Result>> _parser = new BaseResettableThunk<Parser<Token, Result>>() {
		@Override
		protected Parser<Token, Result> compute() {
			return LazyParser.this.compute();
		}
	};
	
	protected abstract Parser<Token, Result> compute();
	
	@Override
	public String getDescription() {
		return _parser.evaluate().getDescription();
	}
	
	@Override
	public void run(final ParserClosure<Token, Result> closure, final ParserState<Token> state) {
		_parser.evaluate().run(closure, state);
	}
}
