package org.sing_group.rnaseq.api.environment.execution;

import java.io.File;

import org.sing_group.rnaseq.api.environment.binaries.StringTieBinaries;

public interface StringTieBinariesExecutor
	extends BinariesExecutor<StringTieBinaries> 
	{
	public abstract ExecutionResult obtainTranscripts(File referenceAnnotationFile,
		File inputBam, File outputTranscripts)
		throws ExecutionException, InterruptedException;
}
