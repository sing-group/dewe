package org.sing_group.rnaseq.api.controller;

import java.io.File;

import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.StringTieBinariesExecutor;

public interface StringTieController {
	public abstract void setStringTieBinariesExecutor(
		StringTieBinariesExecutor executor);

	public abstract void obtainTranscripts(File referenceAnnotationFile,
		File inputBam, File outputTranscripts)
		throws ExecutionException, InterruptedException;
}
