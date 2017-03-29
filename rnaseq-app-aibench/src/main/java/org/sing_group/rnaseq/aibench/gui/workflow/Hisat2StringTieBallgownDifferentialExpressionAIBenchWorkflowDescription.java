package org.sing_group.rnaseq.aibench.gui.workflow;

import org.sing_group.rnaseq.aibench.gui.wizard.AIBenchHisatStringTieAndBallgownDifferentialExpressionWizard;
import org.sing_group.rnaseq.gui.workflow.Hisat2StringTieBallgownDifferentialExpressionWorkflowDescription;

public class Hisat2StringTieBallgownDifferentialExpressionAIBenchWorkflowDescription
	extends Hisat2StringTieBallgownDifferentialExpressionWorkflowDescription 
	implements AIBenchWorkflowDescription {

	@Override
	public void launchWorkflowWizard() {
		AIBenchHisatStringTieAndBallgownDifferentialExpressionWizard.showWizard();
	}
}
