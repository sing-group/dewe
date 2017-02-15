package org.sing_group.rnaseq.api.controller;

import java.io.File;

import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.HtseqBinariesExecutor;

public interface HtseqController {
	public abstract void setHtseqBinariesExecutor(
		HtseqBinariesExecutor executor);

	public abstract void countBamReverseExon(File referenceAnnotationFile,
		File inputBam, File output)
		throws ExecutionException, InterruptedException;

	public abstract void countBamReverseExon(File referenceAnnotationFile,
		File[] inputBams, File outputDir, File joinFile)
		throws ExecutionException, InterruptedException;
}
