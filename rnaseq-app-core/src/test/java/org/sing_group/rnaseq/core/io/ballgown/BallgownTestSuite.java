package org.sing_group.rnaseq.core.io.ballgown;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	BallgownGenesCsvFileLoaderTest.class,
	BallgownPhenotypeDataCsvLoaderTest.class,
	BallgownTranscriptsCsvFileLoaderTest.class 
})
public class BallgownTestSuite {

}
