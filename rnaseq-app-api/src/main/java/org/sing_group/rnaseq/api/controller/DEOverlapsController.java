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
package org.sing_group.rnaseq.api.controller;

import java.io.File;

import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.RBinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.parameters.ImageConfigurationParameter;
import org.sing_group.rnaseq.api.environment.execution.parameters.VennDiagramConfigurationParameter;

/**
 * The interface for controlling the DE overlaps Script.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface DEOverlapsController {
	/**
	 * Sets the {@code RBinariesExecutor} to use.
	 *
	 * @param executor the {@code RBinariesExecutor} to use
	 */
	public abstract void setRBinariesExecutor(
		RBinariesExecutor executor);

	/**
	 * <p>
	 * Detects overlaps between Ballgown and edgeR differential expression 
	 * analysis  and stores the results in {@code workingDirectory}.
	 * </p>
	 *
	 * @param workingDir the directory where DE results are stored
	 * @param outputDir the directory where results must be stored
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public abstract void ballgownEdgerROverlaps(File workingDir, File outputDir)
		throws ExecutionException, InterruptedException;


	public void createBallgownEdgerRVennDiagram(File workingDirectory,
			VennDiagramConfigurationParameter imageConfiguration)
		throws ExecutionException, InterruptedException ;
}
