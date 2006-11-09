package com.trazere.util.text;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;

import com.trazere.util.Assert;

/**
 * The <code>Scanner</code> class provides a tool to analyse character streams.
 * <p>
 * It provides various methods to scan the upcoming characters with infinite look ahead. Scanning operations are defined as reading the appropriate characters
 * and matching them against some filter. When the matches (scans) are successful, the read characters are consumed. Otherwise, no characters are consumed, even
 * when the match was partially successful.
 */
public class Scanner {
	/** Reader providing the character stream. */
	protected final PushbackReader _reader;

	/**
	 * Instanciate a new scanner with the given reader.
	 * 
	 * @param reader Reader providing the character stream.
	 */
	public Scanner(final Reader reader) {
		Assert.notNull(reader);

		// Initialization.
		_reader = new PushbackReader(reader, 512);
	}

	/**
	 * Get the reader of the receiver scanner.
	 * 
	 * @return The reader.
	 */
	public Reader getReader() {
		return _reader;
	}

	/**
	 * Test wether the end-of-file has been reached.
	 * 
	 * @return <code>true</code> if the eof has been reached, <code>false</code> if characters are still available.
	 * @throws IOException
	 */
	public boolean eof()
	throws IOException {
		final int i = _reader.read();
		if (-1 == i) {
			return true;
		} else {
			_reader.unread(i);
			return false;
		}
	}

	/**
	 * Scan the upcoming character.
	 * <p>
	 * This scan operation always succeeds, unless there are no more characters to read.
	 * 
	 * @return The scanned character, or <code>null</code> if the eof has been reached.
	 * @throws IOException
	 */
	public Character scanChar()
	throws IOException {
		final int i = _reader.read();
		if (-1 == i) {
			return null;
		}

		return new Character((char) i);
	}

	/**
	 * Scan the given character.
	 * <p>
	 * This scan operation succeeds if the upcoming character equals the given character.
	 * 
	 * @param c Character to scan.
	 * @return <code>true</code> if the scan is succesful, <code>false</code> otherwise.
	 * @throws IOException
	 */
	public boolean scanChar(final char c)
	throws IOException {
		final int i = _reader.read();
		if (-1 == i) {
			return false;
		}

		if ((char) i == c) {
			return true;
		} else {
			_reader.unread(i);
			return false;
		}
	}

	/**
	 * Scan the characters belonging to the character set defined by the given string.
	 * <p>
	 * This scan operation accumulates the continuous sequence of characters belonging to the given set. It always succeeds, but the sequence may be empty.
	 * 
	 * @param chars Set of characters to scan.
	 * @return The sequence of scanned characters.
	 * @throws IOException
	 */
	public String scanChars(final String chars)
	throws IOException {
		final CharFilter filter = new CharFilter() {
			public boolean filter(final char c) {
				return chars.indexOf(c) >= 0;
			}
		};
		return scanChars(filter);
	}

	/**
	 * Scan the characters accepted by the given filter.
	 * <p>
	 * This scan operation accumulates the continuous sequence of characters accepted by the filter. It always succeeds, but the sequence may be empty.
	 * 
	 * @param filter Filter defining the accepted characters.
	 * @return The sequence of scanned characters.
	 * @throws IOException
	 */
	public String scanChars(final CharFilter filter)
	throws IOException {
		final StringBuffer buffer = new StringBuffer();
		while (true) {
			final int i = _reader.read();
			if (-1 == i) {
				break;
			}

			final char c = (char) i;
			if (filter.filter(c)) {
				buffer.append(c);
			} else {
				_reader.unread(i);
				break;
			}
		}
		return buffer.toString();
	}

	/**
	 * Scan the given sequence of characters.
	 * <p>
	 * This scan operation succeeds if the upcoming sequence of character equals the given sequence of characters.
	 * 
	 * @param string Sequence of characters.
	 * @return <code>true</code> if the scan is succesful, <code>false</code> otherwise.
	 * @throws IOException
	 */
	public boolean scanString(final String string)
	throws IOException {
		Assert.notNull(string);

		// Scan.
		final char[] buffer = new char[string.length()];
		final int n = _reader.read(buffer);
		if (-1 == n) {
			return false;
		}

		final String s = new String(buffer, 0, n);
		if (s.equals(string)) {
			return true;
		} else {
			_reader.unread(buffer, 0, n);
			return false;
		}
	}

	/**
	 * Scan the upcoming characters up to the given character.
	 * <p>
	 * This scan operation accumulates the upcoming sequence of characters until the given character or the end-of-file is reached. The scan always succeeds,
	 * but the sequence may be empty. The stopping character is not included in the scan.
	 * 
	 * @param c Stopping character.
	 * @return The sequence of scanned characters.
	 * @throws IOException
	 */
	public String scanUpToChar(final char c)
	throws IOException {
		// Scan.
		final StringBuilder buffer = new StringBuilder();
		while (true) {
			final int i = _reader.read();
			if (-1 == i) {
				break;
			}

			final char rc = (char) i;
			if (rc != c) {
				buffer.append(rc);
			} else {
				_reader.unread(i);
				break;
			}
		}
		return buffer.toString();
	}

	/**
	 * Scan the upcoming characters up to any given character.
	 * <p>
	 * This scan operation accumulates the upcoming sequence of characters until some character of the given set or the end-of-file is reached. The scan always
	 * succeeds, but the sequence may be empty. The stopping character is not included in the scan.
	 * 
	 * @param chars Stopping character set.
	 * @return The sequence of scanned characters.
	 * @throws IOException
	 */
	public String scanUpToAnyChar(final String chars)
	throws IOException {
		final CharFilter filter = new CharFilter() {
			public boolean filter(final char c) {
				return chars.indexOf(c) >= 0;
			}
		};
		return scanUpToAnyChar(filter);
	}

	/**
	 * Scan the upcoming characters up to any character accepted by the given filter.
	 * <p>
	 * This scan operation accumulates the upcoming sequence of characters until some character is accepted by the filter or the end-of-file is reached. The
	 * scan always succeeds, but the sequence may be empty. The stopping character is not included in the scan.
	 * 
	 * @param filter Filter defining the stopping characters.
	 * @return The sequence of scanned characters.
	 * @throws IOException
	 */
	public String scanUpToAnyChar(final CharFilter filter)
	throws IOException {
		Assert.notNull(filter);

		// Scan.
		final StringBuffer buffer = new StringBuffer();
		while (true) {
			final int i = _reader.read();
			if (-1 == i) {
				break;
			}

			final char c = (char) i;
			if (!filter.filter(c)) {
				buffer.append(c);
			} else {
				_reader.unread(i);
				break;
			}
		}
		return buffer.toString();
	}

	/**
	 * Scan the upcoming characters up to the given sequence of character.
	 * <p>
	 * This scan operation accumulates the upcoming sequence of characters until the given sequence of character or the end-of-file is reached. The scan always
	 * succeeds, but the sequence may be empty. The stopping sequence of characters is not included in the scan.
	 * 
	 * @param string Stopping sequence of characters.
	 * @return The sequence of scanned characters.
	 * @throws IOException
	 */
	public String scanUpToString(final String string)
	throws IOException {
		Assert.notNull(string);
		Assert.expression(string.length() > 0, "empty string");

		// Scan.
		final char c = string.charAt(0);

		final StringBuffer buffer = new StringBuffer();
		while (true) {
			// Scan part.
			buffer.append(scanUpToChar(c));

			// Check string.
			if (scanString(string)) {
				_reader.unread(string.toCharArray());
				break;
			}

			// Scan char.
			final int i = _reader.read();
			if (-1 == i) {
				break;
			}
			buffer.append((char) i);
		}
		return buffer.toString();
	}

	// public Integer scanInteger() {
	//		
	// }
	//	
	// public String scanPattern(final Pattern pattern) {
	//		
	// }
	//	
	// public String scanUpToPattern(final Pattern pattern) {
	//		
	// }

	/**
	 * Close the reader of the receiver scanner.
	 * 
	 * @throws IOException 
	 */
	public void close()
	throws IOException {
		_reader.close();
	}
}
