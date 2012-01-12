/*
 *  Copyright 2006-2012 Julien Dufour
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
import com.trazere.util.reference.MutableReference;

// TODO: improve parser comparison to handle recursive parsers

/**
 * DOCME
 * 
 * @param <Token>
 * @param <Result>
 */
public class ParserReference<Token, Result>
implements Parser<Token, Result> {
	// Parser.
	
	protected MutableReference<Parser<Token, Result>> _parser = new MutableReference<Parser<Token, Result>>();
	
	public <P extends Parser<Token, Result>> P set(final P parser) {
		assert null != parser;
		
		_parser.set(parser);
		return parser;
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
	
	//	// Object.
	//	
	//	@Override
	//	public int hashCode() {
	//		final HashCode result = new HashCode(this);
	//		result.append(_parser.get());
	//		return result.get();
	//	}
	//	
	//	@Override
	//	public boolean equals(final Object object) {
	//		if (this == object) {
	//			return true;
	//		} else if (null != object && getClass().equals(object.getClass())) {
	//			final ParserReference<?, ?> parser = (ParserReference<?, ?>) object;
	//			return _parser.get().equals(parser._parser.get());
	//		} else if (null != object && getClass().equals(_parser.get().getClass())) {
	//			final Parser<?, ?> parser = (Parser<?, ?>) object;
	//			return _parser.get().equals(parser);
	//		} else {
	//			return false;
	//		}
	//	}
}
