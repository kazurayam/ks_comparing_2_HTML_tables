import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.configuration.RunConfiguration

import java.nio.file.Path
import java.nio.file.Paths

import your.ks.keyword.YourTextGridDiffer

Path projectDir = Paths.get(RunConfiguration.getProjectDir())

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
		[ "Customer A", "Retail B" , "x"],
		[ "Customer D", "Key Account" , "x"],
		[ "Customer B", "Retail A" , "x"],
		[ "Customer C", "Retail B" , "x"],
		[ "Customer E", "Retail A" , "x"],
		// [ "Customer F", "Key Account" , "x"],
		[ "Customer G", "Key Account" , "x"]
];

YourTextGridDiffer differ = new YourTextGridDiffer()
int warnings = differ.diffTextGrids(input1, input2, 0..1, "TC1x")

WebUI.comment("the report is found at " + differ.getReportPathRelativeTo(projectDir))

if (warnings > 0) {
	KeywordUtil.markWarning("found ${warnings} differences.")
}