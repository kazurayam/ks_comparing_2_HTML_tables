package com.kazurayam.ks.keyword

import static org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.kazurayam.ks.keyword.HTMLTableDiffer.RowValues

@RunWith(JUnit4.class)

public class HTMLTableDiffer_RowValues_Test {

	@Test
	public void test_equals_true() {
		List<String> l1 = ["a"];
		List<String> l2 = ["a"]
		RowValues rv1 = new RowValues(l1)
		RowValues rv2 = new RowValues(l2)
		assertTrue(rv1.equals(rv2))
	}

	@Test
	public void test_equals_false_by_size() {
		List<String> l1 = ["a"];
		List<String> l2 = ["a", "a"]
		RowValues rv1 = new RowValues(l1)
		RowValues rv2 = new RowValues(l2)
		assertFalse(rv1.equals(rv2))
	}

	@Test
	public void test_equals_false_by_value() {
		List<String> l1 = ["a"];
		List<String> l2 = ["b"]
		RowValues rv1 = new RowValues(l1)
		RowValues rv2 = new RowValues(l2)
		assertFalse(rv1.equals(rv2))
	}

	@Test
	public void test_hashCode() {
		List<String> l1 = ["a"]
		RowValues rv1 = new RowValues(l1)
		assertTrue(rv1.hashCode() > 0)
	}

	@Test
	public void test_toString() {
		List<String> l1 = ["a"]
		RowValues rv1 = new RowValues(l1)
		assertEquals("[\"a\"]", rv1.toString())
	}

	@Test
	public void test_compareTo_0() {
		List<String> l1 = ["a"];
		List<String> l2 = ["a"]
		RowValues rv1 = new RowValues(l1)
		RowValues rv2 = new RowValues(l2)
		assertEquals(0, rv1.compareTo(rv2))
	}

	@Test
	public void test_compareTo_minus_by_value() {
		List<String> l1 = ["a"];
		List<String> l2 = ["b"]
		RowValues rv1 = new RowValues(l1)
		RowValues rv2 = new RowValues(l2)
		assertEquals(-1, rv1.compareTo(rv2))
	}

	@Test
	public void test_compareTo_plus_by_value() {
		List<String> l1 = ["b"];
		List<String> l2 = ["a"]
		RowValues rv1 = new RowValues(l1)
		RowValues rv2 = new RowValues(l2)
		assertEquals(1, rv1.compareTo(rv2))
	}

	@Test
	public void test_compareTo_minus_by_size() {
		List<String> l1 = ["a"];
		List<String> l2 = ["a", "b"]
		RowValues rv1 = new RowValues(l1)
		RowValues rv2 = new RowValues(l2)
		assertEquals(-1, rv1.compareTo(rv2))
	}

	@Test
	public void test_compareTo_plus_by_size() {
		List<String> l1 = ["a", "b"];
		List<String> l2 = ["a"]
		RowValues rv1 = new RowValues(l1)
		RowValues rv2 = new RowValues(l2)
		assertEquals(1, rv1.compareTo(rv2))
	}
}
