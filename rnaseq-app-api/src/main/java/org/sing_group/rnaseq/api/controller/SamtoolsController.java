package org.sing_group.rnaseq.api.controller;

import java.io.File;

import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.SamtoolsBinariesExecutor;

public interface SamtoolsController {
	public abstract void setSamtoolsBinariesExecutor(
		SamtoolsBinariesExecutor executor);

	public abstract void samToBam(File sam, File bam)
		throws ExecutionException, InterruptedException;
}
