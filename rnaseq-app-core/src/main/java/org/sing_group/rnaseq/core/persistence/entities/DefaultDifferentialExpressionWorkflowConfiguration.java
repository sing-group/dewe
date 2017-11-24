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
package org.sing_group.rnaseq.core.persistence.entities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.api.persistence.entities.DifferentialExpressionWorkflowConfiguration;
import org.sing_group.rnaseq.api.persistence.entities.ReferenceGenomeIndex;
import org.sing_group.rnaseq.core.entities.DefaultFastqReadsSamples;

public class DefaultDifferentialExpressionWorkflowConfiguration
	implements DifferentialExpressionWorkflowConfiguration {
	private static final long serialVersionUID = 1L;

	private ReferenceGenomeIndex referenceGenome;
	private FastqReadsSamples reads;
	private File referenceAnnotationFile;
	private File workingDirectory;

	public DefaultDifferentialExpressionWorkflowConfiguration(
		ReferenceGenomeIndex referenceGenome, FastqReadsSamples reads,
		File referenceAnnotationFile, File workingDirectory
	) {
		this.referenceGenome = referenceGenome;
		this.reads = reads;
		this.referenceAnnotationFile = referenceAnnotationFile;
		this.workingDirectory = workingDirectory;
	}

	public ReferenceGenomeIndex getReferenceGenome() {
		return referenceGenome;
	}

	public void setReferenceGenome(ReferenceGenomeIndex referenceGenome) {
		this.referenceGenome = referenceGenome;
	}

	public FastqReadsSamples getReads() {
		return reads;
	}

	public void setReads(FastqReadsSamples reads) {
		this.reads = reads;
	}

	public File getReferenceAnnotationFile() {
		return referenceAnnotationFile;
	}

	public void setReferenceAnnotationFile(File referenceAnnotationFile) {
		this.referenceAnnotationFile = referenceAnnotationFile;
	}

	public File getWorkingDirectory() {
		return workingDirectory;
	}

	public void setWorkingDirectory(File workingDirectory) {
		this.workingDirectory = workingDirectory;
	}

	public static void persistWorkflowConfiguration(
		ReferenceGenomeIndex referenceGenome, FastqReadsSamples reads,
		File referenceAnnotationFile, File workingDirectory, File output
	) {
		DefaultDifferentialExpressionWorkflowConfiguration object = 
			new DefaultDifferentialExpressionWorkflowConfiguration(
				referenceGenome, reads, referenceAnnotationFile, 
				workingDirectory
			);

		FileOutputStream fos;
		try {
			fos = new FileOutputStream(output);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(object);
			oos.close();
			fos.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static DefaultDifferentialExpressionWorkflowConfiguration loadWorkflowConfiguration(
		File file
	) {
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			DefaultDifferentialExpressionWorkflowConfiguration toret = 
				(DefaultDifferentialExpressionWorkflowConfiguration) 
				ois.readObject();
			ois.close();
			fis.close();

			return toret;
		} catch (ClassNotFoundException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, FastqReadsSamples> getExperimentalConditionsAndSamples() {
		Map<String, FastqReadsSamples> map = new HashMap<>();
		getReads().forEach(r -> {
			map.putIfAbsent(r.getCondition(), new DefaultFastqReadsSamples());
			map.get(r.getCondition()).add(r);
		});

		return map;
	}
}