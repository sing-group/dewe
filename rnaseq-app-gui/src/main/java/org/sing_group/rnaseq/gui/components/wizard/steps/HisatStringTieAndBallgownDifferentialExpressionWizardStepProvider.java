package org.sing_group.rnaseq.gui.components.wizard.steps;

/**
 * The interface that defines a step provider for the HISAT2, StringTie and
 * Ballgown wizard.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface HisatStringTieAndBallgownDifferentialExpressionWizardStepProvider {
	/**
	 * Returns a {@code Hisat2ReferenceGenomeSelectionStep}.
	 * 
	 * @return a {@code Hisat2ReferenceGenomeSelectionStep}
	 */
	public abstract Hisat2ReferenceGenomeSelectionStep getHisat2ReferenceGenomeSelectionStep();
}
