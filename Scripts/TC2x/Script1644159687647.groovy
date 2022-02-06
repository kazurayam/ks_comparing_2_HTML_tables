
import java.nio.file.Path
import java.nio.file.Paths

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.util.KeywordUtil
import com.kazurayam.ks.keyword.HTMLTableDiffer

Path projectDir = Paths.get(RunConfiguration.getProjectDir())
Path webDir = projectDir.resolve("Include/web")

URL page1 = webDir.resolve("page1.html").toFile().toURI().toURL()
List<List<String>> t1 = HTMLTableDiffer.getDataFromPage(page1, 'table1')

URL page2 = webDir.resolve("page2.html").toFile().toURI().toURL()
List<List<String>> t2 = HTMLTableDiffer.getDataFromPage(page2, 'table2')

HTMLTableDiffer.diffTextGrids(t1, t2, "TC2x")
