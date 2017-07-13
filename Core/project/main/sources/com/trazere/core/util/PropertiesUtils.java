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
package com.trazere.core.util;

import com.trazere.core.functional.Function;
import com.trazere.core.functional.Thunk;
import com.trazere.core.imperative.PairIterator;
import com.trazere.core.io.Input;
import com.trazere.core.lang.PairIterable;
import com.trazere.core.record.FieldUtils;
import com.trazere.core.record.InvalidFieldException;
import com.trazere.core.record.MissingFieldException;
import com.trazere.core.text.TextSerializers;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;

/**
 * The {@link PropertiesUtils} class provides various utilities regarding {@link Properties properties}.
 * 
 * @see Properties
 * @since 2.0
 */
public class PropertiesUtils {
	/**
	 * Loads the properties from the given input in a simple line-oriented format into the given properties table.
	 * 
	 * @param properties Property table to populate.
	 * @param input Input providing the properties to load.
	 * @throws IOException When the input cannot be read.
	 * @see Properties#load(InputStream)
	 * @since 2.0
	 */
	public static void load(final Properties properties, final Input input)
	throws IOException {
		try (final InputStream stream = input.open()) {
			properties.load(stream);
		}
	}
	
	/**
	 * Loads the properties represented by the XML document from the given input into this properties table.
	 * 
	 * @param properties Property table to populate.
	 * @param input Input providing the properties to load.
	 * @throws IOException When the input cannot be read.
	 * @see Properties#load(InputStream)
	 * @since 2.0
	 */
	public static void loadFromXML(final Properties properties, final Input input)
	throws IOException {
		try (final InputStream stream = input.open()) {
			properties.loadFromXML(stream);
		}
	}
	
	private PropertiesUtils() {
		// Prevents instantiation.
	}
}
