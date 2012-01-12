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
package com.trazere.parser.monad;

/**
 * DOCME
 */
public class MonadParsers {
	public static <Token, Result> FailureParser<Token, Result> failure(final String description) {
		return new FailureParser<Token, Result>(description);
	}
	
	public static <Token, Result> SuccessParser<Token, Result> success(final Result result, final String description) {
		return new SuccessParser<Token, Result>(result, description);
	}
	
	private MonadParsers() {
		// Prevents instantiation.
	}
}
