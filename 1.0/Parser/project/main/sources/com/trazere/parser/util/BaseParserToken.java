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
package com.trazere.parser.util;

public abstract class BaseParserToken<R, V>
implements ParserToken<R, V> {
	public BaseParserToken(final R representation) {
		assert null != representation;
		
		// Initialization.
		_representation = representation;
	}
	
	// Representation.
	
	private final R _representation;
	
	@Override
	public R getRepresentation() {
		return _representation;
	}
	
	// Parse.
	
	@Override
	public V parse() {
		return parse(_representation);
	}
	
	protected abstract V parse(R representation);
}
