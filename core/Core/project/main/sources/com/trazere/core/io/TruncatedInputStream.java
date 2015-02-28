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
package com.trazere.core.io;

import com.trazere.core.lang.MutableLong;
import java.io.IOException;
import java.io.InputStream;

/**
 * The {@link TruncatedInputStream} class implements input streams that truncate the read data after some maximal length.
 */
public class TruncatedInputStream
extends DecoratorInputStream {
	private final MutableLong _remaining;
	private final MutableLong _mark = new MutableLong(0);
	
	/**
	 * Instantiates a new truncated stream.
	 * 
	 * @param stream Input stream providing the data.
	 * @param length Maximal length of data to read.
	 */
	public TruncatedInputStream(final InputStream stream, final long length) {
		super(stream);
		
		// Checks.
		assert length >= 0;
		
		// Initialization.
		_remaining = new MutableLong(length);
	}
	
	@Override
	public int available()
	throws IOException {
		final long available = super.available();
		final long remaining = _remaining.get();
		return (int) (available < remaining ? available : remaining);
	}
	
	@Override
	public int read()
	throws IOException {
		if (_remaining.get() > 0) {
			final int c = super.read();
			if (c >= 0) {
				_remaining.sub(1);
			}
			return c;
		} else {
			return -1;
		}
	}
	
	@Override
	public int read(final byte[] b)
	throws IOException {
		return read(b, 0, b.length);
	}
	
	@Override
	public int read(final byte[] b, final int off, final int len)
	throws IOException {
		if (_remaining.get() > 0) {
			final int truncatedLen = (int) Math.min(len, _remaining.get());
			final int read = super.read(b, off, truncatedLen);
			_remaining.sub(read);
			return read;
		} else {
			return -1;
		}
	}
	
	@Override
	public long skip(final long n)
	throws IOException {
		final long truncatedN = Math.min(n, _remaining.get());
		final long skip = super.skip(truncatedN);
		_remaining.sub(skip);
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
