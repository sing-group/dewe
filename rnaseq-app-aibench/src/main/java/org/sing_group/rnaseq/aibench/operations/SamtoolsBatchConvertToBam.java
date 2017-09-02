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
package org.sing_group.rnaseq.aibench.operations;

import static org.sing_group.rnaseq.aibench.gui.util.PortConfiguration.EXTRAS_SAM_FILES;

import java.io.File;
import java.util.Arrays;

import es.uvigo.ei.aibench.core.Core;
import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;

@Operation(
	name = "Convert several sam files to sorted bam files", 
	description = "Converts several sam files into sorted bam files using samtools."
)
public class SamtoolsBatchConvertToBam {
	private File[] inputFiles;

	@Port(
		direction = Direction.INPUT, 
		name = "Input sam files",
		description = "Input sam files",
		allowNull = false,
		order = 1,
		extras = EXTRAS_SAM_FILES
	)
	public void setInputFile(File[] files) {
		this.inputFiles = files;

		this.runOperation();
	}

	private void runOperation() {
		for(final File f : inputFiles) {
			Core.getInstance().executeOperation(
				"operations.samtoolsconvertsamtobam", null, 
				Arrays.asList(new Object[]{f, null})
			);
		}
	}
}

