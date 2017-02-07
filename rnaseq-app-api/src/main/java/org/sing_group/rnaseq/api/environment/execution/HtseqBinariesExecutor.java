package org.sing_group.rnaseq.api.environment.execution;

import java.io.File;

import org.sing_group.rnaseq.api.environment.binaries.HtseqBinaries;

public interface HtseqBinariesExecutor
	extends BinariesExecutor<HtseqBinaries> 
	{
	public abstract ExecutionResult countBamReverseExon(File referenceAnnotationFile,
		File inputBam, File output)
		throws ExecutionException, InterruptedException;
}
