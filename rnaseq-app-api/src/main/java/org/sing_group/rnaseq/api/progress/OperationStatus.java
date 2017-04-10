package org.sing_group.rnaseq.api.progress;

/**
 * A representation of the status of a operation.
 * 
 * @author Hugo López-Fernández
 * @author A. Blanco-Míguez
 *
 */
public interface OperationStatus {
	/**
	 * Returns the overall progress of the operation.
	 * 
	 * @return a float between 0 and 1 indicating the overall progress of the
	 *         operation
	 */
	public float getTotal();

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
