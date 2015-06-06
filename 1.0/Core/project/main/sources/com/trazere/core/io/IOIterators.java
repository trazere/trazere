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
package com.trazere.core.io;

import com.trazere.core.imperative.LookAheadIterator;
import com.trazere.core.lang.ThrowableFactory;
import com.trazere.core.util.Maybe;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Iterator;

/**
 * The {@link IOIterators} class provides various factories of {@link Iterator iterators} regarding I/Os.
 * 
 * @see Iterator
 * @since 1.0
 */
public class IOIterators {
	/**
	 * Builds an iterator of the bytes read from the given input stream.
	 * 
	 * @param stream Stream to read.
	 * @param failureFactory Factory of the exceptions for the IO failures.
	 * @return The built iterator.
	 * @since 1.0
	 */
	public Iterator<Byte> fromStream(final InputStream stream, final ThrowableFactory<? extends RuntimeException> failureFactory) {
		assert null != stream;
		assert null != failureFactory;
		
		return new LookAheadIterator<Byte>() {
			@Override
			protected Maybe<Byte> pull() {
				try {
					final int b = stream.read();
					return b >= 0 ? Maybe.some((byte) b) : Maybe.none();
				} catch (final IOException exception) {
					throw failureFactory.build("Failed reading next byte from stream " + stream, exception);
				}
			}
		};
	}
	
	/**
	 * Builds an iterator of the characters read from the given reader.
	 * 
	 * @param reader Reader to read.
	 * @param failureFactory Factory of the exceptions for the IO failures.
	 * @return The built iterator.
	 * @since 1.0
	 */
	public Iterator<Character> fromReader(final Reader reader, final ThrowableFactory<? extends RuntimeException> failureFactory) {
		assert null != reader;
		assert null != failureFactory;
		
		return new LookAheadIterator<Character>() {
			@Override
			protected Maybe<Character> pull() {
				try {
					final int c = reader.read();
					return c >= 0 ? Maybe.some((char) c) : Maybe.none();
				} catch (final IOException exception) {
					throw failureFactory.build("Failed reading next character from reader " + reader, exception);
				}
			}
		};
	}
	
	private IOIterators() {
		// Prevent instantiation.
	}
}
