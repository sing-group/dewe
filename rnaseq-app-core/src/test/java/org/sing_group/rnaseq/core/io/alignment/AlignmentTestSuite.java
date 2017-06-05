package org.sing_group.rnaseq.core.io.alignment;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
		DefaultAlignmentLogParserTest.class,
		SamplesAlignmentStatisticsCsvWriterTest.class
})
public class AlignmentTestSuite {

}
