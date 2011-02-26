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

import com.trazere.parser.ParserPosition;

public class IndexParserPosition<Token>
implements ParserPosition<Token> {
	protected final int _index;
	
	public IndexParserPosition() {
		this(0);
	}
	
	public IndexParserPosition(final int index) {
		_index = index;
	}
	
	public int getIndex() {
		return _index;
	}
	
	public String getDescription() {
		return "index " + _index;
	}
	
	public ParserPosition<Token> next(final Token token) {
		return new IndexParserPosition<Token>(_index + 1);
	}
	
	public int compareTo(final ParserPosition<Token> position) {
		assert null != position;
		
		if (position instanceof IndexParserPosition<?>) {
			final IndexParserPosition<?> indexPosition = (IndexParserPosition<?>) position;
			return getIndex() - indexPosition.getIndex();
		} else {
			throw new IllegalArgumentException("Cannot compare position " + this + " with position " + position);
		}
	}
}
