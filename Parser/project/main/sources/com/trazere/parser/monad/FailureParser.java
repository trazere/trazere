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
package com.trazere.parser.monad;

import com.trazere.core.lang.HashCode;
import com.trazere.core.lang.ObjectUtils;
import com.trazere.parser.BaseParser;
import com.trazere.parser.ParserClosure;
import com.trazere.parser.ParserState;

public class FailureParser<Token, Result>
extends BaseParser<Token, Result> {
	public FailureParser(final String description) {
		super(description);
	}
	
	// Parser.
	
	@Override
	public void run(final ParserClosure<Token, Result> closure, final ParserState<Token> state) {
		// Failure.
		closure.failure(state);
	}
	
	// Object.
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_description);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final FailureParser<?, ?> parser = (FailureParser<?, ?>) object;
			return ObjectUtils.safeEquals(_description, parser._description);
		} else {
			return false;
		}
	}
}
