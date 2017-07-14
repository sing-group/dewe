package org.sing_group.rnaseq.core.io.samples;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	ImportExperimentalConditionsTest.class,
	ImportPairedSamplesDirectoryTest.class
})
public class SamplesTestSuite {

}
