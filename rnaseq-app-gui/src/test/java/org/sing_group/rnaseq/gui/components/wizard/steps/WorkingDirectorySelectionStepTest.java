package org.sing_group.rnaseq.gui.components.wizard.steps;

import org.sing_group.rnaseq.gui.util.TestUtils;

public class WorkingDirectorySelectionStepTest {

	public static void main(String[] args) {
		WorkingDirectorySelectionStep step = 
			new WorkingDirectorySelectionStep();
		step.addWizardStepListener(new DefaultWizardStepListener());
		TestUtils.showStepComponent(step);
	}
}
