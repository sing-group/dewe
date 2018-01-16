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
package org.sing_group.rnaseq.gui.workflow;

import static java.awt.BorderLayout.CENTER;
import static javax.swing.BoxLayout.Y_AXIS;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXLabel;
import org.sing_group.gc4s.ui.text.MultilineLabel;
import org.sing_group.rnaseq.api.entities.WorkflowDescription;

/**
 * A panel to show the workflow description.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class WorkflowDescriptionPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private WorkflowDescription workflow;

	/**
   * Creates a new {@code WorkflowDescriptionPanel} to show the description of
   * the specified {@code workflow}.
   * 
   * @param workflow a {@code WorkflowDescription}
   */
	public WorkflowDescriptionPanel(WorkflowDescription workflow) {
		this.workflow = workflow;

		this.init();
	}

	private void init() {
		this.setOpaque(false);
		this.setLayout(new BorderLayout());
		this.add(getDescritionPanel(), CENTER);
	}

	protected JComponent getDescritionPanel() {
		JPanel descriptionPanel = new JPanel();
		descriptionPanel.setOpaque(false);
		descriptionPanel.setLayout(new BoxLayout(descriptionPanel, Y_AXIS));
		descriptionPanel.add(getTitleLabel());
		descriptionPanel.add(getDescriptionLabel());
		return descriptionPanel;
	}

	protected JXLabel getTitleLabel() {
		JXLabel label = new JXLabel(workflow.getTitle());
		label.setFont(label.getFont().deriveFont(Font.BOLD, 14f));
		label.setAlignmentX(LEFT_ALIGNMENT);

		return label;
	}

	protected JComponent getDescriptionLabel() {
		MultilineLabel description = new MultilineLabel(
			workflow.getShortDescription());
		description.setFont(description.getFont().deriveFont(13f));
		return description;
	}
}
