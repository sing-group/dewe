package org.sing_group.rnaseq.aibench.gui.util;

import org.sing_group.rnaseq.core.progress.DefaultOperationStatus;

import es.uvigo.ei.aibench.core.operation.annotation.ProgressProperty;

public class AIBenchOperationStatus extends DefaultOperationStatus {

	@ProgressProperty(label = "Task: ", order = 1)
	@Override
	public String getSubtask() {
		return super.getSubtask();
	}

	@ProgressProperty(label = "Task progress: ", order = 2)
	@Override
	public float getSubtaskProgress() {
		return super.getSubtaskProgress();
	}

	@ProgressProperty(label = "Stage: ", order = 3)
	@Override
	public String getStage() {
		return super.getStage();
	}

	@ProgressProperty(label = "Total progress: ", order = 4)
	@Override
	public float getTotal() {
		return super.getTotal();
	}
}
