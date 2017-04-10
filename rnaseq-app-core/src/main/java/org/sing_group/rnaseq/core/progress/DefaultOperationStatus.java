package org.sing_group.rnaseq.core.progress;

import org.sing_group.rnaseq.api.progress.OperationStatus;

/**
 * The default implementation of {@link OperationStatus}. 
 * 
 * @author Hugo López-Fernández
 * @author A. Blanco-Míguez
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
	public float getTotal() {
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
