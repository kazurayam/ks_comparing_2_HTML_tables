package com.kazurayam.ks.keyword

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable


import static org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.kazurayam.ks.keyword.HTMLTableDiffer.Record
import com.kazurayam.ks.keyword.HTMLTableDiffer.RowKey
import com.kazurayam.ks.keyword.HTMLTableDiffer.RowValues
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.Files


@RunWith(JUnit4.class)

public class HTMLTableDiffer_diff_Test {

	List<List<String>> input1 = [
		[ "Customer A", "Retail B", "x" ],
		// [ "Customer B", "Retail A", "x" ],
		[ "Customer C", "Retail B", "x" ],
		[ "Customer D", "Key Account", "x" ],
		[ "Customer E", "Retail A", "x" ],
		[ "Customer F", "Key Account", "x" ],
		[ "Customer G", "Key Account", "x" ],
		];

	List<List<String>> input2 = [
		[ "Customer D", "Key Account" ],
		[ "Customer B", "Retail A" ],
		[ "Customer C", "Retail B" ],
		[ "Customer A", "Retail B" ],
		[ "Customer G", "Key Account" ],
		[ "Customer E", "Retail A" ],
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
}
