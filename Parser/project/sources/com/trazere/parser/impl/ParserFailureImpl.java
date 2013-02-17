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
package com.trazere.parser.impl;

import com.trazere.parser.Parser;
import com.trazere.parser.ParserFailure;
import com.trazere.parser.ParserPosition;

/**
 * DOCME
 * 
 * @param <Token>
 */
public class ParserFailureImpl<Token>
implements ParserFailure<Token> {
	public ParserFailureImpl(final Parser<Token, ?> parser, final ParserPosition<Token> position) {
		assert null != parser;
		assert null != position;
		
		// Initialization.
		_parser = parser;
		_position = position;
	}
	
	protected final Parser<Token, ?> _parser;
	
	@Override
	public Parser<Token, ?> getParser() {
		return _parser;
	}
	
	protected final ParserPosition<Token> _position;
	
	@Override
	public ParserPosition<Token> getPosition() {
		return _position;
	}
}
