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

import com.trazere.core.lang.HashCode;
import com.trazere.core.lang.LangUtils;
import com.trazere.parser.Parser;
import com.trazere.parser.ParserException;
import com.trazere.util.type.Maybe;

public class DefaultParser<Token, Result>
extends Sequence1Parser<Token, Maybe<Result>, Result> {
	public DefaultParser(final Parser<Token, Maybe<Result>> subParser, final Result defaultResult, final String description) {
		super(subParser, description);
		
		// Initialization.
		_defaultResult = defaultResult;
	}
	
	// Parser.
	
	protected final Result _defaultResult;
	
	@Override
	protected Result combine(final Maybe<Result> subResult1)
	throws ParserException {
		return subResult1.get(_defaultResult);
	}
	
	// Object.
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_description);
		result.append(_subParser1);
		result.append(_defaultResult);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final DefaultParser<?, ?> parser = (DefaultParser<?, ?>) object;
			return LangUtils.safeEquals(_description, parser._description) && _subParser1.equals(parser._subParser1) && LangUtils.safeEquals(_defaultResult, parser._defaultResult);
		} else {
			return false;
		}
	}
}
