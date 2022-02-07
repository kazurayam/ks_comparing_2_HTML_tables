package your.ks.keyword

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kazurayam.materialstore.textgrid.DefaultTextGridDiffer
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

class YourTextGridDiffer extends DefaultTextGridDiffer {

	/**
	 * open a URL in browser, scrape table data, return data as List&lt;List&lt;String>>
	 *
	 * @param url
	 * @return List&lt;List&lt;String>> data collected from a &lt;table>
	 */
	public static final List<List<String>> getDataFromPage(URL url, String tableId) {
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

	/**
	 * scrape data out of a &lt;table&gt;
	 */
	private static final List<List<String>> scrapeDataOutOfTable(WebElement table) {
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

