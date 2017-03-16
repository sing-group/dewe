package org.sing_group.rnaseq.aibench.gui.util;

import org.sing_group.rnaseq.core.progress.DefaultOperationStatus;

import es.uvigo.ei.aibench.core.operation.annotation.ProgressProperty;

public class FileOperationStatus extends DefaultOperationStatus {

	@ProgressProperty(ignore = true)
	@Override
	public String getSubtask() {
		return super.getSubtask();
	}

	@ProgressProperty(ignore = true)
	@Override
	public float getSubtaskProgress() {
		return super.getSubtaskProgress();
	}

	@ProgressProperty(label = "File: ", order = 1)
	@Override
	public String getStage() {
		return super.getStage();
	}

	@ProgressProperty(ignore = true)
	@Override
	public float getTotal() {
		return super.getTotal();
	}
}
