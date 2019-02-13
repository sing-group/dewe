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
package org.sing_group.rnaseq.aibench.operations;

import java.io.File;

import org.sing_group.rnaseq.aibench.datatypes.EdgeRWorkingDirectory;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;

@Operation(
	name = "View edgeR working directory",
	description = "Views an edgeR working directory."
)
public class ViewEdgeRWorkingDirectory {
	private File directory;

	@Port(
		direction = Direction.INPUT,
		name = "Directory",
		description = "Directory that contains the edgeR results.",
		allowNull = false,
		order = 1,
		extras="selectionMode=directories"
	)
	public void setDirectory(File directory) {
		this.directory = directory;
	}

	@Port(
		direction = Direction.OUTPUT,
		order = 2
	)
	public EdgeRWorkingDirectory getEdgeRWorkingDirectory() {
		return new EdgeRWorkingDirectory(directory);
	}
}
