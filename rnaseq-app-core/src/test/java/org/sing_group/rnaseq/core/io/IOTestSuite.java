package org.sing_group.rnaseq.core.io;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.sing_group.rnaseq.core.io.alignment.AlignmentTestSuite;
import org.sing_group.rnaseq.core.io.ballgown.BallgownTestSuite;
import org.sing_group.rnaseq.core.io.edger.EdgeRTestSuite;

@RunWith(Suite.class)
@SuiteClasses({
	AlignmentTestSuite.class,
	BallgownTestSuite.class,
	EdgeRTestSuite.class
})
public class IOTestSuite {

}
