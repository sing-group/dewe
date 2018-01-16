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

import org.sing_group.rnaseq.api.controller.HtseqController;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;
import org.sing_group.rnaseq.api.environment.execution.HtseqBinariesExecutor;
import org.sing_group.rnaseq.core.util.FileUtils;

/**
 * The default {@code HtseqController} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultHtseqController implements HtseqController {
	private HtseqBinariesExecutor htseqBinariesExecutor;

	@Override
	public void setHtseqBinariesExecutor(HtseqBinariesExecutor executor) {
		this.htseqBinariesExecutor = executor;
	}

	@Override
	public void countBamReverseExon(File referenceAnnotationFile, File inputBam,
		File output) throws ExecutionException, InterruptedException {
		final ExecutionResult result = 
			this.htseqBinariesExecutor.countBamReverseExon(
				referenceAnnotationFile, inputBam, output);
		
		if (result.getExitStatus() != 0) {
			throw new ExecutionException(result.getExitStatus(),
					"Error executing htseq-count. Please, check error log.", "");
		}
	}

	@Override
	public void countBamReverseExon(File referenceAnnotationFile,
		File[] inputBams, File outputDir, File joinFile)
		throws ExecutionException, InterruptedException {
		
		File[] results = new File[inputBams.length];
		for (int i = 0; i < inputBams.length; i++) {
			File inputBam = inputBams[i];
			String fileName = FileUtils.removeExtension(inputBam);
			File resultTsv = new File(outputDir, fileName + ".tsv");
			countBamReverseExon(referenceAnnotationFile, inputBam, resultTsv);
			results[i] = resultTsv;
		}

		DefaultAppController.getInstance().getSystemController()
			.join(results, joinFile);
	}
}
