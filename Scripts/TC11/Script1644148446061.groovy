import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Collectors

import com.kazurayam.ks.keyword.HTMLTableDiffer.Record
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
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

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

Path projectDir = Paths.get(RunConfiguration.getProjectDir())
Path root = projectDir.resolve("store")
Store store = Stores.newInstance(root)
JobName jobName = new JobName("TC11")

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

/**
 * 
 */
def jsonifyAndStore(Store store, JobName jobName, JobTimestamp jobTimestamp, List<List<String>> input, String inputId) {
	List<String> lines1 = input.stream()
						.map({ List<String> row -> new Record(row, 0..0) })
						.map({ Record rec -> rec.toJson() })
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
private void write(List<String> lines, File file) {
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

