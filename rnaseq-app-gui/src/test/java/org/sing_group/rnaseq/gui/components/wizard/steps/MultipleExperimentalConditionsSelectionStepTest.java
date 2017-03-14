package org.sing_group.rnaseq.gui.components.wizard.steps;

import org.sing_group.rnaseq.gui.util.TestUtils;

public class MultipleExperimentalConditionsSelectionStepTest {

	public static void main(String[] args) {
		ExperimentalConditionsStep step = new ExperimentalConditionsStep(1, 4);
		step.addWizardStepListener(new DefaultWizardStepListener());
		TestUtils.showStepComponent(step);
	}
}
