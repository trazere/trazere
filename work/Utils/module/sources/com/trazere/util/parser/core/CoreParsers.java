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
package com.trazere.util.parser.core;

import java.util.List;

import com.trazere.util.collection.CollectionUtils;
import com.trazere.util.parser.Parser;

/**
 * DOCME
 */
public class CoreParsers {
	private static final EOFParser<?, ?> EOF = eof("end of file");
	
	@SuppressWarnings("unchecked")
	public static <Token, Result> EOFParser<Token, Result> eof() {
		return (EOFParser<Token, Result>) EOF;
	}
	
	public static <Token, Result> EOFParser<Token, Result> eof(final String description) {
		return new EOFParser<Token, Result>(description);
	}
	
	public static <Token, Result> OptionParser<Token, Result> option(final Parser<Token, Result> subParser, final String description) {
		return new OptionParser<Token, Result>(subParser, description);
	}
	
	public static <Token, Result> ManyParser<Token, Result> many(final Parser<Token, Result> subParser, final String description) {
		return new ManyParser<Token, Result>(subParser, description);
	}
	
	public static <Token, Result> Many1Parser<Token, Result> many1(final Parser<Token, Result> subParser, final String description) {
		return new Many1Parser<Token, Result>(subParser, description);
	}
	
	public static <Token, Result> ChoiceParser<Token, Result> choice(final Parser<Token, ? extends Result> subParser1, final Parser<Token, ? extends Result> subParser2, final String description) {
		return choice(CollectionUtils.<Parser<Token, ? extends Result>>list(subParser1, subParser2), description);
	}
	
	public static <Token, Result> ChoiceParser<Token, Result> choice(final Parser<Token, ? extends Result> subParser1, final Parser<Token, ? extends Result> subParser2, final Parser<Token, ? extends Result> subParser3, final String description) {
		return choice(CollectionUtils.<Parser<Token, ? extends Result>>list(subParser1, subParser2, subParser3), description);
	}
	
	public static <Token, Result> ChoiceParser<Token, Result> choice(final List<Parser<Token, ? extends Result>> subParsers, final String description) {
		return new ChoiceParser<Token, Result>(subParsers, description);
	}
	
	private CoreParsers() {
		// Prevent instantiation.
	}
}
