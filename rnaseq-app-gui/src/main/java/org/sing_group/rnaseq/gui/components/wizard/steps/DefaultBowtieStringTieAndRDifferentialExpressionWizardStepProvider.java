package org.sing_group.rnaseq.gui.components.wizard.steps;

import org.sing_group.rnaseq.core.persistence.DefaultReferenceGenomeDatabaseManager;

/**
 * The default
 * {@code BowtieStringTieAndRDifferentialExpressionWizardStepProvider}
 * implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultBowtieStringTieAndRDifferentialExpressionWizardStepProvider
	implements BowtieStringTieAndRDifferentialExpressionWizardStepProvider {

	@Override
	public Bowtie2ReferenceGenomeSelectionStep getBowtie2ReferenceGenomeSelectionStep() {
		return new Bowtie2ReferenceGenomeSelectionStep(
			DefaultReferenceGenomeDatabaseManager.getInstance());
	}
}
