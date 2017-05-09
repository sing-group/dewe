package org.sing_group.rnaseq.aibench.gui.wizard.steps;

import org.sing_group.rnaseq.gui.components.wizard.steps.Bowtie2ReferenceGenomeIndexSelectionStep;
import org.sing_group.rnaseq.gui.components.wizard.steps.BowtieStringTieAndRDifferentialExpressionWizardStepProvider;

/**
 * An implementation of
 * {@code BowtieStringTieAndRDifferentialExpressionWizardStepProvider} to use in
 * the AIBench module.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class AIBenchBowtieStringTieAndRDifferentialExpressionStepProvider
	implements BowtieStringTieAndRDifferentialExpressionWizardStepProvider {

	@Override
	public Bowtie2ReferenceGenomeIndexSelectionStep getBowtie2ReferenceGenomeSelectionStep() {
		return new AIBenchBowtie2ReferenceGenomeIndexSelectionStep();
	}
}