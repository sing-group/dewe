package org.sing_group.rnaseq.core.controller;

import java.io.File;

import org.sing_group.rnaseq.api.controller.SamtoolsController;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;
import org.sing_group.rnaseq.api.environment.execution.SamtoolsBinariesExecutor;

/**
 * The default {@code SamtoolsController} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultSamtoolsController implements SamtoolsController {
	private SamtoolsBinariesExecutor samtoolsBinariesExecutor;

	@Override
	public void setSamtoolsBinariesExecutor(SamtoolsBinariesExecutor executor) {
		this.samtoolsBinariesExecutor = executor;
	}

	@Override
	public void samToBam(File sam, File bam)
			throws ExecutionException, InterruptedException {
		final ExecutionResult result =
			this.samtoolsBinariesExecutor.samToBam(sam, bam);

		if (result.getExitStatus() != 0) {
			throw new ExecutionException(result.getExitStatus(),
				"Error executing samtools. Please, check error log.", "");
		}
	}

}
