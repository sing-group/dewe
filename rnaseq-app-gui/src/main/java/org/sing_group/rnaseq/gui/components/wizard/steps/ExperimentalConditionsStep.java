package org.sing_group.rnaseq.gui.components.wizard.steps;

import static org.sing_group.rnaseq.gui.components.wizard.steps.StepUtils.configureStepComponent;

import java.awt.FlowLayout;
import java.awt.Window;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;

import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXLabel;
import org.sing_group.gc4s.ui.CenteredJPanel;
import org.sing_group.gc4s.ui.icons.Icons;
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

	private static final String TOOLTIP_IMPORT_CONDITIONS = "<html>"
		+ "This option allows you to import the experimental conditions and "
		+ "their samples from a directory. <br/><br/>To do that, the directory "
		+ "that you select here must be organized as follows:<ul>"
		+ "<li>It must contain two folders and the name of each folder will "
		+ "correspond to a condition.</li>"
		+ "<li>Each of the two folders must contain the pairwise files of the "
		+ "samples and these files must be in .fq, .fastq or .fastq.gz format."
		+ "</li></ul>"
		+ "Note that samples are paired-end and the first read file must "
		+ "end in _1 and the second in _2.";

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
		descriptionLabel.setAlignmentX(SwingConstants.LEFT);
		selectionPanel.add(Box.createHorizontalGlue());
		selectionPanel.add(descriptionLabel);
		selectionPanel.add(getExperimentalConditionsPanel());
		selectionPanel.add(Box.createHorizontalStrut(30));

		JPanel importConditionsPanel = new JPanel();
		importConditionsPanel.setLayout(new FlowLayout());
		importConditionsPanel.setOpaque(false);
		JXButton importConditions = new JXButton(new ExtendedAbstractAction(
			"Or import them from a data directory", this::importConditions));
		importConditionsPanel.add(importConditions);
		JLabel importConditionsInfo = new JLabel(Icons.ICON_INFO_16);
		importConditionsInfo.setToolTipText(TOOLTIP_IMPORT_CONDITIONS);
		importConditionsPanel.add(importConditionsInfo);

		selectionPanel.add(importConditionsPanel);
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

				@Override
				public void experimentalConditionsRenamed(ChangeEvent event,
					Map<String, String> renameMap) {
					conditionsRenamed(renameMap);
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

	protected void conditionsRenamed(Map<String, String> renameMap) {
		renameConditions(renameMap);
		notifyWizardStepStatus();
	}

	private void renameConditions(Map<String, String> renameMap) {
		if (this.experimentalConditionsAndSamples != null) {
			Map<String, FastqReadsSamples> newExperimentalConditionsAndSamples = new HashMap<>();
			for (Entry<String, FastqReadsSamples> entry : experimentalConditionsAndSamples
				.entrySet()) {
				newExperimentalConditionsAndSamples
					.put(renameMap.get(entry.getKey()), entry.getValue());
			}
			this.experimentalConditionsAndSamples = newExperimentalConditionsAndSamples;
		}
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
