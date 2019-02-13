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

import org.sing_group.rnaseq.api.controller.Bowtie2Controller;
import org.sing_group.rnaseq.api.environment.execution.Bowtie2BinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;
import org.sing_group.rnaseq.api.persistence.entities.Bowtie2ReferenceGenomeIndex;

/**
 * The default {@code Bowtie2Controller} implementation.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
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

	@Override
	public void alignReads(Bowtie2ReferenceGenomeIndex genome, File reads1,
		File reads2, String params, File output
	) throws ExecutionException, InterruptedException {
		final ExecutionResult result = this.bowtie2BinariesExecutor
			.alignReads(genome, reads1, reads2, params, output);

		checkResult(result);
	}

	@Override
	public void alignReads(Bowtie2ReferenceGenomeIndex genome, File reads,
		String params, File output
	) throws ExecutionException, InterruptedException {
		final ExecutionResult result = this.bowtie2BinariesExecutor
			.alignReads(genome, reads, params, output);

		checkResult(result);
	}

	@Override
	public void alignReads(Bowtie2ReferenceGenomeIndex genome, File reads1,
		File reads2, String params, File output,
		boolean saveAlignmentLog
	) throws ExecutionException, InterruptedException {
		if(saveAlignmentLog) {
			alignReads(genome, reads1, reads2, params, output,
				getAlignmentLogFile(output));
		} else {
			alignReads(genome, reads1, reads2, params, output);
		}
	}

	@Override
	public void alignReads(Bowtie2ReferenceGenomeIndex genome, File reads,
		String params, File output,
		boolean saveAlignmentLog
	) throws ExecutionException, InterruptedException {
		if(saveAlignmentLog) {
			alignReads(genome, reads, params, output,
				getAlignmentLogFile(output));
		} else {
			alignReads(genome, reads, params, output);
		}
	}

	@Override
	public void alignReads(Bowtie2ReferenceGenomeIndex genome, File reads1,
		File reads2, String params, File output,
		File alignmentLogFile
	) throws ExecutionException, InterruptedException {
		final ExecutionResult result = this.bowtie2BinariesExecutor.alignReads(
			genome, reads1, reads2, params, output, alignmentLogFile);

		checkResult(result);
	}

	@Override
	public void alignReads(Bowtie2ReferenceGenomeIndex genome, File reads,
		String params, File output,
		File alignmentLogFile
	) throws ExecutionException, InterruptedException {
		final ExecutionResult result = this.bowtie2BinariesExecutor.alignReads(
			genome, reads, params, output, alignmentLogFile);

		checkResult(result);
	}

	private static File getAlignmentLogFile(File outputFile) {
		return new File(outputFile.getAbsoluteFile() + ".txt");
	}

	private void checkResult(ExecutionResult result) throws ExecutionException {
		if (result.getExitStatus() != 0) {
			throw new ExecutionException(result.getExitStatus(),
				"Error executing bowtie2. Please, check error log.", "");
		}
	}
}
