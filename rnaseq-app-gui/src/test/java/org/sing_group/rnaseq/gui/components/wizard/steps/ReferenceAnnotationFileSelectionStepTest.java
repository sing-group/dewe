package org.sing_group.rnaseq.gui.components.wizard.steps;

import es.uvigo.ei.sing.hlfernandez.demo.DemoUtils;
import es.uvigo.ei.sing.hlfernandez.wizard.event.WizardStepEvent;
import es.uvigo.ei.sing.hlfernandez.wizard.event.WizardStepListener;

public class ReferenceAnnotationFileSelectionStepTest {

	public static void main(String[] args) {
		DemoUtils.setNimbusLookAndFeel();
		ReferenceAnnotationFileSelectionStep step = 
			new ReferenceAnnotationFileSelectionStep();
		step.addWizardStepListener(new WizardStepListener() {
			
			@Override
			public void wizardStepUncompleted(WizardStepEvent event) {
				System.err.println("Step uncompleted. Is completed = " + step.isStepCompleted());
			}
			
			@Override
			public void wizardStepCompleted(WizardStepEvent event) {
				System.err.println("Step completed. Is completed = " + step.isStepCompleted());
			}
		});
		DemoUtils.showComponent(step.getStepComponent());
	}
}
