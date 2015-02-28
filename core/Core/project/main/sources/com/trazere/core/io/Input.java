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

import java.io.IOException;
import java.io.InputStream;

/**
 * The {@link Input} interface defines abstract data inputs.
 * <p>
 * Inputs may or may not be opened several times depending on the implementation.
 */
public interface Input {
	/**
	 * Indicates whether this underlying data of this input exists or not.
	 * <p>
	 * Non-existant inputs cannot be successfully opened.
	 * 
	 * @return <code>true</code> when the source exists, <code>false</code> otherwise.
	 * @throws IOException When the test fails.
	 */
	public boolean exists()
	throws IOException;
	
	/**
	 * Opens this input for reading.
	 * <p>
	 * This method should fail when the underlying data of this input does not {@link #exists() exists}.
	 * 
	 * @return An input stream providing the data.
	 * @throws IOException When the input cannot be opened.
	 */
	public InputStream open()
	throws IOException;
}
