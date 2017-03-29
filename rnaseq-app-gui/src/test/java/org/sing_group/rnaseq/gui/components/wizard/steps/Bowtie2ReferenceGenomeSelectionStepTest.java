package org.sing_group.rnaseq.gui.components.wizard.steps;

import org.sing_group.rnaseq.gui.util.TestUtils;

public class Bowtie2ReferenceGenomeSelectionStepTest {

	public static void main(String[] args) {
		Bowtie2ReferenceGenomeSelectionStep step = 
			new Bowtie2ReferenceGenomeSelectionStep(
				TestUtils.createReferenceGenomeDatabaseManager()
			);
		TestUtils.showStepComponent(step);
	}
}
