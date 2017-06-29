package org.sing_group.rnaseq.gui.components.wizard.steps;

import org.sing_group.rnaseq.gui.util.TestUtils;

public class Bowtie2ReferenceGenomeIndexSelectionStepTest {

	public static void main(String[] args) {
		Bowtie2ReferenceGenomeIndexSelectionStep step = 
			new Bowtie2ReferenceGenomeIndexSelectionStep(
				TestUtils.createReferenceGenomeDatabaseManager()
			);
		step.addWizardStepListener(new DefaultWizardStepListener());
		TestUtils.showStepComponent(step);
	}
}
