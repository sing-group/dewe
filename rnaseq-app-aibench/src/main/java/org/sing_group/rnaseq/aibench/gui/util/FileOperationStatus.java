/*
 * #%L
 * DEWE
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
package org.sing_group.rnaseq.aibench.gui.util;

import org.sing_group.rnaseq.core.progress.DefaultOperationStatus;

import es.uvigo.ei.aibench.core.operation.annotation.ProgressProperty;

/**
 * An extension of {@link DefaultOperationStatus} that hides all properties but
 * stage, which is used to show the file being processed.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class FileOperationStatus extends DefaultOperationStatus {

	@ProgressProperty(ignore = true)
	@Override
	public String getSubStage() {
		return super.getSubStage();
	}

	@ProgressProperty(ignore = true)
	@Override
	public float getStageProgress() {
		return super.getStageProgress();
	}

	@ProgressProperty(label = "File: ", order = 1)
	@Override
	public String getStage() {
		return super.getStage();
	}

	@ProgressProperty(ignore = true)
	@Override
	public float getOverallProgress() {
		return super.getOverallProgress();
	}
}
