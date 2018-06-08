/*
 * #%L
 * DEWE
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
package org.sing_group.rnaseq.aibench.gui.wizard.steps;

import static javax.swing.BorderFactory.createEmptyBorder;
import static org.sing_group.rnaseq.aibench.gui.wizard.AIBenchHisatStringTieAndRDifferentialExpressionWizard.BUILD_INDEX;
import static org.sing_group.rnaseq.aibench.gui.wizard.AIBenchHisatStringTieAndRDifferentialExpressionWizard.IMPORT_INDEX;
import static org.sing_group.rnaseq.gui.components.wizard.steps.StepUtils.configureStepComponent;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.sing_group.gc4s.ui.CenteredJPanel;
import org.sing_group.gc4s.utilities.ExtendedAbstractAction;
import org.sing_group.rnaseq.api.persistence.ReferenceGenomeIndexDatabaseManager;
import org.sing_group.rnaseq.core.persistence.DefaultReferenceGenomeIndexDatabaseManager;
import org.sing_group.rnaseq.gui.components.wizard.steps.Hisat2ReferenceGenomeIndexSelectionStep;

import es.uvigo.ei.aibench.workbench.Workbench;

/**
 * An extension of {@code Hisat2ReferenceGenomeIndexSelectionStep} that adds
 * buttons to import or build genome indexes using AIBench operations.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class AIBenchHisat2ReferenceGenomeIndexSelectionStep
	extends Hisat2ReferenceGenomeIndexSelectionStep {
	private CenteredJPanel stepComponent;
	
	/**
	 * Creates a new {@code AIBenchHisat2ReferenceGenomeIndexSelectionStep}
	 * using {@code DefaultReferenceGenomeIndexDatabaseManager}.
	 */
	public AIBenchHisat2ReferenceGenomeIndexSelectionStep() {
		this(DefaultReferenceGenomeIndexDatabaseManager.getInstance());
	}
	
	/**
	 * Creates a new {@code AIBenchHisat2ReferenceGenomeIndexSelectionStep}
	 * using the specified {@code databaseManager}.
	 * 
	 * @param databaseManager the {@code ReferenceGenomeIndexDatabaseManager}
	 */
	public AIBenchHisat2ReferenceGenomeIndexSelectionStep(
		ReferenceGenomeIndexDatabaseManager databaseManager
	) {
		super(databaseManager);
	}

	@Override
	public JComponent getStepComponent() {
		if(this.stepComponent == null) {
			this.stepComponent = createStepComponent();
			configureStepComponent(this.stepComponent);
		}
		return this.stepComponent;
	}

	private CenteredJPanel createStepComponent() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);
		panel.add(getButtonsPane(), BorderLayout.NORTH);
		panel.add(super.getSelectionPanel(), BorderLayout.CENTER);

		return new CenteredJPanel(panel);
	}

	private JPanel getButtonsPane() {
		JPanel buttonsPanel = new JPanel(new FlowLayout());
		buttonsPanel.setOpaque(false);
		buttonsPanel.setBorder(createEmptyBorder(0, 20, 10, 0));
		buttonsPanel.add(new JButton(
			new ExtendedAbstractAction("Import index", this::importIndex)));
		buttonsPanel.add(new JButton(
			new ExtendedAbstractAction("Build index", this::buildIndex)));

		return buttonsPanel;
	}

	private void importIndex() {
		Workbench.getInstance().executeOperationAndWait(IMPORT_INDEX);
	}

	private void buildIndex() {
		Workbench.getInstance().executeOperationAndWait(BUILD_INDEX);
	}
}
