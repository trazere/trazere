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

/**
 * The {@link Parser} interface defines parsing functions from streams of token to data structures.
 * <p>
 * The parsing framework has been designed to encourage the writing of parsers as combinators. Core parsers may implement primitives of some grammar langage
 * (like reading a character or reading some optional value) while higher level parsers may implement the grammars themselves.
 * 
 * @param <Token>
 * @param <Result>
 */
public interface Parser<Token, Result> {
	public String getDescription();
	
	public void run(final ParserClosure<Token, Result> closure, final ParserState<Token> state)
	throws ParserException;
}
