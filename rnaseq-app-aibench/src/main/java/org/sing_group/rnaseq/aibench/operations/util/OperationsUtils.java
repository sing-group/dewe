/*
 * #%L
 * DEWE
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
package org.sing_group.rnaseq.aibench.operations.util;

import static org.sing_group.rnaseq.gui.sample.FastqSampleEditor.extractSampleNameFromReadsFile;

import java.io.File;

import org.sing_group.rnaseq.core.controller.DefaultAppController;

/**
 * A class that provides utilities for AIBench's operations classes.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class OperationsUtils {

	private static final String SAM = ".sam";

	/**
	 * If {@code outputFile} is not {@code null}, then it is returned after
	 * adding the {@code .sam} extension is added to it if not present.
	 * 
	 * If {@code outputFile} is {@code null}, then it is created a new file
	 * alongside {@code readsFile} by extracting the name of the reads file and
	 * adding the {@code .sam} extension.
	 * 
	 * @param outputFile the target sam output file
	 * @param readsFile the target reads file
	 * @return a file with {@code .sam} extension
	 */
	public static File getSamOutputFile(File outputFile, File readsFile) {
		if (outputFile == null) {
			String sampleName = extractSampleNameFromReadsFile(readsFile);

			return new File(readsFile.getParentFile(), sampleName + SAM);
		} else {
			return outputFile.getName().endsWith(SAM) ? outputFile
				: new File(outputFile.getAbsolutePath() + SAM);
		}
	}
	
	public static void validateName(String name) {
		if (name.isEmpty()) {
			throw new IllegalArgumentException("Index name can't be empty");
		} else if (DefaultAppController.getInstance()
			.getReferenceGenomeDatabaseManager().existsName(name)) {
			throw new IllegalArgumentException(
				"Index name is already registered. Please, choose a different name");
		}
	}
}
