package org.sing_group.rnaseq.gui.components.wizard.steps;

import org.sing_group.rnaseq.gui.util.TestUtils;

public class Hisat2ReferenceGenomeIndexSelectionStepTest {

	public static void main(String[] args) {
		Hisat2ReferenceGenomeIndexSelectionStep step = 
			new Hisat2ReferenceGenomeIndexSelectionStep(
				TestUtils.createReferenceGenomeDatabaseManager()
			);
		step.addWizardStepListener(new DefaultWizardStepListener());
		TestUtils.showStepComponent(step);
	}
}
