package org.sing_group.rnaseq.api.environment.execution;

import java.io.File;
import java.util.List;

import org.sing_group.rnaseq.api.environment.binaries.StringTieBinaries;

public interface StringTieBinariesExecutor
	extends BinariesExecutor<StringTieBinaries> 
	{
	public abstract ExecutionResult obtainLabeledTranscripts(File referenceAnnotationFile,
			File inputBam, File outputTranscripts, String label)
			throws ExecutionException, InterruptedException;
	
	public abstract ExecutionResult obtainTranscripts(File referenceAnnotationFile,
		File inputBam, File outputTranscripts)
		throws ExecutionException, InterruptedException;

	public abstract ExecutionResult mergeTranscripts(File referenceAnnotationFile, 
		File mergeList, File mergedAnnotationFile)
		throws ExecutionException, InterruptedException;

	public abstract ExecutionResult mergeTranscripts(File referenceAnnotationFile, 
		List<File> annotationFilesToMerge, File mergedAnnotationFile)
		throws ExecutionException, InterruptedException;
}
