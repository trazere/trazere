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
package com.trazere.xml;

import com.trazere.core.lang.MutableInt;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * The {@link IndentingXMLStreamWriter} class implements decorators of {@link XMLStreamWriter XML stream writers} that properly indent the output according to
 * the document structure.
 * 
 * @since 1.0
 */
public class IndentingXMLStreamWriter
extends DecoratorXMLStreamWriter {
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
	
	// Indentation.
	
	/** Indentation string. */
	private final String _indentation;
	
	/**
	 * Gets the indentation string.
	 * 
	 * @return The identation string.
	 * @since 1.0
	 */
	public String getIndentation() {
		return _indentation;
	}
	
	/** Current indentation level. */
	private final MutableInt _level = new MutableInt(0);
	
	/**
	 * Gets the current indentation level.
	 * 
	 * @return The current level of indentation.
	 * @since 1.0
	 */
	public int getLevel() {
		return _level.get();
	}
	
	/** Current level of mix content. */
	private final MutableInt _mixContentLevel = new MutableInt(-1);
	
	/**
	 * Indicates whether the current content is mixed or not.
	 * 
	 * @return <code>true</code> when the content is mixed, <code>false</code> otherwise.
	 */
	private boolean isMixedContent() {
		return _mixContentLevel.get() >= 0;
	}
	
	/**
	 * Marks that the content as mixed for the current indentation level.
	 */
	private void setMixedContent() {
		if (!isMixedContent()) {
			_mixContentLevel.set(_level.get());
		}
	}
	
	/**
	 * Increases the level of indentation.
	 * 
	 * @since 1.0
	 */
	protected void indent() {
		// Increase the current indentation level.
		_level.add(1);
	}
	
	/**
	 * Decreases the level of indentation.
	 * 
	 * @since 1.0
	 */
	protected void unindent() {
		// Decrease the current indentation level.
		_level.sub(1);
		
		// Mar Ends mixed content if needed.
		final int level = _level.get();
		if (level < _mixContentLevel.get()) {
			_mixContentLevel.set(-1);
		}
	}
	
	/**
	 * Writes some indentation according to the current level.
	 * 
	 * @throws XMLStreamException On failure.
	 * @since 1.0
	 */
	protected void writeIndentation()
	throws XMLStreamException {
		final int level = _level.get();
		for (int index = 0; index < level; index += 1) {
			super.writeCharacters(_indentation);
		}
	}
	
	/**
	 * The {@link Write} interface defines write effects.
	 * 
	 * @since 1.0
	 */
	protected static interface Write {
		/**
		 * Writes some content.
		 * 
		 * @throws XMLStreamException On failure.
		 */
		public void write()
		throws XMLStreamException;
	}
	
	/**
	 * Writes an indentated content.
	 * 
	 * @param content Content to write.
	 * @throws XMLStreamException On failure.
	 * @since 1.0
	 */
	protected void writeIndentated(final Write content)
	throws XMLStreamException {
		// Write an EOL and an indentation if needed.
		if (!isMixedContent()) {
			super.writeCharacters("\n");
			writeIndentation();
		}
		
		// Write the content.
		content.write();
	}
	
	/**
	 * Writes an indentated content start.
	 * 
	 * @param content Content start to write.
	 * @throws XMLStreamException On failure.
	 * @since 1.0
	 */
	protected void writeIndentatedStart(final Write content)
	throws XMLStreamException {
		// Write an EOL and an indentation if needed.
		if (!isMixedContent()) {
			super.writeCharacters("\n");
			writeIndentation();
		}
		
		// Write the start.
		content.write();
		
		// Increase the indentation.
		indent();
	}
	
	/**
	 * Writes an indentated content end.
	 * 
	 * @param content Content end to write.
	 * @throws XMLStreamException On failure.
	 * @since 1.0
	 */
	protected void writeIndentatedEnd(final Write content)
	throws XMLStreamException {
		// Decrease the indentation.
		final boolean mixed = isMixedContent();
		unindent();
		
		// Write an EOL and an indentation if needed.
		if (!mixed) {
			super.writeCharacters("\n");
			writeIndentation();
		}
		
		// Write the end.
		content.write();
	}
	
	@Override
	public void writeProcessingInstruction(final String target)
	throws XMLStreamException {
		writeIndentated(() -> {
			super.writeProcessingInstruction(target);
		});
	}
	
	@Override
	public void writeProcessingInstruction(final String target, final String data)
	throws XMLStreamException {
		writeIndentated(() -> {
			super.writeProcessingInstruction(target, data);
		});
	}
	
	@Override
	public void writeEmptyElement(final String localName)
	throws XMLStreamException {
		writeIndentated(() -> {
			super.writeEmptyElement(localName);
		});
	}
	
	@Override
	public void writeEmptyElement(final String namespaceURI, final String localName)
	throws XMLStreamException {
		writeIndentated(() -> {
			super.writeEmptyElement(namespaceURI, localName);
		});
	}
	
	@Override
	public void writeEmptyElement(final String prefix, final String localName, final String namespaceURI)
	throws XMLStreamException {
		writeIndentated(() -> {
			super.writeEmptyElement(prefix, localName, namespaceURI);
		});
	}
	
	@Override
	public void writeStartElement(final String localName)
	throws XMLStreamException {
		writeIndentatedStart(() -> {
			super.writeStartElement(localName);
		});
	}
	
	@Override
	public void writeStartElement(final String namespaceURI, final String localName)
	throws XMLStreamException {
		writeIndentatedStart(() -> {
			super.writeStartElement(namespaceURI, localName);
		});
	}
	
	@Override
	public void writeStartElement(final String prefix, final String localName, final String namespaceURI)
	throws XMLStreamException {
		writeIndentatedStart(() -> {
			super.writeStartElement(prefix, localName, namespaceURI);
		});
	}
	
	@Override
	public void writeEndElement()
	throws XMLStreamException {
		writeIndentatedEnd(() -> {
			super.writeEndElement();
		});
	}
	
	@Override
	public void writeCharacters(final String text)
	throws XMLStreamException {
		// Write the characters.
		super.writeCharacters(text);
		
		// Mark content as mixed.
		setMixedContent();
	}
	
	@Override
	public void writeCharacters(final char[] text, final int start, final int len)
	throws XMLStreamException {
		// Write the characters.
		super.writeCharacters(text, start, len);
		
		// Mark content as mixed.
		setMixedContent();
	}
}
