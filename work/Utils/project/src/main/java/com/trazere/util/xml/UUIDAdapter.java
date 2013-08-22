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
package com.trazere.util.xml;

import java.util.UUID;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * The {@link UUIDAdapter} class provides JAXB adpater for UUID.
 */
public class UUIDAdapter
extends XmlAdapter<String, UUID> {
	@Override
	public UUID unmarshal(final String value) {
		return DatatypeConverters.parseUUID(value);
	}
	
	@Override
	public String marshal(final UUID value) {
		return DatatypeConverters.printUUID(value);
	}
}
