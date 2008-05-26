/*
 *  Copyright 2008 Julien Dufour
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

import java.io.IOException;
import java.io.InputStream;

/**
 * The {@link Input} interface defines data inputs.
 * <p>
 * It is basically a factory of input stream.
 */
public interface Input {
	/**
	 * Indicated wether the underlying data exist so that the stream can be opened.
	 * 
	 * @return <code>true</code> when the data exist, <code>false</code> otherwise.
	 * @throws IOException When the test cannot be done.
	 */
	public boolean exists()
	throws IOException;
	
	/**
	 * Open the input stream.
	 * 
	 * @return The built input stream.
	 * @throws IOException When the input stream cannot be built.
	 */
	public InputStream open()
	throws IOException;
}
