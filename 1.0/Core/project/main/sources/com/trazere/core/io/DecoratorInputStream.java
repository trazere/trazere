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

import java.io.IOException;
import java.io.InputStream;

/**
 * The {@link DecoratorInputStream} class implements decorators of {@link InputStream input streams}.
 * 
 * @see InputStream
 * @since 1.0
 */
public abstract class DecoratorInputStream
extends InputStream {
	/**
	 * Decorated input stream.
	 * 
	 * @since 1.0
	 */
	protected final InputStream _decorated;
	
	/**
	 * Instantiates a new decorator.
	 * 
	 * @param decorated Decorated input stream.
	 * @since 1.0
	 */
	public DecoratorInputStream(final InputStream decorated) {
		assert null != decorated;
		
		// Initialization.
		_decorated = decorated;
	}
	
	@Override
	public int available()
	throws IOException {
		return _decorated.available();
	}
	
	@Override
	public int read()
	throws IOException {
		return _decorated.read();
	}
	
	@Override
	public int read(final byte[] b)
	throws IOException {
		return _decorated.read(b);
	}
	
	@Override
	public int read(final byte[] b, final int off, final int len)
	throws IOException {
		return _decorated.read(b, off, len);
	}
	
	@Override
	public long skip(final long n)
	throws IOException {
		return _decorated.skip(n);
	}
	
	@Override
	public boolean markSupported() {
		return _decorated.markSupported();
	}
	
	@Override
	public synchronized void mark(final int readlimit) {
		_decorated.mark(readlimit);
	}
	
	@Override
	public synchronized void reset()
	throws IOException {
		_decorated.reset();
	}
	
	@Override
	public void close()
	throws IOException {
		_decorated.close();
	}
}
