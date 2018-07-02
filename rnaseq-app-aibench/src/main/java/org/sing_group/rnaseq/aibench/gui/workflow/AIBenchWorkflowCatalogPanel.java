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
package org.sing_group.rnaseq.aibench.gui.workflow;

import static java.util.Arrays.asList;

import java.util.List;

import org.sing_group.rnaseq.api.entities.WorkflowDescription;
import org.sing_group.rnaseq.gui.workflow.WorkflowCatalogPanel;

/**
 * An extension of {@code WorkflowCatalogPanel} to use as AIBench component.
 * This class defines the available workflows and overrides the
 * {@code runWorkflow} method in order to execute them.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class AIBenchWorkflowCatalogPanel extends WorkflowCatalogPanel {
	private static final long serialVersionUID = 1L;

	private static final List<WorkflowDescription> WORKFLOWS = asList(
		new QualityControlFakeWorkflowDescription(),
		new Bowtie2StringTieDifferentialExpressionAIBenchWorkflowDescription(),
		new Hisat2StringTieDifferentialExpressionAIBenchWorkflowDescription()
	);

	/**
	 * Creates a new {@code AIBenchWorkflowCatalogPanel} component.
	 */
	public AIBenchWorkflowCatalogPanel() {
		super(WORKFLOWS);
	}

	@Override
	protected void runWorkflow(WorkflowDescription workflow) {
		if (workflow instanceof AIBenchWorkflowDescription) {
			((AIBenchWorkflowDescription) workflow).launchWorkflowWizard();
		} else {
			super.runWorkflow(workflow);
		}
	}

	@Override
	protected void importWorkflow(WorkflowDescription workflow) {
		if (workflow instanceof AIBenchWorkflowDescription) {
			((AIBenchWorkflowDescription) workflow).importWorkflow();
		} else {
			super.importWorkflow(workflow);
		}
	}
}
