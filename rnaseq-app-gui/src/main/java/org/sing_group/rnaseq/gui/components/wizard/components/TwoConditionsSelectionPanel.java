/*
 * #%L
 * DEWE GUI
 * %%
 * Copyright (C) 2016 - 2017 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
 * 			Borja Sánchez, and Anália Lourenço
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package org.sing_group.rnaseq.gui.components.wizard.components;

import static java.util.Arrays.asList;
import static javax.swing.SwingUtilities.getWindowAncestor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentListener;

import org.jdesktop.swingx.JXTextField;
import org.sing_group.gc4s.event.DocumentAdapter;
import org.sing_group.gc4s.input.InputParameter;
import org.sing_group.gc4s.input.InputParametersPanel;
import org.sing_group.gc4s.utilities.ExtendedAbstractAction;
import org.sing_group.rnaseq.gui.components.wizard.steps.ControlAndTreatmentConditionsDialog;
import org.sing_group.rnaseq.gui.components.wizard.steps.event.ExperimentalConditionsEditorListener;
import org.sing_group.rnaseq.gui.util.UISettings;

/**
 * A component that allows the introduction of two condition labels using 
 * text fields.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class TwoConditionsSelectionPanel extends JPanel
	implements ExperimentalConditionsSelectionComponent {
	private static final long serialVersionUID = 1L;

	private InputParametersPanel inputParametersPanel;
	private JXTextField conditionAtf;
	private JXTextField conditionBtf;

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

	private JButton controlTreatmentButton;

	/**
	 * Creates a new {@code TwoConditionsSelectionPanel} component.
	 */
	public TwoConditionsSelectionPanel() {
		this.init();
	}

	private void init() {
		this.setLayout(new BorderLayout());
		this.add(getInputParametersPanel(), BorderLayout.CENTER);
		this.add(getSouthComponent(), BorderLayout.EAST);
		this.checkConditions();
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
		conditionAtf = new JXTextField("Condition A");
		conditionAtf.getDocument().addDocumentListener(documentListener);
		return new InputParameter("Condition A", conditionAtf,
			"The first condition");
	}

	private InputParameter getConditionBParameter() {
		conditionBtf = new JXTextField("Condition B");
		conditionBtf.getDocument().addDocumentListener(documentListener);
		return new InputParameter("Condition B", conditionBtf,
			"The second condition");
	}

	private void conditionsChanged() {
		checkConditions();
		for (ExperimentalConditionsEditorListener l : this
			.getListeners(ExperimentalConditionsEditorListener.class)
		) {
			l.experimentalConditionsChanged(new ChangeEvent(this));
		}
	}

	private void checkConditions() {
		conditionAtf.setBackground(getConditionABackgroundColor());
		conditionBtf.setBackground(getConditionBBackgroundColor());
		controlTreatmentButton.setEnabled(isValidSelection());
	}

	private Color getConditionABackgroundColor() {
		return isConditionAValid() ? null : UISettings.COLOR_ERROR;
	}

	private Color getConditionBBackgroundColor() {
		return isConditionBValid() ? null : UISettings.COLOR_ERROR;
	}

	@Override
	public boolean isValidSelection() {
		return isConditionAValid() && isConditionBValid();
	}

	private boolean isConditionAValid() {
		return !conditionAtf.getText().isEmpty() && areConditionsDifferent();
	}

	private boolean isConditionBValid() {
		return !conditionBtf.getText().isEmpty() && areConditionsDifferent();
	}

	private boolean areConditionsDifferent() {
		return !conditionAtf.getText().equals(conditionBtf.getText());
	}

	public JComponent getSouthComponent() {
		controlTreatmentButton = new JButton(
			new ExtendedAbstractAction(
				"<html>Define control <br/>and treatment</html>",
				this::defineControlAndTreatment)
		);
		controlTreatmentButton.setEnabled(false);
		return controlTreatmentButton;
	}

	private void defineControlAndTreatment() {
		ControlAndTreatmentConditionsDialog dialog =
			new ControlAndTreatmentConditionsDialog(getWindowAncestor(this),
			conditionAtf.getText(), conditionBtf.getText());
		dialog.setVisible(true);

		if (!dialog.isCanceled()) {
			updateConditionNames(dialog.getConditionMapping());
		}
	}

	private void updateConditionNames(Map<String, String> conditionMapping) {
		this.conditionAtf.setText(
			conditionMapping.get(this.conditionAtf.getText()));
		this.conditionBtf.setText(
			conditionMapping.get(this.conditionBtf.getText()));
		for (ExperimentalConditionsEditorListener l : this
			.getListeners(ExperimentalConditionsEditorListener.class)
		) {
			l.experimentalConditionsRenamed(new ChangeEvent(this), conditionMapping);
		}
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
	
	@Override
	public void setExperimentalConditionIntroductionEnabled(boolean enabled) {
		this.conditionAtf.setEnabled(enabled);
		this.conditionBtf.setEnabled(enabled);
	}
}
