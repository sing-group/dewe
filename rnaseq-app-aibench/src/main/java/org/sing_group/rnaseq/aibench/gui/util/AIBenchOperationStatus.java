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
