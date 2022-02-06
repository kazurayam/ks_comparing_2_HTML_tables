import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

/**
 * https://forum.katalon.com/t/comparing-two-tables/62051
 */

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
		[ "Customer A", "Retail B" ],
		[ "Customer D", "Key Account" ],
		[ "Customer B", "Retail A" ],
		[ "Customer C", "Retail B" ],
		[ "Customer E", "Retail A" ],
		// [ "Customer F", "Key Account" ],
		[ "Customer G", "Key Account" ]
		
];

WebUI.callTestCase(findTestCase("compare2datasets"), ["data1": input1, "data2": input2])
