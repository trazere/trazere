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
package com.trazere.util.xml;

import com.trazere.util.lang.Decorator;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * The {@link DecoratorXMLStreamWriter} class implements decorators of XML stream writers.
 * 
 * @deprecated Use {@link com.trazere.xml.DecoratorXMLStreamWriter}.
 */
@Deprecated
public class DecoratorXMLStreamWriter
extends Decorator<XMLStreamWriter>
implements XMLStreamWriter {
	public DecoratorXMLStreamWriter(final XMLStreamWriter writer) {
		super(writer);
	}
	
	public XMLStreamWriter getWriter() {
		return _decorated;
	}
	
	@Override
	public Object getProperty(final String name)
	throws IllegalArgumentException {
		return _decorated.getProperty(name);
	}
	
	@Override
	public NamespaceContext getNamespaceContext() {
		return _decorated.getNamespaceContext();
	}
	
	@Override
	public void setNamespaceContext(final NamespaceContext context)
	throws XMLStreamException {
		_decorated.setNamespaceContext(context);
	}
	
	@Override
	public String getPrefix(final String uri)
	throws XMLStreamException {
		return _decorated.getPrefix(uri);
	}
	
	@Override
	public void setPrefix(final String prefix, final String uri)
	throws XMLStreamException {
		_decorated.setPrefix(prefix, uri);
	}
	
	@Override
	public void setDefaultNamespace(final String uri)
	throws XMLStreamException {
		_decorated.setDefaultNamespace(uri);
	}
	
	@Override
	public void writeStartDocument()
	throws XMLStreamException {
		_decorated.writeStartDocument();
	}
	
	@Override
	public void writeStartDocument(final String version)
	throws XMLStreamException {
		_decorated.writeStartDocument(version);
	}
	
	@Override
	public void writeStartDocument(final String encoding, final String version)
	throws XMLStreamException {
		_decorated.writeStartDocument(encoding, version);
	}
	
	@Override
	public void writeEndDocument()
	throws XMLStreamException {
		_decorated.writeEndDocument();
	}
	
	@Override
	public void writeDTD(final String dtd)
	throws XMLStreamException {
		_decorated.writeDTD(dtd);
	}
	
	@Override
	public void writeEntityRef(final String name)
	throws XMLStreamException {
		_decorated.writeEntityRef(name);
	}
	
	@Override
	public void writeProcessingInstruction(final String target)
	throws XMLStreamException {
		_decorated.writeProcessingInstruction(target);
	}
	
	@Override
	public void writeProcessingInstruction(final String target, final String data)
	throws XMLStreamException {
		_decorated.writeProcessingInstruction(target, target);
	}
	
	@Override
	public void writeEmptyElement(final String localName)
	throws XMLStreamException {
		_decorated.writeEmptyElement(localName);
	}
	
	@Override
	public void writeEmptyElement(final String namespaceURI, final String localName)
	throws XMLStreamException {
		_decorated.writeEmptyElement(namespaceURI, localName);
	}
	
	@Override
	public void writeEmptyElement(final String prefix, final String localName, final String namespaceURI)
	throws XMLStreamException {
		_decorated.writeEmptyElement(prefix, localName, namespaceURI);
	}
	
	@Override
	public void writeStartElement(final String localName)
	throws XMLStreamException {
		_decorated.writeStartElement(localName);
	}
	
	@Override
	public void writeStartElement(final String namespaceURI, final String localName)
	throws XMLStreamException {
		_decorated.writeStartElement(namespaceURI, localName);
	}
	
	@Override
	public void writeStartElement(final String prefix, final String localName, final String namespaceURI)
	throws XMLStreamException {
		_decorated.writeStartElement(prefix, localName, namespaceURI);
	}
	
	@Override
	public void writeEndElement()
	throws XMLStreamException {
		_decorated.writeEndElement();
	}
	
	@Override
	public void writeDefaultNamespace(final String namespaceURI)
	throws XMLStreamException {
		_decorated.writeDefaultNamespace(namespaceURI);
	}
	
	@Override
	public void writeNamespace(final String prefix, final String namespaceURI)
	throws XMLStreamException {
		_decorated.writeNamespace(prefix, namespaceURI);
	}
	
	@Override
	public void writeAttribute(final String localName, final String value)
	throws XMLStreamException {
		_decorated.writeAttribute(localName, value);
	}
	
	@Override
	public void writeAttribute(final String namespaceURI, final String localName, final String value)
	throws XMLStreamException {
		_decorated.writeAttribute(namespaceURI, localName, value);
	}
	
	@Override
	public void writeAttribute(final String prefix, final String namespaceURI, final String localName, final String value)
	throws XMLStreamException {
		_decorated.writeAttribute(prefix, namespaceURI, localName, value);
	}
	
	@Override
	public void writeCData(final String data)
	throws XMLStreamException {
		_decorated.writeCData(data);
	}
	
	@Override
	public void writeCharacters(final String text)
	throws XMLStreamException {
		_decorated.writeCharacters(text);
	}
	
	@Override
	public void writeCharacters(final char[] text, final int start, final int len)
	throws XMLStreamException {
		_decorated.writeCharacters(text, start, len);
	}
	
	@Override
	public void writeComment(final String data)
	throws XMLStreamException {
		_decorated.writeComment(data);
	}
	
	@Override
	public void flush()
	throws XMLStreamException {
		_decorated.flush();
	}
	
	@Override
	public void close()
	throws XMLStreamException {
		_decorated.close();
	}
}
