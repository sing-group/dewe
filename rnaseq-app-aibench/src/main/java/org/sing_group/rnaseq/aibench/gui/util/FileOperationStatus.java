package org.sing_group.rnaseq.aibench.gui.util;

import org.sing_group.rnaseq.core.progress.DefaultOperationStatus;

import es.uvigo.ei.aibench.core.operation.annotation.ProgressProperty;

/**
 * An extension of {@link DefaultOperationStatus} that hides all properties but
 * stage, which is used to show the file being processed.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class FileOperationStatus extends DefaultOperationStatus {

	@ProgressProperty(ignore = true)
	@Override
	public String getSubStage() {
		return super.getSubStage();
	}

	@ProgressProperty(ignore = true)
	@Override
	public float getStageProgress() {
		return super.getStageProgress();
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
