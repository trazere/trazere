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

import com.trazere.util.Assert;

/**
 * DOCME
 * 
 * @param <Token>
 * @param <Result>
 */
public abstract class AbstractParserClosure<Token, Result>
implements ParserClosure<Token, Result> {
	protected final Parser<Token, Result> _parser;
	protected final int _position;
	
	public AbstractParserClosure(final Parser<Token, Result> parser, final int position) {
		Assert.notNull(parser);
		
		// Initialization.
		_parser = parser;
		_position = position;
	}
	
	public Parser<Token, Result> getParser() {
		return _parser;
	}
	
	public int getPosition() {
		return _position;
	}
}
