package org.sing_group.rnaseq.core.io.ballgown;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class BallgownPhenotypeDataCsvLoaderTest {
	public static final File FILE = new File(
		"src/test/resources/data/ballgown/phenotype-data.csv");

	private static final List<String> SAMPLES = Arrays.asList(
		"ERR188245_chrX", "ERR188428_chrX", "ERR188337_chrX",
		"ERR188401_chrX", "ERR188257_chrX",	"ERR188383_chrX",
		"ERR204916_chrX", "ERR188234_chrX", "ERR188273_chrX",
		"ERR188454_chrX", "ERR188104_chrX", "ERR188044_chrX"
	);

	@Test
	public void testLoadGenesFile() throws IOException {
		List<String> samples = BallgownPhenotypeDataCsvLoader
			.loadSampleNames(FILE);
		assertEquals(SAMPLES, samples);
	}
}
