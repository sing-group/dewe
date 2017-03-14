package org.sing_group.rnaseq.gui.components.wizard.steps;

import org.sing_group.rnaseq.gui.util.TestUtils;

public class TwoExperimentalConditionsSelectionStepTest {

	public static void main(String[] args) {
		ExperimentalConditionsStep step = new ExperimentalConditionsStep(2, 2);
		step.addWizardStepListener(new DefaultWizardStepListener());
		TestUtils.showStepComponent(step);
	}
}
