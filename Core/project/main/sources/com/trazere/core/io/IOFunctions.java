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

import com.trazere.core.functional.Function;
import java.io.File;

/**
 * The {@link IOFunctions} class provides various factories of {@link Function functions} regarding I/O.
 * 
 * @see Function
 */
public class IOFunctions {
	/**
	 * Builds a function that builds files from paths.
	 * 
	 * @return The built function.
	 */
	public static Function<String, File> file() {
		return FILE;
	}
	
	private static Function<String, File> FILE = File::new;
	
	private IOFunctions() {
		// Prevents instantiation.
	}
}
