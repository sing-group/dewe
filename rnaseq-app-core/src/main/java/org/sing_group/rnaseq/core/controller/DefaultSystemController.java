package org.sing_group.rnaseq.core.controller;

import java.io.File;

import org.sing_group.rnaseq.api.controller.SystemController;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;
import org.sing_group.rnaseq.api.environment.execution.SystemBinariesExecutor;

public class DefaultSystemController implements SystemController {
	
	private SystemBinariesExecutor systemBinariesExecutor;

	@Override
	public void setSystemBinariesExecutor(SystemBinariesExecutor executor) {
		this.systemBinariesExecutor = executor;
	}

	@Override
	public void join(File[] files, File resultsFile)
		throws ExecutionException, InterruptedException {
		final ExecutionResult result =
			this.systemBinariesExecutor.join(files, resultsFile);
		
		if (result.getExitStatus() != 0) {
			throw new ExecutionException(result.getExitStatus(),
				"Error joining files. Please, check error log.", "");
		}
	}

	@Override
	public void join(File a, File b, File resultsFile)
			throws ExecutionException, InterruptedException {
		 final ExecutionResult result =
			this.systemBinariesExecutor.join(a, b, resultsFile);
				
		if (result.getExitStatus() != 0) {
			throw new ExecutionException(result.getExitStatus(),
				"Error joining files. Please, check error log.", "");
		}
	}

	@Override
	public void sed(String... params)
			throws ExecutionException, InterruptedException {
		final ExecutionResult result =
			this.systemBinariesExecutor.sed(params);
	
		if (result.getExitStatus() != 0) {
			throw new ExecutionException(result.getExitStatus(),
				"Error running sed. Please, check error log.", "");
		}
	}

	@Override
	public void awk(File resultsFile, String... params)
			throws ExecutionException, InterruptedException {
		final ExecutionResult result =
			this.systemBinariesExecutor.awk(resultsFile,params);
	
		if (result.getExitStatus() != 0) {
			throw new ExecutionException(result.getExitStatus(),
				"Error running awk. Please, check error log.", "");
		}
	}

	@Override
	public void ensgidsToSymbols(File referenceAnnotationFile, File outputFile)
			throws ExecutionException, InterruptedException {
		final ExecutionResult result =
			this.systemBinariesExecutor.ensgidsToSymbols(
				referenceAnnotationFile, outputFile);

		if (result.getExitStatus() != 0) {
			throw new ExecutionException(result.getExitStatus(),
				"Error running script. Please, check error log.", "");
		}
	}
}
