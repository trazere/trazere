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
package com.trazere.parser;

/**
 * The {@link Parser} interface defines parsing functions from streams of tokens to values.
 * <p>
 * The parsing framework has been designed to encourage the writing of parsers as combinators. Low level parsers implement the primitives of some grammar
 * language (like reading a character or reading some optional value) while higher level parsers implement the grammars themselves.
 * 
 * @param <Token> Type of the tokens.
 * @param <Result> Type of the result values.
 */
public interface Parser<Token, Result> {
	/**
	 * Get the description of the receiver parser.
	 * 
	 * @return The description.
	 */
	String getDescription();
	
	/**
	 * Run the receiver parser.
	 * 
	 * @param closure The parsing closure.
	 * @param state The parsing state to use.
	 */
	void run(ParserClosure<Token, Result> closure, ParserState<Token> state);
}
