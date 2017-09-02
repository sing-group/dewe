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
import java.util.List;

import org.sing_group.rnaseq.api.controller.StringTieController;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;
import org.sing_group.rnaseq.api.environment.execution.StringTieBinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.SystemBinariesExecutor;

/**
 * The default {@code StringTieController} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultStringTieController implements StringTieController {
	private StringTieBinariesExecutor stringTieBinariesExecutor;
	private SystemBinariesExecutor systemBinariesExecutor;

	@Override
	public void setStringTieBinariesExecutor(StringTieBinariesExecutor executor) {
		this.stringTieBinariesExecutor = executor;
	}

	@Override
	public void setSystemBinariesExecutor(SystemBinariesExecutor executor) {
		this.systemBinariesExecutor = executor;
	}

	@Override
	public void obtainLabeledTranscripts(File referenceAnnotationFile,
		File inputBam, File outputTranscripts, String label)
		throws ExecutionException, InterruptedException {
		final ExecutionResult result =
			this.stringTieBinariesExecutor.obtainLabeledTranscripts(
				referenceAnnotationFile, inputBam, outputTranscripts, label);

		if (result.getExitStatus() != 0) {
			throw new ExecutionException(result.getExitStatus(),
					"Error executing samtools. Please, check error log.", "");
		}
	}

	@Override
	public void obtainTranscripts(File referenceAnnotationFile, File inputBam,
		File outputTranscripts)
		throws ExecutionException, InterruptedException {
		final ExecutionResult result =
			this.stringTieBinariesExecutor.obtainTranscripts(
				referenceAnnotationFile, inputBam, outputTranscripts);

		fixStringTieTData(inputBam);

		if (result.getExitStatus() != 0) {
			throw new ExecutionException(result.getExitStatus(),
					"Error executing samtools. Please, check error log.", "");
		}
	}

	private void fixStringTieTData(File inputBam)
		throws ExecutionException, InterruptedException {
		String tDataPath = inputBam.getParent() + "/t_data.ctab";
		File tmp = new File(inputBam.getParent() + "/tmp.tmp");
		File tdata = new File(tDataPath);
		systemBinariesExecutor.awk(
			tmp,
			"-F", "\\t", "{if($12!=\"FPKM\")$12+=0.000001;}1", "OFS=\\t",
			tDataPath
		);
		tmp.renameTo(tdata);
	}

	@Override
	public void mergeTranscripts(File referenceAnnotationFile,
		List<File> inputAnnotationFiles, File mergedAnnotationFile)
		throws ExecutionException, InterruptedException {
		final ExecutionResult result =
			this.stringTieBinariesExecutor.mergeTranscripts(
				referenceAnnotationFile, inputAnnotationFiles, mergedAnnotationFile);

		if (result.getExitStatus() != 0) {
			throw new ExecutionException(result.getExitStatus(),
					"Error executing samtools. Please, check error log.", "");
		}
	}
}
