package com.kazurayam.ks.keyword

import static org.junit.Assert.*

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4.class)

public class HTMLTableDiffer_diff_Test {

	List<List<String>> input1 = [
		[
			"Customer A",
			"Retail B",
			"x"
		],
		// [ "Customer B", "Retail A", "x" ],
		[
			"Customer C",
			"Retail B",
			"x"
		],
		[
			"Customer D",
			"Key Account",
			"x"
		],
		[
			"Customer E",
			"Retail A",
			"x"
		],
		[
			"Customer F",
			"Key Account",
			"x"
		],
		[
			"Customer G",
			"Key Account",
			"x"
		],
	];

	List<List<String>> input2 = [
		[
			"Customer D",
			"Key Account"
		],
		["Customer B", "Retail A"],
		["Customer C", "Retail B"],
		["Customer A", "Retail B"],
		[
			"Customer G",
			"Key Account"
		],
		["Customer E", "Retail A"],
		// [ "Customer F", "Key Account" ],
	];

	Path classOutputDir

	@Before
	void setup() {
		Path projectDir = Paths.get(".")
		Path testOutputDir = projectDir.resolve("build/tmp/testOutput")
		classOutputDir = testOutputDir.resolve(this.getClass().getSimpleName())
		Files.createDirectories(classOutputDir)
	}

	@Test
	void test_diff() {
		File output = classOutputDir.resolve("test_diff.txt").toFile()
		HTMLTableDiffer differ = new HTMLTableDiffer()
		boolean different = differ.diff(input1, input2, output)
		assertTrue(different)
	}

	@Test
	void test_diffKeyws() {
		File output = classOutputDir.resolve("test_diffKeys.txt").toFile()
		HTMLTableDiffer differ = new HTMLTableDiffer()
		boolean different = differ.diffKeys(input1, input2, 0..1, output)
		assertTrue(different)
	}
}
