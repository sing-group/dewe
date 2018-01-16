/*
 * #%L
 * DEWE GUI
 * %%
 * Copyright (C) 2016 - 2018 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
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

import static javax.swing.BorderFactory.createEmptyBorder;
import static org.sing_group.rnaseq.gui.components.wizard.steps.StepUtils.configureStepComponent;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import org.sing_group.gc4s.dialog.wizard.WizardStep;

/**
 * This abstract class extends {@code WizardStep} to show a text formatted as
 * HTML. Concrete classes must implement the {@code getHtmlText} method to
 * return this text.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public abstract class AbstractHtmlTextStep extends WizardStep {
	private JComponent stepComponent;

	@Override
	public boolean isStepCompleted() {
		return true;
	}

	@Override
	public abstract String getStepTitle();

	@Override
	public JComponent getStepComponent() {
		this.stepComponent = getWelcomePanel();
		configureStepComponent(stepComponent);

		return this.stepComponent;
	}

	private JComponent getWelcomePanel() {
		JTextPane textArea = new JTextPane();
		textArea.setContentType("text/html");
		textArea.setEditable(false);
		textArea.setOpaque(false);
		textArea.setFont(textArea.getFont().deriveFont(13f));
		textArea.setBorder(createEmptyBorder(20, 10, 20, 10));
		textArea.setText(getHtmlText());

		return new JScrollPane(textArea);
	}

	/**
	 * Returns an string formatted in HTML with the text to be displayed.
	 *
	 * @return an string formatted in HTML with the text to be displayed
	 */
	protected abstract String getHtmlText();

	@Override
	public void stepEntered() {
	}
}
