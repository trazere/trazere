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

public interface ParserState<Token> {
	public ParserPosition<Token> getPosition();
	
	public <Result> void parse(final Parser<Token, Result> parser, final ParserHandler<Token, ? super Result> handler, final ParserClosure<Token, ?> parent)
	throws ParserException;
	
	public void read(final ParserContinuation<Token> continuation)
	throws ParserException;
}
