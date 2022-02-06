package com.kazurayam.ks.keyword

import static org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.google.gson.Gson

@RunWith(JUnit4.class)

class GsonTest {

	@Test
	void test_newline() {
		String s = new Gson().toJson("""Hello,\nworld!""")
		println s
		assertEquals("\"Hello,\\nworld!\"", s)
	}

	@Test
	void test_quotes() {
		String s = new Gson().toJson('\"Hello, world!\"');
		assertEquals('''\"\\"Hello, world!\\"\"''', s)
	}
}