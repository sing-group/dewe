package org.sing_group.rnaseq.gui.components.wizard.steps;

/**
 * The interface that defines a step provider for the Bowtie2, StringTie and R
 * wizard.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface BowtieStringTieAndRDifferentialExpressionWizardStepProvider {
	/**
	 * Returns a {@code Bowtie2ReferenceGenomeSelectionStep}.
	 * 
	 * @return a {@code Bowtie2ReferenceGenomeSelectionStep}
	 */
	public abstract Bowtie2ReferenceGenomeSelectionStep getBowtie2ReferenceGenomeSelectionStep();
}
