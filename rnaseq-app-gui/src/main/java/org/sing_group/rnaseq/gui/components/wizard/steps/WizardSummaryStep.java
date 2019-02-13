/*
 * #%L
 * DEWE GUI
 * %%
 * Copyright (C) 2016 - 2019 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
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
package org.sing_group.rnaseq.gui.components.wizard.steps;

import static org.sing_group.rnaseq.gui.components.wizard.steps.StepUtils.configureStepComponent;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.sing_group.gc4s.dialog.wizard.WizardStep;

/**
 * A {@code WizardStep} implementation that shows a wizard summary provided by a
 * {@code WizardSummaryProvider}.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class WizardSummaryStep extends WizardStep {
	private WizardSummaryProvider wizardSummaryProvider;
	private JTextArea textArea;

	private JScrollPane scrollPane;

	/**
	 * Creates an empty {@code WizardSummaryStep}.
	 */
	public WizardSummaryStep() {
		this(null);
	}

	/**
	 * Creates a new {@code WizardSummaryStep} with the specified wizard summary
	 * provider.
	 * 
	 * @param wizardSummaryProvider a {@code WizardSummaryProvider}
	 */
	public WizardSummaryStep(WizardSummaryProvider wizardSummaryProvider) {
		this.wizardSummaryProvider = wizardSummaryProvider;
	}

	/**
	 * Sets the {@code WizardSummaryProvider}.
	 * 
	 * @param wizardSummaryProvider a {@code WizardSummaryProvider}
	 */
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
		this.scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		configureStepComponent(this.scrollPane);
		return this.scrollPane;
	}

	private Component getTextArea() {
		this.textArea = new JTextArea();
		textArea.setTabSize(2);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setOpaque(false);
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
