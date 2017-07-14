package org.sing_group.rnaseq.core.io.samples;

import static java.util.Arrays.asList;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.sing_group.rnaseq.api.entities.FastqReadsSamples;

import junit.framework.Assert;

public class ImportExperimentalConditionsTest {

	public static final File DATA_DIR = new File(
		"src/test/resources/data/samples/import-directory");
	private static final Set<String> EXPECTED_CONDITIONS =
		new HashSet<>(asList("conditionA", "conditionB", "conditionC"));

	@Test
	public void importExperimentalConditionsTest() {
		Map<String, FastqReadsSamples> conditions = ImportExperimentalConditions
			.importDirectory(DATA_DIR);
		Assert.assertEquals(EXPECTED_CONDITIONS, conditions.keySet());
	}
}
