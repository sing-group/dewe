/*
 * #%L
 * DEWE API
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
package org.sing_group.rnaseq.api.persistence.entities;

import java.io.File;
import java.io.Serializable;
import java.util.Map;

import org.sing_group.rnaseq.api.controller.WorkflowController;
import org.sing_group.rnaseq.api.entities.FastqReadsSamples;

public interface DifferentialExpressionWorkflowConfiguration
	extends Serializable {
	public ReferenceGenomeIndex getReferenceGenome();

	public FastqReadsSamples getReads();

	public File getReferenceAnnotationFile();

	public File getWorkingDirectory();

	public Map<String, FastqReadsSamples> getExperimentalConditionsAndSamples();

	public Map<WorkflowController.Parameters, String> getCommandLineApplicationsParameters();
}
