package org.sing_group.rnaseq.api.environment.execution;

import java.io.File;

import org.sing_group.rnaseq.api.environment.binaries.SystemBinaries;

public interface SystemBinariesExecutor
	extends BinariesExecutor<SystemBinaries> 
	{
	public abstract ExecutionResult join(File a, File b, File result)
		throws ExecutionException, InterruptedException;

	public abstract ExecutionResult join(File[] files, File result)
		throws ExecutionException, InterruptedException;

	public abstract ExecutionResult sed(String...params)
		throws ExecutionException, InterruptedException;

	public abstract ExecutionResult awk(File output, String...params)
		throws ExecutionException, InterruptedException;
	
	public abstract ExecutionResult ensgidsToSymbols(
		File referenceAnnotationFile, File outputFile) 
		throws ExecutionException, InterruptedException;
}
