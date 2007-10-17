/*
 *  Copyright 2006 Julien Dufour
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
package com.trazere.util.parser;

import java.util.Collections;
import java.util.Set;

import com.trazere.util.Assert;
import com.trazere.util.collection.CollectionUtils;

/**
 * DOCME
 * 
 * @param <Token>
 */
public abstract class AbstractParserContinuation<Token>
implements ParserContinuation<Token> {
	protected final Set<? extends ParserClosure<Token, ?>> _closures;
	
	public AbstractParserContinuation(final ParserClosure<Token, ?> closure) {
		this(CollectionUtils.set(closure));
	}
	
	public AbstractParserContinuation(final Set<? extends ParserClosure<Token, ?>> closures) {
		// Checks.
		Assert.notNull(closures);
		
		// Initialization.
		_closures = Collections.unmodifiableSet(closures);
	}
	
	public Set<? extends ParserClosure<Token, ?>> getClosures() {
		return _closures;
	}
}