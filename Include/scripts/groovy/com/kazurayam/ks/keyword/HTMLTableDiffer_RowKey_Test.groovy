package com.kazurayam.ks.keyword

import static org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.kazurayam.ks.keyword.HTMLTableDiffer.RowKey

@RunWith(JUnit4.class)

public class HTMLTableDiffer_RowKey_Test {

	@Test
	public void test_constructor() {
		List<String> row = ["a", "b", "c"]
		Range keyRange = 0..1
		RowKey rowKey = new RowKey(row, keyRange)
		assertNotNull(rowKey)
		assertEquals(keyRange, rowKey.keyRange())
	}

	@Test
	public void test_keyElements() {
		List<String> row = ["a", "b", "c"]
		Range keyRange = 0..1
		RowKey rowKey = new RowKey(row, keyRange)
		assertNotNull(rowKey)
		assertEquals(["a", "b"], rowKey.keyElements())
	}

	@Test
	public void test_equals_true() {
		List<String> row1 = ["a", "b", "c"]
		List<String> row2 = ["a", "b", "c"]
		RowKey rk1 = new RowKey(row1, 0..1)
		RowKey rk2 = new RowKey(row2, 0..1)
		assertEquals(rk1, rk2)
	}

	@Test
	public void test_equals_false_by_value() {
		List<String> row1 = ["a", "b", "c"]
		List<String> row2 = ["A", "b", "c"]
		RowKey rk1 = new RowKey(row1, 0..1)
		RowKey rk2 = new RowKey(row2, 0..1)
		assertNotEquals(rk1, rk2)
	}

	@Test
	public void test_equals_false_by_keyRange() {
		List<String> row1 = ["a", "b", "c"]
		List<String> row2 = ["a", "b", "c"]
		RowKey rk1 = new RowKey(row1, 0..1)
		RowKey rk2 = new RowKey(row2, 0..2)
		assertNotEquals(rk1, rk2)
	}

	@Test
	public void test_hashCode() {
		List<String> row1 = ["a", "b", "c"]
		RowKey rk1 = new RowKey(row1, 0..1)
		assertTrue(rk1.hashCode() > 0)
	}

	@Test
	public void test_toString() {
		List<String> row1 = ["a", "b", "c"]
		RowKey rk1 = new RowKey(row1, 0..1)
		assertEquals("[\"a\",\"b\"]", rk1.toString())
	}

	@Test
	public void test_compareTo_0() {
		List<String> row1 = ["a", "b", "c"]
		List<String> row2 = ["a", "b", "c"]
		RowKey rk1 = new RowKey(row1, 0..1)
		RowKey rk2 = new RowKey(row2, 0..1)
		assertEquals(0, rk1.compareTo(rk2))
	}

	@Test
	public void test_compareTo_minus() {
		List<String> row1 = ["a", "b", "c"]
		List<String> row2 = ["b", "c", "d"]
		RowKey rk1 = new RowKey(row1, 0..1)
		RowKey rk2 = new RowKey(row2, 0..1)
		assertEquals(-1, rk1.compareTo(rk2))
	}

	@Test
	public void test_compareTo_plus() {
		List<String> row1 = ["b", "c", "d"]
		List<String> row2 = ["a", "b", "c"]
		RowKey rk1 = new RowKey(row1, 0..1)
		RowKey rk2 = new RowKey(row2, 0..1)
		assertEquals(1, rk1.compareTo(rk2))
	}

	@Test
	public void test_compareTo_shorter() {
		List<String> row1 = ["a", "b", "c"]
		List<String> row2 = ["a", "b", "c"]
		RowKey rk1 = new RowKey(row1, 0..0)
		RowKey rk2 = new RowKey(row2, 0..1)
		assertEquals(-1, rk1.compareTo(rk2))
	}

	@Test
	public void test_compareTo_longer() {
		List<String> row1 = ["a", "b", "c"]
		List<String> row2 = ["a", "b", "c"]
		RowKey rk1 = new RowKey(row1, 0..1)
		RowKey rk2 = new RowKey(row2, 0..0)
		assertEquals(1, rk1.compareTo(rk2))
	}
}