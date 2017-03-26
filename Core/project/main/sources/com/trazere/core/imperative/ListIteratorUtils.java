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
package com.trazere.core.imperative;

import com.trazere.core.collection.CollectionFactory;
import com.trazere.core.collection.Lists;
import com.trazere.core.functional.Function;
import com.trazere.core.functional.Predicate;
import com.trazere.core.util.Maybe;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * The {@link ListIteratorUtils} class provides various utilities regarding {@link ListIterator list iterators}.
 * 
 * @see Iterator
 * @since 2.0
 */
public class ListIteratorUtils {
	/**
	 * Gets the previous element provided by the given list iterator.
	 * <p>
	 * This method supports exhausted iterators.
	 * 
	 * @param <E> Type of the elements.
	 * @param iterator Iterator to read.
	 * @return The previous element, or nothing when the iterator is exhausted.
	 * @since 2.0
	 */
	public static <E> Maybe<E> optionalPrevious(final ListIterator<? extends E> iterator) {
		return iterator.hasPrevious() ? Maybe.some(iterator.previous()) : Maybe.none();
	}
	
	/**
	 * Takes n next elements provided by the given list iterator.
	 * <p>
	 * The elements are taken according their iteration order.
	 * <p>
	 * The built iterator feeds lazyly from the given iterator.
	 *
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the elements to take.
	 * @param n Number of elements to take.
	 * @return An iterator providing the taken elements.
	 * @since 2.0
	 */
	public static <E> ExListIterator<E> take(final ListIterator<E> iterator, final int n) {
		assert null != iterator;
		
		return new ExListIterator<E>() {
			private int _i = 0;
			private int _n = n;
			
			@Override
			public boolean hasNext() {
				return iterator.hasNext() && _i < _n;
			}
			
			@Override
			public E next() {
				if (_i < _n) {
					_i += 1;
					return iterator.next();
				} else {
					throw new NoSuchElementException();
				}
			}
			
			@Override
			public int nextIndex() {
				return _i;
			}
			
			@Override
			public boolean hasPrevious() {
				return _i > 0;
			}
			
			@Override
			public E previous() {
				if (_i > 0) {
					_i -= 1;
					return iterator.previous();
				} else {
					throw new NoSuchElementException();
				}
			}
			
			@Override
			public int previousIndex() {
				return _i - 1;
			}
			
			@Override
			public void remove() {
				iterator.remove();
				_n -= 1;
			}
			
			@Override
			public void set(final E e) {
				iterator.set(e);
			}
			
			@Override
			public void add(final E e) {
				iterator.add(e);
				_n += 1;
			}
		};
	}
	
	/**
	 * Drops n elements provided by the given list iterator.
	 * <p>
	 * The elements are dropped according their iteration order.
	 * <p>
	 * The built iterator feeds lazyly from the given iterator.
	 *
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the elements to drop.
	 * @param n Number of elements to drop.
	 * @return An iterator providing the remaining elements.
	 * @since 2.0
	 */
	public static <E> ExListIterator<E> drop(final ListIterator<E> iterator, final int n) {
		assert null != iterator;
		
		return new ExListIterator<E>() {
			private int _i = -n;
			
			@Override
			public boolean hasNext() {
				drop();
				return iterator.hasNext();
			}
			
			@Override
			public E next() {
				drop();
				return iterator.next();
			}
			
			@Override
			public int nextIndex() {
				return Math.max(0, _i);
			}
			
			@Override
			public boolean hasPrevious() {
				return _i > 0;
			}
			
			@Override
			public E previous() {
				drop();
				if (_i > 0) {
					_i -= 1;
					return iterator.previous();
				} else {
					throw new NoSuchElementException();
				}
			}
			
			@Override
			public int previousIndex() {
				return Math.max(-1, _i - 1);
			}
			
			@Override
			public void remove() {
				drop();
				iterator.remove();
			}
			
			@Override
			public void set(final E e) {
				drop();
				iterator.set(e);
			}
			
			@Override
			public void add(final E e) {
				drop();
				iterator.add(e);
			}
			
			private void drop() {
				while (_i < 0 && iterator.hasNext()) {
					iterator.next();
					_i += 1;
				}
			}
		};
	}
	
	/**
	 * Groups the elements provided by the given list iterator into batches of the given size.
	 * <p>
	 * The built iterator feeds lazyly from the given list iterator and is indexed on the batch containing the element with the current index.
	 * 
	 * @param <E> Type of the elements.
	 * @param <B> Type of the batch collections.
	 * @param iterator Iterator providing the elements to group.
	 * @param n Number of elements of each batch.
	 * @param batchFactory Factory of the batch collections.
	 * @return An iterator providing the batches of elements.
	 * @since 2.0
	 */
	public static <E, B extends Collection<? super E>> ExListIterator<B> group(final ListIterator<E> iterator, final int n, final CollectionFactory<? super E, B> batchFactory) {
		assert null != iterator;
		assert n > 0;
		assert null != batchFactory;
		
		return new ExListIterator<B>() {
			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}
			
			@Override
			public B next() {
				align();
				final B batch = batchFactory.build(n);
				do {
					batch.add(iterator.next());
				} while (batch.size() < n && iterator.hasNext());
				return batch;
			}
			
			@Override
			public int nextIndex() {
				return iterator.nextIndex() / n;
			}
			
			@Override
			public boolean hasPrevious() {
				align();
				return iterator.hasPrevious();
			}
			
			@Override
			public B previous() {
				align();
				final B batch = batchFactory.build(n);
				do {
					batch.add(iterator.previous());
				} while (batch.size() < n && 0 != (iterator.nextIndex() % n));
				return batch;
			}
			
			@Override
			public int previousIndex() {
				return iterator.nextIndex() / n - 1;
			}
			
			private void align() {
				if (iterator.hasNext()) { // Note: iterator is aligned when at the end
					while (0 != (iterator.nextIndex() % n)) {
						iterator.previous();
					}
				}
			}
		};
	}
	
	/**
	 * Filters the elements provided by the given list iterator using the given filter.
	 * <p>
	 * The built iterator feeds lazyly from the given iterator.
	 *
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the elements to filter.
	 * @param filter Predicate to use to filter the elements.
	 * @return An iterator providing the filtered elements.
	 * @since 2.0
	 */
	public static <E> ExListIterator<E> filter(final ListIterator<? extends E> iterator, final Predicate<? super E> filter) {
		assert null != iterator;
		assert null != filter;
		
		return new ExListIterator<E>() {
			@Override
			public boolean hasNext() {
				while (iterator.hasNext()) {
					if (filter.evaluate(iterator.next())) {
						iterator.previous();
						return true;
					}
				}
				return false;
			}
			
			@Override
			public E next() {
				while (iterator.hasNext()) {
					final E next = iterator.next();
					if (filter.evaluate(next)) {
						return next;
					}
				}
				throw new NoSuchElementException();
			}
			
			@Override
			public int nextIndex() {
				while (iterator.hasNext()) {
					if (filter.evaluate(iterator.next())) {
						iterator.previous();
						break;
					}
				}
				return iterator.nextIndex();
			}
			
			@Override
			public boolean hasPrevious() {
				while (iterator.hasPrevious()) {
					if (filter.evaluate(iterator.previous())) {
						iterator.next();
						return true;
					}
				}
				return false;
			}
			
			@Override
			public E previous() {
				while (iterator.hasPrevious()) {
					final E previous = iterator.previous();
					if (filter.evaluate(previous)) {
						return previous;
					}
				}
				throw new NoSuchElementException();
			}
			
			@Override
			public int previousIndex() {
				while (iterator.hasPrevious()) {
					if (filter.evaluate(iterator.previous())) {
						iterator.next();
						break;
					}
				}
				return iterator.previousIndex();
			}
		};
	}
	
	/**
	 * Transforms the elements provided by the given list iterator using the given function.
	 * <p>
	 * The built iterator feeds lazyly from the given iterator.
	 *
	 * @param <E> Type of the elements.
	 * @param <TE> Type of the transformed elements.
	 * @param iterator Iterator providing the elements to transform.
	 * @param function Function to use to transform the elements.
	 * @return An iterator providing the transformed elements.
	 * @since 2.0
	 */
	public static <E, TE> ExListIterator<TE> map(final ListIterator<? extends E> iterator, final Function<? super E, ? extends TE> function) {
		assert null != iterator;
		assert null != function;
		
		return new ExListIterator<TE>() {
			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}
			
			@Override
			public TE next() {
				return function.evaluate(iterator.next());
			}
			
			@Override
			public int nextIndex() {
				return iterator.nextIndex();
			}
			
			@Override
			public boolean hasPrevious() {
				return iterator.hasPrevious();
			}
			
			@Override
			public TE previous() {
				return function.evaluate(iterator.previous());
			}
			
			@Override
			public int previousIndex() {
				return iterator.previousIndex();
			}
			
			@Override
			public void remove() {
				iterator.remove();
			}
		};
	}
	
	/**
	 * Extracts the elements provided by the given list iterator using the given extractor.
	 * <p>
	 * The built iterator feeds lazyly from the given iterator.
	 *
	 * @param <E> Type of the elements.
	 * @param <EE> Type of the extracted elements.
	 * @param iterator Iterator providing the elements to extract from.
	 * @param extractor Function to use to extract from the elements.
	 * @return An iterator providing the extracted elements.
	 * @since 2.0
	 */
	public static <E, EE> ExListIterator<EE> extract(final ListIterator<? extends E> iterator, final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
		assert null != iterator;
		assert null != extractor;
		
		return new ExListIterator<EE>() {
			@Override
			public boolean hasNext() {
				while (iterator.hasNext()) {
					if (extractor.evaluate(iterator.next()).isSome()) {
						iterator.previous();
						return true;
					}
				}
				return false;
			}
			
			@Override
			public EE next() {
				while (iterator.hasNext()) {
					final Maybe<? extends EE> next = extractor.evaluate(iterator.next());
					if (next.isSome()) {
						return next.asSome().getValue();
					}
				}
				throw new NoSuchElementException();
			}
			
			@Override
			public int nextIndex() {
				while (iterator.hasNext()) {
					if (extractor.evaluate(iterator.next()).isSome()) {
						iterator.previous();
						break;
					}
				}
				return iterator.nextIndex();
			}
			
			@Override
			public boolean hasPrevious() {
				while (iterator.hasPrevious()) {
					if (extractor.evaluate(iterator.previous()).isSome()) {
						iterator.next();
						return true;
					}
				}
				return false;
			}
			
			@Override
			public EE previous() {
				while (iterator.hasPrevious()) {
					final Maybe<? extends EE> previous = extractor.evaluate(iterator.previous());
					if (previous.isSome()) {
						return previous.asSome().getValue();
					}
				}
				throw new NoSuchElementException();
			}
			
			@Override
			public int previousIndex() {
				while (iterator.hasPrevious()) {
					if (extractor.evaluate(iterator.previous()).isSome()) {
						iterator.next();
						break;
					}
				}
				return iterator.previousIndex();
			}
		};
	}
	
	/**
	 * Gets all elements extracted from the elements provided by the given list iterator using the given extractor.
	 * <p>
	 * The built iterator feeds lazyly from the given iterator.
	 *
	 * @param <E> Type of the elements.
	 * @param <TE> Type of the extracted elements.
	 * @param iterator Iterator providing the elements to extract from.
	 * @param extractor Function to use to extract from the elements.
	 * @return An iterator providing the extracted elements.
	 * @since 2.0
	 */
	public static <E, TE> ExListIterator<TE> extractAll(final ListIterator<? extends E> iterator, final Function<? super E, ? extends List<? extends TE>> extractor) {
		return flatMap(iterator, extractor.map(List::listIterator));
	}
	
	/**
	 * Appends the given list iterators together.
	 * <p>
	 * The built iterator feeds lazyly from the given iterators.
	 *
	 * @param <E> Type of the elements.
	 * @param iterator1 First iterator providing the elements to append.
	 * @param iterator2 Second iterator providing the elements to append.
	 * @return An iterator providing the appended elements.
	 * @since 2.0
	 */
	public static <E> ExListIterator<E> append(final ListIterator<? extends E> iterator1, final ListIterator<? extends E> iterator2) {
		assert null != iterator1;
		assert null != iterator2;
		
		return flatten(Lists.fromElements(iterator1, iterator2).listIterator());
	}
	
	/**
	 * Flattens the elements provided by the list iterators provided by the given list iterator.
	 * <p>
	 * The built iterator feeds lazyly from the given iterators.
	 *
	 * @param <E> Type of the elements.
	 * @param iterator Iterator providing the iterators providing the elements to flatten.
	 * @return An iterator providing the flatten elements.
	 * @since 2.0
	 */
	public static <E> ExListIterator<E> flatten(final ListIterator<? extends ListIterator<? extends E>> iterator) {
		assert null != iterator;
		
		return new ExListIterator<E>() {
			private ListIterator<? extends E> _iterator = ListIterators.empty();
			
			@Override
			public boolean hasNext() {
				lookAhead();
				return _iterator.hasNext();
			}
			
			@Override
			public E next() {
				lookAhead();
				return _iterator.next();
			}
			
			@Override
			public int nextIndex() {
				throw new UnsupportedOperationException("nextIndex");
			}
			
			private void lookAhead() {
				while (!_iterator.hasNext() && iterator.hasNext()) {
					_iterator = iterator.next();
					while (_iterator.hasPrevious()) {
						_iterator.previous();
					}
				}
			}
			
			@Override
			public boolean hasPrevious() {
				lookBehind();
				return _iterator.hasPrevious();
			}
			
			@Override
			public E previous() {
				lookBehind();
				return _iterator.previous();
			}
			
			@Override
			public int previousIndex() {
				throw new UnsupportedOperationException("nextIndex");
			}
			
			private void lookBehind() {
				while (!_iterator.hasPrevious() && iterator.hasPrevious()) {
					_iterator = iterator.next();
					while (_iterator.hasNext()) {
						_iterator.next();
					}
				}
			}
		};
	}
	
	/**
	 * Transforms and flattens the elements provided by the given list iterator using the given function.
	 * <p>
	 * The built iterator feeds lazyly from the given iterator.
	 *
	 * @param <E> Type of the elements.
	 * @param <TE> Type of the transformed elements.
	 * @param iterator Iterator providing the elements to transform.
	 * @param function Function to use to transform the elements.
	 * @return An iterator providing the flatten, transformed elements.
	 * @since 2.0
	 */
	public static <E, TE> ExListIterator<TE> flatMap(final ListIterator<? extends E> iterator, final Function<? super E, ? extends ListIterator<? extends TE>> function) {
		return flatten(map(iterator, function));
	}
	
	// TODO: zip ?
	
	/**
	 * Builds an unmodifiable view of the given list iterator.
	 *
	 * @param <E> Type of the elements.
	 * @param iterator Iterator to wrap.
	 * @return An unmodifiable view of the given iterator, or the given iterator when is it already unmodifiable.
	 * @since 2.0
	 */
	public static <E> ExListIterator<E> unmodifiable(final ListIterator<E> iterator) {
		assert null != iterator;
		
		return iterator instanceof UnmodifiableListIterator ? (UnmodifiableListIterator<E>) iterator : new UnmodifiableListIterator<>(iterator);
	}
	
	private static class UnmodifiableListIterator<E>
	extends ListIteratorDecorator<E> {
		public UnmodifiableListIterator(final ListIterator<E> decorated) {
			super(decorated);
		}
		
		// ListIterator.
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void set(final E e) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void add(final E e) {
			throw new UnsupportedOperationException();
		}
		
		// ExListIterator.
		
		@Override
		public ExListIterator<E> unmodifiable() {
			return this;
		}
		
		// Traversable.
		
		@Override
		public ExListIterator<E> take(final int n) {
			return super.take(n).unmodifiable();
		}
		
		@Override
		public ExListIterator<E> drop(final int n) {
			return super.drop(n).unmodifiable();
		}
		
		@Override
		public <B extends Collection<? super E>> ExListIterator<B> group(final int n, final CollectionFactory<? super E, B> batchFactory) {
			return super.group(n, batchFactory).unmodifiable();
		}
		
		@Override
		public ExListIterator<E> filter(final Predicate<? super E> filter) {
			return super.filter(filter).unmodifiable();
		}
		
		@Override
		public <TE> ExListIterator<TE> map(final Function<? super E, ? extends TE> function) {
			return super.<TE>map(function).unmodifiable();
		}
		
		@Override
		public <EE> ExListIterator<EE> extract(final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
			return super.<EE>extract(extractor).unmodifiable();
		}
		
		@Override
		public <EE> ExListIterator<EE> extractAll(final Function<? super E, ? extends List<? extends EE>> extractor) {
			return super.<EE>extractAll(extractor).unmodifiable();
		}
		
		@Override
		public <TE> ExListIterator<TE> flatMap(final Function<? super E, ? extends ListIterator<? extends TE>> extractor) {
			return super.<TE>flatMap(extractor).unmodifiable();
		}
	}
	
	private ListIteratorUtils() {
		// Prevent instantiation.
	}
}
