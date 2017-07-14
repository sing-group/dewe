package org.sing_group.rnaseq.core.io.samples;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.core.entities.DefaultFastqReadsSample;
import org.sing_group.rnaseq.core.entities.DefaultFastqReadsSamples;

@RunWith(Parameterized.class)
public class ImportPairedSamplesDirectoryTest {
	public static final File DATA_DIR_1 = new File(
		"src/test/resources/data/samples/import-directory/conditionA");
	public static final FastqReadsSamples DATA_DIR_1_SAMPLES =
		new DefaultFastqReadsSamples(Arrays.asList(
			new DefaultFastqReadsSample("sampleA.1", "conditionA",
				testFile("conditionA/sampleA.1_1.fastq"),
				testFile("conditionA/sampleA.1_2.fastq")
			),
			new DefaultFastqReadsSample("sampleA.2", "conditionA",
				testFile("conditionA/sampleA.2_1.fastq"),
				testFile("conditionA/sampleA.2_2.fastq")
			)
		));

	public static final File DATA_DIR_2 = new File(
		"src/test/resources/data/samples/import-directory/conditionB");
	public static final FastqReadsSamples DATA_DIR_2_SAMPLES =
		new DefaultFastqReadsSamples(Arrays.asList(
			new DefaultFastqReadsSample("sampleB.1", "conditionB",
				testFile("conditionB/sampleB.1_1.fq"),
				testFile("conditionB/sampleB.1_2.fq")
			),
			new DefaultFastqReadsSample("sampleB.2", "conditionB",
				testFile("conditionB/sampleB.2_1.fq"),
				testFile("conditionB/sampleB.2_2.fq")
			)
		));

	public static final File DATA_DIR_3 = new File(
		"src/test/resources/data/samples/import-directory/conditionC");
	public static final FastqReadsSamples DATA_DIR_3_SAMPLES =
		new DefaultFastqReadsSamples(Arrays.asList(
			new DefaultFastqReadsSample("sampleC.1", "conditionC",
				testFile("conditionC/sampleC.1_1.fastq.gz"),
				testFile("conditionC/sampleC.1_2.fastq.gz")
			),
			new DefaultFastqReadsSample("sampleC.2", "conditionC",
				testFile("conditionC/sampleC.2_1.fastq.gz"),
				testFile("conditionC/sampleC.2_2.fastq.gz")
			)
		));

	@Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                 { DATA_DIR_1, DATA_DIR_1_SAMPLES },
                 { DATA_DIR_2, DATA_DIR_2_SAMPLES },
                 { DATA_DIR_3, DATA_DIR_3_SAMPLES },
           });
    }

	private File dataDir;
	private FastqReadsSamples samples;

	public ImportPairedSamplesDirectoryTest(File dataDir,
		FastqReadsSamples samples
	) {
		this.dataDir = dataDir;
		this.samples = samples;
	}

	@Test
	public void importPairedSamplesDirectory() {
		FastqReadsSamples actualSamples =
			ImportPairedSamplesDirectory.importDirectory(dataDir);

		assertThat(actualSamples.toArray())
			.containsExactlyInAnyOrder(samples.toArray());
	}

	private static File testFile(String file) {
		return new File("src/test/resources/data/samples/import-directory/", file);
	}
}
