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
import java.io.OutputStream;

/**
 * The {@link DecoratorOutputStream} class implements decorators of {@link OutputStream output streams}.
 * 
 * @see OutputStream
 * @since 2.0
 */
public abstract class DecoratorOutputStream
extends OutputStream {
	/**
	 * Decorated output stream.
	 * 
	 * @since 2.0
	 */
	protected final OutputStream _decorated;
	
	/**
	 * Instantiates a new decorator.
	 * 
	 * @param decorated Decorated output stream.
	 * @since 2.0
	 */
	public DecoratorOutputStream(final OutputStream decorated) {
		assert null != decorated;
		
		// Initialization.
		_decorated = decorated;
	}
	
	@Override
	public void write(final int b)
	throws IOException {
		_decorated.write(b);
	}
	
	@Override
	public void write(final byte[] b)
	throws IOException {
		_decorated.write(b);
	}
	
	@Override
	public void write(final byte[] b, final int off, final int len)
	throws IOException {
		_decorated.write(b, off, len);
	}
	
	@Override
	public void flush()
	throws IOException {
		_decorated.flush();
	}
	
	@Override
	public void close()
	throws IOException {
		_decorated.close();
	}
}
