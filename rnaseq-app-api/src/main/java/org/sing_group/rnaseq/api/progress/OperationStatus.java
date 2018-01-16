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
package org.sing_group.rnaseq.api.progress;

/**
 * A representation of the status of a operation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface OperationStatus {
	/**
	 * Returns the overall progress of the operation.
	 * 
	 * @return a float between 0 and 1 indicating the overall progress of the
	 *         operation
	 */
	public float getOverallProgress();

	/**
	 * Sets the overall progress of the operation.
	 * 
	 * @param overalProgress a float between 0 and 1 indicating the overall 
	 *        progress of the operation
	 */
	public void setOverallProgress(float overalProgress);

	/**
	 * Returns the current stage of the operation.
	 * 
	 * @return the current stage of the operation
	 */
	public String getStage();

	/**
	 * Sets the current stage of the operation.
	 * 
	 * @param stage the current stage of the operation
	 */
	public void setStage(String stage);

	/**
	 * Returns the progress of the current stage.
	 * 
	 * @return a float between 0 and 1 indicating the progress of the current
	 *         stage
	 */
	public float getStageProgress();

	/**
	 * Sets the progress of the current stage.
	 * 
	 * @param stageProgress a float between 0 and 1 indicating the progress of 
	 *        the current stage
	 */
	public void setStageProgress(float stageProgress);

	/**
	 * Returns the current sub stage of the operation.
	 * 
	 * @return the current sub stage of the operation
	 */
	public String getSubStage();

	/**
	 * Sets the current sub stage of the operation.
	 * 
	 * @param subStage the current sub stage of the operation
	 */
	public void setSubStage(String subStage);
}
