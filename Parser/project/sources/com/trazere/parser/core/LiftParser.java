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
import com.trazere.util.lang.HashCode;
import com.trazere.util.lang.LangUtils;

public class LiftParser<Token, SubResult, Result>
extends Sequence1Parser<Token, SubResult, Result> {
	public LiftParser(final Parser<Token, ? extends SubResult> subParser, final Result result, final String description) {
		super(subParser, description);
		
		// Initialization.
		_result = result;
	}
	
	// Parser.
	
	protected final Result _result;
	
	@Override
	protected Result combine(final SubResult subResult1) {
		return _result;
	}
	
	// Object.
	
	@Override
	public int hashCode() {
		final HashCode hashCode = new HashCode(this);
		hashCode.append(_description);
		hashCode.append(_subParser1);
		hashCode.append(_result);
		return hashCode.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final LiftParser<?, ?, ?> parser = (LiftParser<?, ?, ?>) object;
			return LangUtils.equals(_description, parser._description) && _subParser1.equals(parser._subParser1) && LangUtils.equals(_result, parser._result);
		} else {
			return false;
		}
	}
}
