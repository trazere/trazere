/*
 *  Copyright 2006-2011 Julien Dufour
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

import com.trazere.util.lang.Decorator;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * The {@link DecoratorXMLStreamWriter} class implements decorators of XML stream writers.
 */
public class DecoratorXMLStreamWriter
extends Decorator<XMLStreamWriter>
implements XMLStreamWriter {
	public DecoratorXMLStreamWriter(final XMLStreamWriter writer) {
		super(writer);
	}
	
	public XMLStreamWriter getWriter() {
		return _decorated;
	}
	
	public Object getProperty(final String name)
	throws IllegalArgumentException {
		return _decorated.getProperty(name);
	}
	
	public NamespaceContext getNamespaceContext() {
		return _decorated.getNamespaceContext();
	}
	
	public void setNamespaceContext(final NamespaceContext context)
	throws XMLStreamException {
		_decorated.setNamespaceContext(context);
	}
	
	public String getPrefix(final String uri)
	throws XMLStreamException {
		return _decorated.getPrefix(uri);
	}
	
	public void setPrefix(final String prefix, final String uri)
	throws XMLStreamException {
		_decorated.setPrefix(prefix, uri);
	}
	
	public void setDefaultNamespace(final String uri)
	throws XMLStreamException {
		_decorated.setDefaultNamespace(uri);
	}
	
	public void writeStartDocument()
	throws XMLStreamException {
		_decorated.writeStartDocument();
	}
	
	public void writeStartDocument(final String version)
	throws XMLStreamException {
		_decorated.writeStartDocument(version);
	}
	
	public void writeStartDocument(final String encoding, final String version)
	throws XMLStreamException {
		_decorated.writeStartDocument(encoding, version);
	}
	
	public void writeEndDocument()
	throws XMLStreamException {
		_decorated.writeEndDocument();
	}
	
	public void writeDTD(final String dtd)
	throws XMLStreamException {
		_decorated.writeDTD(dtd);
	}
	
	public void writeEntityRef(final String name)
	throws XMLStreamException {
		_decorated.writeEntityRef(name);
	}
	
	public void writeProcessingInstruction(final String target)
	throws XMLStreamException {
		_decorated.writeProcessingInstruction(target);
	}
	
	public void writeProcessingInstruction(final String target, final String data)
	throws XMLStreamException {
		_decorated.writeProcessingInstruction(target, target);
	}
	
	public void writeEmptyElement(final String localName)
	throws XMLStreamException {
		_decorated.writeEmptyElement(localName);
	}
	
	public void writeEmptyElement(final String namespaceURI, final String localName)
	throws XMLStreamException {
		_decorated.writeEmptyElement(namespaceURI, localName);
	}
	
	public void writeEmptyElement(final String prefix, final String localName, final String namespaceURI)
	throws XMLStreamException {
		_decorated.writeEmptyElement(prefix, localName, namespaceURI);
	}
	
	public void writeStartElement(final String localName)
	throws XMLStreamException {
		_decorated.writeStartElement(localName);
	}
	
	public void writeStartElement(final String namespaceURI, final String localName)
	throws XMLStreamException {
		_decorated.writeStartElement(namespaceURI, localName);
	}
	
	public void writeStartElement(final String prefix, final String localName, final String namespaceURI)
	throws XMLStreamException {
		_decorated.writeStartElement(prefix, localName, namespaceURI);
	}
	
	public void writeEndElement()
	throws XMLStreamException {
		_decorated.writeEndElement();
	}
	
	public void writeDefaultNamespace(final String namespaceURI)
	throws XMLStreamException {
		_decorated.writeDefaultNamespace(namespaceURI);
	}
	
	public void writeNamespace(final String prefix, final String namespaceURI)
	throws XMLStreamException {
		_decorated.writeNamespace(prefix, namespaceURI);
	}
	
	public void writeAttribute(final String localName, final String value)
	throws XMLStreamException {
		_decorated.writeAttribute(localName, value);
	}
	
	public void writeAttribute(final String namespaceURI, final String localName, final String value)
	throws XMLStreamException {
		_decorated.writeAttribute(namespaceURI, localName, value);
	}
	
	public void writeAttribute(final String prefix, final String namespaceURI, final String localName, final String value)
	throws XMLStreamException {
		_decorated.writeAttribute(prefix, namespaceURI, localName, value);
	}
	
	public void writeCData(final String data)
	throws XMLStreamException {
		_decorated.writeCData(data);
	}
	
	public void writeCharacters(final String text)
	throws XMLStreamException {
		_decorated.writeCharacters(text);
	}
	
	public void writeCharacters(final char[] text, final int start, final int len)
	throws XMLStreamException {
		_decorated.writeCharacters(text, start, len);
	}
	
	public void writeComment(final String data)
	throws XMLStreamException {
		_decorated.writeComment(data);
	}
	
	public void flush()
	throws XMLStreamException {
		_decorated.flush();
	}
	
	public void close()
	throws XMLStreamException {
		_decorated.close();
	}
}
