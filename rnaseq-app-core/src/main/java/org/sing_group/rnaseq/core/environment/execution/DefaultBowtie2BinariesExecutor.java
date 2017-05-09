package org.sing_group.rnaseq.core.environment.execution;

import java.io.File;

import org.sing_group.rnaseq.api.environment.binaries.Bowtie2Binaries;
import org.sing_group.rnaseq.api.environment.execution.Bowtie2BinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;
import org.sing_group.rnaseq.api.environment.execution.check.BinaryCheckException;
import org.sing_group.rnaseq.api.environment.execution.parameters.bowtie2.Bowtie2EndToEndConfiguration;
import org.sing_group.rnaseq.api.persistence.entities.Bowtie2ReferenceGenomeIndex;
import org.sing_group.rnaseq.core.environment.execution.check.DefaultBowtie2BinariesChecker;
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
	public ExecutionResult buildIndex(File genome, File outDir, String baseName)
		throws ExecutionException, InterruptedException {

		return executeCommand(
			LOG,
			this.binaries.getBuildIndex(),
			genome.getAbsolutePath(),
			new File(outDir, baseName).getAbsolutePath()
		);
	}

	@Override
	public ExecutionResult alignReads(Bowtie2ReferenceGenomeIndex genome,
		File reads1, File reads2, Bowtie2EndToEndConfiguration configuration,
		File output
	) throws ExecutionException, InterruptedException {
		return alignReads(genome, reads1, reads2, configuration, output, null);
	}

	@Override
	public ExecutionResult alignReads(Bowtie2ReferenceGenomeIndex genome,
		File reads1, File reads2, Bowtie2EndToEndConfiguration configuration,
		File output, File alignmentLog
	) throws ExecutionException, InterruptedException {
		return executeCommand(
			null,
			alignmentLog,
			LOG,
			this.binaries.getAlignReads(),
			"--threads",
			getThreads(),
			configuration.getParameter(),
			"-x",
			genome.getReferenceGenomeIndex(),
			"-1",
			reads1.getAbsolutePath(),
			"-2",
			reads2.getAbsolutePath(),
			"-S",
			output.getAbsolutePath()
		);
	}
}
