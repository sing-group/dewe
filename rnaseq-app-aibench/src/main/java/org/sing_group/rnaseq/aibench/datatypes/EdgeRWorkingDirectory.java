/*
 * #%L
 * DEWE
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
package org.sing_group.rnaseq.aibench.datatypes;

import java.io.File;

import es.uvigo.ei.aibench.core.datatypes.annotation.Datatype;
import es.uvigo.ei.aibench.core.datatypes.annotation.Property;
import es.uvigo.ei.aibench.core.datatypes.annotation.Structure;

/**
 * An AIBench datatype to wrap an edgeR working directory.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
@Datatype(
	structure = Structure.COMPLEX,
	namingMethod = "getName",
	renameable = true,
	clipboardClassName = "edgeR working directory",
	autoOpen = true
)
public class EdgeRWorkingDirectory {
	private File workingDirectory;

	/**
	 * Creates a new {@code EdgeRWorkingDirectory} with the specified
	 * {@code workingDirectory}.
	 *
	 * @param workingDirectory the file that represents the working directory
	 */
	public EdgeRWorkingDirectory(File workingDirectory) {
		this.workingDirectory = workingDirectory;
	}

	/**
	 * Returns the name of the datatype
	 *
	 * @return the name of the datatype
	 */
	public String getName() {
		return this.workingDirectory.getName();
	}

	/**
	 * Returns the {@code File} that represents the working directory.
	 *
	 * @return the {@code File} that represents the working directory
	 */
	@Property(name = "Directory")
	public File getWorkingDirectory() {
		return this.workingDirectory;
	}
}
