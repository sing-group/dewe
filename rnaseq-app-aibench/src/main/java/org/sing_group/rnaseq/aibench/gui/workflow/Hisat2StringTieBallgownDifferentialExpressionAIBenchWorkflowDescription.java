/*
 * #%L
 * DEWE
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
package org.sing_group.rnaseq.aibench.gui.workflow;

import org.sing_group.rnaseq.aibench.gui.wizard.AIBenchHisatStringTieAndBallgownDifferentialExpressionWizard;
import org.sing_group.rnaseq.gui.workflow.Hisat2StringTieBallgownDifferentialExpressionWorkflowDescription;

/**
 * The AIBench description for the differential expression workflow that uses
 * HISAT2, StringTie and Ballgown.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class Hisat2StringTieBallgownDifferentialExpressionAIBenchWorkflowDescription
	extends Hisat2StringTieBallgownDifferentialExpressionWorkflowDescription 
	implements AIBenchWorkflowDescription {

	@Override
	public void launchWorkflowWizard() {
		AIBenchHisatStringTieAndBallgownDifferentialExpressionWizard.showWizard(false);
	}

	@Override
	public void importWorkflow() {
		AIBenchHisatStringTieAndBallgownDifferentialExpressionWizard.showWizard(true);
	}
}
