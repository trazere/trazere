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
package com.trazere.core.collection;

import com.trazere.core.text.Describable;
import com.trazere.core.text.DescriptionBuilder;
import com.trazere.core.text.TextUtils;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.NoSuchElementException;

/**
 * The {@link BaseMemoizedFeed} class provides a skeleton implementation of feeds that memoize their head and tail.
 * 
 * @param <E> Type of the elements.
 * @since 2.0
 */
public abstract class BaseMemoizedFeed<E>
implements MemoizedFeed<E>, Describable {
	/**
	 * Indicates whether the head and tail have been computed or not.
	 * 
	 * @since 2.0
	 */
	protected boolean _evaluated = false;
	
	/**
	 * Memoized head and tail of the feed.
	 * 
	 * @since 2.0
	 */
	protected Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> _item = null;
	
	/**
	 * Evaluates and memoizes the head and tail of this feed.
	 * 
	 * @return The head and tail.
	 * @since 2.0
	 */
	protected Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> evaluate() {
		if (!_evaluated) {
			_item = compute();
			_evaluated = true;
		}
		return _item;
	}
	
	/**
	 * Computes the head and tail of this feed.
	 * 
	 * @return The computed head and tail.
	 * @since 2.0
	 */
	protected abstract Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> compute();
	
	@Override
	public boolean isEmpty() {
		return evaluate().isNone();
	}
	
	@Override
	public E head()
	throws NoSuchElementException {
		return item().get1();
	}
	
	@Override
	public Maybe<E> optionalHead() {
		return optionalItem().map(Tuple2::get1);
	}
	
	@Override
	public Feed<? extends E> tail()
	throws NoSuchElementException {
		return item().get2();
	}
	
	@Override
	public Maybe<? extends Feed<? extends E>> optionalTail() {
		return optionalItem().map(Tuple2::get2);
	}
	
	@Override
	public Tuple2<? extends E, ? extends Feed<? extends E>> item()
	throws NoSuchElementException {
		return evaluate().get(() -> {
			throw new NoSuchElementException();
		});
	}
	
	@Override
	public Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>> optionalItem() {
		return evaluate();
	}
	
	@Override
	public boolean isMemoized() {
		return _evaluated;
	}
	
	@Override
	public Maybe<Maybe<? extends Tuple2<? extends E, ? extends Feed<? extends E>>>> probe() {
		return _evaluated ? Maybe.some(_item) : Maybe.none();
	}
	
	// Object.
	
	@Override
	public String toString() {
		if (_evaluated) {
			return String.valueOf(_item);
		} else {
			return TextUtils.description(this);
		}
	}
	
	@Override
	public void appendDescription(final DescriptionBuilder description) {
		// Nothing to do.
	}
}
