import java.nio.file.Path
import java.nio.file.Paths

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import your.ks.keyword.YourTextGridDiffer

Path projectDir = Paths.get(RunConfiguration.getProjectDir())
Path webDir = projectDir.resolve("Include/web")

// open a web page in browser, scrape data out of a <table id="table1">
URL page1 = webDir.resolve("page1.html").toFile().toURI().toURL()
List<List<String>> t1 = YourTextGridDiffer.getDataFromPage(page1, 'table1')

// open another web page in browser, scrape data out of a <table id="table2">
URL page2 = webDir.resolve("page2.html").toFile().toURI().toURL()
List<List<String>> t2 = YourTextGridDiffer.getDataFromPage(page2, 'table2')

// convert data into JSON files, make the diff information, compile a report
YourTextGridDiffer differ = new YourTextGridDiffer()
int warnings = differ.diffTextGrids(t1, t2, "TC2x")

WebUI.comment("the report is found at " + differ.getReportPathRelativeTo(projectDir))

if (warnings > 0) {
	KeywordUtil.markWarning("found ${warnings} differences.")
}