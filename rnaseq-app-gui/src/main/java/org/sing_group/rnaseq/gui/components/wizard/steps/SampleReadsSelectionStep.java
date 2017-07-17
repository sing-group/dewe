package org.sing_group.rnaseq.gui.components.wizard.steps;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static org.sing_group.rnaseq.gui.components.wizard.steps.StepUtils.configureStepComponent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;

import org.sing_group.gc4s.wizard.WizardStep;
import org.sing_group.rnaseq.api.entities.FastqReadsSample;
import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.gui.sample.FastqSamplesEditor;
import org.sing_group.rnaseq.gui.sample.listener.SamplesEditorListener;

public class SampleReadsSelectionStep extends WizardStep
	implements SamplesEditorListener {

	private static final String WARNING_SAMPLES_CONDITION =
		"At least two samples must be assigned to each condition.";

	private ExperimentalConditionsStep experimentalConditionsStep;
	private FastqSamplesEditor fastqSamplesEditor;
	private int minSamplesPerCondition;
	private int initialNumSamples;

	public SampleReadsSelectionStep(
		ExperimentalConditionsStep experimentalConditionsStep,
		int minSamplesPerCondition,
		int initialNumSamples
	) {
		this.experimentalConditionsStep = experimentalConditionsStep;
		this.minSamplesPerCondition = minSamplesPerCondition;
		this.initialNumSamples = initialNumSamples;
	}

	@Override
	public String getStepTitle() {
		return "Samples selection";
	}

	@Override
	public JComponent getStepComponent() {
		this.fastqSamplesEditor = new FastqSamplesEditor(initialNumSamples);
		this.fastqSamplesEditor.addSamplesEditorListener(this);
		configureStepComponent(fastqSamplesEditor);
		return fastqSamplesEditor;
	}

	@Override
	public boolean isStepCompleted() {
		if (this.fastqSamplesEditor.isValidSelection()) {
			return checkSamplesSelection();
		} else {
			return false;
		}
	}

	private boolean checkSamplesSelection() {
		boolean samplesPerCondition = checkSamplesPerCondition();
		List<String> duplicatedSampleNames = findDuplicateSampleNames();
		boolean allSampleNamesAreUnique = duplicatedSampleNames.isEmpty();

		if (samplesPerCondition && allSampleNamesAreUnique) {
			this.fastqSamplesEditor.removeWarningMessages();
			return true;
		} else {
			final List<String> warningMessages =
				getWarningMessages(samplesPerCondition, duplicatedSampleNames);
			this.fastqSamplesEditor.setWarningMessages(warningMessages);
			return false;
		}
	}

	private List<String> findDuplicateSampleNames() {
		Map<Object, Long> counts = this.fastqSamplesEditor.getSamples().stream()
			.map(FastqReadsSample::getName)
			.collect(groupingBy(e -> e, counting()));

		List<String> duplicatedNames = new LinkedList<>();
		for (Entry<Object, Long> entry : counts.entrySet()) {
			if (entry.getValue() > 1) {
				duplicatedNames.add(entry.getKey().toString());
			}
		}

		return duplicatedNames;
	}

	private List<String> getWarningMessages(boolean samplesPerCondition,
		List<String> duplicatedSampleNames
	) {
		boolean allSampleNamesAreUnique = duplicatedSampleNames.isEmpty();
		final List<String> warningMessages = new ArrayList<>();

		if (!samplesPerCondition) {
			warningMessages.add(WARNING_SAMPLES_CONDITION);
		}

		if(!allSampleNamesAreUnique) {
			duplicatedSampleNames.stream()
				.map(n -> "Sample name '" + n + " 'is duplicated.")
				.forEach(n -> warningMessages.add(n));
		}

		return warningMessages;
	}

	private boolean checkSamplesPerCondition() {
		Map<String, Long> counts = this.fastqSamplesEditor.getSamples().stream()
			.map(FastqReadsSample::getCondition)
			.collect(groupingBy(e -> e, counting()));

		return allConditionsSelected(counts)
			&& eachConditionContainsMinimumSamples(counts);
	}

	private boolean eachConditionContainsMinimumSamples(
		Map<String, Long> counts) {
		return !counts.values().stream()
				.filter(numSamples -> numSamples < minSamplesPerCondition)
				.findAny().isPresent();
	}

	private boolean allConditionsSelected(Map<String, Long> counts) {
		return counts.keySet().containsAll(
			this.experimentalConditionsStep.getExperimentalConditions());
	}

	@Override
	public void stepEntered() {
		List<String> experimentalConditions =
			this.experimentalConditionsStep.getExperimentalConditions();
		this.fastqSamplesEditor.setSelectableConditions(experimentalConditions);

		Optional<Map<String, FastqReadsSamples>> conditionSamples =
			this.experimentalConditionsStep.getExperimentalConditionsAndSamples();
		if (conditionSamples.isPresent()) {
			this.setSamples(conditionSamples.get());
		}
	}

	private void setSamples(
		Map<String, FastqReadsSamples> conditionSamples) {
		if (conditionSamples != null) {
			this.fastqSamplesEditor.setSamples(conditionSamples.values()
				.stream().flatMap(List::stream).collect(Collectors.toList()));
			samplesListEdited();
		}
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
