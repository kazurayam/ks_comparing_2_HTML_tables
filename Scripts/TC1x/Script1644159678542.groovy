import com.kazurayam.ks.keyword.HTMLTableDiffer

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


boolean b = HTMLTableDiffer.diffTextGrids(input1, input2, "TC1x")
