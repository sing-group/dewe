package org.sing_group.rnaseq.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.sing_group.rnaseq.core.io.IOTestSuite;
import org.sing_group.rnaseq.core.operations.OperationsTestSuite;

@RunWith(Suite.class)
@SuiteClasses({
	IOTestSuite.class,
	OperationsTestSuite.class
})
public class CoreTestSuite {

}
