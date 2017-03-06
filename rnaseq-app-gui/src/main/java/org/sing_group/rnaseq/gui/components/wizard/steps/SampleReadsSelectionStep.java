package org.sing_group.rnaseq.gui.components.wizard.steps;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;

import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.gui.sample.FastqSamplesEditor;
import org.sing_group.rnaseq.gui.sample.listener.SamplesEditorListener;

import es.uvigo.ei.sing.hlfernandez.wizard.WizardStep;

public class SampleReadsSelectionStep extends WizardStep implements SamplesEditorListener {
	
	private ExperimentalConditionsStep experimentalConditionsStep;
	private FastqSamplesEditor fastqSamplesEditor;
	
	public SampleReadsSelectionStep(
		ExperimentalConditionsStep experimentalConditionsStep
	) {
		this.experimentalConditionsStep = experimentalConditionsStep;
	}

	@Override
	public String getStepTitle() {
		return "Samples selection";
	}

	@Override
	public JComponent getStepComponent() {
		this.fastqSamplesEditor = new FastqSamplesEditor();
		this.fastqSamplesEditor.addSamplesEditorListener(this);
		return fastqSamplesEditor;
	}

	@Override
	public boolean isStepCompleted() {
		return this.fastqSamplesEditor.isValidSelection();
	}

	@Override
	public void stepEntered() {
		List<String> experimentalConditions =
			this.experimentalConditionsStep.getExperimentalConditions();
		this.fastqSamplesEditor.setSelectableConditions(experimentalConditions);
	}

	public FastqReadsSamples getSamples() {
		return this.fastqSamplesEditor.getSamples();
	}

	@Override
	public void onSampleEdited(ChangeEvent event) {
		samplesListEdited();
	}

	@Override
	public void onSampleAdded(ChangeEvent event) {
		samplesListEdited();
	}

	@Override
	public void onSampleRemoved(ChangeEvent event) {
		samplesListEdited();		
	}

	private void samplesListEdited() {
		this.notifyWizardStepStatus();
	}
}
