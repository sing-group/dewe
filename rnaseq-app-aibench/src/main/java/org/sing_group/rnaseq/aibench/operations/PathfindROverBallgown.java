/*
 * #%L
 * DEWE API
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
package org.sing_group.rnaseq.aibench.operations;

import static javax.swing.SwingUtilities.invokeLater;

import java.io.File;

import org.sing_group.rnaseq.aibench.datatypes.PathfindRWorkingDirectory;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.core.controller.DefaultAppController;
import org.sing_group.rnaseq.core.environment.execution.parameters.pathfindr.DefaultPathfindRClusteringMethod;
import org.sing_group.rnaseq.core.environment.execution.parameters.pathfindr.DefaultPathfindRGeneSets;

import es.uvigo.ei.aibench.core.Core;
import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.workbench.Workbench;

@Operation(
		name = "Pathways enrichment with PathfindR over Ballgown",
		description = "Performs a pathways enrichment using PathfindR over Ballgown results."
	)
public class PathfindROverBallgown {
	private File directory;
	private File outputDir;
	private boolean humanGeneSymbols;
	private DefaultPathfindRGeneSets geneSets;
	private DefaultPathfindRClusteringMethod clusteringMethod;

	@Port(
		direction = Direction.INPUT,
		name = "Directory",
		description = "Directory that contains the Ballgown results.",
		allowNull = false,
		order = 1,
		extras="selectionMode=directories"
	)
	public void setDirectory(File directory) {
		this.directory = directory;
	}
	
	@Port(
		direction = Direction.INPUT,
		name = "Human gene symbols",
		description = "Whether the input genes are human gene symbols or not.",
		allowNull = false,
		order = 2,
		defaultValue = "true",
		advanced = false
	)
	public void setHumanGeneSymbols(boolean humanGeneSymbols) {
		this.humanGeneSymbols = humanGeneSymbols;
	}
	
	@Port(
		direction = Direction.INPUT,
		name = "Gene sets",
		description = "The gene sets to be used for enrichment analysis.",
		allowNull = false,
		order = 3,
		extras = "mode=radiobuttons, numrows=2, numcolumns=4",
		advanced = false,
		defaultValue = DefaultPathfindRGeneSets.DEFAULT_VALUE_STR
	)
	public void setPathfindRGeneSets(
			DefaultPathfindRGeneSets geneSets
	) {
		this.geneSets = geneSets;
	}

	@Port(
		direction = Direction.INPUT,
		name = "Clustering method",
		description = "The agglomeration method to be used to cluster pathways.",
		allowNull = false,
		order = 4,
		extras = "mode=radiobuttons, numrows=2, numcolumns=4",
		advanced = false,
		defaultValue = DefaultPathfindRClusteringMethod.DEFAULT_VALUE_STR
	)
	public void setPathfindRClusteringMethod(
			DefaultPathfindRClusteringMethod clusteringMethod
	) {
		this.clusteringMethod = clusteringMethod;
	}	
	
	@Port(
		direction = Direction.INPUT,
		name = "Output folder",
		description = "The folder where the pathfindR results will be stored.",
		allowNull = true,
		order = 5,
		extras = "selectionMode=directories",
		advanced = true
	)
	public void setOutputFolder(File outputDir) {
		this.outputDir = outputDir == null ? this.directory : outputDir;

		this.runOperation();
	}
	
	private void runOperation() {
		try {
			File outputDirectory = new File(this.outputDir.toString()+"/pathfindr");
			if(! outputDirectory.exists())
				outputDirectory.mkdir();
			DefaultAppController.getInstance().getPathfindRBallgownController()
				.pathwaysEnrichment(directory, outputDirectory, humanGeneSymbols, geneSets.getParameter(), 
									clusteringMethod.getParameter());
			invokeLater(this::succeed);
			processOutputs();
		} catch (ExecutionException e) {
			Workbench.getInstance().error(e, e.getMessage());
		} catch (InterruptedException e) {
			Workbench.getInstance().error(e, e.getMessage());
		}
	}

	private void processOutputs() {
		File outputDirectory = new File(this.outputDir.toString()+"/pathfindr");
		PathfindRWorkingDirectory pathfindRDirectory =
				new PathfindRWorkingDirectory(outputDirectory);
		Core.getInstance().getClipboard()
			.putItem(pathfindRDirectory, pathfindRDirectory.getName());
	}

	private void succeed() {
		Workbench.getInstance().info(
			"Succeed: PathfindR results can be found at " +
			this.outputDir.getAbsolutePath() + "."
		);
	}
}
