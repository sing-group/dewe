package org.sing_group.rnaseq.gui.components.wizard.steps;

import org.sing_group.rnaseq.gui.util.TestUtils;

public class ReferenceAnnotationFileSelectionStepTest {

	public static void main(String[] args) {
		ReferenceAnnotationFileSelectionStep step = 
			new ReferenceAnnotationFileSelectionStep();
		step.addWizardStepListener(new DefaultWizardStepListener());
		TestUtils.showStepComponent(step);
	}
}