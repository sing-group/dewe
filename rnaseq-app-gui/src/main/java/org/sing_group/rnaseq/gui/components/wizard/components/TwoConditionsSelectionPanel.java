package org.sing_group.rnaseq.gui.components.wizard.components;

import static java.util.Arrays.asList;
import static org.sing_group.rnaseq.gui.util.UISettings.COLOR_ERROR;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentListener;

import org.sing_group.gc4s.event.DocumentAdapter;
import org.sing_group.gc4s.input.InputParameter;
import org.sing_group.gc4s.input.InputParametersPanel;
import org.sing_group.gc4s.text.ExtendedJXTextField;
import org.sing_group.rnaseq.gui.components.wizard.steps.event.ExperimentalConditionsEditorListener;

public class TwoConditionsSelectionPanel extends JPanel
	implements ExperimentalConditionsSelectionComponent {
	private static final long serialVersionUID = 1L;

	private InputParametersPanel inputParametersPanel;
	private ExtendedJXTextField conditionAtf;
	private ExtendedJXTextField conditionBtf;

	private DocumentListener documentListener = new DocumentAdapter() {
		public void insertUpdate(javax.swing.event.DocumentEvent e) {
			conditionsChanged();
		};

		public void changedUpdate(javax.swing.event.DocumentEvent e) {
			conditionsChanged();
		};

		public void removeUpdate(javax.swing.event.DocumentEvent e) {
			conditionsChanged();
		};
	};


	public TwoConditionsSelectionPanel() {
		this.init();
	}

	private void init() {
		this.add(getInputParametersPanel());
	}

	private Component getInputParametersPanel() {
		inputParametersPanel = new InputParametersPanel(getInputParameters());
		inputParametersPanel.setOpaque(false);
		return inputParametersPanel;
	}

	private InputParameter[] getInputParameters() {
		InputParameter[] parameters = new InputParameter[2];
		parameters[0] = getConditionAParameter();
		parameters[1] = getConditionBParameter();
		return parameters;
	}

	private InputParameter getConditionAParameter() {
		conditionAtf = new ExtendedJXTextField("Condition A");
		conditionAtf.setEmptyTextFieldColor(COLOR_ERROR);
		conditionAtf.getDocument().addDocumentListener(documentListener);
		return new InputParameter("Condition A", conditionAtf,
			"The first condition");
	}

	private InputParameter getConditionBParameter() {
		conditionBtf = new ExtendedJXTextField("Condition B");
		conditionBtf.setEmptyTextFieldColor(COLOR_ERROR);
		conditionBtf.getDocument().addDocumentListener(documentListener);
		return new InputParameter("Condition B", conditionBtf,
			"The second condition");
	}

	private void conditionsChanged() {
		for (ExperimentalConditionsEditorListener l : this
			.getListeners(ExperimentalConditionsEditorListener.class)
		) {
			l.experimentalConditionsChanged(new ChangeEvent(this));
		}
	}

	@Override
	public boolean isValidSelection() {
		return !conditionAtf.getText().isEmpty()
			&& !conditionBtf.getText().isEmpty();
	}

	@Override
	public Set<String> getSelectedConditions() {
		return new HashSet<>(
			asList(conditionAtf.getText(), conditionBtf.getText()));
	}

	@Override
	public void addExperimentalConditionsEditorListener(
		ExperimentalConditionsEditorListener l
	) {
		this.listenerList.add(ExperimentalConditionsEditorListener.class, l);
	}

	@Override
	public void setSelectedConditions(Set<String> selectedConditions) {
		List<String> conditions = new ArrayList<>(selectedConditions);
		if (conditions.size() > 0) {
			this.conditionAtf.setText(conditions.get(0));
			if (conditions.size() > 1) {
				this.conditionBtf.setText(conditions.get(1));
			}
		}
	}
}
