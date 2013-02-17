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
package com.trazere.util.io;

import com.trazere.util.lang.HashCode;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * The {@link FileInput} class implements data inputs based on files.
 */
public class FileInput
implements Input {
	/** File to read. */
	protected final File _file;
	
	/**
	 * Instanciate a new data input with the given file.
	 * 
	 * @param file File to read.
	 */
	public FileInput(final File file) {
		assert null != file;
		
		// Initialization.
		_file = file;
	}
	
	/**
	 * Get the file read by the receiver data input.
	 * 
	 * @return The file.
	 */
	public File getFile() {
		return _file;
	}
	
	@Override
	public boolean exists()
	throws IOException {
		return _file.exists();
	}
	
	@Override
	public InputStream open()
	throws IOException {
		return new FileInputStream(_file);
	}
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_file);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final FileInput input = (FileInput) object;
			return _file.equals(input._file);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return _file.toString();
	}
}
