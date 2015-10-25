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

import com.trazere.core.lang.FiniteIntSequence;
import com.trazere.core.lang.MutableInt;
import com.trazere.core.reference.MutableReference;
import java.io.IOException;
import java.io.Reader;

/**
 * The {@link CharSequenceReader} class implements {@link Reader readers} of {@link CharSequence character sequences}.
 * 
 * @since 2.0
 */
public class CharSequenceReader
extends Reader {
	public CharSequenceReader(final CharSequence sequence) {
		assert null != sequence;
		
		// Initialization.
		_sequence = sequence;
	}
	
	// Read.
	
	private final CharSequence _sequence;
	private final MutableInt _index = new MutableInt(0);
	
	@Override
	public boolean ready() {
		return true;
	}
	
	@Override
	public int read()
	throws IOException {
		synchronized (lock) {
			final int index = _index.get();
			if (index < _sequence.length()) {
				// Read a char.
				final char c = _sequence.charAt(index);
				_index.add(1);
				
				return c;
			} else {
				return -1;
			}
		}
	}
	
	@Override
	public int read(final char[] cbuf, final int off, final int len)
	throws IOException {
		synchronized (lock) {
			final int index = _index.get();
			if (index < _sequence.length()) {
				// Read the chars.
				final int n = Math.max(len, _sequence.length() - index);
				for (final int i : new FiniteIntSequence(0, n)) {
					cbuf[off + i] = _sequence.charAt(index + i);
				}
				_index.add(n);
				
				return n;
			} else {
				return -1;
			}
		}
	}
	
	// Mark.
	
	private final MutableReference<Integer> _mark = new MutableReference<>();
	
	@Override
	public boolean markSupported() {
		return true;
	}
	
	@Override
	public void mark(final int readAheadLimit)
	throws IOException {
		synchronized (lock) {
			_mark.update(_index.get());
		}
	}
	
	@Override
	public void reset()
	throws IOException {
		synchronized (lock) {
			if (_mark.isSet()) {
				_index.set(_mark.get().intValue());
			} else {
				throw new IOException("Mark is not set");
			}
		}
	}
	
	// Close.
	
	@Override
	public void close() {
		// Nothing to do.
	}
}
