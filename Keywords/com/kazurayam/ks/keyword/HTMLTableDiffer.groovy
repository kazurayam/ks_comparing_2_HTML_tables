package com.kazurayam.ks.keyword

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Collectors

import org.openqa.selenium.WebElement

import java.nio.file.Path
import java.nio.file.Paths

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.util.KeywordUtil

import com.kazurayam.materialstore.DiffArtifacts
import com.kazurayam.materialstore.FileType
import com.kazurayam.materialstore.IgnoringMetadataKeys
import com.kazurayam.materialstore.JobName
import com.kazurayam.materialstore.JobTimestamp
import com.kazurayam.materialstore.Material
import com.kazurayam.materialstore.MaterialList
import com.kazurayam.materialstore.Metadata
import com.kazurayam.materialstore.MetadataPattern
import com.kazurayam.materialstore.Store
import com.kazurayam.materialstore.Stores
import com.kazurayam.materialstore.datamodel.textgrid.Row
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


class HTMLTableDiffer {

	HTMLTableDiffer() {}

	
	static boolean diffTextGrids(List<List<String>> input1, List<List<String>> input2, String givenJobName) {
		Path projectDir = Paths.get(RunConfiguration.getProjectDir())
		Path root = projectDir.resolve("store")
		Store store = Stores.newInstance(root)
		JobName jobName = new JobName(givenJobName)
	
		JobTimestamp timestamp1 = JobTimestamp.now()
		jsonifyAndStore(store, jobName, timestamp1, input1, "input1")
	
		JobTimestamp timestamp2 = JobTimestamp.now()
		jsonifyAndStore(store, jobName, timestamp2, input2, "input2")
	
		MaterialList left = store.select(jobName, timestamp1,
							new MetadataPattern.Builder().build())
	
		MaterialList right = store.select(jobName, timestamp2,
							new MetadataPattern.Builder().build())
		double criteria = 0.0d
	
		DiffArtifacts stuffedDiffArtifacts = store.makeDiff(left, right, IgnoringMetadataKeys.of("input"))
		int warnings = stuffedDiffArtifacts.countWarnings(criteria)
	
		Path reportFile = store.reportDiffs(jobName, stuffedDiffArtifacts, criteria, jobName.toString() + "-index.html")
		assert Files.exists(reportFile)
		WebUI.comment("The report can be found ${reportFile.toString()}")
	
		if (warnings > 0) {
			KeywordUtil.markWarning("found ${warnings} differences.")
		}
	}
	
	/**
	 *
	 */
	private static void jsonifyAndStore(Store store, JobName jobName, JobTimestamp jobTimestamp, List<List<String>> input, String inputId) {
		List<String> lines1 = input.stream()
							.map({ List<String> list -> new Row(list, 0..0) })
							.map({ Row row -> row.toJson() })
							.collect(Collectors.toList())
		Path tempFile = Files.createTempFile(null, null)
		write(lines1, tempFile.toFile())
		Metadata metadata = new Metadata.Builder()
							.put("input", inputId)
							.put("target", "whole_rows")
							.build()
		Material mat = store.write(jobName, jobTimestamp, FileType.TXT, metadata, tempFile)
	}
	
	/**
	 *
	 */
	private static void write(List<String> lines, File file) {
		PrintWriter pw = new PrintWriter(
				new BufferedWriter(
				new OutputStreamWriter(
				new FileOutputStream(file),"UTF-8")))
		for (String line in lines) {
			pw.println(line)
		}
		pw.flush()
		pw.close()
	}
	
	/**
	 * open URL in browser, scrape table data, return data as List<List<String>>
	 *
	 * @param url
	 * @return List<List<String>> data collected from a <TABLE>
	 */
	public static List<List<String>> getDataFromPage(URL url, String tableId) {
		List<List<String>> data = new ArrayList<>()
		WebUI.openBrowser("")
		WebUI.navigateToUrl(url.toString())
		WebDriver driver = DriverFactory.getWebDriver();
		WebElement table = driver.findElement(By.xpath("//table[@id='${tableId}']"))
		if (table != null) {
			data.addAll(scrapeDataOutOfTable(table))
		} else {
			KeywordUtil.markFailedAndStop("<table id=${tableId}> is not found in ${url}")
		}
		WebUI.closeBrowser()
		return data
	}
	
	private static List<List<String>> scrapeDataOutOfTable(WebElement table) {
		Objects.requireNonNull(table)
		List<List<String>> data = new ArrayList<>()
		List<WebElement> trList = table.findElements(By.xpath("tbody/tr"))
		if (trList != null) {
			for (WebElement tr in trList) {
				List<WebElement> tdList = tr.findElements(By.xpath("td"))
				List<String> row = new ArrayList<>()
				for (WebElement td in tdList) {
					row.add(td.getText())
				}
				data.add(row)
			}
		}
		return data
	}
	
}

