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
package com.trazere.util.io;

import com.trazere.util.lang.MutableLong;
import java.io.IOException;
import java.io.InputStream;

/**
 * The {@link SubInputStream} class provide input stream combinators which truncate the content after some maximal length.
 * 
 * @deprecated Use {@link com.trazere.core.io.TruncatedInputStream}.
 */
@Deprecated
public class SubInputStream
extends DecoratorInputStream {
	private final MutableLong _remaining;
	private final MutableLong _mark = new MutableLong(0);
	
	/**
	 * Instantiates a new sub stream for the given stream and length.
	 * 
	 * @param stream The stream.
	 * @param length The maximal length.
	 * @deprecated Use {@link com.trazere.core.io.TruncatedInputStream#TruncatedInputStream(InputStream, long)}.
	 */
	@Deprecated
	public SubInputStream(final InputStream stream, final long length) {
		super(stream);
		
		// Checks.
		assert length >= 0;
		
		// Initialization.
		_remaining = new MutableLong(length);
	}
	
	@Override
	public int available()
	throws IOException {
		final int available = super.available();
		final int remaining = (int) _remaining.get();
		return available < remaining ? available : remaining;
	}
	
	@Override
	public int read()
	throws IOException {
		final long remaining = _remaining.get();
		if (remaining > 0) {
			final int c = super.read();
			if (c >= 0) {
				_remaining.set(remaining - 1);
			}
			return c;
		} else {
			return -1;
		}
	}
	
	@Override
	public int read(final byte[] b)
	throws IOException {
		assert null != b;
		
		return read(b, 0, b.length);
	}
	
	@Override
	public int read(final byte[] b, final int off, final int len)
	throws IOException {
		final long remaining = _remaining.get();
		final int len2 = len < remaining ? len : (int) remaining;
		final int read = super.read(b, off, len2);
		_remaining.set(remaining - read);
		return read;
	}
	
	@Override
	public long skip(final long n)
	throws IOException {
		final long remaining = _remaining.get();
		final long n2 = n < remaining ? n : remaining;
		final long skip = super.skip(n2);
		_remaining.set(remaining - skip);
		return skip;
	}
	
	@Override
	public synchronized void mark(final int readlimit) {
		if (markSupported()) {
			super.mark(readlimit);
			_mark.set(_remaining.get());
		}
	}
	
	@Override
	public synchronized void reset()
	throws IOException {
		if (markSupported()) {
			super.reset();
			_remaining.set(_mark.get());
			_mark.set(0);
		} else {
			throw new IOException();
		}
	}
}
