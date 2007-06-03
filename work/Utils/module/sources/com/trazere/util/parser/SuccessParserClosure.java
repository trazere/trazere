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
 * DOCME
 * 
 * @param <Token>
 * @param <Result>
 */
public class SuccessParserClosure<Token, Result>
extends AbstractParserClosure<Token, Result> {
	public SuccessParserClosure(final Parser<Token, Result> parser, final int position) {
		super(parser, position);
	}
	//	@Override
	//	public int hashCode() {
	//		int result = getClass().hashCode();
	//		result = result * 31 + _parser.hashCode();
	//		result = result * 31 + _position;
	//		return result;
	//	}
	//
	//	@Override
	//	public boolean equals(final Object object) {
	//		if (this == object) {
	//			return true;
	//		} else if (null != object && getClass().equals(object.getClass())) {
	//			final SimpleParserNode node = (SimpleParserNode) object;
	//			return _parser.equals(node._parser) && _position == node._position;
	//		} else {
	//			return false;
	//		}
	//	}
}
