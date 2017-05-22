package org.sing_group.rnaseq.api.controller;

import java.io.File;

import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.SystemBinariesExecutor;

public interface SystemController {
	public abstract void setSystemBinariesExecutor(
		SystemBinariesExecutor executor);

	public abstract void join(File a, File b, File result)
		throws ExecutionException, InterruptedException;

	public abstract void join(File[] files, File result)
		throws ExecutionException, InterruptedException;
	
	public abstract void sed(String...params)
		throws ExecutionException, InterruptedException;
	
	public abstract void awk(File result, String...params)
		throws ExecutionException, InterruptedException;
}
