/*
 * #%L
 * DEWE Core
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
package org.sing_group.rnaseq.core.progress;

import org.sing_group.rnaseq.api.progress.OperationStatus;

/**
 * The default implementation of {@link OperationStatus}. 
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultOperationStatus implements OperationStatus {

	private String subStage;
	private float overalProgress = 0.0f;
	private String stage;
	private float stageProgress = 0.0f;

	@Override
	public void setStage(String stage) {
		this.stage = stage;
	}

	@Override
	public String getStage() {
		return this.stage;
	}

	@Override
	public float getOverallProgress() {
		return this.overalProgress;
	}

	@Override
	public void setOverallProgress(float overalProgress) {
		this.overalProgress = overalProgress;
	}

	@Override
	public String getSubStage() {
		return this.subStage;
	}

	@Override
	public void setSubStage(String subStage) {
		this.subStage = subStage;
	}

	@Override
	public void setStageProgress(float stageProgress) {
		this.stageProgress = stageProgress;
	}

	@Override
	public float getStageProgress() {
		return stageProgress;
	}
}
