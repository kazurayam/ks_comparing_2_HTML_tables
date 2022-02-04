import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import java.nio.file.Path
import java.nio.file.Paths

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

Path projectDir = Paths.get(RunConfiguration.getProjectDir())
Path webDir = projectDir.resolve("Include/web")

URL page1 = webDir.resolve("page1.html").toFile().toURI().toURL()
List<List<String>> t1 = getDataFromTable(page1)

URL page2 = webDir.resolve("page2.html").toFile().toURI().toURL()
List<List<String>> t2 = getDataFromTable(page2)

WebUI.callTestCase(findTestCase("compare2datasets"), ["data1": t1, "data2": t2])



/**
 * open URL in browser, scrape table data, return data as List<List<String>>
 * 
 * @param url 
 * @return List<List<String>> data collected from a <TABLE>
 */
List<List<String>> getDataFromTable(URL url) {
	WebUI.openBrowser("")
	WebUI.navigateToUrl(url.toString())
	WebDriver driver = DriverFactory.getWebDriver();
	WebElement table1 = driver.findElement(By.xpath("//table[@id='table1']"))
	List<WebElement> trList = table1.findElements(By.xpath("tbody/tr"))
	List<List<String>> data = new ArrayList<>()
	for (WebElement tr in trList) {
		List<WebElement> tdList = tr.findElements(By.xpath("td"))
		List<String> row = new ArrayList<>()
		for (WebElement td in tdList) {
			row.add(td.getText())
		}
		data.add(row)
	}
	WebUI.closeBrowser()
	return data
}