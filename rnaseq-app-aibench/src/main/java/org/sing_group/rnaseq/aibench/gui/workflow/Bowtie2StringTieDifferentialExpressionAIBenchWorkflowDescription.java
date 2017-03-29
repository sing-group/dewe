package org.sing_group.rnaseq.aibench.gui.workflow;

import org.sing_group.rnaseq.aibench.gui.wizard.AIBenchBowtieStringTieAndRDifferentialExpressionWizard;
import org.sing_group.rnaseq.gui.workflow.Bowtie2StringTieDifferentialExpressionWorkflowDescription;

public class Bowtie2StringTieDifferentialExpressionAIBenchWorkflowDescription
	extends Bowtie2StringTieDifferentialExpressionWorkflowDescription 
	implements AIBenchWorkflowDescription {

	@Override
	public void launchWorkflowWizard() {
		AIBenchBowtieStringTieAndRDifferentialExpressionWizard.showWizard();
	}
}
