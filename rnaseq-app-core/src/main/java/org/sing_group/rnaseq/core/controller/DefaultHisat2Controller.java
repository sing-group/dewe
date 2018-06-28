/*
 * #%L
 * DEWE Core
 * %%
 * Copyright (C) 2016 - 2018 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
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

import org.sing_group.rnaseq.api.controller.Hisat2Controller;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;
import org.sing_group.rnaseq.api.environment.execution.Hisat2BinariesExecutor;
import org.sing_group.rnaseq.api.persistence.entities.Hisat2ReferenceGenomeIndex;

/**
 * The default {@code Hisat2Controller} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultHisat2Controller implements Hisat2Controller {
	private Hisat2BinariesExecutor hisat2BinariesExecutor;

	@Override
	public void setHisat2BinariesExecutor(Hisat2BinariesExecutor executor) {
		this.hisat2BinariesExecutor = executor;
	}

	@Override
	public void buildIndex(File file, File outDir, String baseName)
		throws ExecutionException, InterruptedException {
		final ExecutionResult result =
			this.hisat2BinariesExecutor.buildIndex(file, outDir, baseName);
		
		checkResult(result);
	}

	@Override
	public void alignReads(Hisat2ReferenceGenomeIndex genome, File reads1,
		File reads2, boolean dta, String params, File output
	) throws ExecutionException, InterruptedException {
		final ExecutionResult result = this.hisat2BinariesExecutor
			.alignReads(genome, reads1, reads2, dta, params, output);

		checkResult(result);
	}

	@Override
	public void alignReads(Hisat2ReferenceGenomeIndex genome, File reads,
		boolean dta, String params, File output
	) throws ExecutionException, InterruptedException {
		final ExecutionResult result = this.hisat2BinariesExecutor
			.alignReads(genome, reads, dta, params, output);

		checkResult(result);
	}

	@Override
	public void alignReads(Hisat2ReferenceGenomeIndex genome, File reads1,
		File reads2, boolean dta, String params, File output,
		boolean saveAlignmentLog
	) throws ExecutionException, InterruptedException {
		if(saveAlignmentLog) {
			alignReads(genome, reads1, reads2, dta, params, output,
				getAlignmentLogFile(output));
		} else {
			alignReads(genome, reads1, reads2, dta, params , output);
		}
	}

	@Override
	public void alignReads(Hisat2ReferenceGenomeIndex genome, File reads,
		boolean dta, String params, File output, boolean saveAlignmentLog
	) throws ExecutionException, InterruptedException {
		if(saveAlignmentLog) {
			alignReads(genome, reads, dta, params, output,
				getAlignmentLogFile(output));
		} else {
			alignReads(genome, reads, dta, params, output);
		}
	}

	private static File getAlignmentLogFile(File outputFile) {
		return new File(outputFile.getAbsoluteFile() + ".txt");
	}

	@Override
	public void alignReads(Hisat2ReferenceGenomeIndex genome, File reads1,
		File reads2, boolean dta, String params, File output, File alignmentLog
	) throws ExecutionException, InterruptedException {
		final ExecutionResult result = this.hisat2BinariesExecutor
			.alignReads(genome, reads1, reads2, dta, params, output, alignmentLog);

		checkResult(result);
	}

	@Override
	public void alignReads(Hisat2ReferenceGenomeIndex genome, File reads,
		boolean dta, String params, File output, File alignmentLog
	) throws ExecutionException, InterruptedException {
		final ExecutionResult result = this.hisat2BinariesExecutor
			.alignReads(genome, reads, dta, params, output, alignmentLog);

		checkResult(result);
	}

	private void checkResult(ExecutionResult result) throws ExecutionException {
		if (result.getExitStatus() != 0) {
			throw new ExecutionException(result.getExitStatus(),
				"Error executing HISAT2. Please, check error log.", "");
		}
	}
}
