package org.sing_group.rnaseq.api.controller;

import java.io.File;

import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.RBinariesExecutor;

public interface RController {
	public abstract void setRBinariesExecutor(
		RBinariesExecutor executor);

	public abstract void runScript(File script, String...args)
		throws ExecutionException, InterruptedException;
}
