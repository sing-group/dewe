package org.sing_group.rnaseq.aibench.gui.workflow;

import org.sing_group.rnaseq.api.entities.WorkflowDescription;

/**
 * The interface that defines the description of a workflow in AIBench.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface AIBenchWorkflowDescription extends WorkflowDescription {
	public abstract void launchWorkflowWizard();
}
