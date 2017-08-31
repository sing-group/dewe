package org.sing_group.rnaseq.aibench.gui.workflow;

import static java.util.Arrays.asList;

import java.util.List;

import org.sing_group.rnaseq.api.entities.WorkflowDescription;
import org.sing_group.rnaseq.gui.workflow.WorkflowCatalogPanel;

/**
 * An extension of {@code WorkflowCatalogPanel} to use as AIBench component.
 * This class defines the available workflows and overrides the
 * {@code runWorkflow} method in order to execute them.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class AIBenchWorkflowCatalogPanel extends WorkflowCatalogPanel {
	private static final long serialVersionUID = 1L;

	private static final List<WorkflowDescription> WORKFLOWS = asList(
		new Bowtie2StringTieDifferentialExpressionAIBenchWorkflowDescription(),
		new Hisat2StringTieBallgownDifferentialExpressionAIBenchWorkflowDescription()
	);

	/**
	 * Creates a new {@code AIBenchWorkflowCatalogPanel} component.
	 */
	public AIBenchWorkflowCatalogPanel() {
		super(WORKFLOWS);
	}

	@Override
	protected void runWorkflow(WorkflowDescription workflow) {
		if (workflow instanceof AIBenchWorkflowDescription) {
			((AIBenchWorkflowDescription) workflow).launchWorkflowWizard();
		} else {
			super.runWorkflow(workflow);
		}
	}
}
