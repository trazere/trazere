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
package com.trazere.core.text;

import com.trazere.core.util.Maybe;
import java.io.Closeable;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;

/**
 * The {@link Scanner} class provides helpers to analyse/read character streams.
 * <p>
 * Scanners provide various operations that read characters and match them against some filter with an infinite look aread. Upon success of the match, the read
 * characters are consumed and returned. Otherwise, no characters are consumed, even when the match was partially successful.
 * 
 * @since 1.0
 */
public class Scanner
implements Closeable {
	/**
	 * Reader providing the character stream.
	 * 
	 * @since 1.0
	 */
	protected final PushbackReader _reader;
	
	/**
	 * Current scanning position.
	 * 
	 * @since 1.0
	 */
	protected int _position;
	
	/**
	 * Instantiates a new scanner with an initial <code>0</code> position.
	 * 
	 * @param reader Reader providing the character stream.
	 * @since 1.0
	 */
	public Scanner(final Reader reader) {
		this(reader, 0);
	}
	
	/**
	 * Instantiates a new scanner with the given reader.
	 * 
	 * @param reader Reader providing the character stream.
	 * @param position Initial scanning position.
	 * @since 1.0
	 */
	public Scanner(final Reader reader, final int position) {
		assert null != reader;
		assert position >= 0;
		
		// Initialization.
		_reader = new PushbackReader(reader, 512);
		_position = position;
	}
	
	/**
	 * Gets the reader providing the character stream to this scanner.
	 * 
	 * @return The reader.
	 * @since 1.0
	 */
	public Reader getReader() {
		return _reader;
	}
	
	/**
	 * Gets the current scanning position of this scanner.
	 * 
	 * @return The position.
	 * @since 1.0
	 */
	public int getPosition() {
		return _position;
	}
	
	/**
	 * Tests whether the end-of-file has been reached by this scanner.
	 * 
	 * @return <code>true</code> when the eof has been reached, <code>false</code> if characters are still available.
	 * @since 1.0
	 */
	public boolean isEof() {
		try {
			// Read the next char.
			final int i = _reader.read();
			if (i < 0) {
				return true;
			} else {
				// Unread the char.
				_reader.unread(i);
				return false;
			}
		} catch (final IOException exception) {
			throw new TextException(exception);
		}
	}
	
	/**
	 * Scans the upcoming character.
	 * 
	 * @return The scanned character, or nothing when the eof has been reached.
	 * @since 1.0
	 */
	public Maybe<Character> scanChar() {
		try {
			// Read the next char.
			final int i = _reader.read();
			if (i < 0) {
				return Maybe.none();
			} else {
				// Consume the char.
				_position += 1;
				return Maybe.some((char) i);
			}
		} catch (final IOException exception) {
			throw new TextException(exception);
		}
	}
	
	/**
	 * Scans the given character.
	 * 
	 * @param c Character to scan.
	 * @return <code>true</code> when the given character has been scanned, <code>false</code> when the eof has been reached or when the next upcoming character
	 *         was not the excepted one.
	 * @since 1.0
	 */
	public boolean scanChar(final char c) {
		try {
			// Read the next char.
			final int i = _reader.read();
			if (i < 0) {
				return false;
			} else if ((char) i != c) {
				// Unread the unexcepted char.
				_reader.unread(i);
				return false;
			} else {
				// Consume the char.
				_position += 1;
				return true;
			}
		} catch (final IOException exception) {
			throw new TextException("Failed scanning char '" + c + "\'", exception);
		}
	}
	
	/**
	 * Scans one character accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the character.
	 * @return The scanned character, or nothing when the eof has been reached or when the next upcoming character was not accepted by the filter.
	 * @since 1.0
	 */
	public Maybe<Character> scanChar(final CharPredicate filter) {
		try {
			// Read the next char.
			final int i = _reader.read();
			if (i < 0) {
				return Maybe.none();
			}
			final char c = (char) i;
			
			// Filter the char.
			if (!filter.evaluate(c)) {
				_reader.unread(i);
				return Maybe.none();
			}
			
			// Consume the char.
			_position += 1;
			return Maybe.some(c);
		} catch (final IOException exception) {
			throw new TextException("Failed scanning some char accepted by \"" + filter + "\"", exception);
		}
	}
	
	/**
	 * Scans the upcoming characters accepted by the given filter.
	 * <p>
	 * This scan operation continues to scan characters as long as they are accepted by the filter.
	 * 
	 * @param filter Predicate to use to filter the character.
	 * @return The sequence of scanned characters.
	 * @since 1.0
	 */
	public CharSequence scanChars(final CharPredicate filter) {
		try {
			final StringBuilder buffer = new StringBuilder();
			while (true) {
				// Read the next char.
				final int i = _reader.read();
				if (i < 0) {
					return buffer;
				}
				final char c = (char) i;
				
				// Filter the char.
				if (!filter.evaluate(c)) {
					_reader.unread(i);
					return buffer;
				}
				
				// Consume the char.
				_position += 1;
				buffer.append(c);
			}
		} catch (final IOException exception) {
			throw new TextException("Failed scanning chars accepted by \"" + filter + "\"", exception);
		}
	}
	
	/**
	 * Scans the given sequence of characters.
	 * 
	 * @param s Sequence of characters to scan.
	 * @return <code>true</code> when the given sequence of characters has been scanned, or <code>false</code> when the eof has been reached or when the
	 *         upcoming characters don't match the given sequence
	 * @since 1.0
	 */
	public boolean scanSeq(final CharSequence s) {
		try {
			// Scan the chars.
			final char[] buffer = new char[s.length()];
			final int n = _reader.read(buffer);
			if (n < 0) {
				return false;
			}
			final String rs = new String(buffer, 0, n);
			
			// Filter the chars.
			if (!rs.equals(s)) {
				_reader.unread(buffer, 0, n);
				return false;
			}
			
			// Consume the chars.
			_position += n;
			return true;
		} catch (final IOException exception) {
			throw new TextException("Failed scanning char sequence \"" + s + "\"", exception);
		}
	}
	
	/**
	 * Scans the upcoming characters until the end-of-file.
	 * <p>
	 * This scan operation accumulates the upcoming sequence of characters until the end-of-file is reached. The scan always succeeds, but the sequence may be
	 * empty.
	 * 
	 * @return The sequence of scanned characters.
	 * @since 1.0
	 */
	public CharSequence scanToEOF() {
		try {
			final StringBuilder buffer = new StringBuilder();
			while (true) {
				// Scan the next char.
				final int i = _reader.read();
				if (i < 0) {
					return buffer;
				}
				
				// Consume the char.
				_position += 1;
				buffer.append((char) i);
			}
		} catch (final IOException exception) {
			throw new TextException(exception);
		}
	}
	
	/**
	 * Scans the upcoming characters until the given character.
	 * <p>
	 * The stopping character is not scanned.
	 * 
	 * @param c Character at which scanning should stop.
	 * @return The sequence of scanned characters.
	 * @since 1.0
	 */
	public CharSequence scanToChar(final char c) {
		try {
			final StringBuilder buffer = new StringBuilder();
			while (true) {
				// Scan the next character.
				final int i = _reader.read();
				if (i < 0) {
					return buffer;
				}
				final char rc = (char) i;
				
				// Filter the char.
				if (rc == c) {
					_reader.unread(i);
					return buffer;
				}
				
				// Consume the char.
				_position += 1;
				buffer.append(rc);
			}
		} catch (final IOException exception) {
			throw new TextException("Failed scanning to char '" + c + "\'", exception);
		}
	}
	
	/**
	 * Scans the upcoming characters until any character accepted by the given filter.
	 * <p>
	 * This scan operation continues to scan characters as long as they are rejected by the filter.
	 * 
	 * @param filter Predicate to use to filter the character.
	 * @return The sequence of scanned characters.
	 * @since 1.0
	 */
	public CharSequence scanToChar(final CharPredicate filter) {
		try {
			final StringBuffer buffer = new StringBuffer();
			while (true) {
				// Read the next char.
				final int i = _reader.read();
				if (i < 0) {
					return buffer;
				}
				final char c = (char) i;
				
				// Filter the char.
				if (filter.evaluate(c)) {
					_reader.unread(i);
					return buffer;
				}
				
				// Consume the char.
				_position += 1;
				buffer.append(c);
			}
		} catch (final IOException exception) {
			throw new TextException("Failed scanning to any char accepted by \"" + filter + "\"", exception);
		}
	}
	
	/**
	 * Scans the upcoming characters until the given sequence of characters.
	 * <p>
	 * The stopping character sequence is not scanned.
	 * 
	 * @param s Stopping sequence of characters.
	 * @return The sequence of scanned characters.
	 * @since 1.0
	 */
	public CharSequence scanToSeq(final CharSequence s) {
		try {
			if (0 == s.length()) {
				return "";
			}
			final char c = s.charAt(0);
			
			final StringBuilder buffer = new StringBuilder();
			while (true) {
				// Scan part.
				buffer.append(scanToChar(c));
				
				// Scan for the stopping sequence.
				if (scanSeq(s)) {
					_reader.unread(s.toString().toCharArray());
					return buffer;
				}
				
				// Consume the next char.
				final int i = _reader.read();
				if (i < 0) {
					return buffer;
				} else {
					_position += 1;
					buffer.append((char) i);
				}
			}
		} catch (final IOException exception) {
			throw new TextException("Failed scanning to char sequence \"" + s + "\"", exception);
		}
	}
	
	/**
	 * Closes the underlying reader providing the characters to this scanner.
	 * 
	 * @since 1.0
	 */
	@Override
	public void close() {
		try {
			_reader.close();
		} catch (final IOException exception) {
			throw new TextException(exception);
		}
	}
}
