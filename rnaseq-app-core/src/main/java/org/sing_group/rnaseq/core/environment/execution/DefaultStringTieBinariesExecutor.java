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
package org.sing_group.rnaseq.core.environment.execution;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.sing_group.rnaseq.api.environment.binaries.StringTieBinaries;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;
import org.sing_group.rnaseq.api.environment.execution.StringTieBinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.check.BinaryCheckException;
import org.sing_group.rnaseq.core.environment.execution.check.DefaultStringTieBinariesChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default {@code StringTieBinaries} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultStringTieBinariesExecutor
	extends AbstractBinariesExecutor<StringTieBinaries>
	implements StringTieBinariesExecutor {
	private final static Logger LOG = LoggerFactory.getLogger(DefaultStringTieBinariesExecutor.class);  
	
	/**
	 * Creates a new {@code DefaultStringTieBinariesExecutor} instance to execute
	 * the specified {@code StringTieBinaries}.
	 * 
	 * @param binaries the {@code StringTieBinaries} to execute
	 * @throws BinaryCheckException if any of the commands can't be executed
	 */
	public DefaultStringTieBinariesExecutor(StringTieBinaries binaries)
		throws BinaryCheckException {
		this.setBinaries(binaries);
	}

	@Override
	public void setBinaries(StringTieBinaries binaries)
		throws BinaryCheckException {
		super.setBinaries(binaries);
		this.checkBinaries();
	}

	@Override
	public void checkBinaries() throws BinaryCheckException {
		DefaultStringTieBinariesChecker.checkAll(binaries);
	}

	@Override
	public ExecutionResult obtainLabeledTranscripts(File referenceAnnotationFile,
			File inputBam, File outputTranscripts, String label)
			throws ExecutionException, InterruptedException {
		return executeCommand(
			LOG,
			this.binaries.getStringTie(),
			"-p",
			getThreads(),
			"-G", 	referenceAnnotationFile.getAbsolutePath(),
			"-l",	label,
			"-o", 	outputTranscripts.getAbsolutePath(),
			inputBam.getAbsolutePath()
		);
	}

	@Override
	public ExecutionResult obtainTranscripts(File referenceAnnotationFile,
			File inputBam, File outputTranscripts)
			throws ExecutionException, InterruptedException {
		return executeCommand(
			LOG,
			this.binaries.getStringTie(),
			"-p",
			getThreads(),
			"-G", 	referenceAnnotationFile.getAbsolutePath(),
			"-e",
			"-B",
			"-o", 	outputTranscripts.getAbsolutePath(),
			inputBam.getAbsolutePath()
		);
	}
	
	@Override
	public ExecutionResult mergeTranscripts(File referenceAnnotationFile,
		List<File> inputAnnotationFiles, File mergedAnnotationFile)
		throws ExecutionException, InterruptedException {
		try {
			return mergeTranscripts(referenceAnnotationFile, 
				getMergedTxtFile(inputAnnotationFiles), mergedAnnotationFile);
		} catch (IOException e) {
			throw new ExecutionException(-1, e.getMessage());
		}
	}

	private static File getMergedTxtFile(List<File> inputAnnotationFiles)
		throws IOException {
		File mergeList = Files.createTempFile("merged-list", ".txt").toFile();
		try {
			Files.write(mergeList.toPath(),
				mergeList(inputAnnotationFiles).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return mergeList;
	}

	private static String mergeList(List<File> inputAnnotationFiles) {
		StringBuilder sb = new StringBuilder();
		for (File gtf : inputAnnotationFiles) {
			sb.append(gtf.getAbsolutePath()).append("\n");
		}
		return sb.toString();
	}

	@Override
	public ExecutionResult mergeTranscripts(File referenceAnnotationFile,
			File mergeList, File mergedAnnotationFile)
			throws ExecutionException, InterruptedException {
		return executeCommand(
			LOG,
			this.binaries.getStringTie(),
			"--merge", 
			"-p",
			getThreads(),
			"-G", 	referenceAnnotationFile.getAbsolutePath(),
			"-o", 	mergedAnnotationFile.getAbsolutePath(),
			mergeList.getAbsolutePath()
		);
	}
}
