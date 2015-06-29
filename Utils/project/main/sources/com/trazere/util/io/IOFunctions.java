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

import com.trazere.util.function.Function1;
import java.io.File;

/**
 * The {@link IOFunctions} class provides factories of functions regarding I/O.
 * 
 * @deprecated Use {@link com.trazere.core}.
 */
@Deprecated
public class IOFunctions {
	/**
	 * Builds a function that builds files.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 * @deprecated Use {@link com.trazere.core.io.IOFunctions#file()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Function1<String, File, X> file() {
		return (Function1<String, File, X>) _FILE;
	}
	
	private static Function1<String, File, ?> _FILE = new Function1<String, File, RuntimeException>() {
		@Override
		public File evaluate(final String path) {
			return new File(path);
		}
	};
	
	private IOFunctions() {
		// Prevents instantiation.
	}
}
