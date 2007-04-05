/*
 *  Copyright 2006 Julien Dufour
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
package com.trazere.util.type;

import com.trazere.util.ObjectUtils;
import com.trazere.util.text.Descriptable;
import com.trazere.util.text.TextUtils;

/**
 * The <code>Either</code> class represents the <em>Either</em> algebraic data type which wraps a disjonction value.
 * 
 * @param <Left> Type of the left value.
 * @param <Right> Type of the right value.
 */
public abstract class Either<Left, Right>
implements Descriptable {
	/**
	 * The <code>Tag</code> enumeration represents the various constructors of the algebraic data type.
	 */
	public static enum Tag {
		LEFT,
		RIGHT,
	}

	/**
	 * Build a new instance using the <code>LEFT</code> constuctor.
	 * 
	 * @param <Left> Type of the left value.
	 * @param <Right> Type of the right value.
	 * @param left Left value to wrap.
	 * @return The instance.
	 */
	public static <Left, Right> Either<Left, Right> left(final Left left) {
		return new Either<Left, Right>() {
			@Override
			public Tag getTag() {
				return Tag.LEFT;
			}

			@Override
			public boolean isLeft() {
				return true;
			}

			@Override
			public boolean isRight() {
				return false;
			}

			@Override
			public Left getLeft() {
				return left;
			}

			@Override
			public Right getRight() {
				throw new UnsupportedOperationException();
			}

			@Override
			public int hashCode() {
				int result = getClass().hashCode();
				if (null != left) {
					result = result * 31 + left.hashCode();
				}
				return result;
			}

			@Override
			public boolean equals(final Object object) {
				if (this == object) {
					return true;
				} else if (null != object && getClass().equals(object.getClass())) {
					final Either<?, ?> either = (Either<?, ?>) object;
					return ObjectUtils.equals(left, either.getLeft());
				} else {
					return false;
				}
			}

			public void fillDescription(final StringBuilder builder) {
				builder.append(" - Left = ").append(left);
			}
		};
	}

	/**
	 * Build a new instance using the <code>RIGHT</code> constuctor.
	 * 
	 * @param <Left> Type of the left value.
	 * @param <Right> Type of the right value.
	 * @param right Right value to wrap.
	 * @return The instance.
	 */
	public static <Left, Right> Either<Left, Right> right(final Right right) {
		return new Either<Left, Right>() {
			@Override
			public Tag getTag() {
				return Tag.RIGHT;
			}

			@Override
			public boolean isLeft() {
				return false;
			}

			@Override
			public boolean isRight() {
				return true;
			}

			@Override
			public Left getLeft() {
				throw new UnsupportedOperationException();
			}

			@Override
			public Right getRight() {
				return right;
			}

			@Override
			public int hashCode() {
				int result = getClass().hashCode();
				if (null != right) {
					result = result * 31 + right.hashCode();
				}
				return result;
			}

			@Override
			public boolean equals(final Object object) {
				if (this == object) {
					return true;
				} else if (null != object && getClass().equals(object.getClass())) {
					final Either<?, ?> either = (Either<?, ?>) object;
					return ObjectUtils.equals(right, either.getRight());
				} else {
					return false;
				}
			}

			public void fillDescription(final StringBuilder builder) {
				builder.append(" - Right = ").append(right);
			}
		};
	}

	/**
	 * Get the algebraic type of the receiver instance.
	 * 
	 * @return The tag.
	 */
	public abstract Tag getTag();

	/**
	 * Test wether the receiver instance has been built using the <code>LEFT</code> constructor.
	 * 
	 * @return <code>true</code> when the instance has been built with the <code>LEFT</code> constructor, <code>false</code> otherwise.
	 */
	public abstract boolean isLeft();

	/**
	 * Test wether the receiver instance has been built using the <code>RIGHT</code> constructor.
	 * 
	 * @return <code>true</code> when the instance has been built with the <code>RIGHT</code> constructor, <code>false</code> otherwise.
	 */
	public abstract boolean isRight();

	/**
	 * Get the left value wrapped in the receiver instance.
	 * <p>
	 * This method should only be called with instances built with the <code>LEFT</code> constructor.
	 * 
	 * @return The wrapped value.
	 * @throws UnsupportedOperationException when the receiver instance has not been built using the <code>LEFT</code> constuctor.
	 */
	public abstract Left getLeft();

	/**
	 * Get the right value wrapped in the receiver instance.
	 * <p>
	 * This method should only be called with instances built with the <code>RIGHT</code> constructor.
	 * 
	 * @return The wrapped value.
	 * @throws UnsupportedOperationException when the receiver instance has not been built using the <code>RIGHT</code> constuctor.
	 */
	public abstract Right getRight();

	@Override
	public final String toString() {
		return TextUtils.computeDescription(this);
	}
}
