package org.sing_group.rnaseq.aibench.gui.workflow;

import static java.util.Arrays.asList;

import java.util.List;

import org.sing_group.rnaseq.api.entities.WorkflowDescription;
import org.sing_group.rnaseq.gui.workflow.WorkflowCatalogPanel;

public class AIBenchWorkflowCatalogPanel extends WorkflowCatalogPanel {
	private static final long serialVersionUID = 1L;

	private static final List<WorkflowDescription> WORKFLOWS = asList(
		new Bowtie2StringTieDifferentialExpressionAIBenchWorkflowDescription(),
		new Hisat2StringTieBallgownDifferentialExpressionAIBenchWorkflowDescription()
	);

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
