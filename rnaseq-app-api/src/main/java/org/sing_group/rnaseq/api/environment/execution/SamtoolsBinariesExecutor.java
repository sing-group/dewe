package org.sing_group.rnaseq.api.environment.execution;

import java.io.File;

import org.sing_group.rnaseq.api.environment.binaries.SamtoolsBinaries;

public interface SamtoolsBinariesExecutor
	extends BinariesExecutor<SamtoolsBinaries> 
	{
	public abstract ExecutionResult samToBam(File sam, File bam)
		throws ExecutionException, InterruptedException;
}
