package org.sing_group.rnaseq.core.progress;

import org.sing_group.rnaseq.api.progress.OperationStatus;

public class DefaultOperationStatus implements OperationStatus {

	private String subtask;
	private float total = 0.0f;
	private String stage;
	private float subtaskprogress = 0.0f;

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
		return this.total;
	}

	@Override
	public void setTotal(float total) {
		this.total = total;
	}

	@Override
	public String getSubtask() {
		return this.subtask;
	}

	@Override
	public void setSubtask(String subtask) {
		this.subtask = subtask;
	}

	@Override
	public void setSubtaskProgress(float subtask) {
		this.subtaskprogress = subtask;
	}

	@Override
	public float getSubtaskProgress() {
		return subtaskprogress;
	}
}