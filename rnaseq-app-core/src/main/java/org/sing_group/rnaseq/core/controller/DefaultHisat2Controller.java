package org.sing_group.rnaseq.core.controller;

import java.io.File;

import org.sing_group.rnaseq.api.controller.Hisat2Controller;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;
import org.sing_group.rnaseq.api.environment.execution.Hisat2BinariesExecutor;
import org.sing_group.rnaseq.api.persistence.entities.Hisat2ReferenceGenome;

public class DefaultHisat2Controller implements Hisat2Controller {
	private Hisat2BinariesExecutor hisat2BinariesExecutor;

	@Override
	public void setHisat2BinariesExecutor(Hisat2BinariesExecutor executor) {
		this.hisat2BinariesExecutor = executor;
	}

	@Override
	public void alignReads(Hisat2ReferenceGenome genome, File reads1,
		File reads2, File output
	) throws ExecutionException, InterruptedException {
		final ExecutionResult result =
			this.hisat2BinariesExecutor.alignReads(genome, reads1, reads2, output);

		if (result.getExitStatus() != 0) {
			throw new ExecutionException(result.getExitStatus(),
				"Error executing hisat2. Please, check error log.", "");
		}
	}
}
