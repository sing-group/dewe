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

import java.util.LinkedList;
import java.util.List;

import javax.swing.Action;

import org.sing_group.rnaseq.aibench.operations.util.LaunchOperationAbstractAction;

/**
 * A fake workflow description to include the quality control in the AIBench 
 * workflow catalog.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class QualityControlFakeWorkflowDescription
	implements AIBenchWorkflowDescription {
	private static final String FAST_QC = "operations.fastqc";
	private static final String TRIMMOMATIC_SE = "operations.trimmomatic.singleend";
	private static final String TRIMMOMATIC_PE = "operations.trimmomatic.pairedend";

	@Override
	public void launchWorkflowWizard() { }

	@Override
	public void importWorkflow() { }

	@Override
	public String getTitle() {
		return "Quality control";
	}

	@Override
	public String getShortDescription() {
		return "Built-in workflows are meant to start with high quality reads. "
			+ "Also, quality control is a process that must be carried out "
			+ "carefully in order to properly in order to obtain those high "
			+ "quality reads. For this purpose, DEWE includes the FastQC and "
			+ "Trimmomatic tools. Use the following buttons to launch the "
			+ "corresponding operations in case you need to perform the "
			+ "quality control before running a workflow.";
	}
	
	@Override
	public boolean isImportWorkflowOptionEnabled() {
		return false;
	}
	
	@Override
	public boolean isRunWorkflowOptionEnabled() {
		return false;
	}
	
	@Override
	public List<Action> getAdditionalWorkflowActions() {
		List<Action> list = new LinkedList<>();

		list.add(new LaunchOperationAbstractAction("FastQC", FAST_QC));
		list.add(new LaunchOperationAbstractAction("Trimmomatic (single-end)",
			TRIMMOMATIC_SE));
		list.add(new LaunchOperationAbstractAction("Trimmomatic (paired-end)",
			TRIMMOMATIC_PE));

		return list;
	}
}
