package org.sing_group.rnaseq.core.controller;

import java.io.File;

import org.sing_group.rnaseq.api.controller.StringTieController;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;
import org.sing_group.rnaseq.api.environment.execution.StringTieBinariesExecutor;

public class DefaultStringTieController implements StringTieController {
	
	private StringTieBinariesExecutor stringTieBinariesExecutor;

	@Override
	public void setStringTieBinariesExecutor(StringTieBinariesExecutor executor) {
		this.stringTieBinariesExecutor = executor;
	}

	@Override
	public void obtainTranscripts(File referenceAnnotationFile, File inputBam,
			File outputTranscripts)
			throws ExecutionException, InterruptedException {
		final ExecutionResult result =
			this.stringTieBinariesExecutor.obtainTranscripts(
				referenceAnnotationFile, inputBam, outputTranscripts);
		
		if (result.getExitStatus() != 0) {
			throw new ExecutionException(result.getExitStatus(),
					"Error executing samtools. Please, check error log.", "");
		}
	}

	@Override
	public void mergeTranscripts(File referenceAnnotationFile,
			File mergeList, File mergedAnnotationFile)
			throws ExecutionException, InterruptedException {
		final ExecutionResult result =
			this.stringTieBinariesExecutor.mergeTranscripts(
				referenceAnnotationFile, mergeList, mergedAnnotationFile);
		
		if (result.getExitStatus() != 0) {
			throw new ExecutionException(result.getExitStatus(),
					"Error executing samtools. Please, check error log.", "");
		}
	}
}
