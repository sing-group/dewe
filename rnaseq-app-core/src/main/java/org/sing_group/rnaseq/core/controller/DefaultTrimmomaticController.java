/*
 * #%L
 * DEWE Core
 * %%
 * Copyright (C) 2016 - 2017 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
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

import org.sing_group.rnaseq.api.controller.TrimmomaticController;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;
import org.sing_group.rnaseq.api.environment.execution.TrimmomaticBinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.parameters.trimmomatic.TrimmomaticParameter;

/**
 * The default {@code TrimmomaticController} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultTrimmomaticController implements TrimmomaticController {
	private TrimmomaticBinariesExecutor trimmomaticBinariesExecutor;

	@Override
	public void setTrimmomaticBinariesExecutor(TrimmomaticBinariesExecutor executor) {
		this.trimmomaticBinariesExecutor = executor;
	}

	@Override
	public void filterSingleEndReads(File inputFile, File outputFile,
		TrimmomaticParameter... parameters
	) throws ExecutionException, InterruptedException {
		final ExecutionResult result = this.trimmomaticBinariesExecutor
			.filterSingleEndReads(inputFile, outputFile, parameters);

		if (result.getExitStatus() != 0) {
			throw new ExecutionException(result.getExitStatus(),
				"Error executing trimmomatic. Please, check error log.", "");
		}
	}

	@Override
	public void filterPairedEndReads(File reads1, File reads2,
		File outputBase, TrimmomaticParameter... parameters
	) throws ExecutionException, InterruptedException {
		final ExecutionResult result = this.trimmomaticBinariesExecutor
			.filterPairedEndReads(reads1, reads2, outputBase, parameters);

		if (result.getExitStatus() != 0) {
			throw new ExecutionException(result.getExitStatus(),
				"Error executing trimmomatic. Please, check error log.", "");
		}
	}
}
