package org.sing_group.rnaseq.aibench.gui.wizard.steps;

import org.sing_group.rnaseq.gui.components.wizard.steps.Hisat2ReferenceGenomeIndexSelectionStep;
import org.sing_group.rnaseq.gui.components.wizard.steps.HisatStringTieAndBallgownDifferentialExpressionWizardStepProvider;

/**
 * An implementation of
 * {@code HisatStringTieAndBallgownDifferentialExpressionWizardStepProvider} to use in
 * the AIBench module.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class AIBenchHisatStringTieAndBallgownDifferentialExpressionWizardStepProvider
	implements HisatStringTieAndBallgownDifferentialExpressionWizardStepProvider {

	@Override
	public Hisat2ReferenceGenomeIndexSelectionStep getHisat2ReferenceGenomeSelectionStep() {
		return new AIBenchHisat2ReferenceGenomeIndexSelectionStep();
	}
}
