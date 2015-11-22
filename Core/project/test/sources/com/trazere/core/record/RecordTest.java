package com.trazere.core.record;

import com.trazere.core.util.Maybe;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class RecordTest {
	private static final TestFieldKey<String> KEY1 = new TestFieldKey<>("key1", String.class, false);
	private static final TestFieldKey<Integer> KEY2 = new TestFieldKey<>("key2", Integer.class, false);
	private static final TestFieldKey<String> KEY3 = new TestFieldKey<>("key3", String.class, true);
	
	public void test1() {
		// Build.
		final RecordBuilder<TestFieldKey<?>, ?> builder = new SimpleRecordBuilder<>();
		builder.set(KEY1, "v1");
		builder.set(KEY2, 42);
		final Record<TestFieldKey<?>> record = builder.build();
		
		// Tests.
		Assert.assertEquals(record.size(), 2, "record size");
		
		Assert.assertTrue(record.contains(KEY1), "record contains KEY1");
		Assert.assertFalse(record.contains(KEY3), "record does not contain KEY3");
		
		final Maybe<String> get1 = record.get(KEY1);
		Assert.assertEquals((Object) get1, Maybe.some("v1"), "value of KEY1");
		final Maybe<Integer> get2 = record.get(KEY2);
		Assert.assertEquals((Object) get2, Maybe.some(42), "value of KEY2");
		final Maybe<String> key3 = record.get(KEY3);
		Assert.assertEquals((Object) key3, Maybe.none(), "value of KEY3");
	}
}
