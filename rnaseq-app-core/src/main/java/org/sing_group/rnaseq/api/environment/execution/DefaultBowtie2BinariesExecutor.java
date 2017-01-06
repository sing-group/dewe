package org.sing_group.rnaseq.api.environment.execution;

import java.io.File;

import org.sing_group.rnaseq.api.environment.binaries.Bowtie2Binaries;
import org.sing_group.rnaseq.api.environment.execution.check.BinaryCheckException;
import org.sing_group.rnaseq.api.environment.execution.check.DefaultBowtie2BinariesChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultBowtie2BinariesExecutor
		extends AbstractBinariesExecutor<Bowtie2Binaries>
		implements Bowtie2BinariesExecutor {
	private final static Logger LOG = LoggerFactory.getLogger(DefaultBowtie2BinariesExecutor.class);  
	
	public DefaultBowtie2BinariesExecutor(Bowtie2Binaries binaries) throws BinaryCheckException {
		this.setBinaries(binaries);
	}
	
	@Override
	public void setBinaries(Bowtie2Binaries binaries) throws BinaryCheckException {
		super.setBinaries(binaries);
		this.checkBinaries();
	}

	@Override
	public void checkBinaries() throws BinaryCheckException {
		DefaultBowtie2BinariesChecker.checkAll(binaries);
	}

	@Override
	public ExecutionResult buildIndex(File genome, File outDir, String baseName) throws ExecutionException, InterruptedException {
		return executeCommand(
			LOG,
			this.binaries.getBuildIndex(),
			genome.getAbsolutePath(),
			new File(outDir, baseName).getAbsolutePath()
		);
	}
}
