package org.sing_group.rnaseq.core.environment.execution;

import java.io.File;

import org.sing_group.rnaseq.api.environment.binaries.SamtoolsBinaries;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;
import org.sing_group.rnaseq.api.environment.execution.SamtoolsBinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.check.BinaryCheckException;
import org.sing_group.rnaseq.core.environment.execution.check.DefaultSamtoolsBinariesChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultSamtoolsBinariesExecutor
		extends AbstractBinariesExecutor<SamtoolsBinaries>
		implements SamtoolsBinariesExecutor {
	private final static Logger LOG = LoggerFactory.getLogger(DefaultSamtoolsBinariesExecutor.class);  
	
	public DefaultSamtoolsBinariesExecutor(SamtoolsBinaries binaries) throws BinaryCheckException {
		this.setBinaries(binaries);
	}
	
	@Override
	public void setBinaries(SamtoolsBinaries binaries) throws BinaryCheckException {
		super.setBinaries(binaries);
		this.checkBinaries();
	}

	@Override
	public void checkBinaries() throws BinaryCheckException {
		DefaultSamtoolsBinariesChecker.checkAll(binaries);
	}

	@Override
	public ExecutionResult samToBam(File sam, File bam)
			throws ExecutionException, InterruptedException {
		return executeCommand(
			LOG,
			this.binaries.getSamToBam(),
			"sort",
			"--threads", "4",
			"-o",
			bam.getAbsolutePath(),
			sam.getAbsolutePath()
		);
	}
}
