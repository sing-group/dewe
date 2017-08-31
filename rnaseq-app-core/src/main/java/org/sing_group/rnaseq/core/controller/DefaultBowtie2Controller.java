package org.sing_group.rnaseq.core.controller;

import java.io.File;

import org.sing_group.rnaseq.api.controller.Bowtie2Controller;
import org.sing_group.rnaseq.api.environment.execution.Bowtie2BinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;
import org.sing_group.rnaseq.api.environment.execution.parameters.bowtie2.Bowtie2EndToEndConfiguration;
import org.sing_group.rnaseq.api.persistence.entities.Bowtie2ReferenceGenomeIndex;

/**
 * The default {@code Bowtie2Controller} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultBowtie2Controller implements Bowtie2Controller {
	private Bowtie2BinariesExecutor bowtie2BinariesExecutor;

	@Override
	public void setBowtie2BinariesExecutor(Bowtie2BinariesExecutor executor) {
		this.bowtie2BinariesExecutor = executor;
	}

	@Override
	public void buildIndex(File genome, File outDir, String baseName) 
	throws ExecutionException, InterruptedException {
		final ExecutionResult result = 
			this.bowtie2BinariesExecutor.buildIndex(genome, outDir, baseName);
		
		if (result.getExitStatus() != 0) {
			throw new ExecutionException(result.getExitStatus(),
				"Error executing bowtie2-build. Please, check error log.", "");
		}
	}

	@Override
	public void alignReads(Bowtie2ReferenceGenomeIndex genome, File reads1,
		File reads2, Bowtie2EndToEndConfiguration configuration, File output
	) throws ExecutionException, InterruptedException {
		final ExecutionResult result = this.bowtie2BinariesExecutor
			.alignReads(genome, reads1, reads2, configuration, output);

		checkResult(result);
	}

	@Override
	public void alignReads(Bowtie2ReferenceGenomeIndex genome, File reads1,
		File reads2, Bowtie2EndToEndConfiguration configuration, File output,
		boolean saveAlignmentLog
	) throws ExecutionException, InterruptedException {
		if(saveAlignmentLog) {
			alignReads(genome, reads1, reads2, configuration, output,
				getAlignmentLogFile(output));
		} else {
			alignReads(genome, reads1, reads2, configuration, output);
		}
	}

	@Override
	public void alignReads(Bowtie2ReferenceGenomeIndex genome, File reads1,
		File reads2, Bowtie2EndToEndConfiguration configuration, File output,
		File alignmentLogFile
	) throws ExecutionException, InterruptedException {
		final ExecutionResult result = this.bowtie2BinariesExecutor.alignReads(
			genome, reads1, reads2, configuration, output, alignmentLogFile);

		checkResult(result);
	}

	private static File getAlignmentLogFile(File outputFile) {
		return new File(outputFile.getAbsoluteFile() + ".txt");
	}

	private void checkResult(ExecutionResult result) throws ExecutionException {
		if (result.getExitStatus() != 0) {
			throw new ExecutionException(result.getExitStatus(),
				"Error executing bowtie2. Please, check error log.", "");
		}
	}
}
