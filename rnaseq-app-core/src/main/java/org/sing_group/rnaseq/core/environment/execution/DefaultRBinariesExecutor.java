package org.sing_group.rnaseq.core.environment.execution;

import java.io.File;

import org.sing_group.rnaseq.api.environment.binaries.RBinaries;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;
import org.sing_group.rnaseq.api.environment.execution.RBinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.check.BinaryCheckException;
import org.sing_group.rnaseq.core.environment.execution.check.DefaultRBinariesChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultRBinariesExecutor
	extends AbstractBinariesExecutor<RBinaries>
	implements RBinariesExecutor {
	private final static Logger LOG = LoggerFactory.getLogger(DefaultRBinariesExecutor.class);  
	
	public DefaultRBinariesExecutor(RBinaries binaries) throws BinaryCheckException {
		this.setBinaries(binaries);
	}
	
	@Override
	public void setBinaries(RBinaries binaries) throws BinaryCheckException {
		super.setBinaries(binaries);
		this.checkBinaries();
	}

	@Override
	public void checkBinaries() throws BinaryCheckException {
		DefaultRBinariesChecker.checkAll(binaries);
	}

	@Override
	public ExecutionResult runScript(File script, String...args)
		throws ExecutionException, InterruptedException {
		final String[] newArgsArray = new String[args.length+1];
		newArgsArray[0] = script.getAbsolutePath();
		System.arraycopy(args, 0, newArgsArray, 1, args.length);

		return executeCommand(
				LOG,
				this.binaries.getRscript(),
				newArgsArray
			);
	}

}
