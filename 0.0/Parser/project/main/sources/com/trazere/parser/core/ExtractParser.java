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
import com.trazere.parser.ParserException;
import com.trazere.util.function.Function1;
import com.trazere.util.lang.HashCode;
import com.trazere.util.lang.LangUtils;
import com.trazere.util.type.Maybe;

public class ExtractParser<Token, SubResult, Result>
extends Sequence1Parser<Token, SubResult, Result> {
	public ExtractParser(final Parser<Token, ? extends SubResult> subParser, final Function1<? super SubResult, ? extends Maybe<? extends Result>, ? extends ParserException> extractor, final String description) {
		super(subParser, description);
		
		// Checks.
		assert null != extractor;
		
		// Initialization.
		_extractor = extractor;
	}
	
	// Parser.
	
	protected final Function1<? super SubResult, ? extends Maybe<? extends Result>, ? extends ParserException> _extractor;
	
	@Override
	protected Result combine(final SubResult subResult)
	throws ParserException {
		final Maybe<? extends Result> result = _extractor.evaluate(subResult);
		if (result.isSome()) {
			return result.asSome().getValue();
		} else {
			throw new ParserException("Failed extracting value \"" + subResult + "\"");
		}
	}
	
	// Object.
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_description);
		result.append(_subParser1);
		result.append(_extractor);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final ExtractParser<?, ?, ?> parser = (ExtractParser<?, ?, ?>) object;
			return LangUtils.safeEquals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _extractor.equals(parser._extractor);
		} else {
			return false;
		}
	}
}
