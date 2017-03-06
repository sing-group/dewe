package org.sing_group.rnaseq.gui.components.wizard.steps;

import org.sing_group.rnaseq.gui.util.TestUtils;

import es.uvigo.ei.sing.hlfernandez.demo.DemoUtils;

public class ReferenceGenomeSelectionStepTest {

	public static void main(String[] args) {
		Bowtie2ReferenceGenomeSelectionStep step = 
			new Bowtie2ReferenceGenomeSelectionStep(TestUtils.createReferenceGenomeDatabaseManager()
		);
		DemoUtils.showComponent(step.getStepComponent());
	}
}
