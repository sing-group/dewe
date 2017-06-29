package org.sing_group.rnaseq.gui.components.wizard.steps;

import org.sing_group.gc4s.wizard.event.WizardStepEvent;
import org.sing_group.gc4s.wizard.event.WizardStepListener;

public class DefaultWizardStepListener implements WizardStepListener {

	public void wizardStepUncompleted(WizardStepEvent event) {
		System.err.println("Step uncompleted. Is completed = "
			+ event.getSource().isStepCompleted());
	}

	@Override
	public void wizardStepCompleted(WizardStepEvent event) {
		System.err.println("Step completed. Is completed = "
			+ event.getSource().isStepCompleted());
	}

	@Override
	public void wizardStepNextButtonTooltipChanged(String nextButtonTooltip) {
		System.err.println("Change next button tooltip to: " + nextButtonTooltip);
	}
}
