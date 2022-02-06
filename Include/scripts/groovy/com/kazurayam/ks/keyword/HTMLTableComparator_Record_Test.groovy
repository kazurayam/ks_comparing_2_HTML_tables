package com.kazurayam.ks.keyword

import static org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.kazurayam.ks.keyword.HTMLTableComparator.Record
import com.kazurayam.ks.keyword.HTMLTableComparator.RowKey
import com.kazurayam.ks.keyword.HTMLTableComparator.RowValues

@RunWith(JUnit4.class)

public class HTMLTableComparator_Record_Test {

	@Test
	public void test_constructor() {
		List<String> row = ["a", "b", "c"]
		Range<Integer> keyRange = 0..1
		Record rec = new Record(row, keyRange)
		assertNotNull(rec)
	}

	@Test
	public void test_rowValues() {
		List<String> row = ["a", "b", "c"]
		Range keyRange = 0..1
		Record rec = new Record(row, keyRange)
		assertEquals(new RowValues(["a", "b", "c"]), rec.rowValues())
	}

	@Test
	public void test_rowKey() {
		List<String> row = ["a", "b", "c"]
		Range keyRange = 0..1
		Record rec = new Record(row, keyRange)
		assertEquals(new RowKey(["a", "b", "c"], 0..1), rec.rowKey())
	}


	@Test
	public void test_keyRange() {
		List<String> row = ["a", "b", "c"]
		Range keyRange = 0..1
		Record rec = new Record(row, keyRange)
		assertEquals(0..1, rec.keyRange())
	}


	@Test
	public void test_rowKeyEquals() {
		List<String> row1 = ["a", "b", "c"]
		List<String> row2 = ["a", "b", "c"]
		Record r1 = new Record(row1, 0..1)
		Record r2 = new Record(row2, 0..1)
		assertTrue(r1.rowKeyEquals(r2))
	}

	@Test
	public void test_equals_true() {
		List<String> row1 = ["a", "b", "c"]
		List<String> row2 = ["a", "b", "c"]
		Record r1 = new Record(row1, 0..1)
		Record r2 = new Record(row2, 0..1)
		assertEquals(r1, r2)
	}


	@Test
	public void test_equals_false_by_value() {
		List<String> row1 = ["a", "b", "c"]
		List<String> row2 = ["A", "b", "c"]
		Record r1 = new Record(row1, 0..1)
		Record r2 = new Record(row2, 0..1)
		assertNotEquals(r1, r2)
	}

	@Test
	public void test_equals_false_by_keyRange() {
		List<String> row1 = ["a", "b", "c"]
		List<String> row2 = ["a", "b", "c"]
		Record r1 = new Record(row1, 0..1)
		Record r2 = new Record(row2, 0..2)
		assertNotEquals(r1, r2)
	}

	@Test
	public void test_hashCode() {
		List<String> row1 = ["a", "b", "c"]
		Record r1 = new Record(row1, 0..1)
		assertTrue(r1.hashCode() > 0)
	}


	@Test
	public void test_toString() {
		List<String> row1 = ["a", "b", "c"]
		Record r1 = new Record(row1, 0..1)
		println r1.toString()
		assertEquals('''{"rowKey":"[a,b]","rowValues":"[a,b,c]","keyRange":"0..1"}''', r1.toString())
	}


	@Test
	public void test_compareTo_0() {
		List<String> row1 = ["a", "b", "c"]
		List<String> row2 = ["a", "b", "c"]
		Record r1 = new Record(row1, 0..1)
		Record r2 = new Record(row2, 0..1)
		assertEquals(0, r1.compareTo(r2))
	}

	/*
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
	 */
}
