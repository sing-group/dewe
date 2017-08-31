package org.sing_group.rnaseq.core.environment.execution;

import java.io.File;

import org.sing_group.rnaseq.api.environment.binaries.HtseqBinaries;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;
import org.sing_group.rnaseq.api.environment.execution.HtseqBinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.check.BinaryCheckException;
import org.sing_group.rnaseq.core.environment.execution.check.DefaultHtseqBinariesChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default {@code HtseqBinariesExecutor} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultHtseqBinariesExecutor extends
	AbstractBinariesExecutor<HtseqBinaries> implements HtseqBinariesExecutor {
	private final static Logger LOG = LoggerFactory.getLogger(DefaultHtseqBinariesExecutor.class);  
	
	
	/**
	 * Creates a new {@code DefaultHtseqBinariesExecutor} instance to execute
	 * the specified {@code Bowtie2Binaries}.
	 * 
	 * @param binaries the {@code HtseqBinaries} to execute
	 * @throws BinaryCheckException if any of the commands can't be executed
	 */
	public DefaultHtseqBinariesExecutor(HtseqBinaries binaries)
		throws BinaryCheckException {
		this.setBinaries(binaries);
	}

	@Override
	public void setBinaries(HtseqBinaries binaries)
		throws BinaryCheckException {
		super.setBinaries(binaries);
		this.checkBinaries();
	}

	@Override
	public void checkBinaries() throws BinaryCheckException {
		DefaultHtseqBinariesChecker.checkAll(binaries);
	}

	@Override
	public ExecutionResult countBamReverseExon(File referenceAnnotationFile,
		File inputBam, File output)
		throws ExecutionException, InterruptedException {
		return executeCommand(
			output,
			LOG, 
			this.binaries.getHtseqCount(), 
			"--format",
			"bam",
			"--order",
			"pos",
			"--mode",
			"intersection-strict",
			"--stranded",
			"reverse",
			"--minaqual",
			"1",
			"--type",
			"exon",
			"--idattr",
			"gene_id",
			inputBam.getAbsolutePath(),
			referenceAnnotationFile.getAbsolutePath());
	}
}
