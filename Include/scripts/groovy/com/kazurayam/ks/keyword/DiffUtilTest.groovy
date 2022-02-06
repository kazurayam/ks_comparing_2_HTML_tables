package com.kazurayam.ks.keyword

import java.util.function.Function

import org.junit.Test

import com.github.difflib.DiffUtils
import com.github.difflib.UnifiedDiffUtils
import com.github.difflib.patch.Patch
import com.github.difflib.text.DiffRow
import com.github.difflib.text.DiffRowGenerator

public class DiffUtilTest {

	@Test
	void test_unified_diff() {
		List<String> text1=Arrays.asList("this is a test","a test");
		List<String> text2=Arrays.asList("this is a testfile","a test");

		//generating diff information.
		Patch<String> diff = DiffUtils.diff(text1, text2);

		//generating unified diff format
		List<String> unifiedDiff = UnifiedDiffUtils.generateUnifiedDiff("original-file.txt", "new-file.txt", text1, diff, 0);

		for (String line in unifiedDiff) {
			println line
		}
	}

	@Test
	void test_DiffRowGenerator() {
		Function strikethrough = { any -> "~" }
		Function bold = { any -> "**" }
		DiffRowGenerator generator =
				DiffRowGenerator.create()
				.showInlineDiffs(true)
				.inlineDiffByWord(true)
				.oldTag(strikethrough)    // introduce markdown style for strikethrough
				.newTag(bold)   // introduce markdown style for bold
				.build()

		List<DiffRow> rows = generator.generateDiffRows(
				Arrays.asList("This is a test senctence.", "This is the second line.", "And here is the finish."),
				Arrays.asList("This is a test for diffutils.", "This is the second line."));

		System.out.println("|original|new|");
		System.out.println("|--------|---|");
		for (DiffRow row : rows) {
			System.out.println("|" + row.getOldLine() + "|" + row.getNewLine() + "|");
		}
	}
}
