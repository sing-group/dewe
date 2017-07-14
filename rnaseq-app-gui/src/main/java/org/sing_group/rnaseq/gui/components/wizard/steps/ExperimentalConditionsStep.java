package org.sing_group.rnaseq.gui.components.wizard.steps;

import static org.sing_group.rnaseq.gui.components.wizard.steps.StepUtils.configureStepComponent;

import java.awt.Window;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;

import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXLabel;
import org.sing_group.gc4s.ui.CenteredJPanel;
import org.sing_group.gc4s.utilities.ExtendedAbstractAction;
import org.sing_group.gc4s.wizard.WizardStep;
import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.core.io.samples.ImportExperimentalConditions;
import org.sing_group.rnaseq.gui.components.wizard.components.ExperimentalConditionsSelectionComponent;
import org.sing_group.rnaseq.gui.components.wizard.components.MultipleConditionsSelectionPanel;
import org.sing_group.rnaseq.gui.components.wizard.components.TwoConditionsSelectionPanel;
import org.sing_group.rnaseq.gui.components.wizard.steps.event.ExperimentalConditionsEditorListener;
import org.sing_group.rnaseq.gui.util.CommonFileChooser;

public class ExperimentalConditionsStep extends WizardStep {

	private JPanel stepComponent;
	private int minConditions;
	private int maxConditions;
	private ExperimentalConditionsSelectionComponent conditionsSelectionComponent;
	private Map<String, FastqReadsSamples> experimentalConditionsAndSamples;

	public ExperimentalConditionsStep(int minConditions) {
		this(minConditions, Integer.MAX_VALUE);
	}

	public ExperimentalConditionsStep(int minConditions, int maxConditions) {
		checkConditions(minConditions, maxConditions);
		this.minConditions = minConditions;
		this.maxConditions = maxConditions;
	}

	private void checkConditions(int minConditions, int maxConditions) {
		if (minConditions <= 0) {
			throw new IllegalArgumentException(
				"minConditions must be greater than 0");
		} else if (minConditions > maxConditions) {
			throw new IllegalArgumentException(
				"maxConditions must be greater or equal than minConditions");
		}
	}

	@Override
	public String getStepTitle() {
		return "Experimental conditions";
	}

	@Override
	public JComponent getStepComponent() {
		if(this.stepComponent == null) {
			this.stepComponent = new CenteredJPanel(getSelectionPanel());
			configureStepComponent(stepComponent);
		}
		return this.stepComponent;
	}

	private JComponent getSelectionPanel() {
		JPanel selectionPanel = new JPanel();
		selectionPanel.setLayout(
			new BoxLayout(selectionPanel, BoxLayout.Y_AXIS));
		selectionPanel.setOpaque(false);
		JXLabel descriptionLabel = new JXLabel(
			"Introduce the experimental conditions:");
		selectionPanel.add(Box.createHorizontalGlue());
		selectionPanel.add(descriptionLabel);
		selectionPanel.add(getExperimentalConditionsPanel());
		selectionPanel.add(Box.createHorizontalStrut(30));
		JXButton importConditions = new JXButton(new ExtendedAbstractAction(
			"Or import them from a data directory", this::importConditions));
		selectionPanel.add(importConditions);
		selectionPanel.add(Box.createHorizontalGlue());
		return selectionPanel;
	}

	private JComponent getExperimentalConditionsPanel() {
		if (minConditions == 2 && maxConditions == 2) {
			conditionsSelectionComponent = new TwoConditionsSelectionPanel();
		} else {
			conditionsSelectionComponent = new MultipleConditionsSelectionPanel(
				minConditions, maxConditions);
		}
		((JComponent) conditionsSelectionComponent).setOpaque(false);
		conditionsSelectionComponent.addExperimentalConditionsEditorListener(
			new ExperimentalConditionsEditorListener() {
				@Override
				public void experimentalConditionsChanged(ChangeEvent event) {
					conditionsListChanged();
				}
			}
		);

		return (JComponent) conditionsSelectionComponent;
	}

	private void importConditions() {
		JFileChooser fileChooser = CommonFileChooser.getInstance()
			.getSingleFilechooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fileChooser.showOpenDialog(getDialogParent());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			importConditions(fileChooser.getSelectedFile());
		}
	}

	private void importConditions(File conditionsDir) {
		Map<String, FastqReadsSamples> conditions = ImportExperimentalConditions
			.importDirectory(conditionsDir);
		this.conditionsSelectionComponent.setSelectedConditions(conditions.keySet());
		this.experimentalConditionsAndSamples = conditions;
	}

	private Window getDialogParent() {
		return SwingUtilities.getWindowAncestor(stepComponent);
	}

	private void conditionsListChanged() {
		notifyWizardStepStatus();
	}

	@Override
	public boolean isStepCompleted() {
		return conditionsSelectionComponent.isValidSelection();
	}

	@Override
	public void stepEntered() {
	}

	public List<String> getExperimentalConditions() {
		return new LinkedList<>(
			conditionsSelectionComponent.getSelectedConditions());
	}

	public Optional<Map<String, FastqReadsSamples>> getExperimentalConditionsAndSamples() {
		return Optional.ofNullable(this.experimentalConditionsAndSamples);
	}
}
