import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import java.nio.file.Path
import java.nio.file.Paths

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.util.KeywordUtil

Path projectDir = Paths.get(RunConfiguration.getProjectDir())
Path webDir = projectDir.resolve("Include/web")

URL page1 = webDir.resolve("page1.html").toFile().toURI().toURL()
List<List<String>> t1 = getDataFromPage(page1, 'table1')

URL page2 = webDir.resolve("page2.html").toFile().toURI().toURL()
List<List<String>> t2 = getDataFromPage(page2, 'table2')

WebUI.callTestCase(findTestCase("compare2datasets"), ["data1": t1, "data2": t2])



/**
 * open URL in browser, scrape table data, return data as List<List<String>>
 * 
 * @param url 
 * @return List<List<String>> data collected from a <TABLE>
 */
List<List<String>> getDataFromPage(URL url, String tableId) {
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

List<List<String>> scrapeDataOutOfTable(WebElement table) {
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