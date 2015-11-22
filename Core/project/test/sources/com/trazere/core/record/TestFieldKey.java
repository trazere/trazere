package com.trazere.core.record;

public final class TestFieldKey<V>
extends StrongFieldKey<TestFieldKey<?>, V> {
	public TestFieldKey(final String label, final Class<V> type, final boolean nullable) {
		super(label, type, nullable);
	}
	
	public TestFieldKey(final String label, final Class<V> type) {
		super(label, type);
	}
}
