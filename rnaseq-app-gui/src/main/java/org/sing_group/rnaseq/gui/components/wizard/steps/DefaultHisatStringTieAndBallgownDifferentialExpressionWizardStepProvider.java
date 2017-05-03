package org.sing_group.rnaseq.gui.components.wizard.steps;

import org.sing_group.rnaseq.core.persistence.DefaultReferenceGenomeDatabaseManager;

/**
 * The default
 * {@code HisatStringTieAndBallgownDifferentialExpressionWizardStepProvider}
 * implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultHisatStringTieAndBallgownDifferentialExpressionWizardStepProvider
	implements
	HisatStringTieAndBallgownDifferentialExpressionWizardStepProvider {

	@Override
	public Hisat2ReferenceGenomeSelectionStep getHisat2ReferenceGenomeSelectionStep() {
		return new Hisat2ReferenceGenomeSelectionStep(
			DefaultReferenceGenomeDatabaseManager.getInstance());
	}
}
