package org.sing_group.rnaseq.gui.components.wizard.steps;

import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.sing_group.gc4s.dialog.AbstractInputJDialog;

/**
 * A dialog that allows the definition of control and treatment conditions.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class ControlAndTreatmentConditionsDialog extends AbstractInputJDialog {
	private static final String PREFIX_CONTROL = "Control_";
	private static final String PREFIX_TREATMENT = "Treatment_";
	private static final long serialVersionUID = 1L;
	private JLabel conditionALabel;
	private JLabel conditionBLabel;
	private JRadioButton conditionATreatment;
	private JRadioButton conditionAControl;
	private JRadioButton conditionBTreatment;
	private JRadioButton conditionBControl;
	private String conditionA;
	private String conditionADisplayName;
	private String conditionB;
	private String conditionBDisplayName;

	/**
	 * Creates a new {@code ControlAndTreatmentConditionsDialog} instance.
	 *
	 * @param parent the parent {@code Window}
	 * @param conditionA the first condition
	 * @param conditionB the second condition
	 */
	public ControlAndTreatmentConditionsDialog(Window parent,
		String conditionA, String conditionB
	) {
		super(parent);

		this.conditionA = conditionA;
		this.conditionADisplayName = cleanConditionName(conditionA);
		this.conditionALabel.setText(this.conditionADisplayName);
		this.conditionB = conditionB;
		this.conditionBDisplayName = cleanConditionName(conditionB);
		this.conditionBLabel.setText(this.conditionBDisplayName);
	}

	@Override
	protected String getDialogTitle() {
		return "Control and treatment conditions definition";
	}

	@Override
	protected String getDescription() {
		return "This dialog allows you to define which condition should be used "
			+ "as control and which condition as treatment (or case). To do "
			+ "that, use the radio buttons to define your selection.";
	}

	@Override
	protected JPanel getInputComponentsPane() {
		JPanel inputComponentsPane = new JPanel(new GridLayout(2, 2));
		inputComponentsPane.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		conditionALabel = new JLabel();
		conditionBLabel = new JLabel();

		inputComponentsPane.add(conditionALabel);
		inputComponentsPane.add(getConditionADefinitionPanel());
		inputComponentsPane.add(conditionBLabel);
		inputComponentsPane.add(getConditionBDefinitionPanel());

		return inputComponentsPane;
	}

	private JPanel getConditionADefinitionPanel() {
		ButtonGroup conditionAGroup = new ButtonGroup();
		conditionATreatment = new JRadioButton("Treatment");
		conditionAControl = new JRadioButton("Control");
		conditionAGroup.add(conditionATreatment);
		conditionAGroup.add(conditionAControl);

		JPanel conditionAPanel = new JPanel(new GridLayout(1, 2));
		conditionAPanel.add(conditionATreatment);
		conditionAPanel.add(conditionAControl);

		ItemListener itemListener = new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					conditionASelectionChanged();
				}
			}
		};
		conditionATreatment.addItemListener(itemListener);
		conditionAControl.addItemListener(itemListener);

		return conditionAPanel;
	}

	private void conditionASelectionChanged() {
		conditionBTreatment.setSelected(!conditionATreatment.isSelected());
		conditionBControl.setSelected(conditionATreatment.isSelected());
		this.okButton.setEnabled(true);
	}

	private JPanel getConditionBDefinitionPanel() {
		ButtonGroup conditionBGroup = new ButtonGroup();
		conditionBTreatment = new JRadioButton("Treatment");
		conditionBControl = new JRadioButton("Control");
		conditionBGroup.add(conditionBTreatment);
		conditionBGroup.add(conditionBControl);

		JPanel conditionBPanel = new JPanel(new GridLayout(1, 2));
		conditionBPanel.add(conditionBTreatment);
		conditionBPanel.add(conditionBControl);
		conditionBTreatment.setEnabled(false);
		conditionBControl.setEnabled(false);

		return conditionBPanel;
	}

	@Override
	public void setVisible(boolean b) {
		this.pack();
		super.setVisible(b);
	}

	/**
	 * Returns a map from the original names to the new ones.
	 *
	 * @return a map from the original names to the new ones
	 */
	public Map<String, String> getConditionMapping() {
		Map<String, String> toret = new HashMap<>();
		toret.put(this.conditionA, getConditionANewName());
		toret.put(this.conditionB, getConditionBNewName());
		return toret;
	}

	private String getConditionANewName() {
		return this.conditionATreatment.isSelected()
			? getTreatmentName(this.conditionADisplayName)
			: getControlName(this.conditionADisplayName);
	}

	private String getConditionBNewName() {
		return this.conditionBTreatment.isSelected()
			? getTreatmentName(this.conditionBDisplayName)
			: getControlName(this.conditionBDisplayName);
	}

	private static String getTreatmentName(String condition) {
		return PREFIX_TREATMENT + condition;
	}

	private static String getControlName(String condition) {
		return PREFIX_CONTROL + condition;
	}

	private static String cleanConditionName(String condition) {
		return condition
			.replace(PREFIX_CONTROL, "").replace(PREFIX_TREATMENT, "");
	}
}
