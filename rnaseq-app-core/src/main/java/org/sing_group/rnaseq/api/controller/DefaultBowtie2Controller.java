package org.sing_group.rnaseq.api.controller;

import java.io.File;

import org.sing_group.rnaseq.api.environment.execution.Bowtie2BinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;

public class DefaultBowtie2Controller implements Bowtie2Controller {
	private Bowtie2BinariesExecutor bowtie2BinariesExecutor;

	@Override
	public void setBowtie2BinariesExecutor(Bowtie2BinariesExecutor executor) {
		this.bowtie2BinariesExecutor = executor;
	}

	@Override
	public void buildIndex(File genome, File outDir, String baseName) 
	throws ExecutionException, InterruptedException {
		final ExecutionResult result = 
			this.bowtie2BinariesExecutor.buildIndex(genome, outDir, baseName);
		
		if (result.getExitStatus() != 0) {
			throw new ExecutionException(result.getExitStatus(), 
				"Error executing bowtie2-build. Please, check error log.", "");
		}
	}
}
