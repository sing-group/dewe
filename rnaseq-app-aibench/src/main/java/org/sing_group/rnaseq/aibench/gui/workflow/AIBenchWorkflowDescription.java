package org.sing_group.rnaseq.aibench.gui.workflow;

import org.sing_group.rnaseq.api.entities.WorkflowDescription;

public interface AIBenchWorkflowDescription extends WorkflowDescription {

	public abstract void launchWorkflowWizard();
}
