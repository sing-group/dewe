package org.sing_group.rnaseq.api.environment.execution;

import java.io.File;

import org.sing_group.rnaseq.api.environment.binaries.RBinaries;

public interface RBinariesExecutor
	extends BinariesExecutor<RBinaries> 
	{
	public abstract ExecutionResult runScript(File script, String...args)
		throws ExecutionException, InterruptedException;
}
