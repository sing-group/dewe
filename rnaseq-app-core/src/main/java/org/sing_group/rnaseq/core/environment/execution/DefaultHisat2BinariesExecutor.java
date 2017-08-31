package org.sing_group.rnaseq.core.environment.execution;

import java.io.File;

import org.sing_group.rnaseq.api.environment.binaries.Hisat2Binaries;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;
import org.sing_group.rnaseq.api.environment.execution.Hisat2BinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.check.BinaryCheckException;
import org.sing_group.rnaseq.api.persistence.entities.Hisat2ReferenceGenomeIndex;
import org.sing_group.rnaseq.core.environment.execution.check.DefaultHisat2BinariesChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default {@code Hisat2BinariesExecutor} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultHisat2BinariesExecutor extends
	AbstractBinariesExecutor<Hisat2Binaries> implements Hisat2BinariesExecutor {
	private final static Logger LOG = LoggerFactory.getLogger(DefaultHisat2BinariesExecutor.class);	  
	
	/**
	 * Creates a new {@code DefaultHisat2BinariesExecutor} instance to execute
	 * the specified {@code Hisat2Binaries}.
	 * 
	 * @param binaries the {@code Hisat2Binaries} to execute
	 * @throws BinaryCheckException if any of the commands can't be executed
	 */
	public DefaultHisat2BinariesExecutor(Hisat2Binaries binaries)
		throws BinaryCheckException {
		this.setBinaries(binaries);
	}
	
	@Override
	public void setBinaries(Hisat2Binaries binaries)
		throws BinaryCheckException {
		super.setBinaries(binaries);
		this.checkBinaries();
	}

	@Override
	public void checkBinaries() throws BinaryCheckException {
		DefaultHisat2BinariesChecker.checkAll(binaries);
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
	public ExecutionResult alignReads(Hisat2ReferenceGenomeIndex genome,
		File reads1, File reads2, boolean dta, File output
	) throws ExecutionException, InterruptedException {
		return alignReads(genome, reads1, reads2, dta, output, null);
	}

	@Override
	public ExecutionResult alignReads(Hisat2ReferenceGenomeIndex genome,
		File reads1, File reads2, boolean dta, File output, File alignmentLog
	) throws ExecutionException, InterruptedException {
		return executeCommand(
			null,
			alignmentLog,
			LOG,
			this.binaries.getAlignReads(),
			"--threads",
			getThreads(),
			dta ? "--dta" : "",
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
