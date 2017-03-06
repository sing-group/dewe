package org.sing_group.rnaseq.api.progress;

public interface OperationStatus {
	public void setStage(String stage);

	public String getStage();

	public float getTotal();

	public void setTotal(float total);

	public String getSubtask();

	public void setSubtask(String subtask);

	public void setSubtaskProgress(float subtask);

	public float getSubtaskProgress();
}
