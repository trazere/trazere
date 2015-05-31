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

import com.trazere.core.lang.HashCode;
import com.trazere.core.lang.ObjectUtils;
import com.trazere.parser.BaseParser;
import com.trazere.parser.Parser;
import com.trazere.parser.ParserClosure;
import com.trazere.parser.ParserHandler;
import com.trazere.parser.ParserState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SeparatorParser<Token, Value>
extends BaseParser<Token, List<Value>> {
	protected final Parser<Token, ? extends Value> _valueParser;
	protected final Parser<Token, ?> _delimiterParser;
	protected final int _min;
	protected final int _max;
	
	public SeparatorParser(final Parser<Token, ? extends Value> valueParser, final Parser<Token, ?> delimiterParser, final String description) {
		this(valueParser, delimiterParser, 0, Integer.MAX_VALUE, description);
	}
	
	public SeparatorParser(final Parser<Token, ? extends Value> valueParser, final Parser<Token, ?> delimiterParser, final int min, final int max, final String description) {
		super(description);
		
		// Checks.
		assert null != valueParser;
		assert null != delimiterParser;
		
		// Initialization.
		_valueParser = valueParser;
		_delimiterParser = delimiterParser;
		_min = min;
		_max = max;
	}
	
	// Parser.
	
	@Override
	public void run(final ParserClosure<Token, List<Value>> closure, final ParserState<Token> state) {
		run(closure, 0, Collections.<Value>emptyList(), state);
	}
	
	public void run(final ParserClosure<Token, List<Value>> closure, final int count, final List<Value> values, final ParserState<Token> state) {
		// Success.
		if (count >= _min) {
			closure.success(Collections.unmodifiableList(values), state);
		}
		
		// More.
		if (count < _max) {
			final Parser<Token, ? extends Value> more = count > 0 ? CoreParsers.second(_delimiterParser, _valueParser, null) : _valueParser;
			state.parse(more, buildMoreHandler(closure, count, values), closure);
		}
	}
	
	protected ParserHandler<Token, Value> buildMoreHandler(final ParserClosure<Token, List<Value>> closure, final int previousCount, final List<Value> previousValues) {
		return (final Value value, final ParserState<Token> state) -> {
			// Add the value.
			final List<Value> values = new ArrayList<>(previousValues);
			values.add(value);
			
			// Continue.
			run(closure, previousCount + 1, values, state);
		};
	}
	
	// Object.
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_description);
		result.append(_valueParser);
		result.append(_delimiterParser);
		result.append(_min);
		result.append(_max);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final SeparatorParser<?, ?> parser = (SeparatorParser<?, ?>) object;
			return ObjectUtils.safeEquals(_description, parser._description) && _valueParser.equals(parser._valueParser) && _delimiterParser.equals(parser._delimiterParser) && _min == parser._min && _max == parser._max;
		} else {
			return false;
		}
	}
}
