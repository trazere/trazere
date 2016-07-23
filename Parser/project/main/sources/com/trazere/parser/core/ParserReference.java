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

import com.trazere.core.reference.MutableReference;
import com.trazere.core.reference.ReferenceNotSetException;
import com.trazere.parser.Parser;
import com.trazere.parser.ParserClosure;
import com.trazere.parser.ParserException;
import com.trazere.parser.ParserState;

// TODO: improve parser comparison to handle recursive parsers

public class ParserReference<Token, Result>
implements Parser<Token, Result> {
	// Parser.
	
	protected MutableReference<Parser<Token, Result>> _parser = new MutableReference<>();
	
	public <P extends Parser<Token, Result>> P set(final P parser) {
		assert null != parser;
		
		_parser.set(parser);
		return parser;
	}
	
	protected Parser<Token, Result> get() {
		try {
			return _parser.get();
		} catch (final ReferenceNotSetException exception) {
			throw new ParserException("Parser reference has not been set", exception);
		}
	}
	
	@Override
	public String getDescription() {
		return _parser.asMaybe().map(p -> p.getDescription()).get("?");
	}
	
	@Override
	public void run(final ParserClosure<Token, Result> closure, final ParserState<Token> state) {
		get().run(closure, state);
	}
	
	// // Object.
	//
	// @Override
	// public int hashCode() {
	// final HashCode result = new HashCode(this);
	// result.append(_parser.get());
	// return result.get();
	// }
	//
	// @Override
	// public boolean equals(final Object object) {
	// if (this == object) {
	// return true;
	// } else if (null != object && getClass().equals(object.getClass())) {
	// final ParserReference<?, ?> parser = (ParserReference<?, ?>) object;
	// return _parser.get().equals(parser._parser.get());
	// } else if (null != object && getClass().equals(_parser.get().getClass())) {
	// final Parser<?, ?> parser = (Parser<?, ?>) object;
	// return _parser.get().equals(parser);
	// } else {
	// return false;
	// }
	// }
}
