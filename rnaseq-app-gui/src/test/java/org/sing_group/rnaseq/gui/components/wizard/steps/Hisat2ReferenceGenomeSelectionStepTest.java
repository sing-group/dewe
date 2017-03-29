package org.sing_group.rnaseq.gui.components.wizard.steps;

import org.sing_group.rnaseq.gui.util.TestUtils;

public class Hisat2ReferenceGenomeSelectionStepTest {

	public static void main(String[] args) {
		Hisat2ReferenceGenomeSelectionStep step = 
			new Hisat2ReferenceGenomeSelectionStep(
				TestUtils.createReferenceGenomeDatabaseManager()
			);
		TestUtils.showStepComponent(step);
	}
}
