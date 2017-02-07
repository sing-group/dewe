package org.sing_group.rnaseq.core.controller;

import java.io.File;

import org.sing_group.rnaseq.api.controller.HtseqController;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;
import org.sing_group.rnaseq.api.environment.execution.HtseqBinariesExecutor;

public class DefaultHtseqController implements HtseqController {
	
	private HtseqBinariesExecutor htseqBinariesExecutor;

	@Override
	public void setHtseqBinariesExecutor(HtseqBinariesExecutor executor) {
		this.htseqBinariesExecutor = executor;
	}

	@Override
	public void countBamReverseExon(File referenceAnnotationFile, File inputBam,
			File output) throws ExecutionException, InterruptedException {
		final ExecutionResult result = 
			this.htseqBinariesExecutor.countBamReverseExon(
				referenceAnnotationFile, inputBam, output);
		
		if (result.getExitStatus() != 0) {
			throw new ExecutionException(result.getExitStatus(),
					"Error executing htseq-count. Please, check error log.", "");
		}
	}
}
