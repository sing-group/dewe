package org.sing_group.rnaseq.api.environment.execution;

import java.io.File;

import org.sing_group.rnaseq.api.environment.binaries.Bowtie2Binaries;

public interface Bowtie2BinariesExecutor
	extends BinariesExecutor<Bowtie2Binaries> 
	{
	public abstract ExecutionResult buildIndex(
		File genome, File outDir, String baseName) 
		throws ExecutionException, InterruptedException;
}
