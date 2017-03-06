package org.sing_group.rnaseq.gui.components.wizard.steps;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import es.uvigo.ei.sing.hlfernandez.wizard.WizardStep;

public class WizardSummaryStep extends WizardStep {
	private static final Color BG_COLOR = Color.decode("#d6d9df");

	private WizardSummaryProvider wizardSummaryProvider;
	private JTextArea textArea;

	private JScrollPane scrollPane;

	public WizardSummaryStep() {
		this(null);
	}

	public WizardSummaryStep(WizardSummaryProvider wizardSummaryProvider) {
		this.wizardSummaryProvider = wizardSummaryProvider;
	}

	public void setWizardSummaryProvider(WizardSummaryProvider wizardSummaryProvider) {
		this.wizardSummaryProvider = wizardSummaryProvider;
	}

	@Override
	public String getStepTitle() {
		return "Wizard summary";
	}

	@Override
	public JComponent getStepComponent() {
		this.scrollPane = new JScrollPane(getTextArea());
		this.scrollPane.setOpaque(false);
		this.scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		return this.scrollPane;
	}

	private Component getTextArea() {
		this.textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setOpaque(false);
		textArea.setBackground(BG_COLOR);
		textArea.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
		return textArea;
	}

	@Override
	public boolean isStepCompleted() {
		return true;
	}

	@Override
	public void stepEntered() {
		this.textArea.setText(wizardSummaryProvider.getSummary());
	}
}
