/*
 *  Copyright 2006-2011 Julien Dufour
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

import com.trazere.parser.ParserException;
import com.trazere.parser.ParserSource;
import java.io.IOException;
import java.io.Reader;

/**
 * DOCME
 */
public class ReaderParserSource
implements ParserSource<Character> {
	protected final Reader _reader;
	protected boolean _lookAhead = false;
	protected int _next = -1;
	
	public ReaderParserSource(final Reader reader) {
		assert null != reader;
		
		// Initialization.
		_reader = reader;
	}
	
	public boolean hasNext()
	throws ParserException {
		lookAhead();
		return -1 != _next;
	}
	
	public Character next()
	throws ParserException {
		lookAhead();
		_lookAhead = false;
		return Character.valueOf((char) _next);
	}
	
	protected void lookAhead()
	throws ParserException {
		if (!_lookAhead) {
			try {
				_next = _reader.read();
			} catch (final IOException exception) {
				throw new ParserException(exception);
			}
			_lookAhead = true;
		}
	}
}
