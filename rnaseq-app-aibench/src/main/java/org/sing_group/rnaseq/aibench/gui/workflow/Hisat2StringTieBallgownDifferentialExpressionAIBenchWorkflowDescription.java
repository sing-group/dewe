package org.sing_group.rnaseq.aibench.gui.workflow;

import org.sing_group.rnaseq.aibench.gui.wizard.AIBenchHisatStringTieAndBallgownDifferentialExpressionWizard;
import org.sing_group.rnaseq.gui.workflow.Hisat2StringTieBallgownDifferentialExpressionWorkflowDescription;

/**
 * The AIBench description for the differential expression workflow that uses
 * HISAT2, StringTie and Ballgown.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class Hisat2StringTieBallgownDifferentialExpressionAIBenchWorkflowDescription
	extends Hisat2StringTieBallgownDifferentialExpressionWorkflowDescription 
	implements AIBenchWorkflowDescription {

	@Override
	public void launchWorkflowWizard() {
		AIBenchHisatStringTieAndBallgownDifferentialExpressionWizard.showWizard();
	}
}
