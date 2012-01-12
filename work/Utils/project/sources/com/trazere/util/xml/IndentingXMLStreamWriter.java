/*
 *  Copyright 2006-2012 Julien Dufour
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

import com.trazere.util.lang.MutableInt;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * The {@link IndentingXMLStreamWriter} class represents decorators of XML stream writers which indent output according to the document structure.
 */
public class IndentingXMLStreamWriter
extends DecoratorXMLStreamWriter {
	private final String _indentation;
	private final MutableInt _level = new MutableInt(0);
	private final MutableInt _mixLevel = new MutableInt(0);
	
	public IndentingXMLStreamWriter(final XMLStreamWriter writer) {
		this(writer, "\t");
	}
	
	public IndentingXMLStreamWriter(final XMLStreamWriter writer, final String indentation) {
		super(writer);
		
		// Checks.
		assert null != indentation;
		
		// Initialization.
		_indentation = indentation;
	}
	
	@Override
	public void writeEmptyElement(final String localName)
	throws XMLStreamException {
		if (_mixLevel.get() == 0) {
			super.writeCharacters("\n");
			writeIndentation();
		}
		super.writeEmptyElement(localName);
	}
	
	@Override
	public void writeEmptyElement(final String namespaceURI, final String localName)
	throws XMLStreamException {
		if (_mixLevel.get() == 0) {
			super.writeCharacters("\n");
			writeIndentation();
		}
		super.writeEmptyElement(namespaceURI, localName);
	}
	
	@Override
	public void writeEmptyElement(final String prefix, final String localName, final String namespaceURI)
	throws XMLStreamException {
		if (_mixLevel.get() == 0) {
			super.writeCharacters("\n");
			writeIndentation();
		}
		super.writeEmptyElement(prefix, localName, namespaceURI);
	}
	
	@Override
	public void writeStartElement(final String localName)
	throws XMLStreamException {
		if (_mixLevel.get() == 0) {
			super.writeCharacters("\n");
			writeIndentation();
		}
		super.writeStartElement(localName);
		
		indent();
	}
	
	@Override
	public void writeStartElement(final String namespaceURI, final String localName)
	throws XMLStreamException {
		if (_mixLevel.get() == 0) {
			super.writeCharacters("\n");
			writeIndentation();
		}
		super.writeStartElement(namespaceURI, localName);
		
		indent();
	}
	
	@Override
	public void writeStartElement(final String prefix, final String localName, final String namespaceURI)
	throws XMLStreamException {
		writeIndentation();
		super.writeStartElement(prefix, localName, namespaceURI);
		
		indent();
	}
	
	@Override
	public void writeEndElement()
	throws XMLStreamException {
		final boolean mixed = _mixLevel.get() > 0;
		unindent();
		
		if (!mixed) {
			super.writeCharacters("\n");
			writeIndentation();
		}
		super.writeEndElement();
	}
	
	protected void indent() {
		_level.add(1);
	}
	
	protected void unindent() {
		_level.sub(1);
		
		final int level = _level.get();
		if (level < _mixLevel.get()) {
			_mixLevel.set(0);
		}
	}
	
	protected void writeIndentation()
	throws XMLStreamException {
		final int level = _level.get();
		for (int index = 0; index < level; index += 1) {
			super.writeCharacters(_indentation);
		}
	}
	
	@Override
	public void writeCharacters(final String text)
	throws XMLStreamException {
		super.writeCharacters(text);
		
		// Mark mixed.
		setMixed();
	}
	
	@Override
	public void writeCharacters(final char[] text, final int start, final int len)
	throws XMLStreamException {
		super.writeCharacters(text, start, len);
		
		// Mark mixed.
		setMixed();
	}
	
	protected void setMixed() {
		if (_mixLevel.get() == 0) {
			_mixLevel.set(_level.get());
		}
	}
}
