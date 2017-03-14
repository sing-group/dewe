package org.sing_group.rnaseq.gui.components.wizard.steps;

import es.uvigo.ei.sing.hlfernandez.wizard.event.WizardStepEvent;
import es.uvigo.ei.sing.hlfernandez.wizard.event.WizardStepListener;

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
}
