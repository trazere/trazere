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
package com.trazere.parser.util;

import com.trazere.parser.ParserSource;

/**
 * DOCME
 */
public class StringParserSource
implements ParserSource<Character> {
	protected final String _string;
	protected int _position = 0;
	
	public StringParserSource(final String string) {
		assert null != string;
		
		// Initialization.
		_string = string;
	}
	
	@Override
	public boolean hasNext() {
		return _position < _string.length();
	}
	
	@Override
	public Character next() {
		final char c = _string.charAt(_position);
		_position += 1;
		
		return Character.valueOf(c);
	}
}
