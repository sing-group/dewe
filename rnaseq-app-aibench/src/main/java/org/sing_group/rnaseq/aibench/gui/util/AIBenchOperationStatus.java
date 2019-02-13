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
package org.sing_group.rnaseq.aibench.gui.util;

import org.sing_group.rnaseq.core.progress.DefaultOperationStatus;

import es.uvigo.ei.aibench.core.operation.annotation.ProgressProperty;

/**
 * An extension of {@code DefaultOperationStatus} to add AIBench's
 * {@code ProgressProperty} annotations.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class AIBenchOperationStatus extends DefaultOperationStatus {

	@ProgressProperty(label = "Sub stage: ", order = 1)
	@Override
	public String getSubStage() {
		return super.getSubStage();
	}

	@ProgressProperty(label = "Stage progress: ", order = 2)
	@Override
	public float getStageProgress() {
		return super.getStageProgress();
	}

	@ProgressProperty(label = "Stage: ", order = 3)
	@Override
	public String getStage() {
		return super.getStage();
	}

	@ProgressProperty(label = "Overall progress: ", order = 4)
	@Override
	public float getOverallProgress() {
		return super.getOverallProgress();
	}
}
