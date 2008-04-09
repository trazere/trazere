package com.trazere.util.lang;

import com.trazere.util.Assert;
import com.trazere.util.text.Describable;
import com.trazere.util.text.TextUtils;

public class HashCode
implements Describable {
	private int _hashCode;
	
	public HashCode(final Object object) {
		Assert.notNull(object);
		
		// Initialization.
		_hashCode = object.getClass().hashCode();
	}
	
	public HashCode append(final boolean value) {
		_hashCode = _hashCode * 31 + (value ? 1 : 0);
		return this;
	}
	
	public HashCode append(final int value) {
		_hashCode = _hashCode * 31 + value;
		return this;
	}
	
	public HashCode append(final long value) {
		_hashCode = _hashCode * 31 + (int) (value >> 32);
		_hashCode = _hashCode * 31 + (int) (value & 0xFFFFFFFF);
		return this;
	}
	
	public HashCode append(final Object value) {
		if (null != value) {
			_hashCode = _hashCode * 31 + value.hashCode();
		}
		return this;
	}
	
	public int get() {
		return _hashCode;
	}
	
	@Override
	public String toString() {
		return TextUtils.computeDescription(this);
	}
	
	public void fillDescription(final StringBuilder builder) {
		builder.append(" - Hash code = ").append(_hashCode);
	}
}
