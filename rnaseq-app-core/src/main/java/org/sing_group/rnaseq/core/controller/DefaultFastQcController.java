/*
 * #%L
 * DEWE Core
 * %%
 * Copyright (C) 2016 - 2019 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola,
 * 			Borja Sánchez, and Anália Lourenço
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package org.sing_group.rnaseq.core.controller;

import java.io.File;

import org.sing_group.rnaseq.api.controller.FastQcController;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;
import org.sing_group.rnaseq.api.environment.execution.FastQcBinariesExecutor;

/**
 * The default {@code FastQcController} implementation.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultFastQcController implements FastQcController {
	private FastQcBinariesExecutor fastQcBinariesExecutor;

	@Override
	public void setFastQcBinariesExecutor(FastQcBinariesExecutor executor) {
		this.fastQcBinariesExecutor = executor;
	}

	@Override
	public void fastqc(File[] reads, File outputDir)
		throws ExecutionException, InterruptedException {
		final ExecutionResult result =
			this.fastQcBinariesExecutor.fastqc(reads, outputDir);

		if (result.getExitStatus() != 0) {
			throw new ExecutionException(result.getExitStatus(),
				"Error executing FastQC. Please, check error log.", "");
		}
	}

	@Override
	public void fastqc(File[] reads)
		throws ExecutionException, InterruptedException {
		final ExecutionResult result =
			this.fastQcBinariesExecutor.fastqc(reads);

		if (result.getExitStatus() != 0) {
			throw new ExecutionException(result.getExitStatus(),
				"Error executing FastQC. Please, check error log.", "");
		}
	}
}
